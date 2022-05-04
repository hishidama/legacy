package jp.hishidama.ant.types.htlex;

import jp.hishidama.html.HtmlEscape;

import org.apache.tools.ant.types.EnumeratedAttribute;

/**
 * HTMLエスケープ方法クラス.
 *<p>
 * HTMLエスケープの方法を管理するクラス。
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/ant/htlex.html"
 *         >ひしだま</a>
 * @since 2009.01.25
 * @version 2009.02.01
 * @see HtmlEscape
 */
public class HtmlEscapeEnum extends EnumeratedAttribute {

	/** {@value}：基本 */
	public static final String BASE = "base";
	/** {@value}：基本＋ダブルクォーテーション */
	public static final String DQ = "dq";
	/** {@value}：基本＋シングルクォーテーション */
	public static final String SQ = "sq";
	/** {@value}：全て */
	public static final String ALL = "all";

	@Override
	public String[] getValues() {
		return new String[] { BASE, DQ, SQ, ALL };
	}

	public HtmlEscapeEnum() {
	}

	public HtmlEscapeEnum(String s) {
		setValue(s);
	}

	/**
	 * @return ダブルクォーテーションを変換する場合、true
	 */
	public boolean isDq() {
		int i = getIndex();
		return (i == 1 || i == 3);
	}

	/**
	 * @return シングルクォーテーションを変換する場合、true
	 */
	public boolean isSq() {
		int i = getIndex();
		return (i == 2 || i == 3);
	}
}
