package jp.hishidama.html.lexer.reader;

/**
 * HtHtmlLexerの一文字を保持するクラス.
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/htlexer.html"
 *         >ひしだま</a>
 * @since 2009.01.01
 */
public class Char {

	/**
	 * 読み込み終了.
	 */
	public static final Char EOF = new Char(CharType.EOF, null);

	protected CharType type;
	protected StringBuilder sb;

	protected Char(CharType type, StringBuilder sb) {
		this.type = type;
		this.sb = sb;
	}

	/**
	 * インスタンス作成.
	 *
	 * @param type
	 *            文字タイプ
	 * @param sb
	 *            内容
	 * @return Charインスタンス
	 */
	public static Char createChar(CharType type, StringBuilder sb) {
		return new Char(type, sb);
	}

	/**
	 * インスタンス生成.
	 *
	 * @param type
	 *            文字タイプ
	 * @param c
	 *            内容
	 * @return Charインスタンス
	 */
	public static Char createChar(CharType type, char c) {
		StringBuilder sb = new StringBuilder(1);
		sb.append(c);
		return new Char(type, sb);
	}

	/**
	 * 文字タイプ取得.
	 *
	 * @return 文字タイプ
	 */
	public CharType getType() {
		return type;
	}

	/**
	 * 内容取得.
	 *
	 * @return 内容
	 */
	public String getString() {
		return sb.toString();
	}

	/**
	 * 内容取得.
	 *
	 * @return 内容
	 */
	public StringBuilder getSB() {
		return this.sb;
	}

	@Override
	public String toString() {
		return sb.toString();
	}
}
