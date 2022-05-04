package jp.hishidama.robot;

import jp.hishidama.win32.com.ComMgr;
import jp.hishidama.win32.com.Variant;
import jp.hishidama.win32.mshtml.IHTMLDocument;
import jp.hishidama.win32.mshtml.IHTMLFramesCollection;
import jp.hishidama.win32.mshtml.IHTMLWindow;

public class IHTMLElementCollectionTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ComMgr.initialize();
		try {
			execute();
			execute2();
		} finally {
			ComMgr.uninitialize();
		}
	}

	protected static void execute() {
		IERobot robot = IERobot.create();
		if (robot == null) {
			throw new RuntimeException("IEê∂ê¨é∏îs");
		}
		try {
			robot.navigate("about:blank");
			IHTMLDocument doc = robot.getDocument();
			// doc.write("<html></hmtl>");
			// doc.close();
			IHTMLFramesCollection fs = doc.getFrames();
			int n = fs.getLength();
			System.out.println(n);
		} finally {
			robot.dispose();
		}
	}

	protected static void execute2() {
		IERobot robot = IERobot.create();
		if (robot == null) {
			throw new RuntimeException("IEê∂ê¨é∏îs");
		}
		try {
			robot
					.navigate("http://www.ne.jp/asahi/hishidama/home/game/ds4/index.html");
			robot.waitDocumentComplete(100, 80);
			IHTMLDocument doc = robot.getDocument();
			IHTMLWindow w = doc.getParentWindow();
			IHTMLFramesCollection fs = w.getFrames();
			int n = fs.getLength();
			System.out.println(n + " " + fs.getTypeName());
			for (int i = 0; i < n; i++) {
				IHTMLWindow e = fs.item(new Variant(i));
				int m = e.getLength();
				System.out.println(m + e.getTypeName());
			}
			IHTMLWindow e = fs.item(new Variant("MAIN"));
			System.out.println(e.getTypeName());
			System.out.println(e.getScreenLeft() + "," + e.getScreenTop());
		} finally {
			robot.dispose();
		}
	}

}
