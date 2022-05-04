package jp.hishidama.win32.com;

/**
 * HRESULT例外.
 * <p>
 * COM関連関数を実行した際に、関数の戻り値（HRESULT）がエラーの場合に当例外を発生させている。
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">ひしだま</a>
 * @since 2007.10.22
 * @version 2007.11.07
 */
public class HResultException extends ComException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 798106052860935312L;

	private int hr;

	/**
	 * コンストラクター.
	 * 
	 * @param hr
	 *            HRESULT
	 */
	public HResultException(int hr) {
		this.hr = hr;
	}

	/**
	 * HRESULT取得.
	 * 
	 * @return HRESULT
	 */
	public int getHResult() {
		return hr;
	}

	public String getMessage() {
		String code = Integer.toHexString(hr);
		code = ("00000000" + code).substring(code.length());

		String msg = getMessage(hr);
		return code + ": " + msg;
	}

	/**
	 * エラー名取得.
	 * 
	 * @param hr
	 *            HRESULT
	 * @return エラー名
	 */
	public static String getMessage(int hr) {
		switch (hr) {
		case 0:
			return "S_OK";
		case 1:
			return "S_FALSE";
		case 0x80004001:
			return "E_NOTIMPL";
		case 0x80004002:
			return "E_NOINTERFACE";
		case 0x80004003:
			return "E_POINTER";
		case 0x80004004:
			return "E_ABORT";
		case 0x80004005:
			return "E_FAIL";
		case 0x8000FFFF:
			return "E_UNEXPECTED";
		case 0x80010106:
			return "RPC_E_CHANGED_MODE";
		case 0x80010107:
			return "RPC_E_INVALIDMETHOD";
		case 0x80010108:
			return "RPC_E_DISCONNECTED";
		case 0x80020001:
			return "DISP_E_UNKNOWNINTERFACE";
		case 0x80020003:
			return "DISP_E_MEMBERNOTFOUND";
		case 0x80020004:
			return "DISP_E_PARAMNOTFOUND";
		case 0x80020005:
			return "DISP_E_TYPEMISMATCH";
		case 0x80020006:
			return "DISP_E_UNKNOWNNAME";
		case 0x80020007:
			return "DISP_E_NONAMEDARGS";
		case 0x80020008:
			return "DISP_E_BADVARTYPE";
		case 0x80020009:
			return "DISP_E_EXCEPTION";
		case 0x8002000a:
			return "DISP_E_OVERFLOW";
		case 0x8002000b:
			return "DISP_E_BADINDEX";
		case 0x8002000c:
			return "DISP_E_UNKNOWNLCID";
		case 0x8002000d:
			return "DISP_E_ARRAYISLOCKED";
		case 0x8002000e:
			return "DISP_E_BADPARAMCOUNT";
		case 0x8002000f:
			return "DISP_E_PARAMNOTOPTIONAL";
		case 0x80020010:
			return "DISP_E_BADCALLEE";
		case 0x800401F0:
			return "CO_E_NOTINITIALIZED";
		case 0x800401F1:
			return "CO_E_ALREADYINITIALIZED";
		case 0x80070005:
			return "E_ACCESSDENIED";
		case 0x80070057:
			return "E_INVALIDARG";
		}
		return "";
	}
}
