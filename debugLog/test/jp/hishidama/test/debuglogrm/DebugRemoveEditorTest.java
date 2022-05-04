package jp.hishidama.test.debuglogrm;

import static org.junit.Assert.assertEquals;

import java.security.ProtectionDomain;

import javassist.ClassPool;
import javassist.CtClass;
import jp.hishidama.debuglogrm.DebugRemoveEditor;
import jp.hishidama.test.debuglog.DebugLog;
import jp.hishidama.test.debuglog.TestClass;

public class DebugRemoveEditorTest {

	protected static final String LS = System.getProperty("line.separator");

	protected DebugRemoveEditor r;

	protected void convert(String className) {
		try {
			ClassPool classPool = ClassPool.getDefault();

			CtClass cc = classPool.get(className);
			cc.instrument(r);

			Class tc = this.getClass();
			ClassLoader cl = tc.getClassLoader();
			ProtectionDomain pd = tc.getProtectionDomain();
			cc.toClass(cl, pd);

		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected void setup(String level) {
		r = new DebugRemoveEditor(level);
		convert("jp.hishidama.test.debuglog.TestClass");
		convert("jp.hishidama.test.debuglog.TypeClass");
	}

	protected void test1(String level, String expected) {
		setup(level);

		DebugLog dbg = new DebugLog();
		TestClass tt = new TestClass(dbg);
		tt.exec();

		assertEquals(expected + LS, dbg.toString());
	}
}
