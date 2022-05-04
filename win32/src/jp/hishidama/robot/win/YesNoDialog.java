package jp.hishidama.robot.win;

import jp.hishidama.win32.JWnd;

/**
 * �u�͂�/�������v�_�C�A���O.
 * <p>
 * Yes/No���m�F����_�C�A���O�B
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">�Ђ�����</a>
 * @since 2007.11.01
 */
public class YesNoDialog extends WindowsDialog {

	protected JWnd yes, no;

	/**
	 * �R���X�g���N�^�[.
	 * 
	 * @param wnd
	 *            �u�͂��v/�u�������v��I������_�C�A���O�̃E�B���h�E�n���h��
	 * @see FileSaveDialog#getOverwirteDialog()
	 */
	public YesNoDialog(JWnd wnd) {
		super(wnd);
	}

	protected void doInit(InitData ini) {
		// if (ini.i == 0)
		// listID();

		if (initYesButton(ini)) {
			return;
		}
		if (initNoButton(ini)) {
			return;
		}
	}

	protected boolean initYesButton(InitData ini) {
		if (ini.isButton() && ini.isDlgCtrl(IDYES)) {
			setYesButton(ini.wnd);
			return true;
		}
		return false;
	}

	protected boolean initNoButton(InitData ini) {
		if (ini.isButton() && ini.isDlgCtrl(IDNO)) {
			setNoButton(ini.wnd);
			return true;
		}
		return false;
	}

	protected void setYesButton(JWnd btn) {
		this.yes = btn;
	}

	protected void setNoButton(JWnd btn) {
		this.no = btn;
	}

	/**
	 * �u�͂��v�{�^���N���b�N.
	 * 
	 * @return ���������ꍇ�Atrue
	 */
	public boolean clickYesButton() {
		init();
		return click(yes);
	}

	/**
	 * �u�������v�{�^���N���b�N.
	 * 
	 * @return ���������ꍇ�Atrue
	 */
	public boolean clickNoButton() {
		init();
		return click(no);
	}
}
