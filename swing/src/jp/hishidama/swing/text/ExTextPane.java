package jp.hishidama.swing.text;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JViewport;
import javax.swing.plaf.TextUI;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 * �g��JTextPane.
 *<p>
 * �܂�Ԃ��̗L����ݒ�ł���B
 * </p>
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/java/swing/JTextPane.html"
 *         >�Ђ�����</a>
 * @since 2009.03.22
 * @version 2009.04.12 UNDO�E�|�b�v�A�b�v�@�\�ǉ�
 */
public class ExTextPane extends JTextPane {
	private static final long serialVersionUID = 4589613545320717526L;

	protected TextManager textManager;

	private boolean wrap = false;

	/**
	 * �R���X�g���N�^�[.
	 */
	public ExTextPane() {
		super();
		textManager = createDefaultTextManager();
		textManager.installTo(this);
	}

	/**
	 * �R���X�g���N�^�[.
	 *
	 * @param doc
	 */
	public ExTextPane(StyledDocument doc) {
		super(doc);
		textManager = createDefaultTextManager();
		textManager.installTo(this);
	}

	protected TextManager createDefaultTextManager() {
		return new TextManager();
	}

	/**
	 * �e�L�X�g�̐܂�Ԃ��L����ݒ肷��B
	 *
	 * @param wrap
	 *            true�̏ꍇ�A�܂�Ԃ�
	 * @see JTextArea#setLineWrap(boolean)
	 */
	public void setLineWrap(boolean wrap) {
		boolean old = this.wrap;
		this.wrap = wrap;
		firePropertyChange("lineWrap", old, wrap);
	}

	/**
	 * �e�L�X�g�̐܂�Ԃ��L�����擾����B
	 *
	 * @return true�̏ꍇ�A�܂�Ԃ�
	 * @see JTextArea#getLineWrap()
	 */
	public boolean getLineWrap() {
		return wrap;
	}

	@Override
	public boolean getScrollableTracksViewportWidth() {
		if (wrap) {
			return true;
		}

		Object parent = getParent();
		if (parent instanceof JViewport) {
			JViewport port = (JViewport) parent;
			int w = port.getWidth();

			TextUI ui = getUI();
			Dimension sz = ui.getPreferredSize(this); // ���ۂ̕\��������T�C�Y
			if (sz.width < w) {
				return true;
			}
		}
		return false;
	}

	/**
	 * ������ǉ�.
	 *
	 * @param str
	 *            ������
	 */
	public void append(String str) {
		append(str, (AttributeSet) null);
	}

	/**
	 * ������ǉ�.
	 *
	 * @param str
	 *            ������
	 * @param fc
	 *            �O�i�F
	 */
	public void append(String str, Color fc) {
		SimpleAttributeSet attr = new SimpleAttributeSet();
		// attr.addAttribute(StyleConstants.Foreground, fc);
		StyleConstants.setForeground(attr, fc);

		append(str, attr);
	}

	/**
	 * ������ǉ�.
	 *
	 * @param str
	 *            ������
	 * @param attr
	 *            ����
	 * @see JTextArea#append(String)
	 */
	public void append(String str, AttributeSet attr) {
		Document doc = getDocument();
		if (doc != null) {
			try {
				doc.insertString(doc.getLength(), str, attr);
			} catch (BadLocationException e) {
			}
		}
	}

}
