package jp.hishidama.win32.mshtml;

import java.util.ArrayList;
import java.util.List;

import jp.hishidama.win32.com.ComPtr;
import jp.hishidama.win32.com.IDispatch;
import jp.hishidama.win32.com.IUnknown;
import jp.hishidama.win32.com.Variant;

/**
 * IHTMLElementCollection,IHTMLElementCollection2�`3�N���X.
 * <p>
 * HTML�v�f�̈ꗗ�������N���X�ł��B
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">�Ђ�����</a>
 * @since 2007.10.22
 * @version 2008.07.14
 */
public class IHTMLElementCollection extends IDispatch {

	protected IHTMLElementCollection(long dll_ptr) {
		super(dll_ptr);
	}

	/**
	 * ���ݒ�.
	 * 
	 * @param len
	 *            ��
	 */
	public void setLength(int len) {
		Native.put_length(getPtr(), len);
	}

	/**
	 * ���擾.
	 * 
	 * @return ��
	 */
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

	/**
	 * HTML�v�f�擾.
	 * 
	 * @param name
	 *            ���O
	 * @param index
	 *            �ԍ�
	 * @return HTML�v�f
	 */
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

	/**
	 * HTML�v�f�擾.
	 * 
	 * @param index
	 *            �ԍ�
	 * @return HTML�v�f
	 */
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

	/**
	 * HTML�v�f�擾.
	 * 
	 * @param tagName
	 *            �^�O��
	 * @return HTML�v�f
	 */
	public IHTMLElement tags(Variant tagName) {
		return (IHTMLElement) Native.tags(getPtr(), tagName);
	}

	/**
	 * @see ComPtr#disposeChild()
	 * @deprecated
	 * @see #tags(Variant)
	 */
	public IHTMLElement tags(Variant tagName, boolean addChild) {
		return tags(tagName);
	}

	/**
	 * HTML�v�f�擾.
	 * 
	 * @param urn
	 *            URN
	 * @return HTML�v�f
	 */
	public IHTMLElement urns(Variant urn) {
		return (IHTMLElement) Native.urns(getPtr(), urn);
	}

	/**
	 * @see ComPtr#disposeChild()
	 * @deprecated
	 * @see #urns(Variant)
	 */
	public IHTMLElement urns(Variant urn, boolean addChild) {
		return urns(urn);
	}

	/**
	 * HTML�v�f�擾.
	 * 
	 * @param name
	 *            ����
	 * @return HTML�v�f
	 */
	public IHTMLElement getNamedItem(String name) {
		return (IHTMLElement) Native.namedItem(getPtr(), name);
	}

	/**
	 * @see ComPtr#disposeChild()
	 * @deprecated
	 * @see #getNamedItem(String)
	 */
	public IHTMLElement getNamedItem(String name, boolean addChild) {
		return getNamedItem(name);
	}

	/**
	 * ���X�g��.
	 * <p>
	 * �ێ����Ă���S�v�f�����X�g�ɓ���ĕԂ��B
	 * </p>
	 * 
	 * @return {@link IHTMLElement}�̃��X�g
	 */
	public List list() {
		long ptr = getPtr();
		int l = Native.get_length(ptr);
		List list = new ArrayList(l);
		Variant vari = new Variant();
		for (int i = 0; i < l; i++) {
			vari.setInt(i);
			IDispatch e = Native.item(ptr, vari, vari);
			list.add(e);
		}
		return list;
	}

	/**
	 * @see ComPtr#disposeChild()
	 * @deprecated
	 * @see #list()
	 */
	public List list(boolean addChild) {
		return list();
	}

	private static class Native {
		//
		// IHTMLElementCollection
		//

		// private static native String toString (long ptr);

		private static native void put_length(long ptr, int p);

		private static native int get_length(long ptr);

		private static native IUnknown get__newEnum(long ptr);

		private static native IDispatch item(long ptr, Variant name,
				Variant index);

		private static native IDispatch tags(long ptr, Variant tagName);

		//
		// IHTMLElementCollection2
		//

		private static native IDispatch urns(long ptr, Variant urn);

		//
		// IHTMLElementCollection3
		//

		private static native IDispatch namedItem(long ptr, String name);
	}
}
