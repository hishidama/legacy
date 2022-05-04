package jp.hishidama.test;

import javassist.ClassPool;
import javassist.CtClass;
import jp.hishidama.debuglogrm.AbstractDebugRemoveEditor;
import jp.hishidama.debuglogrm.DebugRemoveEditor;
import jp.hishidama.test.debuglog.DebugLog;
import jp.hishidama.test.debuglog.TestClass;

/**
 * �ʏ�̎��s
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

		// cc���ǂ��ɂ����f�����Ă��Ȃ��̂ŁA�㑱�͕��ʂ�Java�̃N���X

		TestClass tt = new TestClass(new DebugLog());

		tt.exec();
		tt.dump();
	}

}
