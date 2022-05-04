package jp.hishidama.html.lexer.rule;

import java.io.IOException;

import jp.hishidama.html.lexer.reader.Char;
import jp.hishidama.html.lexer.reader.CharType;
import jp.hishidama.html.lexer.token.CrLfAtom;
import jp.hishidama.html.lexer.token.TextToken;
import jp.hishidama.html.lexer.token.ValueToken;
import jp.hishidama.html.lexer.token.WordAtom;

/**
 * HtHtmlLexerルール（値）.
 *<p>
 * 値（クォーテーションで囲まれている文字列と囲まれていない文字列）の解釈を行う。
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/htlexer.html"
 *         >ひしだま</a>
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
	 * 値解釈.
	 * <p>
	 * クォーテーションが無い場合の解釈を行う。
	 * </p>
	 *
	 * @param ch
	 *            属性値の先頭1文字
	 * @return 値トークン
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
	 * 値解釈.
	 * <p>
	 * クォーテーションがある場合の解釈を行う。
	 * </p>
	 *
	 * @param ch
	 *            属性値の先頭1文字
	 * @return 値トークン
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
	 * 属性値解釈.
	 *<p>
	 * 文字列が全て属性値であるものとして解釈を行う。<br>
	 * 属性値を囲むクォーテーションは引数で渡す為、文字列内部にダブルクォーテーション・シングルクォーテーションがあっても属性値の一部となる。
	 * </p>
	 *
	 * @param quote1
	 *            先頭クォーテーション
	 * @param quote2
	 *            末尾クォーテーション
	 * @return 値トークン
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
