package jp.hishidama.robot;

/**
 * �^�C���A�E�g��O.
 * <p>
 * �҂����������鏈���ɂ����ă^�C���A�E�g�����ꍇ�ɔ��������O�B
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">�Ђ�����</a>
 * @since 2007.11.01
 */
public class TimeoutException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1420932826242953503L;

	protected int pos;

	protected int timeout;

	/**
	 * �R���X�g���N�^�[.
	 * 
	 * @param pos
	 *            �^�C���A�E�g�����ӏ�
	 * @param timeout
	 *            �w�肳��Ă����^�C���A�E�g����[ms]
	 */
	public TimeoutException(int pos, int timeout) {
		this.pos = pos;
		this.timeout = timeout;
	}

	/**
	 * �^�C���A�E�g�����ӏ��擾.
	 * 
	 * @return �^�C���A�E�g�����ӏ��i�l�͌Ăяo�������\�b�h�ɂ��j
	 */
	public int getPos() {
		return pos;
	}

	/**
	 * �^�C���A�E�g���Ԏ擾.
	 * 
	 * @return �w�肳��Ă����^�C���A�E�g����[ms]
	 */
	public int getTimeout() {
		return timeout;
	}

	public String getMessage() {
		return "timeout: pos=" + pos + ", " + timeout + "ms";
	}

}
