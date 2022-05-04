package jp.hishidama.win32.mshtml;

/**
 * IHTMLDDElementクラス.
 * <p>
 * ddタグを扱うクラスです。
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">ひしだま</a>
 * @since 2007.11.05
 */
public class IHTMLDDElement extends IHTMLElement {

	protected IHTMLDDElement(long dll_ptr) {
		super(dll_ptr);
	}

	public void setNoWrap(boolean b) {
		Native.put_noWrap(getPtr(), b);
	}

	public boolean getNoWrap() {
		return Native.get_noWrap(getPtr());
	}

	private static class Native {
		//
		// IHTMLDDElement
		//
		private static native void put_noWrap(long ptr, boolean p);

		private static native boolean get_noWrap(long ptr);
	}
}
