package jp.hishidama.eval.exp;

/**
 * ò_óùî€íËÉNÉâÉX.
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >Ç–ÇµÇæÇ‹</a>
 */
public class NotExpression extends Col1Expression {

	public static final String NAME = "not";

	public NotExpression() {
		setOperator("!");
	}

	@Override
	public String getExpressionName() {
		return NAME;
	}

	protected NotExpression(NotExpression from, ShareExpValue s) {
		super(from, s);
	}

	@Override
	public AbstractExpression dup(ShareExpValue s) {
		return new NotExpression(this, s);
	}

	@Override
	public Object eval() {
		Object x = exp.eval();
		Object r = share.oper.not(x);
		if (share.log != null) {
			share.log.logEval(getExpressionName(), x, r);
		}
		return r;
	}
}
