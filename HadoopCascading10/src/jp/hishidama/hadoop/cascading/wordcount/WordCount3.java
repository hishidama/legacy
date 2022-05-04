package jp.hishidama.hadoop.cascading.wordcount;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import jp.hishidama.hadoop.conf.ConfigurationMap;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import cascading.flow.Flow;
import cascading.flow.FlowConnector;
import cascading.flow.MultiMapReducePlanner;
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

@SuppressWarnings("deprecation")
public class WordCount3 extends Configured implements Tool {

	public static final String F_LINE = "line";
	public static final String F_WORD = "word";
	public static final String F_COUNT = "count";

	public String makeQualifiedPath(String path) {
		try {
			FileSystem fs = FileSystem.get(super.getConf());
			return new Path(path).makeQualified(fs).toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public int run(String[] args) throws Exception {
		Tap source = new Hfs(new TextLine(new Fields(F_LINE)),
				makeQualifiedPath(args[0]));
		Tap sink = new Hfs(new TextLine(), makeQualifiedPath(args[1]),
				SinkMode.REPLACE);

		Pipe pipe = new Pipe("wordcount");
		pipe = new Each(pipe, new RegexSplitGenerator(new Fields(F_WORD),
				"[ \t\n\r\f]+"));

		pipe = new GroupBy(pipe, new Fields(F_WORD));
		pipe = new Every(pipe, new Count(new Fields(F_COUNT)));

		Map<Object, Object> properties;
		if (true) {
			properties = new HashMap<Object, Object>();
			MultiMapReducePlanner
					.setJobConf(properties, new JobConf(getConf()));
		} else {
			properties = new ConfigurationMap(getConf());
			// Å~setApplicationJarClass()Ç≈ClassÇì¸ÇÍÇÈÇ©ÇÁÅA
			// StringÇÃÇ›Ç≈Ç†ÇÈConfigurationMapÇ≈ÇÕÉ_ÉÅ
		}
		FlowConnector.setApplicationJarClass(properties, getClass());
		FlowConnector flowConnector = new FlowConnector(properties);
		Flow flow = flowConnector.connect("wordcount", source, sink, pipe);
	//	flow.complete();
		flow.writeDOT("C:/temp/graphviz/wordcount.dot");

		return 0;
	}

	public static void main(String[] args) throws Exception {
		int r = ToolRunner.run(new WordCount3(), args);
		System.exit(r);
	}
}
