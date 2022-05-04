package jp.hishidama.win32.api;

/**
 * Win32API呼び出し例外.
 * <p>
 * Win32APIを呼び出してGetLastError()が0以外になった場合、当例外を発生させている。
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/hmwin32.html">ひしだま</a>
 * @since 2007.10.02
 */
public class Win32Exception extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6826834469784878586L;

	protected int error;

	/**
	 * コンストラクター
	 * 
	 * @param error
	 *            エラーコード
	 */
	public Win32Exception(int error) {
		this.error = error;
	}

	/**
	 * エラーコード取得.
	 * 
	 * @return エラーコード
	 */
	public int getLastError() {
		return error;
	}

	public String getMessage() {
		return WinBase.FormatMessage(error);
	}
}
