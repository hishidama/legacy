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
import jp.hishidama.win32.com.Variant;
import jp.hishidama.win32.shdocvw.IWebBrowser;

public class HtmlElementTest {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ComMgr.initialize();
		IHTMLDocument doc = null;
		try {
			doc = createDocument();
			writeSample(doc);
			// operate(doc);
			// list(doc);
			// image(doc);
			table(doc);
		} finally {
			ComPtr.dispose(doc);
			ComMgr.uninitialize();
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

	public static void sleep(int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
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

	public static void operate(IHTMLDocument doc) {
		IHTMLElementCollection forms = doc.getForms();
		IHTMLFormElement f = (IHTMLFormElement) forms.item(0);
		forms.dispose();
		{
			IHTMLInputTextElement t1_0 = (IHTMLInputTextElement) f
					.item("t1", 0);
			t1_0.setValue("名前0による値をセット");
			t1_0.dispose();
		}
		{
			IHTMLInputTextElement t1_1 = (IHTMLInputTextElement) f
					.item("t1", 1);
			t1_1.setValue("名前1による値をセット");
			t1_1.dispose();
		}
		{
			IHTMLElement s1 = doc.getElementById("s1");
			s1.setInnerText("idによるspanタグへの値のセット");
		}
		forms.dispose();
	}

	public static void list(IHTMLDocument doc) {
		System.out.println("●list●");

		IHTMLElementCollection list = doc.getAll();
		int l = list.getLength();
		System.out.println(l);
		for (int i = 0; i < l; i++) {
			IHTMLElement el = list.item(i);
			System.out.println("[" + i + "]" + el.getTagName() + " " + el + "："
					+ el.getInnerText());
			el.dispose();
		}
		list.dispose();
	}

	public static void image(IHTMLDocument doc) {
		IHTMLElementCollection list = doc.getElementsByTagName("img");
		IHTMLImgElement img = (IHTMLImgElement) list.item(0);
		System.out.println(img.getSrc());
		System.out.println(img.getBorder());
		System.out.println(img.getLoop());
	}

	public static void table(IHTMLDocument doc) {
		IHTMLTable tbl = (IHTMLTable) doc.getElementById("tbl1");
		System.out.println(tbl.getCaption());

		Variant v = tbl.getCellSpacing();
		System.out.println("space:" + v.getVt());
		System.out.println(v.getStr());
		v = tbl.getCellPadding();
		System.out.println("pad:" + v.getVt());
		System.out.println(v.getStr());
		v = tbl.getBorder();
		System.out.println("border:" + v.getVt());
		System.out.println(v.getStr());
		tbl.setBorder(new Variant(2));
		v = tbl.getBorder();
		System.out.println("border:" + v.getVt());
		System.out.println(v.getStr());

		IHTMLElementCollection cs = tbl.getCells();
		for (int i = 0; i < cs.getLength(); i++) {
			IHTMLTableCell c = (IHTMLTableCell) cs.item(i);
			IHTMLTableRow r = (IHTMLTableRow) c.getParentElement();
			System.out.println("[" + i + "]" + c.getTagName() + "("
					+ c.getCellIndex() + "," + r.getRowIndex() + ")");
		}
		IHTMLElementCollection rs = tbl.getRows();
		for (int i = 0; i < rs.getLength(); i++) {
			IHTMLTableRow r = (IHTMLTableRow) rs.item(i);
			IHTMLElementCollection rcs = r.getCells();
			for (int j = 0; j < rcs.getLength(); j++) {
				IHTMLTableCell c = (IHTMLTableCell) rcs.item(j);
				System.out.println("[" + i + "," + j + "]" + c.getInnerHtml());
			}
		}

		System.out.println(tbl.getTHead());
		System.out.println(tbl.getTFoot());
	}

}
