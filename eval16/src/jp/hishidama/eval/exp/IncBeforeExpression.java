package jp.hishidama.eval.exp;

/**
 * 前置インクリメント演算子クラス.
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 * @since 2006.10.31
 */
public class IncBeforeExpression extends Col1Expression {

	public static final String NAME = "incBefore";

	public IncBeforeExpression() {
		setOperator("++");
	}

	@Override
	public String getExpressionName() {
		return NAME;
	}

	protected IncBeforeExpression(IncBeforeExpression from, ShareExpValue s) {
		super(from, s);
	}

	@Override
	public AbstractExpression dup(ShareExpValue s) {
		return new IncBeforeExpression(this, s);
	}

	@Override
	public Object eval() {
		Object val = exp.eval();
		Object r = share.oper.inc(val, +1);
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
