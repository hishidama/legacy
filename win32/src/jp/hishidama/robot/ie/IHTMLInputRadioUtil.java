package jp.hishidama.robot.ie;

import java.util.ArrayList;
import java.util.List;

import jp.hishidama.win32.mshtml.IHTMLInputElement;

/**
 * ラジオボタン支援クラス.
 * <p>
 * {@link IHTMLInputElement}のラジオボタンを操作するクラスです。<br> →<a target="hishidama"
 * href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">使用例</a>
 * </p>
 * <p>
 * コンストラクターの引数に指定するHTML要素は、呼び出し元で破棄して下さい。<br>
 * {@link jp.hishidama.robot.IERobot#getRadioByName(String)}によって当インスタンスを取得した場合はIERobotが破棄します。
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">ひしだま</a>
 * @since 2007.10.24
 */
public class IHTMLInputRadioUtil {

	protected List list;

	/**
	 * コンストラクター.
	 * 
	 * @param l
	 *            {@link IHTMLInputElement}のリスト
	 * @see jp.hishidama.robot.IERobot#getRadioByName(String)
	 */
	public IHTMLInputRadioUtil(List l) {
		list = new ArrayList(l.size());
		for (int i = 0; i < l.size(); i++) {
			try {
				IHTMLInputElement r = (IHTMLInputElement) l.get(i);
				if ("RADIO".equalsIgnoreCase(r.getType())) {
					list.add(r);
				}
			} catch (RuntimeException e) {
			}
		}
	}

	/**
	 * 個数取得.
	 * 
	 * @return ラジオボタンの個数
	 */
	public int size() {
		return list.size();
	}

	/**
	 * 値取得.
	 * 
	 * @param i
	 *            index
	 * @return 値
	 */
	public String getValue(int i) {
		IHTMLInputElement r = getElement(i);
		return r.getValue();
	}

	/**
	 * チェック値取得.
	 * 
	 * @return チェックされているラジオボタンの値（無い場合、null）
	 */
	public String getCheckedValue() {
		IHTMLInputElement r = getCheckedElement();
		if (r != null) {
			return r.getValue();
		}
		return null;
	}

	/**
	 * チェック値設定.
	 * <p>
	 * trueをセットすると、今までtrueだったチェックボックスはfalseになる。
	 * </p>
	 * 
	 * @param i
	 *            index
	 * @param b
	 *            チェック有無
	 */
	public void setChecked(int i, boolean b) {
		IHTMLInputElement r = getElement(i);
		r.setChecked(b);
	}

	/**
	 * チェック値設定.
	 * <p>
	 * trueをセットすると、今までtrueだったラジオボタンはfalseになる。
	 * </p>
	 * 
	 * @param value
	 *            値
	 * @param b
	 *            チェック有無
	 * @return 成功した場合、true
	 */
	public boolean setChecked(String value, boolean b) {
		for (int i = 0; i < size(); i++) {
			IHTMLInputElement r = getElement(i);
			if (value.equals(r.getValue())) {
				r.setChecked(b);
				return true;
			}
		}
		return false;
	}

	/**
	 * input要素取得.
	 * <p>
	 * 当メソッドによって取得したHTML要素は、コンストラクターを呼び出したオブジェクトによって破棄される。
	 * </p>
	 * 
	 * @param i
	 *            index
	 * @return input要素
	 */
	public IHTMLInputElement getElement(int i) {
		return (IHTMLInputElement) list.get(i);
	}

	/**
	 * チェックされたinput要素取得.
	 * 
	 * @return input要素（無い場合、null）
	 */
	public IHTMLInputElement getCheckedElement() {
		for (int i = 0; i < size(); i++) {
			IHTMLInputElement r = getElement(i);
			if (r.getChecked()) {
				return r;
			}
		}
		return null;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer(128);
		sb.append(getClass());
		sb.append("\' {");
		for (int i = 0; i < size(); i++) {
			IHTMLInputElement r = getElement(i);
			if (i != 0) {
				sb.append(", ");
			}
			sb.append('\"');
			sb.append(r.getValue());
			sb.append('\"');
			if (r.getChecked()) {
				sb.append(" checked");
			}
		}
		sb.append("}");
		return sb.toString();
	}

}
