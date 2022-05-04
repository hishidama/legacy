package jp.hishidama.robot.ie;

import java.util.List;

import jp.hishidama.robot.win.FileSaveDialog;
import jp.hishidama.win32.JWnd;

/**
 * IEファイルダウンロードダイアログ.
 * <p>
 * IEでファイルをダウンロードする際の、最終的にファイルダウンロードを行うダイアログ。
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">ひしだま</a>
 * @see FileDownloadDialogFactory#enumFileDownloadDialog()
 * @see FileDownloadRobot#download(int, String, int)
 * @since 2007.11.01
 */
public class FileDownloadProgressDialog extends FileDownloadDialog {

	protected FileDownloadConfirmDialog confirmDlg;

	protected FileSaveDialog saveDlg;

	protected JWnd close, file, folder, cancel;

	/**
	 * コンストラクター.
	 * 
	 * @param wnd
	 *            「ファイルダのウンロード」ダイアログのウィンドウハンドル
	 * @see FileDownloadDialogFactory#enumFileDownloadDialog()
	 */
	public FileDownloadProgressDialog(JWnd wnd) {
		super(wnd);
	}

	/**
	 * 開く/保存確認ダイアログ取得.
	 * 
	 * @return 開く/保存確認ダイアログ（存在しない場合、null）
	 */
	public FileDownloadConfirmDialog getConfirmDialog() {
		if (confirmDlg != null) {
			if (!confirmDlg.getWnd().isWindow()) {
				return null;
			}
			return confirmDlg;
		}

		List list = JWnd.enumWindows();
		for (int i = 0; i < list.size(); i++) {
			JWnd w = (JWnd) list.get(i);
			if (!wnd.equals(w.GetParent())) {
				continue;
			}
			if (isConfirmDlg(w)) {
				confirmDlg = createConfirmDlg(w);
				break;
			}
		}
		return confirmDlg;
	}

	/**
	 * 開く/保存確認ダイアログ判断.
	 * <p>
	 * IEのバージョンが変わったとき等は当メソッドをオーバーライドして判定方法を変更する想定。
	 * </p>
	 * 
	 * @param w
	 *            ウィンドウハンドル
	 * @return 開く/保存確認ダイアログの場合、true
	 */
	protected boolean isConfirmDlg(JWnd w) {
		String title = w.GetWindowText();
		return "ファイルのダウンロード".equals(title);
	}

	protected FileDownloadConfirmDialog createConfirmDlg(JWnd w) {
		return new FileDownloadConfirmDialog(w);
	}

	/**
	 * 保存ダイアログ取得.
	 * 
	 * @return 保存ダイアログ（存在しない場合、null）
	 */
	public FileSaveDialog getSaveDialog() {
		if (saveDlg != null) {
			if (!saveDlg.getWnd().isWindow()) {
				return null;
			}
			return saveDlg;
		}

		List list = JWnd.enumWindows();
		for (int i = 0; i < list.size(); i++) {
			JWnd w = (JWnd) list.get(i);
			if (!wnd.equals(w.GetParent())) {
				continue;
			}
			if (isSaveDlg(w)) {
				saveDlg = createSaveDlg(w);
				break;
			}
		}
		return saveDlg;
	}

	protected boolean isSaveDlg(JWnd w) {
		String title = w.GetWindowText();
		return "名前を付けて保存".equals(title);
	}

	protected FileSaveDialog createSaveDlg(JWnd w) {
		return new FileSaveDialog(w);
	}

	protected void doInit(InitData ini) {
		// if (ini.i == 0)
		// listID();

		if (initInfo(ini)) {
			return;
		}
		if (initCloseButton(ini)) {
			return;
		}
		if (initFileButton(ini)) {
			return;
		}
		if (initFolderButton(ini)) {
			return;
		}
		if (initCanelButton(ini)) {
			return;
		}
	}

	protected boolean initInfo(InitData ini) {
		if (!"msctls_progress32".equals(ini.name)) {
			return false;
		}
		JWnd w = (JWnd) ini.list.get(ini.i - 1);
		String info = w.GetWindowText();
		int f = info.indexOf(" - ");
		String src, name;
		if (f >= 0) {
			src = info.substring(0, f);
			name = info.substring(f + 3);
		} else {
			src = null;
			name = info;
		}
		setSrc(src);
		setName(name);
		return true;
	}

	protected boolean initCloseButton(InitData ini) {
		if (ini.isButton() && ini.textStartsWith("ダウンロードの完了後、このダイアログ ボックスを閉じる")) {
			setCloseButton(ini.wnd);
			return true;
		}
		return false;
	}

	protected boolean initFileButton(InitData ini) {
		if (ini.isButton() && ini.textStartsWith("ファイルを開く")) {
			setFileButton(ini.wnd);
			return true;
		}
		return false;
	}

	protected boolean initFolderButton(InitData ini) {
		if (ini.isButton() && ini.textStartsWith("フォルダを開く")) {
			setFolderButton(ini.wnd);
			return true;
		}
		return false;
	}

	protected boolean initCanelButton(InitData ini) {
		if (ini.isButton() && ini.isDlgCtrl(IDCANCEL)) {
			setCancelButton(ini.wnd);
			return true;
		}
		return false;
	}

	protected void setCloseButton(JWnd btn) {
		this.close = btn;
	}

	protected void setFileButton(JWnd btn) {
		this.file = btn;
	}

	protected void setFolderButton(JWnd btn) {
		this.folder = btn;
	}

	protected void setCancelButton(JWnd btn) {
		this.cancel = btn;
	}

	/**
	 * 「ダウンロード完了時にダイアログを閉じる」チェック取得.
	 * 
	 * @return ダウンロード完了時にダイアログを閉じるチェックが付いている場合、true
	 */
	public boolean isCloseDownloaded() {
		init();
		try {
			return wnd.IsDlgButtonChecked(this.close.GetDlgCtrlID()) != BST_UNCHECKED;
		} catch (RuntimeException e) {
			return false;
		}
	}

	/**
	 * 「ダウンロード完了時にダイアログを閉じる」チェック設定.
	 * 
	 * @param b
	 *            ダウンロード完了時にダイアログを閉じる場合、true
	 * @return 成功した場合、true
	 */
	public boolean setCloseDownloaded(boolean b) {
		init();
		try {
			int c = b ? BST_CHECKED : BST_UNCHECKED;
			return wnd.CheckDlgButton(close.GetDlgCtrlID(), c);
		} catch (RuntimeException e) {
			return false;
		}
	}

	/**
	 * 「ファイルを開く」ボタン有効判断.
	 * 
	 * @return 有効な場合、true
	 */
	public boolean isFileButtonEnabled() {
		init();
		try {
			return file.IsWindowEnabled();
		} catch (RuntimeException e) {
			return false;
		}
	}

	/**
	 * 「ファイルを開く」ボタンクリック.
	 * <p>
	 * 事前にダイアログにフォーカスを当てておくこと。
	 * </p>
	 * 
	 * @return 成功した場合、true
	 */
	public boolean clickFileButton() {
		init();
		return click(file);
	}

	/**
	 * 「フォルダを開く」ボタン有効判断.
	 * 
	 * @return 有効な場合、true
	 */
	public boolean isFolderButtonEnabled() {
		init();
		try {
			return folder.IsWindowEnabled();
		} catch (RuntimeException e) {
			return false;
		}
	}

	/**
	 * 「フォルダを開く」ボタンクリック.
	 * <p>
	 * 事前にダイアログにフォーカスを当てておくこと。
	 * </p>
	 * 
	 * @return 成功した場合、true
	 */
	public boolean clickFolderButton() {
		init();
		return click(folder);
	}

	/**
	 * 「キャンセル」ボタンクリック.
	 * <p>
	 * 場合によっては「閉じる」ボタンだが、「キャンセル」ボタンと同一。
	 * </p>
	 * 
	 * @return 成功した場合、true
	 */
	public boolean clickCancelButton() {
		init();
		return click(cancel);
	}

}
