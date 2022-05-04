package jp.hishidama.eval.exp;

/**
 * 後置インクリメント演算子クラス.
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 * @since 2006.10.31
 */
public class IncAfterExpression extends Col1AfterExpression {

	public static final String NAME = "incAfter";

	public IncAfterExpression() {
		setOperator("++");
	}

	@Override
	public String getExpressionName() {
		return NAME;
	}

	protected IncAfterExpression(IncAfterExpression from, ShareExpValue s) {
		super(from, s);
	}

	@Override
	public AbstractExpression dup(ShareExpValue s) {
		return new IncAfterExpression(this, s);
	}

	@Override
	public Object eval() {
		Object val = exp.eval();
		Object r = share.oper.inc(val, +1);
		exp.let(r, pos);
		if (share.log != null) {
			share.log.logEval(getExpressionName(), val, r);
		}
		return val;
	}
}
