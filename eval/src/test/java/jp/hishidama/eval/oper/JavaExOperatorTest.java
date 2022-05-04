package jp.hishidama.eval.oper;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.Assert.*;

import org.junit.Test;

import jp.hishidama.eval.oper.JavaExOperator;

/**
 * 拡張Java演算テストクラス.
 *
 * @see jp.hishidama.eval.oper.JavaExOperator
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 */
public class JavaExOperatorTest {

	private static final Byte BYTE1 = new Byte((byte) 1);

	private static final Short SHORT1 = new Short((short) 2);

	private static final Integer INT1 = new Integer(3);

	private static final Long LONG1 = new Long(4);

	private static final Float FLOAT1 = new Float(5.1);

	private static final Double DBL1 = new Double(6.1);

	private static final BigInteger BIGI1 = BigInteger.valueOf(7);

	private static final BigDecimal BIGD1 = new BigDecimal(8.2);

	private static final double DELTA = 0.00001;

	private static JavaExOperator t = new JavaExOperator();

	@Test
	public void testInLong() {
		assertFalse(t.inLong(null));
		assertEquals(true, t.inLong(BYTE1));
		assertEquals(true, t.inLong(SHORT1));
		assertEquals(true, t.inLong(INT1));
		assertEquals(true, t.inLong(LONG1));
		assertEquals(false, t.inLong(FLOAT1));
		assertEquals(false, t.inLong(DBL1));
		assertEquals(true, t.inLong(BIGI1));
		assertEquals(true, t.inLong(BIGD1));
	}

	@Test
	public void testL() {
		assertEquals((byte) 0xff, t.l(new Byte((byte) 0xfff)));
		assertEquals(123, t.l(new Long(123)));
	}

	@Test
	public void testInDouble() {
		assertFalse(t.inDouble(null));
		assertEquals(false, t.inDouble(BYTE1));
		assertEquals(false, t.inDouble(SHORT1));
		assertEquals(false, t.inDouble(INT1));
		assertEquals(false, t.inDouble(LONG1));
		assertEquals(true, t.inDouble(FLOAT1));
		assertEquals(true, t.inDouble(DBL1));
		assertEquals(false, t.inDouble(BIGI1));
		assertEquals(false, t.inDouble(BIGD1));
	}

	@Test
	public void testD() {
		assertEquals(123.4, t.d(new Float(123.4)), DELTA);
		assertEquals(123.4, t.d(new Double(123.4)), DELTA);
	}

	@Test
	public void testNLongObject() {
		assertEquals(new Byte((byte) 22), t.n(22, BYTE1));
		assertEquals(new Short((short) 22), t.n(22, SHORT1));
		assertEquals(new Integer(22), t.n(22, INT1));
		assertEquals(new Long(22), t.n(22, LONG1));
		assertEquals(new Float(22), t.n(22, FLOAT1));
		assertEqualsDouble(new Double(22), t.n(22, DBL1));
		assertEquals(BigInteger.valueOf(22), t.n(22, BIGI1));
		assertEquals(BigDecimal.valueOf(22), t.n(22, BIGD1));
		assertEquals("22", t.n(22, ""));
		assertEquals(new Long(22), t.n(22, null));
	}

	@Test
	public void testNLongObjectObject() {
		assertEquals(new Byte((byte) 22), t.n(22, BYTE1, LONG1));
		assertEquals(new Byte((byte) 22), t.n(22, LONG1, BYTE1));
		assertEquals(new Short((short) 22), t.n(22, SHORT1, LONG1));
		assertEquals(new Short((short) 22), t.n(22, LONG1, SHORT1));
		assertEquals(new Integer(22), t.n(22, INT1, LONG1));
		assertEquals(new Integer(22), t.n(22, LONG1, INT1));
		assertEquals(new Long(22), t.n(22, LONG1, BIGI1));
		assertEquals(new Long(22), t.n(22, BIGI1, LONG1));
		assertEquals(new Float(22), t.n(22, FLOAT1, DBL1));
		assertEquals(new Float(22), t.n(22, DBL1, FLOAT1));
		assertEqualsDouble(new Double(22), t.n(22, DBL1, ""));
		assertEqualsDouble(new Double(22), t.n(22, "", DBL1));
		assertEquals(BigInteger.valueOf(22), t.n(22, BIGI1, BIGD1));
		assertEquals(BigInteger.valueOf(22), t.n(22, BIGD1, BIGI1));
		assertEquals(BigDecimal.valueOf(22), t.n(22, BIGD1, FLOAT1));
		assertEquals(BigDecimal.valueOf(22), t.n(22, FLOAT1, BIGD1));
		assertEquals("22", t.n(22, "", ""));
		assertEquals(new Long(22), t.n(22, null, null));
	}

	@Test
	public void testNDoubleObject() {
		assertEqualsFloat(new Float(22.2), t.n(22.2, FLOAT1));
		assertEqualsDouble(new Double(22.2), t.n(22.2, LONG1));
		assertEquals("22.2", t.n(22.2, ""));
		assertEqualsDouble(new Double(22.2), t.n(22.2, null));
	}

	@Test
	public void testNDoubleObjectObject() {
		assertEqualsFloat(new Float(22.2), t.n(22.2, FLOAT1, DBL1));
		assertEqualsFloat(new Float(22.2), t.n(22.2, DBL1, FLOAT1));
		assertEqualsDouble(new Double(22.2), t.n(22.2, LONG1, SHORT1));
		assertEqualsDouble(new Double(22.2), t.n(22.2, SHORT1, LONG1));
		assertEquals("22.2", t.n(22.2, "", ""));
		assertEqualsDouble(new Double(22.2), t.n(22.2, null, null));
	}

	private void assertEqualsException(Exception e, Exception a) {
		assertEquals(e.toString(), a.toString());
	}

	@Test
	public void testUndefinedObject() {
		String msg = "未定義単項演算aa：";
		assertEqualsException(new UnsupportedOperationException(msg + "null"),
				t.undefined("aa", null));
		assertEqualsException(new UnsupportedOperationException(msg
				+ "java.lang.Object"), t.undefined("aa", new Object()));
	}

	@Test
	public void testUndefinedObjectObject() {
		String msg = "未定義二項演算bb：";
		assertEqualsException(new UnsupportedOperationException(msg
				+ "null , null"), t.undefined("bb", null, null));
		assertEqualsException(new UnsupportedOperationException(msg
				+ "java.lang.Object , java.lang.String"), t.undefined("bb",
				new Object(), ""));
	}

	@Test
	public void testPower() {
		assertNull(t.power(null, null));
		assertEquals(new Long(121), t.power(new Byte((byte) 11), new Short(
				(short) 2)));
		assertEquals(new Long(121), t.power(new Short((short) 11), new Integer(
				2)));
		assertEquals(new Long(121), t.power(new Integer(11), new Long(2)));
		assertEquals(new Long(121), t
				.power(new Long(11), BigInteger.valueOf(2)));
		assertEquals(new Long(121), t.power(BigInteger.valueOf(11), BigDecimal
				.valueOf(2)));
		assertEquals(new Long(121), t.power(BigDecimal.valueOf(11), BigDecimal
				.valueOf(2)));
		assertEqualsDouble(new Double(1.21), t.power(new Float(1.1),
				new Double(2)));
		assertEqualsDouble(new Double(1.21), t.power(new Double(1.1),
				new Double(2)));
	}

	@Test
	public void testSignPlus() {
		assertNull(t.signPlus(null));
		assertSame(BYTE1, t.signPlus(BYTE1));
		assertSame(SHORT1, t.signPlus(SHORT1));
		assertSame(INT1, t.signPlus(INT1));
		assertSame(LONG1, t.signPlus(LONG1));
		assertSame(FLOAT1, t.signPlus(FLOAT1));
		assertSame(DBL1, t.signPlus(DBL1));
		assertSame(BIGI1, t.signPlus(BIGI1));
		assertSame(BIGD1, t.signPlus(BIGD1));
		assertEquals("abc", t.signPlus("abc"));
	}

	@Test
	public void testSignMinus() {
		assertNull(t.signMinus(null));
		assertEquals(new Byte((byte) -1), t.signMinus(new Byte((byte) 1)));
		assertEquals(new Short((short) -1), t.signMinus(new Short((short) 1)));
		assertEquals(new Integer(-1), t.signMinus(new Integer(1)));
		assertEquals(new Long(-1), t.signMinus(new Long(1)));
		assertEquals(new Float(-1.1), t.signMinus(new Float(1.1)));
		assertEqualsDouble(new Double(-1.1), t.signMinus(new Double(1.1)));
		assertEquals(BigInteger.valueOf(-1), t.signMinus(BigInteger.valueOf(1)));
		assertEquals(BigDecimal.valueOf(-1), t.signMinus(BigDecimal.valueOf(1)));
		try {
			t.signMinus("abc");
		} catch (RuntimeException e) {
			return;
		}
		fail();
	}

	private void assertEqualsFloat(Float e, Object a) {
		Float n = (Float) a;
		assertEquals(e.doubleValue(), n.doubleValue(), DELTA);
	}

	private void assertEqualsDouble(Double e, Object a) {
		Double n = (Double) a;
		assertEquals(e.doubleValue(), n.doubleValue(), DELTA);
	}

	@Test
	public void testPlus() {
		assertNull(t.plus(null, null));
		assertEquals(new Long(3), t.plus(new Byte((byte) 1), new Short(
				(short) 2)));
		assertEquals(new Long(3), t.plus(new Short((short) 1), new Integer(2)));
		assertEquals(new Long(3), t.plus(new Integer(1), new Long(2)));
		assertEquals(BigInteger.valueOf(3), t.plus(new Long(1), BigInteger
				.valueOf(2)));
		assertEquals(BigDecimal.valueOf(3), t.plus(BigInteger.valueOf(1),
				BigDecimal.valueOf(2)));
		assertEqualsDouble(new Double(3.3), t.plus(new Float(1.1), new Double(
				2.2)));
		assertEqualsDouble(new Double(3.3), t.plus(new Double(1.1), new Double(
				2.2)));
		assertEquals("abcnull", t.plus("abc", null));
	}

	@Test
	public void testMinus() {
		assertNull(t.minus(null, null));
		assertEquals(new Long(9), t.minus(new Byte((byte) 11), new Short(
				(short) 2)));
		assertEquals(new Long(9), t
				.minus(new Short((short) 11), new Integer(2)));
		assertEquals(new Long(9), t.minus(new Integer(11), new Long(2)));
		assertEquals(BigInteger.valueOf(9), t.minus(new Long(11), BigInteger
				.valueOf(2)));
		assertEquals(BigDecimal.valueOf(9), t.minus(BigInteger.valueOf(11),
				BigDecimal.valueOf(2)));
		assertEqualsDouble(new Double(8.9), t.minus(new Float(11.1),
				new Double(2.2)));
		assertEqualsDouble(new Double(8.9), t.minus(new Double(11.1),
				new Double(2.2)));
	}

	@Test
	public void testMult() {
		assertNull(t.mult(null, null));
		assertEquals(new Long(22), t.mult(new Byte((byte) 11), new Short(
				(short) 2)));
		assertEquals(new Long(22), t
				.mult(new Short((short) 11), new Integer(2)));
		assertEquals(new Long(22), t.mult(new Integer(11), new Long(2)));
		assertEquals(BigInteger.valueOf(22), t.mult(new Long(11), BigInteger
				.valueOf(2)));
		assertEquals(BigDecimal.valueOf(22), t.mult(BigInteger.valueOf(11),
				BigDecimal.valueOf(2)));
		assertEqualsDouble(new Double(24.42), t.mult(new Float(11.1),
				new Double(2.2)));
		assertEqualsDouble(new Double(24.42), t.mult(new Double(11.1),
				new Double(2.2)));

		// 文字列*数値は文字列を繰り返す
		assertEquals("abcabcabc", t.mult("abc", 3));
		assertEquals("abcabcabc", t.mult(3, "abc"));
	}

	@Test
	public void testDiv() {
		assertNull(t.div(null, null));
		assertEquals(new Long(5), t.div(new Byte((byte) 11), new Short(
				(short) 2)));
		assertEquals(new Long(5), t.div(new Short((short) 11), new Integer(2)));
		assertEquals(new Long(5), t.div(new Integer(11), new Long(2)));
		assertEquals(new Long(5), t.div(new Long(11), BigInteger.valueOf(2)));
		assertEquals(BigInteger.valueOf(5), t.div(BigInteger.valueOf(11),
				BigDecimal.valueOf(2)));
		assertEquals(BigDecimal.valueOf(5), t.div(BigDecimal.valueOf(11),
				BigDecimal.valueOf(2)));
		assertEqualsDouble(new Double(5.55), t.div(new Float(11.1), new Double(
				2)));
		assertEqualsDouble(new Double(5.55), t.div(new Double(11.1),
				new Double(2)));

		// 文字列/文字列はsplit
		assertArrayEquals(new String[] { "a", "b", "c" }, (String[]) t.div(
				"a,b,c", ","));
	}

	@Test
	public void testMod() {
		assertNull(t.mod(null, null));
		assertEquals(new Long(2), t.mod(new Byte((byte) 11), new Short(
				(short) 3)));
		assertEquals(new Long(2), t.mod(new Short((short) 11), new Integer(3)));
		assertEquals(new Long(2), t.mod(new Integer(11), new Long(3)));
		assertEquals(new Long(2), t.mod(new Long(11), BigInteger.valueOf(3)));
		assertEquals(BigInteger.valueOf(2), t.mod(BigInteger.valueOf(11),
				BigDecimal.valueOf(3)));
		assertEquals(BigDecimal.valueOf(2), t.mod(BigDecimal.valueOf(11),
				BigDecimal.valueOf(3)));
		assertEqualsDouble(new Double(1.1), t.mod(new Float(11.1),
				new Double(2)));
		assertEqualsDouble(new Double(1.1), t.mod(new Double(11.1), new Double(
				2)));
	}

	@Test
	public void testBitNot() {
		assertNull(t.bitNot(null));
		assertEquals(new Byte((byte) ~11), t.bitNot(new Byte((byte) 11)));
		assertEquals(new Short((short) ~11), t.bitNot(new Short((short) 11)));
		assertEquals(new Integer(~11), t.bitNot(new Integer(11)));
		assertEquals(new Long(~11), t.bitNot(new Long(11)));
		assertEquals(BigInteger.valueOf(~11), t.bitNot(BigInteger.valueOf(11)));
		assertEquals(BigDecimal.valueOf(~11), t.bitNot(BigDecimal.valueOf(11)));
		assertEqualsFloat(new Float(~11), t.bitNot(new Float(11)));
		assertEqualsDouble(new Double(~11), t.bitNot(new Double(11)));
	}

	@Test
	public void testShiftLeft() {
		assertNull(t.shiftLeft(null, null));
		assertEquals(new Byte((byte) 88), t.shiftLeft(new Byte((byte) 11),
				new Short((short) 3)));
		assertEquals(new Short((short) 88), t.shiftLeft(new Short((short) 11),
				new Integer(3)));
		assertEquals(new Integer(88), t.shiftLeft(new Integer(11), new Long(3)));
		assertEquals(new Long(88), t.shiftLeft(new Long(11), BigInteger
				.valueOf(3)));
		assertEquals(BigInteger.valueOf(88), t.shiftLeft(
				BigInteger.valueOf(11), BigDecimal.valueOf(3)));
		assertEquals(BigDecimal.valueOf(88), t.shiftLeft(
				BigDecimal.valueOf(11), BigDecimal.valueOf(3)));
		assertEqualsFloat(new Float(88.8), t.shiftLeft(new Float(11.1),
				new Double(3)));
		assertEqualsDouble(new Double(88.8), t.shiftLeft(new Double(11.1),
				new Double(3)));
	}

	@Test
	public void testShiftRight() {
		assertNull(t.shiftRight(null, null));
		assertEquals(new Byte((byte) -2), t.shiftRight(new Byte((byte) -16),
				new Short((short) 3)));
		assertEquals(new Short((short) -2), t.shiftRight(
				new Short((short) -16), new Integer(3)));
		assertEquals(new Integer(-2), t.shiftRight(new Integer(-16),
				new Long(3)));
		assertEquals(new Long(-2), t.shiftRight(new Long(-16), BigInteger
				.valueOf(3)));
		assertEquals(BigInteger.valueOf(-2), t.shiftRight(BigInteger
				.valueOf(-16), BigDecimal.valueOf(3)));
		assertEquals(BigDecimal.valueOf(-2), t.shiftRight(BigDecimal
				.valueOf(-16), BigDecimal.valueOf(3)));
		assertEqualsFloat(new Float(-2.1), t.shiftRight(new Float(-16.8),
				new Double(3)));
		assertEqualsDouble(new Double(-2.1), t.shiftRight(new Double(-16.8),
				new Double(3)));
	}

	@Test
	public void testShiftRightLogical() {
		assertNull(t.shiftRightLogical(null, null));
		assertEquals(new Byte((byte) 0x1f), t.shiftRightLogical(new Byte(
				(byte) -1), new Short((short) 3)));
		assertEquals(new Short((short) 0x1fff), t.shiftRightLogical(new Short(
				(short) -1), new Integer(3)));
		assertEquals(new Integer(0x1fffffff), t.shiftRightLogical(new Integer(
				-1), new Long(3)));
		assertEquals(new Long(-1l >>> 3), t.shiftRightLogical(new Long(-1),
				BigInteger.valueOf(3)));
		assertEquals(BigInteger.valueOf(-1l >>> 3), t.shiftRightLogical(
				BigInteger.valueOf(-1), BigDecimal.valueOf(3)));
		assertEquals(BigDecimal.valueOf(-1l >>> 3), t.shiftRightLogical(
				BigDecimal.valueOf(-1), BigDecimal.valueOf(3)));
		assertEqualsFloat(new Float(2.1), t.shiftRightLogical(new Float(-16.8),
				new Double(3)));
		assertEqualsDouble(new Double(2.1), t.shiftRightLogical(new Double(
				-16.8), new Double(3)));
	}

	@Test
	public void testBitAnd() {
		assertNull(t.bitAnd(null, null));
		assertEquals(new Byte((byte) 2), t.bitAnd(new Byte((byte) 6),
				new Short((short) 3)));
		assertEquals(new Short((short) 2), t.bitAnd(new Short((short) 6),
				new Integer(3)));
		assertEquals(new Integer(2), t.bitAnd(new Integer(6), new Long(3)));
		assertEquals(new Long(2), t.bitAnd(new Long(6), BigInteger.valueOf(3)));
		assertEquals(BigInteger.valueOf(2), t.bitAnd(BigInteger.valueOf(6),
				BigDecimal.valueOf(3)));
		assertEquals(BigDecimal.valueOf(2), t.bitAnd(BigDecimal.valueOf(6),
				BigDecimal.valueOf(3)));
		assertEqualsFloat(new Float(2), t.bitAnd(new Float(6.9),
				new Double(3.9)));
		assertEqualsDouble(new Double(2), t.bitAnd(new Double(6.9), new Double(
				3.9)));
	}

	@Test
	public void testBitOr() {
		assertNull(t.bitOr(null, null));
		assertEquals(new Byte((byte) 7), t.bitOr(new Byte((byte) 6), new Short(
				(short) 3)));
		assertEquals(new Short((short) 7), t.bitOr(new Short((short) 6),
				new Integer(3)));
		assertEquals(new Integer(7), t.bitOr(new Integer(6), new Long(3)));
		assertEquals(new Long(7), t.bitOr(new Long(6), BigInteger.valueOf(3)));
		assertEquals(BigInteger.valueOf(7), t.bitOr(BigInteger.valueOf(6),
				BigDecimal.valueOf(3)));
		assertEquals(BigDecimal.valueOf(7), t.bitOr(BigDecimal.valueOf(6),
				BigDecimal.valueOf(3)));
		assertEqualsFloat(new Float(7), t
				.bitOr(new Float(6.9), new Double(3.9)));
		assertEqualsDouble(new Double(7), t.bitOr(new Double(6.9), new Double(
				3.9)));
	}

	@Test
	public void testBitXor() {
		assertNull(t.bitXor(null, null));
		assertEquals(new Byte((byte) 5), t.bitXor(new Byte((byte) 6),
				new Short((short) 3)));
		assertEquals(new Short((short) 5), t.bitXor(new Short((short) 6),
				new Integer(3)));
		assertEquals(new Integer(5), t.bitXor(new Integer(6), new Long(3)));
		assertEquals(new Long(5), t.bitXor(new Long(6), BigInteger.valueOf(3)));
		assertEquals(BigInteger.valueOf(5), t.bitXor(BigInteger.valueOf(6),
				BigDecimal.valueOf(3)));
		assertEquals(BigDecimal.valueOf(5), t.bitXor(BigDecimal.valueOf(6),
				BigDecimal.valueOf(3)));
		assertEqualsFloat(new Float(5), t.bitXor(new Float(6.9),
				new Double(3.9)));
		assertEqualsDouble(new Double(5), t.bitXor(new Double(6.9), new Double(
				3.9)));
	}

	@Test
	public void testNot() {
		assertNull(t.not(null));
		assertEquals(Boolean.TRUE, t.not(Boolean.FALSE));
		assertEquals(Boolean.TRUE, t.not(new Byte((byte) 0)));
		assertEquals(Boolean.TRUE, t.not(new Short((short) 0)));
		assertEquals(Boolean.TRUE, t.not(new Integer(0)));
		assertEquals(Boolean.TRUE, t.not(new Long(0)));
		assertEquals(Boolean.TRUE, t.not(BigInteger.valueOf(0)));
		assertEquals(Boolean.TRUE, t.not(BigDecimal.valueOf(0)));
		assertEquals(Boolean.TRUE, t.not(new Float(0)));
		assertEquals(Boolean.TRUE, t.not(new Double(0)));
		assertEquals(Boolean.FALSE, t.not(Boolean.TRUE));
		assertEquals(Boolean.FALSE, t.not(new Byte((byte) 1)));
		assertEquals(Boolean.FALSE, t.not(new Short((short) 1)));
		assertEquals(Boolean.FALSE, t.not(new Integer(1)));
		assertEquals(Boolean.FALSE, t.not(new Long(1)));
		assertEquals(Boolean.FALSE, t.not(BigInteger.valueOf(1)));
		assertEquals(Boolean.FALSE, t.not(BigDecimal.valueOf(1)));
		assertEquals(Boolean.FALSE, t.not(new Float(1)));
		assertEquals(Boolean.FALSE, t.not(new Double(1)));
	}

	@Test
	public void testEqual() {
		Boolean l = Boolean.FALSE;
		Boolean e = Boolean.TRUE;
		Boolean g = Boolean.FALSE;
		assertEquals(l, t.equal(null, new Object()));
		assertEquals(e, t.equal(null, null));
		assertEquals(g, t.equal(new Object(), null));
		assertEquals(l, t.equal(new Byte((byte) 6), new Short((short) 8)));
		assertEquals(e, t.equal(new Byte((byte) 7), new Short((short) 7)));
		assertEquals(g, t.equal(new Byte((byte) 8), new Short((short) 6)));
		assertEquals(l, t.equal(new Short((short) 6), new Integer(8)));
		assertEquals(e, t.equal(new Short((short) 7), new Integer(7)));
		assertEquals(g, t.equal(new Short((short) 8), new Integer(6)));
		assertEquals(l, t.equal(new Integer(6), new Long(8)));
		assertEquals(e, t.equal(new Integer(7), new Long(7)));
		assertEquals(g, t.equal(new Integer(8), new Long(6)));
		assertEquals(l, t.equal(new Long(6), BigInteger.valueOf(8)));
		assertEquals(e, t.equal(new Long(7), BigInteger.valueOf(7)));
		assertEquals(g, t.equal(new Long(8), BigInteger.valueOf(6)));
		assertEquals(l, t.equal(BigInteger.valueOf(6), BigDecimal.valueOf(8)));
		assertEquals(e, t.equal(BigInteger.valueOf(7), BigDecimal.valueOf(7)));
		assertEquals(g, t.equal(BigInteger.valueOf(8), BigDecimal.valueOf(6)));
		assertEquals(l, t.equal(BigDecimal.valueOf(6), BigDecimal.valueOf(8)));
		assertEquals(e, t.equal(BigDecimal.valueOf(7), BigDecimal.valueOf(7)));
		assertEquals(g, t.equal(BigDecimal.valueOf(8), BigDecimal.valueOf(6)));
		assertEquals(l, t.equal(new Float(6), new Double(8)));
		assertEquals(e, t.equal(new Float(7), new Double(7)));
		assertEquals(g, t.equal(new Float(8), new Double(6)));
		assertEquals(l, t.equal(new Double(6), new Double(8)));
		assertEquals(e, t.equal(new Double(7), new Double(7)));
		assertEquals(g, t.equal(new Double(8), new Double(6)));
		assertEquals(l, t.equal("123", "ABC"));
		assertEquals(e, t.equal("abc", "abc"));
		assertEquals(g, t.equal("ABC", "123"));
	}

	@Test
	public void testNotEqual() {
		Boolean l = Boolean.TRUE;
		Boolean e = Boolean.FALSE;
		Boolean g = Boolean.TRUE;
		assertEquals(l, t.notEqual(null, new Object()));
		assertEquals(e, t.notEqual(null, null));
		assertEquals(g, t.notEqual(new Object(), null));
		assertEquals(l, t.notEqual(new Byte((byte) 6), new Short((short) 8)));
		assertEquals(e, t.notEqual(new Byte((byte) 7), new Short((short) 7)));
		assertEquals(g, t.notEqual(new Byte((byte) 8), new Short((short) 6)));
		assertEquals(l, t.notEqual(new Short((short) 6), new Integer(8)));
		assertEquals(e, t.notEqual(new Short((short) 7), new Integer(7)));
		assertEquals(g, t.notEqual(new Short((short) 8), new Integer(6)));
		assertEquals(l, t.notEqual(new Integer(6), new Long(8)));
		assertEquals(e, t.notEqual(new Integer(7), new Long(7)));
		assertEquals(g, t.notEqual(new Integer(8), new Long(6)));
		assertEquals(l, t.notEqual(new Long(6), BigInteger.valueOf(8)));
		assertEquals(e, t.notEqual(new Long(7), BigInteger.valueOf(7)));
		assertEquals(g, t.notEqual(new Long(8), BigInteger.valueOf(6)));
		assertEquals(l, t
				.notEqual(BigInteger.valueOf(6), BigDecimal.valueOf(8)));
		assertEquals(e, t
				.notEqual(BigInteger.valueOf(7), BigDecimal.valueOf(7)));
		assertEquals(g, t
				.notEqual(BigInteger.valueOf(8), BigDecimal.valueOf(6)));
		assertEquals(l, t
				.notEqual(BigDecimal.valueOf(6), BigDecimal.valueOf(8)));
		assertEquals(e, t
				.notEqual(BigDecimal.valueOf(7), BigDecimal.valueOf(7)));
		assertEquals(g, t
				.notEqual(BigDecimal.valueOf(8), BigDecimal.valueOf(6)));
		assertEquals(l, t.notEqual(new Float(6), new Double(8)));
		assertEquals(e, t.notEqual(new Float(7), new Double(7)));
		assertEquals(g, t.notEqual(new Float(8), new Double(6)));
		assertEquals(l, t.notEqual(new Double(6), new Double(8)));
		assertEquals(e, t.notEqual(new Double(7), new Double(7)));
		assertEquals(g, t.notEqual(new Double(8), new Double(6)));
		assertEquals(l, t.notEqual("123", "ABC"));
		assertEquals(e, t.notEqual("abc", "abc"));
		assertEquals(g, t.notEqual("ABC", "123"));
	}

	@Test
	public void testLessThan() {
		Boolean l = Boolean.TRUE;
		Boolean e = Boolean.FALSE;
		Boolean g = Boolean.FALSE;
		assertEquals(l, t.lessThan(null, new Object()));
		assertEquals(e, t.lessThan(null, null));
		assertEquals(g, t.lessThan(new Object(), null));
		assertEquals(l, t.lessThan(new Byte((byte) 6), new Short((short) 8)));
		assertEquals(e, t.lessThan(new Byte((byte) 7), new Short((short) 7)));
		assertEquals(g, t.lessThan(new Byte((byte) 8), new Short((short) 6)));
		assertEquals(l, t.lessThan(new Short((short) 6), new Integer(8)));
		assertEquals(e, t.lessThan(new Short((short) 7), new Integer(7)));
		assertEquals(g, t.lessThan(new Short((short) 8), new Integer(6)));
		assertEquals(l, t.lessThan(new Integer(6), new Long(8)));
		assertEquals(e, t.lessThan(new Integer(7), new Long(7)));
		assertEquals(g, t.lessThan(new Integer(8), new Long(6)));
		assertEquals(l, t.lessThan(new Long(6), BigInteger.valueOf(8)));
		assertEquals(e, t.lessThan(new Long(7), BigInteger.valueOf(7)));
		assertEquals(g, t.lessThan(new Long(8), BigInteger.valueOf(6)));
		assertEquals(l, t
				.lessThan(BigInteger.valueOf(6), BigDecimal.valueOf(8)));
		assertEquals(e, t
				.lessThan(BigInteger.valueOf(7), BigDecimal.valueOf(7)));
		assertEquals(g, t
				.lessThan(BigInteger.valueOf(8), BigDecimal.valueOf(6)));
		assertEquals(l, t
				.lessThan(BigDecimal.valueOf(6), BigDecimal.valueOf(8)));
		assertEquals(e, t
				.lessThan(BigDecimal.valueOf(7), BigDecimal.valueOf(7)));
		assertEquals(g, t
				.lessThan(BigDecimal.valueOf(8), BigDecimal.valueOf(6)));
		assertEquals(l, t.lessThan(new Float(6), new Double(8)));
		assertEquals(e, t.lessThan(new Float(7), new Double(7)));
		assertEquals(g, t.lessThan(new Float(8), new Double(6)));
		assertEquals(l, t.lessThan(new Double(6), new Double(8)));
		assertEquals(e, t.lessThan(new Double(7), new Double(7)));
		assertEquals(g, t.lessThan(new Double(8), new Double(6)));
		assertEquals(l, t.lessThan("123", "ABC"));
		assertEquals(e, t.lessThan("abc", "abc"));
		assertEquals(g, t.lessThan("ABC", "123"));
	}

	@Test
	public void testLessEqual() {
		Boolean l = Boolean.TRUE;
		Boolean e = Boolean.TRUE;
		Boolean g = Boolean.FALSE;
		assertEquals(l, t.lessEqual(null, new Object()));
		assertEquals(e, t.lessEqual(null, null));
		assertEquals(g, t.lessEqual(new Object(), null));
		assertEquals(l, t.lessEqual(new Byte((byte) 6), new Short((short) 8)));
		assertEquals(e, t.lessEqual(new Byte((byte) 7), new Short((short) 7)));
		assertEquals(g, t.lessEqual(new Byte((byte) 8), new Short((short) 6)));
		assertEquals(l, t.lessEqual(new Short((short) 6), new Integer(8)));
		assertEquals(e, t.lessEqual(new Short((short) 7), new Integer(7)));
		assertEquals(g, t.lessEqual(new Short((short) 8), new Integer(6)));
		assertEquals(l, t.lessEqual(new Integer(6), new Long(8)));
		assertEquals(e, t.lessEqual(new Integer(7), new Long(7)));
		assertEquals(g, t.lessEqual(new Integer(8), new Long(6)));
		assertEquals(l, t.lessEqual(new Long(6), BigInteger.valueOf(8)));
		assertEquals(e, t.lessEqual(new Long(7), BigInteger.valueOf(7)));
		assertEquals(g, t.lessEqual(new Long(8), BigInteger.valueOf(6)));
		assertEquals(l, t.lessEqual(BigInteger.valueOf(6), BigDecimal
				.valueOf(8)));
		assertEquals(e, t.lessEqual(BigInteger.valueOf(7), BigDecimal
				.valueOf(7)));
		assertEquals(g, t.lessEqual(BigInteger.valueOf(8), BigDecimal
				.valueOf(6)));
		assertEquals(l, t.lessEqual(BigDecimal.valueOf(6), BigDecimal
				.valueOf(8)));
		assertEquals(e, t.lessEqual(BigDecimal.valueOf(7), BigDecimal
				.valueOf(7)));
		assertEquals(g, t.lessEqual(BigDecimal.valueOf(8), BigDecimal
				.valueOf(6)));
		assertEquals(l, t.lessEqual(new Float(6), new Double(8)));
		assertEquals(e, t.lessEqual(new Float(7), new Double(7)));
		assertEquals(g, t.lessEqual(new Float(8), new Double(6)));
		assertEquals(l, t.lessEqual(new Double(6), new Double(8)));
		assertEquals(e, t.lessEqual(new Double(7), new Double(7)));
		assertEquals(g, t.lessEqual(new Double(8), new Double(6)));
		assertEquals(l, t.lessEqual("123", "ABC"));
		assertEquals(e, t.lessEqual("abc", "abc"));
		assertEquals(g, t.lessEqual("ABC", "123"));
	}

	@Test
	public void testGreaterThan() {
		Boolean l = Boolean.FALSE;
		Boolean e = Boolean.FALSE;
		Boolean g = Boolean.TRUE;
		assertEquals(l, t.greaterThan(null, new Object()));
		assertEquals(e, t.greaterThan(null, null));
		assertEquals(g, t.greaterThan(new Object(), null));
		assertEquals(l, t.greaterThan(new Byte((byte) 6), new Short((short) 8)));
		assertEquals(e, t.greaterThan(new Byte((byte) 7), new Short((short) 7)));
		assertEquals(g, t.greaterThan(new Byte((byte) 8), new Short((short) 6)));
		assertEquals(l, t.greaterThan(new Short((short) 6), new Integer(8)));
		assertEquals(e, t.greaterThan(new Short((short) 7), new Integer(7)));
		assertEquals(g, t.greaterThan(new Short((short) 8), new Integer(6)));
		assertEquals(l, t.greaterThan(new Integer(6), new Long(8)));
		assertEquals(e, t.greaterThan(new Integer(7), new Long(7)));
		assertEquals(g, t.greaterThan(new Integer(8), new Long(6)));
		assertEquals(l, t.greaterThan(new Long(6), BigInteger.valueOf(8)));
		assertEquals(e, t.greaterThan(new Long(7), BigInteger.valueOf(7)));
		assertEquals(g, t.greaterThan(new Long(8), BigInteger.valueOf(6)));
		assertEquals(l, t.greaterThan(BigInteger.valueOf(6), BigDecimal
				.valueOf(8)));
		assertEquals(e, t.greaterThan(BigInteger.valueOf(7), BigDecimal
				.valueOf(7)));
		assertEquals(g, t.greaterThan(BigInteger.valueOf(8), BigDecimal
				.valueOf(6)));
		assertEquals(l, t.greaterThan(BigDecimal.valueOf(6), BigDecimal
				.valueOf(8)));
		assertEquals(e, t.greaterThan(BigDecimal.valueOf(7), BigDecimal
				.valueOf(7)));
		assertEquals(g, t.greaterThan(BigDecimal.valueOf(8), BigDecimal
				.valueOf(6)));
		assertEquals(l, t.greaterThan(new Float(6), new Double(8)));
		assertEquals(e, t.greaterThan(new Float(7), new Double(7)));
		assertEquals(g, t.greaterThan(new Float(8), new Double(6)));
		assertEquals(l, t.greaterThan(new Double(6), new Double(8)));
		assertEquals(e, t.greaterThan(new Double(7), new Double(7)));
		assertEquals(g, t.greaterThan(new Double(8), new Double(6)));
		assertEquals(l, t.greaterThan("123", "ABC"));
		assertEquals(e, t.greaterThan("abc", "abc"));
		assertEquals(g, t.greaterThan("ABC", "123"));
	}

	@Test
	public void testGreaterEqual() {
		Boolean l = Boolean.FALSE;
		Boolean e = Boolean.TRUE;
		Boolean g = Boolean.TRUE;
		assertEquals(l, t.greaterEqual(null, new Object()));
		assertEquals(e, t.greaterEqual(null, null));
		assertEquals(g, t.greaterEqual(new Object(), null));
		assertEquals(l, t
				.greaterEqual(new Byte((byte) 6), new Short((short) 8)));
		assertEquals(e, t
				.greaterEqual(new Byte((byte) 7), new Short((short) 7)));
		assertEquals(g, t
				.greaterEqual(new Byte((byte) 8), new Short((short) 6)));
		assertEquals(l, t.greaterEqual(new Short((short) 6), new Integer(8)));
		assertEquals(e, t.greaterEqual(new Short((short) 7), new Integer(7)));
		assertEquals(g, t.greaterEqual(new Short((short) 8), new Integer(6)));
		assertEquals(l, t.greaterEqual(new Integer(6), new Long(8)));
		assertEquals(e, t.greaterEqual(new Integer(7), new Long(7)));
		assertEquals(g, t.greaterEqual(new Integer(8), new Long(6)));
		assertEquals(l, t.greaterEqual(new Long(6), BigInteger.valueOf(8)));
		assertEquals(e, t.greaterEqual(new Long(7), BigInteger.valueOf(7)));
		assertEquals(g, t.greaterEqual(new Long(8), BigInteger.valueOf(6)));
		assertEquals(l, t.greaterEqual(BigInteger.valueOf(6), BigDecimal
				.valueOf(8)));
		assertEquals(e, t.greaterEqual(BigInteger.valueOf(7), BigDecimal
				.valueOf(7)));
		assertEquals(g, t.greaterEqual(BigInteger.valueOf(8), BigDecimal
				.valueOf(6)));
		assertEquals(l, t.greaterEqual(BigDecimal.valueOf(6), BigDecimal
				.valueOf(8)));
		assertEquals(e, t.greaterEqual(BigDecimal.valueOf(7), BigDecimal
				.valueOf(7)));
		assertEquals(g, t.greaterEqual(BigDecimal.valueOf(8), BigDecimal
				.valueOf(6)));
		assertEquals(l, t.greaterEqual(new Float(6), new Double(8)));
		assertEquals(e, t.greaterEqual(new Float(7), new Double(7)));
		assertEquals(g, t.greaterEqual(new Float(8), new Double(6)));
		assertEquals(l, t.greaterEqual(new Double(6), new Double(8)));
		assertEquals(e, t.greaterEqual(new Double(7), new Double(7)));
		assertEquals(g, t.greaterEqual(new Double(8), new Double(6)));
		assertEquals(l, t.greaterEqual("123", "ABC"));
		assertEquals(e, t.greaterEqual("abc", "abc"));
		assertEquals(g, t.greaterEqual("ABC", "123"));
	}

	@Test
	public void testBool() {
		assertFalse(t.bool(null));
		assertFalse(t.bool(Boolean.FALSE));
		assertTrue(t.bool(Boolean.TRUE));
		assertFalse(t.bool(new Long(0)));
		assertTrue(t.bool(new Long(1)));
		assertTrue(t.bool("true"));
		assertFalse(t.bool("false"));
	}

	@Test
	public void testInc() {
		assertNull(t.inc(null, 1));
		assertEquals(new Byte((byte) 3), t.inc(new Byte((byte) 2), 1));
		assertEquals(new Short((short) 3), t.inc(new Short((short) 2), 1));
		assertEquals(new Integer(3), t.inc(new Integer(2), 1));
		assertEquals(new Long(3), t.inc(new Long(2), 1));
		assertEquals(BigInteger.valueOf(3), t.inc(BigInteger.valueOf(2), 1));
		assertEquals(BigDecimal.valueOf(3), t.inc(BigDecimal.valueOf(2), 1));
		assertEqualsFloat(new Float(3), t.inc(new Float(2), 1));
		assertEqualsDouble(new Double(3), t.inc(new Double(2), 1));
	}

}
