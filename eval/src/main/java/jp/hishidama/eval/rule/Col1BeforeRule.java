package jp.hishidama.eval.rule;

import jp.hishidama.eval.exp.Col1Expression;
import jp.hishidama.eval.exp.AbstractExpression;
import jp.hishidama.eval.lex.Lex;

/**
 * 単項前置演算子ルールクラス.
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 * @since 2006.10.27
 * @version 2006.10.31
 */
public class Col1BeforeRule extends AbstractRule {

	public Col1BeforeRule(ShareRuleValue share) {
		super(share);
	}

	@Override
	public AbstractExpression parse(Lex lex) {
		switch (lex.getType()) {
		case Lex.TYPE_OPE:
			String ope = lex.getOperator();
			if (isMyOperator(ope)) {
				int pos = lex.getPos();
				return Col1Expression.create(
						newExpression(ope, lex.getShare()), lex.getString(),
						pos, parse(lex.next()));
			}
			return nextRule.parse(lex);
		default:
			return nextRule.parse(lex);
		}
	}
}
