package jp.hishidama.ant.types.htlex;

import static org.junit.Assert.*;
import jp.hishidama.html.lexer.token.Tag;

import org.junit.Test;

public class AttrAddTypeTest {

	@Test
	public void testAdd() {
		AttrAddType at = new AttrAddType();
		at.setNewName("test");
		at.setNewValue("val");

		HtLexerConverter conv = new HtLexerConverter();
		at.initHtLexerConverter(conv);
		at.validate();

		Tag tag = new Tag();
		at.add(tag);
		assertEquals(" test=\"val\"", tag.getText());
	}

	@Test
	public void testAddCase() {
		AttrAddType at = new AttrAddType();
		at.setNewName("test");
		at.setNewValue("VAL\"val");
		at.setNewValueCase(new CaseEnum(CaseEnum.PROPER));
		at.setNewValueHtmlEscape(new HtmlEscapeEnum(HtmlEscapeEnum.ALL));

		HtLexerConverter conv = new HtLexerConverter();
		at.initHtLexerConverter(conv);
		at.validate();

		Tag tag = new Tag();
		at.add(tag);
		assertEquals(" test=\"Val&quot;Val\"", tag.getText());
	}
}
