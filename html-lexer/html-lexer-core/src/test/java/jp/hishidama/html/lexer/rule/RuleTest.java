package jp.hishidama.html.lexer.rule;

import java.io.IOException;

import jp.hishidama.html.lexer.reader.Char;

public class RuleTest {

	protected String readRest(HtLexer data) {
		StringBuilder sb = null;
		for (;;) {
			Char ch;
			try {
				ch = data.readChar();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			if (ch == Char.EOF) {
				break;
			}
			if (sb == null) {
				sb = new StringBuilder();
			}
			sb.append(ch.getSB());
		}
		if (sb == null) {
			return null;
		}
		return sb.toString();
	}
}
