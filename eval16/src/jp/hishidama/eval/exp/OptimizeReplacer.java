package jp.hishidama.eval.exp;

import jp.hishidama.eval.repl.ReplaceAdapter;

/**
 * 最適化クラス.
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 * @since eval16
 */
public class OptimizeReplacer extends ReplaceAdapter {

	/**
	 * 定数判断
	 * 
	 * @param x
	 * @return 定数のときtrue
	 */
	protected boolean isConst(AbstractExpression x) {
		return x instanceof NumberExpression || x instanceof StringExpression
				|| x instanceof CharExpression;
	}

	/**
	 * 真偽値取得
	 * 
	 * @param x
	 * @return 真偽値
	 */
	protected boolean isTrue(AbstractExpression x) {
		return x.share.oper.bool(x.eval());
	}

	/**
	 * 定数変換
	 * 
	 * @param exp
	 * @return 定数
	 */
	protected AbstractExpression toConst(AbstractExpression exp) {
		try {
			Object val = exp.eval();
			if (val instanceof String) {
				return StringExpression.create(exp, (String) val);
			}
			if (val instanceof Character) {
				return CharExpression.create(exp, val.toString());
			}
			if (val instanceof Number) {
				return NumberExpression.create(exp, val.toString());
			}
			return exp;
		} catch (Exception e) {
			return exp;
		}
	}

	@Override
	public AbstractExpression replace0(WordExpression exp) {
		if (exp instanceof VariableExpression) {
			return toConst(exp);
		}
		return exp;
	}

	@Override
	public AbstractExpression replace1(Col1Expression exp) {
		if (exp instanceof ParenExpression) {
			return exp.exp;
		}
		if (exp instanceof SignPlusExpression) {
			return exp.exp;
		}
		if (isConst(exp.exp)) {
			return toConst(exp);
		}
		return exp;
	}

	@Override
	public AbstractExpression replace2(Col2Expression exp) {
		boolean const_l = isConst(exp.expl);
		boolean const_r = isConst(exp.expr);
		if (const_l && const_r) {
			return toConst(exp);
		}

		return exp;
	}

	@Override
	public AbstractExpression replace2(Col2OpeExpression exp) {
		if (exp instanceof ArrayExpression) {
			if (isConst(exp.expr)) {
				return toConst(exp);
			}
			return exp;
		}
		if (exp instanceof FieldExpression) {
			return toConst(exp);
		}

		boolean const_l = isConst(exp.expl);
		if (exp instanceof AndExpression) {
			if (const_l) {
				if (isTrue(exp.expl)) {
					return exp.expr;
				} else {
					return exp.expl;
				}
			}
			return exp;
		}
		if (exp instanceof OrExpression) {
			if (const_l) {
				if (isTrue(exp.expl)) {
					return exp.expl;
				} else {
					return exp.expr;
				}
			}
			return exp;
		}
		if (exp instanceof CommaExpression) {
			if (const_l) {
				return exp.expr;
			}
			return exp;
		}

		return exp;
	}

	@Override
	public AbstractExpression replace3(Col3Expression exp) {
		if (isConst(exp.exp1)) {
			if (isTrue(exp.exp1)) {
				return exp.exp2;
			} else {
				return exp.exp3;
			}
		}
		return exp;
	}
}
