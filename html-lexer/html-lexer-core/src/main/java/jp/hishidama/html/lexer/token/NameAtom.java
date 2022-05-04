package jp.hishidama.html.lexer.token;

/**
 * HtHtmlLexerトークン（名前）.
 *<p>
 * 名前を表すトークン。
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/htlexer.html"
 *         >ひしだま</a>
 * @since 2009.01.10
 */
public class NameAtom extends AtomToken {

	/**
	 * @since 2009.02.07
	 */
	@Override
	public NameAtom clone() throws CloneNotSupportedException {
		return new NameAtom(this);
	}

	protected NameAtom(NameAtom o) {
		super(o);
	}

	/**
	 * コンストラクター.
	 */
	public NameAtom() {
	}

	/**
	 * 名前設定.
	 *
	 * @param sb
	 *            名前
	 */
	public void setName(StringBuilder sb) {
		super.setAtom(sb);
	}

	/**
	 * 名前設定.
	 *
	 * @param s
	 *            名前
	 */
	public void setName(String s) {
		super.setAtom(s);
	}

	/**
	 * 名前取得.
	 *
	 * @return 名前（存在しない場合、null）
	 */
	public String getName() {
		return super.getAtom();
	}
}
