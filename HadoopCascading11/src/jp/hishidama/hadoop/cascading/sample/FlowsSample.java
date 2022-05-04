package jp.hishidama.hadoop.cascading.sample;

import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import jp.hishidama.hadoop.cascading.conf.CascadingConfigured;
import cascading.cascade.Cascade;
import cascading.cascade.CascadeConnector;
import cascading.flow.Flow;
import cascading.flow.FlowConnector;
import cascading.operation.aggregator.Count;
import cascading.operation.expression.ExpressionFunction;
import cascading.operation.regex.RegexSplitGenerator;
import cascading.pipe.Each;
import cascading.pipe.Every;
import cascading.pipe.GroupBy;
import cascading.pipe.Pipe;
import cascading.scheme.SequenceFile;
import cascading.scheme.TextLine;
import cascading.tap.Hfs;
import cascading.tap.SinkMode;
import cascading.tap.Tap;
import cascading.tuple.Fields;

public class FlowsSample extends CascadingConfigured implements Tool {

	@Override
	public int run(String[] args) {
		Tap source = new Hfs(new TextLine(new Fields("line")),
				makeQualifiedPath(args[0]));
		Tap tap1 = new Hfs(new SequenceFile(new Fields("word")), "flows-temp1");
		Tap tap2 = new Hfs(new SequenceFile(new Fields("word")), "flows-temp2");
		// Tap tap1 = new TempHfs("temp1", SequenceFile.class);
		// Tap tap2 = new TempHfs("temp2", SequenceFile.class);
		Tap sink1 = new Hfs(new TextLine(), makeQualifiedPath(args[1]),
				SinkMode.REPLACE);
		Tap sink2 = new Hfs(new TextLine(), makeQualifiedPath(args[2]),
				SinkMode.REPLACE);

		Pipe pipe = new Pipe("pipe");
		pipe = new Each(pipe, new RegexSplitGenerator(new Fields("word"),
				"[ \t\n\r\f]+"));

		Pipe pipe1 = new Pipe("pipe1", pipe);
		Pipe pipe2 = new Pipe("pipe2", pipe);

		Pipe pipe31 = new Pipe("pipe31");
		pipe31 = new GroupBy(pipe31, Fields.ALL);
		pipe31 = new Every(pipe31, new Count());

		Pipe pipe32 = new Pipe("pipe32");
		pipe32 = new Each(pipe32, new Fields("word"), new ExpressionFunction(
				new Fields("word"), "word.toLowerCase()", String.class));
		pipe32 = new GroupBy(pipe32, Fields.ALL);
		pipe32 = new Every(pipe32, new Count());

		Map<String, Tap> taps = new HashMap<String, Tap>();
		taps.put(pipe1.getName(), tap1);
		taps.put(pipe2.getName(), tap2);

		FlowConnector flowConnector = new FlowConnector(getProperties());
		Flow flow1 = flowConnector.connect("flow1", source, taps, pipe1, pipe2);
		Flow flow31 = flowConnector.connect("flow31", tap1, sink1, pipe31);
		Flow flow32 = flowConnector.connect("flow32", tap1, sink2, pipe32);
		Cascade cascade = new CascadeConnector(getProperties()).connect(flow1,
				flow31, flow32);
		cascade.complete();

		return 0;
	}

	public static void main(String[] args) throws Exception {
		int r = ToolRunner.run(new FlowsSample(), args);
		System.exit(r);
	}
}
