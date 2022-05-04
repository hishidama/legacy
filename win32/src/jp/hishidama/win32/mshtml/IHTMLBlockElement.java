package jp.hishidama.win32.mshtml;

/**
 * IHTMLBlockElement,IHTMLBlockElement2ƒNƒ‰ƒX.
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">‚Ð‚µ‚¾‚Ü</a>
 * @since 2007.11.05
 */
public class IHTMLBlockElement extends IHTMLElement {

	protected IHTMLBlockElement(long dll_ptr) {
		super(dll_ptr);
	}

	public void setClear(String clear) {
		Native.put_clear(getPtr(), clear);
	}

	public String getClear() {
		return Native.get_clear(getPtr());
	}

	public void setCite(String cite) {
		Native.put_cite(getPtr(), cite);
	}

	public String getCite() {
		return Native.get_cite(getPtr());
	}

	public void setWidth(String width) {
		Native.put_width(getPtr(), width);
	}

	public String getWidth() {
		return Native.get_width(getPtr());
	}

	private static class Native {
		//
		// IHTMLBlockElement
		//
		private static native void put_clear(long ptr, String p);

		private static native String get_clear(long ptr);

		//
		// IHTMLBlockElement2
		//
		private static native void put_cite(long ptr, String p);

		private static native String get_cite(long ptr);

		private static native void put_width(long ptr, String p);

		private static native String get_width(long ptr);
	}
}
