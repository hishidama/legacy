package jp.hishidama.eval.log;

import jp.hishidama.eval.Expression;

/**
 * 演算時ログ出力インターフェース.
 * <p>
 * {@link Expression#eval()}において演算を実行した際にログ出力する為のインターフェース。
 * </p>
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 * @since eval16
 */
public interface EvalLog {

	/**
	 * ログ出力（無演算）.
	 *
	 * @param name
	 *            演算名（null以外）
	 * @param r
	 *            演算結果の値
	 */
	public void logEval(String name, Object r);

	/**
	 * ログ出力（単項演算）.
	 *
	 * @param name
	 *            演算名（null以外）
	 * @param x
	 *            値
	 * @param r
	 *            演算結果の値
	 */
	public void logEval(String name, Object x, Object r);

	/**
	 * ログ出力（二項演算）.
	 *
	 * @param name
	 *            演算名（null以外）
	 * @param x
	 *            値1
	 * @param y
	 *            値2
	 * @param r
	 *            演算結果の値
	 */
	public void logEval(String name, Object x, Object y, Object r);

	/**
	 * ログ出力（関数）.
	 *
	 * @param name
	 *            演算名（null以外）
	 * @param funcName
	 *            関数名（null以外）
	 * @param args
	 *            関数の引数の値（null以外）
	 * @param r
	 *            演算結果の値
	 */
	public void logEvalFunction(String name, String funcName, Object[] args,
			Object r);
}
