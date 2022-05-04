package jp.hishidama.eval.exp;

import jp.hishidama.eval.EvalException;

/**
 * ”z—ñƒNƒ‰ƒX.
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >‚Ð‚µ‚¾‚Ü</a>
 * @since 2006.10.27
 */
public class ArrayExpression extends Col2OpeExpression {

	public static final String NAME = "array";

	public ArrayExpression() {
		setOperator("[");
		setEndOperator("]");
	}

	@Override
	public String getExpressionName() {
		return NAME;
	}

	protected ArrayExpression(ArrayExpression from, ShareExpValue s) {
		super(from, s);
	}

	@Override
	public AbstractExpression dup(ShareExpValue s) {
		return new ArrayExpression(this, s);
	}

	@Override
	public Object eval() {
		try {
			return share.oper.variable(getVariable(), this);
		} catch (EvalException e) {
			throw e;
		} catch (Exception e) {
			throw new EvalException(EvalException.EXP_NOT_VAR_VALUE, this, e);
		}
	}

	@Override
	protected Object getVariable() {
		Object obj = expl.getVariable();
		Object index = expr.eval();
		try {
			return share.var.getArrayValue(obj, expl.toString(), index, this);
		} catch (EvalException e) {
			throw e;
		} catch (Exception e) {
			throw new EvalException(EvalException.EXP_NOT_ARR_VALUE,
					toString(), this, e);
		}
	}

	@Override
	protected void let(Object val, int pos) {
		Object obj = expl.getVariable();
		Object index = expr.eval();
		try {
			share.var.setArrayValue(obj, expl.toString(), index, val, this);
		} catch (EvalException e) {
			throw e;
		} catch (Exception e) {
			throw new EvalException(EvalException.EXP_NOT_LET_ARR, toString(),
					this, e);
		}
	}

	@Override
	protected AbstractExpression replaceVar() {
		expl = expl.replaceVar();
		expr = expr.replace();
		return share.repl.replaceVar2(this);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(expl.toString());
		sb.append('[');
		sb.append(expr.toString());
		sb.append(']');
		return sb.toString();
	}
}
