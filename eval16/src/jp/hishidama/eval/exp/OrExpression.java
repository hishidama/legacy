package jp.hishidama.eval.exp;

/**
 * ò_óùòaÉNÉâÉX.
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >Ç–ÇµÇæÇ‹</a>
 */
public class OrExpression extends Col2OpeExpression {

	public static final String NAME = "or";

	public OrExpression() {
		setOperator("||");
	}

	@Override
	public String getExpressionName() {
		return NAME;
	}

	protected OrExpression(OrExpression from, ShareExpValue s) {
		super(from, s);
	}

	@Override
	public AbstractExpression dup(ShareExpValue s) {
		return new OrExpression(this, s);
	}

	@Override
	public Object eval() {
		Object val = expl.eval();
		if (share.oper.bool(val)) {
			return val;
		}
		return expr.eval();
	}
}
