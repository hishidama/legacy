package jp.hishidama.win32.api;

import jp.hishidama.win32.com.ComMgr;

/**
 * objbase.hの関数.
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/hmwin32.html">ひしだま</a>
 * @since 2007.10.22
 * @version 2007.11.07
 */
public class ObjBase {
	static {
		System.loadLibrary("hmwin32");
	}

	/**
	 * COMの初期化.
	 * <p>
	 * ::CoInitialize()
	 * </p>
	 * 
	 * @return HRESULT
	 * @see ComMgr#initialize()
	 */
	public static native int CoInitialize();

	/**
	 * COMの初期化.
	 * <p>
	 * ::CoInitializeEx()
	 * </p>
	 * 
	 * @param coInit
	 *            アパートメント（{@link ObjBaseConst#COINIT_MULTITHREADED}等）
	 * @return HRESULT
	 * @see ComMgr#initialize()
	 */
	public static native int CoInitializeEx(int coInit);

	/**
	 * COMの解放.
	 * <p>
	 * ::CoUninitialize()
	 * </p>
	 * 
	 * @see ComMgr#uninitialize()
	 */
	public static native void CoUninitialize();

}
