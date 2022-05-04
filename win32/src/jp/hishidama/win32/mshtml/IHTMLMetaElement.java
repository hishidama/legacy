package jp.hishidama.win32.mshtml;

/**
 * IHTMLMetaElement,IHTMLMetaElement2クラス.
 * <p>
 * meta要素を扱うクラスです。
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">ひしだま</a>
 * @since 2007.11.05
 */
public class IHTMLMetaElement extends IHTMLElement {

	protected IHTMLMetaElement(long dll_ptr) {
		super(dll_ptr);
	}

	public void setHttpEquiv(String equiv) {
		Native.put_httpEquiv(getPtr(), equiv);
	}

	public String getHttpEquiv() {
		return Native.get_httpEquiv(getPtr());
	}

	public void setContent(String content) {
		Native.put_content(getPtr(), content);
	}

	public String getContent() {
		return Native.get_content(getPtr());
	}

	public void setName(String name) {
		Native.put_name(getPtr(), name);
	}

	public String getName() {
		return Native.get_name(getPtr());
	}

	public void setUrl(String url) {
		Native.put_url(getPtr(), url);
	}

	public String getUrl() {
		return Native.get_url(getPtr());
	}

	public void setCharset(String charset) {
		Native.put_charset(getPtr(), charset);
	}

	public String getCharset() {
		return Native.get_charset(getPtr());
	}

	public void setScheme(String scheme) {
		Native.put_scheme(getPtr(), scheme);
	}

	public String getScheme() {
		return Native.get_scheme(getPtr());
	}

	private static class Native {
		//
		// IHTMLMetaElement
		//
		private static native void put_httpEquiv(long ptr, String p);

		private static native String get_httpEquiv(long ptr);

		private static native void put_content(long ptr, String p);

		private static native String get_content(long ptr);

		private static native void put_name(long ptr, String p);

		private static native String get_name(long ptr);

		private static native void put_url(long ptr, String p);

		private static native String get_url(long ptr);

		private static native void put_charset(long ptr, String p);

		private static native String get_charset(long ptr);

		//
		// IHTMLMetaElement2
		//
		private static native void put_scheme(long ptr, String p);

		private static native String get_scheme(long ptr);
	}
}
