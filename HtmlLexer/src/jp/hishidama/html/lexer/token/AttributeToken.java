package jp.hishidama.html.lexer.token;

/**
 * HtHtmlLexerトークン（属性）.
 * <p>
 * 属性（属性名と属性値の組）を１つ保持するトークン。
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/htlexer.html"
 *         >ひしだま</a>
 * @since 2009.01.10
 * @see NameAtom
 * @see ValueToken
 */
public class AttributeToken extends ListToken {

	/**
	 * @since 2009.02.07
	 */
	@Override
	public AttributeToken clone() throws CloneNotSupportedException {
		return new AttributeToken(this);
	}

	protected AttributeToken(AttributeToken o) {
		super(o);
	}

	protected static enum Pos {
		NAME, SKIP1, LET, SKIP2, VALUE
	}

	/**
	 * コンストラクター.
	 */
	public AttributeToken() {
	}

	/**
	 * コンストラクター.
	 *
	 * @param name
	 *            属性名
	 * @since 2011.12.10
	 */
	public AttributeToken(String name) {
		setName(name);
	}

	/**
	 * コンストラクター.
	 *
	 * @param name
	 *            属性名
	 * @param q1
	 *            左クォーテーション
	 * @param value
	 *            属性値
	 * @param q2
	 *            右クォーテーション
	 * @since 2011.12.10
	 */
	public AttributeToken(String name, String q1, String value, String q2) {
		setName(name);
		setLet("=");
		setValue(q1, value, q2);
	}

	/**
	 * 属性名設定.
	 *
	 * @param name
	 *            属性名
	 */
	public void setName(NameAtom name) {
		set(Pos.NAME, name);
	}

	/**
	 * 属性名設定.
	 *
	 * @param s
	 *            属性名
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
	 * 空白設定.
	 * <p>
	 * 属性名と代入記号の間の空白。
	 * </p>
	 *
	 * @param skip
	 *            空白
	 */
	public void setSkip1(SkipToken skip) {
		set(Pos.SKIP1, skip);
	}

	/**
	 * 代入記号設定.
	 *
	 * @param word
	 *            代入記号
	 */
	public void setLet(WordAtom word) {
		set(Pos.LET, word);
	}

	/**
	 * 代入記号設定.
	 *
	 * @param s
	 *            代入記号（普通は「=」）
	 */
	public void setLet(String s) {
		setLet(new WordAtom(s));
	}

	/**
	 * 空白設定.
	 * <p>
	 * 代入記号と属性値の間の空白。
	 * </p>
	 *
	 * @param skip
	 *            空白
	 */
	public void setSkip2(SkipToken skip) {
		set(Pos.SKIP2, skip);
	}

	/**
	 * 属性値設定.
	 *
	 * @param value
	 *            属性値
	 */
	public void setValue(ValueToken value) {
		set(Pos.VALUE, value);
	}

	/**
	 * 属性値設定.
	 *
	 * @param q1
	 *            左クォーテーション
	 * @param value
	 *            属性値
	 * @param q2
	 *            右クォーテーション
	 * @since 2011.12.10
	 */
	public void setValue(String q1, String value, String q2) {
		ValueToken token = new ValueToken(q1, value, q2);
		setValue(token);
	}

	/**
	 * 属性名取得.
	 *
	 * @return 属性名（存在しない場合、null）
	 */
	public NameAtom getNameAtom() {
		return (NameAtom) get(Pos.NAME);
	}

	/**
	 * 属性名取得.
	 *
	 * @return 属性名（存在しない場合、null）
	 */
	public String getName() {
		NameAtom name = getNameAtom();
		if (name != null) {
			return name.getName();
		}
		return null;
	}

	/**
	 * 空白取得.
	 * <p>
	 * 属性名と代入記号の間の空白。
	 * </p>
	 *
	 * @return 空白（存在しない場合、null）
	 */
	public SkipToken getSkip1Token() {
		return (SkipToken) get(Pos.SKIP1);
	}

	/**
	 * 空白取得.
	 * <p>
	 * 属性名と代入記号の間の空白。
	 * </p>
	 *
	 * @return 空白（存在しない場合、null）
	 */
	public String getSkip1() {
		SkipToken skip1 = getSkip1Token();
		if (skip1 != null) {
			return skip1.getText();
		}
		return null;
	}

	/**
	 * 代入記号取得.
	 *
	 * @return 代入記号（存在しない場合、null）
	 */
	public WordAtom getLetToken() {
		return (WordAtom) get(Pos.LET);
	}

	/**
	 * 代入記号取得.
	 *
	 * @return 代入記号（存在しない場合、null）
	 */
	public String getLet() {
		WordAtom eq = getLetToken();
		if (eq != null) {
			return eq.getString();
		}
		return null;
	}

	/**
	 * 空白取得.
	 * <p>
	 * 代入記号と属性値の間の空白。
	 * </p>
	 *
	 * @return 空白（存在しない場合、null）
	 */
	public SkipToken getSkip2Token() {
		return (SkipToken) get(Pos.SKIP2);
	}

	/**
	 * 空白取得.
	 * <p>
	 * 代入記号と属性値の間の空白。
	 * </p>
	 *
	 * @return 空白（存在しない場合、null）
	 */
	public String getSkip2() {
		SkipToken skip2 = getSkip2Token();
		if (skip2 != null) {
			return skip2.getText();
		}
		return null;
	}

	/**
	 * 属性値取得.
	 *
	 * @return 属性値（存在しない場合、null）
	 */
	public ValueToken getValueToken() {
		return (ValueToken) get(Pos.VALUE);
	}

	/**
	 * 属性値取得.
	 *
	 * @return 属性値（存在しない場合、null）
	 */
	public String getValue() {
		ValueToken value = getValueToken();
		if (value != null) {
			return value.getValue();
		}
		return null;
	}
}
