package jp.hishidama.swing.table.editor;

import java.awt.Color;
import java.awt.Component;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.DefaultCellEditor;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

/**
 * 日付エディター.
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/java/swing/JTable.html"
 *         >ひしだま</a>
 * @since 2009.03.22
 */
public class DateEditor extends DefaultCellEditor {
	private static final long serialVersionUID = 7159257623625914894L;

	private DateFormat formatter;
	protected Date dvalue;

	/** コンストラクター. */
	public DateEditor() {
		super(new JTextField());
	}

	/**
	 * コンストラクター.
	 *
	 * @param pattern
	 *            日付パターン
	 */
	public DateEditor(String pattern) {
		super(new JTextField());
		formatter = new SimpleDateFormat(pattern);
	}

	/**
	 * フォーマッター設定.
	 *
	 * @param df
	 *            日付フォーマッター
	 */
	public void setFormatter(DateFormat df) {
		formatter = df;
	}

	/**
	 * フォーマッター取得.
	 *
	 * @return 日付フォーマッター
	 */
	public DateFormat getFormatter() {
		if (formatter == null) {
			formatter = DateFormat.getDateInstance();
			formatter.setLenient(false);
		}
		return formatter;
	}

	/**
	 * 編集開始時に呼ばれる
	 *
	 * @param table
	 *            対象テーブル
	 * @param value
	 *            編集対象データ（初期値）
	 * @param isSelected
	 * @param row
	 *            データのある行番号
	 * @param column
	 *            データのある列番号
	 * @return 描画用コンポーネント
	 */
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		this.dvalue = null;
		((JComponent) getComponent()).setBorder(new LineBorder(Color.black));

		// row,columnは、TableColumnModelの体系
		// System.out.printf("getComponent(%b,%d,%d)+++%s %s%n", isSelected,
		// row, column, value.getClass(), value);

		DateFormat f = getFormatter();
		String str = (value == null) ? "" : f.format(value);
		return super.getTableCellEditorComponent(table, str, isSelected, row,
				column);
	}

	/**
	 * 編集完了時に呼ばれる.
	 * <p>
	 * super.stopCellEditing()を呼び出すと正常終了扱いとなる。
	 * </p>
	 *
	 * @return trueの場合、正常終了（と思われるが、実際には何を返しても無関係っぽい）
	 */
	@Override
	public boolean stopCellEditing() {
		Date d = null;

		String s = (String) super.getCellEditorValue();
		if (s != null && !s.isEmpty()) {
			DateFormat f = getFormatter();
			ParsePosition pos = new ParsePosition(0);
			d = f.parse(s, pos);
			if (pos.getErrorIndex() >= 0) {
				((JComponent) getComponent()).setBorder(new LineBorder(
						Color.red));
				return false;
			}
		}

		this.dvalue = d;
		return super.stopCellEditing();
	}

	/**
	 * 入力された値を返す.
	 *
	 * @return 編集された値
	 */
	@Override
	public Object getCellEditorValue() {
		return dvalue;
	}

	@Override
	public void cancelCellEditing() {
		// どうもこのメソッドは呼ばれない模様
		// System.out.println("cancelCellEditing:" + this);
		super.cancelCellEditing();
	}
}