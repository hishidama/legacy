package jp.hishidama.ant.types.htlex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jp.hishidama.ant.types.htlex.eval.HtLexerExpRuleFactory;
import jp.hishidama.eval.Expression;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Location;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.types.EnumeratedAttribute;

/**
 * ������u���N���X.
 *<p>
 * ������̒u�����s���N���X�B
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/ant/htlex.html"
 *         >�Ђ�����</a>
 * @since 2009.01.30
 * @version 2010.01.31
 */
public class StringOpeEnum extends EnumeratedAttribute {

	protected Project project;

	public void setProject(Project project) {
		this.project = project;
	}

	/**
	 * {@value}�F�P������i�u���j
	 */
	public static final String LET = "let";
	/**
	 * {@value}�F�擪������̂ݒu��
	 *
	 * @see Matcher#replaceFirst(String)
	 */
	public static final String REP_FIRST = "replaceFirst";
	/**
	 * {@value}�F�S�u��
	 *
	 * @see Matcher#replaceAll(String)
	 */
	public static final String REP_ALL = "replaceAll";
	/**
	 * {@value}�F���Z
	 */
	public static final String EVAL = "evaluate";

	@Override
	public String[] getValues() {
		return new String[] { LET, REP_FIRST, REP_ALL, EVAL };
	}

	public StringOpeEnum() {
	}

	public StringOpeEnum(String s) {
		setValue(s);
	}

	protected Pattern pat;
	protected Expression exp;

	/**
	 * �������s.
	 *
	 * @param me
	 *            �}�b�`���O���@
	 * @param loc
	 *            �f�[�^�^�C�v�ʒu
	 * @throws BuildException
	 *             �����G���[��
	 */
	public void validate(MatchEnum me, Location loc) throws BuildException {
		switch (getIndex()) {
		case 1:// replaceFirst
		case 2:// replaceAll
			if (me == null || (pat = me.getPattern()) == null) {
				throw new BuildException("match-pattern must be set.", loc);
			}
			break;
		}
	}

	/**
	 * �ϊ����s.
	 *
	 * @param s
	 *            �ϊ���������
	 * @param n
	 *            �ϊ��敶����
	 * @return �ϊ��㕶����
	 */
	public String convert(String s, String n) {
		int i = getIndex();
		switch (i) {
		case 1:
		case 2:
		case 3:
			if (s == null) {
				s = "";
			}
			if (n == null) {
				n = "";
			}
			break;
		}

		switch (i) {
		default: // let
			return n;
		case 1: // replaceFirst
			return pat.matcher(s).replaceFirst(n);
		case 2: // replaceAll
			return pat.matcher(s).replaceAll(n);
		case 3:// expression
		{
			if (exp == null) {
				exp = HtLexerExpRuleFactory.getRule(project).parse(n);
			}
			Object r = exp.eval();
			if (r == null) {
				return null;
			} else {
				return r.toString();
			}
		}
		}
	}
}
