package jp.hishidama.eval.sample;

import jp.hishidama.eval.Expression;
import jp.hishidama.eval.ExpRuleFactory;

/**
 * ������\���̗�
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html">�Ђ�����</a>
 */
public class Print {

	public static void main(String[] args) {
		if (args.length <= 0) {
			System.out.println("set args");
			return;
		}
		String str = args[0];
		System.out.println("���́F" + str);

		Expression exp = ExpRuleFactory.getDefaultRule().parse(str);
		System.out.println("�ێ��F" + exp.toString());
	}
}
