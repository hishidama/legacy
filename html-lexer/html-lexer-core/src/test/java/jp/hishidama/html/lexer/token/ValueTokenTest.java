package jp.hishidama.html.lexer.token;

import static org.junit.Assert.*;

import org.junit.Test;

public class ValueTokenTest {

	@Test
	public void testValueTokenStringStringString() {
		ValueToken token = new ValueToken("q1", "value", "q2");

		StringBuilder sb = new StringBuilder();
		token.writeTo(sb);
		assertEquals("q1valueq2", sb.toString());
	}

	@Test
	public void setQuote1() {
		ValueToken token = new ValueToken();

		token.setQuote1((String) null);
		assertNull(token.getQuote1());

		token.setQuote1("q");
		assertEquals("q", token.getQuote1());
		token.setQuote1("q1");
		assertEquals("q1", token.getQuote1());
	}

	@Test
	public void setQuote2() {
		ValueToken token = new ValueToken();

		token.setQuote2((String) null);
		assertNull(token.getQuote2());

		token.setQuote2("q");
		assertEquals("q", token.getQuote2());
		token.setQuote2("q2");
		assertEquals("q2", token.getQuote2());
	}

	@Test
	public void setValue() {
		ValueToken token = new ValueToken();

		token.setValue((String) null);
		assertNull(token.getValue());

		token.setValue("v");
		assertEquals("v", token.getValue());
		token.setValue("va");
		assertEquals("va", token.getValue());
	}
}
