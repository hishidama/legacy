package jp.hishidama.win32.mshtml;

import jp.hishidama.win32.com.ComPtr;

/**
 * IHTMLMapElementクラス.
 * <p>
 * mapタグを扱うクラスです。
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">ひしだま</a>
 * @since 2007.11.05
 * @version 2008.07.14
 */
public class IHTMLMapElement extends IHTMLElement {

	protected IHTMLMapElement(long dll_ptr) {
		super(dll_ptr);
	}

	public IHTMLAreasCollection getAreas() {
		return Native.get_areas(getPtr());
	}

	/**
	 * @see ComPtr#disposeChild()
	 * @deprecated
	 * @see #getAreas()
	 */
	public IHTMLAreasCollection getAreas(boolean addChild) {
		return getAreas();
	}

	public void setName(String name) {
		Native.put_name(getPtr(), name);
	}

	public String getName() {
		return Native.get_name(getPtr());
	}

	private static class Native {
		//
		// IHTMLMapElement
		//
		private static native IHTMLAreasCollection get_areas(long ptr);

		private static native void put_name(long ptr, String p);

		private static native String get_name(long ptr);
	}
}
