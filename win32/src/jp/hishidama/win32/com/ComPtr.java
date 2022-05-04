package jp.hishidama.win32.com;

import java.util.Iterator;
import java.util.List;

/**
 * COMオブジェクト.
 * <p>
 * IUnkownに該当するクラス。<br>
 * DLL内のポインターを保持しているので、当クラスを継承したインスタンスでは、使い終わったら必ずdispose()を呼び出して解放（内部で参照カウンターを減らす）する必要がある。
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">ひしだま</a>
 * @since 2007.10.22
 * @version 2008.07.14
 */
public class ComPtr {
	/**
	 * DLL内で確保したポインター
	 */
	private long ptr;

	private boolean enable = true;

	static WeakList clist = new WeakList();

	// static int debug = 0;

	protected ComPtr(long dll_ptr) {
		ptr = dll_ptr;

		clist.add(this);

		// System.out.println("new[" + debug + "]" + ptr);
		// debug++;
	}

	protected long getPtr() {
		if (!enable) {
			throw new ComException("already disposed.");
		}
		return ptr;
	}

	/**
	 * インスタンス破棄.
	 * <p>
	 * 当該インスタンスを破棄する。<br>
	 * （DLL内の参照カウンターを減少させる為、必ず最後に当メソッドを呼ぶ必要がある）
	 * </p>
	 */
	public void dispose() {
		try {
			if (enable) {
				try {
					ComMgr.delete(ptr);
				} finally {
					// debug--;
					// System.out.println("dis[" + debug + "]" + ptr);

					ptr = 0;
				}
			}
		} finally {
			enable = false;
		}
	}

	protected void finalize() throws Throwable {
		dispose();
	}

	/**
	 * 子オブジェクト追加.
	 * <p>
	 * オブジェクトを“連動して破棄する対象”に加える。
	 * </p>
	 * 
	 * @param c
	 *            子オブジェクト
	 * @see #disposeChild()
	 * @deprecated 弱参照を使用することにより、当メソッドは不要になった
	 */
	protected void addChildComPtr(ComPtr c) {
	}

	/**
	 * 子オブジェクト破棄.
	 * <p>
	 * 当オブジェクトから取得したオブジェクトを全て破棄する。<br>
	 * 取得時に引数addChildにtrueを指定していたオブジェクトが対象。
	 * </p>
	 * <p>
	 * 例１：<br>
	 * <code>WebBrowser wb = WebBrowser.create();<br>
	 * HtmlDocument doc = wb.getDocument(<b>false</b>); //addChild=false<br>
	 * ComPtr.dispose(doc, false); //docも<br>
	 * ComPtr.dispose(wb, false);  //wbも別々に破棄する必要がある</code>
	 * </p>
	 * <p>
	 * 例２：<br>
	 * <code>WebBrowser wb = WebBrowser.create();<br>
	 * HtmlDocument doc = wb.getDocument(<b>true</b>); //addChild=true<br>
	 * ComPtr.dispose(wb, <b>true</b>); //これで、docも同時に破棄される</code><br>
	 * 内部で当メソッドが呼ばれている。
	 * </p>
	 * <p>
	 * 例３：<br>
	 * <code>IERobot robot = IERobot.create();<br>
	 * HtmlDocument doc = robot.getDocument();<br>
	 * HtmlElement elem = doc.getActiveElement(<b>true</b>); //addChild=true<br>
	 * robot.dispose(); //robotを破棄するとelemもdocも破棄される</code><br>
	 * 内部で当メソッドが呼ばれている。
	 * </p>
	 * 
	 * @see #addChildComPtr(ComPtr)
	 * @deprecated 弱参照を使用することにより、当メソッドは不要になった
	 */
	public void disposeChild() {
	}

	/**
	 * インスタンス破棄.
	 * 
	 * @param c
	 *            インスタンス（nullの場合、無処理）
	 * @see #dispose()
	 */
	public static void dispose(ComPtr c) {
		if (c != null) {
			c.dispose();
		}
	}

	/**
	 * @deprecated 弱参照を使用することにより、当メソッドは不要になった
	 * @see #dispose(ComPtr)
	 */
	public static void dispose(ComPtr c, boolean child) {
		dispose(c);
	}

	/**
	 * リスト内インスタンス破棄.
	 * <p>
	 * リスト内の全インスタンスを破棄する。
	 * </p>
	 * 
	 * @param list
	 *            ComPtrのリスト
	 * @see #dispose(List, boolean)
	 */
	public static void dispose(List list) {
		if (list == null || list.size() <= 0)
			return;
		for (Iterator i = list.iterator(); i.hasNext();) {
			ComPtr c = (ComPtr) i.next();
			dispose(c);
		}
	}

	/**
	 * @deprecated 弱参照を使用することにより、当メソッドは不要になった
	 * @see #dispose(List)
	 */
	public static void dispose(List list, boolean child) {
		dispose(list);
	}

	/**
	 * リスト内インスタンス破棄.
	 * <p>
	 * リスト内の（指定されたもの以外の）全インスタンスを破棄する。
	 * </p>
	 * 
	 * @param list
	 *            ComPtrのリスト
	 * @param exclude
	 *            破棄しないComPtr
	 * @see #dispose(ComPtr, boolean)
	 */
	public static void dispose(List list, ComPtr exclude) {
		if (list == null || list.size() <= 0)
			return;
		for (Iterator i = list.iterator(); i.hasNext();) {
			ComPtr c = (ComPtr) i.next();
			if (c != exclude) {
				dispose(c);
			}
		}
	}

	/**
	 * @deprecated 弱参照を使用することにより、当メソッドは不要になった
	 * @see #dispose(List, ComPtr)
	 */
	public static void dispose(List list, ComPtr exclude, boolean child) {
		dispose(list, exclude);
	}

}
