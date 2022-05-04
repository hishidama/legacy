package jp.hishidama.eval.exp;

import jp.hishidama.eval.EvalException;
import jp.hishidama.eval.lex.Lex;

/**
 * 数値クラス.
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 */
public class NumberExpression extends WordExpression {

	public static final String NAME = "number";

	@Override
	public String getExpressionName() {
		return NAME;
	}

	/**
	 * 式インスタンス生成（数値用）.
	 * 
	 * @param lex
	 *            解釈中位置
	 * @param prio
	 *            優先順位
	 * @return 式インスタンス
	 * @since 2007.02.15
	 */
	public static AbstractExpression create(Lex lex, int prio) {
		AbstractExpression exp = new NumberExpression(lex.getWord());
		exp.setPos(lex.getString(), lex.getPos());
		exp.setPriority(prio);
		exp.share = lex.getShare();
		return exp;
	}

	public NumberExpression(String str) {
		super(str);
	}

	protected NumberExpression(NumberExpression from, ShareExpValue s) {
		super(from, s);
	}

	@Override
	public AbstractExpression dup(ShareExpValue s) {
		return new NumberExpression(this, s);
	}

	public static NumberExpression create(AbstractExpression from, String word) {
		NumberExpression n = new NumberExpression(word);
		n.string = from.string;
		n.pos = from.pos;
		n.prio = from.prio;
		n.share = from.share;
		return n;
	}

	@Override
	public Object eval() {
		try {
			return share.oper.number(word, this);
		} catch (EvalException e) {
			throw e;
		} catch (Exception e) {
			throw new EvalException(EvalException.EXP_NOT_NUMBER, this, e);
		}
	}
}
