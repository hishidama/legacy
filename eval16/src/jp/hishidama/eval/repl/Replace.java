package jp.hishidama.eval.repl;

import jp.hishidama.eval.exp.*;

/**
 * 構文解析木置換インターフェース.
 * <p>
 * AbstractExpression#replace()が呼ばれた際、構文解析木のそれぞれの条件に応じた当インターフェースのメソッドが呼ばれる。<br>
 * 各メソッドでは、構文解析木を置換する場合は新しい構文解析木を返し、置換しない場合は引数をそのまま返す必要がある。
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html">ひしだま</a>
 * @since 2007.02.20
 */
public interface Replace {

	/**
	 * 識別子置換
	 * 
	 * @param exp
	 * @return 新exp
	 */
	AbstractExpression replace0(WordExpression exp);

	/**
	 * 単項演算子置換
	 * 
	 * @param exp
	 * @return 新exp
	 */
	AbstractExpression replace1(Col1Expression exp);

	/**
	 * 二項演算子置換
	 * <p>
	 * 加減算などの単純な二項演算子のとき呼ばれる。
	 * </p>
	 * 
	 * @param exp
	 * @return 新exp
	 */
	AbstractExpression replace2(Col2Expression exp);

	/**
	 * 二項演算子（特殊）置換
	 * <p>
	 * and,or,カンマなどの単純でない二項演算子のとき呼ばれる。
	 * </p>
	 * 
	 * @param exp
	 * @return 新exp
	 */
	AbstractExpression replace2(Col2OpeExpression exp);

	/**
	 * 三項演算子置換
	 * 
	 * @param exp
	 * @return 新exp
	 */
	AbstractExpression replace3(Col3Expression exp);

	/**
	 * 識別子置換（左辺値として使用される場合）
	 * 
	 * @param exp
	 * @return 新exp
	 */
	AbstractExpression replaceVar0(WordExpression exp);

	/**
	 * 単項演算子置換（左辺値として使用される場合）
	 * 
	 * @param exp
	 * @return 新exp
	 */
	AbstractExpression replaceVar1(Col1Expression exp);

	/**
	 * 二項演算子置換（左辺値として使用される場合）
	 * 
	 * @param exp
	 * @return 新exp
	 */
	AbstractExpression replaceVar2(Col2Expression exp);

	/**
	 * 二項演算子置換（左辺値として使用される場合）
	 * 
	 * @param exp
	 * @return 新exp
	 */
	AbstractExpression replaceVar2(Col2OpeExpression exp);

	/**
	 * 三項演算子置換（左辺値として使用される場合）
	 * 
	 * @param exp
	 * @return 新exp
	 */
	AbstractExpression replaceVar3(Col3Expression exp);

	/**
	 * 関数置換
	 * 
	 * @param exp
	 * @return 新exp
	 */
	AbstractExpression replaceFunc(FunctionExpression exp);

	/**
	 * 代入演算子置換
	 * 
	 * @param exp
	 * @return 新exp
	 */
	AbstractExpression replaceLet(Col2Expression exp);

}
