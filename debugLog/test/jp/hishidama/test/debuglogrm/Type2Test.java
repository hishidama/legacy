package jp.hishidama.test.debuglogrm;

import static org.junit.Assert.assertEquals;
import jp.hishidama.test.debuglog.DebugLog;
import jp.hishidama.test.debuglog.TypeClass;

import org.junit.Test;

public class Type2Test extends DebugRemoveEditorTest {

	@Test
	public void test() throws Exception {
		setup("IT");

		DebugLog dbg = new DebugLog();
		TypeClass tt = new TypeClass(dbg);
		tt.exec();

		String expected = "";
		expected += "[I]start" + LS;
		expected += "[T]bool1: true" + LS;
		expected += "[T]bool2: true" + LS;
		expected += "[T]char1: A" + LS;
		expected += "[T]char2: A" + LS;
		expected += "[T]byte1: 127" + LS;
		expected += "[T]byte2: 127" + LS;
		expected += "[T]short1: 111" + LS;
		expected += "[T]short2: 111" + LS;
		expected += "[T]int1: 222" + LS;
		expected += "[T]int2: 222" + LS;
		expected += "[T]long1: 123456" + LS;
		expected += "[T]long2: 123456" + LS;
		expected += "[T]float1: 123456.0" + LS;
		expected += "[T]float2: 123456.0" + LS;
		expected += "[T]double1: 123456.0" + LS;
		expected += "[T]double2: 123456.0" + LS;
		expected += "[I]end" + LS;

		assertEquals(expected, dbg.toString());
	}
}
