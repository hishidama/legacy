package jp.hishidama.test.debuglog;

import jp.hishidama.debuglogrm.UseDebugLog;

@UseDebugLog
public class SwitchClass {
	public DebugLog dbgLog;

	public SwitchClass() {
		dbgLog = new DebugLog();
	}

	public SwitchClass(DebugLog dbglog) {
		dbgLog = dbglog;
	}

	public void exec(int n) {
		System.out.println("start");
		switch (n) {
		case 0:
			dbgLog.writeLogD("debug0");
			dbgLog.writeLogI("info0");
			dbgLog.writeLogE("error0");
			break;
		case 1:
			dbgLog.writeLogD("debug1");
			dbgLog.writeLogI("info1");
			dbgLog.writeLogE("error1");
			break;
		default:
			dbgLog.writeLogD("debug");
			dbgLog.writeLogI("info");
			dbgLog.writeLogE("error");
			break;
		}
		System.out.println("end");
	}

	public void dump() {
		System.out.print(dbgLog.toString());
	}
}
