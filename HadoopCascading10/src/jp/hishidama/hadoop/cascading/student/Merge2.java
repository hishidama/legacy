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
	public static final String F_���w = "suugaku";
	public static final String F_���� = "kokugo";
	public static final String F_���� = "rika";
	public static final String F_�Љ� = "shakai";
	public static final String F_�p�� = "eigo";
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
			super(new Fields(F_ID2, F_���w, F_����, F_����, F_�Љ�, F_�p��, F_TOTAL,
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
					entry.set(F_AID, "1"); // �S������
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
			super(new Fields(F_���w, F_����, F_����, F_�Љ�, F_�p��, F_TOTAL, F_CID,
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
			context.ten[0] += args.getInteger(F_���w);
			context.ten[1] += args.getInteger(F_����);
			context.ten[2] += args.getInteger(F_����);
			context.ten[3] += args.getInteger(F_�Љ�);
			context.ten[4] += args.getInteger(F_�p��);
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
			// �o�͂���t�B�[���h�����w��
			// super(new Fields(F_ID2, F_NAME, F_���w, F_����, F_����, F_�Љ�, F_�p��,
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
		// TextLine�ŏo�͂��鍀�ڂ��w��ł���
		Tap sink;
		if (false) {
			sink = new Hfs(new TextLine(TextLine.DEFAULT_SOURCE_FIELDS,
					new Fields(F_ID2, F_NAME, F_���w, F_����, F_����, F_�Љ�, F_�p��,
							F_TOTAL)), new Path(args[2]).toUri().toString(),
					SinkMode.REPLACE);
		} else {
			sink = new Hfs(new TextLine(),
					new Path(args[2]).toUri().toString(), SinkMode.REPLACE);
		}

		Pipe pipe1 = new Pipe("���Opipe");
		{
			pipe1 = new Each(pipe1, new NameMap());
		}

		Pipe pipe2 = new Pipe("����pipe");
		{
			pipe2 = new Each(pipe2, new TenMap());
		}

		Pipe pipe3 = new Pipe("���vpipe", pipe2);
		{
			// �������ɑS���R�[�h���v�ł��AEvery�̑O��GroupBy�����Ȃ���΂Ȃ�Ȃ��B
			// GroupBy�ł͍Œ�1�̍��ڂ��w�肵�Ȃ��Ƃ����Ȃ��̂ŁA
			// �����ł͑S���R�[�h�œ����l�ƂȂ�F_AID��p�ӂ��Ďg���Ă���B
			pipe3 = new GroupBy(pipe3, new Fields(F_AID));
			pipe3 = new Every(pipe3, new Sum5Aggregator());

			// ���ڂ̕��я���pipe2�̕��я��ƍ��킹��
			pipe3 = new Each(pipe3, new SelectFunction(new TenMap()
					.getFieldDeclaration()));
		}
		// GroupBy�ɂ����2�ȏ�̃p�C�v���}�[�W����Ƃ��́A���ڂ̕��я��E�f�[�^�^�����S�Ɉ�v���Ă��Ȃ���΂Ȃ�Ȃ�
		pipe2 = new GroupBy(pipe2.getName(), new Pipe[] { pipe2, pipe3 },
				new Fields(F_ID2));

		if (false) {
			// CoGroup���g���Ǝ����I�Ƀ\�[�g�����悤�Ȃ̂ŁA���O�ł�GroupBy�͕s�v
			pipe1 = new GroupBy(pipe1, new Fields(F_ID1));
			pipe2 = new GroupBy(pipe2, new Fields(F_ID2));
		}
		Pipe pipe = new CoGroup(pipe2.getName(), pipe2, new Fields(F_ID2),
				pipe1, new Fields(F_ID1), null, new LeftJoin());

		// �_���̍~��
		pipe = new GroupBy(pipe, new Fields(F_TOTAL), true); // true�F�t��

		// �o�͕�����̐��`
		if (true) {
			// sink��TextLine�ɍ��ڂ̍i���݂����Ȃ���΁A����ŃJ���}��؂�ɏo����
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
