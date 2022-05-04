package jp.hishidama.eval.exp;

/**
 * èÊéZÉNÉâÉX.
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >Ç–ÇµÇæÇ‹</a>
 */
public class MultExpression extends Col2Expression {

	public static final String NAME = "mult";

	public MultExpression() {
		setOperator("*");
	}

	@Override
	public String getExpressionName() {
		return NAME;
	}

	protected MultExpression(MultExpression from, ShareExpValue s) {
		super(from, s);
	}

	@Override
	public AbstractExpression dup(ShareExpValue s) {
		return new MultExpression(this, s);
	}

	@Override
	protected Object operateObject(Object vl, Object vr) {
		return share.oper.mult(vl, vr);
	}
}
