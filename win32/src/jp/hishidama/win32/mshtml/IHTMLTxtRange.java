package jp.hishidama.win32.mshtml;

import jp.hishidama.win32.com.ComPtr;
import jp.hishidama.win32.com.IDispatch;
import jp.hishidama.win32.com.Variant;

/**
 * IHTMLTxtRangeクラス.
 * <p>
 * テキスト入力エリアのテキストを操作するクラスです。
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">ひしだま</a>
 * @since 2007.10.28
 * @version 2008.07.14
 */
public class IHTMLTxtRange extends IDispatch {

	protected IHTMLTxtRange(long dll_ptr) {
		super(dll_ptr);
	}

	public String getHtmlText() {
		return Native.get_htmlText(getPtr());
	}

	public void setText(String text) {
		Native.put_text(getPtr(), text);
	}

	public String getText() {
		return Native.get_text(getPtr());
	}

	public IHTMLElement parentElement() {
		return Native.parentElement(getPtr());
	}

	/**
	 * @see ComPtr#disposeChild()
	 * @deprecated
	 * @see #parentElement()
	 */
	public IHTMLElement parentElement(boolean addChild) {
		return parentElement();
	}

	public IHTMLTxtRange duplicate() {
		return Native.duplicate(getPtr());
	}

	/**
	 * @see ComPtr#disposeChild()
	 * @deprecated
	 * @see #duplicate()
	 */
	public IHTMLTxtRange duplicate(boolean addChild) {
		return duplicate();
	}

	public boolean inRange(IHTMLTxtRange range) {
		return Native.inRange(getPtr(), range.getPtr());
	}

	public boolean isEqual(IHTMLTxtRange range) {
		return Native.isEqual(getPtr(), range.getPtr());
	}

	public void scrollIntoView(boolean fStart) {
		Native.scrollIntoView(getPtr(), fStart);
	}

	public void collapse(boolean start) {
		Native.collapse(getPtr(), start);
	}

	public boolean expand(String unit) {
		return Native.expand(getPtr(), unit);
	}

	public int move(String unit, int count) {
		return Native.move(getPtr(), unit, count);
	}

	public int moveStart(String unit, int count) {
		return Native.moveStart(getPtr(), unit, count);
	}

	public int moveEnd(String unit, int count) {
		return Native.moveEnd(getPtr(), unit, count);
	}

	public void select() {
		Native.select(getPtr());
	}

	public void pasteHTML(String html) {
		Native.pasteHTML(getPtr(), html);
	}

	public void moveToElementText(IHTMLElement element) {
		Native.moveToElementText(getPtr(), element.getPtr());
	}

	public void setEndPoint(String how, IHTMLTxtRange sourceRange) {
		Native.setEndPoint(getPtr(), how, sourceRange.getPtr());
	}

	public int compareEndPoints(String how, IHTMLTxtRange sourceRange) {
		return Native.compareEndPoints(getPtr(), how, sourceRange.getPtr());
	}

	public boolean findText(String string, int count, int flags) {
		return Native.findText(getPtr(), string, count, flags);
	}

	public void moveToPoint(int x, int y) {
		Native.moveToPoint(getPtr(), x, y);
	}

	public String getBookmark() {
		return Native.getBookmark(getPtr());
	}

	public boolean moveToBookmark(String bookmark) {
		return Native.moveToBookmark(getPtr(), bookmark);
	}

	public boolean queryCommandSupported(String cmdID) {
		return Native.queryCommandSupported(getPtr(), cmdID);
	}

	public boolean queryCommandEnabled(String cmdID) {
		return Native.queryCommandEnabled(getPtr(), cmdID);
	}

	public boolean queryCommandState(String cmdID) {
		return Native.queryCommandState(getPtr(), cmdID);
	}

	public boolean queryCommandIndeterm(String cmdID) {
		return Native.queryCommandIndeterm(getPtr(), cmdID);
	}

	public String queryCommandText(String cmdID) {
		return Native.queryCommandText(getPtr(), cmdID);
	}

	public String queryCommandValue(String cmdID) {
		return Native.queryCommandValue(getPtr(), cmdID);
	}

	public boolean execCommand(String cmdID, boolean showUI, Variant value) {
		return Native.execCommand(getPtr(), cmdID, showUI, value);
	}

	public boolean execCommandShowHelp(String cmdID) {
		return Native.execCommandShowHelp(getPtr(), cmdID);
	}

	private static class Native {
		//
		// IHTMLTxtRange
		//
		private static native String get_htmlText(long ptr);

		private static native void put_text(long ptr, String p);

		private static native String get_text(long ptr);

		private static native IHTMLElement parentElement(long ptr);

		private static native IHTMLTxtRange duplicate(long ptr);

		private static native boolean inRange(long ptr, long range);

		private static native boolean isEqual(long ptr, long range);

		private static native void scrollIntoView(long ptr, boolean fStart);

		private static native void collapse(long ptr, boolean Start);

		private static native boolean expand(long ptr, String Unit);

		private static native int move(long ptr, String Unit, int Count);

		private static native int moveStart(long ptr, String Unit, int Count);

		private static native int moveEnd(long ptr, String Unit, int Count);

		private static native void select(long ptr);

		private static native void pasteHTML(long ptr, String html);

		private static native void moveToElementText(long ptr, long element);

		private static native void setEndPoint(long ptr, String how,
				long SourceRange);

		private static native int compareEndPoints(long ptr, String how,
				long SourceRange);

		private static native boolean findText(long ptr, String String,
				int Count, int Flags);

		private static native void moveToPoint(long ptr, int x, int y);

		private static native String getBookmark(long ptr);

		private static native boolean moveToBookmark(long ptr, String Bookmark);

		private static native boolean queryCommandSupported(long ptr,
				String cmdID);

		private static native boolean queryCommandEnabled(long ptr, String cmdID);

		private static native boolean queryCommandState(long ptr, String cmdID);

		private static native boolean queryCommandIndeterm(long ptr,
				String cmdID);

		private static native String queryCommandText(long ptr, String cmdID);

		private static native String queryCommandValue(long ptr, String cmdID);

		private static native boolean execCommand(long ptr, String cmdID,
				boolean showUI, Variant value);

		private static native boolean execCommandShowHelp(long ptr, String cmdID);
	}
}
