package jp.hishidama.eval.oper;

import jp.hishidama.eval.EvalException;
import jp.hishidama.eval.exp.AbstractExpression;
import jp.hishidama.eval.exp.CharExpression;
import jp.hishidama.eval.exp.NumberExpression;
import jp.hishidama.eval.exp.StringExpression;

/**
 * 演算実行インターフェース.
 * <p>
 * eval()において、実際の演算を実行するクラスの為のインターフェース。
 * </p>
 *
 * @see jp.hishidama.eval.Expression#setOperator(Operator)
 * @see jp.hishidama.eval.Expression#eval()
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 * @since 2007.02.15
 * @version eval16
 */
public interface Operator {
	/**
	 * 累乗演算.
	 *
	 * @param x
	 * @param y
	 * @return 値
	 * @since 2007.02.16
	 */
	public Object power(Object x, Object y);

	/**
	 * 正符号演算.
	 *
	 * @param x
	 * @return 値
	 */
	public Object signPlus(Object x);

	/**
	 * 負符号演算.
	 *
	 * @param x
	 * @return 値
	 */
	public Object signMinus(Object x);

	/**
	 * 加算.
	 *
	 * @param x
	 * @param y
	 * @return 値
	 */
	public Object plus(Object x, Object y);

	/**
	 * 減算.
	 *
	 * @param x
	 * @param y
	 * @return 値
	 */
	public Object minus(Object x, Object y);

	/**
	 * 乗算.
	 *
	 * @param x
	 * @param y
	 * @return 値
	 */
	public Object mult(Object x, Object y);

	/**
	 * 除算.
	 *
	 * @param x
	 * @param y
	 * @return 値
	 */
	public Object div(Object x, Object y);

	/**
	 * 余算.
	 *
	 * @param x
	 * @param y
	 * @return 値
	 */
	public Object mod(Object x, Object y);

	/**
	 * ビット否定.
	 *
	 * @param x
	 * @return 値
	 */
	public Object bitNot(Object x);

	/**
	 * 左シフト.
	 *
	 * @param x
	 * @param y
	 * @return 値
	 */
	public Object shiftLeft(Object x, Object y);

	/**
	 * 右シフト.
	 *
	 * @param x
	 * @param y
	 * @return 値
	 */
	public Object shiftRight(Object x, Object y);

	/**
	 * 論理右シフト.
	 *
	 * @param x
	 * @param y
	 * @return 値
	 */
	public Object shiftRightLogical(Object x, Object y);

	/**
	 * ビット論理積.
	 *
	 * @param x
	 * @param y
	 * @return 値
	 */
	public Object bitAnd(Object x, Object y);

	/**
	 * ビット論理和.
	 *
	 * @param x
	 * @param y
	 * @return 値
	 */
	public Object bitOr(Object x, Object y);

	/**
	 * ビット排他的論理和.
	 *
	 * @param x
	 * @param y
	 * @return 値
	 */
	public Object bitXor(Object x, Object y);

	/**
	 * 否定.
	 *
	 * @param x
	 * @return 値
	 */
	public Object not(Object x);

	/**
	 * 等号.
	 *
	 * @param x
	 * @param y
	 * @return 値
	 */
	public Object equal(Object x, Object y);

	/**
	 * 不等号.
	 *
	 * @param x
	 * @param y
	 * @return 値
	 */
	public Object notEqual(Object x, Object y);

	/**
	 * より小.
	 *
	 * @param x
	 * @param y
	 * @return 値
	 */
	public Object lessThan(Object x, Object y);

	/**
	 * 以下.
	 *
	 * @param x
	 * @param y
	 * @return 値
	 */
	public Object lessEqual(Object x, Object y);

	/**
	 * より大.
	 *
	 * @param x
	 * @param y
	 * @return 値
	 */
	public Object greaterThan(Object x, Object y);

	/**
	 * 以上.
	 *
	 * @param x
	 * @param y
	 * @return 値
	 */
	public Object greaterEqual(Object x, Object y);

	/**
	 * 真偽値.
	 * <p>
	 * オブジェクトを真偽値に変換する。
	 * </p>
	 *
	 * @param x
	 * @return 真偽値
	 */
	public boolean bool(Object x);

	/**
	 * インクリメント・デクリメント
	 *
	 * @param x
	 * @param inc
	 *            インクリメントのとき+1、デクリメントのとき-1
	 * @return 値
	 */
	public Object inc(Object x, int inc);

	/**
	 * 文字へ変換.
	 * <p>
	 * シングルクォーテーションで囲まれた文字列を値に変換する。<br>
	 * エスケープ文字を解釈したい場合等は当メソッドで変換する。
	 * </p>
	 *
	 * @param word
	 *            値
	 * @param exp
	 *            Expression
	 * @return 値
	 * @see CharExpression
	 * @since eval16
	 */
	public Object character(String word, AbstractExpression exp)
			throws EvalException;

	/**
	 * 文字列へ変換.
	 * <p>
	 * ダブルクォーテーションで囲まれた文字列を値に変換する。<br>
	 * 基本的には、引数のwordをそのまま返すことになるだろう。<br>
	 * エスケープ文字を解釈したい場合等は当メソッドで変換する。
	 * </p>
	 *
	 * @param word
	 *            値
	 * @param exp
	 *            Expression
	 * @return 値
	 * @see StringExpression
	 * @since eval16
	 */
	public Object string(String word, AbstractExpression exp)
			throws EvalException;

	/**
	 * 数値へ変換.
	 * <p>
	 * 数字のみで構成された文字列を値に変換する。<br>
	 * 例えば「123」という文字列をInteger型の123に変換して返す。
	 * </p>
	 *
	 * @param word
	 *            値
	 * @param exp
	 *            Expression
	 * @return 値
	 * @see NumberExpression
	 * @since eval16
	 */
	public Object number(String word, AbstractExpression exp)
			throws EvalException;

	/**
	 * 変数から取得した値を変換.
	 * <p>
	 * 変数から取得した値に対して、型に応じて独自の変換を行いたい場合に当メソッドで変換する。
	 * </p>
	 *
	 * @param value
	 *            値
	 * @param exp
	 *            Expression
	 * @return 値
	 * @since eval16
	 */
	public Object variable(Object value, AbstractExpression exp)
			throws EvalException;
}
