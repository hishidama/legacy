package jp.hishidama.eval.example;

import jp.hishidama.eval.ExpRuleFactory;
import jp.hishidama.eval.Expression;
import jp.hishidama.eval.Rule;
import jp.hishidama.eval.exp.AbstractExpression;
import jp.hishidama.eval.oper.JavaExOperator;
import jp.hishidama.eval.var.MapVariable;

/**
 * シングルクォーテーションをダブルクォーテーションと同様に扱う例
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 */
public class QuoteExample {

	public static void main(String[] args) {
		MapVariable<String, Object> var = new MapVariable<String, Object>(
				String.class, Object.class);
		Rule rule = ExpRuleFactory.getDefaultRule();
		Expression exp = rule.parse("v1='ABC', v2=\"DEF\"");
		exp.setVariable(var);

		// デフォルトでは、シングルクォーテーションで囲んだ文字列は、
		// 解釈時は先頭1文字を切り出している
		exp.eval();
		System.out.println("デフォルト：" + var.getMap());

		exp.setOperator(new StringOperator());
		exp.eval();
		System.out.println("変更後　　：" + var.getMap());
	}

	static class StringOperator extends JavaExOperator {

		@Override
		public Object character(String word, AbstractExpression exp) {
			return word;
		}
	}
}
