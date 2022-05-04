package jp.hishidama.robot;

import java.util.List;

import jp.hishidama.win32.JWnd;
import jp.hishidama.win32.com.ComMgr;
import jp.hishidama.win32.com.Variant;
import jp.hishidama.win32.mshtml.IHTMLAnchorElement;
import jp.hishidama.win32.mshtml.IHTMLInputTextElement;

public class IERobotGoogleTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ComMgr.initialize();
		try {
			execute();
		} finally {
			ComMgr.uninitialize();
		}
	}

	public static void execute() {
		// IE取得
		IERobot robot = IERobot.findIE("Yahoo! JAPAN");
		if (robot != null) {
			System.out.println("Yahooウィンドウ発見！");
		} else {
			robot = IERobot.create();
			if (robot == null) {
				throw new RuntimeException("IE生成失敗");
			}
			System.out.println("IE新規作成");
			robot.navigate("about:blank");

			// robot.setRethrow(true);
			// robot.goBack();
		}

		try {
			robot.setRethrow(true); // 発生した例外をそのままスローする
			robot.setDebugMode(true); // 例外発生時にスタックトレースをコンソール出力する

			list(robot);
			execute(robot);
		} finally {
			robot.dispose();
		}
	}

	public static void execute(IERobot robot) {

		// IEを表示して最前面に移動
		robot.setVisible(true);
		robot.setForeground();

		// サイズを変更する
		robot.setLocation(32, 16);
		robot.setSize(768, 640);

		// Googleへ遷移
		robot.navigate("http://www.google.co.jp");
		robot.waitDocumentComplete(100, 80); // 100ms×80で最大8秒待つ

		// 検索文字列を入力
		IHTMLInputTextElement search;
		// search = (HtmlInputTextElement) robot.getElementById("q");
		search = (IHTMLInputTextElement) robot.getElementByName("q", 0);
		search.setValue("Java WebBrowserコントロール");
		System.out
				.println("attr_name   : " + search.getAttributeString("name"));
		search.setAttribute("example", new Variant("aaa"));
		System.out.println("attr_example1: "
				+ search.getAttributeString("example"));
		search.removeAttribute("example");
		System.out.println("attr_example2: "
				+ search.getAttributeString("example"));

		// サブミット
		robot.submit();
		try {
			robot.waitDocumentComplete(100, 80); // 100ms×80で最大8秒待つ
		} catch (RuntimeException e) {
			System.out.println("遷移失敗：" + robot.getTitle());
			throw e;
		}
		System.out.println("遷移成功：" + robot.getTitle());

		// リンクを表示してみる
		List links = robot.getLinkList();
		for (int i = 0; i < links.size(); i++) {
			IHTMLAnchorElement a = (IHTMLAnchorElement) links.get(i);
			System.out.println("[" + i + "]" + a.getInnerText());
			System.out.println(a.getHref());
		}
	}

	public static void list(IERobot robot) {
		JWnd root = robot.getWnd();
		System.out.println(root.getClassName());
		List list = root.enumChildWindows();
		for (int i = 0; i < list.size(); i++) {
			JWnd w = (JWnd) list.get(i);
			System.out.println("[" + i + "]" + w.getClassName());
		}
	}
}
