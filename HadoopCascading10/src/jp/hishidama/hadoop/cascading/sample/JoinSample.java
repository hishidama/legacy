package jp.hishidama.hadoop.cascading.sample;

import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.fs.Path;

import cascading.flow.Flow;
import cascading.flow.FlowConnector;
import cascading.flow.FlowProcess;
import cascading.operation.Aggregator;
import cascading.operation.AggregatorCall;
import cascading.operation.BaseOperation;
import cascading.operation.regex.RegexSplitter;
import cascading.pipe.CoGroup;
import cascading.pipe.Each;
import cascading.pipe.Every;
import cascading.pipe.Pipe;
import cascading.pipe.cogroup.InnerJoin;
import cascading.pipe.cogroup.Joiner;
import cascading.pipe.cogroup.LeftJoin;
import cascading.pipe.cogroup.OuterJoin;
import cascading.pipe.cogroup.RightJoin;
import cascading.scheme.TextLine;
import cascading.tap.Hfs;
import cascading.tap.SinkMode;
import cascading.tap.Tap;
import cascading.tuple.Fields;

public class JoinSample {

	public static final String F_ID1 = "id1";
	public static final String F_WD1 = "word1";
	public static final String F_ID2 = "id2";
	public static final String F_WD2 = "word2";

	static class DebugAggregator extends BaseOperation<Object> implements
			Aggregator<Object> {
		private static final long serialVersionUID = 1L;

		@Override
		public void start(FlowProcess flowProcess,
				AggregatorCall<Object> aggregatorCall) {
			if (false) {
				System.err.println(aggregatorCall.getArguments());
				System.out.println(aggregatorCall.getGroup());
			}
		}

		@Override
		public void aggregate(FlowProcess flowProcess,
				AggregatorCall<Object> aggregatorCall) {
			if (false) {
				System.err.println(aggregatorCall.getGroup().getFields());
			}
			System.out.println("aggregate+++" + aggregatorCall.getGroup()
					+ " / " + aggregatorCall.getArguments());
		}

		@Override
		public void complete(FlowProcess flowProcess,
				AggregatorCall<Object> aggregatorCall) {
			if (false) {
				System.err.println(aggregatorCall.getGroup().getFields());
			}
		}
	}

	public static void main(String[] args) {
		Tap source1 = new Hfs(new TextLine(new Fields("line")), new Path(
				args[0]).toUri().toString());
		Tap source2 = new Hfs(new TextLine(new Fields("line")), new Path(
				args[1]).toUri().toString());

		Tap sink = new Hfs(new TextLine(),
				new Path(args[2]).toUri().toString(), SinkMode.REPLACE);

		Pipe pipe1 = new Pipe("pipe1");
		pipe1 = new Each(pipe1, new RegexSplitter(new Fields(F_ID1, F_WD1),
				"[ \t]*,[ \t]*"));

		Pipe pipe2 = new Pipe("pipe2");
		pipe2 = new Each(pipe2, new RegexSplitter(new Fields(F_ID2, F_WD2),
				"[ \t]*,[ \t]*"));

		Joiner join;
		switch (3) {
		default:
			join = new InnerJoin();
			break;
		case 1:
			join = new LeftJoin();
			break;
		case 2:
			join = new RightJoin();
			break;
		case 3:
			join = new OuterJoin();
			break;
		}
		Pipe pipe = new CoGroup(pipe1, new Fields(F_ID1), pipe2, new Fields(
				F_ID2), join);
		if (false) {
			pipe = new Every(pipe, new DebugAggregator());
			// getGroup()Ç≈éÊÇÍÇÈFieldsÇ≈ÇÕÅAçÄñ⁄ñºÇÕïtÇØÇÁÇÍÇƒÇ¢Ç»Ç¢ÅB
		}

		Map<String, Tap> sources = new HashMap<String, Tap>();
		sources.put(pipe1.getName(), source1);
		sources.put(pipe2.getName(), source2);

		FlowConnector flowConnector = new FlowConnector();
		Flow flow = flowConnector.connect("join-flow", sources, sink, pipe);
		flow.complete();
	}
}
