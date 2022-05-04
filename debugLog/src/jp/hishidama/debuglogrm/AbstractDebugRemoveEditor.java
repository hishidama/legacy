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
 * デバッグログ出力メソッド削除抽象クラス.
 * <p>
 * Javassistを利用してデバッグログ出力メソッドを削除する本体。 <br> →<a target="hishidama"
 * href="http://www.ne.jp/asahi/hishidama/home/soft/java/dbglogrm.html">使用例</a>
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/index.html">ひしだま</a>
 * @since 2007.11.17
 * @version 2007.11.21
 */
public abstract class AbstractDebugRemoveEditor extends ExprEditor {

	protected ClassPool classPool = ClassPool.getDefault();

	/**
	 * 変換実行.
	 * <p>
	 * このメソッドはオーバーライドするものではないのだが、本来のExprEditorとは異なる範囲を変更したいので、オーバーライドしている。
	 * </p>
	 * 
	 * @param clazz
	 *            クラス
	 * @param minfo
	 *            メソッド
	 * @return バイトコードを変更したとき、true
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

		// ステートメント一覧作成
		BytecodeBlocks blocks = new BytecodeBlocks(minfo);
		List<BytecodeStatement> list = blocks.getAllStatement();

		// 削除対象抽出
		List<BytecodeStatement> rmlist = getRemoveList(minfo, list);
		if (rmlist.isEmpty()) {
			return false;
		}

		// 削除（NOPで埋める）
		byte[] bytes = ca.getCode();
		// eの昇順で処理する必要がある（rmlistにはその順序で入っている）
		for (BytecodeStatement r : rmlist) {
			String rsig = r.getReturnType();
			int s = r.getFirstPos();
			int e = r.getEndPos();
			// System.out.println("◆" + s + "～" + e);

			fillNop(bytes, s, e, rsig);
		}

		return true;
	}

	/**
	 * 削除対象抽出.
	 * <p>
	 * 削除対象のデバッグログ出力メソッドのステートメント一覧を返す。<br>
	 * それぞれのメソッドで使っている引数の先頭位置も探してセットする。
	 * </p>
	 * 
	 * @param minfo
	 *            メソッド情報
	 * @param list
	 *            メソッド内の全ステートメントのリスト
	 * @return 削除対象メソッド呼び出しのステートメントのリスト
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
	 * NOPで埋める。
	 * <p>
	 * 指定された範囲をNOPで埋める。<br>
	 * メソッド呼び出しを削除するのが目的なので、そのメソッドの戻り型が「V（void）」以外の場合、（バイト列の末尾に）その型に応じた0をPUSHする。
	 * </p>
	 * 
	 * @param bytes
	 *            対象バイト配列
	 * @param s
	 *            開始位置
	 * @param e
	 *            終了位置（この位置は埋める対象には含まれない）
	 * @param rsig
	 *            戻り型のシグニチャー
	 */
	protected void fillNop(byte[] bytes, int s, int e, String rsig) {
		Arrays.fill(bytes, s, e, (byte) NOP);

		// 戻り値があるメソッドの場合、本来ならその値がPUSHされる。
		// 後続でその値を使用しているので 何らかの値をPUSHしておく必要がある
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
	 * 処理を行うクラスかどうかを判断する。
	 * 
	 * @param cc
	 *            対象クラス
	 * @return 処理対象クラスの場合、true
	 */
	protected abstract boolean useDebugLog(CtClass cc);

	/**
	 * デバッグログ出力メソッドかどうかを判断する。
	 * 
	 * @param m
	 *            判定対象メソッド
	 * @return 削除対象の場合、true
	 */
	protected abstract boolean isDebugLogWriteMethod(CtMethod m);

	/**
	 * @deprecated 当クラスではこのメソッドを呼び出しません。
	 */
	@Override
	public final void edit(Cast c) throws CannotCompileException {
	}

	/**
	 * @deprecated 当クラスではこのメソッドを呼び出しません。
	 */
	@Override
	public final void edit(ConstructorCall c) throws CannotCompileException {
	}

	/**
	 * @deprecated 当クラスではこのメソッドを呼び出しません。
	 */
	@Override
	public final void edit(FieldAccess f) throws CannotCompileException {
	}

	/**
	 * @deprecated 当クラスではこのメソッドを呼び出しません。
	 */
	@Override
	public final void edit(Handler h) throws CannotCompileException {
	}

	/**
	 * @deprecated 当クラスではこのメソッドを呼び出しません。
	 */
	@Override
	public final void edit(Instanceof i) throws CannotCompileException {
	}

	/**
	 * @deprecated 当クラスではこのメソッドを呼び出しません。
	 */
	@Override
	public final void edit(MethodCall m) throws CannotCompileException {
	}

	/**
	 * @deprecated 当クラスではこのメソッドを呼び出しません。
	 */
	@Override
	public final void edit(NewArray a) throws CannotCompileException {
	}

	/**
	 * @deprecated 当クラスではこのメソッドを呼び出しません。
	 */
	@Override
	public final void edit(NewExpr e) throws CannotCompileException {
	}
}
