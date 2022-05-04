package jp.hishidama.ant.types;

import static org.junit.Assert.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import jp.hishidama.ant.taskdefs.MapPropertyTask;
import jp.hishidama.ant.taskdefs.MapPropertyTask.MapEntry;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.PropertyHelper;
import org.junit.Before;
import org.junit.Test;

public class MapMapperTest {

	protected MapMapper mm;

	@Before
	public void setUp() throws Exception {
		mm = new MapMapper();
		mm.setProject(new Project());
	}

	@Test
	public void testFromTo() {
		String r = "C:/temp/";
		mm.setPrefix(r);

		String k = "zzz/fr1";
		String v = "abc/to1";
		mm.setFrom(r + k);
		mm.setTo(r + v);

		String[] s = mm.mapFileName(data(k));
		assertArrayEquals(new String[] { data(v) }, s);
		assertNull(mm.mapFileName("aaa"));
	}

	@Test
	public void testProperty() {
		String r = "C:/temp/";
		mm.setPrefix(r);

		String k = "zzz/fr1";
		String v = "abc/to1";
		Map<String, String> map = new HashMap<String, String>();
		map.put(r + k, r + v);
		PropertyHelper ph = PropertyHelper.getPropertyHelper(mm.getProject());
		ph.setProperty("prop", map, false);
		mm.setProperty("prop");

		String[] s = mm.mapFileName(data(k));
		assertArrayEquals(new String[] { data(v) }, s);
		assertNull(mm.mapFileName("aaa"));
	}

	@Test
	public void testMapEntry() {
		String r = "C:/temp/";
		mm.setPrefix(r);

		String k = "zzz/fr1";
		String v = "abc/to1";
		MapEntry entry = new MapEntry();
		entry.setKey(r + k);
		entry.setValue(r + v);
		mm.addConfiguredEntry(entry);

		String[] s = mm.mapFileName(data(k));
		assertArrayEquals(new String[] { data(v) }, s);
		assertNull(mm.mapFileName("aaa"));
	}

	@Test
	public void testMapPropertyTask() {
		String r = "C:/temp/";
		mm.setPrefix(r);

		String k = "zzz/fr1";
		String v = "abc/to1";
		MapPropertyTask mptask = new MapPropertyTask();
		MapEntry entry = new MapEntry();
		entry.setKey(r + k);
		entry.setValue(r + v);
		mptask.addConfiguredEntry(entry);
		mm.addConfiguredMapProperty(mptask);

		String[] s = mm.mapFileName(data(k));
		assertArrayEquals(new String[] { data(v) }, s);
		assertNull(mm.mapFileName("aaa"));
	}

	protected static String data(String path) {
		return path.replace('/', File.separatorChar);
	}
}
