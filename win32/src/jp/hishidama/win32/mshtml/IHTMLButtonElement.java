package jp.hishidama.win32.mshtml;

import jp.hishidama.win32.com.ComPtr;
import jp.hishidama.win32.com.Variant;

/**
 * IHTMLButtonElementクラス.
 * <p>
 * buttonタグを扱うクラスです。
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">ひしだま</a>
 * @since 2007.11.05
 * @version 2008.07.14
 */
public class IHTMLButtonElement extends IHTMLElement implements
		IHTMLTextContainer {

	protected IHTMLButtonElement(long dll_ptr) {
		super(dll_ptr);
	}

	public String getType() {
		return Native.get_type(getPtr());
	}

	public void setValue(String value) {
		Native.put_value(getPtr(), value);
	}

	public String getValue() {
		return Native.get_value(getPtr());
	}

	public void setName(String name) {
		Native.put_name(getPtr(), name);
	}

	public String getName() {
		return Native.get_name(getPtr());
	}

	public void setStatus(Variant status) {
		Native.put_status(getPtr(), status);
	}

	public Variant getStatus() {
		return Native.get_status(getPtr());
	}

	public void setDisabled(boolean b) {
		Native.put_disabled(getPtr(), b);
	}

	public boolean getDisabled() {
		return Native.get_disabled(getPtr());
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

	private static class Native {
		//
		// IHTMLButtonElement
		//
		private static native String get_type(long ptr);

		private static native void put_value(long ptr, String p);

		private static native String get_value(long ptr);

		private static native void put_name(long ptr, String p);

		private static native String get_name(long ptr);

		private static native void put_status(long ptr, Variant p);

		private static native Variant get_status(long ptr);

		private static native void put_disabled(long ptr, boolean p);

		private static native boolean get_disabled(long ptr);

		private static native IHTMLFormElement get_form(long ptr);

		private static native IHTMLTxtRange createTextRange(long ptr);
	}
}
