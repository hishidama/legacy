package jp.hishidama.eval.exp;

/**
 * 小括弧クラス.
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 * @since 2007.02.17
 */
public class ParenExpression extends Col1Expression {

	public static final String NAME = "paren";

	public ParenExpression() {
		setOperator("(");
		setEndOperator(")");
	}

	@Override
	public String getExpressionName() {
		return NAME;
	}

	protected ParenExpression(ParenExpression from, ShareExpValue s) {
		super(from, s);
	}

	@Override
	public AbstractExpression dup(ShareExpValue s) {
		return new ParenExpression(this, s);
	}

	@Override
	public Object eval() {
		Object r = exp.eval();
		if (share.log != null) {
			share.log.logEval(getExpressionName(), r);
		}
		return r;
	}

	@Override
	public String toString() {
		if (exp == null) {
			return "";
		}
		return getOperator() + exp.toString() + getEndOperator();
	}
}
