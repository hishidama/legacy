package jp.hishidama.swing.tree;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * 遅延展開ツリーノード.
 *<p>
 * インスタンス生成時には子ノード一覧の作成を行わず、ツリーを展開する際に作成する。
 * </p>
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/java/swing/JTree.html"
 *         >ひしだま</a>
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
	 * 子ノードにデータを追加する.
	 * <p>
	 * 一度追加されている場合は実際の追加処理は行わない。
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
	 * 子ノードにデータを追加する.
	 *
	 * @see #addChildNodes()
	 */
	protected abstract void addChildNodesImpl();
}
