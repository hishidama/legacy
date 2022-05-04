package jp.hishidama.html.parser.elem;

import java.io.*;

import jp.hishidama.html.lexer.token.ListToken;
import jp.hishidama.html.lexer.token.Tag;
import jp.hishidama.html.lexer.token.Token;

/**
 * HTML要素.
 *<p>
 * HtHtmlParserにおける、要素（の抽象クラス）。
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/htlexer.html"
 *         >ひしだま</a>
 * @since 2009.02.08
 * @version 2009.02.21
 */
public abstract class HtElement {

	protected HtListElement parent;

	/**
	 * 親要素設定.
	 *
	 * @param parent
	 *            親要素
	 * @since 2009.02.21
	 */
	public void setParent(HtListElement parent) {
		this.parent = parent;
	}

	/**
	 * 親要素取得.
	 *
	 * @return 親要素（親が無い場合、null）
	 * @since 2009.02.21
	 */
	public HtListElement getParent() {
		return parent;
	}

	protected String name;

	/**
	 * 要素名取得.
	 *
	 * @return 要素名（要素でない場合はnull）
	 */
	public String getName() {
		return name;
	}

	protected boolean fix;

	/**
	 * 確定状態設定.
	 *
	 * @param b
	 *            trueの場合、確定
	 */
	public void setFix(boolean b) {
		fix = b;
	}

	/**
	 * 確定状態取得.
	 *
	 * @return trueの場合、確定済み
	 */
	public boolean isFix() {
		return fix;
	}

	/**
	 * タグの要素かどうか.
	 *
	 * @return タグの場合、true
	 */
	public boolean isTag() {
		return false;
	}

	/**
	 * 要素の開始かどうか.
	 *
	 * @return 要素の開始の場合、true
	 */
	public boolean isStart() {
		return false;
	}

	/**
	 * 要素の終了かどうか.
	 *
	 * @return 要素の終了の場合、true
	 */
	public boolean isEnd() {
		return false;
	}

	/**
	 * 開始タグ取得.
	 *
	 * @return 開始タグ（無い場合はnull）
	 */
	public abstract Tag getStartTag();

	/**
	 * 終了タグ取得.
	 *
	 * @return 終了タグ（無い場合はnull）
	 */
	public abstract Tag getEndTag();

	/**
	 * 中身が無いかどうか.
	 *
	 * @return 中身が無い場合、true
	 * @since 2009.02.21
	 */
	public abstract boolean isEmpty();

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
	 * トークン探索.
	 * <p>
	 * 指定されたトークンを保持している要素を探す。<br>
	 * リストトークン（トークンが複数のトークンを保持している）の中までは探さない。
	 * </p>
	 *
	 * @param t
	 *            トークン
	 * @return トークンが見つかった場合、そのトークンを保持している要素（見つからなかった場合はnull）
	 * @since 2009.02.15
	 */
	public abstract HtElement searchToken(Token t);

	/**
	 * リストトークン作成.
	 * <p>
	 * 要素内に保持しているトークンをリストに追加する。
	 * </p>
	 *
	 * @param tlist
	 *            出力先リストトークン
	 * @since 2009.02.15
	 */
	public abstract void toToken(ListToken tlist);
}
