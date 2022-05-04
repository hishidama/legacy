package jp.hishidama.win32.mshtml;

/**
 * IHTMLAreaElementクラス.
 * <p>
 * areaタグを扱うクラスです。
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">ひしだま</a>
 * @since 2007.11.05
 */
public class IHTMLAreaElement extends IHTMLElement {

	protected IHTMLAreaElement(long dll_ptr) {
		super(dll_ptr);
	}

	public void setShape(String shape) {
		Native.put_shape(getPtr(), shape);
	}

	public String getShape() {
		return Native.get_shape(getPtr());
	}

	public void setCoords(String coords) {
		Native.put_coords(getPtr(), coords);
	}

	public String getCoords() {
		return Native.get_coords(getPtr());
	}

	public void setHref(String href) {
		Native.put_href(getPtr(), href);
	}

	public String getHref() {
		return Native.get_href(getPtr());
	}

	public void setTarget(String target) {
		Native.put_target(getPtr(), target);
	}

	public String getTarget() {
		return Native.get_target(getPtr());
	}

	public void setAlt(String alt) {
		Native.put_alt(getPtr(), alt);
	}

	public String getAlt() {
		return Native.get_alt(getPtr());
	}

	public void setNoHref(boolean b) {
		Native.put_noHref(getPtr(), b);
	}

	public boolean getNoHref() {
		return Native.get_noHref(getPtr());
	}

	public void setHost(String host) {
		Native.put_host(getPtr(), host);
	}

	public String getHost() {
		return Native.get_host(getPtr());
	}

	public void setHostname(String hostname) {
		Native.put_hostname(getPtr(), hostname);
	}

	public String getHostname() {
		return Native.get_hostname(getPtr());
	}

	public void setPathname(String pathname) {
		Native.put_pathname(getPtr(), pathname);
	}

	public String getPathname() {
		return Native.get_pathname(getPtr());
	}

	public void setPort(String port) {
		Native.put_port(getPtr(), port);
	}

	public String getPort() {
		return Native.get_port(getPtr());
	}

	public void setProtocol(String protocol) {
		Native.put_protocol(getPtr(), protocol);
	}

	public String getProtocol() {
		return Native.get_protocol(getPtr());
	}

	public void setSearch(String search) {
		Native.put_search(getPtr(), search);
	}

	public String getSearch() {
		return Native.get_search(getPtr());
	}

	public void setHash(String hash) {
		Native.put_hash(getPtr(), hash);
	}

	public String getHash() {
		return Native.get_hash(getPtr());
	}

	public void setTabIndex(short tabindex) {
		Native.put_tabIndex(getPtr(), tabindex);
	}

	public int getTabIndex() {
		return Native.get_tabIndex(getPtr());
	}

	public void focus() {
		Native.focus(getPtr());
	}

	public void blur() {
		Native.blur(getPtr());
	}

	private static class Native {
		//
		// IHTMLAreaElement
		//
		private static native void put_shape(long ptr, String p);

		private static native String get_shape(long ptr);

		private static native void put_coords(long ptr, String p);

		private static native String get_coords(long ptr);

		private static native void put_href(long ptr, String p);

		private static native String get_href(long ptr);

		private static native void put_target(long ptr, String p);

		private static native String get_target(long ptr);

		private static native void put_alt(long ptr, String p);

		private static native String get_alt(long ptr);

		private static native void put_noHref(long ptr, boolean p);

		private static native boolean get_noHref(long ptr);

		private static native void put_host(long ptr, String p);

		private static native String get_host(long ptr);

		private static native void put_hostname(long ptr, String p);

		private static native String get_hostname(long ptr);

		private static native void put_pathname(long ptr, String p);

		private static native String get_pathname(long ptr);

		private static native void put_port(long ptr, String p);

		private static native String get_port(long ptr);

		private static native void put_protocol(long ptr, String p);

		private static native String get_protocol(long ptr);

		private static native void put_search(long ptr, String p);

		private static native String get_search(long ptr);

		private static native void put_hash(long ptr, String p);

		private static native String get_hash(long ptr);

		private static native void put_tabIndex(long ptr, short p);

		private static native int get_tabIndex(long ptr);

		private static native void focus(long ptr);

		private static native void blur(long ptr);
	}
}
