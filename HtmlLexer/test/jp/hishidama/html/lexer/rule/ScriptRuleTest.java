package jp.hishidama.html.lexer.rule;

import static org.junit.Assert.*;

import java.io.IOException;

import jp.hishidama.html.lexer.token.TextToken;

import org.junit.Test;

public class ScriptRuleTest extends RuleTest {

	@Test
	public void testParse() {
		assertValue("abc", "abc", null);
		assertValue("abc</script>", "abc", "</script>");
		assertValue("abc\r\ndef</script>", "abc\r\ndef", "</script>");

		assertValue("abc<tag></script>", "abc<tag>", "</script>");
		assertValue("abc</tag></script>", "abc</tag>", "</script>");
	}

	@Test
	public void testParseSQ() {
		assertValue("var v='abc'", "var v='abc'", null);
		assertValue("var v='abc'</script>", "var v='abc'", "</script>");
		assertValue("var v='abc\r\ndef'", "var v='abc\r\ndef'", null);
		assertValue("var v='</script>'", "var v='</script>'", null);
		assertValue("var v='\\</script>'", "var v='\\</script>'", null);
		assertValue("var v='abc\"</script>def'", "var v='abc\"</script>def'",
				null);
		assertValue("var v='abc\\'</script>def'", "var v='abc\\'</script>def'",
				null);
		assertValue("var v='abc\\\r\n\\'</script>'",
				"var v='abc\\\r\n\\'</script>'", null);
		assertValue("var v='a\\nbc'</script>", "var v='a\\nbc'", "</script>");
	}

	@Test
	public void testParseDQ() {
		assertValue("var v=\"abc\"", "var v=\"abc\"", null);
		assertValue("var v=\"abc\"</script>", "var v=\"abc\"", "</script>");
		assertValue("var v=\"abc\r\ndef\"", "var v=\"abc\r\ndef\"", null);
		assertValue("var v=\"</script>\"", "var v=\"</script>\"", null);
		assertValue("var v=\"\\</script>\"", "var v=\"\\</script>\"", null);
		assertValue("var v=\"abc'</script>def\"", "var v=\"abc'</script>def\"",
				null);
		assertValue("var v=\"abc\\\"</script>def\"",
				"var v=\"abc\\\"</script>def\"", null);
		assertValue("var v=\"abc\\\r\n\\\"</script>\"",
				"var v=\"abc\\\r\n\\\"</script>\"", null);
		assertValue("var v=\"a\\nbc\"</script>", "var v=\"a\\nbc\"",
				"</script>");
	}

	@Test
	public void testComment() {
		assertValueNull("<!-- v--; --></script>", "<!-- v--; --></script>");
		assertValue("v++;<!--", "v++;", "<!--");
	}

	@Test
	public void testCDATA() {
		assertValueNull("<![CDATA[v--;]]></script>",
				"<![CDATA[v--;]]></script>");
		assertValue("v++;<![CDATA[", "v++;", "<![CDATA[");
	}

	void assertValue(String value, String expected, String rest) {
		HtLexer data = new HtLexer(value);
		ScriptRule rule = data.getScriptRule();

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
		ScriptRule rule = data.getScriptRule();

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
