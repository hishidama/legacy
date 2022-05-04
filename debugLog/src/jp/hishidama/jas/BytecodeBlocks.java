package jp.hishidama.jas;

import static javassist.bytecode.Opcode.*;

import java.util.*;

import javassist.bytecode.BadBytecode;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.CodeIterator;
import javassist.bytecode.MethodInfo;

/**
 * �o�C�g�R�[�h�u���b�N�Q.
 * <p>
 * �o�C�g�R�[�h�̈ꃁ�\�b�h���̂����܂��ێ�����N���X�B<br>
 * Javassist�𗘗p���Ă���B
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/dbglogrm.html">�Ђ�����</a>
 * @since 2007.11.21
 */
public class BytecodeBlocks {

	protected MethodInfo minfo;

	protected CodeAttribute ca;

	/**
	 * �R���X�g���N�^�[.
	 * 
	 * @param minfo
	 *            ���\�b�h���
	 */
	public BytecodeBlocks(MethodInfo minfo) {
		this.minfo = minfo;
		this.ca = minfo.getCodeAttribute();
		init();
	}

	protected void init() {
		try {
			create();
			initEndPos();
			createStatement();
		} catch (BadBytecode e) {
			throw new RuntimeException(e);
		}
	}

	protected TreeMap<Integer, BytecodeBlock> blockMap;

	protected void create() throws BadBytecode {
		blockMap = new TreeMap<Integer, BytecodeBlock>();
		blockMap.put(0, new BytecodeBlock(this, 0));

		boolean begin = false;
		for (CodeIterator i = ca.iterator(); i.hasNext();) {
			int pos = i.next();
			if (begin) {
				blockMap.put(pos, new BytecodeBlock(this, pos));
				begin = false;
			}
			switch (i.byteAt(pos)) {
			case GOTO:
			case GOTO_W:
			case RET:
			case IRETURN:
			case LRETURN:
			case FRETURN:
			case DRETURN:
			case ARETURN:
			case RETURN:
				begin = true;
				break;
			}
		}
	}

	protected void initEndPos() {
		BytecodeBlock before = null;
		for (Integer pos : blockMap.keySet()) {
			BytecodeBlock bb = blockMap.get(pos);
			if (before != null) {
				before.setEnd(bb.getPos());
			}
			before = bb;
		}
		if (before != null) {
			before.setEnd(ca.getCodeLength());
		}
	}

	protected Map<Integer, Integer> stackMap;

	protected void createStatement() throws BadBytecode {
		stackMap = new HashMap<Integer, Integer>(ca.getCodeLength() / 8);
		stackMap.put(0, 0);

		List<BytecodeBlock> list = new LinkedList<BytecodeBlock>();
		for (Integer pos : blockMap.keySet()) {
			list.add(blockMap.get(pos));
		}

		loop: while (!list.isEmpty()) {
			for (Iterator<BytecodeBlock> i = list.iterator(); i.hasNext();) {
				BytecodeBlock block = i.next();
				Integer stack_pos = stackMap.get(block.getPos());
				if (stack_pos != null) {
					block.createStatement(ca, stack_pos);
					i.remove();
					continue loop;
				}
			}

			// �ǂ�����Ă΂�邩������Ȃ��u���b�N�́A�b��I�ɃX�^�b�N�ʒu0�Ƃ��ď�������
			BytecodeBlock block = list.get(0);
			stackMap.put(block.getPos(), 0);
		}
	}

	/**
	 * �X�^�b�N�ʒu�ݒ�.
	 * <p>
	 * IF��GOTO���ŃW�����v�����̃X�^�b�N�ʒu��ۑ�����B
	 * </p>
	 * 
	 * @param pos
	 *            �o�C�g�R�[�h��̈ʒu
	 * @param stack_pos
	 *            �X�^�b�N�ʒu
	 */
	public void addStackPos(int pos, int stack_pos) {
		stackMap.put(pos, stack_pos);
	}

	/**
	 * �S�X�e�[�g�����g�擾.
	 * 
	 * @return �X�e�[�g�����g�̃��X�g
	 */
	public List<BytecodeStatement> getAllStatement() {
		int len = 0;
		for (Integer pos : blockMap.keySet()) {
			BytecodeBlock bb = blockMap.get(pos);
			len += bb.getStatementList().size();
		}

		List<BytecodeStatement> list = new ArrayList<BytecodeStatement>(len);
		for (Integer pos : blockMap.keySet()) {
			BytecodeBlock bb = blockMap.get(pos);
			list.addAll(bb.getStatementList());
		}

		return list;
	}
}
