package jp.hishidama.robot.ie;

import java.util.List;

import jp.hishidama.robot.IERobot;
import jp.hishidama.robot.ie.FileDownloadProgressDialog;
import jp.hishidama.win32.JWnd;
import jp.hishidama.win32.com.ComMgr;
import jp.hishidama.win32.mshtml.IHTMLAnchorElement;

public class FileDownloadRobotTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// ComMgr.initialize();
		ComMgr.initializeMTA();
		IERobot robot = null;
		try {
			robot = IERobot
					.findIE("Java Win32API呼出クラス (Hishidama's Java Win32API call)");
			if (robot == null) {
				robot = IERobot.create();
				robot.setVisible(true);
				robot
						.navigate("http://www.ne.jp/asahi/hishidama/home/soft/java/hmwin32.html");
				robot.waitDocumentComplete(100, 80);
			}
			robot.setRethrow(true);
			// robot.setForeground();
			execute(robot);
		} finally {
			if (robot != null) {
				robot.dispose();
			}
			ComMgr.uninitialize();
		}
	}

	private static void execute(IERobot robot) {
		clickTargetLink(robot, "hmwin32.zip");

		for (int i = 0; i < 100; i++) {
			boolean block = robot.isBlockingFileDownload();
			System.out.println("download block:" + block);
			if (block) {
				robot.setBlockingFileDownload(true);
				robot.waitDocumentComplete(100, 10);
				clickTargetLink(robot, "hmwin32.zip");
			}

			FileDownloadProgressDialog pd = getDownloadDialog(robot,
					"hmwin32.zip");
			if (pd != null) {
				System.out.println("dialog :" + pd);
				// System.out.println("confirm:" + pd.getConfirmDialog());
				// System.out.println("save :" + pd.getSaveDialog());
				// System.out.println("close :" + pd.isCloseDownloaded());
				// System.out.println("file :" + pd.isFileButtonEnabled());
				// System.out.println("folder :" + pd.isFolderButtonEnabled());
				// pd.setCloseDownloaded(false);

				FileDownloadRobot dr = new FileDownloadRobot(pd);
				dr.download(FileDownloadRobot.CONFIRM_SAVE,
						"C:\\テンポラリー\\hmwin32.zip", 10 * 1000);
				break;
			}

			// listChild(robot);
			robot.delay(100);
		}
	}

	public static void clickTargetLink(IERobot robot, String fliename) {
		List list = robot.getLinkList();
		for (int i = 0; i < list.size(); i++) {
			IHTMLAnchorElement a = (IHTMLAnchorElement) list.get(i);
			if (a.getHref().endsWith(fliename)) {
				a.click();
				robot.delay(500);
				break;
			}
		}
	}

	public static FileDownloadProgressDialog getDownloadDialog(IERobot robot,
			String filename) {
		List list = robot.getFileDownloadDialog();
		for (int i = 0; i < list.size(); i++) {
			FileDownloadProgressDialog pd = (FileDownloadProgressDialog) list
					.get(i);
			if (filename.equals(pd.getName())) {
				return pd;
			}
		}
		return null;
	}

	public static void listTop(List nl, List ol) {
		// ポップアップウィンドウのクラス名："#32768"
		for (int i = 0; i < nl.size(); i++) {
			JWnd n = (JWnd) nl.get(i);
			boolean exist = false;
			for (int j = 0; j < ol.size(); j++) {
				JWnd o = (JWnd) ol.get(j);
				if (o.equals(n)) {
					exist = true;
					break;
				}
			}
			if (!exist) {
				System.out.println(n.getClassName() + "/" + n.GetWindowText());
			}
		}
	}

	public static void listChild(IERobot robot) {
		List cl = robot.getWnd().enumChildWindows();
		for (int i = 0; i < cl.size(); i++) {
			JWnd w = (JWnd) cl.get(i);
			String text = w.GetWindowText();
			System.out.println(w.getClassName() + "/" + text);
			if (text.startsWith("セキュリティ保護のため、")) {
				System.out.println(w.isWindow() + "," + w.IsWindowVisible());
			}
		}
	}
}
