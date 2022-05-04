package jp.hishidama.test;

import javassist.ClassPool;
import javassist.CtClass;
import jp.hishidama.debuglogrm.AbstractDebugRemoveEditor;
import jp.hishidama.debuglogrm.DebugRemoveEditor;
import jp.hishidama.test.debuglog.DebugLog;
import jp.hishidama.test.debuglog.TestClass;

/**
 * 通常の実行
 */
public class Main0NoChange {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		ClassPool classPool = ClassPool.getDefault();

		CtClass cc = classPool.get("jp.hishidama.test.debuglog.TestClass");
		AbstractDebugRemoveEditor r = new DebugRemoveEditor();
		cc.instrument(r);

		// ccをどこにも反映させていないので、後続は普通のJavaのクラス

		TestClass tt = new TestClass(new DebugLog());

		tt.exec();
		tt.dump();
	}

}
