package jp.hishidama.robot.win;

import java.util.List;

import jp.hishidama.robot.ie.FileDownloadProgressDialog;
import jp.hishidama.win32.JWnd;

/**
 * 「ファイルの保存」ダイアログ.
 * <p>
 * ファイルを保存する場所を指定するダイアログ。
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">ひしだま</a>
 * @since 2007.11.01
 */
public class FileSaveDialog extends WindowsDialog {

	protected JWnd name;

	protected JWnd save, cancel;

	/**
	 * コンストラクター.
	 * 
	 * @param wnd
	 *            「ファイルの保存」ダイアログのウィンドウハンドル
	 * @see FileDownloadProgressDialog#getSaveDialog()
	 */
	public FileSaveDialog(JWnd wnd) {
		super(wnd);
	}

	protected void doInit(InitData ini) {
		// if (ini.i == 0)
		// listID();

		if (initNameEdit(ini)) {
			return;
		}
		if (initSaveButton(ini)) {
			return;
		}
		if (initCanelButton(ini)) {
			return;
		}
	}

	protected boolean initNameEdit(InitData ini) {
		if (ini.isStatic() && ini.textStartsWith("ファイル名")) {
			for (;;) {
				ini.next();
				if (!ini.hasData()) {
					break;
				}
				if (ini.isEdit()) {
					setNameEdit(ini.wnd);
					break;
				}
			}
			return true;
		}
		return false;
	}

	protected boolean initSaveButton(InitData ini) {
		if (ini.isButton() && ini.isDlgCtrl(IDOK)) {
			setSaveButton(ini.wnd);
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

	protected void setNameEdit(JWnd edit) {
		this.name = edit;
	}

	protected void setSaveButton(JWnd btn) {
		this.save = btn;
	}

	protected void setCancelButton(JWnd btn) {
		this.cancel = btn;
	}

	/**
	 * ファイル名取得.
	 * 
	 * @return ファイル名（失敗した場合、null）
	 */
	public String getName() {
		init();
		return getEditText(name);
	}

	/**
	 * ファイル名設定.
	 * 
	 * @param text
	 *            ファイル名
	 * @return 成功した場合、true
	 */
	public boolean setName(String text) {
		init();
		return setEditText(name, text);
	}

	/**
	 * 「保存」ボタンクリック.
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

	/**
	 * 「名前を付けて保存」ダイアログ取得.
	 * 
	 * @return 「はい/いいえ」ダイアログ（存在しない場合、null）
	 */
	public YesNoDialog getOverwirteDialog() {
		List list = JWnd.enumWindows();
		for (int i = 0; i < list.size(); i++) {
			JWnd w = (JWnd) list.get(i);
			if (wnd.equals(w.GetParent()) && isOverwirteDialog(w)) {
				return new YesNoDialog(w);
			}
		}
		return null;
	}

	protected boolean isOverwirteDialog(JWnd w) {
		return "名前を付けて保存".equals(w.GetWindowText());
	}
}
