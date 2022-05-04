package jp.hishidama.html.lexer.rule;

import static org.junit.Assert.*;

import java.io.IOException;

import jp.hishidama.html.lexer.rule.AttrRule;
import jp.hishidama.html.lexer.token.AttributeToken;

import org.junit.Test;

public class AttrRuleTest extends RuleTest {

	@Test
	public void testParse() {
		assertValue("test=value", "test", null, "=", null, "value", null);
		assertValue("test =value", "test", " ", "=", null, "value", null);
		assertValue("test= value", "test", null, "=", " ", "value", null);
		assertValue("test = value", "test", " ", "=", " ", "value", null);
		assertValue("test = value ", "test", " ", "=", " ", "value", " ");
		assertValue("test = value>", "test", " ", "=", " ", "value", ">");
	}

	@Test
	public void testNameOnly() {
		assertValue("test", "test", null, null, null, null, null);
		assertValue("test ", "test", " ", null, null, null, null);
		assertValue("test>", "test", null, null, null, null, ">");
		assertValue("test >", "test", " ", null, null, null, ">");

		assertValue("test =", "test", " ", "=", null, null, null);
		assertValue("test =>", "test", " ", "=", null, null, ">");

		assertValue("-->", "--", null, null, null, null, ">");
	}

	@Test
	public void testSingleQuote() {
		assertValue("test='value'", "test", null, "=", null, "value", null);
		assertValue("test ='value'", "test", " ", "=", null, "value", null);
		assertValue("test= 'value'", "test", null, "=", " ", "value", null);
		assertValue("test = 'value'", "test", " ", "=", " ", "value", null);
		assertValue("test = 'value' ", "test", " ", "=", " ", "value", " ");
		assertValue("test = 'value'>", "test", " ", "=", " ", "value", ">");
	}

	@Test
	public void testDoubleQuote() {
		assertValue("test=\"value\"", "test", null, "=", null, "value", null);
		assertValue("test =\"value\"", "test", " ", "=", null, "value", null);
		assertValue("test= \"value\"", "test", null, "=", " ", "value", null);
		assertValue("test = \"value\"", "test", " ", "=", " ", "value", null);
		assertValue("test = \"value\" ", "test", " ", "=", " ", "value", " ");
		assertValue("test = \"value\">", "test", " ", "=", " ", "value", ">");
	}

	void assertValue(String value, String expectedName, String expectedSkip1,
			String expectedEq, String expectedSkip2, String expectedValue,
			String rest) {
		HtLexer data = new HtLexer(value);
		AttrRule rule = data.getTagRule().attrRule;

		AttributeToken attr;
		try {
			attr = rule.parse();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		assertEquals(expectedName, attr.getName());
		assertEquals(expectedSkip1, attr.getSkip1());
		assertEquals(expectedEq, attr.getLet());
		assertEquals(expectedSkip2, attr.getSkip2());
		assertEquals(expectedValue, attr.getValue());

		assertEquals(attr.getText().length(), attr.getTextLength());

		String s = readRest(data);
		assertEquals(rest, s);
	}
}
