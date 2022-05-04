package jp.hishidama.html.lexer.rule;

import java.io.IOException;

import jp.hishidama.html.lexer.reader.Char;
import jp.hishidama.html.lexer.reader.CharType;
import jp.hishidama.html.lexer.token.MarkDeclare;
import jp.hishidama.html.lexer.token.ValueToken;

/**
 * HtHtmlLexer���[���i�}�[�N�錾�j.
 *<p>
 * �}�[�N�錾�i{@literal <!DOCTYPE�`>}�j�̉��߂��s���B
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/htlexer.html"
 *         >�Ђ�����</a>
 * @since 2009.01.10
 */
public class MarkDeclRule extends MarkupRule {

	protected MarkDeclRule(HtLexer data) {
		super(data);
		setSepRule(DECL_SEP_RULE);
	}

	protected ValueRule valueRule;

	protected void setValueRule(ValueRule rule) {
		valueRule = rule;
		valueRule.setSepRule(DECL_SEP_RULE);
	}

	protected class DeclSepRule implements SeparateRule {

		@Override
		public boolean isEnd(Char ch) throws IOException {
			switch (ch.getType()) {
			case SPACE:
			case EOL:
			case TAGC:
				return true;
			}
			return false;
		}
	}

	protected final SeparateRule DECL_SEP_RULE = createDeclSepRule();

	protected SeparateRule createDeclSepRule() {
		return new DeclSepRule();
	}

	protected static final CharType[] START_TYPE = { CharType.TAGO,
			CharType.EXCL };

	/**
	 * �}�[�N�錾�J�n���f.
	 *
	 * @param cs
	 *            ������
	 * @return �}�[�N�錾���n�܂镶����̂Ƃ��Atrue
	 * @throws IOException
	 */
	public boolean isStart(Chars cs) throws IOException {
		return cs.isType(START_TYPE);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @throws IOException
	 * @throws ClassCastException
	 * @deprecated ���ߑΏۂ��}�[�N�錾�ȊO�̏ꍇ�ɗ�O����������B
	 * @see MarkupRule
	 */
	@Override
	@Deprecated
	public MarkDeclare parse() throws IOException {
		return (MarkDeclare) super.parse();
	}

	protected MarkDeclare parse(Chars cs) throws IOException {
		StringBuilder tago = cs.clear(START_TYPE.length);

		MarkDeclare tag = createMarkDeclareToken();
		tag.setTag1(tago);

		parseName(tag);
		parseValue(tag);
		parseTagC(tag);

		return tag;
	}

	protected MarkDeclare createMarkDeclareToken() {
		return new MarkDeclare();
	}

	protected void parseValue(MarkDeclare tag) throws IOException {
		for (;;) {
			ValueToken value = valueRule.parse();
			if (value == null) {
				break;
			}
			tag.addValue(value);
			tag.addSkip(skipSpace());
		}
	}
}
