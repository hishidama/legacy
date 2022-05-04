package jp.hishidama.hadoop.cascading.sample;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import jp.hishidama.hadoop.cascading.conf.CascadingConfigured;

import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import cascading.flow.Flow;
import cascading.flow.FlowConnector;
import cascading.flow.FlowProcess;
import cascading.operation.BaseOperation;
import cascading.operation.Buffer;
import cascading.operation.BufferCall;
import cascading.operation.OperationCall;
import cascading.operation.regex.RegexSplitter;
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

public class BufferSample extends CascadingConfigured implements Tool {

	protected static class TimeDeltaBuffer1 extends BaseOperation<Object>
			implements Buffer<Object> {
		private static final long serialVersionUID = 1L;

		protected static final ThreadLocal<DateFormat> dflocal = new ThreadLocal<DateFormat>() {
			@Override
			protected DateFormat initialValue() {
				return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
			}
		};

		public TimeDeltaBuffer1(Fields fieldDeclaration) {
			super(1, fieldDeclaration);
		}

		@Override
		public void operate(FlowProcess flowProcess,
				BufferCall<Object> bufferCall) {
			TupleEntryCollector collector = bufferCall.getOutputCollector();

			Tuple group = bufferCall.getGroup().getTuple();

			Iterator<TupleEntry> i = bufferCall.getArgumentsIterator();

			long prev = 0;

			while (i.hasNext()) {
				Tuple value = i.next().getTuple();
				String s = value.getString(0);
				long now = getTimestamp(s);

				if (prev == 0) {
					prev = now;
				}

				long delta = now - prev;

				if (false) {
					Tuple result = Tuple.size(fieldDeclaration.size());
					result.append(group);
					result.add(delta);
					collector.add(result);
				} else {
					collector.add(new Tuple(delta));
				}

				prev = now;
			}
		}

		protected long getTimestamp(String s) {
			try {
				Date date = dflocal.get().parse(s);
				return date.getTime();
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
		}
	}

	protected static class TimeDeltaBuffer extends BaseOperation<DateFormat>
			implements Buffer<DateFormat> {
		private static final long serialVersionUID = 1L;

		public TimeDeltaBuffer(Fields fieldDeclaration) {
			super(1, fieldDeclaration);
		}

		@Override
		public void prepare(FlowProcess flowProcess,
				OperationCall<DateFormat> operationCall) {
			super.prepare(flowProcess, operationCall);

			if (operationCall.getContext() == null) {
				operationCall.setContext(new SimpleDateFormat(
						"yyyy/MM/dd HH:mm:ss.SSS"));
			}
		}

		@Override
		public void operate(FlowProcess flowProcess,
				BufferCall<DateFormat> bufferCall) {
			TupleEntryCollector collector = bufferCall.getOutputCollector();
			DateFormat df = bufferCall.getContext();

			// Tuple group = bufferCall.getGroup().getTuple();

			long prev = 0, now;

			for (Iterator<TupleEntry> i = bufferCall.getArgumentsIterator(); i
					.hasNext(); prev = now) {
				try {
					Tuple value = i.next().getTuple();
					String s = value.getString(0);
					now = df.parse(s).getTime();
				} catch (ParseException e) {
					throw new RuntimeException(e);
				}

				if (prev == 0) {
					prev = now;
				}

				long delta = now - prev;

				collector.add(new Tuple(delta));
			}
		}
	}

	@Override
	public int run(String[] args) throws Exception {
		Tap source = new Hfs(new TextLine(new Fields("line")),
				makeQualifiedPath(args[0]));
		Tap sink = new Hfs(new TextLine(), makeQualifiedPath(args[1]),
				SinkMode.REPLACE);

		Pipe pipe = new Pipe("pipe");
		pipe = new Each(pipe, new RegexSplitter(new Fields("key", "time",
				"other"), ","));

		// keyでグルーピング、timeで2次ソート
		pipe = new GroupBy(pipe, new Fields("key"), new Fields("time"));
		pipe = new Every(pipe, new Fields("time"), new TimeDeltaBuffer(
				new Fields("delta")), Fields.ALL);

		FlowConnector flowConnector = new FlowConnector(super
				.getProperties());
		Flow flow = flowConnector.connect("buffer-sample", source, sink, pipe);
		flow.complete();

		return 0;
	}

	public static void main(String[] args) throws Exception {
		int r = ToolRunner.run(new BufferSample(), args);
		System.exit(r);
	}
}
