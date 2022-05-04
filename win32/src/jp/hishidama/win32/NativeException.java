package jp.hishidama.win32;

/**
 * MS-Windows�\������O.
 * <p>
 * �E�B���h�E�Y�̍\������O�̃��b�p�[�N���X�B
 * </p>
 * <p>
 * JNI�Ăяo���ɂ����ăA�N�Z�X�ᔽ�Ȃǂ̍\������O�����������ꍇ�ɁA���̏������ɓ���O�𔭐������Ă���B<br>
 * �f�t�H���g�ł� �\������O�����������JNI���C�u�����[�������_���v���Ĉُ�I�����邪�A�����C�u�����ł͂��̗�O�ŃL���b�`�ł���B�͂��B
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/hmwin32.html">�Ђ�����</a>
 * @since 2007.10.22
 */
public class NativeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4164868244515537003L;

	protected int code;

	protected int flag;

	protected long addr;

	protected long[] info;

	/**
	 * �R���X�g���N�^�[.
	 * 
	 * @param code
	 *            �G���[�R�[�h
	 * @param flag
	 *            �t���O
	 * @param addr
	 *            �A�h���X
	 * @param info
	 *            �G���[���
	 */
	public NativeException(int code, int flag, long addr, long[] info) {
		this.code = code;
		this.flag = flag;
		this.addr = addr;
		this.info = info;
	}

	/**
	 * �G���[�R�[�h�擾.
	 * 
	 * @return �G���[�R�[�h
	 */
	public int getCode() {
		return code;
	}

	/**
	 * �t���O�擾.
	 * 
	 * @return �t���O
	 */
	public int getFlag() {
		return flag;
	}

	/**
	 * �A�h���X�擾.
	 * 
	 * @return �G���[�������̃v���O�����̃A�h���X
	 */
	public long getAddr() {
		return addr;
	}

	/**
	 * �G���[���擾.
	 * 
	 * @return �G���[���̔z��
	 */
	public long[] getInfo() {
		return info;
	}

	public String getMessage() {
		StringBuffer sb = new StringBuffer(128);
		sb.append("code=0x");
		sb.append(Integer.toHexString(code));
		String msg = getMessage(code);
		if (!msg.equals("")) {
			sb.append('(');
			sb.append(msg);
			sb.append(')');
		}
		sb.append(" flag=0x");
		sb.append(Integer.toHexString(flag));
		sb.append(" addr=0x");
		sb.append(Long.toHexString(addr));
		sb.append(" info=");
		if (info == null) {
			sb.append(info);
		} else {
			sb.append('{');
			for (int i = 0; i < info.length; i++) {
				if (i != 0)
					sb.append(", ");
				sb.append("0x");
				sb.append(Long.toHexString(info[i]));
			}
			sb.append('}');
		}
		return sb.toString();
	}

	/**
	 * �G���[���擾.
	 * 
	 * @param code
	 *            �G���[�R�[�h
	 * @return �G���[��
	 */
	public static String getMessage(int code) {
		switch (code) {
		case 0xC0000005:
			return "ACCESS_VIOLATION";
		case 0xC0000008:
			return "INVALID_HANDLE";
		}
		return "";
	}
}
