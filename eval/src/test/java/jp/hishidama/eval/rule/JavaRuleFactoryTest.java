package jp.hishidama.eval.rule;

import static org.junit.Assert.*;

import org.junit.Test;

import jp.hishidama.eval.EvalException;
import jp.hishidama.eval.Rule;

/**
 * Javaルールのテスト.
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 */
public class JavaRuleFactoryTest {

	protected Rule rule = JavaRuleFactory.getJavaRule();

	@Test
	public void testRule() {
		assertEquals("2 * 3", rule.parse("2*3").toString());
		assertError("2**2");
		assertError("a**=2");
		assertError("1,2,3");
	}

	protected void assertError(String str) {
		try {
			rule.parse(str);
		} catch (EvalException e) {
			return; // OK
		}
		fail("例外が起きるはず");
	}

}
