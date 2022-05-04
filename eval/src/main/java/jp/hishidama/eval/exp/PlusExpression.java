package jp.hishidama.eval.exp;

/**
 * 加算クラス.
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 */
public class PlusExpression extends Col2Expression {

	public static final String NAME = "plus";

	public PlusExpression() {
		setOperator("+");
	}

	@Override
	public String getExpressionName() {
		return NAME;
	}

	protected PlusExpression(PlusExpression from, ShareExpValue s) {
		super(from, s);
	}

	@Override
	public AbstractExpression dup(ShareExpValue s) {
		return new PlusExpression(this, s);
	}

	@Override
	protected Object operateObject(Object vl, Object vr) {
		return share.oper.plus(vl, vr);
	}
}
