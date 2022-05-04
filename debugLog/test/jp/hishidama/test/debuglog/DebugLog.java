package jp.hishidama.test.debuglog;

import jp.hishidama.debuglogrm.DebugLevel;
import jp.hishidama.debuglogrm.DebugLogWriteMethod;

public class DebugLog {

	public static final String LS = System.getProperty("line.separator");

	protected StringBuilder sb = new StringBuilder();

	@DebugLogWriteMethod(DebugLevel.TRACE)
	public void writeLogT(String message) {
		sb.append("[T]");
		sb.append(message);
		sb.append(LS);
	}

	@DebugLogWriteMethod(DebugLevel.TRACE)
	public boolean writeLogTbool(String message, Boolean val) {
		sb.append("[T]");
		sb.append(message);
		sb.append(": ");
		sb.append(val);
		sb.append(LS);
		return val;
	}

	@DebugLogWriteMethod(DebugLevel.TRACE)
	public char writeLogTchar(String message, char val) {
		sb.append("[T]");
		sb.append(message);
		sb.append(": ");
		sb.append(val);
		sb.append(LS);
		return val;
	}

	@DebugLogWriteMethod(DebugLevel.TRACE)
	public byte writeLogTbyte(String message, byte val) {
		sb.append("[T]");
		sb.append(message);
		sb.append(": ");
		sb.append(val);
		sb.append(LS);
		return val;
	}

	@DebugLogWriteMethod(DebugLevel.TRACE)
	public short writeLogTshort(String message, short val) {
		sb.append("[T]");
		sb.append(message);
		sb.append(": ");
		sb.append(val);
		sb.append(LS);
		return val;
	}

	@DebugLogWriteMethod(DebugLevel.TRACE)
	public int writeLogTint(String message, int val) {
		sb.append("[T]");
		sb.append(message);
		sb.append(": ");
		sb.append(val);
		sb.append(LS);
		return val;
	}

	@DebugLogWriteMethod(DebugLevel.TRACE)
	public long writeLogTlong(String message, long val) {
		sb.append("[T]");
		sb.append(message);
		sb.append(": ");
		sb.append(val);
		sb.append(LS);
		return val;
	}

	@DebugLogWriteMethod(DebugLevel.TRACE)
	public float writeLogTfloat(String message, float val) {
		sb.append("[T]");
		sb.append(message);
		sb.append(": ");
		sb.append(val);
		sb.append(LS);
		return val;
	}

	@DebugLogWriteMethod(DebugLevel.TRACE)
	public double writeLogTdouble(String message, double val) {
		sb.append("[T]");
		sb.append(message);
		sb.append(": ");
		sb.append(val);
		sb.append(LS);
		return val;
	}

	@DebugLogWriteMethod(DebugLevel.DEBUG)
	public void writeLogD(String message) {
		sb.append("[D]");
		sb.append(message);
		sb.append(LS);
	}

	@DebugLogWriteMethod(DebugLevel.VERBOSE)
	public void writeLogV(String message) {
		sb.append("[V]");
		sb.append(message);
		sb.append(LS);
	}

	@DebugLogWriteMethod(DebugLevel.INFO)
	public void writeLogI(String message) {
		sb.append("[I]");
		sb.append(message);
		sb.append(LS);
	}

	@DebugLogWriteMethod(DebugLevel.WARNING)
	public void writeLogW(String message) {
		sb.append("[W]");
		sb.append(message);
		sb.append(LS);
	}

	@DebugLogWriteMethod(DebugLevel.ERROR)
	public void writeLogE(String message) {
		sb.append("[E]");
		sb.append(message);
		sb.append(LS);
	}

	@DebugLogWriteMethod(DebugLevel.ERROR)
	public void writeLogE(String message, Throwable e) {
		sb.append("[E]");
		sb.append(message);
		sb.append(": ");
		sb.append(e.getMessage());
		sb.append(LS);
	}

	@DebugLogWriteMethod(DebugLevel.FATAL)
	public void writeLogF(String message) {
		sb.append("[F]");
		sb.append(message);
		sb.append(LS);
	}

	@DebugLogWriteMethod(DebugLevel.FATAL)
	public void writeLogF(String message, Throwable e) {
		sb.append("[F]");
		sb.append(message);
		sb.append(": ");
		sb.append(e.getMessage());
		sb.append(LS);
	}

	public String toString() {
		return sb.toString();
	}

	public void clear() {
		sb.setLength(0);
	}
}
