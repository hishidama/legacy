package jp.hishidama.win32.mshtml;

import jp.hishidama.win32.com.Variant;

/**
 * IHTMLMarqueeElementクラス.
 * <p>
 * marqueeタグ（IE独自のタグ）を扱うクラスです。
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">ひしだま</a>
 * @since 2007.11.05
 */
public class IHTMLMarqueeElement extends IHTMLElement implements
		IHTMLControlElement {

	protected IHTMLMarqueeElement(long dll_ptr) {
		super(dll_ptr);
	}

	public void setBgColor(Variant color) {
		Native.put_bgColor(getPtr(), color);
	}

	public Variant getBgColor() {
		return Native.get_bgColor(getPtr());
	}

	public void setScrollDelay(int delay) {
		Native.put_scrollDelay(getPtr(), delay);
	}

	public int getScrollDelay() {
		return Native.get_scrollDelay(getPtr());
	}

	public void setDirection(String direction) {
		Native.put_direction(getPtr(), direction);
	}

	public String getDirection() {
		return Native.get_direction(getPtr());
	}

	public void setBehavior(String behavior) {
		Native.put_behavior(getPtr(), behavior);
	}

	public String getBehavior() {
		return Native.get_behavior(getPtr());
	}

	public void setScrollAmount(int scrollamount) {
		Native.put_scrollAmount(getPtr(), scrollamount);
	}

	public int getScrollAmount() {
		return Native.get_scrollAmount(getPtr());
	}

	public void setLoop(int loop) {
		Native.put_loop(getPtr(), loop);
	}

	public int getLoop() {
		return Native.get_loop(getPtr());
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

	public void setTrueSpeed(boolean b) {
		Native.put_trueSpeed(getPtr(), b);
	}

	public boolean getTrueSpeed() {
		return Native.get_trueSpeed(getPtr());
	}

	public void start() {
		Native.Start(getPtr());
	}

	public void stop() {
		Native.stop(getPtr());
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
		// IHTMLMarqueeElement
		//
		private static native void put_bgColor(long ptr, Variant p);

		private static native Variant get_bgColor(long ptr);

		private static native void put_scrollDelay(long ptr, int p);

		private static native int get_scrollDelay(long ptr);

		private static native void put_direction(long ptr, String p);

		private static native String get_direction(long ptr);

		private static native void put_behavior(long ptr, String p);

		private static native String get_behavior(long ptr);

		private static native void put_scrollAmount(long ptr, int p);

		private static native int get_scrollAmount(long ptr);

		private static native void put_loop(long ptr, int p);

		private static native int get_loop(long ptr);

		private static native void put_vspace(long ptr, int p);

		private static native int get_vspace(long ptr);

		private static native void put_hspace(long ptr, int p);

		private static native int get_hspace(long ptr);

		private static native void put_width(long ptr, Variant p);

		private static native Variant get_width(long ptr);

		private static native void put_height(long ptr, Variant p);

		private static native Variant get_height(long ptr);

		private static native void put_trueSpeed(long ptr, boolean p);

		private static native boolean get_trueSpeed(long ptr);

		private static native void Start(long ptr);

		private static native void stop(long ptr);
	}
}
