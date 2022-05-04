package jp.hishidama.win32.mshtml;

/**
 * IHTMLHeadElementクラス.
 * <p>
 * headタグを扱うクラスです。
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">ひしだま</a>
 * @since 2007.11.05
 */
public class IHTMLHeadElement extends IHTMLElement {

	protected IHTMLHeadElement(long dll_ptr) {
		super(dll_ptr);
	}

	public void setProfile(String profile) {
		Native.put_profile(getPtr(), profile);
	}

	public String getProfile() {
		return Native.get_profile(getPtr());
	}

	private static class Native {
		//
		// IHTMLHeadElement
		//
		private static native void put_profile(long ptr, String p);

		private static native String get_profile(long ptr);
	}
}
