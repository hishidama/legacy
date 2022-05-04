package jp.hishidama.html.lexer.token;

import static org.junit.Assert.*;
import jp.hishidama.html.lexer.token.Tag;

import org.junit.Test;

public class TagTest {

	@Test
	public void testIsStart() {
		Tag tag = new Tag();
		tag.setTag1(new StringBuilder("<"));
		assertTrue(tag.isStart());

		tag.setTag1(new StringBuilder("</"));
		assertFalse(tag.isStart());

		tag.setTag1(null);
		assertFalse(tag.isStart());
	}

	@Test
	public void testIsEnd() {
		Tag tag = new Tag();
		tag.setTag1(new StringBuilder("<"));
		tag.setTag2(new StringBuilder(">"));
		assertFalse(tag.isEnd());

		tag.setTag1(new StringBuilder("</"));
		tag.setTag2(new StringBuilder(">"));
		assertTrue(tag.isEnd());

		tag.setTag1(new StringBuilder("<"));
		tag.setTag2(new StringBuilder("/>"));
		assertTrue(tag.isEnd());

		tag.setTag1(null);
		tag.setTag2(new StringBuilder(">"));
		assertFalse(tag.isEnd());
		tag.setTag1(new StringBuilder("<"));
		tag.setTag2(null);
		assertFalse(tag.isEnd());
	}
}
