package jp.hishidama.eval.sample;

import jp.hishidama.eval.ExpRuleFactory;
import jp.hishidama.eval.Expression;
import jp.hishidama.eval.Rule;
import jp.hishidama.eval.var.MapVariable;

/**
 * �I�u�W�F�N�g�̃����o�[�i�t�B�[���h�E���\�b�h�j�̎g�p��
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >�Ђ�����</a>
 */
public class MemberSample {

	public static void main(String[] args) {
		SampleClass sc = new SampleClass();
		MapVariable<String, SampleClass> map = new MapVariable<String, SampleClass>(
				String.class, SampleClass.class);
		map.put("s", sc);

		Rule rule = ExpRuleFactory.getDefaultRule();
		Expression exp = rule.parse("s.n");
		exp.setVariable(map);
		System.out.println("�t�B�[���h�F" + exp.eval());

		exp = rule.parse("s.get()");
		exp.setVariable(map);
		System.out.println("���\�b�h�@�F" + exp.eval());
	}

	public static class SampleClass {
		public int n = 10;

		public int get() {
			return 12;
		}
	}
}
