package jp.hishidama.html.parser;

import static org.junit.Assert.*;

import jp.hishidama.html.lexer.rule.HtLexer;
import jp.hishidama.html.lexer.token.ListToken;
import jp.hishidama.html.lexer.token.Token;
import jp.hishidama.html.parser.elem.HtElement;
import jp.hishidama.html.parser.elem.HtElementUtil;
import jp.hishidama.html.parser.elem.HtListElement;
import jp.hishidama.html.parser.elem.HtTagElement;
import jp.hishidama.html.parser.rule.HtParser;
import jp.hishidama.html.parser.rule.HtParserManager;

import org.junit.Test;

public class HtParserTest {

	@Test
	public void testDefaultParser() {
		HtListElement elist;
		TagData data;

		elist = assertParse("<table><tr><th>h1<th>h2<tr><td>d1<td>d2</table>");
		data = new TagData("table", true,
				true, //
				new TagData("tr", true,
						false, //
						new TagData("th", true, false, new TagData("h1")),
						new TagData("th", true, false, new TagData("h2"))),
				new TagData("tr", true,
						false, //
						new TagData("td", true, false, new TagData("d1")),
						new TagData("td", true, false, new TagData("d2"))));
		assertData(data, elist);

		elist = assertParse("<table><tr><th>h1<th>h2<tr><td>d1<td>d2<p></table>");
		data = new TagData("table", true,
				true, //
				new TagData("tr", true,
						false, //
						new TagData("th", true, false, new TagData("h1")),
						new TagData("th", true, false, new TagData("h2"))),
				new TagData("tr", true,
						false, //
						new TagData("td", true, false, new TagData("d1")),
						new TagData("td", true, false, new TagData("d2"),
								new TagData("p", true, false))));
		assertData(data, elist);

		elist = assertParse("<table><tr><th>h1<th>h2<tr><td>d1<td>d2<p>zzz</table>");
		data = new TagData("table", true,
				true, //
				new TagData("tr", true,
						false, //
						new TagData("th", true, false, new TagData("h1")),
						new TagData("th", true, false, new TagData("h2"))),
				new TagData("tr", true,
						false, //
						new TagData("td", true, false, new TagData("d1")),
						new TagData("td", true, false, new TagData("d2"),
								new TagData("p", true, false,
										new TagData("zzz")))));
		assertData(data, elist);
	}

	@Test
	public void html() {
		HtListElement elist = assertParse("<html></html>");
		TagData data = new TagData("html", true, true);
		assertData(data, elist);

		elist = assertParse("<html>abc");
		data = new TagData("html", true, false, new TagData("abc"));
		assertData(data, elist);
	}

	@Test
	public void p() {
		HtListElement elist;
		TagData data;

		elist = assertParse("<p>aaa</p>");
		data = new TagData("p", true, true, new TagData("aaa"));
		assertData(data, elist);

		elist = assertParse("<body><p>aaa<p>bbb</body>");
		data = new TagData("body", true, true,//
				new TagData("p", true, false, new TagData("aaa")),//
				new TagData("p", true, false, new TagData("bbb")));
		assertData(data, elist);

		elist = assertParse("<body>aaa<p>bbb<p></body>");
		data = new TagData("body", true, true,//
				new TagData("aaa"),//
				new TagData("p", true, false, new TagData("bbb")),//
				new TagData("p", true, false));
		assertData(data, elist);

		elist = assertParse("<p><center><img src='zzz'></center></p>");
		data = new TagData("p", true, true, //
				new TagData("center", true, true, new TagData("img", true,
						false)));
		assertData(data, elist);

		elist = assertParse("<body><p>aaa<center><img src='zzz'></center></body>");
		data = new TagData("body", true, true,//
				new TagData("p", true, false, new TagData("aaa")), //
				new TagData("center", true, true, new TagData("img", true,
						false)));
		assertData(data, elist);
	}

	protected HtListElement assertParse(String expected) {
		ListToken tlist = HtLexer.parse(expected);

		HtParser parser = new HtParserManager().getDefaultParser();
		HtListElement elist = parser.parse(tlist);
		try {
			assertEquals(expected, elist.getText());

			HtTagElement tag = (HtTagElement) elist.get(0);
			assertTrue(tag.isFix());

			assertListToken(tlist, HtElementUtil.toToken(elist));

			return elist;

		} catch (RuntimeException e) {
			HtElementUtil.dumpTree(elist, -1);
			throw e;
		} catch (Error e) {
			HtElementUtil.dumpTree(elist, -1);
			throw e;
		}
	}

	protected void assertListToken(ListToken elist, ListToken tlist) {
		assertEquals(elist.size(), tlist.size());
		for (int i = 0; i < elist.size(); i++) {
			Token e = elist.get(i);
			Token t = tlist.get(i);
			assertSame(e, t);
		}
	}

	protected void assertData(TagData expected, HtElement elist) {
		TagData data = new TagData(null, false, false, expected);
		try {
			data.assertData(elist);
		} catch (RuntimeException e) {
			HtElementUtil.dumpTree(elist, -1);
			throw e;
		} catch (Error e) {
			HtElementUtil.dumpTree(elist, -1);
			throw e;
		}
	}
}

class TagData {
	protected String name;
	protected boolean s, e;
	protected TagData[] dlist;
	protected String text;

	public TagData(String name, boolean s, boolean e, TagData... dlist) {
		this.name = name;
		this.s = s;
		this.e = e;
		this.dlist = dlist;
	}

	public TagData(String text) {
		this.text = text;
	}

	public void assertData(HtElement he) {
		if (name != null) {
			assertEquals(name, he.getName());
			assertEquals(s, he.getStartTag() != null);
			assertEquals(e, he.getEndTag() != null);
		}
		if (text != null) {
			assertEquals(text, he.getText());
		}

		if (dlist != null) {
			HtListElement elist = (HtListElement) he;
			assertEquals(dlist.length, elist.size());
			for (int i = 0; i < dlist.length; i++) {
				dlist[i].assertData(elist.get(i));
			}
		}
	}

}