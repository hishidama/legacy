package jp.hishidama.eval.exp;

/**
 * ïsìôçÜÉNÉâÉX.
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >Ç–ÇµÇæÇ‹</a>
 */
public class NotEqualExpression extends Col2Expression {

	public static final String NAME = "ne";

	public NotEqualExpression() {
		setOperator("!=");
	}

	@Override
	public String getExpressionName() {
		return NAME;
	}

	protected NotEqualExpression(NotEqualExpression from, ShareExpValue s) {
		super(from, s);
	}

	@Override
	public AbstractExpression dup(ShareExpValue s) {
		return new NotEqualExpression(this, s);
	}

	@Override
	protected Object operateObject(Object vl, Object vr) {
		return share.oper.notEqual(vl, vr);
	}
}
