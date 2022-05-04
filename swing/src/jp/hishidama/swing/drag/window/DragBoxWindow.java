package jp.hishidama.swing.drag.window;

import java.awt.*;

/**
 * �����h���b�O����E�B���h�E.
 * <p>
 * ���i�w�i�͓��߂��Ȃ��j���h���b�O����B
 * </p>
 * 
 * <p>��<a target="hishidama"
 * href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/swing/DragWindow.html">�g�p��</a>
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/swing.html">�Ђ�����</a>
 * @since 2007.02.25
 */
public class DragBoxWindow extends DragWindow {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2477635780208877639L;

	public DragBoxWindow(Point mouse, Point location, Dimension size) {
		super(mouse, location, size);
	}

	public DragBoxWindow(Window owner, Point mouse, Point location,
			Dimension size) {
		super(owner, mouse, location, size);
	}

	public void initImage() {
	}

	public void paint(Graphics g) {
		int w = getWidth();
		int h = getHeight();

		g.setColor(getBorderColor());
		g.drawRect(0, 0, w - 1, h - 1);
	}
}
