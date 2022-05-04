package jp.hishidama.robot.ie;

import jp.hishidama.robot.TimeoutException;
import jp.hishidama.robot.win.FileSaveDialog;
import jp.hishidama.robot.win.YesNoDialog;

/**
 * IEファイルダウンロード自動実行クラス.
 * <p>
 * IEを使ったファイルのダウンロードを行うクラス。
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">ひしだま</a>
 * @since 2007.11.01
 * @version 2007.11.06
 */
public class FileDownloadRobot {

	/** ファイル処理：開く */
	public static final int CONFIRM_OPEN = 0;

	/** ファイル処理：保存 */
	public static final int CONFIRM_SAVE = 1;

	/** ファイル処理：キャンセル */
	public static final int CONFIRM_CANCEL = 2;

	/**
	 * タイムアウト時：ダウンロード処理開始中
	 * 
	 * @see #download(int, String, int)
	 * @see TimeoutException#getPos()
	 */
	public static final int POS_START = 0;

	/** タイムアウト時：開く/保存の確認中 */
	public static final int POS_CONFIRM = 1;

	/** タイムアウト時：開く/保存の確認完了後 */
	public static final int POS_CONFIRM_END = 2;

	/** タイムアウト時：保存場所の確認中 */
	public static final int POS_SAVE = 3;

	/** タイムアウト時：保存場所の確認完了後 */
	public static final int POS_SAVE_END = 4;

	/** タイムアウト時：上書き確認中 */
	public static final int POS_OVERWRITE = 5;

	/** タイムアウト時：上書き確認完了後 */
	public static final int POS_OVERWRITE_END = 6;

	/** タイムアウト時：クローズ処理中 */
	public static final int POS_CLOSE = 7;

	protected FileDownloadProgressDialog pd;

	/**
	 * コンストラクター
	 * 
	 * @param pd
	 *            処理対象の「ファイルのダウンロード」ダイアログ
	 */
	public FileDownloadRobot(FileDownloadProgressDialog pd) {
		this.pd = pd;
	}

	protected int pos;

	/**
	 * ダウンロード実行.
	 * 
	 * @param confirm
	 *            {@link #CONFIRM_SAVE}等
	 * @param name
	 *            保存する場合のファイル名（nullなら デフォルトのまま）
	 * @param timeout
	 *            タイムアウト時間（0未満だとタイムアウトしない）
	 * @exception TimeoutException
	 *                タイムアウト時（posは{@link #POS_START}等）
	 */
	public void download(int confirm, String name, int timeout) {

		pos = POS_START;
		long start = System.currentTimeMillis();

		// ダウンロードダイアログが有効な間、処理
		while (pd.isWindow()) {
			try {
				// if (!pd.isCloseDownloaded()) {
				if (pd.isFileButtonEnabled() && pd.isFolderButtonEnabled()) {
					// 「ファイルを開く」ボタンと「フォルダーを開く」ボタンが有効になっていたら
					// ダウンロード完了
					pd.clickCancelButton(); // 「閉じる」ボタン（IDはキャンセルボタンと同一）
					pos = POS_CLOSE;
					while (pd.isWindow()) {
						long t = System.currentTimeMillis();
						if (timeout >= 0 && t - start > timeout) {
							throw new TimeoutException(pos, timeout);
						}
						pd.delay(100);
					}
					break;
				}
				// }
			} catch (TimeoutException e) {
				throw e;
			} catch (RuntimeException e) {
			}

			long t = System.currentTimeMillis();
			if (timeout >= 0 && t - start > timeout) {
				throw new TimeoutException(pos, timeout);
			}

			// 開くか保存するかを確認するダイアログ
			FileDownloadConfirmDialog cd = pd.getConfirmDialog();
			if (cd != null) {
				doConfirmDialog(cd, confirm, start, timeout);
			}

			// 「名前を付けて保存」ダイアログ
			FileSaveDialog sd = pd.getSaveDialog();
			if (sd != null) {
				doSaveDialog(sd, name, start, timeout);
			}

			pd.delay(100);
		}
	}

	protected void doConfirmDialog(FileDownloadConfirmDialog cd, int confirm,
			long start, int timeout) {
		pos = POS_CONFIRM;
		while (cd.isWindow()) {
			cd.SetForegroundWindow();
			// cd.setFocus();

			long t = System.currentTimeMillis();
			if (timeout >= 0 && t - start > timeout) {
				throw new TimeoutException(pos, timeout);
			}

			switch (confirm) {
			case CONFIRM_OPEN:
				cd.clickOpenButton();
				break;
			case CONFIRM_SAVE:
				cd.clickSaveButton();
				break;
			case CONFIRM_CANCEL:
				cd.clickCancelButton();
				break;
			default:
				throw new IllegalArgumentException("confirm:" + confirm);
			}

			cd.delay(100);
		}
		pos = POS_CONFIRM_END;
	}

	protected void doSaveDialog(FileSaveDialog sd, String name, long start,
			int timeout) {
		pos = POS_SAVE;

		if (name != null) {
			sd.SetForegroundWindow();
			sd.delay(100);
			for (;;) {
				// 保存するファイル名を変更
				sd.setName(name);
				sd.delay(100);

				String n = sd.getName();
				if (name.equals(n)) {
					break;
				}

				long t = System.currentTimeMillis();
				if (timeout >= 0 && t - start > timeout) {
					throw new TimeoutException(pos, timeout);
				}
			}
		}

		while (sd.isWindow()) {
			// sd.setFocus();
			sd.SetForegroundWindow();

			long t = System.currentTimeMillis();
			if (timeout >= 0 && t - start > timeout) {
				throw new TimeoutException(pos, timeout);
			}

			sd.clickSaveButton();

			// ファイルが既に存在している場合、『上書きするかどうかを確認するダイアログ』が開く
			YesNoDialog yd = sd.getOverwirteDialog();
			if (yd != null) {
				pos = POS_OVERWRITE;
				while (yd.isWindow()) {
					t = System.currentTimeMillis();
					if (timeout >= 0 && t - start > timeout) {
						throw new TimeoutException(pos, timeout);
					}
					yd.clickYesButton();
					yd.delay(100);
				}
				pos = POS_OVERWRITE_END;

				while (sd.isWindow()) {
					t = System.currentTimeMillis();
					if (timeout >= 0 && t - start > timeout) {
						throw new TimeoutException(pos, timeout);
					}

					sd.delay(100);
				}
				break;

			} else {
				sd.delay(100);
			}
		}
		pos = POS_SAVE_END;
	}
}
