package jp.hishidama.eval.log;

/**
 * 演算時ログ出力アダプタークラス.
 * <p>
 * {@link EvalLog ログ出力インターフェース}を実装したクラス。<br>
 * 当クラスでは、処理は何も行わない。
 * </p>
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 * @since eval16
 */
public class EvalLogAdapter implements EvalLog {

	@Override
	public void logEval(String name, Object r) {
	}

	@Override
	public void logEval(String name, Object x, Object r) {
	}

	@Override
	public void logEval(String name, Object x, Object y, Object r) {
	}

	@Override
	public void logEvalFunction(String name, String funcName, Object[] args,
			Object r) {
	}
}
