package jp.hishidama.eval.sample;

import jp.hishidama.eval.ExpRuleFactory;
import jp.hishidama.eval.Expression;
import jp.hishidama.eval.Rule;
import jp.hishidama.eval.exp.AbstractExpression;
import jp.hishidama.eval.oper.JavaExOperator;
import jp.hishidama.eval.var.MapVariable;

/**
 * �V���O���N�H�[�e�[�V�������_�u���N�H�[�e�[�V�����Ɠ��l�Ɉ�����
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >�Ђ�����</a>
 */
public class QuoteSample {

	public static void main(String[] args) {
		MapVariable<String, Object> var = new MapVariable<String, Object>(
				String.class, Object.class);
		Rule rule = ExpRuleFactory.getDefaultRule();
		Expression exp = rule.parse("v1='ABC', v2=\"DEF\"");
		exp.setVariable(var);

		// �f�t�H���g�ł́A�V���O���N�H�[�e�[�V�����ň͂񂾕�����́A
		// ���ߎ��͐擪1������؂�o���Ă���
		exp.eval();
		System.out.println("�f�t�H���g�F" + var.getMap());

		exp.setOperator(new StringOperator());
		exp.eval();
		System.out.println("�ύX��@�@�F" + var.getMap());
	}

	static class StringOperator extends JavaExOperator {

		@Override
		public Object character(String word, AbstractExpression exp) {
			return word;
		}
	}
}
