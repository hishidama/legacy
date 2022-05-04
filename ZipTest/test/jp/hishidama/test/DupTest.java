package jp.hishidama.test;

import java.io.*;
import java.util.*;
import java.util.zip.CRC32;

import jp.hishidama.zip.ZipEntry;
import jp.hishidama.zip.ZipFile;
import jp.hishidama.zip.ZipOutputStream;
import jp.hishidama.zip.ZipPasswordException;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class DupTest {

	static File ZIP_FILE_TEST = new File("experiment/dup.zip");
	static File ZIP_FILE_WIN = new File("experiment/dup_win2.zip"); // WindowsXPÇ≈çÏÇ¡ÇΩzipÉtÉ@ÉCÉã
	static File ZIP_FILE = ZIP_FILE_TEST;

	static byte[] DATA = "ABC0123Z".getBytes();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		if (ZIP_FILE == ZIP_FILE_TEST) {
			ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(
					ZIP_FILE), "MS932");
			zos.setPassword("qwerty".getBytes("MS932"));

			ZipEntry ze = new ZipEntry("test/a.txt");
			zos.putNextEntry(ze);
			zos.write(DATA);

			zos.close();
		}
	}

	@Test
	public void testNormal() throws IOException {
		ZipFile zf = new ZipFile(ZIP_FILE);
		zf.setPassword("qwerty".getBytes());
		for (Iterator<ZipEntry> i = zf.getEntriesIterator(); i.hasNext();) {
			ZipEntry ze = i.next();
			System.out.println(ze.getName());
			InputStream is = zf.getInputStream(ze);

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			CRC32 crc = new CRC32();
			for (;;) {
				int c = is.read();
				if (c == -1)
					break;
				bos.write(c);
				crc.update(c);
			}
			is.close();

			assertEquals(ze.getCrc(), crc.getValue());
			// System.out.println("size: " + bos.size());
			// System.out.println(Arrays.toString(bos.toByteArray()));
			assertTrue(Arrays.equals(DATA, bos.toByteArray()));
		}
		zf.close();
	}

	@Test
	public void testDup() throws IOException {
		loop: for (int j = 0; j < 65536; j++) {
			// System.out.println(j);

			ZipFile zf = new ZipFile(ZIP_FILE);
			byte[] pass = createPassword(j);
			zf.setPassword(pass);

			String match = "Å°";

			for (Iterator<ZipEntry> i = zf.getEntriesIterator(); i.hasNext();) {
				ZipEntry ze = i.next();
				// System.out.println(ze.getName());
				InputStream is;
				try {
					is = zf.getInputStream(ze);
				} catch (ZipPasswordException e) {
					// System.out.printf("<G1>%04x(%s):(%s) %s%n", j,
					// toString(pass), e.getClass(), e.getMessage());
					zf.close();
					continue loop;
				} catch (IOException e) {
					System.out.printf("<G2>%04x(%s):(%s) %s%n", j,
							toString(pass), e.getClass(), e.getMessage());
					zf.close();
					continue loop;
				}
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				CRC32 crc = new CRC32();
				for (;;) {
					int c;
					try {
						c = is.read();
					} catch (IOException e) {
						System.out.printf("<R1>%04x(%s):(%s) %s%n", j,
								toString(pass), e.getClass(), e.getMessage());
						zf.close();
						continue loop;
					}
					if (c == -1)
						break;
					bos.write(c);
					crc.update(c);
				}
				is.close();

				if (crc.getValue() != ze.getCrc()) {
					System.out.printf("<C1>%04x(%s):%n", j, toString(pass));
					zf.close();
					continue loop;
				}

				// System.out.println("size: " + bos.size());
				// System.out.println(Arrays.toString(bos.toByteArray()));
				if (!Arrays.equals(DATA, bos.toByteArray())) {
					// zf.close();
					// continue loop;
					match = "Å£";
				}
			}
			zf.close();

			System.out.printf("%s%04x:(%s) %s%n", match, j, toString(pass),
					Arrays.toString(pass));
		}
	}

	private byte[] createPassword(int i) {
		byte[] buf = new byte[2];
		buf[0] = (byte) (i >>> 8);
		buf[1] = (byte) i;
		return buf;
	}

	private String toString(byte[] buf) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < buf.length; i++) {
			byte b = buf[i];
			if (0x20 < b && b < 0x7f) {
				sb.append((char) b);
			} else {
				return null;
			}
		}
		return sb.toString();
	}
}
