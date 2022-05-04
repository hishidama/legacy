package jp.hishidama.html.lexer.token;

/**
 * HtHtmlLexerトークン（スクリプトテキスト）.
 *<p>
 * スクリプト用テキスト（{@literal <script></script>で囲まれたテキスト}）を意味するトークン。
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/htlexer.html"
 *         >ひしだま</a>
 * @since 2009.01.28
 */
public class ScriptToken extends TextToken {

	/**
	 * @since 2009.02.07
	 */
	@Override
	public ScriptToken clone() throws CloneNotSupportedException {
		return new ScriptToken(this);
	}

	protected ScriptToken(ScriptToken o) {
		super(o);
	}

	/**
	 * コンストラクター.
	 */
	public ScriptToken() {
	}
}
