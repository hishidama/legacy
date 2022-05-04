/**
 *
 */
package jp.hishidama.ant.types.htlex;

import jp.hishidama.html.lexer.token.Tag;
import jp.hishidama.html.parser.elem.HtElement;
import jp.hishidama.html.parser.elem.HtTagElement;

import org.apache.tools.ant.types.EnumeratedAttribute;

/**
 * タグペア条件クラス.
 *<p>
 * タグのペア条件を判定するクラス。
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/ant/htlex.html"
 *         >ひしだま</a>
 * @since 2009.02.17
 */
public class PairTagEnum extends EnumeratedAttribute {
	/**
	 * ignore：ペア条件を判定しない（デフォルト）
	 */
	public static final String IGNORE = "ignore";
	protected static final int IGNORE_INDEX = 0;
	/**
	 * stag：開始タグ（&lt;tag&gt;）の場合、真
	 */
	public static final String STAG = "stag";
	protected static final int STAG_INDEX = IGNORE_INDEX + 1;
	/**
	 * etag：終了タグ（&lt;/tag&gt;）の場合、真
	 */
	public static final String ETAG = "etag";
	protected static final int ETAG_INDEX = STAG_INDEX + 1;
	/**
	 * setag：開始終了タグ（&lt;tag/&gt;）の場合、真
	 */
	public static final String SETAG = "setag";
	protected static final int SETAG_INDEX = ETAG_INDEX + 1;
	/**
	 * start：要素の開始（&lt;tag&gt;や&lt;tag/&gt;）の場合、真
	 */
	public static final String START = "start";
	protected static final int START_INDEX = SETAG_INDEX + 1;
	/**
	 * end：要素の終了（&lt;/tag&gt;や&lt;tag/&gt;）の場合、真
	 */
	public static final String END = "end";
	protected static final int END_INDEX = START_INDEX + 1;
	/**
	 * pair：ペアがある場合、真
	 */
	public static final String PAIR = "pair";
	protected static final int PAIR_INDEX = END_INDEX + 1;
	/**
	 * nopair：ペアが無い場合、真
	 */
	public static final String NO_PAIR = "nopair";
	protected static final int NOPAIR_INDEX = PAIR_INDEX + 1;

	@Override
	public String[] getValues() {
		return new String[] { IGNORE, STAG, ETAG, SETAG, START, END, PAIR,
				NO_PAIR };
	}

	public PairTagEnum() {
	}

	public PairTagEnum(String s) {
		setValue(s);
	}

	/**
	 * HtHtmlParserを使用するインデックスの最低値
	 */
	public static final int PARSER_INDEX = PAIR_INDEX;

	/**
	 * タグのペア判定.
	 *
	 * @param tag
	 *            タグ
	 * @param he
	 *            要素（HtHtmlParserを使用して判定する場合は必須）
	 * @return 判定条件に一致した場合、true
	 */
	public boolean matches(Tag tag, HtElement he) {
		switch (getIndex()) {
		default:
			return true;
		case STAG_INDEX:
			return "<".equals(tag.getTag1()) && ">".equals(tag.getTag2());
		case ETAG_INDEX:
			return "</".equals(tag.getTag1()) && ">".equals(tag.getTag2());
		case SETAG_INDEX:
			return "<".equals(tag.getTag1()) && "/>".equals(tag.getTag2());
		case START_INDEX:
			return "<".equals(tag.getTag1());
		case END_INDEX:
			return "</".equals(tag.getTag1()) || "/>".equals(tag.getTag2());
		case PAIR_INDEX:
		case NOPAIR_INDEX:
			boolean ret;
			if ("/>".equals(tag.getTag2())) {
				ret = true;
			} else if (he instanceof HtTagElement) {
				HtTagElement te = (HtTagElement) he;
				ret = (te.getStartTag() != null) && (te.getEndTag() != null);
			} else {
				ret = false;
			}
			if (getIndex() == NOPAIR_INDEX) {
				ret = !ret;
			}
			return ret;
		}
	}
}