package jp.hishidama.hadoop.cascading.hbase;

import jp.hishidama.hadoop.cascading.conf.CascadingConfigured;

import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import cascading.flow.Flow;
import cascading.flow.FlowConnector;
import cascading.hbase.HBaseScheme;
import cascading.hbase.HBaseTap;
import cascading.hbase.HBaseTapEx;
import cascading.operation.Debug;
import cascading.operation.Identity;
import cascading.operation.aggregator.Sum;
import cascading.pipe.Each;
import cascading.pipe.Every;
import cascading.pipe.GroupBy;
import cascading.pipe.Pipe;
import cascading.tap.SinkMode;
import cascading.tap.Tap;
import cascading.tuple.Fields;

public class CBaseTotal5 extends CascadingConfigured implements Tool {

	public static final String TABLE_NAME = "student";

	public static final String F_SID = "student-id";
	public static final String F_TEN = "ten";

	@Override
	public int run(String[] args) throws Exception {
		String 試験名 = args[0];
		final String F_数学 = "suugaku:" + 試験名;
		final String F_国語 = "kokugo:" + 試験名;
		final String F_理科 = "rika:" + 試験名;
		final String F_社会 = "shakai:" + 試験名;
		final String F_英語 = "eigo:" + 試験名;
		final String F_合計 = "total5:" + 試験名;

		Fields keyFields = new Fields(F_SID);
		Fields valueFields = new Fields(F_数学, F_国語, F_理科, F_社会, F_英語);
		Tap source = new HBaseTap(TABLE_NAME, new HBaseScheme(keyFields,
				valueFields));

		Tap sink = new HBaseTapEx(TABLE_NAME, new HBaseScheme(keyFields,
				new Fields(F_合計)), SinkMode.UPDATE);

		Pipe pipe = new Pipe("pipe");
		Pipe pipe1 = new Each(pipe, new Fields(F_SID, F_数学), new Identity(
				new Fields(F_SID, F_TEN), Object.class, int.class));
		Pipe pipe2 = new Each(pipe, new Fields(F_SID, F_国語), new Identity(
				new Fields(F_SID, F_TEN), Object.class, int.class));
		Pipe pipe3 = new Each(pipe, new Fields(F_SID, F_理科), new Identity(
				new Fields(F_SID, F_TEN), Object.class, int.class));
		Pipe pipe4 = new Each(pipe, new Fields(F_SID, F_社会), new Identity(
				new Fields(F_SID, F_TEN), Object.class, int.class));
		Pipe pipe5 = new Each(pipe, new Fields(F_SID, F_英語), new Identity(
				new Fields(F_SID, F_TEN), Object.class, int.class));
		pipe = new GroupBy("pipe", Pipe
				.pipes(pipe1, pipe2, pipe3, pipe4, pipe5), new Fields(F_SID));
		if (false) {
			pipe = new Each(pipe, new Debug());
			pipe = new GroupBy(pipe, new Fields(F_SID));
		}
		pipe = new Every(pipe, new Fields(F_TEN), new Sum(new Fields(F_合計),
				int.class));

		FlowConnector flowConnector = new FlowConnector(getProperties());
		Flow flow = flowConnector.connect("student-total5", source, sink, pipe);
		flow.writeDOT("C:/temp/graphviz/total5.dot");
		// flow.complete();

		return 0;
	}

	public static void main(String[] args) throws Exception {
		int r = ToolRunner.run(new CBaseTotal5(), args);
		System.exit(r);
	}
}
