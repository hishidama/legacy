package jp.hishidama.test.debuglog;

import jp.hishidama.debuglogrm.UseDebugLog;

@UseDebugLog
public class IfClass {
	public DebugLog dbgLog;

	protected int n = 0;

	public IfClass() {
		dbgLog = new DebugLog();
		n = 0;
	}

	public IfClass(DebugLog dbglog) {
		dbgLog = dbglog;
		n = 1;
	}

	public void exec() {
		System.out.println("start");
		dbgLog.writeLogD("debug:" + (n == 0 ? "n==0" : "n!=0"));
		dbgLog.writeLogI("info:" + (n == 0 ? "n==0" : "n!=0"));
		dbgLog.writeLogE("error:" + (n == 0 ? "n==0" : "n!=0"));
		System.out.println("end");
	}

	public void dump() {
		System.out.print(dbgLog.toString());
	}
}
