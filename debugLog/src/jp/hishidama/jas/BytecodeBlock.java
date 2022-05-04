package jp.hishidama.jas;

import static javassist.bytecode.Opcode.*;

import java.util.*;

import javassist.bytecode.BadBytecode;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.CodeIterator;

/**
 * バイトコードブロック.
 * <p>
 * バイトコードのひとかたまりを保持するクラス。<br>
 * Javassistを利用している。
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/dbglogrm.html">ひしだま</a>
 * @since 2007.11.21
 */
public class BytecodeBlock {

	protected BytecodeBlocks all;

	protected int pos, end;

	protected List<BytecodeStatement> list;

	/**
	 * コンストラクター.
	 * 
	 * @param group
	 *            属するメソッド
	 * @param pos
	 *            ブロックの開始位置
	 */
	public BytecodeBlock(BytecodeBlocks group, int pos) {
		this.all = group;
		this.pos = pos;
	}

	/**
	 * 開始位置取得.
	 * 
	 * @return ブロックの開始位置
	 */
	public int getPos() {
		return pos;
	}

	/**
	 * 終了位置設定.
	 * 
	 * @param end
	 *            ブロックの終了位置
	 */
	public void setEnd(int end) {
		this.end = end;
	}

	/**
	 * ステートメント生成.
	 * 
	 * @param ca
	 *            コードアトリビュート
	 * @param stack_pos
	 *            スタックの開始位置
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
	 * ステートメント取得.
	 * 
	 * @return ステートメントのリスト
	 */
	public List<BytecodeStatement> getStatementList() {
		return list;
	}
}
