package jp.hishidama.swing;

import java.awt.*;
import javax.swing.*;

public class SwingSample extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		SwingSample me = new SwingSample();
		me.init();
		me.setVisible(true);
	}

	public SwingSample() {
		super("ひしだま Swing部品 サンプル");
	}

	public void init() {

		// ウィンドウの位置の初期化
		initBounds();

		// ウィンドウの外観の初期化
		initLookFeel();

		// 終了方法の初期化
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// メニューバーの初期化
		initMenuBar();

		getContentPane().add(new SamplePanel(this, menuBar));
	}

	/**
	 * 画面の中央に表示
	 */
	private void initBounds() {
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		int nx = d.width / 2;
		int ny = d.height / 2;
		int x = (d.width - nx) / 2;
		int y = (d.height - ny) / 2;

		setLocation(x, y);
		setSize(nx, ny);
		// 同じ→setBounds(x, y, nx, ny);
	}

	/**
	 * 外観をOSのものに似せる
	 * <p>
	 * （WindowsならWindowsの外観に似る）
	 * </p>
	 */
	private void initLookFeel() {
		try {
			String look =
			// "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
			UIManager.getSystemLookAndFeelClassName();
			UIManager.setLookAndFeel(look);
		} catch (Exception e) {
			// 駄目なときは諦める
			e.printStackTrace();
		}
	}

	SampleMenuBar menuBar;

	/**
	 * メニューバーの初期化
	 */
	private void initMenuBar() {
		menuBar = new SampleMenuBar();
		setJMenuBar(menuBar);
	}
}
