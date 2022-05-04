package jp.hishidama.html;

import static org.junit.Assert.*;

import org.junit.Test;

public class HtmlEscapeTest {

	@Test
	public void testIsDigit() {
		assertFalse(HtmlEscape.isDigit((char) ('0' - 1)));
		assertTrue(HtmlEscape.isDigit('0'));
		assertTrue(HtmlEscape.isDigit('9'));
		assertFalse(HtmlEscape.isDigit((char) ('9' + 1)));

		assertFalse(HtmlEscape.isDigit((char) ('a' - 1)));
		assertFalse(HtmlEscape.isDigit('a'));
		assertFalse(HtmlEscape.isDigit('f'));
		assertFalse(HtmlEscape.isDigit((char) ('f' + 1)));

		assertFalse(HtmlEscape.isDigit((char) ('A' - 1)));
		assertFalse(HtmlEscape.isDigit('A'));
		assertFalse(HtmlEscape.isDigit('F'));
		assertFalse(HtmlEscape.isDigit((char) ('F' + 1)));
	}

	@Test
	public void testIsHexDigit() {
		assertFalse(HtmlEscape.isHexDigit((char) ('0' - 1)));
		assertTrue(HtmlEscape.isHexDigit('0'));
		assertTrue(HtmlEscape.isHexDigit('9'));
		assertFalse(HtmlEscape.isHexDigit((char) ('9' + 1)));

		assertFalse(HtmlEscape.isHexDigit((char) ('a' - 1)));
		assertTrue(HtmlEscape.isHexDigit('a'));
		assertTrue(HtmlEscape.isHexDigit('f'));
		assertFalse(HtmlEscape.isHexDigit((char) ('f' + 1)));

		assertFalse(HtmlEscape.isHexDigit((char) ('A' - 1)));
		assertTrue(HtmlEscape.isHexDigit('A'));
		assertTrue(HtmlEscape.isHexDigit('F'));
		assertFalse(HtmlEscape.isHexDigit((char) ('F' + 1)));
	}

	@Test
	public void testEscapedAmp() {
		HtmlEscape he = new HtmlEscape();

		assertEquals("amp", he.escapedAmp("&amp;", 0));
		assertEquals(null, he.escapedAmp("&amp", 0));
		assertEquals(null, he.escapedAmp("&am;", 0));
		assertEquals(null, he.escapedAmp("&a;", 0));
		assertEquals(null, he.escapedAmp("&;", 0));
		assertEquals(null, he.escapedAmp("&ampa;", 0));

		assertEquals("lt", he.escapedAmp("&lt;", 0));
		assertEquals(null, he.escapedAmp("&lt", 0));
		assertEquals(null, he.escapedAmp("&l;", 0));
		assertEquals(null, he.escapedAmp("&ltt;", 0));

		assertEquals("gt", he.escapedAmp("&gt;", 0));
		assertEquals(null, he.escapedAmp("&gt", 0));
		assertEquals(null, he.escapedAmp("&g;", 0));
		assertEquals(null, he.escapedAmp("&gtt;", 0));

		assertEquals("quot", he.escapedAmp("&quot;", 0));
		assertEquals(null, he.escapedAmp("&quo;", 0));
		assertEquals(null, he.escapedAmp("&qu;", 0));
		assertEquals(null, he.escapedAmp("&q;", 0));
		assertEquals(null, he.escapedAmp("&quott;", 0));

		assertEquals("#39", he.escapedAmp("&#39;", 0));
		assertEquals(null, he.escapedAmp("&#3", 0));
		assertEquals(null, he.escapedAmp("&#;", 0));
		assertEquals(null, he.escapedAmp("&#399", 0));
		assertEquals("#1", he.escapedAmp("&#1;", 0));
		assertEquals("#111", he.escapedAmp("&#111;", 0));
		assertEquals("#1111", he.escapedAmp("&#1111;", 0));
		assertEquals(null, he.escapedAmp("&#1111", 0));

		assertEquals("#xff", he.escapedAmp("&#xff;", 0));
		assertEquals(null, he.escapedAmp("&#xf", 0));
		assertEquals(null, he.escapedAmp("&#x;", 0));
		assertEquals(null, he.escapedAmp("&#;", 0));
		assertEquals(null, he.escapedAmp("&#xfff", 0));
		assertEquals("#xf", he.escapedAmp("&#xf;", 0));
		assertEquals("#xfff", he.escapedAmp("&#xfff;", 0));
		assertEquals("#xffff", he.escapedAmp("&#xffff;", 0));
		assertEquals(null, he.escapedAmp("&#xffff", 0));

		assertEquals("#Xff", he.escapedAmp("&#Xff;", 0));
		assertEquals(null, he.escapedAmp("&#Xf", 0));
		assertEquals(null, he.escapedAmp("&#X;", 0));
		assertEquals(null, he.escapedAmp("&#;", 0));
		assertEquals(null, he.escapedAmp("&#Xfff", 0));
		assertEquals("#Xf", he.escapedAmp("&#Xf;", 0));
		assertEquals("#Xfff", he.escapedAmp("&#Xfff;", 0));
		assertEquals("#Xffff", he.escapedAmp("&#Xffff;", 0));
		assertEquals(null, he.escapedAmp("&#Xffff", 0));

		assertEquals("nbsp", he.escapedAmp("&nbsp;", 0));
		assertEquals("copy", he.escapedAmp("&copy;", 0));
		assertEquals("reg", he.escapedAmp("&reg;", 0));

		assertEquals(null, he.escapedAmp("&aaa;", 0));
	}

	@Test
	public void testEscape() {
		HtmlEscape he = new HtmlEscape();
		assertNull(he.escape(null));

		assertEscape("&lt;tag attr=&quot;zzz&quot;&gt;", he,
				"<tag attr=\"zzz\">");
		assertEscape("&lt;tag attr=&#39;zzz&#39;&gt;", he, "<tag attr=\'zzz\'>");
		assertEscape("&lt;tag attr=&#39;&amp;&#39;&gt;", he, "<tag attr=\'&\'>");
		assertEscape("&lt;tag attr=&#39;&amp;&#39;&gt;", he,
				"<tag attr=\'&amp;\'>");
		assertEscape("attr=&quot;Hishidama&#39;s encoder&quot;", he,
				"attr=\"Hishidama's encoder\"");

		assertEscape(" ", he, " ");
		assertEscape("&copy;", he, "&copy;");
		assertEscape("&reg;", he, "&reg;");

		he = new HtmlEscape(true, false);
		assertEscape("&lt;tag attr=&quot;zzz&quot;&gt;", he,
				"<tag attr=\"zzz\">");
		assertEscape("&lt;tag attr=\'zzz\'&gt;", he, "<tag attr=\'zzz\'>");
		assertEscape("&lt;tag attr=\'&amp;\'&gt;", he, "<tag attr=\'&\'>");
		assertEscape("&lt;tag attr=\'&amp;\'&gt;", he, "<tag attr=\'&amp;\'>");
		assertEscape("attr=&quot;Hishidama's encoder&quot;", he,
				"attr=\"Hishidama's encoder\"");

		he = new HtmlEscape(false, true);
		assertEscape("&lt;tag attr=\"zzz\"&gt;", he, "<tag attr=\"zzz\">");
		assertEscape("&lt;tag attr=&#39;zzz&#39;&gt;", he, "<tag attr=\'zzz\'>");
		assertEscape("&lt;tag attr=&#39;&amp;&#39;&gt;", he, "<tag attr=\'&\'>");
		assertEscape("&lt;tag attr=&#39;&amp;&#39;&gt;", he,
				"<tag attr=\'&amp;\'>");
		assertEscape("attr=\"Hishidama&#39;s encoder\"", he,
				"attr=\"Hishidama's encoder\"");

		he = new HtmlEscape(false, false);
		assertEscape("&lt;tag attr=\"zzz\"&gt;", he, "<tag attr=\"zzz\">");
		assertEscape("&lt;tag attr=\'zzz\'&gt;", he, "<tag attr=\'zzz\'>");
		assertEscape("&lt;tag attr=\'&amp;\'&gt;", he, "<tag attr=\'&\'>");
		assertEscape("&lt;tag attr=\'&amp;\'&gt;", he, "<tag attr=\'&amp;\'>");
		assertEscape("attr=\"Hishidama's encoder\"", he,
				"attr=\"Hishidama's encoder\"");
	}

	protected void assertEscape(String expected, HtmlEscape he, String s) {
		String t = he.escape(s);
		assertEquals(expected, t);
		assertEquals(expected, he.escape(t));
	}
}
