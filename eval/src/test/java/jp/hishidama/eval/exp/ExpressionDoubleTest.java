package jp.hishidama.eval.exp;

import java.util.*;

import static org.junit.Assert.*;

import org.junit.Test;

import jp.hishidama.eval.EvalException;
import jp.hishidama.eval.Expression;
import jp.hishidama.eval.ExpRuleFactory;
import jp.hishidama.eval.func.*;
import jp.hishidama.eval.var.*;

/**
 * double演算のテスト.
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 */
public class ExpressionDoubleTest {

	/*
	 * 'jp.hishidama.eval.Expression.eval()' のためのテスト・メソッド
	 */
	@Test
	public void testEval() {
		testEval("", null, null, 0);
		testEval(" 12.34 ", null, null, 12.34);
		testEval("1.2+3.4", null, null, 4.6);
		testEval("12.3+-+4", null, null, 8.3);
		testEval("12.3*4", null, null, 49.2);
		testEval("1+2*3-4.5", null, null, 2.5);
		testEval("(1+2)*3.5", null, null, 10.5);
		testEval("1+10.5%4", null, null, 1 + 10.5 % 4);
		testEval("10 / 3 * 3", null, null, 10);
		testEval("3 * 10 / 3", null, null, 10);
		testEval("-2 ** 3**2", null, null, -64);
	}

	@Test
	public void testHex() {
		testEval("0x1234567890", null, null, 0x1234567890l);
		testEval("0xabcdef", null, null, 0xabcdef);
		testEval("0XABCDEF", null, null, 0xabcdef);
		testEval(" 0xfl ", null, null, 0xf);
		testEval(" 0xfL ", null, null, 0xf);
	}

	@Test
	public void testBin() {
		testEval("0b1110", null, null, 0xe);
		testEval("0B0111", null, null, 7);
		testEval("0B0111l", null, null, 7);
		testEval("0B0111L", null, null, 7);
	}

	@Test
	public void testOct() {
		testEval("0o12345670", null, null, 012345670);
		testEval("0O12345670", null, null, 012345670);
		testEval("0O12345670l", null, null, 012345670);
		testEval("0O12345670L", null, null, 012345670);
	}

	@Test
	public void testShift() {
		testEval("1<<8", null, null, 1 << 8);
		testEval("-8>>2", null, null, -8L >> 2);
		testEval("-8>>>2", null, null, 8L >>> 2);
		testEval("1+2<<3-1", null, null, 1 + 2 << 3 - 1);
	}

	@Test
	public void testBit() {
		testEval("~123", null, null, ~123L);
		testEval("1 | 3 ^ 5 & 7", null, null, 1 | 3 ^ 5 & 7);
	}

	@Test
	public void test括弧() {
		testEval("(1)", null, null, 1);
		testEval("((2))", null, null, 2);
		testParse("(3))");
		testParse("((99)");
	}

	@Test
	public void testError() {
		testParse("12 89");
		testParse("12+"); // 正常に終わっていない式
		testParse("12%%4"); // オペランドのない演算子
		testParse("12+/*zzz"); // 終わっていないブロックコメント
		testParse("12+/*/4"); // 不正なコメント
	}

	@Test
	public void testComment() {
		testEval("12//4", null, null, 12);
		testEval("12//3\r+4", null, null, 16);
		testEval("12//3\r\n+4", null, null, 16);
		testEval("12//3\n+4", null, null, 16);

		testEval("12/*4*/", null, null, 12);
		testEval("12 /* +3 */+4", null, null, 16);
		testEval("12 /* +3 */ +4", null, null, 16);

		testEval("12/*zzz", null, null, 12); // 終わっていないブロックコメント
	}

	@Test
	public void testIf() {
		testEval("1?2:3", null, null, 2);
		testEval("0?2:3", null, null, 3);
		testParse("1:2?3");
		testEval("0?2:1?4:5", null, null, 4); // false? 2 : (true ?4:5)
		testEval("0?2:0?4:5", null, null, 5); // false? 2 : (false?4:5)
		testEval("1?2:1?4:5", null, null, 2); // true ? 2 : (true ?4:5)
		testEval("1?2?3:4:5", null, null, 3);
		testEval("1?0?3:4:5", null, null, 4);
		testEval("0?2?3:4:5", null, null, 5);
		testEval("(1?2:0)?3:4", null, null, 3);
		testParse("2?:3");
		testParse("2?3:");
	}

	@Test
	public void testCond() {
		testEval("2==2", null, null, 1);
		testEval("2==3", null, null, 0);
		testEval("2!=3", null, null, 1);
		testEval("3!=3", null, null, 0);
		testEval("2<3", null, null, 1);
		testEval("3<3", null, null, 0);
		testEval("2<=3", null, null, 1);
		testEval("3<=3", null, null, 1);
		testEval("3<=2", null, null, 0);
		testEval("4>3", null, null, 1);
		testEval("4>4", null, null, 0);
		testEval("4>=3", null, null, 1);
		testEval("4>=4", null, null, 1);
		testEval("3>=4", null, null, 0);
		testEval("!0", null, null, 1);
		testEval("!1", null, null, 0);
		testEval("!2", null, null, 0);
	}

	@Test
	public void testAndOr() {
		testEval("2&&3", null, null, 3);
		testEval("0&&3", null, null, 0);
		testEval("2&&0", null, null, 0);
		testEval("2||3", null, null, 2);
		testEval("0||3", null, null, 3);
		testEval("0||0", null, null, 0);
		testEval("0||2&&3", null, null, 3);
		testEval("1||2&&3", null, null, 1);
	}

	@Test
	public void testSign() {
		testEval("-1", null, null, -1);
		testEval("+23", null, null, 23);
		testEval("!0", null, null, 1);
		testEval("~0", null, null, ~0);
		testEval("-+-123", null, null, 123);
		testEval("-+!0", null, null, -1);
	}

	@Test
	public void testVar() {
		testEval("1+0abc+2", null, null);
		testEval("abc", null, null);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("abc", new Double(12));
		testEval("abc", map, null, 12);
		testEval("1+ abc+2", map, null, 15);
		testEval("1+ def+2", map, null);
		map.put("def", new Float(34));
		testEval("1+ def+2", map, null, 37);
	}

	@Test
	public void testArray() {
		Double[] a = new Double[4];
		a[1] = new Double(11);
		a[2] = new Double(22);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("a", a);
		testEval("a[1]", map, null, 11);
		testEval("a[3]=a[1]+a[2]", map, null, 33);
		testEval("a[4]", map, null);

		testEval("a[1]+=33", map, null, 44);
		System.out.println(a[1]);

		Double[][] b = new Double[2][];
		b[0] = new Double[3];
		b[1] = new Double[3];
		b[1][0] = new Double(100);
		b[1][1] = new Double(101);
		b[1][2] = new Double(102);
		map.put("b", b);
		map.put("j", new Long(2));
		testEval("b[1][j-1]", map, null, 101);
		testEval("a[1][0]", map, null);
		testEval("b[0][0]=b[1][0]+b[1][2]", map, null, 202);
		testEval("b[0][0]", map, null, 202);
	}

	@Test
	public void testLet() {
		Map<String, Object> map = new HashMap<String, Object>();
		testEval("a=100+11", map, null, 111);
		assertVar(111, map, "a");
		testEval("a=b=12", map, null, 12);
		assertVar(12, map, "a");
		assertVar(12, map, "b");
		testEval("a=1+(b=12)", map, null, 13);
		assertVar(13, map, "a");
		assertVar(12, map, "b");
		testEval("a=1+b=2", map, null);

		map.put("a", new Double(100));
		map.put("b", new Double(10));
		testEval("a+=b+=1", map, null, 111);
		assertVar(111, map, "a");
		assertVar(11, map, "b");

		map.put("a", new Double(100));
		map.put("b", new Double(10));
		testEval("a-=b-=1", map, null, 91);
		assertVar(91, map, "a");
		assertVar(9, map, "b");

		map.put("a", new Double(2));
		map.put("b", new Double(3));
		testEval("a*=b*=4", map, null, 24);
		assertVar(24, map, "a");
		assertVar(12, map, "b");

		map.put("a", new Double(24));
		map.put("b", new Double(6));
		testEval("a/=b/=2", map, null, 8);
		assertVar(8, map, "a");
		assertVar(3, map, "b");

		map.put("a", new Double(10));
		map.put("b", new Double(10));
		testEval("a%=b%=6", map, null, 2);
		assertVar(2, map, "a");
		assertVar(4, map, "b");

		map.put("a", new Double(1));
		map.put("b", new Double(2));
		testEval("a<<=b<<=1", map, null, 1 << 4);
		assertVar(1 << 4, map, "a");
		assertVar(4, map, "b");

		map.put("a", new Double(1 << 8));
		map.put("b", new Double(1 << 4));
		testEval("a>>=b>>=3", map, null, 1 << 6);
		assertVar(1 << 6, map, "a");
		assertVar(1 << 1, map, "b");

		map.put("a", new Double(-1));
		map.put("b", new Double(-1));
		double b = 1d / Math.pow(2, 63);
		double a = 1d / Math.pow(2, b);
		testEval("a>>>=b>>>=63", map, null, a);
		assertVar(a, map, "a");
		assertVar(b, map, "b");

		map.put("a", new Double(0x0f7f));
		map.put("b", new Double(0x0ff0));
		testEval("a&=b&=255", map, null, 0x70);
		assertVar(0x70, map, "a");
		assertVar(0xf0, map, "b");

		map.put("a", new Double(0xf000));
		map.put("b", new Double(0x0f00));
		testEval("a|=b|=255-15", map, null, 0xfff0);
		assertVar(0xfff0, map, "a");
		assertVar(0x0ff0, map, "b");

		map.put("a", new Double(0xffff));
		map.put("b", new Double(0x0f0f));
		testEval("a^=b^=255", map, null, 0xf00f);
		assertVar(0xf00f, map, "a");
		assertVar(0xff0, map, "b");

		map.put("a", new Double(2));
		map.put("b", new Double(2));
		testEval("a**=b**=3", map, null, 256);
		assertVar(256, map, "a");
		assertVar(8, map, "b");
	}

	@Test
	public void testComma() {
		Map<String, Object> map = new HashMap<String, Object>();
		testEval("aa=1,bb=0,cc=2,aa+bb+cc", map, null, 3);
		assertVar(1, map, "aa");
		assertVar(0, map, "bb");
		assertVar(2, map, "cc");
	}

	@Test
	public void testIncBefore() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("i", new Double(1));
		testEval("++i", map, null, 2);
		assertVar(2, map, "i");
		testEval("--i", map, null, 1);
		assertVar(1, map, "i");
		testEval("2+ ++i", map, null, 4);

		testEval("++--i", map, null);
	}

	@Test
	public void testIncAfter() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("i", new Double(1));
		testEval("i++", map, null, 1);
		assertVar(2, map, "i");
		testEval("i--", map, null, 2);
		assertVar(1, map, "i");
		testEval("2+ i++", map, null, 3);
		assertVar(2, map, "i");

		testEval("i++--", map, null);
		testEval("++i++", map, null);
		testEval("++i--", map, null);
	}

	@Test
	public void testFunction() {
		testEval("test()", null, null, 0);
		testParse("test((1,1+1)");
		testParse("test(1,1+1))");
		Function func = new InvokeFunction();
		testEval("zzz()+foo(1)+test(2,3+4,5*6-7)", null, func, 0);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("a", new FieldTestA(10.1));
		testEval("a.getDbl()", map, func, 10.1);

		Function math = new MathFunction();
		testEval("sin(1)", null, math, Math.sin(1));
		testEval("cos(2)", null, math, Math.cos(2));
		testEval("pow(cos(3),2)+pow(sin(3),2)", null, math, 1);
	}

	@Test
	public void testField() {
		FieldTestA a = new FieldTestA(111);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("a", a);
		testEval("a.n", map, null, 111);

		FieldTestC c = new FieldTestC();
		map.put("c", c);
		testEval("c.a.n", map, null, 222);
		testEval("c.a.n-c.z*2+a.n", map, null, -333);
	}

	@Test
	public void testメンバー複合() {
		FieldTestA[] arr = new FieldTestA[3];
		arr[1] = new FieldTestA(10.1);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("a", arr);
		Function func = new InvokeFunction();
		testEval("a[1].dblValue", map, null, 12.3);
		testEval("a[1].getDbl()", map, func, 10.1);
		testEval("a[1].getB()[1].get()", map, func, 22);
	}

	/**
	 * 正常系のテスト.
	 *
	 * @param str
	 *            文字列
	 * @param map
	 *            変数マップ
	 * @param func
	 *            関数定義
	 * @param expected
	 *            正しい結果
	 */
	private void testEval(String str, Map<String, Object> map, Function func,
			double expected) {
		System.out.println("●" + str + "●");
		Expression exp = ExpRuleFactory.getDefaultRule().parse(str);
		if (map != null) {
			MapVariable<String, Object> var = new MapVariable<String, Object>();
			var.setMap(map);
			exp.setVariable(var);
		}
		// exp.dump(0);
		System.out.println(exp.toString());
		if (func != null) {
			exp.setFunction(func);
		}
		double ret = exp.evalDouble();
		System.out.println("= " + ret);
		assertEquals(expected, ret, DELTA);
	}

	/**
	 * 異常系のテスト.
	 * <p>
	 * 字句解析・構文解析でEvalExceptionが起きるはず。<br>
	 * （メッセージが正しいかどうかについてのチェックは省略）
	 * </p>
	 *
	 * @param str
	 *            文字列
	 */
	private void testParse(String str) {
		System.out.println("●" + str + "●");
		Expression exp;
		try {
			exp = ExpRuleFactory.getDefaultRule().parse(str);
		} catch (EvalException e) {
			System.out.println(e.toString());
			return;
		}
		// exp.dump(0);
		System.out.println(exp.toString());
		fail(str + "→" + exp.toString());
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
	 * @param func
	 *            関数定義
	 */
	private void testEval(String str, Map<String, Object> map, Function func) {
		System.out.println("●" + str + "●");
		Expression exp = ExpRuleFactory.getDefaultRule().parse(str);
		if (map != null) {
			MapVariable<String, Object> var = new MapVariable<String, Object>();
			var.setMap(map);
			exp.setVariable(var);
		}
		// exp.dump(0);
		System.out.println(exp.toString());
		if (func != null) {
			exp.setFunction(func);
		}
		try {
			System.out.println("= " + exp.evalDouble());
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
	private void assertVar(double expected, Map<String, Object> map, String name) {
		try {
			Double var = (Double) map.get(name);
			assertEquals(expected, var.doubleValue(), DELTA);
		} catch (Exception e) {
			throw new RuntimeException("assertVar(" + name + ")", e);
		}
	}

	/** doubleの比較に使用する閾値. */
	private static final double DELTA = Math.pow(10, -15);

}
