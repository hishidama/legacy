package jp.hishidama.eval.func;

import static org.junit.Assert.*;
import jp.hishidama.eval.ExpRuleFactory;
import jp.hishidama.eval.Expression;
import jp.hishidama.eval.Rule;
import jp.hishidama.eval.var.MapVariable;

import org.junit.Test;

/**
 *
 * @see java.lang.Math
 * @see MathFunction
 */
public class MathFunctionTest {

	Rule rule = ExpRuleFactory.getDefaultRule().defaultFunction(
			new MathFunction());

	@Test
	public void testEval() {
		// Math.max()には、int,long,float,doubleのオーバーロードがある。
		// 呼び出し時の値の型によって呼ばれるメソッドが変わることを確認する。
		// （呼び出したメソッドの引数の型と戻り値の型は一致しているので、それを確認する）

		Expression exp = rule.parse("max(a,b)");
		MapVariable<String, Object> var = new MapVariable<String, Object>();
		exp.setVariable(var);

		Object r;

		var.put("a", 123);
		var.put("b", 456);
		r = exp.eval();
		assertEquals(Integer.class, r.getClass());
		assertEquals(456, r);

		var.put("a", 123L);
		var.put("b", 456L);
		r = exp.eval();
		assertEquals(Long.class, r.getClass());
		assertEquals(456L, r);

		var.put("a", 123);
		var.put("b", 456L);
		r = exp.eval();
		assertEquals(Long.class, r.getClass());
		assertEquals(456L, r);

		var.put("a", 123L);
		var.put("b", 456);
		r = exp.eval();
		assertEquals(Long.class, r.getClass());
		assertEquals(456L, r);

		var.put("a", 123f);
		var.put("b", 456);
		r = exp.eval();
		assertEquals(Float.class, r.getClass());
		assertEquals(456f, r);

		var.put("a", 123);
		var.put("b", 456f);
		r = exp.eval();
		assertEquals(Float.class, r.getClass());
		assertEquals(456f, r);

		var.put("a", 123f);
		var.put("b", 456d);
		r = exp.eval();
		assertEquals(Double.class, r.getClass());
		assertEquals(456d, r);

		var.put("a", 123d);
		var.put("b", 456);
		r = exp.eval();
		assertEquals(Double.class, r.getClass());
		assertEquals(456d, r);
	}
}
