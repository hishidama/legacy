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
 * ファイル名のエンコーディングのテスト.
 */
public class FileEncodingTest {

	static String dir = "experiment/";

	@Test
	public void testNormal() throws IOException {
		test(null, null, null);
		test(null, "UTF-8", "UTF-8");
		test(null, "MS932", "MS932");
		test(null, "MS932", "JISAutoDetect");
		test(null, "EUC_JP", "EUC_JP");
		test(null, "EUC_JP", "JISAutoDetect");
		// test(null, "EUC_JP", "MS932");
	}

	@Test
	public void testPassword() throws IOException {
		test("abc", null, null);
		test("abc", "UTF-8", "UTF-8");
		test("abc", "MS932", "MS932");
		test("abc", "MS932", "JISAutoDetect");
		test("abc", "EUC_JP", "EUC_JP");
		test("abc", "EUC_JP", "JISAutoDetect");
		// test("abc", "EUC_JP", "MS932");
	}

	void test(String password, String encenc, String decenc) throws IOException {
		TestData[] data = { new TestData("test/あいうえお1.txt", 10),
				new TestData("test/かきくけこ2.txt", 256),
				new TestData("test/さしすせそ3.txt", 65535),
				new TestData("dummy/", 0) };
		Map<String, TestData> map = new HashMap<String, TestData>(data.length);
		for (int i = 0; i < data.length; i++) {
			TestData d = data[i];
			map.put(d.name, d);
		}

		File f = new File(dir + "test_src_" + password + ".zip");
		ZipOutputStream zos;
		if (true) {
			zos = new ZipOutputStream(new FileOutputStream(f), encenc);
		} else {
			zos = new ZipOutputStream(new FileOutputStream(f));
			zos.setEncoding(encenc);
		}
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

		ZipFile zf;
		if (true) {
			zf = new ZipFile(f, decenc);
		} else {
			// zf = new ZipFile(f);
			// zf.setEncoding(decenc);
		}
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
