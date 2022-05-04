package jp.hishidama.eval.oper;

import jp.hishidama.eval.EvalException;
import jp.hishidama.eval.exp.AbstractExpression;
import jp.hishidama.util.NumberUtil;

/**
 * Integer演算実行クラス.
 * <p>
 * int型で演算を行う演算クラス。
 * </p>
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 * @since eval16
 */
public class IntOperator implements Operator {

	/** 真偽値：真. */
	public static final int TRUE = 1;

	/** 真偽値：偽. */
	public static final int FALSE = 0;

	protected int n(Object obj) {
		if (obj == null) {
			return 0;
		}
		return ((Number) obj).intValue();
	}

	@Override
	public Object power(Object x, Object y) {
		return power(n(x), n(y));
	}

	protected int power(int x, int y) {
		return (int) Math.pow(x, y);
	}

	@Override
	public Object signPlus(Object x) {
		return signPlus(n(x));
	}

	protected int signPlus(int x) {
		return x;
	}

	@Override
	public Object signMinus(Object x) {
		return signMinus(n(x));
	}

	protected int signMinus(int x) {
		return -x;
	}

	@Override
	public Object plus(Object x, Object y) {
		return plus(n(x), n(y));
	}

	protected int plus(int x, int y) {
		return x + y;
	}

	@Override
	public Object minus(Object x, Object y) {
		return minus(n(x), n(y));
	}

	protected int minus(int x, int y) {
		return x - y;
	}

	@Override
	public Object mult(Object x, Object y) {
		return mult(n(x), n(y));
	}

	protected int mult(int x, int y) {
		return x * y;
	}

	@Override
	public Object div(Object x, Object y) {
		return div(n(x), n(y));
	}

	protected int div(int x, int y) {
		return x / y;
	}

	@Override
	public Object mod(Object x, Object y) {
		return mod(n(x), n(y));
	}

	protected int mod(int x, int y) {
		return x % y;
	}

	@Override
	public Object bitNot(Object x) {
		return bitNot(n(x));
	}

	protected int bitNot(int x) {
		return ~x;
	}

	@Override
	public Object shiftLeft(Object x, Object y) {
		return shiftLeft(n(x), n(y));
	}

	protected int shiftLeft(int x, int y) {
		return x << y;
	}

	@Override
	public Object shiftRight(Object x, Object y) {
		return shiftRight(n(x), n(y));
	}

	protected int shiftRight(int x, int y) {
		return x >> y;
	}

	@Override
	public Object shiftRightLogical(Object x, Object y) {
		return shiftRightLogical(n(x), n(y));
	}

	protected int shiftRightLogical(int x, int y) {
		return x >>> y;
	}

	@Override
	public Object bitAnd(Object x, Object y) {
		return bitAnd(n(x), n(y));
	}

	protected int bitAnd(int x, int y) {
		return x & y;
	}

	@Override
	public Object bitOr(Object x, Object y) {
		return bitOr(n(x), n(y));
	}

	protected int bitOr(int x, int y) {
		return x | y;
	}

	@Override
	public Object bitXor(Object x, Object y) {
		return bitXor(n(x), n(y));
	}

	protected int bitXor(int x, int y) {
		return x ^ y;
	}

	@Override
	public Object not(Object x) {
		return not(n(x));
	}

	protected int not(int x) {
		return (x == 0) ? TRUE : FALSE;
	}

	@Override
	public Object equal(Object x, Object y) {
		return equal(n(x), n(y));
	}

	protected int equal(int x, int y) {
		return (x == y) ? TRUE : FALSE;
	}

	@Override
	public Object notEqual(Object x, Object y) {
		return notEqual(n(x), n(y));
	}

	protected int notEqual(int x, int y) {
		return (x != y) ? TRUE : FALSE;
	}

	@Override
	public Object lessThan(Object x, Object y) {
		return lessThan(n(x), n(y));
	}

	protected int lessThan(int x, int y) {
		return (x < y) ? TRUE : FALSE;
	}

	@Override
	public Object lessEqual(Object x, Object y) {
		return lessEqual(n(x), n(y));
	}

	protected int lessEqual(int x, int y) {
		return (x <= y) ? TRUE : FALSE;
	}

	@Override
	public Object greaterThan(Object x, Object y) {
		return greaterThan(n(x), n(y));
	}

	protected int greaterThan(int x, int y) {
		return (x > y) ? TRUE : FALSE;
	}

	@Override
	public Object greaterEqual(Object x, Object y) {
		return greaterEqual(n(x), n(y));
	}

	protected int greaterEqual(int x, int y) {
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

	protected int inc(int x, int inc) {
		return x + inc;
	}

	@Override
	public Object character(String word, AbstractExpression exp) {
		return (int) word.charAt(0);
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

	protected int toNumber(String word, AbstractExpression exp) {
		try {
			return Integer.valueOf((int) NumberUtil.parseLong(word));
		} catch (Exception e) {
		}
		try {
			return Integer.valueOf(word);
		} catch (Exception e) {
		}
		try {
			return (int) Double.parseDouble(word);
		} catch (Exception e) {
			throw new EvalException(EvalException.EXP_NOT_NUMBER, exp, e);
		}
	}
}
