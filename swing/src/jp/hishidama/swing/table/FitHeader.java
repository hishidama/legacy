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
 * �f�[�^���ɉ����Ċe��̕�������ł���TableHeader.
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/java/swing/JTable.html"
 *         >�Ђ�����</a>
 * @since 2009.03.13
 */
public class FitHeader extends JTableHeader {
	private static final long serialVersionUID = -5014336688889079560L;

	protected boolean headFit;

	public FitHeader(TableColumnModel columnModel) {
		this(columnModel, true);
	}

	/**
	 * �R���X�g���N�^�[
	 *
	 * @param columnModel
	 * @param headFit
	 *            true�̏ꍇ�A��w�b�_�[�̕�������񕝌v�Z�Ɋ܂߂�
	 */
	public FitHeader(TableColumnModel columnModel, boolean headFit) {
		super(columnModel);
		this.headFit = headFit;
	}

	/**
	 * @return true�̏ꍇ�A��w�b�_�[�̕�������񕝌v�Z�Ɋ܂߂�
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
					// �V���O���N���b�N
					// �������Ń��^�[�����Ȃ��ꍇ�A�\�[�g�@�\�������Ă��܂�
					return;
				} else {
					// �_�u���N���b�N
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
	 * �S��̕����f�[�^�ɍ��킹�Đݒ肷��.
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
	 * �w���̕����f�[�^�ɍ��킹�Đݒ肷��.
	 *
	 * @param vc
	 *            ��ԍ�
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