package jp.hishidama.eval.lex.comment;

/**
 * ブロックコメント解釈クラス.
 * <p>
 * ブロックコメント（例：/&#x2a;～&#x2a;/）の開始・終了文字列を保持する。<br>
 * ブロックコメントのネストは出来ない。
 * </p>
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 * @since eval16
 */
public class BlockComment extends CommentLex {

	/** 終了文字列 */
	protected String end;

	/**
	 * コンストラクター.
	 *
	 * @param top
	 *            開始文字列（null不可）
	 * @param end
	 *            終了文字列（null不可）
	 */
	public BlockComment(String top, String end) {
		super(top);
		this.end = end;
	}

	/**
	 * 終了文字列取得.
	 *
	 * @return 終了文字列
	 */
	public String getEndString() {
		return end;
	}

	@Override
	public int isEnd(String string, int pos) {
		return is(end, string, pos);
	}
}
