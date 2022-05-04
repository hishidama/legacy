package jp.hishidama.html.lexer.token;

/**
 * HtHtmlLexerトークン（テキスト）.
 *<p>
 * テキスト（複数の{@link WordAtom 文字列}）を保持するトークン。
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/htlexer.html"
 *         >ひしだま</a>
 * @since 2009.01.10
 * @version 2009.02.21
 */
public class TextToken extends ListToken {

	/**
	 * @since 2009.02.07
	 */
	@Override
	public TextToken clone() throws CloneNotSupportedException {
		return new TextToken(this);
	}

	protected TextToken(TextToken o) {
		super(o);
	}

	/**
	 * コンストラクター.
	 */
	public TextToken() {
	}

	/**
	 * コンストラクター.
	 *
	 * @param size
	 *            内部リストの初期サイズ
	 * @since 2009.02.21
	 */
	public TextToken(int size) {
		super(size);
	}

	/**
	 * 文字列追加.
	 *
	 * @param word
	 *            文字列
	 */
	public void add(WordAtom word) {
		super.add(word);
	}

	/**
	 * 文字列設定.
	 *<p>
	 * 内部に保持しているリストを、渡されたテキスト内のリストに置き換える。
	 * </p>
	 *
	 * @param text
	 *            テキスト
	 */
	public void setText(TextToken text) {
		list.clear();
		for (int i = 0; i < text.size(); i++) {
			this.add(text.get(i));
		}
	}
}
