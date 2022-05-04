package jp.hishidama.win32.mshtml;

/**
 * IHTMLTableCaptionクラス.
 * <p>
 * tableのcaptionタグを扱うクラスです。
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">ひしだま</a>
 * @since 2007.11.04
 */
public class IHTMLTableCaption extends IHTMLElement implements
		IHTMLControlElement, IHTMLTextContainer {

	protected IHTMLTableCaption(long dll_ptr) {
		super(dll_ptr);
	}

	public void setAlign(String align) {
		Native.put_align(getPtr(), align);
	}

	public String getAlign() {
		return Native.get_align(getPtr());
	}

	public void setVAlign(String valign) {
		Native.put_vAlign(getPtr(), valign);
	}

	public String getVAlign() {
		return Native.get_vAlign(getPtr());
	}

	//
	// IHTMLControlElement
	//

	public void setTabIndex(int index) {
		IHTMLControlElement$Native.put_tabIndex(getPtr(), index);
	}

	public int getTabIndex() {
		return IHTMLControlElement$Native.get_tabIndex(getPtr());
	}

	public void setAccessKey(String key) {
		IHTMLControlElement$Native.put_accessKey(getPtr(), key);
	}

	public String getAccessKey() {
		return IHTMLControlElement$Native.get_accessKey(getPtr());
	}

	public void focus() {
		IHTMLControlElement$Native.focus(getPtr());
	}

	public void blur() {
		IHTMLControlElement$Native.blur(getPtr());
	}

	public int getClientHeight() {
		return IHTMLControlElement$Native.get_clientHeight(getPtr());
	}

	public int getClientWidth() {
		return IHTMLControlElement$Native.get_clientWidth(getPtr());
	}

	public int getClientTop() {
		return IHTMLControlElement$Native.get_clientTop(getPtr());
	}

	public int getClientLeft() {
		return IHTMLControlElement$Native.get_clientLeft(getPtr());
	}

	private static class Native {
		//
		// IHTMLTableCaption
		//
		private static native void put_align(long ptr, String p);

		private static native String get_align(long ptr);

		private static native void put_vAlign(long ptr, String p);

		private static native String get_vAlign(long ptr);
	}
}
