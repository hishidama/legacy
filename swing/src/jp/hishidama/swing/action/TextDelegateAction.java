package jp.hishidama.swing.action;

import javax.swing.Action;
import javax.swing.text.JTextComponent;

/**
 * テキストコンポーネント用委譲アクション.
 * <p>
 * 対象テキストコンポーネントが編集不可あるいは使用不可の場合にアクションを使用不可とする。
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/java/swing/JPopupMenu.html"
 *         >ひしだま</a>
 * @since 2009.04.19
 */
public class TextDelegateAction extends DelegateAction {
	private static final long serialVersionUID = -5945792901022724790L;

	/**
	 * コンストラクター.
	 *
	 * @param tc
	 *            処理対象テキストコンポーネント
	 * @param action
	 *            委譲先アクション
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
