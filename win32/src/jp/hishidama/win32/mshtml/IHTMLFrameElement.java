package jp.hishidama.win32.mshtml;

import jp.hishidama.win32.com.Variant;

/**
 * IHTMLFrameElement,IHTMLFrameElement2クラス.
 * <p>
 * frameタグを扱うクラスです。
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">ひしだま</a>
 * @since 2007.11.04
 * @version 2007.11.05
 */
public class IHTMLFrameElement extends IHTMLFrameBase {

	protected IHTMLFrameElement(long dll_ptr) {
		super(dll_ptr);
	}

	public void setBorderColor(Variant color) {
		Native.put_borderColor(getPtr(), color);
	}

	public Variant getBorderColor() {
		return Native.get_borderColor(getPtr());
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

		private static native void put_borderColor(long ptr, Variant p);

		private static native Variant get_borderColor(long ptr);

		//
		// IHTMLIFrameElement2
		//

		private static native void put_height(long ptr, Variant p);

		private static native Variant get_height(long ptr);

		private static native void put_width(long ptr, Variant p);

		private static native Variant get_width(long ptr);
	}
}
