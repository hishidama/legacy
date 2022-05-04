package jp.hishidama.eval.exp;

import jp.hishidama.eval.EvalException;
import jp.hishidama.eval.ref.Refactor;
import jp.hishidama.eval.srch.SearchAdapter;

/**
 * リファクタリング（変数名変更用）構文解析木探索クラス.
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 * @since 2007.02.20
 */
public class Search4RefactorName extends SearchAdapter {

	protected Refactor ref;

	Search4RefactorName(Refactor ref) {
		this.ref = ref;
	}

	@Override
	public void search0(WordExpression exp) {
		if (exp instanceof VariableExpression) {
			String name = ref.getNewName(null, exp.getWord());
			if (name != null) {
				exp.setWord(name);
			}
		}
	}

	@Override
	public boolean search2_2(Col2Expression exp) {
		if (exp instanceof FieldExpression) {
			AbstractExpression exp1 = exp.expl;
			Object obj = exp1.getVariable();
			if (obj == null) {
				throw new EvalException(EvalException.EXP_NOT_DEF_OBJ,
						toString(), exp1, null);
			}
			AbstractExpression exp2 = exp.expr;
			String name = ref.getNewName(obj, exp2.getWord());
			if (name != null) {
				exp2.setWord(name);
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean searchFunc_2(FunctionExpression exp) {
		Object obj = null;
		if (exp.target != null) {
			obj = exp.target.getVariable();
		}
		String name = ref.getNewFuncName(obj, exp.name);
		if (name != null) {
			exp.name = name;
		}

		return false;
	}

}
