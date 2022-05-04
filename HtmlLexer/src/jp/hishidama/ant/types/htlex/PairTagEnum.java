/**
 *
 */
package jp.hishidama.ant.types.htlex;

import jp.hishidama.html.lexer.token.Tag;
import jp.hishidama.html.parser.elem.HtElement;
import jp.hishidama.html.parser.elem.HtTagElement;

import org.apache.tools.ant.types.EnumeratedAttribute;

/**
 * �^�O�y�A�����N���X.
 *<p>
 * �^�O�̃y�A�����𔻒肷��N���X�B
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/ant/htlex.html"
 *         >�Ђ�����</a>
 * @since 2009.02.17
 */
public class PairTagEnum extends EnumeratedAttribute {
	/**
	 * ignore�F�y�A�����𔻒肵�Ȃ��i�f�t�H���g�j
	 */
	public static final String IGNORE = "ignore";
	protected static final int IGNORE_INDEX = 0;
	/**
	 * stag�F�J�n�^�O�i&lt;tag&gt;�j�̏ꍇ�A�^
	 */
	public static final String STAG = "stag";
	protected static final int STAG_INDEX = IGNORE_INDEX + 1;
	/**
	 * etag�F�I���^�O�i&lt;/tag&gt;�j�̏ꍇ�A�^
	 */
	public static final String ETAG = "etag";
	protected static final int ETAG_INDEX = STAG_INDEX + 1;
	/**
	 * setag�F�J�n�I���^�O�i&lt;tag/&gt;�j�̏ꍇ�A�^
	 */
	public static final String SETAG = "setag";
	protected static final int SETAG_INDEX = ETAG_INDEX + 1;
	/**
	 * start�F�v�f�̊J�n�i&lt;tag&gt;��&lt;tag/&gt;�j�̏ꍇ�A�^
	 */
	public static final String START = "start";
	protected static final int START_INDEX = SETAG_INDEX + 1;
	/**
	 * end�F�v�f�̏I���i&lt;/tag&gt;��&lt;tag/&gt;�j�̏ꍇ�A�^
	 */
	public static final String END = "end";
	protected static final int END_INDEX = START_INDEX + 1;
	/**
	 * pair�F�y�A������ꍇ�A�^
	 */
	public static final String PAIR = "pair";
	protected static final int PAIR_INDEX = END_INDEX + 1;
	/**
	 * nopair�F�y�A�������ꍇ�A�^
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
	 * HtHtmlParser���g�p����C���f�b�N�X�̍Œ�l
	 */
	public static final int PARSER_INDEX = PAIR_INDEX;

	/**
	 * �^�O�̃y�A����.
	 *
	 * @param tag
	 *            �^�O
	 * @param he
	 *            �v�f�iHtHtmlParser���g�p���Ĕ��肷��ꍇ�͕K�{�j
	 * @return ��������Ɉ�v�����ꍇ�Atrue
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