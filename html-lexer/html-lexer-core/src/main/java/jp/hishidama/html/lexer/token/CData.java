package jp.hishidama.html.lexer.token;

/**
 * HtHtmlLexerトークン（CDATA）.
 *<p>
 * CDATAセクション（{@literal <![CDATA[～]]>}）を保持するトークン。
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/htlexer.html"
 *         >ひしだま</a>
 * @since 2009.01.25
 */
public class CData extends NamedMarkup {

	/**
	 * @since 2009.02.07
	 */
	@Override
	public CData clone() throws CloneNotSupportedException {
		return new CData(this);
	}

	protected CData(CData o) {
		super(o);
	}

	static enum Pos {
		TAGO, NAME, BRAO, TEXT, TAGC
	}

	/**
	 * コンストラクター.
	 */
	public CData() {
	}

	/**
	 * 角括弧開き設定.
	 * <p>
	 * 「CDATA」の直後の角括弧。
	 * </p>
	 *
	 * @param word
	 *            角括弧（普通は「[」）
	 */
	public void setBraO(WordAtom word) {
		set(Pos.BRAO, word);
	}

	/**
	 * テキスト設定.
	 *
	 * @param text
	 *            テキスト
	 */
	public void setData(TextToken text) {
		set(Pos.TEXT, text);
	}

	@Override
	public void setTag2(StringBuilder sb) {
		AtomToken atom = getTag2Atom();
		if (atom == null) {
			atom = new AtomToken(sb);
			set(Pos.TAGC, atom);
		} else {
			atom.setAtom(sb);
		}
	}

	@Deprecated
	@Override
	public void addSkip(SkipToken skip) {
		throw new UnsupportedOperationException();
	}

	/**
	 * テキスト取得.
	 *
	 * @return テキスト（存在しない場合、null）
	 */
	public String getData() {
		TextToken text = getDataToken();
		if (text != null) {
			return text.getText();
		}
		return null;
	}

	/**
	 * テキスト取得.
	 *
	 * @return テキスト（存在しない場合、null）
	 */
	public TextToken getDataToken() {
		return (TextToken) get(Pos.TEXT);
	}

	@Override
	public String getTag2() {
		AtomToken atom = getTag2Atom();
		if (atom != null) {
			return atom.getAtom();
		}
		return null;
	}

	@Override
	public AtomToken getTag2Atom() {
		return (AtomToken) get(Pos.TAGC);
	}
}
