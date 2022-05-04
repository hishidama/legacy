package jp.hishidama.win32.mshtml;

import jp.hishidama.win32.com.ComPtr;
import jp.hishidama.win32.com.IDispatch;
import jp.hishidama.win32.com.IUnknown;
import jp.hishidama.win32.com.Variant;

/**
 * IHTMLFormElement,IHTMLFormElement2,IHTMLFormElement3クラス.
 * <p>
 * formタグを扱うクラスです。
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">ひしだま</a>
 * @since 2007.10.22
 * @version 2008.07.14
 */
public class IHTMLFormElement extends IHTMLElement {

	protected IHTMLFormElement(long dll_ptr) {
		super(dll_ptr);
	}

	public void setAction(String action) {
		Native.put_action(getPtr(), action);
	}

	public String getAction() {
		return Native.get_action(getPtr());
	}

	public void setDir(String dir) {
		Native.put_dir(getPtr(), dir);
	}

	public String getDir() {
		return Native.get_dir(getPtr());
	}

	public void setEncoding(String encoding) {
		Native.put_encoding(getPtr(), encoding);
	}

	public String getEncoding() {
		return Native.get_encoding(getPtr());
	}

	public void setMethod(String method) {
		Native.put_method(getPtr(), method);
	}

	public String getMethod() {
		return Native.get_method(getPtr());
	}

	public IHTMLFormElement getElements() {
		return (IHTMLFormElement) Native.get_elements(getPtr());
	}

	/**
	 * @see ComPtr#disposeChild()
	 * @deprecated
	 * @see #getElements()
	 */
	public IHTMLFormElement getElements(boolean addChild) {
		return getElements();
	}

	public void setTarget(String target) {
		Native.put_target(getPtr(), target);
	}

	public String getTarget() {
		return Native.get_target(getPtr());
	}

	public void setName(String name) {
		Native.put_name(getPtr(), name);
	}

	public String getName() {
		return Native.get_name(getPtr());
	}

	public void submit() {
		Native.submit(getPtr());
	}

	public void reset() {
		Native.reset(getPtr());
	}

	public void setLength(int len) {
		Native.put_length(getPtr(), len);
	}

	public int getLength() {
		return Native.get_length(getPtr());
	}

	public IUnknown getNewEnum() {
		return Native.get__newEnum(getPtr());
	}

	/**
	 * @see ComPtr#disposeChild()
	 * @deprecated
	 * @see #getNewEnum()
	 */
	public IUnknown getNewEnum(boolean addChild) {
		return getNewEnum();
	}

	public IHTMLElement item(String name, int index) {
		return (IHTMLElement) Native.item(getPtr(), new Variant(name),
				new Variant(index));
	}

	/**
	 * @see ComPtr#disposeChild()
	 * @deprecated
	 * @see #item(String, int)
	 */
	public IHTMLElement item(String name, int index, boolean addChild) {
		return item(name, index);
	}

	public IHTMLElement item(int index) {
		Variant vari = new Variant(index);
		return (IHTMLElement) Native.item(getPtr(), vari, vari);
	}

	/**
	 * @see ComPtr#disposeChild()
	 * @deprecated
	 * @see #item(int)
	 */
	public IHTMLElement item(int index, boolean addChild) {
		return item(index);
	}

	public IDispatch tags(Variant tagName) {
		return Native.tags(getPtr(), tagName);
	}

	/**
	 * @see ComPtr#disposeChild()
	 * @deprecated
	 * @see #tags(Variant)
	 */
	public IDispatch tags(Variant tagName, boolean addChild) {
		return tags(tagName);
	}

	public void setAcceptCharset(String charset) {
		Native.put_acceptCharset(getPtr(), charset);
	}

	public String getAcceptCharset() {
		return Native.get_acceptCharset(getPtr());
	}

	public IDispatch urns(Variant urn) {
		return Native.urns(getPtr(), urn);
	}

	/**
	 * @see ComPtr#disposeChild()
	 * @deprecated
	 * @see #urns(Variant)
	 */
	public IDispatch urns(Variant urn, boolean addChild) {
		return urns(urn);
	}

	public IDispatch namedItem(String name) {
		return Native.namedItem(getPtr(), name);
	}

	/**
	 * @see ComPtr#disposeChild()
	 * @deprecated
	 * @see #namedItem(String)
	 */
	public IDispatch namedItem(String name, boolean addChild) {
		return namedItem(name);
	}

	private static class Native {
		//
		// IHTMLFormElement
		//

		private static native void put_action(long ptr, String action);

		private static native String get_action(long ptr);

		private static native void put_dir(long ptr, String dir);

		private static native String get_dir(long ptr);

		private static native void put_encoding(long ptr, String encoding);

		private static native String get_encoding(long ptr);

		private static native void put_method(long ptr, String method);

		private static native String get_method(long ptr);

		private static native IDispatch get_elements(long ptr);

		private static native void put_target(long ptr, String target);

		private static native String get_target(long ptr);

		private static native void put_name(long ptr, String name);

		private static native String get_name(long ptr);

		private static native void submit(long ptr);

		private static native void reset(long ptr);

		private static native void put_length(long ptr, int len);

		private static native int get_length(long ptr);

		private static native IUnknown get__newEnum(long ptr);

		private static native IDispatch item(long ptr, Variant name,
				Variant index);

		private static native IDispatch tags(long ptr, Variant tagName);

		//
		// IHTMLFormElement2
		//
		private static native void put_acceptCharset(long ptr, String charset);

		private static native String get_acceptCharset(long ptr);

		private static native IDispatch urns(long ptr, Variant urn);

		//
		// IHTMLFormElement3
		//
		private static native IDispatch namedItem(long ptr, String name);
	}
}
