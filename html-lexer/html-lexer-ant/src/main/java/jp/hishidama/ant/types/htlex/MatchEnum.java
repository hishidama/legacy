package jp.hishidama.ant.types.htlex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tools.ant.types.EnumeratedAttribute;

/**
 * 文字列マッチングクラス.
 *<p>
 * 文字列の比較を行うクラス。
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/ant/htlex.html"
 *         >ひしだま</a>
 * @since 2009.01.23
 * @version 2009.02.01
 */
public class MatchEnum extends EnumeratedAttribute {

	/**
	 * {@value}：大文字小文字を無視して比較
	 */
	public static final String IGNORE_CASE = "ignorecase";
	/**
	 * {@value}：単純比較
	 */
	public static final String EQUALS = "equals";
	/**
	 * {@value}：正規表現で検索
	 *
	 * @see Matcher#find()
	 */
	public static final String FIND = "find";
	/**
	 * {@value}：正規表現で一致
	 *
	 * @see Matcher#matches()
	 */
	public static final String MATCHES = "matches";

	@Override
	public String[] getValues() {
		return new String[] { IGNORE_CASE, EQUALS, FIND, MATCHES };
	}

	public MatchEnum() {
	}

	public MatchEnum(String s) {
		setValue(s);
	}

	private String comp = "";
	private Pattern pattern;

	/**
	 * 比較文字列設定.
	 *
	 * @param comp
	 *            比較元文字列
	 */
	public void setPattern(String comp) {
		if (comp == null) {
			comp = "";
		}
		this.comp = comp;
		switch (getIndex()) {
		case 2:
		case 3:
			pattern = Pattern.compile(comp);
			break;
		}
	}

	/**
	 * 比較文字列取得.
	 *
	 * @return 比較元文字列
	 */
	public String getPatternString() {
		return comp;
	}

	/**
	 * 比較パターン取得.
	 *
	 * @return 比較パターン（正規表現の場合のみ有効）
	 */
	public Pattern getPattern() {
		return pattern;
	}

	/**
	 * マッチング判断.
	 *
	 * @param s
	 *            比較対象文字列（nullの場合は空文字列として判断する）
	 * @return 指定されていた条件にマッチした場合、true
	 */
	public boolean matches(String s) {
		if (s == null) {
			s = "";
		}
		switch (getIndex()) {
		default: // ignore case
			return comp.equalsIgnoreCase(s);
		case 1: // equals
			return comp.equals(s);
		case 2: // find
			return pattern.matcher(s).find();
		case 3: // matches
			return pattern.matcher(s).matches();
		}
	}
}
