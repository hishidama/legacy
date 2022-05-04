package jp.hishidama.swing.table;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoableEdit;

import jp.hishidama.swing.undo.ExUndoableEditSupport;

/**
 * 拡張TableModel.
 *
 * <ul>
 * <li>セル編集・行追加削除のUNDO/REDO機能</li>
 * </ul>
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/java/swing/JTable.html"
 *         >ひしだま</a>
 * @since 2009.04.26
 */
public class ExTableModel extends DefaultTableModel {
	private static final long serialVersionUID = -8310245115388152864L;

	protected ExUndoableEditSupport undoSupport;

	/** コンストラクター. */
	public ExTableModel() {
		super();
		initUndoableEditSupport();
	}

	/**
	 * UndoableEditSupport初期化.
	 */
	public void initUndoableEditSupport() {
		undoSupport = new ExUndoableEditSupport(this);
	}

	/**
	 * UndoableEditSupport取得.
	 *
	 * @return UndoableEditSupport
	 */
	public ExUndoableEditSupport getUndoableEditSupport() {
		return undoSupport;
	}

	// セルの値の編集

	@Override
	public void setValueAt(Object value, int row, int column) {
		if (!undoSupport.isIgnore()) {
			UndoableEdit ue = new SetValueUndo(value, row, column);
			undoSupport.postEdit(ue);
		}

		setValueAt0(value, row, column);
	}

	protected void setValueAt0(Object value, int row, int column) {
		super.setValueAt(value, row, column);
	}

	protected Object getValueAt0(int row, int column) {
		return super.getValueAt(row, column);
	}

	/**
	 * @see ExTableModel#setValueAt(Object, int, int)
	 */
	public class SetValueUndo extends AbstractUndoableEdit {
		private static final long serialVersionUID = -4285749797087066129L;

		protected Object oldValue;
		protected Object newValue;
		protected int row, column;

		public SetValueUndo(Object value, int row, int column) {
			oldValue = getValueAt0(row, column);
			newValue = value;
			this.row = row;
			this.column = column;
		}

		@Override
		public void undo() throws CannotUndoException {
			super.undo();

			undoSupport.beginIgnore();
			try {
				setValueAt0(oldValue, row, column);
			} finally {
				undoSupport.endIgnore();
			}
		}

		@Override
		public void redo() throws CannotRedoException {
			super.redo();

			undoSupport.beginIgnore();
			try {
				setValueAt0(newValue, row, column);
			} finally {
				undoSupport.endIgnore();
			}
		}

		@Override
		public void die() {
			super.die();
			oldValue = null;
			newValue = null;
		}
	}

	// 行追加・削除

	@SuppressWarnings("unchecked")
	@Override
	public void insertRow(int row, Vector rowData) {
		if (!undoSupport.isIgnore()) {
			UndoableEdit ue = new InsertRowUndo(row, rowData);
			undoSupport.postEdit(ue);
		}

		insertRow0(row, rowData);
	}

	protected void insertRow0(int row, Vector<?> rowData) {
		super.insertRow(row, rowData);
	}

	@Override
	public void moveRow(int start, int end, int to) {
		if (!undoSupport.isIgnore()) {
			UndoableEdit ue = new MoveRowUndo(start, end, to);
			undoSupport.postEdit(ue);
		}

		moveRow0(start, end, to);
	}

	protected void moveRow0(int start, int end, int to) {
		super.moveRow(start, end, to);
	}

	@Override
	public void removeRow(int row) {
		if (!undoSupport.isIgnore()) {
			UndoableEdit ue = new RemoveRowUndo(row);
			undoSupport.postEdit(ue);
		}

		removeRow0(row);
	}

	protected void removeRow0(int row) {
		super.removeRow(row);
	}

	@Override
	public void setNumRows(int rowCount) {
		if (!undoSupport.isIgnore()) {
			UndoableEdit ue = new SetNumRowsUndo(rowCount);
			undoSupport.postEdit(ue);
		}

		setNumRows0(rowCount);
	}

	protected void setNumRows0(int rowCount) {
		super.setNumRows(rowCount);
	}

	/**
	 * @see ExTableModel#insertRow(int, Vector)
	 */
	public class InsertRowUndo extends AbstractUndoableEdit {
		private static final long serialVersionUID = -6460259062834570436L;

		protected int row;
		protected Vector<?> newData;

		public InsertRowUndo(int row, Vector<?> rowData) {
			newData = cloneRowData(rowData);
			this.row = row;
		}

		@Override
		public void undo() throws CannotUndoException {
			super.undo();

			undoSupport.beginIgnore();
			try {
				removeRow0(row);
			} finally {
				undoSupport.endIgnore();
			}
		}

		@Override
		public void redo() throws CannotRedoException {
			super.redo();

			undoSupport.beginIgnore();
			try {
				Vector<?> rowData = cloneRowData(newData);
				insertRow0(row, rowData);
			} finally {
				undoSupport.endIgnore();
			}
		}

		@Override
		public void die() {
			super.die();
			newData = null;
		}
	}

	/**
	 * @see ExTableModel#moveRow(int, int, int)
	 */
	public class MoveRowUndo extends AbstractUndoableEdit {
		private static final long serialVersionUID = 5778922981265953245L;

		protected int start, shift, to;

		public MoveRowUndo(int start, int end, int to) {
			this.start = start;
			this.shift = end - start;
			this.to = to;
		}

		@Override
		public void undo() throws CannotUndoException {
			super.undo();

			undoSupport.beginIgnore();
			try {
				moveRow0(to, to + shift, start);
			} finally {
				undoSupport.endIgnore();
			}
		}

		@Override
		public void redo() throws CannotRedoException {
			super.redo();

			undoSupport.beginIgnore();
			try {
				moveRow0(start, start + shift, to);
			} finally {
				undoSupport.endIgnore();
			}
		}
	}

	/**
	 * @see ExTableModel#removeRow(int)
	 */
	public class RemoveRowUndo extends AbstractUndoableEdit {
		private static final long serialVersionUID = 8053068881092666629L;

		protected int row;
		protected Vector<?> oldData;

		public RemoveRowUndo(int row) {
			this.row = row;
			oldData = (Vector<?>) getDataVector().get(row);
		}

		@Override
		public void undo() throws CannotUndoException {
			super.undo();

			undoSupport.beginIgnore();
			try {
				Vector<?> rowData = cloneRowData(oldData);
				insertRow0(row, rowData);
			} finally {
				undoSupport.endIgnore();
			}
		}

		@Override
		public void redo() throws CannotRedoException {
			super.redo();

			undoSupport.beginIgnore();
			try {
				removeRow0(row);
			} finally {
				undoSupport.endIgnore();
			}
		}

		@Override
		public void die() {
			super.die();
			oldData = null;
		}
	}

	/**
	 * @see ExTableModel#setNumRows(int)
	 */
	public class SetNumRowsUndo extends AbstractUndoableEdit {
		private static final long serialVersionUID = -8729739044656312938L;

		protected int newRowCount;
		protected int oldRowCount;
		protected List<Vector<?>> rowList;

		public SetNumRowsUndo(int rowCount) {
			newRowCount = rowCount;
			oldRowCount = getRowCount();

			if (newRowCount < oldRowCount) {
				rowList = new ArrayList<Vector<?>>(oldRowCount - newRowCount);
				for (int i = newRowCount; i < oldRowCount; i++) {
					Vector<?> rowData = (Vector<?>) dataVector.get(i);
					rowList.add(rowData);
				}
			}
		}

		@Override
		public void undo() throws CannotUndoException {
			super.undo();

			undoSupport.beginIgnore();
			try {
				if (newRowCount < oldRowCount) {
					for (Vector<?> rowData : rowList) {
						insertRow0(getRowCount(), rowData);
					}
				} else {
					setNumRows0(oldRowCount);
				}
			} finally {
				undoSupport.endIgnore();
			}
		}

		@Override
		public void redo() throws CannotRedoException {
			super.redo();

			undoSupport.beginIgnore();
			try {
				setNumRows0(newRowCount);
			} finally {
				undoSupport.endIgnore();
			}
		}

		@Override
		public void die() {
			super.die();
			rowList = null;
		}
	}

	protected Vector<?> cloneRowData(Vector<?> rowData) {
		if (rowData == null) {
			return null;
		}
		return (Vector<?>) rowData.clone();
	}
}
