package jp.hishidama.eval.rule;

import jp.hishidama.eval.exp.*;
import jp.hishidama.eval.lex.Lex;

/**
 * 二項演算子（右結合）ルールクラス.
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html">ひしだま</a>
 */
public class Col2RightJoinRule extends AbstractRule {

	public Col2RightJoinRule(ShareRuleValue share) {
		super(share);
	}

	@Override
	protected AbstractExpression parse(Lex lex) {
		AbstractExpression x = nextRule.parse(lex);
		switch (lex.getType()) {
		case Lex.TYPE_OPE:
			String ope = lex.getOperator();
			if (isMyOperator(ope)) {
				int pos = lex.getPos();
				AbstractExpression y = this.parse(lex.next());
				x = Col2Expression.create(newExpression(ope, lex.getShare()),
						lex.getString(), pos, x, y);
			}
			return x;
		default:
			return x;
		}
	}
}
