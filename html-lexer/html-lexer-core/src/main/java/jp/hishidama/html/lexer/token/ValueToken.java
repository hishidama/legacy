package jp.hishidama.html.lexer.token;

/**
 * HtHtmlLexerトークン（属性値）.
 * <p>
 * 属性値を保持するトークン。
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/htlexer.html"
 *         >ひしだま</a>
 * @since 2009.01.10
 */
public class ValueToken extends ListToken {

	/**
	 * @since 2009.02.07
	 */
	@Override
	public ValueToken clone() throws CloneNotSupportedException {
		return new ValueToken(this);
	}

	protected ValueToken(ValueToken o) {
		super(o);
	}

	protected static enum Pos {
		BEGIN, VALUE, END
	}

	/**
	 * コンストラクター.
	 */
	public ValueToken() {
	}

	/**
	 * コンストラクター.
	 *
	 * @param q1
	 *            左クォーテーション
	 * @param text
	 *            値
	 * @param q2
	 *            右クォーテーション
	 * @since 2011.12.10
	 */
	public ValueToken(String q1, String text, String q2) {
		setQuote1(q1);
		setValue(text);
		setQuote2(q2);
	}

	/**
	 * クォーテーション設定.
	 * <p>
	 * 属性値の先頭のクォーテーション。
	 * </p>
	 *
	 * @param sb
	 *            クォーテーション
	 */
	public void setQuote1(StringBuilder sb) {
		if (sb == null) {
			setQuote1Atom(null);
			return;
		}
		AtomToken atom = getQuote1Atom();
		if (atom == null) {
			atom = new AtomToken(sb);
			setQuote1Atom(atom);
		} else {
			atom.setAtom(sb);
		}
	}

	/**
	 * クォーテーション設定.
	 * <p>
	 * 属性値の先頭のクォーテーション。
	 * </p>
	 *
	 * @param s
	 *            クォーテーション
	 */
	public void setQuote1(String s) {
		if (s == null) {
			setQuote1Atom(null);
			return;
		}
		AtomToken atom = getQuote1Atom();
		if (atom == null) {
			atom = new AtomToken(s);
			setQuote1Atom(atom);
		} else {
			atom.setAtom(s);
		}
	}

	/**
	 * クォーテーション設定.
	 * <p>
	 * 属性値の先頭のクォーテーション。
	 * </p>
	 *
	 * @param atom
	 *            クォーテーション
	 */
	public void setQuote1Atom(AtomToken atom) {
		set(Pos.BEGIN, atom);
	}

	/**
	 * 値設定.
	 *
	 * @param text
	 *            値
	 */
	public void setValue(TextToken text) {
		set(Pos.VALUE, text);
	}

	/**
	 * 値設定
	 *
	 * @param text
	 *            値
	 * @since 2011.12.10
	 */
	public void setValue(String text) {
		if (text == null) {
			setValue((TextToken) null);
		} else {
			TextToken token = new TextToken();
			WordAtom word = new WordAtom(text);
			token.add(word);
			setValue(token);
		}
	}

	/**
	 * クォーテーション設定.
	 * <p>
	 * 属性値の末尾のクォーテーション。
	 * </p>
	 *
	 * @param sb
	 *            クォーテーション
	 */
	public void setQuote2(StringBuilder sb) {
		if (sb == null) {
			setQuote2Atom(null);
			return;
		}
		AtomToken atom = getQuote2Atom();
		if (atom == null) {
			atom = new AtomToken(sb);
			setQuote2Atom(atom);
		} else {
			atom.setAtom(sb);
		}
	}

	/**
	 * クォーテーション設定.
	 * <p>
	 * 属性値の末尾のクォーテーション。
	 * </p>
	 *
	 * @param s
	 *            クォーテーション
	 */
	public void setQuote2(String s) {
		AtomToken atom = getQuote2Atom();
		if (atom == null) {
			atom = new AtomToken(s);
			setQuote2Atom(atom);
		} else {
			atom.setAtom(s);
		}
	}

	/**
	 * クォーテーション設定.
	 * <p>
	 * 属性値の末尾のクォーテーション。
	 * </p>
	 *
	 * @param atom
	 *            クォーテーション
	 */
	public void setQuote2Atom(AtomToken atom) {
		set(Pos.END, atom);
	}

	/**
	 * クォーテーション取得.
	 * <p>
	 * 属性値の先頭のクォーテーション。
	 * </p>
	 *
	 * @return クォーテーション（存在しない場合、null）
	 */
	public String getQuote1() {
		AtomToken q = getQuote1Atom();
		if (q == null) {
			return null;
		}
		return q.getAtom();
	}

	/**
	 * クォーテーション取得.
	 * <p>
	 * 属性値の先頭のクォーテーション。
	 * </p>
	 *
	 * @return クォーテーション（存在しない場合、null）
	 */
	public AtomToken getQuote1Atom() {
		return (AtomToken) get(Pos.BEGIN);
	}

	/**
	 * 値取得.
	 *
	 * @return 値（存在しない場合、null）
	 */
	public TextToken getValueToken() {
		return (TextToken) get(Pos.VALUE);
	}

	/**
	 * 値取得.
	 * <p>
	 * クォーテーションは含まれない。
	 * </p>
	 *
	 * @return 値（存在しない場合、null）
	 */
	public String getValue() {
		TextToken text = getValueToken();
		if (text != null) {
			return text.getText();
		}
		return null;
	}

	/**
	 * 属性値取得.
	 *
	 * @param quote
	 *            trueの場合、クォーテーションを含む。
	 * @return 値（存在しない場合、null）
	 */
	public String getValue(boolean quote) {
		if (quote) {
			return getText();
		} else {
			return getValue();
		}
	}

	/**
	 * クォーテーション取得.
	 * <p>
	 * 属性値の末尾のクォーテーション。
	 * </p>
	 *
	 * @return クォーテーション（存在しない場合、null）
	 */
	public String getQuote2() {
		AtomToken q = getQuote2Atom();
		if (q == null) {
			return null;
		}
		return q.getAtom();
	}

	/**
	 * クォーテーション取得.
	 * <p>
	 * 属性値の末尾のクォーテーション。
	 * </p>
	 *
	 * @return クォーテーション（存在しない場合、null）
	 */
	public AtomToken getQuote2Atom() {
		return (AtomToken) get(Pos.END);
	}
}
