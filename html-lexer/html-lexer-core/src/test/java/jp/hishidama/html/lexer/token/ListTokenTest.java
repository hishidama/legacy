package jp.hishidama.html.lexer.token;

import static org.junit.Assert.*;

import org.junit.Test;

public class ListTokenTest {

	@Test
	public void testRemove() {
		ListToken list = new ListToken();

		Token[] ts = new Token[5];
		for (int i = 0; i < ts.length; i++) {
			list.add(ts[i] = new AtomToken());
		}

		Token r = list.remove(new AtomToken());
		assertNull(r);

		r = list.remove(ts[2]);
		assertSame(ts[2], r);
		assertEquals(ts.length - 1, list.size());
		int j = 0;
		assertSame(ts[0], list.get(j++));
		assertSame(ts[1], list.get(j++));
		assertSame(ts[3], list.get(j++));
		assertSame(ts[4], list.get(j++));

		r = list.remove(ts[0]);
		assertSame(ts[0], r);
		assertEquals(ts.length - 2, list.size());
		j = 0;
		assertSame(ts[1], list.get(j++));
		assertSame(ts[3], list.get(j++));
		assertSame(ts[4], list.get(j++));

		r = list.remove(ts[4]);
		assertSame(ts[4], r);
		assertEquals(ts.length - 3, list.size());
		j = 0;
		assertSame(ts[1], list.get(j++));
		assertSame(ts[3], list.get(j++));
	}

	@Test
	public void testCutIntInt() {
		ListToken list = new ListToken();

		Token[] ts = new Token[5];
		for (int i = 0; i < ts.length; i++) {
			list.add(ts[i] = new AtomToken());
		}

		assertNull(list.cut(-1, 2));
		assertNull(list.cut(2, -1));
		assertNull(list.cut(2, ts.length));
		assertNull(list.cut(ts.length, 2));

		ListToken nlist = list.cut(1, 3);
		assertEquals(3, nlist.size());
		int j = 0;
		assertSame(ts[1], nlist.get(j++));
		assertSame(ts[2], nlist.get(j++));
		assertSame(ts[3], nlist.get(j++));
		j = 0;
		assertEquals(2, list.size());
		assertSame(ts[0], list.get(j++));
		assertSame(ts[4], list.get(j++));
	}

	@Test
	public void testCutTokenToken() {
		ListToken list = new ListToken();

		Token[] ts = new Token[5];
		for (int i = 0; i < ts.length; i++) {
			list.add(ts[i] = new AtomToken());
		}

		assertNull(list.cut(new AtomToken(), ts[2]));
		assertNull(list.cut(ts[2], new AtomToken()));

		ListToken nlist = list.cut(null, null);
		assertEquals(0, list.size());
		assertEquals(ts.length, nlist.size());
		for (int i = 0; i < ts.length; i++) {
			assertSame(ts[i], nlist.get(i));
		}

		list = nlist;
		nlist = list.cut(ts[3], null);
		assertEquals(2, nlist.size());
		int j = 0;
		assertSame(ts[3], nlist.get(j++));
		assertSame(ts[4], nlist.get(j++));
		j = 0;
		assertEquals(3, list.size());
		assertSame(ts[0], list.get(j++));
		assertSame(ts[1], list.get(j++));
		assertSame(ts[2], list.get(j++));

		nlist = list.cut(null, ts[1]);
		assertEquals(2, nlist.size());
		j = 0;
		assertSame(ts[0], nlist.get(j++));
		assertSame(ts[1], nlist.get(j++));
		j = 0;
		assertEquals(1, list.size());
		assertSame(ts[2], list.get(j++));

		list = new ListToken();
		for (int i = 0; i < ts.length; i++) {
			list.add(ts[i]);
		}
		assertNull(list.cut(ts[2], ts[1])); // 逆転している場合はnull

		nlist = list.cut(ts[1], ts[2]);
		assertEquals(2, nlist.size());
		j = 0;
		assertSame(ts[1], nlist.get(j++));
		assertSame(ts[2], nlist.get(j++));
		j = 0;
		assertEquals(3, list.size());
		assertSame(ts[0], list.get(j++));
		assertSame(ts[3], list.get(j++));
		assertSame(ts[4], list.get(j++));
	}

	@Test
	public void testCutWithPreSkip() {
		ListToken list = new ListToken();

		Token[] ts = new Token[] { new WordAtom("hoge"),
				new CrLfAtom(new StringBuilder("\r\n")),
				new SpaceAtom(new StringBuilder("  ")), new SkipToken(),
				new AtomToken(), new CrLfAtom(new StringBuilder("\r\n")) };
		for (int i = 0; i < ts.length; i++) {
			list.add(ts[i]);
		}

		ListToken nlist = list.cutWithPreSkip(ts[4]);
		assertEquals(4, nlist.size());
		int j = 0;
		assertSame(ts[1], nlist.get(j++));
		assertSame(ts[2], nlist.get(j++));
		assertSame(ts[3], nlist.get(j++));
		assertSame(ts[4], nlist.get(j++));
		j = 0;
		assertEquals(2, list.size());
		assertSame(ts[0], list.get(j++));
		assertSame(ts[5], list.get(j++));

		nlist = list.cutWithPreSkip(ts[0]);
		assertEquals(1, nlist.size());
		assertSame(ts[0], nlist.get(0));
		assertEquals(1, list.size());
		assertSame(ts[5], list.get(0));

		list = new ListToken();
		for (int i = 1; i < ts.length; i++) {
			list.add(ts[i]);
		}
		nlist = list.cutWithPreSkip(ts[4]);
		assertEquals(4, nlist.size());
		j = 0;
		assertSame(ts[1], nlist.get(j++));
		assertSame(ts[2], nlist.get(j++));
		assertSame(ts[3], nlist.get(j++));
		assertSame(ts[4], nlist.get(j++));
		j = 0;
		assertEquals(1, list.size());
		assertSame(ts[5], list.get(j++));
	}
}
