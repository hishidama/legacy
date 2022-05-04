package jp.hishidama.html.lexer.token;

import java.io.IOException;
import java.io.Writer;

/**
 * HtHtmlLexerトークン.
 * <p>
 * HTMLのタグ・属性・テキストを表すクラス。
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/htlexer.html"
 *         >ひしだま</a>
 * @since 2009.01.10
 */
public abstract class Token implements Cloneable {

	protected int line;

	/**
	 * @since 2009.02.07
	 */
	@Override
	public abstract Token clone() throws CloneNotSupportedException;

	protected Token(Token o) {
		this.line = o.line;
	}

	protected Token() {
	}

	/**
	 * 文字列取得.
	 *<p>
	 * 保持しているトークンをStringにして返す。
	 * </p>
	 *
	 * @return 文字列（必ずnull以外）
	 * @see #writeTo(StringBuilder)
	 */
	public final String getText() {
		StringBuilder sb = new StringBuilder(getTextLength());
		writeTo(sb);
		return sb.toString();
	}

	/**
	 * 文字列長取得.
	 *
	 * @return 文字数
	 */
	public abstract int getTextLength();

	/**
	 * 文字列出力.
	 *<p>
	 * 保持しているトークンを文字列にして出力する。
	 * </p>
	 *
	 * @param sb
	 *            出力先バッファー
	 */
	public abstract void writeTo(StringBuilder sb);

	/**
	 * 文字列出力.
	 * <p>
	 * 保持しているトークンを文字列にしてWriterに出力する。
	 * </p>
	 *
	 * @param w
	 *            Writer
	 * @throws IOException
	 */
	public abstract void writeTo(Writer w) throws IOException;

	/**
	 * 行番号算出.
	 *
	 * @param n
	 *            開始行番号
	 * @return 次のトークンが始まる行番号
	 */
	public int calcLine(int n) {
		line = n;
		return n;
	}

	/**
	 * 行番号取得.
	 * <p>
	 * {@link #calcLine(int)}によってセットされた後でないと意味を持たない。
	 * </p>
	 *
	 * @return 行番号
	 */
	public int getLine() {
		return line;
	}
}
