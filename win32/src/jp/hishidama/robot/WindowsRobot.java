package jp.hishidama.robot;

import java.awt.Rectangle;
import java.util.List;

import jp.hishidama.win32.JWnd;
import jp.hishidama.win32.api.WinUserConst;

/**
 * MS-Windows�E�B���h�E�������s�N���X.
 * <p>
 * MS-Windows�̃E�B���h�E�𑀍삷�郆�[�e�B���e�B�[�N���X�B<br> ��<a target="hishidama"
 * href="http://www.ne.jp/asahi/hishidama/home/soft/java/hmwin32.html">�g�p��</a>
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/hmwin32.html">�Ђ�����</a>
 */
public class WindowsRobot implements WinUserConst {

	/**
	 * �w�莞�ԑҋ@.
	 * 
	 * @param time
	 *            �E�F�C�g[�~���b]
	 */
	public void delay(int time) {
		if (time <= 0)
			return;
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
		}
	}

	/**
	 * ����ΏۃE�B���h�E
	 */
	protected JWnd wnd;

	/**
	 * �������s��̃E�F�C�g[�~���b]
	 */
	protected int wait;

	/**
	 * �E�B���h�E�T��.
	 * <p>
	 * �^�C�g���̈ꕔ����v����E�B���h�E��T���B<br>
	 * ������Ȃ��ꍇ�A�w�肳�ꂽ���Ԃ����X���[�v���A�ēx�T������B<br>
	 * �w�肳�ꂽ�񐔂������g���C����B
	 * </p>
	 * 
	 * @param title
	 *            �^�C�g��
	 * @param time
	 *            �X���[�v����[�~���b]
	 * @param count
	 *            ���g���C��
	 * @return �E�B���h�E�i������Ȃ��ꍇ�Anull�j
	 */
	public JWnd findWindow(String title, int time, int count) {
		for (; count >= 0; count--) {
			List list = JWnd.enumWindows();
			for (int i = 0; i < list.size(); i++) {
				JWnd wnd = (JWnd) list.get(i);
				if (isTarget(wnd, title)) {
					return wnd;
				}
			}
			if (count > 0) {
				delay(time);
			}
		}
		return null;
	}

	/**
	 * ����ΏۃE�B���h�E�ݒ�.
	 * 
	 * @param wnd
	 *            JWnd
	 */
	public void setJWnd(JWnd wnd) {
		this.wnd = wnd;
	}

	/**
	 * ����ΏۃE�B���h�E�擾.
	 * 
	 * @return JWnd
	 */
	public JWnd getJWnd() {
		return wnd;
	}

	/**
	 * �ҋ@���Ԑݒ�.
	 * <p>
	 * ���Ԃ̂�����E�B���h�E������Ăяo���e���\�b�h�ł́A�����������ɃE�F�C�g����B<br>
	 * ���̎��Ԃ𓖃��\�b�h�Őݒ肷��B
	 * </p>
	 * 
	 * @param ms
	 *            �E�F�C�g[�~���b]
	 */
	public void setAutoDelay(int ms) {
		this.wait = ms;
	}

	/**
	 * �ҋ@���Ԏ擾.
	 * 
	 * @return �E�F�C�g[�~���b]
	 */
	public int getAutoDelay() {
		return wait;
	}

	/**
	 * �^�C�g���ύX�҂�.
	 * <p>
	 * �E�B���h�E�̃^�C�g���̈ꕔ����v����܂ő҂B<br>
	 * IE��e�L�X�g�G�f�B�^���́A�E�B���h�E�̃^�C�g�����ς��ꍇ�� �����҂ۂɎg�����Ƃ�z�肵�Ă���B<br>
	 * �w�肳�ꂽ���Ԃ����X���[�v���A�w�肳�ꂽ�񐔂������g���C����B
	 * </p>
	 * 
	 * @param title
	 *            �^�C�g��
	 * @param time
	 *            �X���[�v����[�~���b]
	 * @param count
	 *            ���g���C��
	 * @return ���������ꍇ�Atrue
	 */
	public boolean waitForTitle(String title, int time, int count) {
		for (; count > 0; count--) {
			if (isTarget(wnd, title)) {
				return true;
			}
			if (count >= 0) {
				delay(time);
			}
		}
		return false;
	}

	/**
	 * �^�C�g���m�F.
	 * <p>
	 * �E�B���h�E�̃^�C�g���� �w�肳�ꂽ�����񂪊܂܂�Ă����true��Ԃ��B
	 * </p>
	 * 
	 * @param wnd
	 *            �E�B���h�E
	 * @param title
	 *            ������
	 * @return ������v�����ꍇ�Atrue
	 */
	public boolean isTarget(JWnd wnd, String title) {
		String text = wnd.GetWindowText();
		if (text != null && text.indexOf(title) >= 0) {
			return true;
		}
		return false;
	}

	/**
	 * �E�B���h�E�^�C�g���擾.
	 * 
	 * @return �^�C�g���i�擾���s����null�j
	 */
	public String getTitle() {
		return wnd.GetWindowText();
	}

	/**
	 * �őO�ʈړ�.
	 * <p>
	 * �E�B���h�E���őO�ʂɈړ�������B<br>
	 * �E�B���h�E���A�C�R�����i�ŏ����j���Ă����ꍇ�́A�ȑO�̑傫���ɖ߂��B
	 * </p>
	 * 
	 * @return true�F����
	 * @see #setAutoDelay(int)
	 */
	public boolean setForegroundWindow() {
		if (wnd.IsIconic()) {
			wnd.ShowWindow(SW_RESTORE);
		}
		boolean ret = wnd.SetForegroundWindow();
		if (ret) {
			delay(wait);
		}
		return ret;
	}

	/**
	 * �ʒu�ݒ�.
	 * 
	 * @param x
	 * @param y
	 * @return true�F����
	 * @see #setBounds(int, int, int, int)
	 * @see #setAutoDelay(int)
	 */
	public boolean setLocation(int x, int y) {
		int flags = SWP_NOSIZE | SWP_NOZORDER | SWP_NOACTIVATE;
		boolean ret = wnd.SetWindowPos(null, x, y, 0, 0, flags);
		if (ret) {
			delay(wait);
		}
		return ret;
	}

	/**
	 * �T�C�Y�ݒ�.
	 * 
	 * @param cx
	 * @param cy
	 * @return true�F����
	 * @see #setBounds(int, int, int, int)
	 * @see #setAutoDelay(int)
	 */
	public boolean setSize(int cx, int cy) {
		int flags = SWP_NOMOVE | SWP_NOZORDER | SWP_NOACTIVATE;
		boolean ret = wnd.SetWindowPos(null, 0, 0, cx, cy, flags);
		if (ret) {
			delay(wait);
		}
		return ret;
	}

	/**
	 * �͈͐ݒ�.
	 * 
	 * @param x
	 * @param y
	 * @param cx
	 * @param cy
	 * @return true�F����
	 * @see #setAutoDelay(int)
	 */
	public boolean setBounds(int x, int y, int cx, int cy) {
		int flags = SWP_NOZORDER | SWP_NOACTIVATE;
		boolean ret = wnd.SetWindowPos(null, x, y, cx, cy, flags);
		if (ret) {
			delay(wait);
		}
		return ret;
	}

	/**
	 * �E�B���h�E�͈͎擾.
	 * 
	 * @return �͈́i�X�N���[�����W�n�j
	 */
	public Rectangle getBounds() {
		return wnd.getWindowRect();
	}

}
