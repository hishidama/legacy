package jp.hishidama.html.lexer.rule;

import static org.junit.Assert.*;

import java.io.IOException;

import jp.hishidama.html.lexer.rule.ValueRule;
import jp.hishidama.html.lexer.token.ValueToken;

import org.junit.Test;

public class ValueRuleTest extends RuleTest {

	@Test
	public void testParseString() {
		assertValue("test", "test", null);
		assertValue("test ", "test", " ");
		assertValue("test\r\n", "test", "\r\n");
		assertValue("test/ ", "test/", " ");
		assertValue("test/>", "test", "/>");
		assertValue("test? ", "test?", " ");
		assertValue("test! ", "test!", " ");

		assertValue("--def", "--def", null);
		assertValue("abc--def", "abc--def", null);
	}

	@Test
	public void testParseSQ() {
		assertValue("'test'", "test", null, "'");
		assertValue("'test ' ", "test ", " ", "'");
		assertValue("'test\r\n' ", "test\r\n", " ", "'");
		assertValue("''", "", null, "'");

		assertValue("'--def'", "--def", null, "'");
		assertValue("'abc--def'", "abc--def", null, "'");
	}

	@Test
	public void testParseDQ() {
		assertValue("\"test\"", "test", null, "\"");
		assertValue("\"test \" ", "test ", " ", "\"");
		assertValue("\"test\r\n\" ", "test\r\n", " ", "\"");
		assertValue("\"\"", "", null, "\"");

		assertValue("\"--def\"", "--def", null, "\"");
		assertValue("\"abc--def\"", "abc--def", null, "\"");
	}

	void assertValue(String value, String expected, String rest) {
		assertValue(value, expected, rest, "");
	}

	@SuppressWarnings("resource")
	void assertValue(String value, String expected, String rest, String q) {
		HtLexer data = new HtLexer(value);
		ValueRule rule = data.getTagRule().attrRule.valueRule;

		ValueToken token;
		try {
			token = rule.parse();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		assertEquals(expected, token.getValue());
		String expectedq = q + expected + q;
		assertEquals(expectedq, token.getText());

		assertEquals(expectedq.length(), token.getTextLength());

		String s = readRest(data);
		assertEquals(rest, s);
	}
}
