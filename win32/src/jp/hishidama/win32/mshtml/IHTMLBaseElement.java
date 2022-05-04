package jp.hishidama.win32.mshtml;

/**
 * IHTMLBaseElementクラス.
 * <p>
 * baseタグを扱うクラスです。
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">ひしだま</a>
 * @since 2007.11.05
 */
public class IHTMLBaseElement extends IHTMLElement {

	protected IHTMLBaseElement(long dll_ptr) {
		super(dll_ptr);
	}

	public void setHref(String href) {
		Native.put_href(getPtr(), href);
	}

	public String getHref() {
		return Native.get_href(getPtr());
	}

	public void setTarget(String target) {
		Native.put_target(getPtr(), target);
	}

	public String getTarget() {
		return Native.get_target(getPtr());
	}

	private static class Native {
		//
		// IHTMLBaseElement
		//
		private static native void put_href(long ptr, String p);

		private static native String get_href(long ptr);

		private static native void put_target(long ptr, String p);

		private static native String get_target(long ptr);
	}
}
