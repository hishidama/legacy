package jp.hishidama.sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * SQL生成補助クラス.
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/sql.html"
 *         >ひしだま</a>
 * @since 2007.09.14
 */
public class SqlUtil {

	/**
	 * insert用項目値付加.
	 *
	 * @param cb
	 *            項目用StringBuffer
	 * @param vb
	 *            値用StringBuffer
	 * @param col
	 *            カラム名
	 * @param val
	 *            値
	 * @param quote
	 *            true：値をシングルクォートでくくる
	 */
	public static void append(StringBuffer cb, StringBuffer vb, String col,
			int val, boolean quote) {
		append(cb, vb, col, Integer.toString(val), quote);
	}

	/**
	 * insert用項目値付加.
	 *
	 * @param cb
	 *            項目用StringBuffer
	 * @param vb
	 *            値用StringBuffer
	 * @param col
	 *            カラム名
	 * @param val
	 *            値
	 * @param quote
	 *            true：値をシングルクォートでくくる
	 */
	public static void append(StringBuffer cb, StringBuffer vb, String col,
			String val, boolean quote) {
		if (cb.length() != 0) {
			cb.append(',');
			vb.append(',');
		}

		cb.append(col);

		if (quote)
			vb.append('\'');
		vb.append(val);
		if (quote)
			vb.append('\'');
	}

	/**
	 * update用項目値追加.
	 *
	 * @param sb
	 *            StringBuffer
	 * @param col
	 *            カラム名
	 * @param val
	 *            値
	 * @param quote
	 *            true：値をシングルクォートでくくる
	 */
	public static void append(StringBuffer sb, String col, int val,
			boolean quote) {
		append(sb, col, Integer.toString(val), quote);
	}

	/**
	 * update用項目値追加.
	 *
	 * @param sb
	 *            StringBuffer
	 * @param col
	 *            カラム名
	 * @param val
	 *            値
	 * @param quote
	 *            true：値をシングルクォートでくくる
	 */
	public static void append(StringBuffer sb, String col, String val,
			boolean quote) {
		if (!endsTrimWith(sb, "set")) {
			sb.append(',');
		}
		sb.append(col);
		sb.append('=');
		if (quote)
			sb.append('\'');
		sb.append(val);
		if (quote)
			sb.append('\'');
	}

	public static boolean endsTrimWith(StringBuffer sb, String str) {

		int i = sb.length() - 1;
		for (; i >= 0; i--) {
			char c = sb.charAt(i);
			if (!isSpace(c)) {
				break;
			}

		}

		int j = str.length() - 1;
		for (; j >= 0 && i >= 0; j--, i--) {
			char c = Character.toLowerCase(sb.charAt(i));
			char d = Character.toLowerCase(str.charAt(j));
			if (c != d)
				return false;
		}

		if (j != -1)
			return false;
		if (i < 0)
			return true;
		if (isSpace(sb.charAt(i)))
			return true;
		return false;
	}

	public static boolean isSpace(char c) {
		return c == ' ' || c == '\t' || c == '\r' || c == '\n';
	}

	/**
	 * 更新SQL実行.
	 *
	 * @param conn
	 *            コネクション
	 * @param sql
	 *            SQL
	 * @return 更新結果
	 */
	public static int execute(Connection conn, String sql) {
		Statement stat = null;
		try {
			stat = conn.createStatement();
			return stat.executeUpdate(sql);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (stat != null) {
				try {
					stat.close();
				} catch (SQLException e) {
				}
			}
		}
	}

}
