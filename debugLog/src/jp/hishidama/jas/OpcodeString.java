package jp.hishidama.jas;

import javassist.bytecode.Opcode;

/**
 * �I�y�R�[�h������N���X.
 * <p>
 * �I�y�R�[�h�ɊY������j�[���j�b�N�������B
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/dbglogrm.html">�Ђ�����</a>
 * @since 2007.11.17
 */
public class OpcodeString implements Opcode {

	/**
	 * �j�[���j�b�N�擾.
	 * <p>
	 * �I�y�R�[�h�ɊY������j�[���j�b�N�̕������Ԃ��B<br>
	 * ��F0x00��"NOP"
	 * </p>
	 * 
	 * @param opcode
	 *            �I�y�R�[�h
	 * @return �j�[���j�b�N
	 */
	public static String get(int opcode) {
		switch (opcode) {
		case AALOAD:
			return "AALOAD";
		case AASTORE:
			return "AASTORE";
		case ACONST_NULL:
			return "ACONST_NULL";
		case ALOAD:
			return "ALOAD";
		case ALOAD_0:
			return "ALOAD_0";
		case ALOAD_1:
			return "ALOAD_1";
		case ALOAD_2:
			return "ALOAD_2";
		case ALOAD_3:
			return "ALOAD_3";
		case ANEWARRAY:
			return "ANEWARRAY";
		case ARETURN:
			return "ARETURN";
		case ARRAYLENGTH:
			return "ARRAYLENGTH";
		case ASTORE:
			return "ASTORE";
		case ASTORE_0:
			return "ASTORE_0";
		case ASTORE_1:
			return "ASTORE_1";
		case ASTORE_2:
			return "ASTORE_2";
		case ASTORE_3:
			return "ASTORE_3";
		case ATHROW:
			return "ATHROW";
		case BALOAD:
			return "BALOAD";
		case BASTORE:
			return "BASTORE";
		case BIPUSH:
			return "BIPUSH";
		case CALOAD:
			return "CALOAD";
		case CASTORE:
			return "CASTORE";
		case CHECKCAST:
			return "CHECKCAST";
		case D2F:
			return "D2F";
		case D2I:
			return "D2I";
		case D2L:
			return "D2L";
		case DADD:
			return "DADD";
		case DALOAD:
			return "DALOAD";
		case DASTORE:
			return "DASTORE";
		case DCMPG:
			return "DCMPG";
		case DCMPL:
			return "DCMPL";
		case DCONST_0:
			return "DCONST_0";
		case DCONST_1:
			return "DCONST_1";
		case DDIV:
			return "DDIV";
		case DLOAD:
			return "DLOAD";
		case DLOAD_0:
			return "DLOAD_0";
		case DLOAD_1:
			return "DLOAD_1";
		case DLOAD_2:
			return "DLOAD_2";
		case DLOAD_3:
			return "DLOAD_3";
		case DMUL:
			return "DMUL";
		case DNEG:
			return "DNEG";
		case DREM:
			return "DREM";
		case DRETURN:
			return "DRETURN";
		case DSTORE:
			return "DSTORE";
		case DSTORE_0:
			return "DSTORE_0";
		case DSTORE_1:
			return "DSTORE_1";
		case DSTORE_2:
			return "DSTORE_2";
		case DSTORE_3:
			return "DSTORE_3";
		case DSUB:
			return "DSUB";
		case DUP:
			return "DUP";
		case DUP2:
			return "DUP2";
		case DUP2_X1:
			return "DUP2_X1";
		case DUP2_X2:
			return "DUP2_X2";
		case DUP_X1:
			return "DUP_X1";
		case DUP_X2:
			return "DUP_X2";
		case F2D:
			return "F2D";
		case F2I:
			return "F2I";
		case F2L:
			return "F2L";
		case FADD:
			return "FADD";
		case FALOAD:
			return "FALOAD";
		case FASTORE:
			return "FASTORE";
		case FCMPG:
			return "FCMPG";
		case FCMPL:
			return "FCMPL";
		case FCONST_0:
			return "FCONST_0";
		case FCONST_1:
			return "FCONST_1";
		case FCONST_2:
			return "FCONST_2";
		case FDIV:
			return "FDIV";
		case FLOAD:
			return "FLOAD";
		case FLOAD_0:
			return "FLOAD_0";
		case FLOAD_1:
			return "FLOAD_1";
		case FLOAD_2:
			return "FLOAD_2";
		case FLOAD_3:
			return "FLOAD_3";
		case FMUL:
			return "FMUL";
		case FNEG:
			return "FNEG";
		case FREM:
			return "FREM";
		case FRETURN:
			return "FRETURN";
		case FSTORE:
			return "FSTORE";
		case FSTORE_0:
			return "FSTORE_0";
		case FSTORE_1:
			return "FSTORE_1";
		case FSTORE_2:
			return "FSTORE_2";
		case FSTORE_3:
			return "FSTORE_3";
		case FSUB:
			return "FSUB";
		case GETFIELD:
			return "GETFIELD";
		case GETSTATIC:
			return "GETSTATIC";
		case GOTO:
			return "GOTO";
		case GOTO_W:
			return "GOTO_W";
		case I2B:
			return "I2B";
		case I2C:
			return "I2C";
		case I2D:
			return "I2D";
		case I2F:
			return "I2F";
		case I2L:
			return "I2L";
		case I2S:
			return "I2S";
		case IADD:
			return "IADD";
		case IALOAD:
			return "IALOAD";
		case IAND:
			return "IAND";
		case IASTORE:
			return "IASTORE";
		case ICONST_0:
			return "ICONST_0";
		case ICONST_1:
			return "ICONST_1";
		case ICONST_2:
			return "ICONST_2";
		case ICONST_3:
			return "ICONST_3";
		case ICONST_4:
			return "ICONST_4";
		case ICONST_5:
			return "ICONST_5";
		case ICONST_M1:
			return "ICONST_M1";
		case IDIV:
			return "IDIV";
		case IFEQ:
			return "IFEQ";
		case IFGE:
			return "IFGE";
		case IFGT:
			return "IFGT";
		case IFLE:
			return "IFLE";
		case IFLT:
			return "IFLT";
		case IFNE:
			return "IFNE";
		case IFNONNULL:
			return "IFNONNULL";
		case IFNULL:
			return "IFNULL";
		case IF_ACMPEQ:
			return "IF_ACMPEQ";
		case IF_ACMPNE:
			return "IF_ACMPNE";
		case IF_ICMPEQ:
			return "IF_ICMPEQ";
		case IF_ICMPGE:
			return "IF_ICMPGE";
		case IF_ICMPGT:
			return "IF_ICMPGT";
		case IF_ICMPLE:
			return "IF_ICMPLE";
		case IF_ICMPLT:
			return "IF_ICMPLT";
		case IF_ICMPNE:
			return "IF_ICMPNE";
		case IINC:
			return "IINC";
		case ILOAD:
			return "ILOAD";
		case ILOAD_0:
			return "ILOAD_0";
		case ILOAD_1:
			return "ILOAD_1";
		case ILOAD_2:
			return "ILOAD_2";
		case ILOAD_3:
			return "ILOAD_3";
		case IMUL:
			return "IMUL";
		case INEG:
			return "INEG";
		case INSTANCEOF:
			return "INSTANCEOF";
		case INVOKEINTERFACE:
			return "INVOKEINTERFACE";
		case INVOKESPECIAL:
			return "INVOKESPECIAL";
		case INVOKESTATIC:
			return "INVOKESTATIC";
		case INVOKEVIRTUAL:
			return "INVOKEVIRTUAL";
		case IOR:
			return "IOR";
		case IREM:
			return "IREM";
		case IRETURN:
			return "IRETURN";
		case ISHL:
			return "ISHL";
		case ISHR:
			return "ISHR";
		case ISTORE:
			return "ISTORE";
		case ISTORE_0:
			return "ISTORE_0";
		case ISTORE_1:
			return "ISTORE_1";
		case ISTORE_2:
			return "ISTORE_2";
		case ISTORE_3:
			return "ISTORE_3";
		case ISUB:
			return "ISUB";
		case IUSHR:
			return "IUSHR";
		case IXOR:
			return "IXOR";
		case JSR:
			return "JSR";
		case JSR_W:
			return "JSR_W";
		case L2D:
			return "L2D";
		case L2F:
			return "L2F";
		case L2I:
			return "L2I";
		case LADD:
			return "LADD";
		case LALOAD:
			return "LALOAD";
		case LAND:
			return "LAND";
		case LASTORE:
			return "LASTORE";
		case LCMP:
			return "LCMP";
		case LCONST_0:
			return "LCONST_0";
		case LCONST_1:
			return "LCONST_1";
		case LDC:
			return "LDC";
		case LDC2_W:
			return "LDC2_W";
		case LDC_W:
			return "LDC_W";
		case LDIV:
			return "LDIV";
		case LLOAD:
			return "LLOAD";
		case LLOAD_0:
			return "LLOAD_0";
		case LLOAD_1:
			return "LLOAD_1";
		case LLOAD_2:
			return "LLOAD_2";
		case LLOAD_3:
			return "LLOAD_3";
		case LMUL:
			return "LMUL";
		case LNEG:
			return "LNEG";
		case LOOKUPSWITCH:
			return "LOOKUPSWITCH";
		case LOR:
			return "LOR";
		case LREM:
			return "LREM";
		case LRETURN:
			return "LRETURN";
		case LSHL:
			return "LSHL";
		case LSHR:
			return "LSHR";
		case LSTORE:
			return "LSTORE";
		case LSTORE_0:
			return "LSTORE_0";
		case LSTORE_1:
			return "LSTORE_1";
		case LSTORE_2:
			return "LSTORE_2";
		case LSTORE_3:
			return "LSTORE_3";
		case LSUB:
			return "LSUB";
		case LUSHR:
			return "LUSHR";
		case LXOR:
			return "LXOR";
		case MONITORENTER:
			return "MONITORENTER";
		case MONITOREXIT:
			return "MONITOREXIT";
		case MULTIANEWARRAY:
			return "MULTIANEWARRAY";
		case NEW:
			return "NEW";
		case NEWARRAY:
			return "NEWARRAY";
		case NOP:
			return "NOP";
		case POP:
			return "POP";
		case POP2:
			return "POP2";
		case PUTFIELD:
			return "PUTFIELD";
		case PUTSTATIC:
			return "PUTSTATIC";
		case RET:
			return "RET";
		case RETURN:
			return "RETURN";
		case SALOAD:
			return "SALOAD";
		case SASTORE:
			return "SASTORE";
		case SIPUSH:
			return "SIPUSH";
		case SWAP:
			return "SWAP";
		case TABLESWITCH:
			return "TABLESWITCH";
		case WIDE:
			return "WIDE";
		}
		return Integer.toString(opcode);
	}
}