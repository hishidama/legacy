package jp.hishidama.robot;

/**
 * タイムアウト例外.
 * <p>
 * 待ちが発生する処理においてタイムアウトした場合に発生する例外。
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">ひしだま</a>
 * @since 2007.11.01
 */
public class TimeoutException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1420932826242953503L;

	protected int pos;

	protected int timeout;

	/**
	 * コンストラクター.
	 * 
	 * @param pos
	 *            タイムアウト発生箇所
	 * @param timeout
	 *            指定されていたタイムアウト時間[ms]
	 */
	public TimeoutException(int pos, int timeout) {
		this.pos = pos;
		this.timeout = timeout;
	}

	/**
	 * タイムアウト発生箇所取得.
	 * 
	 * @return タイムアウト発生箇所（値は呼び出したメソッドによる）
	 */
	public int getPos() {
		return pos;
	}

	/**
	 * タイムアウト時間取得.
	 * 
	 * @return 指定されていたタイムアウト時間[ms]
	 */
	public int getTimeout() {
		return timeout;
	}

	public String getMessage() {
		return "timeout: pos=" + pos + ", " + timeout + "ms";
	}

}
