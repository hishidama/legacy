package jp.hishidama.eval.exp;

import jp.hishidama.eval.*;
import jp.hishidama.eval.ref.Refactor;
import jp.hishidama.eval.repl.ReplaceAdapter;
import jp.hishidama.eval.rule.ShareRuleValue;

/**
 * リファクタリング（変数値取得を関数に変える）置換クラス.
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 * @since 2007.02.20
 */
public class Replace4RefactorGetter extends ReplaceAdapter {

	protected Refactor ref;

	protected ShareRuleValue rule;

	Replace4RefactorGetter(Refactor ref, Rule rule) {
		this.ref = ref;
		this.rule = (ShareRuleValue) rule;
	}

	protected AbstractExpression var(VariableExpression exp) {
		String name = ref.getNewName(null, exp.getWord());
		if (name == null) {
			return exp;
		}

		return rule.parse(name, exp.share);
	}

	protected AbstractExpression field(FieldExpression exp) {
		AbstractExpression exp1 = exp.expl;
		Object obj = exp1.getVariable();
		if (obj == null) {
			return exp;
		}
		AbstractExpression exp2 = exp.expr;
		String name = ref.getNewName(obj, exp2.getWord());
		if (name == null) {
			return exp;
		}

		exp.expr = rule.parse(name, exp2.share);
		return exp;
	}

	@Override
	public AbstractExpression replace0(WordExpression exp) {
		if (exp instanceof VariableExpression) {
			return var((VariableExpression) exp);
		}
		return exp;
	}

	@Override
	public AbstractExpression replace2(Col2OpeExpression exp) {
		if (exp instanceof FieldExpression) {
			return field((FieldExpression) exp);
		}
		return exp;
	}

}
