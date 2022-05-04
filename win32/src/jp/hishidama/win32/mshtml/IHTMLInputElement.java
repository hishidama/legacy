package jp.hishidama.win32.mshtml;

import jp.hishidama.win32.com.ComPtr;
import jp.hishidama.win32.com.Variant;

/**
 * IHTMLInputElement,IHTMLInputElement2クラス.
 * <p>
 * inputタグを扱うクラスです。
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">ひしだま</a>
 * @since 2007.10.22
 * @version 2008.07.14
 */
public class IHTMLInputElement extends IHTMLElement implements
		IHTMLControlElement {

	protected IHTMLInputElement(long dll_ptr) {
		super(dll_ptr);
	}

	/**
	 * タイプ設定.
	 * 
	 * @param type
	 *            タイプ
	 */
	public void setType(String type) {
		Native.put_type(getPtr(), type);
	}

	/**
	 * タイプ取得.
	 * 
	 * @return タイプ
	 */
	public String getType() {
		return Native.get_type(getPtr());
	}

	/**
	 * 値設定.
	 * 
	 * @param value
	 *            値
	 */
	public void setValue(String value) {
		Native.put_value(getPtr(), value);
	}

	/**
	 * 値取得.
	 * 
	 * @return 値
	 */
	public String getValue() {
		return Native.get_value(getPtr());
	}

	/**
	 * 名前設定.
	 * 
	 * @param name
	 *            名前
	 */
	public void setName(String name) {
		Native.put_name(getPtr(), name);
	}

	/**
	 * 名前取得.
	 * 
	 * @return 名前
	 */
	public String getName() {
		return Native.get_name(getPtr());
	}

	public void setStatus(boolean b) {
		Native.put_status(getPtr(), b);
	}

	public boolean getStatus() {
		return Native.get_status(getPtr());
	}

	public void setDisabled(boolean b) {
		Native.put_disabled(getPtr(), b);
	}

	public boolean isDisabled() {
		return Native.get_disabled(getPtr());
	}

	/**
	 * フォーム取得.
	 * 
	 * @return フォーム要素
	 */
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

	public void setSize(int size) {
		Native.put_size(getPtr(), size);
	}

	public int getSize() {
		return Native.get_size(getPtr());
	}

	public void setMaxLength(int length) {
		Native.put_maxLength(getPtr(), length);
	}

	public int getMaxLength() {
		return Native.get_maxLength(getPtr());
	}

	public void setDefaultValue(String value) {
		Native.put_defaultValue(getPtr(), value);
	}

	public String getDefaultValue() {
		return Native.get_defaultValue(getPtr());
	}

	public void setReadOnly(boolean b) {
		Native.put_readOnly(getPtr(), b);
	}

	public boolean getReadOnly() {
		return Native.get_readOnly(getPtr());
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

	public void setDefaultChecked(boolean b) {
		Native.put_defaultChecked(getPtr(), b);
	}

	public boolean getDefaultChecked() {
		return Native.get_defaultChecked(getPtr());
	}

	public void setChecked(boolean b) {
		Native.put_checked(getPtr(), b);
	}

	public boolean getChecked() {
		return Native.get_checked(getPtr());
	}

	public void setBorder(Variant border) {
		Native.put_border(getPtr(), border);
	}

	public Variant getBorder() {
		return Native.get_border(getPtr());
	}

	public void setVspace(int p) {
		Native.put_vspace(getPtr(), p);
	}

	public int getVspace() {
		return Native.get_vspace(getPtr());
	}

	public void setHspace(int p) {
		Native.put_hspace(getPtr(), p);
	}

	public int getHspace() {
		return Native.get_hspace(getPtr());
	}

	public void setAlt(String alt) {
		Native.put_alt(getPtr(), alt);
	}

	public String getAlt() {
		return Native.get_alt(getPtr());
	}

	public void setSrc(String src) {
		Native.put_src(getPtr(), src);
	}

	public String getSrc() {
		return Native.get_src(getPtr());
	}

	public String getReadyState() {
		return Native.get_readyState(getPtr());
	}

	public boolean getComplete() {
		return Native.get_complete(getPtr());
	}

	public void setLoop(Variant loop) {
		Native.put_loop(getPtr(), loop);
	}

	public Variant getLoop() {
		return Native.get_loop(getPtr());
	}

	public void setAlign(String align) {
		Native.put_align(getPtr(), align);
	}

	public String getAlign() {
		return Native.get_align(getPtr());
	}

	public void setWidth(int width) {
		Native.put_width(getPtr(), width);
	}

	public int getWidth() {
		return Native.get_width(getPtr());
	}

	public void setHeight(int height) {
		Native.put_height(getPtr(), height);
	}

	public int getHeight() {
		return Native.get_height(getPtr());
	}

	public void setStart(String start) {
		Native.put_Start(getPtr(), start);
	}

	public String getStart() {
		return Native.get_Start(getPtr());
	}

	public void setAccept(String accept) {
		Native.put_accept(getPtr(), accept);
	}

	public String getAccept() {
		return Native.get_accept(getPtr());
	}

	public void setUseMap(String map) {
		Native.put_useMap(getPtr(), map);
	}

	public String getUseMap() {
		return Native.get_useMap(getPtr());
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
		// IHtmlInputElement
		//

		private static native void put_type(long ptr, String type);

		private static native String get_type(long ptr);

		private static native void put_value(long ptr, String value);

		private static native String get_value(long ptr);

		private static native void put_name(long ptr, String name);

		private static native String get_name(long ptr);

		private static native void put_status(long ptr, boolean b);

		private static native boolean get_status(long ptr);

		private static native void put_disabled(long ptr, boolean b);

		private static native boolean get_disabled(long ptr);

		private static native IHTMLFormElement get_form(long ptr);

		private static native void put_size(long ptr, int size);

		private static native int get_size(long ptr);

		private static native void put_maxLength(long ptr, int length);

		private static native int get_maxLength(long ptr);

		private static native void put_defaultValue(long ptr, String value);

		private static native String get_defaultValue(long ptr);

		private static native void put_readOnly(long ptr, boolean b);

		private static native boolean get_readOnly(long ptr);

		private static native IHTMLTxtRange createTextRange(long ptr);

		private static native void put_defaultChecked(long ptr, boolean b);

		private static native boolean get_defaultChecked(long ptr);

		private static native void put_checked(long ptr, boolean b);

		private static native boolean get_checked(long ptr);

		private static native void put_border(long ptr, Variant p);

		private static native Variant get_border(long ptr);

		private static native void put_vspace(long ptr, int p);

		private static native int get_vspace(long ptr);

		private static native void put_hspace(long ptr, int p);

		private static native int get_hspace(long ptr);

		private static native void put_alt(long ptr, String alt);

		private static native String get_alt(long ptr);

		private static native void put_src(long ptr, String src);

		private static native String get_src(long ptr);

		private static native String get_readyState(long ptr);

		private static native boolean get_complete(long ptr);

		private static native void put_loop(long ptr, Variant p);

		private static native Variant get_loop(long ptr);

		private static native void put_align(long ptr, String align);

		private static native String get_align(long ptr);

		private static native void put_width(long ptr, int width);

		private static native int get_width(long ptr);

		private static native void put_height(long ptr, int height);

		private static native int get_height(long ptr);

		private static native void put_Start(long ptr, String start);

		private static native String get_Start(long ptr);

		//
		// IHtmlInputElement2
		//

		private static native void put_accept(long ptr, String accept);

		private static native String get_accept(long ptr);

		private static native void put_useMap(long ptr, String map);

		private static native String get_useMap(long ptr);
	}
}
