package jp.hishidama.swing.action;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;
import javax.swing.Action;

/**
 * �Ϗ��A�N�V����.
 * <p>
 * �ΏۃR���|�[�l���g�ɃA�N�V�������Ϗ�����B
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/java/swing/JPopupMenu.html"
 *         >�Ђ�����</a>
 * @since 2009.04.19
 */
public class DelegateAction extends AbstractAction {
	private static final long serialVersionUID = -8684123884513557405L;

	protected Component component;
	protected Action action;

	/**
	 * �R���X�g���N�^�[.
	 *
	 *@param c
	 *            �����ΏۃR���|�[�l���g
	 * @param action
	 *            �Ϗ���A�N�V����
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
