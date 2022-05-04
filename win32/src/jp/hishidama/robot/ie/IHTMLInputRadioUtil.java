package jp.hishidama.robot.ie;

import java.util.ArrayList;
import java.util.List;

import jp.hishidama.win32.mshtml.IHTMLInputElement;

/**
 * ���W�I�{�^���x���N���X.
 * <p>
 * {@link IHTMLInputElement}�̃��W�I�{�^���𑀍삷��N���X�ł��B<br> ��<a target="hishidama"
 * href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">�g�p��</a>
 * </p>
 * <p>
 * �R���X�g���N�^�[�̈����Ɏw�肷��HTML�v�f�́A�Ăяo�����Ŕj�����ĉ������B<br>
 * {@link jp.hishidama.robot.IERobot#getRadioByName(String)}�ɂ���ē��C���X�^���X���擾�����ꍇ��IERobot���j�����܂��B
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">�Ђ�����</a>
 * @since 2007.10.24
 */
public class IHTMLInputRadioUtil {

	protected List list;

	/**
	 * �R���X�g���N�^�[.
	 * 
	 * @param l
	 *            {@link IHTMLInputElement}�̃��X�g
	 * @see jp.hishidama.robot.IERobot#getRadioByName(String)
	 */
	public IHTMLInputRadioUtil(List l) {
		list = new ArrayList(l.size());
		for (int i = 0; i < l.size(); i++) {
			try {
				IHTMLInputElement r = (IHTMLInputElement) l.get(i);
				if ("RADIO".equalsIgnoreCase(r.getType())) {
					list.add(r);
				}
			} catch (RuntimeException e) {
			}
		}
	}

	/**
	 * ���擾.
	 * 
	 * @return ���W�I�{�^���̌�
	 */
	public int size() {
		return list.size();
	}

	/**
	 * �l�擾.
	 * 
	 * @param i
	 *            index
	 * @return �l
	 */
	public String getValue(int i) {
		IHTMLInputElement r = getElement(i);
		return r.getValue();
	}

	/**
	 * �`�F�b�N�l�擾.
	 * 
	 * @return �`�F�b�N����Ă��郉�W�I�{�^���̒l�i�����ꍇ�Anull�j
	 */
	public String getCheckedValue() {
		IHTMLInputElement r = getCheckedElement();
		if (r != null) {
			return r.getValue();
		}
		return null;
	}

	/**
	 * �`�F�b�N�l�ݒ�.
	 * <p>
	 * true���Z�b�g����ƁA���܂�true�������`�F�b�N�{�b�N�X��false�ɂȂ�B
	 * </p>
	 * 
	 * @param i
	 *            index
	 * @param b
	 *            �`�F�b�N�L��
	 */
	public void setChecked(int i, boolean b) {
		IHTMLInputElement r = getElement(i);
		r.setChecked(b);
	}

	/**
	 * �`�F�b�N�l�ݒ�.
	 * <p>
	 * true���Z�b�g����ƁA���܂�true���������W�I�{�^����false�ɂȂ�B
	 * </p>
	 * 
	 * @param value
	 *            �l
	 * @param b
	 *            �`�F�b�N�L��
	 * @return ���������ꍇ�Atrue
	 */
	public boolean setChecked(String value, boolean b) {
		for (int i = 0; i < size(); i++) {
			IHTMLInputElement r = getElement(i);
			if (value.equals(r.getValue())) {
				r.setChecked(b);
				return true;
			}
		}
		return false;
	}

	/**
	 * input�v�f�擾.
	 * <p>
	 * �����\�b�h�ɂ���Ď擾����HTML�v�f�́A�R���X�g���N�^�[���Ăяo�����I�u�W�F�N�g�ɂ���Ĕj�������B
	 * </p>
	 * 
	 * @param i
	 *            index
	 * @return input�v�f
	 */
	public IHTMLInputElement getElement(int i) {
		return (IHTMLInputElement) list.get(i);
	}

	/**
	 * �`�F�b�N���ꂽinput�v�f�擾.
	 * 
	 * @return input�v�f�i�����ꍇ�Anull�j
	 */
	public IHTMLInputElement getCheckedElement() {
		for (int i = 0; i < size(); i++) {
			IHTMLInputElement r = getElement(i);
			if (r.getChecked()) {
				return r;
			}
		}
		return null;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer(128);
		sb.append(getClass());
		sb.append("\' {");
		for (int i = 0; i < size(); i++) {
			IHTMLInputElement r = getElement(i);
			if (i != 0) {
				sb.append(", ");
			}
			sb.append('\"');
			sb.append(r.getValue());
			sb.append('\"');
			if (r.getChecked()) {
				sb.append(" checked");
			}
		}
		sb.append("}");
		return sb.toString();
	}

}
