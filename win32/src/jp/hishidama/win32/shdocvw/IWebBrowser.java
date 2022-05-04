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
 * IWebBrowser,IWebBrowser2,IWebBrowserApp�N���X.
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">�Ђ�����</a>
 * @since 2007.10.22
 * @version 2008.07.14
 */
public class IWebBrowser extends IDispatch {

	/**
	 * �V�KWebBrowser�쐬.
	 * 
	 * @return �E�F�u�u���E�U�[
	 */
	public static IWebBrowser create() {
		long ptr = Native.create();
		if (ptr == 0)
			return null;
		return new IWebBrowser(ptr);
	}

	/**
	 * WebBrowser�T��.
	 * 
	 * @param hwnd
	 *            HWND
	 * @return �E�F�u�u���E�U�[
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
	 * WebBrowser��.
	 * 
	 * @return {@link IWebBrowser}�̃��X�g
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
	 * �����̑O�y�[�W�ֈړ�.
	 */
	public void goBack() {
		Native.GoBack(getPtr());
	}

	/**
	 * �����̎��y�[�W�ֈړ�.
	 */
	public void goForward() {
		Native.GoForward(getPtr());
	}

	/**
	 * �z�[���ֈړ�.
	 */
	public void goHome() {
		Native.GoHome(getPtr());
	}

	/**
	 * �����y�[�W�ֈړ�.
	 */
	public void goSearch() {
		Native.GoSearch(getPtr());
	}

	/**
	 * �w��URL�ֈړ�.
	 * 
	 * @param url
	 *            URL
	 */
	public void navigate(String url) {
		Native.Navigate(getPtr(), url, null, null);
	}

	/**
	 * �w��URL�ֈړ�.
	 * 
	 * @param url
	 *            URL
	 * @param flags
	 *            �t���O
	 * @param targetFrameName
	 *            �t���[����
	 */
	public void navigate(String url, String flags, String targetFrameName) {
		Native.Navigate(getPtr(), url, flags, targetFrameName);
	}

	/**
	 * �X�V.
	 */
	public void refresh() {
		Native.Refresh(getPtr());
	}

	/**
	 * ��~.
	 */
	public void stop() {
		Native.Stop(getPtr());
	}

	/**
	 * HTML�h�L�������g�擾.
	 * 
	 * @return HTML�h�L�������g
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
	 * �h�L�������g�I�u�W�F�N�g���擾.
	 * 
	 * @return ����
	 */
	public String getType() {
		return Native.get_Type(getPtr());
	}

	/**
	 * �ʒu�擾.
	 * 
	 * @return �ʒu
	 */
	public Point getLocation() {
		long ptr = getPtr();
		int x = Native.get_Left(ptr);
		int y = Native.get_Top(ptr);
		return new Point(x, y);
	}

	/**
	 * �ʒu�ݒ�.
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
	 * �T�C�Y�擾.
	 * 
	 * @return �T�C�Y
	 */
	public Dimension getSize() {
		long ptr = getPtr();
		int cx = Native.get_Width(ptr);
		int cy = Native.get_Height(ptr);
		return new Dimension(cx, cy);
	}

	/**
	 * �T�C�Y�ݒ�.
	 * 
	 * @param cx
	 *            ��
	 * @param cy
	 *            ����
	 */
	public void setSize(int cx, int cy) {
		long ptr = getPtr();
		Native.put_Width(ptr, cx);
		Native.put_Height(ptr, cy);
	}

	/**
	 * �ʒu�T�C�Y�擾.
	 * 
	 * @return ��`
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
	 * �ʒu�T�C�Y�ݒ�.
	 * 
	 * @param x
	 *            X
	 * @param y
	 *            Y
	 * @param cx
	 *            ��
	 * @param cy
	 *            ����
	 */
	public void setBounds(int x, int y, int cx, int cy) {
		long ptr = getPtr();
		Native.put_Left(ptr, x);
		Native.put_Top(ptr, y);
		Native.put_Width(ptr, cx);
		Native.put_Height(ptr, cy);
	}

	/**
	 * X���W�擾.
	 * 
	 * @return X
	 */
	public int getLeft() {
		return Native.get_Left(getPtr());
	}

	/**
	 * X���W�ݒ�.
	 * 
	 * @param x
	 *            X
	 */
	public void setLeft(int x) {
		Native.put_Left(getPtr(), x);
	}

	/**
	 * Y���W�擾.
	 * 
	 * @return Y
	 */
	public int getTop() {
		return Native.get_Top(getPtr());
	}

	/**
	 * Y���W�ݒ�.
	 * 
	 * @param y
	 *            Y
	 */
	public void setTop(int y) {
		Native.put_Top(getPtr(), y);
	}

	/**
	 * ���擾.
	 * 
	 * @return ��
	 */
	public int getWidth() {
		return Native.get_Width(getPtr());
	}

	/**
	 * ���ݒ�.
	 * 
	 * @param width
	 *            ��
	 */
	public void setWidth(int width) {
		Native.put_Width(getPtr(), width);
	}

	/**
	 * �����擾.
	 * 
	 * @return ����
	 */
	public int getHeight() {
		return Native.get_Height(getPtr());
	}

	/**
	 * �����ݒ�.
	 * 
	 * @param height
	 *            ����
	 */
	public void setHeight(int height) {
		Native.put_Height(getPtr(), height);
	}

	/**
	 * ���P�[�V�������擾.
	 * 
	 * @return ����
	 */
	public String getLocationName() {
		return Native.get_LocationName(getPtr());
	}

	/**
	 * ���P�[�V����URL�擾.
	 * 
	 * @return URL
	 */
	public String getLocationURL() {
		return Native.get_LocationURL(getPtr());
	}

	/**
	 * �r�W�[��Ԏ擾.
	 * 
	 * @return �r�W�[���
	 */
	public boolean getBusy() {
		return Native.get_Busy(getPtr());
	}

	/**
	 * �I��.
	 */
	public void quit() {
		Native.Quit(getPtr());
	}

	/**
	 * �R���g���[���̖��̎擾.
	 * 
	 * @return ����
	 */
	public String getName() {
		return Native.get_Name(getPtr());
	}

	/**
	 * HWND�擾.
	 * 
	 * @return HWND
	 */
	public long getHWND() {
		return Native.get_HWND(getPtr());
	}

	/**
	 * ���s���W���[���̃t���p�X�擾.
	 * 
	 * @return �t���p�X��
	 */
	public String getFullName() {
		return Native.get_FullName(getPtr());
	}

	/**
	 * ���s���W���[���̃p�X�擾.
	 * 
	 * @return �p�X
	 */
	public String getPath() {
		return Native.get_Path(getPtr());
	}

	/**
	 * �\����Ԏ擾.
	 * 
	 * @return �\�����
	 */
	public boolean getVisible() {
		return Native.get_Visible(getPtr());
	}

	/**
	 * �\����Ԑݒ�.
	 * 
	 * @param b
	 *            �\�����
	 */
	public void setVisible(boolean b) {
		Native.put_Visible(getPtr(), b);
	}

	/**
	 * �X�e�[�^�X�o�[�\����Ԏ擾.
	 * 
	 * @return �\�����
	 */
	public boolean getStatusBar() {
		return Native.get_StatusBar(getPtr());
	}

	/**
	 * �X�e�[�^�X�o�[�\����Ԑݒ�.
	 * 
	 * @param b
	 *            �\�����
	 */
	public void setStatusBar(boolean b) {
		Native.put_StatusBar(getPtr(), b);
	}

	/**
	 * �X�e�[�^�X�o�[�\��������擾.
	 * 
	 * @return ������
	 */
	public String getStatusText() {
		return Native.get_StatusText(getPtr());
	}

	/**
	 * �X�e�[�^�X�o�[������\��.
	 * 
	 * @param text
	 *            ������
	 */
	public void setStatusText(String text) {
		Native.put_StatusText(getPtr(), text);
	}

	/**
	 * �c�[���o�[�\����Ԏ擾.
	 * 
	 * @return �\�����
	 */
	public int getToolBar() {
		return Native.get_ToolBar(getPtr());
	}

	/**
	 * �c�[���o�[�\����Ԑݒ�.
	 * 
	 * @param value
	 *            �\�����
	 */
	public void setToolBar(int value) {
		Native.put_ToolBar(getPtr(), value);
	}

	/**
	 * ���j���[�o�[�\����Ԏ擾.
	 * 
	 * @return �\�����
	 */
	public boolean getMenuBar() {
		return Native.get_MenuBar(getPtr());
	}

	/**
	 * ���j���[�o�[�\����Ԑݒ�.
	 * 
	 * @param b
	 *            �\�����
	 */
	public void setMenuBar(boolean b) {
		Native.put_MenuBar(getPtr(), b);
	}

	/**
	 * �t���X�N���[����Ԏ擾.
	 * 
	 * @return �t���X�N���[�����
	 */
	public boolean getFullScreen() {
		return Native.get_FullScreen(getPtr());
	}

	/**
	 * �t���X�N���[����Ԑݒ�.
	 * 
	 * @param b
	 *            �t���X�N���[�����
	 */
	public void setFullScreen(boolean b) {
		Native.put_FullScreen(getPtr(), b);
	}

	/**
	 * ReadyState�擾.
	 * 
	 * @return ReadyState
	 * @see OcIdlConst#READYSTATE_COMPLETE
	 */
	public int getReadyState() {
		return Native.get_ReadyState(getPtr());
	}

	/**
	 * �I�t���C����Ԏ擾.
	 * 
	 * @return �I�t���C�����
	 */
	public boolean getOffline() {
		return Native.get_Offline(getPtr());
	}

	/**
	 * �I�t���C����Ԑݒ�.
	 * 
	 * @param b
	 *            �I�t���C�����
	 */
	public void setOffline(boolean b) {
		Native.put_Offline(getPtr(), b);
	}

	/**
	 * �_�C�A���O�\���ێ擾.
	 * 
	 * @return �\����
	 */
	public boolean getSilent() {
		return Native.get_Silent(getPtr());
	}

	/**
	 * �_�C�A���O�\���ېݒ�.
	 * 
	 * @param b
	 *            �\����
	 */
	public void setSilent(boolean b) {
		Native.put_Silent(getPtr(), b);
	}

	/**
	 * �g�b�v���x���u���E�U�[�o�^��Ԏ擾.
	 * 
	 * @return �o�^���
	 */
	public boolean getRegisterAsBrowser() {
		return Native.get_RegisterAsBrowser(getPtr());
	}

	/**
	 * �g�b�v���x���u���E�U�[�o�^��Ԑݒ�.
	 * 
	 * @param b
	 *            �o�^���
	 */
	public void setRegisterAsBrowser(boolean b) {
		Native.put_RegisterAsBrowser(getPtr(), b);
	}

	/**
	 * �h���b�v�^�[�Q�b�g�o�^��Ԏ擾.
	 * 
	 * @return �o�^���
	 */
	public boolean getRegisterAsDropTarget() {
		return Native.get_RegisterAsDropTarget(getPtr());
	}

	/**
	 * �h���b�v�^�[�Q�b�g�o�^��Ԑݒ�.
	 * 
	 * @param b
	 *            �o�^���
	 */
	public void setRegisterAsDropTarget(boolean b) {
		Native.put_RegisterAsDropTarget(getPtr(), b);
	}

	/**
	 * �V�A�^�[��Ԏ擾.
	 * 
	 * @return �V�A�^�[���
	 */
	public boolean getTheaterMode() {
		return Native.get_TheaterMode(getPtr());
	}

	/**
	 * �V�A�^�[��Ԑݒ�.
	 * 
	 * @param b
	 *            �V�A�^�[���
	 */
	public void setTheaterMode(boolean b) {
		Native.put_TheaterMode(getPtr(), b);
	}

	/**
	 * �A�h���X�o�[�\����Ԏ擾.
	 * 
	 * @return �\�����
	 */
	public boolean getAddressBar() {
		return Native.get_AddressBar(getPtr());
	}

	/**
	 * �A�h���X�o�[�\����Ԑݒ�.
	 * 
	 * @param b
	 *            �\�����
	 */
	public void setAddressBar(boolean b) {
		Native.put_AddressBar(getPtr(), b);
	}

	/**
	 * �T�C�Y�ύX�ێ擾.
	 * 
	 * @return �ύX��
	 */
	public boolean getResizable() {
		return Native.get_Resizable(getPtr());
	}

	/**
	 * �T�C�Y�ύX�ېݒ�.
	 * 
	 * @param b
	 *            �ύX��
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
