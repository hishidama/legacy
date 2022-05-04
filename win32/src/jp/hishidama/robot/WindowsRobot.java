package jp.hishidama.robot;

import java.awt.Rectangle;
import java.util.List;

import jp.hishidama.win32.JWnd;
import jp.hishidama.win32.api.WinUserConst;

/**
 * MS-Windowsウィンドウ自動実行クラス.
 * <p>
 * MS-Windowsのウィンドウを操作するユーティリティークラス。<br> →<a target="hishidama"
 * href="http://www.ne.jp/asahi/hishidama/home/soft/java/hmwin32.html">使用例</a>
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/hmwin32.html">ひしだま</a>
 */
public class WindowsRobot implements WinUserConst {

	/**
	 * 指定時間待機.
	 * 
	 * @param time
	 *            ウェイト[ミリ秒]
	 */
	public void delay(int time) {
		if (time <= 0)
			return;
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
		}
	}

	/**
	 * 操作対象ウィンドウ
	 */
	protected JWnd wnd;

	/**
	 * 処理実行後のウェイト[ミリ秒]
	 */
	protected int wait;

	/**
	 * ウィンドウ探索.
	 * <p>
	 * タイトルの一部が一致するウィンドウを探す。<br>
	 * 見つからない場合、指定された時間だけスリープし、再度探索する。<br>
	 * 指定された回数だけリトライする。
	 * </p>
	 * 
	 * @param title
	 *            タイトル
	 * @param time
	 *            スリープ時間[ミリ秒]
	 * @param count
	 *            リトライ回数
	 * @return ウィンドウ（見つからない場合、null）
	 */
	public JWnd findWindow(String title, int time, int count) {
		for (; count >= 0; count--) {
			List list = JWnd.enumWindows();
			for (int i = 0; i < list.size(); i++) {
				JWnd wnd = (JWnd) list.get(i);
				if (isTarget(wnd, title)) {
					return wnd;
				}
			}
			if (count > 0) {
				delay(time);
			}
		}
		return null;
	}

	/**
	 * 操作対象ウィンドウ設定.
	 * 
	 * @param wnd
	 *            JWnd
	 */
	public void setJWnd(JWnd wnd) {
		this.wnd = wnd;
	}

	/**
	 * 操作対象ウィンドウ取得.
	 * 
	 * @return JWnd
	 */
	public JWnd getJWnd() {
		return wnd;
	}

	/**
	 * 待機時間設定.
	 * <p>
	 * 時間のかかるウィンドウ操作を呼び出す各メソッドでは、処理完了時にウェイトする。<br>
	 * その時間を当メソッドで設定する。
	 * </p>
	 * 
	 * @param ms
	 *            ウェイト[ミリ秒]
	 */
	public void setAutoDelay(int ms) {
		this.wait = ms;
	}

	/**
	 * 待機時間取得.
	 * 
	 * @return ウェイト[ミリ秒]
	 */
	public int getAutoDelay() {
		return wait;
	}

	/**
	 * タイトル変更待ち.
	 * <p>
	 * ウィンドウのタイトルの一部が一致するまで待つ。<br>
	 * IEやテキストエディタ等の、ウィンドウのタイトルが変わる場合に それを待つ際に使うことを想定している。<br>
	 * 指定された時間だけスリープしつつ、指定された回数だけリトライする。
	 * </p>
	 * 
	 * @param title
	 *            タイトル
	 * @param time
	 *            スリープ時間[ミリ秒]
	 * @param count
	 *            リトライ回数
	 * @return 見つかった場合、true
	 */
	public boolean waitForTitle(String title, int time, int count) {
		for (; count > 0; count--) {
			if (isTarget(wnd, title)) {
				return true;
			}
			if (count >= 0) {
				delay(time);
			}
		}
		return false;
	}

	/**
	 * タイトル確認.
	 * <p>
	 * ウィンドウのタイトルに 指定された文字列が含まれていればtrueを返す。
	 * </p>
	 * 
	 * @param wnd
	 *            ウィンドウ
	 * @param title
	 *            文字列
	 * @return 部分一致した場合、true
	 */
	public boolean isTarget(JWnd wnd, String title) {
		String text = wnd.GetWindowText();
		if (text != null && text.indexOf(title) >= 0) {
			return true;
		}
		return false;
	}

	/**
	 * ウィンドウタイトル取得.
	 * 
	 * @return タイトル（取得失敗時はnull）
	 */
	public String getTitle() {
		return wnd.GetWindowText();
	}

	/**
	 * 最前面移動.
	 * <p>
	 * ウィンドウを最前面に移動させる。<br>
	 * ウィンドウがアイコン化（最小化）していた場合は、以前の大きさに戻す。
	 * </p>
	 * 
	 * @return true：成功
	 * @see #setAutoDelay(int)
	 */
	public boolean setForegroundWindow() {
		if (wnd.IsIconic()) {
			wnd.ShowWindow(SW_RESTORE);
		}
		boolean ret = wnd.SetForegroundWindow();
		if (ret) {
			delay(wait);
		}
		return ret;
	}

	/**
	 * 位置設定.
	 * 
	 * @param x
	 * @param y
	 * @return true：成功
	 * @see #setBounds(int, int, int, int)
	 * @see #setAutoDelay(int)
	 */
	public boolean setLocation(int x, int y) {
		int flags = SWP_NOSIZE | SWP_NOZORDER | SWP_NOACTIVATE;
		boolean ret = wnd.SetWindowPos(null, x, y, 0, 0, flags);
		if (ret) {
			delay(wait);
		}
		return ret;
	}

	/**
	 * サイズ設定.
	 * 
	 * @param cx
	 * @param cy
	 * @return true：成功
	 * @see #setBounds(int, int, int, int)
	 * @see #setAutoDelay(int)
	 */
	public boolean setSize(int cx, int cy) {
		int flags = SWP_NOMOVE | SWP_NOZORDER | SWP_NOACTIVATE;
		boolean ret = wnd.SetWindowPos(null, 0, 0, cx, cy, flags);
		if (ret) {
			delay(wait);
		}
		return ret;
	}

	/**
	 * 範囲設定.
	 * 
	 * @param x
	 * @param y
	 * @param cx
	 * @param cy
	 * @return true：成功
	 * @see #setAutoDelay(int)
	 */
	public boolean setBounds(int x, int y, int cx, int cy) {
		int flags = SWP_NOZORDER | SWP_NOACTIVATE;
		boolean ret = wnd.SetWindowPos(null, x, y, cx, cy, flags);
		if (ret) {
			delay(wait);
		}
		return ret;
	}

	/**
	 * ウィンドウ範囲取得.
	 * 
	 * @return 範囲（スクリーン座標系）
	 */
	public Rectangle getBounds() {
		return wnd.getWindowRect();
	}

}
