package jp.hishidama.html.lexer.rule;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import jp.hishidama.html.lexer.token.AttributeToken;
import jp.hishidama.html.lexer.token.CData;
import jp.hishidama.html.lexer.token.Comment;
import jp.hishidama.html.lexer.token.ListToken;
import jp.hishidama.html.lexer.token.MarkDeclare;
import jp.hishidama.html.lexer.token.SkipToken;
import jp.hishidama.html.lexer.token.Token;
import jp.hishidama.html.lexer.token.Tag;
import jp.hishidama.html.lexer.token.TextToken;
import jp.hishidama.html.lexer.token.ValueToken;
import jp.hishidama.html.lexer.token.XmlDeclare;

import org.junit.Test;

public class HtLexerTest {

	@Test
	public void testHtml1_0d0a() {
		StringBuilder sb = new StringBuilder(256);
		sb.append("<html>\r\n");
		sb.append("</html>\r\n");

		ListToken list = HtLexer.parse(sb.toString());
		list.calcLine(1);

		int n = 0, l = 1;
		assertTag(list, n++, "<", "html", ">", l);
		assertSkp(list, n++, "\r\n", l++);
		assertTag(list, n++, "</", "html", ">", l);
		assertSkp(list, n++, "\r\n", l);
		assertEquals(n, list.size());

		assertEquals(sb.toString(), getText(list));
	}

	@Test
	public void testHtml1_0a() {
		StringBuilder sb = new StringBuilder(256);
		sb.append("<html>\n");
		sb.append("</html>\n");

		ListToken list = HtLexer.parse(sb.toString());
		list.calcLine(1);

		int n = 0, l = 1;
		assertTag(list, n++, "<", "html", ">", l);
		assertSkp(list, n++, "\n", l++);
		assertTag(list, n++, "</", "html", ">", l);
		assertSkp(list, n++, "\n", l++);
		assertEquals(n, list.size());

		assertEquals(sb.toString(), getText(list));
	}

	@Test
	public void testHtml1_0d() {
		StringBuilder sb = new StringBuilder(256);
		sb.append("<html>\r");
		sb.append("</html>\r");

		ListToken list = HtLexer.parse(sb.toString());
		list.calcLine(1);

		int n = 0, l = 1;
		assertTag(list, n++, "<", "html", ">", l);
		assertSkp(list, n++, "\r", l++);
		assertTag(list, n++, "</", "html", ">", l);
		assertSkp(list, n++, "\r", l++);
		assertEquals(n, list.size());

		assertEquals(sb.toString(), getText(list));
	}

	@Test
	public void testHtml2() {
		StringBuilder sb = new StringBuilder(256);
		sb
				.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\r\n");
		sb.append("<html>\r\n");
		sb.append("<head>\r\n");
		sb.append("\t<title>タイトル</title>\r\n");
		sb.append("</head>\r\n");
		sb.append("<body>\r\n");
		sb.append("<!--コメント-->\r\n");
		sb.append("ボディー部\r\n");
		sb.append("2行目\r\n");
		sb.append("</body>\r\n");
		sb.append("</html>\r\n");

		ListToken list = HtLexer.parse(sb.toString());
		list.calcLine(1);

		int n = 0, l = 1;
		assertDcl(list, n++, "<!", "DOCTYPE", ">", l, "HTML", "PUBLIC",
				"\"-//W3C//DTD HTML 4.01 Transitional//EN\"");
		assertSkp(list, n++, "\r\n", l++);
		assertTag(list, n++, "<", "html", ">", l);
		assertSkp(list, n++, "\r\n", l++);
		assertTag(list, n++, "<", "head", ">", l);
		assertSkp(list, n++, "\r\n\t", l++);
		assertTag(list, n++, "<", "title", ">", l);
		assertTxt(list, n++, "タイトル", l);
		assertTag(list, n++, "</", "title", ">", l);
		assertSkp(list, n++, "\r\n", l++);
		assertTag(list, n++, "</", "head", ">", l);
		assertSkp(list, n++, "\r\n", l++);
		assertTag(list, n++, "<", "body", ">", l);
		assertSkp(list, n++, "\r\n", l++);
		assertCmt(list, n++, "<!--", "コメント", "-->", l);
		assertSkp(list, n++, "\r\n", l++);
		assertTxt(list, n++, "ボディー部\r\n2行目\r\n", l);
		l += 2;
		assertTag(list, n++, "</", "body", ">", l);
		assertSkp(list, n++, "\r\n", l++);
		assertTag(list, n++, "</", "html", ">", l);
		assertSkp(list, n++, "\r\n", l++);
		assertEquals(n, list.size());

		assertEquals(sb.toString(), getText(list));
	}

	@Test
	public void testXhtml() {
		StringBuilder sb = new StringBuilder(256);
		sb.append("<?xml version=\"1.0\" encoding=\"Shift_JIS\"?>\r\n");
		sb
				.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\"\r\n");
		sb
				.append("   \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\r\n");
		sb.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n");
		sb.append("<head>\r\n");
		sb.append("<title>タイトル</title>\r\n");
		sb.append("</head>\r\n");
		sb.append("<body>\r\n");
		sb.append("<!--コメント-->\r\n");
		sb.append("ボディー部<br />\r\n");
		sb.append("2行目\r\n");
		sb.append("</body>\r\n");
		sb.append("</html>\r\n");

		ListToken list = HtLexer.parse(sb.toString());
		list.calcLine(1);

		int n = 0, l = 1;
		assertXdc(list, n++, "<?", "xml", "?>", l, "version", "1.0",
				"encoding", "Shift_JIS");
		assertSkp(list, n++, "\r\n", l++);
		assertDcl(list, n++, "<!", "DOCTYPE", ">", l, "html", "PUBLIC",
				"\"-//W3C//DTD XHTML 1.0 Strict//EN\"",
				"\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\"");
		l++;
		assertSkp(list, n++, "\r\n", l++);
		assertTag(list, n++, "<", "html", ">", l);
		assertSkp(list, n++, "\r\n", l++);
		assertTag(list, n++, "<", "head", ">", l);
		assertSkp(list, n++, "\r\n", l++);
		assertTag(list, n++, "<", "title", ">", l);
		assertTxt(list, n++, "タイトル", l);
		assertTag(list, n++, "</", "title", ">", l);
		assertSkp(list, n++, "\r\n", l++);
		assertTag(list, n++, "</", "head", ">", l);
		assertSkp(list, n++, "\r\n", l++);
		assertTag(list, n++, "<", "body", ">", l);
		assertSkp(list, n++, "\r\n", l++);
		assertCmt(list, n++, "<!--", "コメント", "-->", l);
		assertSkp(list, n++, "\r\n", l++);
		assertTxt(list, n++, "ボディー部", l);
		assertTag(list, n++, "<", "br", "/>", l);
		assertSkp(list, n++, "\r\n", l++);
		assertTxt(list, n++, "2行目\r\n", l++);
		assertTag(list, n++, "</", "body", ">", l);
		assertSkp(list, n++, "\r\n", l++);
		assertTag(list, n++, "</", "html", ">", l);
		assertSkp(list, n++, "\r\n", l++);
		assertEquals(n, list.size());

		assertEquals(sb.toString(), getText(list));
	}

	@Test
	public void testCData() {
		StringBuilder sb = new StringBuilder(256);
		sb.append("<html>\r\n");
		sb.append("<![CDATA[\r\n");
		sb.append("\tif (n <= 1) { alert(\"</script>\"); }\r\n");
		sb.append("]]>\r\n");
		sb.append("</html>\r\n");

		ListToken list = HtLexer.parse(sb.toString());
		list.calcLine(1);

		int n = 0, l = 1;
		assertTag(list, n++, "<", "html", ">", l);
		assertSkp(list, n++, "\r\n", l++);
		assertCDt(list, n++, "<![", "CDATA",
				"\r\n\tif (n <= 1) { alert(\"</script>\"); }\r\n", "]]>", l);
		l += 2;
		assertSkp(list, n++, "\r\n", l++);
		assertTag(list, n++, "</", "html", ">", l);
		assertSkp(list, n++, "\r\n", l++);
		assertEquals(n, list.size());

		assertEquals(sb.toString(), getText(list));
	}

	@Test
	public void testScript() {
		StringBuilder sb = new StringBuilder(256);
		sb.append("<script>\r\n");
		sb.append("\tif (n <= 1) { alert(\"</script>\"); }\r\n");
		sb.append("</script>\r\n");

		ListToken list = HtLexer.parse(sb.toString());
		list.calcLine(1);

		int n = 0, l = 1;
		assertTag(list, n++, "<", "script", ">", l);
		assertTxt(list, n++, "\r\n\tif (n <= 1) { alert(\"</script>\"); }\r\n",
				l);
		l += 2;
		assertTag(list, n++, "</", "script", ">", l);
		assertSkp(list, n++, "\r\n", l++);
		assertEquals(n, list.size());

		assertEquals(sb.toString(), getText(list));
	}

	@Test
	public void testScript1() {
		StringBuilder sb = new StringBuilder(256);
		sb.append("<script src=\"test.js\"/><tag>\r\n");

		ListToken list = HtLexer.parse(sb.toString());
		list.calcLine(1);

		int n = 0, l = 1;
		assertTag(list, n++, "<", "script", "/>", l);
		assertTag(list, n++, "<", "tag", ">", l);
		assertSkp(list, n++, "\r\n", l++);
		assertEquals(n, list.size());

		assertEquals(sb.toString(), getText(list));
	}

	@Test
	public void testScriptComment() {
		StringBuilder sb = new StringBuilder(256);
		sb.append("<script>\r\n");
		sb.append("<!--\r\n");
		sb.append("\tif (n <= 1) { alert(\"</script>\"); }\r\n");
		sb.append("//-->\r\n");
		sb.append("</script>\r\n");

		ListToken list = HtLexer.parse(sb.toString());
		list.calcLine(1);

		int n = 0, l = 1;
		assertTag(list, n++, "<", "script", ">", l);
		assertTxt(list, n++, "\r\n", l++);
		assertCmt(list, n++, "<!--",
				"\r\n\tif (n <= 1) { alert(\"</script>\"); }\r\n//", "-->", l);
		l += 2;
		assertTxt(list, n++, "\r\n", l++);
		assertTag(list, n++, "</", "script", ">", l);
		assertSkp(list, n++, "\r\n", l++);
		assertEquals(n, list.size());

		assertEquals(sb.toString(), getText(list));
	}

	@Test
	public void testScriptCDATA() {
		StringBuilder sb = new StringBuilder(256);
		sb.append("<script>\r\n");
		sb.append("<![CDATA[\r\n");
		sb.append("\tif (n <= 1) { alert(\"</script>\"); }\r\n");
		sb.append("]]>\r\n");
		sb.append("</script>\r\n");

		ListToken list = HtLexer.parse(sb.toString());
		list.calcLine(1);

		int n = 0, l = 1;
		assertTag(list, n++, "<", "script", ">", l);
		assertTxt(list, n++, "\r\n", l++);
		assertCDt(list, n++, "<![", "CDATA",
				"\r\n\tif (n <= 1) { alert(\"</script>\"); }\r\n", "]]>", l);
		l += 2;
		assertTxt(list, n++, "\r\n", l++);
		assertTag(list, n++, "</", "script", ">", l);
		assertSkp(list, n++, "\r\n", l++);
		assertEquals(n, list.size());

		assertEquals(sb.toString(), getText(list));
	}

	@Test
	public void testLtGt() {
		StringBuilder sb = new StringBuilder(256);
		sb.append("<pre>\r\n");
		sb.append("\tfor(int i=0;i<256;i++){\r\n");
		sb.append("\t\tint r=((i>>5)&7)*255/7;\r\n");
		sb.append("\t\tint g=((i>>2)&7)*255/7;\r\n");
		sb.append("\t\tint b=((i>>0)&3)*255/3;\r\n");
		sb
				.append("\t\tCBrush bsh(<a href=\"color.html\">PALETTERGB</a>(r,g,b));\r\n");
		sb.append("</pre>\r\n");

		ListToken list = HtLexer.parse(sb.toString());
		list.calcLine(1);

		int n = 0, l = 1;
		assertTag(list, n++, "<", "pre", ">", l);
		assertSkp(list, n++, "\r\n\t", l++);
		assertTxt(list, n++, "for(int i=0;i<256;i++){\r\n"
				+ "\t\tint r=((i>>5)&7)*255/7;\r\n"
				+ "\t\tint g=((i>>2)&7)*255/7;\r\n"
				+ "\t\tint b=((i>>0)&3)*255/3;\r\n" + "\t\tCBrush bsh(", l);
		l += 4;
		assertTag(list, n++, "<", "a", ">", l);
		assertTxt(list, n++, "PALETTERGB", l);
		assertTag(list, n++, "</", "a", ">", l);
		assertTxt(list, n++, "(r,g,b));\r\n", l++);
		assertTag(list, n++, "</", "pre", ">", l);
		assertSkp(list, n++, "\r\n", l++);
		assertEquals(n, list.size());

		assertEquals(sb.toString(), getText(list));
	}

	@Test
	public void testAttrValue() throws IOException {
		@SuppressWarnings("resource")
		HtLexer lexer = new HtLexer("<test>");
		ValueToken value = lexer.parseAttrValue("\"", "\'");
		assertEquals("\"", value.getQuote1());
		assertEquals("\'", value.getQuote2());
		assertEquals("<test>", value.getValue());
	}

	@Test
	public void testSkip() throws IOException {
		@SuppressWarnings("resource")
		HtLexer lexer = new HtLexer("\r\n \t\r\n");
		SkipToken ss = lexer.parseSkip();
		assertEquals(3, ss.calcLine(1));
		assertEquals("\r\n \t\r\n", ss.getText());
	}

	@Test(expected = IOException.class)
	public void errorSkip() throws IOException {
		@SuppressWarnings("resource")
		HtLexer lexer = new HtLexer("\r\na\t\r\n");
		lexer.parseSkip();
		fail();
	}

	@Test
	public void testText() throws IOException {
		@SuppressWarnings("resource")
		HtLexer lexer = new HtLexer("<abc>テキスト\r\nとして解釈\r\n</abc>");
		TextToken tt = lexer.parseText();
		assertEquals(3, tt.calcLine(1));
		assertEquals("<abc>テキスト\r\nとして解釈\r\n</abc>", tt.getText());
	}

	void assertTag(ListToken list, int n, String tag1, String name,
			String tag2, int l) {
		Tag tag = (Tag) list.get(n);
		assertEquals(tag1, tag.getTag1());
		assertEquals(name, tag.getName());
		assertEquals(tag2, tag.getTag2());
		assertEquals(l, tag.getLine());
	}

	void assertSkp(ListToken list, int n, String expected, int l) {
		SkipToken skip = (SkipToken) list.get(n);
		assertEquals(expected, skip.getText());
		assertEquals(l, skip.getLine());
	}

	void assertTxt(ListToken list, int n, String expected, int l) {
		TextToken text = (TextToken) list.get(n);
		if (text instanceof SkipToken) {
			fail();
		}
		assertEquals(expected, text.getText());
		assertEquals(l, text.getLine());
	}

	void assertCmt(ListToken list, int n, String tag1, String text,
			String tag2, int l) {
		Comment cmt = (Comment) list.get(n);
		assertEquals(tag1, cmt.getTag1());
		assertEquals(text, cmt.getComment());
		assertEquals(tag2, cmt.getTag2());
		assertEquals(l, cmt.getLine());
	}

	void assertDcl(ListToken list, int n, String tag1, String name,
			String tag2, int l, String... args) {
		MarkDeclare mark = (MarkDeclare) list.get(n);
		assertEquals(tag1, mark.getTag1());
		assertEquals(name, mark.getName());
		assertEquals(tag2, mark.getTag2());
		assertEquals(l, mark.getLine());

		List<ValueToken> vlist = mark.getValueList();
		assertEquals(args.length, vlist.size());
		for (int i = 0; i < args.length; i++) {
			ValueToken v = vlist.get(i);
			assertEquals(args[i], v.getValue(true));
		}
	}

	void assertCDt(ListToken list, int n, String tag1, String name,
			String text, String tag2, int l) {
		CData cdata = (CData) list.get(n);
		assertEquals(tag1, cdata.getTag1());
		assertEquals(name, cdata.getName());
		assertEquals(text, cdata.getData());
		assertEquals(tag2, cdata.getTag2());
		assertEquals(l, cdata.getLine());
	}

	void assertXdc(ListToken list, int n, String tag1, String name,
			String tag2, int l, String... args) {
		XmlDeclare mark = (XmlDeclare) list.get(n);
		assertEquals(tag1, mark.getTag1());
		assertEquals(name, mark.getName());
		assertEquals(tag2, mark.getTag2());
		assertEquals(l, mark.getLine());

		List<AttributeToken> vlist = mark.getAttributeList();
		assertEquals(args.length / 2, vlist.size());
		for (int i = 0; i < args.length; i += 2) {
			AttributeToken v = vlist.get(i / 2);
			assertEquals(args[i], v.getName());
			assertEquals(args[i + 1], v.getValue());
		}
	}

	String getText(ListToken list) {
		StringBuilder sb = new StringBuilder(256);
		for (Token token : list) {
			token.writeTo(sb);
		}
		return sb.toString();
	}
}
