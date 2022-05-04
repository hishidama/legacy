package jp.hishidama.robot;

import java.awt.AWTException;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.List;

import jp.hishidama.win32.JWnd;
import jp.hishidama.win32.api.Win32Exception;

public class WinRobotGoogleTest {

	protected static RobotUtil robot;
	static {
		try {
			robot = new RobotUtil();
		} catch (AWTException e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {
		JWnd.setThrowLastError(true);
		try {
			execute();
		} catch (Win32Exception e) {
			int error = e.getLastError();
			String msg = e.getMessage();
			System.out.println("Win32 LastError[" + error + "]：" + msg);
			throw e;
		}
	}

	public static void execute() {
		// IERobotTodo ie = new IERobotTodo();
		WindowsRobot ie = new WindowsRobot();

		// Googleのトップページを表示しているIEを探す
		String title = "Google - Microsoft Internet Explorer";
		JWnd wnd = ie.findWindow(title, 200, 2);
		if (wnd == null) {
			System.out.println("探索失敗：\"" + title + "\"");
			return;
		}

		// 操作の為の準備
		ie.setJWnd(wnd);
		ie.setAutoDelay(200);
		System.out.println("探索成功：\"" + ie.getTitle() + "\"");

		// IEを最前面に表示する
		ie.setForegroundWindow();

		// サイズを変更する
		// ie.setLocation(32, 16);
		ie.setSize(768, 640);
		// ie.setBounds(16, 32, 1024, 768);

		// ウィンドウの中央にある領域を取得
		Rectangle ieRect = ie.getBounds();
		Point pt = ieRect.getLocation();
		pt.x += ieRect.width / 2;
		pt.y += ieRect.height / 2;
		wnd.ScreenToClient(pt);
		JWnd body = wnd.ChildWindowFromPoint(pt);

		Rectangle rect = body.getWindowRect();
		System.out.println("主要領域：" + rect);

		// ここからはjava.awt.Robotの拡張クラスを使用

		// 検索文字列を入れるボックスをクリック
		// Googleのデザイン変更によって位置は変わるかも…
		robot.clickL(rect.x + rect.width / 2, rect.y + 188);

		// 既に入っている文字を全選択
		robot.sendCtrlString("A"); // Ctrl+A

		// 選択した文字を消去
		robot.sendKeyCode(KeyEvent.VK_DELETE);

		// コピー&ペーストを使って、検索文字列を入力
		robot.pasteString("java Win32API");

		// Enterキーを押下することによってsubmit、つまり検索実行
		robot.sendChar('\n'); // ENTERキー

		// 画面遷移を待つ
		title = "java Win32API - Google 検索 - Microsoft Internet Explorer";
		if (!ie.waitForTitle(title, 200, 10)) {
			System.out.println("遷移失敗：\"" + title + "\"");
			return;
		}
		System.out.println("遷移成功：\"" + ie.getTitle() + "\"");
	}

	public static void rect(JWnd wnd) {
		Rectangle rw = wnd.getWindowRect();
		System.out.println("win:" + rw);
		Rectangle rc = wnd.getClientRect();
		System.out.println("cli:" + rc);
		wnd.ClientToScreen(rc);
		System.out.println("c s:" + rc);
	}

	public static void ebumChildWindows(JWnd wnd, Rectangle rw) {
		List list = wnd.enumChildWindows();
		for (int i = 0; i < list.size(); i++) {
			JWnd w = (JWnd) list.get(i);
			Rectangle rect = w.getWindowRect();
			rect.x -= rw.x;
			rect.y -= rw.y;
			System.out.println(i + ":" + rect + w.GetWindowText());
		}
	}
}
