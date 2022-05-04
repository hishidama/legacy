package jp.hishidama.lang;

/**
 * 引数の個数が間違っている場合にスローされる例外
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 * @since 2010.02.16
 */
public class IllegalArgumentLengthException extends IllegalArgumentException {
	private static final long serialVersionUID = -1652752633469109078L;

	protected int len;
	protected int min;
	protected int max;

	/**
	 * コンストラクター.
	 *
	 * @param len
	 *            引数の個数
	 * @param min
	 *            許容個数の最小値
	 * @param max
	 *            許容個数の最大値
	 */
	public IllegalArgumentLengthException(int len, int min, int max) {
		this.len = len;
		this.min = min;
		this.max = max;
	}

	/**
	 * コンストラクター.
	 *
	 * @param message
	 *            メッセージ
	 * @param len
	 *            引数の個数
	 * @param min
	 *            許容個数の最小値
	 * @param max
	 *            許容個数の最大値
	 */
	public IllegalArgumentLengthException(String message, int len, int min,
			int max) {
		super(message);
		this.len = len;
		this.min = min;
		this.max = max;
	}

	/**
	 * 引数の個数取得.
	 *
	 * @return 引数の個数
	 */
	public int getLen() {
		return len;
	}

	/**
	 * 引数の個数設定.
	 *
	 * @param len
	 *            引数の個数
	 */
	public void setLen(int len) {
		this.len = len;
	}

	/**
	 * 許容個数の最小値取得.
	 *
	 * @return 最小個数
	 */
	public int getMin() {
		return min;
	}

	/**
	 * 許容個数の最小値設定.
	 *
	 * @param min
	 *            最小個数
	 */
	public void setMin(int min) {
		this.min = min;
	}

	/**
	 * 許容個数の最大値取得.
	 *
	 * @return 最大個数
	 */
	public int getMax() {
		return max;
	}

	/**
	 * 許容個数の最大値設定.
	 *
	 * @param max
	 *            最大個数
	 */
	public void setMax(int max) {
		this.max = max;
	}

	@Override
	public String getMessage() {
		String msg = super.getMessage();
		if (msg == null) {
			msg = "";
		}
		msg += " args.length=" + len + " (" + min + ".." + max + ")";
		return msg;
	}
}
