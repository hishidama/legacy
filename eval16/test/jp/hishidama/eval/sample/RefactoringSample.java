package jp.hishidama.eval.sample;

import jp.hishidama.eval.ExpRuleFactory;
import jp.hishidama.eval.Expression;
import jp.hishidama.eval.Rule;
import jp.hishidama.eval.ref.RefactorVarName;
import jp.hishidama.eval.var.MapVariable;

/**
 * ���t�@�N�^�����O�̗�
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >�Ђ�����</a>
 */
public class RefactoringSample {

	public static void main(String[] args) {
		refactorName();
		System.out.println();
		refactorFunc();
	}

	static void refactorName() {
		Rule rule = ExpRuleFactory.getDefaultRule();
		Expression exp = rule.parse("aa+bb+1");

		System.out.println("�ύX�O�F" + exp.toString());
		exp.refactorName(new RefactorVarName(null, "bb", "foo")); // �ϐ���bb��foo�ɕύX
		System.out.println("�ύX��F" + exp.toString());
	}

	static void refactorFunc() {
		Rule rule = ExpRuleFactory.getDefaultRule();
		Expression exp = rule.parse("a.x /2");

		MapVariable<String, Object> var = new MapVariable<String, Object>();
		var.setValue("a", new Object());
		exp.setVariable(var);

		System.out.println("�ύX�O�F" + exp.toString());
		exp
				.refactorFunc(new RefactorVarName(Object.class, "x", "getX()"),
						rule);
		System.out.println("�ύX��F" + exp.toString());
	}
}
