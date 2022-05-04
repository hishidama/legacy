package jp.hishidama.robot.ie;

import jp.hishidama.win32.com.ComPtr;
import jp.hishidama.win32.com.Variant;
import jp.hishidama.win32.mshtml.*;

/**
 * コンボボックス・リストボックス支援クラス.
 * <p>
 * {@link IHTMLSelectElement}や{@link IHTMLOptionElement}のコンボボックス・リストボックスを操作するクラスです。<br> →<a
 * target="hishidama"
 * href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">使用例</a>
 * </p>
 * <p>
 * コンストラクターの引数に指定するHTML要素は、呼び出し元で（子要素も共に）破棄して下さい。<br>
 * {@link jp.hishidama.robot.IERobot#getSelectById(String)}によって当インスタンスを取得した場合はIERobotが破棄します。
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">ひしだま</a>
 * @since 2007.10.24
 */
public class IHTMLSelectUtil {

	protected IHTMLSelectElement sel;

	/**
	 * コンストラクター.
	 * 
	 * @param select
	 *            select要素
	 * @see jp.hishidama.robot.IERobot#getSelectById(String)
	 * @see jp.hishidama.robot.IERobot#getSelectByName(String, int)
	 */
	public IHTMLSelectUtil(IHTMLSelectElement select) {
		this.sel = select;
	}

	/**
	 * 表示行数取得.
	 * <p>
	 * 1のときコンボボックス、それより大きいときリストボックス。
	 * </p>
	 * 
	 * @return 行数
	 */
	public int getSize() {
		return sel.getSize();
	}

	/**
	 * 表示行数設定.
	 * 
	 * @param size
	 *            行数
	 */
	public void setSize(int size) {
		sel.setSize(size);
	}

	/**
	 * 複数行選択可否取得.
	 * 
	 * @return 複数行選択可能のとき、true
	 */
	public boolean isMultiple() {
		return sel.getMultiple();
	}

	/**
	 * 複数行選択可否設定.
	 * 
	 * @param b
	 *            true：複数行選択
	 */
	public void setMultiple(boolean b) {
		sel.setMultiple(b);
	}

	/**
	 * 選択行番号取得.
	 * <p>
	 * 選択されている行のindexを返す。<br>
	 * 複数行選択されている場合、先頭のindexが返る模様。
	 * </p>
	 * 
	 * @return index
	 * @see #isSelected(int)
	 */
	public int getSelectedIndex() {
		return sel.getSelectedIndex();
	}

	/**
	 * 選択値取得.
	 * <p>
	 * 選択されている行の値を返す。<br>
	 * 単独行選択でサブミットした際に送信されるのは、たぶんこの値。
	 * </p>
	 * 
	 * @return 値
	 */
	public String getSelectedValue() {
		return sel.getValue();
	}

	/**
	 * 単一行選択.
	 * <p>
	 * 行を選択する。<br>
	 * 複数行選択の場合、選んだ行以外は選択が解除される。
	 * </p>
	 * 
	 * @param index
	 *            index
	 * @see #getSelectedIndex()
	 */
	public void setSelected(int index) {
		sel.setSelectedIndex(index);
	}

	/**
	 * 値設定.
	 * 
	 * @param value
	 *            値
	 * @see #getSelectedValue()
	 */
	public void setSelected(String value) {
		sel.setValue(value);
	}

	/**
	 * 選択肢追加.
	 * 
	 * @param index
	 *            追加位置
	 * @param text
	 *            表示されるテキスト
	 * @param value
	 *            値
	 * @param defaultSelected
	 *            デフォルト選択有無
	 * @param selected
	 *            選択有無
	 */
	public void add(int index, String text, String value,
			boolean defaultSelected, boolean selected) {
		IHTMLDocument doc = null;
		IHTMLOptionElement opt = null;
		try {
			doc = sel.getDocument();
			IHTMLWindow win = doc.getParentWindow();
			opt = IHTMLOptionElement.create(win, text, value, defaultSelected,
					selected);
			sel.add(opt, new Variant(index));
			opt.setSelected(selected);
		} finally {
			ComPtr.dispose(opt);
			ComPtr.dispose(doc);
		}
	}

	/**
	 * 選択肢削除.
	 * 
	 * @param index
	 *            削除位置
	 */
	public void remove(int index) {
		sel.remove(index);
	}

	/**
	 * select要素取得.
	 * <p>
	 * 当メソッドによって取得したHTML要素は、コンストラクターを呼び出したオブジェクトによって破棄される。
	 * </p>
	 * 
	 * @return select要素
	 */
	public IHTMLSelectElement getElement() {
		return sel;
	}

	/**
	 * 個数取得.
	 * 
	 * @return 選択肢の個数
	 */
	public int size() {
		return sel.getLength();
	}

	/**
	 * option要素取得.
	 * <p>
	 * 当メソッドによって取得したHTML要素は、コンストラクターを呼び出したオブジェクトによって連動して破棄される。
	 * </p>
	 * 
	 * @param index
	 *            index
	 * @return option要素
	 */
	public IHTMLOptionElement getElement(int index) {
		return sel.item(index);
	}

	/**
	 * 明細値取得.
	 * 
	 * @param index
	 *            index
	 * @return 値
	 */
	public String getValue(int index) {
		IHTMLOptionElement o = sel.item(index);
		try {
			return o.getValue();
		} finally {
			ComPtr.dispose(o);
		}
	}

	/**
	 * 明細テキスト取得.
	 * 
	 * @param index
	 *            index
	 * @return 選択肢として表示されている文言
	 */
	public String getText(int index) {
		IHTMLOptionElement o = sel.item(index);
		try {
			return o.getText();
		} finally {
			ComPtr.dispose(o);
		}
	}

	/**
	 * 選択有無取得.
	 * 
	 * @param index
	 *            index
	 * @return 選択されている場合、true
	 */
	public boolean isSelected(int index) {
		IHTMLOptionElement o = sel.item(index);
		try {
			return o.getSelected();
		} finally {
			ComPtr.dispose(o);
		}
	}

	/**
	 * 選択有無設定.
	 * 
	 * @param index
	 *            index
	 * @param b
	 *            選択有無
	 */
	public void setSelected(int index, boolean b) {
		IHTMLOptionElement o = sel.item(index);
		try {
			o.setSelected(b);
		} finally {
			ComPtr.dispose(o);
		}
	}

}
