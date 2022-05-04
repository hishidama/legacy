package jp.hishidama.html.lexer.rule;

import static org.junit.Assert.*;

import java.io.IOException;

import jp.hishidama.html.lexer.rule.TextRule;
import jp.hishidama.html.lexer.token.TextToken;

import org.junit.Test;

public class TextRuleTest extends RuleTest {

	@Test
	public void testParse() {
		assertValue("abc", "abc", null);
		assertValue("abc\r\n\tdef", "abc\r\n\tdef", null);
		assertValue("abc<", "abc<", null);
		assertValue("abc< ", "abc< ", null);
		assertValue("abc<> ", "abc<> ", null);
		assertValue("abc<def> ", "abc", "<def> ");
		assertValue("abc</> ", "abc", "</> ");
	}

	@Test
	public void testTagO() {
		assertValue("<", "<", null);
		assertValue("< ", "< ", null);
		assertValue("<> ", "<> ", null);

		assertValue("<= <a>", "<= ", "<a>");
		assertValue("<\" <a>", "<\" ", "<a>");
		assertValue("<\' <a>", "<\' ", "<a>");
		assertValue("<--<a>", "<--", "<a>");
	}

	@Test
	public void testEmpty() {
		assertValueNull("", null);
		assertValueNull("<def> ", "<def> ");
		assertValueNull("</> ", "</> ");
	}

	void assertValue(String value, String expected, String rest) {
		HtLexer data = new HtLexer(value);
		TextRule rule = data.getTopTextRule();

		TextToken text;
		try {
			text = rule.parse();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		assertEquals(expected, text.getText());

		assertEquals(expected.length(), text.getTextLength());

		String s = readRest(data);
		assertEquals(rest, s);
	}

	void assertValueNull(String value, String rest) {
		HtLexer data = new HtLexer(value);
		TextRule rule = data.getTopTextRule();

		TextToken text;
		try {
			text = rule.parse();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		assertNull(text);

		String s = readRest(data);
		assertEquals(rest, s);
	}
}
