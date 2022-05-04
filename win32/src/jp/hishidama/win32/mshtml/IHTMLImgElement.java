package jp.hishidama.win32.mshtml;

import jp.hishidama.win32.com.Variant;

/**
 * IHTMLImgElement,IHTMLImgElement2クラス.
 * <p>
 * imgタグ（画像）を扱うクラスです。
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">ひしだま</a>
 * @since 2007.10.24
 * @version 2007.11.04
 */
public class IHTMLImgElement extends IHTMLElement implements
		IHTMLControlElement {

	protected IHTMLImgElement(long dll_ptr) {
		super(dll_ptr);
	}

	public void setIsMap(boolean b) {
		Native.put_isMap(getPtr(), b);
	}

	public boolean getIsMap() {
		return Native.get_isMap(getPtr());
	}

	public void setUseMap(String map) {
		Native.put_useMap(getPtr(), map);
	}

	public String getUseMap() {
		return Native.get_useMap(getPtr());
	}

	public String getMimeType() {
		return Native.get_mimeType(getPtr());
	}

	public String getFileSize() {
		return Native.get_fileSize(getPtr());
	}

	public String getFileCreatedDate() {
		return Native.get_fileCreatedDate(getPtr());
	}

	public String getFileModifiedDate() {
		return Native.get_fileModifiedDate(getPtr());
	}

	public String getFileUpdatedDate() {
		return Native.get_fileUpdatedDate(getPtr());
	}

	public String getProtocol() {
		return Native.get_protocol(getPtr());
	}

	public String getHref() {
		return Native.get_href(getPtr());
	}

	public String getNameProp() {
		return Native.get_nameProp(getPtr());
	}

	public void setBorder(Variant border) {
		Native.put_border(getPtr(), border);
	}

	public Variant getBorder() {
		return Native.get_border(getPtr());
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

	public void setLowsrc(String src) {
		Native.put_lowsrc(getPtr(), src);
	}

	public String getLowsrc() {
		return Native.get_lowsrc(getPtr());
	}

	public void setVrml(String vrml) {
		Native.put_vrml(getPtr(), vrml);
	}

	public String getVrml() {
		return Native.get_vrml(getPtr());
	}

	public void setDynsrc(String src) {
		Native.put_dynsrc(getPtr(), src);
	}

	public String getDynsrc() {
		return Native.get_dynsrc(getPtr());
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

	public void setName(String name) {
		Native.put_name(getPtr(), name);
	}

	public String getName() {
		return Native.get_name(getPtr());
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

	public void setLongDesc(String desc) {
		Native.put_longDesc(getPtr(), desc);
	}

	public String getLongDesc() {
		return Native.get_longDesc(getPtr());
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
		// IHTMLImgElement
		//
		private static native void put_isMap(long ptr, boolean b);

		private static native boolean get_isMap(long ptr);

		private static native void put_useMap(long ptr, String map);

		private static native String get_useMap(long ptr);

		private static native String get_mimeType(long ptr);

		private static native String get_fileSize(long ptr);

		private static native String get_fileCreatedDate(long ptr);

		private static native String get_fileModifiedDate(long ptr);

		private static native String get_fileUpdatedDate(long ptr);

		private static native String get_protocol(long ptr);

		private static native String get_href(long ptr);

		private static native String get_nameProp(long ptr);

		private static native void put_border(long ptr, Variant border);

		private static native Variant get_border(long ptr);

		private static native void put_vspace(long ptr, int p);

		private static native int get_vspace(long ptr);

		private static native void put_hspace(long ptr, int p);

		private static native int get_hspace(long ptr);

		private static native void put_alt(long ptr, String alt);

		private static native String get_alt(long ptr);

		private static native void put_src(long ptr, String src);

		private static native String get_src(long ptr);

		private static native void put_lowsrc(long ptr, String src);

		private static native String get_lowsrc(long ptr);

		private static native void put_vrml(long ptr, String vrml);

		private static native String get_vrml(long ptr);

		private static native void put_dynsrc(long ptr, String src);

		private static native String get_dynsrc(long ptr);

		private static native String get_readyState(long ptr);

		private static native boolean get_complete(long ptr);

		private static native void put_loop(long ptr, Variant loop);

		private static native Variant get_loop(long ptr);

		private static native void put_align(long ptr, String align);

		private static native String get_align(long ptr);

		private static native void put_name(long ptr, String name);

		private static native String get_name(long ptr);

		private static native void put_width(long ptr, int p);

		private static native int get_width(long ptr);

		private static native void put_height(long ptr, int p);

		private static native int get_height(long ptr);

		private static native void put_Start(long ptr, String start);

		private static native String get_Start(long ptr);

		//
		// IHTMLImgElement2
		//

		private static native void put_longDesc(long ptr, String desc);

		private static native String get_longDesc(long ptr);
	}
}
