package jp.hishidama.win32.mshtml;

import jp.hishidama.win32.com.ComPtr;
import jp.hishidama.win32.com.IDispatch;
import jp.hishidama.win32.com.IUnknown;
import jp.hishidama.win32.com.Variant;

/**
 * IHTMLSelectElement,IHTMLSelectElement4クラス.
 * <p>
 * selectタグ（コンボボックス・リストボックス）を扱うクラスです。
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">ひしだま</a>
 * @since 2007.10.24
 * @version 2008.07.14
 */
public class IHTMLSelectElement extends IHTMLElement implements
		IHTMLControlElement {

	protected IHTMLSelectElement(long dll_ptr) {
		super(dll_ptr);
	}

	public void setSize(int size) {
		Native.put_size(getPtr(), size);
	}

	public int getSize() {
		return Native.get_size(getPtr());
	}

	public void setMultiple(boolean b) {
		Native.put_multiple(getPtr(), b);
	}

	public boolean getMultiple() {
		return Native.get_multiple(getPtr());
	}

	public void setName(String name) {
		Native.put_name(getPtr(), name);
	}

	public String getName() {
		return Native.get_name(getPtr());
	}

	/**
	 * ？取得.
	 * 
	 * @return HTML要素（HtmlSelectElementっぽいので、用途が不明）
	 */
	public IHTMLElement getOptions() {
		return (IHTMLElement) Native.get_options(getPtr());
	}

	/**
	 * @see ComPtr#disposeChild()
	 * @deprecated
	 * @see #getOptions()
	 */
	public IHTMLElement getOptions(boolean addChild) {
		return getOptions();
	}

	public void setSelectedIndex(int index) {
		Native.put_selectedIndex(getPtr(), index);
	}

	public int getSelectedIndex() {
		return Native.get_selectedIndex(getPtr());
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

	public void setDisabled(boolean b) {
		Native.put_disabled(getPtr(), b);
	}

	public boolean getDisabled() {
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

	public void add(IHTMLOptionElement element, Variant before) {
		Native.add(getPtr(), element.getPtr(), before);
	}

	public void remove(int index) {
		Native.remove(getPtr(), index);
	}

	public void setLength(int len) {
		Native.put_length(getPtr(), len);
	}

	public int getLength() {
		return Native.get_length(getPtr());
	}

	public IUnknown getNewEnum() {
		return Native.get__newEnum(getPtr());
	}

	/**
	 * option要素取得.
	 * 
	 * @param name
	 *            名前
	 * @param index
	 *            番号
	 * @return option要素
	 */
	public IHTMLOptionElement item(String name, int index) {
		return (IHTMLOptionElement) Native.item(getPtr(), new Variant(name),
				new Variant(index));
	}

	/**
	 * @see ComPtr#disposeChild()
	 * @deprecated
	 * @see #item(String, int)
	 */
	public IHTMLOptionElement item(String name, int index, boolean addChild) {
		return item(name, index);
	}

	/**
	 * option要素取得.
	 * 
	 * @param index
	 *            番号
	 * @return option要素
	 */
	public IHTMLOptionElement item(int index) {
		Variant vari = new Variant(index);
		return (IHTMLOptionElement) Native.item(getPtr(), vari, vari);
	}

	/**
	 * @see ComPtr#disposeChild()
	 * @deprecated
	 * @see #item(int)
	 */
	public IHTMLOptionElement item(int index, boolean addChild) {
		return item(index);
	}

	public IDispatch tags(Variant tagName) {
		return Native.tags(getPtr(), tagName);
	}

	public IDispatch urns(Variant urn) {
		return Native.urns(getPtr(), urn);
	}

	/**
	 * option要素取得.
	 * 
	 * @param name
	 *            名称
	 * @return option要素
	 */
	public IHTMLOptionElement getNamedItem(String name) {
		return (IHTMLOptionElement) Native.namedItem(getPtr(), name);
	}

	/**
	 * @see ComPtr#disposeChild()
	 * @deprecated
	 * @see #getNamedItem(String)
	 */
	public IHTMLOptionElement getNamedItem(String name, boolean addChild) {
		return getNamedItem(name);
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
		// IHTMLSelectElement
		//
		private static native void put_size(long ptr, int p);

		private static native int get_size(long ptr);

		private static native void put_multiple(long ptr, boolean p);

		private static native boolean get_multiple(long ptr);

		private static native void put_name(long ptr, String p);

		private static native String get_name(long ptr);

		private static native IDispatch get_options(long ptr);

		private static native void put_selectedIndex(long ptr, int p);

		private static native int get_selectedIndex(long ptr);

		private static native String get_type(long ptr);

		private static native void put_value(long ptr, String p);

		private static native String get_value(long ptr);

		private static native void put_disabled(long ptr, boolean p);

		private static native boolean get_disabled(long ptr);

		private static native IHTMLFormElement get_form(long ptr);

		private static native void add(long ptr, long element, Variant before);

		private static native void remove(long ptr, int index);

		private static native void put_length(long ptr, int p);

		private static native int get_length(long ptr);

		private static native IUnknown get__newEnum(long ptr);

		private static native IDispatch item(long ptr, Variant name,
				Variant index);

		private static native IDispatch tags(long ptr, Variant tagName);

		//
		// IHTMLSelectElement2
		//
		private static native IDispatch urns(long ptr, Variant urn);

		//
		// IHTMLSelectElement4
		//
		private static native IDispatch namedItem(long ptr, String name);
	}
}
