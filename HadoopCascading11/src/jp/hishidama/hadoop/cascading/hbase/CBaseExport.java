package jp.hishidama.hadoop.cascading.hbase;

import jp.hishidama.hadoop.cascading.conf.CascadingConfigured;

import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import cascading.flow.Flow;
import cascading.flow.FlowConnector;
import cascading.hbase.HBaseScheme;
import cascading.hbase.HBaseTap;
import cascading.pipe.GroupBy;
import cascading.pipe.Pipe;
import cascading.scheme.TextLine;
import cascading.tap.Hfs;
import cascading.tap.SinkMode;
import cascading.tap.Tap;
import cascading.tuple.Fields;

public class CBaseExport extends CascadingConfigured implements Tool {

	public static final String TABLE_NAME = "student";

	public static final String F_SID = "student-id";
	public static final String F_NAME = "personal:name";

	@Override
	public int run(String[] args) throws Exception {
		String ������ = args[0];
		final String F_���w = "suugaku:" + ������;
		final String F_���� = "kokugo:" + ������;
		final String F_���� = "rika:" + ������;
		final String F_�Љ� = "shakai:" + ������;
		final String F_�p�� = "eigo:" + ������;
		final String F_���v = "total5:" + ������;

		Fields keyFields = new Fields(F_SID);
		Fields valueFields = new Fields(F_NAME, F_���w, F_����, F_����, F_�Љ�, F_�p��,
				F_���v);
		Tap source = new HBaseTap(TABLE_NAME, new HBaseScheme(keyFields,
				valueFields));

		Tap sink = new Hfs(new TextLine(), makeQualifiedPath(args[1]),
				SinkMode.REPLACE);

		Pipe pipe = new Pipe("pipe");
		pipe = new GroupBy(pipe, Fields.ALL);

		FlowConnector flowConnector = new FlowConnector(getProperties());
		Flow flow = flowConnector.connect("student-export", source, sink, pipe);
		flow.complete();

		return 0;
	}

	public static void main(String[] args) throws Exception {
		int r = ToolRunner.run(new CBaseExport(), args);
		System.exit(r);
	}
}
