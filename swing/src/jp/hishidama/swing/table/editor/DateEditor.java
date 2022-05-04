package jp.hishidama.swing.table.editor;

import java.awt.Color;
import java.awt.Component;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.DefaultCellEditor;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

/**
 * ���t�G�f�B�^�[.
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/java/swing/JTable.html"
 *         >�Ђ�����</a>
 * @since 2009.03.22
 */
public class DateEditor extends DefaultCellEditor {
	private static final long serialVersionUID = 7159257623625914894L;

	private DateFormat formatter;
	protected Date dvalue;

	/** �R���X�g���N�^�[. */
	public DateEditor() {
		super(new JTextField());
	}

	/**
	 * �R���X�g���N�^�[.
	 *
	 * @param pattern
	 *            ���t�p�^�[��
	 */
	public DateEditor(String pattern) {
		super(new JTextField());
		formatter = new SimpleDateFormat(pattern);
	}

	/**
	 * �t�H�[�}�b�^�[�ݒ�.
	 *
	 * @param df
	 *            ���t�t�H�[�}�b�^�[
	 */
	public void setFormatter(DateFormat df) {
		formatter = df;
	}

	/**
	 * �t�H�[�}�b�^�[�擾.
	 *
	 * @return ���t�t�H�[�}�b�^�[
	 */
	public DateFormat getFormatter() {
		if (formatter == null) {
			formatter = DateFormat.getDateInstance();
			formatter.setLenient(false);
		}
		return formatter;
	}

	/**
	 * �ҏW�J�n���ɌĂ΂��
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
		this.dvalue = null;
		((JComponent) getComponent()).setBorder(new LineBorder(Color.black));

		// row,column�́ATableColumnModel�̑̌n
		// System.out.printf("getComponent(%b,%d,%d)+++%s %s%n", isSelected,
		// row, column, value.getClass(), value);

		DateFormat f = getFormatter();
		String str = (value == null) ? "" : f.format(value);
		return super.getTableCellEditorComponent(table, str, isSelected, row,
				column);
	}

	/**
	 * �ҏW�������ɌĂ΂��.
	 * <p>
	 * super.stopCellEditing()���Ăяo���Ɛ���I�������ƂȂ�B
	 * </p>
	 *
	 * @return true�̏ꍇ�A����I���i�Ǝv���邪�A���ۂɂ͉���Ԃ��Ă����֌W���ۂ��j
	 */
	@Override
	public boolean stopCellEditing() {
		Date d = null;

		String s = (String) super.getCellEditorValue();
		if (s != null && !s.isEmpty()) {
			DateFormat f = getFormatter();
			ParsePosition pos = new ParsePosition(0);
			d = f.parse(s, pos);
			if (pos.getErrorIndex() >= 0) {
				((JComponent) getComponent()).setBorder(new LineBorder(
						Color.red));
				return false;
			}
		}

		this.dvalue = d;
		return super.stopCellEditing();
	}

	/**
	 * ���͂��ꂽ�l��Ԃ�.
	 *
	 * @return �ҏW���ꂽ�l
	 */
	@Override
	public Object getCellEditorValue() {
		return dvalue;
	}

	@Override
	public void cancelCellEditing() {
		// �ǂ������̃��\�b�h�͌Ă΂�Ȃ��͗l
		// System.out.println("cancelCellEditing:" + this);
		super.cancelCellEditing();
	}
}