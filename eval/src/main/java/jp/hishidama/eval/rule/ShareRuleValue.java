package jp.hishidama.eval.rule;

import java.util.*;

import jp.hishidama.eval.EvalException;
import jp.hishidama.eval.Expression;
import jp.hishidama.eval.Rule;
import jp.hishidama.eval.exp.AbstractExpression;
import jp.hishidama.eval.exp.ShareExpValue;
import jp.hishidama.eval.lex.Lex;
import jp.hishidama.eval.lex.LexFactory;
import jp.hishidama.eval.oper.Operator;
import jp.hishidama.eval.ref.Refactor;
import jp.hishidama.eval.srch.Search;
import jp.hishidama.eval.var.Variable;

/**
 * ルール共通情報クラス.
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 * @version eval16
 */
public class ShareRuleValue extends Rule {
	public AbstractRule topRule;

	public AbstractRule funcArgRule;

	/**
	 * Lexを作成するファクトリー.
	 */
	public LexFactory lexFactory;

	/**
	 * 演算子群.
	 * <p>
	 * 字句解析で演算子と認識する文字列。 演算子の文字数によって配列を分けている。 （長い演算子から先にマッチさせる為）
	 * </p>
	 */
	@SuppressWarnings("unchecked")
	protected List<String>[] opeList = new List[4];

	/** 丸括弧の演算子 */
	public AbstractExpression paren;

	@Override
	public Expression parse(String str) {
		if (str == null) {
			return null;
		}
		if (str.trim().length() <= 0) {
			return new EmptyExpression();
		}

		ShareExpValue exp = new ShareExpValue();

		AbstractExpression x = parse(str, exp);
		exp.setAbstractExpression(x);

		if (defaultVar != null) {
			exp.setVariable(defaultVar);
		}
		if (defaultFunc != null) {
			exp.setFunction(defaultFunc);
		}
		if (defaultOper != null) {
			exp.setOperator(defaultOper);
		}
		if (defaultLog != null) {
			exp.setEvalLog(defaultLog);
		}

		return exp;
	}

	/**
	 * 構文解析（内部用）
	 *
	 * @param str
	 *            解析対象文字列
	 * @param exp
	 *            式共通情報
	 * @return 構文解析木
	 * @since 2007.02.20
	 */
	public AbstractExpression parse(String str, ShareExpValue exp) {
		if (str == null) {
			return null;
		}

		Lex lex = lexFactory.create(str, opeList, this, exp);
		lex.check();

		AbstractExpression x = topRule.parse(lex);
		if (lex.getType() != Lex.TYPE_EOF) {
			throw new EvalException(EvalException.PARSE_STILL_EXIST, lex);
		}
		return x;
	}

	/**
	 * 解析対象が無いときに返す空Expression
	 */
	class EmptyExpression extends Expression {

		@Override
		public Object eval() {
			return null;
		}

		@Override
		public void optimize(Variable var, Operator oper) {
		}

		@Override
		public void search(Search srch) {
		}

		@Override
		public void refactorName(Refactor ref) {
		}

		@Override
		public void refactorFunc(Refactor ref, Rule rule) {
		}

		@Override
		public Expression dup() {
			return new EmptyExpression();
		}

		@Override
		public boolean same(Expression obj) {
			if (obj instanceof EmptyExpression) {
				return true;
			}
			return false;
		}

		@Override
		public String toString() {
			return "";
		}
	}
}
