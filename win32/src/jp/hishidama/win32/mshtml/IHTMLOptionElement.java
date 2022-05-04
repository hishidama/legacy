package jp.hishidama.win32.mshtml;

import jp.hishidama.win32.com.ComPtr;

/**
 * IHTMLOptionElement,IHTMLOptionElement3クラス.
 * <p>
 * optionタグ（コンボボックス・リストボックスの明細）を扱うクラスです。
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">ひしだま</a>
 * @since 2007.10.24
 * @version 2008.07.14
 * @see IHTMLSelectElement
 */
public class IHTMLOptionElement extends IHTMLElement {

	protected IHTMLOptionElement(long dll_ptr) {
		super(dll_ptr);
	}

	public void setSelected(boolean b) {
		Native.put_selected(getPtr(), b);
	}

	public boolean getSelected() {
		return Native.get_selected(getPtr());
	}

	public void setValue(String value) {
		Native.put_value(getPtr(), value);
	}

	public String getValue() {
		return Native.get_value(getPtr());
	}

	public void setDefaultSelected(boolean b) {
		Native.put_defaultSelected(getPtr(), b);
	}

	public boolean getDefaultSelected() {
		return Native.get_defaultSelected(getPtr());
	}

	public void setIndex(int index) {
		Native.put_index(getPtr(), index);
	}

	public int getIndex() {
		return Native.get_index(getPtr());
	}

	public void setText(String text) {
		Native.put_text(getPtr(), text);
	}

	public String getText() {
		return Native.get_text(getPtr());
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

	public void setLabel(String label) {
		Native.put_label(getPtr(), label);
	}

	public String getLabel() {
		return Native.get_label(getPtr());
	}

	public static IHTMLOptionElement create(IHTMLWindow win, String text,
			String value, boolean defaultSelected, boolean selected) {
		return Native.create(win.getPtr(), text, value, defaultSelected,
				selected);
	}

	private static class Native {
		//
		// IHTMLOptionElement
		//
		private static native void put_selected(long ptr, boolean b);

		private static native boolean get_selected(long ptr);

		private static native void put_value(long ptr, String value);

		private static native String get_value(long ptr);

		private static native void put_defaultSelected(long ptr, boolean b);

		private static native boolean get_defaultSelected(long ptr);

		private static native void put_index(long ptr, int index);

		private static native int get_index(long ptr);

		private static native void put_text(long ptr, String text);

		private static native String get_text(long ptr);

		private static native IHTMLFormElement get_form(long ptr);

		//
		// IHTMLOptionElement3
		//
		private static native void put_label(long ptr, String label);

		private static native String get_label(long ptr);

		//
		// IHTMLOptionElementFactory
		//
		private static native IHTMLOptionElement create(long ptr, String text,
				String value, boolean defaultSelected, boolean selected);
	}
}
