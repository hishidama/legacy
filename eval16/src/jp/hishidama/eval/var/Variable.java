package jp.hishidama.eval.var;

import jp.hishidama.eval.exp.AbstractExpression;

/**
 * 変数インターフェース.
 * <p>
 * 変数と変数値を管理するインターフェース。
 * </p>
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 * @since 2007.02.15
 * @version eval16
 */
public interface Variable {

	/**
	 * 変数値取得.
	 * <p>
	 * 変数を表すオブジェクトを返す。（単なる変数や配列を想定）
	 * </p>
	 *
	 * @param name
	 *            変数名
	 * @return 変数オブジェクト
	 */
	public Object getValue(Object name);

	/**
	 * 変数設定.
	 * <p>
	 * 変数に値をセットする。
	 * </p>
	 *
	 * @param name
	 *            変数名
	 * @param value
	 *            値
	 */
	public void setValue(Object name, Object value);

	/**
	 * 変数値取得.
	 * <p>
	 * 配列の要素を返す。
	 * </p>
	 *
	 * @param array
	 *            配列オブジェクト
	 * @param arrayName
	 *            配列名
	 * @param index
	 *            添字
	 * @param exp
	 *            当メソッドを呼び出した式クラス
	 * @return 要素オブジェクト
	 */
	public Object getArrayValue(Object array, String arrayName, Object index,
			AbstractExpression exp);

	/**
	 * 配列値設定.
	 * <p>
	 * 配列に値をセットする。
	 * </p>
	 *
	 * @param array
	 *            配列オブジェクト
	 * @param arrayName
	 *            配列名
	 * @param index
	 *            添字
	 * @param value
	 *            値
	 * @param exp
	 *            当メソッドを呼び出した式クラス
	 */
	public void setArrayValue(Object array, String arrayName, Object index,
			Object value, AbstractExpression exp);

	/**
	 * フィールド値取得.
	 * <p>
	 * オブジェクトのフィールドの値を返す。
	 * </p>
	 *
	 * @param obj
	 *            オブジェクト
	 * @param objName
	 *            オブジェクト名
	 * @param field
	 *            フィールド名
	 * @param exp
	 *            当メソッドを呼び出した式クラス
	 * @return 要素オブジェクト
	 */
	public Object getFieldValue(Object obj, String objName, String field,
			AbstractExpression exp);

	/**
	 * フィールド値設定.
	 * <p>
	 * オブジェクトのフィールドに値をセットする。
	 * </p>
	 *
	 * @param obj
	 *            オブジェクト
	 * @param objName
	 *            オブジェクト名
	 * @param field
	 *            フィールド名
	 * @param value
	 *            値
	 * @param exp
	 *            当メソッドを呼び出した式クラス
	 */
	public void setFieldValue(Object obj, String objName, String field,
			Object value, AbstractExpression exp);
}
