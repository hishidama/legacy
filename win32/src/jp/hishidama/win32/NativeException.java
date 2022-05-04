package jp.hishidama.win32;

/**
 * MS-Windows構造化例外.
 * <p>
 * ウィンドウズの構造化例外のラッパークラス。
 * </p>
 * <p>
 * JNI呼び出しにおいてアクセス違反などの構造化例外が発生した場合に、その情報を元に当例外を発生させている。<br>
 * デフォルトでは 構造化例外が発生するとJNIライブラリーが情報をダンプして異常終了するが、当ライブラリではこの例外でキャッチできる。はず。
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/hmwin32.html">ひしだま</a>
 * @since 2007.10.22
 */
public class NativeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4164868244515537003L;

	protected int code;

	protected int flag;

	protected long addr;

	protected long[] info;

	/**
	 * コンストラクター.
	 * 
	 * @param code
	 *            エラーコード
	 * @param flag
	 *            フラグ
	 * @param addr
	 *            アドレス
	 * @param info
	 *            エラー情報
	 */
	public NativeException(int code, int flag, long addr, long[] info) {
		this.code = code;
		this.flag = flag;
		this.addr = addr;
		this.info = info;
	}

	/**
	 * エラーコード取得.
	 * 
	 * @return エラーコード
	 */
	public int getCode() {
		return code;
	}

	/**
	 * フラグ取得.
	 * 
	 * @return フラグ
	 */
	public int getFlag() {
		return flag;
	}

	/**
	 * アドレス取得.
	 * 
	 * @return エラー発生時のプログラムのアドレス
	 */
	public long getAddr() {
		return addr;
	}

	/**
	 * エラー情報取得.
	 * 
	 * @return エラー情報の配列
	 */
	public long[] getInfo() {
		return info;
	}

	public String getMessage() {
		StringBuffer sb = new StringBuffer(128);
		sb.append("code=0x");
		sb.append(Integer.toHexString(code));
		String msg = getMessage(code);
		if (!msg.equals("")) {
			sb.append('(');
			sb.append(msg);
			sb.append(')');
		}
		sb.append(" flag=0x");
		sb.append(Integer.toHexString(flag));
		sb.append(" addr=0x");
		sb.append(Long.toHexString(addr));
		sb.append(" info=");
		if (info == null) {
			sb.append(info);
		} else {
			sb.append('{');
			for (int i = 0; i < info.length; i++) {
				if (i != 0)
					sb.append(", ");
				sb.append("0x");
				sb.append(Long.toHexString(info[i]));
			}
			sb.append('}');
		}
		return sb.toString();
	}

	/**
	 * エラー名取得.
	 * 
	 * @param code
	 *            エラーコード
	 * @return エラー名
	 */
	public static String getMessage(int code) {
		switch (code) {
		case 0xC0000005:
			return "ACCESS_VIOLATION";
		case 0xC0000008:
			return "INVALID_HANDLE";
		}
		return "";
	}
}
