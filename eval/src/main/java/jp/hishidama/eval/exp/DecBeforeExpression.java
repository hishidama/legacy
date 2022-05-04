package jp.hishidama.eval.exp;

/**
 * 前置デクリメント演算子クラス.
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 * @since 2006.10.31
 */
public class DecBeforeExpression extends Col1Expression {

	public static final String NAME = "decBefore";

	public DecBeforeExpression() {
		setOperator("--");
	}

	@Override
	public String getExpressionName() {
		return NAME;
	}

	protected DecBeforeExpression(DecBeforeExpression from, ShareExpValue s) {
		super(from, s);
	}

	@Override
	public AbstractExpression dup(ShareExpValue s) {
		return new DecBeforeExpression(this, s);
	}

	@Override
	public Object eval() {
		Object val = exp.eval();
		Object r = share.oper.inc(val, -1);
		exp.let(r, pos);
		if (share.log != null) {
			share.log.logEval(getExpressionName(), val, r);
		}
		return r;
	}

	@Override
	protected AbstractExpression replace() {
		exp = exp.replaceVar();
		return share.repl.replaceVar1(this);
	}
}
