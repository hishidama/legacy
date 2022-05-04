package jp.hishidama.win32.mshtml;

import jp.hishidama.win32.com.IDispatch;

class IHTMLTextContainer$Native {
	//
	// IHTMLTextContainer
	//
	protected static native IDispatch createControlRange(long ptr);

	protected static native int get_scrollHeight(long ptr);

	protected static native int get_scrollWidth(long ptr);

	protected static native void put_scrollTop(long ptr, int p);

	protected static native int get_scrollTop(long ptr);

	protected static native void put_scrollLeft(long ptr, int p);

	protected static native int get_scrollLeft(long ptr);
}
