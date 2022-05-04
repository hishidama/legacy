package jp.hishidama.eval.exp;

/**
 * 条件演算子クラス.
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 */
public class IfExpression extends Col3Expression {

	public static final String NAME = "if";

	public IfExpression() {
		setOperator("?");
		setEndOperator(":");
	}

	@Override
	public String getExpressionName() {
		return "if";
	}

	protected IfExpression(IfExpression from, ShareExpValue s) {
		super(from, s);
	}

	@Override
	public AbstractExpression dup(ShareExpValue s) {
		return new IfExpression(this, s);
	}

	@Override
	public Object eval() {
		Object x = exp1.eval();
		Object r;
		if (share.oper.bool(x)) {
			r = exp2.eval();
		} else {
			r = exp3.eval();
		}
		if (share.log != null) {
			share.log.logEval(getExpressionName(), x, r);
		}
		return r;
	}
}
