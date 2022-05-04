package jp.hishidama.ant.types.htlex.eval;

import org.apache.tools.ant.Project;

import jp.hishidama.eval.ExpRuleFactory;
import jp.hishidama.eval.Rule;
import jp.hishidama.eval.exp.*;

/**
 * HtHtmlLexer�^�O�������Z�̃��[���쐬�N���X.
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/ant/htlex.html"
 *         >�Ђ�����</a>
 * @since 2010.01.31
 */
public class HtLexerExpRuleFactory extends ExpRuleFactory {

	@Override
	protected AbstractExpression createFieldExpression() {
		AbstractExpression exp = super.createFieldExpression();
		// // obj.field��obj.func()�́u#�v�Ƃ���
		// // �i�s���I�h�͕ϐ����̈ꕔ�Ƃ��Ĉ����j
		// exp.setOperator("#");
		return exp;
	}

	@Override
	protected AbstractExpression createCommaExpression() {
		AbstractExpression exp = super.createCommaExpression();
		exp.setOperator(";");
		return exp;
	}

	private static HtLexerExpRuleFactory me = null;

	/**
	 * ���[���擾.
	 *
	 * @param project
	 * @return ���[��
	 */
	public static Rule getRule(Project project) {
		if (me == null) {
			me = new HtLexerExpRuleFactory();
			me.setProject(project);
		}
		return me.getRule();
	}

	protected Project project;

	public void setProject(Project project) {
		this.project = project;
	}

	@Override
	protected Rule createRule() {
		return super.createRule(new HtLexerVariable(project),
				new HtLexerFunction(project), new HtLexerOperator(project),
				new HtLexerEvalLog(project));
	}
}
