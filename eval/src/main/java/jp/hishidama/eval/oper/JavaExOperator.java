package jp.hishidama.eval.oper;

import java.math.*;

import jp.hishidama.eval.exp.*;
import jp.hishidama.util.NumberUtil;

/**
 * 拡張Java演算実行クラス.
 * <p>
 * Javaもどきの演算を行う演算クラス。
 * </p>
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 * @since 2007.02.15
 * @version eval16
 */
public class JavaExOperator implements Operator {

	boolean inLong(Object x) {
		if (x instanceof Long) {
			return true;
		}
		if (x instanceof Integer) {
			return true;
		}
		if (x instanceof Short) {
			return true;
		}
		if (x instanceof Byte) {
			return true;
		}
		if (x instanceof BigInteger) {
			return true;
		}
		if (x instanceof BigDecimal) {
			return true;
		}
		return false;
	}

	long l(Object x) {
		return ((Number) x).longValue();
	}

	boolean inDouble(Object x) {
		if (x instanceof Double) {
			return true;
		}
		if (x instanceof Float) {
			return true;
		}
		return false;
	}

	double d(Object x) {
		return ((Number) x).doubleValue();
	}

	Object n(long n, Object x) {
		if (x instanceof Long) {
			return Long.valueOf(n);
		}
		if (x instanceof Double) {
			return Double.valueOf(n);
		}
		if (x instanceof Integer) {
			return Integer.valueOf((int) n);
		}
		if (x instanceof Short) {
			return Short.valueOf((short) n);
		}
		if (x instanceof Byte) {
			return Byte.valueOf((byte) n);
		}
		if (x instanceof Float) {
			return Float.valueOf(n);
		}
		if (x instanceof BigInteger) {
			return BigInteger.valueOf(n);
		}
		if (x instanceof BigDecimal) {
			return BigDecimal.valueOf(n);
		}
		if (x instanceof String) {
			return String.valueOf(n);
		}
		return Long.valueOf(n);
	}

	Object n(long n, Object x, Object y) {
		if (x instanceof Byte || y instanceof Byte) {
			return Byte.valueOf((byte) n);
		}
		if (x instanceof Short || y instanceof Short) {
			return Short.valueOf((short) n);
		}
		if (x instanceof Integer || y instanceof Integer) {
			return Integer.valueOf((int) n);
		}
		if (x instanceof Long || y instanceof Long) {
			return Long.valueOf(n);
		}
		if (x instanceof BigInteger || y instanceof BigInteger) {
			return BigInteger.valueOf(n);
		}
		if (x instanceof BigDecimal || y instanceof BigDecimal) {
			return BigDecimal.valueOf(n);
		}
		if (x instanceof Float || y instanceof Float) {
			return Float.valueOf(n);
		}
		if (x instanceof Double || y instanceof Double) {
			return Double.valueOf(n);
		}
		if (x instanceof String || y instanceof String) {
			return String.valueOf(n);
		}
		return Long.valueOf(n);
	}

	Object n(double n, Object x) {
		if (x instanceof Float) {
			return Float.valueOf((float) n);
		}
		if (x instanceof String) {
			return String.valueOf(n);
		}
		return Double.valueOf(n);
	}

	Object n(double n, Object x, Object y) {
		if (x instanceof Float || y instanceof Float) {
			return Float.valueOf((float) n);
		}
		if (x instanceof Number || y instanceof Number) {
			return Double.valueOf(n);
		}
		if (x instanceof String || y instanceof String) {
			return String.valueOf(n);
		}
		return Double.valueOf(n);
	}

	Object nn(long n, Object x) {
		if (x instanceof BigDecimal) {
			return BigDecimal.valueOf(n);
		}
		if (x instanceof BigInteger) {
			return BigInteger.valueOf(n);
		}
		return Long.valueOf(n);
	}

	Object nn(long n, Object x, Object y) {
		if (x instanceof BigDecimal || y instanceof BigDecimal) {
			return BigDecimal.valueOf(n);
		}
		if (x instanceof BigInteger || y instanceof BigInteger) {
			return BigInteger.valueOf(n);
		}
		return Long.valueOf(n);
	}

	Object nn(double n, Object x) {
		if (inLong(x)) {
			return Long.valueOf((long) n);
		}
		return Double.valueOf(n);
	}

	Object nn(double n, Object x, Object y) {
		if (inLong(x) && inLong(y)) {
			return Long.valueOf((long) n);
		}
		return Double.valueOf(n);
	}

	RuntimeException undefined(String name, Object x) {
		String c = null;
		if (x != null) {
			c = x.getClass().getName();
		}
		return new UnsupportedOperationException("未定義単項演算" + name + "：" + c);
	}

	RuntimeException undefined(String name, Object x, Object y) {
		String c1 = null, c2 = null;
		if (x != null) {
			c1 = x.getClass().getName();
		}
		if (y != null) {
			c2 = y.getClass().getName();
		}
		return new UnsupportedOperationException("未定義二項演算" + name + "：" + c1
				+ " , " + c2);
	}

	@Override
	public Object power(Object x, Object y) {
		if (x == null && y == null) {
			return null;
		}
		return nn(Math.pow(d(x), d(y)), x, y);
	}

	@Override
	public Object signPlus(Object x) {
		return x;
	}

	@Override
	public Object signMinus(Object x) {
		if (x == null) {
			return null;
		}
		if (inLong(x)) {
			return n(-l(x), x);
		}
		if (inDouble(x)) {
			return n(-d(x), x);
		}
		if (x instanceof Boolean) {
			return x; // -0は0だし-1は1なので、0以外をTRUEとすると変更なし
		}

		throw undefined(SignMinusExpression.NAME, x);
	}

	@Override
	public Object plus(Object x, Object y) {
		if (x == null && y == null) {
			return null;
		}
		if (inLong(x) && inLong(y)) {
			return nn(l(x) + l(y), x, y);
		}
		if (inDouble(x) && inDouble(y)) {
			return nn(d(x) + d(y), x, y);
		}
		if (x instanceof String || y instanceof String) {
			return String.valueOf(x) + String.valueOf(y);
		}
		if (x instanceof Character || y instanceof Character) {
			return String.valueOf(x) + String.valueOf(y);
		}
		throw undefined(PlusExpression.NAME, x, y);
	}

	@Override
	public Object minus(Object x, Object y) {
		if (x == null && y == null) {
			return null;
		}
		if (inLong(x) && inLong(y)) {
			return nn(l(x) - l(y), x, y);
		}
		if (inDouble(x) && inDouble(y)) {
			return nn(d(x) - d(y), x, y);
		}
		throw undefined(MinusExpression.NAME, x, y);
	}

	@Override
	public Object mult(Object x, Object y) {
		if (x == null && y == null) {
			return null;
		}
		if (inLong(x) && inLong(y)) {
			return nn(l(x) * l(y), x, y);
		}
		if (inDouble(x) && inDouble(y)) {
			return nn(d(x) * d(y), x, y);
		}

		// 文字列*数値は、文字列を繰り返すことにしてみる
		String s = null;
		int ct = 0;
		boolean str = false;
		if (x instanceof String && y instanceof Number) {
			s = (String) x;
			ct = ((Number) y).intValue();
			str = true;
		} else if (y instanceof String && x instanceof Number) {
			s = (String) y;
			ct = ((Number) x).intValue();
			str = true;
		}
		if (str) {
			StringBuilder sb = new StringBuilder(s.length() * ct);
			for (int i = 0; i < ct; i++) {
				sb.append(s);
			}
			return sb.toString();
		}

		throw undefined(MultExpression.NAME, x, y);
	}

	@Override
	public Object div(Object x, Object y) {
		if (x == null && y == null) {
			return null;
		}
		if (inLong(x) && inLong(y)) {
			return nn(l(x) / l(y), x);
		}
		if (inDouble(x) && inDouble(y)) {
			return nn(d(x) / d(y), x);
		}

		// 文字列/文字列は、文字列を分割することにしてみる
		if (x instanceof String && y instanceof String) {
			String s = (String) x;
			String r = (String) y;
			return s.split(r);
		}

		throw undefined(DivExpression.NAME, x, y);
	}

	@Override
	public Object mod(Object x, Object y) {
		if (x == null && y == null) {
			return null;
		}
		if (inLong(x) && inLong(y)) {
			return nn(l(x) % l(y), x);
		}
		if (inDouble(x) && inDouble(y)) {
			return nn(d(x) % d(y), x);
		}
		throw undefined(ModExpression.NAME, x, y);
	}

	@Override
	public Object bitNot(Object x) {
		if (x == null) {
			return null;
		}
		if (x instanceof Number) {
			return n(~l(x), x);
		}
		throw undefined(BitNotExpression.NAME, x);
	}

	@Override
	public Object shiftLeft(Object x, Object y) {
		if (x == null && y == null) {
			return null;
		}
		if (inLong(x) && inLong(y)) {
			return n(l(x) << l(y), x);
		}
		if (inDouble(x) && inDouble(y)) {
			return n(d(x) * Math.pow(2, d(y)), x);
		}
		throw undefined(ShiftLeftExpression.NAME, x, y);
	}

	@Override
	public Object shiftRight(Object x, Object y) {
		if (x == null && y == null) {
			return null;
		}
		if (inLong(x) && inLong(y)) {
			return n(l(x) >> l(y), x);
		}
		if (inDouble(x) && inDouble(y)) {
			return n(d(x) / Math.pow(2, d(y)), x);
		}
		throw undefined(ShiftRightExpression.NAME, x, y);
	}

	@Override
	public Object shiftRightLogical(Object x, Object y) {
		if (x == null && y == null) {
			return null;
		}
		if (x instanceof Byte && y instanceof Number) {
			return n((l(x) & 0xff) >>> l(y), x);
		}
		if (x instanceof Short && y instanceof Number) {
			return n((l(x) & 0xffff) >>> l(y), x);
		}
		if (x instanceof Integer && y instanceof Number) {
			return n((l(x) & 0xffffffffl) >>> l(y), x);
		}
		if (inLong(x) && y instanceof Number) {
			return n(l(x) >>> l(y), x);
		}
		if (inDouble(x) && y instanceof Number) {
			double t = d(x);
			if (t < 0) {
				t = -t;
			}
			return n(t / Math.pow(2, d(y)), x);
		}
		throw undefined(ShiftRightLogicalExpression.NAME, x, y);
	}

	@Override
	public Object bitAnd(Object x, Object y) {
		if (x == null && y == null) {
			return null;
		}
		if (x instanceof Number && y instanceof Number) {
			return n(l(x) & l(y), x);
		}
		throw undefined(BitAndExpression.NAME, x, y);
	}

	@Override
	public Object bitOr(Object x, Object y) {
		if (x == null && y == null) {
			return null;
		}
		if (x instanceof Number && y instanceof Number) {
			return n(l(x) | l(y), x);
		}
		throw undefined(BitOrExpression.NAME, x, y);
	}

	@Override
	public Object bitXor(Object x, Object y) {
		if (x == null && y == null) {
			return null;
		}
		if (x instanceof Number && y instanceof Number) {
			return n(l(x) ^ l(y), x);
		}
		throw undefined(BitXorExpression.NAME, x, y);
	}

	@Override
	public Object not(Object x) {
		if (x == null) {
			return null;
		}
		if (x instanceof Boolean) {
			return ((Boolean) x).booleanValue() ? Boolean.FALSE : Boolean.TRUE;
		}
		if (x instanceof Number) {
			if (l(x) == 0) {
				return Boolean.TRUE;
			} else {
				return Boolean.FALSE;
			}
		}
		throw undefined(NotExpression.NAME, x);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected int compare(Object x, Object y) {
		if (x == null || y == null) {
			return compareNull(x, y);
		}

		if (inLong(x) && inLong(y)) {
			long c = l(x) - l(y);
			if (c == 0) {
				return 0;
			}
			if (c < 0) {
				return -1;
			} else {
				return 1;
			}
		}
		if (x instanceof Number && y instanceof Number) {
			double n = d(x) - d(y);
			if (n == 0) {
				return 0;
			}
			return (n < 0) ? -1 : +1;
			// return (int) Math.signum(d(x) - d(y));
		}

		if (x instanceof String && !(y instanceof String)) {
			return ((String) x).compareTo(y.toString());
		}
		if (!(x instanceof String) && y instanceof String) {
			return x.toString().compareTo((String) y);
		}

		Class<?> xc = x.getClass();
		Class<?> yc = y.getClass();
		if (xc.isAssignableFrom(yc) && x instanceof Comparable) {
			return ((Comparable) x).compareTo(y);
		}
		if (yc.isAssignableFrom(xc) && y instanceof Comparable) {
			return -((Comparable) y).compareTo(x);
		}
		if (x.equals(y)) {
			return 0;
		}

		throw undefined("compare", x, y);
	}

	/**
	 * 比較.
	 * <p>
	 * xかyのどちらかがnullの場合に呼ばれる。</o>
	 *
	 * @param x
	 * @param y
	 * @return
	 */
	protected int compareNull(Object x, Object y) {
		if (x == null && y == null) {
			return 0;
		}
		if (x == null && y != null) {
			return -1;
		}
		if (x != null && y == null) {
			return 1;
		}
		throw new InternalError();
	}

	@Override
	public Object equal(Object x, Object y) {
		return compare(x, y) == 0 ? Boolean.TRUE : Boolean.FALSE;
	}

	@Override
	public Object notEqual(Object x, Object y) {
		return compare(x, y) != 0 ? Boolean.TRUE : Boolean.FALSE;
	}

	@Override
	public Object lessThan(Object x, Object y) {
		return compare(x, y) < 0 ? Boolean.TRUE : Boolean.FALSE;
	}

	@Override
	public Object lessEqual(Object x, Object y) {
		return compare(x, y) <= 0 ? Boolean.TRUE : Boolean.FALSE;
	}

	@Override
	public Object greaterThan(Object x, Object y) {
		return compare(x, y) > 0 ? Boolean.TRUE : Boolean.FALSE;
	}

	@Override
	public Object greaterEqual(Object x, Object y) {
		return compare(x, y) >= 0 ? Boolean.TRUE : Boolean.FALSE;
	}

	@Override
	public boolean bool(Object x) {
		if (x == null) {
			return false;
		}
		if (x instanceof Boolean) {
			return ((Boolean) x).booleanValue();
		}
		if (x instanceof Number) {
			return ((Number) x).longValue() != 0;
		}
		return Boolean.valueOf(x.toString()).booleanValue();
	}

	@Override
	public Object inc(Object x, int inc) {
		if (x == null) {
			return null;
		}
		if (inLong(x)) {
			return n(l(x) + inc, x);
		}
		if (inDouble(x)) {
			return n(d(x) + inc, x);
		}
		throw undefined("inc" + inc, x);
	}

	@Override
	public Object character(String word, AbstractExpression exp) {
		return Character.valueOf(word.charAt(0));
	}

	@Override
	public Object string(String word, AbstractExpression exp) {
		return word;
	}

	@Override
	public Object number(String word, AbstractExpression exp) {
		try {
			return Long.valueOf(NumberUtil.parseLong(word));
		} catch (Exception e) {
		}
		try {
			return Long.valueOf(word);
		} catch (Exception e) {
		}
		return Double.valueOf(word);
	}

	@Override
	public Object variable(Object value, AbstractExpression exp) {
		return value;
	}
}
