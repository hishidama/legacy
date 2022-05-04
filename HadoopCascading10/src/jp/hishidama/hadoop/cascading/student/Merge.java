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
import cascading.pipe.CoGroup;
import cascading.pipe.Each;
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

public class Merge {
	public static final String F_LINE = "line";
	public static final String F_ID = "student-id";
	public static final String F_NAME = "student-name";
	public static final String F_TOTAL = "total5";

	protected static class NameMap extends BaseOperation<Object> implements
			Function<Object> {
		private static final long serialVersionUID = 1L;

		public NameMap() {
			super(new Fields(F_ID, F_NAME));
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
				String token = tokenizer.nextToken();
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

	protected static class Total5Map extends BaseOperation<Object> implements
			Function<Object> {
		private static final long serialVersionUID = 1L;

		public Total5Map() {
			super(new Fields(F_ID, F_TOTAL));
		}

		@Override
		public void operate(FlowProcess flowProcess,
				FunctionCall<Object> functionCall) {

			String line = functionCall.getArguments().getString(F_LINE);
			if (line.isEmpty() || line.startsWith("#")) {
				return;
			}

			String id = null;
			int ten = 0;

			StringTokenizer tokenizer = new StringTokenizer(line, ",");
			for (int i = 0; tokenizer.hasMoreTokens(); i++) {
				String token = tokenizer.nextToken();
				switch (i) {
				case 0:
					id = token;
					break;
				default:
					ten += Integer.parseInt(token.trim());
					break;
				}
			}

			if (id != null) {
				TupleEntryCollector collector = functionCall
						.getOutputCollector();
				collector.add(new Tuple(id, ten));
			}
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

	public static void main(String[] args) {
		Tap source1 = new Hfs(new TextLine(new Fields(F_LINE)), new Path(
				args[0]).toUri().toString());
		Tap source2 = new Hfs(new TextLine(new Fields(F_LINE)), new Path(
				args[1]).toUri().toString());
		// TextLineで出力する項目を指定できる
		Tap sink = new Hfs(new TextLine(TextLine.DEFAULT_SOURCE_FIELDS,
				new Fields(F_ID, F_NAME, F_TOTAL)), new Path(args[2]).toUri()
				.toString(), SinkMode.REPLACE);

		Pipe pipe1 = new Pipe("名前pipe");
		{
			pipe1 = new Each(pipe1, new NameMap());
		}

		Pipe pipe2 = new Pipe("成績pipe");
		{
			pipe2 = new Each(pipe2, new Total5Map());
		}

		if (false) {
			// CoGroupを使うと自動的にソートされるようなので、自前でのGroupByは不要
			pipe1 = new GroupBy(pipe1, new Fields(F_ID));
			pipe2 = new GroupBy(pipe2, new Fields(F_ID));
		}
		Pipe pipe = new CoGroup("merge-id", pipe1, new Fields(F_ID), pipe2,
				new Fields(F_ID), new Fields(F_ID, F_NAME, "id2", F_TOTAL),
				new LeftJoin());

		// 項目の絞込み
		if (false) {
			pipe = new Each(pipe, new SelectFunction(new Fields(F_ID, F_NAME,
					F_TOTAL)));
		} else if (false) {
			pipe = new Each(pipe, new NOPFunction(), new Fields(F_ID, F_NAME,
					F_TOTAL));
		}

		// 点数の降順
		pipe = new GroupBy(pipe, new Fields(F_TOTAL), true); // true：逆順

		Map<String, Tap> sources = new HashMap<String, Tap>();
		sources.put(pipe1.getName(), source1);
		sources.put(pipe2.getName(), source2);

		FlowConnector flowConnector = new FlowConnector();
		Flow flow = flowConnector.connect("merge-flow", sources, sink, pipe);
		flow.complete();
	}
}
