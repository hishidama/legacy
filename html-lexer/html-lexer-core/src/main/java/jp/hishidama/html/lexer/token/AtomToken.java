package jp.hishidama.html.lexer.token;

import java.io.IOException;
import java.io.Writer;

/**
 * HtHtmlLexerトークン（原子）.
 *<p>
 * {@link Token HtHtmlLexerトークン}のうち、内部構造を持たないトークン。
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/htlexer.html"
 *         >ひしだま</a>
 * @since 2009.01.10
 * @see ListToken
 */
public class AtomToken extends Token {

	protected StringBuilder atom;

	/**
	 * @since 2009.02.07
	 */
	@Override
	public AtomToken clone() throws CloneNotSupportedException {
		return new AtomToken(this);
	}

	protected AtomToken(AtomToken o) {
		super(o);
		this.atom = new StringBuilder(o.atom);
	}

	protected AtomToken() {
	}

	protected AtomToken(StringBuilder sb) {
		setAtom(sb);
	}

	protected AtomToken(String s) {
		setAtom(s);
	}

	protected void setAtom(StringBuilder sb) {
		this.atom = sb;
	}

	protected void setAtom(String s) {
		if (s == null) {
			atom = null;
			return;
		}
		if (atom == null) {
			atom = new StringBuilder(s);
		} else {
			atom.setLength(0);
			atom.append(s);
		}
	}

	protected String getAtom() {
		if (atom != null) {
			return atom.toString();
		}
		return null;
	}

	@Override
	public int getTextLength() {
		if (atom != null) {
			return atom.length();
		}
		return 0;
	}

	@Override
	public void writeTo(StringBuilder sb) {
		if (atom != null) {
			sb.append(atom);
		}
	}

	@Override
	public void writeTo(Writer w) throws IOException {
		if (atom != null) {
			w.write(atom.toString());
		}
	}
}
