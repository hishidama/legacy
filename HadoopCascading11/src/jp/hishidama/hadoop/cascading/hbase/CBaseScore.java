package jp.hishidama.hadoop.cascading.hbase;

import jp.hishidama.hadoop.cascading.conf.CascadingConfigured;
import jp.hishidama.hadoop.cascading.pipe.assembly.StandardScore;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import cascading.cascade.Cascade;
import cascading.cascade.CascadeConnector;
import cascading.flow.Flow;
import cascading.flow.FlowConnector;
import cascading.hbase.HBaseScheme;
import cascading.hbase.HBaseTap;
import cascading.hbase.HBaseTapEx;
import cascading.operation.Insert;
import cascading.pipe.Each;
import cascading.pipe.Pipe;
import cascading.tap.SinkMode;
import cascading.tap.Tap;
import cascading.tuple.Fields;

public class CBaseScore extends CascadingConfigured implements Tool {

	public static final String TABLE_NAME = "student";

	public static final Fields F_SID = new Fields("student-id");

	@Override
	public int run(String[] args) throws Exception {
		String ŽŽŒ±–¼ = args[0];

		final String[] kname = { "suugaku", "kokugo", "rika", "shakai", "eigo",
				"total5" };
		Flow[] flow = new Flow[kname.length];
		// Flow[] flow = new Flow[1];
		for (int i = 0; i < flow.length; i++) {
			flow[i] = createFlow(ŽŽŒ±–¼, kname[i]);
		}

		Cascade cascade = new CascadeConnector().connect(flow);
		cascade.complete();

		return 0;
	}

	protected Flow createFlow(String ŽŽŒ±–¼, String kname) {
		final Fields F_KEY = new Fields("dummy");
		final Fields F_TEN = new Fields(kname + ":" + ŽŽŒ±–¼);
		final Fields F_SCORE = new Fields(kname + ":" + ŽŽŒ±–¼ + ".score");

		Tap source = new HBaseTap(TABLE_NAME, new HBaseScheme(F_SID, F_TEN));
		Tap sink = new HBaseTapEx(TABLE_NAME, new HBaseScheme(F_SID, F_SCORE),
				SinkMode.UPDATE);

		Pipe pipe = new Pipe("pipe");
		if (false) {
			pipe = new Each(pipe, new Insert(F_KEY, 0), Fields.ALL);
		} else {
			pipe = new Each(pipe, new Insert(F_KEY, NullWritable.get()),
					Fields.ALL);
		}
		pipe = new StandardScore(pipe, F_KEY, F_TEN, new Fields("ave", "sigma")
				.append(F_SCORE), Fields.join(F_SID, F_SCORE));

		FlowConnector flowConnector = new FlowConnector(getProperties());
		Flow flow = flowConnector.connect(kname + "-flow", source, sink, pipe);
		return flow;
	}

	public static void main(String[] args) throws Exception {
		int r = ToolRunner.run(new CBaseScore(), args);
		System.exit(r);
	}
}
