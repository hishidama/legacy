package jp.hishidama.swing.text;

import java.awt.event.FocusEvent;

import javax.swing.text.DefaultCaret;

/**
 * 常に表示されるキャレット.
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/java/swing/caret.html"
 *         >ひしだま</a>
 * @since 2009.04.19
 */
public class VisibleCaret extends DefaultCaret {
	private static final long serialVersionUID = 1140099288992101139L;

	/**
	 * コンストラクター.
	 */
	public VisibleCaret() {
	}

	/**
	 * コンストラクター.
	 *
	 * @param rate
	 *            ブリンク間隔
	 * @see #setBlinkRate(int)
	 */
	public VisibleCaret(int rate) {
		setBlinkRate(rate);
	}

	@Override
	public void focusGained(FocusEvent e) {
		setVisible(true);
		setSelectionVisible(true);
	}
}
