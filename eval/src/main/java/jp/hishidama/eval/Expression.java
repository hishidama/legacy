package jp.hishidama.eval;

import jp.hishidama.eval.exp.AbstractExpression;
import jp.hishidama.eval.func.Function;
import jp.hishidama.eval.log.EvalLog;
import jp.hishidama.eval.oper.DoubleOperator;
import jp.hishidama.eval.oper.IntOperator;
import jp.hishidama.eval.oper.LongOperator;
import jp.hishidama.eval.oper.Operator;
import jp.hishidama.eval.ref.Refactor;
import jp.hishidama.eval.repl.Replace;
import jp.hishidama.eval.srch.Search;
import jp.hishidama.eval.var.Variable;

/**
 * 式クラス.
 * <p>
 * 構文解析木を保持し、演算の評価を実施する。
 * </p>
 *
 * @see Rule#parse(String)
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 * @version eval16
 */
public abstract class Expression {

	public Variable var;

	public Function func;

	public Operator oper;

	public EvalLog log;

	public Search srch;

	public Replace repl;

	protected AbstractExpression ae;

	/**
	 * 構文解析実行.
	 * <p>
	 * デフォルトルールで構文解析を行う。
	 * </p>
	 *
	 * @param str
	 *            解析対象文字列
	 * @return 構文解析結果
	 * @throws EvalException
	 *             構文がおかしいとき
	 * @see ExpRuleFactory#getDefaultRule()
	 * @see Rule#parse(String)
	 */
	public static Expression parse(String str) {
		return ExpRuleFactory.getDefaultRule().parse(str);
	}

	/**
	 * 変数群設定.
	 * <p>
	 * 評価実行の際に 式の中に変数があれば、当メソッドで指定した変数オブジェクトのメソッドが呼ばれる。
	 * </p>
	 *
	 * @param var
	 *            変数オブジェクト
	 * @see #evalInt()
	 * @see #evalLong()
	 * @see #evalDouble()
	 * @see #eval()
	 * @since 2007.02.09
	 */
	public void setVariable(Variable var) {
		this.var = var;
	}

	/**
	 * 関数群設定.
	 * <p>
	 * 評価実行の際に 式の中に関数があれば、当メソッドで指定した関数オブジェクトのメソッドが呼ばれる。
	 * </p>
	 *
	 * @param func
	 *            関数オブジェクト
	 * @see #evalInt()
	 * @see #evalLong()
	 * @see #evalDouble()
	 * @see #eval()
	 */
	public void setFunction(Function func) {
		this.func = func;
	}

	/**
	 * 演算設定.
	 * <p>
	 * 評価実行の際に 式の中に演算（+や-等）があれば、指定した演算オブジェクトのメソッドが呼ばれる。
	 * </p>
	 *
	 * @param oper
	 *            演算オブジェクト
	 * @see #eval()
	 * @since 2007.02.15
	 */
	public void setOperator(Operator oper) {
		this.oper = oper;
	}

	/**
	 * ログ出力設定.
	 * <p>
	 * 評価実行の際に指定したログ出力オブジェクトのメソッドが呼ばれる。
	 * </p>
	 *
	 * @param log
	 *            ログ出力オブジェクト
	 * @see #eval()
	 * @since eval16
	 */
	public void setEvalLog(EvalLog log) {
		this.log = log;
	}

	/**
	 * 評価実行(int).
	 *<p>
	 * 当メソッドでは、{@link IntOperator}を使用して演算する。
	 * </p>
	 *
	 * @return 演算結果
	 * @throws EvalException
	 *             演算中にエラーが発生したとき
	 * @see #setOperator(Operator)
	 * @see IntOperator
	 * @since eval16
	 */
	public int evalInt() {
		Operator bak = oper;
		try {
			if (!(oper instanceof IntOperator)) {
				setOperator(new IntOperator());
			}
			Number n = (Number) eval();
			if (n != null) {
				return n.intValue();
			} else {
				return 0;
			}
		} finally {
			oper = bak;
		}
	}

	/**
	 * 評価実行(long).
	 *<p>
	 * 当メソッドでは、{@link LongOperator}を使用して演算する。
	 * </p>
	 *
	 * @return 演算結果
	 * @throws EvalException
	 *             演算中にエラーが発生したとき
	 * @see #setOperator(Operator)
	 * @see LongOperator
	 */
	public long evalLong() {
		Operator bak = oper;
		try {
			if (!(oper instanceof LongOperator)) {
				setOperator(new LongOperator());
			}
			Number n = (Number) eval();
			if (n != null) {
				return n.longValue();
			} else {
				return 0;
			}
		} finally {
			oper = bak;
		}
	}

	/**
	 * 評価実行(double).
	 *<p>
	 * 当メソッドでは、{@link DoubleOperator}を使用して演算する。
	 * </p>
	 *
	 * @return 演算結果
	 * @throws EvalException
	 *             演算中にエラーが発生したとき
	 * @see #setOperator(Operator)
	 * @see DoubleOperator
	 */
	public double evalDouble() {
		Operator bak = oper;
		try {
			if (!(oper instanceof DoubleOperator)) {
				setOperator(new DoubleOperator());
			}
			Number n = (Number) eval();
			if (n != null) {
				return n.doubleValue();
			} else {
				return 0;
			}
		} finally {
			oper = bak;
		}
	}

	/**
	 * 評価実行(Object).
	 * <p>
	 * Object型で演算を実施して結果を返す。<br>
	 * 演算実行クラスを登録する必要あり。
	 * </p>
	 *
	 * @see #setOperator(Operator)
	 * @return 演算結果
	 * @throws EvalException
	 *             演算中にエラーが発生したとき
	 * @since 2007.02.15
	 */
	public abstract Object eval();

	/**
	 * 最適化実行(Object).
	 * <p>
	 * 超簡易最適化を行う。演算は指定されたoperを使って行う。<br>
	 * 変数に値が入っている場合、定数と見なし、値に置換する。
	 * </p>
	 *
	 * @param var
	 *            定数として扱う変数群（null可）
	 * @param oper
	 *            演算実行クラス
	 * @throws EvalException
	 *             最適化中にエラーが発生したとき
	 * @since 2007.02.21
	 */
	public abstract void optimize(Variable var, Operator oper);

	/**
	 * 探索実行.
	 * <p>
	 * 構文解析木の探索を行う。<br>
	 * 全構文解析木について、1つずつ探索インターフェースのメソッドを呼び出す。
	 * </p>
	 *
	 * @param srch
	 *            探索インターフェース
	 * @see Search#search(AbstractExpression)
	 * @since 2007.02.17
	 */
	public abstract void search(Search srch);

	/**
	 * リファクタリング（識別子名変更）.
	 * <p>
	 * 変数名/関数名あるいはオブジェクトのフィールド名/メソッド名を変換する。
	 * </p>
	 * <p>
	 * 式の中にオブジェクトのメンバーが存在する場合は、オブジェクトを取得して変更対象のオブジェクトかどうか判定する。<br>
	 * したがって、オブジェクトが在る場合はsetVariable()でオブジェクトの変数を返すようにしておく必要がある。
	 * </p>
	 *
	 * @param ref
	 *            リファクタリングインターフェース
	 * @since 2007.02.19
	 */
	public abstract void refactorName(Refactor ref);

	/**
	 * リファクタリング（関数への変更）.
	 * <p>
	 * 変数名あるいはオブジェクトのフィールド名を関数（あるいはメソッド）に変換する。
	 * </p>
	 * <p>
	 * 式の中にオブジェクトのメンバーが存在する場合は、オブジェクトを取得して変更対象のオブジェクトかどうか判定する。<br>
	 * したがって、オブジェクトが在る場合はsetVariable()でオブジェクトの変数を返すようにしておく必要がある。
	 * </p>
	 *
	 * @param ref
	 *            リファクタリングインターフェース
	 * @param rule
	 *            変換時に使用するルール
	 * @since 2007.02.20
	 */
	public abstract void refactorFunc(Refactor ref, Rule rule);

	/**
	 * 複製作成.
	 * <p>
	 * 当インスタンスの複製を作成する。
	 * </p>
	 *
	 * @return 複製
	 * @since 2007.02.17
	 */
	public abstract Expression dup();

	/**
	 * オブジェクト比較.
	 * <p>
	 * 演算解析結果が等しいかどうかをチェックする。<br>
	 * 演算子の文字列表現の違いは意識しない。
	 * </p>
	 *
	 * @param obj
	 *            オブジェクト
	 * @return 等しいとき、true
	 * @see #same(Expression)
	 * @since 2007.02.27
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Expression) {
			AbstractExpression e = ((Expression) obj).ae;
			if (ae == null && e == null) {
				return true;
			}
			if (ae == null || e == null) {
				return false;
			}
			return ae.equals(e);
		}
		return super.equals(obj);
	}

	/**
	 * ハッシュコード値取得.
	 *
	 * @return ハッシュコード値
	 * @since 2007.02.27
	 */
	@Override
	public int hashCode() {
		if (ae == null) {
			return 0;
		}
		return ae.hashCode();
	}

	/**
	 * オブジェクト比較.
	 * <p>
	 * 演算子の文字列表現まで含めてオブジェクトが等しいかどうかチェックする。
	 * </p>
	 *
	 * @param obj
	 *            比較対象
	 * @return 等しいとき、true
	 * @see #equals(Object)
	 * @since 2007.02.27
	 */
	public boolean same(Expression obj) {
		AbstractExpression e = obj.ae;
		if (ae == null) {
			return e == null;
		}
		return ae.same(e);
	}

	/**
	 * 空チェック.
	 * <p>
	 * 解析結果が空かどうかをチェックする。
	 * </p>
	 *
	 * @return 空のとき、true
	 * @since 2007.03.01
	 */
	public boolean isEmpty() {
		return ae == null;
	}

	@Override
	public String toString() {
		if (ae == null) {
			return "";
		}
		return ae.toString();
	}
}
