package jp.hishidama.eval.lex;

import java.util.ArrayList;
import java.util.List;

import jp.hishidama.eval.exp.ShareExpValue;
import jp.hishidama.eval.lex.comment.BlockComment;
import jp.hishidama.eval.lex.comment.CommentLex;
import jp.hishidama.eval.lex.comment.LineComment;
import jp.hishidama.eval.rule.ShareRuleValue;

/**
 * Lexファクトリー.
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 * @since 2007.02.16
 * @version eval16
 */
public class LexFactory {

	/** デフォルトコメント文字列一覧 */
	protected List<CommentLex> defaultCommentList = new ArrayList<CommentLex>();

	/**
	 * コンストラクター.
	 */
	public LexFactory() {
		initCommentList();
	}

	protected void initCommentList() {
		defaultCommentList.add(new BlockComment("/*", "*/"));
		defaultCommentList.add(new LineComment("//"));
	}

	/**
	 * デフォルトコメント文字列設定.
	 *
	 * @param list
	 *            コメント文字列一覧
	 * @since eval16
	 */
	public void setDefaultCommentLexList(List<CommentLex> list) {
		defaultCommentList = list;
	}

	/**
	 * Lexインスタンス生成.
	 *
	 * @param str
	 *            字句解析対象文字列
	 * @param opeList
	 *            演算子群
	 * @param share
	 *            ルール共通情報
	 * @param exp
	 *            構文解析木共通情報
	 * @return Lexインスタンス
	 */
	public Lex create(String str, List<String>[] opeList, ShareRuleValue share,
			ShareExpValue exp) {
		Lex lex = new Lex(str, opeList, share.paren, exp);
		if (defaultCommentList != null) {
			lex.setCommentLexList(defaultCommentList);
		}
		return lex;
	}
}
