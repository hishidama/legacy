package jp.hishidama.eval.exp;

/**
 * —]ŽZƒNƒ‰ƒX.
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >‚Ð‚µ‚¾‚Ü</a>
 */
public class ModExpression extends Col2Expression {

	public static final String NAME = "mod";

	public ModExpression() {
		setOperator("%");
	}

	@Override
	public String getExpressionName() {
		return NAME;
	}

	protected ModExpression(ModExpression from, ShareExpValue s) {
		super(from, s);
	}

	@Override
	public AbstractExpression dup(ShareExpValue s) {
		return new ModExpression(this, s);
	}

	@Override
	protected Object operateObject(Object vl, Object vr) {
		return share.oper.mod(vl, vr);
	}
}
