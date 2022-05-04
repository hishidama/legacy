package jp.hishidama.eval.exp;

/**
 * 右シフト(符号なし)クラス.
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 */
public class ShiftRightLogicalExpression extends Col2Expression {

	public static final String NAME = "srl";

	public ShiftRightLogicalExpression() {
		setOperator(">>>");
	}

	@Override
	public String getExpressionName() {
		return NAME;
	}

	protected ShiftRightLogicalExpression(ShiftRightLogicalExpression from,
			ShareExpValue s) {
		super(from, s);
	}

	@Override
	public AbstractExpression dup(ShareExpValue s) {
		return new ShiftRightLogicalExpression(this, s);
	}

	@Override
	protected Object operateObject(Object vl, Object vr) {
		return share.oper.shiftRightLogical(vl, vr);
	}
}
