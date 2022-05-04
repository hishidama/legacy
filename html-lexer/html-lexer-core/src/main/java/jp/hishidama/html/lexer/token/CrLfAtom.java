package jp.hishidama.html.lexer.token;

/**
 * HtHtmlLexerトークン（改行）.
 *<p>
 * 改行１つ分を保持するトークン。
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/htlexer.html"
 *         >ひしだま</a>
 * @since 2009.01.10
 */
public final class CrLfAtom extends WordAtom {

	/**
	 * @since 2009.02.07
	 */
	@Override
	public CrLfAtom clone() throws CloneNotSupportedException {
		return new CrLfAtom(this);
	}

	protected CrLfAtom(CrLfAtom o) {
		super(o);
	}

	/**
	 * コンストラクター.
	 *
	 * @param sb
	 *            改行文字列
	 */
	public CrLfAtom(StringBuilder sb) {
		super(sb);
	}

	@Override
	public int calcLine(int n) {
		line = n;
		return n + 1;
	}
}
