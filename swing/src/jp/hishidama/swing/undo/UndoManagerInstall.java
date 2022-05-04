package jp.hishidama.swing.undo;

import javax.swing.text.JTextComponent;

/**
 * �C���X�g�[�����\�b�h���܂�UndoManager
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/swing.html"
 *         >�Ђ�����</a>
 * @since 2009.04.12
 * @see jp.hishidama.swing.text.TextManager
 */
public interface UndoManagerInstall {
	/**
	 * ������.
	 * <p>
	 * JTextComponent�ɓ�UndoManager��o�^����B
	 * </p>
	 *
	 * @param tc
	 *            �e�L�X�g�R���|�[�l���g�iJEditorPane�EJTextField�EJTextArea���j
	 */
	public void install(JTextComponent tc);

	/**
	 * �I��.
	 * <p>
	 * JTextComponent���瓖UndoManager���폜����B
	 * </p>
	 *
	 * @param tc
	 *            �e�L�X�g�R���|�[�l���g�iJEditorPane�EJTextField�EJTextArea���j
	 */
	public void uninstall(JTextComponent tc);
}
