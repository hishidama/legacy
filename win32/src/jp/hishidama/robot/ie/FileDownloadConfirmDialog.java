package jp.hishidama.robot.ie;

import jp.hishidama.win32.JWnd;

/**
 * IE�t�@�C���_�E�����[�h�F�J��/�ۑ��m�F�_�C�A���O.
 * <p>
 * IE�Ńt�@�C�����_�E�����[�h����ۂ́A�t�@�C�����J�����ۑ����邩�m�F����_�C�A���O�B
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">�Ђ�����</a>
 * @see FileDownloadProgressDialog#getConfirmDialog()
 * @since 2007.11.01
 */
public class FileDownloadConfirmDialog extends FileDownloadDialog {

	protected String type;

	protected JWnd open, save, cancel;

	/**
	 * �R���X�g���N�^�[.
	 * 
	 * @param wnd
	 *            �u�J���v/�u�ۑ��v��I������_�C�A���O�̃E�B���h�E�n���h��
	 * @see FileDownloadProgressDialog#getConfirmDialog()
	 */
	public FileDownloadConfirmDialog(JWnd wnd) {
		super(wnd);
	}

	protected void doInit(InitData ini) {
		// if (ini.i == 0)
		// listID();

		if (initName(ini)) {
			return;
		}
		if (initType(ini)) {
			return;
		}
		if (initSrc(ini)) {
			return;
		}
		if (initOpenButton(ini)) {
			return;
		}
		if (initSaveButton(ini)) {
			return;
		}
		if (initCancelButton(ini)) {
			return;
		}
	}

	protected boolean initName(InitData ini) {
		if (ini.isStatic() && ini.text != null && ini.text.startsWith("���O")) {
			ini.next();
			setName(ini.text);
			return true;
		}
		return false;
	}

	protected boolean initType(InitData ini) {
		if (ini.isStatic() && ini.text != null && ini.text.startsWith("���")) {
			ini.next();
			setType(ini.text);
			return true;
		}
		return false;
	}

	protected boolean initSrc(InitData ini) {
		if (ini.isStatic() && ini.text != null && ini.text.startsWith("���M��")) {
			ini.next();
			setSrc(ini.text);
			return true;
		}
		return false;
	}

	protected boolean initOpenButton(InitData ini) {
		if (ini.isButton() && ini.textStartsWith("�J��")) {
			setOpenButton(ini.wnd);
			return true;
		}
		return false;
	}

	protected boolean initSaveButton(InitData ini) {
		if (ini.isButton() && ini.textStartsWith("�ۑ�")) {
			setSaveButton(ini.wnd);
			return true;
		}
		return false;
	}

	protected boolean initCancelButton(InitData ini) {
		if (ini.isButton() && ini.isDlgCtrl(IDCANCEL)) {
			setCancelButton(ini.wnd);
			return true;
		}
		return false;
	}

	protected void setType(String type) {
		this.type = type;
	}

	/**
	 * ��ގ擾.
	 * 
	 * @return ���
	 */
	public String getType() {
		init();
		return type;
	}

	protected void setOpenButton(JWnd btn) {
		this.open = btn;
	}

	protected void setSaveButton(JWnd btn) {
		this.save = btn;
	}

	protected void setCancelButton(JWnd btn) {
		this.cancel = btn;
	}

	/**
	 * �u�J���v�{�^���N���b�N.
	 * <p>
	 * ���O�Ƀ_�C�A���O�Ƀt�H�[�J�X�𓖂ĂĂ������ƁB
	 * </p>
	 * 
	 * @return ���������ꍇ�Atrue
	 */
	public boolean clickOpenButton() {
		init();
		return click(open);
	}

	/**
	 * �u�ۑ��v�{�^���N���b�N.
	 * <p>
	 * ���O�Ƀ_�C�A���O�Ƀt�H�[�J�X�𓖂ĂĂ������ƁB
	 * </p>
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
}
