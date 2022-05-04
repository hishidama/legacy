package jp.hishidama.swing.table;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import jp.hishidama.swing.action.ExAction;

/**
 * �x���X�VJTable.
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/java/swing/JTable.html"
 *         >�Ђ�����</a>
 * @since 2009.03.29
 * @version 2009.10.01
 */
public class LazyTable extends ExTable {
	private static final long serialVersionUID = 1967024288947619406L;

	/** �Z���X�e�[�^�X�F�ǉ� */
	public static final int ADD = 1;
	/** �Z���X�e�[�^�X�F�ύX */
	public static final int CHG = 2;
	/** �Z���X�e�[�^�X�F�폜 */
	public static final int DEL = 4;

	/**
	 * �R���X�g���N�^�[.
	 */
	public LazyTable() {
		super();
	}

	/**
	 * �R���X�g���N�^�[.
	 *
	 * @param dm
	 * @param cm
	 * @param sm
	 */
	public LazyTable(LazyTableModel dm, TableColumnModel cm,
			ListSelectionModel sm) {
		super(dm, cm, sm);
	}

	/**
	 * �R���X�g���N�^�[.
	 *
	 * @param dm
	 * @param cm
	 */
	public LazyTable(LazyTableModel dm, TableColumnModel cm) {
		super(dm, cm);
	}

	/**
	 * �R���X�g���N�^�[.
	 *
	 * @param dm
	 */
	public LazyTable(LazyTableModel dm) {
		super(dm);
	}

	@Override
	protected TableModel createDefaultDataModel() {
		return new LazyTableModel();
	}

	@Override
	public LazyTableModel getModel() {
		return (LazyTableModel) super.getModel();
	}

	@Override
	protected void initializeLocalVars() {
		super.initializeLocalVars();

		initializeKeyAction();
	}

	protected void initializeKeyAction() {
		LazyAddAction aa = new LazyAddAction();
		setKeyAction(KeyStroke.getKeyStroke(KeyEvent.VK_INSERT, 0), aa);
		setKeyAction(KeyStroke.getKeyStroke(KeyEvent.VK_INSERT,
				KeyEvent.SHIFT_DOWN_MASK), aa);

		LazyDelAction da = new LazyDelAction();
		setKeyAction(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), da);

		if (false) {
			LazyCommitAction sa = new LazyCommitAction();
			setKeyAction(KeyStroke.getKeyStroke(KeyEvent.VK_S,
					KeyEvent.CTRL_DOWN_MASK), sa);

			LazyRollbackAction ra = new LazyRollbackAction();
			setKeyAction(KeyStroke.getKeyStroke(KeyEvent.VK_R,
					KeyEvent.CTRL_DOWN_MASK), ra);
		}
	}

	protected Color addColor = new Color(0x40, 0xe0, 0xff);
	protected Color chgColor = new Color(0xff, 0xff, 0x80);
	protected Color delColor = new Color(0xff, 0x80, 0x80);

	/**
	 * �ύX���̔w�i�F��ݒ肷��B
	 *
	 * @param add
	 *            �ǉ����w�i�F
	 * @param chg
	 *            �ύX���w�i�F
	 * @param del
	 *            �폜���w�i�F
	 */
	public void setBackground(Color add, Color chg, Color del) {
		addColor = add;
		chgColor = chg;
		delColor = del;
	}

	@Override
	public TableCellRenderer getCellRenderer(int row, int column) {
		TableCellRenderer r = super.getCellRenderer(row, column);
		changeCellRenderer(row, column, r);
		return r;
	}

	protected void changeCellRenderer(int row, int column, TableCellRenderer r) {
		if (r instanceof JComponent) {
			JComponent c = (JComponent) r;
			int type = getRowStatus(row);
			if ((type & DEL) != 0) {
				c.setBackground(delColor);
			} else if ((type & ADD) != 0) {
				c.setBackground(addColor);
			} else if ((getStatusAt(row, column) & CHG) != 0) {
				c.setBackground(chgColor);
			} else {
				c.setBackground(getBackground());
			}
		}
	}

	/**
	 * �s�X�e�[�^�X�擾.
	 *
	 * @param vr
	 *            �s�C���f�b�N�X
	 * @return �X�e�[�^�X�i{@link #CHG}���j
	 */
	public int getRowStatus(int vr) {
		return getModel().getRowStatus(convertRowIndexToModel(vr));
	}

	/**
	 * �X�e�[�^�X�擾.
	 *
	 * @param vr
	 *            �s�C���f�b�N�X
	 * @param vc
	 *            ��C���f�b�N�X
	 * @return �X�e�[�^�X�i{@link #CHG}���j
	 */
	public int getStatusAt(int vr, int vc) {
		return getModel().getStatusAt(convertRowIndexToModel(vr),
				convertColumnIndexToModel(vc));
	}

	/**
	 * �ǉ��A�N�V����.
	 * <p>
	 * �s��ǉ�����B
	 * </p>
	 */
	public class LazyAddAction extends ExAction {
		private static final long serialVersionUID = 7760245387919187532L;

		public LazyAddAction() {
			super();
		}

		public LazyAddAction(String name) {
			super(name);
		}

		public LazyAddAction(String name, Icon icon) {
			super(name, icon);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			beginUndoUpdate();
			try {
				int r = getSelectedRow();
				int s = r;
				if (r < 0) { // �I������Ă��Ȃ��Ƃ�
					if ((e.getModifiers() & ActionEvent.SHIFT_MASK) != 0) {
						r = 0;
					} else {
						r = getRowCount();
					}
				} else {
					if ((e.getModifiers() & ActionEvent.SHIFT_MASK) == 0) {
						// SHIFT�L�[��������Ă��Ȃ��Ƃ�
						r++;
					} else {
						s++;
					}
				}

				LazyTableModel model = getModel();
				int mr;
				if (r >= getRowCount()) {
					mr = r;
				} else {
					mr = convertRowIndexToModel(r);
				}
				Vector<Object> vec = new Vector<Object>(model.getColumnCount());
				for (int i = 0; i < model.getColumnCount(); i++) {
					vec.add(model.getDefaultValueAt(mr, i));
				}
				model.insertRow(mr, vec);
				model.setRowStatus(mr, ADD);

				getSelectionModel().setSelectionInterval(s, s);
			} finally {
				endUndoUpdate();
			}
		}
	}

	/**
	 * UNDO�\�ȍs�ǉ�.
	 *
	 * @param rowData
	 *            �s�f�[�^
	 * @since 2009.10.01
	 */
	public void addRowUndoable(Vector<Object> rowData) {
		beginUndoUpdate();
		try {
			LazyTableModel model = getModel();
			if (rowData == null) {
				rowData = new Vector<Object>(model.getColumnCount());
			}

			int mr = model.getRowCount();
			for (int i = rowData.size(); i < model.getColumnCount(); i++) {
				rowData.add(model.getDefaultValueAt(mr, i));
			}

			model.insertRow(mr, rowData);
			model.setRowStatus(mr, ADD);
		} finally {
			endUndoUpdate();
		}
	}

	/**
	 * �폜�A�N�V����.
	 * <p>
	 * �s���폜����B
	 * </p>
	 */
	public class LazyDelAction extends ExAction {
		private static final long serialVersionUID = 7353001704303178725L;

		public LazyDelAction() {
			super();
		}

		public LazyDelAction(String name) {
			super(name);
		}

		public LazyDelAction(String name, Icon icon) {
			super(name, icon);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			beginUndoUpdate();
			try {
				List<Integer> dlist = null;

				LazyTableModel model = getModel();
				int[] rs = getSelectedRows();
				for (int r : rs) {
					int mr = convertRowIndexToModel(r);
					if (model.getRowStatus(mr) == ADD) {
						// �V�K�ǉ����Ă����s�́A�}�[�N�����ɒ��ڍ폜����
						if (dlist == null) {
							dlist = new ArrayList<Integer>();
						}
						dlist.add(mr);
					} else {
						model.setRowStatus(mr, DEL);
					}
				}
				if (dlist != null) {
					Collections.sort(dlist);
					for (int i = dlist.size() - 1; i >= 0; i--) {
						model.removeRow(dlist.get(i));
					}
				}
			} finally {
				endUndoUpdate();
			}
		}
	}

	/**
	 * �R�~�b�g�A�N�V����.
	 * <p>
	 * �ύX���m�肷��B
	 * </p>
	 */
	public class LazyCommitAction extends ExAction {
		private static final long serialVersionUID = -6131563976413164547L;

		public LazyCommitAction() {
			super();
		}

		public LazyCommitAction(String name) {
			super(name);
		}

		public LazyCommitAction(String name, Icon icon) {
			super(name, icon);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			LazyTableModel model = getModel();
			model.commit();
		}
	}

	/**
	 * ���[���o�b�N�A�N�V����.
	 * <p>
	 * �ύX�����ɖ߂��B
	 * </p>
	 */
	public class LazyRollbackAction extends ExAction {
		private static final long serialVersionUID = 9071502699445820522L;

		public LazyRollbackAction() {
			super();
		}

		public LazyRollbackAction(String name) {
			super(name);
		}

		public LazyRollbackAction(String name, Icon icon) {
			super(name, icon);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			LazyTableModel model = getModel();
			model.rollback();
		}
	}
}
