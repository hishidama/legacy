package jp.hishidama.test.debuglogrm;

import static org.junit.Assert.assertEquals;
import jp.hishidama.debuglogrm.DebugRemoveEditor;
import jp.hishidama.test.debuglog.DebugLog;
import jp.hishidama.test.debuglog.IfClass;

import org.junit.Test;

public class IfTest extends DebugRemoveEditorTest {

	@Test
	public void test() throws Exception {
		r = new DebugRemoveEditor("EI");
		convert("jp.hishidama.test.debuglog.IfClass");

		DebugLog dbg = new DebugLog();
		IfClass tt = new IfClass(dbg);
		tt.exec();
		assertEquals("[I]info:n!=0" + LS + "[E]error:n!=0" + LS, dbg.toString());
	}
}
