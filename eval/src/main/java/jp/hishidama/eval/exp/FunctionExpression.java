package jp.hishidama.eval.exp;

import java.util.ArrayList;
import java.util.List;

import jp.hishidama.eval.EvalException;

/**
 * 関数クラス.
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 * @version 2010.02.15
 */
public class FunctionExpression extends Col1Expression {

	public static final String NAME = "function";

	@Override
	public String getExpressionName() {
		return NAME;
	}

	/**
	 * 関数インスタンス生成.
	 *
	 * @param x
	 *            関数名、あるいはオブジェクト.関数名
	 * @param args
	 *            引数
	 * @param prio
	 *            優先順位
	 * @return 式インスタンス
	 */
	public static AbstractExpression create(AbstractExpression x,
			AbstractExpression args, int prio, ShareExpValue share) {
		AbstractExpression obj;
		if (x instanceof VariableExpression) {
			obj = null;
		} else if (x instanceof FieldExpression) {
			FieldExpression f = (FieldExpression) x;
			obj = f.expl;
			x = f.expr;
		} else {
			throw new EvalException(EvalException.PARSE_NOT_FUNC, x.toString(),
					x, null);
		}
		String name = x.getWord();
		FunctionExpression f = new FunctionExpression(obj, name);
		f.setExpression(args);
		f.setPos(x.getString(), x.getPos());
		f.setPriority(prio);
		f.share = share;
		return f;
	}

	/**
	 * メソッドの属しているオブジェクト.
	 * <p>
	 * 無い場合はnull。
	 * </p>
	 *
	 * @since 2007.02.15
	 */
	protected AbstractExpression target;

	/** 関数名. */
	String name;

	public FunctionExpression() {
		setOperator("(");
		setEndOperator(")");
	}

	/**
	 * コンストラクター.
	 *
	 * @param obj
	 *            オブジェクト
	 * @param word
	 *            関数名
	 */
	public FunctionExpression(AbstractExpression obj, String word) {
		this();
		target = obj;
		name = word;
	}

	protected FunctionExpression(FunctionExpression from, ShareExpValue s) {
		super(from, s);
		if (from.target != null) {
			target = from.target.dup(s);
		}
		name = from.name;
	}

	@Override
	public AbstractExpression dup(ShareExpValue s) {
		return new FunctionExpression(this, s);
	}

	@Override
	public Object eval() {
		Object obj = null;
		if (target != null) {
			obj = target.getVariable();
		}
		List<Object> args = evalArgsObject();
		try {
			Object[] arr = args.toArray(new Object[args.size()]);
			Object r;
			if (target != null) {
				r = share.func.eval(obj, name, arr);
			} else {
				r = share.func.eval(name, arr);
			}
			if (share.log != null) {
				share.log.logEvalFunction(getExpressionName(), name, arr, r);
			}
			return r;
		} catch (EvalException e) {
			throw e;
		} catch (Exception e) {
			throw new EvalException(EvalException.EXP_FUNC_CALL_ERROR, name,
					this, e);
		}
	}

	private List<Object> evalArgsObject() {
		List<Object> args = new ArrayList<Object>();
		if (exp != null) {
			exp.evalArgs(args);
		}
		return args;
	}

	@Override
	protected Object getVariable() {
		return eval();
	}

	@Override
	protected void search() {
		share.srch.search(this);
		if (share.srch.end()) {
			return;
		}

		if (share.srch.searchFunc_begin(this)) {
			return;
		}
		if (share.srch.end()) {
			return;
		}

		if (target != null) {
			target.search();
			if (share.srch.end()) {
				return;
			}
		}

		if (share.srch.searchFunc_2(this)) {
			return;
		}
		if (share.srch.end()) {
			return;
		}

		if (exp != null) {
			exp.search();
			if (share.srch.end()) {
				return;
			}
		}

		share.srch.searchFunc_end(this);
	}

	@Override
	protected AbstractExpression replace() {
		if (target != null) {
			target = target.replace();
		}
		if (exp != null) {
			exp = exp.replace();
		}
		return share.repl.replaceFunc(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof FunctionExpression) {
			FunctionExpression e = (FunctionExpression) obj;
			return name.equals(e.name) && equals(target, e.target)
					&& equals(exp, e.exp);
		}
		return false;
	}

	private static boolean equals(AbstractExpression e1, AbstractExpression e2) {
		if (e1 == null) {
			return e2 == null;
		}
		if (e2 == null) {
			return false;
		}
		return e1.equals(e2);
	}

	@Override
	public int hashCode() {
		int t = (target != null) ? target.hashCode() : 0;
		int a = (exp != null) ? exp.hashCode() : 0;
		return name.hashCode() ^ t ^ (a * 2);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (target != null) {
			sb.append(target.toString());
			sb.append('.');
		}
		sb.append(name);
		sb.append('(');
		if (exp != null) {
			sb.append(exp.toString());
		}
		sb.append(')');
		return sb.toString();
	}
}
