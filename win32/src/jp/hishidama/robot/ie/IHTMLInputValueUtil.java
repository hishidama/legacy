package jp.hishidama.robot.ie;

import jp.hishidama.robot.IERobot;
import jp.hishidama.win32.JWnd;
import jp.hishidama.win32.api.WinUserConst;
import jp.hishidama.win32.com.ComPtr;
import jp.hishidama.win32.mshtml.*;

/**
 * テキストボックス支援クラス.
 * <p>
 * {@link IHTMLInputElement}のテキストの入出力操作をするクラスです。<br> →<a target="hishidama"
 * href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">使用例</a>
 * </p>
 * <p>
 * コンストラクターの引数に指定するHTML要素は、呼び出し元で破棄して下さい。<br>
 * {@link jp.hishidama.robot.IERobot#getInputById(String)}によって当インスタンスを取得した場合はIERobotが破棄します。
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">ひしだま</a>
 * @since 2007.10.28
 */
public class IHTMLInputValueUtil {

	protected IERobot ie;

	protected IHTMLInputElement elem;

	/**
	 * コンストラクター.
	 * 
	 * @param ie
	 *            IERobot
	 * @param input
	 *            input要素
	 * @see IERobot#getInputById(String)
	 * @see IERobot#getInputByName(String, int)
	 */
	public IHTMLInputValueUtil(IERobot ie, IHTMLInputElement input) {
		this.ie = ie;
		this.elem = input;
	}

	/**
	 * 使用可否取得.
	 * 
	 * @return 使用不可の場合、true
	 */
	public boolean isDisabled() {
		return elem.isDisabled();
	}

	/**
	 * 使用可否設定.
	 * 
	 * @param b
	 *            true：使用不可
	 */
	public void setDisabled(boolean b) {
		elem.setDisabled(b);
	}

	/**
	 * 読込可否取得.
	 * 
	 * @return 読み込み専用のとき、true
	 */
	public boolean isReadOnly() {
		return elem.getReadOnly();
	}

	/**
	 * 読込可否設定.
	 * 
	 * @param b
	 *            true：読み込み専用
	 */
	public void setReadOnly(boolean b) {
		elem.setReadOnly(b);
	}

	/**
	 * 値取得.
	 * 
	 * @return 値
	 */
	public String getValue() {
		return elem.getValue();
	}

	/**
	 * 値設定.
	 * <p>
	 * value属性に直接値を書き込む。したがってtype="ファイル"等では使えない。<br>
	 * それ以外のtypeでは、使用不可状態であっても設定できる。
	 * </p>
	 * 
	 * @param value
	 *            値
	 */
	public void setValue(String value) {
		elem.setValue(value);
	}

	/**
	 * 値送信.
	 * <p>
	 * 外部から値を送信する。type="ファイル"にも入力可能。ただしフォーカスを変更してしまうので注意。<br>
	 * 対象がウィンドウ上で入力可能な状態でないとエラーになる。すなわち、disableだったりtype="hidden"には使用できない。<br>
	 * 対象が読み込み専用の場合、エラーにならないが実際には反映されない。
	 * </p>
	 * 
	 * @param value
	 *            値
	 * @exception RuntimeException
	 *                入力できない時
	 */
	public void sendValue(String value) {
		final String ERR_MSG = "入力不可";

		IHTMLTxtRange r = null;
		IHTMLElement ae = null;
		try {
			r = elem.createTextRange();
			r.select(); // 現在入力されているものを選択（これによりフォーカスも当たる）

			// アクティブな要素を取得し、それが対象と一致しているか確認
			// disableな場合やtype=hiddenは一致しない
			IHTMLDocument doc = ie.getDocument();
			ae = doc.getActiveElement();
			if (ae == null || ae.getClass() != elem.getClass()) {
				throw new RuntimeException(ERR_MSG);
			}
			IHTMLTxtRange ar = ((IHTMLInputElement) ae).createTextRange();
			if (!r.isEqual(ar)) {
				throw new RuntimeException(ERR_MSG);
			}

			// クライアント領域のHWND取得
			JWnd wnd = ie.getCliendWnd();

			// 一文字ずつ送信
			for (int i = 0; i < value.length(); i++) {
				char c = value.charAt(i);
				wnd.SendMessage(WinUserConst.WM_IME_CHAR, c, 0);
			}
		} finally {
			ComPtr.dispose(r);
			ComPtr.dispose(ae);
		}
	}

	/**
	 * input要素取得.
	 * <p>
	 * 当メソッドによって取得したHTML要素は、コンストラクターを呼び出したオブジェクトによって破棄される。
	 * </p>
	 * 
	 * @return input要素
	 */
	public IHTMLInputElement getElement() {
		return elem;
	}
}
