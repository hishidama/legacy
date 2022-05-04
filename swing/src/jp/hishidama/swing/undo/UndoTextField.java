package jp.hishidama.swing.undo;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;
import javax.swing.text.Document;
import javax.swing.undo.UndoManager;

/**
 * UNDO�\JTextField.
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/java/swing/JTextField.html"
 *         >�Ђ�����</a>
 * @since 2009.03.15
 */
public class UndoTextField extends JTextField implements KeyListener {
	private static final long serialVersionUID = 1425296751813583239L;

	protected UndoManager um = new UndoManager();

	public UndoTextField() {
		// �ҏW���X�i�[�̓o�^
		Document doc = getDocument();
		doc.addUndoableEditListener(um);

		// UNDO/REDO�����s����L�[�̓o�^
		addKeyListener(this);
	}

	/**
	 * UNDO�����N���A����.
	 */
	public void clearUndoEdit() {
		um.discardAllEdits();
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_Z: // CTRL+Z�̂Ƃ��AUNDO���s
			if (e.isControlDown() && um.canUndo()) {
				um.undo();
				e.consume();
			}
			break;
		case KeyEvent.VK_Y: // CTRL+Y�̂Ƃ��AREDO���s
			if (e.isControlDown() && um.canRedo()) {
				um.redo();
				e.consume();
			}
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
}
