package jp.hishidama.eval.exp;

/**
 * 単項後置演算子クラス.
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 * @since 2006.10.31
 */
public abstract class Col1AfterExpression extends Col1Expression {

	protected Col1AfterExpression() {
	}

	protected Col1AfterExpression(Col1Expression from, ShareExpValue s) {
		super(from, s);
	}

	@Override
	protected AbstractExpression replace() {
		exp = exp.replaceVar();
		return share.repl.replaceVar1(this);
	}

	@Override
	protected AbstractExpression replaceVar() {
		return this.replace();
	}

	@Override
	public String toString() {
		if (exp == null) {
			return getOperator();
		}
		StringBuilder sb = new StringBuilder();
		if (exp.getPriority() > prio) {
			sb.append(exp.toString());
			sb.append(getOperator());
		} else if (exp.getPriority() == prio) {
			sb.append(exp.toString());
			sb.append(' ');
			sb.append(getOperator());
		} else {
			sb.append(share.paren.getOperator());
			sb.append(exp.toString());
			sb.append(share.paren.getEndOperator());
			sb.append(getOperator());
		}
		return sb.toString();
	}
}
