package jp.hishidama.eval.exp;

import java.util.List;

/**
 * 関数引数クラス.
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 * @since 2006.11.07
 * @version 2007.02.17
 */
public class FuncArgExpression extends Col2OpeExpression {

	public static final String NAME = "funcArg";

	public FuncArgExpression() {
		setOperator(",");
	}

	@Override
	public String getExpressionName() {
		return NAME;
	}

	protected FuncArgExpression(FuncArgExpression from, ShareExpValue s) {
		super(from, s);
	}

	@Override
	public AbstractExpression dup(ShareExpValue s) {
		return new FuncArgExpression(this, s);
	}

	@Override
	protected void evalArgs(List<Object> args) {
		expl.evalArgs(args);
		expr.evalArgs(args);
	}

	@Override
	protected String toStringLeftSpace() {
		return "";
	}
}
