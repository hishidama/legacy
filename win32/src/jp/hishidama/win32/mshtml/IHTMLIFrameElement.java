package jp.hishidama.win32.mshtml;

import jp.hishidama.win32.com.Variant;

/**
 * IHTMLIFrameElement,IHTMLIFrameElement2クラス.
 * <p>
 * iframeタグを扱うクラスです。
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">ひしだま</a>
 * @since 2007.11.05
 */
public class IHTMLIFrameElement extends IHTMLFrameBase {

	protected IHTMLIFrameElement(long dll_ptr) {
		super(dll_ptr);
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

	public void setAlign(String align) {
		Native.put_align(getPtr(), align);
	}

	public String getAlign() {
		return Native.get_align(getPtr());
	}

	public void setHeight(Variant height) {
		Native.put_height(getPtr(), height);
	}

	public Variant getHeight() {
		return Native.get_height(getPtr());
	}

	public void setWidth(Variant width) {
		Native.put_width(getPtr(), width);
	}

	public Variant getWidth() {
		return Native.get_width(getPtr());
	}

	private static class Native {
		//
		// IHTMLIFrameElement
		//
		private static native void put_vspace(long ptr, int p);

		private static native int get_vspace(long ptr);

		private static native void put_hspace(long ptr, int p);

		private static native int get_hspace(long ptr);

		private static native void put_align(long ptr, String p);

		private static native String get_align(long ptr);

		//
		// IHTMLIFrameElement2
		//
		private static native void put_height(long ptr, Variant p);

		private static native Variant get_height(long ptr);

		private static native void put_width(long ptr, Variant p);

		private static native Variant get_width(long ptr);
	}
}
