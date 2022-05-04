package jp.hishidama.robot.ie;

import java.util.ArrayList;
import java.util.List;

import jp.hishidama.robot.win.WindowsDialog;
import jp.hishidama.win32.JWnd;

/**
 * IEファイルダウンロードダイアログファクトリー.
 * <p>
 * トップレベルの「ファイルのダウンロード」ダイアログを列挙するクラス。
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">ひしだま</a>
 * @since 2007.11.01
 */
public class FileDownloadDialogFactory {

	protected static FileDownloadDialogFactory instance;

	/**
	 * ファクトリー取得.
	 * 
	 * @return ファクトリー
	 */
	public static FileDownloadDialogFactory getInstance() {
		if (instance == null) {
			instance = new FileDownloadDialogFactory();
		}
		return instance;
	}

	protected FileDownloadDialogFactory() {
	}

	/**
	 * 「ファイルのダウンロード」ダイアログ一覧取得.
	 * <p>
	 * 現在表示されている、トップレベルの「ファイルのダウンロード」ダイアログを全て取得する。
	 * </p>
	 * 
	 * @return {@link FileDownloadProgressDialog}のリスト（必ずnull以外）
	 */
	public List enumFileDownloadDialog() {
		List list = JWnd.enumWindows();
		if (list == null) {
			return new ArrayList(0);
		}

		List pl = new ArrayList(list.size() / 8);
		for (int i = 0; i < list.size(); i++) {
			JWnd wnd = (JWnd) list.get(i);
			try {
				if (addProgress(pl, wnd)) {
					continue;
				}
			} catch (RuntimeException e) {
			}
		}

		return pl;
	}

	protected final String DIALOG_CLASS = "#32770";

	protected final String DIALOG_NAME = "ファイルのダウンロード";

	protected boolean addProgress(List pl, JWnd wnd) {
		if (DIALOG_CLASS.equals(wnd.getClassName())
				&& DIALOG_NAME.equals(wnd.GetWindowText())
				&& wnd.GetParent() == null) {
			pl.add(new FileDownloadProgressDialog(wnd));
			return true;
		}
		return false;
	}

}

/**
 * IEファイルダウンロードスーパークラス.
 * <p>
 * 「ファイルのダウンロード」ダイアログを扱うクラス。
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">ひしだま</a>
 * @since 2007.11.01
 */
abstract class FileDownloadDialog extends WindowsDialog {

	protected String src;

	protected String name;

	protected FileDownloadDialog(JWnd wnd) {
		super(wnd);
	}

	protected void setSrc(String src) {
		this.src = src;
	}

	/**
	 * 発信元取得.
	 * <p>
	 * ダイアログに表示されている、ダウンロード元のサイト名を取得する。
	 * </p>
	 * 
	 * @return 発信元
	 */
	public String getSrc() {
		init();
		return src;
	}

	protected void setName(String name) {
		this.name = name;
	}

	/**
	 * 名前取得.
	 * <p>
	 * ダイアログに表示されている、ダウンロードするファイル名を取得する。
	 * </p>
	 * 
	 * @return ファイル名
	 */
	public String getName() {
		init();
		return name;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer(256);
		sb.append(super.toString());
		sb.append("{ HWND=");
		sb.append(Long.toHexString(wnd.GetSafeHwnd()));
		sb.append(", SRC=");
		sb.append(getSrc());
		sb.append(", NAME=");
		sb.append(getName());
		sb.append(" }");
		return sb.toString();
	}

}
