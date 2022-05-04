package jp.hishidama.win32.api;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;

import jp.hishidama.win32.JWnd;

/**
 * winuser.hの関数.
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/hmwin32.html">ひしだま</a>
 * @see JWnd
 * @since 2007.10.01
 * @version 2007.11.01
 */
public final class WinUser {

	static {
		System.loadLibrary("hmwin32");
	}

	private WinUser() {
	}

	/**
	 * メッセージ送信.
	 * 
	 * @param hwnd
	 *            HWND
	 * @param msg
	 *            メッセージ（WM_*）
	 * @param wparam
	 *            WPARAM
	 * @param lparam
	 *            LPARAM
	 * @return メッセージにより異なる
	 */
	public static native int SendMessage(long hwnd, int msg, int wparam,
			int lparam);

	/**
	 * メッセージ送信.
	 * 
	 * @param hwnd
	 *            HWND
	 * @param msg
	 *            メッセージ（WM_*）
	 * @param wparam
	 *            WPARAM
	 * @param lparam
	 *            LPARAM（文字列）
	 * @return メッセージにより異なる
	 */
	public static native int SendMessage(long hwnd, int msg, int wparam,
			String lparam);

	/**
	 * メッセージ送信.
	 * 
	 * @param hwnd
	 *            HWND
	 * @param msg
	 *            メッセージ（WM_*）
	 * @param wparam
	 *            WPARAM
	 * @param lparam
	 *            LPARAM（バイト配列）
	 * @return メッセージにより異なる
	 */
	public static native int SendMessage(long hwnd, int msg, int wparam,
			byte[] lparam);

	/**
	 * メッセージキューへの送信.
	 * 
	 * @param hwnd
	 *            HWND
	 * @param msg
	 *            メッセージ（WM_*）
	 * @param wparam
	 *            WPARAM
	 * @param lparam
	 *            LPARAM
	 * @return メッセージにより異なる
	 */
	public static native boolean PostMessage(long hwnd, int msg, int wparam,
			int lparam);

	/**
	 * ウィンドウ存在判断.
	 * 
	 * @param hwnd
	 *            HWND
	 * @return true：ウィンドウが存在しているとき
	 */
	public static native boolean IsWindow(long hwnd);

	/**
	 * 子ウィンドウ判断.
	 * 
	 * @param hwndParent
	 *            親ウィンドウのHWND
	 * @param hwnd
	 *            調査するHWND
	 * @return true：子ウィンドウあるいは子孫ウィンドウのとき
	 */
	public static native boolean IsChild(long hwndParent, long hwnd);

	/**
	 * ウィンドウ表示状態設定.
	 * 
	 * @param wnd
	 *            HWND
	 * @param cmdShow
	 *            表示方法（{@link WinUserConst#SW_HIDE}等）
	 * @return true：ウィンドウが以前から表示されていたとき<br>
	 *         false：ウィンドウが以前は非表示だったとき
	 */
	public static native boolean ShowWindow(long wnd, int cmdShow);

	/**
	 * ウィンドウ移動.
	 * <p>
	 * ウィンドウの位置とサイズを変更する。<br>
	 * トップレベルウィンドウはスクリーン座標、親ウィンドウを持つウィンドウはクライアント座標で指定する。
	 * </p>
	 * 
	 * @param hwnd
	 *            HWND
	 * @param x
	 *            X
	 * @param y
	 *            Y
	 * @param width
	 *            幅
	 * @param height
	 *            高さ
	 * @param repaint
	 *            再描画オプション（true：ウィンドウにWM_PAINTが送られる）
	 * @return true：成功
	 * @throws Win32Exception
	 *             失敗時
	 */
	public static native boolean MoveWindow(long hwnd, int x, int y, int width,
			int height, boolean repaint) throws Win32Exception;

	/**
	 * ウィンドウ位置設定.
	 * <p>
	 * ウィンドウの位置・サイズ・Zオーダーを変更する。
	 * </p>
	 * 
	 * @param hwnd
	 *            HWND
	 * @param hWndInsertAfter
	 *            配置順序のHWND
	 * @param x
	 *            X
	 * @param y
	 *            Y
	 * @param cx
	 *            幅
	 * @param cy
	 *            高さ
	 * @param flags
	 *            ウィンドウ位置オプション（SWP_*）
	 * @return true：成功
	 * @throws Win32Exception
	 *             失敗時
	 */
	public static native boolean SetWindowPos(long hwnd, long hWndInsertAfter,
			int x, int y, int cx, int cy, int flags) throws Win32Exception;

	/**
	 * 表示状態判断.
	 * 
	 * @param wnd
	 *            HWND
	 * @return true：表示されている（WS_VISIBLEスタイルを持っている）
	 */
	public static native boolean IsWindowVisible(long wnd);

	/**
	 * アイコン状態判断.
	 * 
	 * @param wnd
	 *            HWND
	 * @return true：最小化（アイコン化）されている
	 */
	public static native boolean IsIconic(long wnd);

	/**
	 * 最大化状態判断.
	 * 
	 * @param wnd
	 *            HWND
	 * @return true：最大化されている
	 */
	public static native boolean IsZoomed(long wnd);

	/**
	 * ダイアログボックス：コントロール取得.
	 * 
	 * @param hdlg
	 *            HWND
	 * @param id
	 *            コントロールの識別子
	 * @return コントロールのHWND
	 * @throws Win32Exception
	 *             失敗時
	 */
	public static native long GetDlgItem(long hdlg, int id)
			throws Win32Exception;

	/**
	 * ダイアログボックス：コントロール数値設定.
	 * 
	 * @param hdlg
	 *            HWND
	 * @param id
	 *            コントロールの識別子
	 * @param value
	 *            値
	 * @param signed
	 *            符号を付けるかどうか
	 * @return 成功した場合、true
	 * @throws Win32Exception
	 *             失敗時
	 */
	public static native boolean SetDlgItemInt(long hdlg, int id, int value,
			boolean signed) throws Win32Exception;

	/**
	 * ダイアログボックス：コントロール数値取得.
	 * 
	 * @param hdlg
	 *            HWND
	 * @param id
	 *            コントロールの識別子
	 * @param translated
	 *            0番に、成功したかどうかの状態を返す（nullの場合は何も返さない）
	 * @param signed
	 *            true：符号付きとして扱う
	 * @return 値
	 * @throws Win32Exception
	 *             失敗時
	 */
	public static native int GetDlgItemInt(long hdlg, int id,
			boolean[] translated, boolean signed) throws Win32Exception;

	/**
	 * ダイアログボックス：コントロールテキスト設定.
	 * 
	 * @param hdlg
	 *            HWND
	 * @param id
	 *            コントロールの識別子
	 * @param text
	 *            文字列
	 * @return 成功した場合、true
	 * @throws Win32Exception
	 *             失敗時
	 */
	public static native boolean SetDlgItemText(long hdlg, int id, String text)
			throws Win32Exception;

	/**
	 * ダイアログボックス：コントロールテキスト取得.
	 * 
	 * @param hdlg
	 *            HWND
	 * @param id
	 *            コントロールの識別子
	 * @param len
	 *            文字列の最大サイズ
	 * @return 文字列
	 * @throws Win32Exception
	 *             失敗時
	 */
	public static native String GetDlgItemText(long hdlg, int id, int len)
			throws Win32Exception;

	/**
	 * ダイアログボックス：チェックボタン設定.
	 * 
	 * @param hdlg
	 *            HWND
	 * @param nIDButton
	 *            コントロールの識別子
	 * @param uCheck
	 *            値（BST_*）
	 * @return 成功した場合、true
	 * @throws Win32Exception
	 *             失敗時
	 */
	public static native boolean CheckDlgButton(long hdlg, int nIDButton,
			int uCheck) throws Win32Exception;

	/**
	 * ダイアログボックス：ラジオボタン設定.
	 * 
	 * @param hdlg
	 *            HWND
	 * @param nIDFirstButton
	 *            グループ内の最初のボタンの識別子
	 * @param nIDLastButton
	 *            グループ内の最後のボタンの識別子
	 * @param nIDCheckButton
	 *            選択したいボタンの識別子
	 * @return 成功した場合、true
	 * @throws Win32Exception
	 *             失敗時
	 */
	public static native boolean CheckRadioButton(long hdlg,
			int nIDFirstButton, int nIDLastButton, int nIDCheckButton)
			throws Win32Exception;

	/**
	 * ダイアログボックス：チェックボタン状態取得.
	 * 
	 * @param hdlg
	 *            HWND
	 * @param nIDButton
	 *            ボタンの識別子
	 * @return {@link WinUserConst#BST_CHECKED}等
	 */
	public static native int IsDlgButtonChecked(long hdlg, int nIDButton);

	/**
	 * ダイアログボックス：グループ内の次コントロール取得.
	 * 
	 * @param hdlg
	 *            HWND
	 * @param hctl
	 *            コントロールのHWND
	 * @param previous
	 *            方向フラグ（true：前のコントロール、false：次のコントロール）
	 * @return コントロールのHWND
	 * @throws Win32Exception
	 *             失敗時
	 */
	public static native long GetNextDlgGroupItem(long hdlg, long hctl,
			boolean previous) throws Win32Exception;

	/**
	 * ダイアログボックス：タブ移動可能な次コントロール取得.
	 * 
	 * @param hdlg
	 *            HWND
	 * @param hctl
	 *            コントロールのHWND
	 * @param previous
	 *            方向フラグ（true：前のコントロール、false：次のコントロール）
	 * @return コントロールのHWND
	 * @throws Win32Exception
	 *             失敗時
	 */
	public static native long GetNextDlgTabItem(long hdlg, long hctl,
			boolean previous) throws Win32Exception;

	/**
	 * ダイアログボックス：コントロール識別子取得.
	 * 
	 * @param hctl
	 *            コントロールのHWND
	 * @return コントロールの識別子
	 * @throws Win32Exception
	 *             失敗時
	 */
	public static native int GetDlgCtrlID(long hctl) throws Win32Exception;

	/**
	 * キーボードフォーカス設定.
	 * 
	 * @param wnd
	 *            HWND
	 * @return 以前にキーボードフォーカスを持っていたウィンドウのHWND
	 * @throws Win32Exception
	 *             失敗時
	 */
	public static native long SetFocus(long wnd) throws Win32Exception;

	/**
	 * キーボードフォーカスウィンドウ取得.
	 * 
	 * @return HWND キーボードフォーカスを持つウィンドウのHWND（該当ウィンドウが無い場合、0）
	 * @see #GetForegroundWindow()
	 */
	public static native long GetFocus();

	/**
	 * アクティブウィンドウ設定.
	 * <p>
	 * ウィンドウをアクティブにする。
	 * </p>
	 * 
	 * @param wnd
	 *            HWND
	 * @return 以前アクティブだったウィンドウのHWND
	 * @throws Win32Exception
	 *             失敗時
	 */
	public static native long SetActiveWindow(long wnd) throws Win32Exception;

	/**
	 * アクティブウィンドウ取得.
	 * 
	 * @return HWND アクティブなウィンドウのHWND（該当ウィンドウがない場合、null）
	 * @see #GetForegroundWindow()
	 */
	public static native long GetActiveWindow();

	/**
	 * 入力可否設定.
	 * <p>
	 * 無効にするとマウス入力やキーボード入力を受け付けない。グレーアウト状態？
	 * </p>
	 * 
	 * @param wnd
	 *            HWND
	 * @param enable
	 *            true：有効、false：無効
	 * @return true：既に無効になっていたとき
	 * @throws Win32Exception
	 *             失敗時
	 */
	public static native boolean EnableWindow(long wnd, boolean enable)
			throws Win32Exception;

	/**
	 * 入力可否判断.
	 * <p>
	 * マウス入力やキーボード入力を受け付ける状態（グレーアウト？）かどうかを返す。
	 * </p>
	 * 
	 * @param wnd
	 *            HWND
	 * @return true：有効、false：無効
	 */
	public static native boolean IsWindowEnabled(long wnd);

	/**
	 * フォアグラウンドウィンドウ設定.
	 * <p>
	 * ウィンドウをフォアグラウンド（かつアクティブ）にする。
	 * </p>
	 * 
	 * @param wnd
	 *            HWND
	 * @return true：フォアグラウンドになった
	 */
	public static native boolean SetForegroundWindow(long wnd);

	/**
	 * フォアグラウンドウィンドウ取得.
	 * 
	 * @return HWND
	 */
	public static native long GetForegroundWindow();

	/**
	 * スクロールバー表示状態設定.
	 * 
	 * @param wnd
	 *            HWND
	 * @param bar
	 *            設定対象（SB_*）
	 * @param show
	 *            true：表示、false：非表示
	 * @return true：成功
	 * @throws Win32Exception
	 *             失敗時
	 */
	public static native boolean ShowScrollBar(long wnd, int bar, boolean show)
			throws Win32Exception;

	/**
	 * スクロールバー可否設定.
	 * 
	 * @param wnd
	 *            HWND
	 * @param bar
	 *            設定対象（SB_*）
	 * @param arrows
	 *            矢印の有効無効設定（ESB_*）
	 * @return true：成功<br>
	 *         false：既に設定されていた
	 * @throws Win32Exception
	 *             失敗時
	 */
	public static native boolean EnableScrollBar(long wnd, int bar, int arrows)
			throws Win32Exception;

	/**
	 * ウィンドウテキスト設定.
	 * <p>
	 * ウィンドウのタイトルやコントロールのテキストを変更する。<br>
	 * 他アプリケーションのテキストは変更できない。
	 * </p>
	 * 
	 * @param wnd
	 *            HWND
	 * @param string
	 *            テキスト
	 * @return true：成功
	 * @throws Win32Exception
	 *             失敗時
	 */
	public static native boolean SetWindowText(long wnd, String string)
			throws Win32Exception;

	/**
	 * ウィンドウテキスト取得.
	 * <p>
	 * ウィンドウのタイトルやコントロールのテキストを取得する。
	 * </p>
	 * 
	 * @param wnd
	 *            HWND
	 * @return テキスト
	 * @throws Win32Exception
	 *             失敗時
	 */
	public static native String getWindowText(long wnd) throws Win32Exception;

	/**
	 * ウィンドウテキスト長取得.
	 * 
	 * @param wnd
	 *            HWND
	 * @return ウィンドウテキストの文字数
	 * @throws Win32Exception
	 *             失敗時
	 */
	public static native int GetWindowTextLength(long wnd)
			throws Win32Exception;

	/**
	 * クライアント領域取得.
	 * <p>
	 * クライアント領域をクライアント座標で返すので、左上は常に(0,0)となる。
	 * </p>
	 * 
	 * @param wnd
	 *            HWND
	 * @param r
	 *            座標値（クライアント座標系）
	 * @return true：成功
	 * @throws Win32Exception
	 *             失敗時
	 */
	public static native boolean GetClientRect(long wnd, Rectangle r)
			throws Win32Exception;

	/**
	 * ウィンドウ領域取得.
	 * 
	 * @param wnd
	 *            HWND
	 * @param r
	 *            座標値（スクリーン座標系）
	 * @return true：成功
	 * @throws Win32Exception
	 *             失敗時
	 */
	public static native boolean GetWindowRect(long wnd, Rectangle r)
			throws Win32Exception;

	/**
	 * メッセージボックス.
	 * 
	 * @param wnd
	 *            HWND
	 * @param text
	 *            テキスト
	 * @param caption
	 *            タイトル
	 * @param type
	 *            スタイル（{@link WinUserConst#MB_OK}等）
	 * @return 戻り値（{@link WinUserConst#IDOK}等）
	 * @throws Win32Exception
	 *             失敗時
	 */
	public static native int MessageBox(long wnd, String text, String caption,
			int type) throws Win32Exception;

	/**
	 * スクリーン座標変換.
	 * 
	 * @param wnd
	 *            HWND
	 * @param point
	 *            クライアント座標（スクリーン座標に変換される）
	 * @return true：成功
	 * @throws Win32Exception
	 *             失敗時
	 */
	public static native boolean ClientToScreen(long wnd, Point point)
			throws Win32Exception;

	/**
	 * クライアント座標変換.
	 * 
	 * @param wnd
	 *            HWND
	 * @param point
	 *            スクリーン座標（クライアント座標に変換される）
	 * @return true：成功
	 * @throws Win32Exception
	 *             失敗時
	 */
	public static native boolean ScreenToClient(long wnd, Point point)
			throws Win32Exception;

	/**
	 * 指定位置ウィンドウ取得.
	 * 
	 * @param x
	 *            X
	 * @param y
	 *            Y
	 * @return HWND（ウィンドウが無い場合、null）
	 * @see #ChildWindowFromPoint(long, int, int)
	 */
	public static native long WindowFromPoint(int x, int y);

	/**
	 * 指定位置子ウィンドウ取得.
	 * 
	 * @param x
	 *            X（クライアント座標系）
	 * @param y
	 *            Y（クライアント座標系）
	 * @return HWND（座標が親ウィンドウの外にある場合、null）
	 * @see #ChildWindowFromPointEx(long, int, int, int)
	 */
	public static native long ChildWindowFromPoint(long hwnd, int x, int y);

	/**
	 * 指定位置子ウィンドウ取得.
	 * 
	 * @param x
	 *            X（クライアント座標系）
	 * @param y
	 *            Y（クライアント座標系）
	 * @param flags
	 *            無視する対象を示すオプション（CWP_*）
	 * @return HWND（座標が親ウィンドウの外にある場合や失敗した場合、null）
	 */
	public static native long ChildWindowFromPointEx(long hwnd, int x, int y,
			int flags);

	/**
	 * ウィンドウ情報取得.
	 * 
	 * @param hwnd
	 *            HWND
	 * @param index
	 *            情報のオフセット
	 * @return 情報（32bit）
	 * @throws Win32Exception
	 *             失敗時
	 * @see #SetParent(long, long)
	 */
	public static native int GetWindowLong(long hwnd, int index)
			throws Win32Exception;

	/**
	 * ウィンドウ情報設定.
	 * 
	 * @param hwnd
	 *            HWND
	 * @param index
	 *            情報のオフセット
	 * @param value
	 *            新しい値
	 * @return 変更前の値
	 * @throws Win32Exception
	 *             失敗時
	 * @see #SetParent(long, long)
	 */
	public static native int SetWindowLong(long hwnd, int index, int value)
			throws Win32Exception;

	/**
	 * デスクトップ取得.
	 * 
	 * @return HWND
	 * @see #GetWindow(long, int)
	 */
	public static native long GetDesktopWindow();

	/**
	 * 親ウィンドウ取得.
	 * 
	 * @param wnd
	 *            HWND
	 * @return 親ウィンドウ（またはオーナーウィンドウ）のHWND<br>
	 *         トップレベルウィンドウの場合、0
	 * @throws Win32Exception
	 *             失敗時
	 * @see #GetAncestor(long, int)
	 */
	public static native long GetParent(long wnd) throws Win32Exception;

	/**
	 * 親ウィンドウ設定.
	 * 
	 * @param wnd
	 *            HWND
	 * @param wndParent
	 *            親ウィンドウ（nullの場合、デスクトップ）
	 * @return 直前の親ウィンドウのHWND
	 * @throws Win32Exception
	 *             失敗時
	 */
	public static native long SetParent(long wnd, long wndParent)
			throws Win32Exception;

	/**
	 * 子ウィンドウ列挙.
	 * 
	 * @param hwnd
	 *            HWND
	 * @param list
	 *            子ウィンドウ（{@link jp.hishidama.win32.JWnd}）が入れられる
	 * @return true：成功
	 * @throws Win32Exception
	 *             失敗時
	 * @see #GetWindow(long, int)
	 */
	public static native boolean EnumChildWindows(long hwnd, List list)
			throws Win32Exception;

	/**
	 * トップレベルウィンドウ列挙.
	 * 
	 * @param list
	 *            ウィンドウ（{@link jp.hishidama.win32.JWnd}）が入れられる
	 * @return true：成功
	 * @throws Win32Exception
	 *             失敗時
	 * @see #GetWindow(long, int)
	 */
	public static native boolean EnumWindows(List list) throws Win32Exception;

	/**
	 * トップレベルウィンドウ検索.
	 * 
	 * @param className
	 *            クラス名
	 * @param windowName
	 *            ウィンドウ名
	 * @return HWND
	 * @throws Win32Exception
	 *             失敗時
	 * @see #getClassName(long)
	 * @see #getWindowText(long)
	 */
	public static native long FindWindow(String className, String windowName)
			throws Win32Exception;

	/**
	 * ウィンドウクラス名取得.
	 * 
	 * @param hwnd
	 *            HWND
	 * @return クラス名
	 * @throws Win32Exception
	 *             失敗時
	 */
	public static native String getClassName(long hwnd) throws Win32Exception;

	/**
	 * 一番上の子ウィンドウ取得.
	 * 
	 * @param hwnd
	 *            HWND
	 * @return HWND（子ウィンドウを持たない場合、0）
	 * @throws Win32Exception
	 *             失敗時
	 */
	public static native long GetTopWindow(long hwnd) throws Win32Exception;

	/**
	 * 次ウィンドウ取得.
	 * 
	 * @param hwnd
	 *            HWND
	 * @param cmd
	 *            方向（GW_HWNDNEXT または GW_HWNDPREV）
	 * @return HWND
	 * @throws Win32Exception
	 *             失敗時
	 */
	public static long GetNextWindow(long hwnd, int cmd) throws Win32Exception {
		return GetWindow(hwnd, cmd);
	}

	/**
	 * ウィンドウ取得.
	 * 
	 * @param wnd
	 *            HWND
	 * @param cmd
	 *            関係（GW_*）
	 * @return HWND
	 * @throws Win32Exception
	 *             失敗時
	 */
	public static native long GetWindow(long wnd, int cmd)
			throws Win32Exception;

	/**
	 * 祖先ウィンドウ取得.
	 * 
	 * @param wnd
	 *            HWND
	 * @param flags
	 *            パラメータ（GA_*）
	 * @return HWND
	 * @see #GetParent(long)
	 */
	public static native long GetAncestor(long wnd, int flags);
}
