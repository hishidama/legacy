package jp.hishidama.swing.undo;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.*;
import javax.swing.text.*;
import javax.swing.undo.*;

/**
 * テキストUNDOマネージャー.
 * <p>
 * JTextComponentに文字列入力のUNDO/REDO機能を追加する為のマネージャー。
 * </p>
 * <p>
 * 標準のUndoManagerでは、1文字ずつのUNDO/REDOになる。<br>
 * 当UndoManagerでは、一度の呼び出しで同じ種類（つまり文字の追加・削除）のUNDO/REDOを連続して行うことにより、文字列単位でのUNDO/
 * REDOを実現する。
 * </p>
 *
 * <p>
 * →<a target="hishidama" href=
 * "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/swing/UndoManager.html"
 * >使用例</a>
 * </p>
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/swing.html"
 *         >ひしだま</a>
 * @since 2007.02.24
 * @version 2009.04.12 UndoManagerInstallを追加
 * @version 2009.04.24 CompoundEditを使う実装に変更
 */
public class TextUndoManager extends UndoManager implements
		UndoableEditListener, UndoManagerInstall {
	private static final long serialVersionUID = 5375924557489349842L;

	protected UndoManagerUtil umu;

	/**
	 * 初期化.
	 * <p>
	 * JTextComponentに当UndoManagerを登録する。
	 * </p>
	 *
	 * @param tc
	 *            テキストコンポーネント（JEditorPane・JTextField・JTextArea等）
	 */
	public void install(JTextComponent tc) {
		if (tc == null)
			return;

		if (umu == null) {
			umu = new UndoManagerUtil(this);
		}
		umu.installTo(tc);

		if (handler == null)
			handler = createHandler();
		tc.addKeyListener(handler);
		tc.addMouseListener(handler);
		tc.addFocusListener(handler);
		update();
	}

	/**
	 * 終了.
	 * <p>
	 * JTextComponentから当UndoManagerを削除する。
	 * </p>
	 *
	 * @param tc
	 *            テキストコンポーネント（JEditorPane・JTextField・JTextArea等）
	 */
	public void uninstall(JTextComponent tc) {
		if (tc == null)
			return;

		if (umu != null) {
			umu.uninstall(tc);
			// umu = null;
		} else {
			Document doc = tc.getDocument();
			doc.removeUndoableEditListener(this);
		}

		tc.removeKeyListener(handler);
		tc.removeMouseListener(handler);
		tc.removeFocusListener(handler);
	}

	protected EventHandler handler;

	protected EventHandler createHandler() {
		return new EventHandler();
	}

	@Override
	public void undoableEditHappened(UndoableEditEvent e) {
		super.undoableEditHappened(e);
		update();
	}

	/**
	 * Undo内容更新.
	 * <p>
	 * Undo/Redoメニューに関わる変更が行われたときに呼ばれる。
	 * </p>
	 * <p>
	 * サブクラスでオーバーライドして、Undo/Redoメニューのenabledの更新などを行う。
	 * </p>
	 */
	public void update() {
	}

	@Override
	public synchronized void discardAllEdits() {
		super.discardAllEdits();
		update();
	}

	/**
	 * @see CompoundEdit
	 */
	static class SameEdit extends AbstractUndoableEdit {
		private static final long serialVersionUID = 3096295069062300124L;

		protected boolean inProgress = true;
		protected List<UndoableEdit> edits = new ArrayList<UndoableEdit>();

		public SameEdit(UndoableEdit e) {
			edits.add(e);
		}

		@Override
		public void undo() throws CannotUndoException {
			super.undo();
			for (int i = edits.size() - 1; i >= 0; i--) {
				UndoableEdit e = edits.get(i);
				e.undo();
			}
		}

		@Override
		public void redo() throws CannotRedoException {
			super.redo();
			for (UndoableEdit e : edits) {
				e.redo();
			}
		}

		protected UndoableEdit lastEdit() {
			int count = edits.size();
			return edits.get(count - 1);
		}

		@Override
		public void die() {
			for (int i = edits.size() - 1; i >= 0; i--) {
				UndoableEdit e = edits.get(i);
				e.die();
			}
			super.die();
		}

		@Override
		public boolean addEdit(UndoableEdit anEdit) {
			if (!inProgress) {
				return false;
			}
			UndoableEdit last = lastEdit();
			if (last.getPresentationName().equals(anEdit.getPresentationName())) {
				edits.add(anEdit);
				return true;
			}
			return false;
		}

		public void end() {
			inProgress = false;
		}

		public boolean isInProgress() {
			return inProgress;
		}

		@Override
		public boolean isSignificant() {
			for (UndoableEdit e : edits) {
				if (e.isSignificant()) {
					return true;
				}
			}
			return false;
		}

		@Override
		public String getPresentationName() {
			UndoableEdit last = lastEdit();
			return last.getPresentationName();
		}

		@Override
		public String getUndoPresentationName() {
			UndoableEdit last = lastEdit();
			return last.getUndoPresentationName();
		}

		@Override
		public String getRedoPresentationName() {
			UndoableEdit last = lastEdit();
			return last.getRedoPresentationName();
		}

		@Override
		public String toString() {
			return super.toString() + " inProgress: " + inProgress + " edits: "
					+ edits;
		}
	}

	/**
	 * UNDOリストに区切りを入れる.
	 */
	public void addSeparator() {
		SameEdit last = (SameEdit) lastEdit();
		if (last != null) {
			last.end();
		}
	}

	@Override
	public synchronized boolean addEdit(UndoableEdit anEdit) {
		UndoableEdit last = lastEdit();
		if (last != null && last.addEdit(anEdit)) {
			return true;
		}
		SameEdit se = new SameEdit(anEdit);
		return super.addEdit(se);
	}

	@Override
	public synchronized void undo() throws CannotUndoException {
		addSeparator();
		if (!canUndo())
			return;

		super.undo();

		update();
	}

	@Override
	public synchronized void redo() throws CannotRedoException {
		addSeparator();
		if (!canRedo())
			return;

		super.redo();

		update();
	}

	protected class EventHandler implements KeyListener, MouseListener,
			FocusListener {

		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_Z:
				// zKeyPressed(e);
				break;
			case KeyEvent.VK_Y:
				// yKeyPressed(e);
				break;
			case KeyEvent.VK_UP:
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_HOME:
			case KeyEvent.VK_END:
			case KeyEvent.VK_PAGE_UP:
			case KeyEvent.VK_PAGE_DOWN:
				addSeparator();
				break;
			}
		}

		/**
		 * Zキー処理.
		 * <p>
		 * アクセラレータキー等でUNDOを処理する場合は、当メソッドをオーバーライドして無処理に出来る。
		 * </p>
		 *
		 * @param e
		 *            キーイベント
		 */
		protected void zKeyPressed(KeyEvent e) {
			if (e.isControlDown()) {
				undo();
				e.consume();
			}
		}

		/**
		 * Yキー処理.
		 * <p>
		 * アクセラレータキー等でREDOを処理する場合は、当メソッドをオーバーライドして無処理に出来る。
		 * </p>
		 *
		 * @param e
		 *            キーイベント
		 */
		protected void yKeyPressed(KeyEvent e) {
			if (e.isControlDown()) {
				redo();
				e.consume();
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}

		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// 本来は、テキストのカーソルの位置が変わったときだけ区切りたいが
			// ここでは判断できないので、常に区切る
			addSeparator();
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void focusGained(FocusEvent e) {
			addSeparator();
		}

		@Override
		public void focusLost(FocusEvent e) {
			addSeparator();
		}
	}
}
