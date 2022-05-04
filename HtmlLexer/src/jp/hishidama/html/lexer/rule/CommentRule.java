package jp.hishidama.html.lexer.rule;

import java.io.IOException;

import jp.hishidama.html.lexer.reader.Char;
import jp.hishidama.html.lexer.reader.CharType;
import jp.hishidama.html.lexer.token.Comment;
import jp.hishidama.html.lexer.token.TextToken;

/**
 * HtHtmlLexer���[���i�R�����g�j.
 *<p>
 * �}�[�N�A�b�v�i�^�O��錾�E�R�����g���j�̉��߂��s���B
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/htlexer.html"
 *         >�Ђ�����</a>
 * @since 2009.01.10
 */
public class CommentRule extends MarkupRule {

	protected CommentRule(HtLexer data) {
		super(data);
	}

	protected TextRule textRule;

	protected void setTextRule(TextRule rule) {
		textRule = rule;
		textRule.setSepRule(CMT_SEP_RULE);
	}

	protected class CommentSepRule implements SeparateRule {

		@Override
		public boolean isEnd(Char ch) throws IOException {
			if (ch.getType() == CharType.HYPHEN) {
				Char n = data.readChar();
				try {
					if (n.getType() == CharType.HYPHEN) {
						Char n2 = data.readChar();
						data.unreadChar(n2);
						if (n2.getType() == CharType.TAGC) {
							return true;
						}
					}
				} finally {
					data.unreadChar(n);
				}
			}
			return false;
		}
	}

	protected final SeparateRule CMT_SEP_RULE = createSepRule();

	protected SeparateRule createSepRule() {
		return new CommentSepRule();
	}

	private static final CharType START_TYPE[] = { CharType.TAGO,
			CharType.EXCL, CharType.HYPHEN, CharType.HYPHEN };

	/**
	 * �R�����g�J�n���f.
	 *
	 * @param cs
	 *            ������
	 * @return �R�����g���n�܂镶����̂Ƃ��Atrue
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
	 * @deprecated ���ߑΏۂ��R�����g�ȊO�̏ꍇ�ɗ�O����������B
	 * @see MarkupRule
	 */
	@Override
	@Deprecated
	public Comment parse() throws IOException {
		return (Comment) super.parse();
	}

	protected Comment parse(Chars cs) throws IOException {
		StringBuilder tago = cs.clear(START_TYPE.length);

		Comment tag = createCommentToken();
		tag.setTag1(tago);

		parseText(tag);
		parseTagC(tag);

		return tag;
	}

	protected Comment createCommentToken() {
		return new Comment();
	}

	protected void parseText(Comment tag) throws IOException {
		TextToken text = textRule.parse();
		tag.setComment(text);
	}
}
