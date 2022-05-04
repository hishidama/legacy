package jp.hishidama.html.lexer.rule;

import java.io.IOException;

import jp.hishidama.html.lexer.reader.Char;
import jp.hishidama.html.lexer.reader.CharType;
import jp.hishidama.html.lexer.token.CrLfAtom;
import jp.hishidama.html.lexer.token.TextToken;
import jp.hishidama.html.lexer.token.ValueToken;
import jp.hishidama.html.lexer.token.WordAtom;

/**
 * HtHtmlLexer���[���i�l�j.
 *<p>
 * �l�i�N�H�[�e�[�V�����ň͂܂�Ă��镶����ƈ͂܂�Ă��Ȃ�������j�̉��߂��s���B
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/htlexer.html"
 *         >�Ђ�����</a>
 * @since 2009.01.01
 */
public class ValueRule extends HtLexerRule {

	protected ValueRule(HtLexer data) {
		super(data);
	}

	@Override
	public ValueToken parse() throws IOException {
		Char ch = data.readChar();
		if (ch == Char.EOF) {
			return null;
		}
		switch (ch.getType()) {
		case DQ:
		case SQ:
			return parseQuote(ch);
		default:
			return parseString(ch);
		}
	}

	protected ValueToken createValueToken() {
		return new ValueToken();
	}

	/**
	 * �l����.
	 * <p>
	 * �N�H�[�e�[�V�����������ꍇ�̉��߂��s���B
	 * </p>
	 *
	 * @param ch
	 *            �����l�̐擪1����
	 * @return �l�g�[�N��
	 * @throws IOException
	 */
	protected ValueToken parseString(Char ch) throws IOException {
		StringBuilder sb = null;
		for (; ch != Char.EOF; ch = data.readChar()) {
			if (parseEnd(ch)) {
				data.unreadChar(ch);
				break;
			}
			if (sb == null) {
				sb = new StringBuilder();
			}
			sb.append(ch.getSB());
		}

		ValueToken token = null;
		if (sb != null) {
			WordAtom str = new WordAtom(sb);
			TextToken text = new TextToken();
			text.add(str);

			token = createValueToken();
			token.setValue(text);
		}
		return token;
	}

	/**
	 * �l����.
	 * <p>
	 * �N�H�[�e�[�V����������ꍇ�̉��߂��s���B
	 * </p>
	 *
	 * @param ch
	 *            �����l�̐擪1����
	 * @return �l�g�[�N��
	 * @throws IOException
	 */
	protected ValueToken parseQuote(Char ch) throws IOException {
		ValueToken token = createValueToken();
		token.setQuote1(ch.getSB());
		CharType q = ch.getType();

		StringBuilder sb = null;
		TextToken text = null;
		for (ch = data.readChar(); ch != Char.EOF; ch = data.readChar()) {
			CharType tt = ch.getType();
			if (tt == q) {
				token.setQuote2(ch.getSB());
				break;
			}
			if (tt == CharType.EOL) {
				if (text == null) {
					text = new TextToken();
				}
				if (sb != null) {
					WordAtom str = new WordAtom(sb);
					text.add(str);
					sb = null;
				}
				CrLfAtom eol = new CrLfAtom(ch.getSB());
				text.add(eol);
			} else {
				if (sb == null) {
					sb = new StringBuilder();
				}
				sb.append(ch.getSB());
			}
		}

		if (text == null && sb == null) {
			sb = new StringBuilder(0);
		}
		if (sb != null) {
			if (text == null) {
				text = new TextToken();
			}
			WordAtom str = new WordAtom(sb);
			text.add(str);
		}
		token.setValue(text);

		return token;
	}

	/**
	 * �����l����.
	 *<p>
	 * �����񂪑S�đ����l�ł�����̂Ƃ��ĉ��߂��s���B<br>
	 * �����l���͂ރN�H�[�e�[�V�����͈����œn���ׁA����������Ƀ_�u���N�H�[�e�[�V�����E�V���O���N�H�[�e�[�V�����������Ă������l�̈ꕔ�ƂȂ�B
	 * </p>
	 *
	 * @param quote1
	 *            �擪�N�H�[�e�[�V����
	 * @param quote2
	 *            �����N�H�[�e�[�V����
	 * @return �l�g�[�N��
	 * @throws IOException
	 */
	public ValueToken parseAll(String quote1, String quote2) throws IOException {
		ValueToken token = createValueToken();
		if (quote1 != null) {
			token.setQuote1(quote1);
		}

		StringBuilder sb = null;
		TextToken text = null;
		for (Char ch = data.readChar(); ch != Char.EOF; ch = data.readChar()) {
			CharType tt = ch.getType();
			if (tt == CharType.EOL) {
				if (text == null) {
					text = new TextToken();
				}
				if (sb != null) {
					WordAtom str = new WordAtom(sb);
					text.add(str);
					sb = null;
				}
				CrLfAtom eol = new CrLfAtom(ch.getSB());
				text.add(eol);
			} else {
				if (sb == null) {
					sb = new StringBuilder();
				}
				sb.append(ch.getSB());
			}
		}

		if (text == null && sb == null) {
			sb = new StringBuilder(0);
		}
		if (sb != null) {
			if (text == null) {
				text = new TextToken();
			}
			WordAtom str = new WordAtom(sb);
			text.add(str);
		}
		token.setValue(text);

		if (quote2 != null) {
			token.setQuote2(quote2);
		}
		return token;
	}
}
