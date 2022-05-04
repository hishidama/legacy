package jp.hishidama.win32.com;

import java.util.Iterator;
import java.util.List;

/**
 * COM�I�u�W�F�N�g.
 * <p>
 * IUnkown�ɊY������N���X�B<br>
 * DLL���̃|�C���^�[��ێ����Ă���̂ŁA���N���X���p�������C���X�^���X�ł́A�g���I�������K��dispose()���Ăяo���ĉ���i�����ŎQ�ƃJ�E���^�[�����炷�j����K�v������B
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">�Ђ�����</a>
 * @since 2007.10.22
 * @version 2008.07.14
 */
public class ComPtr {
	/**
	 * DLL���Ŋm�ۂ����|�C���^�[
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
	 * �C���X�^���X�j��.
	 * <p>
	 * ���Y�C���X�^���X��j������B<br>
	 * �iDLL���̎Q�ƃJ�E���^�[������������ׁA�K���Ō�ɓ����\�b�h���ĂԕK�v������j
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
	 * �q�I�u�W�F�N�g�ǉ�.
	 * <p>
	 * �I�u�W�F�N�g���g�A�����Ĕj������Ώہh�ɉ�����B
	 * </p>
	 * 
	 * @param c
	 *            �q�I�u�W�F�N�g
	 * @see #disposeChild()
	 * @deprecated ��Q�Ƃ��g�p���邱�Ƃɂ��A�����\�b�h�͕s�v�ɂȂ���
	 */
	protected void addChildComPtr(ComPtr c) {
	}

	/**
	 * �q�I�u�W�F�N�g�j��.
	 * <p>
	 * ���I�u�W�F�N�g����擾�����I�u�W�F�N�g��S�Ĕj������B<br>
	 * �擾���Ɉ���addChild��true���w�肵�Ă����I�u�W�F�N�g���ΏہB
	 * </p>
	 * <p>
	 * ��P�F<br>
	 * <code>WebBrowser wb = WebBrowser.create();<br>
	 * HtmlDocument doc = wb.getDocument(<b>false</b>); //addChild=false<br>
	 * ComPtr.dispose(doc, false); //doc��<br>
	 * ComPtr.dispose(wb, false);  //wb���ʁX�ɔj������K�v������</code>
	 * </p>
	 * <p>
	 * ��Q�F<br>
	 * <code>WebBrowser wb = WebBrowser.create();<br>
	 * HtmlDocument doc = wb.getDocument(<b>true</b>); //addChild=true<br>
	 * ComPtr.dispose(wb, <b>true</b>); //����ŁAdoc�������ɔj�������</code><br>
	 * �����œ����\�b�h���Ă΂�Ă���B
	 * </p>
	 * <p>
	 * ��R�F<br>
	 * <code>IERobot robot = IERobot.create();<br>
	 * HtmlDocument doc = robot.getDocument();<br>
	 * HtmlElement elem = doc.getActiveElement(<b>true</b>); //addChild=true<br>
	 * robot.dispose(); //robot��j�������elem��doc���j�������</code><br>
	 * �����œ����\�b�h���Ă΂�Ă���B
	 * </p>
	 * 
	 * @see #addChildComPtr(ComPtr)
	 * @deprecated ��Q�Ƃ��g�p���邱�Ƃɂ��A�����\�b�h�͕s�v�ɂȂ���
	 */
	public void disposeChild() {
	}

	/**
	 * �C���X�^���X�j��.
	 * 
	 * @param c
	 *            �C���X�^���X�inull�̏ꍇ�A�������j
	 * @see #dispose()
	 */
	public static void dispose(ComPtr c) {
		if (c != null) {
			c.dispose();
		}
	}

	/**
	 * @deprecated ��Q�Ƃ��g�p���邱�Ƃɂ��A�����\�b�h�͕s�v�ɂȂ���
	 * @see #dispose(ComPtr)
	 */
	public static void dispose(ComPtr c, boolean child) {
		dispose(c);
	}

	/**
	 * ���X�g���C���X�^���X�j��.
	 * <p>
	 * ���X�g���̑S�C���X�^���X��j������B
	 * </p>
	 * 
	 * @param list
	 *            ComPtr�̃��X�g
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
	 * @deprecated ��Q�Ƃ��g�p���邱�Ƃɂ��A�����\�b�h�͕s�v�ɂȂ���
	 * @see #dispose(List)
	 */
	public static void dispose(List list, boolean child) {
		dispose(list);
	}

	/**
	 * ���X�g���C���X�^���X�j��.
	 * <p>
	 * ���X�g���́i�w�肳�ꂽ���̈ȊO�́j�S�C���X�^���X��j������B
	 * </p>
	 * 
	 * @param list
	 *            ComPtr�̃��X�g
	 * @param exclude
	 *            �j�����Ȃ�ComPtr
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
	 * @deprecated ��Q�Ƃ��g�p���邱�Ƃɂ��A�����\�b�h�͕s�v�ɂȂ���
	 * @see #dispose(List, ComPtr)
	 */
	public static void dispose(List list, ComPtr exclude, boolean child) {
		dispose(list, exclude);
	}

}
