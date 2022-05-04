package jp.hishidama.hadoop.cascading.wordcount;

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
import cascading.operation.aggregator.Count;
import cascading.operation.regex.RegexSplitGenerator;
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

public class WordCount {

	// フィールド名の定義
	public static final String F_LINE = "line";
	public static final String F_WORD = "word";
	public static final String F_COUNT = "count";

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

			TupleEntry args = functionCall.getArguments();
			String line = args.getString(F_LINE);
			StringTokenizer tokenizer = new StringTokenizer(line);
			while (tokenizer.hasMoreTokens()) {
				String word = tokenizer.nextToken();
				collector.add(new Tuple(word));
			}
		}
	}

	public static class SumAggregator extends
			BaseOperation<SumAggregator.Context> implements
			Aggregator<SumAggregator.Context> {
		private static final long serialVersionUID = 1L;

		public SumAggregator() {
			super(new Fields(F_COUNT));
		}

		static class Context {
			int count;
			int sum;
		}

		@Override
		public void start(FlowProcess flowProcess,
				AggregatorCall<Context> aggregatorCall) {
			Context context = aggregatorCall.getContext();
			System.out.println("SumAggregator#start: "
					+ aggregatorCall.getArguments());
			if (context == null) {
				context = new Context();
				aggregatorCall.setContext(context);
			}
			context.count = 0;
			context.sum = 0;
		}

		@Override
		public void aggregate(FlowProcess flowProcess,
				AggregatorCall<Context> aggregatorCall) {
			TupleEntry entry = aggregatorCall.getArguments(); // 入力データ

			Context context = aggregatorCall.getContext();
			context.count++;
			context.sum += entry.getInteger("data");
		}

		@Override
		public void complete(FlowProcess flowProcess,
				AggregatorCall<Context> aggregatorCall) {
			System.out.println("SumAggregator#complete: "
					+ aggregatorCall.getArguments());

			TupleEntryCollector collector = aggregatorCall.getOutputCollector();

			Context context = aggregatorCall.getContext();
			collector.add(new Tuple(context.count));
		}
	}

	public static void main(String[] args) throws Exception {
		Tap source = new Hfs(new TextLine(new Fields(F_LINE)),
				new Path(args[0]).toUri().toString());
		Tap sink = new Hfs(new TextLine(),
				new Path(args[1]).toUri().toString(), SinkMode.REPLACE);

		// Pipe pipe = new Pipe("wordcount");
		Pipe pipe;
		if (false) {
			// pipe = new Each(pipe, new SplitFunction());
			pipe = new Each("wordcount-pipe", new SplitFunction());
		} else {
			// pipe = new Each(pipe, new RegexSplitGenerator(new Fields(F_WORD),
			// "[ \t\n\r\f]+"));
			pipe = new Each("PIPE!", new RegexSplitGenerator(
					new Fields(F_WORD), "[ \t\n\r\f]+"));
		}

		// F_WORD毎にカウントして件数をF_COUNTに入れる
		pipe = new GroupBy(pipe, new Fields(F_WORD)); // Everyを使う前にGroupByしておく必要がある
		if (false) {
			pipe = new Every(pipe, new SumAggregator());
		} else {
			pipe = new Every(pipe, new Count(new Fields(F_COUNT)));
		}
		FlowConnector flowConnector = new FlowConnector();
		Flow flow = flowConnector.connect("wordcount", source, sink, pipe);
		flow.complete();
	}
}
