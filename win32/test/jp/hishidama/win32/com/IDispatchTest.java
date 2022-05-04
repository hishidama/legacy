package jp.hishidama.win32.com;

import jp.hishidama.win32.JWnd;
import jp.hishidama.win32.api.WinNTConst;
import jp.hishidama.win32.shdocvw.IWebBrowser;

public class IDispatchTest {

	public static void main(String[] args) {
		ComMgr.initialize();
		IWebBrowser wb = null;
		try {
			wb = create();
			if (wb == null)
				return;
			dump(wb);
			dump(wb.getDocument());
		} finally {
			ComPtr.dispose(wb);
			ComMgr.uninitialize();
		}
	}

	public static IWebBrowser create() {
		IWebBrowser wb = IWebBrowser.create();
		if (wb == null) {
			throw new RuntimeException("WebBrowser create failed.");
		}
		wb.setVisible(true);

		JWnd wnd = new JWnd(wb.getHWND());
		wnd.SetForegroundWindow();

		wb.navigate("about:blank");

		return wb;
	}

	public static void dump(IDispatch dp) {
		System.out.println("----");
		try {
			System.out.println("navigate :" + dp.getIDOfName("navigate"));
			System.out.println("navigate2:" + dp.getIDOfName("navigate2"));
		} catch (HResultException e) {
			// IHTMLDocumentÇæÇ∆navigateÇ™ñ≥Ç¢ÇÃÇ≈HRESULTÇ™ÉGÉâÅ[Ç…Ç»ÇÈ
			e.printStackTrace();
		}

		int n = dp.getTypeInfoCount();
		System.out.println("getTypeInfoCount:" + n);
		for (int i = 0; i < n; i++) {
			ITypeInfo info = dp
					.getTypeInfo(i, WinNTConst.LOCALE_SYSTEM_DEFAULT);

			System.out.println(info.getName());
			System.out.println(info.getDoc());

			// long memid = info.getIDOfName("navigate");
			// System.out.println(memid);

			String[] name = new String[1];
			String[] doc = new String[1];
			int[] helpContext = new int[1];
			String[] helpFile = new String[1];
			info.getDocumentation(-1, name, doc, helpContext, helpFile);
			System.out.println("name:" + name[0]);
			System.out.println("doc:" + doc[0]);
			System.out.println("helpc:" + helpContext[0]);
			System.out.println("helpf:" + helpFile[0]);

			String mop = info.getMops(0);
			System.out.println("mop0:" + mop);
		}
	}
}
