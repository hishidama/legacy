package jp.hishidama.robot;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.*;

import jp.hishidama.robot.ie.FileDownloadDialogFactory;
import jp.hishidama.robot.ie.FileDownloadProgressDialog;
import jp.hishidama.robot.ie.IHTMLInputRadioUtil;
import jp.hishidama.robot.ie.IHTMLInputValueUtil;
import jp.hishidama.robot.ie.IHTMLSelectUtil;
import jp.hishidama.win32.JWnd;
import jp.hishidama.win32.api.OcIdlConst;
import jp.hishidama.win32.api.WinUser;
import jp.hishidama.win32.api.WinUserConst;
import jp.hishidama.win32.com.ComMgr;
import jp.hishidama.win32.com.ComPtr;
import jp.hishidama.win32.mshtml.*;
import jp.hishidama.win32.shdocvw.IWebBrowser;

/**
 * InternetExplorer自動実行クラス.
 * <p>
 * IEを操作するCOM(ActiveX)のSHDocVwやMSHTMLを扱うロボットクラス。<br> →<a target="hishidama"
 * href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">使用例</a>
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
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">ひしだま</a>
 * @since 2007.10.22
 * @version 2007.11.01
 */
public class IERobot implements OcIdlConst {

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

	//
	// 初期化関連
	//

	/**
	 * 新規ウィンドウ作成.
	 * <p>
	 * 新しいIEを作成する。
	 * </p>
	 * <p>
	 * 当メソッドの使用前に{@link ComMgr#initialize()}が一度呼ばれていなければならない。<br>
	 * 当インスタンスの使用終了後は、必ず{@link #dispose()}を呼び出してインスタンスを破棄すること。
	 * </p>
	 * 
	 * @return IERobot（作成に失敗した場合、null）
	 * @see #setBounds(int, int, int, int)
	 * @see #setVisible(boolean)
	 * @see #setForeground()
	 * @see #dispose()
	 */
	public static IERobot create() {
		IWebBrowser wb = IWebBrowser.create();
		if (wb == null)
			return null;
		return new IERobot(wb);
	}

	/**
	 * IERobot作成.
	 * <p>
	 * 指定されたWebBrowserを操作するIERobotを作成する。
	 * </p>
	 * <p>
	 * 当メソッドの使用前に{@link ComMgr#initialize()}が一度呼ばれていなければならない。<br>
	 * 当インスタンスの使用終了後に{@link #dispose()}を呼び出すと、指定されたWebBrowserも破棄される。
	 * </p>
	 * 
	 * @param wb
	 *            WebBrowserコントロール
	 * @return IERobot（wbがnullの場合、null）
	 * @see #detach()
	 */
	public static IERobot create(IWebBrowser wb) {
		if (wb == null)
			return null;
		return new IERobot(wb);
	}

	/**
	 * IERobot作成.
	 * <p>
	 * 指定されたウィンドウを操作するIERobotを作成する。
	 * </p>
	 * <p>
	 * 当メソッドの使用前に{@link ComMgr#initialize()}が一度呼ばれていなければならない。<br>
	 * 当インスタンスの使用終了後は、必ず{@link #dispose()}を呼び出してインスタンスを破棄すること。
	 * </p>
	 * 
	 * @param wnd
	 *            ウィンドウ
	 * @return IERobot（ウィンドウがWebBrowserコントロールでない場合、null）
	 * @see #dispose()
	 */
	public static IERobot create(JWnd wnd) {
		IWebBrowser wb = IWebBrowser.findWebBrowser(wnd.GetSafeHwnd());
		if (wb == null)
			return null;
		return new IERobot(wb);
	}

	/**
	 * IE探索.
	 * <p>
	 * タイトルが一致するウィンドウ（WebBrowser）のIERobotを作成する。
	 * </p>
	 * <p>
	 * 当メソッドの使用前に{@link ComMgr#initialize()}が一度呼ばれていなければならない。<br>
	 * 当インスタンスの使用終了後は、必ず{@link #dispose()}を呼び出してインスタンスを破棄すること。
	 * </p>
	 * 
	 * @param title
	 *            タイトル
	 * @return IERobot（見つからなかった場合、null）
	 * @see #dispose()
	 */
	public static IERobot findIE(String title) {
		List list = enumIEWebBrowser();
		IWebBrowser wb = null;
		for (int i = 0; i < list.size(); i++) {
			IHTMLDocument doc = null;
			try {
				IWebBrowser w = (IWebBrowser) list.get(i);
				doc = w.getDocument();
				// System.out.println(i + ":" + doc.getTitle());
				if (title.equals(doc.getTitle())) {
					wb = w;
					break;
				}
			} catch (RuntimeException e) {
				// e.printStackTrace();
			} finally {
				ComPtr.dispose(doc);
			}
		}
		if (wb == null) {
			ComPtr.dispose(list);
			return null;
		} else {
			ComPtr.dispose(list, wb);
			return new IERobot(wb);
		}
	}

	/**
	 * WebBrowser列挙.
	 * <p>
	 * 存在するWebBrowserコントロールのオブジェクトを全て列挙する。<br>
	 * IEやExplorer等が含まれる。
	 * <p>
	 * <p>
	 * 当メソッドの使用前に{@link ComMgr#initialize()}が一度呼ばれていなければならない。<br>
	 * リスト内の各インスタンスの使用終了後は、必ず個々に{@link #dispose()}を呼び出してインスタンスを破棄すること。
	 * </p>
	 * 
	 * @return {@link IWebBrowser}のリスト（必ずnull以外）
	 * @see ComPtr#dispose(List, boolean)
	 * @see ComPtr#dispose(List, ComPtr, boolean)
	 */
	public static List enumWebBrowser() {
		return IWebBrowser.enumWebBrowser();
	}

	/**
	 * WebBrowser(IE)列挙.
	 * <p>
	 * 存在するIEのオブジェクトを全て列挙する。<br>
	 * 厳密にはHtmlDocumentを持つウィンドウなので、IE以外でそういうオブジェクトがあればそれも含まれる。
	 * </p>
	 * <p>
	 * 当メソッドの使用前に{@link ComMgr#initialize()}が一度呼ばれていなければならない。<br>
	 * リスト内の各インスタンスの使用終了後は、必ず個々に{@link #dispose()}を呼び出してインスタンスを破棄すること。
	 * </p>
	 * 
	 * @return {@link IWebBrowser}のリスト（必ずnull以外）
	 * @see ComPtr#dispose(List, boolean)
	 * @see ComPtr#dispose(List, ComPtr, boolean)
	 */
	public static List enumIEWebBrowser() {
		List wbs = IWebBrowser.enumWebBrowser();
		if (wbs.size() <= 0) {
			return new ArrayList(0);
		}

		List ret = new ArrayList(wbs.size());
		for (Iterator i = wbs.iterator(); i.hasNext();) {
			IWebBrowser wb = (IWebBrowser) i.next();
			boolean use = false;
			IHTMLDocument doc = null;
			try {
				doc = wb.getDocument();
				long hwnd = wb.getHWND();
				if (doc != null && hwnd != 0) {
					ret.add(wb);
					use = true;
				}
			} catch (RuntimeException e) {
				// e.printStackTrace();
			} finally {
				if (doc != null)
					doc.dispose();
				if (!use)
					wb.dispose();
			}
		}
		return ret;
	}

	//
	// インスタンス関連
	//

	protected IWebBrowser wb;

	protected boolean debug = false;

	protected boolean rethrow = false;

	protected Exception ex = null;

	protected IERobot(IWebBrowser wb) {
		this.wb = wb;
	}

	/**
	 * WebBrowserコントロール取得.
	 * <p>
	 * ここで取得したWebBrowserは{@link IWebBrowser#dispose()}で破棄しないこと。
	 * </p>
	 * 
	 * @return WebBrowserコントロール
	 */
	public IWebBrowser getWebBrowser() {
		return wb;
	}

	/**
	 * デバッグモード設定.
	 * 
	 * @param b
	 *            trueの場合、例外発生時にスタックトレースを標準エラーに出力する。
	 */
	public void setDebugMode(boolean b) {
		debug = b;
	}

	/**
	 * 例外スロー設定.
	 * 
	 * @param b
	 *            trueの場合、各メソッドで発生した例外をそのままスローする。
	 */
	public void setRethrow(boolean b) {
		rethrow = b;
	}

	/**
	 * 発生例外取得.
	 * 
	 * @return 最後に呼び出したメソッドで発生した例外（無い場合、null）
	 */
	public Exception getException() {
		return ex;
	}

	protected void doFail(RuntimeException e) {
		ex = e;
		if (debug)
			e.printStackTrace();
		if (rethrow)
			throw e;
	}

	// 一時保存

	private IHTMLDocument tempDoc = null;

	private List formList = null;

	private Map idMap = null;

	private Map nameMap = null;

	private Map tagMap = null;

	private List linkList = null;

	protected void disposeTempElems() {
		ComPtr.dispose(formList);
		formList = null;

		if (idMap != null) {
			for (Iterator i = idMap.keySet().iterator(); i.hasNext();) {
				Object key = i.next();
				ComPtr c = (ComPtr) idMap.get(key);
				ComPtr.dispose(c);
			}
		}
		idMap = null;

		if (nameMap != null) {
			for (Iterator i = nameMap.keySet().iterator(); i.hasNext();) {
				Object key = i.next();
				List list = (List) nameMap.get(key);
				ComPtr.dispose(list);
			}
		}
		nameMap = null;

		if (tagMap != null) {
			for (Iterator i = tagMap.keySet().iterator(); i.hasNext();) {
				Object key = i.next();
				List list = (List) tagMap.get(key);
				ComPtr.dispose(list);
			}
		}
		tagMap = null;

		ComPtr.dispose(linkList);
		linkList = null;
	}

	protected void disposeTemp() {
		ComPtr.dispose(tempDoc);
		tempDoc = null;

		disposeTempElems();
	}

	/**
	 * IERobot破棄.
	 * <p>
	 * 当該インスタンスを破棄する。<br>
	 * 内部で保持していたWebBrowserコントロールも破棄される。
	 * </p>
	 */
	public void dispose() {
		ex = null;
		disposeTemp();

		if (wb != null) {
			try {
				ComPtr.dispose(wb);
			} catch (RuntimeException e) {
				doFail(e);
			} finally {
				wb = null;
			}
		}
	}

	/**
	 * IERobot破棄.
	 * <p>
	 * 当該インスタンスを破棄する。<br>
	 * 内部で保持していたWebBrowserコントロールは破棄しない。
	 * </p>
	 * 
	 * @return 保持していたWebBrowserコントロール
	 */
	public IWebBrowser detach() {
		ex = null;
		disposeTemp();

		IWebBrowser ret = wb;
		wb = null;
		return ret;
	}

	//
	// ナビゲート関連
	//

	/**
	 * 指定位置へ移動する.
	 * <p>
	 * 当メソッドを実行すると、当クラスから直接取得したCOMオブジェクトは{@link ComPtr#dispose()}される。
	 * </p>
	 * 
	 * @param url
	 *            URL
	 * @return 成功した場合、true<br>
	 *         失敗した場合、false／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 */
	public boolean navigate(String url) {
		ex = null;
		disposeTemp();
		try {
			wb.navigate(url);
			return true;
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * 指定位置へ移動する.
	 * <p>
	 * 当メソッドを実行すると、当クラスから直接取得したCOMオブジェクトは{@link ComPtr#dispose()}される。
	 * </p>
	 * 
	 * @param url
	 *            URL
	 * @param flags
	 *            フラグ
	 * @param targetFrameName
	 *            ターゲットのフレーム名
	 * @return 成功した場合、true<br>
	 *         失敗した場合、false／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 */
	public boolean navigate(String url, String flags, String targetFrameName) {
		ex = null;
		disposeTemp();
		try {
			wb.navigate(url, flags, targetFrameName);
			return true;
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * 指定位置へ移動する.
	 * <p>
	 * 当メソッドを実行すると、当クラスから直接取得したCOMオブジェクトは{@link ComPtr#dispose()}される。
	 * </p>
	 * 
	 * @param a
	 *            アンカー要素
	 * @return 成功した場合、true<br>
	 *         失敗した場合、false／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 */
	public boolean navigate(IHTMLAnchorElement a) {
		ex = null;
		String url = null;
		String tar = null;
		try {
			url = a.getHref();
			tar = a.getTarget();
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
		if (tar == null) {
			return navigate(url);
		} else {
			return navigate(url, null, tar);
		}
	}

	/**
	 * 履歴の前ページへ戻る.
	 * <p>
	 * 当メソッドを実行すると、当クラスから直接取得したCOMオブジェクトは{@link ComPtr#dispose()}される。
	 * </p>
	 * 
	 * @return 成功した場合、true<br>
	 *         失敗した場合、false／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 */
	public boolean goBack() {
		ex = null;
		disposeTemp();
		try {
			wb.goBack();
			return true;
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * 履歴の次ページへ行く.
	 * <p>
	 * 当メソッドを実行すると、当クラスから直接取得したCOMオブジェクトは{@link ComPtr#dispose()}される。
	 * </p>
	 * 
	 * @return 成功した場合、true<br>
	 *         失敗した場合、false／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 */
	public boolean goForward() {
		ex = null;
		disposeTemp();
		try {
			wb.goForward();
			return true;
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * ホームへ移動する.
	 * <p>
	 * 当メソッドを実行すると、当クラスから直接取得したCOMオブジェクトは{@link ComPtr#dispose()}される。
	 * </p>
	 * 
	 * @return 成功した場合、true<br>
	 *         失敗した場合、false／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 */
	public boolean goHome() {
		ex = null;
		disposeTemp();
		try {
			wb.goHome();
			return true;
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * 検索ページへ移動する.
	 * <p>
	 * 当メソッドを実行すると、当クラスから直接取得したCOMオブジェクトは{@link ComPtr#dispose()}される。
	 * </p>
	 * 
	 * @return 成功した場合、true<br>
	 *         失敗した場合、false／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 */
	public boolean goSearch() {
		ex = null;
		disposeTemp();
		try {
			wb.goSearch();
			return true;
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * 更新する.
	 * <p>
	 * 当メソッドを実行すると、当クラスから直接取得したCOMオブジェクトは{@link ComPtr#dispose()}される。
	 * </p>
	 * 
	 * @return 成功した場合、true<br>
	 *         失敗した場合、false／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 */
	public boolean refresh() {
		ex = null;
		disposeTemp();
		try {
			wb.refresh();
			return true;
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}

	}

	/**
	 * 停止する.
	 * <p>
	 * 当メソッドを実行すると、当クラスから直接取得したCOMオブジェクトは{@link ComPtr#dispose()}される。
	 * </p>
	 * 
	 * @return 成功した場合、true<br>
	 *         失敗した場合、false／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 */
	public boolean stop() {
		ex = null;
		disposeTemp();
		try {
			wb.stop();
			return true;
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}

	}

	/**
	 * HTMLを初期化する.
	 * <p>
	 * 保持しているHTMLドキュメントをクリアする。
	 * </p>
	 * <p>
	 * 当メソッドを実行すると、当クラスから直接取得したCOMオブジェクトは{@link ComPtr#dispose()}される。
	 * </p>
	 * 
	 * @return 成功した場合、true<br>
	 *         失敗した場合、false／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 */
	public boolean clearHtml() {
		IHTMLDocument doc = getDocument();
		if (doc != null) {
			try {
				doc.clear();
				disposeTempElems();
				return true;
			} catch (RuntimeException e) {
				doFail(e);
			}
		}
		return false;
	}

	/**
	 * HTMLを書き込む.
	 * <p>
	 * 保持しているHTMLドキュメントにHTMLを出力する。（改行なし）
	 * </p>
	 * <p>
	 * 当メソッドを実行すると、当クラスから直接取得したCOMオブジェクトは{@link ComPtr#dispose()}される。
	 * </p>
	 * 
	 * @param html
	 *            HTML
	 * @return 成功した場合、true<br>
	 *         失敗した場合、false／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 * @see #closeHtml()
	 */
	public boolean writeHtml(String html) {
		IHTMLDocument doc = getDocument();
		if (doc != null) {
			try {
				doc.write(html);
				disposeTempElems();
				return true;
			} catch (RuntimeException e) {
				doFail(e);
			}
		}
		return false;
	}

	/**
	 * HTMLを書き込む.
	 * <p>
	 * 保持しているHTMLドキュメントにHTMLを出力する。（改行あり）
	 * </p>
	 * <p>
	 * 当メソッドを実行すると、当クラスから直接取得したCOMオブジェクトは{@link ComPtr#dispose()}される。
	 * </p>
	 * 
	 * @param html
	 *            HTML
	 * @return 成功した場合、true<br>
	 *         失敗した場合、false／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 * @see #closeHtml()
	 */
	public boolean writelnHtml(String html) {
		IHTMLDocument doc = getDocument();
		if (doc != null) {
			try {
				doc.writeln(html);
				disposeTempElems();
				return true;
			} catch (RuntimeException e) {
				doFail(e);
			}
		}
		return false;
	}

	/**
	 * HTMLをクローズする.
	 * <p>
	 * 保持しているHTMLドキュメントへのHTML出力を終了する。
	 * </p>
	 * <p>
	 * 当メソッドを実行すると、当クラスから直接取得したCOMオブジェクトは{@link ComPtr#dispose()}される。
	 * </p>
	 * 
	 * @return 成功した場合、true<br>
	 *         失敗した場合、false／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 */
	public boolean closeHtml() {
		IHTMLDocument doc = getDocument();
		if (doc != null) {
			try {
				doc.close();
				disposeTempElems();
				return true;
			} catch (RuntimeException e) {
				doFail(e);
			}
		}
		return false;
	}

	/**
	 * 終了する.
	 * <p>
	 * 当メソッドを実行すると、当クラスから直接取得したCOMオブジェクトは{@link ComPtr#dispose()}される。
	 * </p>
	 * 
	 * @return 成功した場合、true<br>
	 *         失敗した場合、false／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 */
	public boolean quit() {
		ex = null;
		disposeTemp();
		try {
			wb.quit();
			return true;
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * ビジーかどうかを返す.
	 * 
	 * @return ビジーの場合、true（そうでない場合、false）<br>
	 *         失敗した場合、false／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 */
	public boolean isBusy() {
		ex = null;
		try {
			return wb.getBusy();
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * 現在の状態を返す.
	 * 
	 * @return 現在の状態<br>
	 *         失敗した場合、-1／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 * @see OcIdlConst#READYSTATE_COMPLETE
	 */
	public int getReadyState() {
		ex = null;
		try {
			return wb.getReadyState();
		} catch (RuntimeException e) {
			doFail(e);
			return -1;
		}
	}

	/**
	 * ドキュメント完了待ち.
	 * <p>
	 * ドキュメントの読み込みが完了するまで待つ。
	 * </p>
	 * 
	 * @param time
	 *            ポーリング間隔[ミリ秒]
	 * @param count
	 *            ポーリング回数
	 * @return true：読み込み完了<br>
	 *         タイムアウトまたは失敗した場合、false／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 */
	public boolean waitDocumentComplete(int time, int count) {
		ex = null;
		try {
			if (!wb.getBusy() && wb.getReadyState() == READYSTATE_COMPLETE)
				return true;

			for (int i = 1; i < count; i++) {
				delay(time);
				if (!wb.getBusy() && wb.getReadyState() == READYSTATE_COMPLETE)
					return true;
			}
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}

		// タイムアウト
		if (debug || rethrow) {
			TimeoutException e = new TimeoutException(0, time * count);
			doFail(e);
		}
		return false;
	}

	/**
	 * ロケーション名取得.
	 * 
	 * @return ロケーションの名前<br>
	 *         失敗した場合、null／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 */
	public String getLocationName() {
		ex = null;
		try {
			return wb.getLocationName();
		} catch (RuntimeException e) {
			doFail(e);
			return null;
		}
	}

	/**
	 * URL取得.
	 * 
	 * @return URL<br>
	 *         失敗した場合、null／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 */
	public String getLocationURL() {
		ex = null;
		try {
			return wb.getLocationURL();
		} catch (RuntimeException e) {
			doFail(e);
			return null;
		}
	}

	/**
	 * タイトル取得.
	 * 
	 * @return タイトル<br>
	 *         失敗した場合、null／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 */
	public String getTitle() {
		IHTMLDocument doc = getDocument();
		if (doc != null) {
			try {
				return doc.getTitle();
			} catch (RuntimeException e) {
				doFail(e);
			}
		}
		return null;
	}

	//
	// モジュール情報関連
	//

	/**
	 * ドキュメントオブジェクト名取得.
	 * 
	 * @return ドキュメントオブジェクト名<br>
	 *         失敗した場合、null／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 */
	public String getType() {
		ex = null;
		try {
			return wb.getType();
		} catch (RuntimeException e) {
			doFail(e);
			return null;
		}
	}

	/**
	 * モジュール名取得.
	 * 
	 * @return 名称<br>
	 *         失敗した場合、null／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 */
	public String getModuleName() {
		ex = null;
		try {
			return wb.getName();
		} catch (RuntimeException e) {
			doFail(e);
			return null;
		}
	}

	/**
	 * 実行モジュールフルパス取得.
	 * 
	 * @return フルパス名<br>
	 *         失敗した場合、null／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 */
	public String getModuleFullName() {
		ex = null;
		try {
			return wb.getFullName();
		} catch (RuntimeException e) {
			doFail(e);
			return null;
		}
	}

	/**
	 * 実行モジュールパス取得.
	 * 
	 * @return パス<br>
	 *         失敗した場合、null／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 */
	public String getModulePath() {
		ex = null;
		try {
			return wb.getPath();
		} catch (RuntimeException e) {
			doFail(e);
			return null;
		}
	}

	//
	// ウィンドウ関連
	//

	/**
	 * 位置設定.
	 * 
	 * @param x
	 *            X
	 * @param y
	 *            Y
	 * @return 成功した場合、true<br>
	 *         失敗した場合、false／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 */
	public boolean setLocation(int x, int y) {
		ex = null;
		try {
			wb.setLocation(x, y);
			return true;
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * 位置設定.
	 * 
	 * @param pt
	 *            位置
	 * @return 成功した場合、true<br>
	 *         失敗した場合、false／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 */
	public boolean setLocation(Point pt) {
		ex = null;
		try {
			wb.setLocation(pt.x, pt.y);
			return true;
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * 位置取得.
	 * 
	 * @return 位置<br>
	 *         失敗した場合、null／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 */
	public Point getLocation() {
		ex = null;
		try {
			return wb.getLocation();
		} catch (RuntimeException e) {
			doFail(e);
			return null;
		}
	}

	/**
	 * サイズ設定.
	 * 
	 * @param cx
	 *            幅
	 * @param cy
	 *            高さ
	 * @return 成功した場合、true<br>
	 *         失敗した場合、false／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 */
	public boolean setSize(int cx, int cy) {
		ex = null;
		try {
			wb.setSize(cx, cy);
			return true;
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * サイズ設定.
	 * 
	 * @param sz
	 *            サイズ
	 * @return 成功した場合、true<br>
	 *         失敗した場合、false／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 */
	public boolean setSize(Dimension sz) {
		ex = null;
		try {
			wb.setSize(sz.width, sz.height);
			return true;
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * サイズ取得.
	 * 
	 * @return サイズ<br>
	 *         失敗した場合、null／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 */
	public Dimension getSize() {
		ex = null;
		try {
			return wb.getSize();
		} catch (RuntimeException e) {
			doFail(e);
			return null;
		}
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
	 * @return 成功した場合、true<br>
	 *         失敗した場合、false／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 */
	public boolean setBounds(int x, int y, int cx, int cy) {
		ex = null;
		try {
			wb.setBounds(x, y, cx, cy);
			return true;
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * 位置サイズ設定.
	 * 
	 * @param pt
	 *            位置
	 * @param sz
	 *            サイズ
	 * @return 成功した場合、true<br>
	 *         失敗した場合、false／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 */
	public boolean setBounds(Point pt, Dimension sz) {
		ex = null;
		try {
			wb.setBounds(pt.x, pt.y, sz.width, sz.height);
			return true;
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * 位置サイズ設定.
	 * 
	 * @param rect
	 *            矩形
	 * @return 成功した場合、true<br>
	 *         失敗した場合、false／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 */
	public boolean setBounds(Rectangle rect) {
		ex = null;
		try {
			wb.setBounds(rect.x, rect.y, rect.width, rect.height);
			return true;
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * 位置サイズ取得.
	 * 
	 * @return 矩形<br>
	 *         失敗した場合、null／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 */
	public Rectangle getBounds() {
		ex = null;
		try {
			return wb.getBounds();
		} catch (RuntimeException e) {
			doFail(e);
			return null;
		}
	}

	/**
	 * サイズ変更可否取得.
	 * 
	 * @return サイズ変更可能な場合、true（不可の場合、false）<br>
	 *         失敗した場合、false／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 */
	public boolean isResizable() {
		ex = null;
		try {
			return wb.getResizable();
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * サイズ変更可否設定.
	 * 
	 * @param b
	 *            サイズ変更可否
	 * @return 成功した場合、true<br>
	 *         失敗した場合、false／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 */
	public boolean setResizable(boolean b) {
		ex = null;
		try {
			wb.setResizable(b);
			return true;
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * 表示状態取得.
	 * 
	 * @return 表示されている場合、true（非表示の場合、false）<br>
	 *         失敗した場合、false／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 */
	public boolean isVisible() {
		ex = null;
		try {
			return wb.getVisible();
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * 表示状態設定.
	 * 
	 * @param b
	 *            表示状態
	 * @return 成功した場合、true<br>
	 *         失敗した場合、false／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 */
	public boolean setVisible(boolean b) {
		ex = null;
		try {
			wb.setVisible(b);
			return true;
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * 最前面移動.
	 * 
	 * @return 成功した場合、true<br>
	 *         失敗した場合、false／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 */
	public boolean setForeground() {
		ex = null;
		JWnd wnd = getWnd();
		if (wnd == null) {
			return false;
		}
		try {
			return wnd.SetForegroundWindow();
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * HWND取得.
	 * 
	 * @return JWnd<br>
	 *         失敗した場合、null／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 */
	public JWnd getWnd() {
		ex = null;
		try {
			long hwnd = wb.getHWND();
			if (hwnd == 0)
				return null;
			return new JWnd(hwnd);
		} catch (RuntimeException e) {
			doFail(e);
			return null;
		}
	}

	/**
	 * クライアント領域HWND取得.
	 * <p>
	 * ウィンドウクラス名が「Internet Explorer_Server」であるウィンドウを返す。
	 * </p>
	 * 
	 * @return JWnd（見つからなかった場合、null）<br>
	 *         失敗した場合、null／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 */
	public JWnd getCliendWnd() {
		ex = null;
		try {
			long hwnd = wb.getHWND();
			if (hwnd == 0)
				return null;
			List list = new LinkedList();
			WinUser.EnumChildWindows(hwnd, list);
			for (Iterator i = list.iterator(); i.hasNext();) {
				try {
					JWnd w = (JWnd) i.next();
					if ("Internet Explorer_Server".equals(w.getClassName())) {
						return w;
					}
				} catch (RuntimeException e) {
				}
			}
		} catch (RuntimeException e) {
			doFail(e);
		}
		return null;
	}

	//
	// ウィンドウパーツ関連
	//

	/**
	 * ステータスバー表示状態取得.
	 * 
	 * @return 表示されている場合、true（非表示の場合、false）<br>
	 *         失敗した場合、false／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 */
	public boolean isStatusBar() {
		ex = null;
		try {
			return wb.getStatusBar();
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * ステータスバー表示状態設定.
	 * 
	 * @param b
	 *            表示状態
	 * @return 成功した場合、true<br>
	 *         失敗した場合、false／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 */
	public boolean setStatusBar(boolean b) {
		ex = null;
		try {
			wb.setStatusBar(b);
			return true;
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * ステータスバー表示文字列取得.
	 * <p>
	 * 取得した文字列は、末尾の文字がなぜか乱れている…。
	 * </p>
	 * 
	 * @return 文字列<br>
	 *         失敗した場合、null／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 */
	public String getStatusText() {
		ex = null;
		try {
			return wb.getStatusText();
		} catch (RuntimeException e) {
			doFail(e);
			return null;
		}
	}

	/**
	 * ステータスバー文字列表示.
	 * 
	 * @param text
	 *            文字列
	 * @return 成功した場合、true<br>
	 *         失敗した場合、false／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 */
	public boolean setStatusText(String text) {
		ex = null;
		try {
			wb.setStatusText(text);
			return true;
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * ツールバー表示状態取得.
	 * 
	 * @return 表示されている場合、true（非表示の場合、false）<br>
	 *         失敗した場合、false／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 */
	public boolean isToolBar() {
		ex = null;
		try {
			return wb.getToolBar() != 0;
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * ツールバー表示状態設定.
	 * 
	 * @param b
	 *            表示状態
	 * @return 成功した場合、true<br>
	 *         失敗した場合、false／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 */
	public boolean setToolBar(boolean b) {
		ex = null;
		try {
			wb.setToolBar(b ? 1 : 0);
			return true;
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * メニューバー表示状態取得.
	 * 
	 * @return 表示されている場合、true（非表示の場合、false）<br>
	 *         失敗した場合、false／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 */
	public boolean isMenuBar() {
		ex = null;
		try {
			return wb.getMenuBar();
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * メニューバー表示状態設定.
	 * 
	 * @param b
	 *            表示状態
	 * @return 成功した場合、true<br>
	 *         失敗した場合、false／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 */
	public boolean setMenuBar(boolean b) {
		ex = null;
		try {
			wb.setMenuBar(b);
			return true;
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * フルスクリーン状態取得.
	 * 
	 * @return フルスクリーンの場合、true（そうでない場合、false）<br>
	 *         失敗した場合、false／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 */
	public boolean isFullScreen() {
		ex = null;
		try {
			return wb.getFullScreen();
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * フルスクリーン状態設定.
	 * 
	 * @param b
	 *            フルスクリーン状態
	 * @return 成功した場合、true<br>
	 *         失敗した場合、false／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 */
	public boolean setFullScreen(boolean b) {
		ex = null;
		try {
			wb.setFullScreen(b);
			return true;
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * オフライン状態取得.
	 * <p>
	 * ウェブブラウザーがオフライン状態で動作しているのかどうかを返す。
	 * </p>
	 * 
	 * @return オフラインの場合、true（オンラインの場合、false）<br>
	 *         失敗した場合、false／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 */
	public boolean isOffline() {
		ex = null;
		try {
			return wb.getOffline();
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * オフライン状態設定.
	 * 
	 * @param b
	 *            オフライン状態
	 * @return 成功した場合、true<br>
	 *         失敗した場合、false／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 */
	public boolean setOffline(boolean b) {
		ex = null;
		try {
			wb.setOffline(b);
			return true;
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * ダイアログボックス表示可否取得.
	 * 
	 * @return ダイアログを表示できない場合、true（表示できる場合、false）<br>
	 *         失敗した場合、false／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 */
	public boolean isSilent() {
		ex = null;
		try {
			return wb.getSilent();
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * ダイアログボックス表示可否設定.
	 * <p>
	 * エラー発生時などのダイアログを表示するかどうかを設定する。
	 * </p>
	 * 
	 * @param b
	 *            表示可否
	 * @return 成功した場合、true<br>
	 *         失敗した場合、false／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 */
	public boolean setSilent(boolean b) {
		ex = null;
		try {
			wb.setSilent(b);
			return true;
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * トップレベルブラウザー登録状態取得.
	 * <p>
	 * ウェブブラウザーがターゲット名解決のトップレベルブラウザーとして登録されているかどうかを返す。
	 * </p>
	 * 
	 * @return トップレベルブラウザーの場合、true（そうでない場合、false）<br>
	 *         失敗した場合、false／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 */
	public boolean isRegisterAsBrowser() {
		ex = null;
		try {
			return wb.getRegisterAsBrowser();
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * トップレベルブラウザー登録状態設定.
	 * 
	 * @param b
	 *            登録状態
	 * @return 成功した場合、true<br>
	 *         失敗した場合、false／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 */
	public boolean setRegisterAsBrowser(boolean b) {
		ex = null;
		try {
			wb.setRegisterAsBrowser(b);
			return true;
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * ドロップターゲット登録状態取得.
	 * <p>
	 * ウェブブラウザーがドロップを受け付けるかどうかを返す。
	 * </p>
	 * 
	 * @return ドロップを受け付ける場合、true（受け付けない場合、false）<br>
	 *         失敗した場合、false／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 */
	public boolean isRegisterAsDropTarget() {
		ex = null;
		try {
			return wb.getRegisterAsDropTarget();
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * ドロップターゲット登録状態取得.
	 * 
	 * @param b
	 *            登録状態
	 * @return 成功した場合、true<br>
	 *         失敗した場合、false／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 */
	public boolean setRegisterAsDropTarget(boolean b) {
		ex = null;
		try {
			wb.setRegisterAsDropTarget(b);
			return true;
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * シアター状態取得.
	 * 
	 * @return シアターモードの場合、true（そうでない場合、false）<br>
	 *         失敗した場合、false／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 */
	public boolean isTheaterMode() {
		ex = null;
		try {
			return wb.getTheaterMode();
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * シアター状態設定.
	 * 
	 * @param b
	 *            シアター状態
	 * @return 成功した場合、true<br>
	 *         失敗した場合、false／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 */
	public boolean setTheaterMode(boolean b) {
		ex = null;
		try {
			wb.setTheaterMode(b);
			return true;
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * アドレスバー表示状態取得.
	 * 
	 * @return 表示されている場合、true（非表示の場合、false）<br>
	 *         失敗した場合、false／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 */
	public boolean isAddressBar() {
		ex = null;
		try {
			return wb.getAddressBar();
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * アドレスバー表示状態設定.
	 * 
	 * @param b
	 *            表示状態
	 * @return 成功した場合、true<br>
	 *         失敗した場合、false／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 */
	public boolean setAddressBar(boolean b) {
		ex = null;
		try {
			wb.setAddressBar(b);
			return true;
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	//
	// ファイルダウンロード関連
	//

	/**
	 * ファイルダウンロード拒否状態取得.
	 * 
	 * @return ファイルダウンロードが拒否されている場合、true<br>
	 *         失敗した場合、false／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 */
	public boolean isBlockingFileDownload() {
		JWnd w = getBlockingFileDownloadWnd();
		if (w != null) {
			return w.IsWindowVisible();
		}
		return false;
	}

	/**
	 * ファイルダウンロード可否設定.
	 * <p>
	 * ファイルダウンロードを拒否する情報が表示されているときに、その可否を設定する。
	 * </p>
	 * 
	 * @param b
	 *            true：ダウンロード許可<br>
	 *            false：ダウンロード不可 にしたいのだが、まだ未完成
	 * @return 成功した場合、true<br>
	 *         失敗した場合、false／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 */
	public boolean setBlockingFileDownload(boolean b) {
		JWnd w = getBlockingFileDownloadWnd();
		if (w == null) {
			return false;
		}
		if (b) {
			w.PostMessage(WinUserConst.BM_CLICK, 0, 0);
			JWnd popup = null;
			while (popup == null) {
				delay(100);
				popup = JWnd.FindWindow("#32768", null);
			}
			popup.SendMessage(WinUserConst.WM_CHAR, 'D', 0);
			disposeTemp();
			return true;
		} else {
			if (false) {
				w.SetFocus();
				delay(1000);
				Rectangle rt = w.getClientRect();
				int x = rt.width - 10, y = 8;
				int lpos = (y << 16) | x;
				w.PostMessage(WinUserConst.WM_MOUSEMOVE, 0, lpos);
				Point pt = new Point(x, y);
				w.ClientToScreen(pt);
				w
						.SendMessage(WinUserConst.WM_NCHITTEST, 0, (pt.y << 16)
								| pt.x);
				w.SendMessage(WinUserConst.WM_SETCURSOR, (int) w.GetSafeHwnd(),
						(WinUserConst.WM_MOUSEMOVE << 16) | 1);
				w.PostMessage(WinUserConst.WM_LBUTTONDOWN,
						WinUserConst.MK_LBUTTON, lpos);
				w.PostMessage(WinUserConst.WM_LBUTTONUP, 0, lpos);
				// w.PostMessage(WinUserConst.WM_MOUSELEAVE, 0, 0);
			}
			throw new RuntimeException("キャンセルは未完成。というか上手くいかない＞＜");
		}
	}

	/**
	 * ファイルダウンロード拒否ウィンドウ取得.
	 * <p>
	 * ファイルをダウンロードしようとした際に、InternetExplorer6(SP2)によってダウンロードがブロックされることがある。そのメッセージを表示しているウィンドウを返す。
	 * </p>
	 * 
	 * @return JWnd（無い場合、null）<br>
	 *         失敗した場合、null／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 */
	public JWnd getBlockingFileDownloadWnd() {
		JWnd wnd = getWnd();
		if (wnd == null) {
			return null;
		}
		List list = wnd.enumChildWindows();
		if (list == null) {
			return null;
		}
		for (int i = 0; i < list.size(); i++) {
			JWnd w = (JWnd) list.get(i);
			if (w != null) {
				String name = w.getClassName();
				String text = w.GetWindowText();
				if ("Button".equals(name) && text != null
						&& text.startsWith("セキュリティ保護のため、")) {
					return w;
				}
			}
		}
		return null;
	}

	/**
	 * ファイルダウンロードダイアログ取得.
	 * 
	 * @return {@link FileDownloadProgressDialog}のリスト（必ずnull以外）
	 * @see FileDownloadDialogFactory
	 */
	public List getFileDownloadDialog() {
		FileDownloadDialogFactory factory = FileDownloadDialogFactory
				.getInstance();
		return factory.enumFileDownloadDialog();
	}

	//
	// フォーム関連
	//

	/**
	 * HTMLドキュメント取得.
	 * <p>
	 * 当メソッドにより返されたHTMLドキュメントはIERobotで破棄するので、ユーザーは破棄しないこと。
	 * </p>
	 * 
	 * @return HTMLドキュメント<br>
	 *         失敗した場合、null／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 */
	public IHTMLDocument getDocument() {
		ex = null;
		try {
			if (tempDoc == null) {
				tempDoc = wb.getDocument();
			}
		} catch (RuntimeException e) {
			doFail(e);
		}
		return tempDoc;
	}

	/**
	 * フォーム一覧取得.
	 * <p>
	 * 当メソッドにより返されたフォームはIERobotで破棄するので、ユーザーは破棄しないこと。
	 * </p>
	 * 
	 * @return {@link IHTMLFormElement}のリスト<br>
	 *         失敗した場合、null／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 */
	public List getFormList() {
		ex = null;
		if (formList == null) {
			IHTMLDocument doc = getDocument();
			if (doc != null) {
				IHTMLElementCollection c = null;
				try {
					c = doc.getForms();
					formList = c.list();
				} catch (RuntimeException e) {
					doFail(e);
				} finally {
					ComPtr.dispose(c);
				}
			}
		}
		return formList;
	}

	/**
	 * フォーム取得.
	 * <p>
	 * 当メソッドにより返されたフォームはIERobotで破棄するので、ユーザーは破棄しないこと。
	 * </p>
	 * 
	 * @param index
	 *            フォーム番号
	 * @return フォーム<br>
	 *         失敗した場合、null／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 */
	public IHTMLFormElement getForm(int index) {
		List list = getFormList();
		try {
			return (IHTMLFormElement) list.get(index);
		} catch (RuntimeException e) {
			doFail(e);
		}
		return null;
	}

	/**
	 * フォーム取得.
	 * <p>
	 * 当メソッドにより返されたフォームはIERobotで破棄するので、ユーザーは破棄しないこと。
	 * </p>
	 * 
	 * @param name
	 *            フォーム名
	 * @return フォーム（見つからない場合、null）<br>
	 *         失敗した場合、null／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 */
	public IHTMLFormElement getForm(String name) {
		List list = getFormList();
		try {
			for (int i = 0; i < list.size(); i++) {
				IHTMLFormElement f = (IHTMLFormElement) list.get(i);
				if (name.equals(f.getName())) {
					return f;
				}
			}
		} catch (RuntimeException e) {
			doFail(e);
		}
		return null;
	}

	/**
	 * HTML要素取得.
	 * <p>
	 * 当メソッドにより返されたHTML要素はIERobotで破棄するので、ユーザーは破棄しないこと。
	 * </p>
	 * 
	 * @param id
	 *            HTMLのタグに付けられているID
	 * @return HTML要素<br>
	 *         失敗した場合、null／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 */
	public IHTMLElement getElementById(String id) {
		if (idMap == null) {
			idMap = new HashMap();
		}
		IHTMLElement el = (IHTMLElement) idMap.get(id);
		if (el == null) {
			IHTMLDocument doc = getDocument();
			if (doc != null) {
				try {
					el = doc.getElementById(id);
					idMap.put(id, el);
				} catch (RuntimeException e) {
					doFail(e);
				}
			}
		}
		return el;
	}

	/**
	 * HTML要素取得.
	 * <p>
	 * 当メソッドにより返されたHTML要素はIERobotで破棄するので、ユーザーは破棄しないこと。
	 * </p>
	 * 
	 * @param name
	 *            名称
	 * @return {@link IHTMLElement}のリスト<br>
	 *         失敗した場合、null／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 */
	public List getElementListByName(String name) {
		if (nameMap == null) {
			nameMap = new HashMap();
		}
		List list = (List) nameMap.get(name);
		if (list == null) {
			IHTMLDocument doc = getDocument();
			if (doc != null) {
				IHTMLElementCollection c = null;
				try {
					c = doc.getElementsByName(name);
					list = c.list();
					nameMap.put(name, list);
				} catch (RuntimeException e) {
					doFail(e);
				} finally {
					ComPtr.dispose(c);
				}
			}
		}
		return list;
	}

	/**
	 * HTML要素取得.
	 * <p>
	 * 当メソッドにより返されたHTML要素はIERobotで破棄するので、ユーザーは破棄しないこと。
	 * </p>
	 * 
	 * @param name
	 *            名前
	 * @param index
	 *            番号（同名のタグが複数ある場合の番号）
	 * @return HTML要素<br>
	 *         失敗した場合、null／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 */
	public IHTMLElement getElementByName(String name, int index) {
		List list = getElementListByName(name);
		if (list != null) {
			try {
				return (IHTMLElement) list.get(index);
			} catch (RuntimeException e) {
				doFail(e);
			}
		}
		return null;
	}

	/**
	 * HTML要素一覧取得.
	 * <p>
	 * 当メソッドにより返されたHTML要素はIERobotで破棄するので、ユーザーは破棄しないこと。
	 * </p>
	 * 
	 * @param tag
	 *            タグ名
	 * @return {@link IHTMLElement}のリスト<br>
	 *         失敗した場合、null／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 */
	public List getElementListByTagName(String tag) {
		if (tagMap == null) {
			tagMap = new HashMap();
		}
		tag = tag.toUpperCase();
		List list = (List) tagMap.get(tag);
		if (list == null) {
			IHTMLDocument doc = getDocument();
			if (doc != null) {
				IHTMLElementCollection c = null;
				try {
					c = doc.getElementsByTagName(tag);
					list = c.list();
					tagMap.put(tag, list);
				} catch (RuntimeException e) {
					doFail(e);
				} finally {
					ComPtr.dispose(c);
				}
			}
		}
		return list;
	}

	/**
	 * リンク一覧取得.
	 * <p>
	 * 当メソッドにより返されたHTML要素はIERobotで破棄するので、ユーザーは破棄しないこと。
	 * </p>
	 * 
	 * @return {@link IHTMLAnchorElement}のリスト<br>
	 *         失敗した場合、null／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 */
	public List getLinkList() {
		ex = null;
		if (linkList == null) {
			IHTMLDocument doc = getDocument();
			if (doc != null) {
				IHTMLElementCollection c = null;
				try {
					c = doc.getLinks();
					linkList = c.list();
				} catch (RuntimeException e) {
					doFail(e);
				} finally {
					ComPtr.dispose(c);
				}
			}
		}
		return linkList;
	}

	/**
	 * テキストユーティリティー取得.
	 * 
	 * @param id
	 *            ID
	 * @return inputユーティリティー
	 */
	public IHTMLInputValueUtil getInputById(String id) {
		IHTMLElement e = getElementById(id);
		if (e instanceof IHTMLInputElement) {
			return new IHTMLInputValueUtil(this, (IHTMLInputElement) e);
		}
		return null;
	}

	/**
	 * テキストユーティリティー取得.
	 * 
	 * @param name
	 *            名前
	 * @param index
	 *            index
	 * @return inputユーティリティー
	 */
	public IHTMLInputValueUtil getInputByName(String name, int index) {
		IHTMLElement e = getElementByName(name, index);
		if (e instanceof IHTMLInputElement) {
			return new IHTMLInputValueUtil(this, (IHTMLInputElement) e);
		}
		return null;
	}

	/**
	 * ラジオボタンユーティリティー取得.
	 * 
	 * @param name
	 *            名前
	 * @return radioユーティリティー
	 */
	public IHTMLInputRadioUtil getRadioByName(String name) {
		List list = getElementListByName(name);
		if (list == null) {
			return null;
		}
		return new IHTMLInputRadioUtil(list);
	}

	/**
	 * コンボボックス・リストボックス取得.
	 * 
	 * @param id
	 *            ID
	 * @return selectユーティリティー
	 */
	public IHTMLSelectUtil getSelectById(String id) {
		IHTMLElement e = getElementById(id);
		if (e instanceof IHTMLSelectElement) {
			return new IHTMLSelectUtil((IHTMLSelectElement) e);
		}
		return null;
	}

	/**
	 * コンボボックス・リストボックスユーティリティー取得.
	 * 
	 * @param name
	 *            名前
	 * @param index
	 *            index
	 * @return selectユーティリティー
	 */
	public IHTMLSelectUtil getSelectByName(String name, int index) {
		IHTMLElement e = getElementByName(name, index);
		if (e instanceof IHTMLSelectElement) {
			return new IHTMLSelectUtil((IHTMLSelectElement) e);
		}
		return null;
	}

	/**
	 * サブミット実行.
	 * <p>
	 * アクティブになっているHTML要素があれば、その要素が属しているフォームに対してサブミットする。<br>
	 * そうでない場合、先頭のフォームに対してサブミットする。
	 * </p>
	 * 
	 * @return 成功した場合、true<br>
	 *         失敗した場合、false／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 */
	public boolean submit() {
		IHTMLDocument doc = getDocument();
		IHTMLElement el = null;
		try {
			el = doc.getActiveElement();
			if (el != null) {
				IHTMLFormElement f;
				if (el instanceof IHTMLInputElement) {
					IHTMLInputElement in = (IHTMLInputElement) el;
					f = in.getForm();
					if (f != null) {
						f.submit();
						return true;
					}
				}

				IHTMLElement p = el.getParentElement();
				while (p != null) {
					if (p instanceof IHTMLFormElement) {
						f = (IHTMLFormElement) p;
						f.submit();
						return true;
					}
					p = p.getParentElement();
				}
			}

		} catch (RuntimeException e) {
			doFail(e);
			return false;
		} finally {
			ComPtr.dispose(el);
		}

		return submit(0);
	}

	/**
	 * サブミット実行.
	 * <p>
	 * 指定されたフォーム番号のフォームに対してサブミットする。
	 * </p>
	 * 
	 * @param index
	 *            フォーム番号
	 * @return 成功した場合、true<br>
	 *         失敗した場合、false／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 */
	public boolean submit(int index) {
		IHTMLFormElement f = getForm(index);
		if (f != null) {
			try {
				f.submit();
				return true;
			} catch (RuntimeException e) {
				doFail(e);
			}
		}
		return false;
	}

	/**
	 * サブミット実行.
	 * 
	 * @param name
	 *            フォーム名
	 * @return 成功した場合、true<br>
	 *         失敗した場合、false／もしくは例外をスロー
	 * @see #setRethrow(boolean)
	 */
	public boolean submit(String name) {
		IHTMLFormElement f = getForm(name);
		if (f != null) {
			try {
				f.submit();
				return true;
			} catch (RuntimeException e) {
				doFail(e);
			}
		}
		return false;
	}

}
