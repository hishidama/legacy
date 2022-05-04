package jp.hishidama.robot.ie;

import jp.hishidama.robot.IERobot;
import jp.hishidama.win32.com.ComMgr;
import junit.framework.TestCase;

public class HtmlInputRadioUtilTest extends TestCase {

	public void testMain() {
		IERobot ie = null;
		ComMgr.initialize();
		try {
			ie = IERobot.create();
			ie.setRethrow(true);
			ie.setDebugMode(true);
			ie.setVisible(true);
			ie.setForeground();
			ie.navigate("http://www.google.co.jp");
			ie.waitDocumentComplete(100, 80);

			execute(ie);
		} finally {
			if (ie != null) {
				ie.quit();
				ie.dispose();
			}
			ComMgr.uninitialize();
		}
	}

	public void execute(IERobot ie) {
		IHTMLInputRadioUtil r = ie.getRadioByName("lr");
		System.out.println(r.toString());

		r.setChecked("", true);
		assertEquals("", r.getCheckedValue());
		System.out.println(r.toString());

		r.setChecked("lang_ja", true);
		assertEquals("lang_ja", r.getCheckedValue());
		System.out.println(r.toString());
	}
}
