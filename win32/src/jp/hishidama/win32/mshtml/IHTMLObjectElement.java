package jp.hishidama.win32.mshtml;

import jp.hishidama.win32.com.ComPtr;
import jp.hishidama.win32.com.IDispatch;
import jp.hishidama.win32.com.Variant;

/**
 * IHTMLObjectElement,IHTMLObjectElement2～3クラス.
 * <p>
 * objectタグを扱うクラスです。
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">ひしだま</a>
 * @since 2007.11.05
 * @version 2008.07.14
 */
public class IHTMLObjectElement extends IHTMLElement implements
		IHTMLControlElement {

	protected IHTMLObjectElement(long dll_ptr) {
		super(dll_ptr);
	}

	public IDispatch getObject() {
		return Native.get_object(getPtr());
	}

	/**
	 * @see ComPtr#disposeChild()
	 * @deprecated
	 * @see #getObject()
	 */
	public IDispatch getObject(boolean addChild) {
		return getObject();
	}

	public void setrefRecordset(IDispatch recordset) {
		Native.putref_recordset(getPtr(), recordset);
	}

	public IDispatch getRecordset() {
		return Native.get_recordset(getPtr());
	}

	/**
	 * @see ComPtr#disposeChild()
	 * @deprecated
	 * @see #getRecordset()
	 */
	public IDispatch getRecordset(boolean addChild) {
		return getRecordset();
	}

	public void setAlign(String align) {
		Native.put_align(getPtr(), align);
	}

	public String getAlign() {
		return Native.get_align(getPtr());
	}

	public void setName(String name) {
		Native.put_name(getPtr(), name);
	}

	public String getName() {
		return Native.get_name(getPtr());
	}

	public void setCodeBase(String codebase) {
		Native.put_codeBase(getPtr(), codebase);
	}

	public String getCodeBase() {
		return Native.get_codeBase(getPtr());
	}

	public void setCodeType(String codetype) {
		Native.put_codeType(getPtr(), codetype);
	}

	public String getCodeType() {
		return Native.get_codeType(getPtr());
	}

	public void setCode(String code) {
		Native.put_code(getPtr(), code);
	}

	public String getCode() {
		return Native.get_code(getPtr());
	}

	public String getBaseHref() {
		return Native.get_BaseHref(getPtr());
	}

	public void setType(String type) {
		Native.put_type(getPtr(), type);
	}

	public String getType() {
		return Native.get_type(getPtr());
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

	public int getReadyState() {
		return Native.get_readyState(getPtr());
	}

	public void setAltHtml(String althtml) {
		Native.put_altHtml(getPtr(), althtml);
	}

	public String getAltHtml() {
		return Native.get_altHtml(getPtr());
	}

	public void setVspace(int vspace) {
		Native.put_vspace(getPtr(), vspace);
	}

	public int getVspace() {
		return Native.get_vspace(getPtr());
	}

	public void setHspace(int hspace) {
		Native.put_hspace(getPtr(), hspace);
	}

	public int getHspace() {
		return Native.get_hspace(getPtr());
	}

	public IDispatch namedRecordset(String dataMember, Variant hierarchy) {
		return Native.namedRecordset(getPtr(), dataMember, hierarchy);
	}

	/**
	 * @see ComPtr#disposeChild()
	 * @deprecated
	 * @see #namedRecordset(String, Variant)
	 */
	public IDispatch namedRecordset(String dataMember, Variant hierarchy,
			boolean addChild) {
		return namedRecordset(dataMember, hierarchy);
	}

	public void setClassid(String classid) {
		Native.put_classid(getPtr(), classid);
	}

	public String getClassid() {
		return Native.get_classid(getPtr());
	}

	public void setData(String data) {
		Native.put_data(getPtr(), data);
	}

	public String getData() {
		return Native.get_data(getPtr());
	}

	public void setArchive(String archive) {
		Native.put_archive(getPtr(), archive);
	}

	public String getArchive() {
		return Native.get_archive(getPtr());
	}

	public void setAlt(String alt) {
		Native.put_alt(getPtr(), alt);
	}

	public String getAlt() {
		return Native.get_alt(getPtr());
	}

	public void setDeclare(boolean b) {
		Native.put_declare(getPtr(), b);
	}

	public boolean getDeclare() {
		return Native.get_declare(getPtr());
	}

	public void setStandby(String standby) {
		Native.put_standby(getPtr(), standby);
	}

	public String getStandby() {
		return Native.get_standby(getPtr());
	}

	public void setBorder(Variant border) {
		Native.put_border(getPtr(), border);
	}

	public Variant getBorder() {
		return Native.get_border(getPtr());
	}

	public void setUseMap(String usemap) {
		Native.put_useMap(getPtr(), usemap);
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
		// IHTMLObjectElement
		//
		private static native IDispatch get_object(long ptr);

		private static native void putref_recordset(long ptr,
				IDispatch recordset);

		private static native IDispatch get_recordset(long ptr);

		private static native void put_align(long ptr, String p);

		private static native String get_align(long ptr);

		private static native void put_name(long ptr, String p);

		private static native String get_name(long ptr);

		private static native void put_codeBase(long ptr, String p);

		private static native String get_codeBase(long ptr);

		private static native void put_codeType(long ptr, String p);

		private static native String get_codeType(long ptr);

		private static native void put_code(long ptr, String p);

		private static native String get_code(long ptr);

		private static native String get_BaseHref(long ptr);

		private static native void put_type(long ptr, String p);

		private static native String get_type(long ptr);

		private static native IHTMLFormElement get_form(long ptr);

		private static native void put_width(long ptr, Variant p);

		private static native Variant get_width(long ptr);

		private static native void put_height(long ptr, Variant p);

		private static native Variant get_height(long ptr);

		private static native int get_readyState(long ptr);

		private static native void put_altHtml(long ptr, String p);

		private static native String get_altHtml(long ptr);

		private static native void put_vspace(long ptr, int p);

		private static native int get_vspace(long ptr);

		private static native void put_hspace(long ptr, int p);

		private static native int get_hspace(long ptr);

		//
		// IHTMLObjectElement2
		//
		private static native IDispatch namedRecordset(long ptr,
				String dataMember, Variant hierarchy);

		private static native void put_classid(long ptr, String p);

		private static native String get_classid(long ptr);

		private static native void put_data(long ptr, String p);

		private static native String get_data(long ptr);

		//
		// IHTMLObjectElement3
		//
		private static native void put_archive(long ptr, String p);

		private static native String get_archive(long ptr);

		private static native void put_alt(long ptr, String p);

		private static native String get_alt(long ptr);

		private static native void put_declare(long ptr, boolean p);

		private static native boolean get_declare(long ptr);

		private static native void put_standby(long ptr, String p);

		private static native String get_standby(long ptr);

		private static native void put_border(long ptr, Variant p);

		private static native Variant get_border(long ptr);

		private static native void put_useMap(long ptr, String p);

		private static native String get_useMap(long ptr);
	}
}
