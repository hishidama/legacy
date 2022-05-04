package jp.hishidama.hadoop.cascading.student;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import jp.hishidama.hadoop.cascading.pipe.CoGroupEx;
import jp.hishidama.hadoop.cascading.student.Merge4.StandardDeviation.Context;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import cascading.flow.Flow;
import cascading.flow.FlowConnector;
import cascading.flow.FlowProcess;
import cascading.flow.MultiMapReducePlanner;
import cascading.operation.Aggregator;
import cascading.operation.AggregatorCall;
import cascading.operation.BaseOperation;
import cascading.operation.Function;
import cascading.operation.FunctionCall;
import cascading.operation.aggregator.Average;
import cascading.operation.expression.ExpressionFunction;
import cascading.operation.regex.RegexSplitter;
import cascading.operation.text.FieldJoiner;
import cascading.pipe.Each;
import cascading.pipe.Every;
import cascading.pipe.GroupBy;
import cascading.pipe.Pipe;
import cascading.scheme.TextLine;
import cascading.tap.Hfs;
import cascading.tap.SinkMode;
import cascading.tap.Tap;
import cascading.tuple.Fields;
import cascading.tuple.Tuple;
import cascading.tuple.TupleEntry;
import cascading.tuple.TupleEntryCollector;

@SuppressWarnings("deprecation")
public class Merge4 extends Configured implements Tool {
	public static final String F_LINE = "line";
	public static final String F_KID = "kamoku-id";
	public static final String F_SID = "student-id";
	public static final String F_NAME = "student-name";
	public static final String F_TEN = "ten";
	public static final String F_数学 = "suugaku";
	public static final String F_国語 = "kokugo";
	public static final String F_理科 = "rika";
	public static final String F_社会 = "shakai";
	public static final String F_英語 = "eigo";
	public static final String F_TOTAL = "total5";
	public static final String F_平均 = "average";
	public static final String F_偏差 = "deviation";
	public static final String F_偏差値 = "score";

	protected static class NameMap extends BaseOperation<Object> implements
			Function<Object> {
		private static final long serialVersionUID = 1L;

		public NameMap() {
			super(new Fields(F_SID, F_NAME));
		}

		@Override
		public void operate(FlowProcess flowProcess,
				FunctionCall<Object> functionCall) {

			String line = functionCall.getArguments().getString(F_LINE);
			if (line.isEmpty() || line.startsWith("#")) {
				return;
			}
			TupleEntryCollector collector = functionCall.getOutputCollector();

			String id = null;
			String name = null;

			StringTokenizer tokenizer = new StringTokenizer(line, ",");
			loop: for (int i = 0; tokenizer.hasMoreTokens(); i++) {
				String token = tokenizer.nextToken().trim();
				switch (i) {
				case 0:
					id = token;
					break;
				case 1:
					name = token;
					collector.add(new Tuple(id, name));
					break loop;
				}
			}
		}
	}

	protected static class TenMap extends BaseOperation<Object> implements
			Function<Object> {
		private static final long serialVersionUID = 1L;

		public TenMap() {
			super(new Fields(F_SID, F_KID, F_TEN));
		}

		@Override
		public void operate(FlowProcess flowProcess,
				FunctionCall<Object> functionCall) {

			TupleEntry entry = functionCall.getArguments();
			String line = entry.getString(F_LINE);
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
				case 0:
					sid = token;
					break;
				default:
					int ten = Integer.parseInt(token);
					Tuple t = Tuple.size(fieldDeclaration.size());
					t.set(0, sid);
					t.set(1, Integer.toString(i));
					t.set(2, ten);
					collector.add(t);

					sum += ten;
					break;
				}
			}

			Tuple t = Tuple.size(fieldDeclaration.size());
			t.set(0, sid);
			t.set(1, "6");
			t.set(2, sum);
			// System.out.println("+++" + t.isUnmodifiable());
			collector.add(t);
			// System.out.println("+++" + t.isUnmodifiable());
		}
	}

	/** 標準偏差を求める集約関数 */
	protected static class StandardDeviation extends
			BaseOperation<StandardDeviation.Context> implements
			Aggregator<StandardDeviation.Context> {
		private static final long serialVersionUID = 1L;

		class Context {
			double sum;
			int count;

			void reset() {
				sum = 0;
				count = 0;
			}
		}

		public StandardDeviation(Fields fields) {
			super(2, fields);
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

			if (true) {
				TupleEntry group = aggregatorCall.getGroup();
				System.out.println("StandardDeviation#group+++" + group);
			}
		}

		@Override
		public void aggregate(FlowProcess flowProcess,
				AggregatorCall<Context> aggregatorCall) {
			Context ctx = aggregatorCall.getContext();
			Tuple input = aggregatorCall.getArguments().getTuple();
			double value = input.getDouble(0);
			double average = input.getDouble(1);
			ctx.sum += Math.pow(value - average, 2);
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

	/** 偏差値を求める関数 */
	protected static class StandardScore extends
			BaseOperation<StandardDeviation.Context> implements
			Function<StandardDeviation.Context> {
		private static final long serialVersionUID = 1L;

		public StandardScore(Fields fields) {
			super(3, fields);
			// args0：点数
			// args1：平均点
			// args2：標準偏差
		}

		@Override
		public void operate(FlowProcess flowProcess,
				FunctionCall<Context> functionCall) {
			Tuple input = functionCall.getArguments().getTuple();
			double ten = input.getInteger(0);
			double average = input.getDouble(1);
			double sigma = input.getDouble(2);
			double score = (ten - average) * 10 / sigma + 50;
			functionCall.getOutputCollector().add(new Tuple(score));
		}
	}

	protected static class 科目名 extends BaseOperation<Object> implements
			Function<Object> {
		private static final long serialVersionUID = 1L;

		protected static final String[] knames = { "数学", "国語", "理科", "社会",
				"英語", "合計" };

		protected int pos = -1;

		public 科目名() {
			super(Fields.ARGS);
		}

		@Override
		public void operate(FlowProcess flowProcess,
				FunctionCall<Object> functionCall) {
			TupleEntry entry = functionCall.getArguments();
			if (pos < 0) {
				pos = entry.getFields().getPos(F_KID);
				System.out.println("科目名#pos+++" + pos);
			}

			Tuple tuple = entry.getTuple();
			int kid = tuple.getInteger(pos);
			String kname;
			if (kid >= 1 && kid <= knames.length) {
				kname = knames[kid - 1];
			} else {
				kname = Integer.toString(kid);
			}

			tuple = new Tuple(tuple);
			tuple.set(pos, kname);
			if (true) {
				functionCall.getOutputCollector().add(tuple);
			} else {
				// entry = new TupleEntry(entry.getFields(), tuple);
				entry.setTuple(tuple);
				System.out.println("entry+++" + entry);

				functionCall.getOutputCollector().add(entry);
			}
		}
	}

	@Override
	public int run(String[] args) throws Exception {
		// Tap source1 = new Hfs(new TextLine(new Fields(F_LINE)), new Path(
		// args[0]).toUri().toString());
		// Tap source2 = new Hfs(new TextLine(new Fields(F_LINE)), new Path(
		// args[1]).toUri().toString());
		// Tap sink = new Hfs(new TextLine(),
		// new Path(args[2]).toUri().toString(), SinkMode.REPLACE);
		Tap source1 = new Hfs(new TextLine(new Fields(F_LINE)), args[0]);
		Tap source2 = new Hfs(new TextLine(new Fields(F_LINE)), args[1]);
		Tap sink = new Hfs(new TextLine(), args[2], SinkMode.REPLACE);

		Pipe pipe1 = new Pipe("名前pipe");
		if (true) {
			pipe1 = new Each(pipe1, new NameMap());
		} else {
			pipe1 = new Each(pipe1, new RegexSplitter(
					new Fields(F_SID, F_NAME), "[ \t]*,[ \t]*"));
			// ↑trim()してくれない
			Pipe pipe11 = new Each(pipe1, new Fields(F_SID),
					new ExpressionFunction(new Fields(F_SID),
							F_SID + ".trim()", String.class));
		}

		Pipe pipe2 = new Pipe("成績pipe");
		pipe2 = new Each(pipe2, new TenMap()
		/* ,new Fields(F_SID, F_KID, F_TEN) */);

		// 平均を求める
		Pipe pipe3 = new GroupBy("平均pipe", pipe2, new Fields(F_KID));
		pipe3 = new Every(pipe3, new Fields(F_TEN), new Average(
				new Fields(F_平均)));

		// 各学生の点数と平均を結合
		pipe2 = new CoGroupEx("点数+平均pipe", pipe2, new Fields(F_KID), pipe3,
				new Fields(F_KID));

		// 標準偏差を求める
		Pipe pipe4 = new GroupBy("標準偏差pipe", pipe2, new Fields(F_KID));
		// Pipe pipe4=new Pipe("標準偏差pipe",pipe2);
		pipe4 = new Every(pipe4, new Fields(F_TEN, F_平均),
				new StandardDeviation(new Fields(F_偏差)),
				new Fields(F_KID, F_偏差));

		// 偏差値を求める
		Pipe pipe5 = new CoGroupEx("偏差値pipe", pipe2, new Fields(F_KID), pipe4,
				new Fields(F_KID));
		Pipe pipe;
		if (false) {
			pipe5 = new Each(pipe5, new Fields(F_TEN, F_平均, F_偏差),
					new StandardScore(new Fields(F_偏差値)), new Fields(F_SID,
							F_KID, F_偏差値));

			pipe = new CoGroupEx(pipe2.getName(), pipe2, new Fields(F_SID,
					F_KID), pipe5, new Fields(F_SID, F_KID));
		} else {
			pipe = new Each(pipe5, new Fields(F_TEN, F_平均, F_偏差),
					new StandardScore(new Fields(F_偏差値)),
					// new Fields(F_SID, F_KID, F_TEN, F_平均, F_偏差値)
					Fields.ALL);
		}
		// 学生名を結合
		pipe = new CoGroupEx("pipe", pipe, new Fields(F_SID), pipe1,
				new Fields(F_SID));

		// 科目IDを科目名に変換
		pipe = new Each(pipe, new 科目名());

		// 必要な出力項目の絞り込み・並び替え および カンマ区切り化
		pipe = new Each(pipe, new Fields(F_SID, F_NAME, F_KID, F_TEN, F_平均,
				F_偏差値), new FieldJoiner(", "));

		Map<String, Tap> sources = new HashMap<String, Tap>();
		sources.put(pipe1.getName(), source1);
		sources.put(pipe2.getName(), source2);

		Map<Object, Object> properties = new HashMap<Object, Object>();
		MultiMapReducePlanner.setJobConf(properties, new JobConf(getConf()));
		FlowConnector.setApplicationJarClass(properties, getClass());
		FlowConnector flowConnector = new FlowConnector(properties);
		Flow flow = flowConnector.connect("merge-flow", sources, sink, pipe);
		flow.complete();

		return 0;
	}

	public static void main(String[] args) throws Exception {
		int r = ToolRunner.run(new Merge4(), args);
		System.exit(r);
	}
}
