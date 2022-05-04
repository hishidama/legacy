package jp.hishidama.win32.mshtml;

/**
 * IHTMLCommentElement,IHTMLCommentElement2ƒNƒ‰ƒX.
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">‚Ð‚µ‚¾‚Ü</a>
 * @since 2007.11.05
 */
public class IHTMLCommentElement extends IHTMLElement {

	protected IHTMLCommentElement(long dll_ptr) {
		super(dll_ptr);
	}

	public void setText(String text) {
		Native.put_text(getPtr(), text);
	}

	public String getText() {
		return Native.get_text(getPtr());
	}

	public void setAtomic(int atomic) {
		Native.put_atomic(getPtr(), atomic);
	}

	public int getAtomic() {
		return Native.get_atomic(getPtr());
	}

	public void setData(String data) {
		Native.put_data(getPtr(), data);
	}

	public String getData() {
		return Native.get_data(getPtr());
	}

	public int getLength() {
		return Native.get_length(getPtr());
	}

	public String substringData(int offset, int Count) {
		return Native.substringData(getPtr(), offset, Count);
	}

	public void appendData(String string) {
		Native.appendData(getPtr(), string);
	}

	public void insertData(int offset, String string) {
		Native.insertData(getPtr(), offset, string);
	}

	public void deleteData(int offset, int Count) {
		Native.deleteData(getPtr(), offset, Count);
	}

	public void replaceData(int offset, int Count, String string) {
		Native.replaceData(getPtr(), offset, Count, string);
	}

	private static class Native {
		//
		// IHTMLCommentElement
		//
		private static native void put_text(long ptr, String p);

		private static native String get_text(long ptr);

		private static native void put_atomic(long ptr, int p);

		private static native int get_atomic(long ptr);

		//
		// IHTMLCommentElement2
		//
		private static native void put_data(long ptr, String p);

		private static native String get_data(long ptr);

		private static native int get_length(long ptr);

		private static native String substringData(long ptr, int offset,
				int Count);

		private static native void appendData(long ptr, String bstrstring);

		private static native void insertData(long ptr, int offset,
				String bstrstring);

		private static native void deleteData(long ptr, int offset, int Count);

		private static native void replaceData(long ptr, int offset, int Count,
				String bstrstring);
	}
}
