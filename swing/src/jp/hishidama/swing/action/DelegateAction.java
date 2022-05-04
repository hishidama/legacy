package jp.hishidama.swing.action;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;
import javax.swing.Action;

/**
 * 委譲アクション.
 * <p>
 * 対象コンポーネントにアクションを委譲する。
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/java/swing/JPopupMenu.html"
 *         >ひしだま</a>
 * @since 2009.04.19
 */
public class DelegateAction extends AbstractAction {
	private static final long serialVersionUID = -8684123884513557405L;

	protected Component component;
	protected Action action;

	/**
	 * コンストラクター.
	 *
	 *@param c
	 *            処理対象コンポーネント
	 * @param action
	 *            委譲先アクション
	 */
	public DelegateAction(Component c, Action action) {
		this.component = c;
		this.action = action;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		ActionEvent ae = new ActionEvent(component, e.getID(), e
				.getActionCommand(), e.getWhen(), e.getModifiers());
		action.actionPerformed(ae);
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
