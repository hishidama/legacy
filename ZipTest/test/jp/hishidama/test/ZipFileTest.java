package jp.hishidama.test;

import java.io.*;
import java.util.*;
import java.util.zip.CRC32;

import jp.hishidama.zip.ZipEntry;
import jp.hishidama.zip.ZipFile;
import jp.hishidama.zip.ZipOutputStream;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * ZipOutputStream‚¨‚æ‚ÑZipFile‚ÌƒeƒXƒg.
 */
public class ZipFileTest {

	static String dir = "experiment/";

	@Test
	public void testNormal() throws IOException {
		test(null);
	}

	@Test
	public void testPassword() throws IOException {
		test("abc");
	}

	void test(String password) throws IOException {
		TestData[] data = { new TestData("test/d1.txt", 10),
				new TestData("test/d2.txt", 256),
				new TestData("test/d3.txt", 65535), new TestData("dummy/", 0) };
		Map<String, TestData> map = new HashMap<String, TestData>(data.length);
		for (int i = 0; i < data.length; i++) {
			TestData d = data[i];
			map.put(d.name, d);
		}

		File f = new File(dir + "test_src_" + password + ".zip");
		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(f));
		if (password != null) {
			zos.setPassword(password.getBytes());
		}

		for (int i = 0; i < data.length; i++) {
			TestData d = data[i];
			ZipEntry ze = new ZipEntry(d.name);
			zos.putNextEntry(ze);
			zos.write(d.data);
		}
		zos.close();

		ZipFile zf = new ZipFile(f);
		if (password != null) {
			zf.setPassword(password.getBytes());
		}
		for (Iterator<ZipEntry> i = zf.getEntriesIterator(); i.hasNext();) {
			ZipEntry ze = i.next();
			InputStream is = zf.getInputStream(ze);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			for (;;) {
				int c = is.read();
				if (c == -1)
					break;
				bos.write(c);
			}
			is.close();

			TestData d = map.get(ze.getName());
			assertTrue(Arrays.equals(d.data, bos.toByteArray()));

			CRC32 crc = new CRC32();
			crc.update(bos.toByteArray());
			assertEquals(ze.getCrc(), crc.getValue());
			// System.out.printf("ze:%04x crc32:%04x%n", ze.getCrc(),
			// crc.getValue());
		}
		zf.close();
	}

}
