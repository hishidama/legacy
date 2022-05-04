package jp.hishidama.eval;

import jp.hishidama.eval.exp.AbstractExpression;
import jp.hishidama.eval.lex.Lex;

/**
 * 演算エラークラス.
 * <p>
 * 構文解析に失敗した場合や評価の実行に失敗した場合にスローされる。
 * </p>
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 * @version 2007.03.02
 */
@SuppressWarnings("serial")
public class EvalException extends RuntimeException {

	/**
	 * 閉じ括弧が存在しない.
	 * <p>
	 * %0：閉じ括弧
	 * </p>
	 */
	public static final int PARSE_NOT_FOUND_END_OP = 1001;

	/** 未定義の演算子 */
	public static final int PARSE_INVALID_OP = 1002;

	/** 未定義の文字 */
	public static final int PARSE_INVALID_CHAR = 1003;

	/** 解釈の途中で文字列が終了した */
	public static final int PARSE_END_OF_STR = 1004;

	/** 解釈が終了したのに文字列が残っている */
	public static final int PARSE_STILL_EXIST = 1005;

	/** 関数として扱えない */
	public static final int PARSE_NOT_FUNC = 1101;

	/**
	 * 禁止されたメソッドを呼び出した.
	 * <p>
	 * 考えられる原因：
	 * <ul>
	 * <li>必要なメソッドがオーバーライドされていない</li>
	 * <li>そのクラスでは、仕様上そのメソッドを呼んではいけない</li>
	 * </p>
	 */
	public static final int EXP_FORBIDDEN_CALL = 2001;

	/** 変数として扱えない */
	public static final int EXP_NOT_VARIABLE = 2002;

	/** 数値として扱えない */
	public static final int EXP_NOT_NUMBER = 2003;

	/** 代入できない */
	public static final int EXP_NOT_LET = 2004;

	/**
	 * 文字として扱えない
	 */
	public static final int EXP_NOT_CHAR = 2005;

	/**
	 * 文字列として扱えない
	 */
	public static final int EXP_NOT_STRING = 2006;

	/** 変数値が取得できない */
	public static final int EXP_NOT_VAR_VALUE = 2101;

	/** 変数に代入できない */
	public static final int EXP_NOT_LET_VAR = 2102;

	/** 変数が未定義 */
	public static final int EXP_NOT_DEF_VAR = 2103;

	/** オブジェクトが未定義 */
	public static final int EXP_NOT_DEF_OBJ = 2104;

	/** 配列値が取得できない */
	public static final int EXP_NOT_ARR_VALUE = 2201;

	/** 配列に代入できない */
	public static final int EXP_NOT_LET_ARR = 2202;

	/** フィールド値が取得できない */
	public static final int EXP_NOT_FLD_VALUE = 2301;

	/** フィールドに代入できない */
	public static final int EXP_NOT_LET_FIELD = 2302;

	/** 関数呼び出しの失敗 */
	public static final int EXP_FUNC_CALL_ERROR = 2401;

	/**
	 * エラーメッセージコード.
	 *
	 * @since 2007.03.02
	 */
	protected int msgCode;

	/**
	 * エラーメッセージのオプション.
	 *
	 * @since 2007.03.02
	 */
	protected String[] msgOpt;

	/** エラーの起きた文字列. */
	protected String string;

	/** 文字列中のエラーの起きた位置. */
	protected int pos = -1;

	/** 何を解析していたか. */
	protected String ename = "word";

	/** 解釈中の文字列. */
	protected String word;

	protected EvalException(RuntimeException e) {
		super(e);
	}

	/**
	 * コンストラクター
	 *
	 * @param msg
	 *            エラーメッセージコード
	 * @param lex
	 *            字句解析位置
	 * @since 2007.03.02
	 */
	public EvalException(int msg, Lex lex) {
		this(msg, null, lex);
	}

	/**
	 * コンストラクター
	 *
	 * @param msg
	 *            エラーメッセージコード
	 * @param opt
	 *            エラーメッセージオプション
	 * @param lex
	 *            字句解析位置
	 * @since 2007.03.02
	 */
	public EvalException(int msg, String[] opt, Lex lex) {
		this.msgCode = msg;
		this.msgOpt = opt;
		if (lex != null) {
			this.string = lex.getString();
			this.pos = lex.getPos();
			this.ename = "word";
			this.word = lex.getWord();
		}
	}

	/**
	 * コンストラクター
	 *
	 * @param msg
	 *            エラーメッセージコード
	 * @param exp
	 *            Expression
	 * @param e
	 *            原因となった例外
	 */
	public EvalException(int msg, AbstractExpression exp, Throwable e) {
		this(msg, exp.getExpressionName(), exp.getWord(), exp.getString(), exp
				.getPos(), e);
	}

	/**
	 * コンストラクター
	 *
	 * @param msg
	 *            エラーメッセージコード
	 * @param word
	 *            対象文字列
	 * @param exp
	 *            Expression
	 * @param e
	 *            原因となった例外
	 */
	public EvalException(int msg, String word, AbstractExpression exp,
			Throwable e) {
		this(msg, exp.getExpressionName(), word, exp.getString(), exp.getPos(),
				e);
	}

	/**
	 * コンストラクター
	 *
	 * @param msg
	 *            エラーメッセージコード
	 * @param expName
	 *            解析名
	 * @param word
	 *            対象文字列
	 * @param string
	 *            全文字列
	 * @param pos
	 *            位置
	 * @param e
	 *            原因となった例外
	 * @since 2007.03.02
	 */
	public EvalException(int msg, String expName, String word, String string,
			int pos, Throwable e) {
		initException(e);
		this.msgCode = msg;
		this.string = string;
		this.pos = pos;
		this.ename = expName;
		this.word = word;
	}

	protected void initException(Throwable e) {
		while (e != null) {
			if (e.getClass() == RuntimeException.class && e.getCause() != null) {
				e = e.getCause();
			} else {
				break;
			}
		}
		if (e != null) {
			super.initCause(e);
		}
	}

	/**
	 * エラーコード取得
	 *
	 * @return エラーコード
	 * @since 2007.03.02
	 */
	public int getErrorCode() {
		return msgCode;
	}

	/**
	 * メッセージ付加情報取得
	 *
	 * @return 付加情報
	 * @since 2007.03.02
	 */
	public String[] getOption() {
		return msgOpt;
	}

	/**
	 * 解析名称取得.
	 *
	 * @return 解析名
	 */
	public String getExpressionName() {
		return ename;
	}

	/**
	 * 対象識別子取得
	 *
	 * @return 文字列
	 * @since 2007.03.02
	 */
	public String getWord() {
		return word;
	}

	/**
	 * 解析前文字列取得
	 *
	 * @return 文字列
	 * @since 2007.03.02
	 */
	public String getString() {
		return string;
	}

	/**
	 * エラー発生位置取得
	 *
	 * @return 位置
	 * @since 2007.03.02
	 */
	public int getPos() {
		return pos;
	}

	/**
	 * @see EvalExceptionFormatter#toString(EvalException)
	 */
	@Override
	public String toString() {
		return EvalExceptionFormatter.getDefault().toString(this);
	}
}
