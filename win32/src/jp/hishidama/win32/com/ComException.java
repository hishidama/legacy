package jp.hishidama.win32.com;

/**
 * COM関連例外.
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">ひしだま</a>
 * @since 2007.10.22
 */
public class ComException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1633520862595253090L;

	public ComException() {
	}

	public ComException(String message) {
		super(message);
	}

}
