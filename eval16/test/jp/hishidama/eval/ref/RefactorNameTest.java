package jp.hishidama.eval.ref;

import static org.junit.Assert.*;

import org.junit.Test;

import jp.hishidama.eval.*;
import jp.hishidama.eval.exp.FieldTestA;
import jp.hishidama.eval.ref.RefactorFuncName;
import jp.hishidama.eval.ref.RefactorVarName;
import jp.hishidama.eval.var.MapVariable;

/**
 * リファクタリング（変数名変更）のテスト.
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 */
public class RefactorNameTest {

	/*
	 * 変数名を変更するテスト・メソッド
	 */
	@Test
	public void testVar() {
		Rule rule = ExpRuleFactory.getDefaultRule();
		Expression exp = rule.parse("1+abc+2+def*abc+3");
		Expression chg = exp.dup();

		chg.refactorName(new RefactorVarName(null, "abc", "foo"));
		assertEquals("1 + abc + 2 + def * abc + 3", exp.toString());
		assertEquals("1 + foo + 2 + def * foo + 3", chg.toString());
		chg.refactorName(new RefactorVarName(null, "def", "d"));
		assertEquals("1 + foo + 2 + d * foo + 3", chg.toString());
	}

	@Test
	public void testField() {
		Rule rule = ExpRuleFactory.getDefaultRule();
		Expression exp = rule.parse("a.f1=2");
		assertEquals("a.f1 = 2", exp.toString());

		MapVariable<?, ?> var = new MapVariable<Object, Object>();
		var.setValue("a", new FieldTestA(1));
		exp.setVariable(var);

		// 単なる変数の変更
		exp.refactorName(new RefactorVarName(null, "f1", "chg1"));
		assertEquals("a.f1 = 2", exp.toString());

		// フィールド名の変更
		exp.refactorName(new RefactorVarName(FieldTestA.class, "f1", "chg1"));
		assertEquals("a.chg1 = 2", exp.toString());

		exp = rule.parse("a.f1+a.f2+f1+a.f1+a.f1()");
		exp.setVariable(var);
		assertEquals("a.f1 + a.f2 + f1 + a.f1 + a.f1()", exp.toString());
		exp.refactorName(new RefactorVarName(FieldTestA.class, "f1", "chg"));
		assertEquals("a.chg + a.f2 + f1 + a.chg + a.f1()", exp.toString());

		// オブジェクトの変数名の変更
		var.setValue("b", new Object());
		exp.refactorName(new RefactorVarName(null, "a", "b"));
		assertEquals("b.chg + b.f2 + f1 + b.chg + b.f1()", exp.toString());
	}

	@Test
	public void testFunc() {
		Rule rule = ExpRuleFactory.getDefaultRule();
		Expression exp = rule.parse("1+abc()+2+def()*abc(1)+abc");
		assertEquals("1 + abc() + 2 + def() * abc(1) + abc", exp.toString());

		exp.refactorName(new RefactorFuncName(null, "abc", "foo"));
		assertEquals("1 + foo() + 2 + def() * foo(1) + abc", exp.toString());
		exp.refactorName(new RefactorFuncName(null, "def", "d"));
		assertEquals("1 + foo() + 2 + d() * foo(1) + abc", exp.toString());

		exp = rule.parse("a(f(a(1,a())))");
		assertEquals("a(f(a(1, a())))", exp.toString());
		exp.refactorName(new RefactorFuncName(null, "a", "b"));
		assertEquals("b(f(b(1, b())))", exp.toString());
	}

	@Test
	public void testMethod() {
		Rule rule = ExpRuleFactory.getDefaultRule();
		MapVariable<?, ?> var = new MapVariable<Object, Object>();
		var.setValue("a", new Object());
		Expression exp = rule.parse("1+a.abc()+2+a.def()*a.abc(1)+abc+abc()");
		exp.setVariable(var);
		assertEquals("1 + a.abc() + 2 + a.def() * a.abc(1) + abc + abc()", exp
				.toString());

		exp.refactorName(new RefactorFuncName(Object.class, "abc", "foo"));
		assertEquals("1 + a.foo() + 2 + a.def() * a.foo(1) + abc + abc()", exp
				.toString());
		exp.refactorName(new RefactorFuncName(Object.class, "def", "d"));
		assertEquals("1 + a.foo() + 2 + a.d() * a.foo(1) + abc + abc()", exp
				.toString());

		exp = rule.parse("a.a(a.f(a.a(1,a.a())))");
		exp.setVariable(var);
		assertEquals("a.a(a.f(a.a(1, a.a())))", exp.toString());
		exp.refactorName(new RefactorFuncName(Object.class, "a", "b"));
		assertEquals("a.b(a.f(a.b(1, a.b())))", exp.toString());
	}

	@Test
	public void testArray() {
		Rule rule = ExpRuleFactory.getDefaultRule();
		MapVariable<?, ?> var = new MapVariable<Object, Object>();
		var.setValue("a", new Long[10]);

		Expression exp = rule.parse("a[a[0]+1]");
		exp.setVariable(var);
		assertEquals("a[a[0] + 1]", exp.toString());
		exp.refactorName(new RefactorVarName(null, "a", "b"));
		assertEquals("b[b[0] + 1]", exp.toString());
	}

	@Test
	public void testType() {
		Rule rule = ExpRuleFactory.getDefaultRule();
		MapVariable<?, ?> var = new MapVariable<Object, Object>();
		var.setValue("a", new Long(10));
		var.setValue("b", new Integer(10));

		Expression exp = rule.parse("a.x=b.x");
		exp.setVariable(var);
		assertEquals("a.x = b.x", exp.toString());
		exp.refactorName(new RefactorVarName(Long.class, "x", "y"));
		assertEquals("a.y = b.x", exp.toString());
		exp.refactorName(new RefactorVarName(Integer.class, "x", "z"));
		assertEquals("a.y = b.z", exp.toString());
	}
}
