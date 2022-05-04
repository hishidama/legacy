package jp.hishidama.swing;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.swing.*;

import jp.hishidama.swing.drag.window.*;
import jp.hishidama.swing.undo.TextUndoManager;
import jp.hishidama.swing.util.ComponentUtil;

public class SamplePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected Window owner;

	protected SampleMenuBar menuBar;

	public SamplePanel(Window owner, SampleMenuBar menuBar) {
		super(null);// Layout
		this.owner = owner;
		this.menuBar = menuBar;
		init();
	}

	public void init() {
		// �h���b�O�p�̃��x��
		JLabel label = new JLabel("���̃��x�����h���b�O");
		label.setToolTipText("�h���b�O�E�B���h�E�̃T���v���B���j���[�Ŏ�ނ�I�Ԃƃh���b�O���̌`���ς��");
		label.setLocation(32, 32);
		ComponentUtil.resizeComponentFromText(label);
		MouseHandler handler = new MouseHandler();
		label.addMouseListener(handler);
		label.addMouseMotionListener(handler);
		add(label);

		// UNDO�̎g����e�L�X�g�t�B�[���h
		JTextField field = new JTextField();
		field.setToolTipText("������P�ʂł�UNDO/REDO�̃T���v��");
		TextUndoManager um = new TextUndoManager() {
			private static final long serialVersionUID = -1535894974802124514L;

			public void update() {
				menuBar.updateEditMenu();
			}
		};
		menuBar.setUndoManager(um);
		um.install(field);
		field.setBounds(32, 64, 128, 24);
		add(field);
	}

	class MouseHandler extends MouseAdapter implements MouseMotionListener {

		protected DragWindow win;

		protected JComponent drag;

		public void mousePressed(MouseEvent e) {
			drag = (JComponent) e.getSource();
			switch (menuBar.selectedDragWindow()) {
			default:
				win = new DragWindow(owner, e.getPoint(), drag
						.getLocationOnScreen(), drag.getSize());
				break;
			case 1:
				win = new DragBoxWindow(owner, e.getPoint(), drag
						.getLocationOnScreen(), drag.getSize());
				break;
			case 2:
				BufferedImage i = new BufferedImage(drag.getWidth(), drag
						.getHeight(), BufferedImage.TYPE_INT_ARGB);
				Graphics g = i.createGraphics();
				g.setColor(Color.BLUE);
				g.fillRect(0, 0, drag.getWidth(), drag.getHeight());
				g.setColor(Color.BLACK);
				g.drawString("image", 0, 8);
				g.dispose();
				changeTransparent(i, Color.BLACK);
				// ������image�ɓ����F���w�肵�Ă��A�`����Graphics���̂̔w�i�������ł͂Ȃ��̂�
				// �E�B���h�E�����߂���킯�ł͂Ȃ�
				win = new DragImageWindow(owner, e.getPoint(), drag
						.getLocationOnScreen(), i);
				break;
			}
			win.setVisible(true);
		}

		public void changeTransparent(BufferedImage img, Color c) {
			int t = c.getRGB();

			// RGB�l��0�ɒu��
			for (int y = 0; y < img.getHeight(); y++) {
				for (int x = 0; x < img.getWidth(); x++) {
					if (img.getRGB(x, y) == t)
						img.setRGB(x, y, 0);
				}
			}
		}

		public void mouseReleased(MouseEvent e) {
			if (win != null) {
				Point pt = win.getLocation();
				SwingUtilities.convertPointFromScreen(pt, drag.getParent());
				drag.setLocation(pt);
				drag = null;

				win.setVisible(false);
				win.dispose();
				win = null;
			}
		}

		public void mouseDragged(MouseEvent e) {
			win.setMouseLocation(e.getPoint());
		}

		public void mouseMoved(MouseEvent e) {
		}
	}
}
