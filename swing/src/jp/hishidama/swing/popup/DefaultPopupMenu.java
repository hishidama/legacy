package jp.hishidama.swing.popup;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import javax.swing.text.DefaultEditorKit;

/**
 * デフォルトポップアップメニュー.
 * <p>
 * 使用例：
 * </p>
 *
 * <pre>
 * JTextField text = new JTextField();
 * new DefaultPopupMenu().installTo(text);
 *</pre>
 *
 * これにより、テキストフィールドを右クリックした時あるいはコンテキストメニューキーを押した時にポップアップメニューが表示される。
 * <ul>
 * <li>切り取り(<u>X</u>)</li>
 * <li>コピー(<u>C</u>)</li>
 * <li>貼り付け(<u>P</u>)</li>
 * <li>すべて選択(<u>A</u>)</li>
 * </ul>
 * ※アクションが対象コンポーネントに登録されていない場合は、メニューは表示されない。<br>
 * 例えばJTreeでは「すべて選択」は表示されない。<br>
 * 逆にコピー&amp;ペーストは実際は動作しない場合でも割り当てられている事が多いので、ほぼ常に表示される（選択した際に動作するとは限らない）
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/java/swing/JPopupMenu.html"
 *         >ひしだま</a>
 * @since 2009.04.11
 * @version 2009.04.12
 */
public class DefaultPopupMenu extends AbstractAction implements MouseListener {
	private static final long serialVersionUID = -6253061612303005121L;

	/**
	 * アクション名
	 */
	public static String DefaultPopupMenu_NAME = "context-menu";

	/**
	 * コンストラクター.
	 */
	public DefaultPopupMenu() {
		super(DefaultPopupMenu_NAME);
	}

	/**
	 * 初期設定.
	 * <p>
	 * コンポーネントに対し、ポップアップ開始マウスイベントやキーイベントを登録する。
	 * </p>
	 *
	 * @param c
	 *            ポップアップメニュー操作対象のコンポーネント
	 */
	public void installTo(JComponent c) {
		c.removeMouseListener(this);
		c.addMouseListener(this);

		InputMap im = c.getInputMap();
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_CONTEXT_MENU, 0),
				DefaultPopupMenu_NAME);
		ActionMap am = c.getActionMap();
		am.put(DefaultPopupMenu_NAME, this);
	}

	/**
	 * アンインストール.
	 *
	 * @param c
	 *            対象コンポーネント
	 */
	public void uninstall(JComponent c) {
		c.removeMouseListener(this);
		InputMap im = c.getInputMap();
		im.remove(KeyStroke.getKeyStroke(KeyEvent.VK_CONTEXT_MENU, 0));
		ActionMap am = c.getActionMap();
		am.remove(DefaultPopupMenu_NAME);
	}

	/**
	 * コンテキストメニューキー押下時の処理.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		JComponent c = (JComponent) e.getSource();
		Point pos = c.getMousePosition();
		if (pos != null) {
			showPopup(c, pos.x, pos.y);
		}
	}

	// マウスイベント処理
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	/**
	 * マウスキー押下時の処理.
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		mousePopup(e);
	}

	/**
	 * マウスのキーを離した時の処理.
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		mousePopup(e);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	protected void mousePopup(MouseEvent e) {
		if (!e.isPopupTrigger()) {
			return;
		}
		JComponent c = (JComponent) e.getSource();
		showPopup(c, e.getX(), e.getY());
		e.consume();
	}

	/**
	 * ポップアップメニュー表示.
	 * <p>
	 * マウス駆動とキー駆動で共通。
	 * </p>
	 *
	 * @param c
	 *            対象コンポーネント
	 * @param x
	 * @param y
	 */
	protected void showPopup(JComponent c, int x, int y) {
		JPopupMenu pmenu = new JPopupMenu();
		initPopupMenu(pmenu, c);
		pmenu.show(c, x, y);
	}

	protected void initPopupMenu(JPopupMenu pmenu, JComponent c) {
		ActionMap am = c.getActionMap();

		Action cut = am.get("cut");
		addMenu(pmenu, "切り取り(X)", cut, 'X', KeyStroke.getKeyStroke(
				KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK));

		Action copy = am.get("copy");
		addMenu(pmenu, "コピー(C)", copy, 'C', KeyStroke.getKeyStroke(
				KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK));

		Action paste = am.get("paste");
		addMenu(pmenu, "貼り付け(V)", paste, 'V', KeyStroke.getKeyStroke(
				KeyEvent.VK_V, KeyEvent.CTRL_DOWN_MASK));

		Action all = am.get(DefaultEditorKit.selectAllAction);
		addMenu(pmenu, "すべて選択(A)", all, 'A', KeyStroke.getKeyStroke(
				KeyEvent.VK_A, KeyEvent.CTRL_DOWN_MASK));
	}

	protected boolean addMenu(JPopupMenu menu, String text, Action action,
			int mnemonic, KeyStroke ks) {
		if (action != null) {
			JMenuItem mi = menu.add(new PopupMenuDelegateAction(action));
			if (text != null) {
				mi.setText(text);
			}
			if (mnemonic != 0) {
				mi.setMnemonic(mnemonic);
			}
			if (ks != null) {
				mi.setAccelerator(ks);
			}
			return true;
		}
		return false;
	}
}
