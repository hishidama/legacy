package jp.hishidama.eval.exp;

/**
 * ˜_—ÏƒNƒ‰ƒX.
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >‚Ğ‚µ‚¾‚Ü</a>
 */
public class AndExpression extends Col2OpeExpression {

	public static final String NAME = "and";

	public AndExpression() {
		setOperator("&&");
	}

	@Override
	public String getExpressionName() {
		return NAME;
	}

	protected AndExpression(AndExpression from, ShareExpValue s) {
		super(from, s);
	}

	@Override
	public AbstractExpression dup(ShareExpValue s) {
		return new AndExpression(this, s);
	}

	@Override
	public Object eval() {
		Object val = expl.eval();
		if (!share.oper.bool(val)) {
			return val;
		}
		return expr.eval();
	}
}
