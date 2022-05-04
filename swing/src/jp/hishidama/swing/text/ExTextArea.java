package jp.hishidama.swing.text;

import javax.swing.JTextArea;
import javax.swing.undo.UndoManager;

/**
 * �g��JTextArea.
 *
 * @see TextManager
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/java/swing/JTextComponent.html"
 *         >�Ђ�����</a>
 * @since 2009.04.19
 */
public class ExTextArea extends JTextArea {
	private static final long serialVersionUID = 7740398681415777592L;

	protected TextManager textManager;

	/**
	 * �R���X�g���N�^�[.
	 */
	public ExTextArea() {
		super();
		textManager = createDefaultTextManager();
		textManager.installTo(this);
	}

	/**
	 * �R���X�g���N�^�[.
	 *
	 * @param text
	 */
	public ExTextArea(String text) {
		super(text);
		textManager = createDefaultTextManager();
		textManager.installTo(this);
	}

	protected TextManager createDefaultTextManager() {
		return new TextManager();
	}

	/**
	 * UNDO�����N���A����.
	 */
	public void clearUndoEdit() {
		UndoManager um = textManager.getUndoManager();
		um.discardAllEdits();
	}
}
