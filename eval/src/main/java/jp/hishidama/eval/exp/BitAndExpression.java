package jp.hishidama.eval.exp;

/**
 * ビット論理積クラス.
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 */
public class BitAndExpression extends Col2Expression {

	public static final String NAME = "bitAnd";

	public BitAndExpression() {
		setOperator("&");
	}

	@Override
	public String getExpressionName() {
		return NAME;
	}

	protected BitAndExpression(BitAndExpression from, ShareExpValue s) {
		super(from, s);
	}

	@Override
	public AbstractExpression dup(ShareExpValue s) {
		return new BitAndExpression(this, s);
	}

	@Override
	protected Object operateObject(Object vl, Object vr) {
		return share.oper.bitAnd(vl, vr);
	}
}
