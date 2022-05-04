package jp.hishidama.swing.undo;

import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoManager;
import javax.swing.undo.UndoableEdit;
import javax.swing.undo.UndoableEditSupport;

/**
 * 拡張UndoableEditSupport.
 * <ul>
 * <li>UNDO通知無視機能</li>
 * </ul>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/swing/UndoManager.html"
 *         >ひしだま</a>
 * @since 2009.04.26
 */
public class ExUndoableEditSupport extends UndoableEditSupport {

	/** コンストラクター. */
	public ExUndoableEditSupport() {
		super();
	}

	/**
	 * コンストラクター.
	 *
	 * @param r
	 *            オブジェクト
	 */
	public ExUndoableEditSupport(Object r) {
		super(r);
	}

	protected int ignoreLevel = 0;

	/**
	 * UNDO通知無視の開始.
	 */
	public synchronized void beginIgnore() {
		ignoreLevel++;
	}

	/**
	 * UNDO通知無視の終了.
	 */
	public synchronized void endIgnore() {
		ignoreLevel--;
	}

	/**
	 * UNDO通知無視中かどうかを返す.
	 *
	 * @return 通知無視中の場合、true
	 */
	public boolean isIgnore() {
		return ignoreLevel > 0;
	}

	/**
	 * UNDO無視中の場合、通知は行わない。
	 *
	 * @see #beginIgnore()
	 */
	@Override
	protected void _postEdit(UndoableEdit e) {
		if (ignoreLevel == 0) {
			super._postEdit(e);
		}
	}

	/**
	 * UNDOマネージャーを空にする.
	 * <p>
	 * 登録されているリスナーがUndoManagerのとき、そのdiscardAllEdits()を呼び出す。
	 * </p>
	 *
	 * @see UndoManager#discardAllEdits()
	 */
	public void discardAllEdits() {
		for (UndoableEditListener l : listeners) {
			if (l instanceof UndoManager) {
				UndoManager um = (UndoManager) l;
				um.discardAllEdits();
			}
		}
	}
}
