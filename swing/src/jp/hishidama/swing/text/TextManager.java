package jp.hishidama.swing.text;

import javax.swing.Action;
import javax.swing.JPopupMenu;
import javax.swing.text.JTextComponent;
import javax.swing.undo.UndoManager;

import jp.hishidama.swing.popup.ExPopupMenu;
import jp.hishidama.swing.undo.UndoManagerInstall;
import jp.hishidama.swing.undo.UndoManagerUtil;

/**
 * JTextComponent拡張ユーティリティー.
 * <p>
 * JTextComponentに機能を付加する。
 * </p>
 * <ul>
 * <li>UNDO/REDO機能</li>
 * <li>ポップアップメニュー</li>
 * </ul>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/java/swing/JTextComponent.html"
 *         >ひしだま</a>
 * @since 2009.04.12
 * @version 2009.04.19
 */
public class TextManager {

	protected boolean enableUndoManager = true;
	protected UndoManager undoManager;

	protected boolean enablePopupMenu = true;
	protected JPopupMenu popupMenu;

	public TextManager() {
	}

	public TextManager(UndoManager um) {
		this.undoManager = um;
	}

	public boolean isEnableUndoManager() {
		return enableUndoManager;
	}

	public void setEnableUndoManager(boolean enableUndoManager) {
		this.enableUndoManager = enableUndoManager;
	}

	public UndoManager getUndoManager() {
		return undoManager;
	}

	public void setUndoManager(UndoManager undoManager) {
		this.undoManager = undoManager;
	}

	public boolean isEnablePopupMenu() {
		return enablePopupMenu;
	}

	public void setEnablePopupMenu(boolean enablePopupMenu) {
		this.enablePopupMenu = enablePopupMenu;
	}

	public JPopupMenu getPopupMenuManager() {
		return popupMenu;
	}

	public void setPopupMenuManager(JPopupMenu popup) {
		this.popupMenu = popup;
	}

	public void installTo(JTextComponent tc) {
		if (enableUndoManager) {
			if (undoManager == null) {
				undoManager = createDefaultUndoManager();
			}
			installUndoManager(tc, undoManager);
		}
		if (enablePopupMenu) {
			if (popupMenu == null) {
				popupMenu = createPopupMenu(tc);
			}
			tc.setComponentPopupMenu(popupMenu);
		}
	}

	protected UndoManager createDefaultUndoManager() {
		return new UndoManager();
	}

	public static void installUndoManager(JTextComponent tc, UndoManager um) {
		if (um instanceof UndoManagerInstall) {
			UndoManagerInstall umi = (UndoManagerInstall) um;
			umi.install(tc);
		} else {
			UndoManagerUtil umu = new UndoManagerUtil(um);
			umu.installTo(tc);
		}
	}

	protected JPopupMenu createPopupMenu(final JTextComponent tc) {
		return new ExPopupMenu(tc) {
			private static final long serialVersionUID = -7878047470095782252L;

			@Override
			protected void addMenus() {
				super.addMenus();

				if (enableUndoManager) {
					super.addSeparator();

					Action undo = actionMap
							.get(UndoManagerUtil.UndoAction_NAME);
					addDelegateMenu(null, undo);
					Action redo = actionMap
							.get(UndoManagerUtil.RedoAction_NAME);
					addDelegateMenu(null, redo);
				}
			}
		};
	}
}
