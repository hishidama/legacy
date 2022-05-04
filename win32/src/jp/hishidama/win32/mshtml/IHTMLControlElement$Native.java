package jp.hishidama.win32.mshtml;

class IHTMLControlElement$Native {
	//
	// IHTMLControlElement
	//

	protected static native void put_tabIndex(long ptr, int index);

	protected static native int get_tabIndex(long ptr);

	protected static native void focus(long ptr);

	protected static native void put_accessKey(long ptr, String key);

	protected static native String get_accessKey(long ptr);

	protected static native void blur(long ptr);

	protected static native int get_clientHeight(long ptr);

	protected static native int get_clientWidth(long ptr);

	protected static native int get_clientTop(long ptr);

	protected static native int get_clientLeft(long ptr);

}
