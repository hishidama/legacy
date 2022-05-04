package jp.hishidama.eval.example;

import jp.hishidama.eval.ExpRuleFactory;
import jp.hishidama.eval.Expression;
import jp.hishidama.eval.Rule;
import jp.hishidama.eval.ref.RefactorVarName;
import jp.hishidama.eval.var.MapVariable;

/**
 * リファクタリングの例
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 */
public class RefactoringExample {

	public static void main(String[] args) {
		refactorName();
		System.out.println();
		refactorFunc();
	}

	static void refactorName() {
		Rule rule = ExpRuleFactory.getDefaultRule();
		Expression exp = rule.parse("aa+bb+1");

		System.out.println("変更前：" + exp.toString());
		exp.refactorName(new RefactorVarName(null, "bb", "foo")); // 変数名bbをfooに変更
		System.out.println("変更後：" + exp.toString());
	}

	static void refactorFunc() {
		Rule rule = ExpRuleFactory.getDefaultRule();
		Expression exp = rule.parse("a.x /2");

		MapVariable<String, Object> var = new MapVariable<String, Object>();
		var.setValue("a", new Object());
		exp.setVariable(var);

		System.out.println("変更前：" + exp.toString());
		exp
				.refactorFunc(new RefactorVarName(Object.class, "x", "getX()"),
						rule);
		System.out.println("変更後：" + exp.toString());
	}
}
