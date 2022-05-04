package jp.hishidama.swing.drag.window;

import java.awt.*;

/**
 * 画像をドラッグするウィンドウ.
 * 
 * <p>→<a target="hishidama"
 * href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/swing/DragWindow.html">使用例</a>
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/swing.html">ひしだま</a>
 * @since 2007.02.25
 */
public class DragImageWindow extends DragWindow {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2474361760012233166L;

	/**
	 * jre1.6で使用可能なコンストラクター
	 * 
	 * @param mouse
	 *            最初のマウスの位置
	 * @param location
	 *            最初のウィンドウの位置
	 * @param image
	 *            ドラッグ時に表示する画像
	 */
	public DragImageWindow(Point mouse, Point location, Image image) {
		this(null, mouse, location, image);
	}

	/**
	 * コンストラクター
	 * 
	 * @param owner
	 *            親ウィンドウ
	 * @param mouse
	 *            最初のマウスの位置
	 * @param location
	 *            最初のウィンドウの位置
	 * @param image
	 *            ドラッグ時に表示する画像
	 */
	public DragImageWindow(Window owner, Point mouse, Point location,
			Image image) {
		super(owner, mouse, location, new Dimension(image.getWidth(null), image
				.getHeight(null)));
		this.image = image;
	}

	public void initImage() {
	}

	public void paint(Graphics g) {
		// int w = getWidth();
		// int h = getHeight();

		g.drawImage(image, 0, 0, this);

		// g.setColor(getBorderColor());
		// g.drawRect(0, 0, w - 1, h - 1);
	}
}
