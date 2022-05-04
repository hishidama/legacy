package jp.hishidama.test;

//import java.lang.reflect.Method;
import java.security.ProtectionDomain;

import javassist.ClassPool;
import javassist.CtClass;
import jp.hishidama.debuglogrm.AbstractDebugRemoveEditor;
import jp.hishidama.debuglogrm.DebugRemoveEditor;
import jp.hishidama.test.debuglog.DebugLog;
import jp.hishidama.test.debuglog.TestClass;

/**
 * Javassist�ɂ���ĕϊ������N���X���N���X���[�_�[�ɓo�^���邱�Ƃɂ��A���ʂɁunew�v�ł��邱�Ƃ̎����B
 * <p>
 * �ϊ��ΏۃN���X�͌ʂɕϊ����āA1���N���X���[�_�[�ɓo�^���Ȃ���΂Ȃ�Ȃ��B
 * </p>
 */
public class Main3CL {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		ClassPool classPool = ClassPool.getDefault();

		CtClass cc = classPool.get("jp.hishidama.test.debuglog.TestClass");
		AbstractDebugRemoveEditor r = new DebugRemoveEditor();
		cc.instrument(r);

		// �N���X���[�_�[�ɓo�^���Ă��܂��΁A��͕��ʂ�new�ł���
		ClassLoader cl = Main3CL.class.getClassLoader();
		ProtectionDomain pd = Main3CL.class.getProtectionDomain();
		cc.toClass(cl, pd);

		TestClass tt = new TestClass(new DebugLog());
		tt.exec();
		tt.dump();

		// Class<?> tc = cl.loadClass("jp.hishidama.debuglog.TestClassCaller");
		// �~ Class tc = Class.forName("jp.hishidama.debuglog.TestClassCaller");
		// Method m = tc.getDeclaredMethod("call");
		// m.invoke(null, new Object[] {});

	}

}
