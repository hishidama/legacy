package jp.hishidama.eval.lex.comment;

/**
 * コメント解釈クラス.
 * <p>
 * コメントの開始・終了を解釈する。
 * </p>
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 * @since eval16
 */
public abstract class CommentLex {

	/** 開始文字列 */
	protected String top;

	/**
	 * コンストラクター.
	 *
	 * @param top
	 *            開始文字列（null不可）
	 */
	protected CommentLex(String top) {
		this.top = top;
	}

	/**
	 * 開始文字列と一致しているか.
	 *
	 * @param string
	 *            対象文字列
	 * @param pos
	 *            位置
	 * @return 0以上：一致している文字数、負：一致していない
	 */
	public int isTop(String string, int pos) {
		return is(top, string, pos);
	}

	/**
	 * 終了文字列と一致しているか.
	 *
	 * @param string
	 *            対象文字列
	 * @param pos
	 *            位置
	 * @return 0以上：一致している文字数、負：一致していない
	 */
	public abstract int isEnd(String string, int pos);

	protected int is(String ope, String string, int pos) {
		int size = ope.length();
		for (int i = 0; i < size; i++, pos++) {
			if (pos >= string.length()) {
				return -1;
			}
			char c = string.charAt(pos);
			char d = ope.charAt(i);
			if (c != d) {
				return -1;
			}
		}
		return size;
	}

	/**
	 * 開始文字列取得.
	 *
	 * @return 開始文字列
	 */
	public String getTopString() {
		return top;
	}

	/**
	 * 開始文字列長取得.
	 *
	 * @return 開始文字列の長さ
	 */
	public int topLength() {
		return top.length();
	}

	/**
	 * コメントスキップ.
	 *
	 * @param string
	 *            対象文字列
	 * @param pos
	 *            スキップ開始位置
	 * @return コメントの次の文字の位置（負の場合、コメントが終了せずに文字列が終わった）
	 */
	public int skip(String string, int pos) {
		for (;;) {
			if (pos > string.length()) {
				return -1; // 文字列の最後まで来た
			}
			int e = isEnd(string, pos);
			if (e < 0) {
				pos++;
				continue;
			}
			return pos + e;
		}
	}
}
