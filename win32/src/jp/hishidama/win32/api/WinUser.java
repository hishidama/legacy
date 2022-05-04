package jp.hishidama.win32.api;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;

import jp.hishidama.win32.JWnd;

/**
 * winuser.h�̊֐�.
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/hmwin32.html">�Ђ�����</a>
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
	 * ���b�Z�[�W���M.
	 * 
	 * @param hwnd
	 *            HWND
	 * @param msg
	 *            ���b�Z�[�W�iWM_*�j
	 * @param wparam
	 *            WPARAM
	 * @param lparam
	 *            LPARAM
	 * @return ���b�Z�[�W�ɂ��قȂ�
	 */
	public static native int SendMessage(long hwnd, int msg, int wparam,
			int lparam);

	/**
	 * ���b�Z�[�W���M.
	 * 
	 * @param hwnd
	 *            HWND
	 * @param msg
	 *            ���b�Z�[�W�iWM_*�j
	 * @param wparam
	 *            WPARAM
	 * @param lparam
	 *            LPARAM�i������j
	 * @return ���b�Z�[�W�ɂ��قȂ�
	 */
	public static native int SendMessage(long hwnd, int msg, int wparam,
			String lparam);

	/**
	 * ���b�Z�[�W���M.
	 * 
	 * @param hwnd
	 *            HWND
	 * @param msg
	 *            ���b�Z�[�W�iWM_*�j
	 * @param wparam
	 *            WPARAM
	 * @param lparam
	 *            LPARAM�i�o�C�g�z��j
	 * @return ���b�Z�[�W�ɂ��قȂ�
	 */
	public static native int SendMessage(long hwnd, int msg, int wparam,
			byte[] lparam);

	/**
	 * ���b�Z�[�W�L���[�ւ̑��M.
	 * 
	 * @param hwnd
	 *            HWND
	 * @param msg
	 *            ���b�Z�[�W�iWM_*�j
	 * @param wparam
	 *            WPARAM
	 * @param lparam
	 *            LPARAM
	 * @return ���b�Z�[�W�ɂ��قȂ�
	 */
	public static native boolean PostMessage(long hwnd, int msg, int wparam,
			int lparam);

	/**
	 * �E�B���h�E���ݔ��f.
	 * 
	 * @param hwnd
	 *            HWND
	 * @return true�F�E�B���h�E�����݂��Ă���Ƃ�
	 */
	public static native boolean IsWindow(long hwnd);

	/**
	 * �q�E�B���h�E���f.
	 * 
	 * @param hwndParent
	 *            �e�E�B���h�E��HWND
	 * @param hwnd
	 *            ��������HWND
	 * @return true�F�q�E�B���h�E���邢�͎q���E�B���h�E�̂Ƃ�
	 */
	public static native boolean IsChild(long hwndParent, long hwnd);

	/**
	 * �E�B���h�E�\����Ԑݒ�.
	 * 
	 * @param wnd
	 *            HWND
	 * @param cmdShow
	 *            �\�����@�i{@link WinUserConst#SW_HIDE}���j
	 * @return true�F�E�B���h�E���ȑO����\������Ă����Ƃ�<br>
	 *         false�F�E�B���h�E���ȑO�͔�\���������Ƃ�
	 */
	public static native boolean ShowWindow(long wnd, int cmdShow);

	/**
	 * �E�B���h�E�ړ�.
	 * <p>
	 * �E�B���h�E�̈ʒu�ƃT�C�Y��ύX����B<br>
	 * �g�b�v���x���E�B���h�E�̓X�N���[�����W�A�e�E�B���h�E�����E�B���h�E�̓N���C�A���g���W�Ŏw�肷��B
	 * </p>
	 * 
	 * @param hwnd
	 *            HWND
	 * @param x
	 *            X
	 * @param y
	 *            Y
	 * @param width
	 *            ��
	 * @param height
	 *            ����
	 * @param repaint
	 *            �ĕ`��I�v�V�����itrue�F�E�B���h�E��WM_PAINT��������j
	 * @return true�F����
	 * @throws Win32Exception
	 *             ���s��
	 */
	public static native boolean MoveWindow(long hwnd, int x, int y, int width,
			int height, boolean repaint) throws Win32Exception;

	/**
	 * �E�B���h�E�ʒu�ݒ�.
	 * <p>
	 * �E�B���h�E�̈ʒu�E�T�C�Y�EZ�I�[�_�[��ύX����B
	 * </p>
	 * 
	 * @param hwnd
	 *            HWND
	 * @param hWndInsertAfter
	 *            �z�u������HWND
	 * @param x
	 *            X
	 * @param y
	 *            Y
	 * @param cx
	 *            ��
	 * @param cy
	 *            ����
	 * @param flags
	 *            �E�B���h�E�ʒu�I�v�V�����iSWP_*�j
	 * @return true�F����
	 * @throws Win32Exception
	 *             ���s��
	 */
	public static native boolean SetWindowPos(long hwnd, long hWndInsertAfter,
			int x, int y, int cx, int cy, int flags) throws Win32Exception;

	/**
	 * �\����Ԕ��f.
	 * 
	 * @param wnd
	 *            HWND
	 * @return true�F�\������Ă���iWS_VISIBLE�X�^�C���������Ă���j
	 */
	public static native boolean IsWindowVisible(long wnd);

	/**
	 * �A�C�R����Ԕ��f.
	 * 
	 * @param wnd
	 *            HWND
	 * @return true�F�ŏ����i�A�C�R�����j����Ă���
	 */
	public static native boolean IsIconic(long wnd);

	/**
	 * �ő剻��Ԕ��f.
	 * 
	 * @param wnd
	 *            HWND
	 * @return true�F�ő剻����Ă���
	 */
	public static native boolean IsZoomed(long wnd);

	/**
	 * �_�C�A���O�{�b�N�X�F�R���g���[���擾.
	 * 
	 * @param hdlg
	 *            HWND
	 * @param id
	 *            �R���g���[���̎��ʎq
	 * @return �R���g���[����HWND
	 * @throws Win32Exception
	 *             ���s��
	 */
	public static native long GetDlgItem(long hdlg, int id)
			throws Win32Exception;

	/**
	 * �_�C�A���O�{�b�N�X�F�R���g���[�����l�ݒ�.
	 * 
	 * @param hdlg
	 *            HWND
	 * @param id
	 *            �R���g���[���̎��ʎq
	 * @param value
	 *            �l
	 * @param signed
	 *            ������t���邩�ǂ���
	 * @return ���������ꍇ�Atrue
	 * @throws Win32Exception
	 *             ���s��
	 */
	public static native boolean SetDlgItemInt(long hdlg, int id, int value,
			boolean signed) throws Win32Exception;

	/**
	 * �_�C�A���O�{�b�N�X�F�R���g���[�����l�擾.
	 * 
	 * @param hdlg
	 *            HWND
	 * @param id
	 *            �R���g���[���̎��ʎq
	 * @param translated
	 *            0�ԂɁA�����������ǂ����̏�Ԃ�Ԃ��inull�̏ꍇ�͉����Ԃ��Ȃ��j
	 * @param signed
	 *            true�F�����t���Ƃ��Ĉ���
	 * @return �l
	 * @throws Win32Exception
	 *             ���s��
	 */
	public static native int GetDlgItemInt(long hdlg, int id,
			boolean[] translated, boolean signed) throws Win32Exception;

	/**
	 * �_�C�A���O�{�b�N�X�F�R���g���[���e�L�X�g�ݒ�.
	 * 
	 * @param hdlg
	 *            HWND
	 * @param id
	 *            �R���g���[���̎��ʎq
	 * @param text
	 *            ������
	 * @return ���������ꍇ�Atrue
	 * @throws Win32Exception
	 *             ���s��
	 */
	public static native boolean SetDlgItemText(long hdlg, int id, String text)
			throws Win32Exception;

	/**
	 * �_�C�A���O�{�b�N�X�F�R���g���[���e�L�X�g�擾.
	 * 
	 * @param hdlg
	 *            HWND
	 * @param id
	 *            �R���g���[���̎��ʎq
	 * @param len
	 *            ������̍ő�T�C�Y
	 * @return ������
	 * @throws Win32Exception
	 *             ���s��
	 */
	public static native String GetDlgItemText(long hdlg, int id, int len)
			throws Win32Exception;

	/**
	 * �_�C�A���O�{�b�N�X�F�`�F�b�N�{�^���ݒ�.
	 * 
	 * @param hdlg
	 *            HWND
	 * @param nIDButton
	 *            �R���g���[���̎��ʎq
	 * @param uCheck
	 *            �l�iBST_*�j
	 * @return ���������ꍇ�Atrue
	 * @throws Win32Exception
	 *             ���s��
	 */
	public static native boolean CheckDlgButton(long hdlg, int nIDButton,
			int uCheck) throws Win32Exception;

	/**
	 * �_�C�A���O�{�b�N�X�F���W�I�{�^���ݒ�.
	 * 
	 * @param hdlg
	 *            HWND
	 * @param nIDFirstButton
	 *            �O���[�v���̍ŏ��̃{�^���̎��ʎq
	 * @param nIDLastButton
	 *            �O���[�v���̍Ō�̃{�^���̎��ʎq
	 * @param nIDCheckButton
	 *            �I���������{�^���̎��ʎq
	 * @return ���������ꍇ�Atrue
	 * @throws Win32Exception
	 *             ���s��
	 */
	public static native boolean CheckRadioButton(long hdlg,
			int nIDFirstButton, int nIDLastButton, int nIDCheckButton)
			throws Win32Exception;

	/**
	 * �_�C�A���O�{�b�N�X�F�`�F�b�N�{�^����Ԏ擾.
	 * 
	 * @param hdlg
	 *            HWND
	 * @param nIDButton
	 *            �{�^���̎��ʎq
	 * @return {@link WinUserConst#BST_CHECKED}��
	 */
	public static native int IsDlgButtonChecked(long hdlg, int nIDButton);

	/**
	 * �_�C�A���O�{�b�N�X�F�O���[�v���̎��R���g���[���擾.
	 * 
	 * @param hdlg
	 *            HWND
	 * @param hctl
	 *            �R���g���[����HWND
	 * @param previous
	 *            �����t���O�itrue�F�O�̃R���g���[���Afalse�F���̃R���g���[���j
	 * @return �R���g���[����HWND
	 * @throws Win32Exception
	 *             ���s��
	 */
	public static native long GetNextDlgGroupItem(long hdlg, long hctl,
			boolean previous) throws Win32Exception;

	/**
	 * �_�C�A���O�{�b�N�X�F�^�u�ړ��\�Ȏ��R���g���[���擾.
	 * 
	 * @param hdlg
	 *            HWND
	 * @param hctl
	 *            �R���g���[����HWND
	 * @param previous
	 *            �����t���O�itrue�F�O�̃R���g���[���Afalse�F���̃R���g���[���j
	 * @return �R���g���[����HWND
	 * @throws Win32Exception
	 *             ���s��
	 */
	public static native long GetNextDlgTabItem(long hdlg, long hctl,
			boolean previous) throws Win32Exception;

	/**
	 * �_�C�A���O�{�b�N�X�F�R���g���[�����ʎq�擾.
	 * 
	 * @param hctl
	 *            �R���g���[����HWND
	 * @return �R���g���[���̎��ʎq
	 * @throws Win32Exception
	 *             ���s��
	 */
	public static native int GetDlgCtrlID(long hctl) throws Win32Exception;

	/**
	 * �L�[�{�[�h�t�H�[�J�X�ݒ�.
	 * 
	 * @param wnd
	 *            HWND
	 * @return �ȑO�ɃL�[�{�[�h�t�H�[�J�X�������Ă����E�B���h�E��HWND
	 * @throws Win32Exception
	 *             ���s��
	 */
	public static native long SetFocus(long wnd) throws Win32Exception;

	/**
	 * �L�[�{�[�h�t�H�[�J�X�E�B���h�E�擾.
	 * 
	 * @return HWND �L�[�{�[�h�t�H�[�J�X�����E�B���h�E��HWND�i�Y���E�B���h�E�������ꍇ�A0�j
	 * @see #GetForegroundWindow()
	 */
	public static native long GetFocus();

	/**
	 * �A�N�e�B�u�E�B���h�E�ݒ�.
	 * <p>
	 * �E�B���h�E���A�N�e�B�u�ɂ���B
	 * </p>
	 * 
	 * @param wnd
	 *            HWND
	 * @return �ȑO�A�N�e�B�u�������E�B���h�E��HWND
	 * @throws Win32Exception
	 *             ���s��
	 */
	public static native long SetActiveWindow(long wnd) throws Win32Exception;

	/**
	 * �A�N�e�B�u�E�B���h�E�擾.
	 * 
	 * @return HWND �A�N�e�B�u�ȃE�B���h�E��HWND�i�Y���E�B���h�E���Ȃ��ꍇ�Anull�j
	 * @see #GetForegroundWindow()
	 */
	public static native long GetActiveWindow();

	/**
	 * ���͉ېݒ�.
	 * <p>
	 * �����ɂ���ƃ}�E�X���͂�L�[�{�[�h���͂��󂯕t���Ȃ��B�O���[�A�E�g��ԁH
	 * </p>
	 * 
	 * @param wnd
	 *            HWND
	 * @param enable
	 *            true�F�L���Afalse�F����
	 * @return true�F���ɖ����ɂȂ��Ă����Ƃ�
	 * @throws Win32Exception
	 *             ���s��
	 */
	public static native boolean EnableWindow(long wnd, boolean enable)
			throws Win32Exception;

	/**
	 * ���͉۔��f.
	 * <p>
	 * �}�E�X���͂�L�[�{�[�h���͂��󂯕t�����ԁi�O���[�A�E�g�H�j���ǂ�����Ԃ��B
	 * </p>
	 * 
	 * @param wnd
	 *            HWND
	 * @return true�F�L���Afalse�F����
	 */
	public static native boolean IsWindowEnabled(long wnd);

	/**
	 * �t�H�A�O���E���h�E�B���h�E�ݒ�.
	 * <p>
	 * �E�B���h�E���t�H�A�O���E���h�i���A�N�e�B�u�j�ɂ���B
	 * </p>
	 * 
	 * @param wnd
	 *            HWND
	 * @return true�F�t�H�A�O���E���h�ɂȂ���
	 */
	public static native boolean SetForegroundWindow(long wnd);

	/**
	 * �t�H�A�O���E���h�E�B���h�E�擾.
	 * 
	 * @return HWND
	 */
	public static native long GetForegroundWindow();

	/**
	 * �X�N���[���o�[�\����Ԑݒ�.
	 * 
	 * @param wnd
	 *            HWND
	 * @param bar
	 *            �ݒ�ΏہiSB_*�j
	 * @param show
	 *            true�F�\���Afalse�F��\��
	 * @return true�F����
	 * @throws Win32Exception
	 *             ���s��
	 */
	public static native boolean ShowScrollBar(long wnd, int bar, boolean show)
			throws Win32Exception;

	/**
	 * �X�N���[���o�[�ېݒ�.
	 * 
	 * @param wnd
	 *            HWND
	 * @param bar
	 *            �ݒ�ΏہiSB_*�j
	 * @param arrows
	 *            ���̗L�������ݒ�iESB_*�j
	 * @return true�F����<br>
	 *         false�F���ɐݒ肳��Ă���
	 * @throws Win32Exception
	 *             ���s��
	 */
	public static native boolean EnableScrollBar(long wnd, int bar, int arrows)
			throws Win32Exception;

	/**
	 * �E�B���h�E�e�L�X�g�ݒ�.
	 * <p>
	 * �E�B���h�E�̃^�C�g����R���g���[���̃e�L�X�g��ύX����B<br>
	 * ���A�v���P�[�V�����̃e�L�X�g�͕ύX�ł��Ȃ��B
	 * </p>
	 * 
	 * @param wnd
	 *            HWND
	 * @param string
	 *            �e�L�X�g
	 * @return true�F����
	 * @throws Win32Exception
	 *             ���s��
	 */
	public static native boolean SetWindowText(long wnd, String string)
			throws Win32Exception;

	/**
	 * �E�B���h�E�e�L�X�g�擾.
	 * <p>
	 * �E�B���h�E�̃^�C�g����R���g���[���̃e�L�X�g���擾����B
	 * </p>
	 * 
	 * @param wnd
	 *            HWND
	 * @return �e�L�X�g
	 * @throws Win32Exception
	 *             ���s��
	 */
	public static native String getWindowText(long wnd) throws Win32Exception;

	/**
	 * �E�B���h�E�e�L�X�g���擾.
	 * 
	 * @param wnd
	 *            HWND
	 * @return �E�B���h�E�e�L�X�g�̕�����
	 * @throws Win32Exception
	 *             ���s��
	 */
	public static native int GetWindowTextLength(long wnd)
			throws Win32Exception;

	/**
	 * �N���C�A���g�̈�擾.
	 * <p>
	 * �N���C�A���g�̈���N���C�A���g���W�ŕԂ��̂ŁA����͏��(0,0)�ƂȂ�B
	 * </p>
	 * 
	 * @param wnd
	 *            HWND
	 * @param r
	 *            ���W�l�i�N���C�A���g���W�n�j
	 * @return true�F����
	 * @throws Win32Exception
	 *             ���s��
	 */
	public static native boolean GetClientRect(long wnd, Rectangle r)
			throws Win32Exception;

	/**
	 * �E�B���h�E�̈�擾.
	 * 
	 * @param wnd
	 *            HWND
	 * @param r
	 *            ���W�l�i�X�N���[�����W�n�j
	 * @return true�F����
	 * @throws Win32Exception
	 *             ���s��
	 */
	public static native boolean GetWindowRect(long wnd, Rectangle r)
			throws Win32Exception;

	/**
	 * ���b�Z�[�W�{�b�N�X.
	 * 
	 * @param wnd
	 *            HWND
	 * @param text
	 *            �e�L�X�g
	 * @param caption
	 *            �^�C�g��
	 * @param type
	 *            �X�^�C���i{@link WinUserConst#MB_OK}���j
	 * @return �߂�l�i{@link WinUserConst#IDOK}���j
	 * @throws Win32Exception
	 *             ���s��
	 */
	public static native int MessageBox(long wnd, String text, String caption,
			int type) throws Win32Exception;

	/**
	 * �X�N���[�����W�ϊ�.
	 * 
	 * @param wnd
	 *            HWND
	 * @param point
	 *            �N���C�A���g���W�i�X�N���[�����W�ɕϊ������j
	 * @return true�F����
	 * @throws Win32Exception
	 *             ���s��
	 */
	public static native boolean ClientToScreen(long wnd, Point point)
			throws Win32Exception;

	/**
	 * �N���C�A���g���W�ϊ�.
	 * 
	 * @param wnd
	 *            HWND
	 * @param point
	 *            �X�N���[�����W�i�N���C�A���g���W�ɕϊ������j
	 * @return true�F����
	 * @throws Win32Exception
	 *             ���s��
	 */
	public static native boolean ScreenToClient(long wnd, Point point)
			throws Win32Exception;

	/**
	 * �w��ʒu�E�B���h�E�擾.
	 * 
	 * @param x
	 *            X
	 * @param y
	 *            Y
	 * @return HWND�i�E�B���h�E�������ꍇ�Anull�j
	 * @see #ChildWindowFromPoint(long, int, int)
	 */
	public static native long WindowFromPoint(int x, int y);

	/**
	 * �w��ʒu�q�E�B���h�E�擾.
	 * 
	 * @param x
	 *            X�i�N���C�A���g���W�n�j
	 * @param y
	 *            Y�i�N���C�A���g���W�n�j
	 * @return HWND�i���W���e�E�B���h�E�̊O�ɂ���ꍇ�Anull�j
	 * @see #ChildWindowFromPointEx(long, int, int, int)
	 */
	public static native long ChildWindowFromPoint(long hwnd, int x, int y);

	/**
	 * �w��ʒu�q�E�B���h�E�擾.
	 * 
	 * @param x
	 *            X�i�N���C�A���g���W�n�j
	 * @param y
	 *            Y�i�N���C�A���g���W�n�j
	 * @param flags
	 *            ��������Ώۂ������I�v�V�����iCWP_*�j
	 * @return HWND�i���W���e�E�B���h�E�̊O�ɂ���ꍇ�⎸�s�����ꍇ�Anull�j
	 */
	public static native long ChildWindowFromPointEx(long hwnd, int x, int y,
			int flags);

	/**
	 * �E�B���h�E���擾.
	 * 
	 * @param hwnd
	 *            HWND
	 * @param index
	 *            ���̃I�t�Z�b�g
	 * @return ���i32bit�j
	 * @throws Win32Exception
	 *             ���s��
	 * @see #SetParent(long, long)
	 */
	public static native int GetWindowLong(long hwnd, int index)
			throws Win32Exception;

	/**
	 * �E�B���h�E���ݒ�.
	 * 
	 * @param hwnd
	 *            HWND
	 * @param index
	 *            ���̃I�t�Z�b�g
	 * @param value
	 *            �V�����l
	 * @return �ύX�O�̒l
	 * @throws Win32Exception
	 *             ���s��
	 * @see #SetParent(long, long)
	 */
	public static native int SetWindowLong(long hwnd, int index, int value)
			throws Win32Exception;

	/**
	 * �f�X�N�g�b�v�擾.
	 * 
	 * @return HWND
	 * @see #GetWindow(long, int)
	 */
	public static native long GetDesktopWindow();

	/**
	 * �e�E�B���h�E�擾.
	 * 
	 * @param wnd
	 *            HWND
	 * @return �e�E�B���h�E�i�܂��̓I�[�i�[�E�B���h�E�j��HWND<br>
	 *         �g�b�v���x���E�B���h�E�̏ꍇ�A0
	 * @throws Win32Exception
	 *             ���s��
	 * @see #GetAncestor(long, int)
	 */
	public static native long GetParent(long wnd) throws Win32Exception;

	/**
	 * �e�E�B���h�E�ݒ�.
	 * 
	 * @param wnd
	 *            HWND
	 * @param wndParent
	 *            �e�E�B���h�E�inull�̏ꍇ�A�f�X�N�g�b�v�j
	 * @return ���O�̐e�E�B���h�E��HWND
	 * @throws Win32Exception
	 *             ���s��
	 */
	public static native long SetParent(long wnd, long wndParent)
			throws Win32Exception;

	/**
	 * �q�E�B���h�E��.
	 * 
	 * @param hwnd
	 *            HWND
	 * @param list
	 *            �q�E�B���h�E�i{@link jp.hishidama.win32.JWnd}�j���������
	 * @return true�F����
	 * @throws Win32Exception
	 *             ���s��
	 * @see #GetWindow(long, int)
	 */
	public static native boolean EnumChildWindows(long hwnd, List list)
			throws Win32Exception;

	/**
	 * �g�b�v���x���E�B���h�E��.
	 * 
	 * @param list
	 *            �E�B���h�E�i{@link jp.hishidama.win32.JWnd}�j���������
	 * @return true�F����
	 * @throws Win32Exception
	 *             ���s��
	 * @see #GetWindow(long, int)
	 */
	public static native boolean EnumWindows(List list) throws Win32Exception;

	/**
	 * �g�b�v���x���E�B���h�E����.
	 * 
	 * @param className
	 *            �N���X��
	 * @param windowName
	 *            �E�B���h�E��
	 * @return HWND
	 * @throws Win32Exception
	 *             ���s��
	 * @see #getClassName(long)
	 * @see #getWindowText(long)
	 */
	public static native long FindWindow(String className, String windowName)
			throws Win32Exception;

	/**
	 * �E�B���h�E�N���X���擾.
	 * 
	 * @param hwnd
	 *            HWND
	 * @return �N���X��
	 * @throws Win32Exception
	 *             ���s��
	 */
	public static native String getClassName(long hwnd) throws Win32Exception;

	/**
	 * ��ԏ�̎q�E�B���h�E�擾.
	 * 
	 * @param hwnd
	 *            HWND
	 * @return HWND�i�q�E�B���h�E�������Ȃ��ꍇ�A0�j
	 * @throws Win32Exception
	 *             ���s��
	 */
	public static native long GetTopWindow(long hwnd) throws Win32Exception;

	/**
	 * ���E�B���h�E�擾.
	 * 
	 * @param hwnd
	 *            HWND
	 * @param cmd
	 *            �����iGW_HWNDNEXT �܂��� GW_HWNDPREV�j
	 * @return HWND
	 * @throws Win32Exception
	 *             ���s��
	 */
	public static long GetNextWindow(long hwnd, int cmd) throws Win32Exception {
		return GetWindow(hwnd, cmd);
	}

	/**
	 * �E�B���h�E�擾.
	 * 
	 * @param wnd
	 *            HWND
	 * @param cmd
	 *            �֌W�iGW_*�j
	 * @return HWND
	 * @throws Win32Exception
	 *             ���s��
	 */
	public static native long GetWindow(long wnd, int cmd)
			throws Win32Exception;

	/**
	 * �c��E�B���h�E�擾.
	 * 
	 * @param wnd
	 *            HWND
	 * @param flags
	 *            �p�����[�^�iGA_*�j
	 * @return HWND
	 * @see #GetParent(long)
	 */
	public static native long GetAncestor(long wnd, int flags);
}
