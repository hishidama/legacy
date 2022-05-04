package jp.hishidama.hadoop.cascading.student;

import java.util.StringTokenizer;

import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import cascading.flow.Flow;
import cascading.flow.FlowConnector;
import cascading.flow.FlowProcess;
import cascading.operation.Aggregator;
import cascading.operation.AggregatorCall;
import cascading.operation.BaseOperation;
import cascading.operation.Debug;
import cascading.operation.Function;
import cascading.operation.FunctionCall;
import cascading.operation.aggregator.Average;
import cascading.operation.text.FieldJoiner;
import cascading.pipe.CoGroup;
import cascading.pipe.Each;
import cascading.pipe.Every;
import cascading.pipe.GroupBy;
import cascading.pipe.Pipe;
import cascading.pipe.cogroup.LeftJoin;
import cascading.scheme.TextLine;
import cascading.tap.Hfs;
import cascading.tap.SinkMode;
import cascading.tap.Tap;
import cascading.tuple.Fields;
import cascading.tuple.Tuple;
import cascading.tuple.TupleEntryCollector;

import jp.hishidama.hadoop.cascading.conf.CascadingConfigured;

public class Student0 extends CascadingConfigured implements Tool {

	public static final String F_LINE = "line";

	public static final String F_SID = "student-id";
	public static final String F_NAME = "student-name";
	public static final String F_KID = "kamoku-id";
	public static final String F_TEN = "ten";
	public static final String F_AVE = "average";
	public static final String F_SIGMA = "standard-deviation";
	public static final String F_SCORE = "standard-score";

	/**
	 * 学生名を抽出するFunction
	 */
	protected static class NameFunction extends BaseOperation<Object> implements
			Function<Object> {
		private static final long serialVersionUID = 1L;

		public NameFunction(Fields fieldDeclaration) {
			super(1, fieldDeclaration);
		}

		@Override
		public void operate(FlowProcess flowProcess,
				FunctionCall<Object> functionCall) {

			String line = functionCall.getArguments().getTuple().getString(0);
			if (line.isEmpty() || line.startsWith("#")) {
				return;
			}
			TupleEntryCollector collector = functionCall.getOutputCollector();

			String sid = null;
			String name = null;

			StringTokenizer tokenizer = new StringTokenizer(line, ",");
			loop: for (int i = 0; tokenizer.hasMoreTokens(); i++) {
				String token = tokenizer.nextToken().trim();
				switch (i) {
				case 0: // 学生番号
					sid = token;
					break;
				case 1: // 学生名
					name = token;
					collector.add(new Tuple(sid, name));
					break loop;
				}
			}
		}
	}

	static final String[] KAMOKU_NAME = { "数学", "国語", "理科", "社会", "英語", "合計" };

	/**
	 * 成績（点数）を抽出するFunction
	 */
	protected static class TenFunction extends BaseOperation<Object> implements
			Function<Object> {
		private static final long serialVersionUID = 1L;

		public TenFunction(Fields fieldDeclaration) {
			super(1, fieldDeclaration);
		}

		@Override
		public void operate(FlowProcess flowProcess,
				FunctionCall<Object> functionCall) {

			Tuple value = functionCall.getArguments().getTuple();
			String line = value.getString(0);
			if (line.isEmpty() || line.startsWith("#")) {
				return;
			}

			TupleEntryCollector collector = functionCall.getOutputCollector();

			String sid = null;
			int sum = 0;

			StringTokenizer tokenizer = new StringTokenizer(line, ",");
			for (int i = 0; tokenizer.hasMoreTokens(); i++) {
				String token = tokenizer.nextToken().trim();
				switch (i) {
				case 0: // 学生番号
					sid = token;
					break;
				case 1: // 学生名
					break;
				default:
					int ten = Integer.parseInt(token);
					Tuple t = Tuple.size(fieldDeclaration.size());
					t.set(0, sid);
					t.set(1, KAMOKU_NAME[i - 2]);
					t.set(2, ten);
					collector.add(t);

					sum += ten;
					break;
				}
			}

			Tuple t = Tuple.size(fieldDeclaration.size());
			t.set(0, sid);
			t.set(1, KAMOKU_NAME[5]);
			t.set(2, sum);
			collector.add(t);
		}
	}

	/**
	 * 標準偏差を求めるAggregator
	 */
	protected static class StandardDeviation extends
			BaseOperation<StandardDeviation.Context> implements
			Aggregator<StandardDeviation.Context> {
		private static final long serialVersionUID = 1L;

		protected static class Context {
			double sum;
			int count;

			void reset() {
				sum = 0;
				count = 0;
			}
		}

		public StandardDeviation(Fields fieldDeclaration) {
			super(2, fieldDeclaration);
			// args0：点数
			// args1：平均点
		}

		@Override
		public void start(FlowProcess flowProcess,
				AggregatorCall<Context> aggregatorCall) {
			Context ctx = aggregatorCall.getContext();
			if (ctx == null) {
				aggregatorCall.setContext(new Context());
			} else {
				ctx.reset();
			}
		}

		@Override
		public void aggregate(FlowProcess flowProcess,
				AggregatorCall<Context> aggregatorCall) {
			Context ctx = aggregatorCall.getContext();
			Tuple value = aggregatorCall.getArguments().getTuple();
			double ten = value.getDouble(0);
			double average = value.getDouble(1);
			ctx.sum += Math.pow(ten - average, 2);
			ctx.count++;
		}

		@Override
		public void complete(FlowProcess flowProcess,
				AggregatorCall<Context> aggregatorCall) {
			Context ctx = aggregatorCall.getContext();
			aggregatorCall.getOutputCollector().add(
					new Tuple(Math.sqrt(ctx.sum / ctx.count)));
		}
	}

	/**
	 * 偏差値を求めるFunction
	 */
	protected static class StandardScore extends BaseOperation<Object>
			implements Function<Object> {
		private static final long serialVersionUID = 1L;

		public StandardScore(Fields fields) {
			super(3, fields);
			// args0：点数
			// args1：平均点
			// args2：標準偏差
		}

		@Override
		public void operate(FlowProcess flowProcess,
				FunctionCall<Object> functionCall) {
			Tuple value = functionCall.getArguments().getTuple();
			double ten = value.getInteger(0);
			double average = value.getDouble(1);
			double sigma = value.getDouble(2);
			double score = (ten - average) * 10 / sigma + 50;
			functionCall.getOutputCollector().add(new Tuple(score));
		}
	}

	@Override
	public int run(String[] args) throws Exception {
		Tap source = new Hfs(new TextLine(new Fields(F_LINE)),
				makeQualifiedPath(args[0]));
		Tap sink = new Hfs(new TextLine(), makeQualifiedPath(args[1]),
				SinkMode.REPLACE);

		Pipe pipe = new Pipe("pipe");

		// 成績
		Pipe pipe1 = new Pipe("pipe1", pipe);
		pipe1 = new Each(pipe1,
				new TenFunction(new Fields(F_SID, F_KID, F_TEN)));

		// 科目毎の平均点を算出
		Pipe pipe11 = new GroupBy("pipeAverage", pipe1, new Fields(F_KID));
		pipe11 = new Every(pipe11, new Fields(F_TEN), new Average(new Fields(
				F_AVE)));

		// 科目毎に標準偏差を求める
		Pipe pipe12 = new CoGroup("pipeSDV", pipe1, new Fields(F_KID), pipe11,
				new Fields(F_KID), new Fields(F_SID, F_KID, F_TEN,
						F_KID + ".2", F_AVE));

		// Cascading1.0では、CoGroupの後でEveryを使う場合、出力項目名が0になってしまう。
		// その結果、後続でkamoku-idが見つからなくてエラーになる。
		// GroupByでソートして項目名を付ければ一応大丈夫
		// もしくは後続でF_KIDを使わず0を指定する
		if (false) {
			if (false) {
				// pipe12 = new Each(pipe12,Fields.ALL, new Debug(true));
				// pipe12 = new Each(pipe12,Fields.GROUP, new Debug(true));
				pipe12 = new Each(pipe12, new Fields(F_KID), new Debug(true));
			}
			pipe12 = new GroupBy(pipe12, new Fields(F_KID));
		}

		pipe12 = new Every(pipe12, new Fields(F_TEN, F_AVE),
				new StandardDeviation(new Fields(F_SIGMA))
				// , new Fields(F_KID,F_SIGMA)
				, new Fields(0, F_SIGMA)
		// , Fields.ALL
		);
		if (false) {
			pipe12 = new Each(pipe12, new Debug(true));
		}

		// 学生毎に偏差値を求める
		Pipe pipe13 = new CoGroup("pipeScore", Pipe
				.pipes(pipe1, pipe11, pipe12), Fields.fields(new Fields(F_KID),
				new Fields(F_KID), new Fields(0)), new Fields(F_SID, F_KID,
				F_TEN, F_KID + ".2", F_AVE, F_KID + ".3", F_SIGMA),
				new LeftJoin());
		if (true) {
//			pipe13 = new Each(pipe13, Fields.GROUP, new Debug(true));
			pipe13 = new Each(pipe13, Fields.VALUES, new Debug(true));
		}
		pipe13 = new Each(pipe13, new Fields(F_TEN, F_AVE, F_SIGMA),
				new StandardScore(new Fields(F_SCORE)), Fields.ALL);

		// 学生名
		Pipe pipe2 = new Pipe("pipe2", pipe);
		pipe2 = new Each(pipe2, new NameFunction(new Fields(F_SID, F_NAME)));

		// 学生名と成績を結合
		Pipe pipe3 = new CoGroup("pipeResult", pipe13, new Fields(F_SID),
				pipe2, new Fields(F_SID), new Fields(F_SID, F_KID, F_TEN, F_KID
						+ ".2", F_AVE, F_KID + ".3", F_SIGMA, F_SCORE, F_SID
						+ ".2", F_NAME));
		// 必要項目を抽出・並べ替え・カンマ区切り
		pipe3 = new Each(pipe3, new Fields(F_SID, F_NAME, F_KID, F_TEN, F_AVE,
				F_SCORE), new FieldJoiner(", "));
		pipe3 = new GroupBy(pipe3, Fields.ALL);

		FlowConnector flowConnector = new FlowConnector(getProperties());
		Flow flow = flowConnector.connect("student", source, sink, pipe3);
		flow.complete();

		return 0;
	}

	public static void main(String[] args) throws Exception {
		int r = ToolRunner.run(new Student0(), args);
		System.exit(r);
	}
}
