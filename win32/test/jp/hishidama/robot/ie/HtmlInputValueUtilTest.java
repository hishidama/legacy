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
			ie = IERobot.findIE("インプット");
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
		t.setDisabled(false);// 使用可能
		t.setReadOnly(false);

		IHTMLInputValueUtil iv = new IHTMLInputValueUtil(ie, t);
		iv.setValue("値1");
		assertEquals("値1", iv.getValue());

		iv.sendValue("値11");
		assertEquals("値11", iv.getValue());

		iv.setDisabled(true);// 使用不可
		iv.setValue("値2");
		assertEquals("値2", iv.getValue());

		try {
			iv.sendValue("書き込み不可のはず");
			fail();
		} catch (RuntimeException e) {
			assertEquals("入力不可", e.getMessage());
		}

		t.setDisabled(false);// 使用可能
		t.setReadOnly(true); // 読み込み専用
		iv.setValue("値3");
		assertEquals("値3", iv.getValue()); // 読み込み専用でも設定可能
		iv.sendValue("値4"); // 読み込み専用の場合、送信は出来るが実際には変更されない
		assertEquals("値3", iv.getValue());
	}

	public void executeHidden(IERobot ie) {
		IHTMLInputHiddenElement t = (IHTMLInputHiddenElement) ie
				.getElementByName("hidden1", 0);
		t.setDisabled(false); // 使用可能

		IHTMLInputValueUtil iv = new IHTMLInputValueUtil(ie, t);
		iv.setValue("値1");
		assertEquals("値1", iv.getValue());

		try {
			iv.sendValue("書き込み不可のはず1");
			fail();
		} catch (RuntimeException e) {
			assertEquals("入力不可", e.getMessage());
		}

		iv.setDisabled(true);// 使用不可
		iv.setValue("値2");
		assertEquals("値2", iv.getValue());

		try {
			iv.sendValue("書き込み不可のはず2");
			fail();
		} catch (RuntimeException e) {
			assertEquals("入力不可", e.getMessage());
		}
	}

	public void executeFile(IERobot ie) {
		IHTMLInputFileElement f = (IHTMLInputFileElement) ie.getElementByName(
				"file2", 0);
		f.setDisabled(false); // 使用可能

		IHTMLInputValueUtil iv = new IHTMLInputValueUtil(ie, f);
		iv.sendValue("あいうえお");
		assertEquals("あいうえお", iv.getValue());

		assertEquals("あいうえお", f.getValue());

		iv.sendValue("C:\\temp\\test.txt");
		assertEquals("C:\\temp\\test.txt", iv.getValue());

		f.setDisabled(true);// 使用不可
		try {
			iv.sendValue("さしす");
			fail();
		} catch (RuntimeException e) {
			assertEquals("入力不可", e.getMessage());
		}
	}

	public void executeRadio(IERobot ie) {
		IHTMLInputElement f = (IHTMLInputElement) ie.getElementByName("radio1",
				0);
		IHTMLInputValueUtil iv = new IHTMLInputValueUtil(ie, f);
		try {
			// ラジオボタンはTextRangeを取得できない
			iv.sendValue("あああ");
			fail();
		} catch (HResultException e) {
			assertEquals(0x8000ffff, e.getHResult());
		}
	}
}
