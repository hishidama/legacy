package jp.hishidama.ant.types.htlex;

import java.util.ArrayList;
import java.util.List;

import jp.hishidama.html.lexer.token.Tag;
import jp.hishidama.html.parser.elem.HtElement;
import jp.hishidama.html.parser.elem.HtElementUtil;
import jp.hishidama.html.parser.elem.HtTagElement;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.types.DataType;

/**
 * HtHtmlLexerタグ変換タイプ.
 *<p>
 * HTMLファイル内のタグの変換を行うデータタイプ。
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/ant/htlex.html"
 *         >ひしだま</a>
 * @since 2009.01.20
 * @version 2009.02.18
 */
public class TagNewType extends DataType {

	protected boolean replenish;

	/**
	 * タグ補完設定.
	 *
	 * @param b
	 *            trueの場合、ペアとなるタグが省略されている場合に補完する
	 * @since 2009.02.18
	 */
	public void setReplenish(boolean b) {
		replenish = b;
	}

	protected String newName;

	/**
	 * 変換名前設定.
	 *
	 * @param name
	 *            名前
	 */
	public void setNewName(String name) {
		newName = name;
	}

	protected CaseEnum nameCase;

	/**
	 * 名前ケース変換設定.
	 *
	 * @param c
	 *            変換方法
	 */
	public void setNewNameCase(CaseEnum c) {
		nameCase = c;
	}

	protected boolean newNamePair;

	/**
	 * ペアタグ名変更設定.
	 *
	 * @param b
	 *            trueの場合、ペアとなるタグの名前も変更する
	 * @since 2009.02.18
	 */
	public void setNewNamePair(boolean b) {
		newNamePair = b;
	}

	protected String newTago, newTagc;

	/**
	 * 変換タグ開き設定.
	 *
	 * @param s
	 *            タグ開き
	 */
	public void setNewTagO(String s) {
		newTago = s;
	}

	/**
	 * 変換タグ閉じ設定.
	 *
	 * @param s
	 *            タグ閉じ
	 */
	public void setNewTagC(String s) {
		newTagc = s;
	}

	protected List<AttrOpeType> attrList = new ArrayList<AttrOpeType>();

	/**
	 * 属性変換タイプ追加.
	 *
	 * @param attr
	 *            属性変換タイプ
	 */
	public void addConfigured(AttrOpeType attr) {
		attrList.add(attr);
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

		if (replenish || newNamePair) {
			converter.setUseParser(true);
		}

		for (AttrOpeType a : attrList) {
			a.initHtLexerConverter(conv);
			a.validate();
		}
	}

	/**
	 * タグ変換実行.
	 *
	 * @param tag
	 *            タグ
	 * @param he
	 *            タグの属している要素（解析されていない場合、null）
	 * @return 内容を変更した場合、true
	 */
	public boolean convert(Tag tag, HtElement he) {
		boolean ret = false;

		ret |= convertReplenish(tag, he);
		ret |= convertName(tag, he);
		ret |= convertTagN(tag, he);
		if (ret) {
			converter.logConvert("tag", tag.getLine(), tag);
		}

		for (AttrOpeType a : attrList) {
			ret |= a.convert(tag);
		}
		return ret;
	}

	protected boolean convertReplenish(Tag tag, HtElement he) {
		if (!replenish) {
			return false;
		}
		if (tag.isStart() && tag.isEnd()) {
			return false;
		}
		if (!(he instanceof HtTagElement)) {
			return false;
		}

		boolean ret = false;

		HtTagElement te = (HtTagElement) he;
		Tag stag = te.getStartTag();
		Tag etag = te.getEndTag();
		if (stag == tag && etag == null) {
			Tag ntag = new Tag();
			ntag.setTag1(new StringBuilder("</"));
			ntag.setName(tag.getName());
			ntag.setTag2(new StringBuilder(">"));
			te.setEndTag(ntag);
			HtElementUtil.splitLastSkip(te);
			ret = true;
		}
		if (etag == tag && stag == null) {
			Tag ntag = new Tag();
			ntag.setTag1(new StringBuilder("<"));
			ntag.setName(tag.getName());
			ntag.setTag2(new StringBuilder(">"));
			te.setStartTag(ntag);
			HtElementUtil.splitFirstSkip(te);
			ret = true;
		}

		return ret;
	}

	protected boolean convertName(Tag tag, HtElement he) {
		if (newName == null && nameCase == null) {
			return false;
		}

		String o = tag.getName();
		String n = o;
		if (newName != null) {
			n = newName;
		}
		if (nameCase != null) {
			n = nameCase.convert(n);
		}
		boolean ret = false;
		if (!converter.equals(n, o)) {
			tag.setName(n);
			ret = true;
		}
		ret |= convertNamePair(tag, he);
		return ret;
	}

	protected boolean convertNamePair(Tag tag, HtElement he) {
		if (!newNamePair) {
			return false;
		}
		if (tag.isStart() && tag.isEnd()) {
			return false;
		}
		if (!(he instanceof HtTagElement)) {
			return false;
		}

		HtTagElement te = (HtTagElement) he;
		Tag stag = te.getStartTag();
		Tag etag = te.getEndTag();
		if (stag == tag && etag != null) {
			String o = etag.getName();
			String n = tag.getName();
			if (!converter.equals(n, o)) {
				etag.setName(n);
				return true;
			}
		} else if (etag == tag && stag != null) {
			String o = stag.getName();
			String n = tag.getName();
			if (!converter.equals(n, o)) {
				stag.setName(n);
				return true;
			}
		}
		return false;
	}

	protected boolean convertTagN(Tag tag, HtElement he) {
		boolean ret = false;

		if (newTago != null) {
			String o = tag.getTag1();
			String n = newTago;
			if (!converter.equals(n, o)) {
				tag.setTag1(new StringBuilder(n));
				ret |= true;
			}
		}
		if (newTagc != null) {
			String o = tag.getTag2();
			String n = newTagc;
			if (!converter.equals(n, o)) {
				tag.setTag2(new StringBuilder(n));
				ret |= true;
			}
		}

		return ret;
	}
}
