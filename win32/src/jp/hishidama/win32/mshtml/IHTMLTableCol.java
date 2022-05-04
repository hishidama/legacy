package jp.hishidama.win32.mshtml;

import jp.hishidama.win32.com.Variant;

/**
 * IHTMLTableCol,IHTMLTableCol2クラス.
 * <p>
 * tableのcolタグを扱うクラスです。
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">ひしだま</a>
 * @since 2007.11.04
 */
public class IHTMLTableCol extends IHTMLElement {

	protected IHTMLTableCol(long dll_ptr) {
		super(dll_ptr);
	}

	public void setSpan(int span) {
		Native.put_span(getPtr(), span);
	}

	public int getSpan() {
		return Native.get_span(getPtr());
	}

	public void setWidth(Variant width) {
		Native.put_width(getPtr(), width);
	}

	public Variant getWidth() {
		return Native.get_width(getPtr());
	}

	public void setAlign(String align) {
		Native.put_align(getPtr(), align);
	}

	public String getAlign() {
		return Native.get_align(getPtr());
	}

	public void setVAlign(String valign) {
		Native.put_vAlign(getPtr(), valign);
	}

	public String getVAlign() {
		return Native.get_vAlign(getPtr());
	}

	public void setCh(String ch) {
		Native.put_ch(getPtr(), ch);
	}

	public String getCh() {
		return Native.get_ch(getPtr());
	}

	public void setChOff(String off) {
		Native.put_chOff(getPtr(), off);
	}

	public String getChOff() {
		return Native.get_chOff(getPtr());
	}

	private static class Native {
		//
		// IHTMLTableCol
		//
		private static native void put_span(long ptr, int v);

		private static native int get_span(long ptr);

		private static native void put_width(long ptr, Variant v);

		private static native Variant get_width(long ptr);

		private static native void put_align(long ptr, String v);

		private static native String get_align(long ptr);

		private static native void put_vAlign(long ptr, String v);

		private static native String get_vAlign(long ptr);

		//
		// IHTMLTableCol2
		//
		private static native void put_ch(long ptr, String v);

		private static native String get_ch(long ptr);

		private static native void put_chOff(long ptr, String v);

		private static native String get_chOff(long ptr);

	}
}
