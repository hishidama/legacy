package jp.hishidama.eval.exp;

import static org.junit.Assert.*;

import org.junit.Test;

import jp.hishidama.eval.*;
import jp.hishidama.eval.srch.SearchAdapter;

/**
 * equals・hashCode・sameメソッドのテスト.
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 */
public class EqualsTest {

	Rule rule = ExpRuleFactory.getDefaultRule();

	@Test
	public void testEquals() {
		testEquals("1");
		testEquals("a");
		testEquals("\"abc\"");
		testEquals("\'a\'");
		testEquals("(123)");

		testEquals("test()");
		testEquals("test(1,2)");
		testEquals("obj.test()");
		testEquals("arr[0]");
		testEquals("arr[0][1]");
		testEquals("a++");
		testEquals("a--");
		testEquals("obj.field");

		testEquals("2**3");

		testEquals("+1");
		testEquals("-1");
		testEquals("~a");
		testEquals("!b");
		testEquals("++a");
		testEquals("--a");

		testEquals("2*3");
		testEquals("8/4");
		testEquals("5%4");

		testEquals("1+2");
		testEquals("1-2");

		testEquals("1<<2");
		testEquals("256>>2");
		testEquals("256>>>2");

		testEquals("a<b");
		testEquals("a<=b");
		testEquals("a>b");
		testEquals("a>=b");

		testEquals("a==b");
		testEquals("a!=b");

		testEquals("a&&b");
		testEquals("a||b");

		testEquals("0b0111 & 0b1110");
		testEquals("0b0111 | 0b1110");
		testEquals("0b0111 ^ 0b1110");

		testEquals("c?1:2");

		testEquals("a=2");
		testEquals("a*=2");
		testEquals("a/=2");
		testEquals("a%=2");
		testEquals("a+=2");
		testEquals("a-=2");
		testEquals("a<<=2");
		testEquals("a>>=2");
		testEquals("a>>>=2");
		testEquals("a&=2");
		testEquals("a|=2");
		testEquals("a^=2");
		testEquals("a**=2");

		testEquals("1,2,3");

		testEquals("++a[i].x+(2?3:4)*5/6-7");
	}

	protected void testEquals(String str) {
		Expression e1 = rule.parse(str);
		Expression e2 = rule.parse(str);
		assertFalse(e1.isEmpty());
		assertTrue(e1.equals(e1));
		assertTrue(e1.equals(e2));
		assertTrue(e2.equals(e1));
		Expression d1 = e1.dup();
		Expression d2 = e2.dup();
		assertTrue(e1.equals(d2));
		assertTrue(e2.equals(d1));
		assertTrue(d1.equals(d2));
		assertTrue(d2.equals(d1));

		int hash = e1.hashCode();
		assertEquals(hash, e2.hashCode());
		assertEquals(hash, d1.hashCode());
		assertEquals(hash, d2.hashCode());

		Expression e = rule.parse(str + "+1");
		assertFalse(e1.equals(e));
		assertFalse(e.equals(e1));

		testSame(str);
	}

	protected void testSame(String str) {
		Expression e1 = rule.parse(str);
		Expression e2 = rule.parse(str);
		assertTrue(e1.same(e1));
		assertTrue(e1.same(e2));
		assertTrue(e2.same(e1));
		Expression d1 = e1.dup();
		Expression d2 = e2.dup();
		assertTrue(e1.same(d2));
		assertTrue(e2.same(d1));
		assertTrue(d1.same(d2));
		assertTrue(d2.same(d1));

		// 演算子文字列を変更してみる
		OpeChange oc = new OpeChange();
		d1.search(oc);
		// System.out.println(e1);
		// System.out.println(d1);
		assertTrue(d1.equals(e1)); // equalsでは等しくなる
		assertEquals(e1.hashCode(), d1.hashCode());
		assertEquals(oc.same, d1.same(e1));
		assertEquals(oc.same, e1.same(d1));

		d2.search(oc);
		assertTrue(d1.same(d2));
		assertTrue(d2.same(d1));
	}

	class OpeChange extends SearchAdapter {
		boolean same = true;

		@Override
		public void search(AbstractExpression exp) {
			String ope1 = exp.getOperator();
			if (ope1 != null) {
				exp.setOperator(ope1 + ope1);
				same = false;
			}
			String ope2 = exp.getEndOperator();
			if (ope2 != null) {
				exp.setEndOperator(ope2 + ope2);
				same = false;
			}
		}
	}

	@Test
	public void testEmpty() {
		Expression e1 = rule.parse("");
		Expression e2 = rule.parse("");
		assertTrue(e1.isEmpty());
		assertTrue(e1.equals(e1));
		assertTrue(e1.equals(e2));
		assertTrue(e2.equals(e1));
		assertTrue(e1.same(e2));
		assertTrue(e2.same(e1));

		Expression d1 = e1.dup();
		assertTrue(d1.equals(e2));
		assertTrue(e2.equals(d1));
		assertTrue(d1.same(e2));
		assertTrue(e2.same(d1));

		Expression e3 = rule.parse("1");
		assertFalse(e3.equals(e1));
		assertFalse(e1.equals(e3));
		assertFalse(e3.same(e1));
		assertFalse(e1.same(e3));
		assertFalse(e3.equals(d1));
		assertFalse(d1.equals(e3));
		assertFalse(e3.same(d1));
		assertFalse(d1.same(e3));

		int hash = e1.hashCode();
		assertEquals(hash, e2.hashCode());
		assertEquals(hash, d1.hashCode());
	}

	@Test
	public void testNot() {
		testNot("255", "0xff");
		testNot("1+2", "2+1");
		testNot("test(1)", "test(0)");
		testNot("test(1)", "test(1,2)");
		testNot("test(1,2)", "test(2,1)");
		testNot("test()", "tset()");
		testNot("++a", "a++");
		testNot("1?2:3", "1?2:4");
		testNot("1?2:3", "1?3:3");
		testNot("1?2:3", "0?1:2");
		testNot("arr[0][1]", "arr[1][1]");
		testNot("arr[0][1]", "arr[0][2]");
		testNot("a.x", "a.y");
		testNot("a.x", "a.x.n");
		testNot("a.x", "b.x");
	}

	protected void testNot(String s1, String s2) {
		Expression e1 = rule.parse(s1);
		Expression e2 = rule.parse(s2);
		// assertEquals(e1.evalLong(), e2.evalLong());
		assertFalse(e1.equals(e2));
		assertFalse(e2.equals(e1));
		assertFalse(e1.same(e2));
		assertFalse(e2.same(e1));
	}
}
