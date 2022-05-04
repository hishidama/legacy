package jp.hishidama.swing.undo;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.undo.UndoManager;
import javax.swing.undo.UndoableEditSupport;

import jp.hishidama.swing.action.ExAction;

/**
 * UndoManager���[�e�B���e�B�[.
 * <p>
 * UndoManager�Ɋւ���A�N�Z�����[�^�[�L�[��ݒ肷��B
 * </p>
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/swing.html"
 *         >�Ђ�����</a>
 * @since 2009.04.12
 * @version 2009.04.26 UndoManager���R���X�g���N�^�[�Ŏw�肷��悤�ύX
 */
public class UndoManagerUtil {

	private UndoAction actionUndo;
	private RedoAction actionRedo;

	protected UndoManager um;

	/**
	 * �R���X�g���N�^�[.
	 */
	public UndoManagerUtil() {
		um = new UndoManager();
	}

	/**
	 * �R���X�g���N�^�[.
	 *
	 * @param um
	 *            UndoManager
	 */
	public UndoManagerUtil(UndoManager um) {
		this.um = um;
	}

	/**
	 * UndoManager�擾.
	 *
	 * @return UndoManager
	 */
	public UndoManager getUndoManager() {
		return um;
	}

	/**
	 * UndoManager�ݒ�.
	 * <p>
	 * �e�L�X�g�R���|�[�l���g��UndoManager��o�^����Ƌ��ɁA�A�N�Z�����[�^�[�L�[��ݒ肷��B
	 * </p>
	 *
	 * @param tc
	 *            �ݒ�Ώۃe�L�X�g�R���|�[�l���g
	 * @see #setAcceleratorKey(JComponent)
	 */
	public void installTo(JTextComponent tc) {
		Document doc = tc.getDocument();
		doc.addUndoableEditListener(um);

		setAcceleratorKey(tc);
	}

	/**
	 * UndoManager�폜.
	 *
	 * @param tc
	 *            UndoManager���폜����e�L�X�g�R���|�[�l���g
	 */
	public void uninstall(JTextComponent tc) {
		Document doc = tc.getDocument();
		doc.removeUndoableEditListener(um);

		removeAcceleratorKey(tc);
	}

	/**
	 * UndoManager�ݒ�.
	 * <p>
	 * UndoableEditSupport��UndoManager��o�^����Ƌ��ɁA�R���|�[�l���g�ɃA�N�Z�����[�^�[�L�[��ݒ肷��B
	 * </p>
	 *
	 * @param c
	 *            �ݒ�ΏۃR���|�[�l���g
	 * @param us
	 *            UndoableEditSupport
	 * @see #setAcceleratorKey(JComponent)
	 * @since 2009.04.25
	 */
	public void installTo(JComponent c, UndoableEditSupport us) {
		us.addUndoableEditListener(um);

		setAcceleratorKey(c);
	}

	/**
	 * UndoManager�폜.
	 *
	 * @param c
	 *            UndoManager���폜����R���|�[�l���g
	 * @param us
	 *            UndoableEditSupport
	 * @since 2009.04.25
	 */
	public void uninstall(JComponent c, UndoableEditSupport us) {
		us.removeUndoableEditListener(um);

		removeAcceleratorKey(c);
	}

	/**
	 * �A�N�Z�����[�^�[�L�[�ݒ�.
	 * <p>
	 * Ctrl+Z�ECtrl+Y��UNDO�EREDO�����蓖�Ă�B
	 * </p>
	 *
	 * @param tc
	 *            �ݒ�Ώۃe�L�X�g�R���|�[�l���g
	 * @see #createDefaultUndoAction()
	 * @see #createDefaultRedoAction()
	 * @since 2009.04.25
	 */
	public void setAcceleratorKey(JComponent tc) {
		if (actionUndo == null) {
			actionUndo = createDefaultUndoAction();
		}
		if (actionRedo == null) {
			actionRedo = createDefaultRedoAction();
		}

		InputMap im = tc.getInputMap();
		im.put(actionUndo.getAcceleratorKey(), UndoAction_NAME);
		im.put(actionRedo.getAcceleratorKey(), RedoAction_NAME);
		ActionMap am = tc.getActionMap();
		am.put(UndoAction_NAME, actionUndo);
		am.put(RedoAction_NAME, actionRedo);
	}

	/**
	 * �A�N�Z�����[�^�[�L�[����.
	 *
	 * @param tc
	 *            �e�L�X�g�R���|�[�l���g
	 * @since 2009.04.25
	 */
	public static void removeAcceleratorKey(JComponent tc) {
		InputMap im = tc.getInputMap();
		ActionMap am = tc.getActionMap();
		removeAction(im, am, UndoAction_NAME);
		removeAction(im, am, RedoAction_NAME);
	}

	protected static void removeAction(InputMap im, ActionMap am, String name) {
		Action a = am.get(name);
		if (a != null) {
			KeyStroke ks = (KeyStroke) a.getValue(Action.ACCELERATOR_KEY);
			if (ks != null) {
				im.remove(ks);
			}
			am.remove(name);
		}
	}

	/**
	 * UNDO���j���[�ǉ�.
	 *
	 * @param pmenu
	 *            �|�b�v�A�b�v���j���[
	 * @since 2009.04.26
	 */
	public void addMenuUndo(JPopupMenu pmenu) {
		if (actionUndo == null) {
			actionUndo = createDefaultUndoAction();
		}
		if (actionRedo == null) {
			actionRedo = createDefaultRedoAction();
		}
		pmenu.add(actionUndo);
		pmenu.add(actionRedo);
	}

	/**
	 * UNDO�A�N�V��������.
	 *
	 * @return �A�N�V�����C���X�^���X
	 */
	public UndoAction createDefaultUndoAction() {
		return new UndoAction(um);
	}

	/**
	 * REDO�A�N�V��������.
	 *
	 * @return �A�N�V�����C���X�^���X
	 */
	public RedoAction createDefaultRedoAction() {
		return new RedoAction(um);
	}

	/** UNDO�A�N�V������ */
	public static final String UndoAction_NAME = "���ɖ߂�(U)";
	/** REDO�A�N�V������ */
	public static final String RedoAction_NAME = "��蒼��(R)";

	/**
	 * UNDO�A�N�V����
	 */
	public static class UndoAction extends ExAction {
		private static final long serialVersionUID = 3893649109654476856L;

		protected UndoManager um;

		/**
		 * �R���X�g���N�^�[.
		 *
		 * @param um
		 *            UNDO���s���ۂɎg����UndoManager
		 */
		public UndoAction(UndoManager um) {
			super(UndoAction_NAME);
			putMnemonicKey('U');
			putAcceleratorKey(KeyStroke.getKeyStroke(KeyEvent.VK_Z,
					KeyEvent.CTRL_DOWN_MASK));
			this.um = um;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (um.canUndo()) {
				um.undo();
			}
		}

		@Override
		public boolean isEnabled() {
			return um.canUndo();
		}
	}

	/**
	 * REDO�A�N�V����
	 */
	public static class RedoAction extends ExAction {
		private static final long serialVersionUID = -7720240262168030520L;

		protected UndoManager um;

		/**
		 * �R���X�g���N�^�[.
		 *
		 * @param um
		 *            REDO���s���ۂɎg����UndoManager
		 */
		public RedoAction(UndoManager um) {
			super(RedoAction_NAME);
			putMnemonicKey('R');
			putAcceleratorKey(KeyStroke.getKeyStroke(KeyEvent.VK_Y,
					KeyEvent.CTRL_DOWN_MASK));
			this.um = um;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (um.canRedo()) {
				um.redo();
			}
		}

		@Override
		public boolean isEnabled() {
			return um.canRedo();
		}
	}
}
