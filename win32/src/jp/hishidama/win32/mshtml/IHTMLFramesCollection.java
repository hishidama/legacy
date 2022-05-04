package jp.hishidama.win32.mshtml;

import jp.hishidama.win32.com.ComPtr;
import jp.hishidama.win32.com.IDispatch;
import jp.hishidama.win32.com.Variant;

/**
 * IHTMLFramesCollection2クラス.
 * <p>
 * フレームの一覧を扱うクラスです。
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">ひしだま</a>
 * @since 2007.10.22
 * @version 2008.07.14
 */
public class IHTMLFramesCollection extends IDispatch {

	protected IHTMLFramesCollection(long dll_ptr) {
		super(dll_ptr);
	}

	public IHTMLWindow item(Variant index) {
		Variant var = Native.item(getPtr(), index);
		return (IHTMLWindow) var.getCom();
	}

	/**
	 * @see ComPtr#disposeChild()
	 * @deprecated
	 * @see #item(Variant)
	 */
	public IHTMLWindow item(Variant index, boolean addChild) {
		return item(index);
	}

	public int getLength() {
		return Native.get_length(getPtr());
	}

	private static class Native {
		//
		// IHTMLFramesCollection2
		//

		private static native Variant item(long ptr, Variant index);

		private static native int get_length(long ptr);
	}
}
