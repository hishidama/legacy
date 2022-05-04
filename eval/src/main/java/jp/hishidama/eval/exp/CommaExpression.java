package jp.hishidama.eval.exp;

/**
 * カンマ演算子クラス.
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 */
public class CommaExpression extends Col2OpeExpression {

	public static final String NAME = "comma";

	public CommaExpression() {
		setOperator(",");
	}

	@Override
	public String getExpressionName() {
		return NAME;
	}

	protected CommaExpression(CommaExpression from, ShareExpValue s) {
		super(from, s);
	}

	@Override
	public AbstractExpression dup(ShareExpValue s) {
		return new CommaExpression(this, s);
	}

	@Override
	public Object eval() {
		expl.eval();
		return expr.eval();
	}

	@Override
	protected String toStringLeftSpace() {
		return "";
	}
}
