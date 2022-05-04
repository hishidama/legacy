package jp.hishidama.swing.table;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * 行ヘッダーのSelectionModel.
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/java/swing/JTable.html"
 *         >ひしだま</a>
 * @since 2009.03.13
 * @version 2009.04.11 選択時にdataTableを行選択するよう変更
 * @see RowHeaderTable
 */
public class RowHeaderSelectionModel extends DefaultListSelectionModel
		implements ListSelectionListener {
	private static final long serialVersionUID = -9045620466701969980L;

	protected JTable dataTable;
	protected ListSelectionModel rsm, csm;

	/**
	 * コンストラクター
	 *
	 * @param dataTable
	 *            データテーブル
	 */
	public RowHeaderSelectionModel(JTable dataTable) {
		this.dataTable = dataTable;
		this.rsm = dataTable.getSelectionModel();
		this.csm = dataTable.getColumnModel().getSelectionModel();

		rsm.removeListSelectionListener(this);
		rsm.addListSelectionListener(this);
	}

	@Override
	public void setSelectionInterval(int index0, int index1) {
		csm.setSelectionInterval(0, dataTable.getColumnCount() - 1);
		rsm.setSelectionInterval(index0, index1);
	}

	@Override
	public void addSelectionInterval(int index0, int index1) {
		csm.setSelectionInterval(0, dataTable.getColumnCount() - 1);
		rsm.addSelectionInterval(index0, index1);
	}

	@Override
	public void removeSelectionInterval(int index0, int index1) {
		rsm.removeSelectionInterval(index0, index1);
	}

	@Override
	public int getMinSelectionIndex() {
		return rsm.getMinSelectionIndex();
	}

	@Override
	public int getMaxSelectionIndex() {
		return rsm.getMaxSelectionIndex();
	}

	@Override
	public boolean isSelectedIndex(int index) {
		return rsm.isSelectedIndex(index);
	}

	@Override
	public int getAnchorSelectionIndex() {
		return rsm.getAnchorSelectionIndex();
	}

	@Override
	public void setAnchorSelectionIndex(int anchorIndex) {
		csm.setSelectionInterval(0, dataTable.getColumnCount() - 1);
		rsm.setAnchorSelectionIndex(anchorIndex);
	}

	@Override
	public int getLeadSelectionIndex() {
		return rsm.getLeadSelectionIndex();
	}

	@Override
	public void setLeadSelectionIndex(int leadIndex) {
		csm.setSelectionInterval(0, dataTable.getColumnCount() - 1);
		rsm.setLeadSelectionIndex(leadIndex);
	}

	@Override
	public void clearSelection() {
		rsm.clearSelection();
	}

	@Override
	public boolean isSelectionEmpty() {
		return rsm.isSelectionEmpty();
	}

	@Override
	public void insertIndexInterval(int index, int length, boolean before) {
		csm.setSelectionInterval(0, dataTable.getColumnCount() - 1);
		rsm.insertIndexInterval(index, length, before);
	}

	@Override
	public void removeIndexInterval(int index0, int index1) {
		rsm.removeIndexInterval(index0, index1);
	}

	@Override
	public void setValueIsAdjusting(boolean isAdjusting) {
		rsm.setValueIsAdjusting(isAdjusting);
	}

	@Override
	public boolean getValueIsAdjusting() {
		return rsm.getValueIsAdjusting();
	}

	@Override
	public void setSelectionMode(int selectionMode) {
		rsm.setSelectionMode(selectionMode);
	}

	@Override
	public int getSelectionMode() {
		return rsm.getSelectionMode();
	}

	@Override
	public void addListSelectionListener(ListSelectionListener x) {
		rsm.addListSelectionListener(x);
	}

	@Override
	public void removeListSelectionListener(ListSelectionListener x) {
		rsm.removeListSelectionListener(x);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		int fi = e.getFirstIndex(), li = e.getLastIndex();
		super.removeSelectionInterval(fi, li);
		for (int i = fi; i <= li; i++) {
			if (rsm.isSelectedIndex(i)) {
				super.addSelectionInterval(i, i);
			}
		}
	}
}