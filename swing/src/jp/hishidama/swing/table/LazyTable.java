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
 * 遅延更新JTable.
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/java/swing/JTable.html"
 *         >ひしだま</a>
 * @since 2009.03.29
 * @version 2009.10.01
 */
public class LazyTable extends ExTable {
	private static final long serialVersionUID = 1967024288947619406L;

	/** セルステータス：追加 */
	public static final int ADD = 1;
	/** セルステータス：変更 */
	public static final int CHG = 2;
	/** セルステータス：削除 */
	public static final int DEL = 4;

	/**
	 * コンストラクター.
	 */
	public LazyTable() {
		super();
	}

	/**
	 * コンストラクター.
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
	 * コンストラクター.
	 *
	 * @param dm
	 * @param cm
	 */
	public LazyTable(LazyTableModel dm, TableColumnModel cm) {
		super(dm, cm);
	}

	/**
	 * コンストラクター.
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
	 * 変更時の背景色を設定する。
	 *
	 * @param add
	 *            追加時背景色
	 * @param chg
	 *            変更時背景色
	 * @param del
	 *            削除時背景色
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
	 * 行ステータス取得.
	 *
	 * @param vr
	 *            行インデックス
	 * @return ステータス（{@link #CHG}等）
	 */
	public int getRowStatus(int vr) {
		return getModel().getRowStatus(convertRowIndexToModel(vr));
	}

	/**
	 * ステータス取得.
	 *
	 * @param vr
	 *            行インデックス
	 * @param vc
	 *            列インデックス
	 * @return ステータス（{@link #CHG}等）
	 */
	public int getStatusAt(int vr, int vc) {
		return getModel().getStatusAt(convertRowIndexToModel(vr),
				convertColumnIndexToModel(vc));
	}

	/**
	 * 追加アクション.
	 * <p>
	 * 行を追加する。
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
				if (r < 0) { // 選択されていないとき
					if ((e.getModifiers() & ActionEvent.SHIFT_MASK) != 0) {
						r = 0;
					} else {
						r = getRowCount();
					}
				} else {
					if ((e.getModifiers() & ActionEvent.SHIFT_MASK) == 0) {
						// SHIFTキーが押されていないとき
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
	 * UNDO可能な行追加.
	 *
	 * @param rowData
	 *            行データ
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
	 * 削除アクション.
	 * <p>
	 * 行を削除する。
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
						// 新規追加していた行は、マークせずに直接削除する
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
	 * コミットアクション.
	 * <p>
	 * 変更を確定する。
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
	 * ロールバックアクション.
	 * <p>
	 * 変更を元に戻す。
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
