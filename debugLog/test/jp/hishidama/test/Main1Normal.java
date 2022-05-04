package jp.hishidama.test;

import java.lang.reflect.Method;

import javassist.ClassPool;
import javassist.CtClass;
import jp.hishidama.debuglogrm.AbstractDebugRemoveEditor;
import jp.hishidama.debuglogrm.DebugRemoveEditor;
import jp.hishidama.test.debuglog.TestClass;

/**
 * プログラム内で完結する方法（基本）の実験。
 * <p>
 * 変換したいクラスが分かっていて、局所的に変更したい場合はこの方法でよい。<br>
 * 「new クラス」でなく「cc.toClass().newInstance()」を使うのがポイント。
 * </p>
 */
public class Main1Normal {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		ClassPool classPool = ClassPool.getDefault();

		// × CtClass cc = classPool.get(TestTarget.class.getName());
		// 先にTestTargetがロードされてしまうので、
		// 後続でExecuteを変更してインスタンス化しようとしたときに
		// ClassPool#toClassでJavassist.ConnatCompileExceptionが発生する

		CtClass cc = classPool.get("jp.hishidama.test.debuglog.TestClass");
		AbstractDebugRemoveEditor r = new DebugRemoveEditor();
		cc.instrument(r);

		// × TestTarget tt = new TestTarget();
		// ソース内にExecuteクラスを指定してしまっている時点で
		// 当クラスのロード時にExecuteクラスもロードされてしまうので
		// CtClassをいくらいじっても反映されない
		// （変更前の状態で動く）

		// これならOK
		TestClass tt = (TestClass) cc.toClass().newInstance();

		tt.exec();
		tt.dump();
	}

	public static void reflect(TestClass tt) throws Exception {
		Method m = tt.getClass().getMethod("exec", new Class[] {});
		m.invoke(tt, new Object[] {});
	}

}
