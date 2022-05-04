package jp.hishidama.win32.com;

import junit.framework.TestCase;

public class HResultExceptionTest extends TestCase {

	public void testGetMessage() {
		HResultException e = new HResultException(0x123);
		assertEquals(0x123, e.getHResult());
		assertEquals("00000123: ", e.getMessage());

		e = new HResultException(0xabcde);
		assertEquals(0xabcde, e.getHResult());
		assertEquals("000abcde: ", e.getMessage());
	}
}
