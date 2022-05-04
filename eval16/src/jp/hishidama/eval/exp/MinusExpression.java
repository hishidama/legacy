package jp.hishidama.eval.exp;

/**
 * Œ¸ŽZƒNƒ‰ƒX.
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >‚Ð‚µ‚¾‚Ü</a>
 */
public class MinusExpression extends Col2Expression {

	public static final String NAME = "minus";

	public MinusExpression() {
		setOperator("-");
	}

	@Override
	public String getExpressionName() {
		return NAME;
	}

	protected MinusExpression(MinusExpression from, ShareExpValue s) {
		super(from, s);
	}

	@Override
	public AbstractExpression dup(ShareExpValue s) {
		return new MinusExpression(this, s);
	}

	@Override
	protected Object operateObject(Object vl, Object vr) {
		return share.oper.minus(vl, vr);
	}
}
