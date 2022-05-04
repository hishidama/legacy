package jp.hishidama.win32.mshtml;

/**
 * IHTMLScriptElement,IHTMLScriptElement2クラス.
 * <p>
 * scriptタグを扱うクラスです。
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">ひしだま</a>
 * @since 2007.11.05
 */
public class IHTMLScriptElement extends IHTMLElement {

	protected IHTMLScriptElement(long dll_ptr) {
		super(dll_ptr);
	}

	public void setSrc(String src) {
		Native.put_src(getPtr(), src);
	}

	public String getSrc() {
		return Native.get_src(getPtr());
	}

	public void setHtmlFor(String htmlfor) {
		Native.put_htmlFor(getPtr(), htmlfor);
	}

	public String getHtmlFor() {
		return Native.get_htmlFor(getPtr());
	}

	public void setEvent(String event) {
		Native.put_event(getPtr(), event);
	}

	public String getEvent() {
		return Native.get_event(getPtr());
	}

	public void setText(String text) {
		Native.put_text(getPtr(), text);
	}

	public String getText() {
		return Native.get_text(getPtr());
	}

	public void setDefer(boolean b) {
		Native.put_defer(getPtr(), b);
	}

	public boolean getDefer() {
		return Native.get_defer(getPtr());
	}

	public String getReadyState() {
		return Native.get_readyState(getPtr());
	}

	public void setType(String type) {
		Native.put_type(getPtr(), type);
	}

	public String getType() {
		return Native.get_type(getPtr());
	}

	public void setCharset(String charset) {
		Native.put_charset(getPtr(), charset);
	}

	public String getCharset() {
		return Native.get_charset(getPtr());
	}

	private static class Native {
		//
		// IHTMLScriptElement
		//
		private static native void put_src(long ptr, String p);

		private static native String get_src(long ptr);

		private static native void put_htmlFor(long ptr, String p);

		private static native String get_htmlFor(long ptr);

		private static native void put_event(long ptr, String p);

		private static native String get_event(long ptr);

		private static native void put_text(long ptr, String p);

		private static native String get_text(long ptr);

		private static native void put_defer(long ptr, boolean p);

		private static native boolean get_defer(long ptr);

		private static native String get_readyState(long ptr);

		private static native void put_type(long ptr, String p);

		private static native String get_type(long ptr);

		//
		// IHTMLScriptElement2
		//
		private static native void put_charset(long ptr, String p);

		private static native String get_charset(long ptr);
	}
}
