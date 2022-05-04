package jp.hishidama.hadoop.cascading.sample;

import jp.hishidama.hadoop.cascading.conf.CascadingConfigured;

import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import cascading.flow.Flow;
import cascading.flow.FlowConnector;
import cascading.flow.FlowProcess;
import cascading.operation.BaseOperation;
import cascading.operation.Filter;
import cascading.operation.FilterCall;
import cascading.operation.expression.ExpressionFilter;
import cascading.operation.filter.Not;
import cascading.operation.filter.Or;
import cascading.operation.regex.RegexFilter;
import cascading.pipe.Each;
import cascading.pipe.Pipe;
import cascading.scheme.TextLine;
import cascading.tap.Hfs;
import cascading.tap.SinkMode;
import cascading.tap.Tap;
import cascading.tuple.Fields;
import cascading.tuple.Tuple;

public class FilterSample extends CascadingConfigured implements Tool {

	protected static class StartsFilter extends BaseOperation<Object> implements
			Filter<Object> {
		private static final long serialVersionUID = 1L;

		protected String prefix;

		/**
		 * コンストラクター
		 *
		 * @param prefix
		 *            先頭文字列
		 */
		public StartsFilter(String prefix) {
			this.prefix = prefix;
		}

		@Override
		public boolean isRemove(FlowProcess flowProcess,
				FilterCall<Object> filterCall) {
			Tuple tuple = filterCall.getArguments().getTuple();

			String s = tuple.getString(0);
			if (s.startsWith(prefix)) {
				return true;
			}

			return false;
		}
	}

	@Override
	public int run(String[] args) throws Exception {
		Tap source = new Hfs(new TextLine(new Fields("line")),
				makeQualifiedPath(args[0]));
		Tap sink = new Hfs(new TextLine(), makeQualifiedPath(args[1]),
				SinkMode.REPLACE);

		Pipe pipe = new Pipe("pipe");

		Filter<?> filter;
		if (false) {
			// filter= new StartsFilter(";");
			filter = new Or(new StartsFilter("#"), new StartsFilter(";"));
		}
		if (false) {
			// filter = new ExpressionFilter("line.startsWith(\"#\")",
			// String.class);
			// filter = new ExpressionFilter("$0.startsWith(\"#\")",
			// String.class);
			filter = new ExpressionFilter(
					"$0.startsWith(\"#\")||$0.startsWith(\";\")", String.class);
		} else {
			filter = new Not(new RegexFilter("^[;#]"));
		}

		pipe = new Each(pipe, new Fields("line"), filter);

		FlowConnector flowConnector = new FlowConnector(super
				.getProperties());
		Flow flow = flowConnector.connect("filter-sample", source, sink, pipe);
		flow.complete();

		return 0;
	}

	public static void main(String[] args) throws Exception {
		int r = ToolRunner.run(new FilterSample(), args);
		System.exit(r);
	}
}
