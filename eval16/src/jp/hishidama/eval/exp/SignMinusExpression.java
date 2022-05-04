package jp.hishidama.eval.exp;

/**
 * •‰•„†ƒNƒ‰ƒX.
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >‚Ğ‚µ‚¾‚Ü</a>
 */
public class SignMinusExpression extends Col1Expression {

	public static final String NAME = "signMinus";

	public SignMinusExpression() {
		setOperator("-");
	}

	@Override
	public String getExpressionName() {
		return NAME;
	}

	protected SignMinusExpression(SignMinusExpression from, ShareExpValue s) {
		super(from, s);
	}

	@Override
	public AbstractExpression dup(ShareExpValue s) {
		return new SignMinusExpression(this, s);
	}

	@Override
	public Object eval() {
		Object x = exp.eval();
		Object r = share.oper.signMinus(x);
		if (share.log != null) {
			share.log.logEval(getExpressionName(), x, r);
		}
		return r;
	}
}
