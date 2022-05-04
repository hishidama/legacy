package jp.hishidama.util;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Test;

public class WeakListTest {

	static class Dump {
		int no;

		Dump(int no) {
			this.no = no;
		}
	}

	List<Dump> flist;

	WeakList<Dump> create(int n) {

		flist = new ArrayList<Dump>(n);

		WeakList<Dump> list = new WeakList<Dump>();
		for (int i = 0; i < n; i++) {
			Dump d = new Dump(i);
			flist.add(d);
			list.add(d);
		}
		return list;
	}

	@Test(expected = IllegalArgumentException.class)
	public void addFirstNull() {
		WeakList<Dump> list = create(10);
		list.addFirst(null);
	}

	@Test
	public void addFirst() {
		for (int i = 0; i < 3; i++) {
			WeakList<Dump> list = create(i);
			Dump d = new Dump(-1);
			list.addFirst(d);
			for (int j = -1; j < i; j++) {
				assertEquals(j, list.get(j + 1).no);
			}
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void addLastNull() {
		WeakList<Dump> list = create(10);
		list.addLast(null);
	}

	@Test
	public void addLast() {
		for (int i = 0; i < 3; i++) {
			WeakList<Dump> list = create(i);
			Dump d = new Dump(i);
			list.addLast(d);
			for (int j = 0; j <= i; j++) {
				assertEquals(j, list.get(j).no);
			}
		}
	}

	@Test(expected = NoSuchElementException.class)
	public void getFirst0() {
		WeakList<Dump> list = create(0);
		list.getFirst();
	}

	@Test
	public void getFirst() {
		for (int i = 1; i < 3; i++) {
			WeakList<Dump> list = create(i);
			assertEquals(0, list.getFirst().no);
		}
	}

	@Test(expected = NoSuchElementException.class)
	public void getLast0() {
		WeakList<Dump> list = create(0);
		list.getLast();
	}

	@Test
	public void getLast() {
		for (int i = 1; i < 3; i++) {
			WeakList<Dump> list = create(i);
			assertEquals(i - 1, list.getLast().no);
		}
	}

	@Test(expected = NoSuchElementException.class)
	public void removeFirst0() {
		WeakList<Dump> list = create(0);
		list.removeFirst();
	}

	@Test
	public void removeFirst() {
		for (int i = 1; i < 3; i++) {
			WeakList<Dump> list = create(i);
			assertEquals(0, list.removeFirst().no);
			assertEquals(i - 1, list.size());
		}
	}

	@Test(expected = NoSuchElementException.class)
	public void removeLast0() {
		WeakList<Dump> list = create(0);
		list.removeLast();
	}

	@Test
	public void removeLast() {
		for (int i = 1; i < 3; i++) {
			WeakList<Dump> list = create(i);
			assertEquals(i - 1, list.removeLast().no);
			assertEquals(i - 1, list.size());
		}
	}

	@Test
	public void contains() {
		for (int i = 0; i < 10; i++) {
			WeakList<Dump> list = create(i);
			assertFalse(list.contains(null));

			for (int j = 0; j < i; j++) {
				Dump d = flist.get(j);
				assertTrue(list.contains(d));
			}

			Dump d = new Dump(0);
			assertFalse(list.contains(d));
		}
	}

	@Test
	public void size() {
		for (int i = 0; i < 10; i++) {
			WeakList<Dump> list = create(i);
			assertEquals(i, list.size());
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void addNull() {
		WeakList<Dump> list = create(10);
		list.add(null);
	}

	@Test
	public void add() {
		for (int i = 0; i < 3; i++) {
			WeakList<Dump> list = create(i);
			Dump d = new Dump(i);
			assertTrue(list.add(d));
			for (int j = 0; j <= i; j++) {
				assertEquals(j, list.get(j).no);
			}
		}
	}

	@Test
	public void remove() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < i; j++) {
				WeakList<Dump> list = create(i);
				assertFalse(list.remove(null));
				assertFalse(list.remove(new Dump(-1)));

				Dump d = flist.get(j);
				assertTrue(list.remove(d));
				assertEquals(i - 1, list.size());
				assertFalse(list.contains(d));
			}
		}
	}

	@Test
	public void clear() {
		for (int i = 0; i < 3; i++) {
			WeakList<Dump> list = create(i);
			list.clear();
			assertEquals(0, list.size());
			try {
				list.removeFirst();
				fail();
			} catch (NoSuchElementException e) {
				// success
			}
		}
	}

	@Test
	public void get() {
		for (int i = 0; i < 10; i++) {
			WeakList<Dump> list = create(i);
			for (int j = 0; j < list.size(); j++) {
				assertEquals(flist.get(j), list.get(j));
			}
			try {
				list.get(-1);
				fail();
			} catch (IndexOutOfBoundsException e) {
				// success
			}
			try {
				list.get(i);
				fail();
			} catch (IndexOutOfBoundsException e) {
				// success
			}
		}
	}

	@Test
	public void iterator() {
		for (int i = 0; i < 3; i++) {
			WeakList<Dump> list = create(i);
			Iterator<Dump> r = list.iterator();
			for (int j = 0; r.hasNext(); j++) {
				assertEquals(flist.get(j), r.next());
			}
			assertFalse(r.hasNext());
		}
	}

	@Test
	public void iterator_remove() {
		for (int i = 1; i < 3; i++) {
			for (int j = 0; j < i; j++) {
				WeakList<Dump> list = create(i);
				Iterator<Dump> r = list.iterator();
				for (int k = 0; k <= j; k++) {
					r.next();
				}
				r.remove();
				Dump d = flist.get(j);
				assertFalse(list.contains(d));
			}
		}
	}

	//
	//
	//

	@Test
	public void expungeStaleEntries() throws InterruptedException {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < i; j++) {
				WeakList<Dump> list = create(i);

				int no = flist.remove(j).no;

				System.gc();
				Thread.sleep(10);
				for (Dump d : list) {
					if (d.no == no)
						fail();
				}
			}
		}

		for (int i = 0; i < 10; i++) {
			WeakList<Dump> list = create(i);
			flist.clear();

			System.gc();
			Thread.sleep(10);
			assertEquals(0, list.size());
		}
	}
}
