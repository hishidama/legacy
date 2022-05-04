package jp.hishidama.html.lexer.rule;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.*;

import jp.hishidama.html.lexer.rule.MarkDeclRule;
import jp.hishidama.html.lexer.rule.HtLexerRule.Chars;
import jp.hishidama.html.lexer.token.MarkDeclare;
import jp.hishidama.html.lexer.token.ValueToken;

import org.junit.Test;

public class MarkDeclRuleTest extends RuleTest {

	@Test
	public void testIsStart() throws IOException {
		HtLexer data = new HtLexer();
		MarkDeclRule rule = data.getDeclareRule();
		Chars cs = rule.new Chars();

		data.setTarget("<!tag");
		assertTrue(rule.isStart(cs));
		cs.clear(0);

		data.setTarget("</tag");
		assertFalse(rule.isStart(cs));
	}

	@Test
	public void testParse() {
		assertValue("<!DOCTYPE>", "<!", "DOCTYPE", null, ">", null);
		assertValue("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0//EN\">",
				"<!", "DOCTYPE", createValueList("HTML", "PUBLIC",
						"\"-//W3C//DTD HTML 4.0//EN\""), ">", null);
		assertValue(
				"<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0//EN\"\r\n \"http://www.w3.org/TR/REC-html40/strict.dtd\">\r\n",
				"<!", "DOCTYPE", createValueList("HTML", "PUBLIC",
						"\"-//W3C//DTD HTML 4.0//EN\"",
						"\"http://www.w3.org/TR/REC-html40/strict.dtd\""), ">",
				"\r\n");
	}

	List<String> createValueList(String... args) {
		return Arrays.asList(args);
	}

	@SuppressWarnings("deprecation")
	void assertValue(String value, String tag1, String name,
			List<String> expectedValues, String tag2, String rest) {
		HtLexer data = new HtLexer(value);
		MarkDeclRule rule = data.getDeclareRule();

		MarkDeclare mark;
		try {
			mark = rule.parse();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		assertEquals(tag1, mark.getTag1());
		assertEquals(name, mark.getName());
		assertEquals(tag2, mark.getTag2());

		if (expectedValues == null) {
			expectedValues = Collections.emptyList();
		}
		List<ValueToken> values = mark.getValueList();
		assertEquals(expectedValues.size(), values.size());
		for (int i = 0; i < values.size(); i++) {
			ValueToken v = values.get(i);
			String expected = expectedValues.get(i);
			assertEquals(expected, v.getValue(true));
		}

		assertEquals(mark.getText().length(), mark.getTextLength());

		String s = readRest(data);
		assertEquals(rest, s);
	}
}
