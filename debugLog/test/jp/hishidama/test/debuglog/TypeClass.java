package jp.hishidama.test.debuglog;

import jp.hishidama.debuglogrm.UseDebugLog;

@UseDebugLog
public class TypeClass {

	public DebugLog dbgLog;

	public TypeClass() {
		dbgLog = new DebugLog();
	}

	public TypeClass(DebugLog dbglog) {
		dbgLog = dbglog;
	}

	public void exec() {
		dbgLog.writeLogI("start");

		dbgLog.writeLogTbool("bool2", dbgLog.writeLogTbool("bool1", true));
		dbgLog.writeLogTchar("char2", dbgLog.writeLogTchar("char1", 'A'));
		dbgLog
				.writeLogTbyte("byte2", dbgLog.writeLogTbyte("byte1",
						(byte) 127));
		dbgLog.writeLogTshort("short2", dbgLog.writeLogTshort("short1",
				(short) 111));
		dbgLog.writeLogTint("int2", dbgLog.writeLogTint("int1", 222));
		dbgLog.writeLogTlong("long2", dbgLog.writeLogTlong("long1", 123456));
		dbgLog
				.writeLogTfloat("float2", dbgLog.writeLogTfloat("float1",
						123456));
		dbgLog.writeLogTdouble("double2", dbgLog.writeLogTdouble("double1",
				123456));

		dbgLog.writeLogI("end");
	}

	public void dump() {
		System.out.print(dbgLog.toString());
	}
}
