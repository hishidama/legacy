package jp.hishidama.win32;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.UnsupportedEncodingException;
import java.util.*;

import jp.hishidama.win32.api.Win32Exception;
import jp.hishidama.win32.api.WinUser;
import jp.hishidama.win32.api.WinUserConst;

/**
 * Windowsウィンドウクラス.
 * <p>
 * VisualC++MFCのCWndを模したクラス。<br>
 * 中身はただWin32APIを呼び出しているだけなので、Win32APIの使用上の注意をよく読み用法・用量を守って正しくお使い下さい。
 * </p>
 * <p>
 * 使用するには以下の設定が必要。<br>
 * <ul>
 * <li><a target="hishidama"
 * href="http://www.ne.jp/asahi/hishidama/home/soft/java/hmwin32.html">hmwin32.dll</a>を<a
 * target="hishidama"
 * href="http://www.ne.jp/asahi/hishidama/home/tech/eclipse/java.html#実行時パスを追加する方法">実行時のパスに追加</a>する。</li>
 * </ul>
 * </p>
 * <p>
 * 当クラスでは、Win32API呼び出しでGetLastError()が0以外だった場合、{@link Win32Exception}をcatchして0やnullを返す。<br>
 * {@link #setThrowLastError(boolean)}にtrueをセットすることにより、その例外をそのままスローするようになる。
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/hmwin32.html">ひしだま</a>
 * @version 2007.11.01
 */
public class JWnd implements WinUserConst {

	protected long m_hWnd;

	private JWnd owner;

	protected static boolean throwLastError = false;

	protected Win32Exception lastError;

	protected static Win32Exception s_lastError;

	/**
	 * Win32Exceptionスロー有無設定.
	 * 
	 * @param thr
	 *            true：Win32Exception発生時にそれをスローする<br>
	 *            false：スローしない。{@link #getLastError()}により取得する。
	 */
	public static void setThrowLastError(boolean thr) {
		throwLastError = thr;
	}

	protected void setLastError(Win32Exception e) {
		lastError = e;
		if (throwLastError) {
			throw e;
		}
	}

	/**
	 * Win32Exception取得.
	 * <p>
	 * JWndのインスタンスメソッド呼び出し時に発生したWin32Exceptionを返す。
	 * </p>
	 * 
	 * @return LastError（エラーが発生していない場合、null）
	 */
	public Win32Exception getLastError() {
		return lastError;
	}

	protected static void setLastErrorStatic(Win32Exception e) {
		s_lastError = e;
		if (throwLastError) {
			throw e;
		}
	}

	/**
	 * Win32Exception取得（staticメソッド用）.
	 * <p>
	 * JWndのスタティックメソッド呼び出し時に発生したWin32Exceptionを返す。
	 * </p>
	 * 
	 * @return LastError（エラーが発生していない場合、null）
	 */
	public static Win32Exception getLastErrorStatic() {
		return s_lastError;
	}

	protected static Map handleMap = null;

	// ハンドルをマップに保持するので、破棄されずに溜まっていきそうな気がする。
	// だから、作ってはみたけど とりあえず使わない。
	protected static JWnd FromHandleMap(int h) {
		if (h == 0)
			return null;

		String key = Integer.toString(h);

		synchronized (JWnd.class) {
			if (handleMap == null) {
				handleMap = new HashMap();
			}
			JWnd wnd = (JWnd) handleMap.get(key);
			if (wnd == null) {
				wnd = new JWnd(h);
				handleMap.put(key, wnd);
			}
			return wnd;
		}
	}

	// 毎回インスタンスを作って返してるけど、
	// ownerとかはやばそうな気がする。
	protected static JWnd FromHandle(long h) {
		if (h == 0)
			return null;
		return new JWnd(h);
	}

	//
	// 構築
	//

	/**
	 * コンストラクター
	 * 
	 * @param hWnd
	 *            ウィンドウハンドル（HWND）
	 */
	public JWnd(long hWnd) {
		m_hWnd = hWnd;
	}

	//
	// 初期化
	//

	/**
	 * ウィンドウのスタイル取得.
	 * 
	 * @return スタイル
	 */
	public int GetStyle() {
		lastError = null;
		try {
			return WinUser.GetWindowLong(m_hWnd, GWL_STYLE);
		} catch (Win32Exception e) {
			setLastError(e);
			return 0;
		}
	}

	/**
	 * ウィンドウの拡張スタイル取得.
	 * 
	 * @return 拡張スタイル
	 */
	public int GetExStyle() {
		lastError = null;
		try {
			return WinUser.GetWindowLong(m_hWnd, GWL_EXSTYLE);
		} catch (Win32Exception e) {
			setLastError(e);
			return 0;
		}
	}

	/**
	 * ウィンドウのスタイル変更.
	 * 
	 * @param remove
	 *            除去するスタイル
	 * @param add
	 *            追加するスタイル
	 * @return true：成功
	 */
	public boolean ModifyStyle(int remove, int add) {
		return modifyStyle(m_hWnd, GWL_STYLE, remove, add, 0);
	}

	/**
	 * ウィンドウのスタイル変更.
	 * 
	 * @param remove
	 *            除去するスタイル
	 * @param add
	 *            追加するスタイル
	 * @param flags
	 *            SetWindowPosに渡すフラグ
	 * @return true：成功
	 */
	public boolean ModifyStyle(int remove, int add, int flags) {
		return modifyStyle(m_hWnd, GWL_STYLE, remove, add, flags);
	}

	/**
	 * ウィンドウの拡張スタイル変更.
	 * 
	 * @param remove
	 *            除去する拡張スタイル
	 * @param add
	 *            追加する拡張スタイル
	 * @return true：成功
	 */
	public boolean ModifyStyleEx(int remove, int add) {
		return modifyStyle(m_hWnd, GWL_EXSTYLE, remove, add, 0);
	}

	/**
	 * ウィンドウの拡張スタイル変更.
	 * 
	 * @param remove
	 *            除去する拡張スタイル
	 * @param add
	 *            追加する拡張スタイル
	 * @param flags
	 *            SetWindowPosに渡すフラグ
	 * @return true：成功
	 */
	public boolean ModifyStyleEx(int remove, int add, int flags) {
		return modifyStyle(m_hWnd, GWL_EXSTYLE, remove, add, flags);
	}

	protected boolean modifyStyle(long wnd, int styleOffset, int remove,
			int add, int flags) {
		lastError = null;
		try {
			int style = WinUser.GetWindowLong(wnd, styleOffset);
			int newStyle = (style & ~remove) | add;
			if (style == newStyle)
				return false;

			WinUser.SetWindowLong(wnd, styleOffset, newStyle);

			if (flags != 0) {
				flags |= SWP_NOSIZE | SWP_NOMOVE | SWP_NOZORDER
						| SWP_NOACTIVATE;
				WinUser.SetWindowPos(wnd, 0, 0, 0, 0, 0, flags);
			}
			return true;
		} catch (Win32Exception e) {
			setLastError(e);
			return false;
		}
	}

	/**
	 * ウィンドウハンドル取得.
	 * <p>
	 * 内部で保持しているm_hWndを返す。
	 * </p>
	 * 
	 * @return ウィンドウハンドル（HWND）
	 */
	public long GetSafeHwnd() {
		return m_hWnd;
	}

	//
	// ウィンドウ状態
	//

	/**
	 * 入力可否判断.
	 * 
	 * @return true：マウスやキーボードからの入力を許可している
	 * @see #EnableWindow(boolean)
	 */
	public boolean IsWindowEnabled() {
		lastError = null;
		try {
			return WinUser.IsWindowEnabled(m_hWnd);
		} catch (Win32Exception e) {
			setLastError(e);
			return false;
		}
	}

	/**
	 * 入力可否設定.
	 * 
	 * @param enable
	 *            true：マウスやキーボードからの入力を許可する
	 * @return 設定前の状態
	 * @see #IsWindowEnabled()
	 */
	public boolean EnableWindow(boolean enable) {
		lastError = null;
		try {
			return WinUser.EnableWindow(m_hWnd, enable);
		} catch (Win32Exception e) {
			setLastError(e);
			return false;
		}
	}

	/**
	 * アクティブウィンドウ取得.
	 * 
	 * @return JWnd（アクティブなウィンドウが無い場合、null）
	 * @see #SetActiveWindow()
	 */
	public JWnd GetActiveWindow() {
		lastError = null;
		try {
			return FromHandle(WinUser.GetActiveWindow());
		} catch (Win32Exception e) {
			setLastError(e);
			return null;
		}
	}

	/**
	 * アクティブウィンドウ化.
	 * <p>
	 * 自分のウィンドウをアクティブにする。
	 * </p>
	 * 
	 * @return 直前にアクティブだったウィンドウ（無い場合はnull）
	 * @see #GetActiveWindow()
	 */
	public JWnd SetActiveWindow() {
		lastError = null;
		try {
			return FromHandle(WinUser.SetActiveWindow(m_hWnd));
		} catch (Win32Exception e) {
			setLastError(e);
			return null;
		}
	}

	/**
	 * フォーカスのあるウィンドウ取得.
	 * 
	 * @return JWnd（ウィンドウが無い場合、null）
	 * @see #SetFocus()
	 */
	public static JWnd GetFocus() {
		s_lastError = null;
		try {
			return FromHandle(WinUser.GetFocus());
		} catch (Win32Exception e) {
			setLastErrorStatic(e);
			return null;
		}
	}

	/**
	 * フォーカス設定.
	 * <p>
	 * 自分のウィンドウに入力フォーカスを要求する。
	 * </p>
	 * 
	 * @return 直前にフォーカスを持っていたウィンドウ（無い場合はnull）
	 * @see #GetFocus()
	 */
	public JWnd SetFocus() {
		lastError = null;
		try {
			return FromHandle(WinUser.SetFocus(m_hWnd));
		} catch (Win32Exception e) {
			setLastError(e);
			return null;
		}
	}

	/**
	 * デスクトップウィンドウ取得.
	 * 
	 * @return デスクトップのJWnd
	 */
	public static JWnd GetDesktopWindow() {
		s_lastError = null;
		try {
			return FromHandle(WinUser.GetDesktopWindow());
		} catch (Win32Exception e) {
			setLastErrorStatic(e);
			return null;
		}
	}

	/**
	 * 最前面ウィンドウ取得.
	 * 
	 * @return 最前面のウィンドウのJWnd（無い場合はnull）
	 */
	public static JWnd GetForegroundWindow() {
		s_lastError = null;
		try {
			return FromHandle(WinUser.GetForegroundWindow());
		} catch (Win32Exception e) {
			setLastErrorStatic(e);
			return null;
		}
	}

	/**
	 * 最前面移動.
	 * <p>
	 * 自分のウィンドウを最前面に出す。
	 * </p>
	 * 
	 * @return true：成功
	 */
	public boolean SetForegroundWindow() {
		lastError = null;
		try {
			return WinUser.SetForegroundWindow(m_hWnd);
		} catch (Win32Exception e) {
			setLastError(e);
			return false;
		}
	}

	//
	// ウィンドウサイズと位置
	//

	/**
	 * 最小化判断.
	 * 
	 * @return true：最小化（アイコン化）している
	 */
	public boolean IsIconic() {
		lastError = null;
		try {
			return WinUser.IsIconic(m_hWnd);
		} catch (Win32Exception e) {
			setLastError(e);
			return false;
		}
	}

	/**
	 * 最大化判断.
	 * 
	 * @return true：最大化している
	 */
	public boolean IsZoomed() {
		lastError = null;
		try {
			return WinUser.IsZoomed(m_hWnd);
		} catch (Win32Exception e) {
			setLastError(e);
			return false;
		}
	}

	/**
	 * ウィンドウ移動.
	 * <p>
	 * ウィンドウの位置とサイズを変更し、再描画する。
	 * </p>
	 * 
	 * @param x
	 *            X
	 * @param y
	 *            Y
	 * @param width
	 *            幅
	 * @param height
	 *            高さ
	 */
	public void MoveWindow(int x, int y, int width, int height) {
		lastError = null;
		try {
			WinUser.MoveWindow(m_hWnd, x, y, width, height, true);
		} catch (Win32Exception e) {
			setLastError(e);
			return;
		}
	}

	/**
	 * ウィンドウ移動.
	 * <p>
	 * ウィンドウの位置とサイズを変更する。
	 * </p>
	 * 
	 * @param x
	 *            X
	 * @param y
	 *            Y
	 * @param width
	 *            幅
	 * @param height
	 *            高さ
	 * @param repaint
	 *            true：再描画する
	 */
	public void MoveWindow(int x, int y, int width, int height, boolean repaint) {
		lastError = null;
		try {
			WinUser.MoveWindow(m_hWnd, x, y, width, height, repaint);
		} catch (Win32Exception e) {
			setLastError(e);
			return;
		}
	}

	/**
	 * ウィンドウ移動.
	 * <p>
	 * ウィンドウの位置とサイズを変更し、再描画する。
	 * </p>
	 * 
	 * @param r
	 *            範囲
	 */
	public void MoveWindow(Rectangle r) {
		lastError = null;
		try {
			WinUser.MoveWindow(m_hWnd, r.x, r.y, r.width, r.height, true);
		} catch (Win32Exception e) {
			setLastError(e);
			return;
		}
	}

	/**
	 * ウィンドウ移動.
	 * <p>
	 * ウィンドウの位置とサイズを変更する。
	 * </p>
	 * 
	 * @param r
	 *            範囲
	 * @param repaint
	 *            true：再描画する
	 */
	public void MoveWindow(Rectangle r, boolean repaint) {
		lastError = null;
		try {
			WinUser.MoveWindow(m_hWnd, r.x, r.y, r.width, r.height, repaint);
		} catch (Win32Exception e) {
			setLastError(e);
			return;
		}
	}

	/**
	 * ウィンドウ位置変更.
	 * <p>
	 * ウィンドウの位置・サイズ・Zオーダーを変更する。
	 * </p>
	 * 
	 * @param wndInsertAfter
	 *            通常のJWndオブジェクト または{@link #wndTop}, {@link #wndBottom},
	 *            {@link #wndTopMost}, {@link #wndNoTopMost}
	 * @param x
	 *            X
	 * @param y
	 *            Y
	 * @param cx
	 *            幅
	 * @param cy
	 *            高さ
	 * @param flags
	 *            位置変更オプション及びサイズ変更オプション
	 * @return true：成功
	 */
	public boolean SetWindowPos(JWnd wndInsertAfter, int x, int y, int cx,
			int cy, int flags) {
		lastError = null;

		long hwnd = 0;
		if (wndInsertAfter != null) {
			hwnd = wndInsertAfter.GetSafeHwnd();
		}
		try {
			return WinUser
					.SetWindowPos(m_hWnd, (int) hwnd, x, y, cx, cy, flags);
		} catch (Win32Exception e) {
			setLastError(e);
			return false;
		}
	}

	/**
	 * Zオーダーの先頭
	 * 
	 * @see #SetWindowPos(JWnd, int, int, int, int, int)
	 */
	public static final JWnd wndTop = new JWnd(HWND_TOP);

	/**
	 * Zオーダーの最後尾
	 * 
	 * @see #SetWindowPos(JWnd, int, int, int, int, int)
	 */
	public static final JWnd wndBottom = new JWnd(HWND_BOTTOM);

	/**
	 * 最上位でない全てのウィンドウの上
	 * 
	 * @see #SetWindowPos(JWnd, int, int, int, int, int)
	 */
	public static final JWnd wndTopMost = new JWnd(HWND_TOPMOST);

	/**
	 * 最上位でない全てのウィンドウの先頭
	 * 
	 * @see #SetWindowPos(JWnd, int, int, int, int, int)
	 */
	public static final JWnd wndNoTopMost = new JWnd(HWND_NOTOPMOST);

	/**
	 * ウィンドウ範囲取得.
	 * 
	 * @return 範囲（スクリーン座標）（取得エラー時はnull）
	 * @see #getClientRect()
	 */
	public Rectangle getWindowRect() {
		lastError = null;
		try {
			Rectangle r = new Rectangle();
			WinUser.GetWindowRect(m_hWnd, r);
			return r;
		} catch (Win32Exception e) {
			setLastError(e);
			return null;
		}
	}

	/**
	 * クライアント領域取得.
	 * 
	 * @return 範囲（クライアント座標系なので、左上は常に(0,0)）（取得エラー時はnull）
	 * @see #getWindowRect()
	 * @see #ClientToScreen(Rectangle)
	 */
	public Rectangle getClientRect() {
		lastError = null;
		try {
			Rectangle r = new Rectangle();
			WinUser.GetClientRect(m_hWnd, r);
			return r;
		} catch (Win32Exception e) {
			setLastError(e);
			return null;
		}
	}

	//
	// ウィンドウアクセス
	//

	/**
	 * ウィンドウ探索.
	 * <p>
	 * ウィンドウタイトルだけで探す場合、classNameにnullを指定し、windowNameに正確なタイトルを指定する。<br>
	 * ウィンドウタイトルの一部で探したい場合は{@link #enumWindows()}を使用する。
	 * </p>
	 * 
	 * @param className
	 *            Windowsの“ウィンドウクラス”
	 * @param windowName
	 *            ウィンドウのタイトル
	 * @return JWnd（ウィンドウが見つからなかった場合、null）
	 */
	public static JWnd FindWindow(String className, String windowName) {
		s_lastError = null;
		try {
			return FromHandle(WinUser.FindWindow(className, windowName));
		} catch (Win32Exception e) {
			setLastErrorStatic(e);
			return null;
		}
	}

	/**
	 * 次ウィンドウ取得.
	 * 
	 * @return JWnd（失敗した場合、null）
	 */
	public JWnd GetNextWindow() {
		lastError = null;
		try {
			return FromHandle(WinUser.GetNextWindow(m_hWnd, GW_HWNDNEXT));
		} catch (Win32Exception e) {
			setLastError(e);
			return null;
		}
	}

	/**
	 * 次ウィンドウ取得.
	 * 
	 * @param flag
	 *            方向（GW_HWNDNEXT または GW_HWNDPREV）
	 * @return JWnd（失敗した場合、null）
	 */
	public JWnd GetNextWindow(int flag) {
		lastError = null;
		try {
			return FromHandle(WinUser.GetNextWindow(m_hWnd, flag));
		} catch (Win32Exception e) {
			setLastError(e);
			return null;
		}
	}

	/**
	 * ウィンドウ列挙.
	 * <p>
	 * 全ウィンドウを列挙する。<br>
	 * 一覧の中から{@link #GetWindowText()}等を使って目的のウィンドウを探す。
	 * </p>
	 * 
	 * @return JWndのリスト（失敗した場合、null）
	 */
	public static List enumWindows() {
		s_lastError = null;
		try {
			List list = new ArrayList();
			WinUser.EnumWindows(list);
			return list;
		} catch (Win32Exception e) {
			setLastErrorStatic(e);
			return null;
		}
	}

	/**
	 * 子ウィンドウ列挙.
	 * 
	 * @return JWndのリスト（失敗した場合、null）
	 */
	public List enumChildWindows() {
		lastError = null;
		try {
			List list = new ArrayList();
			WinUser.EnumChildWindows(m_hWnd, list);
			return list;
		} catch (Win32Exception e) {
			setLastError(e);
			return null;
		}
	}

	/**
	 * オーナー取得.
	 * 
	 * @return オーナー（エラーまたはオーナーが無い場合、null）
	 */
	public JWnd GetOwner() {
		return (owner != null) ? owner : GetParent();
	}

	/**
	 * オーナー設定.
	 * 
	 * @param owner
	 *            オーナー
	 */
	public void SetOwner(JWnd owner) {
		this.owner = owner;
	}

	/**
	 * トップレベル子ウィンドウ取得.
	 * 
	 * @return JWnd（子ウィンドウが無い場合、null）
	 */
	public JWnd GetTopWindow() {
		lastError = null;
		try {
			return FromHandle(WinUser.GetTopWindow(m_hWnd));
		} catch (Win32Exception e) {
			setLastError(e);
			return null;
		}
	}

	/**
	 * ウィンドウ取得.
	 * 
	 * @param cmd
	 *            要求
	 * @return JWnd（無い場合、null）
	 */
	public JWnd GetWindow(int cmd) {
		lastError = null;
		try {
			return FromHandle(WinUser.GetWindow(m_hWnd, cmd));
		} catch (Win32Exception e) {
			setLastError(e);
			return null;
		}
	}

	/**
	 * ウィンドウ存在判断.
	 * 
	 * @return ウィンドウが生きているとき、true
	 */
	public boolean isWindow() {
		lastError = null;
		try {
			return WinUser.IsWindow(m_hWnd);
		} catch (Win32Exception e) {
			setLastError(e);
			return false;
		}
	}

	/**
	 * 直系子ウィンドウ判断.
	 * 
	 * @param wnd
	 *            調査対象ウィンドウ
	 * @return 子ウィンドウのときtrue
	 */
	public boolean IsChild(JWnd wnd) {
		lastError = null;

		long hwnd = 0;
		if (wnd != null) {
			hwnd = wnd.GetSafeHwnd();
		}
		try {
			return WinUser.IsChild(m_hWnd, hwnd);
		} catch (Win32Exception e) {
			setLastError(e);
			return false;
		}
	}

	/**
	 * 親ウィンドウ取得.
	 * 
	 * @return 親ウィンドウ（エラーまたは親ウィンドウが無い場合、null）
	 */
	public JWnd GetParent() {
		lastError = null;
		try {
			return FromHandle(WinUser.GetParent(m_hWnd));
		} catch (Win32Exception e) {
			setLastError(e);
			return null;
		}
	}

	/**
	 * 親ウィンドウ設定.
	 * 
	 * @param newParent
	 *            親ウィンドウ
	 * @return 直前の親ウィンドウ
	 */
	public JWnd SetParent(JWnd newParent) {
		lastError = null;

		long hwnd = 0;
		if (newParent != null) {
			hwnd = newParent.GetSafeHwnd();
		}
		try {
			return FromHandle(WinUser.SetParent(m_hWnd, hwnd));
		} catch (Win32Exception e) {
			setLastError(e);
			return null;
		}
	}

	/**
	 * 祖先ウィンドウ取得.
	 * 
	 * @param flags
	 *            パラメータ（GA_*）
	 * @return 祖先ウィンドウ
	 */
	public JWnd GetAncestor(int flags) {
		lastError = null;
		try {
			return FromHandle(WinUser.GetAncestor(m_hWnd, flags));
		} catch (Win32Exception e) {
			setLastError(e);
			return null;
		}
	}

	/**
	 * 指定位置ウィンドウ取得.
	 * 
	 * @param pt
	 *            位置
	 * @return JWnd（無い場合、null）
	 * @see #ChildWindowFromPoint(Point)
	 */
	public static JWnd WindowFromPoint(Point pt) {
		s_lastError = null;
		try {
			return FromHandle(WinUser.WindowFromPoint(pt.x, pt.y));
		} catch (Win32Exception e) {
			setLastErrorStatic(e);
			return null;
		}
	}

	/**
	 * 指定位置子ウィンドウ取得.
	 * 
	 * @param pt
	 *            位置
	 * @return JWnd（無い場合、null）
	 */
	public JWnd ChildWindowFromPoint(Point pt) {
		lastError = null;
		try {
			return FromHandle(WinUser.ChildWindowFromPoint(m_hWnd, pt.x, pt.y));
		} catch (Win32Exception e) {
			setLastError(e);
			return null;
		}
	}

	/**
	 * 指定位置子ウィンドウ取得.
	 * 
	 * @param x
	 * @param y
	 * @return JWnd（無い場合、null）
	 */
	public JWnd childWindowFromPoint(int x, int y) {
		lastError = null;
		try {
			return FromHandle(WinUser.ChildWindowFromPoint(m_hWnd, x, y));
		} catch (Win32Exception e) {
			setLastError(e);
			return null;
		}
	}

	/**
	 * 指定位置子ウィンドウ取得.
	 * 
	 * @param pt
	 *            位置
	 * @param flags
	 *            CWPフラグの論理和（{@link #CWP_ALL}等）
	 * @return JWnd（無い場合、null）
	 */
	public JWnd ChildWindowFromPointEx(Point pt, int flags) {
		lastError = null;
		try {
			return FromHandle(WinUser.ChildWindowFromPointEx(m_hWnd, pt.x,
					pt.y, flags));
		} catch (Win32Exception e) {
			setLastError(e);
			return null;
		}
	}

	/**
	 * ダイアログボックスコントロール取得.
	 * 
	 * @param id
	 *            コントロールの識別子
	 * @return コントロールのJWnd（取得に失敗した場合、null）
	 */
	public JWnd GetDlgItem(int id) {
		lastError = null;
		try {
			return FromHandle(WinUser.GetDlgItem(m_hWnd, id));
		} catch (Win32Exception e) {
			setLastError(e);
			return null;
		}
	}

	/**
	 * ダイアログボックス：コントロール識別子取得.
	 * 
	 * @return コントロールの識別子（取得に失敗した場合、0）
	 */
	public int GetDlgCtrlID() {
		lastError = null;
		try {
			return WinUser.GetDlgCtrlID(m_hWnd);
		} catch (Win32Exception e) {
			setLastError(e);
			return 0;
		}
	}

	//
	// 更新・塗り潰し
	//

	/**
	 * ウィンドウ表示状態設定.
	 * 
	 * @param cmdShow
	 *            表示方法
	 * @return true：ウィンドウが直前に表示されていた<br>
	 *         false：直前に非表示だった
	 */
	public boolean ShowWindow(int cmdShow) {
		lastError = null;
		try {
			return WinUser.ShowWindow(m_hWnd, cmdShow);
		} catch (Win32Exception e) {
			setLastError(e);
			return false;
		}
	}

	/**
	 * 可視状態判断.
	 * 
	 * @return true：可視状態
	 */
	public boolean IsWindowVisible() {
		lastError = null;
		try {
			return WinUser.IsWindowVisible(m_hWnd);
		} catch (Win32Exception e) {
			setLastError(e);
			return false;
		}
	}

	/**
	 * スクロールバー状態設定.
	 * <p>
	 * スクロールバーの有効無効を切り替える。
	 * </p>
	 * 
	 * @param bar
	 *            スクロールバー（SBフラグ）
	 * @param arrowFlags
	 *            状態（ESBフラグ）
	 * @return true：変更した場合<br>
	 *         false：状態が同じだった場合 またはエラー
	 * @see #ShowScrollBar(int, boolean)
	 */
	public boolean EnableScrollBar(int bar, int arrowFlags) {
		lastError = null;
		try {
			return WinUser.EnableScrollBar(m_hWnd, bar, arrowFlags);
		} catch (Win32Exception e) {
			setLastError(e);
			return false;
		}
	}

	//
	// 座標マッピング
	//

	/**
	 * スクリーン座標変換.
	 * 
	 * @param x
	 *            クライアント座標
	 * @param y
	 *            クライアント座標
	 * @return スクリーン座標
	 */
	public Point clientToScreen(int x, int y) {
		lastError = null;
		try {
			Point point = new Point(x, y);
			WinUser.ClientToScreen(m_hWnd, point);
			return point;
		} catch (Win32Exception e) {
			setLastError(e);
			return null;
		}
	}

	/**
	 * スクリーン座標変換.
	 * 
	 * @param pt
	 *            クライアント座標
	 */
	public void ClientToScreen(Point pt) {
		lastError = null;
		try {
			WinUser.ClientToScreen(m_hWnd, pt);
		} catch (Win32Exception e) {
			setLastError(e);
			return;
		}
	}

	/**
	 * スクリーン座標変換.
	 * 
	 * @param r
	 *            クライアント座標
	 */
	public void ClientToScreen(Rectangle r) {
		lastError = null;
		try {
			Point t = r.getLocation();
			Point b = new Point(r.x + r.width, r.y + r.height);
			WinUser.ClientToScreen(m_hWnd, t);
			WinUser.ClientToScreen(m_hWnd, b);
			r.x = t.x;
			r.y = t.y;
			r.width = b.x - t.x;
			r.height = b.y - t.y;
		} catch (Win32Exception e) {
			setLastError(e);
			return;
		}
	}

	/**
	 * クライアント座標変換.
	 * 
	 * @param x
	 *            スクリーン座標
	 * @param y
	 *            スクリーン座標
	 * @return クライアント座標
	 */
	public Point screenToClient(int x, int y) {
		lastError = null;
		try {
			Point point = new Point();
			point.x = x;
			point.y = y;
			WinUser.ScreenToClient(m_hWnd, point);
			return point;
		} catch (Win32Exception e) {
			setLastError(e);
			return null;
		}
	}

	/**
	 * クライアント座標変換.
	 * 
	 * @param pt
	 *            スクリーン座標
	 */
	public void ScreenToClient(Point pt) {
		lastError = null;
		try {
			WinUser.ScreenToClient(m_hWnd, pt);
		} catch (Win32Exception e) {
			setLastError(e);
			return;
		}
	}

	/**
	 * クライアント座標変換.
	 * 
	 * @param r
	 *            スクリーン座標
	 */
	public void ScreenToClient(Rectangle r) {
		lastError = null;
		try {
			Point t = r.getLocation();
			Point b = new Point(r.x + r.width, r.y + r.height);
			WinUser.ScreenToClient(m_hWnd, t);
			WinUser.ScreenToClient(m_hWnd, b);
			r.x = t.x;
			r.y = t.y;
			r.width = b.x - t.x;
			r.height = b.y - t.y;
		} catch (Win32Exception e) {
			setLastError(e);
			return;
		}
	}

	//
	// ウィンドウテキスト
	//

	/**
	 * ウィンドウタイトル設定.
	 * 
	 * @param string
	 *            タイトル
	 * @return true：成功
	 */
	public boolean SetWindowText(String string) {
		lastError = null;
		try {
			return WinUser.SetWindowText(m_hWnd, string);
		} catch (Win32Exception e) {
			setLastError(e);
			return false;
		}
	}

	/**
	 * ウィンドウタイトル取得.
	 * 
	 * @return タイトル（取得失敗時はnull）
	 */
	public String GetWindowText() {
		lastError = null;
		try {
			return WinUser.getWindowText(m_hWnd);
		} catch (Win32Exception e) {
			setLastError(e);
			return null;
		}
	}

	/**
	 * ウィンドウタイトル長取得.
	 * 
	 * @return タイトルの長さ
	 */
	public int GetWindowTextLength() {
		lastError = null;
		try {
			return WinUser.GetWindowTextLength(m_hWnd);
		} catch (Win32Exception e) {
			setLastError(e);
			return 0;
		}
	}

	/**
	 * ウィンドウクラス名取得.
	 * 
	 * @return ウィンドウのクラス名
	 */
	public String getClassName() {
		lastError = null;
		try {
			return WinUser.getClassName(m_hWnd);
		} catch (Win32Exception e) {
			setLastError(e);
			return null;
		}
	}

	//
	// スクロール
	//

	/**
	 * スクロールバー表示設定.
	 * 
	 * @param bar
	 *            スクロールバー（SBフラグ）
	 * @param show
	 *            true：表示
	 * @see #EnableScrollBar(int, int)
	 */
	public void ShowScrollBar(int bar, boolean show) {
		lastError = null;
		try {
			WinUser.ShowScrollBar(m_hWnd, bar, show);
		} catch (Win32Exception e) {
			setLastError(e);
			return;
		}
	}

	//
	// ダイアログボックス
	//

	/**
	 * ダイアログボックス：チェックボタン設定.
	 * 
	 * @param nIDButton
	 *            コントロールの識別子
	 * @param uCheck
	 *            値（{@link WinUserConst#BST_CHECKED}等）
	 * @return 成功した場合、true
	 */
	public boolean CheckDlgButton(int nIDButton, int uCheck) {
		lastError = null;
		try {
			return WinUser.CheckDlgButton(m_hWnd, nIDButton, uCheck);
		} catch (Win32Exception e) {
			setLastError(e);
			return false;
		}
	}

	/**
	 * ダイアログボックス：チェックボタン状態取得.
	 * 
	 * @param nIDButton
	 *            ボタンの識別子
	 * @return {@link WinUserConst#BST_CHECKED}等
	 */
	public int IsDlgButtonChecked(int nIDButton) {
		lastError = null;
		try {
			return WinUser.IsDlgButtonChecked(m_hWnd, nIDButton);
		} catch (Win32Exception e) {
			setLastError(e);
			return 0;
		}
	}

	/**
	 * ダイアログボックス：ラジオボタン設定.
	 * 
	 * @param nIDFirstButton
	 *            グループ内の最初のボタンの識別子
	 * @param nIDLastButton
	 *            グループ内の最後のボタンの識別子
	 * @param nIDCheckButton
	 *            選択したいボタンの識別子
	 * @return 成功した場合、true
	 */
	public boolean CheckRadioButton(int nIDFirstButton, int nIDLastButton,
			int nIDCheckButton) {
		lastError = null;
		try {
			return WinUser.CheckRadioButton(m_hWnd, nIDFirstButton,
					nIDLastButton, nIDCheckButton);
		} catch (Win32Exception e) {
			setLastError(e);
			return false;
		}
	}

	/**
	 * ダイアログボックス：ラジオボタン取得.
	 * 
	 * @param nIDFirstButton
	 *            グループ内の最初のボタンの識別子
	 * @param nIDLastButton
	 *            グループ内の最後のボタンの識別子
	 * @return チェックされているボタンの識別子（無い場合、0）
	 */
	public int GetCheckedRadioButton(int nIDFirstButton, int nIDLastButton) {
		lastError = null;
		try {
			for (int nID = nIDFirstButton; nID <= nIDLastButton; nID++) {
				if (WinUser.IsDlgButtonChecked(m_hWnd, nID) != BST_UNCHECKED)
					return nID;
			}
		} catch (Win32Exception e) {
			setLastError(e);
		}
		return 0; // invalid ID
	}

	/**
	 * ダイアログボックス：コントロール数値設定.
	 * 
	 * @param id
	 *            コントロールの識別子
	 * @param value
	 *            値
	 * @param signed
	 *            符号を付けるかどうか
	 * @return 成功した場合、true
	 */
	public boolean SetDlgItemInt(int id, int value, boolean signed) {
		lastError = null;
		try {
			return WinUser.SetDlgItemInt(m_hWnd, id, value, signed);
		} catch (Win32Exception e) {
			setLastError(e);
			return false;
		}
	}

	/**
	 * ダイアログボックス：コントロール数値取得.
	 * 
	 * @param id
	 *            コントロールの識別子
	 * @param translated
	 *            0番に、成功したかどうかの状態を返す（nullの場合は何も返さない）
	 * @param signed
	 *            true：符号付きとして扱う
	 * @return 値
	 */
	public int GetDlgItemInt(int id, boolean[] translated, boolean signed) {
		lastError = null;
		try {
			return WinUser.GetDlgItemInt(m_hWnd, id, translated, signed);
		} catch (Win32Exception e) {
			setLastError(e);
			return 0;
		}
	}

	/**
	 * ダイアログボックス：コントロールテキスト設定.
	 * 
	 * @param id
	 *            コントロールの識別子
	 * @param text
	 *            文字列
	 * @return 成功した場合、true
	 */
	public boolean SetDlgItemText(int id, String text) {
		lastError = null;
		try {
			return WinUser.SetDlgItemText(m_hWnd, id, text);
		} catch (Win32Exception e) {
			setLastError(e);
			return false;
		}
	}

	/**
	 * ダイアログボックス：コントロールテキスト取得.
	 * 
	 * @param id
	 *            コントロールの識別子
	 * @return 文字列
	 */
	public String GetDlgItemText(int id) {
		lastError = null;
		try {
			int len = WinUser.SendMessage(m_hWnd, WM_GETTEXTLENGTH, 0, 0);
			return WinUser.GetDlgItemText(m_hWnd, id, len);
		} catch (Win32Exception e) {
			setLastError(e);
			return null;
		}
	}

	/**
	 * ダイアログボックス：グループ内の次コントロール取得.
	 * 
	 * @param ctl
	 *            コントロール
	 * @param previous
	 *            方向フラグ（true：前のコントロール、false：次のコントロール）
	 * @return コントロール（取得に失敗した場合、null）
	 */
	public JWnd GetNextDlgGroupItem(JWnd ctl, boolean previous) {
		lastError = null;
		try {
			return FromHandle(WinUser.GetNextDlgGroupItem(m_hWnd, ctl
					.GetSafeHwnd(), previous));
		} catch (Win32Exception e) {
			setLastError(e);
			return null;
		}
	}

	/**
	 * ダイアログボックス：タブ移動可能な次コントロール取得.
	 * 
	 * @param ctl
	 *            コントロール
	 * @param previous
	 *            方向フラグ（true：前のコントロール、false：次のコントロール）
	 * @return コントロール（取得に失敗した場合、null）
	 */
	public JWnd GetNextDlgTabItem(JWnd ctl, boolean previous) {
		lastError = null;
		try {
			return FromHandle(WinUser.GetNextDlgTabItem(m_hWnd, ctl
					.GetSafeHwnd(), previous));
		} catch (Win32Exception e) {
			setLastError(e);
			return null;
		}
	}

	//
	// アラート関数
	//

	/**
	 * メッセージ出力.
	 * 
	 * @param text
	 *            メッセージ
	 * @return 値
	 */
	public int MessageBox(String text) {
		lastError = null;
		try {
			return WinUser.MessageBox(m_hWnd, text, null, MB_OK);
		} catch (Win32Exception e) {
			setLastError(e);
			return 0;
		}
	}

	/**
	 * メッセージ出力.
	 * 
	 * @param text
	 *            メッセージ
	 * @param caption
	 *            タイトル
	 * @return 値
	 */
	public int MessageBox(String text, String caption) {
		lastError = null;
		try {
			return WinUser.MessageBox(m_hWnd, text, caption, MB_OK);
		} catch (Win32Exception e) {
			setLastError(e);
			return 0;
		}
	}

	/**
	 * メッセージ出力.
	 * 
	 * @param text
	 *            メッセージ
	 * @param caption
	 *            タイトル
	 * @param type
	 *            タイプ
	 * @return 値
	 */
	public int MessageBox(String text, String caption, int type) {
		lastError = null;
		try {
			return WinUser.MessageBox(m_hWnd, text, caption, type);
		} catch (Win32Exception e) {
			setLastError(e);
			return 0;
		}
	}

	//
	// ウィンドウメッセージ
	//

	/**
	 * メッセージ送信（sned）.
	 * 
	 * @param message
	 *            メッセージ
	 * @param wParam
	 *            パラメータ
	 * @param lParam
	 *            パラメータ
	 * @return メッセージによる
	 */
	public int SendMessage(int message, int wParam, int lParam) {
		lastError = null;
		try {
			return WinUser.SendMessage(m_hWnd, message, wParam, lParam);
		} catch (Win32Exception e) {
			setLastError(e);
			return 0;
		}
	}

	/**
	 * メッセージ送信（sned）.
	 * 
	 * @param message
	 *            メッセージ
	 * @param wParam
	 *            パラメータ
	 * @param lParam
	 *            パラメータ（文字列）
	 * @return メッセージによる
	 */
	public int SendMessage(int message, int wParam, String lParam) {
		lastError = null;
		try {
			return WinUser.SendMessage(m_hWnd, message, wParam, lParam);
		} catch (Win32Exception e) {
			setLastError(e);
			return 0;
		}
	}

	/**
	 * メッセージ送信（sned）.
	 * 
	 * @param message
	 *            メッセージ
	 * @param wParam
	 *            パラメータ
	 * @param lParam
	 *            パラメータ（バイト配列）
	 * @return メッセージによる
	 */
	public int SendMessageGetString(int message, int wParam, byte[] lParam) {
		lastError = null;
		try {
			return WinUser.SendMessage(m_hWnd, message, wParam, lParam);
		} catch (Win32Exception e) {
			setLastError(e);
			return 0;
		}
	}

	/**
	 * メッセージ送信（sned）.
	 * <p>
	 * LPARAMが文字列取得用のバッファで戻り値が文字数であるメッセージで使用可能。<br>
	 * 例：WM_GETTEXT
	 * </p>
	 * 
	 * @param message
	 *            メッセージ
	 * @param wParam
	 *            パラメータ
	 * @param buf_len
	 *            バッファサイズ
	 * @return メッセージによる
	 */
	public String SendMessageGetString(int message, int wParam, int buf_len) {
		lastError = null;
		try {
			byte[] arr = new byte[buf_len];
			int len = WinUser.SendMessage(m_hWnd, message, wParam, arr);
			return new String(arr, 0, len * 2, "UTF-16LE");
		} catch (Win32Exception e) {
			setLastError(e);
			return null;
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * メッセージ送信（post）.
	 * 
	 * @param message
	 *            メッセージ
	 * @param wParam
	 *            パラメータ
	 * @param lParam
	 *            パラメータ
	 * @return true：成功
	 */
	public boolean PostMessage(int message, int wParam, int lParam) {
		lastError = null;
		try {
			return WinUser.PostMessage(m_hWnd, message, wParam, lParam);
		} catch (Win32Exception e) {
			setLastError(e);
			return false;
		}
	}

	public boolean equals(Object obj) {
		if (obj != null && obj instanceof JWnd) {
			JWnd wnd = (JWnd) obj;
			return m_hWnd == wnd.m_hWnd;
		}
		return false;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer(64);
		sb.append(super.toString());
		sb.append("{ HWND=");
		sb.append(Long.toHexString(m_hWnd));
		sb.append(" }");
		return sb.toString();
	}
}
