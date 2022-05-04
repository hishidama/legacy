package jp.hishidama.hadoop.cascading.student;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.hadoop.fs.Path;

import cascading.flow.Flow;
import cascading.flow.FlowConnector;
import cascading.flow.FlowProcess;
import cascading.operation.Aggregator;
import cascading.operation.AggregatorCall;
import cascading.operation.BaseOperation;
import cascading.operation.Function;
import cascading.operation.FunctionCall;
import cascading.operation.text.FieldFormatter;
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

public class Merge2 {
	public static final String F_LINE = "line";
	public static final String F_AID = "all-id";
	public static final String F_CID = "class-id";
	public static final String F_ID1 = "student-id1";
	public static final String F_ID2 = "student-id2";
	public static final String F_NAME = "student-name";
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
			super(new Fields(F_ID1, F_NAME));
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
			super(new Fields(F_ID2, F_数学, F_国語, F_理科, F_社会, F_英語, F_TOTAL,
					F_AID, F_CID));
		}

		@Override
		public void operate(FlowProcess flowProcess,
				FunctionCall<Object> functionCall) {

			String line = functionCall.getArguments().getString(F_LINE);
			if (line.isEmpty() || line.startsWith("#")) {
				return;
			}

			int sum = 0;

			TupleEntry entry = new TupleEntry(fieldDeclaration, Tuple
					.size(fieldDeclaration.size()));

			StringTokenizer tokenizer = new StringTokenizer(line, ",");
			for (int i = 0; tokenizer.hasMoreTokens(); i++) {
				String token = tokenizer.nextToken().trim();
				switch (i) {
				case 0:
					entry.set(0, token);
					entry.set(F_AID, "1"); // 全員共通
					entry.set(F_CID, token.substring(0, 1));
					break;
				default:
					int ten = Integer.parseInt(token);
					sum += ten;
					entry.set(i, ten);
					break;
				}
			}

			TupleEntryCollector collector = functionCall.getOutputCollector();
			entry.set(F_TOTAL, sum);
			collector.add(entry);
		}
	}

	protected static class Sum5Aggregator extends
			BaseOperation<Sum5Aggregator.Context> implements
			Aggregator<Sum5Aggregator.Context> {
		private static final long serialVersionUID = 1L;

		public Sum5Aggregator() {
			super(new Fields(F_数学, F_国語, F_理科, F_社会, F_英語, F_TOTAL, F_CID,
					F_ID2));
		}

		static class Context {
			int[] ten = null;
		}

		@Override
		public void start(FlowProcess flowProcess,
				AggregatorCall<Sum5Aggregator.Context> aggregatorCall) {
			Context context = aggregatorCall.getContext();
			if (context == null) {
				context = new Context();
				aggregatorCall.setContext(context);
				context.ten = new int[fieldDeclaration.size()];
			} else {
				Arrays.fill(context.ten, 0);
			}
		}

		@Override
		public void aggregate(FlowProcess flowProcess,
				AggregatorCall<Sum5Aggregator.Context> aggregatorCall) {
			Context context = aggregatorCall.getContext();
			TupleEntry args = aggregatorCall.getArguments();
			context.ten[0] += args.getInteger(F_数学);
			context.ten[1] += args.getInteger(F_国語);
			context.ten[2] += args.getInteger(F_理科);
			context.ten[3] += args.getInteger(F_社会);
			context.ten[4] += args.getInteger(F_英語);
			context.ten[5] += args.getInteger(F_TOTAL);
		}

		@Override
		public void complete(FlowProcess flowProcess,
				AggregatorCall<Sum5Aggregator.Context> aggregatorCall) {
			Context context = aggregatorCall.getContext();

			TupleEntry entry = new TupleEntry(fieldDeclaration, Tuple
					.size(fieldDeclaration.size()));
			for (int i = 0; i < fieldDeclaration.size(); i++) {
				entry.set(i, context.ten[i]);
			}
			entry.set(F_CID, "9");
			entry.set(F_ID2, "999");
			System.out.println("Sum5#complete(): " + entry);

			TupleEntryCollector collector = aggregatorCall.getOutputCollector();
			collector.add(entry);
		}
	}

	public static class SelectFunction extends BaseOperation<Object> implements
			Function<Object> {
		private static final long serialVersionUID = 1L;

		public SelectFunction(Fields fields) {
			super(fields);
		}

		@Override
		public void operate(FlowProcess flowProcess,
				FunctionCall<Object> functionCall) {

			TupleEntry entry = functionCall.getArguments();
			Tuple tuple = entry.selectTuple(fieldDeclaration);
			functionCall.getOutputCollector().add(tuple);
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
					new Fields(F_ID2, F_NAME, F_数学, F_国語, F_理科, F_社会, F_英語,
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
			// 無条件に全レコード合計でも、Everyの前にGroupByをしなければならない。
			// GroupByでは最低1つの項目を指定しないといけないので、
			// ここでは全レコードで同じ値となるF_AIDを用意して使っている。
			pipe3 = new GroupBy(pipe3, new Fields(F_AID));
			pipe3 = new Every(pipe3, new Sum5Aggregator());

			// 項目の並び順をpipe2の並び順と合わせる
			pipe3 = new Each(pipe3, new SelectFunction(new TenMap()
					.getFieldDeclaration()));
		}
		// GroupByによって2つ以上のパイプをマージするときは、項目の並び順・データ型が完全に一致していなければならない
		pipe2 = new GroupBy(pipe2.getName(), new Pipe[] { pipe2, pipe3 },
				new Fields(F_ID2));

		if (false) {
			// CoGroupを使うと自動的にソートされるようなので、自前でのGroupByは不要
			pipe1 = new GroupBy(pipe1, new Fields(F_ID1));
			pipe2 = new GroupBy(pipe2, new Fields(F_ID2));
		}
		Pipe pipe = new CoGroup(pipe2.getName(), pipe2, new Fields(F_ID2),
				pipe1, new Fields(F_ID1), null, new LeftJoin());

		// 点数の降順
		pipe = new GroupBy(pipe, new Fields(F_TOTAL), true); // true：逆順

		// 出力文字列の整形
		if (true) {
			// sinkのTextLineに項目の絞込みを入れなければ、これでカンマ区切りに出来る
			pipe = new Each(pipe, new CommaFormatFunction());
		} else {
			pipe = new Each(pipe, new FieldFormatter(
					"%s,%s, %5s,%5s,%5s,%5s,%5s,%5s"));
		}

		Map<String, Tap> sources = new HashMap<String, Tap>();
		sources.put(pipe1.getName(), source1);
		sources.put(pipe2.getName(), source2);

		FlowConnector flowConnector = new FlowConnector();
		Flow flow = flowConnector.connect("merge-flow", sources, sink, pipe);
		flow.complete();
	}
}
