package jp.hishidama.eval.rule;

import jp.hishidama.eval.EvalException;
import jp.hishidama.eval.exp.*;
import jp.hishidama.eval.lex.Lex;

/**
 * 単項後置演算子ルールクラス.
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html">ひしだま</a>
 * @since 2006.10.27
 * @version 2007.02.17
 */
public class Col1AfterRule extends AbstractRule {

	/** 関数の演算子 */
	public AbstractExpression func;

	/** 配列の演算子 */
	public AbstractExpression array;

	/** フィールドの演算子 */
	public AbstractExpression field;

	public Col1AfterRule(ShareRuleValue share) {
		super(share);
	}

	@Override
	public AbstractExpression parse(Lex lex) {
		AbstractExpression x = nextRule.parse(lex);
		while (true) {
			switch (lex.getType()) {
			case Lex.TYPE_OPE:
				String ope = lex.getOperator();
				int pos = lex.getPos();
				if (isMyOperator(ope)) {
					if (lex.isOperator(func.getOperator())) {
						x = parseFunc(lex, x);
						continue;
					}
					if (lex.isOperator(array.getOperator())) {
						x = parseArray(lex, x, ope, pos);
						continue;
					}
					if (lex.isOperator(field.getOperator())) {
						x = parseField(lex, x, ope, pos);
						continue;
					}
					x = Col1Expression.create(
							newExpression(ope, lex.getShare()),
							lex.getString(), pos, x);
					lex.next();
					continue;
				}
				return x;
			default:
				return x;
			}
		}
	}

	/**
	 * 関数構文解析.
	 * 
	 * @param lex
	 * @param x
	 * @return Expression
	 */
	protected AbstractExpression parseFunc(Lex lex, AbstractExpression x) {
		AbstractExpression a = null; // 引数が無い関数の場合はnull
		lex.next();
		if (!lex.isOperator(func.getEndOperator())) {
			a = share.funcArgRule.parse(lex);
			if (!lex.isOperator(func.getEndOperator())) {
				throw new EvalException(EvalException.PARSE_NOT_FOUND_END_OP,
						new String[] { func.getEndOperator() }, lex);
			}
		}
		lex.next();
		x = FunctionExpression.create(x, a, prio, lex.getShare());
		return x;
	}

	/**
	 * 配列構文解析.
	 * 
	 * @param lex
	 * @param x
	 * @param ope
	 * @param pos
	 * @return Expression
	 */
	protected AbstractExpression parseArray(Lex lex, AbstractExpression x,
			String ope, int pos) {
		AbstractExpression y = share.topRule.parse(lex.next());
		if (!lex.isOperator(array.getEndOperator())) {
			throw new EvalException(EvalException.PARSE_NOT_FOUND_END_OP,
					new String[] { array.getEndOperator() }, lex);
		}
		lex.next();
		x = Col2Expression.create(newExpression(ope, lex.getShare()), lex
				.getString(), pos, x, y);
		return x;
	}

	/**
	 * フィールド構文解析.
	 * 
	 * @param lex
	 * @param x
	 * @param ope
	 * @param pos
	 * @return Expression
	 */
	protected AbstractExpression parseField(Lex lex, AbstractExpression x,
			String ope, int pos) {
		AbstractExpression y = nextRule.parse(lex.next());
		x = Col2Expression.create(newExpression(ope, lex.getShare()), lex
				.getString(), pos, x, y);
		return x;
	}

}
