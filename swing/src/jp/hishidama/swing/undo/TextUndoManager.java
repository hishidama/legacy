package jp.hishidama.swing.undo;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.*;
import javax.swing.text.*;
import javax.swing.undo.*;

/**
 * �e�L�X�gUNDO�}�l�[�W���[.
 * <p>
 * JTextComponent�ɕ�������͂�UNDO/REDO�@�\��ǉ�����ׂ̃}�l�[�W���[�B
 * </p>
 * <p>
 * �W����UndoManager�ł́A1��������UNDO/REDO�ɂȂ�B<br>
 * ��UndoManager�ł́A��x�̌Ăяo���œ�����ށi�܂蕶���̒ǉ��E�폜�j��UNDO/REDO��A�����čs�����Ƃɂ��A������P�ʂł�UNDO/
 * REDO����������B
 * </p>
 *
 * <p>
 * ��<a target="hishidama" href=
 * "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/swing/UndoManager.html"
 * >�g�p��</a>
 * </p>
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/swing.html"
 *         >�Ђ�����</a>
 * @since 2007.02.24
 * @version 2009.04.12 UndoManagerInstall��ǉ�
 * @version 2009.04.24 CompoundEdit���g�������ɕύX
 */
public class TextUndoManager extends UndoManager implements
		UndoableEditListener, UndoManagerInstall {
	private static final long serialVersionUID = 5375924557489349842L;

	protected UndoManagerUtil umu;

	/**
	 * ������.
	 * <p>
	 * JTextComponent�ɓ�UndoManager��o�^����B
	 * </p>
	 *
	 * @param tc
	 *            �e�L�X�g�R���|�[�l���g�iJEditorPane�EJTextField�EJTextArea���j
	 */
	public void install(JTextComponent tc) {
		if (tc == null)
			return;

		if (umu == null) {
			umu = new UndoManagerUtil(this);
		}
		umu.installTo(tc);

		if (handler == null)
			handler = createHandler();
		tc.addKeyListener(handler);
		tc.addMouseListener(handler);
		tc.addFocusListener(handler);
		update();
	}

	/**
	 * �I��.
	 * <p>
	 * JTextComponent���瓖UndoManager���폜����B
	 * </p>
	 *
	 * @param tc
	 *            �e�L�X�g�R���|�[�l���g�iJEditorPane�EJTextField�EJTextArea���j
	 */
	public void uninstall(JTextComponent tc) {
		if (tc == null)
			return;

		if (umu != null) {
			umu.uninstall(tc);
			// umu = null;
		} else {
			Document doc = tc.getDocument();
			doc.removeUndoableEditListener(this);
		}

		tc.removeKeyListener(handler);
		tc.removeMouseListener(handler);
		tc.removeFocusListener(handler);
	}

	protected EventHandler handler;

	protected EventHandler createHandler() {
		return new EventHandler();
	}

	@Override
	public void undoableEditHappened(UndoableEditEvent e) {
		super.undoableEditHappened(e);
		update();
	}

	/**
	 * Undo���e�X�V.
	 * <p>
	 * Undo/Redo���j���[�Ɋւ��ύX���s��ꂽ�Ƃ��ɌĂ΂��B
	 * </p>
	 * <p>
	 * �T�u�N���X�ŃI�[�o�[���C�h���āAUndo/Redo���j���[��enabled�̍X�V�Ȃǂ��s���B
	 * </p>
	 */
	public void update() {
	}

	@Override
	public synchronized void discardAllEdits() {
		super.discardAllEdits();
		update();
	}

	/**
	 * @see CompoundEdit
	 */
	static class SameEdit extends AbstractUndoableEdit {
		private static final long serialVersionUID = 3096295069062300124L;

		protected boolean inProgress = true;
		protected List<UndoableEdit> edits = new ArrayList<UndoableEdit>();

		public SameEdit(UndoableEdit e) {
			edits.add(e);
		}

		@Override
		public void undo() throws CannotUndoException {
			super.undo();
			for (int i = edits.size() - 1; i >= 0; i--) {
				UndoableEdit e = edits.get(i);
				e.undo();
			}
		}

		@Override
		public void redo() throws CannotRedoException {
			super.redo();
			for (UndoableEdit e : edits) {
				e.redo();
			}
		}

		protected UndoableEdit lastEdit() {
			int count = edits.size();
			return edits.get(count - 1);
		}

		@Override
		public void die() {
			for (int i = edits.size() - 1; i >= 0; i--) {
				UndoableEdit e = edits.get(i);
				e.die();
			}
			super.die();
		}

		@Override
		public boolean addEdit(UndoableEdit anEdit) {
			if (!inProgress) {
				return false;
			}
			UndoableEdit last = lastEdit();
			if (last.getPresentationName().equals(anEdit.getPresentationName())) {
				edits.add(anEdit);
				return true;
			}
			return false;
		}

		public void end() {
			inProgress = false;
		}

		public boolean isInProgress() {
			return inProgress;
		}

		@Override
		public boolean isSignificant() {
			for (UndoableEdit e : edits) {
				if (e.isSignificant()) {
					return true;
				}
			}
			return false;
		}

		@Override
		public String getPresentationName() {
			UndoableEdit last = lastEdit();
			return last.getPresentationName();
		}

		@Override
		public String getUndoPresentationName() {
			UndoableEdit last = lastEdit();
			return last.getUndoPresentationName();
		}

		@Override
		public String getRedoPresentationName() {
			UndoableEdit last = lastEdit();
			return last.getRedoPresentationName();
		}

		@Override
		public String toString() {
			return super.toString() + " inProgress: " + inProgress + " edits: "
					+ edits;
		}
	}

	/**
	 * UNDO���X�g�ɋ�؂������.
	 */
	public void addSeparator() {
		SameEdit last = (SameEdit) lastEdit();
		if (last != null) {
			last.end();
		}
	}

	@Override
	public synchronized boolean addEdit(UndoableEdit anEdit) {
		UndoableEdit last = lastEdit();
		if (last != null && last.addEdit(anEdit)) {
			return true;
		}
		SameEdit se = new SameEdit(anEdit);
		return super.addEdit(se);
	}

	@Override
	public synchronized void undo() throws CannotUndoException {
		addSeparator();
		if (!canUndo())
			return;

		super.undo();

		update();
	}

	@Override
	public synchronized void redo() throws CannotRedoException {
		addSeparator();
		if (!canRedo())
			return;

		super.redo();

		update();
	}

	protected class EventHandler implements KeyListener, MouseListener,
			FocusListener {

		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_Z:
				// zKeyPressed(e);
				break;
			case KeyEvent.VK_Y:
				// yKeyPressed(e);
				break;
			case KeyEvent.VK_UP:
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_HOME:
			case KeyEvent.VK_END:
			case KeyEvent.VK_PAGE_UP:
			case KeyEvent.VK_PAGE_DOWN:
				addSeparator();
				break;
			}
		}

		/**
		 * Z�L�[����.
		 * <p>
		 * �A�N�Z�����[�^�L�[����UNDO����������ꍇ�́A�����\�b�h���I�[�o�[���C�h���Ė������ɏo����B
		 * </p>
		 *
		 * @param e
		 *            �L�[�C�x���g
		 */
		protected void zKeyPressed(KeyEvent e) {
			if (e.isControlDown()) {
				undo();
				e.consume();
			}
		}

		/**
		 * Y�L�[����.
		 * <p>
		 * �A�N�Z�����[�^�L�[����REDO����������ꍇ�́A�����\�b�h���I�[�o�[���C�h���Ė������ɏo����B
		 * </p>
		 *
		 * @param e
		 *            �L�[�C�x���g
		 */
		protected void yKeyPressed(KeyEvent e) {
			if (e.isControlDown()) {
				redo();
				e.consume();
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}

		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// �{���́A�e�L�X�g�̃J�[�\���̈ʒu���ς�����Ƃ�������؂肽����
			// �����ł͔��f�ł��Ȃ��̂ŁA��ɋ�؂�
			addSeparator();
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void focusGained(FocusEvent e) {
			addSeparator();
		}

		@Override
		public void focusLost(FocusEvent e) {
			addSeparator();
		}
	}
}
