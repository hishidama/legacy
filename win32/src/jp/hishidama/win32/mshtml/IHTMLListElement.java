package jp.hishidama.win32.mshtml;

/**
 * IHTMLListElement2ƒNƒ‰ƒX.
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">‚Ð‚µ‚¾‚Ü</a>
 * @since 2007.11.05
 */
public class IHTMLListElement extends IHTMLElement {

	protected IHTMLListElement(long dll_ptr) {
		super(dll_ptr);
	}

	public void setCompact(boolean b) {
		Native.put_compact(getPtr(), b);
	}

	public boolean getCompact() {
		return Native.get_compact(getPtr());
	}

	private static class Native {
		//
		// IHTMLListElement
		//

		//
		// IHTMLListElement2
		//
		private static native void put_compact(long ptr, boolean p);

		private static native boolean get_compact(long ptr);
	}
}
