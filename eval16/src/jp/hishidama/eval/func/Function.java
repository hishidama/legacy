package jp.hishidama.eval.func;

/**
 * 関数インターフェース.
 * <p>
 * 式の評価時に実行される関数を定義する。
 * </p>
 *
 * @see jp.hishidama.eval.Expression#setFunction(Function)
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 * @version eval16
 */
public interface Function {

	/**
	 * 関数実行.
	 * <p>
	 * 式の評価時に関数（{@code 関数名()}）があった場合、当メソッドが呼ばれる。
	 * </p>
	 *
	 * @param name
	 *            関数名（必ずnull以外）
	 * @param args
	 *            引数（必ずnull以外）
	 *
	 * @return 実行結果
	 * @throws Exception
	 *             例外
	 * @see jp.hishidama.eval.exp.FunctionExpression#eval()
	 * @since 2010.02.15
	 */
	public Object eval(String name, Object[] args) throws Exception;

	/**
	 * メソッド実行.
	 * <p>
	 * 式の評価時にメソッド（{@code オブジェクト.メソッド名()}）があった場合、当メソッドが呼ばれる。
	 * </p>
	 *
	 * @param object
	 *            実行対象オブジェクト
	 * @param name
	 *            メソッド名（必ずnull以外）
	 * @param args
	 *            引数（必ずnull以外）
	 *
	 * @return 実行結果
	 * @throws Exception
	 *             例外
	 * @see jp.hishidama.eval.exp.FunctionExpression#eval()
	 * @since 2007.02.15
	 */
	public Object eval(Object object, String name, Object[] args)
			throws Exception;
}
