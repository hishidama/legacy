package jp.hishidama.eval.exp;

import java.util.*;

import static org.junit.Assert.*;

import org.junit.Test;

import jp.hishidama.eval.EvalException;
import jp.hishidama.eval.Expression;
import jp.hishidama.eval.ExpRuleFactory;
import jp.hishidama.eval.oper.LongOperator;
import jp.hishidama.eval.var.*;

/**
 * long最適化のテスト.
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 */
public class OptimizeLongTest {

	/*
	 * 'jp.hishidama.eval.Expression.optimizeLong()' のためのテスト・メソッド
	 */
	@Test
	public void testOptimize() {
		testOpt(" 1234 ", null, 1234);
		testOpt("12+34", null, 46);
		testOpt("123+-+4", null, 123 - 4);
		testOpt("123*4", null, 123 * 4);
		testOpt("1+2*3-4", null, 3);
		testOpt("(1+2)*3", null, 9);
		testOpt("1+10%4", null, 3);
		testOpt("10 / 3 * 3", null, 9);
		testOpt("3 * 10 / 3", null, 10);
		testOpt("1+2+a", null, "3 + a");
		testOpt("a+2+3", null, "a + 2 + 3");
	}

	@Test
	public void testShift() {
		testOpt("1<<8", null, 1 << 8);
		testOpt("-8>>2", null, -8L >> 2);
		testOpt("-8>>>2", null, -8L >>> 2);
		testOpt("1+2<<3-1", null, 1 + 2 << 3 - 1);
	}

	@Test
	public void testBit() {
		testOpt("~123", null, ~123L);
		testOpt("1 | 3 ^ 5 & 7", null, 1 | 3 ^ 5 & 7);
	}

	@Test
	public void test括弧() {
		testOpt("(1)", null, 1);
		testOpt("((2))", null, 2);
		testOpt("(a)", null, "a");
		testOpt("((a))", null, "a");
	}

	@Test
	public void testIf() {
		testOpt("1?2:3", null, 2);
		testOpt("0?2:3", null, 3);
		testOpt("0?2:1?4:5", null, 4); // false? 2 : (true ?4:5)
		testOpt("0?2:0?4:5", null, 5); // false? 2 : (false?4:5)
		testOpt("1?2:1?4:5", null, 2); // true ? 2 : (true ?4:5)
		testOpt("1?2?3:4:5", null, 3);
		testOpt("1?0?3:4:5", null, 4);
		testOpt("0?2?3:4:5", null, 5);
		testOpt("(1?2:0)?3:4", null, 3);
		testOpt("1?a:b", null, "a");
		testOpt("0?a:b", null, "b");
		testOpt("(1?2:0)?a:b", null, "a");
	}

	@Test
	public void testCond() {
		testOpt("2==2", null, 1);
		testOpt("2==3", null, 0);
		testOpt("2!=3", null, 1);
		testOpt("3!=3", null, 0);
		testOpt("2<3", null, 1);
		testOpt("3<3", null, 0);
		testOpt("2<=3", null, 1);
		testOpt("3<=3", null, 1);
		testOpt("3<=2", null, 0);
		testOpt("4>3", null, 1);
		testOpt("4>4", null, 0);
		testOpt("4>=3", null, 1);
		testOpt("4>=4", null, 1);
		testOpt("3>=4", null, 0);
		testOpt("!0", null, 1);
		testOpt("!1", null, 0);
		testOpt("!2", null, 0);
	}

	@Test
	public void testAndOr() {
		testOpt("2&&3", null, 3);
		testOpt("0&&3", null, 0);
		testOpt("2&&0", null, 0);
		testOpt("2||3", null, 2);
		testOpt("0||3", null, 3);
		testOpt("0||0", null, 0);
		testOpt("0||2&&3", null, 3);
		testOpt("1||2&&3", null, 1);
	}

	@Test
	public void testSign() {
		testOpt("-1", null, -1);
		testOpt("+23", null, 23);
		testOpt("!0", null, 1);
		testOpt("~0", null, ~0);
		testOpt("-+-123", null, 123);
		testOpt("-+!0", null, -1);
	}

	@Test
	public void testVar() {
		testOpt("abc", null, "abc");
		testOpt("1+0abc+2", null, "1 + 0abc + 2");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("abc", new Long(12));
		testOpt("abc", map, 12);
		testOpt("1+ abc+2", map, 15);
		testOpt("1+ def+2", map, "1 + def + 2");
		map.put("def", new Integer(34));
		testOpt("1+ def+2", map, 37);

		map = new HashMap<String, Object>();
		map.put("b", new Long(1));
		testOpt("(b? (A=11):(A=22)), c=A", map, "A = 11, c = A");
		assertNull(map.get("A"));
		assertNull(map.get("c"));
	}

	@Test
	public void testArray() {
		Map<String, Object> map = new HashMap<String, Object>();
		Long[] a = new Long[4];
		a[1] = new Long(11);
		a[2] = new Long(22);
		map.put("a", a);
		testOpt("a[1]", map, 11);
		testOpt("a[3]=a[1]+a[2]", map, "a[3] = 33");
		testOpt("a[4]", map, "a[4]");

		Long[][] b = new Long[2][];
		b[1] = new Long[3];
		b[1][0] = new Long(100);
		b[1][1] = new Long(101);
		b[1][2] = new Long(102);
		map.put("b", b);
		map.put("j", new Long(2));
		testOpt("b[1][j-1]", map, 101);
		testOpt("b[1][j-1]", null, "b[1][j - 1]");
	}

	@Test
	public void testLet() {
		Map<String, Object> map = new HashMap<String, Object>();
		testOpt("a=100+11", map, "a = 111");
		// assertVar(111, map, "a");

		testOpt("b?(c=1):(c=2)", map, "b ? (c = 1) : (c = 2)");
		// ↑このcには定数を入れてはいけない。
		// その判断をするのが難しいので、値の代入は行わない

		testOpt("a=b=12", map, "a = b = 12");
		// assertVar(12, map, "a");
		// assertVar(12, map, "b");
		testOpt("a=1+(b=12)", map, "a = 1 + (b = 12)");
		// assertVar(13, map, "a");
		// assertVar(12, map, "b");
		testOpt("a=1+b=2", map, "a = 1 + b = 2");

		map.put("a", new Long(100));
		map.put("b", new Long(10));
		testOpt("a+=b+=1", map, "a += b += 1");
		assertVar(100, map, "a");
		assertVar(10, map, "b");

		map.put("a", new Long(100));
		map.put("b", new Long(10));
		testOpt("a-=b-=1", map, "a -= b -= 1");
		assertVar(100, map, "a");
		assertVar(10, map, "b");

		map.put("a", new Long(2));
		map.put("b", new Long(3));
		testOpt("a*=b*=4", map, "a *= b *= 4");
		assertVar(2, map, "a");
		assertVar(3, map, "b");

		map.put("a", new Long(24));
		map.put("b", new Long(6));
		testOpt("a/=b/=2", map, "a /= b /= 2");
		assertVar(24, map, "a");
		assertVar(6, map, "b");

		map.put("a", new Long(10));
		map.put("b", new Long(10));
		testOpt("a%=b%=6", map, "a %= b %= 6");
		assertVar(10, map, "a");
		assertVar(10, map, "b");

		map.put("a", new Long(1));
		map.put("b", new Long(2));
		testOpt("a<<=b<<=1", map, "a <<= b <<= 1");
		assertVar(1, map, "a");
		assertVar(2, map, "b");

		map.put("a", new Long(1 << 8));
		map.put("b", new Long(1 << 4));
		testOpt("a>>=b>>=3", map, "a >>= b >>= 3");
		assertVar(1 << 8, map, "a");
		assertVar(1 << 4, map, "b");

		map.put("a", new Long(-1));
		map.put("b", new Long(-1));
		testOpt("a>>>=b>>>=63", map, "a >>>= b >>>= 63");
		assertVar(-1, map, "a");
		assertVar(-1, map, "b");

		map.put("a", new Long(0x0f7f));
		map.put("b", new Long(0x0ff0));
		testOpt("a&=b&=255", map, "a &= b &= 255");
		assertVar(0xf7f, map, "a");
		assertVar(0xff0, map, "b");

		map.put("a", new Long(0xf000));
		map.put("b", new Long(0x0f00));
		testOpt("a|=b|=255-15", map, "a |= b |= 240");
		assertVar(0xf000, map, "a");
		assertVar(0x0f00, map, "b");

		map.put("a", new Long(0xffff));
		map.put("b", new Long(0x0f0f));
		testOpt("a^=b^=255", map, "a ^= b ^= 255");
		assertVar(0xffff, map, "a");
		assertVar(0x0f0f, map, "b");
	}

	@Test
	public void testComma() {
		testOpt("1,2,3", null, 3);
		testOpt("1,a,3", null, "a, 3");
		Map<String, Object> map = new HashMap<String, Object>();
		testOpt("aa=1,bb=0,cc=2,aa+bb+cc", map,
				"aa = 1, bb = 0, cc = 2, aa + bb + cc");
		// assertVar(1, map, "aa");
		// assertVar(0, map, "bb");
		// assertVar(2, map, "cc");
	}

	@Test
	public void testIncBefore() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("i", new Long(1));
		testOpt("++i", map, "++i");
		// assertVar(2, map, "i");
		testOpt("--i", map, "--i");
		// assertVar(1, map, "i");
		testOpt("2+ ++i", map, "2 + ++i");

		testOpt("++--i", map, "++ --i");
	}

	@Test
	public void testIncAfter() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("i", new Long(1));
		testOpt("i++", map, "i++");
		// assertVar(2, map, "i");
		testOpt("i--", map, "i--");
		// assertVar(1, map, "i");
		testOpt("2+ i++", map, "2 + i++");
		// assertVar(2, map, "i");

		testOpt("i++--", map, "i++ --");
		testOpt("++i++", map, "++i++");
		testOpt("++i--", map, "++i--");
	}

	@Test
	public void testFunction() {
		testOpt("test()", null, "test()");
		testOpt("zzz()+foo(1)+test(2,3+4,5*6-7)", null,
				"zzz() + foo(1) + test(2, 7, 23)");

		testOpt("abs(-9)", null, "abs(-9)");
		testOpt("max(2,3)", null, "max(2, 3)");
		testOpt("min(2,3)", null, "min(2, 3)");
		testOpt("1+round(100)", null, "1 + round(100)");
	}

	@Test
	public void testField() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("a", new FieldTestA(1));
		testOpt("a.n", null, "a.n");
		testOpt("a.n", map, 1);
	}

	/**
	 * 正常系のテスト.
	 *
	 * @param str
	 *            文字列
	 * @param map
	 *            変数マップ
	 * @param expected
	 *            正しい結果
	 */
	private void testOpt(String str, Map<String, Object> map, long expected) {
		System.out.println("●" + str + "●");
		Expression exp = ExpRuleFactory.getDefaultRule().parse(str);
		// exp.dump(0);
		System.out.println(exp.toString());
		MapVariable<String, Object> var = null;
		if (map != null) {
			var = new MapVariable<String, Object>();
			var.setMap(map);
		}

		String old = exp.toString();

		Expression opt = exp.dup();
		opt.optimize(var, new LongOperator());
		long ret;
		try {
			ret = Long.parseLong(opt.toString());
		} catch (RuntimeException ex) {
			System.out.println("testOpt-value: " + opt.toString());
			throw ex;
		}
		System.out.println("= " + ret);
		assertEquals(expected, ret);

		assertEquals(old, exp.toString());
	}

	/**
	 * 正常系のテスト.
	 *
	 * @param str
	 *            文字列
	 * @param map
	 *            変数マップ
	 * @param expected
	 *            正しい結果
	 */
	private void testOpt(String str, Map<String, Object> map, String expected) {
		System.out.println("●" + str + "●");
		Expression exp = ExpRuleFactory.getDefaultRule().parse(str);
		// exp.dump(0);
		System.out.println(exp.toString());
		MapVariable<String, Object> var = null;
		if (map != null) {
			var = new MapVariable<String, Object>();
			var.setMap(map);
		}

		String old = exp.toString();

		Expression opt = exp.dup();
		opt.optimize(var, new LongOperator());
		String ret = opt.toString();
		System.out.println("= " + ret);
		assertEquals(expected, ret);

		assertEquals(old, exp.toString());
	}

	/**
	 * 異常系のテスト.
	 * <p>
	 * 評価中にEvalExceptionが起きるはず。<br>
	 * （メッセージが正しいかどうかについてのチェックは省略）
	 * </p>
	 *
	 * @param str
	 *            文字列
	 * @param map
	 *            変数マップ
	 */
	public void testOpt(String str, Map<String, Object> map) {
		System.out.println("●" + str + "●");
		Expression exp = ExpRuleFactory.getDefaultRule().parse(str);
		// exp.dump(0);
		System.out.println(exp.toString());
		MapVariable<String, Object> var = null;
		if (map != null) {
			var = new MapVariable<String, Object>();
			var.setMap(map);
		}
		try {
			exp.optimize(var, new LongOperator());
			System.out.println("= " + exp.toString());
		} catch (EvalException e) {
			System.out.println(e.toString());
			return;
		}
		fail();
	}

	/**
	 * 変数値の確認.
	 *
	 * @param expected
	 *            想定される値
	 * @param map
	 *            変数マップ
	 * @param name
	 *            変数名
	 */
	private void assertVar(long expected, Map<String, Object> map, String name) {
		try {
			Long var = (Long) map.get(name);
			assertEquals(expected, var.longValue());
		} catch (Exception e) {
			throw new RuntimeException("assertVar(" + name + ")", e);
		}
	}

}
