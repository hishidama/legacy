package jp.hishidama.swing.text;

import javax.swing.JTextField;
import javax.swing.undo.UndoManager;

/**
 * 拡張JTextField.
 *
 * @see TextManager
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/java/swing/JTextField.html"
 *         >ひしだま</a>
 * @since 2009.04.19
 * @version 2009.10.25
 */
public class ExTextField extends JTextField {
	private static final long serialVersionUID = 1814642627567181326L;

	protected TextManager textManager;

	/**
	 * コンストラクター.
	 */
	public ExTextField() {
		this(null, true);
	}

	/**
	 * コンストラクター.
	 *
	 * @param text
	 */
	public ExTextField(String text) {
		this(text, true);
	}

	/**
	 * コンストラクター.
	 *
	 * @param text
	 * @param editable
	 */
	public ExTextField(String text, boolean editable) {
		super(text);
		setEditable(editable);

		textManager = createDefaultTextManager();
		textManager.installTo(this);
	}

	protected TextManager createDefaultTextManager() {
		TextManager tm = new TextManager();
		tm.setEnableUndoManager(isEditable());
		return tm;
	}

	public TextManager getTextManager() {
		return textManager;
	}

	/**
	 * UNDO情報をクリアする.
	 */
	public void clearUndoEdit() {
		UndoManager um = textManager.getUndoManager();
		um.discardAllEdits();
	}
}
