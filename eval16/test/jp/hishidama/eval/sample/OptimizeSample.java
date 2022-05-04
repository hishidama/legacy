package jp.hishidama.eval.sample;

import jp.hishidama.eval.Expression;
import jp.hishidama.eval.ExpRuleFactory;
import jp.hishidama.eval.Rule;
import jp.hishidama.eval.oper.IntOperator;
import jp.hishidama.eval.oper.LongOperator;

/**
 * �œK���̗�
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >�Ђ�����</a>
 */
public class OptimizeSample {

	public static void main(String[] args) {
		free(args);
		// sample();
	}

	static void free(String[] args) {
		if (args.length <= 0) {
			System.out.println("set args");
			return;
		}
		String str = args[0];
		System.out.println("���́F" + str);

		Expression exp = ExpRuleFactory.getDefaultRule().parse(str);
		System.out.println("�O�@�F" + exp.toString());

		Expression opt = exp.dup();
		opt.optimize(null, new LongOperator());
		System.out.println("��@�F" + opt.toString());

		System.out.println("�ȑO�F" + exp.toString());
	}

	static void sample() {
		Rule rule = ExpRuleFactory.getDefaultRule();
		Expression exp = rule.parse("1+2+a");
		Expression opt = exp.dup();
		opt.optimize(null, new IntOperator());
		System.out.println("�œK���O�F" + exp.toString());
		System.out.println("�œK����F" + opt.toString());
	}
}
