package jp.hishidama.test.debuglog;

import jp.hishidama.debuglogrm.UseDebugLog;

@UseDebugLog
public class TestClass {

	public DebugLog dbgLog;

	public TestClass() {
		dbgLog = new DebugLog();
	}

	public TestClass(DebugLog dbglog) {
		dbgLog = dbglog;
	}

	public void exec() {
		System.out.println("start");
		dbgLog.writeLogT("trace");
		dbgLog.writeLogD("debug");
		dbgLog.writeLogV("verbose");
		dbgLog.writeLogI("info");
		dbgLog.writeLogW("warning");
		dbgLog.writeLogE("error");
		dbgLog.writeLogF("fatal");
		System.out.println("end");
	}

	public void dump() {
		System.out.print(dbgLog.toString());
	}
}
