package jp.hishidama.robot.win;

import java.util.List;

import jp.hishidama.robot.ie.FileDownloadProgressDialog;
import jp.hishidama.win32.JWnd;

/**
 * �u�t�@�C���̕ۑ��v�_�C�A���O.
 * <p>
 * �t�@�C����ۑ�����ꏊ���w�肷��_�C�A���O�B
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">�Ђ�����</a>
 * @since 2007.11.01
 */
public class FileSaveDialog extends WindowsDialog {

	protected JWnd name;

	protected JWnd save, cancel;

	/**
	 * �R���X�g���N�^�[.
	 * 
	 * @param wnd
	 *            �u�t�@�C���̕ۑ��v�_�C�A���O�̃E�B���h�E�n���h��
	 * @see FileDownloadProgressDialog#getSaveDialog()
	 */
	public FileSaveDialog(JWnd wnd) {
		super(wnd);
	}

	protected void doInit(InitData ini) {
		// if (ini.i == 0)
		// listID();

		if (initNameEdit(ini)) {
			return;
		}
		if (initSaveButton(ini)) {
			return;
		}
		if (initCanelButton(ini)) {
			return;
		}
	}

	protected boolean initNameEdit(InitData ini) {
		if (ini.isStatic() && ini.textStartsWith("�t�@�C����")) {
			for (;;) {
				ini.next();
				if (!ini.hasData()) {
					break;
				}
				if (ini.isEdit()) {
					setNameEdit(ini.wnd);
					break;
				}
			}
			return true;
		}
		return false;
	}

	protected boolean initSaveButton(InitData ini) {
		if (ini.isButton() && ini.isDlgCtrl(IDOK)) {
			setSaveButton(ini.wnd);
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

	protected void setNameEdit(JWnd edit) {
		this.name = edit;
	}

	protected void setSaveButton(JWnd btn) {
		this.save = btn;
	}

	protected void setCancelButton(JWnd btn) {
		this.cancel = btn;
	}

	/**
	 * �t�@�C�����擾.
	 * 
	 * @return �t�@�C�����i���s�����ꍇ�Anull�j
	 */
	public String getName() {
		init();
		return getEditText(name);
	}

	/**
	 * �t�@�C�����ݒ�.
	 * 
	 * @param text
	 *            �t�@�C����
	 * @return ���������ꍇ�Atrue
	 */
	public boolean setName(String text) {
		init();
		return setEditText(name, text);
	}

	/**
	 * �u�ۑ��v�{�^���N���b�N.
	 * 
	 * @return ���������ꍇ�Atrue
	 */
	public boolean clickSaveButton() {
		init();
		return click(save);
	}

	/**
	 * �u�L�����Z���v�{�^���N���b�N.
	 * 
	 * @return ���������ꍇ�Atrue
	 */
	public boolean clickCancelButton() {
		init();
		return click(cancel);
	}

	/**
	 * �u���O��t���ĕۑ��v�_�C�A���O�擾.
	 * 
	 * @return �u�͂�/�������v�_�C�A���O�i���݂��Ȃ��ꍇ�Anull�j
	 */
	public YesNoDialog getOverwirteDialog() {
		List list = JWnd.enumWindows();
		for (int i = 0; i < list.size(); i++) {
			JWnd w = (JWnd) list.get(i);
			if (wnd.equals(w.GetParent()) && isOverwirteDialog(w)) {
				return new YesNoDialog(w);
			}
		}
		return null;
	}

	protected boolean isOverwirteDialog(JWnd w) {
		return "���O��t���ĕۑ�".equals(w.GetWindowText());
	}
}
