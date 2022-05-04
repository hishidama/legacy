package jp.hishidama.ant.types.htlex;

import static org.junit.Assert.*;
import jp.hishidama.ant.types.htlex.CaseEnum;

import org.junit.Test;

public class CaseAttributeTest {

	protected CaseEnum create(String s) {
		return (CaseEnum) CaseEnum
				.getInstance(CaseEnum.class, s);
	}

	@Test
	public void same() {
		CaseEnum c = create("same");
		assertNull(c.convert(null));
		assertEquals("name", c.convert("name"));
		assertEquals("NAME", c.convert("NAME"));
		assertEquals("conTent-tYpe", c.convert("conTent-tYpe"));
	}

	@Test
	public void lower() {
		CaseEnum c = create("lower");
		assertNull(c.convert(null));
		assertEquals("name", c.convert("name"));
		assertEquals("name", c.convert("NAME"));
		assertEquals("content-type", c.convert("conTent-tYpe"));
	}

	@Test
	public void upper() {
		CaseEnum c = create("upper");
		assertNull(c.convert(null));
		assertEquals("NAME", c.convert("name"));
		assertEquals("NAME", c.convert("NAME"));
		assertEquals("CONTENT-TYPE", c.convert("conTent-tYpe"));
	}

	@Test
	public void proper() {
		CaseEnum c = create("proper");
		assertNull(c.convert(null));
		assertEquals("Name", c.convert("name"));
		assertEquals("Name", c.convert("NAME"));
		assertEquals("Content-Type", c.convert("conTent-tYpe"));
	}
}
