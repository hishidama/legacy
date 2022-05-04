package jp.hishidama.swing.table;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.util.Date;
import java.util.EventObject;

import javax.swing.Action;
import javax.swing.DefaultCellEditor;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.undo.UndoManager;

import jp.hishidama.swing.table.editor.BooleanEditor;
import jp.hishidama.swing.table.editor.DateEditor;
import jp.hishidama.swing.table.editor.IntEditor;
import jp.hishidama.swing.table.renderer.BooleanRenderer;
import jp.hishidama.swing.undo.ExUndoableEditSupport;
import jp.hishidama.swing.undo.UndoManagerUtil;

/**
 * 拡張JTable.
 *
 *<ul>
 *<li>デフォルトエディターにUNDO機能を追加する。</li>
 *<li>{@link DateEditor 日付エディター}を使用する。</li>
 *<li>列ヘッダー境界をダブルクリックした時に列幅を自動的に調節する。</li>
 *<li>セル・行追加削除のUNDO機能を追加する。</li>
 *</ul>
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/java/swing/JTable.html"
 *         >ひしだま</a>
 * @since 2009.03.22
 * @version 2009.04.26 セル・行操作UNDO機能を追加
 */
public class ExTable extends JTable {
	private static final long serialVersionUID = -1061444858541185937L;

	public ExTable() {
		super();
		initializeUndoSupport();
	}

	public ExTable(TableModel dm, TableColumnModel cm, ListSelectionModel sm) {
		super(dm, cm, sm);
		initializeUndoSupport();
	}

	public ExTable(TableModel dm, TableColumnModel cm) {
		super(dm, cm);
		initializeUndoSupport();
	}

	public ExTable(TableModel dm) {
		super(dm);
		initializeUndoSupport();
	}

	protected UndoManagerUtil umu = null;

	protected void initializeUndoSupport() {
		TableModel model = getModel();
		if (model instanceof ExTableModel) {
			ExTableModel emodel = (ExTableModel) model;
			ExUndoableEditSupport undoSupport = emodel.getUndoableEditSupport();
			if (undoSupport != null) {
				if (umu == null) {
					umu = createUndoManagerUtil();
				}
				umu.installTo(this, undoSupport);
			}
		}
	}

	/**
	 * ポップアップメニューにUNDO/REDOを追加する.
	 *
	 * @param pmenu
	 *            ポップアップメニュー
	 */
	public void addMenuUndo(JPopupMenu pmenu) {
		if (umu == null) {
			umu = createUndoManagerUtil();
		}
		umu.addMenuUndo(pmenu);
	}

	protected UndoManagerUtil createUndoManagerUtil() {
		return new UndoManagerUtil();
	}

	/**
	 * UNDO集約開始.
	 *
	 * @see javax.swing.undo.UndoableEditSupport#beginUpdate()
	 */
	public void beginUndoUpdate() {
		TableModel model = getModel();
		if (model instanceof ExTableModel) {
			ExUndoableEditSupport us = ((ExTableModel) model)
					.getUndoableEditSupport();
			if (us != null) {
				us.beginUpdate();
			}
		}
	}

	/**
	 * UNDO集約終了.
	 *
	 * @see javax.swing.undo.UndoableEditSupport#endUpdate()
	 */
	public void endUndoUpdate() {
		TableModel model = getModel();
		if (model instanceof ExTableModel) {
			ExUndoableEditSupport us = ((ExTableModel) model)
					.getUndoableEditSupport();
			if (us != null) {
				us.endUpdate();
			}
		}
	}

	@Override
	protected JTableHeader createDefaultTableHeader() {
		return new FitHeader(getColumnModel());
	}

	@Override
	public FitHeader getTableHeader() {
		return (FitHeader) super.getTableHeader();
	}

	@Override
	protected void createDefaultRenderers() {
		super.createDefaultRenderers();

		// boolean
		setDefaultRenderer(boolean.class, new BooleanRenderer());
	}

	@Override
	protected void createDefaultEditors() {
		super.createDefaultEditors();

		DefaultCellEditor editor = (DefaultCellEditor) getDefaultEditor(Object.class);
		installUndoManager(editor);
		editor = (DefaultCellEditor) getDefaultEditor(Number.class);
		installUndoManager(editor);

		// int
		editor = new IntEditor();
		setDefaultEditor(int.class, editor);

		// boolean
		editor = new BooleanEditor();
		setDefaultEditor(boolean.class, editor);

		// 日付系
		setDefaultEditor(Date.class, new DateEditor());
		setDefaultEditor(java.sql.Date.class, new DateEditor());
		setDefaultEditor(java.sql.Time.class, new DateEditor("HH:mm:ss"));
		setDefaultEditor(java.sql.Timestamp.class, new DateEditor(
				"yyyy/MM/dd HH:mm:ss.SSS"));
	}

	private UndoManager editorum;

	/**
	 * セルエディターにUndoManagerを登録する。
	 *
	 * @param editor
	 *            デフォルトセルエディター
	 */
	protected final void installUndoManager(DefaultCellEditor editor) {
		if (editorum == null) {
			editorum = createCellEditorUndoManager();
		}

		JTextField tf = (JTextField) editor.getComponent();
		new UndoManagerUtil(editorum).installTo(tf);

		tf.addAncestorListener(new AncestorListener() {

			@Override
			public void ancestorAdded(AncestorEvent event) {
				// System.out.println("ancestorAdded: " + event);
				editorum.discardAllEdits();
			}

			@Override
			public void ancestorMoved(AncestorEvent event) {
				// System.out.println("ancestorMoved: " + event);
			}

			@Override
			public void ancestorRemoved(AncestorEvent event) {
				// System.out.println("ancestorRemoved: " + event);
			}
		});
	}

	protected UndoManager createCellEditorUndoManager() {
		return new UndoManager();
	}

	/**
	 * 直接キー入力から始めた場合に既存文字列をクリアする。
	 */
	@Override
	public boolean editCellAt(int row, int column, EventObject e) {
		// Ctrlキーが押されている時は編集を開始しない
		if (e instanceof KeyEvent) {
			KeyEvent ke = (KeyEvent) e;
			if (ke.isControlDown()) {
				return false;
			}
		}

		boolean r = super.editCellAt(row, column, e);
		if (r) {
			if (e instanceof KeyEvent) {
				KeyEvent ke = (KeyEvent) e;
				char ch = ke.getKeyChar();
				if (!Character.isISOControl(ch)) { // 普通の文字の場合
					Component ec = getEditorComponent();
					if (ec instanceof JTextField) {
						JTextField tf = (JTextField) ec;
						tf.selectAll();// 既存文字列を全選択
					}
				}
			}
		}
		return r;
	}

	/**
	 * キーにアクションを割り当てる。
	 *
	 * @param a
	 * @see Action#ACCELERATOR_KEY
	 */
	protected void setKeyAction(Action a) {
		KeyStroke key = (KeyStroke) a.getValue(Action.ACCELERATOR_KEY);
		setKeyAction(key, a);
	}

	/**
	 * キーにアクションを割り当てる。
	 *
	 * @param key
	 * @param a
	 */
	protected void setKeyAction(KeyStroke key, Action a) {
		registerKeyboardAction(a, key, WHEN_IN_FOCUSED_WINDOW);
		registerKeyboardAction(a, key, WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
	}
}
