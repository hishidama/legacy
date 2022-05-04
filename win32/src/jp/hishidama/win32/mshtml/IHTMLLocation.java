package jp.hishidama.win32.mshtml;

import jp.hishidama.win32.com.IDispatch;

/**
 * IHTMLLocationƒNƒ‰ƒX.
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">‚Ð‚µ‚¾‚Ü</a>
 * @since 2007.11.05
 */
public class IHTMLLocation extends IDispatch {

	protected IHTMLLocation(long dll_ptr) {
		super(dll_ptr);
	}

	public void setHref(String href) {
		Native.put_href(getPtr(), href);
	}

	public String getHref() {
		return Native.get_href(getPtr());
	}

	public void setProtocol(String protocol) {
		Native.put_protocol(getPtr(), protocol);
	}

	public String getProtocol() {
		return Native.get_protocol(getPtr());
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

	public void setPort(String port) {
		Native.put_port(getPtr(), port);
	}

	public String getPort() {
		return Native.get_port(getPtr());
	}

	public void setPathname(String pathname) {
		Native.put_pathname(getPtr(), pathname);
	}

	public String getPathname() {
		return Native.get_pathname(getPtr());
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

	public void reload(boolean flag) {
		Native.reload(getPtr(), flag);
	}

	public void replace(String bstr) {
		Native.replace(getPtr(), bstr);
	}

	public void assign(String bstr) {
		Native.assign(getPtr(), bstr);
	}

	private static class Native {
		//
		// IHTMLLocation
		//
		private static native void put_href(long ptr, String p);

		private static native String get_href(long ptr);

		private static native void put_protocol(long ptr, String p);

		private static native String get_protocol(long ptr);

		private static native void put_host(long ptr, String p);

		private static native String get_host(long ptr);

		private static native void put_hostname(long ptr, String p);

		private static native String get_hostname(long ptr);

		private static native void put_port(long ptr, String p);

		private static native String get_port(long ptr);

		private static native void put_pathname(long ptr, String p);

		private static native String get_pathname(long ptr);

		private static native void put_search(long ptr, String p);

		private static native String get_search(long ptr);

		private static native void put_hash(long ptr, String p);

		private static native String get_hash(long ptr);

		private static native void reload(long ptr, boolean flag);

		private static native void replace(long ptr, String bstr);

		private static native void assign(long ptr, String bstr);
	}
}
