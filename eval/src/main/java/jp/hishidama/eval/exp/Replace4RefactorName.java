package jp.hishidama.eval.exp;

import jp.hishidama.eval.EvalException;
import jp.hishidama.eval.ref.Refactor;
import jp.hishidama.eval.repl.ReplaceAdapter;

/**
 * リファクタリング（変数名変更用）置換クラス.
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 * @since 2007.02.20
 * @deprecated 試しに作ってみた物なので削除予定
 */
@Deprecated
public class Replace4RefactorName extends ReplaceAdapter {

	protected Refactor ref;

	Replace4RefactorName(Refactor ref) {
		this.ref = ref;
	}

	protected void var(VariableExpression exp) {
		String name = ref.getNewName(null, exp.getWord());
		if (name != null) {
			exp.setWord(name);
		}
	}

	protected void field(FieldExpression exp) {
		AbstractExpression exp1 = exp.expl;
		Object obj = exp1.getVariable();
		if (obj == null) {
			throw new EvalException(EvalException.EXP_NOT_DEF_OBJ, toString(),
					exp1, null);
		}
		AbstractExpression exp2 = exp.expr;
		String name = ref.getNewName(obj, exp2.getWord());
		if (name != null) {
			exp2.setWord(name);
		}
	}

	protected void func(FunctionExpression exp) {
		Object obj = null;
		if (exp.target != null) {
			obj = exp.target.getVariable();
		}
		String name = ref.getNewFuncName(obj, exp.name);
		if (name != null) {
			exp.name = name;
		}
	}

	@Override
	public AbstractExpression replace0(WordExpression exp) {
		if (exp instanceof VariableExpression) {
			var((VariableExpression) exp);
		}
		return exp;
	}

	@Override
	public AbstractExpression replace2(Col2Expression exp) {
		if (exp instanceof FieldExpression) {
			field((FieldExpression) exp);
		}
		return exp;
	}

	@Override
	public AbstractExpression replaceFunc(FunctionExpression exp) {
		func(exp);
		return exp;
	}

	public AbstractExpression replaceVar(AbstractExpression exp) {
		if (exp instanceof VariableExpression) {
			var((VariableExpression) exp);
		} else if (exp instanceof FieldExpression) {
			field((FieldExpression) exp);
		} else if (exp instanceof FunctionExpression) {
			func((FunctionExpression) exp);
		}
		return exp;
	}

}
