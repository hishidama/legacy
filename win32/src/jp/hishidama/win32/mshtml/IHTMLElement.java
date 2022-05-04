package jp.hishidama.win32.mshtml;

import jp.hishidama.win32.com.ComPtr;
import jp.hishidama.win32.com.IDispatch;
import jp.hishidama.win32.com.Variant;

/**
 * IHTMLElement,IHTMLElement2～4クラス.
 * <p>
 * HTML要素を扱うクラスです。
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">ひしだま</a>
 * @since 2007.10.22
 * @version 2008.07.14
 */
public class IHTMLElement extends IDispatch {

	protected IHTMLElement(long dll_ptr) {
		super(dll_ptr);
	}

	protected long getPtr() {
		return super.getPtr();
	}

	/**
	 * ID取得.
	 * 
	 * @return ID
	 */
	public String getId() {
		return Native.get_id(getPtr());
	}

	/**
	 * ID設定.
	 * 
	 * @param id
	 *            ID
	 */
	public void setId(String id) {
		Native.put_id(getPtr(), id);
	}

	/**
	 * クラス取得.
	 * 
	 * @return class
	 */
	public String getClassName() {
		return Native.get_className(getPtr());
	}

	/**
	 * クラス設定.
	 * 
	 * @param name
	 *            class
	 */
	public void setClassName(String name) {
		Native.put_className(getPtr(), name);
	}

	/**
	 * タグ名取得.
	 * 
	 * @return タグ
	 */
	public String getTagName() {
		return Native.get_tagName(getPtr());
	}

	/**
	 * 親要素取得.
	 * 
	 * @return HTML要素
	 */
	public IHTMLElement getParentElement() {
		return Native.get_parentElement(getPtr());
	}

	/**
	 * @see ComPtr#disposeChild()
	 * @deprecated
	 * @see #getParentElement()
	 */
	public IHTMLElement getParentElement(boolean addChild) {
		return getParentElement();
	}

	/**
	 * HTMLドキュメント取得.
	 * 
	 * @return HTMLドキュメント
	 */
	public IHTMLDocument getDocument() {
		return (IHTMLDocument) Native.get_document(getPtr());
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
	 * 言語設定.
	 * 
	 * @param lang
	 *            言語
	 */
	public void setLanguage(String lang) {
		Native.put_language(getPtr(), lang);
	}

	/**
	 * 言語取得.
	 * 
	 * @return 言語
	 */
	public String getLanguage() {
		return Native.get_language(getPtr());
	}

	/**
	 * 言語設定.
	 * 
	 * @param lang
	 *            言語
	 */
	public void setLang(String lang) {
		Native.put_lang(getPtr(), lang);
	}

	/**
	 * 言語取得.
	 * 
	 * @return 言語
	 */
	public String getLang() {
		return Native.get_lang(getPtr());
	}

	/**
	 * 相対X座標取得.
	 * 
	 * @return X
	 */
	public int getOffsetLeft() {
		return Native.get_offsetLeft(getPtr());
	}

	/**
	 * 相対Y座標取得.
	 * 
	 * @return Y
	 */
	public int getOffsetTop() {
		return Native.get_offsetTop(getPtr());
	}

	/**
	 * 相対幅取得.
	 * 
	 * @return 幅
	 */
	public int getOffsetWidth() {
		return Native.get_offsetWidth(getPtr());
	}

	/**
	 * 相対高さ取得.
	 * 
	 * @return 高さ
	 */
	public int getOffsetHeight() {
		return Native.get_offsetHeight(getPtr());
	}

	/**
	 * 相対位置基準要素取得.
	 * 
	 * @return HTML要素
	 */
	public IHTMLElement getOffsetParent() {
		return Native.get_offsetParent(getPtr());
	}

	/**
	 * @see ComPtr#disposeChild()
	 * @deprecated
	 * @see #getOffsetParent()
	 */
	public IHTMLElement getOffsetParent(boolean addChild) {
		return getOffsetParent();
	}

	/**
	 * 内部HTML設定.
	 * 
	 * @param html
	 *            HTML
	 */
	public void setInnerHtml(String html) {
		Native.put_innerHTML(getPtr(), html);
	}

	/**
	 * 内部HTML取得.
	 * 
	 * @return HTML
	 */
	public String getInnerHtml() {
		return Native.get_innerHTML(getPtr());
	}

	/**
	 * 内部文字列設定.
	 * 
	 * @param text
	 *            文字列
	 */
	public void setInnerText(String text) {
		Native.put_innerText(getPtr(), text);
	}

	/**
	 * 内部文字列取得.
	 * 
	 * @return 文字列
	 */
	public String getInnerText() {
		return Native.get_innerText(getPtr());
	}

	/**
	 * HTML設定.
	 * 
	 * @param html
	 *            HTML
	 */
	public void setOuterHtml(String html) {
		Native.put_outerHTML(getPtr(), html);
	}

	/**
	 * HTML取得.
	 * 
	 * @return HTML
	 */
	public String getOuterHtml() {
		return Native.get_outerHTML(getPtr());
	}

	/**
	 * 文字列設定.
	 * 
	 * @param text
	 *            文字列
	 */
	public void setOuterText(String text) {
		Native.put_outerText(getPtr(), text);
	}

	/**
	 * 文字列取得.
	 * 
	 * @return 文字列
	 */
	public String getOuterText() {
		return Native.get_outerText(getPtr());
	}

	/**
	 * 親テキスト取得.
	 * 
	 * @return HTML要素
	 */
	public IHTMLElement getParentTextEdit() {
		return Native.get_parentTextEdit(getPtr());
	}

	/**
	 * @see ComPtr#disposeChild()
	 * @deprecated
	 * @see #getParentTextEdit()
	 */
	public IHTMLElement getParentTextEdit(boolean addChild) {
		return getParentTextEdit();
	}

	public boolean isTextEdit() {
		return Native.get_isTextEdit(getPtr());
	}

	/**
	 * 属性取得.
	 * 
	 * @param name
	 *            属性名
	 * @return 属性値
	 */
	public Variant getAttribute(String name) {
		return Native.getAttribute(getPtr(), name, 0);
	}

	/**
	 * 属性取得.
	 * 
	 * @param name
	 *            属性名
	 * @return 属性値
	 */
	public String getAttributeString(String name) {
		Variant var = Native.getAttribute(getPtr(), name, 0);
		return var.getStr();
	}

	/**
	 * 属性設定.
	 * 
	 * @param name
	 *            属性名
	 * @param value
	 *            属性値
	 */
	public void setAttribute(String name, Variant value) {
		Native.setAttribute(getPtr(), name, value, 0);
	}

	/**
	 * 属性削除.
	 * 
	 * @param name
	 *            属性名
	 * @return 成功した場合、true
	 */
	public boolean removeAttribute(String name) {
		return Native.removeAttribute(getPtr(), name, 0);
	}

	/**
	 * クリック.
	 */
	public void click() {
		Native.click(getPtr());
	}

	public IDispatch getChildren() {
		return Native.get_children(getPtr());
	}

	/**
	 * @see ComPtr#disposeChild()
	 * @deprecated
	 * @see #getChildren()
	 */
	public IDispatch getChildren(boolean addChild) {
		return getChildren();
	}

	public IDispatch getAll() {
		return Native.get_all(getPtr());
	}

	/**
	 * @see ComPtr#disposeChild()
	 * @deprecated
	 * @see #getAll()
	 */
	public IDispatch getAll(boolean addChild) {
		return getAll();
	}

	/**
	 * スコープ名取得.
	 * 
	 * @return スコープ
	 */
	public String getScopeName() {
		return Native.get_scopeName(getPtr());
	}

	/**
	 * フォーカス設定.
	 */
	public void focus() {
		Native.focus(getPtr());
	}

	/**
	 * X座標取得.
	 * 
	 * @return X
	 */
	public int getClientLeft() {
		return Native.get_clientLeft(getPtr());
	}

	/**
	 * Y座標取得.
	 * 
	 * @return Y
	 */
	public int getClientTop() {
		return Native.get_clientTop(getPtr());
	}

	/**
	 * 幅取得.
	 * 
	 * @return 幅
	 */
	public int getClientWidth() {
		return Native.get_clientWidth(getPtr());
	}

	/**
	 * 高さ取得.
	 * 
	 * @return 高さ
	 */
	public int getClientHeight() {
		return Native.get_clientHeight(getPtr());
	}

	/**
	 * 方向取得.
	 * 
	 * @return 方向
	 */
	public String getDir() {
		return Native.get_dir(getPtr());
	}

	/**
	 * 方向設定.
	 * 
	 * @param dir
	 *            方向
	 */
	public void setDir(String dir) {
		Native.put_dir(getPtr(), dir);
	}

	/**
	 * スクロールX座標取得.
	 * 
	 * @return X
	 */
	public int getScrollLeft() {
		return Native.get_scrollLeft(getPtr());
	}

	/**
	 * スクロールX座標設定.
	 * 
	 * @param x
	 *            X
	 */
	public void setScrollLeft(int x) {
		Native.put_scrollLeft(getPtr(), x);
	}

	/**
	 * スクロールY座標取得.
	 * 
	 * @return Y
	 */
	public int getScrollTop() {
		return Native.get_scrollTop(getPtr());
	}

	/**
	 * スクロールY座標設定.
	 * 
	 * @param y
	 *            Y
	 */
	public void setScrollTop(int y) {
		Native.put_scrollTop(getPtr(), y);
	}

	/**
	 * スクロール幅取得.
	 * 
	 * @return 幅
	 */
	public int getScrollWidth() {
		return Native.get_scrollWidth(getPtr());
	}

	/**
	 * スクロール高さ取得.
	 * 
	 * @return 高さ
	 */
	public int getScrollHeight() {
		return Native.get_scrollHeight(getPtr());
	}

	/**
	 * タグURN設定.
	 * 
	 * @param urn
	 *            URN
	 */
	public void setTagUrn(String urn) {
		Native.put_tagUrn(getPtr(), urn);
	}

	/**
	 * タグURN取得.
	 * 
	 * @return URN
	 */
	public String getTagUrn() {
		return Native.get_tagUrn(getPtr());
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
	 * 複数行有無取得.
	 * 
	 * @return 複数行状態
	 */
	public boolean isMultiLine() {
		return Native.get_isMultiLine(getPtr());
	}

	/**
	 * HTML有無取得.
	 * 
	 * @return 有無
	 */
	public boolean canHaveHTML() {
		return Native.get_canHaveHTML(getPtr());
	}

	public void setInflateBlock(boolean b) {
		Native.put_inflateBlock(getPtr(), b);
	}

	public boolean getInflateBlock() {
		return Native.get_inflateBlock(getPtr());
	}

	/**
	 * アクティブ化.
	 */
	public void setActive() {
		Native.setActive(getPtr());
	}

	public void setContentEditable(String content) {
		Native.put_contentEditable(getPtr(), content);
	}

	public String getContentEditable() {
		return Native.get_contentEditable(getPtr());
	}

	public boolean isContentEditable() {
		return Native.get_isContentEditable(getPtr());
	}

	public void setHideFocus(boolean b) {
		Native.put_hideFocus(getPtr(), b);
	}

	public boolean getHideFocus() {
		return Native.get_hideFocus(getPtr());
	}

	public void setDisabled(boolean b) {
		Native.put_disabled(getPtr(), b);
	}

	public boolean getDisabled() {
		return Native.get_disabled(getPtr());
	}

	public boolean isDisabled() {
		return Native.get_isDisabled(getPtr());
	}

	public boolean dragDrop() {
		return Native.dragDrop(getPtr());
	}

	public int getGlyphMode() {
		return Native.get_glyphMode(getPtr());
	}

	public void normalize() {
		Native.normalize(getPtr());
	}

	//
	// IHtmlElement
	//

	private static class Native {
		private static native void setAttribute(long ptr, String name,
				Variant value, int flags);

		private static native Variant getAttribute(long ptr, String name,
				int flags);

		private static native boolean removeAttribute(long ptr, String name,
				int flags);

		private static native void put_className(long ptr, String name);

		private static native String get_className(long ptr);

		private static native void put_id(long ptr, String id);

		private static native String get_id(long ptr);

		private static native String get_tagName(long ptr);

		private static native IHTMLElement get_parentElement(long ptr);

		private static native void put_title(long ptr, String title);

		private static native IDispatch get_document(long ptr);

		private static native String get_title(long ptr);

		private static native void put_language(long ptr, String lang);

		private static native String get_language(long ptr);

		private static native void put_lang(long ptr, String lang);

		private static native String get_lang(long ptr);

		private static native int get_offsetLeft(long ptr);

		private static native int get_offsetTop(long ptr);

		private static native int get_offsetWidth(long ptr);

		private static native int get_offsetHeight(long ptr);

		private static native IHTMLElement get_offsetParent(long ptr);

		private static native void put_innerHTML(long ptr, String html);

		private static native String get_innerHTML(long ptr);

		private static native void put_innerText(long ptr, String text);

		private static native String get_innerText(long ptr);

		private static native void put_outerHTML(long ptr, String html);

		private static native String get_outerHTML(long ptr);

		private static native void put_outerText(long ptr, String text);

		private static native String get_outerText(long ptr);

		private static native IHTMLElement get_parentTextEdit(long ptr);

		private static native boolean get_isTextEdit(long ptr);

		private static native void click(long ptr);

		private static native IDispatch get_children(long ptr);

		private static native IDispatch get_all(long ptr);

		//
		// IHtmlElement2
		//

		private static native String get_scopeName(long ptr);

		private static native void focus(long ptr);

		private static native int get_clientLeft(long ptr);

		private static native int get_clientTop(long ptr);

		private static native int get_clientWidth(long ptr);

		private static native int get_clientHeight(long ptr);

		private static native String get_dir(long ptr);

		private static native void put_dir(long ptr, String dir);

		private static native int get_scrollLeft(long ptr);

		private static native void put_scrollLeft(long ptr, int l);

		private static native int get_scrollTop(long ptr);

		private static native void put_scrollTop(long ptr, int l);

		private static native int get_scrollWidth(long ptr);

		private static native int get_scrollHeight(long ptr);

		private static native void put_tagUrn(long ptr, String urn);

		private static native String get_tagUrn(long ptr);

		private static native IHTMLElementCollection getElementsByTagName(
				long ptr, String name);

		//
		// IHtmlElement3
		//

		private static native boolean get_isMultiLine(long ptr);

		private static native boolean get_canHaveHTML(long ptr);

		private static native void put_inflateBlock(long ptr, boolean b);

		private static native boolean get_inflateBlock(long ptr);

		private static native void setActive(long ptr);

		private static native void put_contentEditable(long ptr, String p);

		private static native String get_contentEditable(long ptr);

		private static native boolean get_isContentEditable(long ptr);

		private static native void put_hideFocus(long ptr, boolean b);

		private static native boolean get_hideFocus(long ptr);

		private static native void put_disabled(long ptr, boolean b);

		private static native boolean get_disabled(long ptr);

		private static native boolean get_isDisabled(long ptr);

		private static native boolean dragDrop(long ptr);

		private static native int get_glyphMode(long ptr);

		//
		// IHtmlElement4
		//

		private static native void normalize(long ptr);
	}
}
