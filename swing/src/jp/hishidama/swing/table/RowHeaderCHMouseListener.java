package jp.hishidama.swing.table;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SwingUtilities;
import javax.swing.table.TableModel;

/**
 * �s�w�b�_�[�̗�w�b�_�[�̃}�E�X���X�i�[.
 * <p>
 * �s�w�b�_�[�̏㕔�̃^�C�g�����N���b�N���ꂽ�ۂɃf�[�^�e�[�u���̃\�[�g����������B
 * </p>
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/java/swing/JTable.html"
 *         >�Ђ�����</a>
 * @since 2009.03.13
 * @see RowHeaderTable
 */
public class RowHeaderCHMouseListener extends MouseAdapter {

	protected JTable dataTable;

	/**
	 * �R���X�g���N�^�[
	 *
	 * @param dataTable
	 *            �f�[�^�e�[�u��
	 */
	public RowHeaderCHMouseListener(JTable dataTable) {
		this.dataTable = dataTable;
	}

	/**
	 * {@inheritDoc} �\�[�g����������B
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e)) {
			int cc = e.getClickCount();
			if (cc == 1) {
				RowSorter<? extends TableModel> sort = dataTable.getRowSorter();
				if (sort != null) {
					sort.setSortKeys(null); // �\�[�g����������
					e.consume();
				}
			}
		}
	}
}