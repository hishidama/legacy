package jp.hishidama.ant.types;

import org.apache.tools.ant.types.DataType;

/**
 * �e�L�X�g�p�����[�^�[�^�C�v.
 *<p>
 * �{�f�B�[���Ƀp�����[�^�[�̒l���L�q����ׂ̃f�[�^�^�C�v�B
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/ant/htlex.html"
 *         >�Ђ�����</a>
 * @since 2010.02.01
 */
public class TextParameter extends DataType {

	protected String name;
	protected String value = "";

	/**
	 * �������ݒ�.
	 *
	 * @param s
	 *            ������
	 */
	public void setName(String s) {
		name = s;
	}

	/**
	 * �������擾.
	 *
	 * @return ������
	 */
	public String getName() {
		return name;
	}

	/**
	 * �l�ݒ�.
	 *
	 * @param s
	 *            �l
	 */
	public void setValue(String s) {
		value += s;
	}

	/**
	 * �l�擾.
	 *
	 * @return �l
	 */
	public String getValue() {
		return value;
	}

	/**
	 * �l�ݒ�i�{�f�B�[���j.
	 *
	 * @param s
	 *            �l
	 */
	public void addText(String s) {
		value += s;
	}
}
