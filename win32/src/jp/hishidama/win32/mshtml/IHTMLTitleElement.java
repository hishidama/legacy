package jp.hishidama.win32.mshtml;

/**
 * IHTMLTitleElementクラス.
 * <p>
 * titleタグを扱うクラスです。
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">ひしだま</a>
 * @since 2007.11.05
 */
public class IHTMLTitleElement extends IHTMLElement {

	protected IHTMLTitleElement(long dll_ptr) {
		super(dll_ptr);
	}

	public void setText(String text) {
		Native.put_text(getPtr(), text);
	}

	public String getText() {
		return Native.get_text(getPtr());
	}

	private static class Native {
		//
		// IHTMLTitleElement
		//
		private static native void put_text(long ptr, String p);

		private static native String get_text(long ptr);
	}
}
