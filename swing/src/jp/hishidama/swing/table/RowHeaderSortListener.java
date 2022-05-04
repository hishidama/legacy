package jp.hishidama.swing.table;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.RowSorter;
import javax.swing.event.RowSorterEvent;
import javax.swing.event.RowSorterListener;
import javax.swing.table.TableModel;

/**
 * 行ヘッダーのRowSorterListener.
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/java/swing/JTable.html"
 *         >ひしだま</a>
 * @since 2009.03.13
 * @version 2009.04.21 dataTableのRowSorterが後から変更された場合に対応
 * @see RowHeaderTable
 */
public class RowHeaderSortListener implements RowSorterListener,
		PropertyChangeListener {

	protected RowHeaderTable table;

	/**
	 * コンストラクター
	 *
	 * @param table
	 *            行ヘッダーJTable
	 */
	public RowHeaderSortListener(RowHeaderTable table) {
		this.table = table;
	}

	@Override
	public void sorterChanged(RowSorterEvent e) {
		table.revalidate();
		table.repaint();
	}

	/**
	 * dataTableのrowSorterが変更された時に呼ばれる。
	 *
	 * @since 2009.04.21
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		RowSorter<? extends TableModel> sort = table.dataTable.getRowSorter();
		if (sort != null) {
			sort.removeRowSorterListener(this);
			sort.addRowSorterListener(this);
		}
	}
}