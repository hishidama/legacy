package jp.hishidama.robot.ie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import jp.hishidama.robot.IERobot;
import jp.hishidama.win32.com.ComMgr;
import jp.hishidama.win32.mshtml.IHTMLDocument;
import junit.framework.TestCase;

public class HtmlSelectUtilTest extends TestCase {

	public void testMain() {
		IERobot ie = null;
		if (true) {
			ComMgr.initialize();
		} else {
			// なぜかMTAにするとIHTMLDocument#getParentWindow()がE_NOINTERFACEになる
			ComMgr.initializeMTA();
		}
		try {
			ie = IERobot.create();
			ie.setRethrow(true);
			ie.setDebugMode(true);
			ie.setVisible(true);
			ie.setForeground();
			ie.navigate("about:blank");
			ie.waitDocumentComplete(100, 80);

			writeHtml(ie.getDocument(), "select.html");
			ie.waitDocumentComplete(100, 80);

			executeComboBox(ie);
			executeListBox(ie);
			executeMultiList(ie);
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

	public void executeComboBox(IERobot ie) {
		IHTMLSelectUtil combo = ie.getSelectByName("combo1", 0);

		assertEquals(1, combo.getSize());

		combo.setSelected(1);
		assertEquals(1, combo.getSelectedIndex());
		assertEquals("v2", combo.getSelectedValue());

		combo.setSelected("v4");
		assertEquals(3, combo.getSelectedIndex());
		assertEquals("v4", combo.getSelectedValue());

		assertEquals("v3", combo.getValue(2));
		assertEquals("選択肢3", combo.getText(2));

		assertEquals(4, combo.size());

		combo.remove(1);
		assertEquals(3, combo.size());
		assertEquals("v1", combo.getValue(0));
		assertEquals("選択肢1", combo.getText(0));
		assertEquals("v3", combo.getValue(1));
		assertEquals("選択肢3", combo.getText(1));

		combo.add(2, "テキスト1", "値1", true, true);
		assertEquals(4, combo.size());
		assertEquals(2, combo.getSelectedIndex());
		assertEquals("v1", combo.getValue(0));
		assertEquals("選択肢1", combo.getText(0));
		assertEquals("v3", combo.getValue(1));
		assertEquals("選択肢3", combo.getText(1));
		assertEquals("値1", combo.getValue(2));
		assertEquals("テキスト1", combo.getText(2));
		assertEquals("v4", combo.getValue(3));
		assertEquals("選択肢4", combo.getText(3));

		combo.add(2, "テキスト2", "値2", true, false);
		assertEquals(5, combo.size());
		assertEquals(3, combo.getSelectedIndex());
	}

	public void executeListBox(IERobot ie) {
		IHTMLSelectUtil list = ie.getSelectByName("list1", 0);

		assertEquals(3, list.getSize());
		assertFalse(list.isMultiple());

		assertEquals(2, list.getSelectedIndex());
		assertEquals("v3", list.getSelectedValue());
		assertEquals("選択肢3", list.getText(list.getSelectedIndex()));
	}

	public void executeMultiList(IERobot ie) {
		IHTMLSelectUtil list = ie.getSelectByName("list2", 0);

		assertEquals(3, list.getSize());
		assertTrue(list.isMultiple());

		assertEquals(1, list.getSelectedIndex());
		assertEquals("v2", list.getSelectedValue());
		assertEquals("選択肢2", list.getText(list.getSelectedIndex()));

		for (int i = 0; i < list.size(); i++) {
			if (i == 1 || i == 2) {
				assertTrue(list.isSelected(i));
			} else {
				assertFalse(list.isSelected(i));
			}
		}

		list.setSelected(0, true);
		for (int i = 0; i < list.size(); i++) {
			if (i <= 2) {
				assertTrue(list.isSelected(i));
			} else {
				assertFalse(list.isSelected(i));
			}
		}

		list.setSelected(1);// 他に選択していたものは解除される
		for (int i = 0; i < list.size(); i++) {
			if (i == 1) {
				assertTrue(list.isSelected(i));
			} else {
				assertFalse(list.isSelected(i));
			}
		}
	}
}
