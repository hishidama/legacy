package jp.hishidama.hadoop.cascading.hbase;

import java.util.Map;

import jp.hishidama.hadoop.cascading.conf.CascadingConfigured;

import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import cascading.cascade.Cascades;
import cascading.flow.Flow;
import cascading.flow.FlowConnector;
import cascading.hbase.HBaseScheme;
import cascading.hbase.HBaseTap;
import cascading.hbase.HBaseTapEx;
import cascading.operation.Identity;
import cascading.operation.aggregator.Sum;
import cascading.pipe.Each;
import cascading.pipe.Every;
import cascading.pipe.GroupBy;
import cascading.pipe.Pipe;
import cascading.tap.SinkMode;
import cascading.tap.Tap;
import cascading.tuple.Fields;

public class CBaseTotal5_2 extends CascadingConfigured implements Tool {

	public static final String TABLE_NAME = "student";

	public static final String F_SID = "student-id";
	public static final String F_TEN = "ten";

	@Override
	public int run(String[] args) throws Exception {
		String ������ = args[0];
		final String[] kname = { "suugaku:" + ������, "kokugo:" + ������,
				"rika:" + ������, "shakai:" + ������, "eigo:" + ������ };
		final String F_���v = "total5:" + ������;

		Fields keyFields = new Fields(F_SID);
		Tap[] source = new Tap[kname.length];
		for (int i = 0; i < source.length; i++) {
			source[i] = new HBaseTap(TABLE_NAME, new HBaseScheme(keyFields,
					new Fields(kname[i])));
		}

		Tap sink = new HBaseTapEx(TABLE_NAME, new HBaseScheme(keyFields,
				new Fields(F_���v)), SinkMode.UPDATE);

		Pipe[] pipes = new Pipe[kname.length];
		for (int i = 0; i < pipes.length; i++) {
			pipes[i] = new Each("pipe" + i, new Identity(new Fields(F_SID,
					F_TEN), Object.class, int.class));
		}

		Pipe pipe = new GroupBy("pipe", pipes, new Fields(F_SID));
		pipe = new Every(pipe, new Fields(F_TEN), new Sum(new Fields(F_���v),
				int.class));

		Map<String, Tap> sources = Cascades.tapsMap(pipes, source);

		FlowConnector flowConnector = new FlowConnector(getProperties());
		Flow flow = flowConnector
				.connect("student-total5", sources, sink, pipe);
		 flow.writeDOT("C:/temp/graphviz/total5_2.dot");
	//	flow.complete();

		return 0;
	}

	public static void main(String[] args) throws Exception {
		int r = ToolRunner.run(new CBaseTotal5_2(), args);
		System.exit(r);
	}
}
