package jp.hishidama.eval.lex;

import java.util.*;

import jp.hishidama.eval.exp.AbstractExpression;
import jp.hishidama.eval.exp.ShareExpValue;
import jp.hishidama.eval.lex.comment.CommentLex;
import jp.hishidama.util.CharUtil;

/**
 * 字句解析クラス.
 * <p>
 * 文字列の解析中の位置を保持する。
 * </p>
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 * @version eval16
 */
public class Lex {

	/**
	 * 演算子群.
	 * <p>
	 * 字句解析で演算子と認識する文字列。 演算子の文字数によって配列を分けている。 （長い演算子から先にマッチさせる為）
	 * </p>
	 */
	protected List<String>[] opeList;

	/** 解析対象文字列. */
	protected String string;

	/** 解析中の現在位置. */
	protected int pos = 0;

	/** 現在の解釈結果の文字列の長さ. */
	protected int len = 0;

	/** 現在の解釈結果を表すタイプ. */
	protected int type = TYPE_ERR;

	/** 解釈タイプ：識別子. */
	public static final int TYPE_WORD = 0x7ffffff0;

	/**
	 * 解釈タイプ：識別子(数値).
	 *
	 * @since 2007.02.15
	 */
	public static final int TYPE_NUM = 0x7ffffff1;

	/** 解釈タイプ：演算子. */
	public static final int TYPE_OPE = 0x7ffffff2;

	/**
	 * 解釈タイプ：識別子(文字列).
	 *
	 * @since 2007.02.21
	 */
	public static final int TYPE_STRING = 0x7ffffff3;

	/**
	 * 解釈タイプ：識別子(文字).
	 *
	 * @since 2007.02.21
	 */
	public static final int TYPE_CHAR = 0x7ffffff4;

	/** 解釈タイプ：解釈終了. */
	public static final int TYPE_EOF = 0x7fffffff;

	/** 解釈タイプ：エラー. */
	public static final int TYPE_ERR = -1;

	/**
	 * 演算子の文字列.
	 * <p>
	 * タイプが演算子のときのみ有効。
	 * </p>
	 */
	protected String ope;

	/**
	 * Expressionの共有情報.
	 *
	 * @since 2007.02.09
	 */
	protected ShareExpValue expShare;

	/**
	 * コンストラクター.
	 *
	 * @param str
	 *            対象文字列
	 * @param exp
	 */
	protected Lex(String str, List<String>[] lists, AbstractExpression paren,
			ShareExpValue exp) {
		this.string = str;
		this.opeList = lists;
		expShare = exp;
		if (expShare.paren == null) {
			expShare.paren = paren;
		}
	}

	/**
	 * 空白文字群.
	 * <p>
	 * 空白と解釈してスキップする文字。
	 * </p>
	 */
	protected String SPC_CHAR = " \t\r\n";

	/**
	 * 空白判断.
	 * <p>
	 * 指定された位置の文字が空白かどうかチェックする。<br>
	 * 文字列の長さを超えている場合も空白とする。
	 * </p>
	 *
	 * @param pos
	 *            位置
	 * @return 空白のとき、true
	 * @see #SPC_CHAR
	 */
	protected boolean isSpace(int pos) {
		if (pos >= string.length()) {
			return true;
		}
		char c = string.charAt(pos);
		return SPC_CHAR.indexOf(c) >= 0;
	}

	/**
	 * コメント文字列一覧.
	 */
	protected List<CommentLex> commentList = null;

	/**
	 * コメント文字列一覧設定.
	 *
	 * @param list
	 *            コメント文字列一覧
	 * @since eval16
	 */
	public void setCommentLexList(List<CommentLex> list) {
		commentList = list;
	}

	/**
	 * コメント文字列一覧取得.
	 *
	 * @return コメント文字列一覧
	 * @since eval16
	 */
	public List<CommentLex> getCommentLexList() {
		return commentList;
	}

	/**
	 * コメント判断.
	 *
	 * @param pos
	 *            位置
	 * @return コメントのとき、そのコメントを解釈するオブジェクト
	 * @since 2010.02.11
	 */
	protected CommentLex isCommentTop(int pos) {
		if (commentList != null) {
			for (CommentLex c : commentList) {
				if (c.isTop(string, pos) >= 0) {
					return c;
				}
			}
		}
		return null;
	}

	/**
	 * 数値先頭文字判断.
	 * <p>
	 * 文字列の先頭が数字であれば数値と判断するので、その先頭文字をチェックする。
	 * </p>
	 *
	 * @param pos
	 *            位置
	 * @return 数字のとき、true
	 * @since 2007.02.15
	 */
	protected boolean isNumberTop(int pos) {
		if (pos >= string.length()) {
			return false;
		}
		char c = string.charAt(pos);
		return ('0' <= c && c <= '9');
	}

	/**
	 * 数字扱い文字.
	 * <p>
	 * 演算子として使われそうな記号だが、数字として扱う文字。小数点など。
	 * </p>
	 *
	 * @since 2007.02.15
	 */
	protected String NUMBER_CHAR = "._";

	/**
	 * 特殊数字判断.
	 * <p>
	 * 演算子として使われそうな記号だが、数字として扱う特殊な文字かどうかをチェックする。<br>
	 * 例えばピリオド「.」はオブジェクトとメンバーの区切りに使うが、数値の中で使われたときは小数点として優先的に扱う必要がある。
	 * </p>
	 *
	 * @param pos
	 *            位置
	 * @return 数字扱いのとき、true
	 * @see #NUMBER_CHAR
	 * @since 2007.02.15
	 */
	protected boolean isSpecialNumber(int pos) {
		if (pos >= string.length()) {
			return false;
		}
		char c = string.charAt(pos);
		return NUMBER_CHAR.indexOf(c) >= 0;
	}

	/**
	 * 演算子判断.
	 * <p>
	 * 指定された位置の文字が演算子かどうかチェックする。
	 * </p>
	 *
	 * @param pos
	 *            位置
	 * @return 演算子の場合、演算子の文字列<br>
	 *         それ以外の場合、null
	 */
	protected String isOperator(int pos) {
		// 演算子の文字数の多い方からチェック
		for (int i = opeList.length - 1; i >= 0; i--) {
			if (pos + i >= string.length()) {
				continue;
			}
			List<String> list = opeList[i];
			if (list != null) {
				ope: for (int j = 0; j < list.size(); j++) {
					String ope = list.get(j);
					for (int k = 0; k <= i; k++) {
						char c = string.charAt(pos + k);
						char o = ope.charAt(k);
						if (c != o) {
							continue ope;
						}
					}
					return ope;
				}
			}
		}
		return null;
	}

	/**
	 * 文字列先頭文字判断.
	 *
	 * @param pos
	 *            位置
	 * @return 文字列かどうか
	 * @since 2007.02.21
	 */
	protected boolean isStringTop(int pos) {
		if (pos >= string.length()) {
			return false;
		}
		char c = string.charAt(pos);
		return c == '\"';
	}

	/**
	 * 文字列終了文字判断.
	 *
	 * @param pos
	 *            位置
	 * @return 文字列かどうか
	 * @since 2007.02.21
	 */
	protected boolean isStringEnd(int pos) {
		return isStringTop(pos);
	}

	/**
	 * 文字先頭文字判断.
	 *
	 * @param pos
	 *            位置
	 * @return 文字かどうか
	 * @since 2007.02.21
	 */
	protected boolean isCharTop(int pos) {
		if (pos >= string.length()) {
			return false;
		}
		char c = string.charAt(pos);
		return c == '\'';
	}

	/**
	 * 文字終了文字判断.
	 *
	 * @param pos
	 *            位置
	 * @return 文字かどうか
	 * @since 2007.02.21
	 */
	protected boolean isCharEnd(int pos) {
		return isCharTop(pos);
	}

	/**
	 * 現在位置解釈.
	 * <p>
	 * 現在の位置を解釈し、内部状態をセットする。
	 * </p>
	 */
	public void check() {
		// 空白判断
		for (;;) {
			if (isSpace(pos)) {
				if (pos >= string.length()) {
					// 文字数を超えたら終了
					type = TYPE_EOF;
					len = 0;
					return;
				}
				pos++;
				continue;
			}
			CommentLex comment = isCommentTop(pos);
			if (comment != null) {
				pos += comment.topLength();
				int end = comment.skip(string, pos);
				if (end < 0) {
					type = TYPE_EOF;
					return;
				}
				pos = end;
				continue;
			}
			break;
		}

		// 文字列判断
		if (isStringTop(pos)) {
			processString();
			return;
		}
		// 文字判断
		if (isCharTop(pos)) {
			processChar();
			return;
		}

		// 演算子判断
		String ope = isOperator(pos);
		if (ope != null) {
			this.type = TYPE_OPE;
			this.ope = ope;
			this.len = ope.length();
			return;
		}

		// 上記以外は識別子
		boolean number = isNumberTop(pos);
		type = number ? TYPE_NUM : TYPE_WORD;
		for (len = 1;; len++) {
			if (isSpace(pos + len) || isCommentTop(pos + len) != null) {
				break;
			}
			if (number && isSpecialNumber(pos + len)) {
				continue;
			}
			if (isOperator(pos + len) != null) {
				break;
			}
		}
	}

	protected void processString() {
		int[] ret = new int[1];
		type = TYPE_STRING;
		for (len = 1;;) {
			if (isStringEnd(pos + len)) {
				len++;
				return;
			}
			len += getCharLen(pos + len, ret);
			if (pos + len >= string.length()) {
				type = TYPE_EOF;
				break;
			}
		}
	}

	protected void processChar() {
		int[] ret = new int[1];
		type = TYPE_CHAR;
		for (len = 1;;) {
			if (isCharEnd(pos + len)) {
				len++;
				return;
			}
			len += getCharLen(pos + len, ret);
			if (pos + len >= string.length()) {
				type = TYPE_EOF;
				break;
			}
		}
	}

	protected int getCharLen(int pos, int[] ret) {
		CharUtil.escapeChar(string, pos, string.length(), ret);
		return ret[0];
	}

	/**
	 * 次位置解釈.
	 * <p>
	 * 次の位置へ移動し、解釈して内部状態をセットする。
	 * </p>
	 *
	 * @return 自分自身
	 */
	public Lex next() {
		pos += len;
		check();
		return this;
	}

	/**
	 * タイプ取得.
	 *
	 * @return タイプ
	 */
	public int getType() {
		return type;
	}

	/**
	 * 演算子取得.
	 * <p>
	 * タイプが演算子のときだけ有効。
	 * </p>
	 *
	 * @return 演算子
	 */
	public String getOperator() {
		return ope;
	}

	/**
	 * 演算子比較.
	 *
	 * @param ope
	 *            演算子
	 * @return 現在位置が引数と等しい演算子のとき、true
	 */
	public boolean isOperator(String ope) {
		if (type == TYPE_OPE) {
			return this.ope.equals(ope);
		}
		return false;
	}

	/**
	 * 識別子取得.
	 *
	 * @return 識別子
	 */
	public String getWord() {
		return string.substring(pos, pos + len);
	}

	/**
	 * 文字列取得.
	 * <p>
	 * 解釈中の文字列全体を返す。
	 * </p>
	 *
	 * @return 文字列
	 */
	public String getString() {
		return string;
	}

	/**
	 * 位置取得.
	 *
	 * @return 解釈中の位置
	 */
	public int getPos() {
		return pos;
	}

	/**
	 * Expression共有情報取得.
	 *
	 * @return 共有情報
	 * @since 2007.02.09
	 */
	public ShareExpValue getShare() {
		return expShare;
	}
}
