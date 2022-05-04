package jp.hishidama.eval.exp;

/**
 * ビット排他的論理和クラス.
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 */
public class BitXorExpression extends Col2Expression {

	public static final String NAME = "bitXor";

	public BitXorExpression() {
		setOperator("^");
	}

	@Override
	public String getExpressionName() {
		return NAME;
	}

	protected BitXorExpression(BitXorExpression from, ShareExpValue s) {
		super(from, s);
	}

	@Override
	public AbstractExpression dup(ShareExpValue s) {
		return new BitXorExpression(this, s);
	}

	@Override
	protected Object operateObject(Object vl, Object vr) {
		return share.oper.bitXor(vl, vr);
	}
}
