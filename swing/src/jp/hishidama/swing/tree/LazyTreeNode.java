package jp.hishidama.swing.tree;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * �x���W�J�c���[�m�[�h.
 *<p>
 * �C���X�^���X�������ɂ͎q�m�[�h�ꗗ�̍쐬���s�킸�A�c���[��W�J����ۂɍ쐬����B
 * </p>
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/java/swing/JTree.html"
 *         >�Ђ�����</a>
 * @since 2009.03.20
 * @see LazyTree
 */
public abstract class LazyTreeNode extends DefaultMutableTreeNode {
	private static final long serialVersionUID = -1093870822329983065L;

	protected boolean added = false;

	public LazyTreeNode(Object userObject) {
		super(userObject);
	}

	@Override
	public boolean isLeaf() {
		return false;
	}

	@Override
	public void removeAllChildren() {
		super.removeAllChildren();
		added = false;
	}

	/**
	 * �q�m�[�h�Ƀf�[�^��ǉ�����.
	 * <p>
	 * ��x�ǉ�����Ă���ꍇ�͎��ۂ̒ǉ������͍s��Ȃ��B
	 * </p>
	 *
	 * @see #addChildNodesImpl()
	 */
	public void addChildNodes() {
		if (!added) {
			addChildNodesImpl();
			added = true;
		}
	}

	/**
	 * �q�m�[�h�Ƀf�[�^��ǉ�����.
	 *
	 * @see #addChildNodes()
	 */
	protected abstract void addChildNodesImpl();
}
