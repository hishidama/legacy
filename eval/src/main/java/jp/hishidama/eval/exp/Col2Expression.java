package jp.hishidama.eval.exp;

/**
 * 二項演算子クラス.
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 * @version 2006.11.07
 */
public abstract class Col2Expression extends AbstractExpression {

	/**
	 * 式インスタンス生成（二項演算子用）.
	 * 
	 * @param exp
	 *            演算子インスタンス
	 * @param string
	 *            全体文字列
	 * @param pos
	 *            位置
	 * @param x
	 *            式インスタンス
	 * @param y
	 *            式インスタンス
	 * @return 式インスタンス
	 */
	public static AbstractExpression create(AbstractExpression exp,
			String string, int pos, AbstractExpression x, AbstractExpression y) {
		Col2Expression n = (Col2Expression) exp;
		n.setExpression(x, y);
		n.setPos(string, pos);
		return n;
	}

	/** 左引数の式. */
	public AbstractExpression expl;

	/** 右引数の式. */
	public AbstractExpression expr;

	protected Col2Expression() {
	}

	protected Col2Expression(Col2Expression from, ShareExpValue s) {
		super(from, s);
		if (from.expl != null) {
			expl = from.expl.dup(s);
		}
		if (from.expr != null) {
			expr = from.expr.dup(s);
		}
	}

	/**
	 * 引数設定.
	 * 
	 * @param x
	 *            左式インスタンス
	 * @param y
	 *            右式インスタンス
	 */
	public final void setExpression(AbstractExpression x, AbstractExpression y) {
		expl = x;
		expr = y;
	}

	@Override
	protected final int getCols() {
		return 2;
	}

	@Override
	protected final int getFirstPos() {
		return expl.getFirstPos();
	}

	@Override
	public Object eval() {
		Object x = expl.eval();
		Object y = expr.eval();
		Object r = operateObject(x, y);
		if (share.log != null) {
			share.log.logEval(getExpressionName(), x, y, r);
		}
		return r;
	}

	protected abstract Object operateObject(Object vl, Object vr);

	@Override
	protected void search() {
		share.srch.search(this);
		if (share.srch.end()) {
			return;
		}

		if (share.srch.search2_begin(this)) {
			return;
		}
		if (share.srch.end()) {
			return;
		}

		expl.search();
		if (share.srch.end()) {
			return;
		}

		if (share.srch.search2_2(this)) {
			return;
		}
		if (share.srch.end()) {
			return;
		}

		expr.search();
		if (share.srch.end()) {
			return;
		}

		share.srch.search2_end(this);
	}

	@Override
	protected AbstractExpression replace() {
		expl = expl.replace();
		expr = expr.replace();
		return share.repl.replace2(this);
	}

	@Override
	protected AbstractExpression replaceVar() {
		expl = expl.replaceVar();
		expr = expr.replaceVar();
		return share.repl.replaceVar2(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Col2Expression) {
			Col2Expression e = (Col2Expression) obj;
			if (getClass() == e.getClass()) {
				return expl.equals(e.expl) && expr.equals(e.expr);
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		return getClass().hashCode() ^ expl.hashCode() ^ (expr.hashCode() * 2);
	}

	@Override
	public void dump(int n) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < n; i++) {
			sb.append(' ');
		}
		sb.append(getOperator());
		System.out.println(sb.toString());
		expl.dump(n + 1);
		expr.dump(n + 1);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (expl.getPriority() < prio) {
			sb.append(share.paren.getOperator());
			sb.append(expl.toString());
			sb.append(share.paren.getEndOperator());
		} else {
			sb.append(expl.toString());
		}
		sb.append(toStringLeftSpace());
		sb.append(getOperator());
		sb.append(' ');
		if (expr.getPriority() < prio) {
			sb.append(share.paren.getOperator());
			sb.append(expr.toString());
			sb.append(share.paren.getEndOperator());
		} else {
			sb.append(expr.toString());
		}
		return sb.toString();
	}

	protected String toStringLeftSpace() {
		return " ";
	}
}
