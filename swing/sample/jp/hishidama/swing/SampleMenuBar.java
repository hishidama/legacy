package jp.hishidama.swing;

import java.awt.event.*;

import javax.swing.*;

import jp.hishidama.swing.undo.TextUndoManager;

public class SampleMenuBar extends JMenuBar {
	private static final long serialVersionUID = -5550663386110611235L;

	public SampleMenuBar() {
		init();
	}

	protected JRadioButtonMenuItem drag1 = new JRadioButtonMenuItem(
			"DragWindow");

	protected JRadioButtonMenuItem drag2 = new JRadioButtonMenuItem(
			"DragBoxWindow");

	protected JRadioButtonMenuItem drag3 = new JRadioButtonMenuItem(
			"DragImageWindow");

	protected JMenuItem undo = new JMenuItem("元に戻す(U)", 'U');

	protected JMenuItem redo = new JMenuItem("やり直し(R)", 'R');

	private TextUndoManager um;

	public void init() {
		JMenu drag = new JMenu("ドラッグ(D)");
		drag.setMnemonic('D');
		drag.setToolTipText("ドラッグ時に使用するウィンドウの種類を選択する");
		drag.add(drag1);
		drag.add(drag2);
		drag.add(drag3);
		drag1.setToolTipText("擬似的に背景が透過するが、マシンパワーによっては非常に重い");
		drag2.setToolTipText("背景が透過しないBOX");
		drag3.setToolTipText("Imageをドラッグする");
		ButtonGroup group = new ButtonGroup();
		group.add(drag1);
		group.add(drag2);
		group.add(drag3);
		drag1.setSelected(true);

		JMenu edit = new JMenu("編集(E)");
		edit.setMnemonic('E');
		edit.setToolTipText("テキストフィールドのUNDO/REDOのテスト");
		edit.add(undo);
		edit.add(redo);
		UndoHandler uh = new UndoHandler();
		undo.addActionListener(uh);
		redo.addActionListener(uh);

		this.add(drag);
		this.add(edit);
	}

	/**
	 * 選択されているドラッグ用ウィンドウの種類を返す.
	 *
	 * @return 番号
	 */
	public int selectedDragWindow() {
		if (drag1.isSelected())
			return 0;
		if (drag2.isSelected())
			return 1;
		if (drag3.isSelected())
			return 2;
		return 0;
	}

	public void setUndoManager(TextUndoManager um) {
		this.um = um;
	}

	/**
	 * UNDO/REDOメニューの使用可否を変更する.
	 *
	 */
	public void updateEditMenu() {
		undo.setEnabled(um.canUndo());
		undo.setToolTipText(um.getUndoPresentationName());
		redo.setEnabled(um.canRedo());
		redo.setToolTipText(um.getRedoPresentationName());
	}

	/**
	 * UNDO/REDOメニューを実行するリスナー.
	 */
	class UndoHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Object s = e.getSource();
			if (s == undo) {
				um.undo();
			} else if (s == redo) {
				um.redo();
			} else {
				System.out.println(s);
			}
		}
	}
}
