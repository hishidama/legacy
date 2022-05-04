package jp.hishidama.eval.srch;

import jp.hishidama.eval.exp.*;

/**
 * 探索インターフェース.
 * <p>
 * 全解析木構造を探索するときに呼ばれるインターフェース。
 * </p>
 * 
 * @see jp.hishidama.eval.Expression#search(Search)
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html">ひしだま</a>
 * @since 2007.02.17
 * @version 2007.02.20
 */
public interface Search {

	/**
	 * @return 探索全体を終了するとき、true
	 * @since 2007.02.20
	 */
	public boolean end();

	/**
	 * 探索実行
	 * <p>
	 * 探索実行中に、それぞれの木構造で一度ずつ呼ばれる。
	 * </p>
	 * 
	 * @param exp
	 *            探索対象Expression
	 */
	public void search(AbstractExpression exp);

	/**
	 * 探索実行（単語）
	 * <p>
	 * 探索中に、数値または変数であれば呼ばれる。
	 * </p>
	 * 
	 * @param exp
	 *            探索対象Expression
	 * @since 2007.02.20
	 */
	public void search0(WordExpression exp);

	/**
	 * 探索実行（単項演算子）
	 * <p>
	 * 探索中に、単項演算子であれば呼ばれる。（内部の探索前）
	 * </p>
	 * 
	 * @param exp
	 *            探索対象Expression
	 * @return 当演算子の探索を終了するとき、true
	 * @since 2007.02.20
	 */
	public boolean search1_begin(Col1Expression exp);

	/**
	 * 探索実行（単項演算子）
	 * <p>
	 * 探索中に、単項演算子であれば呼ばれる。（内部の探索後）
	 * </p>
	 * 
	 * @param exp
	 *            探索対象Expression
	 * @since 2007.02.20
	 */
	public void search1_end(Col1Expression exp);

	/**
	 * 探索実行（二項演算子）
	 * <p>
	 * 探索中に、二項演算子であれば呼ばれる。（最初）
	 * </p>
	 * 
	 * @param exp
	 *            探索対象Expression
	 * @return 当演算子の探索を終了するとき、true
	 * @since 2007.02.20
	 */
	public boolean search2_begin(Col2Expression exp);

	/**
	 * 探索実行（二項演算子）
	 * <p>
	 * 探索中に、二項演算子であれば呼ばれる。（第1項の探索後）
	 * </p>
	 * 
	 * @param exp
	 *            探索対象Expression
	 * @return 当演算子の探索を終了するとき、true
	 * @since 2007.02.20
	 */
	public boolean search2_2(Col2Expression exp);

	/**
	 * 探索実行（二項演算子）
	 * <p>
	 * 探索中に、二項演算子であれば呼ばれる。（最後（第2項の探索後））
	 * </p>
	 * 
	 * @param exp
	 *            探索対象Expression
	 * @since 2007.02.20
	 */
	public void search2_end(Col2Expression exp);

	/**
	 * 探索実行（三項演算子）
	 * <p>
	 * 探索中に、三項演算子であれば呼ばれる。（最初）
	 * </p>
	 * 
	 * @param exp
	 *            探索対象Expression
	 * @return 当演算子の探索を終了するとき、true
	 * @since 2007.02.20
	 */
	public boolean search3_begin(Col3Expression exp);

	/**
	 * 探索実行（三項演算子）
	 * <p>
	 * 探索中に、三項演算子であれば呼ばれる。（条件演算子の探索後）
	 * </p>
	 * 
	 * @param exp
	 *            探索対象Expression
	 * @return 当演算子の探索を終了するとき、true
	 * @since 2007.02.20
	 */
	public boolean search3_2(Col3Expression exp);

	/**
	 * 探索実行（三項演算子）
	 * <p>
	 * 探索中に、三項演算子であれば呼ばれる。（第2項の探索後）
	 * </p>
	 * 
	 * @param exp
	 *            探索対象Expression
	 * @return 当演算子の探索を終了するとき、true
	 * @since 2007.02.20
	 */
	public boolean search3_3(Col3Expression exp);

	/**
	 * 探索実行（三項演算子）
	 * <p>
	 * 探索中に、三項演算子であれば呼ばれる。（最後（第3項の探索後））
	 * </p>
	 * 
	 * @param exp
	 *            探索対象Expression
	 * @since 2007.02.20
	 */
	public void search3_end(Col3Expression exp);

	/**
	 * 探索実行（関数）
	 * <p>
	 * 探索中に、関数であれば呼ばれる。（最初）
	 * </p>
	 * 
	 * @param exp
	 *            探索対象Expression
	 * @return 当演算子の探索を終了するとき、true
	 * @since 2007.02.20
	 */
	public boolean searchFunc_begin(FunctionExpression exp);

	/**
	 * 探索実行（関数）
	 * <p>
	 * 探索中に、関数であれば呼ばれる。（オブジェクトの探索後）
	 * </p>
	 * 
	 * @param exp
	 *            探索対象Expression
	 * @return 当演算子の探索を終了するとき、true
	 * @since 2007.02.20
	 */
	public boolean searchFunc_2(FunctionExpression exp);

	/**
	 * 探索実行（関数）
	 * <p>
	 * 探索中に、関数であれば呼ばれる。（最後（引数の探索後））
	 * </p>
	 * 
	 * @param exp
	 *            探索対象Expression
	 * @since 2007.02.20
	 */
	public void searchFunc_end(FunctionExpression exp);
}
