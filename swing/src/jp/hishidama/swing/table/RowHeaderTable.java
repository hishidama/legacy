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
 * �s�w�b�_�[��JTable.
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/java/swing/JTable.html"
 *         >�Ђ�����</a>
 * @since 2009.04.04
 * @version 2009.04.19 �|�b�v�A�b�v���j���[��dataTable�Ɠ������̂��g���悤�C��
 * @version 2009.04.21 dataTable��RowSorter���ォ��ύX���ꂽ�ꍇ�ɑΉ�
 */
public class RowHeaderTable extends JTable implements PropertyChangeListener {
	private static final long serialVersionUID = 5371347964805024090L;

	protected JTable dataTable;
	protected RowHeaderSortListener sortListener;

	/**
	 * �s�w�b�_�[�e�[�u�� �R���X�g���N�^�[
	 *
	 * @param dataTable
	 *            �f�[�^�pJTable
	 * @param name
	 *            �s�w�b�_�[�̗񌩏o��
	 * @param width
	 *            �s�w�b�_�[�̕�
	 */
	public RowHeaderTable(JTable dataTable, String name, int width) {

		super(new RowHeaderDataModel(dataTable), null,
				new RowHeaderSelectionModel(dataTable));
		this.dataTable = dataTable;

		// �B��̗�
		{
			TableColumn tc = new TableColumn(0, width);
			// �Z���̃����_���[
			DefaultTableCellRenderer r = new DefaultTableCellRenderer();
			r.setHorizontalAlignment(SwingConstants.CENTER);
			r.setBackground(this.getTableHeader().getBackground());
			tc.setCellRenderer(r);

			tc.setHeaderValue(name);
			tc.setResizable(false);
			addColumn(tc);
		}

		// ��w�b�_�[
		{
			JTableHeader h = super.getTableHeader();
			// h.setEnabled(false);
			h.setReorderingAllowed(false);
			h.addMouseListener(new RowHeaderCHMouseListener(dataTable));
		}

		// �\�[�^�[
		sortListener = new RowHeaderSortListener(this);
		dataTable.addPropertyChangeListener("rowSorter", sortListener);
		RowSorter<? extends TableModel> sort = dataTable.getRowSorter();
		if (sort != null) {
			sort.addRowSorterListener(sortListener);
		}

		// �|�b�v�A�b�v���j���[
		propertyChange(null);
		dataTable.addPropertyChangeListener("componentPopupMenu", this);
	}

	/**
	 * JScrollPane�ɓ��C���X�^���X��o�^����B
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

	/** �t�H�[�J�X�̓f�[�^�e�[�u���ɓn���B */
	@Override
	public void requestFocus() {
		dataTable.requestFocus();
	}

	/** �t�H�[�J�X�̓f�[�^�e�[�u���ɓn���B */
	@Override
	public boolean requestFocus(boolean temporary) {
		return dataTable.requestFocus(temporary);
	}

	/** �t�H�[�J�X�̓f�[�^�e�[�u���ɓn���B */
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
