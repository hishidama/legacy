package jp.hishidama.swing.popup;

import java.awt.Component;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.JTextComponent;

import jp.hishidama.swing.action.DelegateAction;
import jp.hishidama.swing.action.TextDelegateAction;

/**
 * 拡張ポップアップメニュー.
 *
 * <p>
 * デフォルトで以下のメニューを登録するポップアップメニュー。
 * </p>
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
 * <pre>
 * JTextField text = new JTextField();
 * JPopupMenu pmenu = new ExPopupMenu(text);
 * text.setComponentPopupMenu(pmenu);
 * comp.setComponentPopupMenu(pmenu); //複数のコンポーネントに登録可能だが、アクションはあくまでtextに対して行われる
 * </pre>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/java/swing/JPopupMenu.html"
 *         >ひしだま</a>
 * @since 2009.04.19
 */
public class ExPopupMenu extends JPopupMenu implements PopupMenuListener {
	private static final long serialVersionUID = 164492326728598410L;

	protected JComponent component;

	/**
	 * コンストラクター.
	 * <p>
	 * メニューが選択された際、ここで指定されたコンポーネントに対してアクションを委譲する。
	 * </p>
	 *
	 * @param c
	 *            ポップアップ処理対象コンポーネント
	 */
	public ExPopupMenu(JComponent c) {
		this.component = c;
		addPopupMenuListener(this);

		initPopupMenu();
	}

	protected transient ActionMap actionMap;

	protected void initPopupMenu() {
		actionMap = component.getActionMap();
		addMenus();
		actionMap = null;
	}

	protected void addMenus() {
		addCut();
		addCopy();
		addPaste();
		addSelectAll();
	}

	protected boolean addCut() {
		Action cut = actionMap.get("cut");
		return addDelegateTextMenu(component, "切り取り(X)", cut, 'X', KeyStroke
				.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK));
	}

	protected boolean addCopy() {
		Action copy = actionMap.get("copy");
		return addDelegateMenu("コピー(C)", copy, 'C', KeyStroke.getKeyStroke(
				KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK));
	}

	protected boolean addPaste() {
		Action paste = actionMap.get("paste");
		return addDelegateTextMenu(component, "貼り付け(V)", paste, 'V', KeyStroke
				.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_DOWN_MASK));
	}

	protected boolean addSelectAll() {
		Action all = actionMap.get(DefaultEditorKit.selectAllAction);
		return addDelegateMenu("すべて選択(A)", all, 'A', KeyStroke.getKeyStroke(
				KeyEvent.VK_A, KeyEvent.CTRL_DOWN_MASK));
	}

	protected boolean addDelegateMenu(String text, Action action) {
		return addDelegateMenu(text, action, 0, null);
	}

	protected boolean addDelegateMenu(String text, Action action, int mnemonic,
			KeyStroke ks) {
		if (action != null) {
			action = new DelegateAction(component, action);
		}
		return addMenu(text, action, mnemonic, ks);
	}

	protected boolean addDelegateTextMenu(JComponent c, String text,
			Action action, int mnemonic, KeyStroke ks) {
		if (c instanceof JTextComponent) {
			JTextComponent tc = (JTextComponent) c;
			action = new TextDelegateAction(tc, action);
			return addMenu(text, action, mnemonic, ks);
		} else {
			return addDelegateMenu(text, action, mnemonic, ks);
		}
	}

	protected boolean addMenu(String text, Action action, int mnemonic,
			KeyStroke ks) {
		if (action != null) {
			JMenuItem mi = this.add(action);
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

	// PopupMenuListener

	/**
	 * ポップアップが表示される前に呼ばれる。<br>
	 * ここで各メニューのenabledを再設定する。
	 */
	@Override
	public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
		refresh(this);
	}

	protected void refresh(Component c) {
		if (c instanceof JMenuItem) {
			JMenuItem mi = (JMenuItem) c;
			refreshMenuItem(mi);
		}
		if (c instanceof JComponent) {
			JComponent jc = (JComponent) c;
			int sz = jc.getComponentCount();
			for (int i = 0; i < sz; i++) {
				refresh(jc.getComponent(i));
			}
		}
	}

	protected void refreshMenuItem(JMenuItem mi) {
		Action a = mi.getAction();
		if (a != null) {
			mi.setEnabled(a.isEnabled());
		} else {
			// mi.setEnabled(true);
		}
	}

	@Override
	public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
	}

	@Override
	public void popupMenuCanceled(PopupMenuEvent e) {
	}
}
