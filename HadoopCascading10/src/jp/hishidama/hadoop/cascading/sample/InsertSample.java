package jp.hishidama.hadoop.cascading.sample;

import java.io.IOException;

import org.apache.hadoop.fs.Path;

import cascading.flow.Flow;
import cascading.flow.FlowConnector;
import cascading.operation.Insert;
import cascading.operation.aggregator.Average;
import cascading.pipe.Each;
import cascading.pipe.Every;
import cascading.pipe.GroupBy;
import cascading.pipe.Pipe;
import cascading.scheme.TextLine;
import cascading.tap.Hfs;
import cascading.tap.SinkMode;
import cascading.tap.Tap;
import cascading.tuple.Fields;
import cascading.tuple.TupleEntry;
import cascading.tuple.TupleEntryIterator;

public class InsertSample {

	public static void main(String[] args) throws IOException {
		Tap source = new Hfs(new TextLine(new Fields("line")),
				new Path(args[0]).toUri().toString());
		Tap sink = new Hfs(new TextLine(),
				new Path(args[1]).toUri().toString(), SinkMode.REPLACE);

		Pipe pipe = new Pipe("pipe");
		pipe = new Each(pipe, new Insert(new Fields("key"), "key0"), Fields.ALL);
		pipe = new GroupBy(pipe, new Fields("key"));
		pipe = new Every(pipe, new Fields("line"), new Average(new Fields(
				"count")), Fields.RESULTS);

		FlowConnector flowConnector = new FlowConnector();
		Flow flow = flowConnector.connect("join-flow", source, sink, pipe);
		flow.complete();

		TupleEntryIterator i = flow.openSink();
		try {
			while (i.hasNext()) {
				TupleEntry entry = i.next();
				System.out.println(entry);
			}
		} finally {
			i.close();
		}
	}
}
