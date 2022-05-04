package jp.hishidama.win32.mshtml;

import jp.hishidama.win32.com.ComPtr;
import jp.hishidama.win32.com.IDispatch;
import jp.hishidama.win32.com.Variant;
import jp.hishidama.win32.shdocvw.IWebBrowser;

/**
 * IHTMLDocument2�`4�N���X.
 * <p>
 * HTML�h�L�������g�������N���X�ł��B
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">�Ђ�����</a>
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
	 * �SHTML�v�f�擾.
	 * 
	 * @return �R���N�V����
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
	 * �{�f�B�[�v�f�擾.
	 * 
	 * @return HTML�v�f
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
	 * �A�N�e�B�u�v�f�擾.
	 * 
	 * @return HTML�v�f
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
	 * �摜�v�f�擾.
	 * 
	 * @return �R���N�V����
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
	 * �����N�v�f�擾.
	 * 
	 * @return �R���N�V����
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
	 * �t�H�[���擾.
	 * 
	 * @return �R���N�V����
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
	 * �A���J�[�v�f�擾.
	 * 
	 * @return �R���N�V����
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
	 * ReadyState�擾.
	 * 
	 * @return ReadyState
	 */
	public String getReadyState() {
		return Native.get_readyState(getPtr());
	}

	/**
	 * �t���[���擾.
	 * 
	 * @return �R���N�V����
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
	 * �ŏI�X�V���擾.
	 * 
	 * @return ���t
	 */
	public String getLastModified() {
		return Native.get_lastModified(getPtr());
	}

	/**
	 * URL�ݒ�.
	 * 
	 * @param url
	 *            URL
	 */
	public void setUrl(String url) {
		Native.put_url(getPtr(), url);
	}

	/**
	 * URL�擾.
	 * 
	 * @return URL
	 */
	public String getUrl() {
		return Native.get_url(getPtr());
	}

	/**
	 * �h���C���ݒ�.
	 * 
	 * @param domain
	 *            �h���C��
	 */
	public void setDomain(String domain) {
		Native.put_domain(getPtr(), domain);
	}

	/**
	 * �h���C���擾.
	 * 
	 * @return �h���C��
	 */
	public String getDomain() {
		return Native.get_domain(getPtr());
	}

	/**
	 * �N�b�L�[�ݒ�.
	 * 
	 * @param cookie
	 *            �N�b�L�[
	 */
	public void setCookie(String cookie) {
		Native.put_cookie(getPtr(), cookie);
	}

	/**
	 * �N�b�L�[�擾.
	 * 
	 * @return �N�b�L�[
	 */
	public String getCookie() {
		return Native.get_cookie(getPtr());
	}

	/**
	 * �����Z�b�g�ݒ�.
	 * 
	 * @param charset
	 *            �L�����Z�b�g
	 */
	public void setCharset(String charset) {
		Native.put_charset(getPtr(), charset);
	}

	/**
	 * �����Z�b�g�擾.
	 * 
	 * @return �L�����Z�b�g
	 */
	public String getCharset() {
		return Native.get_charset(getPtr());
	}

	/**
	 * �f�t�H���g�����Z�b�g�ݒ�.
	 * 
	 * @param charset
	 *            �L�����Z�b�g
	 */
	public void setDefaultCharset(String charset) {
		Native.put_defaultCharset(getPtr(), charset);
	}

	/**
	 * �f�t�H���g�����Z�b�g�ݒ�.
	 * 
	 * @return �L�����Z�b�g
	 */
	public String getDefaultCharset() {
		return Native.get_defaultCharset(getPtr());
	}

	/**
	 * MIME�^�C�v�擾.
	 * 
	 * @return MIME�^�C�v
	 */
	public String getMimeType() {
		return Native.get_mimeType(getPtr());
	}

	/**
	 * �t�@�C���T�C�Y�擾.
	 * 
	 * @return �t�@�C���T�C�Y
	 */
	public int getFileSize() {
		return Integer.parseInt(Native.get_fileSize(getPtr()));
	}

	/**
	 * �t�@�C���쐬���擾.
	 * 
	 * @return ���t
	 */
	public String getFileCreatedDate() {
		return Native.get_fileCreatedDate(getPtr());
	}

	/**
	 * �t�@�C���ύX���擾.
	 * 
	 * @return ���t
	 */
	public String getFileModifiedDate() {
		return Native.get_fileModifiedDate(getPtr());
	}

	/**
	 * �t�@�C���X�V���擾.
	 * 
	 * @return ���t
	 */
	public String getFileUpdatedDate() {
		return Native.get_fileUpdatedDate(getPtr());
	}

	/**
	 * �Z�L�����e�B�[�擾.
	 * 
	 * @return �Z�L�����e�B�[
	 */
	public String getSecurity() {
		return Native.get_security(getPtr());
	}

	/**
	 * �v���g�R���擾.
	 * 
	 * @return �v���g�R��
	 */
	public String getProtocol() {
		return Native.get_protocol(getPtr());
	}

	/**
	 * NameProp�擾.
	 * 
	 * @return NameProp
	 */
	public String getNameProp() {
		return Native.get_nameProp(getPtr());
	}

	/**
	 * HTML��������.
	 * 
	 * @param html
	 *            HTML
	 */
	public void write(String html) {
		Native.write(getPtr(), html);
	}

	/**
	 * HTML��������.
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
	 * HTML�������ݏI��.
	 */
	public void close() {
		Native.close(getPtr());
	}

	/**
	 * HTML�N���A�[.
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
	 * HTML�v�f����.
	 * 
	 * @return HTML�v�f
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
	 * HTML�v�f�擾.
	 * 
	 * @param x
	 *            X
	 * @param y
	 *            Y
	 * @return HTML�v�f
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
	 * �e�E�B���h�E�擾.
	 * 
	 * @return HTML�E�B���h�E
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
	 * �x�[�XURL�ݒ�.
	 * 
	 * @param url
	 *            URL
	 */
	public void setBaseUrl(String url) {
		Native.put_baseUrl(getPtr(), url);
	}

	/**
	 * �x�[�XURL�擾.
	 * 
	 * @return URL
	 */
	public String getBaseUrl() {
		return Native.get_baseUrl(getPtr());
	}

	/**
	 * HTML�v�f�擾.
	 * 
	 * @param id
	 *            ID
	 * @return HTML�E�B���h�E
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
	 * HTML�v�f�擾.
	 * 
	 * @param name
	 *            ����
	 * @return �R���N�V����
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
	 * HTML�v�f�擾.
	 * <p>
	 * �����̖��O������ꍇ�A�擪��1��Ԃ��B
	 * </p>
	 * 
	 * @param name
	 *            ����
	 * @return HTML�v�f
	 */
	public IHTMLElement getElementByName(String name) {
		IHTMLElementCollection c = Native.getElementsByName(getPtr(), name);

		// �擪�̈ꌏ��Ԃ��̂ŁA������擾
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
	 * �t�H�[�J�X�ݒ�.
	 */
	public void focus() {
		Native.focus(getPtr());
	}

	/**
	 * �t�H�[�J�X�L���擾.
	 * 
	 * @return �t�H�[�J�X�L��
	 */
	public boolean hasFocus() {
		return Native.hasFocus(getPtr());
	}

	/**
	 * HTML�h�L�������g�쐬.
	 * 
	 * @param url
	 *            URL
	 * @param options
	 *            �I�v�V����
	 * @return HTML�h�L�������g
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
	 * WebBrowser�R���g���[���擾.
	 * 
	 * @return WebBrowser�R���g���[��
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