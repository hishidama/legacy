package jp.hishidama.win32.mshtml;

import jp.hishidama.win32.com.ComPtr;
import jp.hishidama.win32.com.IDispatch;
import jp.hishidama.win32.com.Variant;

/**
 * IHTMLTableSection,IHTMLTableSection2～3クラス.
 * <p>
 * tableのthead,tfoot,tbodyタグを扱うクラスです。
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">ひしだま</a>
 * @since 2007.11.04
 * @version 2008.07.14
 */
public class IHTMLTableSection extends IHTMLElement {

	protected IHTMLTableSection(long dll_ptr) {
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

	public void setBgColor(Variant color) {
		Native.put_bgColor(getPtr(), color);
	}

	public Variant getBgColor() {
		return Native.get_bgColor(getPtr());
	}

	public IHTMLElementCollection getRows() {
		return Native.get_rows(getPtr());
	}

	/**
	 * @see ComPtr#disposeChild()
	 * @deprecated
	 * @see #getRows()
	 */
	public IHTMLElementCollection getRows(boolean addChild) {
		return getRows();
	}

	public IDispatch insertRow(int index) {
		return Native.insertRow(getPtr(), index);
	}

	/**
	 * @see ComPtr#disposeChild()
	 * @deprecated
	 * @see #insertRow(int)
	 */
	public IDispatch insertRow(int index, boolean addChild) {
		return insertRow(index);
	}

	public void deleteRow(int index) {
		Native.deleteRow(getPtr(), index);
	}

	public IDispatch moveRow(int indexFrom, int indexTo) {
		return Native.moveRow(getPtr(), indexFrom, indexTo);
	}

	/**
	 * @see ComPtr#disposeChild()
	 * @deprecated
	 * @see #moveRow(int, int)
	 */
	public IDispatch moveRow(int indexFrom, int indexTo, boolean addChild) {
		return moveRow(indexFrom, indexTo);
	}

	public void setCh(String ch) {
		Native.put_ch(getPtr(), ch);
	}

	public String getCh() {
		return Native.get_ch(getPtr());
	}

	public void setChOff(String off) {
		Native.put_chOff(getPtr(), off);
	}

	public String getChOff() {
		return Native.get_chOff(getPtr());
	}

	private static class Native {
		//
		// IHTMLTableSection
		//
		private static native void put_align(long ptr, String p);

		private static native String get_align(long ptr);

		private static native void put_vAlign(long ptr, String p);

		private static native String get_vAlign(long ptr);

		private static native void put_bgColor(long ptr, Variant p);

		private static native Variant get_bgColor(long ptr);

		private static native IHTMLElementCollection get_rows(long ptr);

		private static native IDispatch insertRow(long ptr, int index);

		private static native void deleteRow(long ptr, int index);

		//
		// IHTMLTableSection2
		//
		private static native IDispatch moveRow(long ptr, int indexFrom,
				int indexTo);

		//
		// IHTMLTableSection3
		//
		private static native void put_ch(long ptr, String p);

		private static native String get_ch(long ptr);

		private static native void put_chOff(long ptr, String p);

		private static native String get_chOff(long ptr);
	}
}
