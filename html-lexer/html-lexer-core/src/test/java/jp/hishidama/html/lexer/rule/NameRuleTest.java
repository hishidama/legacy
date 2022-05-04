package jp.hishidama.html.lexer.rule;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import jp.hishidama.html.lexer.rule.NameRule;
import jp.hishidama.html.lexer.token.Token;

public class NameRuleTest extends RuleTest {

	@Test
	public void testParse() {
		assertValue("test", "test", null);
		assertValue("test ", "test", " ");
		assertValue("test\r\n", "test", "\r\n");
		assertValue("test=", "test", "=");
		assertValue("test>", "test", ">");
		assertValue("test/ ", "test/", " ");
		assertValue("test/>", "test", "/>");
		assertValue("test? ", "test?", " ");
		assertValue("test! ", "test!", " ");

		assertValueNull("", null);
	}

	@SuppressWarnings("resource")
	void assertValue(String value, String expected, String rest) {
		HtLexer data = new HtLexer(value);
		NameRule rule = data.getTagRule().attrRule.nameRule;

		Token token;
		try {
			token = rule.parse();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		StringBuilder sb = new StringBuilder();
		token.writeTo(sb);
		assertEquals(expected, sb.toString());

		assertEquals(expected.length(), token.getTextLength());

		String s = readRest(data);
		assertEquals(rest, s);
	}

	@SuppressWarnings("resource")
	void assertValueNull(String value, String rest) {
		HtLexer data = new HtLexer(value);
		NameRule rule = data.getTagRule().attrRule.nameRule;

		Token token;
		try {
			token = rule.parse();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		assertNull(token);

		String s = readRest(data);
		assertEquals(rest, s);
	}
}
