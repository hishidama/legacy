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
		// IE�擾
		IERobot robot = IERobot.findIE("Yahoo! JAPAN");
		if (robot != null) {
			System.out.println("Yahoo�E�B���h�E�����I");
		} else {
			robot = IERobot.create();
			if (robot == null) {
				throw new RuntimeException("IE�������s");
			}
			System.out.println("IE�V�K�쐬");
			robot.navigate("about:blank");

			// robot.setRethrow(true);
			// robot.goBack();
		}

		try {
			robot.setRethrow(true); // ����������O�����̂܂܃X���[����
			robot.setDebugMode(true); // ��O�������ɃX�^�b�N�g���[�X���R���\�[���o�͂���

			list(robot);
			execute(robot);
		} finally {
			robot.dispose();
		}
	}

	public static void execute(IERobot robot) {

		// IE��\�����čőO�ʂɈړ�
		robot.setVisible(true);
		robot.setForeground();

		// �T�C�Y��ύX����
		robot.setLocation(32, 16);
		robot.setSize(768, 640);

		// Google�֑J��
		robot.navigate("http://www.google.co.jp");
		robot.waitDocumentComplete(100, 80); // 100ms�~80�ōő�8�b�҂�

		// ��������������
		IHTMLInputTextElement search;
		// search = (HtmlInputTextElement) robot.getElementById("q");
		search = (IHTMLInputTextElement) robot.getElementByName("q", 0);
		search.setValue("Java WebBrowser�R���g���[��");
		System.out
				.println("attr_name   : " + search.getAttributeString("name"));
		search.setAttribute("example", new Variant("aaa"));
		System.out.println("attr_example1: "
				+ search.getAttributeString("example"));
		search.removeAttribute("example");
		System.out.println("attr_example2: "
				+ search.getAttributeString("example"));

		// �T�u�~�b�g
		robot.submit();
		try {
			robot.waitDocumentComplete(100, 80); // 100ms�~80�ōő�8�b�҂�
		} catch (RuntimeException e) {
			System.out.println("�J�ڎ��s�F" + robot.getTitle());
			throw e;
		}
		System.out.println("�J�ڐ����F" + robot.getTitle());

		// �����N��\�����Ă݂�
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
