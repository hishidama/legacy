package jp.hishidama.swing.radio;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;

/**
 * 拡張ButtonGroup.
 *<p>
 * 当ボタングループでラジオボタンをグルーピングすると、カーソルの上下左右で選択肢を移動できる。
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/java/swing/JRadioButton.html"
 *         >ひしだま</a>
 * @since 2009.04.05
 * @version 2009.04.19 ボタンを引数にとるコンストラクターを追加
 */
public class ExButtonGroup extends ButtonGroup implements KeyListener {
	private static final long serialVersionUID = -1475598640349542914L;

	/**
	 * コンストラクター.
	 */
	public ExButtonGroup() {
	}

	/**
	 * コンストラクター.
	 *
	 * @param bs
	 *            ボタン
	 * @see #add(AbstractButton)
	 */
	public ExButtonGroup(AbstractButton... bs) {
		for (AbstractButton b : bs) {
			add(b);
		}
	}

	@Override
	public void add(AbstractButton b) {
		super.add(b);
		b.removeKeyListener(this);
		b.addKeyListener(this);
	}

	@Override
	public void remove(AbstractButton b) {
		b.removeKeyListener(this);
		super.remove(b);
	}

	/**
	 * 選択
	 *
	 * @param index
	 *            インデックス
	 * @since 2009.04.19
	 */
	public void setSelectedIndex(int index) {
		AbstractButton b = buttons.get(index);
		setSelected(b.getModel(), true);
	}

	/**
	 * 選択インデックス取得
	 *
	 * @return インデックス（選択されていない場合は-1）
	 * @since 2009.04.19
	 */
	public int getSelectedIndex() {
		for (int i = 0; i < buttons.size(); i++) {
			AbstractButton b = buttons.get(i);
			if (b.isSelected()) {
				return i;
			}
		}
		return -1;
	}

	// KeyListener

	@Override
	public void keyPressed(KeyEvent e) {
		int size = buttons.size();
		if (size <= 0) {
			return;
		}

		int z;
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
		case KeyEvent.VK_LEFT:
			z = -1;
			break;
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_RIGHT:
			z = +1;
			break;
		default:
			return;
		}
		AbstractButton b = (AbstractButton) e.getSource();
		for (int i = 0; i < size; i++) {
			if (buttons.get(i) == b) {
				int n = nextIndex(i, z);
				AbstractButton nb = buttons.get(n);
				nb.setSelected(true);
				nb.requestFocusInWindow();
				e.consume();
				return;
			}
		}
	}

	protected int nextIndex(int index, int z) {
		index += z;
		if (index < 0) {
			index = buttons.size() - 1;
		} else if (index >= buttons.size()) {
			index = 0;
		}
		return index;
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
}
