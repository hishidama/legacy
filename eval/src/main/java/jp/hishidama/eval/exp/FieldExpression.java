package jp.hishidama.eval.exp;

import jp.hishidama.eval.EvalException;

/**
 * フィールドクラス.
 * <p>
 * クラスのフィールド「obj.field」
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 * @since 2007.02.14
 */
public class FieldExpression extends Col2OpeExpression {

	public static final String NAME = "field";

	public FieldExpression() {
		setOperator(".");
	}

	@Override
	public String getExpressionName() {
		return NAME;
	}

	protected FieldExpression(FieldExpression from, ShareExpValue s) {
		super(from, s);
	}

	@Override
	public AbstractExpression dup(ShareExpValue s) {
		return new FieldExpression(this, s);
	}

	@Override
	public Object eval() {
		try {
			return share.oper.variable(getVariable(), this);
		} catch (EvalException e) {
			throw e;
		} catch (Exception e) {
			throw new EvalException(EvalException.EXP_NOT_FLD_VALUE,
					toString(), this, e);
		}
	}

	@Override
	protected Object getVariable() {
		Object obj = expl.getVariable();
		String word = expr.getWord();
		try {
			return share.var.getFieldValue(obj, expl.toString(), word, this);
		} catch (EvalException e) {
			throw e;
		} catch (Exception e) {
			throw new EvalException(EvalException.EXP_NOT_FLD_VALUE,
					toString(), this, e);
		}
	}

	@Override
	protected void let(Object val, int pos) {
		Object obj = expl.getVariable();
		String word = expr.getWord();
		try {
			share.var.setFieldValue(obj, expl.toString(), word, val, this);
		} catch (EvalException e) {
			throw e;
		} catch (Exception e) {
			throw new EvalException(EvalException.EXP_NOT_LET_FIELD,
					toString(), this, e);
		}
	}

	@Override
	protected AbstractExpression replace() {
		expl = expl.replaceVar();
		// expr = expr.replace();
		return share.repl.replace2(this);
	}

	@Override
	protected AbstractExpression replaceVar() {
		expl = expl.replaceVar();
		// expr = expr.replace();
		return share.repl.replaceVar2(this);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(expl.toString());
		sb.append('.');
		sb.append(expr.toString());
		return sb.toString();
	}
}
