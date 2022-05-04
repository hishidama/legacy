package jp.hishidama.swing.radio;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;

/**
 * �g��ButtonGroup.
 *<p>
 * ���{�^���O���[�v�Ń��W�I�{�^�����O���[�s���O����ƁA�J�[�\���̏㉺���E�őI�������ړ��ł���B
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/java/swing/JRadioButton.html"
 *         >�Ђ�����</a>
 * @since 2009.04.05
 * @version 2009.04.19 �{�^���������ɂƂ�R���X�g���N�^�[��ǉ�
 */
public class ExButtonGroup extends ButtonGroup implements KeyListener {
	private static final long serialVersionUID = -1475598640349542914L;

	/**
	 * �R���X�g���N�^�[.
	 */
	public ExButtonGroup() {
	}

	/**
	 * �R���X�g���N�^�[.
	 *
	 * @param bs
	 *            �{�^��
	 * @see #add(AbstractButton)
	 */
	public ExButtonGroup(AbstractButton... bs) {
		for (AbstractButton b : bs) {
			add(b);
		}
	}

	@Override
	public void add(AbstractButton b) {
		super.add(b);
		b.removeKeyListener(this);
		b.addKeyListener(this);
	}

	@Override
	public void remove(AbstractButton b) {
		b.removeKeyListener(this);
		super.remove(b);
	}

	/**
	 * �I��
	 *
	 * @param index
	 *            �C���f�b�N�X
	 * @since 2009.04.19
	 */
	public void setSelectedIndex(int index) {
		AbstractButton b = buttons.get(index);
		setSelected(b.getModel(), true);
	}

	/**
	 * �I���C���f�b�N�X�擾
	 *
	 * @return �C���f�b�N�X�i�I������Ă��Ȃ��ꍇ��-1�j
	 * @since 2009.04.19
	 */
	public int getSelectedIndex() {
		for (int i = 0; i < buttons.size(); i++) {
			AbstractButton b = buttons.get(i);
			if (b.isSelected()) {
				return i;
			}
		}
		return -1;
	}

	// KeyListener

	@Override
	public void keyPressed(KeyEvent e) {
		int size = buttons.size();
		if (size <= 0) {
			return;
		}

		int z;
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
		case KeyEvent.VK_LEFT:
			z = -1;
			break;
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_RIGHT:
			z = +1;
			break;
		default:
			return;
		}
		AbstractButton b = (AbstractButton) e.getSource();
		for (int i = 0; i < size; i++) {
			if (buttons.get(i) == b) {
				int n = nextIndex(i, z);
				AbstractButton nb = buttons.get(n);
				nb.setSelected(true);
				nb.requestFocusInWindow();
				e.consume();
				return;
			}
		}
	}

	protected int nextIndex(int index, int z) {
		index += z;
		if (index < 0) {
			index = buttons.size() - 1;
		} else if (index >= buttons.size()) {
			index = 0;
		}
		return index;
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
}
