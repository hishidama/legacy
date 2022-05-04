package jp.hishidama.debuglogrm;

/**
 * �f�o�b�O���x��.
 * <p>
 * �f�o�b�O���O�̏o�̓��x���������B <br> ��<a target="hishidama"
 * href="http://www.ne.jp/asahi/hishidama/home/soft/java/dbglogrm.html">�g�p��</a>
 * </p>
 * 
 * @see DebugLogWriteMethod
 * @see DebugRemoveEditor
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/index.html">�Ђ�����</a>
 * @since 2007.11.17
 */
public enum DebugLevel {
	/** �v���I���x�� */
	FATAL,
	/** �G���[���x�� */
	ERROR,
	/** �x�����x�� */
	WARNING,
	/** ��񃌃x�� */
	INFO,
	/** �ڍ׃��x�� */
	VERBOSE,
	/** �f�o�b�O���x�� */
	DEBUG,
	/** �g���[�X���x�� */
	TRACE,
}
