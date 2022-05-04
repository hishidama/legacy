package jp.hishidama.eval.exp;

/**
 * 乗算代入演算子クラス.
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 */
public class LetMultExpression extends MultExpression {

	public static final String NAME = "multLet";

	public LetMultExpression() {
		setOperator("*=");
	}

	@Override
	public String getExpressionName() {
		return NAME;
	}

	protected LetMultExpression(LetMultExpression from, ShareExpValue s) {
		super(from, s);
	}

	@Override
	public AbstractExpression dup(ShareExpValue s) {
		return new LetMultExpression(this, s);
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
