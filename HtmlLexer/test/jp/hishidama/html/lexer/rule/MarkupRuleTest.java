package jp.hishidama.html.lexer.rule;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class MarkupRuleTest {

	@Test
	public void testNotTag() throws IOException {
		HtLexer data = new HtLexer();
		MarkupRule rule = data.getMarkupRule();

		String expected = "/!?";
		for (int i = 0; i < expected.length(); i++) {
			data.setTarget("<" + expected.charAt(i) + "abc>");
			assertNotNull(rule.parse());
		}

		data.setTarget("<");
		assertNull(rule.parse());

		String test = "\"#$%&'()-=^~\\|@`[{;+:*]},<.>_" + "0123456789" + "‚ "
				+ " \t\r\n";
		for (int i = 0; i < test.length(); i++) {
			data.setTarget("<" + test.charAt(i) + "abc>");
			assertNull(rule.parse());
		}
	}
}
