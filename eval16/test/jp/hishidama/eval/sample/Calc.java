package jp.hishidama.eval.sample;

import jp.hishidama.eval.Expression;
import jp.hishidama.eval.ExpRuleFactory;
import jp.hishidama.eval.Rule;

/**
 * �l�����Z�̗�
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html">�Ђ�����</a>
 */
public class Calc {

	public static void main(String[] args) {
		if (args.length <= 0) {
			System.out.println("set args");
			return;
		}
		String str = args[0];
		System.out.println("���@�F" + str);

		Rule rule = ExpRuleFactory.getDefaultRule();
		Expression exp = rule.parse(str);
		long result = exp.evalLong();
		System.out.println("���ʁF" + result);
	}
}
