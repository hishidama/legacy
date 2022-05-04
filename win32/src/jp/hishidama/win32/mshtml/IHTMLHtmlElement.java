package jp.hishidama.win32.mshtml;

/**
 * IHTMLHtmlElementクラス.
 * <p>
 * htmlタグを扱うクラスです。
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">ひしだま</a>
 * @since 2007.11.05
 */
public class IHTMLHtmlElement extends IHTMLElement {

	protected IHTMLHtmlElement(long dll_ptr) {
		super(dll_ptr);
	}

	public void setVersion(String version) {
		Native.put_version(getPtr(), version);
	}

	public String getVersion() {
		return Native.get_version(getPtr());
	}

	private static class Native {
		//
		// IHTMLHtmlElement
		//
		private static native void put_version(long ptr, String p);

		private static native String get_version(long ptr);
	}
}
