package jp.hishidama.swing.tree;

/**
 * �I�[�v�������\�m�[�h.
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/java/swing/JTree.html"
 *         >�Ђ�����</a>
 * @since 2009.03.20
 * @see LazyTree
 */
public interface OpenableTreeNode {

	/**
	 * �I�[�v������.
	 *<p>
	 * �_�u���N���b�N��Enter�L�[�����ɂ���ĉ��炩�̏������s���ۂɌĂ΂��B
	 * </p>
	 */
	public void doOpen();
}
