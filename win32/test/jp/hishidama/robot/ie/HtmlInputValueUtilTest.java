package jp.hishidama.robot.ie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import jp.hishidama.robot.IERobot;
import jp.hishidama.win32.com.ComMgr;
import jp.hishidama.win32.com.HResultException;
import jp.hishidama.win32.mshtml.IHTMLDocument;
import jp.hishidama.win32.mshtml.IHTMLInputElement;
import jp.hishidama.win32.mshtml.IHTMLInputFileElement;
import jp.hishidama.win32.mshtml.IHTMLInputHiddenElement;
import jp.hishidama.win32.mshtml.IHTMLInputTextElement;
import junit.framework.TestCase;

public class HtmlInputValueUtilTest extends TestCase {

	public void testMain() {
		IERobot ie = null;
		ComMgr.initialize();
		try {
			ie = IERobot.findIE("�C���v�b�g");
			if (ie == null) {
				ie = IERobot.create();
				ie.navigate("about:blank");
				ie.waitDocumentComplete(100, 80);

				writeHtml(ie.getDocument(), "input.html");
				ie.waitDocumentComplete(100, 80);
			}
			ie.setRethrow(true);
			ie.setDebugMode(true);
			ie.setVisible(true);
			// ie.setForeground();

			executeText(ie);
			executeHidden(ie);
			executeFile(ie);
			executeRadio(ie);
		} finally {
			if (ie != null) {
				// ie.quit();
				ie.dispose();
			}
			ComMgr.uninitialize();
		}
	}

	public void writeHtml(IHTMLDocument doc, String file) {
		Class c = getClass();
		URL url = c.getResource(file);
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

	public void executeText(IERobot ie) {
		IHTMLInputTextElement t = (IHTMLInputTextElement) ie.getElementByName(
				"text1", 0);
		t.setDisabled(false);// �g�p�\
		t.setReadOnly(false);

		IHTMLInputValueUtil iv = new IHTMLInputValueUtil(ie, t);
		iv.setValue("�l1");
		assertEquals("�l1", iv.getValue());

		iv.sendValue("�l11");
		assertEquals("�l11", iv.getValue());

		iv.setDisabled(true);// �g�p�s��
		iv.setValue("�l2");
		assertEquals("�l2", iv.getValue());

		try {
			iv.sendValue("�������ݕs�̂͂�");
			fail();
		} catch (RuntimeException e) {
			assertEquals("���͕s��", e.getMessage());
		}

		t.setDisabled(false);// �g�p�\
		t.setReadOnly(true); // �ǂݍ��ݐ�p
		iv.setValue("�l3");
		assertEquals("�l3", iv.getValue()); // �ǂݍ��ݐ�p�ł��ݒ�\
		iv.sendValue("�l4"); // �ǂݍ��ݐ�p�̏ꍇ�A���M�͏o���邪���ۂɂ͕ύX����Ȃ�
		assertEquals("�l3", iv.getValue());
	}

	public void executeHidden(IERobot ie) {
		IHTMLInputHiddenElement t = (IHTMLInputHiddenElement) ie
				.getElementByName("hidden1", 0);
		t.setDisabled(false); // �g�p�\

		IHTMLInputValueUtil iv = new IHTMLInputValueUtil(ie, t);
		iv.setValue("�l1");
		assertEquals("�l1", iv.getValue());

		try {
			iv.sendValue("�������ݕs�̂͂�1");
			fail();
		} catch (RuntimeException e) {
			assertEquals("���͕s��", e.getMessage());
		}

		iv.setDisabled(true);// �g�p�s��
		iv.setValue("�l2");
		assertEquals("�l2", iv.getValue());

		try {
			iv.sendValue("�������ݕs�̂͂�2");
			fail();
		} catch (RuntimeException e) {
			assertEquals("���͕s��", e.getMessage());
		}
	}

	public void executeFile(IERobot ie) {
		IHTMLInputFileElement f = (IHTMLInputFileElement) ie.getElementByName(
				"file2", 0);
		f.setDisabled(false); // �g�p�\

		IHTMLInputValueUtil iv = new IHTMLInputValueUtil(ie, f);
		iv.sendValue("����������");
		assertEquals("����������", iv.getValue());

		assertEquals("����������", f.getValue());

		iv.sendValue("C:\\temp\\test.txt");
		assertEquals("C:\\temp\\test.txt", iv.getValue());

		f.setDisabled(true);// �g�p�s��
		try {
			iv.sendValue("������");
			fail();
		} catch (RuntimeException e) {
			assertEquals("���͕s��", e.getMessage());
		}
	}

	public void executeRadio(IERobot ie) {
		IHTMLInputElement f = (IHTMLInputElement) ie.getElementByName("radio1",
				0);
		IHTMLInputValueUtil iv = new IHTMLInputValueUtil(ie, f);
		try {
			// ���W�I�{�^����TextRange���擾�ł��Ȃ�
			iv.sendValue("������");
			fail();
		} catch (HResultException e) {
			assertEquals(0x8000ffff, e.getHResult());
		}
	}
}
