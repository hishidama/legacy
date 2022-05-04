package jp.hishidama.ant.types.htlex.eval;

import org.apache.tools.ant.Project;

import jp.hishidama.eval.exp.AbstractExpression;
import jp.hishidama.eval.oper.JavaExOperator;

/**
 * HtHtmlLexer�^�O�������Z�̉��Z�N���X.
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/ant/htlex.html"
 *         >�Ђ�����</a>
 * @since 2010.01.31
 */
public class HtLexerOperator extends JavaExOperator {

	protected Project project;

	public HtLexerOperator(Project project) {
		this.project = project;
	}

	@Override
	public Object character(String word, AbstractExpression exp) {
		return word;
	}

	@Override
	public Object variable(Object value, AbstractExpression exp) {
		if (value instanceof StringWork) {
			// StringWork�͉��߂ł��Ȃ������ϐ��i�v���p�e�B�[�j�Ȃ̂ŁA
			// �l�Ƃ��Ďg���ۂɂ͋󕶎���ɂ���
			return "";
		}
		return super.variable(value, exp);
	}

	@Override
	protected int compare(Object x, Object y) {
		if (x == null) {
			x = "";
		}
		if (y == null) {
			y = "";
		}
		return super.compare(x, y);
	}
}
