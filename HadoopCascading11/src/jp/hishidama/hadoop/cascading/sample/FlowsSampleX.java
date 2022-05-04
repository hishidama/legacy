package jp.hishidama.hadoop.cascading.sample;

import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import jp.hishidama.hadoop.cascading.conf.CascadingConfigured;
import cascading.cascade.Cascade;
import cascading.cascade.CascadeConnector;
import cascading.flow.Flow;
import cascading.flow.FlowConnector;
import cascading.operation.aggregator.Count;
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

public class FlowsSampleX extends CascadingConfigured implements Tool {

	@Override
	public int run(String[] args) {
		Tap source1 = new Hfs(new TextLine(new Fields("line")),
				makeQualifiedPath(args[0]));
		Tap source2 = new Hfs(new TextLine(new Fields("line")),
				makeQualifiedPath(args[1]));
		// Tap tap = new TempHfs("temp", SequenceFile.class);
		Tap tap = new Hfs(new SequenceFile(new Fields("word")), "flows-temp");
		Tap sink = new Hfs(new TextLine(), makeQualifiedPath(args[2]),
				SinkMode.REPLACE);

		Pipe pipe1 = new Pipe("pipe1");
		pipe1 = new Each(pipe1, new RegexSplitGenerator(new Fields("word"),
				"[ \t\n\r\f]+"));

		Pipe pipe2 = new Pipe("pipe2");
		pipe2 = new Each(pipe2, new RegexSplitGenerator(new Fields("word"),
				"[ \t\n\r\f]+"));

		Pipe pipe3 = new Pipe("pipe3");
		pipe3 = new GroupBy(pipe3, Fields.ALL);
		pipe3 = new Every(pipe3, new Count());

		FlowConnector flowConnector = new FlowConnector(getProperties());
		Flow flow1 = flowConnector.connect("flow1", source1, tap, pipe1);
		Flow flow2 = flowConnector.connect("flow2", source2, tap, pipe2);
		Flow flow3 = flowConnector.connect("flow3", tap, sink, pipe3);
		Cascade cascade = new CascadeConnector(getProperties()).connect(flow1,
				flow2, flow3);
		cascade.complete();

		return 0;
	}

	public static void main(String[] args) throws Exception {
		int r = ToolRunner.run(new FlowsSampleX(), args);
		System.exit(r);
	}
}
