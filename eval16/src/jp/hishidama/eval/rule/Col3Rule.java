package jp.hishidama.eval.rule;

import jp.hishidama.eval.EvalException;
import jp.hishidama.eval.exp.*;
import jp.hishidama.eval.lex.Lex;

/**
 * 三項演算子ルールクラス.
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 * @version 2007.02.17
 */
public class Col3Rule extends AbstractRule {

	/** 条件演算子 */
	public AbstractExpression cond;

	public Col3Rule(ShareRuleValue share) {
		super(share);
	}

	@Override
	protected AbstractExpression parse(Lex lex) {
		AbstractExpression x = nextRule.parse(lex);
		switch (lex.getType()) {
		case Lex.TYPE_OPE:
			String ope = lex.getOperator();
			int pos = lex.getPos();
			if (isMyOperator(ope)) {
				if (lex.isOperator(cond.getOperator())) {
					x = parseCond(lex, x, ope, pos);
				}
			}
			return x;
		default:
			return x;
		}
	}

	/**
	 * 条件演算構文解析.
	 *
	 * @param lex
	 * @param x
	 * @param ope
	 * @param pos
	 * @return Expression
	 */
	protected AbstractExpression parseCond(Lex lex, AbstractExpression x,
			String ope, int pos) {
		AbstractExpression y = this.parse(lex.next());
		if (!lex.isOperator(cond.getEndOperator())) {
			throw new EvalException(EvalException.PARSE_NOT_FOUND_END_OP,
					new String[] { cond.getEndOperator() }, lex);
		}
		AbstractExpression z = this.parse(lex.next());
		x = Col3Expression.create(newExpression(ope, lex.getShare()), lex
				.getString(), pos, x, y, z);
		return x;
	}
}
