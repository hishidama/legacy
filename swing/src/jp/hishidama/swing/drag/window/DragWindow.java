package jp.hishidama.swing.drag.window;

import java.awt.*;

/**
 * 擬似的に背景を透過する箱をドラッグするウィンドウ.
 * <p>
 * 枠線以外の背景が擬似的に透過する。<br>
 * 最初にデスクトップの全画面を取得するので、マシンパワーがないと重い。
 * </p>
 * <p>→<a target="hishidama"
 * href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/swing/DragWindow.html">使用例</a>
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/swing.html">ひしだま</a>
 * @since 2007.02.11
 * @version 2007.02.22
 */
public class DragWindow extends Window {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2338068862539204147L;

	/** 枠線の色 */
	protected Color borderColor = Color.BLACK;

	/** ウィンドウ作成時の全画面の画像 */
	protected Image image;

	/** マウスとウィンドウの相対座標X */
	protected int nx;

	/** マウスとウィンドウの相対座標Y */
	protected int ny;

	/**
	 * jre1.6で使用可能なコンストラクター
	 * 
	 * @param mouse
	 *            最初のマウスの位置
	 * @param location
	 *            最初のウィンドウの位置
	 * @param size
	 *            ウィンドウのサイズ
	 */
	public DragWindow(Point mouse, Point location, Dimension size) {
		this(null, mouse, location, size);
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
	 * @param size
	 *            ウィンドウのサイズ
	 */
	public DragWindow(Window owner, Point mouse, Point location, Dimension size) {
		super(owner);
		setBounds(location.x, location.y, size.width, size.height);
		nx = location.x - mouse.x;
		ny = location.y - mouse.y;
		initImage();
	}

	/**
	 * 画像の初期化
	 */
	public void initImage() {
		Dimension sz = Toolkit.getDefaultToolkit().getScreenSize();

		Robot robot = null;
		try {
			robot = new Robot();
		} catch (AWTException e) {
			throw new RuntimeException(e);
		}
		Point pt = new Point(0, 0);
		image = robot.createScreenCapture(new Rectangle(pt, sz));
	}

	/**
	 * 枠線の色を設定
	 * 
	 * @param c
	 *            枠線の色
	 */
	public void setBorderColor(Color c) {
		borderColor = c;
	}

	/**
	 * 枠線の色を取得
	 * 
	 * @return 枠線の色
	 */
	public Color getBorderColor() {
		return borderColor;
	}

	/**
	 * マウス位置指定でのウィンドウ移動.
	 * <p>
	 * 当ウィンドウ作成時に保持した最初のマウス位置と比較し、ウィンドウの位置を移動させる。
	 * </p>
	 */
	public void setMouseLocation(Point mouse) {
		int x = mouse.x + nx;
		int y = mouse.y + ny;
		setLocation(x, y);
	}

	public void setBounds(int x, int y, int width, int height) {
		int oldx = getX();
		int oldy = getY();
		super.setBounds(x, y, width, height);
		if (x != oldx || y != oldy) {
			invalidate();
			repaint();
		}
	}

	/**
	 * 描画実行
	 */
	public void paint(Graphics g) {
		int x = getX();
		int y = getY();
		int w = getWidth();
		int h = getHeight();

		g.drawImage(image, 0, 0, w, h, x, y, x + w, y + h, null);

		g.setColor(getBorderColor());
		g.drawRect(0, 0, w - 1, h - 1);
	}

}
