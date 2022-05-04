package jp.hishidama.swing.undo;

import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoManager;
import javax.swing.undo.UndoableEdit;
import javax.swing.undo.UndoableEditSupport;

/**
 * �g��UndoableEditSupport.
 * <ul>
 * <li>UNDO�ʒm�����@�\</li>
 * </ul>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/swing/UndoManager.html"
 *         >�Ђ�����</a>
 * @since 2009.04.26
 */
public class ExUndoableEditSupport extends UndoableEditSupport {

	/** �R���X�g���N�^�[. */
	public ExUndoableEditSupport() {
		super();
	}

	/**
	 * �R���X�g���N�^�[.
	 *
	 * @param r
	 *            �I�u�W�F�N�g
	 */
	public ExUndoableEditSupport(Object r) {
		super(r);
	}

	protected int ignoreLevel = 0;

	/**
	 * UNDO�ʒm�����̊J�n.
	 */
	public synchronized void beginIgnore() {
		ignoreLevel++;
	}

	/**
	 * UNDO�ʒm�����̏I��.
	 */
	public synchronized void endIgnore() {
		ignoreLevel--;
	}

	/**
	 * UNDO�ʒm���������ǂ�����Ԃ�.
	 *
	 * @return �ʒm�������̏ꍇ�Atrue
	 */
	public boolean isIgnore() {
		return ignoreLevel > 0;
	}

	/**
	 * UNDO�������̏ꍇ�A�ʒm�͍s��Ȃ��B
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
	 * UNDO�}�l�[�W���[����ɂ���.
	 * <p>
	 * �o�^����Ă��郊�X�i�[��UndoManager�̂Ƃ��A����discardAllEdits()���Ăяo���B
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
