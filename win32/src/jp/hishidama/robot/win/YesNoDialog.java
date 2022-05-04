package jp.hishidama.robot.win;

import jp.hishidama.win32.JWnd;

/**
 * 「はい/いいえ」ダイアログ.
 * <p>
 * Yes/Noを確認するダイアログ。
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">ひしだま</a>
 * @since 2007.11.01
 */
public class YesNoDialog extends WindowsDialog {

	protected JWnd yes, no;

	/**
	 * コンストラクター.
	 * 
	 * @param wnd
	 *            「はい」/「いいえ」を選択するダイアログのウィンドウハンドル
	 * @see FileSaveDialog#getOverwirteDialog()
	 */
	public YesNoDialog(JWnd wnd) {
		super(wnd);
	}

	protected void doInit(InitData ini) {
		// if (ini.i == 0)
		// listID();

		if (initYesButton(ini)) {
			return;
		}
		if (initNoButton(ini)) {
			return;
		}
	}

	protected boolean initYesButton(InitData ini) {
		if (ini.isButton() && ini.isDlgCtrl(IDYES)) {
			setYesButton(ini.wnd);
			return true;
		}
		return false;
	}

	protected boolean initNoButton(InitData ini) {
		if (ini.isButton() && ini.isDlgCtrl(IDNO)) {
			setNoButton(ini.wnd);
			return true;
		}
		return false;
	}

	protected void setYesButton(JWnd btn) {
		this.yes = btn;
	}

	protected void setNoButton(JWnd btn) {
		this.no = btn;
	}

	/**
	 * 「はい」ボタンクリック.
	 * 
	 * @return 成功した場合、true
	 */
	public boolean clickYesButton() {
		init();
		return click(yes);
	}

	/**
	 * 「いいえ」ボタンクリック.
	 * 
	 * @return 成功した場合、true
	 */
	public boolean clickNoButton() {
		init();
		return click(no);
	}
}
