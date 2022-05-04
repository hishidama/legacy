package jp.hishidama.html.lexer.rule;

import java.io.IOException;

import jp.hishidama.html.lexer.reader.Char;
import jp.hishidama.html.lexer.reader.CharType;
import jp.hishidama.html.lexer.token.CrLfAtom;
import jp.hishidama.html.lexer.token.ScriptToken;
import jp.hishidama.html.lexer.token.WordAtom;

/**
 * HtHtmlLexerルール（スクリプトテキスト）.
 * <p>
 * スクリプト用テキスト（{@literal <script></script>で囲まれたテキスト}）の解釈を行う。
 * </p>
 *
 * @author <a target="hishidama" href= "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/htlexer.html" >ひしだま</a>
 * @since 2009.01.26
 */
public class ScriptRule extends HtLexerRule {

	protected ScriptRule(HtLexer data) {
		super(data);
		setSepRule(SCRIPT_SEP_RULE);
	}

	protected class ScriptSepRule implements SeparateRule {

		@Override
		public boolean isEnd(Char ch) throws IOException {
			if (ch.getType() != CharType.TAGO) {
				return false;
			}
			Chars cs = new Chars(ch);
			try {
				// </script>
				TagRule tagRule = data.getTagRule();
				if (tagRule.isStart(cs)) {
					if (cs.get(1).getType() == CharType.SLASH) {
						Char n = cs.get(2);
						if (n.getType() == CharType.STRING && "script".equalsIgnoreCase(n.getString())) {
							return true;
						}
					}
				}

				// <!--
				CommentRule cmtRule = data.getCommentRule();
				if (cmtRule.isStart(cs)) {
					return true;
				}

				// CDATA
				CDataRule cdtRule = data.getCDataRule();
				if (cdtRule.isStart(cs)) {
					return true;
				}
			} finally {
				cs.clear(1);
			}

			return false;
		}
	}

	protected final SeparateRule SCRIPT_SEP_RULE = createSepRule();

	protected SeparateRule createSepRule() {
		return new ScriptSepRule();
	}

	@Override
	public ScriptToken parse() throws IOException {
		ScriptToken text = null;
		StringBuilder sb = null;
		for (;;) {
			Char ch = data.readChar();
			if (ch == Char.EOF) {
				break;
			}
			if (parseEnd(ch)) {
				data.unreadChar(ch);
				break;
			}
			switch (ch.getType()) {
			case EOL:
				if (text == null) {
					text = createScriptToken();
				}
				if (sb != null) {
					WordAtom str = new WordAtom(sb);
					text.add(str);
					sb = null;
				}
				CrLfAtom eol = new CrLfAtom(ch.getSB());
				text.add(eol);
				continue;
			case SQ:
				if (text == null) {
					text = createScriptToken();
				}
				if (sb == null) {
					sb = new StringBuilder();
				}
				sb = parseSQ(text, sb, ch);
				continue;
			case DQ:
				if (text == null) {
					text = createScriptToken();
				}
				if (sb == null) {
					sb = new StringBuilder();
				}
				sb = parseDQ(text, sb, ch);
				continue;
			default:
				break;
			}
			if (sb == null) {
				sb = new StringBuilder();
			}
			sb.append(ch.getSB());
		}

		if (sb != null) {
			if (text == null) {
				text = createScriptToken();
			}
			WordAtom str = new WordAtom(sb);
			text.add(str);
		}

		return text;
	}

	protected ScriptToken createScriptToken() {
		return new ScriptToken();
	}

	protected StringBuilder parseSQ(ScriptToken text, StringBuilder sb, Char ch) throws IOException {
		sb.append(ch.getSB());
		boolean esc = false;
		for (;;) {
			ch = data.readChar();
			if (ch == Char.EOF) {
				break;
			}
			switch (ch.getType()) {
			case SQ:
				if (esc) {
					esc = false;
					break;
				}
				if (sb == null) {
					sb = new StringBuilder();
				}
				sb.append(ch.getSB());
				return sb;
			case ESC:
				esc = !esc;
				break;
			case EOL:
				esc = false;
				if (sb != null) {
					WordAtom str = new WordAtom(sb);
					text.add(str);
					sb = null;
				}
				CrLfAtom eol = new CrLfAtom(ch.getSB());
				text.add(eol);
				continue;
			default:
				esc = false;
				break;
			}
			if (sb == null) {
				sb = new StringBuilder();
			}
			sb.append(ch.getSB());
		}
		return sb;
	}

	protected StringBuilder parseDQ(ScriptToken text, StringBuilder sb, Char ch) throws IOException {
		sb.append(ch.getSB());
		boolean esc = false;
		for (;;) {
			ch = data.readChar();
			if (ch == Char.EOF) {
				break;
			}
			switch (ch.getType()) {
			case DQ:
				if (esc) {
					esc = false;
					break;
				}
				if (sb == null) {
					sb = new StringBuilder();
				}
				sb.append(ch.getSB());
				return sb;
			case ESC:
				esc = !esc;
				break;
			case EOL:
				esc = false;
				if (sb != null) {
					WordAtom str = new WordAtom(sb);
					text.add(str);
					sb = null;
				}
				CrLfAtom eol = new CrLfAtom(ch.getSB());
				text.add(eol);
				continue;
			default:
				esc = false;
				break;
			}
			if (sb == null) {
				sb = new StringBuilder();
			}
			sb.append(ch.getSB());
		}
		return sb;
	}
}
