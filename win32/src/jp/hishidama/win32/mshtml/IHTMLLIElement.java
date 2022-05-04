package jp.hishidama.win32.mshtml;

/**
 * IHTMLLIElementクラス.
 * <p>
 * liタグを扱うクラスです。
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">ひしだま</a>
 * @since 2007.11.05
 */
public class IHTMLLIElement extends IHTMLElement {

	protected IHTMLLIElement(long dll_ptr) {
		super(dll_ptr);
	}

	public void setType(String type) {
		Native.put_type(getPtr(), type);
	}

	public String getType() {
		return Native.get_type(getPtr());
	}

	public void setValue(int value) {
		Native.put_value(getPtr(), value);
	}

	public int getValue() {
		return Native.get_value(getPtr());
	}

	private static class Native {
		//
		// IHTMLLIElement
		//
		private static native void put_type(long ptr, String p);

		private static native String get_type(long ptr);

		private static native void put_value(long ptr, int p);

		private static native int get_value(long ptr);
	}
}
