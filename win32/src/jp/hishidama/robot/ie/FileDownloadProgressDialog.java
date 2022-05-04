package jp.hishidama.robot.ie;

import java.util.List;

import jp.hishidama.robot.win.FileSaveDialog;
import jp.hishidama.win32.JWnd;

/**
 * IE�t�@�C���_�E�����[�h�_�C�A���O.
 * <p>
 * IE�Ńt�@�C�����_�E�����[�h����ۂ́A�ŏI�I�Ƀt�@�C���_�E�����[�h���s���_�C�A���O�B
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">�Ђ�����</a>
 * @see FileDownloadDialogFactory#enumFileDownloadDialog()
 * @see FileDownloadRobot#download(int, String, int)
 * @since 2007.11.01
 */
public class FileDownloadProgressDialog extends FileDownloadDialog {

	protected FileDownloadConfirmDialog confirmDlg;

	protected FileSaveDialog saveDlg;

	protected JWnd close, file, folder, cancel;

	/**
	 * �R���X�g���N�^�[.
	 * 
	 * @param wnd
	 *            �u�t�@�C���_�̃E�����[�h�v�_�C�A���O�̃E�B���h�E�n���h��
	 * @see FileDownloadDialogFactory#enumFileDownloadDialog()
	 */
	public FileDownloadProgressDialog(JWnd wnd) {
		super(wnd);
	}

	/**
	 * �J��/�ۑ��m�F�_�C�A���O�擾.
	 * 
	 * @return �J��/�ۑ��m�F�_�C�A���O�i���݂��Ȃ��ꍇ�Anull�j
	 */
	public FileDownloadConfirmDialog getConfirmDialog() {
		if (confirmDlg != null) {
			if (!confirmDlg.getWnd().isWindow()) {
				return null;
			}
			return confirmDlg;
		}

		List list = JWnd.enumWindows();
		for (int i = 0; i < list.size(); i++) {
			JWnd w = (JWnd) list.get(i);
			if (!wnd.equals(w.GetParent())) {
				continue;
			}
			if (isConfirmDlg(w)) {
				confirmDlg = createConfirmDlg(w);
				break;
			}
		}
		return confirmDlg;
	}

	/**
	 * �J��/�ۑ��m�F�_�C�A���O���f.
	 * <p>
	 * IE�̃o�[�W�������ς�����Ƃ����͓����\�b�h���I�[�o�[���C�h���Ĕ�����@��ύX����z��B
	 * </p>
	 * 
	 * @param w
	 *            �E�B���h�E�n���h��
	 * @return �J��/�ۑ��m�F�_�C�A���O�̏ꍇ�Atrue
	 */
	protected boolean isConfirmDlg(JWnd w) {
		String title = w.GetWindowText();
		return "�t�@�C���̃_�E�����[�h".equals(title);
	}

	protected FileDownloadConfirmDialog createConfirmDlg(JWnd w) {
		return new FileDownloadConfirmDialog(w);
	}

	/**
	 * �ۑ��_�C�A���O�擾.
	 * 
	 * @return �ۑ��_�C�A���O�i���݂��Ȃ��ꍇ�Anull�j
	 */
	public FileSaveDialog getSaveDialog() {
		if (saveDlg != null) {
			if (!saveDlg.getWnd().isWindow()) {
				return null;
			}
			return saveDlg;
		}

		List list = JWnd.enumWindows();
		for (int i = 0; i < list.size(); i++) {
			JWnd w = (JWnd) list.get(i);
			if (!wnd.equals(w.GetParent())) {
				continue;
			}
			if (isSaveDlg(w)) {
				saveDlg = createSaveDlg(w);
				break;
			}
		}
		return saveDlg;
	}

	protected boolean isSaveDlg(JWnd w) {
		String title = w.GetWindowText();
		return "���O��t���ĕۑ�".equals(title);
	}

	protected FileSaveDialog createSaveDlg(JWnd w) {
		return new FileSaveDialog(w);
	}

	protected void doInit(InitData ini) {
		// if (ini.i == 0)
		// listID();

		if (initInfo(ini)) {
			return;
		}
		if (initCloseButton(ini)) {
			return;
		}
		if (initFileButton(ini)) {
			return;
		}
		if (initFolderButton(ini)) {
			return;
		}
		if (initCanelButton(ini)) {
			return;
		}
	}

	protected boolean initInfo(InitData ini) {
		if (!"msctls_progress32".equals(ini.name)) {
			return false;
		}
		JWnd w = (JWnd) ini.list.get(ini.i - 1);
		String info = w.GetWindowText();
		int f = info.indexOf(" - ");
		String src, name;
		if (f >= 0) {
			src = info.substring(0, f);
			name = info.substring(f + 3);
		} else {
			src = null;
			name = info;
		}
		setSrc(src);
		setName(name);
		return true;
	}

	protected boolean initCloseButton(InitData ini) {
		if (ini.isButton() && ini.textStartsWith("�_�E�����[�h�̊�����A���̃_�C�A���O �{�b�N�X�����")) {
			setCloseButton(ini.wnd);
			return true;
		}
		return false;
	}

	protected boolean initFileButton(InitData ini) {
		if (ini.isButton() && ini.textStartsWith("�t�@�C�����J��")) {
			setFileButton(ini.wnd);
			return true;
		}
		return false;
	}

	protected boolean initFolderButton(InitData ini) {
		if (ini.isButton() && ini.textStartsWith("�t�H���_���J��")) {
			setFolderButton(ini.wnd);
			return true;
		}
		return false;
	}

	protected boolean initCanelButton(InitData ini) {
		if (ini.isButton() && ini.isDlgCtrl(IDCANCEL)) {
			setCancelButton(ini.wnd);
			return true;
		}
		return false;
	}

	protected void setCloseButton(JWnd btn) {
		this.close = btn;
	}

	protected void setFileButton(JWnd btn) {
		this.file = btn;
	}

	protected void setFolderButton(JWnd btn) {
		this.folder = btn;
	}

	protected void setCancelButton(JWnd btn) {
		this.cancel = btn;
	}

	/**
	 * �u�_�E�����[�h�������Ƀ_�C�A���O�����v�`�F�b�N�擾.
	 * 
	 * @return �_�E�����[�h�������Ƀ_�C�A���O�����`�F�b�N���t���Ă���ꍇ�Atrue
	 */
	public boolean isCloseDownloaded() {
		init();
		try {
			return wnd.IsDlgButtonChecked(this.close.GetDlgCtrlID()) != BST_UNCHECKED;
		} catch (RuntimeException e) {
			return false;
		}
	}

	/**
	 * �u�_�E�����[�h�������Ƀ_�C�A���O�����v�`�F�b�N�ݒ�.
	 * 
	 * @param b
	 *            �_�E�����[�h�������Ƀ_�C�A���O�����ꍇ�Atrue
	 * @return ���������ꍇ�Atrue
	 */
	public boolean setCloseDownloaded(boolean b) {
		init();
		try {
			int c = b ? BST_CHECKED : BST_UNCHECKED;
			return wnd.CheckDlgButton(close.GetDlgCtrlID(), c);
		} catch (RuntimeException e) {
			return false;
		}
	}

	/**
	 * �u�t�@�C�����J���v�{�^���L�����f.
	 * 
	 * @return �L���ȏꍇ�Atrue
	 */
	public boolean isFileButtonEnabled() {
		init();
		try {
			return file.IsWindowEnabled();
		} catch (RuntimeException e) {
			return false;
		}
	}

	/**
	 * �u�t�@�C�����J���v�{�^���N���b�N.
	 * <p>
	 * ���O�Ƀ_�C�A���O�Ƀt�H�[�J�X�𓖂ĂĂ������ƁB
	 * </p>
	 * 
	 * @return ���������ꍇ�Atrue
	 */
	public boolean clickFileButton() {
		init();
		return click(file);
	}

	/**
	 * �u�t�H���_���J���v�{�^���L�����f.
	 * 
	 * @return �L���ȏꍇ�Atrue
	 */
	public boolean isFolderButtonEnabled() {
		init();
		try {
			return folder.IsWindowEnabled();
		} catch (RuntimeException e) {
			return false;
		}
	}

	/**
	 * �u�t�H���_���J���v�{�^���N���b�N.
	 * <p>
	 * ���O�Ƀ_�C�A���O�Ƀt�H�[�J�X�𓖂ĂĂ������ƁB
	 * </p>
	 * 
	 * @return ���������ꍇ�Atrue
	 */
	public boolean clickFolderButton() {
		init();
		return click(folder);
	}

	/**
	 * �u�L�����Z���v�{�^���N���b�N.
	 * <p>
	 * �ꍇ�ɂ���Ắu����v�{�^�������A�u�L�����Z���v�{�^���Ɠ���B
	 * </p>
	 * 
	 * @return ���������ꍇ�Atrue
	 */
	public boolean clickCancelButton() {
		init();
		return click(cancel);
	}

}
