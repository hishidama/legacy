package jp.hishidama.eval.exp;

/**
 * 右シフト（符号なし）代入演算子クラス.
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 */
public class LetShiftRightLogicalExpression extends ShiftRightLogicalExpression {

	public static final String NAME = "srlLet";

	public LetShiftRightLogicalExpression() {
		setOperator(">>>=");
	}

	@Override
	public String getExpressionName() {
		return NAME;
	}

	protected LetShiftRightLogicalExpression(
			LetShiftRightLogicalExpression from, ShareExpValue s) {
		super(from, s);
	}

	@Override
	public AbstractExpression dup(ShareExpValue s) {
		return new LetShiftRightLogicalExpression(this, s);
	}

	@Override
	public Object eval() {
		Object val = super.eval();
		expl.let(val, pos);
		return val;
	}

	@Override
	protected AbstractExpression replace() {
		expl = expl.replaceVar();
		expr = expr.replace();
		return share.repl.replaceLet(this);
	}
}
