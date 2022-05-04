package jp.hishidama.eval.exp;

/**
 * èúéZÉNÉâÉX.
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >Ç–ÇµÇæÇ‹</a>
 */
public class DivExpression extends Col2Expression {

	public static final String NAME = "div";

	public DivExpression() {
		setOperator("/");
	}

	@Override
	public String getExpressionName() {
		return NAME;
	}

	protected DivExpression(DivExpression from, ShareExpValue s) {
		super(from, s);
	}

	@Override
	public AbstractExpression dup(ShareExpValue s) {
		return new DivExpression(this, s);
	}

	@Override
	protected Object operateObject(Object vl, Object vr) {
		return share.oper.div(vl, vr);
	}
}
