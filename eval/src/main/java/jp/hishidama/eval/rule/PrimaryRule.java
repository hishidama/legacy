package jp.hishidama.eval.rule;

import jp.hishidama.eval.EvalException;
import jp.hishidama.eval.exp.*;
import jp.hishidama.eval.lex.Lex;

/**
 * 基本要素ルールクラス.
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html">ひしだま</a>
 * @version 2007.02.21
 */
public class PrimaryRule extends AbstractRule {

	public PrimaryRule(ShareRuleValue share) {
		super(share);
	}

	@Override
	public final AbstractExpression parse(Lex lex) {
		while (true) {
			switch (lex.getType()) {
			case Lex.TYPE_NUM:
				AbstractExpression n = NumberExpression.create(lex, prio);
				lex.next();
				return n;
			case Lex.TYPE_WORD:
				AbstractExpression w = VariableExpression.create(lex, prio);
				lex.next();
				return w;
			case Lex.TYPE_STRING:
				AbstractExpression s = StringExpression.create(lex, prio);
				lex.next();
				return s;
			case Lex.TYPE_CHAR:
				AbstractExpression c = CharExpression.create(lex, prio);
				lex.next();
				return c;
			case Lex.TYPE_OPE:
				String ope = lex.getOperator();
				int pos = lex.getPos();
				if (isMyOperator(ope)) {
					if (ope.equals(share.paren.getOperator())) {
						return parseParen(lex, ope, pos);
					}
					return Col1Expression.create(newExpression(ope, lex
							.getShare()), lex.getString(), pos, parse(lex
							.next()));
				}
				throw new EvalException(EvalException.PARSE_INVALID_OP, lex);
			case Lex.TYPE_EOF:
				throw new EvalException(EvalException.PARSE_END_OF_STR, lex);
			default:
				throw new EvalException(EvalException.PARSE_INVALID_CHAR, lex);
			}
		}
	}

	/**
	 * 小括弧構文解析.
	 * 
	 * @param lex
	 * @param ope
	 * @param pos
	 * @return Expression
	 */
	protected AbstractExpression parseParen(Lex lex, String ope, int pos) {
		AbstractExpression s = share.topRule.parse(lex.next());
		if (!lex.isOperator(share.paren.getEndOperator())) {
			// 閉じ括弧でない場合
			throw new EvalException(EvalException.PARSE_NOT_FOUND_END_OP,
					new String[] { share.paren.getEndOperator() }, lex);
		}
		lex.next();
		return Col1Expression.create(newExpression(ope, lex.getShare()), lex
				.getString(), pos, s);
	}

}
