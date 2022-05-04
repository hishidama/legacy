package jp.hishidama.html.lexer.token;

import static org.junit.Assert.*;

import org.junit.Test;

public class AttributeTokenTest {

	@Test
	public void testAttributeTokenStringStringStringString() {
		AttributeToken token = new AttributeToken("name", "q1", "value", "q2");

		StringBuilder sb = new StringBuilder();
		token.writeTo(sb);
		assertEquals("name=q1valueq2", sb.toString());
	}
}
