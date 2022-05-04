package jp.hishidama.win32.mshtml;

import jp.hishidama.win32.com.Variant;

/**
 * IHTMLTableCell,IHTMLTableCell2クラス.
 * <p>
 * tableのth,tdタグを扱うクラスです。
 * </p>
 * 
 * @see IHTMLTable#getCells(boolean)
 * @see IHTMLTableRow#getCells(boolean)
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">ひしだま</a>
 * @since 2007.11.04
 */
public class IHTMLTableCell extends IHTMLElement implements
		IHTMLControlElement, IHTMLTextContainer {

	protected IHTMLTableCell(long dll_ptr) {
		super(dll_ptr);
	}

	public void setRowSpan(int span) {
		Native.put_rowSpan(getPtr(), span);
	}

	public int getRowSpan() {
		return Native.get_rowSpan(getPtr());
	}

	public void setColSpan(int span) {
		Native.put_colSpan(getPtr(), span);
	}

	public int getColSpan() {
		return Native.get_colSpan(getPtr());
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

	public void setNoWrap(boolean b) {
		Native.put_noWrap(getPtr(), b);
	}

	public boolean getNoWrap() {
		return Native.get_noWrap(getPtr());
	}

	public void setBackground(String bg) {
		Native.put_background(getPtr(), bg);
	}

	public String getBackground() {
		return Native.get_background(getPtr());
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

	public void setWidth(Variant width) {
		Native.put_width(getPtr(), width);
	}

	public Variant getWidth() {
		return Native.get_width(getPtr());
	}

	public void setHeight(Variant height) {
		Native.put_height(getPtr(), height);
	}

	public Variant getHeight() {
		return Native.get_height(getPtr());
	}

	public int getCellIndex() {
		return Native.get_cellIndex(getPtr());
	}

	public void setAbbr(String addr) {
		Native.put_abbr(getPtr(), addr);
	}

	public String getAbbr() {
		return Native.get_abbr(getPtr());
	}

	public void setAxis(String axis) {
		Native.put_axis(getPtr(), axis);
	}

	public String getAxis() {
		return Native.get_axis(getPtr());
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

	public void setHeaders(String head) {
		Native.put_headers(getPtr(), head);
	}

	public String getHeaders() {
		return Native.get_headers(getPtr());
	}

	public void setScope(String scope) {
		Native.put_scope(getPtr(), scope);
	}

	public String getScope() {
		return Native.get_scope(getPtr());
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
		// IHTMLTableCell
		//
		private static native void put_rowSpan(long ptr, int v);

		private static native int get_rowSpan(long ptr);

		private static native void put_colSpan(long ptr, int v);

		private static native int get_colSpan(long ptr);

		private static native void put_align(long ptr, String v);

		private static native String get_align(long ptr);

		private static native void put_vAlign(long ptr, String v);

		private static native String get_vAlign(long ptr);

		private static native void put_bgColor(long ptr, Variant v);

		private static native Variant get_bgColor(long ptr);

		private static native void put_noWrap(long ptr, boolean v);

		private static native boolean get_noWrap(long ptr);

		private static native void put_background(long ptr, String v);

		private static native String get_background(long ptr);

		private static native void put_borderColor(long ptr, Variant v);

		private static native Variant get_borderColor(long ptr);

		private static native void put_borderColorLight(long ptr, Variant v);

		private static native Variant get_borderColorLight(long ptr);

		private static native void put_borderColorDark(long ptr, Variant v);

		private static native Variant get_borderColorDark(long ptr);

		private static native void put_width(long ptr, Variant v);

		private static native Variant get_width(long ptr);

		private static native void put_height(long ptr, Variant v);

		private static native Variant get_height(long ptr);

		private static native int get_cellIndex(long ptr);

		//
		// IHTMLTableCell2
		//

		private static native void put_abbr(long ptr, String v);

		private static native String get_abbr(long ptr);

		private static native void put_axis(long ptr, String v);

		private static native String get_axis(long ptr);

		private static native void put_ch(long ptr, String v);

		private static native String get_ch(long ptr);

		private static native void put_chOff(long ptr, String v);

		private static native String get_chOff(long ptr);

		private static native void put_headers(long ptr, String v);

		private static native String get_headers(long ptr);

		private static native void put_scope(long ptr, String v);

		private static native String get_scope(long ptr);
	}
}
