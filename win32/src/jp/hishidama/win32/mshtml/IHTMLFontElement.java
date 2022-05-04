package jp.hishidama.win32.mshtml;

import jp.hishidama.win32.com.Variant;

/**
 * IHTMLFontElementクラス.
 * <p>
 * fontタグを扱うクラスです。
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">ひしだま</a>
 * @since 2007.11.04
 */
public class IHTMLFontElement extends IHTMLElement {

	protected IHTMLFontElement(long dll_ptr) {
		super(dll_ptr);
	}

	public void setColor(Variant color) {
		Native.put_color(getPtr(), color);
	}

	public Variant getColor() {
		return Native.get_color(getPtr());
	}

	public void setFace(String face) {
		Native.put_face(getPtr(), face);
	}

	public String getFace() {
		return Native.get_face(getPtr());
	}

	public void setSize(Variant size) {
		Native.put_size(getPtr(), size);
	}

	public Variant getSize() {
		return Native.get_size(getPtr());
	}

	private static class Native {
		//
		// IHTMLFontElement
		//
		private static native void put_color(long ptr, Variant p);

		private static native Variant get_color(long ptr);

		private static native void put_face(long ptr, String p);

		private static native String get_face(long ptr);

		private static native void put_size(long ptr, Variant p);

		private static native Variant get_size(long ptr);
	}
}
