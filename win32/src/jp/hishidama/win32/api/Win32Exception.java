package jp.hishidama.win32.api;

/**
 * Win32API�Ăяo����O.
 * <p>
 * Win32API���Ăяo����GetLastError()��0�ȊO�ɂȂ����ꍇ�A����O�𔭐������Ă���B
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/hmwin32.html">�Ђ�����</a>
 * @since 2007.10.02
 */
public class Win32Exception extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6826834469784878586L;

	protected int error;

	/**
	 * �R���X�g���N�^�[
	 * 
	 * @param error
	 *            �G���[�R�[�h
	 */
	public Win32Exception(int error) {
		this.error = error;
	}

	/**
	 * �G���[�R�[�h�擾.
	 * 
	 * @return �G���[�R�[�h
	 */
	public int getLastError() {
		return error;
	}

	public String getMessage() {
		return WinBase.FormatMessage(error);
	}
}
