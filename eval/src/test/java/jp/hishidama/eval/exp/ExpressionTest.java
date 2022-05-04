package jp.hishidama.eval.exp;

import static org.junit.Assert.*;

import org.junit.Test;

import jp.hishidama.eval.Expression;
import jp.hishidama.eval.ExpRuleFactory;

/**
 * 式の整形のテスト.
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 */
public class ExpressionTest {

	@Test
	public void testToString() {
		assertNull(ExpRuleFactory.getDefaultRule().parse(null));
		assertToString("", "");
		assertToString("", " ");
		assertToString("123", "123");
		assertToString("-123", "-123");
		assertToString("1 + 2", "1+2");
		assertToString("1 + 2 + 3", "1+2+3");
		assertToString("4 * 5 / 6", "4*5/6");
		assertToString("1 + 2 * 3 - 4", "1+2*3-4");
		assertToString("(1)", "(1)");
		assertToString("((2))", "((2))");
		assertToString("((a))", "((a))");
		assertToString("(1 + 2) * (3 - 4)", "(1+2)*(3-4)");
		assertToString("(1 + 2 + 3) * (4 + 5 + 6) / ((7 + 8) + 9)",
				"(1+2+3)*(4+5+6)/((7+8)+9)");
		assertToString("(a == b) ? 1 : 2", "(a==b)? 1:2");
		assertToString("a ? (1 + 2) : 3", "a ? (1+2) : 3");
		assertToString("b ? 1 : (2 + 3)", "b ? 1 : 2+3");
		assertToString("((a == b) ? 0 : 1) ? 3 : 4", "((a==b)? 0:1)?3:4");
		assertToString("1, 2, 3", "1,2,3");
		assertToString("a = 1, b = 2, c = 3", "a=1 ,b=2 ,c=3");
		assertToString("a + ++i", "a+ ++i");
		assertToString("a - --i", "a- --i");
		assertToString("a + i++", "a+i++");
		assertToString("a - i--", "a-i--");
		assertToString("12 + + ++i", "12+ + ++i");
		assertToString("12 - - --i", "12- - --i");
		assertToString("abc()", "abc()");
		assertToString("abc(1)", "abc(1)");
		assertToString("abc(1, 2)", "abc(1,2)");
		assertToString("pow(cos(3), 2) + pow(sin(3), 2)",
				"pow(cos(3),2) + pow(sin(3),2)");
		assertToString("abc[1]", "abc[1]");
		assertToString("a[1][2]", "a[1][2]");
		assertToString("obj.member", "obj . member");
		assertToString("obj.func()", "obj . func ( )");
		assertToString("obj.func(1)", "obj . func (1 )");
		assertToString("obj.func(1, 2)", "obj . func (1 ,2)");
		assertToString("obj[1].func(1, 2, a)", "obj [  1 ] . func (1 ,2,a  )");
		assertToString("\"string\"", "\"string\"");
	}

	private void assertToString(String result, String input) {
		Expression exp = ExpRuleFactory.getDefaultRule().parse(input);
		String str = exp.toString();
		assertEquals(result, str);
	}

}
