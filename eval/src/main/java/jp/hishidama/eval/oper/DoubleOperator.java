package jp.hishidama.eval.oper;

import jp.hishidama.eval.EvalException;
import jp.hishidama.eval.exp.AbstractExpression;
import jp.hishidama.util.NumberUtil;

/**
 * Double演算実行クラス.
 * <p>
 * double型で演算を行う演算クラス。
 * </p>
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 * @since eval16
 */
public class DoubleOperator implements Operator {

	/** 真偽値：真. */
	public static final int TRUE = 1;

	/** 真偽値：偽. */
	public static final int FALSE = 0;

	protected double n(Object obj) {
		if (obj == null) {
			return 0;
		}
		return ((Number) obj).doubleValue();
	}

	@Override
	public Object power(Object x, Object y) {
		return power(n(x), n(y));
	}

	protected double power(double x, double y) {
		return (double) Math.pow(x, y);
	}

	@Override
	public Object signPlus(Object x) {
		return signPlus(n(x));
	}

	protected double signPlus(double x) {
		return x;
	}

	@Override
	public Object signMinus(Object x) {
		return signMinus(n(x));
	}

	protected double signMinus(double x) {
		return -x;
	}

	@Override
	public Object plus(Object x, Object y) {
		return plus(n(x), n(y));
	}

	protected double plus(double x, double y) {
		return x + y;
	}

	@Override
	public Object minus(Object x, Object y) {
		return minus(n(x), n(y));
	}

	protected double minus(double x, double y) {
		return x - y;
	}

	@Override
	public Object mult(Object x, Object y) {
		return mult(n(x), n(y));
	}

	protected double mult(double x, double y) {
		return x * y;
	}

	@Override
	public Object div(Object x, Object y) {
		return div(n(x), n(y));
	}

	protected double div(double x, double y) {
		return x / y;
	}

	@Override
	public Object mod(Object x, Object y) {
		return mod(n(x), n(y));
	}

	protected double mod(double x, double y) {
		return x % y;
	}

	@Override
	public Object bitNot(Object x) {
		return bitNot(n(x));
	}

	protected double bitNot(double x) {
		return ~(long) x;
	}

	@Override
	public Object shiftLeft(Object x, Object y) {
		return shiftLeft(n(x), n(y));
	}

	protected double shiftLeft(double x, double y) {
		return x * Math.pow(2, y);
	}

	@Override
	public Object shiftRight(Object x, Object y) {
		return shiftRight(n(x), n(y));
	}

	protected double shiftRight(double x, double y) {
		return x / Math.pow(2, y);
	}

	@Override
	public Object shiftRightLogical(Object x, Object y) {
		return shiftRightLogical(n(x), n(y));
	}

	protected double shiftRightLogical(double x, double y) {
		if (x < 0) {
			x = -x;
		}
		return x / Math.pow(2, y);
	}

	@Override
	public Object bitAnd(Object x, Object y) {
		return bitAnd(n(x), n(y));
	}

	protected double bitAnd(double x, double y) {
		return (long) x & (long) y;
	}

	@Override
	public Object bitOr(Object x, Object y) {
		return bitOr(n(x), n(y));
	}

	protected double bitOr(double x, double y) {
		return (long) x | (long) y;
	}

	@Override
	public Object bitXor(Object x, Object y) {
		return bitXor(n(x), n(y));
	}

	protected double bitXor(double x, double y) {
		return (long) x ^ (long) y;
	}

	@Override
	public Object not(Object x) {
		return not(n(x));
	}

	protected double not(double x) {
		return (x == 0) ? TRUE : FALSE;
	}

	@Override
	public Object equal(Object x, Object y) {
		return equal(n(x), n(y));
	}

	protected double equal(double x, double y) {
		return (x == y) ? TRUE : FALSE;
	}

	@Override
	public Object notEqual(Object x, Object y) {
		return notEqual(n(x), n(y));
	}

	protected double notEqual(double x, double y) {
		return (x != y) ? TRUE : FALSE;
	}

	@Override
	public Object lessThan(Object x, Object y) {
		return lessThan(n(x), n(y));
	}

	protected double lessThan(double x, double y) {
		return (x < y) ? TRUE : FALSE;
	}

	@Override
	public Object lessEqual(Object x, Object y) {
		return lessEqual(n(x), n(y));
	}

	protected double lessEqual(double x, double y) {
		return (x <= y) ? TRUE : FALSE;
	}

	@Override
	public Object greaterThan(Object x, Object y) {
		return greaterThan(n(x), n(y));
	}

	protected double greaterThan(double x, double y) {
		return (x > y) ? TRUE : FALSE;
	}

	@Override
	public Object greaterEqual(Object x, Object y) {
		return greaterEqual(n(x), n(y));
	}

	protected double greaterEqual(double x, double y) {
		return (x >= y) ? TRUE : FALSE;
	}

	@Override
	public boolean bool(Object x) {
		return n(x) != 0;
	}

	@Override
	public Object inc(Object x, int inc) {
		return inc(n(x), inc);
	}

	protected double inc(double x, int inc) {
		return x + inc;
	}

	@Override
	public Object character(String word, AbstractExpression exp) {
		return (double) word.charAt(0);
	}

	@Override
	public Object string(String word, AbstractExpression exp) {
		return toNumber(word, exp);
	}

	@Override
	public Object number(String word, AbstractExpression exp) {
		return toNumber(word, exp);
	}

	@Override
	public Object variable(Object value, AbstractExpression exp) {
		if (value == null) {
			return value;
		}
		if (value instanceof Number) {
			return value;
		}
		return toNumber(value.toString(), exp);
	}

	protected Double toNumber(String word, AbstractExpression exp) {
		try {
			return Double.valueOf(word);
		} catch (Exception e) {
		}
		try {
			return Double.valueOf(NumberUtil.parseLong(word));
		} catch (Exception e) {
			throw new EvalException(EvalException.EXP_NOT_NUMBER, exp, e);
		}
	}
}
