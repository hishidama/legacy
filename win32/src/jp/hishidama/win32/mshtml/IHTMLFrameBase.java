package jp.hishidama.win32.mshtml;

import jp.hishidama.win32.com.ComPtr;
import jp.hishidama.win32.com.Variant;

/**
 * IHTMLFrameBase,IHTMLFrameBase2Å`3ÉNÉâÉX.
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">Ç–ÇµÇæÇ‹</a>
 * @since 2008.07.14
 */
public class IHTMLFrameBase extends IHTMLElement implements IHTMLControlElement {

	protected IHTMLFrameBase(long dll_ptr) {
		super(dll_ptr);
	}

	public void setSrc(String src) {
		Native.put_src(getPtr(), src);
	}

	public String getSrc() {
		return Native.get_src(getPtr());
	}

	public void setName(String name) {
		Native.put_name(getPtr(), name);
	}

	public String getName() {
		return Native.get_name(getPtr());
	}

	public void setBorder(Variant border) {
		Native.put_border(getPtr(), border);
	}

	public Variant getBorder() {
		return Native.get_border(getPtr());
	}

	public void setFrameBorder(String border) {
		Native.put_frameBorder(getPtr(), border);
	}

	public String getFrameBorder() {
		return Native.get_frameBorder(getPtr());
	}

	public void setFrameSpacing(Variant spacing) {
		Native.put_frameSpacing(getPtr(), spacing);
	}

	public Variant getFrameSpacing() {
		return Native.get_frameSpacing(getPtr());
	}

	public void setMarginWidth(Variant width) {
		Native.put_marginWidth(getPtr(), width);
	}

	public Variant getMarginWidth() {
		return Native.get_marginWidth(getPtr());
	}

	public void setMarginHeight(Variant height) {
		Native.put_marginHeight(getPtr(), height);
	}

	public Variant getMarginHeight() {
		return Native.get_marginHeight(getPtr());
	}

	public void setNoResize(boolean b) {
		Native.put_noResize(getPtr(), b);
	}

	public boolean getNoResize() {
		return Native.get_noResize(getPtr());
	}

	public void setScrolling(String scroll) {
		Native.put_scrolling(getPtr(), scroll);
	}

	public String getScrolling() {
		return Native.get_scrolling(getPtr());
	}

	public IHTMLWindow getContentWindow() {
		return Native.get_contentWindow(getPtr());
	}

	/**
	 * @see ComPtr#disposeChild()
	 * @deprecated
	 * @see #getContentWindow()
	 */
	public IHTMLWindow getContentWindow(boolean addChild) {
		return getContentWindow();
	}

	public String getReadyState() {
		return Native.get_readyState(getPtr());
	}

	public void setAllowTransparency(boolean b) {
		Native.put_allowTransparency(getPtr(), b);
	}

	public boolean getAllowTransparency() {
		return Native.get_allowTransparency(getPtr());
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
		// IHTMLFrameBase
		//
		private static native void put_src(long ptr, String p);

		private static native String get_src(long ptr);

		private static native void put_name(long ptr, String p);

		private static native String get_name(long ptr);

		private static native void put_border(long ptr, Variant p);

		private static native Variant get_border(long ptr);

		private static native void put_frameBorder(long ptr, String p);

		private static native String get_frameBorder(long ptr);

		private static native void put_frameSpacing(long ptr, Variant p);

		private static native Variant get_frameSpacing(long ptr);

		private static native void put_marginWidth(long ptr, Variant p);

		private static native Variant get_marginWidth(long ptr);

		private static native void put_marginHeight(long ptr, Variant p);

		private static native Variant get_marginHeight(long ptr);

		private static native void put_noResize(long ptr, boolean p);

		private static native boolean get_noResize(long ptr);

		private static native void put_scrolling(long ptr, String p);

		private static native String get_scrolling(long ptr);

		//
		// IHTMLFrameBase2
		//
		private static native IHTMLWindow get_contentWindow(long ptr);

		private static native String get_readyState(long ptr);

		private static native void put_allowTransparency(long ptr, boolean p);

		private static native boolean get_allowTransparency(long ptr);

		//
		// IHTMLFrameBase3
		//
		private static native void put_longDesc(long ptr, String p);

		private static native String get_longDesc(long ptr);
	}
}
