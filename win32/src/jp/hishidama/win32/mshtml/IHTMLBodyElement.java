package jp.hishidama.win32.mshtml;

import jp.hishidama.win32.com.ComPtr;
import jp.hishidama.win32.com.Variant;

/**
 * IHTMLBodyElementクラス.
 * <p>
 * bodyタグを扱うクラスです。
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">ひしだま</a>
 * @since 2007.11.04
 * @version 2008.07.14
 */
public class IHTMLBodyElement extends IHTMLElement implements
		IHTMLControlElement, IHTMLTextContainer {

	protected IHTMLBodyElement(long dll_ptr) {
		super(dll_ptr);
	}

	public void setBackground(String bg) {
		Native.put_background(getPtr(), bg);
	}

	public String getBackground() {
		return Native.get_background(getPtr());
	}

	public void setBgProperties(String properties) {
		Native.put_bgProperties(getPtr(), properties);
	}

	public String getBgProperties() {
		return Native.get_bgProperties(getPtr());
	}

	public void setLeftMargin(Variant margin) {
		Native.put_leftMargin(getPtr(), margin);
	}

	public Variant getLeftMargin() {
		return Native.get_leftMargin(getPtr());
	}

	public void setTopMargin(Variant margin) {
		Native.put_topMargin(getPtr(), margin);
	}

	public Variant getTopMargin() {
		return Native.get_topMargin(getPtr());
	}

	public void setRightMargin(Variant margin) {
		Native.put_rightMargin(getPtr(), margin);
	}

	public Variant getRightMargin() {
		return Native.get_rightMargin(getPtr());
	}

	public void setBottomMargin(Variant margin) {
		Native.put_bottomMargin(getPtr(), margin);
	}

	public Variant getBottomMargin() {
		return Native.get_bottomMargin(getPtr());
	}

	public void setNoWrap(boolean b) {
		Native.put_noWrap(getPtr(), b);
	}

	public boolean getNoWrap() {
		return Native.get_noWrap(getPtr());
	}

	public void setBgColor(Variant color) {
		Native.put_bgColor(getPtr(), color);
	}

	public Variant getBgColor() {
		return Native.get_bgColor(getPtr());
	}

	public void setText(String text) {
		Native.put_text(getPtr(), text);
	}

	public String getText() {
		return Native.get_text(getPtr());
	}

	public void setLink(Variant link) {
		Native.put_link(getPtr(), link);
	}

	public Variant getLink() {
		return Native.get_link(getPtr());
	}

	public void setVLink(Variant vlink) {
		Native.put_vLink(getPtr(), vlink);
	}

	public Variant getVLink() {
		return Native.get_vLink(getPtr());
	}

	public void setALink(Variant alink) {
		Native.put_aLink(getPtr(), alink);
	}

	public Variant getALink() {
		return Native.get_aLink(getPtr());
	}

	public void setScroll(String scroll) {
		Native.put_scroll(getPtr(), scroll);
	}

	public String getScroll() {
		return Native.get_scroll(getPtr());
	}

	public IHTMLTxtRange createTextRange() {
		return Native.createTextRange(getPtr());
	}

	/**
	 * @see ComPtr#disposeChild()
	 * @deprecated
	 * @see #createTextRange()
	 */
	public IHTMLTxtRange createTextRange(boolean addChild) {
		return createTextRange();
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
		// IHTMLBodyElement
		//

		private static native void put_background(long ptr, String p);

		private static native String get_background(long ptr);

		private static native void put_bgProperties(long ptr, String p);

		private static native String get_bgProperties(long ptr);

		private static native void put_leftMargin(long ptr, Variant p);

		private static native Variant get_leftMargin(long ptr);

		private static native void put_topMargin(long ptr, Variant p);

		private static native Variant get_topMargin(long ptr);

		private static native void put_rightMargin(long ptr, Variant p);

		private static native Variant get_rightMargin(long ptr);

		private static native void put_bottomMargin(long ptr, Variant p);

		private static native Variant get_bottomMargin(long ptr);

		private static native void put_noWrap(long ptr, boolean p);

		private static native boolean get_noWrap(long ptr);

		private static native void put_bgColor(long ptr, Variant p);

		private static native Variant get_bgColor(long ptr);

		private static native void put_text(long ptr, String p);

		private static native String get_text(long ptr);

		private static native void put_link(long ptr, Variant p);

		private static native Variant get_link(long ptr);

		private static native void put_vLink(long ptr, Variant p);

		private static native Variant get_vLink(long ptr);

		private static native void put_aLink(long ptr, Variant p);

		private static native Variant get_aLink(long ptr);

		private static native void put_scroll(long ptr, String p);

		private static native String get_scroll(long ptr);

		private static native IHTMLTxtRange createTextRange(long ptr);

		//
		// IHTMLBodyElement2
		//
	}
}
