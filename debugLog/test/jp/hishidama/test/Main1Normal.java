package jp.hishidama.test;

import java.lang.reflect.Method;

import javassist.ClassPool;
import javassist.CtClass;
import jp.hishidama.debuglogrm.AbstractDebugRemoveEditor;
import jp.hishidama.debuglogrm.DebugRemoveEditor;
import jp.hishidama.test.debuglog.TestClass;

/**
 * �v���O�������Ŋ���������@�i��{�j�̎����B
 * <p>
 * �ϊ��������N���X���������Ă��āA�Ǐ��I�ɕύX�������ꍇ�͂��̕��@�ł悢�B<br>
 * �unew �N���X�v�łȂ��ucc.toClass().newInstance()�v���g���̂��|�C���g�B
 * </p>
 */
public class Main1Normal {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		ClassPool classPool = ClassPool.getDefault();

		// �~ CtClass cc = classPool.get(TestTarget.class.getName());
		// ���TestTarget�����[�h����Ă��܂��̂ŁA
		// �㑱��Execute��ύX���ăC���X�^���X�����悤�Ƃ����Ƃ���
		// ClassPool#toClass��Javassist.ConnatCompileException����������

		CtClass cc = classPool.get("jp.hishidama.test.debuglog.TestClass");
		AbstractDebugRemoveEditor r = new DebugRemoveEditor();
		cc.instrument(r);

		// �~ TestTarget tt = new TestTarget();
		// �\�[�X����Execute�N���X���w�肵�Ă��܂��Ă��鎞�_��
		// ���N���X�̃��[�h����Execute�N���X�����[�h����Ă��܂��̂�
		// CtClass�������炢�����Ă����f����Ȃ�
		// �i�ύX�O�̏�Ԃœ����j

		// ����Ȃ�OK
		TestClass tt = (TestClass) cc.toClass().newInstance();

		tt.exec();
		tt.dump();
	}

	public static void reflect(TestClass tt) throws Exception {
		Method m = tt.getClass().getMethod("exec", new Class[] {});
		m.invoke(tt, new Object[] {});
	}

}
