package jp.hishidama.eval.exp;

import jp.hishidama.eval.EvalException;
import jp.hishidama.eval.lex.Lex;
import jp.hishidama.util.CharUtil;

/**
 * 文字クラス.
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 * @since 2007.02.21
 */
public class CharExpression extends WordExpression {

	public static final String NAME = "char";

	@Override
	public String getExpressionName() {
		return NAME;
	}

	/**
	 * 式インスタンス生成（文字用）.
	 * 
	 * @param lex
	 *            解釈中位置
	 * @param prio
	 *            優先順位
	 * @return 式インスタンス
	 */
	public static AbstractExpression create(Lex lex, int prio) {
		String str = lex.getWord();
		str = CharUtil.escapeString(str, 1, str.length() - 2);
		AbstractExpression exp = new CharExpression(str);
		exp.setPos(lex.getString(), lex.getPos());
		exp.setPriority(prio);
		exp.share = lex.getShare();
		return exp;
	}

	public CharExpression(String str) {
		super(str);
		setOperator("\'");
		setEndOperator("\'");
	}

	protected CharExpression(CharExpression from, ShareExpValue s) {
		super(from, s);
	}

	@Override
	public AbstractExpression dup(ShareExpValue s) {
		return new CharExpression(this, s);
	}

	public static CharExpression create(AbstractExpression from, String word) {
		CharExpression n = new CharExpression(word);
		n.string = from.string;
		n.pos = from.pos;
		n.prio = from.prio;
		n.share = from.share;
		return n;
	}

	@Override
	public Object eval() {
		try {
			return share.oper.character(word, this);
		} catch (EvalException e) {
			throw e;
		} catch (Exception e) {
			throw new EvalException(EvalException.EXP_NOT_CHAR, this, e);
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getOperator());
		sb.append(word);
		sb.append(getEndOperator());
		return sb.toString();
	}
}
