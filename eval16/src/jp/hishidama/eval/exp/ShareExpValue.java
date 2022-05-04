package jp.hishidama.eval.exp;

import jp.hishidama.eval.EvalThroughException;
import jp.hishidama.eval.Expression;
import jp.hishidama.eval.Rule;
import jp.hishidama.eval.func.*;
import jp.hishidama.eval.oper.*;
import jp.hishidama.eval.ref.Refactor;
import jp.hishidama.eval.repl.Replace;
import jp.hishidama.eval.srch.Search;
import jp.hishidama.eval.var.*;

/**
 * éÆã§í èÓïÒÉNÉâÉX.
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >Ç–ÇµÇæÇ‹</a>
 * @since 2007.02.16
 * @version eval16
 */
public class ShareExpValue extends Expression {

	public AbstractExpression paren;

	public void setAbstractExpression(AbstractExpression ae) {
		this.ae = ae;
	}

	public void initVar() {
		if (var == null) {
			var = new MapVariable<String, Object>(String.class, Object.class);
		}
	}

	public void initOper() {
		if (oper == null) {
			oper = new JavaExOperator();
		}
	}

	public void initFunc() {
		if (func == null) {
			// func = new VoidFunction();
			func = new InvokeFunction();
		}
	}

	@Override
	public Object eval() {
		initVar();
		initOper();
		initFunc();
		try {
			return ae.eval();
		} catch (EvalThroughException e) {
			throw (RuntimeException) e.getCause();
		}
	}

	@Override
	public void optimize(Variable var, Operator oper) {
		Operator bak = this.oper;
		this.oper = oper;
		try {
			optimize(var, new OptimizeReplacer());
		} finally {
			this.oper = bak;
		}
	}

	protected void optimize(Variable var, Replace repl) {
		Variable bak = this.var;
		if (var == null) {
			var = new MapVariable<String, Object>(String.class, Object.class);
		}
		this.var = var;
		this.repl = repl;
		try {
			ae = ae.replace();
		} finally {
			this.var = bak;
		}
	}

	@Override
	public void search(Search srch) {
		if (srch == null) {
			throw new NullPointerException();
		}
		this.srch = srch;
		ae.search();
	}

	@Override
	public void refactorName(Refactor ref) {
		if (ref == null) {
			throw new NullPointerException();
		}
		this.srch = new Search4RefactorName(ref);
		ae.search();
		// this.repl = new Replace4RefactorName(ref);
		// ae.replace();
	}

	@Override
	public void refactorFunc(Refactor ref, Rule rule) {
		if (ref == null) {
			throw new NullPointerException();
		}
		this.repl = new Replace4RefactorGetter(ref, rule);
		ae.replace();
	}

	@Override
	public boolean same(Expression obj) {
		if (obj instanceof ShareExpValue) {
			AbstractExpression p = ((ShareExpValue) obj).paren;
			return paren.same(p) && super.same(obj);
		}
		return false;
	}

	@Override
	public Expression dup() {
		ShareExpValue n = new ShareExpValue();
		n.ae = this.ae.dup(n);
		n.paren = this.paren.dup(n);
		return n;
	}
}
