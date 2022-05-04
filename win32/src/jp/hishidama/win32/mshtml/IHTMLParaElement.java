package jp.hishidama.win32.mshtml;

/**
 * IHTMLParaElementクラス.
 * <p>
 * pタグを扱うクラスです。
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">ひしだま</a>
 * @since 2007.11.05
 */
public class IHTMLParaElement extends IHTMLBlockElement {

	protected IHTMLParaElement(long dll_ptr) {
		super(dll_ptr);
	}

	public void setAlign(String align) {
		Native.put_align(getPtr(), align);
	}

	public String getAlign() {
		return Native.get_align(getPtr());
	}

	private static class Native {
		//
		// IHTMLParaElement
		//
		private static native void put_align(long ptr, String p);

		private static native String get_align(long ptr);
	}
}
