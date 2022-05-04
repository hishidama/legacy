package jp.hishidama.swing.action;

import javax.swing.Action;
import javax.swing.text.JTextComponent;

/**
 * �e�L�X�g�R���|�[�l���g�p�Ϗ��A�N�V����.
 * <p>
 * �Ώۃe�L�X�g�R���|�[�l���g���ҏW�s���邢�͎g�p�s�̏ꍇ�ɃA�N�V�������g�p�s�Ƃ���B
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/java/swing/JPopupMenu.html"
 *         >�Ђ�����</a>
 * @since 2009.04.19
 */
public class TextDelegateAction extends DelegateAction {
	private static final long serialVersionUID = -5945792901022724790L;

	/**
	 * �R���X�g���N�^�[.
	 *
	 * @param tc
	 *            �����Ώۃe�L�X�g�R���|�[�l���g
	 * @param action
	 *            �Ϗ���A�N�V����
	 */
	public TextDelegateAction(JTextComponent tc, Action action) {
		super(tc, action);
	}

	@Override
	public boolean isEnabled() {
		if (!action.isEnabled()) {
			return false;
		}
		JTextComponent tc = (JTextComponent) super.component;
		if (!tc.isEnabled()) {
			return false;
		}
		if (!tc.isEditable()) {
			return false;
		}
		return true;
	}
}
