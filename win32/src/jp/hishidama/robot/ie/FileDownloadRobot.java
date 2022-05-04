package jp.hishidama.robot.ie;

import jp.hishidama.robot.TimeoutException;
import jp.hishidama.robot.win.FileSaveDialog;
import jp.hishidama.robot.win.YesNoDialog;

/**
 * IE�t�@�C���_�E�����[�h�������s�N���X.
 * <p>
 * IE���g�����t�@�C���̃_�E�����[�h���s���N���X�B
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">�Ђ�����</a>
 * @since 2007.11.01
 * @version 2007.11.06
 */
public class FileDownloadRobot {

	/** �t�@�C�������F�J�� */
	public static final int CONFIRM_OPEN = 0;

	/** �t�@�C�������F�ۑ� */
	public static final int CONFIRM_SAVE = 1;

	/** �t�@�C�������F�L�����Z�� */
	public static final int CONFIRM_CANCEL = 2;

	/**
	 * �^�C���A�E�g���F�_�E�����[�h�����J�n��
	 * 
	 * @see #download(int, String, int)
	 * @see TimeoutException#getPos()
	 */
	public static final int POS_START = 0;

	/** �^�C���A�E�g���F�J��/�ۑ��̊m�F�� */
	public static final int POS_CONFIRM = 1;

	/** �^�C���A�E�g���F�J��/�ۑ��̊m�F������ */
	public static final int POS_CONFIRM_END = 2;

	/** �^�C���A�E�g���F�ۑ��ꏊ�̊m�F�� */
	public static final int POS_SAVE = 3;

	/** �^�C���A�E�g���F�ۑ��ꏊ�̊m�F������ */
	public static final int POS_SAVE_END = 4;

	/** �^�C���A�E�g���F�㏑���m�F�� */
	public static final int POS_OVERWRITE = 5;

	/** �^�C���A�E�g���F�㏑���m�F������ */
	public static final int POS_OVERWRITE_END = 6;

	/** �^�C���A�E�g���F�N���[�Y������ */
	public static final int POS_CLOSE = 7;

	protected FileDownloadProgressDialog pd;

	/**
	 * �R���X�g���N�^�[
	 * 
	 * @param pd
	 *            �����Ώۂ́u�t�@�C���̃_�E�����[�h�v�_�C�A���O
	 */
	public FileDownloadRobot(FileDownloadProgressDialog pd) {
		this.pd = pd;
	}

	protected int pos;

	/**
	 * �_�E�����[�h���s.
	 * 
	 * @param confirm
	 *            {@link #CONFIRM_SAVE}��
	 * @param name
	 *            �ۑ�����ꍇ�̃t�@�C�����inull�Ȃ� �f�t�H���g�̂܂܁j
	 * @param timeout
	 *            �^�C���A�E�g���ԁi0�������ƃ^�C���A�E�g���Ȃ��j
	 * @exception TimeoutException
	 *                �^�C���A�E�g���ipos��{@link #POS_START}���j
	 */
	public void download(int confirm, String name, int timeout) {

		pos = POS_START;
		long start = System.currentTimeMillis();

		// �_�E�����[�h�_�C�A���O���L���ȊԁA����
		while (pd.isWindow()) {
			try {
				// if (!pd.isCloseDownloaded()) {
				if (pd.isFileButtonEnabled() && pd.isFolderButtonEnabled()) {
					// �u�t�@�C�����J���v�{�^���Ɓu�t�H���_�[���J���v�{�^�����L���ɂȂ��Ă�����
					// �_�E�����[�h����
					pd.clickCancelButton(); // �u����v�{�^���iID�̓L�����Z���{�^���Ɠ���j
					pos = POS_CLOSE;
					while (pd.isWindow()) {
						long t = System.currentTimeMillis();
						if (timeout >= 0 && t - start > timeout) {
							throw new TimeoutException(pos, timeout);
						}
						pd.delay(100);
					}
					break;
				}
				// }
			} catch (TimeoutException e) {
				throw e;
			} catch (RuntimeException e) {
			}

			long t = System.currentTimeMillis();
			if (timeout >= 0 && t - start > timeout) {
				throw new TimeoutException(pos, timeout);
			}

			// �J�����ۑ����邩���m�F����_�C�A���O
			FileDownloadConfirmDialog cd = pd.getConfirmDialog();
			if (cd != null) {
				doConfirmDialog(cd, confirm, start, timeout);
			}

			// �u���O��t���ĕۑ��v�_�C�A���O
			FileSaveDialog sd = pd.getSaveDialog();
			if (sd != null) {
				doSaveDialog(sd, name, start, timeout);
			}

			pd.delay(100);
		}
	}

	protected void doConfirmDialog(FileDownloadConfirmDialog cd, int confirm,
			long start, int timeout) {
		pos = POS_CONFIRM;
		while (cd.isWindow()) {
			cd.SetForegroundWindow();
			// cd.setFocus();

			long t = System.currentTimeMillis();
			if (timeout >= 0 && t - start > timeout) {
				throw new TimeoutException(pos, timeout);
			}

			switch (confirm) {
			case CONFIRM_OPEN:
				cd.clickOpenButton();
				break;
			case CONFIRM_SAVE:
				cd.clickSaveButton();
				break;
			case CONFIRM_CANCEL:
				cd.clickCancelButton();
				break;
			default:
				throw new IllegalArgumentException("confirm:" + confirm);
			}

			cd.delay(100);
		}
		pos = POS_CONFIRM_END;
	}

	protected void doSaveDialog(FileSaveDialog sd, String name, long start,
			int timeout) {
		pos = POS_SAVE;

		if (name != null) {
			sd.SetForegroundWindow();
			sd.delay(100);
			for (;;) {
				// �ۑ�����t�@�C������ύX
				sd.setName(name);
				sd.delay(100);

				String n = sd.getName();
				if (name.equals(n)) {
					break;
				}

				long t = System.currentTimeMillis();
				if (timeout >= 0 && t - start > timeout) {
					throw new TimeoutException(pos, timeout);
				}
			}
		}

		while (sd.isWindow()) {
			// sd.setFocus();
			sd.SetForegroundWindow();

			long t = System.currentTimeMillis();
			if (timeout >= 0 && t - start > timeout) {
				throw new TimeoutException(pos, timeout);
			}

			sd.clickSaveButton();

			// �t�@�C�������ɑ��݂��Ă���ꍇ�A�w�㏑�����邩�ǂ������m�F����_�C�A���O�x���J��
			YesNoDialog yd = sd.getOverwirteDialog();
			if (yd != null) {
				pos = POS_OVERWRITE;
				while (yd.isWindow()) {
					t = System.currentTimeMillis();
					if (timeout >= 0 && t - start > timeout) {
						throw new TimeoutException(pos, timeout);
					}
					yd.clickYesButton();
					yd.delay(100);
				}
				pos = POS_OVERWRITE_END;

				while (sd.isWindow()) {
					t = System.currentTimeMillis();
					if (timeout >= 0 && t - start > timeout) {
						throw new TimeoutException(pos, timeout);
					}

					sd.delay(100);
				}
				break;

			} else {
				sd.delay(100);
			}
		}
		pos = POS_SAVE_END;
	}
}
