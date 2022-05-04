package jp.hishidama.hadoop.cascading.student;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.hadoop.fs.Path;

import cascading.flow.Flow;
import cascading.flow.FlowConnector;
import cascading.flow.FlowProcess;
import cascading.operation.BaseOperation;
import cascading.operation.Function;
import cascading.operation.FunctionCall;
import cascading.operation.Identity;
import cascading.operation.aggregator.Count;
import cascading.operation.aggregator.Sum;
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
import cascading.tuple.TupleEntry;
import cascading.tuple.TupleEntryCollector;

public class Merge3 {
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

			if (false) {
				Object val = flowProcess.getProperty("fs.default.name");
				System.out.println("fs.default.name+++" + val);
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
//			System.out.println("+++" + t.isUnmodifiable());
			collector.add(t);
//			System.out.println("+++" + t.isUnmodifiable());
		}
	}

	public static class NOPFunction extends BaseOperation<Object> implements
			Function<Object> {
		private static final long serialVersionUID = 1L;

		@Override
		public void operate(FlowProcess flowProcess,
				FunctionCall<Object> functionCall) {
			TupleEntry entry = functionCall.getArguments();
			functionCall.getOutputCollector().add(entry);
		}
	}

	static class CommaFormatFunction extends BaseOperation<Object> implements
			Function<Object> {
		private static final long serialVersionUID = 1L;

		public CommaFormatFunction() {
			// 出力するフィールド名を指定
			// super(new Fields(F_ID2, F_NAME, F_数学, F_国語, F_理科, F_社会, F_英語,
			// F_TOTAL));
			super(Fields.ALL);
		}

		@Override
		public void operate(FlowProcess flowProcess,
				FunctionCall<Object> functionCall) {
			TupleEntryCollector collector = functionCall.getOutputCollector();

			TupleEntry args = functionCall.getArguments();
			Tuple tuple = new Tuple(args.getTuple()) {
				private static final long serialVersionUID = 1L;

				@Override
				public String toString() {
					return super.toString(", ");
				}
			};
			collector.add(tuple);
		}
	}

	public static void main(String[] args) {
		Tap source1 = new Hfs(new TextLine(new Fields(F_LINE)), new Path(
				args[0]).toUri().toString());
		Tap source2 = new Hfs(new TextLine(new Fields(F_LINE)), new Path(
				args[1]).toUri().toString());
		// TextLineで出力する項目を指定できる
		Tap sink;
		if (false) {
			sink = new Hfs(new TextLine(TextLine.DEFAULT_SOURCE_FIELDS,
					new Fields(F_SID, F_NAME, F_数学, F_国語, F_理科, F_社会, F_英語,
							F_TOTAL)), new Path(args[2]).toUri().toString(),
					SinkMode.REPLACE);
		} else {
			sink = new Hfs(new TextLine(),
					new Path(args[2]).toUri().toString(), SinkMode.REPLACE);
		}

		Pipe pipe1 = new Pipe("名前pipe");
		{
			pipe1 = new Each(pipe1, new NameMap());
		}

		Pipe pipe2 = new Pipe("成績pipe");
		{
			pipe2 = new Each(pipe2, new TenMap());
		}

		Pipe pipe3 = new Pipe("合計pipe", pipe2);
		{
			pipe3 = new GroupBy(pipe3, new Fields(F_KID));
			pipe3 = new Every(pipe3, new Fields(F_TEN), new Sum());
		}
		Pipe pipe4 = new Pipe("人数pipe", pipe2);
		{
			pipe4 = new GroupBy(pipe4, new Fields(F_KID));
			pipe4 = new Every(pipe4, new Count());
		}

		Pipe pipe = new CoGroup(pipe2.getName(), new Pipe[] { pipe2, pipe3,
				pipe4 }, new Fields[] { new Fields(F_KID), new Fields(F_KID),
				new Fields(F_KID) }, new Fields(F_SID, F_KID, F_TEN, "k3",
				F_TOTAL, "k4", "count"));

		pipe = new CoGroup(pipe.getName(), pipe, new Fields(F_SID), pipe1,
				new Fields(F_SID), new Fields(F_SID, F_KID, F_TEN, "k3",
						F_TOTAL, "k4", "count", "s1", F_NAME), new LeftJoin());

		// 項目の絞り込み
		if (false) {
			pipe = new Each(pipe, new Fields(F_SID, F_NAME, F_KID, F_TEN,
					F_TOTAL, "count"), new Identity());
		} else {
			pipe = new Each(pipe, new Fields(0, 8, 1, 2, 4, 6), new Identity());
			// pipe = new Each(pipe, new Fields(0, 8, -1,7,
			// -2,6,-3,5,4,-4,3,-5,2,-6,1,-7), new Identity());
			// pipe = new Each(pipe, new Identity(), new Fields(0, 8, 1, -2,
			// -3));
		}

		// ソート
		pipe = new GroupBy(pipe, new Fields(F_SID, F_KID));

		Map<String, Tap> sources = new HashMap<String, Tap>();
		sources.put(pipe1.getName(), source1);
		sources.put(pipe2.getName(), source2);

		FlowConnector flowConnector = new FlowConnector();
		Flow flow = flowConnector.connect("merge-flow", sources, sink, pipe);
		flow.complete();
	}
}
