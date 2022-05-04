package jp.hishidama.eval.exp;

/**
 * ‘ã“ü‰‰ŽZŽqƒNƒ‰ƒX.
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >‚Ð‚µ‚¾‚Ü</a>
 */
public class LetExpression extends Col2OpeExpression {

	public static final String NAME = "let";

	public LetExpression() {
		setOperator("=");
	}

	@Override
	public String getExpressionName() {
		return NAME;
	}

	protected LetExpression(LetExpression from, ShareExpValue s) {
		super(from, s);
	}

	@Override
	public AbstractExpression dup(ShareExpValue s) {
		return new LetExpression(this, s);
	}

	@Override
	public Object eval() {
		Object val = expr.eval();
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
