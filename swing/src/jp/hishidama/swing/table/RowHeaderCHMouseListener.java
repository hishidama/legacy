package jp.hishidama.swing.table;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SwingUtilities;
import javax.swing.table.TableModel;

/**
 * 行ヘッダーの列ヘッダーのマウスリスナー.
 * <p>
 * 行ヘッダーの上部のタイトルがクリックされた際にデータテーブルのソートを解除する。
 * </p>
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/java/swing/JTable.html"
 *         >ひしだま</a>
 * @since 2009.03.13
 * @see RowHeaderTable
 */
public class RowHeaderCHMouseListener extends MouseAdapter {

	protected JTable dataTable;

	/**
	 * コンストラクター
	 *
	 * @param dataTable
	 *            データテーブル
	 */
	public RowHeaderCHMouseListener(JTable dataTable) {
		this.dataTable = dataTable;
	}

	/**
	 * {@inheritDoc} ソートを解除する。
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e)) {
			int cc = e.getClickCount();
			if (cc == 1) {
				RowSorter<? extends TableModel> sort = dataTable.getRowSorter();
				if (sort != null) {
					sort.setSortKeys(null); // ソートを解除する
					e.consume();
				}
			}
		}
	}
}