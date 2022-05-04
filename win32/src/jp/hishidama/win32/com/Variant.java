package jp.hishidama.win32.com;

/**
 * VARIANTクラス.
 * <p>
 * VARIANT構造体を扱うクラスです。
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">ひしだま</a>
 * @since 2007.11.04
 * @version 2007.11.05
 */
public class Variant {
	/** VARENUM */
	public static final int VT_EMPTY = 0, VT_NULL = 1, VT_I2 = 2, VT_I4 = 3,
			VT_R4 = 4, VT_R8 = 5, VT_CY = 6, VT_DATE = 7, VT_BSTR = 8,
			VT_DISPATCH = 9, VT_ERROR = 10, VT_BOOL = 11, VT_VARIANT = 12,
			VT_UNKNOWN = 13, VT_DECIMAL = 14, VT_I1 = 16, VT_UI1 = 17,
			VT_UI2 = 18, VT_UI4 = 19, VT_I8 = 20, VT_UI8 = 21, VT_INT = 22,
			VT_UINT = 23, VT_VOID = 24, VT_HRESULT = 25, VT_PTR = 26,
			VT_SAFEARRAY = 27, VT_CARRAY = 28, VT_USERDEFINED = 29,
			VT_LPSTR = 30, VT_LPWSTR = 31, VT_RECORD = 36, VT_INT_PTR = 37,
			VT_UINT_PTR = 38, VT_FILETIME = 64, VT_BLOB = 65, VT_STREAM = 66,
			VT_STORAGE = 67, VT_STREAMED_OBJECT = 68, VT_STORED_OBJECT = 69,
			VT_BLOB_OBJECT = 70, VT_CF = 71, VT_CLSID = 72,
			VT_VERSIONED_STREAM = 73, VT_BSTR_BLOB = 0xfff, VT_VECTOR = 0x1000,
			VT_ARRAY = 0x2000, VT_BYREF = 0x4000, VT_RESERVED = 0x8000,
			VT_ILLEGAL = 0xffff, VT_ILLEGALMASKED = 0xfff, VT_TYPEMASK = 0xfff;

	protected int vt = VT_EMPTY;

	public int getVt() {
		return vt;
	}

	public Variant() {
	}

	protected long num;

	public Variant(int val) {
		setInt(val);
	}

	public Variant(long val) {
		setLong(val);
	}

	public void setInt(int val) {
		vt = VT_I4;
		this.num = val;
	}

	public int getInt() {
		return (int) num;
	}

	public void setLong(long val) {
		vt = VT_I8;
		this.num = val;
	}

	public long getLong() {
		return num;
	}

	public Variant(boolean b) {
		setBool(b);
	}

	public void setBool(boolean b) {
		vt = VT_BOOL;
		num = b ? 1 : 0;
	}

	public boolean getBool() {
		return num != 0;
	}

	protected String str;

	public Variant(String str) {
		setStr(str);
	}

	public void setStr(String str) {
		vt = VT_BSTR;
		this.str = str;
	}

	public String getStr() {
		return str;
	}

	protected ComPtr com;

	public Variant(ComPtr com) {
		setCom(com);
	}

	public void setCom(ComPtr com) {
		vt = VT_UNKNOWN;
		this.com = com;
	}

	public ComPtr getCom() {
		return com;
	}

	public String toString() {
		switch (vt) {
		case VT_EMPTY:
			return "";
		case VT_NULL:
			return "null";
		case VT_I1:
		case VT_I2:
		case VT_I4:
		case VT_I8:
			return Long.toString(num);
		case VT_BOOL:
			return Boolean.toString(num != 0);
		case VT_BSTR:
			return String.valueOf(str);
		case VT_UNKNOWN:
		case VT_DISPATCH:
			return String.valueOf(com);
		default:
			return super.toString();
		}
	}
}
