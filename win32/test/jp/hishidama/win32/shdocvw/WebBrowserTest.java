package jp.hishidama.win32.shdocvw;

import java.util.List;

import jp.hishidama.win32.JWnd;
import jp.hishidama.win32.api.OcIdlConst;
import jp.hishidama.win32.com.ComException;
import jp.hishidama.win32.com.ComMgr;
import jp.hishidama.win32.com.ComPtr;
import jp.hishidama.win32.mshtml.IHTMLDocument;
import jp.hishidama.win32.mshtml.IHTMLFormElement;
import jp.hishidama.win32.mshtml.IHTMLInputTextElement;
import jp.hishidama.win32.shdocvw.IWebBrowser;

public class WebBrowserTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ComMgr.initialize();
		try {
			list();
			navigate();
			google();
		} finally {
			ComMgr.uninitialize();
		}
	}

	public static void list() {
		List list = IWebBrowser.enumWebBrowser();
		System.out.println(list.size());
		for (int i = 0; i < list.size(); i++) {
			IWebBrowser wb = (IWebBrowser) list.get(i);
			try {
				System.out.println("[" + i + "]");
				System.out.println("name: " + wb.getName());
				System.out.println("full: " + wb.getFullName());
				System.out.println("path: " + wb.getPath());
				System.out.println("visible: " + wb.getVisible());
				System.out.println("bounds: " + wb.getBounds());
				System.out.println("hwnd: " + Long.toHexString(wb.getHWND()));
				System.out.println("statusbar: " + wb.getStatusText());
				System.out.println("type: " + wb.getType());
			} catch (ComException e) {
				e.printStackTrace();
			} finally {
				wb.dispose();
			}
		}
	}

	public static void waitBusy(IWebBrowser wb, String msg) {
		while (wb.getBusy()
				|| wb.getReadyState() != OcIdlConst.READYSTATE_COMPLETE) {
			System.out.println("busy " + msg);
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
			}
		}
	}

	public static void sleep(int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
		}
	}

	public static void navigate() {
		IWebBrowser wb = IWebBrowser.create();
		if (wb == null) {
			System.err.println("WebBrowser create failed.");
			return;
		}
		try {
			wb.setVisible(true);

			JWnd wnd = new JWnd(wb.getHWND());
			wnd.SetForegroundWindow();

			wb.navigate("http://www.ne.jp/asahi/hishidama/home/index.html");
			waitBusy(wb, "1");

			sleep(1 * 1000);

			wb
					.navigate("http://www.ne.jp/asahi/hishidama/home/tech/index.html");
			waitBusy(wb, "2");

			sleep(1 * 1000);

			wb.goBack();
			waitBusy(wb, "3");

			sleep(1 * 1000);

			wb.goForward();
			waitBusy(wb, "4");

			sleep(2 * 1000);

			wb.quit();
		} finally {
			wb.dispose();
		}
	}

	public static void google() {
		JWnd wnd = JWnd
				.FindWindow(null, "Google - Microsoft Internet Explorer");
		if (wnd == null) {
			System.out.println("Google window not found");
			return;
		}
		wnd.SetForegroundWindow();
		IWebBrowser wb = IWebBrowser.findWebBrowser(wnd.GetSafeHwnd());
		if (wb == null) {
			System.err.println("Google IE not found");
			return;
		}

		IHTMLDocument doc = null;
		IHTMLInputTextElement text = null;
		IHTMLFormElement form = null;
		try {
			doc = wb.getDocument();
			text = (IHTMLInputTextElement) doc.getElementByName("q");
			text.setValue("java WebBrowser");
			form = text.getForm();
			form.submit();
		} finally {
			ComPtr.dispose(doc);
		}
	}

}
