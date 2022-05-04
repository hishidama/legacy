package jp.hishidama.win32.mshtml;

import jp.hishidama.win32.com.ComPtr;
import jp.hishidama.win32.com.IDispatch;
import jp.hishidama.win32.com.Variant;
import jp.hishidama.win32.shdocvw.IWebBrowser;

/**
 * IHTMLDocument2～4クラス.
 * <p>
 * HTMLドキュメントを扱うクラスです。
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">ひしだま</a>
 * @since 2007.10.22
 * @version 2008.07.14
 */
public class IHTMLDocument extends IDispatch {

	protected IHTMLDocument(long ptr) {
		super(ptr);
	}

	public IDispatch getScript() {
		return Native.get_Script(getPtr());
	}

	/**
	 * 全HTML要素取得.
	 * 
	 * @return コレクション
	 */
	public IHTMLElementCollection getAll() {
		return Native.get_all(getPtr());
	}

	/**
	 * @see ComPtr#disposeChild()
	 * @deprecated
	 * @see #getAll()
	 */
	public IHTMLElementCollection getAll(boolean addChild) {
		return getAll();
	}

	/**
	 * ボディー要素取得.
	 * 
	 * @return HTML要素
	 */
	public IHTMLElement getBody() {
		return Native.get_body(getPtr());
	}

	/**
	 * @see ComPtr#disposeChild()
	 * @deprecated
	 * @see #getBody()
	 */
	public IHTMLElement getBody(boolean addChild) {
		return getBody();
	}

	/**
	 * アクティブ要素取得.
	 * 
	 * @return HTML要素
	 */
	public IHTMLElement getActiveElement() {
		return Native.get_activeElement(getPtr());
	}

	/**
	 * @see ComPtr#disposeChild()
	 * @deprecated
	 * @see #getActiveElement()
	 */
	public IHTMLElement getActiveElement(boolean addChild) {
		return getActiveElement();
	}

	/**
	 * 画像要素取得.
	 * 
	 * @return コレクション
	 */
	public IHTMLElementCollection getImages() {
		return Native.get_images(getPtr());
	}

	/**
	 * @see ComPtr#disposeChild()
	 * @deprecated
	 * @see #getImages()
	 */
	public IHTMLElementCollection getImages(boolean addChild) {
		return getImages();
	}

	/**
	 * リンク要素取得.
	 * 
	 * @return コレクション
	 */
	public IHTMLElementCollection getLinks() {
		return Native.get_links(getPtr());
	}

	/**
	 * @see ComPtr#disposeChild()
	 * @deprecated
	 * @see #getLinks()
	 */
	public IHTMLElementCollection getLinks(boolean addChild) {
		return getLinks();
	}

	/**
	 * フォーム取得.
	 * 
	 * @return コレクション
	 */
	public IHTMLElementCollection getForms() {
		return Native.get_forms(getPtr());
	}

	/**
	 * @see ComPtr#disposeChild()
	 * @deprecated
	 * @see #getForms()
	 */
	public IHTMLElementCollection getForms(boolean addChild) {
		return getForms();
	}

	/**
	 * アンカー要素取得.
	 * 
	 * @return コレクション
	 */
	public IHTMLElementCollection getAnchors() {
		return Native.get_anchors(getPtr());
	}

	/**
	 * @see ComPtr#disposeChild()
	 * @deprecated
	 * @see #getAnchors()
	 */
	public IHTMLElementCollection getAnchors(boolean addChild) {
		return getAnchors();
	}

	/**
	 * タイトル設定.
	 * 
	 * @param title
	 *            タイトル
	 */
	public void setTitle(String title) {
		Native.put_title(getPtr(), title);
	}

	/**
	 * タイトル取得.
	 * 
	 * @return タイトル
	 */
	public String getTitle() {
		return Native.get_title(getPtr());
	}

	/**
	 * ReadyState取得.
	 * 
	 * @return ReadyState
	 */
	public String getReadyState() {
		return Native.get_readyState(getPtr());
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

	public void setAlinkColor(Variant color) {
		Native.put_alinkColor(getPtr(), color);
	}

	public Variant getAlinkColor() {
		return Native.get_alinkColor(getPtr());
	}

	public void setBgColor(Variant color) {
		Native.put_bgColor(getPtr(), color);
	}

	public Variant getBgColor() {
		return Native.get_bgColor(getPtr());
	}

	public void setFgColor(Variant color) {
		Native.put_fgColor(getPtr(), color);
	}

	public Variant getFgColor() {
		return Native.get_fgColor(getPtr());
	}

	public void setLinkColor(Variant color) {
		Native.put_linkColor(getPtr(), color);
	}

	public Variant getLinkColor() {
		return Native.get_linkColor(getPtr());
	}

	public void setVlinkColor(Variant color) {
		Native.put_vlinkColor(getPtr(), color);
	}

	public Variant getVlinkColor() {
		return Native.get_vlinkColor(getPtr());
	}

	public String getReferrer() {
		return Native.get_referrer(getPtr());
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

	/**
	 * 最終更新日取得.
	 * 
	 * @return 日付
	 */
	public String getLastModified() {
		return Native.get_lastModified(getPtr());
	}

	/**
	 * URL設定.
	 * 
	 * @param url
	 *            URL
	 */
	public void setUrl(String url) {
		Native.put_url(getPtr(), url);
	}

	/**
	 * URL取得.
	 * 
	 * @return URL
	 */
	public String getUrl() {
		return Native.get_url(getPtr());
	}

	/**
	 * ドメイン設定.
	 * 
	 * @param domain
	 *            ドメイン
	 */
	public void setDomain(String domain) {
		Native.put_domain(getPtr(), domain);
	}

	/**
	 * ドメイン取得.
	 * 
	 * @return ドメイン
	 */
	public String getDomain() {
		return Native.get_domain(getPtr());
	}

	/**
	 * クッキー設定.
	 * 
	 * @param cookie
	 *            クッキー
	 */
	public void setCookie(String cookie) {
		Native.put_cookie(getPtr(), cookie);
	}

	/**
	 * クッキー取得.
	 * 
	 * @return クッキー
	 */
	public String getCookie() {
		return Native.get_cookie(getPtr());
	}

	/**
	 * 文字セット設定.
	 * 
	 * @param charset
	 *            キャラセット
	 */
	public void setCharset(String charset) {
		Native.put_charset(getPtr(), charset);
	}

	/**
	 * 文字セット取得.
	 * 
	 * @return キャラセット
	 */
	public String getCharset() {
		return Native.get_charset(getPtr());
	}

	/**
	 * デフォルト文字セット設定.
	 * 
	 * @param charset
	 *            キャラセット
	 */
	public void setDefaultCharset(String charset) {
		Native.put_defaultCharset(getPtr(), charset);
	}

	/**
	 * デフォルト文字セット設定.
	 * 
	 * @return キャラセット
	 */
	public String getDefaultCharset() {
		return Native.get_defaultCharset(getPtr());
	}

	/**
	 * MIMEタイプ取得.
	 * 
	 * @return MIMEタイプ
	 */
	public String getMimeType() {
		return Native.get_mimeType(getPtr());
	}

	/**
	 * ファイルサイズ取得.
	 * 
	 * @return ファイルサイズ
	 */
	public int getFileSize() {
		return Integer.parseInt(Native.get_fileSize(getPtr()));
	}

	/**
	 * ファイル作成日取得.
	 * 
	 * @return 日付
	 */
	public String getFileCreatedDate() {
		return Native.get_fileCreatedDate(getPtr());
	}

	/**
	 * ファイル変更日取得.
	 * 
	 * @return 日付
	 */
	public String getFileModifiedDate() {
		return Native.get_fileModifiedDate(getPtr());
	}

	/**
	 * ファイル更新日取得.
	 * 
	 * @return 日付
	 */
	public String getFileUpdatedDate() {
		return Native.get_fileUpdatedDate(getPtr());
	}

	/**
	 * セキュリティー取得.
	 * 
	 * @return セキュリティー
	 */
	public String getSecurity() {
		return Native.get_security(getPtr());
	}

	/**
	 * プロトコル取得.
	 * 
	 * @return プロトコル
	 */
	public String getProtocol() {
		return Native.get_protocol(getPtr());
	}

	/**
	 * NameProp取得.
	 * 
	 * @return NameProp
	 */
	public String getNameProp() {
		return Native.get_nameProp(getPtr());
	}

	/**
	 * HTML書き込み.
	 * 
	 * @param html
	 *            HTML
	 */
	public void write(String html) {
		Native.write(getPtr(), html);
	}

	/**
	 * HTML書き込み.
	 * 
	 * @param html
	 *            HTML
	 */
	public void writeln(String html) {
		Native.writeln(getPtr(), html);
	}

	public IDispatch open(String url, Variant name, Variant features,
			Variant replace) {
		return Native.open(getPtr(), url, name, features, replace);
	}

	/**
	 * HTML書き込み終了.
	 */
	public void close() {
		Native.close(getPtr());
	}

	/**
	 * HTMLクリアー.
	 */
	public void clear() {
		Native.clear(getPtr());
	}

	public boolean queryCommandSupported(String cmdID) {
		return Native.queryCommandSupported(getPtr(), cmdID);
	}

	public boolean queryCommandEnabled(String cmdID) {
		return Native.queryCommandEnabled(getPtr(), cmdID);
	}

	public boolean queryCommandState(String cmdID) {
		return Native.queryCommandState(getPtr(), cmdID);
	}

	public boolean queryCommandIndeterm(String cmdID) {
		return Native.queryCommandIndeterm(getPtr(), cmdID);
	}

	public String queryCommandText(String cmdID) {
		return Native.queryCommandText(getPtr(), cmdID);
	}

	public Variant queryCommandValue(String cmdID) {
		return Native.queryCommandValue(getPtr(), cmdID);
	}

	public boolean execCommand(String cmdID, boolean showUI, Variant value) {
		return Native.execCommand(getPtr(), cmdID, showUI, value);
	}

	/**
	 * HTML要素生成.
	 * 
	 * @return HTML要素
	 */
	public IHTMLElement create(String tagName) {
		return Native.createElement(getPtr(), tagName);
	}

	/**
	 * @see ComPtr#disposeChild()
	 * @deprecated
	 * @see #create(String)
	 */
	public IHTMLElement create(String tagName, boolean addChild) {
		return create(tagName);
	}

	/**
	 * HTML要素取得.
	 * 
	 * @param x
	 *            X
	 * @param y
	 *            Y
	 * @return HTML要素
	 */
	public IHTMLElement elementFromPoint(int x, int y) {
		return Native.elementFromPoint(getPtr(), x, y);
	}

	/**
	 * @see ComPtr#disposeChild()
	 * @deprecated
	 * @see #create(String)
	 */
	public IHTMLElement elementFromPoint(int x, int y, boolean addChild) {
		return elementFromPoint(x, y);
	}

	/**
	 * 親ウィンドウ取得.
	 * 
	 * @return HTMLウィンドウ
	 */
	public IHTMLWindow getParentWindow() {
		return Native.get_parentWindow(getPtr());
	}

	/**
	 * @see ComPtr#disposeChild()
	 * @deprecated
	 * @see #getParentWindow()
	 */
	public IHTMLWindow getParentWindow(boolean addChild) {
		return getParentWindow();
	}

	/**
	 * ベースURL設定.
	 * 
	 * @param url
	 *            URL
	 */
	public void setBaseUrl(String url) {
		Native.put_baseUrl(getPtr(), url);
	}

	/**
	 * ベースURL取得.
	 * 
	 * @return URL
	 */
	public String getBaseUrl() {
		return Native.get_baseUrl(getPtr());
	}

	/**
	 * HTML要素取得.
	 * 
	 * @param id
	 *            ID
	 * @return HTMLウィンドウ
	 */
	public IHTMLElement getElementById(String id) {
		return Native.getElementById(getPtr(), id);
	}

	/**
	 * @see ComPtr#disposeChild()
	 * @deprecated
	 * @see #getElementById(String)
	 */
	public IHTMLElement getElementById(String id, boolean addChild) {
		return getElementById(id);
	}

	/**
	 * HTML要素取得.
	 * 
	 * @param name
	 *            名称
	 * @return コレクション
	 */
	public IHTMLElementCollection getElementsByName(String name) {
		return Native.getElementsByName(getPtr(), name);
	}

	/**
	 * @see ComPtr#disposeChild()
	 * @deprecated
	 * @see #getElementsByName(String)
	 */
	public IHTMLElementCollection getElementsByName(String name,
			boolean addChild) {
		return getElementsByName(name);
	}

	/**
	 * HTML要素取得.
	 * <p>
	 * 複数の名前がある場合、先頭の1つを返す。
	 * </p>
	 * 
	 * @param name
	 *            名称
	 * @return HTML要素
	 */
	public IHTMLElement getElementByName(String name) {
		IHTMLElementCollection c = Native.getElementsByName(getPtr(), name);

		// 先頭の一件を返すので、それを取得
		IHTMLElement e = c.item(name, 0);
		c.dispose();

		return e;
	}

	/**
	 * @see ComPtr#disposeChild()
	 * @deprecated
	 * @see #getElementByName(String)
	 */
	public IHTMLElement getElementByName(String name, boolean addChild) {
		return getElementByName(name);
	}

	/**
	 * HTML要素取得.
	 * 
	 * @param name
	 *            タグ名
	 * @return コレクション
	 */
	public IHTMLElementCollection getElementsByTagName(String name) {
		return Native.getElementsByTagName(getPtr(), name);
	}

	/**
	 * @see ComPtr#disposeChild()
	 * @deprecated
	 * @see #getElementsByTagName(String)
	 */
	public IHTMLElementCollection getElementsByTagName(String name,
			boolean addChild) {
		return getElementsByTagName(name);
	}

	/**
	 * フォーカス設定.
	 */
	public void focus() {
		Native.focus(getPtr());
	}

	/**
	 * フォーカス有無取得.
	 * 
	 * @return フォーカス有無
	 */
	public boolean hasFocus() {
		return Native.hasFocus(getPtr());
	}

	/**
	 * HTMLドキュメント作成.
	 * 
	 * @param url
	 *            URL
	 * @param options
	 *            オプション
	 * @return HTMLドキュメント
	 */
	public IHTMLDocument createDocumentFromUrl(String url, String options) {
		return Native.createDocumentFromUrl(getPtr(), url, options);
	}

	/**
	 * @see ComPtr#disposeChild()
	 * @deprecated
	 * @see #createDocumentFromUrl(String, String)
	 */
	public IHTMLDocument createDocumentFromUrl(String url, String options,
			boolean addChild) {
		return createDocumentFromUrl(url, options);
	}

	/**
	 * WebBrowserコントロール取得.
	 * 
	 * @return WebBrowserコントロール
	 */
	public IWebBrowser getWebBrowser() {
		return Native.getWebBrowser(getPtr());
	}

	/**
	 * @see ComPtr#disposeChild()
	 * @deprecated
	 * @see #getWebBrowser()
	 */
	public IWebBrowser getWebBrowser(boolean addChild) {
		return getWebBrowser();
	}

	private static class Native {
		//
		// original
		//

		private static native IWebBrowser getWebBrowser(long ptr);

		//
		// IHTMLDocument
		//
		private static native IDispatch get_Script(long ptr);

		//
		// IHTMLDocument2
		//

		private static native IHTMLElementCollection get_all(long ptr);

		private static native IHTMLElement get_body(long ptr);

		private static native IHTMLElement get_activeElement(long ptr);

		private static native IHTMLElementCollection get_images(long ptr);

		private static native IHTMLElementCollection get_links(long ptr);

		private static native IHTMLElementCollection get_forms(long ptr);

		private static native IHTMLElementCollection get_anchors(long ptr);

		private static native void put_title(long ptr, String title);

		private static native String get_title(long ptr);

		private static native String get_readyState(long ptr);

		private static native IHTMLFramesCollection get_frames(long ptr);

		private static native void put_alinkColor(long ptr, Variant p);

		private static native Variant get_alinkColor(long ptr);

		private static native void put_bgColor(long ptr, Variant p);

		private static native Variant get_bgColor(long ptr);

		private static native void put_fgColor(long ptr, Variant p);

		private static native Variant get_fgColor(long ptr);

		private static native void put_linkColor(long ptr, Variant p);

		private static native Variant get_linkColor(long ptr);

		private static native void put_vlinkColor(long ptr, Variant p);

		private static native Variant get_vlinkColor(long ptr);

		private static native String get_referrer(long ptr);

		private static native IHTMLLocation get_location(long ptr);

		private static native String get_lastModified(long ptr);

		private static native void put_url(long ptr, String url);

		private static native String get_url(long ptr);

		private static native void put_domain(long ptr, String domain);

		private static native String get_domain(long ptr);

		private static native void put_cookie(long ptr, String cookie);

		private static native String get_cookie(long ptr);

		private static native void put_charset(long ptr, String charset);

		private static native String get_charset(long ptr);

		private static native void put_defaultCharset(long ptr, String charset);

		private static native String get_defaultCharset(long ptr);

		private static native String get_mimeType(long ptr);

		private static native String get_fileSize(long ptr);

		private static native String get_fileCreatedDate(long ptr);

		private static native String get_fileModifiedDate(long ptr);

		private static native String get_fileUpdatedDate(long ptr);

		private static native String get_security(long ptr);

		private static native String get_protocol(long ptr);

		private static native String get_nameProp(long ptr);

		private static native void write(long ptr, String html);

		private static native void writeln(long ptr, String html);

		private static native IDispatch open(long ptr, String url,
				Variant name, Variant features, Variant replace);

		private static native void close(long ptr);

		private static native void clear(long ptr);

		private static native boolean queryCommandSupported(long ptr,
				String cmdID);

		private static native boolean queryCommandEnabled(long ptr, String cmdID);

		private static native boolean queryCommandState(long ptr, String cmdID);

		private static native boolean queryCommandIndeterm(long ptr,
				String cmdID);

		private static native String queryCommandText(long ptr, String cmdID);

		private static native Variant queryCommandValue(long ptr, String cmdID);

		private static native boolean execCommand(long ptr, String cmdID,
				boolean showUI, Variant value);

		private static native IHTMLElement createElement(long ptr, String tag);

		private static native IHTMLElement elementFromPoint(long ptr, int x,
				int y);

		private static native IHTMLWindow get_parentWindow(long ptr);

		//
		// IHTMLDocument3
		//

		private static native void put_baseUrl(long ptr, String url);

		private static native String get_baseUrl(long ptr);

		private static native IHTMLElementCollection getElementsByName(
				long ptr, String name);

		private static native IHTMLElement getElementById(long ptr, String id);

		private static native IHTMLElementCollection getElementsByTagName(
				long ptr, String name);

		//
		// IHTMLDocument4
		//

		private static native void focus(long ptr);

		private static native boolean hasFocus(long ptr);

		private static native IHTMLDocument createDocumentFromUrl(long ptr,
				String url, String options);
	}
}