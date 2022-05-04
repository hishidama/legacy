package jp.hishidama.eval.ref;

import static org.junit.Assert.*;

import org.junit.Test;

import jp.hishidama.eval.*;
import jp.hishidama.eval.exp.*;
import jp.hishidama.eval.srch.SearchAdapter;
import jp.hishidama.eval.var.MapVariable;

/**
 * リファクタリング（getter変更）のテスト.
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 */
public class RefactorGetterTest {

	/*
	 * 変数名を変更するテスト・メソッド
	 */
	@Test
	public void testVar() {
		Rule rule = ExpRuleFactory.getDefaultRule();
		Expression exp = rule.parse("a=x");

		assertEquals("a = x", exp.toString());
		exp.refactorFunc(new RefactorVarName(null, "x", "getX()"), rule);
		assertEquals("a = getX()", exp.toString());

		exp = rule.parse("a=x+y+x");
		assertEquals("a = x + y + x", exp.toString());
		exp.refactorFunc(new RefactorVarName(null, "x", "getX()"), rule);
		assertEquals("a = getX() + y + getX()", exp.toString());
		exp.refactorFunc(new RefactorVarName(null, "y", "getY()"), rule);
		assertEquals("a = getX() + getY() + getX()", exp.toString());

		exp = rule.parse("x+=x=x+x()");
		assertEquals("x += x = x + x()", exp.toString());
		exp.refactorFunc(new RefactorVarName(null, "x", "getX()"), rule);
		assertEquals("x += x = getX() + x()", exp.toString());
	}

	@Test
	public void testField() {
		Rule rule = ExpRuleFactory.getDefaultRule();
		Expression exp = rule.parse("a=x+b.x");
		MapVariable<String, FieldTestA> var = new MapVariable<String, FieldTestA>();
		var.setValue("b", new FieldTestA(1));
		exp.setVariable(var);

		assertEquals("a = x + b.x", exp.toString());
		exp.refactorFunc(new RefactorVarName(FieldTestA.class, "x", "getX()"),
				rule);
		assertEquals("a = x + b.getX()", exp.toString());

		exp = rule.parse("b.x+=b.x=b.x()+b.x+a.x+x");
		exp.setVariable(var);
		assertEquals("b.x += b.x = b.x() + b.x + a.x + x", exp.toString());
		exp.refactorFunc(new RefactorVarName(FieldTestA.class, "x", "getX()"),
				rule);
		assertEquals("b.x += b.x = b.x() + b.getX() + a.x + x", exp.toString());
	}

	@Test
	public void testChangeOperator() {
		Rule rule = ExpRuleFactory.getDefaultRule();
		MapVariable<String, FieldTestA> var = new MapVariable<String, FieldTestA>();
		var.setValue("a", new FieldTestA(1));

		Expression exp = rule.parse("a.x ** 2");
		exp.setVariable(var);
		assertEquals("a.x ** 2", exp.toString());
		exp.refactorFunc(new RefactorVarName(FieldTestA.class, "x",
				"getX(2**3)"), rule);
		assertEquals("a.getX(2 ** 3) ** 2", exp.toString());

		exp.search(new SearchAdapter() {
			// 累乗演算子の「**」を「^」に変える
			@Override
			public void search(AbstractExpression exp) {
				if (exp instanceof PowerExpression) {
					exp.setOperator("^");
				}
			}
		});
		assertEquals("a.getX(2 ^ 3) ^ 2", exp.toString());
	}
}
