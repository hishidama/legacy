package jp.hishidama.swing.undo;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;
import javax.swing.text.Document;
import javax.swing.undo.UndoManager;

/**
 * UNDO可能JTextField.
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/java/swing/JTextField.html"
 *         >ひしだま</a>
 * @since 2009.03.15
 */
public class UndoTextField extends JTextField implements KeyListener {
	private static final long serialVersionUID = 1425296751813583239L;

	protected UndoManager um = new UndoManager();

	public UndoTextField() {
		// 編集リスナーの登録
		Document doc = getDocument();
		doc.addUndoableEditListener(um);

		// UNDO/REDOを実行するキーの登録
		addKeyListener(this);
	}

	/**
	 * UNDO情報をクリアする.
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
		case KeyEvent.VK_Z: // CTRL+Zのとき、UNDO実行
			if (e.isControlDown() && um.canUndo()) {
				um.undo();
				e.consume();
			}
			break;
		case KeyEvent.VK_Y: // CTRL+Yのとき、REDO実行
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
