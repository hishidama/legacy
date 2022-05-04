package jp.hishidama.win32.shdocvw;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import jp.hishidama.win32.api.OcIdlConst;
import jp.hishidama.win32.com.ComPtr;
import jp.hishidama.win32.com.IDispatch;
import jp.hishidama.win32.mshtml.IHTMLDocument;

/**
 * IWebBrowser,IWebBrowser2,IWebBrowserAppクラス.
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">ひしだま</a>
 * @since 2007.10.22
 * @version 2008.07.14
 */
public class IWebBrowser extends IDispatch {

	/**
	 * 新規WebBrowser作成.
	 * 
	 * @return ウェブブラウザー
	 */
	public static IWebBrowser create() {
		long ptr = Native.create();
		if (ptr == 0)
			return null;
		return new IWebBrowser(ptr);
	}

	/**
	 * WebBrowser探索.
	 * 
	 * @param hwnd
	 *            HWND
	 * @return ウェブブラウザー
	 */
	public static IWebBrowser findWebBrowser(long hwnd) {
		if (hwnd == 0) {
			return null;
		}

		long ptr = Native.findWebBrowser(hwnd);
		if (ptr == 0)
			return null;
		return new IWebBrowser(ptr);
	}

	/**
	 * WebBrowser列挙.
	 * 
	 * @return {@link IWebBrowser}のリスト
	 */
	public static List enumWebBrowser() {
		List list = new ArrayList();
		Native.enumWebBrowser(list);
		return list;
	}

	protected IWebBrowser(long dll_ptr) {
		super(dll_ptr);
	}

	/**
	 * 履歴の前ページへ移動.
	 */
	public void goBack() {
		Native.GoBack(getPtr());
	}

	/**
	 * 履歴の次ページへ移動.
	 */
	public void goForward() {
		Native.GoForward(getPtr());
	}

	/**
	 * ホームへ移動.
	 */
	public void goHome() {
		Native.GoHome(getPtr());
	}

	/**
	 * 検索ページへ移動.
	 */
	public void goSearch() {
		Native.GoSearch(getPtr());
	}

	/**
	 * 指定URLへ移動.
	 * 
	 * @param url
	 *            URL
	 */
	public void navigate(String url) {
		Native.Navigate(getPtr(), url, null, null);
	}

	/**
	 * 指定URLへ移動.
	 * 
	 * @param url
	 *            URL
	 * @param flags
	 *            フラグ
	 * @param targetFrameName
	 *            フレーム名
	 */
	public void navigate(String url, String flags, String targetFrameName) {
		Native.Navigate(getPtr(), url, flags, targetFrameName);
	}

	/**
	 * 更新.
	 */
	public void refresh() {
		Native.Refresh(getPtr());
	}

	/**
	 * 停止.
	 */
	public void stop() {
		Native.Stop(getPtr());
	}

	/**
	 * HTMLドキュメント取得.
	 * 
	 * @return HTMLドキュメント
	 */
	public IHTMLDocument getDocument() {
		IHTMLDocument doc = (IHTMLDocument) Native.get_Document(getPtr());
		return doc;
	}

	/**
	 * @see ComPtr#disposeChild()
	 * @deprecated
	 * @see #getDocument()
	 */
	public IHTMLDocument getDocument(boolean addChild) {
		return getDocument();
	}

	/**
	 * ドキュメントオブジェクト名取得.
	 * 
	 * @return 名称
	 */
	public String getType() {
		return Native.get_Type(getPtr());
	}

	/**
	 * 位置取得.
	 * 
	 * @return 位置
	 */
	public Point getLocation() {
		long ptr = getPtr();
		int x = Native.get_Left(ptr);
		int y = Native.get_Top(ptr);
		return new Point(x, y);
	}

	/**
	 * 位置設定.
	 * 
	 * @param x
	 *            X
	 * @param y
	 *            Y
	 */
	public void setLocation(int x, int y) {
		long ptr = getPtr();
		Native.put_Left(ptr, x);
		Native.put_Top(ptr, y);
	}

	/**
	 * サイズ取得.
	 * 
	 * @return サイズ
	 */
	public Dimension getSize() {
		long ptr = getPtr();
		int cx = Native.get_Width(ptr);
		int cy = Native.get_Height(ptr);
		return new Dimension(cx, cy);
	}

	/**
	 * サイズ設定.
	 * 
	 * @param cx
	 *            幅
	 * @param cy
	 *            高さ
	 */
	public void setSize(int cx, int cy) {
		long ptr = getPtr();
		Native.put_Width(ptr, cx);
		Native.put_Height(ptr, cy);
	}

	/**
	 * 位置サイズ取得.
	 * 
	 * @return 矩形
	 */
	public Rectangle getBounds() {
		long ptr = getPtr();
		int x = Native.get_Left(ptr);
		int y = Native.get_Top(ptr);
		int cx = Native.get_Width(ptr);
		int cy = Native.get_Height(ptr);
		return new Rectangle(x, y, cx, cy);
	}

	/**
	 * 位置サイズ設定.
	 * 
	 * @param x
	 *            X
	 * @param y
	 *            Y
	 * @param cx
	 *            幅
	 * @param cy
	 *            高さ
	 */
	public void setBounds(int x, int y, int cx, int cy) {
		long ptr = getPtr();
		Native.put_Left(ptr, x);
		Native.put_Top(ptr, y);
		Native.put_Width(ptr, cx);
		Native.put_Height(ptr, cy);
	}

	/**
	 * X座標取得.
	 * 
	 * @return X
	 */
	public int getLeft() {
		return Native.get_Left(getPtr());
	}

	/**
	 * X座標設定.
	 * 
	 * @param x
	 *            X
	 */
	public void setLeft(int x) {
		Native.put_Left(getPtr(), x);
	}

	/**
	 * Y座標取得.
	 * 
	 * @return Y
	 */
	public int getTop() {
		return Native.get_Top(getPtr());
	}

	/**
	 * Y座標設定.
	 * 
	 * @param y
	 *            Y
	 */
	public void setTop(int y) {
		Native.put_Top(getPtr(), y);
	}

	/**
	 * 幅取得.
	 * 
	 * @return 幅
	 */
	public int getWidth() {
		return Native.get_Width(getPtr());
	}

	/**
	 * 幅設定.
	 * 
	 * @param width
	 *            幅
	 */
	public void setWidth(int width) {
		Native.put_Width(getPtr(), width);
	}

	/**
	 * 高さ取得.
	 * 
	 * @return 高さ
	 */
	public int getHeight() {
		return Native.get_Height(getPtr());
	}

	/**
	 * 高さ設定.
	 * 
	 * @param height
	 *            高さ
	 */
	public void setHeight(int height) {
		Native.put_Height(getPtr(), height);
	}

	/**
	 * ロケーション名取得.
	 * 
	 * @return 名称
	 */
	public String getLocationName() {
		return Native.get_LocationName(getPtr());
	}

	/**
	 * ロケーションURL取得.
	 * 
	 * @return URL
	 */
	public String getLocationURL() {
		return Native.get_LocationURL(getPtr());
	}

	/**
	 * ビジー状態取得.
	 * 
	 * @return ビジー状態
	 */
	public boolean getBusy() {
		return Native.get_Busy(getPtr());
	}

	/**
	 * 終了.
	 */
	public void quit() {
		Native.Quit(getPtr());
	}

	/**
	 * コントロールの名称取得.
	 * 
	 * @return 名称
	 */
	public String getName() {
		return Native.get_Name(getPtr());
	}

	/**
	 * HWND取得.
	 * 
	 * @return HWND
	 */
	public long getHWND() {
		return Native.get_HWND(getPtr());
	}

	/**
	 * 実行モジュールのフルパス取得.
	 * 
	 * @return フルパス名
	 */
	public String getFullName() {
		return Native.get_FullName(getPtr());
	}

	/**
	 * 実行モジュールのパス取得.
	 * 
	 * @return パス
	 */
	public String getPath() {
		return Native.get_Path(getPtr());
	}

	/**
	 * 表示状態取得.
	 * 
	 * @return 表示状態
	 */
	public boolean getVisible() {
		return Native.get_Visible(getPtr());
	}

	/**
	 * 表示状態設定.
	 * 
	 * @param b
	 *            表示状態
	 */
	public void setVisible(boolean b) {
		Native.put_Visible(getPtr(), b);
	}

	/**
	 * ステータスバー表示状態取得.
	 * 
	 * @return 表示状態
	 */
	public boolean getStatusBar() {
		return Native.get_StatusBar(getPtr());
	}

	/**
	 * ステータスバー表示状態設定.
	 * 
	 * @param b
	 *            表示状態
	 */
	public void setStatusBar(boolean b) {
		Native.put_StatusBar(getPtr(), b);
	}

	/**
	 * ステータスバー表示文字列取得.
	 * 
	 * @return 文字列
	 */
	public String getStatusText() {
		return Native.get_StatusText(getPtr());
	}

	/**
	 * ステータスバー文字列表示.
	 * 
	 * @param text
	 *            文字列
	 */
	public void setStatusText(String text) {
		Native.put_StatusText(getPtr(), text);
	}

	/**
	 * ツールバー表示状態取得.
	 * 
	 * @return 表示状態
	 */
	public int getToolBar() {
		return Native.get_ToolBar(getPtr());
	}

	/**
	 * ツールバー表示状態設定.
	 * 
	 * @param value
	 *            表示状態
	 */
	public void setToolBar(int value) {
		Native.put_ToolBar(getPtr(), value);
	}

	/**
	 * メニューバー表示状態取得.
	 * 
	 * @return 表示状態
	 */
	public boolean getMenuBar() {
		return Native.get_MenuBar(getPtr());
	}

	/**
	 * メニューバー表示状態設定.
	 * 
	 * @param b
	 *            表示状態
	 */
	public void setMenuBar(boolean b) {
		Native.put_MenuBar(getPtr(), b);
	}

	/**
	 * フルスクリーン状態取得.
	 * 
	 * @return フルスクリーン状態
	 */
	public boolean getFullScreen() {
		return Native.get_FullScreen(getPtr());
	}

	/**
	 * フルスクリーン状態設定.
	 * 
	 * @param b
	 *            フルスクリーン状態
	 */
	public void setFullScreen(boolean b) {
		Native.put_FullScreen(getPtr(), b);
	}

	/**
	 * ReadyState取得.
	 * 
	 * @return ReadyState
	 * @see OcIdlConst#READYSTATE_COMPLETE
	 */
	public int getReadyState() {
		return Native.get_ReadyState(getPtr());
	}

	/**
	 * オフライン状態取得.
	 * 
	 * @return オフライン状態
	 */
	public boolean getOffline() {
		return Native.get_Offline(getPtr());
	}

	/**
	 * オフライン状態設定.
	 * 
	 * @param b
	 *            オフライン状態
	 */
	public void setOffline(boolean b) {
		Native.put_Offline(getPtr(), b);
	}

	/**
	 * ダイアログ表示可否取得.
	 * 
	 * @return 表示可否
	 */
	public boolean getSilent() {
		return Native.get_Silent(getPtr());
	}

	/**
	 * ダイアログ表示可否設定.
	 * 
	 * @param b
	 *            表示可否
	 */
	public void setSilent(boolean b) {
		Native.put_Silent(getPtr(), b);
	}

	/**
	 * トップレベルブラウザー登録状態取得.
	 * 
	 * @return 登録状態
	 */
	public boolean getRegisterAsBrowser() {
		return Native.get_RegisterAsBrowser(getPtr());
	}

	/**
	 * トップレベルブラウザー登録状態設定.
	 * 
	 * @param b
	 *            登録状態
	 */
	public void setRegisterAsBrowser(boolean b) {
		Native.put_RegisterAsBrowser(getPtr(), b);
	}

	/**
	 * ドロップターゲット登録状態取得.
	 * 
	 * @return 登録状態
	 */
	public boolean getRegisterAsDropTarget() {
		return Native.get_RegisterAsDropTarget(getPtr());
	}

	/**
	 * ドロップターゲット登録状態設定.
	 * 
	 * @param b
	 *            登録状態
	 */
	public void setRegisterAsDropTarget(boolean b) {
		Native.put_RegisterAsDropTarget(getPtr(), b);
	}

	/**
	 * シアター状態取得.
	 * 
	 * @return シアター状態
	 */
	public boolean getTheaterMode() {
		return Native.get_TheaterMode(getPtr());
	}

	/**
	 * シアター状態設定.
	 * 
	 * @param b
	 *            シアター状態
	 */
	public void setTheaterMode(boolean b) {
		Native.put_TheaterMode(getPtr(), b);
	}

	/**
	 * アドレスバー表示状態取得.
	 * 
	 * @return 表示状態
	 */
	public boolean getAddressBar() {
		return Native.get_AddressBar(getPtr());
	}

	/**
	 * アドレスバー表示状態設定.
	 * 
	 * @param b
	 *            表示状態
	 */
	public void setAddressBar(boolean b) {
		Native.put_AddressBar(getPtr(), b);
	}

	/**
	 * サイズ変更可否取得.
	 * 
	 * @return 変更可否
	 */
	public boolean getResizable() {
		return Native.get_Resizable(getPtr());
	}

	/**
	 * サイズ変更可否設定.
	 * 
	 * @param b
	 *            変更可否
	 */
	public void setResizable(boolean b) {
		Native.put_Resizable(getPtr(), b);
	}

	private static class Native {
		//
		// Original
		//

		private static native long create();

		private static native void enumWebBrowser(List list);

		private static native long findWebBrowser(long hwnd);

		//
		// IWebBrowser
		//

		private static native void GoBack(long ptr);

		private static native void GoForward(long ptr);

		private static native void GoHome(long ptr);

		private static native void GoSearch(long ptr);

		private static native void Navigate(long ptr, String url, String flags,
				String targetFrameName);

		private static native void Refresh(long ptr);

		private static native void Stop(long ptr);

		// get_Application
		// get_Parent

		private static native IDispatch get_Document(long ptr);

		private static native String get_Type(long ptr);

		private static native int get_Left(long ptr);

		private static native void put_Left(long ptr, int left);

		private static native int get_Top(long ptr);

		private static native void put_Top(long ptr, int top);

		private static native int get_Width(long ptr);

		private static native void put_Width(long ptr, int width);

		private static native int get_Height(long ptr);

		private static native void put_Height(long ptr, int height);

		private static native String get_LocationName(long ptr);

		private static native String get_LocationURL(long ptr);

		private static native boolean get_Busy(long ptr);

		//
		// IWebBrowserApp
		//

		private static native void Quit(long ptr);

		private static native String get_Name(long ptr);

		private static native long get_HWND(long ptr);

		private static native String get_FullName(long ptr);

		private static native String get_Path(long ptr);

		private static native boolean get_Visible(long ptr);

		private static native void put_Visible(long ptr, boolean b);

		private static native boolean get_StatusBar(long ptr);

		private static native void put_StatusBar(long ptr, boolean b);

		private static native String get_StatusText(long ptr);

		private static native void put_StatusText(long ptr, String text);

		private static native int get_ToolBar(long ptr);

		private static native void put_ToolBar(long ptr, int value);

		private static native boolean get_MenuBar(long ptr);

		private static native void put_MenuBar(long ptr, boolean b);

		private static native boolean get_FullScreen(long ptr);

		private static native void put_FullScreen(long ptr, boolean b);

		//
		// IWebBrowser2
		//

		private static native int get_ReadyState(long ptr);

		private static native boolean get_Offline(long ptr);

		private static native void put_Offline(long ptr, boolean b);

		private static native boolean get_Silent(long ptr);

		private static native void put_Silent(long ptr, boolean b);

		private static native boolean get_RegisterAsBrowser(long ptr);

		private static native void put_RegisterAsBrowser(long ptr, boolean b);

		private static native boolean get_RegisterAsDropTarget(long ptr);

		private static native void put_RegisterAsDropTarget(long ptr, boolean b);

		private static native boolean get_TheaterMode(long ptr);

		private static native void put_TheaterMode(long ptr, boolean b);

		private static native boolean get_AddressBar(long ptr);

		private static native void put_AddressBar(long ptr, boolean b);

		private static native boolean get_Resizable(long ptr);

		private static native void put_Resizable(long ptr, boolean b);
	}
}
