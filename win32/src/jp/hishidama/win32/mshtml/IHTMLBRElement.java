package jp.hishidama.win32.mshtml;

/**
 * IHTMLBRElementクラス.
 * <p>
 * brタグを扱うクラスです。
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">ひしだま</a>
 * @since 2007.11.05
 */
public class IHTMLBRElement extends IHTMLElement {

	protected IHTMLBRElement(long dll_ptr) {
		super(dll_ptr);
	}

	public void setClear(String clear) {
		Native.put_clear(getPtr(), clear);
	}

	public String getClear() {
		return Native.get_clear(getPtr());
	}

	private static class Native {
		//
		// IHTMLBRElement
		//
		private static native void put_clear(long ptr, String p);

		private static native String get_clear(long ptr);
	}
}
