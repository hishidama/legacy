package jp.hishidama.robot.ie;

import jp.hishidama.robot.IERobot;
import jp.hishidama.win32.JWnd;
import jp.hishidama.win32.api.WinUserConst;
import jp.hishidama.win32.com.ComPtr;
import jp.hishidama.win32.mshtml.*;

/**
 * �e�L�X�g�{�b�N�X�x���N���X.
 * <p>
 * {@link IHTMLInputElement}�̃e�L�X�g�̓��o�͑��������N���X�ł��B<br> ��<a target="hishidama"
 * href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">�g�p��</a>
 * </p>
 * <p>
 * �R���X�g���N�^�[�̈����Ɏw�肷��HTML�v�f�́A�Ăяo�����Ŕj�����ĉ������B<br>
 * {@link jp.hishidama.robot.IERobot#getInputById(String)}�ɂ���ē��C���X�^���X���擾�����ꍇ��IERobot���j�����܂��B
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">�Ђ�����</a>
 * @since 2007.10.28
 */
public class IHTMLInputValueUtil {

	protected IERobot ie;

	protected IHTMLInputElement elem;

	/**
	 * �R���X�g���N�^�[.
	 * 
	 * @param ie
	 *            IERobot
	 * @param input
	 *            input�v�f
	 * @see IERobot#getInputById(String)
	 * @see IERobot#getInputByName(String, int)
	 */
	public IHTMLInputValueUtil(IERobot ie, IHTMLInputElement input) {
		this.ie = ie;
		this.elem = input;
	}

	/**
	 * �g�p�ێ擾.
	 * 
	 * @return �g�p�s�̏ꍇ�Atrue
	 */
	public boolean isDisabled() {
		return elem.isDisabled();
	}

	/**
	 * �g�p�ېݒ�.
	 * 
	 * @param b
	 *            true�F�g�p�s��
	 */
	public void setDisabled(boolean b) {
		elem.setDisabled(b);
	}

	/**
	 * �Ǎ��ێ擾.
	 * 
	 * @return �ǂݍ��ݐ�p�̂Ƃ��Atrue
	 */
	public boolean isReadOnly() {
		return elem.getReadOnly();
	}

	/**
	 * �Ǎ��ېݒ�.
	 * 
	 * @param b
	 *            true�F�ǂݍ��ݐ�p
	 */
	public void setReadOnly(boolean b) {
		elem.setReadOnly(b);
	}

	/**
	 * �l�擾.
	 * 
	 * @return �l
	 */
	public String getValue() {
		return elem.getValue();
	}

	/**
	 * �l�ݒ�.
	 * <p>
	 * value�����ɒ��ڒl���������ށB����������type="�t�@�C��"���ł͎g���Ȃ��B<br>
	 * ����ȊO��type�ł́A�g�p�s��Ԃł����Ă��ݒ�ł���B
	 * </p>
	 * 
	 * @param value
	 *            �l
	 */
	public void setValue(String value) {
		elem.setValue(value);
	}

	/**
	 * �l���M.
	 * <p>
	 * �O������l�𑗐M����Btype="�t�@�C��"�ɂ����͉\�B�������t�H�[�J�X��ύX���Ă��܂��̂Œ��ӁB<br>
	 * �Ώۂ��E�B���h�E��œ��͉\�ȏ�ԂłȂ��ƃG���[�ɂȂ�B���Ȃ킿�Adisable��������type="hidden"�ɂ͎g�p�ł��Ȃ��B<br>
	 * �Ώۂ��ǂݍ��ݐ�p�̏ꍇ�A�G���[�ɂȂ�Ȃ������ۂɂ͔��f����Ȃ��B
	 * </p>
	 * 
	 * @param value
	 *            �l
	 * @exception RuntimeException
	 *                ���͂ł��Ȃ���
	 */
	public void sendValue(String value) {
		final String ERR_MSG = "���͕s��";

		IHTMLTxtRange r = null;
		IHTMLElement ae = null;
		try {
			r = elem.createTextRange();
			r.select(); // ���ݓ��͂���Ă�����̂�I���i����ɂ��t�H�[�J�X��������j

			// �A�N�e�B�u�ȗv�f���擾���A���ꂪ�Ώۂƈ�v���Ă��邩�m�F
			// disable�ȏꍇ��type=hidden�͈�v���Ȃ�
			IHTMLDocument doc = ie.getDocument();
			ae = doc.getActiveElement();
			if (ae == null || ae.getClass() != elem.getClass()) {
				throw new RuntimeException(ERR_MSG);
			}
			IHTMLTxtRange ar = ((IHTMLInputElement) ae).createTextRange();
			if (!r.isEqual(ar)) {
				throw new RuntimeException(ERR_MSG);
			}

			// �N���C�A���g�̈��HWND�擾
			JWnd wnd = ie.getCliendWnd();

			// �ꕶ�������M
			for (int i = 0; i < value.length(); i++) {
				char c = value.charAt(i);
				wnd.SendMessage(WinUserConst.WM_IME_CHAR, c, 0);
			}
		} finally {
			ComPtr.dispose(r);
			ComPtr.dispose(ae);
		}
	}

	/**
	 * input�v�f�擾.
	 * <p>
	 * �����\�b�h�ɂ���Ď擾����HTML�v�f�́A�R���X�g���N�^�[���Ăяo�����I�u�W�F�N�g�ɂ���Ĕj�������B
	 * </p>
	 * 
	 * @return input�v�f
	 */
	public IHTMLInputElement getElement() {
		return elem;
	}
}
