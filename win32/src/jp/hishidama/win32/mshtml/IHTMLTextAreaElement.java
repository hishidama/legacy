package jp.hishidama.win32.mshtml;

import jp.hishidama.win32.com.ComPtr;
import jp.hishidama.win32.com.Variant;

/**
 * IHTMLTextAreaElementクラス.
 * <p>
 * textareaタグを扱うクラスです。
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">ひしだま</a>
 * @since 2007.10.22
 * @version 2008.07.14
 */
public class IHTMLTextAreaElement extends IHTMLElement implements
		IHTMLControlElement, IHTMLTextContainer {

	protected IHTMLTextAreaElement(long dll_ptr) {
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

	public boolean isDisabled() {
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

	public void setDefaultValue(String value) {
		Native.put_defaultValue(getPtr(), value);
	}

	public String getDefaultValue() {
		return Native.get_defaultValue(getPtr());
	}

	public void select() {
		Native.select(getPtr());
	}

	public void setReadOnly(boolean b) {
		Native.put_readOnly(getPtr(), b);
	}

	public boolean isReadOnly() {
		return Native.get_readOnly(getPtr());
	}

	public void setRows(int rows) {
		Native.put_rows(getPtr(), rows);
	}

	public int getRows() {
		return Native.get_rows(getPtr());
	}

	public void setCols(int cols) {
		Native.put_cols(getPtr(), cols);
	}

	public int getCols() {
		return Native.get_cols(getPtr());
	}

	public void setWrap(String wrap) {
		Native.put_wrap(getPtr(), wrap);
	}

	public String getWrap() {
		return Native.get_wrap(getPtr());
	}

	/**
	 * テキスト範囲取得.
	 * 
	 * @return テキスト範囲
	 */
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
	// HtmlControlElement
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

	public void blur() {
		IHTMLControlElement$Native.blur(getPtr());
	}

	private static class Native {
		//
		// IHTMLTextAreaElement
		//
		private static native String get_type(long ptr);

		private static native void put_value(long ptr, String p);

		private static native String get_value(long ptr);

		private static native void put_name(long ptr, String p);

		private static native String get_name(long ptr);

		private static native void put_status(long ptr, Variant p);

		private static native Variant get_status(long ptr);

		private static native void put_disabled(long ptr, boolean b);

		private static native boolean get_disabled(long ptr);

		private static native IHTMLFormElement get_form(long ptr);

		private static native void put_defaultValue(long ptr, String p);

		private static native String get_defaultValue(long ptr);

		private static native void select(long ptr);

		private static native void put_readOnly(long ptr, boolean b);

		private static native boolean get_readOnly(long ptr);

		private static native void put_rows(long ptr, int p);

		private static native int get_rows(long ptr);

		private static native void put_cols(long ptr, int p);

		private static native int get_cols(long ptr);

		private static native void put_wrap(long ptr, String p);

		private static native String get_wrap(long ptr);

		private static native IHTMLTxtRange createTextRange(long ptr);
	}
}
