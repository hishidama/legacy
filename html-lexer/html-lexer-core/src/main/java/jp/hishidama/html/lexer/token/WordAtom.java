package jp.hishidama.html.lexer.token;

/**
 * HtHtmlLexerトークン（単語）.
 *<p>
 * 単語（というか文字列）を保持するトークン。<br>
 * 当クラスで直接保持する文字列は、改行コードを持たない。
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/htlexer.html"
 *         >ひしだま</a>
 * @since 2009.01.10
 */
public class WordAtom extends AtomToken {

	/**
	 * @since 2009.02.07
	 */
	@Override
	public WordAtom clone() throws CloneNotSupportedException {
		return new WordAtom(this);
	}

	protected WordAtom(WordAtom o) {
		super(o);
	}

	/**
	 * コンストラクター.
	 */
	public WordAtom() {
	}

	/**
	 * コンストラクター.
	 *
	 * @param sb
	 *            文字列
	 */
	public WordAtom(StringBuilder sb) {
		super(sb);
	}

	/**
	 * コンストラクター.
	 *
	 * @param s
	 *            文字列
	 */
	public WordAtom(String s) {
		super(s);
	}

	/**
	 * 文字列設定.
	 *
	 * @param sb
	 *            文字列
	 */
	public void setString(StringBuilder sb) {
		super.setAtom(sb);
	}

	/**
	 * 文字列設定.
	 *
	 * @param s
	 *            文字列
	 */
	public void setString(String s) {
		super.setAtom(s);
	}

	/**
	 * 文字列取得.
	 *
	 * @return 文字列（存在しない場合、null）
	 */
	public String getString() {
		return super.getAtom();
	}
}
