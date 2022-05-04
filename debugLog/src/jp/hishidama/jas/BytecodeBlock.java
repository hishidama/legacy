package jp.hishidama.jas;

import static javassist.bytecode.Opcode.*;

import java.util.*;

import javassist.bytecode.BadBytecode;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.CodeIterator;

/**
 * �o�C�g�R�[�h�u���b�N.
 * <p>
 * �o�C�g�R�[�h�̂ЂƂ����܂��ێ�����N���X�B<br>
 * Javassist�𗘗p���Ă���B
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/dbglogrm.html">�Ђ�����</a>
 * @since 2007.11.21
 */
public class BytecodeBlock {

	protected BytecodeBlocks all;

	protected int pos, end;

	protected List<BytecodeStatement> list;

	/**
	 * �R���X�g���N�^�[.
	 * 
	 * @param group
	 *            �����郁�\�b�h
	 * @param pos
	 *            �u���b�N�̊J�n�ʒu
	 */
	public BytecodeBlock(BytecodeBlocks group, int pos) {
		this.all = group;
		this.pos = pos;
	}

	/**
	 * �J�n�ʒu�擾.
	 * 
	 * @return �u���b�N�̊J�n�ʒu
	 */
	public int getPos() {
		return pos;
	}

	/**
	 * �I���ʒu�ݒ�.
	 * 
	 * @param end
	 *            �u���b�N�̏I���ʒu
	 */
	public void setEnd(int end) {
		this.end = end;
	}

	/**
	 * �X�e�[�g�����g����.
	 * 
	 * @param ca
	 *            �R�[�h�A�g���r���[�g
	 * @param stack_pos
	 *            �X�^�b�N�̊J�n�ʒu
	 * @throws BadBytecode
	 */
	public void createStatement(CodeAttribute ca, int stack_pos)
			throws BadBytecode {
		list = new ArrayList<BytecodeStatement>((end - pos) / 2);
		CodeIterator i = ca.iterator();
		i.move(pos);
		while (i.hasNext()) {
			int ps = i.next();
			if (ps >= end)
				break;

			BytecodeStatement st = new BytecodeStatement(all.minfo, i, ps);
			st.setStackPos(stack_pos);

			list.add(st);
			stack_pos += st.stackGrowSize();

			// System.out.println(st.dump() + "/" + stack_pos);

			switch (st.getOpcode()) {
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
				int npos = ps + i.u16bitAt(ps + 1);
				all.addStackPos(npos, stack_pos);
				break;
			}
			case GOTO_W:
			case JSR_W: {
				int npos = ps + i.s32bitAt(ps + 1);
				all.addStackPos(npos, stack_pos);
				break;
			}
			}
		}
	}

	/**
	 * �X�e�[�g�����g�擾.
	 * 
	 * @return �X�e�[�g�����g�̃��X�g
	 */
	public List<BytecodeStatement> getStatementList() {
		return list;
	}
}
