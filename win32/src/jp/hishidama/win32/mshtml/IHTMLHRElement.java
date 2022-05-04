package jp.hishidama.win32.mshtml;

import jp.hishidama.win32.com.Variant;

/**
 * IHTMLHRElementクラス.
 * <p>
 * hrタグを扱うクラスです。
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">ひしだま</a>
 * @since 2007.11.05
 */
public class IHTMLHRElement extends IHTMLElement {

	protected IHTMLHRElement(long dll_ptr) {
		super(dll_ptr);
	}

	public void setAlign(String align) {
		Native.put_align(getPtr(), align);
	}

	public String getAlign() {
		return Native.get_align(getPtr());
	}

	public void setColor(Variant color) {
		Native.put_color(getPtr(), color);
	}

	public Variant getColor() {
		return Native.get_color(getPtr());
	}

	public void setNoShade(boolean b) {
		Native.put_noShade(getPtr(), b);
	}

	public boolean getNoShade() {
		return Native.get_noShade(getPtr());
	}

	public void setWidth(Variant width) {
		Native.put_width(getPtr(), width);
	}

	public Variant getWidth() {
		return Native.get_width(getPtr());
	}

	public void setSize(Variant size) {
		Native.put_size(getPtr(), size);
	}

	public Variant getSize() {
		return Native.get_size(getPtr());
	}

	private static class Native {
		//
		// IHTMLHRElement
		//
		private static native void put_align(long ptr, String p);

		private static native String get_align(long ptr);

		private static native void put_color(long ptr, Variant p);

		private static native Variant get_color(long ptr);

		private static native void put_noShade(long ptr, boolean p);

		private static native boolean get_noShade(long ptr);

		private static native void put_width(long ptr, Variant p);

		private static native Variant get_width(long ptr);

		private static native void put_size(long ptr, Variant p);

		private static native Variant get_size(long ptr);
	}
}
