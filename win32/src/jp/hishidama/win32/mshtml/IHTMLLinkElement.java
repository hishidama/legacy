package jp.hishidama.win32.mshtml;

/**
 * IHTMLLinkElement,IHTMLLinkElement2～3クラス.
 * <p>
 * link要素を扱うクラスです。
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">ひしだま</a>
 * @since 2007.10.22
 */
public class IHTMLLinkElement extends IHTMLElement {

	protected IHTMLLinkElement(long dll_ptr) {
		super(dll_ptr);
	}

	public void setHref(String href) {
		Native.put_href(getPtr(), href);
	}

	public String getHref() {
		return Native.get_href(getPtr());
	}

	public void setRel(String rel) {
		Native.put_rel(getPtr(), rel);
	}

	public String getRel() {
		return Native.get_rel(getPtr());
	}

	public void setRev(String rev) {
		Native.put_rev(getPtr(), rev);
	}

	public String getRev() {
		return Native.get_rev(getPtr());
	}

	public void setType(String type) {
		Native.put_type(getPtr(), type);
	}

	public String getType() {
		return Native.get_type(getPtr());
	}

	public String getReadyState() {
		return Native.get_readyState(getPtr());
	}

	public void setDisabled(boolean b) {
		Native.put_disabled(getPtr(), b);
	}

	public boolean getDisabled() {
		return Native.get_disabled(getPtr());
	}

	public void setMedia(String media) {
		Native.put_media(getPtr(), media);
	}

	public String getMedia() {
		return Native.get_media(getPtr());
	}

	public void setTarget(String target) {
		Native.put_target(getPtr(), target);
	}

	public String getTarget() {
		return Native.get_target(getPtr());
	}

	public void setCharset(String charset) {
		Native.put_charset(getPtr(), charset);
	}

	public String getCharset() {
		return Native.get_charset(getPtr());
	}

	public void setHreflang(String lang) {
		Native.put_hreflang(getPtr(), lang);
	}

	public String getHreflang() {
		return Native.get_hreflang(getPtr());
	}

	private static class Native {

		//
		// IHTMLLinkElement
		//

		private static native void put_href(long ptr, String href);

		private static native String get_href(long ptr);

		private static native void put_rel(long ptr, String rel);

		private static native String get_rel(long ptr);

		private static native void put_rev(long ptr, String rev);

		private static native String get_rev(long ptr);

		private static native void put_type(long ptr, String type);

		private static native String get_type(long ptr);

		private static native String get_readyState(long ptr);

		private static native void put_disabled(long ptr, boolean b);

		private static native boolean get_disabled(long ptr);

		private static native void put_media(long ptr, String media);

		private static native String get_media(long ptr);

		//
		// IHTMLLinkElement2
		//

		private static native void put_target(long ptr, String target);

		private static native String get_target(long ptr);

		//
		// IHTMLLinkElement3
		//

		private static native void put_charset(long ptr, String charset);

		private static native String get_charset(long ptr);

		private static native void put_hreflang(long ptr, String lang);

		private static native String get_hreflang(long ptr);
	}
}
