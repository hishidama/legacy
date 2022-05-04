package jp.hishidama.swing.undo;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.undo.UndoManager;
import javax.swing.undo.UndoableEditSupport;

import jp.hishidama.swing.action.ExAction;

/**
 * UndoManagerユーティリティー.
 * <p>
 * UndoManagerに関するアクセラレーターキーを設定する。
 * </p>
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/swing.html"
 *         >ひしだま</a>
 * @since 2009.04.12
 * @version 2009.04.26 UndoManagerをコンストラクターで指定するよう変更
 */
public class UndoManagerUtil {

	private UndoAction actionUndo;
	private RedoAction actionRedo;

	protected UndoManager um;

	/**
	 * コンストラクター.
	 */
	public UndoManagerUtil() {
		um = new UndoManager();
	}

	/**
	 * コンストラクター.
	 *
	 * @param um
	 *            UndoManager
	 */
	public UndoManagerUtil(UndoManager um) {
		this.um = um;
	}

	/**
	 * UndoManager取得.
	 *
	 * @return UndoManager
	 */
	public UndoManager getUndoManager() {
		return um;
	}

	/**
	 * UndoManager設定.
	 * <p>
	 * テキストコンポーネントにUndoManagerを登録すると共に、アクセラレーターキーを設定する。
	 * </p>
	 *
	 * @param tc
	 *            設定対象テキストコンポーネント
	 * @see #setAcceleratorKey(JComponent)
	 */
	public void installTo(JTextComponent tc) {
		Document doc = tc.getDocument();
		doc.addUndoableEditListener(um);

		setAcceleratorKey(tc);
	}

	/**
	 * UndoManager削除.
	 *
	 * @param tc
	 *            UndoManagerを削除するテキストコンポーネント
	 */
	public void uninstall(JTextComponent tc) {
		Document doc = tc.getDocument();
		doc.removeUndoableEditListener(um);

		removeAcceleratorKey(tc);
	}

	/**
	 * UndoManager設定.
	 * <p>
	 * UndoableEditSupportにUndoManagerを登録すると共に、コンポーネントにアクセラレーターキーを設定する。
	 * </p>
	 *
	 * @param c
	 *            設定対象コンポーネント
	 * @param us
	 *            UndoableEditSupport
	 * @see #setAcceleratorKey(JComponent)
	 * @since 2009.04.25
	 */
	public void installTo(JComponent c, UndoableEditSupport us) {
		us.addUndoableEditListener(um);

		setAcceleratorKey(c);
	}

	/**
	 * UndoManager削除.
	 *
	 * @param c
	 *            UndoManagerを削除するコンポーネント
	 * @param us
	 *            UndoableEditSupport
	 * @since 2009.04.25
	 */
	public void uninstall(JComponent c, UndoableEditSupport us) {
		us.removeUndoableEditListener(um);

		removeAcceleratorKey(c);
	}

	/**
	 * アクセラレーターキー設定.
	 * <p>
	 * Ctrl+Z・Ctrl+YにUNDO・REDOを割り当てる。
	 * </p>
	 *
	 * @param tc
	 *            設定対象テキストコンポーネント
	 * @see #createDefaultUndoAction()
	 * @see #createDefaultRedoAction()
	 * @since 2009.04.25
	 */
	public void setAcceleratorKey(JComponent tc) {
		if (actionUndo == null) {
			actionUndo = createDefaultUndoAction();
		}
		if (actionRedo == null) {
			actionRedo = createDefaultRedoAction();
		}

		InputMap im = tc.getInputMap();
		im.put(actionUndo.getAcceleratorKey(), UndoAction_NAME);
		im.put(actionRedo.getAcceleratorKey(), RedoAction_NAME);
		ActionMap am = tc.getActionMap();
		am.put(UndoAction_NAME, actionUndo);
		am.put(RedoAction_NAME, actionRedo);
	}

	/**
	 * アクセラレーターキー解除.
	 *
	 * @param tc
	 *            テキストコンポーネント
	 * @since 2009.04.25
	 */
	public static void removeAcceleratorKey(JComponent tc) {
		InputMap im = tc.getInputMap();
		ActionMap am = tc.getActionMap();
		removeAction(im, am, UndoAction_NAME);
		removeAction(im, am, RedoAction_NAME);
	}

	protected static void removeAction(InputMap im, ActionMap am, String name) {
		Action a = am.get(name);
		if (a != null) {
			KeyStroke ks = (KeyStroke) a.getValue(Action.ACCELERATOR_KEY);
			if (ks != null) {
				im.remove(ks);
			}
			am.remove(name);
		}
	}

	/**
	 * UNDOメニュー追加.
	 *
	 * @param pmenu
	 *            ポップアップメニュー
	 * @since 2009.04.26
	 */
	public void addMenuUndo(JPopupMenu pmenu) {
		if (actionUndo == null) {
			actionUndo = createDefaultUndoAction();
		}
		if (actionRedo == null) {
			actionRedo = createDefaultRedoAction();
		}
		pmenu.add(actionUndo);
		pmenu.add(actionRedo);
	}

	/**
	 * UNDOアクション生成.
	 *
	 * @return アクションインスタンス
	 */
	public UndoAction createDefaultUndoAction() {
		return new UndoAction(um);
	}

	/**
	 * REDOアクション生成.
	 *
	 * @return アクションインスタンス
	 */
	public RedoAction createDefaultRedoAction() {
		return new RedoAction(um);
	}

	/** UNDOアクション名 */
	public static final String UndoAction_NAME = "元に戻す(U)";
	/** REDOアクション名 */
	public static final String RedoAction_NAME = "やり直す(R)";

	/**
	 * UNDOアクション
	 */
	public static class UndoAction extends ExAction {
		private static final long serialVersionUID = 3893649109654476856L;

		protected UndoManager um;

		/**
		 * コンストラクター.
		 *
		 * @param um
		 *            UNDOを行う際に使われるUndoManager
		 */
		public UndoAction(UndoManager um) {
			super(UndoAction_NAME);
			putMnemonicKey('U');
			putAcceleratorKey(KeyStroke.getKeyStroke(KeyEvent.VK_Z,
					KeyEvent.CTRL_DOWN_MASK));
			this.um = um;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (um.canUndo()) {
				um.undo();
			}
		}

		@Override
		public boolean isEnabled() {
			return um.canUndo();
		}
	}

	/**
	 * REDOアクション
	 */
	public static class RedoAction extends ExAction {
		private static final long serialVersionUID = -7720240262168030520L;

		protected UndoManager um;

		/**
		 * コンストラクター.
		 *
		 * @param um
		 *            REDOを行う際に使われるUndoManager
		 */
		public RedoAction(UndoManager um) {
			super(RedoAction_NAME);
			putMnemonicKey('R');
			putAcceleratorKey(KeyStroke.getKeyStroke(KeyEvent.VK_Y,
					KeyEvent.CTRL_DOWN_MASK));
			this.um = um;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (um.canRedo()) {
				um.redo();
			}
		}

		@Override
		public boolean isEnabled() {
			return um.canRedo();
		}
	}
}
