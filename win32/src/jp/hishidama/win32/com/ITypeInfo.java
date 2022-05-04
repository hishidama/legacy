package jp.hishidama.win32.com;

/**
 * ITypeInfoクラス.
 * <p>
 * COMのITypeInfoインターフェースのクラスです。
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">ひしだま</a>
 * @since 2007.11.06
 */
public class ITypeInfo extends IUnknown {

	protected ITypeInfo(long dll_ptr) {
		super(dll_ptr);
	}

	/**
	 * オブジェクト名取得.
	 * 
	 * @return オブジェクト名
	 * @see #getDocumentation(long, String[], String[], int[], String[])
	 */
	public String getName() {
		String[] name = new String[1];
		Native.GetDocumentation(getPtr(), -1, name, null, null, null);
		return name[0];
	}

	/**
	 * オブジェクト説明取得.
	 * 
	 * @return オブジェクト説明
	 * @see #getDocumentation(long, String[], String[], int[], String[])
	 */
	public String getDoc() {
		String[] doc = new String[1];
		Native.GetDocumentation(getPtr(), -1, null, doc, null, null);
		return doc[0];
	}

	/**
	 * MEMBERID取得.
	 * <p>
	 * メソッドまたはプロパティの名前からMEMBERIDを取得する。
	 * </p>
	 * 
	 * @param name
	 *            名称
	 * @return MEMBERID
	 */
	public long getIDOfName(String name) {
		long[] ret = Native.GetIDsOfNames(getPtr(), new String[] { name });
		return ret[0];
	}

	// そもそも配列なのか不明
	// public long[] getIDsOfNames(String[] names) {
	// return Native.GetIDsOfNames(getPtr(), names);
	// }

	/**
	 * ドキュメンテーション取得.
	 * <p>
	 * 説明文を取得する。<br>
	 * memidが-1の場合、オブジェクトそのものの説明を返す。
	 * </p>
	 * 
	 * @param memid
	 *            MEMBERID
	 * @param name
	 *            null以外の場合、添字0にオブジェクト名を返す
	 * @param doc
	 *            null以外の場合、添字0にオブジェクトの説明文を返す
	 * @param helpContext
	 *            null以外の場合、添字0にヘルプのコンテキストIDを返す
	 * @param helpFile
	 *            null以外の場合、添字0にヘルプファイルのパスを返す
	 */
	public void getDocumentation(long memid, String[] name, String[] doc,
			int[] helpContext, String[] helpFile) {
		Native.GetDocumentation(getPtr(), memid, name, doc, helpContext,
				helpFile);
	}

	/**
	 * マーシャリング情報取得.
	 * 
	 * @param memid
	 *            MEMBERID
	 * @return マーシャリング情報
	 */
	public String getMops(long memid) {
		return Native.GetMops(getPtr(), memid);
	}

	private static class Native {
		//
		// ITypeInfo
		//

		// private static native TYPEATTR GetTypeAttr(long ptr);
		// private static native ITypeComp GetTypeComp(long ptr);
		// private static native FUNCDESC GetFuncDesc(long ptr, int index);
		// private static native VARDESC GetVarDesc(long ptr, int index);
		// private static native int GetNames(long ptr, long memid, String
		// rgBstrNames, int cMaxNames);
		// private static native HREFTYPE GetRefTypeOfImplType(long ptr, int
		// index);
		// private static native int GetImplTypeFlags(long ptr, int index);
		private static native long[] GetIDsOfNames(long ptr, String[] rgszNames);

		// private static native void Invoke(long ptr, PVOID pvInstance,
		// MEMBERID memid, WORD wFlags, DISPPARAMS *pDispParams, Variant
		// *pVarResult, EXCEPINFO *pExcepInfo, UINT *puArgErr);
		private static native void GetDocumentation(long ptr, long memid,
				String[] pBstrName, String[] pBstrDocString,
				int[] pdwHelpContext, String[] pBstrHelpFile);

		// private static native void GetDllEntry(long ptr, long memid,
		// INVOKEKIND invKind, String *pBstrDllName, String *pBstrName, WORD
		// *pwOrdinal);
		// private static native ITypeInfo GetRefTypeInfo(long ptr, HREFTYPE
		// hRefType);
		// private static native PVOID AddressOfMember(long ptr, long memid,
		// INVOKEKIND invKind);
		// private static native PVOID CreateInstance(long ptr, IUnknown
		// pUnkOuter, REFIID riid);
		private static native String GetMops(long ptr, long memid);
		// private static native void GetContainingTypeLib(long ptr, ITypeLib
		// **ppTLib, UINT *pIndex);
		// private static native void ReleaseTypeAttr(long ptr, TYPEATTR
		// pTypeAttr);
		// private static native void ReleaseFuncDesc(long ptr, FUNCDESC
		// pFuncDesc);
		// private static native void ReleaseVarDesc(long ptr, VARDESC
		// pVarDesc);
	}
}
