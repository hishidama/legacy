package jp.hishidama.ant.taskdefs;

import jp.hishidama.ant.types.htlex.eval.HtLexerExpRuleFactory;
import jp.hishidama.ant.types.htlex.eval.HtLexerPropertyHelper;
import jp.hishidama.eval.Expression;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.PropertyHelper;
import org.apache.tools.ant.taskdefs.Property;

/**
 * htlexプロパティータスク.
 * <p>
 * htlexの演算を行ってプロパティーに保持するタスク。
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/ant/htlex.html"
 *         >ひしだま</a>
 * @since 2010.02.14
 */
public class HtLexerPropertyTask extends Property {

	protected Expression exp;

	/**
	 * 計算式設定.
	 *
	 * @param s
	 *            式
	 */
	public void setExpression(String s) {
		try {
			exp = HtLexerExpRuleFactory.getRule(getProject()).parse(s);
		} catch (Exception e) {
			throw new BuildException(e, getLocation());
		}
	}

	protected String htlexPrefix = "htlex";

	/**
	 * プロパティー名の接頭辞設定.
	 *
	 * @param s
	 *            プロパティー名の接頭辞
	 */
	public void setHtLexPrefix(String s) {
		htlexPrefix = s;
	}

	@Override
	public void execute() throws BuildException {
		validate();

		if (exp != null) {
			executeExpression();
			return;
		}

		super.execute();
	}

	protected void validate() {
		if (getProject() == null) {
			throw new IllegalStateException("project has not been set");
		}
		if (exp != null) {
			if (name == null) {
				throw new BuildException(
						"You must specify name with the expression attribute",
						getLocation());
			}
		}
	}

	protected void executeExpression() {
		// プロパティーヘルパー初期化
		HtLexerPropertyHelper.getInstance(getProject(), htlexPrefix);

		try {
			Object value = exp.eval();
			PropertyHelper ph = PropertyHelper.getPropertyHelper(getProject());
			ph.setProperty(name, value, false);
		} catch (BuildException e) {
			if (e.getLocation() == null) {
				e.setLocation(getLocation());
			}
			throw e;
		} catch (Exception e) {
			throw new BuildException(e, getLocation());
		}
	}
}
