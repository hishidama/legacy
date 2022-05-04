package jp.hishidama.swing.table;

import java.util.Vector;

import jp.hishidama.swing.table.LazyTable.LazyAddAction;

/**
 * 遅延更新JTable用データモデル.
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/java/swing/JTable.html"
 *         >ひしだま</a>
 * @see LazyTable
 * @since 2009.03.29
 * @version 2009.04.26 ExTableModelから派生するよう変更
 */
public class LazyTableModel extends ExTableModel {
	private static final long serialVersionUID = 1591234686660546702L;

	@Override
	public void setValueAt(Object value, int row, int column) {
		if (value instanceof ChangeValue) {
			super.setValueAt(value, row, column);
			return;
		}
		Object old = super.getValueAt(row, column);
		if (old instanceof ChangeValue) {
			ChangeValue cv = ((ChangeValue) old).clone();
			cv.setValue(value);
			value = cv;
		} else {
			value = new ChangeValue(LazyTable.CHG, value, old);
		}
		super.setValueAt(value, row, column);
	}

	/**
	 * 値を取得.
	 * <p>
	 * 変更中の場合は、その新しい値。
	 * </p>
	 */
	@Override
	public Object getValueAt(int row, int column) {
		Object value = super.getValueAt(row, column);
		if (value instanceof ChangeValue) {
			ChangeValue cv = (ChangeValue) value;
			return cv.getValue();
		} else {
			return value;
		}
	}

	/**
	 * 古い値を取得.
	 *
	 * @param row
	 * @param column
	 * @return 変更中の場合は、その古い値。変更中でない場合は{@link #getValueAt(int, int)}と同じ。
	 */
	public Object getOldValueAt(int row, int column) {
		Object value = super.getValueAt(row, column);
		if (value instanceof ChangeValue) {
			ChangeValue cv = (ChangeValue) value;
			return cv.getOldValue();
		} else {
			return value;
		}
	}

	/**
	 * デフォルト値取得.
	 * <p>
	 * オーバーライドして使用される前提のメソッド。<br>
	 * 行追加される際に当メソッドが呼ばれ、初期値として設定される。
	 * </p>
	 *
	 * @param row
	 * @param column
	 * @return デフォルト値
	 * @see LazyAddAction
	 */
	public Object getDefaultValueAt(int row, int column) {
		return null;
	}

	/**
	 * 行ステータス取得.
	 * <p>
	 * 指定された行を左から調査し、最初に変更（追加・削除）だったときにそのステータスを返す。
	 * </p>
	 *
	 * @param row
	 *            行インデックス
	 * @return ステータス（{@link LazyTable#CHG}等）
	 */
	@SuppressWarnings("unchecked")
	public int getRowStatus(int row) {
		Vector rowVector = (Vector) dataVector.elementAt(row);
		for (Object v : rowVector) {
			if (v instanceof ChangeValue) {
				ChangeValue cv = (ChangeValue) v;
				return cv.getType();
			}
		}
		return 0;
	}

	/**
	 * ステータス取得.
	 *
	 * @param row
	 *            行インデックス
	 * @param column
	 *            列インデックス
	 * @return ステータス（{@link LazyTable#CHG}等）
	 */
	public int getStatusAt(int row, int column) {
		Object value = super.getValueAt(row, column);
		if (value instanceof ChangeValue) {
			ChangeValue cv = (ChangeValue) value;
			return cv.getType();
		} else {
			return 0;
		}
	}

	/**
	 * ステータス設定.
	 *
	 * @param row
	 *            行インデックス
	 * @param type
	 *            ステータス（{@link LazyTable#CHG}等）
	 */
	@SuppressWarnings("unchecked")
	public void setRowStatus(int row, int type) {
		Vector rowVector = (Vector) dataVector.elementAt(row);
		for (int i = 0; i < rowVector.size(); i++) {
			Object v = rowVector.get(i);
			if (v instanceof ChangeValue) {
				ChangeValue cv = (ChangeValue) v;
				cv.setType(type);
			} else {
				ChangeValue cv = new ChangeValue(type, v, v);
				super.setValueAt(cv, row, i);
			}
		}
	}

	/**
	 * 変更確定.
	 */
	@SuppressWarnings("unchecked")
	public void commit() {
		loop: for (int i = getRowCount() - 1; i >= 0; i--) {
			Vector rowVector = (Vector) dataVector.elementAt(i);
			for (Object v : rowVector) {
				if (v instanceof ChangeValue) {
					ChangeValue cv = (ChangeValue) v;
					switch (cv.getType()) {
					case LazyTable.ADD:
					case LazyTable.CHG:
						changeNew(rowVector, i);
						continue loop;
					case LazyTable.DEL:
						changeDel(rowVector, i);
						continue loop;
					}
				}
			}
		}
		undoSupport.discardAllEdits();
	}

	/**
	 * 変更戻し.
	 */
	@SuppressWarnings("unchecked")
	public void rollback() {
		loop: for (int i = getRowCount() - 1; i >= 0; i--) {
			Vector rowVector = (Vector) dataVector.elementAt(i);
			for (Object v : rowVector) {
				if (v instanceof ChangeValue) {
					ChangeValue cv = (ChangeValue) v;
					switch (cv.getType()) {
					case LazyTable.ADD:
						changeDel(rowVector, i);
						continue loop;
					case LazyTable.CHG:
					case LazyTable.DEL:
						changeOld(rowVector, i);
						continue loop;
					}
				}
			}
		}
		undoSupport.discardAllEdits();
	}

	protected void changeNew(Vector<Object> rowVector, int row) {
		int sz = rowVector.size();
		for (int i = 0; i < sz; i++) {
			Object v = rowVector.get(i);
			if (v instanceof ChangeValue) {
				ChangeValue cv = (ChangeValue) v;
				rowVector.set(i, cv.getValue());
			}
		}
		fireTableRowsUpdated(row, row);
	}

	protected void changeOld(Vector<Object> rowVector, int row) {
		int sz = rowVector.size();
		for (int i = 0; i < sz; i++) {
			Object v = rowVector.get(i);
			if (v instanceof ChangeValue) {
				ChangeValue cv = (ChangeValue) v;
				rowVector.set(i, cv.getOldValue());
			}
		}
		fireTableRowsUpdated(row, row);
	}

	protected void changeDel(Vector<Object> rowVector, int row) {
		removeRow(row);
	}

	/**
	 * 変更前後の値を保持する。
	 */
	public static class ChangeValue implements Cloneable {

		protected int type;
		protected Object value, old;

		/**
		 * コンストラクター.
		 *
		 * @param type
		 *            ステータス
		 * @param value
		 *            新しい値
		 * @param old
		 *            古い値
		 */
		public ChangeValue(int type, Object value, Object old) {
			this.type = type;
			this.value = value;
			this.old = old;
		}

		/**
		 * ステータス設定.
		 *
		 * @param type
		 *            ステータス
		 */
		public void setType(int type) {
			this.type = type;
		}

		/**
		 * ステータス取得.
		 *
		 * @return ステータス
		 */
		public int getType() {
			if ((type & LazyTable.CHG) != 0) {
				int t = type;
				if (value == null) {
					if (old == null) {
						t &= ~LazyTable.CHG;
						return t;
					}
				} else {
					if (value.equals(old)) {
						t &= ~LazyTable.CHG;
						return t;
					}
				}
			}
			return type;
		}

		/**
		 * 新しい値を設定.
		 *
		 * @param value
		 *            値
		 */
		public void setValue(Object value) {
			this.value = value;
		}

		/**
		 * 新しい値を取得.
		 *
		 * @return 値
		 */
		public Object getValue() {
			return value;
		}

		/**
		 * 古い値を設定.
		 *
		 * @param old
		 *            値
		 */
		public void setOldValue(Object old) {
			this.old = old;
		}

		/**
		 * 古い値を取得.
		 *
		 * @return 値
		 */
		public Object getOldValue() {
			return old;
		}

		@Override
		public String toString() {
			return String.valueOf(value);
		}

		@Override
		protected ChangeValue clone() {
			try {
				return (ChangeValue) super.clone();
			} catch (CloneNotSupportedException e) {
				throw new InternalError(e.toString());
			}
		}
	}

	@Override
	protected Vector<?> cloneRowData(Vector<?> rowData) {
		@SuppressWarnings("unchecked")
		Vector<Object> v = (Vector<Object>) super.cloneRowData(rowData);
		for (int i = 0; i < v.size(); i++) {
			Object obj = v.get(i);
			if (obj instanceof ChangeValue) {
				ChangeValue cv = (ChangeValue) obj;
				v.set(i, cv.clone());
			}
		}
		return v;
	}
}
