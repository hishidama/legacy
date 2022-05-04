package jp.hishidama.robot.ie;

import jp.hishidama.win32.JWnd;

/**
 * IEファイルダウンロード：開く/保存確認ダイアログ.
 * <p>
 * IEでファイルをダウンロードする際の、ファイルを開くか保存するか確認するダイアログ。
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">ひしだま</a>
 * @see FileDownloadProgressDialog#getConfirmDialog()
 * @since 2007.11.01
 */
public class FileDownloadConfirmDialog extends FileDownloadDialog {

	protected String type;

	protected JWnd open, save, cancel;

	/**
	 * コンストラクター.
	 * 
	 * @param wnd
	 *            「開く」/「保存」を選択するダイアログのウィンドウハンドル
	 * @see FileDownloadProgressDialog#getConfirmDialog()
	 */
	public FileDownloadConfirmDialog(JWnd wnd) {
		super(wnd);
	}

	protected void doInit(InitData ini) {
		// if (ini.i == 0)
		// listID();

		if (initName(ini)) {
			return;
		}
		if (initType(ini)) {
			return;
		}
		if (initSrc(ini)) {
			return;
		}
		if (initOpenButton(ini)) {
			return;
		}
		if (initSaveButton(ini)) {
			return;
		}
		if (initCancelButton(ini)) {
			return;
		}
	}

	protected boolean initName(InitData ini) {
		if (ini.isStatic() && ini.text != null && ini.text.startsWith("名前")) {
			ini.next();
			setName(ini.text);
			return true;
		}
		return false;
	}

	protected boolean initType(InitData ini) {
		if (ini.isStatic() && ini.text != null && ini.text.startsWith("種類")) {
			ini.next();
			setType(ini.text);
			return true;
		}
		return false;
	}

	protected boolean initSrc(InitData ini) {
		if (ini.isStatic() && ini.text != null && ini.text.startsWith("発信元")) {
			ini.next();
			setSrc(ini.text);
			return true;
		}
		return false;
	}

	protected boolean initOpenButton(InitData ini) {
		if (ini.isButton() && ini.textStartsWith("開く")) {
			setOpenButton(ini.wnd);
			return true;
		}
		return false;
	}

	protected boolean initSaveButton(InitData ini) {
		if (ini.isButton() && ini.textStartsWith("保存")) {
			setSaveButton(ini.wnd);
			return true;
		}
		return false;
	}

	protected boolean initCancelButton(InitData ini) {
		if (ini.isButton() && ini.isDlgCtrl(IDCANCEL)) {
			setCancelButton(ini.wnd);
			return true;
		}
		return false;
	}

	protected void setType(String type) {
		this.type = type;
	}

	/**
	 * 種類取得.
	 * 
	 * @return 種類
	 */
	public String getType() {
		init();
		return type;
	}

	protected void setOpenButton(JWnd btn) {
		this.open = btn;
	}

	protected void setSaveButton(JWnd btn) {
		this.save = btn;
	}

	protected void setCancelButton(JWnd btn) {
		this.cancel = btn;
	}

	/**
	 * 「開く」ボタンクリック.
	 * <p>
	 * 事前にダイアログにフォーカスを当てておくこと。
	 * </p>
	 * 
	 * @return 成功した場合、true
	 */
	public boolean clickOpenButton() {
		init();
		return click(open);
	}

	/**
	 * 「保存」ボタンクリック.
	 * <p>
	 * 事前にダイアログにフォーカスを当てておくこと。
	 * </p>
	 * 
	 * @return 成功した場合、true
	 */
	public boolean clickSaveButton() {
		init();
		return click(save);
	}

	/**
	 * 「キャンセル」ボタンクリック.
	 * 
	 * @return 成功した場合、true
	 */
	public boolean clickCancelButton() {
		init();
		return click(cancel);
	}
}
