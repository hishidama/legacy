package jp.hishidama.html.lexer.token;

import java.util.ArrayList;
import java.util.List;

/**
 * HtHtmlLexer�g�[�N���i�}�[�N�錾�j.
 *<p>
 * �}�[�N�錾�i{@literal <!DOCTYPE�`>}�j��ێ�����g�[�N���B
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/htlexer.html"
 *         >�Ђ�����</a>
 * @since 2009.01.10
 */
public class MarkDeclare extends NamedMarkup {

	/**
	 * @since 2009.02.07
	 */
	@Override
	public MarkDeclare clone() throws CloneNotSupportedException {
		return new MarkDeclare(this);
	}

	protected MarkDeclare(MarkDeclare o) {
		super(o);
	}

	protected static enum Pos {
		TAGO, NAME,
		// VALUE,
		// TAGC,
	}

	/**
	 * �R���X�g���N�^�[.
	 */
	public MarkDeclare() {
	}

	/**
	 * �l�ǉ�.
	 *
	 * @param value
	 *            �l
	 */
	public void addValue(ValueToken value) {
		addBeforeTagC(value);
	}

	/**
	 * �l�ꗗ�擾.
	 *
	 * @return �l�ꗗ�i�K��null�ȊO�j
	 */
	public List<ValueToken> getValueList() {
		List<ValueToken> ret = new ArrayList<ValueToken>(list.size());
		for (Token token : list) {
			if (token instanceof ValueToken) {
				ret.add((ValueToken) token);
			}
		}
		return ret;
	}
}
