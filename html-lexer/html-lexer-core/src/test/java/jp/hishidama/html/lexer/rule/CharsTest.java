package jp.hishidama.html.lexer.rule;

import static org.junit.Assert.*;
import static jp.hishidama.html.lexer.reader.CharType.*;

import java.io.IOException;

import jp.hishidama.html.lexer.reader.CharType;
import jp.hishidama.html.lexer.rule.HtLexerRule.Chars;

import org.junit.Test;

public class CharsTest extends RuleTest {

	@Test
	public void testParse() throws IOException {
		HtLexer data = new HtLexer();
		Chars cs = data.getMarkupRule().new Chars();

		data.setTarget("<!--abc-->");
		assertTrue(cs.isType(new CharType[] { TAGO, EXCL, HYPHEN, HYPHEN }));
		assertEquals("<!--", cs.clear(4).toString());
		assertEquals("abc-->", readRest(data));

		data.setTarget("<!-abc-->");
		assertFalse(cs.isType(new CharType[] { TAGO, EXCL, HYPHEN, HYPHEN }));
		assertTrue(cs.isType(new CharType[] { TAGO, EXCL }));
		assertEquals("<!", cs.clear(2).toString());
		assertEquals("-abc-->", readRest(data));
	}
}
