package jp.hishidama.win32;

import java.util.List;

import jp.hishidama.win32.JWnd;
import jp.hishidama.win32.api.Win32Exception;
import jp.hishidama.win32.api.WinBase;

//import org.junit.Test;

public class JWndTest {

	public static void main(String[] args) {
		JWndTest test = new JWndTest();
		test.testSetFocus();
		// test.testJniEnumWindows();
		// test.testEnumChildWindows();
	}

	public void testJniEnumWindows() {
		List list = JWnd.enumWindows();
		for (int i = 0; i < list.size(); i++) {
			JWnd wnd = (JWnd) list.get(i);
			System.out.println("[" + wnd.GetWindowText() + "]");
		}
	}

	public void testSetFocus() {
		JWnd.setThrowLastError(true);
		JWnd wnd = JWnd.FindWindow(null, "“d‘ì");
		if (wnd == null) {
			System.err.println("not found “d‘ì");
			return;
		}
		wnd.SetForegroundWindow();

		try {
			Thread.sleep(3 * 1000);
		} catch (InterruptedException e) {
		}

		JWnd ret = wnd.SetFocus();
		Win32Exception err = wnd.getLastError();
		int ntv = WinBase.GetLastError();
		System.out.println("ret:" + ret + "\nnative:" + ntv);
		if (err != null) {
			err.printStackTrace();
		}
	}

	public void testEnumChildWindows() {
		JWnd pwnd = JWnd.FindWindow(null, "“d‘ì");
		// JWnd pwnd = JWnd.FindWindow(null, "Google - Microsoft Internet
		// Explorer");
		if (pwnd == null) {
			System.err.println("not found target window");
			return;
		}
		List list = pwnd.enumChildWindows();
		for (int i = 0; i < list.size(); i++) {
			JWnd wnd = (JWnd) list.get(i);
			System.out.println("[" + wnd.GetWindowText() + "]");
		}
	}

}
