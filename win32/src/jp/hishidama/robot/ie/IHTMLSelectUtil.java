package jp.hishidama.robot.ie;

import jp.hishidama.win32.com.ComPtr;
import jp.hishidama.win32.com.Variant;
import jp.hishidama.win32.mshtml.*;

/**
 * �R���{�{�b�N�X�E���X�g�{�b�N�X�x���N���X.
 * <p>
 * {@link IHTMLSelectElement}��{@link IHTMLOptionElement}�̃R���{�{�b�N�X�E���X�g�{�b�N�X�𑀍삷��N���X�ł��B<br> ��<a
 * target="hishidama"
 * href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">�g�p��</a>
 * </p>
 * <p>
 * �R���X�g���N�^�[�̈����Ɏw�肷��HTML�v�f�́A�Ăяo�����Łi�q�v�f�����Ɂj�j�����ĉ������B<br>
 * {@link jp.hishidama.robot.IERobot#getSelectById(String)}�ɂ���ē��C���X�^���X���擾�����ꍇ��IERobot���j�����܂��B
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">�Ђ�����</a>
 * @since 2007.10.24
 */
public class IHTMLSelectUtil {

	protected IHTMLSelectElement sel;

	/**
	 * �R���X�g���N�^�[.
	 * 
	 * @param select
	 *            select�v�f
	 * @see jp.hishidama.robot.IERobot#getSelectById(String)
	 * @see jp.hishidama.robot.IERobot#getSelectByName(String, int)
	 */
	public IHTMLSelectUtil(IHTMLSelectElement select) {
		this.sel = select;
	}

	/**
	 * �\���s���擾.
	 * <p>
	 * 1�̂Ƃ��R���{�{�b�N�X�A������傫���Ƃ����X�g�{�b�N�X�B
	 * </p>
	 * 
	 * @return �s��
	 */
	public int getSize() {
		return sel.getSize();
	}

	/**
	 * �\���s���ݒ�.
	 * 
	 * @param size
	 *            �s��
	 */
	public void setSize(int size) {
		sel.setSize(size);
	}

	/**
	 * �����s�I���ێ擾.
	 * 
	 * @return �����s�I���\�̂Ƃ��Atrue
	 */
	public boolean isMultiple() {
		return sel.getMultiple();
	}

	/**
	 * �����s�I���ېݒ�.
	 * 
	 * @param b
	 *            true�F�����s�I��
	 */
	public void setMultiple(boolean b) {
		sel.setMultiple(b);
	}

	/**
	 * �I���s�ԍ��擾.
	 * <p>
	 * �I������Ă���s��index��Ԃ��B<br>
	 * �����s�I������Ă���ꍇ�A�擪��index���Ԃ�͗l�B
	 * </p>
	 * 
	 * @return index
	 * @see #isSelected(int)
	 */
	public int getSelectedIndex() {
		return sel.getSelectedIndex();
	}

	/**
	 * �I��l�擾.
	 * <p>
	 * �I������Ă���s�̒l��Ԃ��B<br>
	 * �P�ƍs�I���ŃT�u�~�b�g�����ۂɑ��M�����̂́A���Ԃ񂱂̒l�B
	 * </p>
	 * 
	 * @return �l
	 */
	public String getSelectedValue() {
		return sel.getValue();
	}

	/**
	 * �P��s�I��.
	 * <p>
	 * �s��I������B<br>
	 * �����s�I���̏ꍇ�A�I�񂾍s�ȊO�͑I�������������B
	 * </p>
	 * 
	 * @param index
	 *            index
	 * @see #getSelectedIndex()
	 */
	public void setSelected(int index) {
		sel.setSelectedIndex(index);
	}

	/**
	 * �l�ݒ�.
	 * 
	 * @param value
	 *            �l
	 * @see #getSelectedValue()
	 */
	public void setSelected(String value) {
		sel.setValue(value);
	}

	/**
	 * �I�����ǉ�.
	 * 
	 * @param index
	 *            �ǉ��ʒu
	 * @param text
	 *            �\�������e�L�X�g
	 * @param value
	 *            �l
	 * @param defaultSelected
	 *            �f�t�H���g�I��L��
	 * @param selected
	 *            �I��L��
	 */
	public void add(int index, String text, String value,
			boolean defaultSelected, boolean selected) {
		IHTMLDocument doc = null;
		IHTMLOptionElement opt = null;
		try {
			doc = sel.getDocument();
			IHTMLWindow win = doc.getParentWindow();
			opt = IHTMLOptionElement.create(win, text, value, defaultSelected,
					selected);
			sel.add(opt, new Variant(index));
			opt.setSelected(selected);
		} finally {
			ComPtr.dispose(opt);
			ComPtr.dispose(doc);
		}
	}

	/**
	 * �I�����폜.
	 * 
	 * @param index
	 *            �폜�ʒu
	 */
	public void remove(int index) {
		sel.remove(index);
	}

	/**
	 * select�v�f�擾.
	 * <p>
	 * �����\�b�h�ɂ���Ď擾����HTML�v�f�́A�R���X�g���N�^�[���Ăяo�����I�u�W�F�N�g�ɂ���Ĕj�������B
	 * </p>
	 * 
	 * @return select�v�f
	 */
	public IHTMLSelectElement getElement() {
		return sel;
	}

	/**
	 * ���擾.
	 * 
	 * @return �I�����̌�
	 */
	public int size() {
		return sel.getLength();
	}

	/**
	 * option�v�f�擾.
	 * <p>
	 * �����\�b�h�ɂ���Ď擾����HTML�v�f�́A�R���X�g���N�^�[���Ăяo�����I�u�W�F�N�g�ɂ���ĘA�����Ĕj�������B
	 * </p>
	 * 
	 * @param index
	 *            index
	 * @return option�v�f
	 */
	public IHTMLOptionElement getElement(int index) {
		return sel.item(index);
	}

	/**
	 * ���גl�擾.
	 * 
	 * @param index
	 *            index
	 * @return �l
	 */
	public String getValue(int index) {
		IHTMLOptionElement o = sel.item(index);
		try {
			return o.getValue();
		} finally {
			ComPtr.dispose(o);
		}
	}

	/**
	 * ���׃e�L�X�g�擾.
	 * 
	 * @param index
	 *            index
	 * @return �I�����Ƃ��ĕ\������Ă��镶��
	 */
	public String getText(int index) {
		IHTMLOptionElement o = sel.item(index);
		try {
			return o.getText();
		} finally {
			ComPtr.dispose(o);
		}
	}

	/**
	 * �I��L���擾.
	 * 
	 * @param index
	 *            index
	 * @return �I������Ă���ꍇ�Atrue
	 */
	public boolean isSelected(int index) {
		IHTMLOptionElement o = sel.item(index);
		try {
			return o.getSelected();
		} finally {
			ComPtr.dispose(o);
		}
	}

	/**
	 * �I��L���ݒ�.
	 * 
	 * @param index
	 *            index
	 * @param b
	 *            �I��L��
	 */
	public void setSelected(int index, boolean b) {
		IHTMLOptionElement o = sel.item(index);
		try {
			o.setSelected(b);
		} finally {
			ComPtr.dispose(o);
		}
	}

}
