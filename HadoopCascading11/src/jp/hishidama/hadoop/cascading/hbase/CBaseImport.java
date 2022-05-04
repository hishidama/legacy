package jp.hishidama.hadoop.cascading.hbase;

import java.util.StringTokenizer;

import jp.hishidama.hadoop.cascading.conf.CascadingConfigured;

import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import cascading.flow.Flow;
import cascading.flow.FlowConnector;
import cascading.flow.FlowProcess;
import cascading.hbase.HBaseScheme;
import cascading.hbase.HBaseTap;
import cascading.hbase.HBaseTapEx;
import cascading.operation.BaseOperation;
import cascading.operation.Function;
import cascading.operation.FunctionCall;
import cascading.pipe.Each;
import cascading.pipe.Pipe;
import cascading.scheme.TextLine;
import cascading.tap.Hfs;
import cascading.tap.SinkMode;
import cascading.tap.Tap;
import cascading.tuple.Fields;
import cascading.tuple.Tuple;

public class CBaseImport extends CascadingConfigured implements Tool {

	public static final String TABLE_NAME = "student";

	public static final String F_SID = "student-id";
	public static final String F_NAME = "personal:name";

	protected static class SplitFunction extends BaseOperation<Object>
			implements Function<Object> {
		private static final long serialVersionUID = 1L;

		public SplitFunction(Fields fieldDeclaration) {
			super(1, fieldDeclaration);
		}

		@Override
		public void operate(FlowProcess flowProcess,
				FunctionCall<Object> functionCall) {
			String line = functionCall.getArguments().getTuple().getString(0);
			if (line.isEmpty() || line.startsWith("#")) {
				return;
			}

			Tuple output = Tuple.size(fieldDeclaration.size());

			StringTokenizer tokenizer = new StringTokenizer(line, ",");
			for (int i = 0; tokenizer.hasMoreTokens(); i++) {
				String token = tokenizer.nextToken().trim();
				output.set(i, token);
			}

			functionCall.getOutputCollector().add(output);
		}
	}

	@Override
	public int run(String[] args) throws Exception {
		String Œ±–¼ = args[0];
		final String F_”Šw = "suugaku:" + Œ±–¼;
		final String F_‘Œê = "kokugo:" + Œ±–¼;
		final String F_—‰È = "rika:" + Œ±–¼;
		final String F_Ğ‰ï = "shakai:" + Œ±–¼;
		final String F_‰pŒê = "eigo:" + Œ±–¼;

		Tap source = new Hfs(new TextLine(new Fields("line")),
				makeQualifiedPath(args[1]));

		Fields keyFields = new Fields(F_SID);
		// String[] familyNames = { "personal", "suugaku", "kokugo", "rika",
		// "shakai", "eigo" };
		// Fields[] valueFields = { new Fields("name"), new Fields(Œ±–¼),
		// new Fields(Œ±–¼), new Fields(Œ±–¼), new Fields(Œ±–¼),
		// new Fields(Œ±–¼) };
		// Fields[] valueFields = { new Fields(F_NAME), new Fields(F_”Šw),
		// new Fields(F_‘Œê), new Fields(F_—‰È), new Fields(F_Ğ‰ï),
		// new Fields(F_‰pŒê) };
		Fields valueFields = new Fields(F_NAME, F_”Šw, F_‘Œê, F_—‰È, F_Ğ‰ï, F_‰pŒê);
		Tap sink = new HBaseTapEx(TABLE_NAME, new HBaseScheme(keyFields,
		/* familyNames, */valueFields), SinkMode.UPDATE);
		// ~ Tap sink = new HBaseTapEx(TABLE_NAME, new HBaseScheme(keyFields,
		// ~ Fields.ALL), SinkMode.UPDATE);

		Pipe pipe = new Pipe("pipe");
		pipe = new Each(pipe, new SplitFunction(new Fields(F_SID, F_NAME, F_”Šw,
				F_‘Œê, F_—‰È, F_Ğ‰ï, F_‰pŒê)));

		FlowConnector flowConnector = new FlowConnector(getProperties());
		Flow flow = flowConnector.connect("student-import", source, sink, pipe);
		flow.complete();

		return 0;
	}

	public static void main(String[] args) throws Exception {
		int r = ToolRunner.run(new CBaseImport(), args);
		System.exit(r);
	}
}
