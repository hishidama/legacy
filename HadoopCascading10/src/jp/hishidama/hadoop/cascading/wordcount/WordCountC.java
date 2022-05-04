package jp.hishidama.hadoop.cascading.wordcount;

import java.util.StringTokenizer;

import cascading.flow.Flow;
import cascading.flow.FlowConnector;
import cascading.flow.FlowProcess;
import cascading.operation.BaseOperation;
import cascading.operation.Function;
import cascading.operation.FunctionCall;
import cascading.operation.aggregator.Count;
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
import cascading.tuple.TupleEntryCollector;

public class WordCountC {

	static final String F_LINE = "line";
	static final String F_WORD = "word";
	static final String F_COUNT = "count";

	public static class SplitFunction extends BaseOperation<Object> implements
			Function<Object> {
		private static final long serialVersionUID = 1L;

		public SplitFunction() {
			super(new Fields(F_WORD)); // 出力するフィールド名を指定
		}

		@Override
		public void operate(FlowProcess flowProcess,
				FunctionCall<Object> functionCall) {
			TupleEntryCollector collector = functionCall.getOutputCollector();

			{
				Object ctx = functionCall.getContext();
				System.out.println("SplitFunction#context: " + ctx);
			}
			String line = functionCall.getArguments().getString(F_LINE);
			StringTokenizer tokenizer = new StringTokenizer(line);
			while (tokenizer.hasMoreTokens()) {
				String word = tokenizer.nextToken();
				collector.add(new Tuple(word));
			}
		}
	}

	public static void main(String[] args) {
		Tap source = new Hfs(new TextLine(new Fields(F_LINE)), args[0]);
		Tap sink = new Hfs(new TextLine(), args[1], SinkMode.REPLACE);

		Pipe pipe = new Pipe("wordcount");
		pipe = new Each(pipe, new SplitFunction());

		// F_WORD毎にカウントして件数をF_COUNTに入れる
		pipe = new GroupBy(pipe, new Fields(F_WORD)); // Everyを使う前にGroupByしておく必要がある
		pipe = new Every(pipe, new Count(new Fields(F_COUNT)));

		FlowConnector flowConnector = new FlowConnector();
		Flow flow = flowConnector.connect("wordcount-flow", source, sink, pipe);
		flow.complete();

		System.out.println("end");
	}
}
