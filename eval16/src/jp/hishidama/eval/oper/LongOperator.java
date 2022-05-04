package jp.hishidama.eval.oper;

import jp.hishidama.eval.EvalException;
import jp.hishidama.eval.exp.AbstractExpression;
import jp.hishidama.util.NumberUtil;

/**
 * Long演算実行クラス.
 * <p>
 * long型で演算を行う演算クラス。
 * </p>
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 * @since eval16
 */
public class LongOperator implements Operator {

	/** 真偽値：真. */
	public static final int TRUE = 1;

	/** 真偽値：偽. */
	public static final int FALSE = 0;

	protected long n(Object obj) {
		if (obj == null) {
			return 0;
		}
		return ((Number) obj).longValue();
	}

	@Override
	public Object power(Object x, Object y) {
		return power(n(x), n(y));
	}

	protected long power(long x, long y) {
		return (long) Math.pow(x, y);
	}

	@Override
	public Object signPlus(Object x) {
		return signPlus(n(x));
	}

	protected long signPlus(long x) {
		return x;
	}

	@Override
	public Object signMinus(Object x) {
		return signMinus(n(x));
	}

	protected long signMinus(long x) {
		return -x;
	}

	@Override
	public Object plus(Object x, Object y) {
		return plus(n(x), n(y));
	}

	protected long plus(long x, long y) {
		return x + y;
	}

	@Override
	public Object minus(Object x, Object y) {
		return minus(n(x), n(y));
	}

	protected long minus(long x, long y) {
		return x - y;
	}

	@Override
	public Object mult(Object x, Object y) {
		return mult(n(x), n(y));
	}

	protected long mult(long x, long y) {
		return x * y;
	}

	@Override
	public Object div(Object x, Object y) {
		return div(n(x), n(y));
	}

	protected long div(long x, long y) {
		return x / y;
	}

	@Override
	public Object mod(Object x, Object y) {
		return mod(n(x), n(y));
	}

	protected long mod(long x, long y) {
		return x % y;
	}

	@Override
	public Object bitNot(Object x) {
		return bitNot(n(x));
	}

	protected long bitNot(long x) {
		return ~x;
	}

	@Override
	public Object shiftLeft(Object x, Object y) {
		return shiftLeft(n(x), n(y));
	}

	protected long shiftLeft(long x, long y) {
		return x << y;
	}

	@Override
	public Object shiftRight(Object x, Object y) {
		return shiftRight(n(x), n(y));
	}

	protected long shiftRight(long x, long y) {
		return x >> y;
	}

	@Override
	public Object shiftRightLogical(Object x, Object y) {
		return shiftRightLogical(n(x), n(y));
	}

	protected long shiftRightLogical(long x, long y) {
		return x >>> y;
	}

	@Override
	public Object bitAnd(Object x, Object y) {
		return bitAnd(n(x), n(y));
	}

	protected long bitAnd(long x, long y) {
		return x & y;
	}

	@Override
	public Object bitOr(Object x, Object y) {
		return bitOr(n(x), n(y));
	}

	protected long bitOr(long x, long y) {
		return x | y;
	}

	@Override
	public Object bitXor(Object x, Object y) {
		return bitXor(n(x), n(y));
	}

	protected long bitXor(long x, long y) {
		return x ^ y;
	}

	@Override
	public Object not(Object x) {
		return not(n(x));
	}

	protected long not(long x) {
		return (x == 0) ? TRUE : FALSE;
	}

	@Override
	public Object equal(Object x, Object y) {
		return equal(n(x), n(y));
	}

	protected long equal(long x, long y) {
		return (x == y) ? TRUE : FALSE;
	}

	@Override
	public Object notEqual(Object x, Object y) {
		return notEqual(n(x), n(y));
	}

	protected long notEqual(long x, long y) {
		return (x != y) ? TRUE : FALSE;
	}

	@Override
	public Object lessThan(Object x, Object y) {
		return lessThan(n(x), n(y));
	}

	protected long lessThan(long x, long y) {
		return (x < y) ? TRUE : FALSE;
	}

	@Override
	public Object lessEqual(Object x, Object y) {
		return lessEqual(n(x), n(y));
	}

	protected long lessEqual(long x, long y) {
		return (x <= y) ? TRUE : FALSE;
	}

	@Override
	public Object greaterThan(Object x, Object y) {
		return greaterThan(n(x), n(y));
	}

	protected long greaterThan(long x, long y) {
		return (x > y) ? TRUE : FALSE;
	}

	@Override
	public Object greaterEqual(Object x, Object y) {
		return greaterEqual(n(x), n(y));
	}

	protected long greaterEqual(long x, long y) {
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

	protected long inc(long x, int inc) {
		return x + inc;
	}

	@Override
	public Object character(String word, AbstractExpression exp) {
		return (long) word.charAt(0);
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

	protected Long toNumber(String word, AbstractExpression exp) {
		try {
			return Long.valueOf(NumberUtil.parseLong(word));
		} catch (Exception e) {
		}
		try {
			return Long.valueOf(word);
		} catch (Exception e) {
		}
		try {
			return (long) Double.parseDouble(word);
		} catch (Exception e) {
			throw new EvalException(EvalException.EXP_NOT_NUMBER, exp, e);
		}
	}
}
