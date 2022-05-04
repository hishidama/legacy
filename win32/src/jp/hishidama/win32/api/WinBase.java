package jp.hishidama.win32.api;

/**
 * winbase.hの関数.
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/hmwin32.html">ひしだま</a>
 * @since 2007.10.01
 * @version 2007.10.02
 */
public class WinBase {
	static {
		System.loadLibrary("hmwin32");
	}

	/**
	 * 最新エラーコード取得.
	 * <p>
	 * Win32APIのGetLastError()を呼び出す。<br>
	 * しかしJNIを経由した関数呼び出しでは（JNIの処理自体がWin32APIを発行している為？）、直前のJNI経由のWin32APIのエラーコードを取ることが出来ない。<br>
	 * したがって当メソッドの存在意義は不明(爆)
	 * </p>
	 * 
	 * @return エラーコード
	 * @see Win32Exception
	 */
	public static native int GetLastError();

	/**
	 * エラーメッセージ取得.
	 * <p>
	 * 使用例：<br>
	 * <code>
	 * int error = WinBase.GetLastError();<br>
	 * String message = WinBase.FormatMessage(error);
	 * </code>
	 * </p>
	 * 
	 * @param error
	 *            {@link #GetLastError()}で取得したエラーコード
	 * @return エラーメッセージ
	 */
	public static native String FormatMessage(int error);

}
