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

	protected JMenuItem undo = new JMenuItem("���ɖ߂�(U)", 'U');

	protected JMenuItem redo = new JMenuItem("��蒼��(R)", 'R');

	private TextUndoManager um;

	public void init() {
		JMenu drag = new JMenu("�h���b�O(D)");
		drag.setMnemonic('D');
		drag.setToolTipText("�h���b�O���Ɏg�p����E�B���h�E�̎�ނ�I������");
		drag.add(drag1);
		drag.add(drag2);
		drag.add(drag3);
		drag1.setToolTipText("�[���I�ɔw�i�����߂��邪�A�}�V���p���[�ɂ���Ă͔��ɏd��");
		drag2.setToolTipText("�w�i�����߂��Ȃ�BOX");
		drag3.setToolTipText("Image���h���b�O����");
		ButtonGroup group = new ButtonGroup();
		group.add(drag1);
		group.add(drag2);
		group.add(drag3);
		drag1.setSelected(true);

		JMenu edit = new JMenu("�ҏW(E)");
		edit.setMnemonic('E');
		edit.setToolTipText("�e�L�X�g�t�B�[���h��UNDO/REDO�̃e�X�g");
		edit.add(undo);
		edit.add(redo);
		UndoHandler uh = new UndoHandler();
		undo.addActionListener(uh);
		redo.addActionListener(uh);

		this.add(drag);
		this.add(edit);
	}

	/**
	 * �I������Ă���h���b�O�p�E�B���h�E�̎�ނ�Ԃ�.
	 *
	 * @return �ԍ�
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
	 * UNDO/REDO���j���[�̎g�p�ۂ�ύX����.
	 *
	 */
	public void updateEditMenu() {
		undo.setEnabled(um.canUndo());
		undo.setToolTipText(um.getUndoPresentationName());
		redo.setEnabled(um.canRedo());
		redo.setToolTipText(um.getRedoPresentationName());
	}

	/**
	 * UNDO/REDO���j���[�����s���郊�X�i�[.
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
