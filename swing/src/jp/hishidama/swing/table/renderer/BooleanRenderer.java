package jp.hishidama.swing.table.renderer;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.UIResource;
import javax.swing.table.TableCellRenderer;

/**
 * 真偽値レンダラー.
 *<p>
 * Boolean以外の値にも対応しているレンダラー。
 * </p>
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/java/swing/JTable.html"
 *         >ひしだま</a>
 * @since 2009.09.27
 */
public class BooleanRenderer extends JCheckBox implements TableCellRenderer,
		UIResource {
	private static final long serialVersionUID = -7556742779722900061L;

	private static final Border noFocusBorder = new EmptyBorder(1, 1, 1, 1);

	private Color unselectedBackground = null;

	/** コンストラクター. */
	public BooleanRenderer() {
		super();
		setHorizontalAlignment(JCheckBox.CENTER);
		setBorderPainted(true);
	}

	@Override
	public void setBackground(Color bg) {
		super.setBackground(bg);
		unselectedBackground = bg;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		if (isSelected) {
			super.setForeground(table.getSelectionForeground());
			super.setBackground(table.getSelectionBackground());
		} else {
			super.setForeground(table.getForeground());
			Color bg = (unselectedBackground != null) ? unselectedBackground
					: table.getBackground();
			super.setBackground(bg);
		}

		boolean selected;
		if (value == null) {
			selected = false;
		} else {
			if (value instanceof Boolean) {
				selected = ((Boolean) value).booleanValue();
			} else if (value instanceof Number) {
				selected = ((Number) value).intValue() != 0;
			} else {
				selected = Boolean.parseBoolean(value.toString());
			}
		}
		setSelected(selected);

		if (hasFocus) {
			setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));
		} else {
			setBorder(noFocusBorder);
		}

		return this;
	}
}
