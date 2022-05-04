package jp.hishidama.eval.exp;

/**
 * 減算クラス.
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
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
