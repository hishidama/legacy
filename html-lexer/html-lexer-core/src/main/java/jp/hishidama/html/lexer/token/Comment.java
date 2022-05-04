package jp.hishidama.html.lexer.token;

/**
 * HtHtmlLexerトークン（コメント）.
 *<p>
 * コメント（{@literal <!--～-->}）を保持するトークン。
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/htlexer.html"
 *         >ひしだま</a>
 * @since 2009.01.10
 */
public class Comment extends Markup {

	/**
	 * @since 2009.02.07
	 */
	@Override
	public Comment clone() throws CloneNotSupportedException {
		return new Comment(this);
	}

	protected Comment(Comment o) {
		super(o);
	}

	protected static enum Pos {
		TAGO, TEXT, TAGC
	}

	/**
	 * コンストラクター.
	 */
	public Comment() {
	}

	/**
	 * コメント設定.
	 *
	 * @param text
	 *            コメント
	 */
	public void setComment(TextToken text) {
		set(Pos.TEXT, text);
	}

	/**
	 * コメント取得.
	 *
	 * @return コメント（存在しない場合、null）
	 */
	public String getComment() {
		TextToken text = getCommentToken();
		if (text != null) {
			return text.getText();
		}
		return null;
	}

	/**
	 * コメント取得.
	 *
	 * @return コメント（存在しない場合、null）
	 */
	public TextToken getCommentToken() {
		return (TextToken) get(Pos.TEXT);
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
