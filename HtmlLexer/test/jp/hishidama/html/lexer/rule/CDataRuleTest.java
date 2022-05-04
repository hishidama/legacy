package jp.hishidama.html.lexer.rule;

import static org.junit.Assert.*;

import java.io.IOException;

import jp.hishidama.html.lexer.rule.HtLexerRule.Chars;
import jp.hishidama.html.lexer.token.CData;

import org.junit.Test;

public class CDataRuleTest extends RuleTest {

	@Test
	public void testIsStart() throws IOException {
		HtLexer data = new HtLexer();
		CDataRule rule = data.getCDataRule();
		Chars cs = rule.new Chars();

		data.setTarget("<![CDATA[");
		assertTrue(rule.isStart(cs));
		cs.clear(0);

		data.setTarget("<![CDATA!");
		assertFalse(rule.isStart(cs));
	}

	@Test
	public void testParse() {
		assertValue("<![CDATA[テスト]]>", "<![", "CDATA", "テスト", "]]>", null);
		assertValue("<![CDATA[<>]>]]>", "<![", "CDATA", "<>]>", "]]>", null);
		assertValue("<![CDATA[あいうえお\r\nかきくけこ]]>", "<![", "CDATA",
				"あいうえお\r\nかきくけこ", "]]>", null);
	}

	@SuppressWarnings("deprecation")
	void assertValue(String value, String tag1, String name, String expected,
			String tag2, String rest) {
		HtLexer data = new HtLexer(value);
		CDataRule rule = data.getCDataRule();

		CData tag;
		try {
			tag = rule.parse();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		assertEquals(tag1, tag.getTag1());
		assertEquals(name, tag.getName());
		assertEquals(expected, tag.getData());
		assertEquals(tag2, tag.getTag2());

		assertEquals(tag.getText().length(), tag.getTextLength());

		String s = readRest(data);
		assertEquals(rest, s);
	}
}
