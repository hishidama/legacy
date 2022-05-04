package jp.hishidama.jas;

import static javassist.bytecode.Opcode.*;

import java.util.*;

import javassist.bytecode.BadBytecode;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.CodeIterator;
import javassist.bytecode.MethodInfo;

/**
 * バイトコードブロック群.
 * <p>
 * バイトコードの一メソッド分のかたまりを保持するクラス。<br>
 * Javassistを利用している。
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/dbglogrm.html">ひしだま</a>
 * @since 2007.11.21
 */
public class BytecodeBlocks {

	protected MethodInfo minfo;

	protected CodeAttribute ca;

	/**
	 * コンストラクター.
	 * 
	 * @param minfo
	 *            メソッド情報
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

			// どこから呼ばれるか分からないブロックは、暫定的にスタック位置0として処理する
			BytecodeBlock block = list.get(0);
			stackMap.put(block.getPos(), 0);
		}
	}

	/**
	 * スタック位置設定.
	 * <p>
	 * IFやGOTO等でジャンプする先のスタック位置を保存する。
	 * </p>
	 * 
	 * @param pos
	 *            バイトコード上の位置
	 * @param stack_pos
	 *            スタック位置
	 */
	public void addStackPos(int pos, int stack_pos) {
		stackMap.put(pos, stack_pos);
	}

	/**
	 * 全ステートメント取得.
	 * 
	 * @return ステートメントのリスト
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
