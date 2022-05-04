package jp.hishidama.win32;

import jp.hishidama.win32.api.WinBase;
import junit.framework.TestCase;

public class WinBaseTest extends TestCase {

	public void testFormatMessage() {
		String actual = WinBase.FormatMessage(0);
		// for (int i = 0; i < actual.length(); i++) {
		// char c = actual.charAt(i);
		// System.out.printf("[%c %x]\n", c, (int)c);
		// }
		assertEquals("‚±‚Ì‘€ì‚ð³‚µ‚­I—¹‚µ‚Ü‚µ‚½B\r\n", actual);
	}

}
