package jp.hishidama.jas;

import javassist.bytecode.CodeIterator;
import javassist.bytecode.ConstPool;
import javassist.bytecode.Descriptor;
import javassist.bytecode.MethodInfo;
import static javassist.bytecode.Opcode.*;

/**
 * �o�C�g�R�[�h����.
 * <p>
 * �o�C�g�R�[�h�̈ꖽ�߂�\���N���X�B<br>
 * Javassist�𗘗p���Ă���B
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/dbglogrm.html">�Ђ�����</a>
 * @since 2007.11.17
 * @version 2007.11.21
 */
public class BytecodeStatement {
	protected MethodInfo method;

	protected CodeIterator iterator;

	/** �o�C�g�z���̈ʒu */
	protected int pos;

	/** �I�y���[�V�����R�[�h */
	protected int opcode;

	/** ���̖��߂܂łɏ�����X�^�b�N */
	protected int stack_pos;

	/**
	 * �R���X�g���N�^�[.
	 * 
	 * @param minfo
	 *            ���\�b�h
	 * @param iterator
	 *            �R�[�h�C�e���[�^�[
	 * @param pos
	 *            �o�C�g�z���̈ʒu
	 */
	public BytecodeStatement(MethodInfo minfo, CodeIterator iterator, int pos) {
		this.method = minfo;
		this.iterator = iterator;
		this.pos = pos;
		this.opcode = iterator.byteAt(pos);
	}

	/**
	 * �ʒu�擾.
	 * 
	 * @return �o�C�g�z���̈ʒu
	 */
	public int getPos() {
		return pos;
	}

	/**
	 * �I�y���[�V�����R�[�h�擾.
	 * 
	 * @return �I�y���[�V�����R�[�h
	 */
	public int getOpcode() {
		return opcode;
	}

	/**
	 * �I�y�����h�i16bit�j�擾.
	 * <p>
	 * ���߂̈�����Ԃ��B
	 * </p>
	 * 
	 * @return �I�y�����h
	 */
	public int getOperand16() {
		return iterator.u16bitAt(pos + 1);
	}

	/**
	 * �_���v�p������擾.
	 * <p>
	 * �j�[���j�b�N�̌`�ɕϊ�����B�f�o�b�O�ړI�B<br>
	 * ����ł͈ꕔ�̖��߂����Ή����ĂȂ��c�B
	 * </p>
	 * 
	 * @return �j�[���j�b�N
	 */
	public String dump() {
		String arg = "";
		switch (opcode) {
		case BIPUSH: {
			int val = iterator.byteAt(pos + 1);
			arg = Integer.toString(val);
			break;
		}
		case IFEQ:
		case IFGE:
		case IFGT:
		case IFLE:
		case IFLT:
		case IFNE:
		case IFNONNULL:
		case IFNULL:
		case IF_ACMPEQ:
		case IF_ACMPNE:
		case IF_ICMPEQ:
		case IF_ICMPGE:
		case IF_ICMPGT:
		case IF_ICMPLE:
		case IF_ICMPLT:
		case IF_ICMPNE:
		case GOTO:
		case JSR: {
			int val = iterator.u16bitAt(pos + 1);
			val += pos;
			arg = Integer.toString(val);
			break;
		}
		case GOTO_W:
		case JSR_W: {
			int val = iterator.s32bitAt(pos + 1);
			val += pos;
			arg = Integer.toString(val);
			break;
		}
		case GETSTATIC: {
			ConstPool cp = method.getConstPool();
			int index = iterator.u16bitAt(pos + 1);
			arg = cp.getFieldrefClassName(index) + "#"
					+ cp.getFieldrefName(index) + " "
					+ cp.getFieldrefType(index);
			break;
		}
		case INVOKEVIRTUAL:
		case INVOKESPECIAL:
		case INVOKESTATIC:
		case INVOKEINTERFACE: {
			ConstPool cp = method.getConstPool();
			int index = iterator.u16bitAt(pos + 1);
			arg = cp.getMethodrefClassName(index) + "#"
					+ cp.getMethodrefName(index) + " "
					+ cp.getMethodrefType(index);
			break;
		}
		case NEW:
		case ANEWARRAY:
		case NEWARRAY: {
			ConstPool cp = method.getConstPool();
			int index = iterator.u16bitAt(pos + 1);
			arg = cp.getClassInfo(index);
			break;
		}
		}
		return pos + "[" + method.getLineNumber(pos) + "]"
				+ OpcodeString.get(opcode) + " " + arg;
	}

	/**
	 * �X�^�b�N�ʒu�ݒ�.
	 * 
	 * @param stack_pos
	 *            �X�^�b�N�ʒu
	 */
	public void setStackPos(int stack_pos) {
		this.stack_pos = stack_pos;
	}

	/**
	 * �X�^�b�N�ʒu�擾.
	 * 
	 * @return �X�^�b�N�ʒu
	 */
	public int getStackPos() {
		return stack_pos;
	}

	/**
	 * �X�^�b�N�����T�C�Y�擾.
	 * <p>
	 * ���ߎ��s��ɑ�������X�^�b�N�̑傫����Ԃ��B<br>
	 * ��������ꍇ�͕��̒l��Ԃ��B
	 * </p>
	 * 
	 * @return �X�^�b�N�T�C�Y
	 */
	public int stackGrowSize() {
		return stackSize(true);
	}

	/**
	 * �X�^�b�N�g�p�T�C�Y�擾.
	 * <p>
	 * ���߂��g�p����X�^�b�N�̑傫����Ԃ��B<br>
	 * ����ł̓��\�b�h�ɂ����Ή����Ă��Ȃ��B
	 * </p>
	 * <p>
	 * �Ⴆ�΁ustatic int test(int,int,int)�v���Ăяo���ꍇ�A<br>
	 * ������int3�Ȃ̂Ŏg�p����X�^�b�N��3�B�����\�b�h��3��Ԃ��B<br>
	 * {@link #stackGrowSize()}���Ɩ߂�l���i�[���镪��������A2��Ԃ��B
	 * </p>
	 * 
	 * @return �X�^�b�N�T�C�Y
	 */
	public int stackDataSize() {
		return stackSize(false);
	}

	protected int stackSize(boolean withRet) {
		switch (opcode) {
		case PUTSTATIC:
		case GETSTATIC:
		case PUTFIELD:
		case GETFIELD: {
			ConstPool cp = method.getConstPool();
			int index = iterator.u16bitAt(pos + 1);
			String sig = cp.getFieldrefType(index);
			int n = withRet ? Descriptor.dataSize(sig) : -Descriptor
					.paramSize(sig);
			// put
			if (opcode == PUTSTATIC || opcode == PUTFIELD) {
				n = -n;
			}
			// field
			if (opcode == PUTFIELD || opcode == GETFIELD) {
				// static�ȊO��this����␳����
				n--;
			}
			return n;
		}
		case INVOKESPECIAL:
		case INVOKEVIRTUAL:
		case INVOKEINTERFACE:
		case INVOKESTATIC: {
			ConstPool cp = method.getConstPool();
			int index = iterator.u16bitAt(pos + 1);
			String sig = cp.getMethodrefType(index);
			int n = withRet ? Descriptor.dataSize(sig) : -Descriptor
					.paramSize(sig);
			if (opcode != INVOKESTATIC) {
				// static�ȊO��this����␳����
				n--;
			}
			return n;
		}
		default:
			return STACK_GROW[opcode];
		}
	}

	/**
	 * �߂�^�擾.
	 * <p>
	 * ���߂����\�b�h�̂Ƃ����������g�p�s�B
	 * </p>
	 * 
	 * @return ���\�b�h�̖߂�l�̌^
	 */
	public String getReturnType() {
		ConstPool cp = method.getConstPool();
		int index = iterator.u16bitAt(pos + 1);
		String sig = cp.getMethodrefType(index);
		return sig.substring(sig.lastIndexOf(')') + 1);
	}

	protected int begin, end;

	/**
	 * �͈͐ݒ�.
	 * 
	 * @param begin
	 *            �J�n�ʒu
	 * @param end
	 *            �I���ʒu
	 */
	public void setRangePos(int begin, int end) {
		this.begin = begin;
		this.end = end;
	}

	/**
	 * �͈͂̊J�n�ʒu�擾.
	 * 
	 * @return �J�n�ʒu
	 */
	public int getFirstPos() {
		return begin;
	}

	/**
	 * �͈͂̏I���ʒu�擾.
	 * 
	 * @return �I���ʒu
	 */
	public int getEndPos() {
		return end;
	}
}
