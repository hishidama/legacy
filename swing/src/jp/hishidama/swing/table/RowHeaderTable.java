package jp.hishidama.swing.table;

import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

/**
 * 行ヘッダーのJTable.
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/java/swing/JTable.html"
 *         >ひしだま</a>
 * @since 2009.04.04
 * @version 2009.04.19 ポップアップメニューもdataTableと同じものを使うよう修正
 * @version 2009.04.21 dataTableのRowSorterが後から変更された場合に対応
 */
public class RowHeaderTable extends JTable implements PropertyChangeListener {
	private static final long serialVersionUID = 5371347964805024090L;

	protected JTable dataTable;
	protected RowHeaderSortListener sortListener;

	/**
	 * 行ヘッダーテーブル コンストラクター
	 *
	 * @param dataTable
	 *            データ用JTable
	 * @param name
	 *            行ヘッダーの列見出し
	 * @param width
	 *            行ヘッダーの幅
	 */
	public RowHeaderTable(JTable dataTable, String name, int width) {

		super(new RowHeaderDataModel(dataTable), null,
				new RowHeaderSelectionModel(dataTable));
		this.dataTable = dataTable;

		// 唯一の列
		{
			TableColumn tc = new TableColumn(0, width);
			// セルのレンダラー
			DefaultTableCellRenderer r = new DefaultTableCellRenderer();
			r.setHorizontalAlignment(SwingConstants.CENTER);
			r.setBackground(this.getTableHeader().getBackground());
			tc.setCellRenderer(r);

			tc.setHeaderValue(name);
			tc.setResizable(false);
			addColumn(tc);
		}

		// 列ヘッダー
		{
			JTableHeader h = super.getTableHeader();
			// h.setEnabled(false);
			h.setReorderingAllowed(false);
			h.addMouseListener(new RowHeaderCHMouseListener(dataTable));
		}

		// ソーター
		sortListener = new RowHeaderSortListener(this);
		dataTable.addPropertyChangeListener("rowSorter", sortListener);
		RowSorter<? extends TableModel> sort = dataTable.getRowSorter();
		if (sort != null) {
			sort.addRowSorterListener(sortListener);
		}

		// ポップアップメニュー
		propertyChange(null);
		dataTable.addPropertyChangeListener("componentPopupMenu", this);
	}

	/**
	 * JScrollPaneに当インスタンスを登録する。
	 *
	 * @param scroll
	 */
	public void installTo(JScrollPane scroll) {

		scroll.setRowHeaderView(this);

		scroll.setCorner(JScrollPane.UPPER_LEFT_CORNER, this.getTableHeader());

		Dimension sz = new Dimension(this.getPreferredSize().width, dataTable
				.getPreferredSize().height);
		scroll.getRowHeader().setPreferredSize(sz);
	}

	/** フォーカスはデータテーブルに渡す。 */
	@Override
	public void requestFocus() {
		dataTable.requestFocus();
	}

	/** フォーカスはデータテーブルに渡す。 */
	@Override
	public boolean requestFocus(boolean temporary) {
		return dataTable.requestFocus(temporary);
	}

	/** フォーカスはデータテーブルに渡す。 */
	@Override
	public boolean requestFocusInWindow() {
		return dataTable.requestFocusInWindow();
	}

	// PropertyChangeListener

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		JPopupMenu pmenu = dataTable.getComponentPopupMenu();
		setComponentPopupMenu(pmenu);
	}
}
