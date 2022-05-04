package jp.hishidama.util;

public class NumberUtil {

	/**
	 * 数値変換.
	 * <p>
	 * 接頭辞付きの数値文字列をlongに変換する。 <table border="1"> <caption>接頭辞</caption>
	 * <tr>
	 * <td>0b</td>
	 * <td>二進数</td>
	 * </tr>
	 * <tr>
	 * <td>0o</td>
	 * <td>八進数</td>
	 * </tr>
	 * <tr>
	 * <td>0x</td>
	 * <td>十六進数</td>
	 * </tr>
	 * <tr>
	 * <td>その他</td>
	 * <td>十進数</td>
	 * </tr>
	 * </table>
	 * </p>
	 * <p>
	 * 接尾辞は、単純に無視する。 <table border="1"> <caption>接尾辞</caption>
	 * <tr>
	 * <td>l</td>
	 * <td>.</td>
	 * </tr>
	 * </table>
	 * </p>
	 * 
	 * @param str
	 *            文字列（接頭辞付き）
	 * @return 数値
	 * @throws NumberFormatException
	 */
	public static long parseLong(String str) {
		if (str == null) {
			return 0;
		}
		str = str.trim();
		int len = str.length();
		if (len <= 0) {
			return 0;
		}

		// 接尾辞をカット
		switch (str.charAt(len - 1)) {
		case 'l':
		case 'L':
		case '.':
			len--;
			break;
		}

		if (len >= 3 && str.charAt(0) == '0') {
			switch (str.charAt(1)) {
			case 'b':
			case 'B':
				return parseLongBin(str, 2, len - 2);
			case 'o':
			case 'O':
				return parseLongOct(str, 2, len - 2);
			case 'x':
			case 'X':
				return parseLongHex(str, 2, len - 2);
			}
		}
		return parseLongDec(str, 0, len);
	}

	/**
	 * 二進数変換.
	 * <p>
	 * 文字列を二進数としてlongに変換する。
	 * </p>
	 * 
	 * @param str
	 *            文字列
	 * @return 数値
	 * @throws NumberFormatException
	 */
	public static long parseLongBin(String str) {
		if (str == null) {
			return 0;
		}
		return parseLongBin(str, 0, str.length());
	}

	/**
	 * 二進数変換.
	 * <p>
	 * 文字列を二進数としてlongに変換する。
	 * </p>
	 * 
	 * @param str
	 *            文字列
	 * @param pos
	 *            位置
	 * @param len
	 *            長さ
	 * @return 数値
	 * @throws NumberFormatException
	 */
	public static long parseLongBin(String str, int pos, int len) {
		long ret = 0;
		for (int i = 0; i < len; i++) {
			ret *= 2;
			char c = str.charAt(pos++);
			switch (c) {
			case '0':
				break;
			case '1':
				ret += 1;
				break;
			default:
				throw new NumberFormatException(str.substring(pos, len));
			}
		}
		return ret;
	}

	/**
	 * 八進数変換.
	 * <p>
	 * 文字列を八進数としてlongに変換する。
	 * </p>
	 * 
	 * @param str
	 *            文字列
	 * @return 数値
	 * @throws NumberFormatException
	 */
	public static long parseLongOct(String str) {
		if (str == null) {
			return 0;
		}
		return parseLongOct(str, 0, str.length());
	}

	/**
	 * 八進数変換.
	 * <p>
	 * 文字列を八進数としてlongに変換する。
	 * </p>
	 * 
	 * @param str
	 *            文字列
	 * @param pos
	 *            位置
	 * @param len
	 *            長さ
	 * @return 数値
	 * @throws NumberFormatException
	 */
	public static long parseLongOct(String str, int pos, int len) {
		long ret = 0;
		for (int i = 0; i < len; i++) {
			ret *= 8;
			char c = str.charAt(pos++);
			switch (c) {
			case '0':
				break;
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
				ret += c - '0';
				break;
			default:
				throw new NumberFormatException(str.substring(pos, len));
			}
		}
		return ret;
	}

	/**
	 * 十進数変換.
	 * <p>
	 * 文字列を十進数としてlongに変換する。
	 * </p>
	 * 
	 * @param str
	 *            文字列
	 * @return 数値
	 * @throws NumberFormatException
	 */
	public static long parseLongDec(String str) {
		if (str == null) {
			return 0;
		}
		return parseLongDec(str, 0, str.length());
	}

	/**
	 * 十進数変換.
	 * <p>
	 * 文字列を十進数としてlongに変換する。
	 * </p>
	 * 
	 * @param str
	 *            文字列
	 * @param pos
	 *            位置
	 * @param len
	 *            長さ
	 * @return 数値
	 * @throws NumberFormatException
	 */
	public static long parseLongDec(String str, int pos, int len) {
		long ret = 0;
		for (int i = 0; i < len; i++) {
			ret *= 10;
			char c = str.charAt(pos++);
			switch (c) {
			case '0':
				break;
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				ret += c - '0';
				break;
			default:
				throw new NumberFormatException(str.substring(pos, len));
			}
		}
		return ret;
	}

	/**
	 * 十六進数変換.
	 * <p>
	 * 文字列を十六進数としてlongに変換する。
	 * </p>
	 * 
	 * @param str
	 *            文字列
	 * @return 数値
	 * @throws NumberFormatException
	 */
	public static long parseLongHex(String str) {
		if (str == null) {
			return 0;
		}
		return parseLongHex(str, 0, str.length());
	}

	/**
	 * 十六進数変換.
	 * <p>
	 * 文字列を十六進数としてlongに変換する。
	 * </p>
	 * 
	 * @param str
	 *            文字列
	 * @param pos
	 *            位置
	 * @param len
	 *            長さ
	 * @return 数値
	 * @throws NumberFormatException
	 */
	public static long parseLongHex(String str, int pos, int len) {
		long ret = 0;
		for (int i = 0; i < len; i++) {
			ret *= 16;
			char c = str.charAt(pos++);
			switch (c) {
			case '0':
				break;
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				ret += c - '0';
				break;
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
				ret += c - 'a' + 10;
				break;
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
				ret += c - 'A' + 10;
				break;
			default:
				throw new NumberFormatException(str.substring(pos, len));
			}
		}
		return ret;
	}

}
