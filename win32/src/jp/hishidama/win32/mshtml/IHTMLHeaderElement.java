package jp.hishidama.win32.mshtml;

/**
 * IHTMLHeaderElement�N���X.
 * <p>
 * h1,h2,�c�^�O�������N���X�ł��B
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">�Ђ�����</a>
 * @since 2007.11.05
 */
public class IHTMLHeaderElement extends IHTMLBlockElement {

	protected IHTMLHeaderElement(long dll_ptr) {
		super(dll_ptr);
	}

	public void setAlign(String align) {
		Native.put_align(getPtr(), align);
	}

	public String getAlign() {
		return Native.get_align(getPtr());
	}

	private static class Native {
		//
		// IHTMLHeaderElement
		//
		private static native void put_align(long ptr, String p);

		private static native String get_align(long ptr);
	}
}
