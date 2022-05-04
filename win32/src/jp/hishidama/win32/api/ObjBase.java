package jp.hishidama.win32.api;

import jp.hishidama.win32.com.ComMgr;

/**
 * objbase.h�̊֐�.
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/hmwin32.html">�Ђ�����</a>
 * @since 2007.10.22
 * @version 2007.11.07
 */
public class ObjBase {
	static {
		System.loadLibrary("hmwin32");
	}

	/**
	 * COM�̏�����.
	 * <p>
	 * ::CoInitialize()
	 * </p>
	 * 
	 * @return HRESULT
	 * @see ComMgr#initialize()
	 */
	public static native int CoInitialize();

	/**
	 * COM�̏�����.
	 * <p>
	 * ::CoInitializeEx()
	 * </p>
	 * 
	 * @param coInit
	 *            �A�p�[�g�����g�i{@link ObjBaseConst#COINIT_MULTITHREADED}���j
	 * @return HRESULT
	 * @see ComMgr#initialize()
	 */
	public static native int CoInitializeEx(int coInit);

	/**
	 * COM�̉��.
	 * <p>
	 * ::CoUninitialize()
	 * </p>
	 * 
	 * @see ComMgr#uninitialize()
	 */
	public static native void CoUninitialize();

}
