package jp.hishidama.win32.mshtml;

/**
 * IHTMLPhraseElement2ƒNƒ‰ƒX.
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">‚Ð‚µ‚¾‚Ü</a>
 * @since 2007.11.05
 */
public class IHTMLPhraseElement extends IHTMLElement {

	protected IHTMLPhraseElement(long dll_ptr) {
		super(dll_ptr);
	}

	public void setCite(String cite) {
		Native.put_cite(getPtr(), cite);
	}

	public String getCite() {
		return Native.get_cite(getPtr());
	}

	public void setDateTime(String datetime) {
		Native.put_dateTime(getPtr(), datetime);
	}

	public String getDateTime() {
		return Native.get_dateTime(getPtr());
	}

	private static class Native {
		//
		// IHTMLPhraseElement
		//

		//
		// IHTMLPhraseElement2
		//
		private static native void put_cite(long ptr, String p);

		private static native String get_cite(long ptr);

		private static native void put_dateTime(long ptr, String p);

		private static native String get_dateTime(long ptr);
	}
}
