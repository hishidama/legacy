package jp.hishidama.swing.table;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 * データ幅に応じて各列の幅を決定できるTableHeader.
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/java/swing/JTable.html"
 *         >ひしだま</a>
 * @since 2009.03.13
 */
public class FitHeader extends JTableHeader {
	private static final long serialVersionUID = -5014336688889079560L;

	protected boolean headFit;

	public FitHeader(TableColumnModel columnModel) {
		this(columnModel, true);
	}

	/**
	 * コンストラクター
	 *
	 * @param columnModel
	 * @param headFit
	 *            trueの場合、列ヘッダーの文字列も列幅計算に含める
	 */
	public FitHeader(TableColumnModel columnModel, boolean headFit) {
		super(columnModel);
		this.headFit = headFit;
	}

	/**
	 * @return trueの場合、列ヘッダーの文字列も列幅計算に含める
	 */
	public boolean isHeadFit() {
		return headFit;
	}

	@Override
	protected void processMouseEvent(MouseEvent e) {
		if (e.getID() == MouseEvent.MOUSE_CLICKED
				&& SwingUtilities.isLeftMouseButton(e)) {
			Cursor cur = this.getCursor();
			if (cur.getType() == Cursor.E_RESIZE_CURSOR) {
				int cc = e.getClickCount();
				if (cc % 2 == 1) {
					// シングルクリック
					// 無処理でリターンしない場合、ソート機能が働いてしまう
					return;
				} else {
					// ダブルクリック
					// BasicTableHeaderUI$MouseInputHandler#mouseClicked()
					// MouseInputHandler#getResizingColumn()
					Point pt = new Point(e.getX() - 3, e.getY());
					int vc = super.columnAtPoint(pt);
					if (vc >= 0) {
						sizeWidthToFitData(vc);
						e.consume();
						return;
					}
				}
			}
		}
		super.processMouseEvent(e);
	}

	/**
	 * 全列の幅をデータに合わせて設定する.
	 *
	 * @see #sizeWidthToFitData(int)
	 */
	public void sizeWidthToFitDataAll() {
		int cols = getColumnModel().getColumnCount();
		for (int i = 0; i < cols; i++) {
			sizeWidthToFitData(i);
		}
	}

	/**
	 * 指定列の幅をデータに合わせて設定する.
	 *
	 * @param vc
	 *            列番号
	 * @see #isHeadFit()
	 */
	public void sizeWidthToFitData(int vc) {
		JTable table = this.getTable();
		TableColumn tc = table.getColumnModel().getColumn(vc);

		int max = 0;
		if (headFit) {
			TableCellRenderer hr = tc.getHeaderRenderer();
			if (hr == null) {
				hr = getDefaultRenderer();
			}
			Component c = hr.getTableCellRendererComponent(table, tc
					.getHeaderValue(), false, false, 0, vc);
			max = c.getPreferredSize().width;
		}

		int vrows = table.getRowCount();
		for (int i = 0; i < vrows; i++) {
			TableCellRenderer r = table.getCellRenderer(i, vc);
			Object value = table.getValueAt(i, vc);
			Component c = r.getTableCellRendererComponent(table, value, false,
					false, i, vc);
			int w = c.getPreferredSize().width;
			if (max < w) {
				max = w;
			}
		}
		tc.setPreferredWidth(max + 1);
	}
}