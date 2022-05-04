package jp.hishidama.swing.table;

import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 * �s�w�b�_�[��TableModel.
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/java/swing/JTable.html"
 *         >�Ђ�����</a>
 * @since 2009.03.13
 * @see RowHeaderTable
 */
public class RowHeaderDataModel extends DefaultTableModel implements
		TableModelListener {
	private static final long serialVersionUID = 9222112264558601539L;

	protected JTable dataTable;

	/**
	 * �R���X�g���N�^�[
	 *
	 * @param dataTable
	 *            �f�[�^�e�[�u��
	 */
	public RowHeaderDataModel(JTable dataTable) {
		this.dataTable = dataTable;

		TableModel dataModel = dataTable.getModel();
		dataModel.removeTableModelListener(this);
		dataModel.addTableModelListener(this);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return Integer.class;
	}

	@Override
	public Object getValueAt(int row, int column) {
		return dataTable.convertRowIndexToModel(row) + 1;
	}

	@Override
	public void setValueAt(Object aValue, int row, int column) {
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

	@Override
	public int getRowCount() {
		if (dataTable != null) {
			return dataTable.getRowCount();
		}
		return 0;
	}

	/*
	 * ���X�i�[�̃��\�b�h�Ȃ̂ŁA�f�[�^�pTableModel�ɕύX���������Ƃ��ɌĂ΂��B
	 */
	@Override
	public void tableChanged(TableModelEvent e) {
		switch (e.getType()) {
		case TableModelEvent.INSERT: // �s�ǉ�
		case TableModelEvent.DELETE: // �s�폜
			fireTableChanged(e);
			break;
		default:
			// System.out.println("tableChanged:" + e.getType());
			break;
		}
	}
}