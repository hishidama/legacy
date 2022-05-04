package jp.hishidama.eval.exp;

/**
 * 三項演算子クラス.
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 * @version 2006.11.07
 */
public abstract class Col3Expression extends AbstractExpression {

	/**
	 * 式インスタンス生成（三項演算子用）.
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
	 * @param z
	 *            式インスタンス
	 * @return 式インスタンス
	 */
	public static AbstractExpression create(AbstractExpression exp,
			String string, int pos, AbstractExpression x, AbstractExpression y,
			AbstractExpression z) {
		Col3Expression n = (Col3Expression) exp;
		n.setExpression(x, y, z);
		n.setPos(string, pos);
		return n;
	}

	/** 第一項の式. */
	protected AbstractExpression exp1;

	/** 第二項の式. */
	protected AbstractExpression exp2;

	/** 第三項の式. */
	protected AbstractExpression exp3;

	protected Col3Expression() {
	}

	protected Col3Expression(Col3Expression from, ShareExpValue s) {
		super(from, s);
		if (from.exp1 != null) {
			exp1 = from.exp1.dup(s);
		}
		if (from.exp2 != null) {
			exp2 = from.exp2.dup(s);
		}
		if (from.exp3 != null) {
			exp3 = from.exp3.dup(s);
		}
	}

	/**
	 * 引数設定.
	 * 
	 * @param x
	 *            第一項式インスタンス
	 * @param y
	 *            第二項式インスタンス
	 * @param z
	 *            第三項式インスタンス
	 */
	public final void setExpression(AbstractExpression x, AbstractExpression y,
			AbstractExpression z) {
		exp1 = x;
		exp2 = y;
		exp3 = z;
	}

	@Override
	protected final int getCols() {
		return 3;
	}

	@Override
	protected int getFirstPos() {
		return exp1.getFirstPos();
	}

	@Override
	protected void search() {
		share.srch.search(this);
		if (share.srch.end()) {
			return;
		}

		if (share.srch.search3_begin(this)) {
			return;
		}
		if (share.srch.end()) {
			return;
		}

		exp1.search();
		if (share.srch.end()) {
			return;
		}

		if (share.srch.search3_2(this)) {
			return;
		}
		if (share.srch.end()) {
			return;
		}

		exp2.search();
		if (share.srch.end()) {
			return;
		}

		if (share.srch.search3_3(this)) {
			return;
		}
		if (share.srch.end()) {
			return;
		}

		exp3.search();
		if (share.srch.end()) {
			return;
		}

		share.srch.search3_end(this);
	}

	@Override
	protected AbstractExpression replace() {
		exp1 = exp1.replace();
		exp2 = exp2.replace();
		exp3 = exp3.replace();
		return share.repl.replace3(this);
	}

	@Override
	protected AbstractExpression replaceVar() {
		exp1 = exp1.replace();
		exp2 = exp2.replaceVar();
		exp3 = exp3.replaceVar();
		return share.repl.replaceVar3(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Col3Expression) {
			Col3Expression e = (Col3Expression) obj;
			if (getClass() == e.getClass()) {
				return exp1.equals(e.exp1) && exp2.equals(e.exp2)
						&& exp3.equals(e.exp3);
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		return getClass().hashCode() ^ exp1.hashCode() ^ (exp2.hashCode() * 2)
				^ (exp3.hashCode() * 3);
	}

	@Override
	public void dump(int n) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < n; i++) {
			sb.append(' ');
		}
		sb.append(getOperator());
		System.out.println(sb.toString());
		exp1.dump(n + 1);
		exp2.dump(n + 1);
		exp3.dump(n + 1);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (exp1.getPriority() <= prio || exp1.getCols() >= 2) {
			sb.append(share.paren.getOperator());
			sb.append(exp1.toString());
			sb.append(share.paren.getEndOperator());
		} else {
			sb.append(exp1.toString());
		}
		sb.append(' ');
		sb.append(getOperator());
		sb.append(' ');
		if (exp2.getPriority() <= prio || exp2.getCols() >= 2) {
			sb.append(share.paren.getOperator());
			sb.append(exp2.toString());
			sb.append(share.paren.getEndOperator());
		} else {
			sb.append(exp2.toString());
		}
		sb.append(' ');
		sb.append(getEndOperator());
		sb.append(' ');
		if (exp3.getPriority() <= prio || exp3.getCols() >= 2) {
			sb.append(share.paren.getOperator());
			sb.append(exp3.toString());
			sb.append(share.paren.getEndOperator());
		} else {
			sb.append(exp3.toString());
		}
		return sb.toString();
	}
}
