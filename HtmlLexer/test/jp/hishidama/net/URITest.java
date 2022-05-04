package jp.hishidama.net;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

public class URITest {

	@Test
	public void testConstruct() {
		assertURI("http:", "http", null, null, null, null, null, null, null);
		assertURI("http://", "http", "//", null, null, null, "", null, null);
		assertURI("http://host", "http", "//", null, "host", null, "", null,
				null);
		assertURI("http://host:p", "http", "//", null, "host", "p", "", null,
				null);
		assertURI("http://host:p/path", "http", "//", null, "host", "p",
				"/path", null, null);
		assertURI("http://host:p/path?query", "http", "//", null, "host", "p",
				"/path", "query", null);
		assertURI("http://host:p/path?query#frag", "http", "//", null, "host",
				"p", "/path", "query", "frag");
		assertURI("http://user@host:p/path?query#frag", "http", "//", "user",
				"host", "p", "/path", "query", "frag");
		assertURI("http://host:p/path#frag", "http", "//", null, "host", "p",
				"/path", null, "frag");

		assertURI("mailto:user@host", "mailto", null, "user", "host", null,
				null, null, null);
		assertURI("string:zzz", "string", null, null, null, null, "zzz", null,
				null);

		// 下の2つは、解釈上 区別が付かない
		assertURI("file:index.html", "file", null, null, null, null,
				"index.html", null, null);
		assertURI("mailto:host.host", "mailto", null, null, null, null,
				"host.host", null, null);

		assertURI("file:/dir", "file", null, null, null, null, "/dir", null,
				null);
		assertURI("file:/C:/dir", "file", null, null, null, null, "/C:/dir",
				null, null);
		assertURI("file:/C:/dir/file.txt", "file", null, null, null, null,
				"/C:/dir/file.txt", null, null);
		assertURI("file:///C:/dir/file.txt", "file", "//", null, null, null,
				"/C:/dir/file.txt", null, null);

		assertURI("index.html", null, null, null, null, null, "index.html",
				null, null);
		assertURI("/index.html", null, null, null, null, null, "/index.html",
				null, null);
		assertURI("/index.do?query", null, null, null, null, null, "/index.do",
				"query", null);
		assertURI("/index.do#frag", null, null, null, null, null, "/index.do",
				null, "frag");
		assertURI("/index.do?query#frag", null, null, null, null, null,
				"/index.do", "query", "frag");

		assertURI("#zzz", null, null, null, null, null, "", null, "zzz");
		assertURI("#", null, null, null, null, null, "", null, "");
		assertURI("", null, null, null, null, null, "", null, null);
	}

	void assertURI(String str, String scheme, String ss, String user,
			String host, String port, String path, String query, String fragment) {
		URI uri = new URI(str);
		// dump(uri);
		assertEquals(scheme, uri.getScheme());
		assertEquals(ss, uri.getSS());
		assertEquals(user, uri.getUserInfo());
		assertEquals(host, uri.getHost());
		assertEquals(port, uri.getPort());
		assertEquals(path, uri.getPath());
		assertEquals(query, uri.getQuery());
		assertEquals(fragment, uri.getFragment());
		assertEquals(str, uri.toString());
	}

	@Test
	public void testConstructJavaURI() {
		assertURJ("http://host/");
		assertURJ("http://user@host:12/path");
		assertURJ("http://user@host:12/path?query");
		assertURJ("http://user@host:12/path?query#frag");
		assertURJ("http://user@host:12/path#frag");
		assertURJ("http://user@host/path#frag");
		assertURJ("http://host/path#frag");
		assertURJ("http://host/path");
		assertURJ("http://host#frag");

		assertURJ("index.html");
		assertURJ("/index.html");
		assertURJ("/index.do?query");
		assertURJ("/index.do#frag");
		assertURJ("/index.do?query#frag");

		assertURJ("#frag");

		// IPv6
		assertURJ("http://[2001:218:2001:3000::181]/");
		assertURJ("http://[2001:218:2001:3000::181]:80/");
		assertURJ("http://user@[2001:218:2001:3000::181]/");
		assertURJ("http://user@[2001:218:2001:3000::181]:80/");

		// assertURJ("http:"); //java.net.URI未対応
		// assertURJ("http://"); //java.net.URI未対応
		assertURJ("http://user@host");
		assertURJ("http://host");

		assertURJ("file:/dir/");
		assertURJ("file:/file");
		assertURJ("file:/C:/path/file.txt");

		// java.net.URIとは仕様が異なる
		assertURJssp("mailto:zzz");
		assertURJssp("mailto:user@host");
	}

	void assertURJ(String str) {
		URI uri = new URI(str);
		java.net.URI urj = java.net.URI.create(str);
		assertEqualsJavaURI(urj, uri);
	}

	void assertURJssp(String str) {
		URI uri = new URI(str);
		java.net.URI urj = java.net.URI.create(str);
		assertEqualsJavaURIssp(urj, uri);
	}

	void assertEqualsJavaURI(java.net.URI urj, URI uri) {
		assertEquals(urj.getScheme(), uri.getScheme());
		assertEquals(urj.getUserInfo(), uri.getUserInfo());
		assertEquals(urj.getHost(), uri.getHost());
		int port = (uri.getPort() == null) ? -1 : Integer.parseInt(uri
				.getPort());
		assertEquals(urj.getPort(), port);
		assertEquals(urj.getPath(), uri.getPath());
		assertEquals(urj.getQuery(), uri.getQuery());
		assertEquals(urj.getFragment(), uri.getFragment());
		assertEqualsJavaURIssp(urj, uri);
		assertEquals(urj.getAuthority(), uri.getAuthority());
	}

	void assertEqualsJavaURIssp(java.net.URI urj, URI uri) {
		assertEquals(urj.toString(), uri.toString());
		assertEquals(urj.getSchemeSpecificPart(), uri.getSchemeSpecificPart());
		assertEquals(urj.isAbsolute(), uri.isAbsolute());
		assertEquals(urj.isOpaque(), uri.isOpaque());
	}

	@Test
	public void testNormalize() {
		assertNormalize("http://host/path/test.html");
		assertNormalize("http://host/path/test.html?query");
		assertNormalize("http://host/path/test.html?query#frag");
		assertNormalize("http://host/path/test.html#frag");

		assertNormalize("http://host/path//test.html");
		assertNormalize("http://host/path/./test.html");
		assertNormalize("http://host/path/../test.html");
		assertNormalize("http://host/path/../../test.html");
		assertNormalize("http://host/path//././zzz/../test.html?q#f");
	}

	void assertNormalize(String str) {
		URI uri = new URI(str).normalize();
		java.net.URI urj = java.net.URI.create(str).normalize();
		assertEqualsJavaURI(urj, uri);
	}

	@Test
	public void testResolveURI() {
		assertResolveURI("http://host/root/abc/index.html", "def.html");
		assertResolveURI("http://host/root/abc/index.html",
				"http://host/def.html");
	}

	void assertResolveURI(String base, String str) {
		URI b = new URI(base).normalize();
		URI u = new URI(str).normalize();
		URI t = b.resolve(u);
		java.net.URI bj = java.net.URI.create(base).normalize();
		java.net.URI uj = java.net.URI.create(str).normalize();
		java.net.URI ex = bj.resolve(uj);
		assertEqualsJavaURI(ex, t);
	}

	@Test
	public void testResolveFile() {
		File d = new File("C:\\temp\\html3"); // 実在しないディレクトリーは、URIのパスの末尾は「/」にならない
		File f = new File("C:\\temp\\html3\\abc\\index.html");
		URI base = new URI("http://host/root/");
		URI expe = new URI("http://host/root/abc/index.html");
		assertEquals(expe, base.resolve(d, f));

		d = new File("C:\\temp"); // 実在するディレクトリーは、URIのパスの末尾が「/」になる
		f = new File("C:\\temp\\abc\\index.html");
		base = new URI("http://host/root/");
		expe = new URI("http://host/root/abc/index.html");
		assertEquals(expe, base.resolve(d, f));

		// ファイルが基準ディレクトリー内に無い場合
		d = new File("C:\\temp\\htm");
		f = new File("C:\\temp\\html\\abc\\index.html");
		base = new URI("http://host/root/");
		expe = new URI("http://host/html/abc/index.html");
		assertEquals(expe, base.resolve(d, f));

		d = new File("C:\\temp\\html2");
		f = new File("C:\\temp\\html\\abc\\index.html");
		base = new URI("http://host/root/");
		expe = new URI("http://host/html/abc/index.html");
		assertEquals(expe, base.resolve(d, f));
	}

	@Test
	public void testResolveFile2() {
		File d = new File("C:\\temp");
		File f = new File("C:\\temp\\abc\\index.html");
		URI base = new URI("http://host/root/");
		URI uri = new URI("def/zzz.html");
		URI expe = new URI("http://host/root/abc/def/zzz.html");
		assertEquals(expe, base.resolve(d, f, uri));

		uri = new URI("../foo.html");
		expe = new URI("http://host/root/foo.html");
		assertEquals(expe, base.resolve(d, f, uri));

		uri = new URI("../def/foo.html");
		expe = new URI("http://host/root/def/foo.html");
		assertEquals(expe, base.resolve(d, f, uri));

		// クエリーと部分識別子
		uri = new URI("../def/foo.html?query#zzz");
		expe = new URI("http://host/root/def/foo.html?query#zzz");
		assertEquals(expe, base.resolve(d, f, uri));

		// 部分識別子のみ
		uri = new URI("#nme");
		expe = new URI("http://host/root/abc/index.html#nme");
		assertEquals(expe, base.resolve(d, f, uri));

		uri = new URI("#");
		expe = new URI("http://host/root/abc/index.html#");
		assertEquals(expe, base.resolve(d, f, uri));

		// 絶対URIの場合、そのままのURIが返る
		uri = new URI("http://other/index.html");
		expe = new URI("http://other/index.html");
		assertEquals(expe, base.resolve(d, f, uri));
	}

	@Test
	public void testRelativize1() {
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
	public void testRelativize2() {
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

	// @Test
	public void testRelativize3() {
		// testRelativize2のu1の末尾に「/」が無いパターンだが
		// ファイルと同じ扱いになるので、testRelativize2と同じ期待値にはならない
		String u1, u2, ex;
		u1 = "http://host/root";
		u2 = "http://host/root/def/to.html";
		ex = "def/to.html";
		assertRelativize(u1, u2, ex);

		u1 = "http://host/root";
		u2 = "http://host/to.html";
		ex = "../to.html";
		assertRelativize(u1, u2, ex);

		u1 = "http://host/root";
		u2 = "http://host/root/to.html";
		ex = "to.html";
		assertRelativize(u1, u2, ex);

		u1 = "http://host/root";
		u2 = "http://host/ro/to.html";
		ex = "../ro/to.html";
		assertRelativize(u1, u2, ex);
	}

	@Test
	public void testRelativize4() {
		URI u1 = new URI("http://host/root/file.html");
		URI u2 = new URI("http://host/root/file.html#frag");
		URI ex = new URI("#frag");
		assertEquals(ex, URI.relativize(u1, u2));

		u1 = new URI("http://host/root/abc/index.html");
		u2 = new URI("#frag");
		assertSame(u2, URI.relativize(u1, u2));
		u2 = new URI("");
		assertSame(u2, URI.relativize(u1, u2));

		u1 = new URI("http://host1/root/abc/index.html");
		u2 = new URI("http://host2/root/def/index.html");
		assertSame(u2, URI.relativize(u1, u2));
}

	void assertRelativize(String s1, String s2, String se) {
		URI u1 = new URI(s1);
		URI u2 = new URI(s2);
		URI ex = new URI(se);
		assertEquals(ex, URI.relativize(u1, u2));
		assertEquals(u2, u1.resolve(ex));

		String q = "?param=value";
		u1 = new URI(s1);
		u2 = new URI(s2 + q);
		ex = new URI(se + q);
		assertEquals(ex, URI.relativize(u1, u2));
		u1 = new URI(s1 + q);
		u2 = new URI(s2);
		ex = new URI(se);
		assertEquals(ex, URI.relativize(u1, u2));
		u1 = new URI(s1 + "?p1=v1");
		u2 = new URI(s2 + q);
		ex = new URI(se + q);
		assertEquals(ex, URI.relativize(u1, u2));

		String f = "#name";
		u1 = new URI(s1);
		u2 = new URI(s2 + f);
		ex = new URI(se + f);
		assertEquals(ex, URI.relativize(u1, u2));
		u1 = new URI(s1 + f);
		u2 = new URI(s2);
		ex = new URI(se);
		assertEquals(ex, URI.relativize(u1, u2));
		u1 = new URI(s1 + "#nnn");
		u2 = new URI(s2 + f);
		ex = new URI(se + f);
		assertEquals(ex, URI.relativize(u1, u2));

		u1 = new URI(s1);
		u2 = new URI(s2 + q + f);
		ex = new URI(se + q + f);
		assertEquals(ex, URI.relativize(u1, u2));
		u1 = new URI(s1 + q + f);
		u2 = new URI(s2);
		ex = new URI(se);
		assertEquals(ex, URI.relativize(u1, u2));
		u1 = new URI(s1 + "?p1=v1");
		u2 = new URI(s2 + q + f);
		ex = new URI(se + q + f);
		assertEquals(ex, URI.relativize(u1, u2));
		u1 = new URI(s1 + "#nnn");
		u2 = new URI(s2 + q + f);
		ex = new URI(se + q + f);
		assertEquals(ex, URI.relativize(u1, u2));
	}

	@Test
	public void testIsFragmentOnly() {
		URI uri = new URI("#frag");
		assertTrue(uri.isFragmentOnly());
		uri = new URI("#");
		assertTrue(uri.isFragmentOnly());

		uri = new URI("");
		assertFalse(uri.isFragmentOnly());

		assertFalseIsFragmentOnly("rel");
		assertFalseIsFragmentOnly("../rel");
		assertFalseIsFragmentOnly("http://host/");
		assertFalseIsFragmentOnly("http://host/path/");
		assertFalseIsFragmentOnly("http://host/path/zzz.html");
		assertFalseIsFragmentOnly("http://host/path/zzz.html?query");
	}

	void assertFalseIsFragmentOnly(String str) {
		URI uri = new URI(str);
		assertFalse(uri.isFragmentOnly());

		uri = new URI(str + "#frag");
		assertFalse(uri.isFragmentOnly());
	}

	@Test
	public void testGetSchemePath() {
		URI ur;
		String ex;
		String str = "http://user@host:123/path";
		ur = new URI(str + "?query#frag");
		ex = str;
		assertEquals(ex, ur.getSchemePath());
		ur = new URI(str + "?query");
		assertEquals(ex, ur.getSchemePath());
		ur = new URI(str + "#frag");
		assertEquals(ex, ur.getSchemePath());
		ur = new URI(str);
		assertEquals(ex, ur.getSchemePath());

		str = "/path";
		ur = new URI(str + "?query#frag");
		ex = str;
		assertEquals(ex, ur.getSchemePath());
		ur = new URI(str + "?query");
		assertEquals(ex, ur.getSchemePath());
		ur = new URI(str + "#frag");
		assertEquals(ex, ur.getSchemePath());
		ur = new URI(str);
		assertEquals(ex, ur.getSchemePath());

		str = "mailto:user@host?query";
		ur = new URI(str + "#frag");
		ex = "mailto:user@host";
		assertEquals(ex, ur.getSchemePath());
		ur = new URI(str);
		assertEquals(ex, ur.getSchemePath());

		ur = new URI("#frag");
		ex = "";
		assertEquals(ex, ur.getSchemePath());
	}

	@Test
	public void testGetSchemeQuery() {
		URI ur;
		String ex;
		String str = "http://user@host:123/path";
		ur = new URI(str + "?query#frag");
		ex = str + "?query";
		assertEquals(ex, ur.getSchemeQuery());
		ur = new URI(str + "?query");
		assertEquals(ex, ur.getSchemeQuery());
		ur = new URI(str + "#frag");
		ex = str;
		assertEquals(ex, ur.getSchemeQuery());
		ur = new URI(str);
		assertEquals(ex, ur.getSchemeQuery());

		str = "/path";
		ur = new URI(str + "?query#frag");
		ex = str + "?query";
		assertEquals(ex, ur.getSchemeQuery());
		ur = new URI(str + "?query");
		assertEquals(ex, ur.getSchemeQuery());
		ur = new URI(str + "#frag");
		ex = str;
		assertEquals(ex, ur.getSchemeQuery());
		ur = new URI(str);
		assertEquals(ex, ur.getSchemeQuery());

		str = "mailto:user@host?query";
		ur = new URI(str + "#frag");
		ex = str;
		assertEquals(ex, ur.getSchemeQuery());
		ur = new URI(str);
		assertEquals(ex, ur.getSchemeQuery());

		ur = new URI("#frag");
		ex = "";
		assertEquals(ex, ur.getSchemeQuery());
	}

	@Test
	public void testSetScheme() {
		URI uri = new URI("http://host/path");
		URI ex = new URI("https://host/path");
		assertEquals(ex, uri.setScheme("https"));
	}

	@Test
	public void testSetPath() {
		URI uri = new URI("http://host/root/abc/index.html?query#frag");
		URI exp = new URI("http://host/root/abc/def/z.html?query#frag");
		assertEquals(exp, uri.setPath("/root/abc/def/z.html"));

		// 不透明URI
		uri = new URI("mailto:user@host");
		exp = new URI("mailto:user@host/root/abc/def/z.html");
		assertEquals(exp, uri.setPath("/root/abc/def/z.html"));
	}

	@Test
	public void testSetFragment() {
		URI uri = new URI("http://host/root/abc/index.html?query#名前");
		URI exp = new URI("http://host/root/abc/index.html?query#frag");
		assertEquals(exp, uri.setFragment("frag"));
	}

	static void dump(URI u) {
		System.out.println("●    : " + u.toString());
		System.out.println("scheme: " + u.getScheme());
		System.out.println("ss    : " + u.getSS());
		System.out.println("user  : " + u.getUserInfo());
		System.out.println("host  : " + u.getHost());
		System.out.println("port  : " + u.getPort());
		System.out.println("path  : " + u.getPath());
		System.out.println("query : " + u.getQuery());
		System.out.println("fragme: " + u.getFragment());
	}
}
