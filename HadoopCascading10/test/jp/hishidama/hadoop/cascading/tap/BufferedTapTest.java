package jp.hishidama.hadoop.cascading.tap;

import java.util.ArrayList;
import java.util.List;

import cascading.flow.Flow;
import cascading.flow.FlowConnector;
import cascading.operation.Insert;
import cascading.operation.aggregator.Average;
import cascading.pipe.Each;
import cascading.pipe.Every;
import cascading.pipe.GroupBy;
import cascading.pipe.Pipe;
import cascading.scheme.TextLine;
import cascading.tap.Lfs;
import cascading.tap.Tap;
import cascading.tuple.Fields;
import cascading.tuple.Tuple;

public class BufferedTapTest {

	public static void main(String[] args) {

		List<Tuple> list = new ArrayList<Tuple>();
		list.add(new Tuple("123"));
		list.add(new Tuple("456"));
		list.add(new Tuple("789"));

		Tap source = new BufferedTap(new TextLine(new Fields("line")), list);
		Tap sink = new Lfs(new TextLine(), "temp");

		Pipe pipe = new Pipe("pipe");
		pipe = new Each(pipe, new Insert(new Fields("key"), "key0"), Fields.ALL);
		pipe = new GroupBy(pipe, new Fields("key"));
		pipe = new Every(pipe, new Average());

		FlowConnector flowConnector = new FlowConnector();
		Flow flow = flowConnector.connect(source, sink, pipe);
		flow.complete();
	}
}
