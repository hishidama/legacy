package jp.hishidama.util;

public class CharUtil {

	/**
	 * エスケープ解釈.
	 * <p>
	 * エスケープ文字「\」を解釈した文字列を返す。
	 * </p>
	 * 
	 * @param str
	 *            文字列
	 * @return エスケープ解釈後文字列
	 */
	public static String escapeString(String str) {
		return escapeString(str, 0, str.length());
	}

	/**
	 * エスケープ解釈.
	 * <p>
	 * エスケープ文字「\」を解釈した文字列を返す。
	 * </p>
	 * 
	 * @param str
	 *            文字列
	 * @param pos
	 *            開始位置
	 * @param len
	 *            処理する長さ
	 * @return エスケープ解釈後文字列
	 */
	public static String escapeString(String str, int pos, int len) {
		StringBuilder sb = new StringBuilder(len);
		int end_pos = pos + len;
		int[] ret = new int[1];
		while (pos < end_pos) {
			char c = escapeChar(str, pos, end_pos, ret);
			if (ret[0] <= 0) {
				break;
			}
			sb.append(c);
			pos += ret[0];
		}
		return sb.toString();
	}

	/**
	 * エスケープ解釈後文字取得.
	 * <p>
	 * 文字列中の文字を返す。「\」があれば、エスケープ文字を解釈した文字を返す。
	 * </p>
	 * 
	 * @param str
	 *            文字列
	 * @param pos
	 *            解釈位置
	 * @param end_pos
	 *            解釈終了位置（この位置にある文字は解釈対象外）
	 * @param ret
	 *            ret[0]:使用した長さ
	 * @return 解釈した文字
	 */
	public static char escapeChar(String str, int pos, int end_pos, int[] ret) {
		if (pos >= end_pos) {
			ret[0] = 0;
			return '\0';
		}
		char c = str.charAt(pos);
		if (c != '\\') {
			ret[0] = 1;
			return c;
		}

		if (++pos >= end_pos) {
			ret[0] = 1;
			return c; // 後続文字が無い場合は'\'そのものを返すことにする
		}

		ret[0] = 2;
		c = str.charAt(pos);
		switch (c) {
		case '0':
		case '1':
		case '2':
		case '3':
		case '4':
		case '5':
		case '6':
		case '7': {
			long code = c - '0';
			for (int i = 1; i < 3 && ++pos < end_pos; i++) {
				c = str.charAt(pos);
				if (c < '0' || c > '7') {
					break;
				}
				ret[0]++;
				code *= 8;
				code += c - '0';
			}
			return (char) code;
		}
		case 'b':
			return '\b';
		case 'f':
			return '\f';
		case 'n':
			return '\n';
		case 'r':
			return '\r';
		case 't':
			return '\t';
		case 'u': {
			long code = 0;
			for (int i = 0; i < 4 && ++pos < end_pos; i++) {
				c = str.charAt(pos);
				if ('0' <= c && c <= '9') {
					ret[0]++;
					code *= 16;
					code += c - '0';
				} else if ('a' <= c && c <= 'f') {
					ret[0]++;
					code *= 16;
					code += c - 'a' + 10;
				} else if ('A' <= c && c <= 'F') {
					ret[0]++;
					code *= 16;
					code += c - 'A' + 10;
				} else {
					break;
				}
			}
			return (char) code;
		}
		default:
			// その他の文字は、文字そのものを返すことにする
			return c;
		}

	}
}
