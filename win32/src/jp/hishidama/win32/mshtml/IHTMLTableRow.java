package jp.hishidama.win32.mshtml;

import jp.hishidama.win32.com.ComPtr;
import jp.hishidama.win32.com.IDispatch;
import jp.hishidama.win32.com.Variant;

/**
 * IHTMLTableRow,IHTMLTableRow2～3,IHTMLTableRowMetricsクラス.
 * <p>
 * tableのtrタグを扱うクラスです。
 * </p>
 * 
 * @see IHTMLTable#getRows(boolean)
 * @see IHTMLTableCell#getParentElement(boolean)
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">ひしだま</a>
 * @since 2007.11.04
 * @version 2008.07.14
 */
public class IHTMLTableRow extends IHTMLElement {

	protected IHTMLTableRow(long dll_ptr) {
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

	public void setBorderColor(Variant color) {
		Native.put_borderColor(getPtr(), color);
	}

	public Variant getBorderColor() {
		return Native.get_borderColor(getPtr());
	}

	public void setBorderColorLight(Variant color) {
		Native.put_borderColorLight(getPtr(), color);
	}

	public Variant getBorderColorLight() {
		return Native.get_borderColorLight(getPtr());
	}

	public void setBorderColorDark(Variant color) {
		Native.put_borderColorDark(getPtr(), color);
	}

	public Variant getBorderColorDark() {
		return Native.get_borderColorDark(getPtr());
	}

	public int getRowIndex() {
		return Native.get_rowIndex(getPtr());
	}

	public int getSectionRowIndex() {
		return Native.get_sectionRowIndex(getPtr());
	}

	public IHTMLElementCollection getCells() {
		return Native.get_cells(getPtr());
	}

	/**
	 * @see ComPtr#disposeChild()
	 * @deprecated
	 * @see #getCells()
	 */
	public IHTMLElementCollection getCells(boolean addChild) {
		return getCells();
	}

	public IDispatch insertCell(int index) {
		return Native.insertCell(getPtr(), index);
	}

	/**
	 * @see ComPtr#disposeChild()
	 * @deprecated
	 * @see #insertCell(int)
	 */
	public IDispatch insertCell(int index, boolean addChild) {
		return insertCell(index);
	}

	public void deleteCell(int index) {
		Native.deleteCell(getPtr(), index);
	}

	public void setHeight(Variant height) {
		Native.put_height(getPtr(), height);
	}

	public Variant getHeight() {
		return Native.get_height(getPtr());
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

	public int getClientHeight() {
		return Native.get_clientHeight(getPtr());
	}

	public int getClientWidth() {
		return Native.get_clientWidth(getPtr());
	}

	public int getClientTop() {
		return Native.get_clientTop(getPtr());
	}

	public int getClientLeft() {
		return Native.get_clientLeft(getPtr());
	}

	private static class Native {
		//
		// IHTMLTableRow
		//
		private static native void put_align(long ptr, String v);

		private static native String get_align(long ptr);

		private static native void put_vAlign(long ptr, String v);

		private static native String get_vAlign(long ptr);

		private static native void put_bgColor(long ptr, Variant v);

		private static native Variant get_bgColor(long ptr);

		private static native void put_borderColor(long ptr, Variant v);

		private static native Variant get_borderColor(long ptr);

		private static native void put_borderColorLight(long ptr, Variant v);

		private static native Variant get_borderColorLight(long ptr);

		private static native void put_borderColorDark(long ptr, Variant v);

		private static native Variant get_borderColorDark(long ptr);

		private static native int get_rowIndex(long ptr);

		private static native int get_sectionRowIndex(long ptr);

		private static native IHTMLElementCollection get_cells(long ptr);

		private static native IDispatch insertCell(long ptr, int index);

		private static native void deleteCell(long ptr, int index);

		//
		// IHTMLTableRow2
		//
		private static native void put_height(long ptr, Variant v);

		private static native Variant get_height(long ptr);

		//
		// IHTMLTableRow3
		//
		private static native void put_ch(long ptr, String v);

		private static native String get_ch(long ptr);

		private static native void put_chOff(long ptr, String v);

		private static native String get_chOff(long ptr);

		//
		// IHTMLTableRowMetrics
		//
		private static native int get_clientHeight(long ptr);

		private static native int get_clientWidth(long ptr);

		private static native int get_clientTop(long ptr);

		private static native int get_clientLeft(long ptr);
	}
}
