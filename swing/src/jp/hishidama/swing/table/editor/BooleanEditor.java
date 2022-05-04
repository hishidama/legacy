package jp.hishidama.swing.table.editor;

import java.awt.Color;
import java.awt.Component;
import java.lang.reflect.Method;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.border.LineBorder;

/**
 * boolean�G�f�B�^�[.
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/java/swing/JTable.html"
 *         >�Ђ�����</a>
 * @since 2009.09.27
 */
public class BooleanEditor extends DefaultCellEditor {
	private static final long serialVersionUID = 3423822347276965549L;

	protected Object dvalue;

	protected Class<?> type;

	/** �R���X�g���N�^�[. */
	public BooleanEditor() {
		super(new JCheckBox());
		getComponent().setHorizontalAlignment(JCheckBox.CENTER);
	}

	@Override
	public JCheckBox getComponent() {
		return (JCheckBox) super.getComponent();
	}

	/**
	 * �ҏW�J�n���ɌĂ΂��.
	 *
	 * @param table
	 *            �Ώۃe�[�u��
	 * @param value
	 *            �ҏW�Ώۃf�[�^�i�����l�j
	 * @param isSelected
	 * @param row
	 *            �f�[�^�̂���s�ԍ�
	 * @param column
	 *            �f�[�^�̂����ԍ�
	 * @return �`��p�R���|�[�l���g
	 */
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		this.dvalue = null; // ������
		getComponent().setBorder(new LineBorder(Color.black)); // �ŏ��͍��g

		Boolean selected;
		if (value == null) {
			selected = false;
			type = null;
		} else {
			type = value.getClass();
			if (value instanceof Boolean) {
				selected = (Boolean) value;
			} else if (value instanceof Number) {
				selected = ((Number) value).intValue() != 0;
			} else {
				selected = Boolean.parseBoolean(value.toString());
			}
		}

		return super.getTableCellEditorComponent(table, selected, isSelected,
				row, column);
	}

	/**
	 * �ҏW�������ɌĂ΂��.
	 * <p>
	 * super.stopCellEditing()���Ăяo���Ɛ���I�������ƂȂ�͗l�B
	 * </p>
	 *
	 * @return true�̏ꍇ�A����I���i�Ǝv���邪�A���ۂɂ͉���Ԃ��Ă����֌W���ۂ��j
	 */
	@Override
	public boolean stopCellEditing() {
		Object d = null;

		Boolean b = (Boolean) super.getCellEditorValue(); // �ҏW���ꂽ�l�̎擾
		if (type == null) {
			d = b;
		} else if (b != null) {
			try {
				if (type == Boolean.class || type == Object.class) {
					d = b;
				} else if (type == String.class) {
					d = b.toString();
				} else {
					Method m = type.getMethod("valueOf", String.class);
					if (b.booleanValue()) {
						d = m.invoke(null, "1");
					} else {
						d = m.invoke(null, "0");
					}
				}
			} catch (Exception e) {
				// �G���[���͐Ԙg��\������
				getComponent().setBorder(new LineBorder(Color.red));
				return false;
			}
		}

		this.dvalue = d; // �Ԃ��l(�ҏW���ꂽ�l)��ێ����Ă���

		return super.stopCellEditing();
		// ����super.stopCellEditing()�̒���getCellEditorValue()���Ă΂�A
		// JTable#setValueAt()�ɂ���Ēl���Z�b�g����A
		// �ҏW�p�R���|�[�l���g��������A
		// �ҏW����������
	}

	/**
	 * ���͂��ꂽ�l(���H�ς�)��Ԃ�.
	 *
	 * @return �ҏW���ꂽ�l
	 */
	@Override
	public Object getCellEditorValue() {
		return dvalue;
	}
}
