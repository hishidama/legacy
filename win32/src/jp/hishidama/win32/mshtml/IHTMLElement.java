package jp.hishidama.win32.mshtml;

import jp.hishidama.win32.com.ComPtr;
import jp.hishidama.win32.com.IDispatch;
import jp.hishidama.win32.com.Variant;

/**
 * IHTMLElement,IHTMLElement2�`4�N���X.
 * <p>
 * HTML�v�f�������N���X�ł��B
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">�Ђ�����</a>
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
	 * ID�擾.
	 * 
	 * @return ID
	 */
	public String getId() {
		return Native.get_id(getPtr());
	}

	/**
	 * ID�ݒ�.
	 * 
	 * @param id
	 *            ID
	 */
	public void setId(String id) {
		Native.put_id(getPtr(), id);
	}

	/**
	 * �N���X�擾.
	 * 
	 * @return class
	 */
	public String getClassName() {
		return Native.get_className(getPtr());
	}

	/**
	 * �N���X�ݒ�.
	 * 
	 * @param name
	 *            class
	 */
	public void setClassName(String name) {
		Native.put_className(getPtr(), name);
	}

	/**
	 * �^�O���擾.
	 * 
	 * @return �^�O
	 */
	public String getTagName() {
		return Native.get_tagName(getPtr());
	}

	/**
	 * �e�v�f�擾.
	 * 
	 * @return HTML�v�f
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
	 * HTML�h�L�������g�擾.
	 * 
	 * @return HTML�h�L�������g
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
	 * �^�C�g���ݒ�.
	 * 
	 * @param title
	 *            �^�C�g��
	 */
	public void setTitle(String title) {
		Native.put_title(getPtr(), title);
	}

	/**
	 * �^�C�g���擾.
	 * 
	 * @return �^�C�g��
	 */
	public String getTitle() {
		return Native.get_title(getPtr());
	}

	/**
	 * ����ݒ�.
	 * 
	 * @param lang
	 *            ����
	 */
	public void setLanguage(String lang) {
		Native.put_language(getPtr(), lang);
	}

	/**
	 * ����擾.
	 * 
	 * @return ����
	 */
	public String getLanguage() {
		return Native.get_language(getPtr());
	}

	/**
	 * ����ݒ�.
	 * 
	 * @param lang
	 *            ����
	 */
	public void setLang(String lang) {
		Native.put_lang(getPtr(), lang);
	}

	/**
	 * ����擾.
	 * 
	 * @return ����
	 */
	public String getLang() {
		return Native.get_lang(getPtr());
	}

	/**
	 * ����X���W�擾.
	 * 
	 * @return X
	 */
	public int getOffsetLeft() {
		return Native.get_offsetLeft(getPtr());
	}

	/**
	 * ����Y���W�擾.
	 * 
	 * @return Y
	 */
	public int getOffsetTop() {
		return Native.get_offsetTop(getPtr());
	}

	/**
	 * ���Ε��擾.
	 * 
	 * @return ��
	 */
	public int getOffsetWidth() {
		return Native.get_offsetWidth(getPtr());
	}

	/**
	 * ���΍����擾.
	 * 
	 * @return ����
	 */
	public int getOffsetHeight() {
		return Native.get_offsetHeight(getPtr());
	}

	/**
	 * ���Έʒu��v�f�擾.
	 * 
	 * @return HTML�v�f
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
	 * ����HTML�ݒ�.
	 * 
	 * @param html
	 *            HTML
	 */
	public void setInnerHtml(String html) {
		Native.put_innerHTML(getPtr(), html);
	}

	/**
	 * ����HTML�擾.
	 * 
	 * @return HTML
	 */
	public String getInnerHtml() {
		return Native.get_innerHTML(getPtr());
	}

	/**
	 * ����������ݒ�.
	 * 
	 * @param text
	 *            ������
	 */
	public void setInnerText(String text) {
		Native.put_innerText(getPtr(), text);
	}

	/**
	 * ����������擾.
	 * 
	 * @return ������
	 */
	public String getInnerText() {
		return Native.get_innerText(getPtr());
	}

	/**
	 * HTML�ݒ�.
	 * 
	 * @param html
	 *            HTML
	 */
	public void setOuterHtml(String html) {
		Native.put_outerHTML(getPtr(), html);
	}

	/**
	 * HTML�擾.
	 * 
	 * @return HTML
	 */
	public String getOuterHtml() {
		return Native.get_outerHTML(getPtr());
	}

	/**
	 * ������ݒ�.
	 * 
	 * @param text
	 *            ������
	 */
	public void setOuterText(String text) {
		Native.put_outerText(getPtr(), text);
	}

	/**
	 * ������擾.
	 * 
	 * @return ������
	 */
	public String getOuterText() {
		return Native.get_outerText(getPtr());
	}

	/**
	 * �e�e�L�X�g�擾.
	 * 
	 * @return HTML�v�f
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
	 * �����擾.
	 * 
	 * @param name
	 *            ������
	 * @return �����l
	 */
	public Variant getAttribute(String name) {
		return Native.getAttribute(getPtr(), name, 0);
	}

	/**
	 * �����擾.
	 * 
	 * @param name
	 *            ������
	 * @return �����l
	 */
	public String getAttributeString(String name) {
		Variant var = Native.getAttribute(getPtr(), name, 0);
		return var.getStr();
	}

	/**
	 * �����ݒ�.
	 * 
	 * @param name
	 *            ������
	 * @param value
	 *            �����l
	 */
	public void setAttribute(String name, Variant value) {
		Native.setAttribute(getPtr(), name, value, 0);
	}

	/**
	 * �����폜.
	 * 
	 * @param name
	 *            ������
	 * @return ���������ꍇ�Atrue
	 */
	public boolean removeAttribute(String name) {
		return Native.removeAttribute(getPtr(), name, 0);
	}

	/**
	 * �N���b�N.
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
	 * �X�R�[�v���擾.
	 * 
	 * @return �X�R�[�v
	 */
	public String getScopeName() {
		return Native.get_scopeName(getPtr());
	}

	/**
	 * �t�H�[�J�X�ݒ�.
	 */
	public void focus() {
		Native.focus(getPtr());
	}

	/**
	 * X���W�擾.
	 * 
	 * @return X
	 */
	public int getClientLeft() {
		return Native.get_clientLeft(getPtr());
	}

	/**
	 * Y���W�擾.
	 * 
	 * @return Y
	 */
	public int getClientTop() {
		return Native.get_clientTop(getPtr());
	}

	/**
	 * ���擾.
	 * 
	 * @return ��
	 */
	public int getClientWidth() {
		return Native.get_clientWidth(getPtr());
	}

	/**
	 * �����擾.
	 * 
	 * @return ����
	 */
	public int getClientHeight() {
		return Native.get_clientHeight(getPtr());
	}

	/**
	 * �����擾.
	 * 
	 * @return ����
	 */
	public String getDir() {
		return Native.get_dir(getPtr());
	}

	/**
	 * �����ݒ�.
	 * 
	 * @param dir
	 *            ����
	 */
	public void setDir(String dir) {
		Native.put_dir(getPtr(), dir);
	}

	/**
	 * �X�N���[��X���W�擾.
	 * 
	 * @return X
	 */
	public int getScrollLeft() {
		return Native.get_scrollLeft(getPtr());
	}

	/**
	 * �X�N���[��X���W�ݒ�.
	 * 
	 * @param x
	 *            X
	 */
	public void setScrollLeft(int x) {
		Native.put_scrollLeft(getPtr(), x);
	}

	/**
	 * �X�N���[��Y���W�擾.
	 * 
	 * @return Y
	 */
	public int getScrollTop() {
		return Native.get_scrollTop(getPtr());
	}

	/**
	 * �X�N���[��Y���W�ݒ�.
	 * 
	 * @param y
	 *            Y
	 */
	public void setScrollTop(int y) {
		Native.put_scrollTop(getPtr(), y);
	}

	/**
	 * �X�N���[�����擾.
	 * 
	 * @return ��
	 */
	public int getScrollWidth() {
		return Native.get_scrollWidth(getPtr());
	}

	/**
	 * �X�N���[�������擾.
	 * 
	 * @return ����
	 */
	public int getScrollHeight() {
		return Native.get_scrollHeight(getPtr());
	}

	/**
	 * �^�OURN�ݒ�.
	 * 
	 * @param urn
	 *            URN
	 */
	public void setTagUrn(String urn) {
		Native.put_tagUrn(getPtr(), urn);
	}

	/**
	 * �^�OURN�擾.
	 * 
	 * @return URN
	 */
	public String getTagUrn() {
		return Native.get_tagUrn(getPtr());
	}

	/**
	 * HTML�v�f�擾.
	 * 
	 * @param name
	 *            �^�O��
	 * @return �R���N�V����
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
	 * �����s�L���擾.
	 * 
	 * @return �����s���
	 */
	public boolean isMultiLine() {
		return Native.get_isMultiLine(getPtr());
	}

	/**
	 * HTML�L���擾.
	 * 
	 * @return �L��
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
	 * �A�N�e�B�u��.
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
