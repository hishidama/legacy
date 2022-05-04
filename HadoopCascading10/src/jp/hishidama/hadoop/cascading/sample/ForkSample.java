package jp.hishidama.hadoop.cascading.sample;

import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.fs.Path;

import cascading.flow.Flow;
import cascading.flow.FlowConnector;
import cascading.operation.*;
import cascading.operation.aggregator.*;
import cascading.operation.regex.*;
import cascading.pipe.*;
import cascading.scheme.TextLine;
import cascading.tap.Hfs;
import cascading.tap.SinkMode;
import cascading.tap.Tap;
import cascading.tuple.Fields;

public class ForkSample {

	public static void main(String[] args) {
		Tap source = new Hfs(new TextLine(new Fields("line")),
				new Path(args[0]).toUri().toString());
		Tap sink1 = new Hfs(new TextLine(), new Path(args[1] + "1").toUri()
				.toString(), SinkMode.REPLACE);
		Tap sink2 = new Hfs(new TextLine(), new Path(args[1] + "2").toUri()
				.toString(), SinkMode.REPLACE);
		Tap sink21 = new Hfs(new TextLine(), new Path(args[1] + "21").toUri()
				.toString(), SinkMode.REPLACE);
		Tap sink22 = new Hfs(new TextLine(), new Path(args[1] + "22").toUri()
				.toString(), SinkMode.REPLACE);

		Pipe pipe = new Pipe("最初のパイプ");
		pipe = new Each(pipe, new RegexSplitter(new Fields("key1", "key2",
				"data"), ",")); // 入力行をカンマ区切りで分割
		if (false) {
			pipe = new Each(pipe, new Identity(String.class, String.class,
					int.class)); // data項目をintに変換
		}

		Pipe pipe1 = new GroupBy("集計1", pipe, new Fields("key1"));
		pipe1 = new Every(pipe1, new Fields("data"), new Sum());

		Pipe pipe2 = new GroupBy("集計2", pipe, new Fields("key2"));
		Pipe pipe21 = new Every(pipe2, new Fields("data"), new Sum());
		pipe21 = new Pipe("SUM2", pipe21);

		Pipe pipe2s = new GroupBy("集計2", pipe, new Fields("key2"));
		// Pipe pipe2s = pipe2;
		Pipe pipe22 = new Every(pipe2s, new Count());
		pipe22 = new Pipe("CNT2", pipe22);
		Pipe pipe2t = new CoGroup("結合2", Pipe.pipes(pipe21, pipe22), Fields
				.fields(Fields.FIRST, Fields.FIRST), new Fields("key2", "sum",
				"dummy2", "count"));

		Map<String, Tap> sinks = new HashMap<String, Tap>();
		sinks.put(pipe1.getName(), sink1);
		sinks.put(pipe21.getName(), sink21);
		sinks.put(pipe22.getName(), sink22);
		sinks.put(pipe2t.getName(), sink2);

		FlowConnector flowConnector = new FlowConnector();
		Flow flow = flowConnector.connect("fork-sample", source, sinks, pipe1,
				pipe21, pipe22, pipe2t);
		// Flow flow = flowConnector.connect("fork-sample", source, sinks,
		// pipe1,
		// pipe2t);
		flow.complete();
	}
}
