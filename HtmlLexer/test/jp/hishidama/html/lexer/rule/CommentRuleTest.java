package jp.hishidama.html.lexer.rule;

import static org.junit.Assert.*;

import java.io.IOException;

import jp.hishidama.html.lexer.rule.CommentRule;
import jp.hishidama.html.lexer.rule.HtLexerRule.Chars;
import jp.hishidama.html.lexer.token.Comment;

import org.junit.Test;

public class CommentRuleTest extends RuleTest {

	@Test
	public void testIsStart() throws IOException {
		HtLexer data = new HtLexer();
		CommentRule rule = data.getCommentRule();
		Chars cs = rule.new Chars();

		data.setTarget("<!--abc");
		assertTrue(rule.isStart(cs));
		cs.clear(0);

		data.setTarget("<!-abc");
		assertFalse(rule.isStart(cs));
	}

	@Test
	public void testParse() {
		assertValue("<!--comment-->", "<!--", "comment", "-->", null);
		assertValue("<!-- comment -->", "<!--", " comment ", "-->", null);
		assertValue("<!--com\r\nment-->", "<!--", "com\r\nment", "-->", null);

		assertValue("<!---->", "<!--", null, "-->", null);
		assertValue("<!--<!--abc-->-->", "<!--", "<!--abc", "-->", "-->");
		assertValue("<!--cmt--><!---->", "<!--", "cmt", "-->", "<!---->");

		assertValue("<!--<p>test</p>-->", "<!--", "<p>test</p>", "-->", null);
	}

	@Test
	public void testError() {
		assertValue("<!--comment--", "<!--", "comment--", null, null);
		assertValue("<!--comment", "<!--", "comment", null, null);
		assertValue("<!--com--ment-->", "<!--", "com--ment", "-->", null);
		assertValue("<!----->", "<!--", "-", "-->", null);
		assertValue("<!------>", "<!--", "--", "-->", null);
		assertValue("<!------->", "<!--", "---", "-->", null);
	}

	@SuppressWarnings("deprecation")
	void assertValue(String value, String tag1, String expected, String tag2,
			String rest) {
		HtLexer data = new HtLexer(value);
		CommentRule rule = data.getCommentRule();

		Comment cmt;
		try {
			cmt = rule.parse();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		assertEquals(tag1, cmt.getTag1());
		assertEquals(expected, cmt.getComment());
		assertEquals(tag2, cmt.getTag2());

		assertEquals(cmt.getText().length(), cmt.getTextLength());

		String s = readRest(data);
		assertEquals(rest, s);
	}
}
