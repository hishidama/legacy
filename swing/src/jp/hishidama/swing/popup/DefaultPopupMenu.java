package jp.hishidama.swing.popup;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import javax.swing.text.DefaultEditorKit;

/**
 * �f�t�H���g�|�b�v�A�b�v���j���[.
 * <p>
 * �g�p��F
 * </p>
 *
 * <pre>
 * JTextField text = new JTextField();
 * new DefaultPopupMenu().installTo(text);
 *</pre>
 *
 * ����ɂ��A�e�L�X�g�t�B�[���h���E�N���b�N���������邢�̓R���e�L�X�g���j���[�L�[�����������Ƀ|�b�v�A�b�v���j���[���\�������B
 * <ul>
 * <li>�؂���(<u>X</u>)</li>
 * <li>�R�s�[(<u>C</u>)</li>
 * <li>�\��t��(<u>P</u>)</li>
 * <li>���ׂđI��(<u>A</u>)</li>
 * </ul>
 * ���A�N�V�������ΏۃR���|�[�l���g�ɓo�^����Ă��Ȃ��ꍇ�́A���j���[�͕\������Ȃ��B<br>
 * �Ⴆ��JTree�ł́u���ׂđI���v�͕\������Ȃ��B<br>
 * �t�ɃR�s�[&amp;�y�[�X�g�͎��ۂ͓��삵�Ȃ��ꍇ�ł����蓖�Ă��Ă��鎖�������̂ŁA�قڏ�ɕ\�������i�I�������ۂɓ��삷��Ƃ͌���Ȃ��j
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/java/swing/JPopupMenu.html"
 *         >�Ђ�����</a>
 * @since 2009.04.11
 * @version 2009.04.12
 */
public class DefaultPopupMenu extends AbstractAction implements MouseListener {
	private static final long serialVersionUID = -6253061612303005121L;

	/**
	 * �A�N�V������
	 */
	public static String DefaultPopupMenu_NAME = "context-menu";

	/**
	 * �R���X�g���N�^�[.
	 */
	public DefaultPopupMenu() {
		super(DefaultPopupMenu_NAME);
	}

	/**
	 * �����ݒ�.
	 * <p>
	 * �R���|�[�l���g�ɑ΂��A�|�b�v�A�b�v�J�n�}�E�X�C�x���g��L�[�C�x���g��o�^����B
	 * </p>
	 *
	 * @param c
	 *            �|�b�v�A�b�v���j���[����Ώۂ̃R���|�[�l���g
	 */
	public void installTo(JComponent c) {
		c.removeMouseListener(this);
		c.addMouseListener(this);

		InputMap im = c.getInputMap();
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_CONTEXT_MENU, 0),
				DefaultPopupMenu_NAME);
		ActionMap am = c.getActionMap();
		am.put(DefaultPopupMenu_NAME, this);
	}

	/**
	 * �A���C���X�g�[��.
	 *
	 * @param c
	 *            �ΏۃR���|�[�l���g
	 */
	public void uninstall(JComponent c) {
		c.removeMouseListener(this);
		InputMap im = c.getInputMap();
		im.remove(KeyStroke.getKeyStroke(KeyEvent.VK_CONTEXT_MENU, 0));
		ActionMap am = c.getActionMap();
		am.remove(DefaultPopupMenu_NAME);
	}

	/**
	 * �R���e�L�X�g���j���[�L�[�������̏���.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		JComponent c = (JComponent) e.getSource();
		Point pos = c.getMousePosition();
		if (pos != null) {
			showPopup(c, pos.x, pos.y);
		}
	}

	// �}�E�X�C�x���g����
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	/**
	 * �}�E�X�L�[�������̏���.
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		mousePopup(e);
	}

	/**
	 * �}�E�X�̃L�[�𗣂������̏���.
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		mousePopup(e);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	protected void mousePopup(MouseEvent e) {
		if (!e.isPopupTrigger()) {
			return;
		}
		JComponent c = (JComponent) e.getSource();
		showPopup(c, e.getX(), e.getY());
		e.consume();
	}

	/**
	 * �|�b�v�A�b�v���j���[�\��.
	 * <p>
	 * �}�E�X�쓮�ƃL�[�쓮�ŋ��ʁB
	 * </p>
	 *
	 * @param c
	 *            �ΏۃR���|�[�l���g
	 * @param x
	 * @param y
	 */
	protected void showPopup(JComponent c, int x, int y) {
		JPopupMenu pmenu = new JPopupMenu();
		initPopupMenu(pmenu, c);
		pmenu.show(c, x, y);
	}

	protected void initPopupMenu(JPopupMenu pmenu, JComponent c) {
		ActionMap am = c.getActionMap();

		Action cut = am.get("cut");
		addMenu(pmenu, "�؂���(X)", cut, 'X', KeyStroke.getKeyStroke(
				KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK));

		Action copy = am.get("copy");
		addMenu(pmenu, "�R�s�[(C)", copy, 'C', KeyStroke.getKeyStroke(
				KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK));

		Action paste = am.get("paste");
		addMenu(pmenu, "�\��t��(V)", paste, 'V', KeyStroke.getKeyStroke(
				KeyEvent.VK_V, KeyEvent.CTRL_DOWN_MASK));

		Action all = am.get(DefaultEditorKit.selectAllAction);
		addMenu(pmenu, "���ׂđI��(A)", all, 'A', KeyStroke.getKeyStroke(
				KeyEvent.VK_A, KeyEvent.CTRL_DOWN_MASK));
	}

	protected boolean addMenu(JPopupMenu menu, String text, Action action,
			int mnemonic, KeyStroke ks) {
		if (action != null) {
			JMenuItem mi = menu.add(new PopupMenuDelegateAction(action));
			if (text != null) {
				mi.setText(text);
			}
			if (mnemonic != 0) {
				mi.setMnemonic(mnemonic);
			}
			if (ks != null) {
				mi.setAccelerator(ks);
			}
			return true;
		}
		return false;
	}
}
