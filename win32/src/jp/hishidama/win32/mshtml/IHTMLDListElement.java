package jp.hishidama.win32.mshtml;

/**
 * IHTMLDListElement�N���X.
 * <p>
 * dl�^�O�������N���X�ł��B
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">�Ђ�����</a>
 * @since 2007.11.05
 */
public class IHTMLDListElement extends IHTMLListElement {

	protected IHTMLDListElement(long dll_ptr) {
		super(dll_ptr);
	}

	public void setCompact(boolean b) {
		Native.put_compact(getPtr(), b);
	}

	public boolean getCompact() {
		return Native.get_compact(getPtr());
	}

	private static class Native {
		//
		// IHTMLDListElement
		//
		private static native void put_compact(long ptr, boolean p);

		private static native boolean get_compact(long ptr);
	}
}
