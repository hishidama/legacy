package jp.hishidama.ant.types.htlex;

import java.lang.reflect.Method;
import java.util.*;

import jp.hishidama.ant.types.htlex.eval.HtLexerExpRuleFactory;
import jp.hishidama.eval.Expression;
import jp.hishidama.html.lexer.token.AttributeToken;
import jp.hishidama.html.lexer.token.Tag;
import jp.hishidama.html.parser.elem.HtElement;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.taskdefs.condition.*;
import org.apache.tools.ant.types.DataType;

/**
 * HtHtmlLexerタグ条件タイプ.
 * <p>
 * HTMLファイル内のタグの条件判断を行うデータタイプ。
 * </p>
 *
 * @author <a target="hishidama" href= "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/ant/htlex.html">ひしだま</a>
 * @since 2009.01.20
 * @version 2010.01.31
 */
public class TagType extends DataType {

	protected MatchEnum nameMatch;

	/**
	 * 名前条件設定.
	 *
	 * @param name
	 *            タグ名
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

	protected String tago, tagc;

	/**
	 * タグ開き条件設定.
	 *
	 * @param s
	 *            タグ開き
	 */
	public void setTagO(String s) {
		tago = s;
	}

	/**
	 * タグ閉じ条件設定.
	 *
	 * @param s
	 *            タグ閉じ
	 */
	public void setTagC(String s) {
		tagc = s;
	}

	protected PairTagEnum pair = new PairTagEnum(PairTagEnum.IGNORE);

	/**
	 * ペア条件設定.
	 *
	 * @param p
	 *            ペア条件
	 */
	public void setPair(PairTagEnum p) {
		this.pair = p;
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

	protected Condition attrCond = null;

	/**
	 * 条件追加.
	 *
	 * @param cond
	 *            条件
	 */
	public void addConfigured(Condition cond) {
		if (attrCond == null) {
			attrCond = cond;
		} else if (attrCond instanceof And) {
			((And) attrCond).add(cond);
		} else {
			And and = new And();
			and.setProject(this.getProject());
			and.add(attrCond);
			and.add(cond);
			attrCond = and;
		}
	}

	/**
	 * And条件追加.
	 *
	 * @param cond
	 *            条件
	 */
	public void addConfiguredAnd(And cond) {
		addConfigured(cond);
	}

	/**
	 * Or条件追加.
	 *
	 * @param cond
	 *            条件
	 */
	public void addConfiguredOr(Or cond) {
		addConfigured(cond);
	}

	/**
	 * Not条件追加.
	 *
	 * @param cond
	 *            条件
	 */
	public void addConfiguredNot(Not cond) {
		addConfigured(cond);
	}

	protected List<TagNewType> tagList = new ArrayList<TagNewType>();

	/**
	 * タグ変換情報追加.
	 *
	 * @param tag
	 *            タグ変換タイプ
	 */
	public void addConfigured(TagNewType tag) {
		tagList.add(tag);
	}

	protected HtLexerConverter converter;

	/**
	 * 精査実行.
	 *
	 * @param conv
	 *            コンバーター
	 * @throws BuildException
	 *             精査エラー時
	 */
	public void validate(HtLexerConverter conv) throws BuildException {
		converter = conv;

		if (pair.getIndex() >= PairTagEnum.PARSER_INDEX) {
			converter.setUseParser(true);
		}

		if (attrCond != null) {
			validateCondition(attrCond, conv);
		}

		for (TagNewType tt : tagList) {
			tt.validate(conv);
		}
	}

	protected void validateCondition(Condition c, HtLexerConverter conv) throws BuildException {
		if (c == null) {
			throw new BuildException("condition must be set.", getLocation());
		}
		if (c instanceof AttrCondType) {
			AttrCondType ac = (AttrCondType) c;
			ac.initHtLexerConverter(conv);
			ac.validate(this);
			return;
		}

		// and,or,notのみ許す
		if (c instanceof And || c instanceof Or || c instanceof Not) {
			Enumeration<Condition> n = getBaseConditions(c);
			while (n.hasMoreElements()) {
				validateCondition(n.nextElement(), conv);
			}
			return;
		} else {
			throw new BuildException(c.getClass().getSimpleName() + " can not be used.", getLocation());
		}
	}

	protected Method GetConditionMethod;

	@SuppressWarnings("unchecked")
	protected Enumeration<Condition> getBaseConditions(Object obj) {
		try {
			if (GetConditionMethod == null) {
				GetConditionMethod = ConditionBase.class.getDeclaredMethod("getConditions", (Class[]) null);
				GetConditionMethod.setAccessible(true);
			}
			return (Enumeration<Condition>) GetConditionMethod.invoke(obj, (Object[]) null);
		} catch (Exception e) {
			throw new BuildException(e, getLocation());
		}
	}

	/**
	 * タグ変換.
	 *
	 * @param tag
	 *            タグ
	 * @param he
	 *            タグの属している要素（解析されていない場合、null）
	 * @return 内容を変更した場合、true
	 */
	public boolean convert(Tag tag, HtElement he) {
		converter.getPropertyHelper().pushTagToken(tag);
		try {
			boolean ret = false;
			if (matches(tag, he)) {
				converter.logMatch(tag);
				for (TagNewType tt : tagList) {
					ret |= tt.convert(tag, he);
				}
			}
			return ret;
		} finally {
			converter.getPropertyHelper().popTagToken();
		}
	}

	protected List<AttributeToken> tokenList;

	/**
	 * 処理対象属性一覧取得.
	 *
	 * @return 属性一覧
	 * @see AttrCondType#eval()
	 */
	public List<AttributeToken> getTokenList() {
		return tokenList;
	}

	/**
	 * マッチング判断.
	 *
	 * @param tag
	 *            タグ
	 * @param he
	 *            タグの属している要素（解析されていない場合、null）
	 * @return 指定されていた条件にマッチした場合、true
	 */
	public boolean matches(Tag tag, HtElement he) {
		String name = tag.getName();
		if (!equalsName(name)) {
			return false;
		}
		if (!equalsTagO(tag.getTag1())) {
			return false;
		}
		if (!equalsTagC(tag.getTag2())) {
			return false;
		}
		if (attrCond != null) {
			tokenList = tag.getAttributeList();
			if (!attrCond.eval()) {
				return false;
			}
		}
		if (!pair.matches(tag, he)) {
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

	protected boolean equalsTagO(String t) {
		if (tago == null) {
			return true;
		}
		return tago.equals(t);
	}

	protected boolean equalsTagC(String t) {
		if (tagc == null) {
			return true;
		}
		return tagc.equals(t);
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
		} catch (RuntimeException e) {
			throw new BuildException(e, getLocation());
		}
		return true;
	}
}
