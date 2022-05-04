package jp.hishidama.ant.types.htlex.eval;

import static org.junit.Assert.*;
import jp.hishidama.eval.Expression;
import jp.hishidama.eval.Rule;

import org.apache.tools.ant.Project;
import org.junit.Test;

public class HtLexerExpRuleTest {

	@Test
	public void testCreateFieldExpression() {

		Project project = new Project();

		HtLexerExpRuleFactory factory = new HtLexerExpRuleFactory();
		factory.setProject(project);
		Rule rule = factory.getRule();

		String str = "abc.def + ghi.def";
		System.out.println("式：" + str);

		project.setProperty("abc.def", "123");
		project.setProperty("ghi.def", "456");

		Expression exp = rule.parse(str);
		long ret = exp.evalLong();

		System.out.println("long= " + ret);
		assertEquals(579, ret);

		Object r = exp.eval();
		System.out.println("obj = " + r);
		assertEquals("123456", r);
	}

}
