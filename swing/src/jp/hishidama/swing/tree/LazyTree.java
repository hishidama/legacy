package jp.hishidama.swing.tree;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

/**
 * íxâÑìWäJÉmÅ[ÉhÇàµÇ§JTree.
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/java/swing/JTree.html"
 *         >Ç–ÇµÇæÇ‹</a>
 * @since 2009.03.20
 */
public class LazyTree extends JTree implements TreeWillExpandListener,
		KeyListener, MouseListener {
	private static final long serialVersionUID = 8874292428617581358L;

	public LazyTree() {
		this(new DefaultMutableTreeNode());
	}

	public LazyTree(TreeNode root) {
		super(root);
		addTreeWillExpandListener(this);
		addKeyListener(this);
		addMouseListener(this);
	}

	@Override
	public DefaultTreeModel getModel() {
		return (DefaultTreeModel) super.getModel();
	}

	// TreeWillExpandListener
	@Override
	public void treeWillExpand(TreeExpansionEvent event)
			throws ExpandVetoException {
		TreePath path = event.getPath();
		Object obj = path.getLastPathComponent(); // àÍî‘ññí[ÇÃ(ç°âÒäJÇ±Ç§Ç∆ÇµÇƒÇ¢ÇÈ)ÉmÅ[Éh
		if (obj instanceof LazyTreeNode) {
			LazyTreeNode node = (LazyTreeNode) obj;
			node.addChildNodes();
		}
	}

	@Override
	public void treeWillCollapse(TreeExpansionEvent event)
			throws ExpandVetoException {
	}

	// KeyListener
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_F5) {
			TreePath[] ps = getSelectionPaths();
			if (ps == null || ps.length <= 0) {
				ps = new TreePath[] { new TreePath(getModel().getRoot()) };
			}
			DefaultTreeModel model = getModel();
			for (TreePath path : ps) {
				Object obj = path.getLastPathComponent();
				if (obj instanceof LazyTreeNode) {
					LazyTreeNode node = (LazyTreeNode) obj;
					node.removeAllChildren();
					node.addChildNodes();
					model.reload(node);
				}
			}
			e.consume();
		}
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			Object obj = getLastSelectedPathComponent();
			if (obj instanceof OpenableTreeNode) {
				OpenableTreeNode node = (OpenableTreeNode) obj;
				node.doOpen();
				e.consume();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	// MouseListener
	@Override
	public void mouseClicked(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2) {
			JTree tree = (JTree) e.getSource();
			TreePath path = tree.getPathForLocation(e.getX(), e.getY());
			if (path != null) {
				Object obj = path.getLastPathComponent();
				if (obj instanceof OpenableTreeNode) {
					OpenableTreeNode node = (OpenableTreeNode) obj;
					node.doOpen();
				}
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}
}
