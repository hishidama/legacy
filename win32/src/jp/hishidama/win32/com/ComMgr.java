package jp.hishidama.win32.com;

import java.util.Iterator;

import jp.hishidama.win32.api.ObjBase;
import jp.hishidama.win32.api.ObjBaseConst;

/**
 * COM管理.
 * <p>
 * COMの制御を行うクラス。
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">ひしだま</a>
 * @since 2007.10.22
 * @version 2007.11.07
 */
public class ComMgr {
	static {
		System.loadLibrary("hmwin32");
	}

	/**
	 * COM（STA）初期化.
	 * <p>
	 * {@link ObjBase#CoInitialize()}を呼び出し、COMをSTA（シングルスレッドアパートメント）で初期化する。
	 * </p>
	 * <p>
	 * COM関連クラスを使う前に、当メソッドを一回だけ呼び出す必要がある。<br>
	 * そして、最後に{@link #uninitialize()}を呼び出す必要がある。
	 * </p>
	 */
	public static synchronized void initialize() {
		ObjBase.CoInitialize();
		// ObjBase.CoInitializeEx(ObjBaseConst.COINIT_APARTMENTTHREADED);
	}

	/**
	 * COM（MTA）初期化.
	 * <p>
	 * {@link ObjBase#CoInitializeEx(int)}を呼び出し、COMをMTA（マルチスレッドアパートメント）で初期化する。
	 * </p>
	 * <p>
	 * COM関連クラスを使う前に、当メソッドを一回だけ呼び出す必要がある。<br>
	 * そして、最後に{@link #uninitialize()}を呼び出す必要がある。
	 * </p>
	 */
	public static synchronized void initializeMTA() {
		ObjBase.CoInitializeEx(ObjBaseConst.COINIT_MULTITHREADED);
	}

	/**
	 * COM終了.
	 * <p>
	 * {@link ObjBase#CoUninitialize()}を呼び出し、COMの使用を終了する。
	 * </p>
	 * <p>
	 * 取得されたが{@link ComPtr#dispose()}が呼ばれていないCOMオブジェクトも解放する。
	 * </p>
	 */
	public static synchronized void uninitialize() {

		// “明示的にdispose()が呼ばれていないオブジェクト”を解放する
		disposeAll();

		ObjBase.CoUninitialize();
	}

	/**
	 * 全COMオブジェクト破棄.
	 */
	public static synchronized void disposeAll() {
		// System.out.println("all:" + ComPtr.debug);
		for (Iterator i = ComPtr.clist.iterator(); i.hasNext();) {
			ComPtr d = (ComPtr) i.next();
			if (d != null) {
				d.dispose();
			}
		}
		ComPtr.clist.clear();
		// System.out.println("end:" + ComPtr.debug);
	}

	/**
	 * 参照カウンターを減少させる.
	 * 
	 * @param ptr
	 *            DLL内ポインター
	 */
	protected static native void delete(long ptr);
}
