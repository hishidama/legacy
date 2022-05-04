package jp.hishidama.swing.popup;

import java.awt.Component;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.JTextComponent;

import jp.hishidama.swing.action.DelegateAction;
import jp.hishidama.swing.action.TextDelegateAction;

/**
 * �g���|�b�v�A�b�v���j���[.
 *
 * <p>
 * �f�t�H���g�ňȉ��̃��j���[��o�^����|�b�v�A�b�v���j���[�B
 * </p>
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
 * <pre>
 * JTextField text = new JTextField();
 * JPopupMenu pmenu = new ExPopupMenu(text);
 * text.setComponentPopupMenu(pmenu);
 * comp.setComponentPopupMenu(pmenu); //�����̃R���|�[�l���g�ɓo�^�\�����A�A�N�V�����͂����܂�text�ɑ΂��čs����
 * </pre>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/java/swing/JPopupMenu.html"
 *         >�Ђ�����</a>
 * @since 2009.04.19
 */
public class ExPopupMenu extends JPopupMenu implements PopupMenuListener {
	private static final long serialVersionUID = 164492326728598410L;

	protected JComponent component;

	/**
	 * �R���X�g���N�^�[.
	 * <p>
	 * ���j���[���I�����ꂽ�ہA�����Ŏw�肳�ꂽ�R���|�[�l���g�ɑ΂��ăA�N�V�������Ϗ�����B
	 * </p>
	 *
	 * @param c
	 *            �|�b�v�A�b�v�����ΏۃR���|�[�l���g
	 */
	public ExPopupMenu(JComponent c) {
		this.component = c;
		addPopupMenuListener(this);

		initPopupMenu();
	}

	protected transient ActionMap actionMap;

	protected void initPopupMenu() {
		actionMap = component.getActionMap();
		addMenus();
		actionMap = null;
	}

	protected void addMenus() {
		addCut();
		addCopy();
		addPaste();
		addSelectAll();
	}

	protected boolean addCut() {
		Action cut = actionMap.get("cut");
		return addDelegateTextMenu(component, "�؂���(X)", cut, 'X', KeyStroke
				.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK));
	}

	protected boolean addCopy() {
		Action copy = actionMap.get("copy");
		return addDelegateMenu("�R�s�[(C)", copy, 'C', KeyStroke.getKeyStroke(
				KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK));
	}

	protected boolean addPaste() {
		Action paste = actionMap.get("paste");
		return addDelegateTextMenu(component, "�\��t��(V)", paste, 'V', KeyStroke
				.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_DOWN_MASK));
	}

	protected boolean addSelectAll() {
		Action all = actionMap.get(DefaultEditorKit.selectAllAction);
		return addDelegateMenu("���ׂđI��(A)", all, 'A', KeyStroke.getKeyStroke(
				KeyEvent.VK_A, KeyEvent.CTRL_DOWN_MASK));
	}

	protected boolean addDelegateMenu(String text, Action action) {
		return addDelegateMenu(text, action, 0, null);
	}

	protected boolean addDelegateMenu(String text, Action action, int mnemonic,
			KeyStroke ks) {
		if (action != null) {
			action = new DelegateAction(component, action);
		}
		return addMenu(text, action, mnemonic, ks);
	}

	protected boolean addDelegateTextMenu(JComponent c, String text,
			Action action, int mnemonic, KeyStroke ks) {
		if (c instanceof JTextComponent) {
			JTextComponent tc = (JTextComponent) c;
			action = new TextDelegateAction(tc, action);
			return addMenu(text, action, mnemonic, ks);
		} else {
			return addDelegateMenu(text, action, mnemonic, ks);
		}
	}

	protected boolean addMenu(String text, Action action, int mnemonic,
			KeyStroke ks) {
		if (action != null) {
			JMenuItem mi = this.add(action);
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

	// PopupMenuListener

	/**
	 * �|�b�v�A�b�v���\�������O�ɌĂ΂��B<br>
	 * �����Ŋe���j���[��enabled���Đݒ肷��B
	 */
	@Override
	public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
		refresh(this);
	}

	protected void refresh(Component c) {
		if (c instanceof JMenuItem) {
			JMenuItem mi = (JMenuItem) c;
			refreshMenuItem(mi);
		}
		if (c instanceof JComponent) {
			JComponent jc = (JComponent) c;
			int sz = jc.getComponentCount();
			for (int i = 0; i < sz; i++) {
				refresh(jc.getComponent(i));
			}
		}
	}

	protected void refreshMenuItem(JMenuItem mi) {
		Action a = mi.getAction();
		if (a != null) {
			mi.setEnabled(a.isEnabled());
		} else {
			// mi.setEnabled(true);
		}
	}

	@Override
	public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
	}

	@Override
	public void popupMenuCanceled(PopupMenuEvent e) {
	}
}
