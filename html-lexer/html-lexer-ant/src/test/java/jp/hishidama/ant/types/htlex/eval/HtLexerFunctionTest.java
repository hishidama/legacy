package jp.hishidama.ant.types.htlex.eval;

import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import jp.hishidama.util.DoubleOrderedHashMap;

import org.apache.tools.ant.Project;
import org.junit.Before;
import org.junit.Test;

public class HtLexerFunctionTest {

	protected HtLexerFunction func;

	@Before
	public void setUp() {
		func = new HtLexerFunction(new Project());
	}

	@Test
	public void testURI() {
		String uri = "http://user@host:80/root/abc/index.html?query#fragment";
		testEval("http", uri, "URI_getScheme");
		testEval("user", uri, "URI_getUserInfo");
		testEval("host", uri, "URI_getHost");
		testEval("80", uri, "URI_getPort");
		testEval("/root/abc/index.html", uri, "URI_getPath");
		testEval("query", uri, "URI_getQuery");
		testEval("fragment", uri, "URI_getFragment");
		testEval(uri, uri, "URI_toString");
		// TODO testEval(uri, null, "URI_toASCIIString", args);
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testURLEncoder() throws UnsupportedEncodingException {
		Object arg = "あいう";
		testStaticEval(URLEncoder.encode("あいう"), "URLEncoder_encode", arg);

		arg = "かきくけこ";
		testStaticEval(URLEncoder.encode("かきくけこ", "MS932"),
				"URLEncoder_encode", arg, "MS932");
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testURLDecoder() throws UnsupportedEncodingException {
		Object arg = URLEncoder.encode("あいう");
		testStaticEval("あいう", "URLDecoder_decode", arg);

		arg = URLEncoder.encode("かきくけこ", "MS932");
		testStaticEval("かきくけこ", "URLDecoder_decode", arg, "MS932");
	}

	@Test
	public void testMap() {
		testMap(new HashMap<String, String>());
		testMap(new DoubleOrderedHashMap<String, String>());
	}

	protected void testMap(Map<String, String> map) {

		testEval2(null, "zzz", map, "Map_put", "abc", "zzz");
		testEval2(null, "foo", map, "Map_put", "def", "foo");

		assertEquals(2, func.eval(map, "Map_size", new Object[0]));

		testEval(Boolean.TRUE, map, "Map_containsKey", "abc");
		testEval(Boolean.FALSE, map, "Map_containsValue", "abc");
		testEval("zzz", map, "Map_get", "abc");
		testEval(null, map, "Map_getKeyForValue", "abc");

		testEval(false, map, "Map_containsKey", "zzz");
		testEval(true, map, "Map_containsValue", "zzz");
		testEval(null, map, "Map_get", "zzz");
		testEval("abc", map, "Map_getKeyForValue", "zzz");
	}

	@Test
	public void testString() {
		testEval(3, "あいう", "String_length", new Object[] {});
		testEval("あいう", "あいう", "String_toString", new Object[] {});

		testEval(true, "あいうえお", "String_startsWith", "あいう");
		testEval(false, "あいうえお", "String_startsWith", "えお");
		testEval(false, "あいうえお", "String_endsWith", "あいう");
		testEval(true, "あいうえお", "String_endsWith", "えお");

		testEval(2, "あいうえお", "String_indexOf", "うえ");

		testEval(false, "abc", "String_containsZenkaku");
		testEval(true, "abあc", "String_containsZenkaku");
	}

	void testEval(Object ex, Object obj, String name, Object... args) {
		assertEquals(ex, func.eval(obj, name, args));

		Object[] sargs = new Object[args.length + 1];
		System.arraycopy(args, 0, sargs, 1, args.length);
		sargs[0] = obj;
		assertEquals(ex, func.eval(name, sargs));
	}

	void testEval2(Object ex1, Object ex2, Object obj, String name,
			Object... args) {
		assertEquals(ex1, func.eval(obj, name, args));

		Object[] sargs = new Object[args.length + 1];
		System.arraycopy(args, 0, sargs, 1, args.length);
		sargs[0] = obj;
		assertEquals(ex2, func.eval(name, sargs));
	}

	void testStaticEval(Object ex, String name, Object... args) {
		assertEquals(ex, func.eval(null, name, args));
		assertEquals(ex, func.eval(name, args));
	}
}
