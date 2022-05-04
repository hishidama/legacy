package jp.hishidama.swing.drag.window;

import java.awt.*;

/**
 * 箱をドラッグするウィンドウ.
 * <p>
 * 箱（背景は透過しない）をドラッグする。
 * </p>
 * 
 * <p>→<a target="hishidama"
 * href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/swing/DragWindow.html">使用例</a>
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/swing.html">ひしだま</a>
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
