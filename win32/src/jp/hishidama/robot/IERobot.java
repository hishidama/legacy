package jp.hishidama.robot;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.*;

import jp.hishidama.robot.ie.FileDownloadDialogFactory;
import jp.hishidama.robot.ie.FileDownloadProgressDialog;
import jp.hishidama.robot.ie.IHTMLInputRadioUtil;
import jp.hishidama.robot.ie.IHTMLInputValueUtil;
import jp.hishidama.robot.ie.IHTMLSelectUtil;
import jp.hishidama.win32.JWnd;
import jp.hishidama.win32.api.OcIdlConst;
import jp.hishidama.win32.api.WinUser;
import jp.hishidama.win32.api.WinUserConst;
import jp.hishidama.win32.com.ComMgr;
import jp.hishidama.win32.com.ComPtr;
import jp.hishidama.win32.mshtml.*;
import jp.hishidama.win32.shdocvw.IWebBrowser;

/**
 * InternetExplorer�������s�N���X.
 * <p>
 * IE�𑀍삷��COM(ActiveX)��SHDocVw��MSHTML���������{�b�g�N���X�B<br> ��<a target="hishidama"
 * href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">�g�p��</a>
 * </p>
 * <p>
 * �g�p����ɂ͈ȉ��̐ݒ肪�K�v�B<br>
 * <ul>
 * <li><a target="hishidama"
 * href="http://www.ne.jp/asahi/hishidama/home/soft/java/hmwin32.html">hmwin32.dll</a>��<a
 * target="hishidama"
 * href="http://www.ne.jp/asahi/hishidama/home/tech/eclipse/java.html#���s���p�X��ǉ�������@">���s���̃p�X�ɒǉ�</a>����B</li>
 * </ul>
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">�Ђ�����</a>
 * @since 2007.10.22
 * @version 2007.11.01
 */
public class IERobot implements OcIdlConst {

	/**
	 * �w�莞�ԑҋ@.
	 * 
	 * @param time
	 *            �E�F�C�g[�~���b]
	 */
	public void delay(int time) {
		if (time <= 0)
			return;
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
		}
	}

	//
	// �������֘A
	//

	/**
	 * �V�K�E�B���h�E�쐬.
	 * <p>
	 * �V����IE���쐬����B
	 * </p>
	 * <p>
	 * �����\�b�h�̎g�p�O��{@link ComMgr#initialize()}����x�Ă΂�Ă��Ȃ���΂Ȃ�Ȃ��B<br>
	 * ���C���X�^���X�̎g�p�I����́A�K��{@link #dispose()}���Ăяo���ăC���X�^���X��j�����邱�ƁB
	 * </p>
	 * 
	 * @return IERobot�i�쐬�Ɏ��s�����ꍇ�Anull�j
	 * @see #setBounds(int, int, int, int)
	 * @see #setVisible(boolean)
	 * @see #setForeground()
	 * @see #dispose()
	 */
	public static IERobot create() {
		IWebBrowser wb = IWebBrowser.create();
		if (wb == null)
			return null;
		return new IERobot(wb);
	}

	/**
	 * IERobot�쐬.
	 * <p>
	 * �w�肳�ꂽWebBrowser�𑀍삷��IERobot���쐬����B
	 * </p>
	 * <p>
	 * �����\�b�h�̎g�p�O��{@link ComMgr#initialize()}����x�Ă΂�Ă��Ȃ���΂Ȃ�Ȃ��B<br>
	 * ���C���X�^���X�̎g�p�I�����{@link #dispose()}���Ăяo���ƁA�w�肳�ꂽWebBrowser���j�������B
	 * </p>
	 * 
	 * @param wb
	 *            WebBrowser�R���g���[��
	 * @return IERobot�iwb��null�̏ꍇ�Anull�j
	 * @see #detach()
	 */
	public static IERobot create(IWebBrowser wb) {
		if (wb == null)
			return null;
		return new IERobot(wb);
	}

	/**
	 * IERobot�쐬.
	 * <p>
	 * �w�肳�ꂽ�E�B���h�E�𑀍삷��IERobot���쐬����B
	 * </p>
	 * <p>
	 * �����\�b�h�̎g�p�O��{@link ComMgr#initialize()}����x�Ă΂�Ă��Ȃ���΂Ȃ�Ȃ��B<br>
	 * ���C���X�^���X�̎g�p�I����́A�K��{@link #dispose()}���Ăяo���ăC���X�^���X��j�����邱�ƁB
	 * </p>
	 * 
	 * @param wnd
	 *            �E�B���h�E
	 * @return IERobot�i�E�B���h�E��WebBrowser�R���g���[���łȂ��ꍇ�Anull�j
	 * @see #dispose()
	 */
	public static IERobot create(JWnd wnd) {
		IWebBrowser wb = IWebBrowser.findWebBrowser(wnd.GetSafeHwnd());
		if (wb == null)
			return null;
		return new IERobot(wb);
	}

	/**
	 * IE�T��.
	 * <p>
	 * �^�C�g������v����E�B���h�E�iWebBrowser�j��IERobot���쐬����B
	 * </p>
	 * <p>
	 * �����\�b�h�̎g�p�O��{@link ComMgr#initialize()}����x�Ă΂�Ă��Ȃ���΂Ȃ�Ȃ��B<br>
	 * ���C���X�^���X�̎g�p�I����́A�K��{@link #dispose()}���Ăяo���ăC���X�^���X��j�����邱�ƁB
	 * </p>
	 * 
	 * @param title
	 *            �^�C�g��
	 * @return IERobot�i������Ȃ������ꍇ�Anull�j
	 * @see #dispose()
	 */
	public static IERobot findIE(String title) {
		List list = enumIEWebBrowser();
		IWebBrowser wb = null;
		for (int i = 0; i < list.size(); i++) {
			IHTMLDocument doc = null;
			try {
				IWebBrowser w = (IWebBrowser) list.get(i);
				doc = w.getDocument();
				// System.out.println(i + ":" + doc.getTitle());
				if (title.equals(doc.getTitle())) {
					wb = w;
					break;
				}
			} catch (RuntimeException e) {
				// e.printStackTrace();
			} finally {
				ComPtr.dispose(doc);
			}
		}
		if (wb == null) {
			ComPtr.dispose(list);
			return null;
		} else {
			ComPtr.dispose(list, wb);
			return new IERobot(wb);
		}
	}

	/**
	 * WebBrowser��.
	 * <p>
	 * ���݂���WebBrowser�R���g���[���̃I�u�W�F�N�g��S�ė񋓂���B<br>
	 * IE��Explorer�����܂܂��B
	 * <p>
	 * <p>
	 * �����\�b�h�̎g�p�O��{@link ComMgr#initialize()}����x�Ă΂�Ă��Ȃ���΂Ȃ�Ȃ��B<br>
	 * ���X�g���̊e�C���X�^���X�̎g�p�I����́A�K���X��{@link #dispose()}���Ăяo���ăC���X�^���X��j�����邱�ƁB
	 * </p>
	 * 
	 * @return {@link IWebBrowser}�̃��X�g�i�K��null�ȊO�j
	 * @see ComPtr#dispose(List, boolean)
	 * @see ComPtr#dispose(List, ComPtr, boolean)
	 */
	public static List enumWebBrowser() {
		return IWebBrowser.enumWebBrowser();
	}

	/**
	 * WebBrowser(IE)��.
	 * <p>
	 * ���݂���IE�̃I�u�W�F�N�g��S�ė񋓂���B<br>
	 * �����ɂ�HtmlDocument�����E�B���h�E�Ȃ̂ŁAIE�ȊO�ł��������I�u�W�F�N�g������΂�����܂܂��B
	 * </p>
	 * <p>
	 * �����\�b�h�̎g�p�O��{@link ComMgr#initialize()}����x�Ă΂�Ă��Ȃ���΂Ȃ�Ȃ��B<br>
	 * ���X�g���̊e�C���X�^���X�̎g�p�I����́A�K���X��{@link #dispose()}���Ăяo���ăC���X�^���X��j�����邱�ƁB
	 * </p>
	 * 
	 * @return {@link IWebBrowser}�̃��X�g�i�K��null�ȊO�j
	 * @see ComPtr#dispose(List, boolean)
	 * @see ComPtr#dispose(List, ComPtr, boolean)
	 */
	public static List enumIEWebBrowser() {
		List wbs = IWebBrowser.enumWebBrowser();
		if (wbs.size() <= 0) {
			return new ArrayList(0);
		}

		List ret = new ArrayList(wbs.size());
		for (Iterator i = wbs.iterator(); i.hasNext();) {
			IWebBrowser wb = (IWebBrowser) i.next();
			boolean use = false;
			IHTMLDocument doc = null;
			try {
				doc = wb.getDocument();
				long hwnd = wb.getHWND();
				if (doc != null && hwnd != 0) {
					ret.add(wb);
					use = true;
				}
			} catch (RuntimeException e) {
				// e.printStackTrace();
			} finally {
				if (doc != null)
					doc.dispose();
				if (!use)
					wb.dispose();
			}
		}
		return ret;
	}

	//
	// �C���X�^���X�֘A
	//

	protected IWebBrowser wb;

	protected boolean debug = false;

	protected boolean rethrow = false;

	protected Exception ex = null;

	protected IERobot(IWebBrowser wb) {
		this.wb = wb;
	}

	/**
	 * WebBrowser�R���g���[���擾.
	 * <p>
	 * �����Ŏ擾����WebBrowser��{@link IWebBrowser#dispose()}�Ŕj�����Ȃ����ƁB
	 * </p>
	 * 
	 * @return WebBrowser�R���g���[��
	 */
	public IWebBrowser getWebBrowser() {
		return wb;
	}

	/**
	 * �f�o�b�O���[�h�ݒ�.
	 * 
	 * @param b
	 *            true�̏ꍇ�A��O�������ɃX�^�b�N�g���[�X��W���G���[�ɏo�͂���B
	 */
	public void setDebugMode(boolean b) {
		debug = b;
	}

	/**
	 * ��O�X���[�ݒ�.
	 * 
	 * @param b
	 *            true�̏ꍇ�A�e���\�b�h�Ŕ���������O�����̂܂܃X���[����B
	 */
	public void setRethrow(boolean b) {
		rethrow = b;
	}

	/**
	 * ������O�擾.
	 * 
	 * @return �Ō�ɌĂяo�������\�b�h�Ŕ���������O�i�����ꍇ�Anull�j
	 */
	public Exception getException() {
		return ex;
	}

	protected void doFail(RuntimeException e) {
		ex = e;
		if (debug)
			e.printStackTrace();
		if (rethrow)
			throw e;
	}

	// �ꎞ�ۑ�

	private IHTMLDocument tempDoc = null;

	private List formList = null;

	private Map idMap = null;

	private Map nameMap = null;

	private Map tagMap = null;

	private List linkList = null;

	protected void disposeTempElems() {
		ComPtr.dispose(formList);
		formList = null;

		if (idMap != null) {
			for (Iterator i = idMap.keySet().iterator(); i.hasNext();) {
				Object key = i.next();
				ComPtr c = (ComPtr) idMap.get(key);
				ComPtr.dispose(c);
			}
		}
		idMap = null;

		if (nameMap != null) {
			for (Iterator i = nameMap.keySet().iterator(); i.hasNext();) {
				Object key = i.next();
				List list = (List) nameMap.get(key);
				ComPtr.dispose(list);
			}
		}
		nameMap = null;

		if (tagMap != null) {
			for (Iterator i = tagMap.keySet().iterator(); i.hasNext();) {
				Object key = i.next();
				List list = (List) tagMap.get(key);
				ComPtr.dispose(list);
			}
		}
		tagMap = null;

		ComPtr.dispose(linkList);
		linkList = null;
	}

	protected void disposeTemp() {
		ComPtr.dispose(tempDoc);
		tempDoc = null;

		disposeTempElems();
	}

	/**
	 * IERobot�j��.
	 * <p>
	 * ���Y�C���X�^���X��j������B<br>
	 * �����ŕێ����Ă���WebBrowser�R���g���[�����j�������B
	 * </p>
	 */
	public void dispose() {
		ex = null;
		disposeTemp();

		if (wb != null) {
			try {
				ComPtr.dispose(wb);
			} catch (RuntimeException e) {
				doFail(e);
			} finally {
				wb = null;
			}
		}
	}

	/**
	 * IERobot�j��.
	 * <p>
	 * ���Y�C���X�^���X��j������B<br>
	 * �����ŕێ����Ă���WebBrowser�R���g���[���͔j�����Ȃ��B
	 * </p>
	 * 
	 * @return �ێ����Ă���WebBrowser�R���g���[��
	 */
	public IWebBrowser detach() {
		ex = null;
		disposeTemp();

		IWebBrowser ret = wb;
		wb = null;
		return ret;
	}

	//
	// �i�r�Q�[�g�֘A
	//

	/**
	 * �w��ʒu�ֈړ�����.
	 * <p>
	 * �����\�b�h�����s����ƁA���N���X���璼�ڎ擾����COM�I�u�W�F�N�g��{@link ComPtr#dispose()}�����B
	 * </p>
	 * 
	 * @param url
	 *            URL
	 * @return ���������ꍇ�Atrue<br>
	 *         ���s�����ꍇ�Afalse�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 */
	public boolean navigate(String url) {
		ex = null;
		disposeTemp();
		try {
			wb.navigate(url);
			return true;
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * �w��ʒu�ֈړ�����.
	 * <p>
	 * �����\�b�h�����s����ƁA���N���X���璼�ڎ擾����COM�I�u�W�F�N�g��{@link ComPtr#dispose()}�����B
	 * </p>
	 * 
	 * @param url
	 *            URL
	 * @param flags
	 *            �t���O
	 * @param targetFrameName
	 *            �^�[�Q�b�g�̃t���[����
	 * @return ���������ꍇ�Atrue<br>
	 *         ���s�����ꍇ�Afalse�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 */
	public boolean navigate(String url, String flags, String targetFrameName) {
		ex = null;
		disposeTemp();
		try {
			wb.navigate(url, flags, targetFrameName);
			return true;
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * �w��ʒu�ֈړ�����.
	 * <p>
	 * �����\�b�h�����s����ƁA���N���X���璼�ڎ擾����COM�I�u�W�F�N�g��{@link ComPtr#dispose()}�����B
	 * </p>
	 * 
	 * @param a
	 *            �A���J�[�v�f
	 * @return ���������ꍇ�Atrue<br>
	 *         ���s�����ꍇ�Afalse�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 */
	public boolean navigate(IHTMLAnchorElement a) {
		ex = null;
		String url = null;
		String tar = null;
		try {
			url = a.getHref();
			tar = a.getTarget();
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
		if (tar == null) {
			return navigate(url);
		} else {
			return navigate(url, null, tar);
		}
	}

	/**
	 * �����̑O�y�[�W�֖߂�.
	 * <p>
	 * �����\�b�h�����s����ƁA���N���X���璼�ڎ擾����COM�I�u�W�F�N�g��{@link ComPtr#dispose()}�����B
	 * </p>
	 * 
	 * @return ���������ꍇ�Atrue<br>
	 *         ���s�����ꍇ�Afalse�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 */
	public boolean goBack() {
		ex = null;
		disposeTemp();
		try {
			wb.goBack();
			return true;
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * �����̎��y�[�W�֍s��.
	 * <p>
	 * �����\�b�h�����s����ƁA���N���X���璼�ڎ擾����COM�I�u�W�F�N�g��{@link ComPtr#dispose()}�����B
	 * </p>
	 * 
	 * @return ���������ꍇ�Atrue<br>
	 *         ���s�����ꍇ�Afalse�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 */
	public boolean goForward() {
		ex = null;
		disposeTemp();
		try {
			wb.goForward();
			return true;
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * �z�[���ֈړ�����.
	 * <p>
	 * �����\�b�h�����s����ƁA���N���X���璼�ڎ擾����COM�I�u�W�F�N�g��{@link ComPtr#dispose()}�����B
	 * </p>
	 * 
	 * @return ���������ꍇ�Atrue<br>
	 *         ���s�����ꍇ�Afalse�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 */
	public boolean goHome() {
		ex = null;
		disposeTemp();
		try {
			wb.goHome();
			return true;
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * �����y�[�W�ֈړ�����.
	 * <p>
	 * �����\�b�h�����s����ƁA���N���X���璼�ڎ擾����COM�I�u�W�F�N�g��{@link ComPtr#dispose()}�����B
	 * </p>
	 * 
	 * @return ���������ꍇ�Atrue<br>
	 *         ���s�����ꍇ�Afalse�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 */
	public boolean goSearch() {
		ex = null;
		disposeTemp();
		try {
			wb.goSearch();
			return true;
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * �X�V����.
	 * <p>
	 * �����\�b�h�����s����ƁA���N���X���璼�ڎ擾����COM�I�u�W�F�N�g��{@link ComPtr#dispose()}�����B
	 * </p>
	 * 
	 * @return ���������ꍇ�Atrue<br>
	 *         ���s�����ꍇ�Afalse�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 */
	public boolean refresh() {
		ex = null;
		disposeTemp();
		try {
			wb.refresh();
			return true;
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}

	}

	/**
	 * ��~����.
	 * <p>
	 * �����\�b�h�����s����ƁA���N���X���璼�ڎ擾����COM�I�u�W�F�N�g��{@link ComPtr#dispose()}�����B
	 * </p>
	 * 
	 * @return ���������ꍇ�Atrue<br>
	 *         ���s�����ꍇ�Afalse�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 */
	public boolean stop() {
		ex = null;
		disposeTemp();
		try {
			wb.stop();
			return true;
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}

	}

	/**
	 * HTML������������.
	 * <p>
	 * �ێ����Ă���HTML�h�L�������g���N���A����B
	 * </p>
	 * <p>
	 * �����\�b�h�����s����ƁA���N���X���璼�ڎ擾����COM�I�u�W�F�N�g��{@link ComPtr#dispose()}�����B
	 * </p>
	 * 
	 * @return ���������ꍇ�Atrue<br>
	 *         ���s�����ꍇ�Afalse�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 */
	public boolean clearHtml() {
		IHTMLDocument doc = getDocument();
		if (doc != null) {
			try {
				doc.clear();
				disposeTempElems();
				return true;
			} catch (RuntimeException e) {
				doFail(e);
			}
		}
		return false;
	}

	/**
	 * HTML����������.
	 * <p>
	 * �ێ����Ă���HTML�h�L�������g��HTML���o�͂���B�i���s�Ȃ��j
	 * </p>
	 * <p>
	 * �����\�b�h�����s����ƁA���N���X���璼�ڎ擾����COM�I�u�W�F�N�g��{@link ComPtr#dispose()}�����B
	 * </p>
	 * 
	 * @param html
	 *            HTML
	 * @return ���������ꍇ�Atrue<br>
	 *         ���s�����ꍇ�Afalse�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 * @see #closeHtml()
	 */
	public boolean writeHtml(String html) {
		IHTMLDocument doc = getDocument();
		if (doc != null) {
			try {
				doc.write(html);
				disposeTempElems();
				return true;
			} catch (RuntimeException e) {
				doFail(e);
			}
		}
		return false;
	}

	/**
	 * HTML����������.
	 * <p>
	 * �ێ����Ă���HTML�h�L�������g��HTML���o�͂���B�i���s����j
	 * </p>
	 * <p>
	 * �����\�b�h�����s����ƁA���N���X���璼�ڎ擾����COM�I�u�W�F�N�g��{@link ComPtr#dispose()}�����B
	 * </p>
	 * 
	 * @param html
	 *            HTML
	 * @return ���������ꍇ�Atrue<br>
	 *         ���s�����ꍇ�Afalse�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 * @see #closeHtml()
	 */
	public boolean writelnHtml(String html) {
		IHTMLDocument doc = getDocument();
		if (doc != null) {
			try {
				doc.writeln(html);
				disposeTempElems();
				return true;
			} catch (RuntimeException e) {
				doFail(e);
			}
		}
		return false;
	}

	/**
	 * HTML���N���[�Y����.
	 * <p>
	 * �ێ����Ă���HTML�h�L�������g�ւ�HTML�o�͂��I������B
	 * </p>
	 * <p>
	 * �����\�b�h�����s����ƁA���N���X���璼�ڎ擾����COM�I�u�W�F�N�g��{@link ComPtr#dispose()}�����B
	 * </p>
	 * 
	 * @return ���������ꍇ�Atrue<br>
	 *         ���s�����ꍇ�Afalse�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 */
	public boolean closeHtml() {
		IHTMLDocument doc = getDocument();
		if (doc != null) {
			try {
				doc.close();
				disposeTempElems();
				return true;
			} catch (RuntimeException e) {
				doFail(e);
			}
		}
		return false;
	}

	/**
	 * �I������.
	 * <p>
	 * �����\�b�h�����s����ƁA���N���X���璼�ڎ擾����COM�I�u�W�F�N�g��{@link ComPtr#dispose()}�����B
	 * </p>
	 * 
	 * @return ���������ꍇ�Atrue<br>
	 *         ���s�����ꍇ�Afalse�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 */
	public boolean quit() {
		ex = null;
		disposeTemp();
		try {
			wb.quit();
			return true;
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * �r�W�[���ǂ�����Ԃ�.
	 * 
	 * @return �r�W�[�̏ꍇ�Atrue�i�����łȂ��ꍇ�Afalse�j<br>
	 *         ���s�����ꍇ�Afalse�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 */
	public boolean isBusy() {
		ex = null;
		try {
			return wb.getBusy();
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * ���݂̏�Ԃ�Ԃ�.
	 * 
	 * @return ���݂̏��<br>
	 *         ���s�����ꍇ�A-1�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 * @see OcIdlConst#READYSTATE_COMPLETE
	 */
	public int getReadyState() {
		ex = null;
		try {
			return wb.getReadyState();
		} catch (RuntimeException e) {
			doFail(e);
			return -1;
		}
	}

	/**
	 * �h�L�������g�����҂�.
	 * <p>
	 * �h�L�������g�̓ǂݍ��݂���������܂ő҂B
	 * </p>
	 * 
	 * @param time
	 *            �|�[�����O�Ԋu[�~���b]
	 * @param count
	 *            �|�[�����O��
	 * @return true�F�ǂݍ��݊���<br>
	 *         �^�C���A�E�g�܂��͎��s�����ꍇ�Afalse�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 */
	public boolean waitDocumentComplete(int time, int count) {
		ex = null;
		try {
			if (!wb.getBusy() && wb.getReadyState() == READYSTATE_COMPLETE)
				return true;

			for (int i = 1; i < count; i++) {
				delay(time);
				if (!wb.getBusy() && wb.getReadyState() == READYSTATE_COMPLETE)
					return true;
			}
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}

		// �^�C���A�E�g
		if (debug || rethrow) {
			TimeoutException e = new TimeoutException(0, time * count);
			doFail(e);
		}
		return false;
	}

	/**
	 * ���P�[�V�������擾.
	 * 
	 * @return ���P�[�V�����̖��O<br>
	 *         ���s�����ꍇ�Anull�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 */
	public String getLocationName() {
		ex = null;
		try {
			return wb.getLocationName();
		} catch (RuntimeException e) {
			doFail(e);
			return null;
		}
	}

	/**
	 * URL�擾.
	 * 
	 * @return URL<br>
	 *         ���s�����ꍇ�Anull�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 */
	public String getLocationURL() {
		ex = null;
		try {
			return wb.getLocationURL();
		} catch (RuntimeException e) {
			doFail(e);
			return null;
		}
	}

	/**
	 * �^�C�g���擾.
	 * 
	 * @return �^�C�g��<br>
	 *         ���s�����ꍇ�Anull�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 */
	public String getTitle() {
		IHTMLDocument doc = getDocument();
		if (doc != null) {
			try {
				return doc.getTitle();
			} catch (RuntimeException e) {
				doFail(e);
			}
		}
		return null;
	}

	//
	// ���W���[�����֘A
	//

	/**
	 * �h�L�������g�I�u�W�F�N�g���擾.
	 * 
	 * @return �h�L�������g�I�u�W�F�N�g��<br>
	 *         ���s�����ꍇ�Anull�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 */
	public String getType() {
		ex = null;
		try {
			return wb.getType();
		} catch (RuntimeException e) {
			doFail(e);
			return null;
		}
	}

	/**
	 * ���W���[�����擾.
	 * 
	 * @return ����<br>
	 *         ���s�����ꍇ�Anull�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 */
	public String getModuleName() {
		ex = null;
		try {
			return wb.getName();
		} catch (RuntimeException e) {
			doFail(e);
			return null;
		}
	}

	/**
	 * ���s���W���[���t���p�X�擾.
	 * 
	 * @return �t���p�X��<br>
	 *         ���s�����ꍇ�Anull�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 */
	public String getModuleFullName() {
		ex = null;
		try {
			return wb.getFullName();
		} catch (RuntimeException e) {
			doFail(e);
			return null;
		}
	}

	/**
	 * ���s���W���[���p�X�擾.
	 * 
	 * @return �p�X<br>
	 *         ���s�����ꍇ�Anull�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 */
	public String getModulePath() {
		ex = null;
		try {
			return wb.getPath();
		} catch (RuntimeException e) {
			doFail(e);
			return null;
		}
	}

	//
	// �E�B���h�E�֘A
	//

	/**
	 * �ʒu�ݒ�.
	 * 
	 * @param x
	 *            X
	 * @param y
	 *            Y
	 * @return ���������ꍇ�Atrue<br>
	 *         ���s�����ꍇ�Afalse�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 */
	public boolean setLocation(int x, int y) {
		ex = null;
		try {
			wb.setLocation(x, y);
			return true;
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * �ʒu�ݒ�.
	 * 
	 * @param pt
	 *            �ʒu
	 * @return ���������ꍇ�Atrue<br>
	 *         ���s�����ꍇ�Afalse�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 */
	public boolean setLocation(Point pt) {
		ex = null;
		try {
			wb.setLocation(pt.x, pt.y);
			return true;
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * �ʒu�擾.
	 * 
	 * @return �ʒu<br>
	 *         ���s�����ꍇ�Anull�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 */
	public Point getLocation() {
		ex = null;
		try {
			return wb.getLocation();
		} catch (RuntimeException e) {
			doFail(e);
			return null;
		}
	}

	/**
	 * �T�C�Y�ݒ�.
	 * 
	 * @param cx
	 *            ��
	 * @param cy
	 *            ����
	 * @return ���������ꍇ�Atrue<br>
	 *         ���s�����ꍇ�Afalse�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 */
	public boolean setSize(int cx, int cy) {
		ex = null;
		try {
			wb.setSize(cx, cy);
			return true;
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * �T�C�Y�ݒ�.
	 * 
	 * @param sz
	 *            �T�C�Y
	 * @return ���������ꍇ�Atrue<br>
	 *         ���s�����ꍇ�Afalse�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 */
	public boolean setSize(Dimension sz) {
		ex = null;
		try {
			wb.setSize(sz.width, sz.height);
			return true;
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * �T�C�Y�擾.
	 * 
	 * @return �T�C�Y<br>
	 *         ���s�����ꍇ�Anull�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 */
	public Dimension getSize() {
		ex = null;
		try {
			return wb.getSize();
		} catch (RuntimeException e) {
			doFail(e);
			return null;
		}
	}

	/**
	 * �ʒu�T�C�Y�ݒ�.
	 * 
	 * @param x
	 *            X
	 * @param y
	 *            Y
	 * @param cx
	 *            ��
	 * @param cy
	 *            ����
	 * @return ���������ꍇ�Atrue<br>
	 *         ���s�����ꍇ�Afalse�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 */
	public boolean setBounds(int x, int y, int cx, int cy) {
		ex = null;
		try {
			wb.setBounds(x, y, cx, cy);
			return true;
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * �ʒu�T�C�Y�ݒ�.
	 * 
	 * @param pt
	 *            �ʒu
	 * @param sz
	 *            �T�C�Y
	 * @return ���������ꍇ�Atrue<br>
	 *         ���s�����ꍇ�Afalse�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 */
	public boolean setBounds(Point pt, Dimension sz) {
		ex = null;
		try {
			wb.setBounds(pt.x, pt.y, sz.width, sz.height);
			return true;
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * �ʒu�T�C�Y�ݒ�.
	 * 
	 * @param rect
	 *            ��`
	 * @return ���������ꍇ�Atrue<br>
	 *         ���s�����ꍇ�Afalse�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 */
	public boolean setBounds(Rectangle rect) {
		ex = null;
		try {
			wb.setBounds(rect.x, rect.y, rect.width, rect.height);
			return true;
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * �ʒu�T�C�Y�擾.
	 * 
	 * @return ��`<br>
	 *         ���s�����ꍇ�Anull�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 */
	public Rectangle getBounds() {
		ex = null;
		try {
			return wb.getBounds();
		} catch (RuntimeException e) {
			doFail(e);
			return null;
		}
	}

	/**
	 * �T�C�Y�ύX�ێ擾.
	 * 
	 * @return �T�C�Y�ύX�\�ȏꍇ�Atrue�i�s�̏ꍇ�Afalse�j<br>
	 *         ���s�����ꍇ�Afalse�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 */
	public boolean isResizable() {
		ex = null;
		try {
			return wb.getResizable();
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * �T�C�Y�ύX�ېݒ�.
	 * 
	 * @param b
	 *            �T�C�Y�ύX��
	 * @return ���������ꍇ�Atrue<br>
	 *         ���s�����ꍇ�Afalse�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 */
	public boolean setResizable(boolean b) {
		ex = null;
		try {
			wb.setResizable(b);
			return true;
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * �\����Ԏ擾.
	 * 
	 * @return �\������Ă���ꍇ�Atrue�i��\���̏ꍇ�Afalse�j<br>
	 *         ���s�����ꍇ�Afalse�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 */
	public boolean isVisible() {
		ex = null;
		try {
			return wb.getVisible();
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * �\����Ԑݒ�.
	 * 
	 * @param b
	 *            �\�����
	 * @return ���������ꍇ�Atrue<br>
	 *         ���s�����ꍇ�Afalse�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 */
	public boolean setVisible(boolean b) {
		ex = null;
		try {
			wb.setVisible(b);
			return true;
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * �őO�ʈړ�.
	 * 
	 * @return ���������ꍇ�Atrue<br>
	 *         ���s�����ꍇ�Afalse�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 */
	public boolean setForeground() {
		ex = null;
		JWnd wnd = getWnd();
		if (wnd == null) {
			return false;
		}
		try {
			return wnd.SetForegroundWindow();
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * HWND�擾.
	 * 
	 * @return JWnd<br>
	 *         ���s�����ꍇ�Anull�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 */
	public JWnd getWnd() {
		ex = null;
		try {
			long hwnd = wb.getHWND();
			if (hwnd == 0)
				return null;
			return new JWnd(hwnd);
		} catch (RuntimeException e) {
			doFail(e);
			return null;
		}
	}

	/**
	 * �N���C�A���g�̈�HWND�擾.
	 * <p>
	 * �E�B���h�E�N���X�����uInternet Explorer_Server�v�ł���E�B���h�E��Ԃ��B
	 * </p>
	 * 
	 * @return JWnd�i������Ȃ������ꍇ�Anull�j<br>
	 *         ���s�����ꍇ�Anull�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 */
	public JWnd getCliendWnd() {
		ex = null;
		try {
			long hwnd = wb.getHWND();
			if (hwnd == 0)
				return null;
			List list = new LinkedList();
			WinUser.EnumChildWindows(hwnd, list);
			for (Iterator i = list.iterator(); i.hasNext();) {
				try {
					JWnd w = (JWnd) i.next();
					if ("Internet Explorer_Server".equals(w.getClassName())) {
						return w;
					}
				} catch (RuntimeException e) {
				}
			}
		} catch (RuntimeException e) {
			doFail(e);
		}
		return null;
	}

	//
	// �E�B���h�E�p�[�c�֘A
	//

	/**
	 * �X�e�[�^�X�o�[�\����Ԏ擾.
	 * 
	 * @return �\������Ă���ꍇ�Atrue�i��\���̏ꍇ�Afalse�j<br>
	 *         ���s�����ꍇ�Afalse�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 */
	public boolean isStatusBar() {
		ex = null;
		try {
			return wb.getStatusBar();
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * �X�e�[�^�X�o�[�\����Ԑݒ�.
	 * 
	 * @param b
	 *            �\�����
	 * @return ���������ꍇ�Atrue<br>
	 *         ���s�����ꍇ�Afalse�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 */
	public boolean setStatusBar(boolean b) {
		ex = null;
		try {
			wb.setStatusBar(b);
			return true;
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * �X�e�[�^�X�o�[�\��������擾.
	 * <p>
	 * �擾����������́A�����̕������Ȃ�������Ă���c�B
	 * </p>
	 * 
	 * @return ������<br>
	 *         ���s�����ꍇ�Anull�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 */
	public String getStatusText() {
		ex = null;
		try {
			return wb.getStatusText();
		} catch (RuntimeException e) {
			doFail(e);
			return null;
		}
	}

	/**
	 * �X�e�[�^�X�o�[������\��.
	 * 
	 * @param text
	 *            ������
	 * @return ���������ꍇ�Atrue<br>
	 *         ���s�����ꍇ�Afalse�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 */
	public boolean setStatusText(String text) {
		ex = null;
		try {
			wb.setStatusText(text);
			return true;
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * �c�[���o�[�\����Ԏ擾.
	 * 
	 * @return �\������Ă���ꍇ�Atrue�i��\���̏ꍇ�Afalse�j<br>
	 *         ���s�����ꍇ�Afalse�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 */
	public boolean isToolBar() {
		ex = null;
		try {
			return wb.getToolBar() != 0;
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * �c�[���o�[�\����Ԑݒ�.
	 * 
	 * @param b
	 *            �\�����
	 * @return ���������ꍇ�Atrue<br>
	 *         ���s�����ꍇ�Afalse�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 */
	public boolean setToolBar(boolean b) {
		ex = null;
		try {
			wb.setToolBar(b ? 1 : 0);
			return true;
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * ���j���[�o�[�\����Ԏ擾.
	 * 
	 * @return �\������Ă���ꍇ�Atrue�i��\���̏ꍇ�Afalse�j<br>
	 *         ���s�����ꍇ�Afalse�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 */
	public boolean isMenuBar() {
		ex = null;
		try {
			return wb.getMenuBar();
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * ���j���[�o�[�\����Ԑݒ�.
	 * 
	 * @param b
	 *            �\�����
	 * @return ���������ꍇ�Atrue<br>
	 *         ���s�����ꍇ�Afalse�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 */
	public boolean setMenuBar(boolean b) {
		ex = null;
		try {
			wb.setMenuBar(b);
			return true;
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * �t���X�N���[����Ԏ擾.
	 * 
	 * @return �t���X�N���[���̏ꍇ�Atrue�i�����łȂ��ꍇ�Afalse�j<br>
	 *         ���s�����ꍇ�Afalse�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 */
	public boolean isFullScreen() {
		ex = null;
		try {
			return wb.getFullScreen();
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * �t���X�N���[����Ԑݒ�.
	 * 
	 * @param b
	 *            �t���X�N���[�����
	 * @return ���������ꍇ�Atrue<br>
	 *         ���s�����ꍇ�Afalse�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 */
	public boolean setFullScreen(boolean b) {
		ex = null;
		try {
			wb.setFullScreen(b);
			return true;
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * �I�t���C����Ԏ擾.
	 * <p>
	 * �E�F�u�u���E�U�[���I�t���C����Ԃœ��삵�Ă���̂��ǂ�����Ԃ��B
	 * </p>
	 * 
	 * @return �I�t���C���̏ꍇ�Atrue�i�I�����C���̏ꍇ�Afalse�j<br>
	 *         ���s�����ꍇ�Afalse�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 */
	public boolean isOffline() {
		ex = null;
		try {
			return wb.getOffline();
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * �I�t���C����Ԑݒ�.
	 * 
	 * @param b
	 *            �I�t���C�����
	 * @return ���������ꍇ�Atrue<br>
	 *         ���s�����ꍇ�Afalse�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 */
	public boolean setOffline(boolean b) {
		ex = null;
		try {
			wb.setOffline(b);
			return true;
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * �_�C�A���O�{�b�N�X�\���ێ擾.
	 * 
	 * @return �_�C�A���O��\���ł��Ȃ��ꍇ�Atrue�i�\���ł���ꍇ�Afalse�j<br>
	 *         ���s�����ꍇ�Afalse�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 */
	public boolean isSilent() {
		ex = null;
		try {
			return wb.getSilent();
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * �_�C�A���O�{�b�N�X�\���ېݒ�.
	 * <p>
	 * �G���[�������Ȃǂ̃_�C�A���O��\�����邩�ǂ�����ݒ肷��B
	 * </p>
	 * 
	 * @param b
	 *            �\����
	 * @return ���������ꍇ�Atrue<br>
	 *         ���s�����ꍇ�Afalse�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 */
	public boolean setSilent(boolean b) {
		ex = null;
		try {
			wb.setSilent(b);
			return true;
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * �g�b�v���x���u���E�U�[�o�^��Ԏ擾.
	 * <p>
	 * �E�F�u�u���E�U�[���^�[�Q�b�g�������̃g�b�v���x���u���E�U�[�Ƃ��ēo�^����Ă��邩�ǂ�����Ԃ��B
	 * </p>
	 * 
	 * @return �g�b�v���x���u���E�U�[�̏ꍇ�Atrue�i�����łȂ��ꍇ�Afalse�j<br>
	 *         ���s�����ꍇ�Afalse�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 */
	public boolean isRegisterAsBrowser() {
		ex = null;
		try {
			return wb.getRegisterAsBrowser();
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * �g�b�v���x���u���E�U�[�o�^��Ԑݒ�.
	 * 
	 * @param b
	 *            �o�^���
	 * @return ���������ꍇ�Atrue<br>
	 *         ���s�����ꍇ�Afalse�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 */
	public boolean setRegisterAsBrowser(boolean b) {
		ex = null;
		try {
			wb.setRegisterAsBrowser(b);
			return true;
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * �h���b�v�^�[�Q�b�g�o�^��Ԏ擾.
	 * <p>
	 * �E�F�u�u���E�U�[���h���b�v���󂯕t���邩�ǂ�����Ԃ��B
	 * </p>
	 * 
	 * @return �h���b�v���󂯕t����ꍇ�Atrue�i�󂯕t���Ȃ��ꍇ�Afalse�j<br>
	 *         ���s�����ꍇ�Afalse�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 */
	public boolean isRegisterAsDropTarget() {
		ex = null;
		try {
			return wb.getRegisterAsDropTarget();
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * �h���b�v�^�[�Q�b�g�o�^��Ԏ擾.
	 * 
	 * @param b
	 *            �o�^���
	 * @return ���������ꍇ�Atrue<br>
	 *         ���s�����ꍇ�Afalse�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 */
	public boolean setRegisterAsDropTarget(boolean b) {
		ex = null;
		try {
			wb.setRegisterAsDropTarget(b);
			return true;
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * �V�A�^�[��Ԏ擾.
	 * 
	 * @return �V�A�^�[���[�h�̏ꍇ�Atrue�i�����łȂ��ꍇ�Afalse�j<br>
	 *         ���s�����ꍇ�Afalse�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 */
	public boolean isTheaterMode() {
		ex = null;
		try {
			return wb.getTheaterMode();
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * �V�A�^�[��Ԑݒ�.
	 * 
	 * @param b
	 *            �V�A�^�[���
	 * @return ���������ꍇ�Atrue<br>
	 *         ���s�����ꍇ�Afalse�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 */
	public boolean setTheaterMode(boolean b) {
		ex = null;
		try {
			wb.setTheaterMode(b);
			return true;
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * �A�h���X�o�[�\����Ԏ擾.
	 * 
	 * @return �\������Ă���ꍇ�Atrue�i��\���̏ꍇ�Afalse�j<br>
	 *         ���s�����ꍇ�Afalse�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 */
	public boolean isAddressBar() {
		ex = null;
		try {
			return wb.getAddressBar();
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	/**
	 * �A�h���X�o�[�\����Ԑݒ�.
	 * 
	 * @param b
	 *            �\�����
	 * @return ���������ꍇ�Atrue<br>
	 *         ���s�����ꍇ�Afalse�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 */
	public boolean setAddressBar(boolean b) {
		ex = null;
		try {
			wb.setAddressBar(b);
			return true;
		} catch (RuntimeException e) {
			doFail(e);
			return false;
		}
	}

	//
	// �t�@�C���_�E�����[�h�֘A
	//

	/**
	 * �t�@�C���_�E�����[�h���ۏ�Ԏ擾.
	 * 
	 * @return �t�@�C���_�E�����[�h�����ۂ���Ă���ꍇ�Atrue<br>
	 *         ���s�����ꍇ�Afalse�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 */
	public boolean isBlockingFileDownload() {
		JWnd w = getBlockingFileDownloadWnd();
		if (w != null) {
			return w.IsWindowVisible();
		}
		return false;
	}

	/**
	 * �t�@�C���_�E�����[�h�ېݒ�.
	 * <p>
	 * �t�@�C���_�E�����[�h�����ۂ����񂪕\������Ă���Ƃ��ɁA���̉ۂ�ݒ肷��B
	 * </p>
	 * 
	 * @param b
	 *            true�F�_�E�����[�h����<br>
	 *            false�F�_�E�����[�h�s�� �ɂ������̂����A�܂�������
	 * @return ���������ꍇ�Atrue<br>
	 *         ���s�����ꍇ�Afalse�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 */
	public boolean setBlockingFileDownload(boolean b) {
		JWnd w = getBlockingFileDownloadWnd();
		if (w == null) {
			return false;
		}
		if (b) {
			w.PostMessage(WinUserConst.BM_CLICK, 0, 0);
			JWnd popup = null;
			while (popup == null) {
				delay(100);
				popup = JWnd.FindWindow("#32768", null);
			}
			popup.SendMessage(WinUserConst.WM_CHAR, 'D', 0);
			disposeTemp();
			return true;
		} else {
			if (false) {
				w.SetFocus();
				delay(1000);
				Rectangle rt = w.getClientRect();
				int x = rt.width - 10, y = 8;
				int lpos = (y << 16) | x;
				w.PostMessage(WinUserConst.WM_MOUSEMOVE, 0, lpos);
				Point pt = new Point(x, y);
				w.ClientToScreen(pt);
				w
						.SendMessage(WinUserConst.WM_NCHITTEST, 0, (pt.y << 16)
								| pt.x);
				w.SendMessage(WinUserConst.WM_SETCURSOR, (int) w.GetSafeHwnd(),
						(WinUserConst.WM_MOUSEMOVE << 16) | 1);
				w.PostMessage(WinUserConst.WM_LBUTTONDOWN,
						WinUserConst.MK_LBUTTON, lpos);
				w.PostMessage(WinUserConst.WM_LBUTTONUP, 0, lpos);
				// w.PostMessage(WinUserConst.WM_MOUSELEAVE, 0, 0);
			}
			throw new RuntimeException("�L�����Z���͖������B�Ƃ�������肭�����Ȃ�����");
		}
	}

	/**
	 * �t�@�C���_�E�����[�h���ۃE�B���h�E�擾.
	 * <p>
	 * �t�@�C�����_�E�����[�h���悤�Ƃ����ۂɁAInternetExplorer6(SP2)�ɂ���ă_�E�����[�h���u���b�N����邱�Ƃ�����B���̃��b�Z�[�W��\�����Ă���E�B���h�E��Ԃ��B
	 * </p>
	 * 
	 * @return JWnd�i�����ꍇ�Anull�j<br>
	 *         ���s�����ꍇ�Anull�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 */
	public JWnd getBlockingFileDownloadWnd() {
		JWnd wnd = getWnd();
		if (wnd == null) {
			return null;
		}
		List list = wnd.enumChildWindows();
		if (list == null) {
			return null;
		}
		for (int i = 0; i < list.size(); i++) {
			JWnd w = (JWnd) list.get(i);
			if (w != null) {
				String name = w.getClassName();
				String text = w.GetWindowText();
				if ("Button".equals(name) && text != null
						&& text.startsWith("�Z�L�����e�B�ی�̂��߁A")) {
					return w;
				}
			}
		}
		return null;
	}

	/**
	 * �t�@�C���_�E�����[�h�_�C�A���O�擾.
	 * 
	 * @return {@link FileDownloadProgressDialog}�̃��X�g�i�K��null�ȊO�j
	 * @see FileDownloadDialogFactory
	 */
	public List getFileDownloadDialog() {
		FileDownloadDialogFactory factory = FileDownloadDialogFactory
				.getInstance();
		return factory.enumFileDownloadDialog();
	}

	//
	// �t�H�[���֘A
	//

	/**
	 * HTML�h�L�������g�擾.
	 * <p>
	 * �����\�b�h�ɂ��Ԃ��ꂽHTML�h�L�������g��IERobot�Ŕj������̂ŁA���[�U�[�͔j�����Ȃ����ƁB
	 * </p>
	 * 
	 * @return HTML�h�L�������g<br>
	 *         ���s�����ꍇ�Anull�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 */
	public IHTMLDocument getDocument() {
		ex = null;
		try {
			if (tempDoc == null) {
				tempDoc = wb.getDocument();
			}
		} catch (RuntimeException e) {
			doFail(e);
		}
		return tempDoc;
	}

	/**
	 * �t�H�[���ꗗ�擾.
	 * <p>
	 * �����\�b�h�ɂ��Ԃ��ꂽ�t�H�[����IERobot�Ŕj������̂ŁA���[�U�[�͔j�����Ȃ����ƁB
	 * </p>
	 * 
	 * @return {@link IHTMLFormElement}�̃��X�g<br>
	 *         ���s�����ꍇ�Anull�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 */
	public List getFormList() {
		ex = null;
		if (formList == null) {
			IHTMLDocument doc = getDocument();
			if (doc != null) {
				IHTMLElementCollection c = null;
				try {
					c = doc.getForms();
					formList = c.list();
				} catch (RuntimeException e) {
					doFail(e);
				} finally {
					ComPtr.dispose(c);
				}
			}
		}
		return formList;
	}

	/**
	 * �t�H�[���擾.
	 * <p>
	 * �����\�b�h�ɂ��Ԃ��ꂽ�t�H�[����IERobot�Ŕj������̂ŁA���[�U�[�͔j�����Ȃ����ƁB
	 * </p>
	 * 
	 * @param index
	 *            �t�H�[���ԍ�
	 * @return �t�H�[��<br>
	 *         ���s�����ꍇ�Anull�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 */
	public IHTMLFormElement getForm(int index) {
		List list = getFormList();
		try {
			return (IHTMLFormElement) list.get(index);
		} catch (RuntimeException e) {
			doFail(e);
		}
		return null;
	}

	/**
	 * �t�H�[���擾.
	 * <p>
	 * �����\�b�h�ɂ��Ԃ��ꂽ�t�H�[����IERobot�Ŕj������̂ŁA���[�U�[�͔j�����Ȃ����ƁB
	 * </p>
	 * 
	 * @param name
	 *            �t�H�[����
	 * @return �t�H�[���i������Ȃ��ꍇ�Anull�j<br>
	 *         ���s�����ꍇ�Anull�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 */
	public IHTMLFormElement getForm(String name) {
		List list = getFormList();
		try {
			for (int i = 0; i < list.size(); i++) {
				IHTMLFormElement f = (IHTMLFormElement) list.get(i);
				if (name.equals(f.getName())) {
					return f;
				}
			}
		} catch (RuntimeException e) {
			doFail(e);
		}
		return null;
	}

	/**
	 * HTML�v�f�擾.
	 * <p>
	 * �����\�b�h�ɂ��Ԃ��ꂽHTML�v�f��IERobot�Ŕj������̂ŁA���[�U�[�͔j�����Ȃ����ƁB
	 * </p>
	 * 
	 * @param id
	 *            HTML�̃^�O�ɕt�����Ă���ID
	 * @return HTML�v�f<br>
	 *         ���s�����ꍇ�Anull�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 */
	public IHTMLElement getElementById(String id) {
		if (idMap == null) {
			idMap = new HashMap();
		}
		IHTMLElement el = (IHTMLElement) idMap.get(id);
		if (el == null) {
			IHTMLDocument doc = getDocument();
			if (doc != null) {
				try {
					el = doc.getElementById(id);
					idMap.put(id, el);
				} catch (RuntimeException e) {
					doFail(e);
				}
			}
		}
		return el;
	}

	/**
	 * HTML�v�f�擾.
	 * <p>
	 * �����\�b�h�ɂ��Ԃ��ꂽHTML�v�f��IERobot�Ŕj������̂ŁA���[�U�[�͔j�����Ȃ����ƁB
	 * </p>
	 * 
	 * @param name
	 *            ����
	 * @return {@link IHTMLElement}�̃��X�g<br>
	 *         ���s�����ꍇ�Anull�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 */
	public List getElementListByName(String name) {
		if (nameMap == null) {
			nameMap = new HashMap();
		}
		List list = (List) nameMap.get(name);
		if (list == null) {
			IHTMLDocument doc = getDocument();
			if (doc != null) {
				IHTMLElementCollection c = null;
				try {
					c = doc.getElementsByName(name);
					list = c.list();
					nameMap.put(name, list);
				} catch (RuntimeException e) {
					doFail(e);
				} finally {
					ComPtr.dispose(c);
				}
			}
		}
		return list;
	}

	/**
	 * HTML�v�f�擾.
	 * <p>
	 * �����\�b�h�ɂ��Ԃ��ꂽHTML�v�f��IERobot�Ŕj������̂ŁA���[�U�[�͔j�����Ȃ����ƁB
	 * </p>
	 * 
	 * @param name
	 *            ���O
	 * @param index
	 *            �ԍ��i�����̃^�O����������ꍇ�̔ԍ��j
	 * @return HTML�v�f<br>
	 *         ���s�����ꍇ�Anull�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 */
	public IHTMLElement getElementByName(String name, int index) {
		List list = getElementListByName(name);
		if (list != null) {
			try {
				return (IHTMLElement) list.get(index);
			} catch (RuntimeException e) {
				doFail(e);
			}
		}
		return null;
	}

	/**
	 * HTML�v�f�ꗗ�擾.
	 * <p>
	 * �����\�b�h�ɂ��Ԃ��ꂽHTML�v�f��IERobot�Ŕj������̂ŁA���[�U�[�͔j�����Ȃ����ƁB
	 * </p>
	 * 
	 * @param tag
	 *            �^�O��
	 * @return {@link IHTMLElement}�̃��X�g<br>
	 *         ���s�����ꍇ�Anull�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 */
	public List getElementListByTagName(String tag) {
		if (tagMap == null) {
			tagMap = new HashMap();
		}
		tag = tag.toUpperCase();
		List list = (List) tagMap.get(tag);
		if (list == null) {
			IHTMLDocument doc = getDocument();
			if (doc != null) {
				IHTMLElementCollection c = null;
				try {
					c = doc.getElementsByTagName(tag);
					list = c.list();
					tagMap.put(tag, list);
				} catch (RuntimeException e) {
					doFail(e);
				} finally {
					ComPtr.dispose(c);
				}
			}
		}
		return list;
	}

	/**
	 * �����N�ꗗ�擾.
	 * <p>
	 * �����\�b�h�ɂ��Ԃ��ꂽHTML�v�f��IERobot�Ŕj������̂ŁA���[�U�[�͔j�����Ȃ����ƁB
	 * </p>
	 * 
	 * @return {@link IHTMLAnchorElement}�̃��X�g<br>
	 *         ���s�����ꍇ�Anull�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 */
	public List getLinkList() {
		ex = null;
		if (linkList == null) {
			IHTMLDocument doc = getDocument();
			if (doc != null) {
				IHTMLElementCollection c = null;
				try {
					c = doc.getLinks();
					linkList = c.list();
				} catch (RuntimeException e) {
					doFail(e);
				} finally {
					ComPtr.dispose(c);
				}
			}
		}
		return linkList;
	}

	/**
	 * �e�L�X�g���[�e�B���e�B�[�擾.
	 * 
	 * @param id
	 *            ID
	 * @return input���[�e�B���e�B�[
	 */
	public IHTMLInputValueUtil getInputById(String id) {
		IHTMLElement e = getElementById(id);
		if (e instanceof IHTMLInputElement) {
			return new IHTMLInputValueUtil(this, (IHTMLInputElement) e);
		}
		return null;
	}

	/**
	 * �e�L�X�g���[�e�B���e�B�[�擾.
	 * 
	 * @param name
	 *            ���O
	 * @param index
	 *            index
	 * @return input���[�e�B���e�B�[
	 */
	public IHTMLInputValueUtil getInputByName(String name, int index) {
		IHTMLElement e = getElementByName(name, index);
		if (e instanceof IHTMLInputElement) {
			return new IHTMLInputValueUtil(this, (IHTMLInputElement) e);
		}
		return null;
	}

	/**
	 * ���W�I�{�^�����[�e�B���e�B�[�擾.
	 * 
	 * @param name
	 *            ���O
	 * @return radio���[�e�B���e�B�[
	 */
	public IHTMLInputRadioUtil getRadioByName(String name) {
		List list = getElementListByName(name);
		if (list == null) {
			return null;
		}
		return new IHTMLInputRadioUtil(list);
	}

	/**
	 * �R���{�{�b�N�X�E���X�g�{�b�N�X�擾.
	 * 
	 * @param id
	 *            ID
	 * @return select���[�e�B���e�B�[
	 */
	public IHTMLSelectUtil getSelectById(String id) {
		IHTMLElement e = getElementById(id);
		if (e instanceof IHTMLSelectElement) {
			return new IHTMLSelectUtil((IHTMLSelectElement) e);
		}
		return null;
	}

	/**
	 * �R���{�{�b�N�X�E���X�g�{�b�N�X���[�e�B���e�B�[�擾.
	 * 
	 * @param name
	 *            ���O
	 * @param index
	 *            index
	 * @return select���[�e�B���e�B�[
	 */
	public IHTMLSelectUtil getSelectByName(String name, int index) {
		IHTMLElement e = getElementByName(name, index);
		if (e instanceof IHTMLSelectElement) {
			return new IHTMLSelectUtil((IHTMLSelectElement) e);
		}
		return null;
	}

	/**
	 * �T�u�~�b�g���s.
	 * <p>
	 * �A�N�e�B�u�ɂȂ��Ă���HTML�v�f������΁A���̗v�f�������Ă���t�H�[���ɑ΂��ăT�u�~�b�g����B<br>
	 * �����łȂ��ꍇ�A�擪�̃t�H�[���ɑ΂��ăT�u�~�b�g����B
	 * </p>
	 * 
	 * @return ���������ꍇ�Atrue<br>
	 *         ���s�����ꍇ�Afalse�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 */
	public boolean submit() {
		IHTMLDocument doc = getDocument();
		IHTMLElement el = null;
		try {
			el = doc.getActiveElement();
			if (el != null) {
				IHTMLFormElement f;
				if (el instanceof IHTMLInputElement) {
					IHTMLInputElement in = (IHTMLInputElement) el;
					f = in.getForm();
					if (f != null) {
						f.submit();
						return true;
					}
				}

				IHTMLElement p = el.getParentElement();
				while (p != null) {
					if (p instanceof IHTMLFormElement) {
						f = (IHTMLFormElement) p;
						f.submit();
						return true;
					}
					p = p.getParentElement();
				}
			}

		} catch (RuntimeException e) {
			doFail(e);
			return false;
		} finally {
			ComPtr.dispose(el);
		}

		return submit(0);
	}

	/**
	 * �T�u�~�b�g���s.
	 * <p>
	 * �w�肳�ꂽ�t�H�[���ԍ��̃t�H�[���ɑ΂��ăT�u�~�b�g����B
	 * </p>
	 * 
	 * @param index
	 *            �t�H�[���ԍ�
	 * @return ���������ꍇ�Atrue<br>
	 *         ���s�����ꍇ�Afalse�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 */
	public boolean submit(int index) {
		IHTMLFormElement f = getForm(index);
		if (f != null) {
			try {
				f.submit();
				return true;
			} catch (RuntimeException e) {
				doFail(e);
			}
		}
		return false;
	}

	/**
	 * �T�u�~�b�g���s.
	 * 
	 * @param name
	 *            �t�H�[����
	 * @return ���������ꍇ�Atrue<br>
	 *         ���s�����ꍇ�Afalse�^�������͗�O���X���[
	 * @see #setRethrow(boolean)
	 */
	public boolean submit(String name) {
		IHTMLFormElement f = getForm(name);
		if (f != null) {
			try {
				f.submit();
				return true;
			} catch (RuntimeException e) {
				doFail(e);
			}
		}
		return false;
	}

}
