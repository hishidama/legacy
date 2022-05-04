package jp.hishidama.eval;

import java.util.*;

import static org.junit.Assert.*;

import org.junit.Test;

import jp.hishidama.eval.exp.*;
import jp.hishidama.eval.lex.Lex;
import jp.hishidama.eval.lex.LexFactory;
import jp.hishidama.eval.oper.DoubleOperator;
import jp.hishidama.eval.oper.LongOperator;
import jp.hishidama.eval.rule.ShareRuleValue;
import jp.hishidama.eval.var.MapVariable;

/**
 * 例外のテスト.
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 */
public class EvalExceptionTest {

	Rule rule = new TestRuleFactory().getRule();

	@Test
	public void testRule() {
		assertParse(EvalException.PARSE_NOT_FOUND_END_OP, "(1]");
		assertParse(EvalException.PARSE_NOT_FOUND_END_OP, "test(1");
		assertParse(EvalException.PARSE_NOT_FOUND_END_OP, "arr[1");
		assertParse(EvalException.PARSE_NOT_FOUND_END_OP, "b?1");
		assertParse(EvalException.PARSE_INVALID_OP, "* 2");
		assertParse(EvalException.PARSE_END_OF_STR, "1*");
		assertParse(EvalException.PARSE_INVALID_CHAR, "謎タイプ"); // 独自字句解析
		assertParse(EvalException.PARSE_STILL_EXIST, "a+b c");
	}

	protected void assertParse(int expected, String str) {
		Expression exp;
		try {
			exp = rule.parse(str);
		} catch (EvalException e) {
			System.out.println(e);
			// e.printStackTrace();
			assertEquals(expected, e.getErrorCode());
			return;
		}
		fail(exp.toString());
	}

	@Test
	public void testSetWord() {
		AbstractExpression exp = new PlusExpression() {
			@Override
			public String toString() {
				super.setWord("err");
				return super.toString();
			}
		};
		try {
			exp.toString();
		} catch (EvalException e) {
			assertEquals(EvalException.EXP_FORBIDDEN_CALL, e.getErrorCode());
			return;
		}
		fail();
	}

	@Test
	public void testEval() {
		assertEvalLong(EvalException.EXP_NOT_LET, "1=2", null);
		assertEvalLong(EvalException.EXP_NOT_VARIABLE, "1[0]", null);
	}

	@Test
	public void testNumber() {
		assertEvalLong(EvalException.EXP_NOT_NUMBER, "0z", null);
		assertEvalDobl(EvalException.EXP_NOT_NUMBER, "0z", null);
		assertEvalObje(EvalException.EXP_NOT_NUMBER, "0z", null);
		CharExpression c = new CharExpression(null);
		c.share = new ShareExpValue();
		assertEvalLong(EvalException.EXP_NOT_CHAR, c);
		assertEvalDobl(EvalException.EXP_NOT_CHAR, c);
		StringExpression s = new StringExpression("zzz");
		s.share = new ShareExpValue();
		assertEvalLong(EvalException.EXP_NOT_NUMBER, s);
		assertEvalDobl(EvalException.EXP_NOT_NUMBER, s);
	}

	@Test
	public void testFunc() {
		assertParse(EvalException.PARSE_NOT_FUNC, "1()");
		assertEvalLong(EvalException.EXP_FUNC_CALL_ERROR, "test()", null);
		assertEvalDobl(EvalException.EXP_FUNC_CALL_ERROR, "test()", null);
		assertEvalObje(EvalException.EXP_FUNC_CALL_ERROR, "test()", null);
	}

	@Test
	public void testVar() {
		Map<String, Object> var = new HashMap<String, Object>();
		var.put("str", "abc");
		assertEvalLong(EvalException.EXP_NOT_NUMBER, "str", var);
		assertEvalDobl(EvalException.EXP_NOT_NUMBER, "str", var);
		assertEvalLong(EvalException.EXP_NOT_LET_VAR, rule.parse("var=1").ae);
		assertEvalLong(EvalException.EXP_NOT_VAR_VALUE, rule.parse("var").ae);
		assertEvalDobl(EvalException.EXP_NOT_DEF_VAR, "n", var);
		assertEvalLong(EvalException.EXP_NOT_VARIABLE, rule.parse("a.x").ae);
	}

	@Test
	public void testArray() {
		Map<String, Object> var = new HashMap<String, Object>();
		var.put("str", new String[] { "abc" });
		var.put("arr", new long[1]);
		assertEvalLong(EvalException.EXP_NOT_NUMBER, "str[0]", var);
		assertEvalDobl(EvalException.EXP_NOT_NUMBER, "str[0]", var);
		assertEvalLong(EvalException.EXP_NOT_DEF_OBJ, "b[0]", var);
		assertEvalLong(EvalException.EXP_NOT_ARR_VALUE, "arr[-1]", var);
		assertEvalLong(EvalException.EXP_NOT_DEF_OBJ, "b[0]=1", var);
		assertEvalLong(EvalException.EXP_NOT_LET_ARR, "str[0]=1", var);
	}

	@Test
	public void testField() {
		Map<String, Object> var = new HashMap<String, Object>();
		var.put("f", new FieldTestA(1));
		assertEvalLong(EvalException.EXP_NOT_NUMBER, "f.s", var);
		assertEvalDobl(EvalException.EXP_NOT_NUMBER, "f.s", var);
		assertEvalLong(EvalException.EXP_NOT_DEF_OBJ, "n.s", var);
		assertEvalLong(EvalException.EXP_NOT_FLD_VALUE, "f.null", var);
		assertEvalLong(EvalException.EXP_NOT_DEF_OBJ, "n.s=1", var);
		assertEvalLong(EvalException.EXP_NOT_LET_FIELD, "f.null=1", var);
	}

	protected void assertEvalLong(int expected, String str,
			Map<String, Object> var) {
		Expression exp = rule.parse(str);
		if (var != null) {
			exp.setVariable(new MapVariable<String, Object>(var));
		}
		assertEvalLong(expected, exp.ae);
	}

	protected void assertEvalLong(int expected, AbstractExpression exp) {
		exp.share.setOperator(new LongOperator());
		try {
			System.out.println(exp.eval());
		} catch (EvalException e) {
			System.out.println(e);
			assertEquals(expected, e.getErrorCode());
			return;
		}
		fail(exp.toString());
	}

	protected void assertEvalDobl(int expected, String str,
			Map<String, Object> var) {
		Expression exp = rule.parse(str);
		if (var != null) {
			exp.setVariable(new MapVariable<String, Object>(var));
		}
		assertEvalDobl(expected, exp.ae);
	}

	protected void assertEvalDobl(int expected, AbstractExpression exp) {
		exp.share.setOperator(new DoubleOperator());
		try {
			System.out.println(exp.eval());
		} catch (EvalException e) {
			System.out.println(e);
			assertEquals(expected, e.getErrorCode());
			return;
		}
		fail(exp.toString());
	}

	protected void assertEvalObje(int expected, String str,
			Map<String, Object> var) {
		Expression exp = rule.parse(str);
		if (var != null) {
			exp.setVariable(new MapVariable<String, Object>(var));
		}
		assertEvalObje(expected, exp.ae);
	}

	protected void assertEvalObje(int expected, AbstractExpression exp) {
		try {
			System.out.println(exp.eval());
		} catch (EvalException e) {
			System.out.println(e);
			assertEquals(expected, e.getErrorCode());
			return;
		}
		fail(exp.toString());
	}

	@Test
	public void testMessage() {
		assertMessage("演算子の文法エラーです。", EvalException.PARSE_INVALID_OP, null);
		assertMessage("演算子「)」が在りません。", EvalException.PARSE_NOT_FOUND_END_OP,
				new String[] { ")" });
	}

	protected void assertMessage(String expected, int code, String[] opt) {
		EvalException e;
		e = new EvalException(code, null, null, null, -1, null);
		e.msgOpt = opt;
		assertEquals(expected, e.toString());
		e = new EvalException(code, "test", "単語", null, -1, null);
		e.msgOpt = opt;
		assertEquals(expected + " test=「単語」", e.toString());
		e = new EvalException(code, null, null, "文字列", -1, null);
		e.msgOpt = opt;
		assertEquals(expected + " string=「文字列」", e.toString());
		e = new EvalException(code, null, null, null, 0, null);
		e.msgOpt = opt;
		assertEquals(expected + " pos=0", e.toString());
		e = new EvalException(code, null, null, null, -1,
				new NullPointerException());
		e.msgOpt = opt;
		assertEquals(expected + " cause by java.lang.NullPointerException", e
				.toString());
		e = new EvalException(code, null, "文字列", "文字列", -1, null);
		e.msgOpt = opt;
		assertEquals(expected + " string=「文字列」", e.toString());
		e = new EvalException(code, "word", "単語", "文字列", 1, null);
		e.msgOpt = opt;
		assertEquals(expected + " word=「単語」 pos=1 string=「文字列」", e.toString());

		assertEquals(String.valueOf(code), EvalExceptionFormatter.getDefault()
				.toString(e, "%c"));
	}
}

class TestLex extends Lex {

	protected TestLex(String str, List<String>[] lists,
			AbstractExpression paren, ShareExpValue exp) {
		super(str, lists, paren, exp);
	}

	@Override
	public void check() {
		super.check();
		if (string.equals("謎タイプ")) {
			type = TYPE_ERR;
		}
	}
}

class TestLexFactory extends LexFactory {

	@Override
	public Lex create(String str, List<String>[] opeList, ShareRuleValue share,
			ShareExpValue exp) {
		return new TestLex(str, opeList, share.paren, exp);
	}
}

class TestRuleFactory extends ExpRuleFactory {

	@Override
	protected LexFactory getLexFactory() {
		if (defaultLexFactory == null) {
			defaultLexFactory = new TestLexFactory();
		}
		return defaultLexFactory;
	}
}