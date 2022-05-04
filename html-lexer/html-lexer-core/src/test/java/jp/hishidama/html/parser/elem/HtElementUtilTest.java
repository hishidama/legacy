package jp.hishidama.html.parser.elem;

import static org.junit.Assert.*;

import jp.hishidama.html.lexer.rule.HtLexer;
import jp.hishidama.html.lexer.token.ListToken;
import jp.hishidama.html.parser.rule.HtParser;
import jp.hishidama.html.parser.rule.HtParserManager;

import org.junit.Test;

public class HtElementUtilTest {

	@Test
	public void testSplitFirstSkip() {
		HtListElement elist = createHtElement("<p>\r\nabc\r\n</p>");
		HtElementUtil.splitFirstSkip((HtTagElement) elist.get(0));
		assertEquals("\r\n<p>abc\r\n</p>", elist.getText());

		elist = createHtElement("<p>\r\n\t</p>");
		HtElementUtil.splitFirstSkip((HtTagElement) elist.get(0));
		assertEquals("\r\n\t<p></p>", elist.getText());

		elist = createHtElement("<p>\r\n</p>");
		HtElementUtil.splitFirstSkip((HtTagElement) elist.get(0));
		assertEquals("\r\n<p></p>", elist.getText());

		elist = createHtElement("<p>\t\r\n\taaa</p>");
		HtElementUtil.splitFirstSkip((HtTagElement) elist.get(0));
		assertEquals("\t\r\n\t<p>aaa</p>", elist.getText());

		elist = createHtElement("<p>abc</p>");
		HtElementUtil.splitFirstSkip((HtTagElement) elist.get(0));
		assertEquals("<p>abc</p>", elist.getText());

		elist = createHtElement("<p></p>");
		HtElementUtil.splitFirstSkip((HtTagElement) elist.get(0));
		assertEquals("<p></p>", elist.getText());
	}

	@Test
	public void testSplitLSkip() {
		HtListElement elist = createHtElement("<p>\r\nabc\r\n</p>");
		HtElementUtil.splitLastSkip((HtTagElement) elist.get(0));
		assertEquals("<p>\r\nabc</p>\r\n", elist.getText());

		elist = createHtElement("<p>aaa\r\n\t</p>");
		HtElementUtil.splitLastSkip((HtTagElement) elist.get(0));
		assertEquals("<p>aaa</p>\r\n\t", elist.getText());

		elist = createHtElement("<p>\r\n\t</p>");
		HtElementUtil.splitLastSkip((HtTagElement) elist.get(0));
		assertEquals("<p></p>\r\n\t", elist.getText());

		elist = createHtElement("<p>\r\n</p>");
		HtElementUtil.splitLastSkip((HtTagElement) elist.get(0));
		assertEquals("<p></p>\r\n", elist.getText());

		elist = createHtElement("<p>abc</p>");
		HtElementUtil.splitLastSkip((HtTagElement) elist.get(0));
		assertEquals("<p>abc</p>", elist.getText());

		elist = createHtElement("<p></p>");
		HtElementUtil.splitLastSkip((HtTagElement) elist.get(0));
		assertEquals("<p></p>", elist.getText());
	}

	protected HtListElement createHtElement(String s) {
		ListToken tlist = HtLexer.parse(s);

		HtParser parser = new HtParserManager().getDefaultParser();
		return parser.parse(tlist);
	}
}
