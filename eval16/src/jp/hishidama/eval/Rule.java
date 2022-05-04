package jp.hishidama.eval;

import jp.hishidama.eval.func.Function;
import jp.hishidama.eval.log.EvalLog;
import jp.hishidama.eval.oper.Operator;
import jp.hishidama.eval.var.Variable;

/**
 * ルールクラス.
 * <p>
 * 構文解析ルール関連のユーザー窓口。
 * </p>
 *
 * @see jp.hishidama.eval.ExpRuleFactory#getRule()
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 * @version eval16
 */
public abstract class Rule implements Cloneable {

	protected Variable defaultVar = null;

	protected Function defaultFunc = null;

	protected Operator defaultOper = null;

	protected EvalLog defaultLog = null;

	/**
	 * デフォルト変数群取得.
	 *
	 * @return デフォルトの変数群
	 * @since eval16
	 */
	public Variable getDefaultVariable() {
		return defaultVar;
	}

	/**
	 * デフォルト変数群を設定したルールを返す。
	 *
	 * @param var
	 *            変数群
	 * @return デフォルト値が設定されたルール
	 * @since eval16
	 */
	public Rule defaultVariable(Variable var) {
		Rule rule = this.clone();
		rule.defaultVar = var;
		return rule;
	}

	/**
	 * デフォルト関数群取得.
	 *
	 * @return デフォルトの関数群
	 * @since eval16
	 */
	public Function getDefaultFunction() {
		return defaultFunc;
	}

	/**
	 * デフォルト関数群を設定したルールを返す。
	 *
	 * @param func
	 *            関数群
	 * @return デフォルト値が設定されたルール
	 */
	public Rule defaultFunction(Function func) {
		Rule rule = this.clone();
		rule.defaultFunc = func;
		return rule;
	}

	/**
	 * デフォルト演算群取得.
	 *
	 * @return デフォルトの演算
	 * @since eval16
	 */
	public Operator getDefaultOperator() {
		return defaultOper;
	}

	/**
	 * デフォルト演算群を設定したルールを返す。
	 *
	 * @param oper
	 *            演算
	 * @return デフォルト値が設定されたルール
	 * @since eval16
	 */
	public Rule defaultOperator(Operator oper) {
		Rule rule = this.clone();
		rule.defaultOper = oper;
		return rule;
	}

	/**
	 * デフォルトログ出力取得.
	 *
	 * @return デフォルトのログ出力オブジェクト
	 * @since eval16
	 */
	public EvalLog getDefaultEvalLog() {
		return defaultLog;
	}

	/**
	 * デフォルトログ出力を設定したルールを返す。
	 *
	 * @param log
	 *            ログ出力オブジェクト
	 * @return デフォルト値が設定されたルール
	 * @since eval16
	 */
	public Rule defaultEvalLog(EvalLog log) {
		Rule rule = this.clone();
		rule.defaultLog = log;
		return rule;
	}

	/**
	 * 構文解析
	 *
	 * @param str
	 *            解析対象文字列
	 * @return 構文解析結果
	 */
	public abstract Expression parse(String str);

	@Override
	protected Rule clone() {
		try {
			return (Rule) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e.toString());
		}
	}
}
