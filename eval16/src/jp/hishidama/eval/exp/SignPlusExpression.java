package jp.hishidama.eval.exp;

/**
 * ê≥ïÑçÜÉNÉâÉX.
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >Ç–ÇµÇæÇ‹</a>
 */
public class SignPlusExpression extends Col1Expression {

	public static final String NAME = "signPlus";

	public SignPlusExpression() {
		setOperator("+");
	}

	@Override
	public String getExpressionName() {
		return NAME;
	}

	protected SignPlusExpression(SignPlusExpression from, ShareExpValue s) {
		super(from, s);
	}

	@Override
	public AbstractExpression dup(ShareExpValue s) {
		return new SignPlusExpression(this, s);
	}

	@Override
	public Object eval() {
		Object x = exp.eval();
		Object r = share.oper.signPlus(x);
		if (share.log != null) {
			share.log.logEval(getExpressionName(), x, r);
		}
		return r;
	}
}
