package jp.hishidama.win32.com;

import jp.hishidama.win32.api.WinNTConst;

/**
 * IDispatchクラス.
 * <p>
 * COMのIDispatchインターフェースのクラスです。
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">ひしだま</a>
 * @since 2007.11.04
 * @version 2008.07.14
 */
public class IDispatch extends IUnknown {

	protected IDispatch(long dll_ptr) {
		super(dll_ptr);
	}

	/**
	 * オブジェクト名取得.
	 * <p>
	 * 「IWebBrowser2」や「DispHTMLDocument」といった、オブジェクトの実体の名称を返す。
	 * </p>
	 * 
	 * @return オブジェクト名（取得に失敗した場合、null）
	 * @see ITypeInfo#getName()
	 */
	public String getTypeName() {
		ITypeInfo info = null;
		try {
			info = Native.GetTypeInfo(getPtr(), 0,
					WinNTConst.LOCALE_USER_DEFAULT);
			return info.getName();
		} catch (RuntimeException e) {
			return null;
		} finally {
			ComPtr.dispose(info);
		}
	}

	/**
	 * 型情報数取得.
	 * <p>
	 * {@link ITypeInfo 型情報}の個数を返す。<br>
	 * IDispatchをサポートするオブジェクトにおいては 常に1なんだそうだ。
	 * </p>
	 * 
	 * @return 個数（0または1）
	 * @see #getTypeInfo(int, int, boolean)
	 */
	public int getTypeInfoCount() {
		return Native.GetTypeInfoCount(getPtr());
	}

	/**
	 * 型情報取得.
	 * 
	 * @param iTInfo
	 *            型情報の番号
	 * @param lcid
	 *            ロケールID（{@link WinNTConst#LOCALE_SYSTEM_DEFAULT}等）
	 * @return 型情報
	 */
	public ITypeInfo getTypeInfo(int iTInfo, int lcid) {
		ITypeInfo c = Native.GetTypeInfo(getPtr(), iTInfo, lcid);
		return c;
	}

	/**
	 * @see ComPtr#disposeChild()
	 * @deprecated
	 * @see #getTypeInfo(int, int)
	 */
	public ITypeInfo getTypeInfo(int iTInfo, int lcid, boolean addChild) {
		return getTypeInfo(iTInfo, lcid);
	}

	/**
	 * DISPID取得.
	 * <p>
	 * メソッドまたはプロパティの名前からDISPIDを取得する。
	 * </p>
	 * 
	 * @param name
	 *            名称
	 * @return DISPID
	 */
	public long getIDOfName(String name) {
		long[] id = Native.GetIDsOfNames(getPtr(), 0/* IID_NULL */,
				new String[] { name }, WinNTConst.LOCALE_USER_DEFAULT);
		return id[0];
	}

	// そもそも配列なのか不明（要素数が1のときは動いてるけど）
	// public long[] getIDsOfNames(long riid, String[] names, int lcid) {
	// return Native.GetIDsOfNames(getPtr(), riid, names, lcid);
	// }

	private static class Native {
		//
		// IDispatch
		//
		private static native int GetTypeInfoCount(long ptr);

		private static native ITypeInfo GetTypeInfo(long ptr, int iTInfo,
				int lcid);

		private static native long[] GetIDsOfNames(long ptr, long riid,
				String[] rgszNames, int lcid);

		// private static native void Invoke(long ptr, DISPID dispIdMember,
		// REFIID riid, LCID lcid, WORD wFlags, DISPPARAMS *pDispParams, Variant
		// *pVarResult, EXCEPINFO *pExcepInfo, UINT *puArgErr);
	}
}
