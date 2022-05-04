package jp.hishidama.robot.win;

import java.util.List;

import jp.hishidama.win32.JWnd;
import jp.hishidama.win32.api.WinUserConst;

/**
 * Windows�_�C�A���O.
 * <p>
 * MS-Windows�̃_�C�A���O�������N���X�B
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">�Ђ�����</a>
 * @since 2007.11.01
 */
public abstract class WindowsDialog implements WinUserConst {

	/**
	 * �w�莞�ԑҋ@.
	 * 
	 * @param time
	 *            �E�F�C�g[�~���b]
	 */
	public void delay(int time) {
		if (time <= 0)
			return;
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
		}
	}

	protected JWnd wnd;

	/**
	 * �R���X�g���N�^�[
	 * 
	 * @param wnd
	 *            �_�C�A���O�̃E�B���h�E�n���h��
	 */
	public WindowsDialog(JWnd wnd) {
		this.wnd = wnd;
	}

	/**
	 * �_�C�A���O�̃E�B���h�E�n���h���擾.
	 * 
	 * @return JWnd
	 */
	public JWnd getWnd() {
		return wnd;
	}

	/**
	 * �E�B���h�E���ݔ��f.
	 * 
	 * @return �_�C�A���O�Ƃ��ėL���ȏꍇ�Atrue
	 */
	public boolean isWindow() {
		return wnd.isWindow();
	}

	/**
	 * �őO�ʈړ�.
	 * 
	 * @return ���������ꍇ�Atrue
	 */
	public boolean SetForegroundWindow() {
		try {
			return wnd.SetForegroundWindow();
		} catch (RuntimeException e) {
			return false;
		}
	}

	/**
	 * �t�H�[�J�X�ݒ�.
	 */
	public void setFocus() {
		try {
			wnd.SetFocus();
		} catch (RuntimeException e) {
		}
	}

	protected boolean init = false;

	/**
	 * �R���g���[��������.
	 * <p>
	 * �q�E�B���h�E�i�R���g���[���j���擾����ׂɌĂԁB<br>
	 * ���x�Ă΂�Ă��ŏ��̈�x�������������Ȃ��B
	 * </p>
	 * 
	 * @see #doInit(jp.hishidama.robot.win.WindowsDialog.InitData)
	 */
	protected void init() {
		if (!init) {
			try {
				InitData ini = new InitData(wnd);
				while (ini.hasData()) {
					doInit(ini);
					ini.next();
				}
			} catch (RuntimeException e) {
			}
			init = true;
		}
	}

	/**
	 * �R���g���[�������������{��.
	 * <p>
	 * {@link #init()}����_�C�A���O�̎q�E�B���h�E�i�R���g���[���j���ɌĂ΂��B<br>
	 * �T�u�N���X�œ����\�b�h���������A�K�v�ȃR���g���[���̕ێ����s���B
	 * </p>
	 * 
	 * @param ini
	 *            �����������ΏۃR���g���[�����
	 * @see #init()
	 */
	protected abstract void doInit(InitData ini);

	protected class InitData {
		/** �q�E�B���h�E�i{@link JWnd}�j�̃��X�g */
		public List list;

		/** {@link #list}���̌��݂̏����ʒu */
		public int i;

		/** ���݂̎q�E�B���h�E�̃E�B���h�E�n���h�� */
		public JWnd wnd;

		/** ���݂̎q�E�B���h�E�̃E�B���h�E�N���X�� */
		public String name;

		/** ���݂̎q�E�B���h�E�̃e�L�X�g�i�^�C�g���j */
		public String text;

		protected int size;

		/**
		 * �R���X�g���N�^�[.
		 * 
		 * @param dlg
		 *            �_�C�A���O�̃E�B���h�E�n���h��
		 */
		public InitData(JWnd dlg) {
			list = dlg.enumChildWindows();
			size = list.size();
			i = -1;
			next();
		}

		/**
		 * ���̎q�E�B���h�E�ֈړ�.
		 */
		public void next() {
			if (++i >= size) {
				wnd = null;
				name = null;
				text = null;
			} else {
				wnd = (JWnd) list.get(i);
				name = wnd.getClassName();
				text = wnd.GetWindowText();
			}
		}

		/**
		 * �����ΏۗL���擾.
		 * 
		 * @return �����Ώۂ�ێ����Ă���ꍇ�Atrue
		 */
		public boolean hasData() {
			return wnd != null;
		}

		/**
		 * ���x�����f.
		 * 
		 * @return �E�B���h�E�N���X����Static�̏ꍇ�Atrue
		 */
		public boolean isStatic() {
			return "Static".equals(name);
		}

		/**
		 * �G�f�B�b�g�{�b�N�X���f.
		 * 
		 * @return �E�B���h�E�N���X����Edit�̏ꍇ�Atrue
		 */
		public boolean isEdit() {
			return "Edit".equals(name);
		}

		/**
		 * �{�^�����f.
		 * 
		 * @return �E�B���h�E�N���X����Button�̏ꍇ�Atrue
		 */
		public boolean isButton() {
			return "Button".equals(name);
		}

		/**
		 * �e�L�X�g���f.
		 * 
		 * @param prefix
		 *            �e�L�X�g�̐擪������
		 * @return �e�L�X�g���w�肳�ꂽ������Ŏn�܂��Ă���ꍇ�Atrue
		 */
		public boolean textStartsWith(String prefix) {
			return text != null && text.startsWith(prefix);
		}

		/**
		 * �R���g���[��ID���f.
		 * 
		 * @param id
		 *            �R���g���[��ID
		 * @return ���݂̎q�E�B���h�E�̃R���g���[�����w�肳�ꂽ���̂ł���ꍇ�Atrue
		 */
		public boolean isDlgCtrl(int id) {
			return wnd != null && (wnd.GetDlgCtrlID() == id);
		}
	}

	/**
	 * �f�o�b�O�p�q�E�B���h�E�ꗗ�\��.
	 */
	protected void listID() {
		List list = wnd.enumChildWindows();
		for (int i = 0; i < list.size(); i++) {
			JWnd w = (JWnd) list.get(i);
			int id = w.GetDlgCtrlID();
			System.out.println(Long.toHexString(w.GetSafeHwnd()) + ":"
					+ Integer.toHexString(id) + "(" + id + ")" + ":"
					+ w.getClassName() + "/" + w.GetWindowText());
		}
	}

	/**
	 * �G�f�B�b�g�{�b�N�X������擾.
	 * 
	 * @param edit
	 *            �G�f�B�b�g�{�b�N�X
	 * @return �G�f�B�b�g�{�b�N�X���̕�����
	 */
	protected String getEditText(JWnd edit) {
		try {
			// x return wnd.GetDlgItemText(edit.GetDlgCtrlID());
			int len = edit.SendMessage(WM_GETTEXTLENGTH, 0, 0) + 1;
			return edit.SendMessageGetString(WM_GETTEXT, len, len * 2);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * �G�f�B�b�g�{�b�N�X������ݒ�.
	 * 
	 * @param edit
	 *            �G�f�B�b�g�{�b�N�X
	 * @param text
	 *            ������
	 * @return ���������ꍇ�Atrue
	 */
	protected boolean setEditText(JWnd edit, String text) {
		try {
			// SetWindowText�͕ʃv���Z�X�̃R���g���[���ɂ͎g���Ȃ�
			// return edit.SetWindowText(text);

			// x return wnd.SetDlgItemText(edit.GetDlgCtrlID(), text);

			edit.SendMessage(WM_SETTEXT, 0, text);

			// for (int i = 0; i < text.length(); i++) {
			// edit.SendMessage(WM_IME_CHAR, text.charAt(i), 0);
			// }
			return true;
		} catch (RuntimeException e) {
			// e.printStackTrace();
		}
		return false;
	}

	/**
	 * �{�^���N���b�N.
	 * <p>
	 * �i�{�^����ID���傫���ꍇ�́j�_�C�A���O�Ƀt�H�[�J�X���������Ă���K�v��������ۂ��̂ŁA���O��{@link #setFocus()}���Ă�ł������ƁB
	 * </p>
	 * 
	 * @param btn
	 *            �{�^��
	 * @return ���������ꍇ�Atrue
	 */
	protected boolean click(JWnd btn) {
		try {
			// �ȉ���3��ނ̂ǂ̕��@�ł����v����
			// �EBM_CLICK��WM_LBUTTONDOWN��WM_LBUTTONUP�𔭐�������
			// �E�ŏI�I��WM_COMMAND����������

			btn.PostMessage(BM_CLICK, 0, 0);

			// int x = 0, y = 0;
			// int lpos = (y << 16) | x;
			// btn.PostMessage(WM_LBUTTONDOWN, MK_LBUTTON, lpos);
			// btn.PostMessage(WM_LBUTTONUP, 0, lpos);

			// int id = btn.GetDlgCtrlID();
			// int lparam = (int) btn.GetSafeHwnd();
			// wnd.PostMessage(WM_COMMAND, (BN_CLICKED << 16) | id, lparam);

			return true;
		} catch (RuntimeException e) {
			// e.printStackTrace();
		}
		return false;
	}
}
