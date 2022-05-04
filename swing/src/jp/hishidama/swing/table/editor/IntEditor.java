package jp.hishidama.swing.table.editor;

import java.awt.Color;
import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.border.LineBorder;

import jp.hishidama.swing.undo.UndoTextField;

/**
 * intエディター.
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/java/swing/JTable.html"
 *         >ひしだま</a>
 * @since 2009.09.27
 */
public class IntEditor extends DefaultCellEditor {
	private static final long serialVersionUID = -8368795944481669400L;

	protected Integer dvalue;

	/** コンストラクター. */
	public IntEditor() {
		super(new UndoTextField());
		getComponent().setHorizontalAlignment(UndoTextField.RIGHT);
	}

	@Override
	public UndoTextField getComponent() {
		return (UndoTextField) super.getComponent();
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

		String str = (value == null) ? "" : value.toString();
		UndoTextField utf = (UndoTextField) super.getTableCellEditorComponent(
				table, str, isSelected, row, column);
		utf.clearUndoEdit();
		return utf;
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
		Integer d = null;

		String str = (String) super.getCellEditorValue(); // 編集された値の取得
		if (str != null && !str.isEmpty()) {
			try {
				d = Integer.valueOf(str);
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
