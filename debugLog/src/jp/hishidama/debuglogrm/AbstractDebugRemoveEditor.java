package jp.hishidama.debuglogrm;

import static javassist.bytecode.Opcode.*;

import java.util.*;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.ConstPool;
import javassist.bytecode.MethodInfo;
import javassist.expr.Cast;
import javassist.expr.ConstructorCall;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;
import javassist.expr.Handler;
import javassist.expr.Instanceof;
import javassist.expr.MethodCall;
import javassist.expr.NewArray;
import javassist.expr.NewExpr;
import jp.hishidama.jas.BytecodeBlocks;
import jp.hishidama.jas.BytecodeStatement;

/**
 * �f�o�b�O���O�o�̓��\�b�h�폜���ۃN���X.
 * <p>
 * Javassist�𗘗p���ăf�o�b�O���O�o�̓��\�b�h���폜����{�́B <br> ��<a target="hishidama"
 * href="http://www.ne.jp/asahi/hishidama/home/soft/java/dbglogrm.html">�g�p��</a>
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/index.html">�Ђ�����</a>
 * @since 2007.11.17
 * @version 2007.11.21
 */
public abstract class AbstractDebugRemoveEditor extends ExprEditor {

	protected ClassPool classPool = ClassPool.getDefault();

	/**
	 * �ϊ����s.
	 * <p>
	 * ���̃��\�b�h�̓I�[�o�[���C�h������̂ł͂Ȃ��̂����A�{����ExprEditor�Ƃ͈قȂ�͈͂�ύX�������̂ŁA�I�[�o�[���C�h���Ă���B
	 * </p>
	 * 
	 * @param clazz
	 *            �N���X
	 * @param minfo
	 *            ���\�b�h
	 * @return �o�C�g�R�[�h��ύX�����Ƃ��Atrue
	 */
	@Override
	public boolean doit(CtClass clazz, MethodInfo minfo)
			throws CannotCompileException {

		if (!useDebugLog(clazz)) {
			return false;
		}

		CodeAttribute ca = minfo.getCodeAttribute();
		if (ca == null) {
			return false;
		}

		// �X�e�[�g�����g�ꗗ�쐬
		BytecodeBlocks blocks = new BytecodeBlocks(minfo);
		List<BytecodeStatement> list = blocks.getAllStatement();

		// �폜�Ώے��o
		List<BytecodeStatement> rmlist = getRemoveList(minfo, list);
		if (rmlist.isEmpty()) {
			return false;
		}

		// �폜�iNOP�Ŗ��߂�j
		byte[] bytes = ca.getCode();
		// e�̏����ŏ�������K�v������irmlist�ɂ͂��̏����œ����Ă���j
		for (BytecodeStatement r : rmlist) {
			String rsig = r.getReturnType();
			int s = r.getFirstPos();
			int e = r.getEndPos();
			// System.out.println("��" + s + "�`" + e);

			fillNop(bytes, s, e, rsig);
		}

		return true;
	}

	/**
	 * �폜�Ώے��o.
	 * <p>
	 * �폜�Ώۂ̃f�o�b�O���O�o�̓��\�b�h�̃X�e�[�g�����g�ꗗ��Ԃ��B<br>
	 * ���ꂼ��̃��\�b�h�Ŏg���Ă�������̐擪�ʒu���T���ăZ�b�g����B
	 * </p>
	 * 
	 * @param minfo
	 *            ���\�b�h���
	 * @param list
	 *            ���\�b�h���̑S�X�e�[�g�����g�̃��X�g
	 * @return �폜�Ώۃ��\�b�h�Ăяo���̃X�e�[�g�����g�̃��X�g
	 */
	protected List<BytecodeStatement> getRemoveList(MethodInfo minfo,
			List<BytecodeStatement> list) {
		List<BytecodeStatement> rmlist = new ArrayList<BytecodeStatement>(list
				.size() / 8);

		ConstPool cp = minfo.getConstPool();

		for (int i = 0; i < list.size(); i++) {
			BytecodeStatement st = list.get(i);
			switch (st.getOpcode()) {
			case INVOKEVIRTUAL:
			case INVOKESPECIAL:
			case INVOKESTATIC:
			case INVOKEINTERFACE:
				try {
					int index = st.getOperand16();
					CtClass cc = classPool.get(cp.getMethodrefClassName(index));
					CtMethod cm = cc.getMethod(cp.getMethodrefName(index), cp
							.getMethodrefType(index));
					if (isDebugLogWriteMethod(cm)) {
						// System.out.println(cc.getName() + "#"
						// + cm.getLongName());
						int sp = st.getStackPos() + st.stackDataSize();
						for (int j = i - 1; j >= 0; j--) {
							BytecodeStatement s = list.get(j);
							if (s.getStackPos() == sp) {
								st.setRangePos(s.getPos(), list.get(i + 1)
										.getPos());
								break;
							}
						}
						rmlist.add(st);
					}
				} catch (NotFoundException e) {
				}
				break;
			}
		}

		return rmlist;
	}

	/**
	 * NOP�Ŗ��߂�B
	 * <p>
	 * �w�肳�ꂽ�͈͂�NOP�Ŗ��߂�B<br>
	 * ���\�b�h�Ăяo�����폜����̂��ړI�Ȃ̂ŁA���̃��\�b�h�̖߂�^���uV�ivoid�j�v�ȊO�̏ꍇ�A�i�o�C�g��̖����Ɂj���̌^�ɉ�����0��PUSH����B
	 * </p>
	 * 
	 * @param bytes
	 *            �Ώۃo�C�g�z��
	 * @param s
	 *            �J�n�ʒu
	 * @param e
	 *            �I���ʒu�i���̈ʒu�͖��߂�Ώۂɂ͊܂܂�Ȃ��j
	 * @param rsig
	 *            �߂�^�̃V�O�j�`���[
	 */
	protected void fillNop(byte[] bytes, int s, int e, String rsig) {
		Arrays.fill(bytes, s, e, (byte) NOP);

		// �߂�l�����郁�\�b�h�̏ꍇ�A�{���Ȃ炻�̒l��PUSH�����B
		// �㑱�ł��̒l���g�p���Ă���̂� ���炩�̒l��PUSH���Ă����K�v������
		switch (rsig.charAt(0)) {
		case 'V': // void
			break;
		case 'Z': // boolean
		case 'C': // char
		case 'B': // byte
		case 'S': // short
		case 'I': // int
			bytes[e - 1] = (byte) ICONST_0;
			break;
		case 'J': // long
			bytes[e - 1] = (byte) LCONST_0;
			break;
		case 'F': // float
			bytes[e - 1] = (byte) FCONST_0;
			break;
		case 'D': // double
			bytes[e - 1] = (byte) DCONST_0;
			break;
		default: // Object
			bytes[e - 1] = (byte) ACONST_NULL;
			break;
		}
	}

	/**
	 * �������s���N���X���ǂ����𔻒f����B
	 * 
	 * @param cc
	 *            �ΏۃN���X
	 * @return �����ΏۃN���X�̏ꍇ�Atrue
	 */
	protected abstract boolean useDebugLog(CtClass cc);

	/**
	 * �f�o�b�O���O�o�̓��\�b�h���ǂ����𔻒f����B
	 * 
	 * @param m
	 *            ����Ώۃ��\�b�h
	 * @return �폜�Ώۂ̏ꍇ�Atrue
	 */
	protected abstract boolean isDebugLogWriteMethod(CtMethod m);

	/**
	 * @deprecated ���N���X�ł͂��̃��\�b�h���Ăяo���܂���B
	 */
	@Override
	public final void edit(Cast c) throws CannotCompileException {
	}

	/**
	 * @deprecated ���N���X�ł͂��̃��\�b�h���Ăяo���܂���B
	 */
	@Override
	public final void edit(ConstructorCall c) throws CannotCompileException {
	}

	/**
	 * @deprecated ���N���X�ł͂��̃��\�b�h���Ăяo���܂���B
	 */
	@Override
	public final void edit(FieldAccess f) throws CannotCompileException {
	}

	/**
	 * @deprecated ���N���X�ł͂��̃��\�b�h���Ăяo���܂���B
	 */
	@Override
	public final void edit(Handler h) throws CannotCompileException {
	}

	/**
	 * @deprecated ���N���X�ł͂��̃��\�b�h���Ăяo���܂���B
	 */
	@Override
	public final void edit(Instanceof i) throws CannotCompileException {
	}

	/**
	 * @deprecated ���N���X�ł͂��̃��\�b�h���Ăяo���܂���B
	 */
	@Override
	public final void edit(MethodCall m) throws CannotCompileException {
	}

	/**
	 * @deprecated ���N���X�ł͂��̃��\�b�h���Ăяo���܂���B
	 */
	@Override
	public final void edit(NewArray a) throws CannotCompileException {
	}

	/**
	 * @deprecated ���N���X�ł͂��̃��\�b�h���Ăяo���܂���B
	 */
	@Override
	public final void edit(NewExpr e) throws CannotCompileException {
	}
}
