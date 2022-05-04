package jp.hishidama.eval.func;

import static org.junit.Assert.*;
import jp.hishidama.eval.ExpRuleFactory;
import jp.hishidama.eval.Expression;
import jp.hishidama.eval.Rule;
import jp.hishidama.eval.oper.IntOperator;

import org.junit.Test;

/**
 * Functionインターフェーステストクラス.
 *
 * @see jp.hishidama.eval.func.Function
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 */
public class FunctionTest {

	static class MockFunction implements Function {
		@Override
		public Object eval(String name, Object[] args) throws Exception {
			assertEquals("func", name);
			assertArrayEquals(new Integer[] { 1, 2, 3 }, args);
			return "関数";
		}

		@Override
		public Object eval(Object object, String name, Object[] args)
				throws Exception {
			assertNull(object);
			assertEquals("method", name);
			assertArrayEquals(new Integer[] { 2, 3, 4 }, args);
			return "メソッド";
		}
	}

	Rule rule = ExpRuleFactory.getDefaultRule().defaultFunction(
			new MockFunction()).defaultOperator(new IntOperator());

	@Test
	public void testEvalFunction() {
		Expression exp = rule.parse("func(1,2,3)");
		Object ret = exp.eval();
		assertEquals("関数", ret);
	}

	@Test
	public void testEvalMethod() {
		Expression exp = rule.parse("zzz.method(2,3,4)");
		Object ret = exp.eval();
		assertEquals("メソッド", ret);
	}
}
