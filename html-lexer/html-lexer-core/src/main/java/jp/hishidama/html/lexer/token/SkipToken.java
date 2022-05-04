package jp.hishidama.html.lexer.token;

/**
 * HtHtmlLexerトークン（区切り文字）.
 *<p>
 * 区切り文字（{@link SpaceAtom 空白}や{@link CrLfAtom 改行}）だけを保持するトークン。
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/htlexer.html"
 *         >ひしだま</a>
 * @since 2009.01.10
 */
public final class SkipToken extends TextToken {

	/**
	 * @since 2009.02.07
	 */
	@Override
	public SkipToken clone() throws CloneNotSupportedException {
		return new SkipToken(this);
	}

	protected SkipToken(SkipToken o) {
		super(o);
	}

	/**
	 * コンストラクター.
	 */
	public SkipToken() {
	}
}
