package jp.hishidama.eval;

/**
 * 演算エラー（スルー）クラス.
 * <p>
 * 演算の結果生じた実行時例外をそのまま返す為の例外。<br>
 * {@link Expression#eval()}から呼ばれた処理の中で当例外をスローすると、
 * eval()からは当例外でラップされた実行時例外をスローする。
 * </p>
 *
 * @see Expression#eval()
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 * @since eval16
 */
@SuppressWarnings("serial")
public class EvalThroughException extends EvalException {

	public EvalThroughException(RuntimeException e) {
		super(e);
	}

	@Override
	public String toString() {
		return getCause().toString();
	}
}
