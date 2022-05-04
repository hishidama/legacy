package jp.hishidama.robot.ie;

import java.util.ArrayList;
import java.util.List;

import jp.hishidama.robot.win.WindowsDialog;
import jp.hishidama.win32.JWnd;

/**
 * IE�t�@�C���_�E�����[�h�_�C�A���O�t�@�N�g���[.
 * <p>
 * �g�b�v���x���́u�t�@�C���̃_�E�����[�h�v�_�C�A���O��񋓂���N���X�B
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">�Ђ�����</a>
 * @since 2007.11.01
 */
public class FileDownloadDialogFactory {

	protected static FileDownloadDialogFactory instance;

	/**
	 * �t�@�N�g���[�擾.
	 * 
	 * @return �t�@�N�g���[
	 */
	public static FileDownloadDialogFactory getInstance() {
		if (instance == null) {
			instance = new FileDownloadDialogFactory();
		}
		return instance;
	}

	protected FileDownloadDialogFactory() {
	}

	/**
	 * �u�t�@�C���̃_�E�����[�h�v�_�C�A���O�ꗗ�擾.
	 * <p>
	 * ���ݕ\������Ă���A�g�b�v���x���́u�t�@�C���̃_�E�����[�h�v�_�C�A���O��S�Ď擾����B
	 * </p>
	 * 
	 * @return {@link FileDownloadProgressDialog}�̃��X�g�i�K��null�ȊO�j
	 */
	public List enumFileDownloadDialog() {
		List list = JWnd.enumWindows();
		if (list == null) {
			return new ArrayList(0);
		}

		List pl = new ArrayList(list.size() / 8);
		for (int i = 0; i < list.size(); i++) {
			JWnd wnd = (JWnd) list.get(i);
			try {
				if (addProgress(pl, wnd)) {
					continue;
				}
			} catch (RuntimeException e) {
			}
		}

		return pl;
	}

	protected final String DIALOG_CLASS = "#32770";

	protected final String DIALOG_NAME = "�t�@�C���̃_�E�����[�h";

	protected boolean addProgress(List pl, JWnd wnd) {
		if (DIALOG_CLASS.equals(wnd.getClassName())
				&& DIALOG_NAME.equals(wnd.GetWindowText())
				&& wnd.GetParent() == null) {
			pl.add(new FileDownloadProgressDialog(wnd));
			return true;
		}
		return false;
	}

}

/**
 * IE�t�@�C���_�E�����[�h�X�[�p�[�N���X.
 * <p>
 * �u�t�@�C���̃_�E�����[�h�v�_�C�A���O�������N���X�B
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">�Ђ�����</a>
 * @since 2007.11.01
 */
abstract class FileDownloadDialog extends WindowsDialog {

	protected String src;

	protected String name;

	protected FileDownloadDialog(JWnd wnd) {
		super(wnd);
	}

	protected void setSrc(String src) {
		this.src = src;
	}

	/**
	 * ���M���擾.
	 * <p>
	 * �_�C�A���O�ɕ\������Ă���A�_�E�����[�h���̃T�C�g�����擾����B
	 * </p>
	 * 
	 * @return ���M��
	 */
	public String getSrc() {
		init();
		return src;
	}

	protected void setName(String name) {
		this.name = name;
	}

	/**
	 * ���O�擾.
	 * <p>
	 * �_�C�A���O�ɕ\������Ă���A�_�E�����[�h����t�@�C�������擾����B
	 * </p>
	 * 
	 * @return �t�@�C����
	 */
	public String getName() {
		init();
		return name;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer(256);
		sb.append(super.toString());
		sb.append("{ HWND=");
		sb.append(Long.toHexString(wnd.GetSafeHwnd()));
		sb.append(", SRC=");
		sb.append(getSrc());
		sb.append(", NAME=");
		sb.append(getName());
		sb.append(" }");
		return sb.toString();
	}

}
