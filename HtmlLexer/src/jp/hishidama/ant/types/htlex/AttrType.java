package jp.hishidama.ant.types.htlex;

import jp.hishidama.ant.types.TextParameter;
import jp.hishidama.ant.types.htlex.eval.HtLexerExpRuleFactory;
import jp.hishidama.ant.types.htlex.eval.HtLexerPropertyHelper;
import jp.hishidama.eval.Expression;
import jp.hishidama.html.lexer.token.AttributeToken;
import jp.hishidama.html.lexer.token.ValueToken;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.IntrospectionHelper;
import org.apache.tools.ant.types.DataType;

/**
 * HtHtmlLexerタグ属性タイプ.
 *<p>
 * HTMLファイル内のタグの属性のマッチ条件を保持するデータタイプ。
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/ant/htlex.html"
 *         >ひしだま</a>
 * @since 2009.01.20
 * @version 2010.02.01
 */
public class AttrType extends DataType {

	protected MatchEnum nameMatch;

	/**
	 * 名前条件設定.
	 *
	 * @param name
	 *            属性名
	 */
	public void setName(String name) {
		if (nameMatch == null) {
			nameMatch = new MatchEnum(MatchEnum.IGNORE_CASE);
		}
		nameMatch.setPattern(name);
	}

	/**
	 * 名前マッチング方法設定.
	 *
	 * @param match
	 *            マッチング方法
	 */
	public void setNameMatch(MatchEnum match) {
		if (nameMatch != null) {
			match.setPattern(nameMatch.getPatternString());
		}
		nameMatch = match;
	}

	protected String let;

	/**
	 * 代入記号条件設定.
	 *
	 * @param s
	 *            代入記号
	 */
	public void setLet(String s) {
		let = s;
	}

	protected MatchEnum valueMatch;

	/**
	 * 属性値条件設定.
	 *
	 * @param value
	 *            属性値
	 */
	public void setValue(String value) {
		if (valueMatch == null) {
			valueMatch = new MatchEnum(MatchEnum.IGNORE_CASE);
		}
		valueMatch.setPattern(value);
	}

	/**
	 * 属性値マッチング方法設定.
	 *
	 * @param match
	 *            マッチング方法
	 */
	public void setValueMatch(MatchEnum match) {
		if (valueMatch != null) {
			match.setPattern(valueMatch.getPatternString());
		}
		valueMatch = match;
	}

	protected String quote;

	/**
	 * クォーテーション条件設定.
	 *
	 * @param s
	 *            クォーテーション
	 */
	public void setQuote(String s) {
		quote = s;
	}

	protected Expression ifExp;

	/**
	 * 条件式設定.
	 *
	 * @param s
	 *            条件式
	 * @since 2010.01.31
	 */
	public void setIf(String s) {
		ifExp = HtLexerExpRuleFactory.getRule(getProject()).parse(s);
	}

	protected String properyName;

	/**
	 * プロパティー名設定.
	 *
	 * @param name
	 *            プロパティー名
	 * @since 2010.01.23
	 */
	public void setPropertyName(String name) {
		properyName = name;
	}

	/**
	 * パラメーター設定.
	 * <p>
	 * 属性値に書きづらい長い式などをパラメーターのボディー部に記述できる。<br>
	 * {@code <attr><param name="foo">bar</param></attr>}の場合、{@code <attr
	 * foo="bar">}と同じ。
	 * </p>
	 *
	 * @param param
	 *            パラメーター
	 * @since 2010.02.01
	 */
	public void addConfiguredParam(TextParameter param) {
		String name = param.getName();
		if (name == null || name.isEmpty()) {
			throw new BuildException("name attribute must be set.", param
					.getLocation());
		}
		IntrospectionHelper ih = IntrospectionHelper.getHelper(getProject(),
				this.getClass());
		try {
			ih.setAttribute(getProject(), this, name, param.getValue());
		} catch (Exception e) {
			throw new BuildException(e, param.getLocation());
		}
	}

	protected HtLexerConverter htmlConverter;

	/**
	 * HtLexerConverter設定.
	 *
	 * @param conv
	 * @since 2010.01.23
	 */
	public void initHtLexerConverter(HtLexerConverter conv) {
		htmlConverter = conv;
	}

	protected void pushAttributeToken(AttributeToken a) {
		HtLexerPropertyHelper helper = htmlConverter.getPropertyHelper();
		if (helper != null) {
			helper.pushAttributeToken(a);
		}
	}

	protected void popAttributeToken() {
		HtLexerPropertyHelper helper = htmlConverter.getPropertyHelper();
		if (helper != null) {
			helper.popAttributeToken();
		}
	}

	/**
	 * マッチング判断.
	 *
	 * @param a
	 *            属性
	 * @return 指定されていた条件にマッチした場合、true
	 */
	public boolean matches(AttributeToken a) {
		String name = a.getName();
		if (!equalsName(name)) {
			return false;
		}

		if (!equalsLet(a.getLet())) {
			return false;
		}

		ValueToken vt = a.getValueToken();
		String v = (vt == null) ? null : vt.getValue();
		if (!equalsValue(v)) {
			return false;
		}
		String q1 = null, q2 = null;
		if (vt != null) {
			q1 = vt.getQuote1();
			q2 = vt.getQuote2();
		}
		if (!equalsQuote(q1) || !equalsQuote(q2)) {
			return false;
		}

		if (!equalsIf()) {
			return false;
		}

		return true;
	}

	protected boolean equalsName(String s) {
		if (nameMatch == null) {
			return true;
		}
		return nameMatch.matches(s);
	}

	protected boolean equalsLet(String s) {
		if (let == null) {
			return true;
		}
		if (let.isEmpty() && s == null) {
			return true;
		}
		return let.equals(s);
	}

	protected boolean equalsValue(String s) {
		if (valueMatch == null) {
			return true;
		}
		return valueMatch.matches(s);
	}

	protected boolean equalsQuote(String q) {
		if (quote == null) {
			return true;
		}
		if (quote.isEmpty() && q == null) {
			return true;
		}
		return quote.equals(q);
	}

	protected boolean equalsIf() {
		if (ifExp == null) {
			return true;
		}
		try {
			Object r = ifExp.eval();
			if (r == null) {
				return false;
			}
			if (r instanceof Boolean) {
				return ((Boolean) r).booleanValue();
			}
			if (r instanceof Number) {
				return ((Number) r).intValue() != 0;
			}
			if (r instanceof String) {
				return !((String) r).isEmpty();
			}
		} catch (BuildException e) {
			e.setLocation(getLocation());
			throw e;
		} catch (Exception e) {
			throw new BuildException(e, getLocation());
		}
		return true;
	}

	/**
	 * プロパティー設定処理.
	 *
	 * @param a
	 *            属性
	 * @since 2010.01.23
	 */
	public void doSetProperty(AttributeToken a) {
		if (properyName == null) {
			return;
		}

		ValueToken vt = a.getValueToken();
		String value = (vt == null) ? null : vt.getValue();

		if (value == null) {
			value = ""; // nullの値はセットできないので空文字列に変換
		}
		getProject().setProperty(properyName, value);
	}
}
