package jp.hishidama.win32;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.UnsupportedEncodingException;
import java.util.*;

import jp.hishidama.win32.api.Win32Exception;
import jp.hishidama.win32.api.WinUser;
import jp.hishidama.win32.api.WinUserConst;

/**
 * Windows�E�B���h�E�N���X.
 * <p>
 * VisualC++MFC��CWnd��͂����N���X�B<br>
 * ���g�͂���Win32API���Ăяo���Ă��邾���Ȃ̂ŁAWin32API�̎g�p��̒��ӂ��悭�ǂݗp�@�E�p�ʂ�����Đ��������g���������B
 * </p>
 * <p>
 * �g�p����ɂ͈ȉ��̐ݒ肪�K�v�B<br>
 * <ul>
 * <li><a target="hishidama"
 * href="http://www.ne.jp/asahi/hishidama/home/soft/java/hmwin32.html">hmwin32.dll</a>��<a
 * target="hishidama"
 * href="http://www.ne.jp/asahi/hishidama/home/tech/eclipse/java.html#���s���p�X��ǉ�������@">���s���̃p�X�ɒǉ�</a>����B</li>
 * </ul>
 * </p>
 * <p>
 * ���N���X�ł́AWin32API�Ăяo����GetLastError()��0�ȊO�������ꍇ�A{@link Win32Exception}��catch����0��null��Ԃ��B<br>
 * {@link #setThrowLastError(boolean)}��true���Z�b�g���邱�Ƃɂ��A���̗�O�����̂܂܃X���[����悤�ɂȂ�B
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/hmwin32.html">�Ђ�����</a>
 * @version 2007.11.01
 */
public class JWnd implements WinUserConst {

	protected long m_hWnd;

	private JWnd owner;

	protected static boolean throwLastError = false;

	protected Win32Exception lastError;

	protected static Win32Exception s_lastError;

	/**
	 * Win32Exception�X���[�L���ݒ�.
	 * 
	 * @param thr
	 *            true�FWin32Exception�������ɂ�����X���[����<br>
	 *            false�F�X���[���Ȃ��B{@link #getLastError()}�ɂ��擾����B
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
	 * Win32Exception�擾.
	 * <p>
	 * JWnd�̃C���X�^���X���\�b�h�Ăяo�����ɔ�������Win32Exception��Ԃ��B
	 * </p>
	 * 
	 * @return LastError�i�G���[���������Ă��Ȃ��ꍇ�Anull�j
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
	 * Win32Exception�擾�istatic���\�b�h�p�j.
	 * <p>
	 * JWnd�̃X�^�e�B�b�N���\�b�h�Ăяo�����ɔ�������Win32Exception��Ԃ��B
	 * </p>
	 * 
	 * @return LastError�i�G���[���������Ă��Ȃ��ꍇ�Anull�j
	 */
	public static Win32Exception getLastErrorStatic() {
		return s_lastError;
	}

	protected static Map handleMap = null;

	// �n���h�����}�b�v�ɕێ�����̂ŁA�j�����ꂸ�ɗ��܂��Ă��������ȋC������B
	// ������A����Ă݂͂����� �Ƃ肠�����g��Ȃ��B
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

	// ����C���X�^���X������ĕԂ��Ă邯�ǁA
	// owner�Ƃ��͂�΂����ȋC������B
	protected static JWnd FromHandle(long h) {
		if (h == 0)
			return null;
		return new JWnd(h);
	}

	//
	// �\�z
	//

	/**
	 * �R���X�g���N�^�[
	 * 
	 * @param hWnd
	 *            �E�B���h�E�n���h���iHWND�j
	 */
	public JWnd(long hWnd) {
		m_hWnd = hWnd;
	}

	//
	// ������
	//

	/**
	 * �E�B���h�E�̃X�^�C���擾.
	 * 
	 * @return �X�^�C��
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
	 * �E�B���h�E�̊g���X�^�C���擾.
	 * 
	 * @return �g���X�^�C��
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
	 * �E�B���h�E�̃X�^�C���ύX.
	 * 
	 * @param remove
	 *            ��������X�^�C��
	 * @param add
	 *            �ǉ�����X�^�C��
	 * @return true�F����
	 */
	public boolean ModifyStyle(int remove, int add) {
		return modifyStyle(m_hWnd, GWL_STYLE, remove, add, 0);
	}

	/**
	 * �E�B���h�E�̃X�^�C���ύX.
	 * 
	 * @param remove
	 *            ��������X�^�C��
	 * @param add
	 *            �ǉ�����X�^�C��
	 * @param flags
	 *            SetWindowPos�ɓn���t���O
	 * @return true�F����
	 */
	public boolean ModifyStyle(int remove, int add, int flags) {
		return modifyStyle(m_hWnd, GWL_STYLE, remove, add, flags);
	}

	/**
	 * �E�B���h�E�̊g���X�^�C���ύX.
	 * 
	 * @param remove
	 *            ��������g���X�^�C��
	 * @param add
	 *            �ǉ�����g���X�^�C��
	 * @return true�F����
	 */
	public boolean ModifyStyleEx(int remove, int add) {
		return modifyStyle(m_hWnd, GWL_EXSTYLE, remove, add, 0);
	}

	/**
	 * �E�B���h�E�̊g���X�^�C���ύX.
	 * 
	 * @param remove
	 *            ��������g���X�^�C��
	 * @param add
	 *            �ǉ�����g���X�^�C��
	 * @param flags
	 *            SetWindowPos�ɓn���t���O
	 * @return true�F����
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
	 * �E�B���h�E�n���h���擾.
	 * <p>
	 * �����ŕێ����Ă���m_hWnd��Ԃ��B
	 * </p>
	 * 
	 * @return �E�B���h�E�n���h���iHWND�j
	 */
	public long GetSafeHwnd() {
		return m_hWnd;
	}

	//
	// �E�B���h�E���
	//

	/**
	 * ���͉۔��f.
	 * 
	 * @return true�F�}�E�X��L�[�{�[�h����̓��͂������Ă���
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
	 * ���͉ېݒ�.
	 * 
	 * @param enable
	 *            true�F�}�E�X��L�[�{�[�h����̓��͂�������
	 * @return �ݒ�O�̏��
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
	 * �A�N�e�B�u�E�B���h�E�擾.
	 * 
	 * @return JWnd�i�A�N�e�B�u�ȃE�B���h�E�������ꍇ�Anull�j
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
	 * �A�N�e�B�u�E�B���h�E��.
	 * <p>
	 * �����̃E�B���h�E���A�N�e�B�u�ɂ���B
	 * </p>
	 * 
	 * @return ���O�ɃA�N�e�B�u�������E�B���h�E�i�����ꍇ��null�j
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
	 * �t�H�[�J�X�̂���E�B���h�E�擾.
	 * 
	 * @return JWnd�i�E�B���h�E�������ꍇ�Anull�j
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
	 * �t�H�[�J�X�ݒ�.
	 * <p>
	 * �����̃E�B���h�E�ɓ��̓t�H�[�J�X��v������B
	 * </p>
	 * 
	 * @return ���O�Ƀt�H�[�J�X�������Ă����E�B���h�E�i�����ꍇ��null�j
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
	 * �f�X�N�g�b�v�E�B���h�E�擾.
	 * 
	 * @return �f�X�N�g�b�v��JWnd
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
	 * �őO�ʃE�B���h�E�擾.
	 * 
	 * @return �őO�ʂ̃E�B���h�E��JWnd�i�����ꍇ��null�j
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
	 * �őO�ʈړ�.
	 * <p>
	 * �����̃E�B���h�E���őO�ʂɏo���B
	 * </p>
	 * 
	 * @return true�F����
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
	// �E�B���h�E�T�C�Y�ƈʒu
	//

	/**
	 * �ŏ������f.
	 * 
	 * @return true�F�ŏ����i�A�C�R�����j���Ă���
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
	 * �ő剻���f.
	 * 
	 * @return true�F�ő剻���Ă���
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
	 * �E�B���h�E�ړ�.
	 * <p>
	 * �E�B���h�E�̈ʒu�ƃT�C�Y��ύX���A�ĕ`�悷��B
	 * </p>
	 * 
	 * @param x
	 *            X
	 * @param y
	 *            Y
	 * @param width
	 *            ��
	 * @param height
	 *            ����
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
	 * �E�B���h�E�ړ�.
	 * <p>
	 * �E�B���h�E�̈ʒu�ƃT�C�Y��ύX����B
	 * </p>
	 * 
	 * @param x
	 *            X
	 * @param y
	 *            Y
	 * @param width
	 *            ��
	 * @param height
	 *            ����
	 * @param repaint
	 *            true�F�ĕ`�悷��
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
	 * �E�B���h�E�ړ�.
	 * <p>
	 * �E�B���h�E�̈ʒu�ƃT�C�Y��ύX���A�ĕ`�悷��B
	 * </p>
	 * 
	 * @param r
	 *            �͈�
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
	 * �E�B���h�E�ړ�.
	 * <p>
	 * �E�B���h�E�̈ʒu�ƃT�C�Y��ύX����B
	 * </p>
	 * 
	 * @param r
	 *            �͈�
	 * @param repaint
	 *            true�F�ĕ`�悷��
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
	 * �E�B���h�E�ʒu�ύX.
	 * <p>
	 * �E�B���h�E�̈ʒu�E�T�C�Y�EZ�I�[�_�[��ύX����B
	 * </p>
	 * 
	 * @param wndInsertAfter
	 *            �ʏ��JWnd�I�u�W�F�N�g �܂���{@link #wndTop}, {@link #wndBottom},
	 *            {@link #wndTopMost}, {@link #wndNoTopMost}
	 * @param x
	 *            X
	 * @param y
	 *            Y
	 * @param cx
	 *            ��
	 * @param cy
	 *            ����
	 * @param flags
	 *            �ʒu�ύX�I�v�V�����y�уT�C�Y�ύX�I�v�V����
	 * @return true�F����
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
	 * Z�I�[�_�[�̐擪
	 * 
	 * @see #SetWindowPos(JWnd, int, int, int, int, int)
	 */
	public static final JWnd wndTop = new JWnd(HWND_TOP);

	/**
	 * Z�I�[�_�[�̍Ō��
	 * 
	 * @see #SetWindowPos(JWnd, int, int, int, int, int)
	 */
	public static final JWnd wndBottom = new JWnd(HWND_BOTTOM);

	/**
	 * �ŏ�ʂłȂ��S�ẴE�B���h�E�̏�
	 * 
	 * @see #SetWindowPos(JWnd, int, int, int, int, int)
	 */
	public static final JWnd wndTopMost = new JWnd(HWND_TOPMOST);

	/**
	 * �ŏ�ʂłȂ��S�ẴE�B���h�E�̐擪
	 * 
	 * @see #SetWindowPos(JWnd, int, int, int, int, int)
	 */
	public static final JWnd wndNoTopMost = new JWnd(HWND_NOTOPMOST);

	/**
	 * �E�B���h�E�͈͎擾.
	 * 
	 * @return �͈́i�X�N���[�����W�j�i�擾�G���[����null�j
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
	 * �N���C�A���g�̈�擾.
	 * 
	 * @return �͈́i�N���C�A���g���W�n�Ȃ̂ŁA����͏��(0,0)�j�i�擾�G���[����null�j
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
	// �E�B���h�E�A�N�Z�X
	//

	/**
	 * �E�B���h�E�T��.
	 * <p>
	 * �E�B���h�E�^�C�g�������ŒT���ꍇ�AclassName��null���w�肵�AwindowName�ɐ��m�ȃ^�C�g�����w�肷��B<br>
	 * �E�B���h�E�^�C�g���̈ꕔ�ŒT�������ꍇ��{@link #enumWindows()}���g�p����B
	 * </p>
	 * 
	 * @param className
	 *            Windows�́g�E�B���h�E�N���X�h
	 * @param windowName
	 *            �E�B���h�E�̃^�C�g��
	 * @return JWnd�i�E�B���h�E��������Ȃ������ꍇ�Anull�j
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
	 * ���E�B���h�E�擾.
	 * 
	 * @return JWnd�i���s�����ꍇ�Anull�j
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
	 * ���E�B���h�E�擾.
	 * 
	 * @param flag
	 *            �����iGW_HWNDNEXT �܂��� GW_HWNDPREV�j
	 * @return JWnd�i���s�����ꍇ�Anull�j
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
	 * �E�B���h�E��.
	 * <p>
	 * �S�E�B���h�E��񋓂���B<br>
	 * �ꗗ�̒�����{@link #GetWindowText()}�����g���ĖړI�̃E�B���h�E��T���B
	 * </p>
	 * 
	 * @return JWnd�̃��X�g�i���s�����ꍇ�Anull�j
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
	 * �q�E�B���h�E��.
	 * 
	 * @return JWnd�̃��X�g�i���s�����ꍇ�Anull�j
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
	 * �I�[�i�[�擾.
	 * 
	 * @return �I�[�i�[�i�G���[�܂��̓I�[�i�[�������ꍇ�Anull�j
	 */
	public JWnd GetOwner() {
		return (owner != null) ? owner : GetParent();
	}

	/**
	 * �I�[�i�[�ݒ�.
	 * 
	 * @param owner
	 *            �I�[�i�[
	 */
	public void SetOwner(JWnd owner) {
		this.owner = owner;
	}

	/**
	 * �g�b�v���x���q�E�B���h�E�擾.
	 * 
	 * @return JWnd�i�q�E�B���h�E�������ꍇ�Anull�j
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
	 * �E�B���h�E�擾.
	 * 
	 * @param cmd
	 *            �v��
	 * @return JWnd�i�����ꍇ�Anull�j
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
	 * �E�B���h�E���ݔ��f.
	 * 
	 * @return �E�B���h�E�������Ă���Ƃ��Atrue
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
	 * ���n�q�E�B���h�E���f.
	 * 
	 * @param wnd
	 *            �����ΏۃE�B���h�E
	 * @return �q�E�B���h�E�̂Ƃ�true
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
	 * �e�E�B���h�E�擾.
	 * 
	 * @return �e�E�B���h�E�i�G���[�܂��͐e�E�B���h�E�������ꍇ�Anull�j
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
	 * �e�E�B���h�E�ݒ�.
	 * 
	 * @param newParent
	 *            �e�E�B���h�E
	 * @return ���O�̐e�E�B���h�E
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
	 * �c��E�B���h�E�擾.
	 * 
	 * @param flags
	 *            �p�����[�^�iGA_*�j
	 * @return �c��E�B���h�E
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
	 * �w��ʒu�E�B���h�E�擾.
	 * 
	 * @param pt
	 *            �ʒu
	 * @return JWnd�i�����ꍇ�Anull�j
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
	 * �w��ʒu�q�E�B���h�E�擾.
	 * 
	 * @param pt
	 *            �ʒu
	 * @return JWnd�i�����ꍇ�Anull�j
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
	 * �w��ʒu�q�E�B���h�E�擾.
	 * 
	 * @param x
	 * @param y
	 * @return JWnd�i�����ꍇ�Anull�j
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
	 * �w��ʒu�q�E�B���h�E�擾.
	 * 
	 * @param pt
	 *            �ʒu
	 * @param flags
	 *            CWP�t���O�̘_���a�i{@link #CWP_ALL}���j
	 * @return JWnd�i�����ꍇ�Anull�j
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
	 * �_�C�A���O�{�b�N�X�R���g���[���擾.
	 * 
	 * @param id
	 *            �R���g���[���̎��ʎq
	 * @return �R���g���[����JWnd�i�擾�Ɏ��s�����ꍇ�Anull�j
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
	 * �_�C�A���O�{�b�N�X�F�R���g���[�����ʎq�擾.
	 * 
	 * @return �R���g���[���̎��ʎq�i�擾�Ɏ��s�����ꍇ�A0�j
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
	// �X�V�E�h��ׂ�
	//

	/**
	 * �E�B���h�E�\����Ԑݒ�.
	 * 
	 * @param cmdShow
	 *            �\�����@
	 * @return true�F�E�B���h�E�����O�ɕ\������Ă���<br>
	 *         false�F���O�ɔ�\��������
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
	 * ����Ԕ��f.
	 * 
	 * @return true�F�����
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
	 * �X�N���[���o�[��Ԑݒ�.
	 * <p>
	 * �X�N���[���o�[�̗L��������؂�ւ���B
	 * </p>
	 * 
	 * @param bar
	 *            �X�N���[���o�[�iSB�t���O�j
	 * @param arrowFlags
	 *            ��ԁiESB�t���O�j
	 * @return true�F�ύX�����ꍇ<br>
	 *         false�F��Ԃ������������ꍇ �܂��̓G���[
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
	// ���W�}�b�s���O
	//

	/**
	 * �X�N���[�����W�ϊ�.
	 * 
	 * @param x
	 *            �N���C�A���g���W
	 * @param y
	 *            �N���C�A���g���W
	 * @return �X�N���[�����W
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
	 * �X�N���[�����W�ϊ�.
	 * 
	 * @param pt
	 *            �N���C�A���g���W
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
	 * �X�N���[�����W�ϊ�.
	 * 
	 * @param r
	 *            �N���C�A���g���W
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
	 * �N���C�A���g���W�ϊ�.
	 * 
	 * @param x
	 *            �X�N���[�����W
	 * @param y
	 *            �X�N���[�����W
	 * @return �N���C�A���g���W
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
	 * �N���C�A���g���W�ϊ�.
	 * 
	 * @param pt
	 *            �X�N���[�����W
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
	 * �N���C�A���g���W�ϊ�.
	 * 
	 * @param r
	 *            �X�N���[�����W
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
	// �E�B���h�E�e�L�X�g
	//

	/**
	 * �E�B���h�E�^�C�g���ݒ�.
	 * 
	 * @param string
	 *            �^�C�g��
	 * @return true�F����
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
	 * �E�B���h�E�^�C�g���擾.
	 * 
	 * @return �^�C�g���i�擾���s����null�j
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
	 * �E�B���h�E�^�C�g�����擾.
	 * 
	 * @return �^�C�g���̒���
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
	 * �E�B���h�E�N���X���擾.
	 * 
	 * @return �E�B���h�E�̃N���X��
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
	// �X�N���[��
	//

	/**
	 * �X�N���[���o�[�\���ݒ�.
	 * 
	 * @param bar
	 *            �X�N���[���o�[�iSB�t���O�j
	 * @param show
	 *            true�F�\��
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
	// �_�C�A���O�{�b�N�X
	//

	/**
	 * �_�C�A���O�{�b�N�X�F�`�F�b�N�{�^���ݒ�.
	 * 
	 * @param nIDButton
	 *            �R���g���[���̎��ʎq
	 * @param uCheck
	 *            �l�i{@link WinUserConst#BST_CHECKED}���j
	 * @return ���������ꍇ�Atrue
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
	 * �_�C�A���O�{�b�N�X�F�`�F�b�N�{�^����Ԏ擾.
	 * 
	 * @param nIDButton
	 *            �{�^���̎��ʎq
	 * @return {@link WinUserConst#BST_CHECKED}��
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
	 * �_�C�A���O�{�b�N�X�F���W�I�{�^���ݒ�.
	 * 
	 * @param nIDFirstButton
	 *            �O���[�v���̍ŏ��̃{�^���̎��ʎq
	 * @param nIDLastButton
	 *            �O���[�v���̍Ō�̃{�^���̎��ʎq
	 * @param nIDCheckButton
	 *            �I���������{�^���̎��ʎq
	 * @return ���������ꍇ�Atrue
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
	 * �_�C�A���O�{�b�N�X�F���W�I�{�^���擾.
	 * 
	 * @param nIDFirstButton
	 *            �O���[�v���̍ŏ��̃{�^���̎��ʎq
	 * @param nIDLastButton
	 *            �O���[�v���̍Ō�̃{�^���̎��ʎq
	 * @return �`�F�b�N����Ă���{�^���̎��ʎq�i�����ꍇ�A0�j
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
	 * �_�C�A���O�{�b�N�X�F�R���g���[�����l�ݒ�.
	 * 
	 * @param id
	 *            �R���g���[���̎��ʎq
	 * @param value
	 *            �l
	 * @param signed
	 *            ������t���邩�ǂ���
	 * @return ���������ꍇ�Atrue
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
	 * �_�C�A���O�{�b�N�X�F�R���g���[�����l�擾.
	 * 
	 * @param id
	 *            �R���g���[���̎��ʎq
	 * @param translated
	 *            0�ԂɁA�����������ǂ����̏�Ԃ�Ԃ��inull�̏ꍇ�͉����Ԃ��Ȃ��j
	 * @param signed
	 *            true�F�����t���Ƃ��Ĉ���
	 * @return �l
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
	 * �_�C�A���O�{�b�N�X�F�R���g���[���e�L�X�g�ݒ�.
	 * 
	 * @param id
	 *            �R���g���[���̎��ʎq
	 * @param text
	 *            ������
	 * @return ���������ꍇ�Atrue
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
	 * �_�C�A���O�{�b�N�X�F�R���g���[���e�L�X�g�擾.
	 * 
	 * @param id
	 *            �R���g���[���̎��ʎq
	 * @return ������
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
	 * �_�C�A���O�{�b�N�X�F�O���[�v���̎��R���g���[���擾.
	 * 
	 * @param ctl
	 *            �R���g���[��
	 * @param previous
	 *            �����t���O�itrue�F�O�̃R���g���[���Afalse�F���̃R���g���[���j
	 * @return �R���g���[���i�擾�Ɏ��s�����ꍇ�Anull�j
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
	 * �_�C�A���O�{�b�N�X�F�^�u�ړ��\�Ȏ��R���g���[���擾.
	 * 
	 * @param ctl
	 *            �R���g���[��
	 * @param previous
	 *            �����t���O�itrue�F�O�̃R���g���[���Afalse�F���̃R���g���[���j
	 * @return �R���g���[���i�擾�Ɏ��s�����ꍇ�Anull�j
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
	// �A���[�g�֐�
	//

	/**
	 * ���b�Z�[�W�o��.
	 * 
	 * @param text
	 *            ���b�Z�[�W
	 * @return �l
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
	 * ���b�Z�[�W�o��.
	 * 
	 * @param text
	 *            ���b�Z�[�W
	 * @param caption
	 *            �^�C�g��
	 * @return �l
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
	 * ���b�Z�[�W�o��.
	 * 
	 * @param text
	 *            ���b�Z�[�W
	 * @param caption
	 *            �^�C�g��
	 * @param type
	 *            �^�C�v
	 * @return �l
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
	// �E�B���h�E���b�Z�[�W
	//

	/**
	 * ���b�Z�[�W���M�isned�j.
	 * 
	 * @param message
	 *            ���b�Z�[�W
	 * @param wParam
	 *            �p�����[�^
	 * @param lParam
	 *            �p�����[�^
	 * @return ���b�Z�[�W�ɂ��
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
	 * ���b�Z�[�W���M�isned�j.
	 * 
	 * @param message
	 *            ���b�Z�[�W
	 * @param wParam
	 *            �p�����[�^
	 * @param lParam
	 *            �p�����[�^�i������j
	 * @return ���b�Z�[�W�ɂ��
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
	 * ���b�Z�[�W���M�isned�j.
	 * 
	 * @param message
	 *            ���b�Z�[�W
	 * @param wParam
	 *            �p�����[�^
	 * @param lParam
	 *            �p�����[�^�i�o�C�g�z��j
	 * @return ���b�Z�[�W�ɂ��
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
	 * ���b�Z�[�W���M�isned�j.
	 * <p>
	 * LPARAM��������擾�p�̃o�b�t�@�Ŗ߂�l���������ł��郁�b�Z�[�W�Ŏg�p�\�B<br>
	 * ��FWM_GETTEXT
	 * </p>
	 * 
	 * @param message
	 *            ���b�Z�[�W
	 * @param wParam
	 *            �p�����[�^
	 * @param buf_len
	 *            �o�b�t�@�T�C�Y
	 * @return ���b�Z�[�W�ɂ��
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
	 * ���b�Z�[�W���M�ipost�j.
	 * 
	 * @param message
	 *            ���b�Z�[�W
	 * @param wParam
	 *            �p�����[�^
	 * @param lParam
	 *            �p�����[�^
	 * @return true�F����
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
