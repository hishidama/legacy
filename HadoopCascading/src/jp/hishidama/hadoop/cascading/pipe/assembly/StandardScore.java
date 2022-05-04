package jp.hishidama.hadoop.cascading.pipe.assembly;

import jp.hishidama.hadoop.cascading.pipe.CoGroupEx;
import cascading.flow.FlowProcess;
import cascading.operation.Aggregator;
import cascading.operation.AggregatorCall;
import cascading.operation.BaseOperation;
import cascading.operation.Function;
import cascading.operation.FunctionCall;
import cascading.operation.aggregator.Average;
import cascading.pipe.Each;
import cascading.pipe.Every;
import cascading.pipe.GroupBy;
import cascading.pipe.Pipe;
import cascading.pipe.SubAssembly;
import cascading.tuple.Fields;
import cascading.tuple.Tuple;

/**
 * 偏差値算出アセンブリー.
 *
 * <p>
 * →<a href="http://www.ne.jp/asahi/hishidama/home/tech/apache/hadoop/cascading/SubAssembly.html"
 * >使用例</a>
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/apache/hadoop/cascading/index.html"
 *         >ひしだま</a>
 * @since 2010.04.21
 */
public class StandardScore extends SubAssembly {
	private static final long serialVersionUID = -7077664290201741811L;

	/**
	 * コンストラクター.
	 *
	 * @param previous
	 *            前パイプ
	 * @param groupFields
	 *            グループキー項目
	 * @param argumentSelector
	 *            点数項目
	 * @param fieldDeclaration
	 *            新規項目名（0：平均、1：標準偏差、2：偏差値）
	 * @param outputSelector
	 *            出力項目（{@link Fields#ALL}等）
	 */
	public StandardScore(Pipe previous, Fields groupFields,
			Fields argumentSelector, Fields fieldDeclaration,
			Fields outputSelector) {
		String pipeName = previous.getName();

		Comparable<?> ten = argumentSelector.get(0);
		String ave = (String) fieldDeclaration.get(0);
		String sigma = (String) fieldDeclaration.get(1);
		String score = (String) fieldDeclaration.get(2);

		// 平均を算出する
		Pipe pipeAve = new Pipe(pipeName + ".average", previous);
		pipeAve = new GroupBy(pipeAve, groupFields);
		pipeAve = new Every(pipeAve, argumentSelector, new Average(new Fields(
				ave)));
		// pipeAve = new Each(pipeAve, new Debug(true));

		// 点数と平均を結合し、標準偏差を求める
		Pipe pipeSigma = new CoGroupEx(pipeName + ".sigma", previous,
				groupFields, pipeAve, groupFields);
		pipeSigma = new Every(pipeSigma, new Fields(ten, ave), new Deviation(
				new Fields(sigma)));
		// pipeSigma = new Each(pipeSigma, new Debug(true));

		// 点数と標準偏差を結合し、偏差値を求める
		Pipe pipe = new CoGroupEx(pipeName, Pipe.pipes(previous, pipeAve,
				pipeSigma), Fields.fields(groupFields, groupFields, Fields
				.size(groupFields.size())));

		pipe = new Each(pipe, new Fields(ten, ave, sigma), new Score(
				new Fields(score)), outputSelector);
		// pipe = new Each(pipe, new Debug(true));

		setTails(pipe);
	}

	/**
	 * 標準偏差を求めるAggregator
	 */
	protected static class Deviation extends BaseOperation<Deviation.Context>
			implements Aggregator<Deviation.Context> {
		private static final long serialVersionUID = 1L;

		protected static class Context {
			public double sum;
			public int count;

			public void reset() {
				sum = 0;
				count = 0;
			}
		}

		public Deviation(Fields fieldDeclaration) {
			super(2, fieldDeclaration);
			// args0：点数
			// args1：平均点
		}

		@Override
		public void start(FlowProcess flowProcess,
				AggregatorCall<Context> aggregatorCall) {
			Context ctx = aggregatorCall.getContext();
			if (ctx == null) {
				aggregatorCall.setContext(new Context());
			} else {
				ctx.reset();
			}
		}

		@Override
		public void aggregate(FlowProcess flowProcess,
				AggregatorCall<Context> aggregatorCall) {
			Context ctx = aggregatorCall.getContext();
			Tuple value = aggregatorCall.getArguments().getTuple();
			double ten = value.getDouble(0);
			double average = value.getDouble(1);
			ctx.sum += Math.pow(ten - average, 2);
			ctx.count++;
		}

		@Override
		public void complete(FlowProcess flowProcess,
				AggregatorCall<Context> aggregatorCall) {
			Context ctx = aggregatorCall.getContext();
			aggregatorCall.getOutputCollector().add(
					new Tuple(Math.sqrt(ctx.sum / ctx.count)));
		}
	}

	/**
	 * 偏差値を求めるFunction
	 */
	protected static class Score extends BaseOperation<Object> implements
			Function<Object> {
		private static final long serialVersionUID = 1L;

		public Score(Fields fieldDeclaration) {
			super(3, fieldDeclaration);
			// args0：点数
			// args1：平均点
			// args2：標準偏差
		}

		@Override
		public void operate(FlowProcess flowProcess,
				FunctionCall<Object> functionCall) {
			Tuple value = functionCall.getArguments().getTuple();
			double ten = value.getInteger(0);
			double average = value.getDouble(1);
			double sigma = value.getDouble(2);
			double score = (ten - average) * 10 / sigma + 50;
			functionCall.getOutputCollector().add(new Tuple(score));
		}
	}
}
