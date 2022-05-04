package jp.hishidama.html.lexer.token;

/**
 * HtHtmlLexerトークン（名前付きマークアップ）.
 *<p>
 * 名前のあるマークアップ（{@literal <name～>}）を意味するトークン（抽象クラス）。
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/htlexer.html"
 *         >ひしだま</a>
 * @since 2009.01.10
 */
public abstract class NamedMarkup extends Markup {

	protected NamedMarkup(NamedMarkup o) {
		super(o);
	}

	protected static enum Pos {
		TAGO, NAME,
		// ETC,
		// TAGC,
	}

	protected NamedMarkup() {
	}

	/**
	 * 名前設定.
	 *
	 * @param atom
	 *            名前
	 */
	public void setName(NameAtom atom) {
		set(Pos.NAME, atom);
	}

	/**
	 * 名前設定.
	 *
	 * @param s
	 *            名前
	 */
	public void setName(String s) {
		NameAtom name = getNameAtom();
		if (name == null) {
			name = new NameAtom();
			setName(name);
		}
		name.setName(s);
	}

	/**
	 * 名前取得.
	 *
	 * @return 名前（存在しない場合、null）
	 */
	public String getName() {
		NameAtom name = getNameAtom();
		if (name != null) {
			return name.getName();
		}
		return null;
	}

	/**
	 * 名前取得.
	 *
	 * @return 名前（存在しない場合、null）
	 */
	public NameAtom getNameAtom() {
		return (NameAtom) get(Pos.NAME);
	}
}
