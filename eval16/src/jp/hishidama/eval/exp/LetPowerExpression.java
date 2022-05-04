package jp.hishidama.eval.exp;

/**
 * —İæ‘ã“ü‰‰ZqƒNƒ‰ƒX.
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >‚Ğ‚µ‚¾‚Ü</a>
 * @since 2007.02.16
 */
public class LetPowerExpression extends PowerExpression {

	public static final String NAME = "powerLet";

	public LetPowerExpression() {
		setOperator("**=");
	}

	@Override
	public String getExpressionName() {
		return NAME;
	}

	protected LetPowerExpression(LetPowerExpression from, ShareExpValue s) {
		super(from, s);
	}

	@Override
	public AbstractExpression dup(ShareExpValue s) {
		return new LetPowerExpression(this, s);
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
