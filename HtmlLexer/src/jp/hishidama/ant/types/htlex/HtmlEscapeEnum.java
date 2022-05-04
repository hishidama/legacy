package jp.hishidama.ant.types.htlex;

import jp.hishidama.html.HtmlEscape;

import org.apache.tools.ant.types.EnumeratedAttribute;

/**
 * HTML�G�X�P�[�v���@�N���X.
 *<p>
 * HTML�G�X�P�[�v�̕��@���Ǘ�����N���X�B
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/ant/htlex.html"
 *         >�Ђ�����</a>
 * @since 2009.01.25
 * @version 2009.02.01
 * @see HtmlEscape
 */
public class HtmlEscapeEnum extends EnumeratedAttribute {

	/** {@value}�F��{ */
	public static final String BASE = "base";
	/** {@value}�F��{�{�_�u���N�H�[�e�[�V���� */
	public static final String DQ = "dq";
	/** {@value}�F��{�{�V���O���N�H�[�e�[�V���� */
	public static final String SQ = "sq";
	/** {@value}�F�S�� */
	public static final String ALL = "all";

	@Override
	public String[] getValues() {
		return new String[] { BASE, DQ, SQ, ALL };
	}

	public HtmlEscapeEnum() {
	}

	public HtmlEscapeEnum(String s) {
		setValue(s);
	}

	/**
	 * @return �_�u���N�H�[�e�[�V������ϊ�����ꍇ�Atrue
	 */
	public boolean isDq() {
		int i = getIndex();
		return (i == 1 || i == 3);
	}

	/**
	 * @return �V���O���N�H�[�e�[�V������ϊ�����ꍇ�Atrue
	 */
	public boolean isSq() {
		int i = getIndex();
		return (i == 2 || i == 3);
	}
}
