package jp.hishidama.eval.sample;

import jp.hishidama.eval.ExpRuleFactory;
import jp.hishidama.eval.Expression;
import jp.hishidama.eval.Rule;
import jp.hishidama.eval.var.MapVariable;

/**
 * オブジェクトのメンバー（フィールド・メソッド）の使用例
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
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
		System.out.println("フィールド：" + exp.eval());

		exp = rule.parse("s.get()");
		exp.setVariable(map);
		System.out.println("メソッド　：" + exp.eval());
	}

	public static class SampleClass {
		public int n = 10;

		public int get() {
			return 12;
		}
	}
}
