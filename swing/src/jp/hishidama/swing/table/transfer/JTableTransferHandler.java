package jp.hishidama.swing.table.transfer;

import java.awt.Component;
import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.TransferHandler;
import javax.swing.table.DefaultTableModel;

import jp.hishidama.swing.table.ExTable;
import jp.hishidama.swing.table.LazyTable;
import jp.hishidama.swing.util.ClassUtil;

/**
 * JTable用転送ハンドラー.
 * <p>
 * 以下のデータを転送可能。
 * </p>
 * <ul>
 * <li>タブ区切り文字列</li>
 * <li>ファイルリスト</li>
 * </ul>
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/java/swing/JTable.html"
 *         >ひしだま</a>
 * @since 2009.09.28
 * @version 2009.10.01 JTableをセットしたJScrollPaneに対応
 */
public class JTableTransferHandler extends TransferHandler {
	private static final long serialVersionUID = -3280869929263746341L;

	@Override
	public int getSourceActions(JComponent c) {
		return COPY;
	}

	@Override
	protected Transferable createTransferable(JComponent c) {
		JTable table = (JTable) c;
		return new JTableTransferable(table);
	}

	@Override
	public boolean canImport(TransferSupport support) {
		return support.isDataFlavorSupported(DataFlavor.stringFlavor)
				|| support.isDataFlavorSupported(DataFlavor.javaFileListFlavor);
	}

	@Override
	public boolean importData(TransferSupport support) {
		try {
			Transferable tdata = support.getTransferable();
			if (tdata.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
				return importFileList(support);
			}
			if (tdata.isDataFlavorSupported(DataFlavor.stringFlavor)) {
				return importString(support);
			}
			return false;

		} catch (RuntimeException e) {
			System.err.println("JTableTransferHandler#importData() catch:");
			e.printStackTrace();
			throw e;
		}
	}

	protected boolean importString(TransferSupport support) {
		Transferable tdata = support.getTransferable();

		String str;
		try {
			str = (String) tdata.getTransferData(DataFlavor.stringFlavor);
		} catch (UnsupportedFlavorException e) {
			return false;
		} catch (IOException e) {
			return false;
		}

		return importString(str, support);
	}

	protected boolean importString(String str, TransferSupport support) {

		String[] strr = str.split("\n");
		int max = 0;
		List<List<String>> rlist = new ArrayList<List<String>>(strr.length);
		for (int i = 0; i < strr.length; i++) {
			String[] strc = strr[i].split("\t");
			max = Math.max(max, strc.length);
			List<String> list = new ArrayList<String>(max);
			for (int j = 0; j < strc.length; j++) {
				list.add(strc[j]);
			}
			rlist.add(list);
		}

		Import<String> importer = createImportString(support);
		return importer.exec(rlist);
	}

	protected Import<String> createImportString(TransferSupport support) {
		return new ImportString(support);
	}

	@SuppressWarnings("unchecked")
	protected boolean importFileList(TransferSupport support) {
		Transferable tdata = support.getTransferable();

		List<File> flist;
		try {
			flist = (List) tdata.getTransferData(DataFlavor.javaFileListFlavor);
		} catch (UnsupportedFlavorException e) {
			return false;
		} catch (IOException e) {
			return false;
		}

		return importFileList(flist, support);
	}

	protected boolean importFileList(List<File> flist, TransferSupport support) {

		Import<File> importer = createImportFile(support);
		return importer.execSingleColumn(flist);
	}

	protected Import<File> createImportFile(TransferSupport support) {
		return new ImportFile(support);
	}

	/**
	 * データインポート本体.
	 *
	 * @param <C>
	 *            インポートデータのクラス
	 */
	protected static abstract class Import<C> {

		protected TransferSupport support;
		protected JTable table;
		protected ExTable extable = null;
		protected LazyTable lztable = null;
		protected boolean importAllRow = false;

		/**
		 * コンストラクター.
		 *
		 * @param support
		 */
		public Import(TransferSupport support) {
			this.support = support;
			table = getTable(support);
			if (table instanceof ExTable) {
				extable = (ExTable) table;
			}
			if (table instanceof LazyTable) {
				lztable = (LazyTable) table;
			}
		}

		protected JTable getTable(TransferSupport support) {
			Component c = support.getComponent();
			if (c instanceof JTable) {
				return (JTable) c;
			}
			if (c instanceof JScrollPane) {
				importAllRow = true;
				JScrollPane sp = (JScrollPane) c;
				Component v = sp.getViewport().getView();
				return (JTable) v;
			}
			throw new UnsupportedOperationException("component=" + c);
		}

		protected void beginExec() {
			if (extable != null) {
				extable.beginUndoUpdate();
			}
		}

		protected void endExec() {
			if (extable != null) {
				extable.endUndoUpdate();
			}
		}

		/**
		 * インポート実行
		 *
		 * @param rlist
		 *            データ（行列）
		 * @return
		 */
		public boolean exec(List<List<C>> rlist) {
			beginExec();

			int cmax = 0;
			for (int i = 0; i < rlist.size(); i++) {
				List<C> list = rlist.get(i);
				cmax = Math.max(cmax, list.size());
			}

			try {
				Point vpos = getImportPosition();
				int[] rows = table.getSelectedRows();
				int[] cols = table.getSelectedColumns();
				List<Integer> tor = getDestIndex(vpos.y, rows, rlist.size());
				List<Integer> toc = getDestIndex(vpos.x, cols, cmax);

				for (int i = 0; i < rlist.size(); i++) {
					int r = tor.get(i);
					if (breakRowLoop(r)) {
						break;
					}
					List<C> list = rlist.get(i);
					importRow(list, r, toc);
				}
			} finally {
				endExec();
			}

			return true;
		}

		/**
		 * インポート実行（縦一列のみ）
		 *
		 * @param rlist
		 *            データ（一列分）
		 * @return
		 */
		public boolean execSingleColumn(List<C> rlist) {
			beginExec();
			try {
				Point vpos = getImportPosition();
				int[] rows = table.getSelectedRows();
				int[] cols = table.getSelectedColumns();
				List<Integer> tor = getDestIndex(vpos.y, rows, rlist.size());
				List<Integer> toc = getDestIndex(vpos.x, cols, 1);

				for (int i = 0; i < rlist.size(); i++) {
					int r = tor.get(i);
					if (breakRowLoop(r)) {
						break;
					}
					C obj = rlist.get(i);
					int c = toc.get(0);
					importValue(obj, r, c);
				}
			} finally {
				endExec();
			}

			return true;
		}

		/**
		 * インポート位置取得.
		 *
		 * @param support
		 * @return インポート開始位置(列, 行)
		 */
		protected Point getImportPosition() {
			int vr, vc;
			if (support.isDrop()) {
				DropLocation loc = support.getDropLocation();
				if (loc instanceof JTable.DropLocation) {
					JTable.DropLocation tloc = (JTable.DropLocation) support
							.getDropLocation();
					vr = tloc.getRow();
					vc = tloc.getColumn();
				} else {
					vr = table.getRowCount();
					vc = 0;
				}
			} else {
				vr = table.getSelectedRow();
				vc = table.getSelectedColumn();
			}
			return new Point(vc, vr);
		}

		/**
		 * 行ループ終了チェック.
		 *
		 * @param r
		 *            行
		 * @return true：ループを終了する
		 */
		protected boolean breakRowLoop(int r) {
			if (importAllRow) {
				if (r >= table.getRowCount()) {
					if (lztable != null) {
						lztable.addRowUndoable(null);
					} else {
						DefaultTableModel model = (DefaultTableModel) table
								.getModel();
						model.addRow(new Vector<Object>());
					}
				}
				return false;
			}
			return (r >= table.getRowCount());
		}

		protected List<Integer> getDestIndex(int start, int[] sels, int size) {
			List<Integer> list = new ArrayList<Integer>(size);
			int last = start;
			list.add(last);
			int i = 1;
			for (int j = 0; j < sels.length; j++) {
				int s = sels[j];
				if (s > last) {
					last = s;
					list.add(last);
					i++;
				}
			}
			for (; i < size; i++) {
				list.add(++last);
			}
			return list;
		}

		protected void importRow(List<C> list, int r, List<Integer> toc) {
			for (int j = 0; j < list.size(); j++) {
				int c = toc.get(j);
				if (c >= table.getColumnCount()) {
					break;
				}
				C obj = list.get(j);
				importValue(obj, r, c);
			}
		}

		protected void importValue(C obj, int r, int c) {
			Class<?> cls = table.getColumnClass(c);
			Object value = toObject(obj, cls);
			if (value != null) {
				table.setValueAt(value, r, c);
			}
		}

		protected abstract Object toObject(C obj, Class<?> cls);
	}

	protected static class ImportString extends Import<String> {

		public ImportString(TransferSupport support) {
			super(support);
		}

		@Override
		protected Object toObject(String obj, Class<?> cls) {
			return ClassUtil.toObject(obj, cls);
		}
	}

	protected static class ImportFile extends Import<File> {

		public ImportFile(TransferSupport support) {
			super(support);
		}

		@Override
		protected Object toObject(File obj, Class<?> cls) {
			return ClassUtil.toObject(obj.toString(), cls);
		}
	}
}
