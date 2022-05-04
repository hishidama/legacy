package jp.hishidama.eval.exp;

/**
 * 比較演算（<=）クラス.
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 */
public class LessEqualExpression extends Col2Expression {

	public static final String NAME = "le";

	public LessEqualExpression() {
		setOperator("<=");
	}

	@Override
	public String getExpressionName() {
		return NAME;
	}

	protected LessEqualExpression(LessEqualExpression from, ShareExpValue s) {
		super(from, s);
	}

	@Override
	public AbstractExpression dup(ShareExpValue s) {
		return new LessEqualExpression(this, s);
	}

	@Override
	protected Object operateObject(Object vl, Object vr) {
		return share.oper.lessEqual(vl, vr);
	}
}
