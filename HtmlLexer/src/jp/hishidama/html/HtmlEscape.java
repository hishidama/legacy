package jp.hishidama.html;

import java.util.*;

/**
 * HTMLエスケープクラス.
 * <p>
 * HTMLエスケープ（HTMLエンコード）の判定や実行を行う。
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/htlexer.html"
 *         >ひしだま</a>
 * @since 2009.01.25
 * @version 2009.02.08
 */
public class HtmlEscape {

	/**
	 * コンストラクター.
	 *<p>
	 * {@link #HtmlEscape(boolean, boolean) 引数ありコンストラクター}に全てtrueを指定した場合と同じ。
	 * </p>
	 *
	 * @see #HtmlEscape(boolean, boolean)
	 */
	public HtmlEscape() {
		this(true, true);
	}

	/**
	 * コンストラクター.
	 * <table border="1">
	 * <caption>デフォルトで指定される文字</caption>
	 * <tr>
	 * <th>文字</th>
	 * <th>変換</th>
	 * <th>備考</th>
	 * <th>version</th>
	 * </tr>
	 * <tr>
	 * <td>&amp;</td>
	 * <td>&amp;amp;</td>
	 * <td>-</td>
	 * <td>&nbsp</td>
	 * </tr>
	 * <tr>
	 * <td>&lt;</td>
	 * <td>&amp;lt;</td>
	 * <td>-</td>
	 * <td>&nbsp</td>
	 * </tr>
	 * <tr>
	 * <td>&gt;</td>
	 * <td>&amp;gt;</td>
	 * <td>-</td>
	 * <td>&nbsp</td>
	 * </tr>
	 * <tr>
	 * <td>&quot;</td>
	 * <td>&amp;quot;</td>
	 * <td>dqがtrueの場合</td>
	 * <td>&nbsp</td>
	 * </tr>
	 * <tr>
	 * <td>&#39;</td>
	 * <td>&amp;#39;</td>
	 * <td>sqがtrueの場合</td>
	 * <td>&nbsp</td>
	 * </tr>
	 * <tr>
	 * <td>&nbsp;</td>
	 * <td>&amp;nbsp;</td>
	 * <td>今のところ、変換対象外</td>
	 * <td>2009.02.08</td>
	 * </tr>
	 * <tr>
	 * <td>なし</td>
	 * <td>&amp;copy;</td>
	 * <td>変換対象外</td>
	 * <td>2009.02.08</td>
	 * </tr>
	 * <tr>
	 * <td>なし</td>
	 * <td>&amp;reg;</td>
	 * <td>変換対象外</td>
	 * <td>2009.02.08</td>
	 * </tr>
	 * </table>
	 *
	 * @param dq
	 *            trueの場合、ダブルクォーテーションの定義も含める
	 * @param sq
	 *            trueの場合、シングルクォーテーションの定義も含める
	 */
	public HtmlEscape(boolean dq, boolean sq) {
		addEntity("amp", "&");
		addEntity("lt", "<");
		addEntity("gt", ">");
		if (dq) {
			addEntity("quot", "\"");
		}
		if (sq) {
			addEntity("#39", "\'");
			// addEntity("apos", "\'");
		}

		// addEntity("nbsp", " ");
		addEntity("nbsp", null);

		addEntity("copy", null);
		addEntity("reg", null);
	}

	protected final Map<String, String> nameMap = new HashMap<String, String>();
	protected final Map<Character, String> entityMap = new HashMap<Character, String>();

	/**
	 * ENTITY追加.
	 * <p>
	 * エンティティー（文字参照の実体）を追加する。<br>
	 * 同一エンティティー（文字）になる名前は、後から追加した方が優先される。
	 * </p>
	 *
	 * @param name
	 *            名前（例：amp）
	 * @param entity
	 *            実体（例：&amp;）。必ず1文字である必要がある。<br>
	 *            nullの場合は変換対象としない。
	 */
	public void addEntity(String name, String entity) {
		nameMap.put(name, entity);
		if (entity == null) {
			return;
		}
		if (entity.length() != 1) {
			throw new IllegalArgumentException("entity.length != 1: " + entity);
		}
		entityMap.put(entity.charAt(0), "&" + name + ";");
	}

	/**
	 * ENTITY定義を全て削除する.
	 */
	public void clearEntities() {
		nameMap.clear();
		entityMap.clear();
	}

	/**
	 * HTMLエスケープ.
	 * <p>
	 * HTMLエスケープを行う。既にエスケープされている文字については変換しない。
	 * </p>
	 *
	 * @param s
	 *            文字列
	 * @return HTMLエスケープされた文字列（sがnullだった場合はnull）
	 */

	public String escape(String s) {
		if (s == null) {
			return null;
		}

		StringBuilder sb = new StringBuilder(s.length() * 2);
		for (int i = 0; i < s.length();) {
			char c = s.charAt(i);
			if (c == '&') {
				String e = escapedAmp(s, i);
				if (e != null) {
					sb.append(c);
					sb.append(e);
					sb.append(';');
					i += e.length() + 2;
					continue;
				}
			}
			String name = entityMap.get(c);
			if (name != null) {
				sb.append(name);
			} else {
				sb.append(c);
			}
			i++;
		}
		return sb.toString();
	}

	/**
	 * HTMLエスケープされている文字かどうかを判定する
	 *
	 * @param s
	 *            文字列
	 * @param pos
	 *            判定開始位置（「&amp;」がある位置）
	 * @return エスケープされている場合、その文字数。<br>
	 *         エスケープされていない場合は0
	 */
	public String escapedAmp(String s, int pos) {
		pos++;
		int semi = s.indexOf(';', pos + 1);
		if (semi < 0) {
			return null;
		}
		String t = s.substring(pos, semi);
		if (nameMap.containsKey(t)) {
			return t;
		}
		char c = t.charAt(0);
		switch (c) {
		case '#':
			if (t.length() < 2) {
				return null;
			}
			c = t.charAt(1);
			if (c == 'x' || c == 'X') {
				if (isHexDigits(t, 2)) {
					return t;
				} else {
					return null;
				}
			} else {
				if (isDigits(t, 1)) {
					return t;
				} else {
					return null;
				}
			}
		}
		return null;
	}

	/**
	 * 十進数かどうかを判定する
	 *
	 * @param s
	 *            文字列
	 * @param first
	 *            判定開始位置
	 * @return 十進数の場合、true
	 */
	public static boolean isDigits(String s, int first) {
		int len = s.length();
		for (int i = first; i < len; i++) {
			if (!isDigit(s.charAt(i))) {
				return false;
			}
		}
		return first < len;
	}

	/**
	 * 十進数文字かどうかを判定する
	 *
	 * @param c
	 *            文字
	 * @return 十進数文字の場合、true
	 */
	public static boolean isDigit(char c) {
		return ('0' <= c && c <= '9');
	}

	/**
	 * 十六進数かどうかを判定する
	 *
	 * @param s
	 *            文字列
	 * @param first
	 *            判定開始位置
	 * @return 十六進数の場合、true
	 */
	public static boolean isHexDigits(String s, int first) {
		int len = s.length();
		for (int i = first; i < len; i++) {
			if (!isHexDigit(s.charAt(i))) {
				return false;
			}
		}
		return first < len;
	}

	/**
	 * 十六進数文字かどうかを判定する
	 *
	 * @param c
	 *            文字
	 * @return 十六進数文字の場合、true
	 */
	public static boolean isHexDigit(char c) {
		return ('0' <= c && c <= '9') || ('a' <= c && c <= 'f')
				|| ('A' <= c && c <= 'F');
	}
}
