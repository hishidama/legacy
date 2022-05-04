package jp.hishidama.swing.tree;

/**
 * オープン処理可能ノード.
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/java/swing/JTree.html"
 *         >ひしだま</a>
 * @since 2009.03.20
 * @see LazyTree
 */
public interface OpenableTreeNode {

	/**
	 * オープン処理.
	 *<p>
	 * ダブルクリックやEnterキー押下によって何らかの処理を行う際に呼ばれる。
	 * </p>
	 */
	public void doOpen();
}
