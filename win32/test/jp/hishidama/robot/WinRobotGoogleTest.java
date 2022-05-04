package jp.hishidama.robot;

import java.awt.AWTException;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.List;

import jp.hishidama.win32.JWnd;
import jp.hishidama.win32.api.Win32Exception;

public class WinRobotGoogleTest {

	protected static RobotUtil robot;
	static {
		try {
			robot = new RobotUtil();
		} catch (AWTException e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {
		JWnd.setThrowLastError(true);
		try {
			execute();
		} catch (Win32Exception e) {
			int error = e.getLastError();
			String msg = e.getMessage();
			System.out.println("Win32 LastError[" + error + "]�F" + msg);
			throw e;
		}
	}

	public static void execute() {
		// IERobotTodo ie = new IERobotTodo();
		WindowsRobot ie = new WindowsRobot();

		// Google�̃g�b�v�y�[�W��\�����Ă���IE��T��
		String title = "Google - Microsoft Internet Explorer";
		JWnd wnd = ie.findWindow(title, 200, 2);
		if (wnd == null) {
			System.out.println("�T�����s�F\"" + title + "\"");
			return;
		}

		// ����ׂ̈̏���
		ie.setJWnd(wnd);
		ie.setAutoDelay(200);
		System.out.println("�T�������F\"" + ie.getTitle() + "\"");

		// IE���őO�ʂɕ\������
		ie.setForegroundWindow();

		// �T�C�Y��ύX����
		// ie.setLocation(32, 16);
		ie.setSize(768, 640);
		// ie.setBounds(16, 32, 1024, 768);

		// �E�B���h�E�̒����ɂ���̈���擾
		Rectangle ieRect = ie.getBounds();
		Point pt = ieRect.getLocation();
		pt.x += ieRect.width / 2;
		pt.y += ieRect.height / 2;
		wnd.ScreenToClient(pt);
		JWnd body = wnd.ChildWindowFromPoint(pt);

		Rectangle rect = body.getWindowRect();
		System.out.println("��v�̈�F" + rect);

		// ���������java.awt.Robot�̊g���N���X���g�p

		// ���������������{�b�N�X���N���b�N
		// Google�̃f�U�C���ύX�ɂ���Ĉʒu�͕ς�邩���c
		robot.clickL(rect.x + rect.width / 2, rect.y + 188);

		// ���ɓ����Ă��镶����S�I��
		robot.sendCtrlString("A"); // Ctrl+A

		// �I����������������
		robot.sendKeyCode(KeyEvent.VK_DELETE);

		// �R�s�[&�y�[�X�g���g���āA��������������
		robot.pasteString("java Win32API");

		// Enter�L�[���������邱�Ƃɂ����submit�A�܂茟�����s
		robot.sendChar('\n'); // ENTER�L�[

		// ��ʑJ�ڂ�҂�
		title = "java Win32API - Google ���� - Microsoft Internet Explorer";
		if (!ie.waitForTitle(title, 200, 10)) {
			System.out.println("�J�ڎ��s�F\"" + title + "\"");
			return;
		}
		System.out.println("�J�ڐ����F\"" + ie.getTitle() + "\"");
	}

	public static void rect(JWnd wnd) {
		Rectangle rw = wnd.getWindowRect();
		System.out.println("win:" + rw);
		Rectangle rc = wnd.getClientRect();
		System.out.println("cli:" + rc);
		wnd.ClientToScreen(rc);
		System.out.println("c s:" + rc);
	}

	public static void ebumChildWindows(JWnd wnd, Rectangle rw) {
		List list = wnd.enumChildWindows();
		for (int i = 0; i < list.size(); i++) {
			JWnd w = (JWnd) list.get(i);
			Rectangle rect = w.getWindowRect();
			rect.x -= rw.x;
			rect.y -= rw.y;
			System.out.println(i + ":" + rect + w.GetWindowText());
		}
	}
}
