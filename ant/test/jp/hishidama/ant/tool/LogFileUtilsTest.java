package jp.hishidama.ant.tool;

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

public class LogFileUtilsTest extends TestCase {

	public void testReplaceDir() {
		assertEqualsReplaceDir("C:\\temp_zzz", "C:\\temp_dest", "\\test.txt");
		assertEqualsReplaceDir("C:\\temp_zzz\\", "C:\\temp_dest\\", "test.txt");
		assertEqualsReplaceDir("/temp_zzz", "/temp_dest", "/test.txt");
		assertEqualsReplaceDir("/temp_zzz/", "/temp_dest/", "test.txt");
		assertEqualsReplaceDir("/temp_zzz", "/temp_dest/sub1", "/test.txt");
		assertEqualsReplaceDir("/temp_src", "/temp_dst", "/sub1/test.txt");
		assertEqualsReplaceDir("/temp_src/", "/temp_dst/", "sub1/test.txt");
	}

	protected void assertEqualsReplaceDir(String base, String dest, String path) {
		LogFileUtils fu = new LogFileUtils();
		try {
			assertEquals(new File(dest + path), fu.replaceDir(new File(base
					+ path), new File(base), new File(dest)));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
