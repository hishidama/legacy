package jp.hishidama.win32.mshtml;

import jp.hishidama.win32.com.IDispatch;
import jp.hishidama.win32.com.IUnknown;
import jp.hishidama.win32.com.Variant;

/**
 * IHTMLAreasCollection,IHTMLAreasCollection2Å`3ÉNÉâÉX.
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">Ç–ÇµÇæÇ‹</a>
 * @since 2007.11.05
 * @version 2008.07.14
 * @see IHTMLMapElement#getAreas(boolean)
 */
public class IHTMLAreasCollection extends IDispatch {

	protected IHTMLAreasCollection(long dll_ptr) {
		super(dll_ptr);
	}

	public void setLength(int length) {
		Native.put_length(getPtr(), length);
	}

	public int getLength() {
		return Native.get_length(getPtr());
	}

	public IUnknown getNewEnum() {
		IUnknown c = Native.get__newEnum(getPtr());
		return c;
	}

	/**
	 * @deprecated
	 * @see #getNewEnum()
	 */
	public IUnknown getNewEnum(boolean addChild) {
		return getNewEnum();
	}

	public IDispatch item(Variant name, Variant index) {
		return Native.item(getPtr(), name, index);
	}

	/**
	 * @deprecated
	 * @see #item(Variant, Variant)
	 */
	public IDispatch item(Variant name, Variant index, boolean addChild) {
		return item(name, index);
	}

	public IDispatch tags(Variant tagName) {
		return Native.tags(getPtr(), tagName);
	}

	/**
	 * @deprecated
	 * @see #tags(Variant)
	 */
	public IDispatch tags(Variant tagName, boolean addChild) {
		return tags(tagName);
	}

	public void add(IHTMLElement element, Variant before) {
		Native.add(getPtr(), element.getPtr(), before);
	}

	public void remove(int index) {
		Native.remove(getPtr(), index);
	}

	public IDispatch urns(Variant urn) {
		return Native.urns(getPtr(), urn);
	}

	/**
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
	 * @deprecated
	 * @see #namedItem(String)
	 */
	public IDispatch namedItem(String name, boolean addChild) {
		return namedItem(name);
	}

	private static class Native {
		//
		// IHTMLAreasCollection
		//
		private static native void put_length(long ptr, int p);

		private static native int get_length(long ptr);

		private static native IUnknown get__newEnum(long ptr);

		private static native IDispatch item(long ptr, Variant name,
				Variant index);

		private static native IDispatch tags(long ptr, Variant tagName);

		private static native void add(long ptr, long element, Variant before);

		private static native void remove(long ptr, int index);

		//
		// IHTMLAreasCollection2
		//
		private static native IDispatch urns(long ptr, Variant urn);

		//
		// IHTMLAreasCollection3
		//
		private static native IDispatch namedItem(long ptr, String name);
	}
}
