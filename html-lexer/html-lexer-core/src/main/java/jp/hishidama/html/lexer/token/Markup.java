package jp.hishidama.html.lexer.token;

/**
 * HtHtmlLexerトークン（マークアップ）.
 * <p>
 * マークアップ（{@literal <～>}）を意味するトークン（抽象クラス）。
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/htlexer.html"
 *         >ひしだま</a>
 * @since 2009.01.10
 */
public class Markup extends ListToken {

	private boolean existTagC = false;

	/**
	 * @since 2009.02.07
	 */
	@Override
	public Markup clone() throws CloneNotSupportedException {
		return new Markup(this);
	}

	protected Markup(Markup o) {
		super(o);
		existTagC = o.existTagC;
	}

	protected Markup() {
	}

	protected static enum Pos {
		TAGO,
		// TAGC,
	}

	/**
	 * タグ開き設定.
	 *
	 * @param sb
	 *            TAGO
	 */
	public void setTag1(StringBuilder sb) {
		AtomToken atom = getTag1Atom();
		if (atom == null) {
			atom = new AtomToken(sb);
			set(Pos.TAGO, atom);
		} else {
			atom.setAtom(sb);
		}
	}

	/**
	 * 空白追加.
	 * <p>
	 * タグ閉じの直前に空白を挿入する。
	 * </p>
	 *
	 * @param skip
	 *            空白
	 */
	public void addSkip(SkipToken skip) {
		addBeforeTagC(skip);
	}

	/**
	 * 空白追加.
	 * <p>
	 * タグ閉じの直前に空白を挿入する。
	 * </p>
	 *
	 * @param skip
	 *            空白
	 * @since 2011.11.13
	 */
	public void addSkip(String skip) {
		SkipToken token = new SkipToken();
		token.add(new WordAtom(skip));
		addSkip(token);
	}

	protected void addBeforeTagC(Token token) {
		if (token == null) {
			return;
		}
		if (existTagC) {
			add(list.size() - 1, token);
		} else {
			add(token);
		}
	}

	/**
	 * タグ閉じ設定.
	 *
	 * @param sb
	 *            TAGC
	 */
	public void setTag2(StringBuilder sb) {
		AtomToken atom = getTag2Atom();
		if (atom == null) {
			atom = new AtomToken(sb);
			add(atom);
			existTagC = true;
		} else {
			atom.setAtom(sb);
		}
	}

	/**
	 * タグ開き取得.
	 *
	 * @return TAGO（存在しない場合、null）
	 */
	public AtomToken getTag1Atom() {
		return (AtomToken) get(Pos.TAGO);
	}

	/**
	 * タグ開き取得.
	 *
	 * @return TAGO（存在しない場合、null）
	 */
	public String getTag1() {
		AtomToken atom = getTag1Atom();
		if (atom != null) {
			return atom.getAtom();
		}
		return null;
	}

	/**
	 * タグ閉じ取得.
	 *
	 * @return TAGC（存在しない場合、null）
	 */
	public AtomToken getTag2Atom() {
		if (existTagC) {
			return (AtomToken) getLast();
		}
		return null;
	}

	/**
	 * タグ閉じ取得.
	 *
	 * @return TAGC（存在しない場合、null）
	 */
	public String getTag2() {
		AtomToken atom = getTag2Atom();
		if (atom != null) {
			return atom.getAtom();
		}
		return null;
	}
}
