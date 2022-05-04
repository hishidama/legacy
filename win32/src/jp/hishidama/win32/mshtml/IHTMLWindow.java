package jp.hishidama.win32.mshtml;

import jp.hishidama.win32.com.ComPtr;
import jp.hishidama.win32.com.IDispatch;
import jp.hishidama.win32.com.IUnknown;
import jp.hishidama.win32.com.Variant;

/**
 * IHTMLWindow2～3クラス.
 * <p>
 * HTMLウィンドウを扱うクラスです。
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">ひしだま</a>
 * @since 2007.10.22
 * @version 2008.07.14
 */
public class IHTMLWindow extends IHTMLFramesCollection {

	protected IHTMLWindow(long dll_ptr) {
		super(dll_ptr);
	}

	protected long getPtr() {
		return super.getPtr();
	}

	/**
	 * フレーム取得.
	 * 
	 * @return コレクション
	 */
	public IHTMLFramesCollection getFrames() {
		return Native.get_frames(getPtr());
	}

	/**
	 * @see ComPtr#disposeChild()
	 * @deprecated
	 * @see #getFrames()
	 */
	public IHTMLFramesCollection getFrames(boolean addChild) {
		return getFrames();
	}

	public void setDefaultStatus(String status) {
		Native.put_defaultStatus(getPtr(), status);
	}

	public String getDefaultStatus() {
		return Native.get_defaultStatus(getPtr());
	}

	public void setStatus(String status) {
		Native.put_status(getPtr(), status);
	}

	public String getStatus() {
		return Native.get_status(getPtr());
	}

	/**
	 * 警告ダイアログ表示.
	 * 
	 * @param message
	 *            メッセージ
	 */
	public void alert(String message) {
		Native.alert(getPtr(), message);
	}

	/**
	 * 確認ダイアログ表示.
	 * 
	 * @param message
	 *            メッセージ.
	 * @return 戻り値
	 */
	public boolean confirm(String message) {
		return Native.confirm(getPtr(), message);
	}

	/**
	 * 入力ダイアログ表示.
	 * 
	 * @param message
	 *            メッセージ.
	 * @param defstr
	 *            初期値
	 * @return 入力値
	 */
	public Variant prompt(String message, String defstr) {
		return Native.prompt(getPtr(), message, defstr);
	}

	public IHTMLLocation getLocation() {
		return Native.get_location(getPtr());
	}

	/**
	 * @see ComPtr#disposeChild()
	 * @deprecated
	 * @see #getLocation()
	 */
	public IHTMLLocation getLocation(boolean addChild) {
		return getLocation();
	}

	public void close() {
		Native.close(getPtr());
	}

	public void setOpener(Variant opener) {
		Native.put_opener(getPtr(), opener);
	}

	public Variant getOpener() {
		return Native.get_opener(getPtr());
	}

	public void setName(String name) {
		Native.put_name(getPtr(), name);
	}

	public String getName() {
		return Native.get_name(getPtr());
	}

	/**
	 * 親ウィンドウ取得.
	 * 
	 * @return HTMLウィンドウ
	 */
	public IHTMLWindow getParent() {
		return Native.get_parent(getPtr());
	}

	/**
	 * @see ComPtr#disposeChild()
	 * @deprecated
	 * @see #getParent()
	 */
	public IHTMLWindow getParent(boolean addChild) {
		return getParent();
	}

	public IHTMLWindow open(String url, String name, String features,
			boolean replace) {
		return Native.open(getPtr(), url, name, features, replace);
	}

	/**
	 * @see ComPtr#disposeChild()
	 * @deprecated
	 * @see #open(String, String, String, boolean)
	 */
	public IHTMLWindow open(String url, String name, String features,
			boolean replace, boolean addChild) {
		return open(url, name, features, replace);
	}

	public IHTMLWindow getSelf() {
		return Native.get_self(getPtr());
	}

	/**
	 * @see ComPtr#disposeChild()
	 * @deprecated
	 * @see #getSelf()
	 */
	public IHTMLWindow getSelf(boolean addChild) {
		return getSelf();
	}

	public IHTMLWindow getTop() {
		return Native.get_top(getPtr());
	}

	/**
	 * @see ComPtr#disposeChild()
	 * @deprecated
	 * @see #getTop()
	 */
	public IHTMLWindow getTop(boolean addChild) {
		return getTop();
	}

	public IHTMLWindow getWindow() {
		return Native.get_window(getPtr());
	}

	/**
	 * @see ComPtr#disposeChild()
	 * @deprecated
	 * @see #getWindow()
	 */
	public IHTMLWindow getWindow(boolean addChild) {
		return getWindow();
	}

	public void navigate(String url) {
		Native.navigate(getPtr(), url);
	}

	/**
	 * HTMLドキュメント取得.
	 * 
	 * @return HTMLドキュメント
	 */
	public IHTMLDocument getDocument() {
		return Native.get_document(getPtr());
	}

	/**
	 * @see ComPtr#disposeChild()
	 * @deprecated
	 * @see #getDocument()
	 */
	public IHTMLDocument getDocument(boolean addChild) {
		return getDocument();
	}

	public IUnknown getNewEnum() {
		return Native.get__newEnum(getPtr());
	}

	/**
	 * @see ComPtr#disposeChild()
	 * @deprecated
	 * @see #getNewEnum()
	 */
	public IUnknown getNewEnum(boolean addChild) {
		return getNewEnum();
	}

	/**
	 * フォーカス設定.
	 */
	public void focus() {
		Native.focus(getPtr());
	}

	public boolean isClosed() {
		return Native.get_closed(getPtr());
	}

	public void blur() {
		Native.blur(getPtr());
	}

	public void scroll(int x, int y) {
		Native.scroll(getPtr(), x, y);
	}

	public Variant execScript(String code, String language) {
		return Native.execScript(getPtr(), code, language);
	}

	public void scrollBy(int x, int y) {
		Native.scrollBy(getPtr(), x, y);
	}

	public void scrollTo(int x, int y) {
		Native.scrollTo(getPtr(), x, y);
	}

	public void moveTo(int x, int y) {
		Native.moveTo(getPtr(), x, y);
	}

	public void moveBy(int x, int y) {
		Native.moveBy(getPtr(), x, y);
	}

	public void resizeTo(int x, int y) {
		Native.resizeTo(getPtr(), x, y);
	}

	public void resizeBy(int x, int y) {
		Native.resizeBy(getPtr(), x, y);
	}

	public IDispatch getExternal() {
		return Native.get_external(getPtr());
	}

	/**
	 * @see ComPtr#disposeChild()
	 * @deprecated
	 * @see #getExternal()
	 */
	public IDispatch getExternal(boolean addChild) {
		return getExternal();
	}

	public int getScreenLeft() {
		return Native.get_screenLeft(getPtr());
	}

	public int getScreenTop() {
		return Native.get_screenTop(getPtr());
	}

	public void print() {
		Native.print(getPtr());
	}

	private static class Native {
		//
		// IHTMLWindow2
		//
		private static native IHTMLFramesCollection get_frames(long ptr);

		private static native void put_defaultStatus(long ptr, String p);

		private static native String get_defaultStatus(long ptr);

		private static native void put_status(long ptr, String p);

		private static native String get_status(long ptr);

		// private static native void setTimeout (long ptr);
		// private static native void clearTimeout (long ptr);

		private static native void alert(long ptr, String message);

		private static native boolean confirm(long ptr, String message);

		private static native Variant prompt(long ptr, String message,
				String defstr);

		// private static native IHTMLImageElementFactory get_Image (long ptr);

		private static native IHTMLLocation get_location(long ptr);

		// private static native IOmHistory get_history (long ptr);

		private static native void close(long ptr);

		private static native void put_opener(long ptr, Variant p);

		private static native Variant get_opener(long ptr);

		// / private static native IOmNavigator get_navigator (long ptr);

		private static native void put_name(long ptr, String p);

		private static native String get_name(long ptr);

		private static native IHTMLWindow get_parent(long ptr);

		private static native IHTMLWindow open(long ptr, String url,
				String name, String features, boolean replace);

		private static native IHTMLWindow get_self(long ptr);

		private static native IHTMLWindow get_top(long ptr);

		private static native IHTMLWindow get_window(long ptr);

		private static native void navigate(long ptr, String url);

		private static native IHTMLDocument get_document(long ptr);

		// private static native IHTMLEventObj get_event (long ptr);

		private static native IUnknown get__newEnum(long ptr);

		// private static native Variant showModalDialog(long ptr, String
		// dialog,
		// Variant[] varArgIn, Variant[] varOptions);

		// private static native void showHelp(long ptr, String helpURL,
		// Variant helpArg, String features);

		// private static native IHTMLScreen get_screen (long ptr);
		// private static native IHTMLOptionElementFactory get_Option (long
		// ptr);

		private static native void focus(long ptr);

		private static native boolean get_closed(long ptr);

		private static native void blur(long ptr);

		private static native void scroll(long ptr, int x, int y);

		// private static native IOmNavigator get_clientInformation (long ptr);
		// private static native void setInterval (long ptr);
		// private static native void clearInterval (long ptr, int timerID );
		// private static native void put_offscreenBuffering (long ptr, VARIANT
		// p );
		// private static native VARIANT get_offscreenBuffering (long ptr);

		private static native Variant execScript(long ptr, String code,
				String language);

		// private static native String toString (long ptr);

		private static native void scrollBy(long ptr, int x, int y);

		private static native void scrollTo(long ptr, int x, int y);

		private static native void moveTo(long ptr, int x, int y);

		private static native void moveBy(long ptr, int x, int y);

		private static native void resizeTo(long ptr, int x, int y);

		private static native void resizeBy(long ptr, int x, int y);

		private static native IDispatch get_external(long ptr);

		//
		// IHTMLWindow3
		//
		private static native int get_screenLeft(long ptr);

		private static native int get_screenTop(long ptr);

		// private static native void attachEvent (long ptr);
		// private static native void detachEvent (long ptr);
		// private static native void setTimeout (long ptr);
		// private static native void setInterval (long ptr);

		private static native void print(long ptr);

		// private static native IHTMLDataTransfer get_clipboardData (long ptr);
		// private static native IHTMLWindow showModelessDialog(long ptr,
		// String url, Variant[] varArgIn, Variant[] options);

		//
		// IHTMLWindow4
		//
		// private static native HtmlFrameBase get_frameElement(long ptr);

	}
}
