package jp.hishidama.jas;

import javassist.bytecode.CodeIterator;
import javassist.bytecode.ConstPool;
import javassist.bytecode.Descriptor;
import javassist.bytecode.MethodInfo;
import static javassist.bytecode.Opcode.*;

/**
 * バイトコード命令.
 * <p>
 * バイトコードの一命令を表すクラス。<br>
 * Javassistを利用している。
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/dbglogrm.html">ひしだま</a>
 * @since 2007.11.17
 * @version 2007.11.21
 */
public class BytecodeStatement {
	protected MethodInfo method;

	protected CodeIterator iterator;

	/** バイト配列上の位置 */
	protected int pos;

	/** オペレーションコード */
	protected int opcode;

	/** この命令までに消費したスタック */
	protected int stack_pos;

	/**
	 * コンストラクター.
	 * 
	 * @param minfo
	 *            メソッド
	 * @param iterator
	 *            コードイテレーター
	 * @param pos
	 *            バイト配列上の位置
	 */
	public BytecodeStatement(MethodInfo minfo, CodeIterator iterator, int pos) {
		this.method = minfo;
		this.iterator = iterator;
		this.pos = pos;
		this.opcode = iterator.byteAt(pos);
	}

	/**
	 * 位置取得.
	 * 
	 * @return バイト配列上の位置
	 */
	public int getPos() {
		return pos;
	}

	/**
	 * オペレーションコード取得.
	 * 
	 * @return オペレーションコード
	 */
	public int getOpcode() {
		return opcode;
	}

	/**
	 * オペランド（16bit）取得.
	 * <p>
	 * 命令の引数を返す。
	 * </p>
	 * 
	 * @return オペランド
	 */
	public int getOperand16() {
		return iterator.u16bitAt(pos + 1);
	}

	/**
	 * ダンプ用文字列取得.
	 * <p>
	 * ニーモニックの形に変換する。デバッグ目的。<br>
	 * 現状では一部の命令しか対応してない…。
	 * </p>
	 * 
	 * @return ニーモニック
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
	 * スタック位置設定.
	 * 
	 * @param stack_pos
	 *            スタック位置
	 */
	public void setStackPos(int stack_pos) {
		this.stack_pos = stack_pos;
	}

	/**
	 * スタック位置取得.
	 * 
	 * @return スタック位置
	 */
	public int getStackPos() {
		return stack_pos;
	}

	/**
	 * スタック増加サイズ取得.
	 * <p>
	 * 命令実行後に増加するスタックの大きさを返す。<br>
	 * 減少する場合は負の値を返す。
	 * </p>
	 * 
	 * @return スタックサイズ
	 */
	public int stackGrowSize() {
		return stackSize(true);
	}

	/**
	 * スタック使用サイズ取得.
	 * <p>
	 * 命令が使用するスタックの大きさを返す。<br>
	 * 現状ではメソッドにしか対応していない。
	 * </p>
	 * <p>
	 * 例えば「static int test(int,int,int)」を呼び出す場合、<br>
	 * 引数がint3つなので使用するスタックは3。当メソッドは3を返す。<br>
	 * {@link #stackGrowSize()}だと戻り値を格納する分が引かれ、2を返す。
	 * </p>
	 * 
	 * @return スタックサイズ
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
				// static以外はthis分を補正する
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
				// static以外はthis分を補正する
				n--;
			}
			return n;
		}
		default:
			return STACK_GROW[opcode];
		}
	}

	/**
	 * 戻り型取得.
	 * <p>
	 * 命令がメソッドのときだけしか使用不可。
	 * </p>
	 * 
	 * @return メソッドの戻り値の型
	 */
	public String getReturnType() {
		ConstPool cp = method.getConstPool();
		int index = iterator.u16bitAt(pos + 1);
		String sig = cp.getMethodrefType(index);
		return sig.substring(sig.lastIndexOf(')') + 1);
	}

	protected int begin, end;

	/**
	 * 範囲設定.
	 * 
	 * @param begin
	 *            開始位置
	 * @param end
	 *            終了位置
	 */
	public void setRangePos(int begin, int end) {
		this.begin = begin;
		this.end = end;
	}

	/**
	 * 範囲の開始位置取得.
	 * 
	 * @return 開始位置
	 */
	public int getFirstPos() {
		return begin;
	}

	/**
	 * 範囲の終了位置取得.
	 * 
	 * @return 終了位置
	 */
	public int getEndPos() {
		return end;
	}
}
