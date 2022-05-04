package jp.hishidama.net;

import static org.junit.Assert.*;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Test;

public class URIUtilTest {

	@Test
	public void testCreate() throws URISyntaxException {
		String str = "http://user@host:123/root/file.html?query#frag";
		URI uri = URIUtil.create(str);
		assertEquals("http", uri.getScheme());
		assertEquals("user", uri.getRawUserInfo());
		assertEquals("host", uri.getHost());
		assertEquals(123, uri.getPort());
		assertEquals("/root/file.html", uri.getRawPath());
		assertEquals("query", uri.getRawQuery());
		assertEquals("frag", uri.getRawFragment());

		// schemeにスペースがある場合は例外発生
		str = "ht tp://user@host:123/root/file.html?query#frag";
		try {
			URIUtil.create(str);
			fail();
		} catch (URISyntaxException e) {
			System.err.println(e.toString());
		}

		// hostにスペースがある場合は例外発生
		str = "http://user@ho st:123/root/file.html?query#frag";
		try {
			URIUtil.create(str);
			fail();
		} catch (URISyntaxException e) {
			System.err.println(e.toString());
		}

		assertEqualsCreate(' ');
		assertEqualsCreate('%');
		assertEqualsCreate('\\');
		assertEqualsCreate('$');
		for (char c = 0x20; c < 0x7f; c++) {
			switch (c) {
			case '/':
			case '@':
			case '?':
			case '#':
				continue;
			}
			assertEqualsCreate(c);
		}

		str = "../test.html#あいうえお かきくけこ";
		uri = URIUtil.create(str);
		assertEquals("../test.html", uri.getPath());
		assertEquals("あいうえお かきくけこ", uri.getFragment());
	}

	void assertEqualsCreate(char c) throws URISyntaxException {
		String str = "http://us" + c + "er@host:123/ro" + c + "ot/fi" + c
				+ "le.html?qu" + c + "ery#fr" + c + "ag";
		URI uri = URIUtil.create(str);
		assertEquals("http", uri.getScheme());
		assertEquals("us" + c + "er", uri.getUserInfo());
		assertEquals("host", uri.getHost());
		assertEquals(123, uri.getPort());
		assertEquals("/ro" + c + "ot/fi" + c + "le.html", uri.getPath());
		assertEquals("qu" + c + "ery", uri.getQuery());
		assertEquals("fr" + c + "ag", uri.getFragment());
	}

	@Test
	public void testResolveFile() throws URISyntaxException {
		File d = new File("C:\\temp\\html3"); // 実在しないディレクトリーは、URIのパスの末尾は「/」にならない
		File f = new File("C:\\temp\\html3\\abc\\index.html");
		URI base = new URI("http://host/root/");
		URI expe = new URI("http://host/root/abc/index.html");
		assertEquals(expe, URIUtil.resolveFile(base, d, f));

		d = new File("C:\\temp"); // 実在するディレクトリーは、URIのパスの末尾が「/」になる
		f = new File("C:\\temp\\abc\\index.html");
		base = new URI("http://host/root/");
		expe = new URI("http://host/root/abc/index.html");
		assertEquals(expe, URIUtil.resolveFile(base, d, f));

		// ファイルが基準ディレクトリー内に無い場合
		d = new File("C:\\temp\\htm");
		f = new File("C:\\temp\\html\\abc\\index.html");
		base = new URI("http://host/root/");
		expe = new URI("http://host/html/abc/index.html");
		assertEquals(expe, URIUtil.resolveFile(base, d, f));

		d = new File("C:\\temp\\html2");
		f = new File("C:\\temp\\html\\abc\\index.html");
		base = new URI("http://host/root/");
		expe = new URI("http://host/html/abc/index.html");
		assertEquals(expe, URIUtil.resolveFile(base, d, f));
	}

	@Test
	public void testResolveFile2() throws URISyntaxException {
		File d = new File("C:\\temp");
		File f = new File("C:\\temp\\abc\\index.html");
		URI base = new URI("http://host/root/");
		URI uri = new URI("def/zzz.html");
		URI expe = new URI("http://host/root/abc/def/zzz.html");
		assertEquals(expe, URIUtil.resolveFile(base, d, f, uri));

		uri = new URI("../foo.html");
		expe = new URI("http://host/root/foo.html");
		assertEquals(expe, URIUtil.resolveFile(base, d, f, uri));

		uri = new URI("../def/foo.html");
		expe = new URI("http://host/root/def/foo.html");
		assertEquals(expe, URIUtil.resolveFile(base, d, f, uri));

		// クエリーと部分識別子
		uri = new URI("../def/foo.html?query#zzz");
		expe = new URI("http://host/root/def/foo.html?query#zzz");
		assertEquals(expe, URIUtil.resolveFile(base, d, f, uri));

		// 部分識別子のみ
		uri = new URI("#nme");
		expe = new URI("http://host/root/abc/index.html#nme");
		assertEquals(expe, URIUtil.resolveFile(base, d, f, uri));

		uri = new URI("#");
		expe = new URI("http://host/root/abc/index.html#");
		assertEquals(expe, URIUtil.resolveFile(base, d, f, uri));

		// 絶対URIの場合、そのままのURIが返る
		uri = new URI("http://other/index.html");
		expe = new URI("http://other/index.html");
		assertEquals(expe, URIUtil.resolveFile(base, d, f, uri));
	}

	@Test
	public void testRelativize1() throws URISyntaxException {
		String u1, u2, ex;
		u1 = "http://host/root/abc/index.html";
		u2 = "http://host/root/def/to.html";
		ex = "../def/to.html";
		assertRelativize(u1, u2, ex);

		u1 = "http://host/root/abc/index.html";
		u2 = "http://host/root/to.html";
		ex = "../to.html";
		assertRelativize(u1, u2, ex);

		u1 = "http://host/root/abc/index.html";
		u2 = "http://host/root/abc/to.html";
		ex = "to.html";
		assertRelativize(u1, u2, ex);

		u1 = "http://host/root/abc/index.html";
		u2 = "http://host/root/abc/def/to.html";
		ex = "def/to.html";
		assertRelativize(u1, u2, ex);

		u1 = "http://host/root/abc/index.html";
		u2 = "http://host/root/abcd/def/to.html";
		ex = "../abcd/def/to.html";
		assertRelativize(u1, u2, ex);

		u1 = "http://host/root/abc/index.html";
		u2 = "http://host/root/ab/def/to.html";
		ex = "../ab/def/to.html";
		assertRelativize(u1, u2, ex);
	}

	@Test
	public void testRelativize2() throws URISyntaxException {
		String u1, u2, ex;
		u1 = "http://host/root/";
		u2 = "http://host/root/def/to.html";
		ex = "def/to.html";
		assertRelativize(u1, u2, ex);

		u1 = "http://host/root/";
		u2 = "http://host/to.html";
		ex = "../to.html";
		assertRelativize(u1, u2, ex);

		u1 = "http://host/root/";
		u2 = "http://host/root/to.html";
		ex = "to.html";
		assertRelativize(u1, u2, ex);

		u1 = "http://host/root/";
		u2 = "http://host/ro/to.html";
		ex = "../ro/to.html";
		assertRelativize(u1, u2, ex);
	}

	@Test
	public void testRelativize3() throws URISyntaxException {
		// testRelativize2のu1の末尾に「/」が無いパターンだが
		// ファイルと同じ扱いになるので、testRelativize2と同じ期待値にはならない
		@SuppressWarnings("unused")
		String u1, u2, ex;
		u1 = "http://host/root";
		u2 = "http://host/root/def/to.html";
		ex = "def/to.html";
		// assertRelativize(u1, u2, ex);

		u1 = "http://host/root";
		u2 = "http://host/to.html";
		ex = "../to.html";
		// assertRelativize(u1, u2, ex);

		u1 = "http://host/root";
		u2 = "http://host/root/to.html";
		ex = "to.html";
		// assertRelativize(u1, u2, ex);

		u1 = "http://host/root";
		u2 = "http://host/ro/to.html";
		ex = "../ro/to.html";
		// assertRelativize(u1, u2, ex);
	}

	@Test
	public void testRelativize4() throws URISyntaxException {
		URI u1 = new URI("http://host/root/file.html");
		URI u2 = new URI("http://host/root/file.html#frag");
		URI ex = new URI("#frag");
		assertEquals(ex, URIUtil.relativize(u1, u2));
	}

	void assertRelativize(String s1, String s2, String se)
			throws URISyntaxException {
		URI u1 = new URI(s1);
		URI u2 = new URI(s2);
		URI ex = new URI(se);
		assertEquals(ex, URIUtil.relativize(u1, u2));
		assertEquals(u2, u1.resolve(ex));

		String q = "?param=value";
		u1 = new URI(s1);
		u2 = new URI(s2 + q);
		ex = new URI(se + q);
		assertEquals(ex, URIUtil.relativize(u1, u2));
		u1 = new URI(s1 + q);
		u2 = new URI(s2);
		ex = new URI(se);
		assertEquals(ex, URIUtil.relativize(u1, u2));
		u1 = new URI(s1 + "?p1=v1");
		u2 = new URI(s2 + q);
		ex = new URI(se + q);
		assertEquals(ex, URIUtil.relativize(u1, u2));

		String f = "#name";
		u1 = new URI(s1);
		u2 = new URI(s2 + f);
		ex = new URI(se + f);
		assertEquals(ex, URIUtil.relativize(u1, u2));
		u1 = new URI(s1 + f);
		u2 = new URI(s2);
		ex = new URI(se);
		assertEquals(ex, URIUtil.relativize(u1, u2));
		u1 = new URI(s1 + "#nnn");
		u2 = new URI(s2 + f);
		ex = new URI(se + f);
		assertEquals(ex, URIUtil.relativize(u1, u2));

		u1 = new URI(s1);
		u2 = new URI(s2 + q + f);
		ex = new URI(se + q + f);
		assertEquals(ex, URIUtil.relativize(u1, u2));
		u1 = new URI(s1 + q + f);
		u2 = new URI(s2);
		ex = new URI(se);
		assertEquals(ex, URIUtil.relativize(u1, u2));
		u1 = new URI(s1 + "?p1=v1");
		u2 = new URI(s2 + q + f);
		ex = new URI(se + q + f);
		assertEquals(ex, URIUtil.relativize(u1, u2));
		u1 = new URI(s1 + "#nnn");
		u2 = new URI(s2 + q + f);
		ex = new URI(se + q + f);
		assertEquals(ex, URIUtil.relativize(u1, u2));
	}

	@Test
	public void testIsFragmentOnly() throws URISyntaxException {
		URI uri = new URI("#frag");
		assertTrue(URIUtil.isFragmentOnly(uri));

		assertFalseIsFragmentOnly("rel");
		assertFalseIsFragmentOnly("../rel");
		assertFalseIsFragmentOnly("http://host/");
		assertFalseIsFragmentOnly("http://host/path/");
		assertFalseIsFragmentOnly("http://host/path/zzz.html");
		assertFalseIsFragmentOnly("http://host/path/zzz.html?query");
	}

	void assertFalseIsFragmentOnly(String str) throws URISyntaxException {
		URI uri = new URI(str);
		assertFalse(URIUtil.isFragmentOnly(uri));

		uri = new URI(str + "#frag");
		assertFalse(URIUtil.isFragmentOnly(uri));
	}

	@Test
	public void testGetSchemePath() throws URISyntaxException {
		URI ur, ex;
		String str = "http://user@host:123/path";
		ur = new URI(str + "?query#frag");
		ex = new URI(str);
		assertEquals(ex, URIUtil.getSchemePath(ur));
		ur = new URI(str + "?query");
		assertEquals(ex, URIUtil.getSchemePath(ur));
		ur = new URI(str + "#frag");
		assertEquals(ex, URIUtil.getSchemePath(ur));
		ur = new URI(str);
		assertEquals(ex, URIUtil.getSchemePath(ur));

		str = "/path";
		ur = new URI(str + "?query#frag");
		ex = new URI(str);
		assertEquals(ex, URIUtil.getSchemePath(ur));
		ur = new URI(str + "?query");
		assertEquals(ex, URIUtil.getSchemePath(ur));
		ur = new URI(str + "#frag");
		assertEquals(ex, URIUtil.getSchemePath(ur));
		ur = new URI(str);
		assertEquals(ex, URIUtil.getSchemePath(ur));

		str = "mailto:user@host?query";
		ur = new URI(str + "#frag");
		ex = new URI(str);
		assertEquals(ex, URIUtil.getSchemePath(ur));
		ur = new URI(str);
		assertEquals(ex, URIUtil.getSchemePath(ur));

		ur = new URI("#frag");
		ex = new URI("");
		assertEquals(ex, URIUtil.getSchemePath(ur));
	}

	@Test
	public void testGetSchemeQuery() throws URISyntaxException {
		URI ur, ex;
		String str = "http://user@host:123/path";
		ur = new URI(str + "?query#frag");
		ex = new URI(str + "?query");
		assertEquals(ex, URIUtil.getSchemeQuery(ur));
		ur = new URI(str + "?query");
		assertEquals(ex, URIUtil.getSchemeQuery(ur));
		ur = new URI(str + "#frag");
		ex = new URI(str);
		assertEquals(ex, URIUtil.getSchemeQuery(ur));
		ur = new URI(str);
		assertEquals(ex, URIUtil.getSchemeQuery(ur));

		str = "/path";
		ur = new URI(str + "?query#frag");
		ex = new URI(str + "?query");
		assertEquals(ex, URIUtil.getSchemeQuery(ur));
		ur = new URI(str + "?query");
		assertEquals(ex, URIUtil.getSchemeQuery(ur));
		ur = new URI(str + "#frag");
		ex = new URI(str);
		assertEquals(ex, URIUtil.getSchemeQuery(ur));
		ur = new URI(str);
		assertEquals(ex, URIUtil.getSchemeQuery(ur));

		str = "mailto:user@host?query";
		ur = new URI(str + "#frag");
		ex = new URI(str);
		assertEquals(ex, URIUtil.getSchemeQuery(ur));
		ur = new URI(str);
		assertEquals(ex, URIUtil.getSchemeQuery(ur));

		ur = new URI("#frag");
		ex = new URI("");
		assertEquals(ex, URIUtil.getSchemeQuery(ur));
	}

	@Test
	public void testSetScheme() throws URISyntaxException {
		URI uri = new URI("http://host/path");
		URI ex = new URI("https://host/path");
		assertEquals(ex, URIUtil.setSchema(uri, "https"));
	}

	@Test
	public void testSetPath() throws URISyntaxException {
		URI uri = new URI("http://host/root/abc/index.html?query#frag");
		URI exp = new URI("http://host/root/abc/def/z.html?query#frag");
		assertEquals(exp, URIUtil.setPath(uri, "/root/abc/def/z.html"));

		// 不透明URI
		uri = new URI("mailto:user@host");
		exp = new URI("mailto:user@host");
		assertEquals(exp, URIUtil.setPath(uri, "/root/abc/def/z.html"));
	}

	@Test
	public void testSetFragment() throws URISyntaxException {
		URI uri = new URI("http://host/root/abc/index.html?query#名前");
		URI exp = new URI("http://host/root/abc/index.html?query#frag");
		assertEquals(exp, URIUtil.setFragment(uri, "frag"));
	}

	@Test
	public void test() throws URISyntaxException {
		String file = "http://www.ne.jp/hishidama/home/book/tech.html";
		String str = "../book/game.html#メタルギアソリッド2 シナリオ・ブック";
		URI fileUri = new URI(file);
		System.out.println(fileUri);
		URI strUri = URIUtil.create(str);
		System.out.println(strUri);
		URI uri = fileUri.resolve(strUri);
		System.out.println(uri);
		URI query = URIUtil.getRawSchemeQuery(uri);
		System.out.println(query);
		URI full = URIUtil.setFragment(query, strUri.getFragment());
		System.out.println(full);
		URI rel = URIUtil.relativize(fileUri, full);
		System.out.println(rel);
	}
}
