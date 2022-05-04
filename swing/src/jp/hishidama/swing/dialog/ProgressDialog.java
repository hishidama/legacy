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
 * �v���O���X�o�[��\������_�C�A���O.
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/swing/ProgressDialog.html"
 *         >�Ђ�����</a>
 * @since 2009.11.01
 */
public class ProgressDialog extends JDialog implements Runnable {
	private static final long serialVersionUID = -7605680076737565525L;

	/** �v���O���X�o�[ */
	protected JProgressBar progressBar;

	protected volatile boolean canceled = false;
	protected boolean autoClose = false;

	protected JButton okButton;
	protected JButton cancelButton;

	/**
	 * �R���X�g���N�^�[.
	 *
	 * @param owner
	 *            �I�[�i�[
	 * @param modal
	 *            ���[�_���L��
	 */
	public ProgressDialog(Frame owner, boolean modal) {
		super(owner, modal);
	}

	/**
	 * �v���O���X�o�[����.
	 *
	 * @return �v���O���X�o�[
	 */
	protected JProgressBar createProgressBar() {
		return new JProgressBar();
	}

	/**
	 * �v���O���X�o�[�擾.
	 *
	 * @return �v���O���X�o�[
	 */
	public JProgressBar getProgressBar() {
		return progressBar;
	}

	/**
	 * �v���O���X�o�[���ݒl�ݒ�.
	 *
	 * @param n
	 *            �i���o�[�̌��݂̒l
	 */
	public void setProgressValue(int n) {
		progressBar.setValue(n);
	}

	/**
	 * �v���O���X�o�[���ݒl�擾.
	 *
	 * @return �i���o�[�̌��݂̒l
	 */
	public int getProgressValue() {
		return progressBar.getValue();
	}

	/**
	 * �����N���[�Y�ݒ�.
	 *
	 * @param b
	 *            true�F�i���I�����ɓ��_�C�A���O�������I�ɕ���
	 */
	public void setAutoClose(boolean b) {
		this.autoClose = b;
	}

	/**
	 * �����N���[�Y�擾.
	 *
	 * @return true�F�i���I�����ɓ��_�C�A���O�������I�ɕ���
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
	 * �I���L���擾.
	 * <p>
	 * ��ɃL�����Z���{�^���ɂ���ăL�����Z�����ꂽ���ǂ����̔���Ɏg�p����B<br>
	 * �_�C�A���O���̂��I�������ۂ�true�ƂȂ�B
	 * </p>
	 *
	 * @return true�F�i�����I�����Ă���
	 */
	public boolean canceled() {
		return canceled;
	}

	/**
	 * �i���I���ݒ�.
	 * <p>
	 * �X���b�h�������I�������ۂɓ����\�b�h���Ăяo���Ȃ���΂Ȃ�Ȃ��B
	 * </p>
	 */
	public synchronized void progressEnd() {
		if (progressBar.isIndeterminate()) { // �s�m�胂�[�h
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
	 * �_�C�A���O������.
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
	 * OK�{�^������.
	 *
	 * @return OK�{�^��
	 */
	protected JButton createOkButton() {
		String text = UIManager.getString("OptionPane.okButtonText");
		JButton button = new JButton(new ProgressDisposeAction(text));
		button.setEnabled(false);
		return button;
	}

	/**
	 * CANCEL�{�^������.
	 *
	 * @return CANCEL�{�^��
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
	 * �_�C�A���O�\���ݒ�.
	 * <p>
	 * true�ɂ���ƁA�X���b�h�i{@link #run()}�j�����s�J�n����B<br>
	 * �܂��A���[�_���_�C�A���O�̏ꍇ�A�X���b�h�̏I�����҂B
	 * </p>
	 *
	 * @param b
	 *            true�F�_�C�A���O��\������
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
	 * �X���b�h����.
	 * <p>
	 * �����\�b�h���I�[�o�[���C�h���āA���ۂ̏������s���B
	 * </p>
	 *
	 * <pre>
	 * // ��
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
