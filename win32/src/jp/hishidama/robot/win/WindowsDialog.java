package jp.hishidama.robot.win;

import java.util.List;

import jp.hishidama.win32.JWnd;
import jp.hishidama.win32.api.WinUserConst;

/**
 * Windowsダイアログ.
 * <p>
 * MS-Windowsのダイアログを扱うクラス。
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">ひしだま</a>
 * @since 2007.11.01
 */
public abstract class WindowsDialog implements WinUserConst {

	/**
	 * 指定時間待機.
	 * 
	 * @param time
	 *            ウェイト[ミリ秒]
	 */
	public void delay(int time) {
		if (time <= 0)
			return;
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
		}
	}

	protected JWnd wnd;

	/**
	 * コンストラクター
	 * 
	 * @param wnd
	 *            ダイアログのウィンドウハンドル
	 */
	public WindowsDialog(JWnd wnd) {
		this.wnd = wnd;
	}

	/**
	 * ダイアログのウィンドウハンドル取得.
	 * 
	 * @return JWnd
	 */
	public JWnd getWnd() {
		return wnd;
	}

	/**
	 * ウィンドウ存在判断.
	 * 
	 * @return ダイアログとして有効な場合、true
	 */
	public boolean isWindow() {
		return wnd.isWindow();
	}

	/**
	 * 最前面移動.
	 * 
	 * @return 成功した場合、true
	 */
	public boolean SetForegroundWindow() {
		try {
			return wnd.SetForegroundWindow();
		} catch (RuntimeException e) {
			return false;
		}
	}

	/**
	 * フォーカス設定.
	 */
	public void setFocus() {
		try {
			wnd.SetFocus();
		} catch (RuntimeException e) {
		}
	}

	protected boolean init = false;

	/**
	 * コントロール初期化.
	 * <p>
	 * 子ウィンドウ（コントロール）を取得する為に呼ぶ。<br>
	 * 何度呼ばれても最初の一度しか初期化しない。
	 * </p>
	 * 
	 * @see #doInit(jp.hishidama.robot.win.WindowsDialog.InitData)
	 */
	protected void init() {
		if (!init) {
			try {
				InitData ini = new InitData(wnd);
				while (ini.hasData()) {
					doInit(ini);
					ini.next();
				}
			} catch (RuntimeException e) {
			}
			init = true;
		}
	}

	/**
	 * コントロール初期化処理本体.
	 * <p>
	 * {@link #init()}からダイアログの子ウィンドウ（コントロール）毎に呼ばれる。<br>
	 * サブクラスで当メソッドを実装し、必要なコントロールの保持を行う。
	 * </p>
	 * 
	 * @param ini
	 *            初期化処理対象コントロール情報
	 * @see #init()
	 */
	protected abstract void doInit(InitData ini);

	protected class InitData {
		/** 子ウィンドウ（{@link JWnd}）のリスト */
		public List list;

		/** {@link #list}内の現在の処理位置 */
		public int i;

		/** 現在の子ウィンドウのウィンドウハンドル */
		public JWnd wnd;

		/** 現在の子ウィンドウのウィンドウクラス名 */
		public String name;

		/** 現在の子ウィンドウのテキスト（タイトル） */
		public String text;

		protected int size;

		/**
		 * コンストラクター.
		 * 
		 * @param dlg
		 *            ダイアログのウィンドウハンドル
		 */
		public InitData(JWnd dlg) {
			list = dlg.enumChildWindows();
			size = list.size();
			i = -1;
			next();
		}

		/**
		 * 次の子ウィンドウへ移動.
		 */
		public void next() {
			if (++i >= size) {
				wnd = null;
				name = null;
				text = null;
			} else {
				wnd = (JWnd) list.get(i);
				name = wnd.getClassName();
				text = wnd.GetWindowText();
			}
		}

		/**
		 * 処理対象有無取得.
		 * 
		 * @return 処理対象を保持している場合、true
		 */
		public boolean hasData() {
			return wnd != null;
		}

		/**
		 * ラベル判断.
		 * 
		 * @return ウィンドウクラス名がStaticの場合、true
		 */
		public boolean isStatic() {
			return "Static".equals(name);
		}

		/**
		 * エディットボックス判断.
		 * 
		 * @return ウィンドウクラス名がEditの場合、true
		 */
		public boolean isEdit() {
			return "Edit".equals(name);
		}

		/**
		 * ボタン判断.
		 * 
		 * @return ウィンドウクラス名がButtonの場合、true
		 */
		public boolean isButton() {
			return "Button".equals(name);
		}

		/**
		 * テキスト判断.
		 * 
		 * @param prefix
		 *            テキストの先頭文字列
		 * @return テキストが指定された文字列で始まっている場合、true
		 */
		public boolean textStartsWith(String prefix) {
			return text != null && text.startsWith(prefix);
		}

		/**
		 * コントロールID判断.
		 * 
		 * @param id
		 *            コントロールID
		 * @return 現在の子ウィンドウのコントロールが指定されたものである場合、true
		 */
		public boolean isDlgCtrl(int id) {
			return wnd != null && (wnd.GetDlgCtrlID() == id);
		}
	}

	/**
	 * デバッグ用子ウィンドウ一覧表示.
	 */
	protected void listID() {
		List list = wnd.enumChildWindows();
		for (int i = 0; i < list.size(); i++) {
			JWnd w = (JWnd) list.get(i);
			int id = w.GetDlgCtrlID();
			System.out.println(Long.toHexString(w.GetSafeHwnd()) + ":"
					+ Integer.toHexString(id) + "(" + id + ")" + ":"
					+ w.getClassName() + "/" + w.GetWindowText());
		}
	}

	/**
	 * エディットボックス文字列取得.
	 * 
	 * @param edit
	 *            エディットボックス
	 * @return エディットボックス内の文字列
	 */
	protected String getEditText(JWnd edit) {
		try {
			// x return wnd.GetDlgItemText(edit.GetDlgCtrlID());
			int len = edit.SendMessage(WM_GETTEXTLENGTH, 0, 0) + 1;
			return edit.SendMessageGetString(WM_GETTEXT, len, len * 2);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * エディットボックス文字列設定.
	 * 
	 * @param edit
	 *            エディットボックス
	 * @param text
	 *            文字列
	 * @return 成功した場合、true
	 */
	protected boolean setEditText(JWnd edit, String text) {
		try {
			// SetWindowTextは別プロセスのコントロールには使えない
			// return edit.SetWindowText(text);

			// x return wnd.SetDlgItemText(edit.GetDlgCtrlID(), text);

			edit.SendMessage(WM_SETTEXT, 0, text);

			// for (int i = 0; i < text.length(); i++) {
			// edit.SendMessage(WM_IME_CHAR, text.charAt(i), 0);
			// }
			return true;
		} catch (RuntimeException e) {
			// e.printStackTrace();
		}
		return false;
	}

	/**
	 * ボタンクリック.
	 * <p>
	 * （ボタンのIDが大きい場合は）ダイアログにフォーカスが当たっている必要があるっぽいので、事前に{@link #setFocus()}を呼んでおくこと。
	 * </p>
	 * 
	 * @param btn
	 *            ボタン
	 * @return 成功した場合、true
	 */
	protected boolean click(JWnd btn) {
		try {
			// 以下の3種類のどの方法でも大丈夫そう
			// ・BM_CLICKはWM_LBUTTONDOWNとWM_LBUTTONUPを発生させる
			// ・最終的にWM_COMMANDが発生する

			btn.PostMessage(BM_CLICK, 0, 0);

			// int x = 0, y = 0;
			// int lpos = (y << 16) | x;
			// btn.PostMessage(WM_LBUTTONDOWN, MK_LBUTTON, lpos);
			// btn.PostMessage(WM_LBUTTONUP, 0, lpos);

			// int id = btn.GetDlgCtrlID();
			// int lparam = (int) btn.GetSafeHwnd();
			// wnd.PostMessage(WM_COMMAND, (BN_CLICKED << 16) | id, lparam);

			return true;
		} catch (RuntimeException e) {
			// e.printStackTrace();
		}
		return false;
	}
}
