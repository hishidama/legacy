package jp.hishidama.win32.mshtml;

/**
 * IHTMLUListElementクラス.
 * <p>
 * ulタグを扱うクラスです。
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">ひしだま</a>
 * @since 2007.11.05
 */
public class IHTMLUListElement extends IHTMLListElement {

	protected IHTMLUListElement(long dll_ptr) {
		super(dll_ptr);
	}

	public void setCompact(boolean b) {
		Native.put_compact(getPtr(), b);
	}

	public boolean getCompact() {
		return Native.get_compact(getPtr());
	}

	public void setType(String type) {
		Native.put_type(getPtr(), type);
	}

	public String getType() {
		return Native.get_type(getPtr());
	}

	private static class Native {
		//
		// IHTMLUListElement
		//
		private static native void put_compact(long ptr, boolean p);

		private static native boolean get_compact(long ptr);

		private static native void put_type(long ptr, String p);

		private static native String get_type(long ptr);
	}
}
