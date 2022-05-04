package jp.hishidama.swing.popup;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 * ポップアップメニュー用委譲アクション.
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/java/swing/JPopupMenu.html"
 *         >ひしだま</a>
 * @since 2009.04.11
 */
public class PopupMenuDelegateAction extends AbstractAction {
	private static final long serialVersionUID = -69443095646469128L;

	protected Action action;

	/**
	 * コンストラクター.
	 *
	 * @param action
	 *            委譲先アクション
	 */
	public PopupMenuDelegateAction(Action action) {
		this.action = action;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Component c = getTargetComponent(e.getSource());
		if (c != null) {
			ActionEvent ae = new ActionEvent(c, e.getID(),
					e.getActionCommand(), e.getWhen(), e.getModifiers());
			action.actionPerformed(ae);
		}
	}

	protected Component getTargetComponent(Object c) {
		while (c != null) {
			if (c instanceof JPopupMenu) {
				JPopupMenu pmenu = (JPopupMenu) c;
				c = pmenu.getInvoker();
				continue;
			}
			if (c instanceof JMenuItem) {
				JMenuItem mi = (JMenuItem) c;
				c = mi.getParent();
				continue;
			}
			if (c instanceof Component) {
				return (Component) c;
			}
			break;
		}
		return null;
	}

	public Object getValue(String key) {
		return action.getValue(key);
	}

	public void putValue(String key, Object value) {
		action.putValue(key, value);
	}

	public void setEnabled(boolean b) {
		action.setEnabled(b);
	}

	public boolean isEnabled() {
		return action.isEnabled();
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		action.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		action.removePropertyChangeListener(listener);
	}
}
