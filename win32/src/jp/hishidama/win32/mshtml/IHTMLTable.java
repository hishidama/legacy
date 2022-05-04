package jp.hishidama.win32.mshtml;

import jp.hishidama.win32.com.ComPtr;
import jp.hishidama.win32.com.IDispatch;
import jp.hishidama.win32.com.Variant;

/**
 * IHTMLTable,IHTMLTable2～3クラス.
 * <p>
 * tableタグを扱うクラスです。
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">ひしだま</a>
 * @since 2007.11.04
 * @version 2008.07.14
 */
public class IHTMLTable extends IHTMLElement implements IHTMLControlElement {

	protected IHTMLTable(long dll_ptr) {
		super(dll_ptr);
	}

	public void setCols(int cols) {
		Native.put_cols(getPtr(), cols);
	}

	public int getCols() {
		return Native.get_cols(getPtr());
	}

	public void setBorder(Variant border) {
		Native.put_border(getPtr(), border);
	}

	public Variant getBorder() {
		return Native.get_border(getPtr());
	}

	public void setFrame(String frame) {
		Native.put_frame(getPtr(), frame);
	}

	public String getFrame() {
		return Native.get_frame(getPtr());
	}

	public void setRules(String rule) {
		Native.put_rules(getPtr(), rule);
	}

	public String getRules() {
		return Native.get_rules(getPtr());
	}

	public void setCellSpacing(Variant space) {
		Native.put_cellSpacing(getPtr(), space);
	}

	public Variant getCellSpacing() {
		return Native.get_cellSpacing(getPtr());
	}

	public void setCellPadding(Variant pad) {
		Native.put_cellPadding(getPtr(), pad);
	}

	public Variant getCellPadding() {
		return Native.get_cellPadding(getPtr());
	}

	public void setBackground(String bg) {
		Native.put_background(getPtr(), bg);
	}

	public String getBackground() {
		return Native.get_background(getPtr());
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

	public void setAlign(String align) {
		Native.put_align(getPtr(), align);
	}

	public String getAlign() {
		return Native.get_align(getPtr());
	}

	public void refresh() {
		Native.refresh(getPtr());
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

	public void setDataPageSize(int size) {
		Native.put_dataPageSize(getPtr(), size);
	}

	public int getDataPageSize() {
		return Native.get_dataPageSize(getPtr());
	}

	public void nextPage() {
		Native.nextPage(getPtr());
	}

	public void previousPage() {
		Native.previousPage(getPtr());
	}

	public IHTMLTableSection getTHead() {
		return Native.get_tHead(getPtr());
	}

	/**
	 * @see ComPtr#disposeChild()
	 * @deprecated
	 * @see #getTHead()
	 */
	public IHTMLTableSection getTHead(boolean addChild) {
		return getTHead();
	}

	public IHTMLTableSection getTFoot() {
		return Native.get_tFoot(getPtr());
	}

	/**
	 * @see ComPtr#disposeChild()
	 * @deprecated
	 * @see #getTFoot()
	 */
	public IHTMLTableSection getTFoot(boolean addChild) {
		return getTFoot();
	}

	public IHTMLElementCollection getTBodies() {
		return Native.get_tBodies(getPtr());
	}

	/**
	 * @see ComPtr#disposeChild()
	 * @deprecated
	 * @see #getTBodies()
	 */
	public IHTMLElementCollection getTBodies(boolean addChild) {
		return getTBodies();
	}

	public IHTMLTableCaption getCaption() {
		return Native.get_caption(getPtr());
	}

	/**
	 * @see ComPtr#disposeChild()
	 * @deprecated
	 * @see #getCaption()
	 */
	public IHTMLTableCaption getCaption(boolean addChild) {
		return getCaption();
	}

	public IDispatch createTHead() {
		return Native.createTHead(getPtr());
	}

	/**
	 * @see ComPtr#disposeChild()
	 * @deprecated
	 * @see #createTHead()
	 */
	public IDispatch createTHead(boolean addChild) {
		return createTHead();
	}

	public void deleteTHead() {
		Native.deleteTHead(getPtr());
	}

	public IDispatch createTFoot() {
		return Native.createTFoot(getPtr());
	}

	/**
	 * @see ComPtr#disposeChild()
	 * @deprecated
	 * @see #createTFoot()
	 */
	public IDispatch createTFoot(boolean addChild) {
		return createTFoot();
	}

	public void deleteTFoot() {
		Native.deleteTFoot(getPtr());
	}

	public IHTMLTableCaption createCaption() {
		return Native.createCaption(getPtr());
	}

	/**
	 * @see ComPtr#disposeChild()
	 * @deprecated
	 * @see #createCaption()
	 */
	public IHTMLTableCaption createCaption(boolean addChild) {
		return createCaption();
	}

	public void deleteCaption() {
		Native.deleteCaption(getPtr());
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

	public String getReadyState() {
		return Native.get_readyState(getPtr());
	}

	public void firstPage() {
		Native.firstPage(getPtr());
	}

	public void lastPage() {
		Native.lastPage(getPtr());
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

	public void setSummary(String summary) {
		Native.put_summary(getPtr(), summary);
	}

	public String getSummary() {
		return Native.get_summary(getPtr());
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
		// IHTMLTable
		//
		private static native void put_cols(long ptr, int p);

		private static native int get_cols(long ptr);

		private static native void put_border(long ptr, Variant p);

		private static native Variant get_border(long ptr);

		private static native void put_frame(long ptr, String p);

		private static native String get_frame(long ptr);

		private static native void put_rules(long ptr, String p);

		private static native String get_rules(long ptr);

		private static native void put_cellSpacing(long ptr, Variant p);

		private static native Variant get_cellSpacing(long ptr);

		private static native void put_cellPadding(long ptr, Variant p);

		private static native Variant get_cellPadding(long ptr);

		private static native void put_background(long ptr, String p);

		private static native String get_background(long ptr);

		private static native void put_bgColor(long ptr, Variant p);

		private static native Variant get_bgColor(long ptr);

		private static native void put_borderColor(long ptr, Variant p);

		private static native Variant get_borderColor(long ptr);

		private static native void put_borderColorLight(long ptr, Variant p);

		private static native Variant get_borderColorLight(long ptr);

		private static native void put_borderColorDark(long ptr, Variant p);

		private static native Variant get_borderColorDark(long ptr);

		private static native void put_align(long ptr, String p);

		private static native String get_align(long ptr);

		private static native void refresh(long ptr);

		private static native IHTMLElementCollection get_rows(long ptr);

		private static native void put_width(long ptr, Variant p);

		private static native Variant get_width(long ptr);

		private static native void put_height(long ptr, Variant p);

		private static native Variant get_height(long ptr);

		private static native void put_dataPageSize(long ptr, int p);

		private static native int get_dataPageSize(long ptr);

		private static native void nextPage(long ptr);

		private static native void previousPage(long ptr);

		private static native IHTMLTableSection get_tHead(long ptr);

		private static native IHTMLTableSection get_tFoot(long ptr);

		private static native IHTMLElementCollection get_tBodies(long ptr);

		private static native IHTMLTableCaption get_caption(long ptr);

		private static native IDispatch createTHead(long ptr);

		private static native void deleteTHead(long ptr);

		private static native IDispatch createTFoot(long ptr);

		private static native void deleteTFoot(long ptr);

		private static native IHTMLTableCaption createCaption(long ptr);

		private static native void deleteCaption(long ptr);

		private static native IDispatch insertRow(long ptr, int index);

		private static native void deleteRow(long ptr, int index);

		private static native String get_readyState(long ptr);

		//
		// IHTMLTable2
		//
		private static native void firstPage(long ptr);

		private static native void lastPage(long ptr);

		private static native IHTMLElementCollection get_cells(long ptr);

		private static native IDispatch moveRow(long ptr, int indexFrom,
				int indexTo);

		//
		// IHTMLTable3
		//
		private static native void put_summary(long ptr, String p);

		private static native String get_summary(long ptr);
	}
}
