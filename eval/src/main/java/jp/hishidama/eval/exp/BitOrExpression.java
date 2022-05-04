package jp.hishidama.eval.exp;

/**
 * ビット論理和クラス.
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 */
public class BitOrExpression extends Col2Expression {

	public static final String NAME = "bitOr";

	public BitOrExpression() {
		setOperator("|");
	}

	@Override
	public String getExpressionName() {
		return NAME;
	}

	protected BitOrExpression(BitOrExpression from, ShareExpValue s) {
		super(from, s);
	}

	@Override
	public AbstractExpression dup(ShareExpValue s) {
		return new BitOrExpression(this, s);
	}

	@Override
	protected Object operateObject(Object vl, Object vr) {
		return share.oper.bitOr(vl, vr);
	}
}
