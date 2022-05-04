package jp.hishidama.ant.taskdefs;

import jp.hishidama.ant.types.htlex.eval.HtLexerExpRuleFactory;
import jp.hishidama.ant.types.htlex.eval.HtLexerPropertyHelper;
import jp.hishidama.eval.Expression;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.PropertyHelper;
import org.apache.tools.ant.taskdefs.Property;

/**
 * htlex�v���p�e�B�[�^�X�N.
 * <p>
 * htlex�̉��Z���s���ăv���p�e�B�[�ɕێ�����^�X�N�B
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/ant/htlex.html"
 *         >�Ђ�����</a>
 * @since 2010.02.14
 */
public class HtLexerPropertyTask extends Property {

	protected Expression exp;

	/**
	 * �v�Z���ݒ�.
	 *
	 * @param s
	 *            ��
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
	 * �v���p�e�B�[���̐ړ����ݒ�.
	 *
	 * @param s
	 *            �v���p�e�B�[���̐ړ���
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
		// �v���p�e�B�[�w���p�[������
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
