package jp.hishidama.eval.exp;

/**
 * 余算代入演算子クラス.
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 */
public class LetModExpression extends ModExpression {

	public static final String NAME = "modLet";

	public LetModExpression() {
		setOperator("%=");
	}

	@Override
	public String getExpressionName() {
		return NAME;
	}

	protected LetModExpression(LetModExpression from, ShareExpValue s) {
		super(from, s);
	}

	@Override
	public AbstractExpression dup(ShareExpValue s) {
		return new LetModExpression(this, s);
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
