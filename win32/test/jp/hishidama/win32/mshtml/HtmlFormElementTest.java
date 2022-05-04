package jp.hishidama.win32.mshtml;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import jp.hishidama.win32.JWnd;
import jp.hishidama.win32.api.OcIdlConst;
import jp.hishidama.win32.com.ComMgr;
import jp.hishidama.win32.com.ComPtr;
import jp.hishidama.win32.shdocvw.IWebBrowser;

public class HtmlFormElementTest {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ComMgr.initialize();
		IHTMLDocument doc = null;
		try {
			doc = createDocument();
			writeSample(doc);
			getElements(doc);
		} finally {
			ComPtr.dispose(doc);
			ComMgr.uninitialize();
		}
	}

	public static IHTMLDocument createDocument() {
		IWebBrowser wb = IWebBrowser.create();
		if (wb == null) {
			throw new RuntimeException("WebBrowser create failed.");
		}
		try {
			wb.navigate("about:blank");
			waitBusy(wb, null);

			wb.setVisible(true);

			JWnd wnd = new JWnd(wb.getHWND());
			wnd.SetForegroundWindow();

			IHTMLDocument doc = wb.getDocument();
			if (doc == null) {
				throw new RuntimeException("GetDocument() error");
			}

			// doc.focus();
			return doc;

		} finally {
			wb.dispose();
		}
	}

	public static void waitBusy(IWebBrowser wb, String msg) {
		while (wb.getBusy()
				|| wb.getReadyState() != OcIdlConst.READYSTATE_COMPLETE) {
			if (msg != null)
				System.out.println("busy " + msg);
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
			}
		}
	}

	public static void writeSample(IHTMLDocument doc) {
		Class c = HtmlElementTest.class;
		URL url = c.getResource("sample.html");
		InputStream is = null;
		BufferedReader br = null;
		try {
			is = url.openStream();
			br = new BufferedReader(new InputStreamReader(is));
			while (br.ready()) {
				doc.writeln(br.readLine());
			}
			doc.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (br != null)
				try {
					br.close();
				} catch (IOException e) {
				}
			if (is != null)
				try {
					is.close();
				} catch (IOException e) {
				}
		}
	}

	public static void getElements(IHTMLDocument doc) {
		IHTMLElementCollection fs = doc.getForms();
		int l = fs.getLength();
		System.out.println(l);
		for (int i = 0; i < l; i++) {
			IHTMLFormElement f = (IHTMLFormElement) fs.item(i);
			IHTMLFormElement e = f.getElements();
			System.out.println("[" + i + "]" + e);

			int fl = f.getLength();
			System.out.println(fl);
			for (int j = 0; j < fl; j++) {
				IHTMLElement obj = f.item(j);
				System.out.println(obj);
			}
		}
	}
}
