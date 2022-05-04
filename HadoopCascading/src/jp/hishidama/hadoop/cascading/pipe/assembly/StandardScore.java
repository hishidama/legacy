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
 * �΍��l�Z�o�A�Z���u���[.
 *
 * <p>
 * ��<a href="http://www.ne.jp/asahi/hishidama/home/tech/apache/hadoop/cascading/SubAssembly.html"
 * >�g�p��</a>
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/apache/hadoop/cascading/index.html"
 *         >�Ђ�����</a>
 * @since 2010.04.21
 */
public class StandardScore extends SubAssembly {
	private static final long serialVersionUID = -7077664290201741811L;

	/**
	 * �R���X�g���N�^�[.
	 *
	 * @param previous
	 *            �O�p�C�v
	 * @param groupFields
	 *            �O���[�v�L�[����
	 * @param argumentSelector
	 *            �_������
	 * @param fieldDeclaration
	 *            �V�K���ږ��i0�F���ρA1�F�W���΍��A2�F�΍��l�j
	 * @param outputSelector
	 *            �o�͍��ځi{@link Fields#ALL}���j
	 */
	public StandardScore(Pipe previous, Fields groupFields,
			Fields argumentSelector, Fields fieldDeclaration,
			Fields outputSelector) {
		String pipeName = previous.getName();

		Comparable<?> ten = argumentSelector.get(0);
		String ave = (String) fieldDeclaration.get(0);
		String sigma = (String) fieldDeclaration.get(1);
		String score = (String) fieldDeclaration.get(2);

		// ���ς��Z�o����
		Pipe pipeAve = new Pipe(pipeName + ".average", previous);
		pipeAve = new GroupBy(pipeAve, groupFields);
		pipeAve = new Every(pipeAve, argumentSelector, new Average(new Fields(
				ave)));
		// pipeAve = new Each(pipeAve, new Debug(true));

		// �_���ƕ��ς��������A�W���΍������߂�
		Pipe pipeSigma = new CoGroupEx(pipeName + ".sigma", previous,
				groupFields, pipeAve, groupFields);
		pipeSigma = new Every(pipeSigma, new Fields(ten, ave), new Deviation(
				new Fields(sigma)));
		// pipeSigma = new Each(pipeSigma, new Debug(true));

		// �_���ƕW���΍����������A�΍��l�����߂�
		Pipe pipe = new CoGroupEx(pipeName, Pipe.pipes(previous, pipeAve,
				pipeSigma), Fields.fields(groupFields, groupFields, Fields
				.size(groupFields.size())));

		pipe = new Each(pipe, new Fields(ten, ave, sigma), new Score(
				new Fields(score)), outputSelector);
		// pipe = new Each(pipe, new Debug(true));

		setTails(pipe);
	}

	/**
	 * �W���΍������߂�Aggregator
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
			// args0�F�_��
			// args1�F���ϓ_
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
	 * �΍��l�����߂�Function
	 */
	protected static class Score extends BaseOperation<Object> implements
			Function<Object> {
		private static final long serialVersionUID = 1L;

		public Score(Fields fieldDeclaration) {
			super(3, fieldDeclaration);
			// args0�F�_��
			// args1�F���ϓ_
			// args2�F�W���΍�
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
