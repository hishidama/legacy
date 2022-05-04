package jp.hishidama.debuglogrm;

import javassist.CtClass;
import javassist.CtMethod;

/**
 * �f�o�b�O���O�o�͍폜�N���X.
 * <p>
 * �f�o�b�O���O�o�̓��\�b�h�i{@link DebugLogWriteMethod}�A�m�e�[�V�����̕t���Ă��郁�\�b�h�j�̍폜���s���N���X�B<br>
 * �폜���s���ΏۂƂȂ�̂́A{@link UseDebugLog}�A�m�e�[�V�����̕t���Ă���N���X�̂݁B<br> ��<a
 * target="hishidama"
 * href="http://www.ne.jp/asahi/hishidama/home/soft/java/dbglogrm.html">�g�p��</a>
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/index.html">�Ђ�����</a>
 * @since 2007.11.17
 * @version 2007.11.18
 */
public class DebugRemoveEditor extends AbstractDebugRemoveEditor {

	protected int writeLevel;

	/**
	 * �R���X�g���N�^�[.
	 * <p>
	 * �o�̓��x���̓f�t�H���g�B
	 * </p>
	 * 
	 * @see #setDefaultLevel()
	 */
	public DebugRemoveEditor() {
		setDefaultLevel();
	}

	/**
	 * �R���X�g���N�^�[.
	 * <p>
	 * �w�肳�ꂽ�o�̓��x���ŏ���������B
	 * </p>
	 * 
	 * @see #setLevel(String)
	 */
	public DebugRemoveEditor(String levelString) {
		setLevel(levelString);
	}

	/**
	 * �o�̓��x��������.
	 * <p>
	 * ��FlevelString���uEWI�v�̂Ƃ��AERROR�EWARNING�EINFO���x�����o�͂���i���̑��̃��x���̏o�̓��\�b�h���폜����j�B
	 * </p>
	 * 
	 * @param levelString
	 *            �o�̓��x���𕡐��̕����̑g�ݍ��킹�Ŏw�肷��B������null�̏ꍇ�̓f�t�H���g�ƂȂ�B <table border=1>
	 *            <tr>
	 *            <th>�w��</th>
	 *            <th>�o�̓��x��</th>
	 *            </tr>
	 *            <tr>
	 *            <td>F</td>
	 *            <td>FATAL���x��</td>
	 *            </tr>
	 *            <tr>
	 *            <td>E</td>
	 *            <td>ERROR���x��</td>
	 *            </tr>
	 *            <tr>
	 *            <td>W</td>
	 *            <td>WARNING���x��</td>
	 *            </tr>
	 *            <tr>
	 *            <td>I</td>
	 *            <td>INFO���x��</td>
	 *            </tr>
	 *            <tr>
	 *            <td>V</td>
	 *            <td>VERBOSE���x��</td>
	 *            </tr>
	 *            <tr>
	 *            <td>D</td>
	 *            <td>DEBUG���x��</td>
	 *            </tr>
	 *            <tr>
	 *            <td>T</td>
	 *            <td>TRACE���x��</td>
	 *            </tr>
	 *            </table>
	 */
	public void setLevel(String levelString) {
		removeAllLevel();
		if (levelString == null) {
			setDefaultLevel();
			return;
		}
		for (int i = 0; i < levelString.length(); i++) {
			switch (levelString.charAt(i)) {
			case 'F':
			case 'f':
				addWriteLevel(DebugLevel.FATAL);
				break;
			case 'E':
			case 'e':
				addWriteLevel(DebugLevel.ERROR);
				break;
			case 'W':
			case 'w':
				addWriteLevel(DebugLevel.WARNING);
				break;
			case 'I':
			case 'i':
				addWriteLevel(DebugLevel.INFO);
				break;
			case 'V':
			case 'v':
				addWriteLevel(DebugLevel.VERBOSE);
				break;
			case 'D':
			case 'd':
				addWriteLevel(DebugLevel.DEBUG);
				break;
			case 'T':
			case 't':
				addWriteLevel(DebugLevel.TRACE);
				break;
			}
		}
	}

	/**
	 * �f�t�H���g�o�̓��x���ݒ�.
	 * <p>
	 * �f�t�H���g�́AFATAL�EERROR�EWARNING�EINFO���x���B
	 * </p>
	 */
	public void setDefaultLevel() {
		addWriteLevel(DebugLevel.FATAL);
		addWriteLevel(DebugLevel.ERROR);
		addWriteLevel(DebugLevel.WARNING);
		addWriteLevel(DebugLevel.INFO);
	}

	/**
	 * �o�̓��x���ݒ�.
	 * 
	 * @param level
	 *            �o�̓��x��
	 */
	public void addWriteLevel(DebugLevel level) {
		writeLevel |= 1 << level.ordinal();
	}

	/**
	 * �ۏo�̓��x���ݒ�.
	 * 
	 * @param level
	 *            �o�͂��Ȃ����x��
	 */
	public void removeLevel(DebugLevel level) {
		writeLevel &= ~(1 << level.ordinal());
	}

	/**
	 * �o�̓��x���S�폜.
	 */
	public void removeAllLevel() {
		writeLevel = 0;
	}

	/**
	 * �o�̓��x������.
	 * 
	 * @param level
	 *            �o�̓��x��
	 * @return �o�͂���Ƃ��Atrue
	 */
	public boolean isWriteLevel(DebugLevel level) {
		return (writeLevel & (1 << level.ordinal())) != 0;
	}

	/**
	 * �o�̓��x��������擾.
	 * 
	 * @return �o�̓��x��
	 */
	public String getLevelString() {
		if (writeLevel == 0) {
			return "nothing";
		}
		StringBuilder sb = new StringBuilder(64);
		for (DebugLevel level : DebugLevel.values()) {
			if (isWriteLevel(level)) {
				if (sb.length() != 0) {
					sb.append(',');
				}
				sb.append(level);
			}
		}
		return sb.toString();
	}

	@Override
	protected boolean useDebugLog(CtClass cc) {
		Object[] anns;
		try {
			anns = cc.getAnnotations();
		} catch (ClassNotFoundException e) {
			// �A�m�e�[�V�����������Ƃ��͑ΏۊO
			// e.printStackTrace();
			return false;
		}

		for (Object a : anns) {
			if (a instanceof UseDebugLog) {
				return true;
			}
		}
		return false;
	}

	@Override
	protected boolean isDebugLogWriteMethod(CtMethod m) {
		Object[] anns;
		try {
			anns = m.getAnnotations();
		} catch (ClassNotFoundException e) {
			// �A�m�e�[�V�����������̂ŕϊ��ΏۊO
			return false;
		}
		boolean ret = false;
		for (Object a : anns) {
			if (a instanceof DebugLogWriteMethod) {
				DebugLogWriteMethod dm = (DebugLogWriteMethod) a;
				if (isWriteLevel(dm.value())) {
					return false;
				} else {
					ret = true;
				}
			}
		}
		return ret;
	}

}
