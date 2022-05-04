package jp.hishidama.eval.exp;

import jp.hishidama.eval.EvalException;
import jp.hishidama.eval.lex.Lex;
import jp.hishidama.util.CharUtil;

/**
 * 文字列クラス.
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/eval16.html"
 *         >ひしだま</a>
 * @since 2007.02.21
 */
public class StringExpression extends WordExpression {

	public static final String NAME = "word";

	@Override
	public String getExpressionName() {
		return NAME;
	}

	/**
	 * 式インスタンス生成（文字列用）.
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
		AbstractExpression exp = new StringExpression(str);
		exp.setPos(lex.getString(), lex.getPos());
		exp.setPriority(prio);
		exp.share = lex.getShare();
		return exp;
	}

	public StringExpression(String str) {
		super(str);
		setOperator("\"");
		setEndOperator("\"");
	}

	protected StringExpression(StringExpression from, ShareExpValue s) {
		super(from, s);
	}

	@Override
	public AbstractExpression dup(ShareExpValue s) {
		return new StringExpression(this, s);
	}

	public static StringExpression create(AbstractExpression from, String word) {
		StringExpression n = new StringExpression(word);
		n.string = from.string;
		n.pos = from.pos;
		n.prio = from.prio;
		n.share = from.share;
		return n;
	}

	@Override
	public Object eval() {
		try {
			return share.oper.string(word, this);
		} catch (EvalException e) {
			throw e;
		} catch (Exception e) {
			throw new EvalException(EvalException.EXP_NOT_STRING, this, e);
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof StringExpression) {
			StringExpression e = (StringExpression) obj;
			return word.equals(e.word);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return word.hashCode();
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
