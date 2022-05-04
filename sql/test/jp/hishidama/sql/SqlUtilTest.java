package jp.hishidama.sql;

import junit.framework.TestCase;

public class SqlUtilTest extends TestCase {

	public void testEndsTrimWith() {
		assertTrue(SqlUtil.endsTrimWith(new StringBuffer("set"), "set"));
		assertTrue(SqlUtil.endsTrimWith(new StringBuffer("Set "), "set"));
		assertTrue(SqlUtil.endsTrimWith(new StringBuffer(" set "), "Set"));
		assertTrue(SqlUtil.endsTrimWith(new StringBuffer("update set "), "Set"));

		assertFalse(SqlUtil.endsTrimWith(new StringBuffer("et"), "Set"));
		assertFalse(SqlUtil.endsTrimWith(new StringBuffer("et "), "Set"));
		assertFalse(SqlUtil.endsTrimWith(new StringBuffer(" et"), "Set"));
		assertFalse(SqlUtil.endsTrimWith(new StringBuffer(" set 1=2"), "Set"));
	}

}
