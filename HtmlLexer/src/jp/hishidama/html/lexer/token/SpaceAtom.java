package jp.hishidama.html.lexer.token;

/**
 * HtHtmlLexerトークン（空白）.
 *<p>
 * 空白文字（スペース・タブ）のみを保持するトークン。
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/htlexer.html"
 *         >ひしだま</a>
 * @since 2009.01.10
 */
public final class SpaceAtom extends WordAtom {

	/**
	 * @since 2009.02.07
	 */
	@Override
	public SpaceAtom clone() throws CloneNotSupportedException {
		return new SpaceAtom(this);
	}

	protected SpaceAtom(SpaceAtom o) {
		super(o);
	}

	/**
	 * コンストラクター.
	 *
	 * @param sb
	 *            空白文字列
	 */
	public SpaceAtom(StringBuilder sb) {
		super(sb);
	}
}
