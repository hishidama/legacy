package jp.hishidama.util;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class DoubleOrderedHashMapTest {

	protected DoubleOrderedHashMap<Integer, String> map;

	@Before
	public void setUp() {
		map = new DoubleOrderedHashMap<Integer, String>();
		map.put(123, "abc");
		map.put(456, "def");
	}

	@Test
	public void testPut() {
		assertEquals(2, map.size());

		assertEquals("abc", map.get(123));
		assertEquals("def", map.get(456));
		assertNull(map.get(789));

		assertEquals(123, (int) map.getKeyForValue("abc"));
		assertEquals(456, (int) map.getKeyForValue("def"));
		assertNull(map.getKeyForValue("zzz"));

		assertTrue(map.containsKey(123));
		assertTrue(map.containsKey(456));
		assertFalse(map.containsKey(789));

		assertTrue(map.containsValue("abc"));
		assertTrue(map.containsValue("def"));
		assertFalse(map.containsValue("zzz"));
	}

	@Test
	public void testRemove() {
		map.remove(123);

		assertEquals(1, map.size());

		assertNull(map.get(123));
		assertEquals("def", map.get(456));

		assertNull(map.getKeyForValue("abc"));
		assertEquals(456, (int) map.getKeyForValue("def"));

		assertFalse(map.containsKey(123));
		assertTrue(map.containsKey(456));

		assertFalse(map.containsValue("abc"));
		assertTrue(map.containsValue("def"));
	}

	@Test
	public void testClear() {
		map.clear();

		assertEquals(0, map.size());

		assertNull(map.get(123));
		assertNull(map.get(456));

		assertNull(map.getKeyForValue("abc"));
		assertNull(map.getKeyForValue("def"));

		assertFalse(map.containsKey(123));
		assertFalse(map.containsKey(456));

		assertFalse(map.containsValue("abc"));
		assertFalse(map.containsValue("def"));
	}

	@Test
	public void testClone() {
		DoubleOrderedHashMap<Integer, String> c = map.clone();
		assertEquals(map, c);
		assertNotSame(map, c);
		assertEquals(map.reverseMap, c.reverseMap);
		assertNotSame(map.reverseMap, c.reverseMap);
	}
}
