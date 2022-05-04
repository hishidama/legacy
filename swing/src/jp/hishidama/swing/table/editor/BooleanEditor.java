package jp.hishidama.swing.table.editor;

import java.awt.Color;
import java.awt.Component;
import java.lang.reflect.Method;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.border.LineBorder;

/**
 * booleanエディター.
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/java/swing/JTable.html"
 *         >ひしだま</a>
 * @since 2009.09.27
 */
public class BooleanEditor extends DefaultCellEditor {
	private static final long serialVersionUID = 3423822347276965549L;

	protected Object dvalue;

	protected Class<?> type;

	/** コンストラクター. */
	public BooleanEditor() {
		super(new JCheckBox());
		getComponent().setHorizontalAlignment(JCheckBox.CENTER);
	}

	@Override
	public JCheckBox getComponent() {
		return (JCheckBox) super.getComponent();
	}

	/**
	 * 編集開始時に呼ばれる.
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
		this.dvalue = null; // 初期化
		getComponent().setBorder(new LineBorder(Color.black)); // 最初は黒枠

		Boolean selected;
		if (value == null) {
			selected = false;
			type = null;
		} else {
			type = value.getClass();
			if (value instanceof Boolean) {
				selected = (Boolean) value;
			} else if (value instanceof Number) {
				selected = ((Number) value).intValue() != 0;
			} else {
				selected = Boolean.parseBoolean(value.toString());
			}
		}

		return super.getTableCellEditorComponent(table, selected, isSelected,
				row, column);
	}

	/**
	 * 編集完了時に呼ばれる.
	 * <p>
	 * super.stopCellEditing()を呼び出すと正常終了扱いとなる模様。
	 * </p>
	 *
	 * @return trueの場合、正常終了（と思われるが、実際には何を返しても無関係っぽい）
	 */
	@Override
	public boolean stopCellEditing() {
		Object d = null;

		Boolean b = (Boolean) super.getCellEditorValue(); // 編集された値の取得
		if (type == null) {
			d = b;
		} else if (b != null) {
			try {
				if (type == Boolean.class || type == Object.class) {
					d = b;
				} else if (type == String.class) {
					d = b.toString();
				} else {
					Method m = type.getMethod("valueOf", String.class);
					if (b.booleanValue()) {
						d = m.invoke(null, "1");
					} else {
						d = m.invoke(null, "0");
					}
				}
			} catch (Exception e) {
				// エラー時は赤枠を表示する
				getComponent().setBorder(new LineBorder(Color.red));
				return false;
			}
		}

		this.dvalue = d; // 返す値(編集された値)を保持しておく

		return super.stopCellEditing();
		// このsuper.stopCellEditing()の中でgetCellEditorValue()が呼ばれ、
		// JTable#setValueAt()によって値がセットされ、
		// 編集用コンポーネントが消され、
		// 編集が完了する
	}

	/**
	 * 入力された値(加工済み)を返す.
	 *
	 * @return 編集された値
	 */
	@Override
	public Object getCellEditorValue() {
		return dvalue;
	}
}
