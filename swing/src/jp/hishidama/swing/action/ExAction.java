package jp.hishidama.swing.action;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.KeyStroke;

/**
 * @since 2009.04.05
 */
public abstract class ExAction extends AbstractAction {
	private static final long serialVersionUID = 8340351742780056052L;

	public ExAction() {
		super();
	}

	public ExAction(String name) {
		super(name);
	}

	public ExAction(String name, Icon icon) {
		super(name, icon);
	}

	/**
	 *
	 * @return 名前
	 * @see #NAME
	 */
	public String getName() {
		return (String) getValue(NAME);
	}

	/**
	 *
	 * @return 小アイコン
	 * @see #SMALL_ICON
	 */
	public Icon getSamllIcon() {
		return (Icon) getValue(SMALL_ICON);
	}

	/**
	 *
	 * @param key
	 * @see #MNEMONIC_KEY
	 */
	public void putMnemonicKey(int key) {
		putValue(MNEMONIC_KEY, key);
	}

	/**
	 *
	 * @return ニーモニックキー
	 * @see #MNEMONIC_KEY
	 */
	public int getMnemonicKey() {
		Integer key = (Integer) getValue(MNEMONIC_KEY);
		if (key == null) {
			return 0;
		}
		return key.intValue();
	}

	/**
	 *
	 * @param n
	 * @see #DISPLAYED_MNEMONIC_INDEX_KEY
	 */
	public void putMnemonicIndexKey(int n) {
		putValue(DISPLAYED_MNEMONIC_INDEX_KEY, n);
	}

	/**
	 *
	 * @return ニーモニックキーの下線の位置
	 * @see #DISPLAYED_MNEMONIC_INDEX_KEY
	 */
	public int getMnemonicIndexKey() {
		Integer n = (Integer) getValue(DISPLAYED_MNEMONIC_INDEX_KEY);
		if (n == null) {
			return 0;
		}
		return n.intValue();
	}

	/**
	 *
	 * @param ks
	 * @see #ACCELERATOR_KEY
	 */
	public void putAcceleratorKey(KeyStroke ks) {
		putValue(ACCELERATOR_KEY, ks);
	}

	/**
	 *
	 * @return アクセラレーターキー
	 * @see #ACCELERATOR_KEY
	 */
	public KeyStroke getAcceleratorKey() {
		return (KeyStroke) getValue(ACCELERATOR_KEY);
	}
}
