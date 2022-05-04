package jp.hishidama.hadoop.cascading.wordcount;

import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

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

public class WordCountCT extends Configured implements Tool {

	public static final String F_LINE = "line";
	public static final String F_WORD = "word";
	public static final String F_COUNT = "count";

	public static class SplitFunction extends
			BaseOperation<SplitFunction.Context> implements
			Function<SplitFunction.Context> {
		private static final long serialVersionUID = 1L;

		protected static class Context {
			// Context�́A�������Ƀ��\�b�h���܂������ăf�[�^��ێ�����Bean�B
			// Function�ł͕s�v�����A
			// �W�v���s��Aggregator���ƁAstart()�ŏ���������aggregate()�ŉ��Z���Acomplete()�Ŏg�p����A�Ƃ������g����������
		}

		public SplitFunction() {
			super(new Fields(F_WORD)); // �o�͂���t�B�[���h�����w��
		}

		@Override
		public void operate(FlowProcess flowProcess,
				FunctionCall<SplitFunction.Context> functionCall) {
			TupleEntryCollector collector = functionCall.getOutputCollector();

			if (false) {
				Context ctx = functionCall.getContext();
				System.out.println("SplitFunction#context: " + ctx);
			}

			// Tuple tuple = new Tuple(new Fields(F_WORD));

			String line = functionCall.getArguments().getString(F_LINE);
			StringTokenizer tokenizer = new StringTokenizer(line);
			while (tokenizer.hasMoreTokens()) {
				String word = tokenizer.nextToken();
				if (true) {
					collector.add(new Tuple(word));
				} else {
					// tuple�͕s�σI�u�W�F�N�g�炵��
					// tuple.set(0, word);
					// collector.add(tuple);
				}
			}
		}
	}

	@Override
	public int run(String[] args) throws Exception {
		Tap source = new Hfs(new TextLine(new Fields(F_LINE)),
				new Path(args[0]).toUri().toString());
		Tap sink = new Hfs(new TextLine(),
				new Path(args[1]).toUri().toString(), SinkMode.KEEP);

		Pipe pipe = new Pipe("wordcount");
		pipe = new Each(pipe, new SplitFunction());

		// F_WORD���ɃJ�E���g���Č�����F_COUNT�ɓ����
		pipe = new GroupBy(pipe, new Fields(F_WORD)); // Every���g���O��GroupBy���Ă����K�v������
		pipe = new Every(pipe, new Count(new Fields(F_COUNT)));

		FlowConnector flowConnector = new FlowConnector();
		Flow flow = flowConnector.connect("wordcount-flow", source, sink, pipe);
		flow.complete();

		return 0;
	}

	public static void main(String[] args) throws Exception {
		int r = ToolRunner.run(new WordCountCT(), args);
		System.exit(r);
	}
}
