package jp.hishidama.win32.api;

/**
 * winbase.h�̊֐�.
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/hmwin32.html">�Ђ�����</a>
 * @since 2007.10.01
 * @version 2007.10.02
 */
public class WinBase {
	static {
		System.loadLibrary("hmwin32");
	}

	/**
	 * �ŐV�G���[�R�[�h�擾.
	 * <p>
	 * Win32API��GetLastError()���Ăяo���B<br>
	 * ������JNI���o�R�����֐��Ăяo���ł́iJNI�̏������̂�Win32API�𔭍s���Ă���ׁH�j�A���O��JNI�o�R��Win32API�̃G���[�R�[�h����邱�Ƃ��o���Ȃ��B<br>
	 * ���������ē����\�b�h�̑��݈Ӌ`�͕s��(��)
	 * </p>
	 * 
	 * @return �G���[�R�[�h
	 * @see Win32Exception
	 */
	public static native int GetLastError();

	/**
	 * �G���[���b�Z�[�W�擾.
	 * <p>
	 * �g�p��F<br>
	 * <code>
	 * int error = WinBase.GetLastError();<br>
	 * String message = WinBase.FormatMessage(error);
	 * </code>
	 * </p>
	 * 
	 * @param error
	 *            {@link #GetLastError()}�Ŏ擾�����G���[�R�[�h
	 * @return �G���[���b�Z�[�W
	 */
	public static native String FormatMessage(int error);

}
