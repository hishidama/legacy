package jp.hishidama.swing.table;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.RowSorter;
import javax.swing.event.RowSorterEvent;
import javax.swing.event.RowSorterListener;
import javax.swing.table.TableModel;

/**
 * �s�w�b�_�[��RowSorterListener.
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/java/swing/JTable.html"
 *         >�Ђ�����</a>
 * @since 2009.03.13
 * @version 2009.04.21 dataTable��RowSorter���ォ��ύX���ꂽ�ꍇ�ɑΉ�
 * @see RowHeaderTable
 */
public class RowHeaderSortListener implements RowSorterListener,
		PropertyChangeListener {

	protected RowHeaderTable table;

	/**
	 * �R���X�g���N�^�[
	 *
	 * @param table
	 *            �s�w�b�_�[JTable
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
	 * dataTable��rowSorter���ύX���ꂽ���ɌĂ΂��B
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