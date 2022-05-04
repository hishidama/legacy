package jp.hishidama.win32.mshtml;

import java.util.Iterator;
import java.util.List;

import jp.hishidama.win32.JWnd;
import jp.hishidama.win32.api.OcIdlConst;
import jp.hishidama.win32.com.ComMgr;
import jp.hishidama.win32.com.ComPtr;
import jp.hishidama.win32.mshtml.IHTMLDocument;
import jp.hishidama.win32.mshtml.IHTMLElement;
import jp.hishidama.win32.mshtml.IHTMLInputTextElement;
import jp.hishidama.win32.shdocvw.IWebBrowser;

public class HtmlDocumentTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ComMgr.initialize();
		IHTMLDocument doc = null;
		try {
			doc = getGoogleDocument();
			if (doc == null)
				return;
			attr(doc);
			// name(doc);
			write(doc);
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

	public static IWebBrowser google() {
		IWebBrowser wb = IWebBrowser.create();
		if (wb == null) {
			throw new RuntimeException("WebBrowser create failed.");
		}
		wb.setVisible(true);

		JWnd wnd = new JWnd(wb.getHWND());
		wnd.SetForegroundWindow();

		wb.navigate("http://www.google.co.jp");
		waitBusy(wb, null);
		return wb;
	}

	public static IHTMLDocument getGoogleDocument() {
		IWebBrowser wb = google();
		try {
			IHTMLDocument doc = wb.getDocument();
			if (doc == null) {
				System.err.println("GetDocument() error");
				return null;
			}

			doc.focus();
			return doc;

		} finally {
			wb.dispose();
		}
	}

	public static void disposeList(List list) {
		for (Iterator i = list.iterator(); i.hasNext();) {
			Object obj = i.next();
			if (obj instanceof ComPtr) {
				ComPtr com = (ComPtr) obj;
				com.dispose();
			}
		}
	}

	public static void attr(IHTMLDocument doc) {
		System.out.println("\nÅúattrÅú");

		IHTMLElementCollection list = doc.getAll();
		System.out.println("all: " + list.getLength());
		list.dispose();

		IHTMLElement body = doc.getBody();
		System.out.println("body: " + body);
		body.dispose();

		IHTMLElement act = doc.getActiveElement();
		System.out.println("active: " + act);
		act.dispose();

		// list=doc.getFrames();
		// System.out.println("frame: " + list.size());
		// disposeList(list);

		list = doc.getImages();
		System.out.println("image: " + list.getLength());
		list.dispose();

		list = doc.getLinks();
		System.out.println("link: " + list.getLength());
		{
			IHTMLElement el = (IHTMLElement) list.item(0);
			System.out.println("link[0]:" + el);
			el.dispose();
		}
		list.dispose();

		list = doc.getForms();
		System.out.println("form: " + list.getLength());
		list.dispose();

		list = doc.getAnchors(); // <a name="">
		System.out.println("anchor: " + list.getLength());
		list.dispose();

		System.out.println("title: " + doc.getTitle());
		System.out.println("ready: " + doc.getReadyState());
		System.out.println("last: " + doc.getLastModified());
		System.out.println("url: " + doc.getUrl());
		System.out.println("domain: " + doc.getDomain());
		System.out.println("cookie: " + doc.getCookie());
		System.out.println("charset: " + doc.getCharset());
		System.out.println("default: " + doc.getDefaultCharset());
		System.out.println("mime: " + doc.getMimeType());
		System.out.println("file_size: " + doc.getFileSize());
		System.out.println("file_create: " + doc.getFileCreatedDate());
		System.out.println("file_modify: " + doc.getFileModifiedDate());
		System.out.println("file_update: " + doc.getFileUpdatedDate());
		System.out.println("security: " + doc.getSecurity());
		System.out.println("protocol: " + doc.getProtocol());
		System.out.println("prop: " + doc.getNameProp());
		// System.out.println("base: " + doc.getBaseUrl());
		System.out.println("focus: " + doc.hasFocus());
	}

	public static void name(IHTMLDocument doc) {
		System.out.println("\nÅúnameÅú");

		IHTMLElementCollection list = doc.getElementsByName("q");
		int l = list.getLength();
		System.out.println(l);
		for (int i = 0; i < l; i++) {
			IHTMLElement elem = (IHTMLElement) list.item(i);
			System.out.println("[" + i + "]" + elem);
			System.out.println("tag: " + elem.getTagName());
			System.out.println("id: " + elem.getId());
			System.out.println("class: " + elem.getClassName());
			IHTMLElement parent = elem.getParentElement();
			String parentTag = (parent != null) ? parent.getTagName() : "";
			System.out.println("parent: " + parentTag + " " + parent);
			System.out.println("x: " + elem.getOffsetLeft());
			System.out.println("y: " + elem.getOffsetTop());
			System.out.println("w: " + elem.getOffsetWidth());
			System.out.println("h: " + elem.getOffsetHeight());
			IHTMLElement offset = elem.getOffsetParent();
			String offsetTag = (offset != null) ? offset.getTagName() : "";
			System.out.println("offset: " + offsetTag + " " + offset);
			System.out.println("inner: " + elem.getInnerHtml());
			if (parent != null)
				parent.dispose();
			if (offset != null)
				offset.dispose();

			if (elem instanceof IHTMLInputTextElement) {
				IHTMLInputTextElement text = (IHTMLInputTextElement) elem;
				String v = text.getValue();
				v += "a";
				text.setValue(v);
			}

			elem.setId("id_set_test");
			System.out.println("new-id: " + elem.getId());

			elem.dispose();
		}
		list.dispose();
	}

	public static void write(IHTMLDocument doc) {
		System.out.println("\nÅúwriteÅú");

		doc.clear();
		doc.writeln("<html>");
		doc.writeln("<head>");
		doc.writeln("<title>java document-write test</title>");
		doc.writeln("</head>");
		doc.writeln("<body>");
		// doc.writeln("HtmlDocument write é¿å±");
		// doc.writeln("<p>HtmlDocument write é¿å±</p>");
		doc.writeln("<p>HtmlDocument write é¿å±");
		doc.writeln("</body>");
		doc.writeln("</html>");
		doc.close();

		IHTMLElementCollection list = doc.getAll();
		int l = list.getLength();
		System.out.println(l);
		for (int i = 0; i < l; i++) {
			IHTMLElement el = list.item(i);
			System.out.println("[" + i + "]" + el.getTagName() + " " + el + "ÅF"
					+ el.getInnerText());
			el.dispose();
		}
		list.dispose();
	}

}
