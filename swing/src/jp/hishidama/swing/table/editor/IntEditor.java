package jp.hishidama.swing.table.editor;

import java.awt.Color;
import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.border.LineBorder;

import jp.hishidama.swing.undo.UndoTextField;

/**
 * int�G�f�B�^�[.
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/java/swing/JTable.html"
 *         >�Ђ�����</a>
 * @since 2009.09.27
 */
public class IntEditor extends DefaultCellEditor {
	private static final long serialVersionUID = -8368795944481669400L;

	protected Integer dvalue;

	/** �R���X�g���N�^�[. */
	public IntEditor() {
		super(new UndoTextField());
		getComponent().setHorizontalAlignment(UndoTextField.RIGHT);
	}

	@Override
	public UndoTextField getComponent() {
		return (UndoTextField) super.getComponent();
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

		String str = (value == null) ? "" : value.toString();
		UndoTextField utf = (UndoTextField) super.getTableCellEditorComponent(
				table, str, isSelected, row, column);
		utf.clearUndoEdit();
		return utf;
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
		Integer d = null;

		String str = (String) super.getCellEditorValue(); // �ҏW���ꂽ�l�̎擾
		if (str != null && !str.isEmpty()) {
			try {
				d = Integer.valueOf(str);
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
