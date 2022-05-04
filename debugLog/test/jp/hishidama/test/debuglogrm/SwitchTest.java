package jp.hishidama.test.debuglogrm;

import static org.junit.Assert.assertEquals;
import jp.hishidama.debuglogrm.DebugRemoveEditor;
import jp.hishidama.test.debuglog.DebugLog;
import jp.hishidama.test.debuglog.SwitchClass;

import org.junit.Test;

public class SwitchTest extends DebugRemoveEditorTest {

	@Test
	public void test() throws Exception {
		r = new DebugRemoveEditor("EI");
		convert("jp.hishidama.test.debuglog.SwitchClass");

		DebugLog dbg = new DebugLog();
		SwitchClass tt = new SwitchClass(dbg);
		tt.exec(0);
		assertEquals("[I]info0" + LS + "[E]error0" + LS, dbg.toString());
		dbg.clear();

		tt.exec(1);
		assertEquals("[I]info1" + LS + "[E]error1" + LS, dbg.toString());
		dbg.clear();

		tt.exec(2);
		assertEquals("[I]info" + LS + "[E]error" + LS, dbg.toString());
		dbg.clear();
	}
}
