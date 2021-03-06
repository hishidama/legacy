package jp.hishidama.eval.exp;

import static org.junit.Assert.*;

import org.junit.Test;

import jp.hishidama.eval.*;
import jp.hishidama.eval.oper.JavaExOperator;
import jp.hishidama.eval.var.MapVariable;

/**
 * 文字列のテスト.
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 */
public class StringExpressionTest {

	Rule rule = ExpRuleFactory.getDefaultRule();

	@Test
	public void testString() {
		assertParse("\"str\"", "\"str\"");
		assertParse("\"str\"", " \"str\" ");
		assertParse("\"s t r\"", " \"s t r\" ");
		assertError("\"str");
		assertError("abc +\"a+ def");

		Expression exp = rule.parse("\"string\"");
		assertEquals("string", exp.eval());

		exp = rule.parse("\"str\"+\"ing\"");
		assertEquals("\"str\" + \"ing\"", exp.toString());
		assertEquals("string", exp.eval());
	}

	@Test
	public void testEmpty() {
		assertParse("\"\"", "\"\"");
	}

	@Test
	public void testLong() {
		Expression exp = rule.parse("\"123\"");
		assertEquals("\"123\"", exp.toString());
		assertEquals(123, exp.evalLong());
	}

	@Test
	public void testDouble() {
		Expression exp = rule.parse("\"123.4\"");
		assertEquals("\"123.4\"", exp.toString());
		assertEquals(123.4, exp.evalDouble(), 0.000001);
	}

	@Test
	public void testOptimize() {
		Expression exp = rule.parse("\"str\"+\"ing\"");
		assertEquals("\"str\" + \"ing\"", exp.toString());
		exp.optimize(new MapVariable<String, Object>(), new JavaExOperator());
		assertEquals("\"string\"", exp.toString());
	}

	protected void assertParse(String expected, String str) {
		assertEquals(expected, rule.parse(str).toString());
	}

	protected void assertError(String str) {
		try {
			rule.parse(str);
		} catch (EvalException e) {
			System.out.println(e);
			return;// OK
		}
		fail("例外が発生するはず");
	}
}
