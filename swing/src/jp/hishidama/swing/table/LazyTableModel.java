package jp.hishidama.swing.table;

import java.util.Vector;

import jp.hishidama.swing.table.LazyTable.LazyAddAction;

/**
 * �x���X�VJTable�p�f�[�^���f��.
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/java/swing/JTable.html"
 *         >�Ђ�����</a>
 * @see LazyTable
 * @since 2009.03.29
 * @version 2009.04.26 ExTableModel����h������悤�ύX
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
	 * �l���擾.
	 * <p>
	 * �ύX���̏ꍇ�́A���̐V�����l�B
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
	 * �Â��l���擾.
	 *
	 * @param row
	 * @param column
	 * @return �ύX���̏ꍇ�́A���̌Â��l�B�ύX���łȂ��ꍇ��{@link #getValueAt(int, int)}�Ɠ����B
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
	 * �f�t�H���g�l�擾.
	 * <p>
	 * �I�[�o�[���C�h���Ďg�p�����O��̃��\�b�h�B<br>
	 * �s�ǉ������ۂɓ����\�b�h���Ă΂�A�����l�Ƃ��Đݒ肳���B
	 * </p>
	 *
	 * @param row
	 * @param column
	 * @return �f�t�H���g�l
	 * @see LazyAddAction
	 */
	public Object getDefaultValueAt(int row, int column) {
		return null;
	}

	/**
	 * �s�X�e�[�^�X�擾.
	 * <p>
	 * �w�肳�ꂽ�s�������璲�����A�ŏ��ɕύX�i�ǉ��E�폜�j�������Ƃ��ɂ��̃X�e�[�^�X��Ԃ��B
	 * </p>
	 *
	 * @param row
	 *            �s�C���f�b�N�X
	 * @return �X�e�[�^�X�i{@link LazyTable#CHG}���j
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
	 * �X�e�[�^�X�擾.
	 *
	 * @param row
	 *            �s�C���f�b�N�X
	 * @param column
	 *            ��C���f�b�N�X
	 * @return �X�e�[�^�X�i{@link LazyTable#CHG}���j
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
	 * �X�e�[�^�X�ݒ�.
	 *
	 * @param row
	 *            �s�C���f�b�N�X
	 * @param type
	 *            �X�e�[�^�X�i{@link LazyTable#CHG}���j
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
	 * �ύX�m��.
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
	 * �ύX�߂�.
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
	 * �ύX�O��̒l��ێ�����B
	 */
	public static class ChangeValue implements Cloneable {

		protected int type;
		protected Object value, old;

		/**
		 * �R���X�g���N�^�[.
		 *
		 * @param type
		 *            �X�e�[�^�X
		 * @param value
		 *            �V�����l
		 * @param old
		 *            �Â��l
		 */
		public ChangeValue(int type, Object value, Object old) {
			this.type = type;
			this.value = value;
			this.old = old;
		}

		/**
		 * �X�e�[�^�X�ݒ�.
		 *
		 * @param type
		 *            �X�e�[�^�X
		 */
		public void setType(int type) {
			this.type = type;
		}

		/**
		 * �X�e�[�^�X�擾.
		 *
		 * @return �X�e�[�^�X
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
		 * �V�����l��ݒ�.
		 *
		 * @param value
		 *            �l
		 */
		public void setValue(Object value) {
			this.value = value;
		}

		/**
		 * �V�����l���擾.
		 *
		 * @return �l
		 */
		public Object getValue() {
			return value;
		}

		/**
		 * �Â��l��ݒ�.
		 *
		 * @param old
		 *            �l
		 */
		public void setOldValue(Object old) {
			this.old = old;
		}

		/**
		 * �Â��l���擾.
		 *
		 * @return �l
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
