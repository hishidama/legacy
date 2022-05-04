package jp.hishidama.win32.mshtml;

import jp.hishidama.win32.com.ComPtr;

/**
 * IHTMLLegendElement,IHTMLLegendElement2クラス.
 * <p>
 * legendタグを扱うクラスです。
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">ひしだま</a>
 * @since 2007.11.05
 * @version 2008.07.14
 */
public class IHTMLLegendElement extends IHTMLElement implements
		IHTMLControlElement {

	protected IHTMLLegendElement(long dll_ptr) {
		super(dll_ptr);
	}

	public void setAlign(String align) {
		Native.put_align(getPtr(), align);
	}

	public String getAlign() {
		return Native.get_align(getPtr());
	}

	public IHTMLFormElement getForm() {
		return Native.get_form(getPtr());
	}

	/**
	 * @see ComPtr#disposeChild()
	 * @deprecated
	 * @see #getForm()
	 */
	public IHTMLFormElement getForm(boolean addChild) {
		return getForm();
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
		// IHTMLLegendElement
		//
		private static native void put_align(long ptr, String p);

		private static native String get_align(long ptr);

		//
		// IHTMLLegendElement2
		//
		private static native IHTMLFormElement get_form(long ptr);
	}
}
