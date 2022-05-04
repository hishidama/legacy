package jp.hishidama.win32.mshtml;

/**
 * IHTMLAnchorElementクラス.
 * <p>
 * aタグを扱うクラスです。
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">ひしだま</a>
 * @since 2007.10.22
 */
public class IHTMLAnchorElement extends IHTMLElement {

	protected IHTMLAnchorElement(long dll_ptr) {
		super(dll_ptr);
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

	public void setUrn(String urn) {
		Native.put_urn(getPtr(), urn);
	}

	public String getUrn() {
		return Native.get_urn(getPtr());
	}

	public void setMethods(String method) {
		Native.put_Methods(getPtr(), method);
	}

	public String getMethods() {
		return Native.get_Methods(getPtr());
	}

	public void setName(String name) {
		Native.put_name(getPtr(), name);
	}

	public String getName() {
		return Native.get_name(getPtr());
	}

	public void setHost(String host) {
		Native.put_host(getPtr(), host);
	}

	public String getHost() {
		return Native.get_host(getPtr());
	}

	public void setHostname(String name) {
		Native.put_hostname(getPtr(), name);
	}

	public String getHostname() {
		return Native.get_hostname(getPtr());
	}

	public void setPathname(String path) {
		Native.put_pathname(getPtr(), path);
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

	public void setAccessKey(String key) {
		Native.put_accessKey(getPtr(), key);
	}

	public String getAccessKey() {
		return Native.get_accessKey(getPtr());
	}

	public String getProtocolLong() {
		return Native.get_protocolLong(getPtr());
	}

	public String getMimeType() {
		return Native.get_mimeType(getPtr());
	}

	public String getNameProp() {
		return Native.get_nameProp(getPtr());
	}

	public void setTabIndex(int index) {
		Native.put_tabIndex(getPtr(), index);
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

	public void setCharset(String charset) {
		Native.put_charset(getPtr(), charset);
	}

	public String getCharset() {
		return Native.get_charset(getPtr());
	}

	public void setCoords(String coords) {
		Native.put_coords(getPtr(), coords);
	}

	public String getCoords() {
		return Native.get_coords(getPtr());
	}

	public void setHreflang(String lang) {
		Native.put_hreflang(getPtr(), lang);
	}

	public String getHreflang() {
		return Native.get_hreflang(getPtr());
	}

	public void setShape(String shape) {
		Native.put_shape(getPtr(), shape);
	}

	public String getShape() {
		return Native.get_shape(getPtr());
	}

	public void setType(String type) {
		Native.put_type(getPtr(), type);
	}

	public String getType() {
		return Native.get_type(getPtr());
	}

	private static class Native {
		//
		// IHTMLAnchorElement
		//

		private static native void put_href(long ptr, String p);

		private static native String get_href(long ptr);

		private static native void put_target(long ptr, String p);

		private static native String get_target(long ptr);

		private static native void put_rel(long ptr, String p);

		private static native String get_rel(long ptr);

		private static native void put_rev(long ptr, String p);

		private static native String get_rev(long ptr);

		private static native void put_urn(long ptr, String p);

		private static native String get_urn(long ptr);

		private static native void put_Methods(long ptr, String p);

		private static native String get_Methods(long ptr);

		private static native void put_name(long ptr, String p);

		private static native String get_name(long ptr);

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

		private static native void put_accessKey(long ptr, String p);

		private static native String get_accessKey(long ptr);

		private static native String get_protocolLong(long ptr);

		private static native String get_mimeType(long ptr);

		private static native String get_nameProp(long ptr);

		private static native void put_tabIndex(long ptr, int p);

		private static native int get_tabIndex(long ptr);

		private static native void focus(long ptr);

		private static native void blur(long ptr);

		//
		// IHTMLAnchorElement2
		//

		private static native void put_charset(long ptr, String p);

		private static native String get_charset(long ptr);

		private static native void put_coords(long ptr, String p);

		private static native String get_coords(long ptr);

		private static native void put_hreflang(long ptr, String p);

		private static native String get_hreflang(long ptr);

		private static native void put_shape(long ptr, String p);

		private static native String get_shape(long ptr);

		private static native void put_type(long ptr, String p);

		private static native String get_type(long ptr);

	}
}
