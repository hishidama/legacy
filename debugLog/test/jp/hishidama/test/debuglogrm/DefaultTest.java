package jp.hishidama.test.debuglogrm;

import org.junit.Test;

public class DefaultTest extends DebugRemoveEditorTest {

	@Test
	public void test() throws Exception {
		test1(null, "[I]info" + LS + "[W]warning" + LS + "[E]error" + LS
				+ "[F]fatal");
	}
}
