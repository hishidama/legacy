package jp.hishidama.html.lexer.rule;

import java.io.IOException;

import jp.hishidama.html.lexer.reader.Char;
import jp.hishidama.html.lexer.reader.CharType;
import jp.hishidama.html.lexer.token.*;

/**
 * HtHtmlLexerルール（テキスト）.
 *<p>
 * テキストの解釈を行う。
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/htlexer.html"
 *         >ひしだま</a>
 * @since 2009.01.01
 * @version 2009.02.21
 */
public class TextRule extends HtLexerRule {

	protected TextRule(HtLexer data) {
		super(data);
	}

	@Override
	public TextToken parse() throws IOException {
		TextToken text = null;
		StringBuilder sb = null;
		for (Char ch = data.readChar(); ch != Char.EOF; ch = data.readChar()) {
			if (parseEnd(ch)) {
				data.unreadChar(ch);
				break;
			}

			switch (ch.getType()) {
			case EOL:
			case SPACE:
				if (text == null) {
					text = new TextToken();
				}
				if (sb != null) {
					WordAtom str = new WordAtom(sb);
					text.add(str);
					sb = null;
				}
				WordAtom wa;
				if (ch.getType() == CharType.EOL) {
					wa = new CrLfAtom(ch.getSB());
				} else {
					wa = new SpaceAtom(ch.getSB());
				}
				text.add(wa);
				break;
			default:
				if (sb == null) {
					sb = new StringBuilder();
				}
				sb.append(ch.getSB());
			}
		}

		if (sb != null) {
			if (text == null) {
				text = new TextToken();
			}
			WordAtom str = new WordAtom(sb);
			text.add(str);
		}

		return text;
	}
}
