package jp.hishidama.html.lexer.rule;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.*;

import jp.hishidama.html.lexer.rule.TagRule;
import jp.hishidama.html.lexer.rule.HtLexerRule.Chars;
import jp.hishidama.html.lexer.token.AttributeToken;
import jp.hishidama.html.lexer.token.Tag;

import org.junit.Test;

public class TagRuleTest extends RuleTest {

	@Test
	public void testIsStart() throws IOException {
		@SuppressWarnings("resource")
		HtLexer data = new HtLexer();
		TagRule rule = data.getTagRule();
		Chars cs = rule.new Chars();

		for (int i = 1; i < 0x7f; i++) {
			cs.clear(0);
			char c = (char) i;
			if (c == '/')
				continue;
			String s = "<" + c + "abc>";
			data.setTarget(s);
			if (('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z')) {
				assertTrue(s, rule.isStart(cs));
			} else {
				assertFalse(s, rule.isStart(cs));
			}
		}

		for (int i = 1; i < 0x7f; i++) {
			cs.clear(0);
			char c = (char) i;
			String s = "</" + c + "abc>";
			data.setTarget(s);
			if (('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z') || c == '>') {
				assertTrue(s, rule.isStart(cs));
			} else {
				assertFalse(s, rule.isStart(cs));
			}
		}
	}

	@Test
	public void testParse() {
		assertValue("<br>", "<", "br", null, ">", null);
		assertValue("<p>", "<", "p", null, ">", null);
		assertValue("</p>", "</", "p", null, ">", null);
		assertValue("<br/>", "<", "br", null, "/>", null);
		assertValue("<br />", "<", "br", null, "/>", null);

		assertValue("<br clear>", "<", "br", createAttrList("clear"), ">", null);
		assertValue("<br clear/>", "<", "br", createAttrList("clear"), "/>",
				null);
		assertValue("<p align=center>", "<", "p",
				createAttrList("align=center"), ">", null);
		assertValue("<a href='#abc'>", "<", "a", createAttrList("href=#abc"),
				">", null);
		assertValue("<a class=\"ext\" href=\"http://zzz.com\">", "<", "a",
				createAttrList("class=ext", "href=http://zzz.com"), ">", null);
	}

	@Test
	public void testRest() {
		assertValue("<br><br>", "<", "br", null, ">", "<br>");
	}

	@Test
	public void testSpecial() {
		assertValue("</>", "</", "", null, ">", null);
		assertValue("<html", "<", "html", null, null, null);
		assertValue("<html ", "<", "html", null, null, null);
		assertValue("<br clear", "<", "br", createAttrList("clear"), null, null);
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testNotTag() throws IOException {
		@SuppressWarnings("resource")
		HtLexer data = new HtLexer();
		TagRule rule = data.getTagRule();

		data.setTarget("<");
		assertNull(rule.parse());
		data.setTarget("</");
		assertNull(rule.parse());
		data.setTarget("<>");
		assertNull(rule.parse());
	}

	static class AttrData {
		String name;
		String value;
	}

	List<AttrData> createAttrList(String... args) {
		List<AttrData> ret = new ArrayList<AttrData>(args.length);
		for (String s : args) {
			AttrData attr = new AttrData();
			int n = s.indexOf('=');
			if (n >= 0) {
				attr.name = s.substring(0, n);
				attr.value = s.substring(n + 1);
			} else {
				attr.name = s;
				attr.value = null;
			}
			ret.add(attr);
		}
		return ret;
	}

	@SuppressWarnings({ "deprecation", "resource" })
	void assertValue(String value, String tag1, String name,
			List<AttrData> expectedAttrs, String tag2, String rest) {
		HtLexer data = new HtLexer(value);
		TagRule rule = data.getTagRule();

		Tag tag;
		try {
			tag = rule.parse();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		assertEquals(tag1, tag.getTag1());
		assertEquals(name, tag.getName());
		assertEquals(tag2, tag.getTag2());

		if (expectedAttrs == null) {
			expectedAttrs = Collections.emptyList();
		}
		List<AttributeToken> attrs = tag.getAttributeList();
		assertEquals(expectedAttrs.size(), attrs.size());
		for (int i = 0; i < attrs.size(); i++) {
			AttributeToken attr = attrs.get(i);
			AttrData expected = expectedAttrs.get(i);
			assertEquals(expected.name, attr.getName());
			assertEquals(expected.value, attr.getValue());
		}

		assertEquals(tag.getText().length(), tag.getTextLength());

		String s = readRest(data);
		assertEquals(rest, s);
	}
}
