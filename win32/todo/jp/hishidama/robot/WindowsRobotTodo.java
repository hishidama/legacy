package jp.hishidama.robot;

import java.awt.Point;
import java.awt.Rectangle;

import jp.hishidama.win32.JWnd;

/**
 * WindowsRobot�ɏ����g�ݓ��ꂽ���@�\.
 * <p>
 * TODO+++WindowsRobotTodo
 * </p>
 * <ul>
 * <li>�}�E�X�ړ�</li>
 * <li>����E�B���h�E�ւ̃}�E�X�N���b�N</li>
 * <li>����E�B���h�E�ւ̃L�[����</li>
 * <li>�E�B���h�E�̓��̓G���A�̒T��</li>
 * <li>�E�B���h�E�̓��̓G���A�ւ̈ړ�</li>
 * <li>�E�B���h�E�̓��̓G���A�̓��e�擾</li>
 * <li>�E�B���h�E�̕\���G���A�̓��e�擾</li>
 * </ul>
 */
class WindowsRobotTodo extends WindowsRobot {

	protected int ox, oy;

	public static final int LOWORD(int l) {
		return l & 0xffff;
	}

	public static final int HIWORD(int l) {
		return (l >>> 16) & 0xffff;
	}

	public static final int MAKELONG(int lo, int hi) {
		return (lo & 0xffff) | ((hi << 16) & 0xffff0000);
	}

	public void setOffset() {
		Rectangle r = wnd.getWindowRect();
		ox = r.x;
		oy = r.y;
	}

	public void setOffset(int ox, int oy) {
		this.ox = ox;
		this.oy = oy;
	}

	public Point getOffset() {
		return new Point(ox, oy);
	}

	public void mouseMove(int x, int y) {
		// robot.mouseMove(x + ox, y + oy);
		int pos = MAKELONG(x + ox, y + oy);
		wnd.PostMessage(WM_MOUSEMOVE, 0, pos);
		delay(wait);
	}

	public void mouseClick(int x, int y, int wflag) {
		if (false) {
			Point pt = wnd.screenToClient(x + ox, y + oy);
			JWnd h = wnd.ChildWindowFromPoint(pt);
			pt = h.screenToClient(x + ox, y + oy);
			int pos = MAKELONG(pt.x, pt.y);
			System.out.println(LOWORD(pos));
			System.out.println(HIWORD(pos));
			System.out.println(h.getWindowRect());
			wflag |= MK_LBUTTON;
			boolean r1 = h.PostMessage(WM_LBUTTONDOWN, wflag, pos);
			System.out.println(r1);
			delay(10);
			wflag &= ~MK_LBUTTON;
			boolean r2 = h.PostMessage(WM_LBUTTONUP, wflag, pos);
			System.out.println(r2);
			delay(wait);
		} else {
			// robot.clickR(x + ox, y + oy);
		}
	}

}
