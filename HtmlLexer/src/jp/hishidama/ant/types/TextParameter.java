package jp.hishidama.ant.types;

import org.apache.tools.ant.types.DataType;

/**
 * テキストパラメータータイプ.
 *<p>
 * ボディー部にパラメーターの値を記述する為のデータタイプ。
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/ant/htlex.html"
 *         >ひしだま</a>
 * @since 2010.02.01
 */
public class TextParameter extends DataType {

	protected String name;
	protected String value = "";

	/**
	 * 属性名設定.
	 *
	 * @param s
	 *            属性名
	 */
	public void setName(String s) {
		name = s;
	}

	/**
	 * 属性名取得.
	 *
	 * @return 属性名
	 */
	public String getName() {
		return name;
	}

	/**
	 * 値設定.
	 *
	 * @param s
	 *            値
	 */
	public void setValue(String s) {
		value += s;
	}

	/**
	 * 値取得.
	 *
	 * @return 値
	 */
	public String getValue() {
		return value;
	}

	/**
	 * 値設定（ボディー部）.
	 *
	 * @param s
	 *            値
	 */
	public void addText(String s) {
		value += s;
	}
}
