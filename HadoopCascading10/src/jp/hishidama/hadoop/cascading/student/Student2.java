package jp.hishidama.hadoop.cascading.student;

import java.util.StringTokenizer;

import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import cascading.flow.Flow;
import cascading.flow.FlowConnector;
import cascading.flow.FlowProcess;
import cascading.operation.BaseOperation;
import cascading.operation.Debug;
import cascading.operation.Function;
import cascading.operation.FunctionCall;
import cascading.operation.text.FieldJoiner;
import cascading.pipe.CoGroup;
import cascading.pipe.Each;
import cascading.pipe.GroupBy;
import cascading.pipe.Pipe;
import cascading.scheme.TextLine;
import cascading.tap.Hfs;
import cascading.tap.SinkMode;
import cascading.tap.Tap;
import cascading.tuple.Fields;
import cascading.tuple.Tuple;
import cascading.tuple.TupleEntryCollector;

import jp.hishidama.hadoop.cascading.conf.CascadingConfigured;
import jp.hishidama.hadoop.cascading.pipe.assembly.StandardScore;

//偏差値を求めるアセンブリーを使用
public class Student2 extends CascadingConfigured implements Tool {

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

		// 偏差値を算出
		pipe1 = new StandardScore(pipe1, new Fields(F_KID), new Fields(F_TEN),
				new Fields(F_AVE, F_SIGMA, F_SCORE),
				// Fields.ALL
				new Fields(F_SID, F_KID, F_TEN, F_AVE, F_SCORE));
		if (false) {
			pipe1 = new Each(pipe1, new Debug(true));
		}

		// 学生名
		Pipe pipe2 = new Pipe("pipe2", pipe);
		pipe2 = new Each(pipe2, new NameFunction(new Fields(F_SID, F_NAME)));

		// 学生名と成績を結合
		Pipe pipe3 = new CoGroup("pipeResult", pipe1, new Fields(F_SID), pipe2,
				new Fields(F_SID),
				// new Fields(F_SID, F_KID, F_TEN,
				// F_KID + ".2", F_AVE, F_KID + ".3", F_SIGMA, F_SCORE,
				// F_SID + ".2", F_NAME)
				new Fields(F_SID, F_KID, F_TEN, F_AVE, F_SCORE, F_SID + ".2",
						F_NAME));
		// 必要項目を抽出・並べ替え・カンマ区切り
		pipe3 = new Each(pipe3, new Fields(F_SID, F_NAME, F_KID, F_TEN, F_AVE,
				F_SCORE), new FieldJoiner(", "));
		pipe3 = new GroupBy(pipe3, Fields.ALL);

		FlowConnector flowConnector = new FlowConnector(getProperties());
		Flow flow = flowConnector.connect("student", source, sink, pipe3);
		flow.writeDOT("C:/temp/graphviz/student2.dot");
//		flow.complete();

		return 0;
	}

	public static void main(String[] args) throws Exception {
		int r = ToolRunner.run(new Student2(), args);
		System.exit(r);
	}
}
