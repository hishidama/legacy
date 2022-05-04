package jp.hishidama.win32.mshtml;

import jp.hishidama.win32.com.Variant;

/**
 * IHTMLEmbedElementクラス.
 * <p>
 * embedタグを扱うクラスです。
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">ひしだま</a>
 * @since 2007.11.05
 */
public class IHTMLEmbedElement extends IHTMLElement implements
		IHTMLControlElement {

	protected IHTMLEmbedElement(long dll_ptr) {
		super(dll_ptr);
	}

	public void setHidden(String hidden) {
		Native.put_hidden(getPtr(), hidden);
	}

	public String getHidden() {
		return Native.get_hidden(getPtr());
	}

	public String getPalette() {
		return Native.get_palette(getPtr());
	}

	public String getPluginspage() {
		return Native.get_pluginspage(getPtr());
	}

	public void setSrc(String src) {
		Native.put_src(getPtr(), src);
	}

	public String getSrc() {
		return Native.get_src(getPtr());
	}

	public void setUnits(String units) {
		Native.put_units(getPtr(), units);
	}

	public String getUnits() {
		return Native.get_units(getPtr());
	}

	public void setName(String name) {
		Native.put_name(getPtr(), name);
	}

	public String getName() {
		return Native.get_name(getPtr());
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
		// IHTMLEmbedElement
		//
		private static native void put_hidden(long ptr, String p);

		private static native String get_hidden(long ptr);

		private static native String get_palette(long ptr);

		private static native String get_pluginspage(long ptr);

		private static native void put_src(long ptr, String p);

		private static native String get_src(long ptr);

		private static native void put_units(long ptr, String p);

		private static native String get_units(long ptr);

		private static native void put_name(long ptr, String p);

		private static native String get_name(long ptr);

		private static native void put_width(long ptr, Variant p);

		private static native Variant get_width(long ptr);

		private static native void put_height(long ptr, Variant p);

		private static native Variant get_height(long ptr);
	}
}
