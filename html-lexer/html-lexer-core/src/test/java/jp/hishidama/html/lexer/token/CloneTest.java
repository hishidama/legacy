package jp.hishidama.html.lexer.token;

import static org.junit.Assert.*;

import java.io.IOException;

import jp.hishidama.html.lexer.rule.HtLexer;

import org.junit.Test;

public class CloneTest {

	@Test
	public void test() {
		assertClone(new AtomToken());
		assertClone(new AttributeToken());
		assertClone(new CData());
		assertClone(new Comment());
		assertClone(new CrLfAtom(new StringBuilder("\r\n")));
		assertClone(new ListToken());
		assertClone(new MarkDeclare());
		assertClone(new Markup());
		assertClone(new NameAtom());
		assertClone(new ScriptToken());
		assertClone(new SkipToken());
		assertClone(new SpaceAtom(new StringBuilder("\t \t")));
		assertClone(new Tag());
		assertClone(new TextToken());
		assertClone(new ValueToken());
		assertClone(new WordAtom());
		assertClone(new XmlDeclare());
	}

	@Test
	public void test2() throws IOException {
		HtLexer lexer = new HtLexer(
				"<html><a href='http://www.ne.jp'>abc</a></html>");
		ListToken list = lexer.parse();
		lexer.close();
		assertClone(list);
	}

	protected void assertClone(Token t) {
		t.line = 1;
		if (t instanceof AtomToken) {
			AtomToken a = (AtomToken) t;
			a.atom = new StringBuilder("abc");
		}
		if (t instanceof ListToken) {
			ListToken l = (ListToken) t;
			l.list.add(new AtomToken("abc"));
			l.list.add(null);
			l.list.add(new AtomToken("def"));
		}

		Token n;
		try {
			n = t.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
		assertEqualsToken(t, n);
	}

	protected void assertEqualsToken(Token e, Token n) {
		if (e == null) {
			assertNull(n);
			return;
		}
		assertSame(e.getClass(), n.getClass());
		assertNotSame(e, n);
		assertEquals(e.line, n.line);

		if (e instanceof AtomToken) {
			AtomToken a = (AtomToken) e;
			AtomToken na = (AtomToken) n;
			assertNotSame(a.atom, na.atom);
			assertEquals(a.atom.toString(), na.atom.toString());
		}
		if (e instanceof ListToken) {
			ListToken l = (ListToken) e;
			ListToken nl = (ListToken) n;
			assertNotSame(l.list, nl.list);
			assertEquals(l.list.size(), nl.list.size());
			for (int i = 0; i < l.list.size(); i++) {
				assertEqualsToken(l.get(i), nl.get(i));
			}
		}
	}
}
