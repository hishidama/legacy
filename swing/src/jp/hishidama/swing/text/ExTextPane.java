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
 * 拡張JTextPane.
 *<p>
 * 折り返しの有無を設定できる。
 * </p>
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/java/swing/JTextPane.html"
 *         >ひしだま</a>
 * @since 2009.03.22
 * @version 2009.04.12 UNDO・ポップアップ機能追加
 */
public class ExTextPane extends JTextPane {
	private static final long serialVersionUID = 4589613545320717526L;

	protected TextManager textManager;

	private boolean wrap = false;

	/**
	 * コンストラクター.
	 */
	public ExTextPane() {
		super();
		textManager = createDefaultTextManager();
		textManager.installTo(this);
	}

	/**
	 * コンストラクター.
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
	 * テキストの折り返し有無を設定する。
	 *
	 * @param wrap
	 *            trueの場合、折り返す
	 * @see JTextArea#setLineWrap(boolean)
	 */
	public void setLineWrap(boolean wrap) {
		boolean old = this.wrap;
		this.wrap = wrap;
		firePropertyChange("lineWrap", old, wrap);
	}

	/**
	 * テキストの折り返し有無を取得する。
	 *
	 * @return trueの場合、折り返す
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
			Dimension sz = ui.getPreferredSize(this); // 実際の表示文字列サイズ
			if (sz.width < w) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 文字列追加.
	 *
	 * @param str
	 *            文字列
	 */
	public void append(String str) {
		append(str, (AttributeSet) null);
	}

	/**
	 * 文字列追加.
	 *
	 * @param str
	 *            文字列
	 * @param fc
	 *            前景色
	 */
	public void append(String str, Color fc) {
		SimpleAttributeSet attr = new SimpleAttributeSet();
		// attr.addAttribute(StyleConstants.Foreground, fc);
		StyleConstants.setForeground(attr, fc);

		append(str, attr);
	}

	/**
	 * 文字列追加.
	 *
	 * @param str
	 *            文字列
	 * @param attr
	 *            属性
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
