package jp.hishidama.win32.mshtml;

import jp.hishidama.win32.com.ComPtr;

/**
 * IHTMLLabelElement,IHTMLLabelElement2クラス.
 * <p>
 * labelタグを扱うクラスです。
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">ひしだま</a>
 * @since 2007.11.05
 * @version 2008.07.14
 */
public class IHTMLLabelElement extends IHTMLElement {

	protected IHTMLLabelElement(long dll_ptr) {
		super(dll_ptr);
	}

	public void setHtmlFor(String htmlfor) {
		Native.put_htmlFor(getPtr(), htmlfor);
	}

	public String getHtmlFor() {
		return Native.get_htmlFor(getPtr());
	}

	public void setAccessKey(String key) {
		Native.put_accessKey(getPtr(), key);
	}

	public String getAccessKey() {
		return Native.get_accessKey(getPtr());
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

	private static class Native {
		//
		// IHTMLLabelElement
		//
		private static native void put_htmlFor(long ptr, String p);

		private static native String get_htmlFor(long ptr);

		private static native void put_accessKey(long ptr, String p);

		private static native String get_accessKey(long ptr);

		//
		// IHTMLLabelElement2
		//
		private static native IHTMLFormElement get_form(long ptr);
	}
}
