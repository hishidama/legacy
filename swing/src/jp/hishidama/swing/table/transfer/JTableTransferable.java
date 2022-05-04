package jp.hishidama.swing.table.transfer;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringBufferInputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;

/**
 * JTable用転送データ.
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/java/swing/JTable.html"
 *         >ひしだま</a>
 * @since 2009.09.28
 */
@SuppressWarnings("deprecation")
public class JTableTransferable implements Transferable {

	protected List<List<Object>> rowList;

	/**
	 * コンストラクター.
	 *
	 * @param table
	 *            テーブル
	 */
	public JTableTransferable(JTable table) {
		int[] rows = table.getSelectedRows();
		int[] cols = table.getSelectedColumns();

		rowList = new ArrayList<List<Object>>(rows.length);

		for (int r = 0; r < rows.length; r++) {
			List<Object> list = new ArrayList<Object>(cols.length);
			for (int c = 0; c < cols.length; c++) {
				Object value = table.getValueAt(rows[r], cols[c]);

				// 本来はvalue.clone()を呼びたいが、汎用的に呼ぶ方法を思い付かない…

				list.add(value);
			}
			rowList.add(list);
		}
	}

	private static DataFlavor[] stringFlavors;
	private static DataFlavor[] htmlFlavors;
	private static DataFlavor[] plainFlavors;

	static {
		try {
			htmlFlavors = new DataFlavor[] {
					new DataFlavor("text/html;class=java.lang.String"),
					new DataFlavor("text/html;class=java.io.Reader"),
					new DataFlavor(
							"text/html;charset=unicode;class=java.io.InputStream") };

			plainFlavors = new DataFlavor[] {
					new DataFlavor("text/plain;class=java.lang.String"),
					new DataFlavor("text/plain;class=java.io.Reader"),
					new DataFlavor(
							"text/plain;charset=unicode;class=java.io.InputStream") };

			stringFlavors = new DataFlavor[] {
					new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType
							+ ";class=java.lang.String"),
					DataFlavor.stringFlavor };
		} catch (ClassNotFoundException cle) {
			cle.printStackTrace();
		}
	}

	@Override
	public DataFlavor[] getTransferDataFlavors() {
		DataFlavor[] fs = new DataFlavor[stringFlavors.length
				+ htmlFlavors.length + plainFlavors.length];
		int n = 0;
		n += addTransferDataFlavors(fs, stringFlavors, n);
		n += addTransferDataFlavors(fs, htmlFlavors, n);
		n += addTransferDataFlavors(fs, plainFlavors, n);
		return fs;
	}

	protected static int addTransferDataFlavors(DataFlavor[] dest,
			DataFlavor[] src, int n) {
		int len = src.length;
		System.arraycopy(src, 0, dest, n, len);
		return len;
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		if (isDataFlavorSupported(flavor, stringFlavors)) {
			return true;
		}
		if (isDataFlavorSupported(flavor, htmlFlavors)) {
			return true;
		}
		if (isDataFlavorSupported(flavor, plainFlavors)) {
			return true;
		}
		return false;
	}

	protected static boolean isDataFlavorSupported(DataFlavor flavor,
			DataFlavor[] flavors) {
		for (int i = 0; i < flavors.length; i++) {
			if (flavors[i].equals(flavor)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Object getTransferData(DataFlavor flavor)
			throws UnsupportedFlavorException, IOException {
		if (isDataFlavorSupported(flavor, stringFlavors)
				|| isDataFlavorSupported(flavor, plainFlavors)) {
			StringBuilder sb = new StringBuilder(256);
			for (int i = 0; i < rowList.size(); i++) {
				if (i != 0) {
					sb.append('\n');
				}
				List<Object> list = rowList.get(i);
				for (int j = 0; j < list.size(); j++) {
					if (j != 0) {
						sb.append('\t');
					}
					Object value = list.get(j);
					String str = value.toString();
					if (str != null) {
						sb.append(str);
					}
				}
			}
			if (Reader.class.equals(flavor.getRepresentationClass())) {
				return new StringReader(sb.toString());
			} else if (InputStream.class
					.equals(flavor.getRepresentationClass())) {
				return new StringBufferInputStream(sb.toString());
			}
			return sb.toString();
		}
		if (isDataFlavorSupported(flavor, htmlFlavors)) {
			StringBuilder sb = new StringBuilder(256);
			sb.append("<html><body><table>\n");
			for (int i = 0; i < rowList.size(); i++) {
				sb.append("<tr>");
				List<Object> list = rowList.get(i);
				for (int j = 0; j < list.size(); j++) {
					sb.append("<td>");
					Object value = list.get(j);
					String str = value.toString();
					if (str != null) {
						sb.append(str);
					}
					sb.append("</td>");
				}
				sb.append("</tr>\n");
			}
			sb.append("</table></body></html>");
			if (String.class.equals(flavor.getRepresentationClass())) {
				return sb.toString();
			} else if (Reader.class.equals(flavor.getRepresentationClass())) {
				return new StringReader(sb.toString());
			} else if (InputStream.class
					.equals(flavor.getRepresentationClass())) {
				return new StringBufferInputStream(sb.toString());
			}
		}
		throw new UnsupportedFlavorException(flavor);
	}
}
