package jp.hishidama.html.lexer.rule;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.*;

import jp.hishidama.html.lexer.rule.HtLexerRule.Chars;
import jp.hishidama.html.lexer.token.AttributeToken;
import jp.hishidama.html.lexer.token.XmlDeclare;

import org.junit.Test;

public class XmlDeclRuleTest extends RuleTest {

	@Test
	public void testIsStart() throws IOException {
		HtLexer data = new HtLexer();
		XmlDeclRule rule = data.getXmlDeclRule();
		Chars cs = rule.new Chars();

		data.setTarget("<?tag");
		assertTrue(rule.isStart(cs));
		cs.clear(0);

		data.setTarget("</tag");
		assertFalse(rule.isStart(cs));
	}

	@Test
	public void testParse() {
		assertValue("<?name?>", "<?", "name", null, "?>", null);

		assertValue("<?xml version=\"1.0\" encoding=\"Shift_JIS\"?>", "<?",
				"xml", createAttrList("version=1.0", "encoding=Shift_JIS"),
				"?>", null);
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

	@SuppressWarnings("deprecation")
	void assertValue(String value, String tag1, String name,
			List<AttrData> expectedAttrs, String tag2, String rest) {
		HtLexer data = new HtLexer(value);
		XmlDeclRule rule = data.getXmlDeclRule();

		XmlDeclare tag;
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
