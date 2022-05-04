package jp.hishidama.html.lexer.rule;

import java.io.IOException;

import jp.hishidama.html.lexer.reader.Char;
import jp.hishidama.html.lexer.token.NameAtom;

/**
 * HtHtmlLexerルール（名前）.
 *<p>
 * 名前の解釈を行う。
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/htlexer.html"
 *         >ひしだま</a>
 * @since 2009.01.01
 */
public class NameRule extends HtLexerRule {

	protected NameRule(HtLexer data) {
		super(data);
	}

	@Override
	public NameAtom parse() throws IOException {
		Char ch = data.readChar();
		return parseString(ch);
	}

	protected NameAtom parseString(Char ch) throws IOException {
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

		NameAtom name = null;
		if (sb != null) {
			name = createNameAtom();
			name.setName(sb);
		}
		return name;
	}

	protected NameAtom createNameAtom() {
		return new NameAtom();
	}
}
