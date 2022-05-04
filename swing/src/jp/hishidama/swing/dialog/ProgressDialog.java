package jp.hishidama.swing.dialog;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.UIManager;

import jp.hishidama.swing.action.ExAction;

/**
 * プログレスバーを表示するダイアログ.
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/swing/ProgressDialog.html"
 *         >ひしだま</a>
 * @since 2009.11.01
 */
public class ProgressDialog extends JDialog implements Runnable {
	private static final long serialVersionUID = -7605680076737565525L;

	/** プログレスバー */
	protected JProgressBar progressBar;

	protected volatile boolean canceled = false;
	protected boolean autoClose = false;

	protected JButton okButton;
	protected JButton cancelButton;

	/**
	 * コンストラクター.
	 *
	 * @param owner
	 *            オーナー
	 * @param modal
	 *            モーダル有無
	 */
	public ProgressDialog(Frame owner, boolean modal) {
		super(owner, modal);
	}

	/**
	 * プログレスバー生成.
	 *
	 * @return プログレスバー
	 */
	protected JProgressBar createProgressBar() {
		return new JProgressBar();
	}

	/**
	 * プログレスバー取得.
	 *
	 * @return プログレスバー
	 */
	public JProgressBar getProgressBar() {
		return progressBar;
	}

	/**
	 * プログレスバー現在値設定.
	 *
	 * @param n
	 *            進捗バーの現在の値
	 */
	public void setProgressValue(int n) {
		progressBar.setValue(n);
	}

	/**
	 * プログレスバー現在値取得.
	 *
	 * @return 進捗バーの現在の値
	 */
	public int getProgressValue() {
		return progressBar.getValue();
	}

	/**
	 * 自動クローズ設定.
	 *
	 * @param b
	 *            true：進捗終了時に当ダイアログを自動的に閉じる
	 */
	public void setAutoClose(boolean b) {
		this.autoClose = b;
	}

	/**
	 * 自動クローズ取得.
	 *
	 * @return true：進捗終了時に当ダイアログを自動的に閉じる
	 */
	public boolean isAutoClose() {
		return autoClose;
	}

	@Override
	public void dispose() {
		canceled = true;
		super.dispose();
	}

	/**
	 * 終了有無取得.
	 * <p>
	 * 主にキャンセルボタンによってキャンセルされたかどうかの判定に使用する。<br>
	 * ダイアログ自体が終了した際もtrueとなる。
	 * </p>
	 *
	 * @return true：進捗が終了している
	 */
	public boolean canceled() {
		return canceled;
	}

	/**
	 * 進捗終了設定.
	 * <p>
	 * スレッド処理が終了した際に当メソッドを呼び出さなければならない。
	 * </p>
	 */
	public synchronized void progressEnd() {
		if (progressBar.isIndeterminate()) { // 不確定モード
			progressBar.setIndeterminate(false);
		}
		if (okButton != null) {
			okButton.setEnabled(true);
		}
		if (cancelButton != null) {
			cancelButton.setEnabled(false);
		}
		if (autoClose) {
			dispose();
		}
	}

	/**
	 * ダイアログ初期化.
	 */
	public void init() {
		canceled = false;
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		Container c = getContentPane();
		initProgressPane(c);
		initCenterPane(c);
		initButtonPane(c);

		pack();
		setLocationRelativeTo(null);
	}

	protected void initProgressPane(Container c) {
		JPanel panel = new JPanel();
		progressBar = createProgressBar();
		panel.add(progressBar);
		c.add(panel, BorderLayout.PAGE_START);
	}

	protected void initCenterPane(Container c) {
	}

	protected void initButtonPane(Container c) {
		okButton = createOkButton();
		cancelButton = createCancelButton();
		if (okButton != null || cancelButton != null) {
			JPanel panel = new JPanel(new FlowLayout());
			if (okButton != null) {
				panel.add(okButton);
			}
			if (cancelButton != null) {
				panel.add(cancelButton);
			}
			c.add(panel, BorderLayout.PAGE_END);
		}
	}

	/**
	 * OKボタン生成.
	 *
	 * @return OKボタン
	 */
	protected JButton createOkButton() {
		String text = UIManager.getString("OptionPane.okButtonText");
		JButton button = new JButton(new ProgressDisposeAction(text));
		button.setEnabled(false);
		return button;
	}

	/**
	 * CANCELボタン生成.
	 *
	 * @return CANCELボタン
	 */
	protected JButton createCancelButton() {
		String text = UIManager.getString("OptionPane.cancelButtonText");
		JButton button = new JButton(new ProgressCancelAction(text));
		return button;
	}

	protected class ProgressDisposeAction extends ExAction {
		private static final long serialVersionUID = -1756791747373917769L;

		public ProgressDisposeAction(String name) {
			super(name);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	}

	protected class ProgressCancelAction extends ExAction {
		private static final long serialVersionUID = -1756791747373917769L;

		public ProgressCancelAction(String name) {
			super(name);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			canceled = true;
			progressEnd();
		}
	}

	/**
	 * ダイアログ表示設定.
	 * <p>
	 * trueにすると、スレッド（{@link #run()}）も実行開始する。<br>
	 * また、モーダルダイアログの場合、スレッドの終了も待つ。
	 * </p>
	 *
	 * @param b
	 *            true：ダイアログを表示する
	 */
	@Override
	public void setVisible(boolean b) {
		if (b) {
			Thread thread = new Thread(this);
			thread.start();
			super.setVisible(true);
			if (isModal()) {
				try {
					thread.join();
				} catch (InterruptedException e) {
				}
			}
		} else {
			super.setVisible(false);
		}
	}

	/**
	 * スレッド処理.
	 * <p>
	 * 当メソッドをオーバーライドして、実際の処理を行う。
	 * </p>
	 *
	 * <pre>
	 * // 例
	 * &#064;Override
	 * public void run() {
	 *  for (int i = 0; i &lt; 100; i++) {
	 *   if ({@link #canceled()}) {
	 *    return;
	 *   }
	 *   {@link #setProgressValue}(i + 1);
	 *  }
	 *  {@link #progressEnd()};
	 * }
	 * </pre>
	 */
	@Override
	public void run() {
		progressEnd();
	}
}
