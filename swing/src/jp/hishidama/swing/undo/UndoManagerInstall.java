package jp.hishidama.swing.undo;

import javax.swing.text.JTextComponent;

/**
 * インストールメソッドを含むUndoManager
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/swing.html"
 *         >ひしだま</a>
 * @since 2009.04.12
 * @see jp.hishidama.swing.text.TextManager
 */
public interface UndoManagerInstall {
	/**
	 * 初期化.
	 * <p>
	 * JTextComponentに当UndoManagerを登録する。
	 * </p>
	 *
	 * @param tc
	 *            テキストコンポーネント（JEditorPane・JTextField・JTextArea等）
	 */
	public void install(JTextComponent tc);

	/**
	 * 終了.
	 * <p>
	 * JTextComponentから当UndoManagerを削除する。
	 * </p>
	 *
	 * @param tc
	 *            テキストコンポーネント（JEditorPane・JTextField・JTextArea等）
	 */
	public void uninstall(JTextComponent tc);
}
