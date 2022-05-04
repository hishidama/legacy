package jp.hishidama.test;

import static org.junit.Assert.*;

import java.io.*;
import java.util.Iterator;
import java.util.zip.CRC32;

import jp.hishidama.zip.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class CrcTest {
	static File f = new File("experiment/crc.zip");

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(f));
		zos.setPassword("abc".getBytes());

		ZipEntry ze = new ZipEntry("test/crc.txt");
		zos.putNextEntry(ze);
		zos.write("jp.hishidama.crc".getBytes());
		zos.close();
	}

	@Test
	public void testNormal() throws IOException {
		{
			ZipFile zf = new ZipFile(f);
			zf.setPassword("abc".getBytes());

			ZipEntry ze = zf.getEntry("test/crc.txt");
			InputStream is = zf.getInputStream(ze);
			CRC32 crc = new CRC32();
			for (;;) {
				int c = is.read();
				if (c < 0)
					break;
				crc.update(c);
			}
			is.close();

			assertEquals(ze.getCrc(), crc.getValue());
			zf.close();
		}
		{
			ZipFile zf = new ZipFile(f);
			zf.setPassword("abc".getBytes());

			ZipEntry ze = zf.getEntry("test/crc.txt");
			InputStream is = zf.getInputStream(ze);
			CRC32 crc = new CRC32();
			byte[] buf = new byte[256];
			for (;;) {
				int len = is.read(buf);
				if (len < 0)
					break;
				crc.update(buf, 0, len);
			}
			is.close();

			assertEquals(ze.getCrc(), crc.getValue());
			zf.close();
		}
	}

	@Test
	public void testCrc() throws IOException {
		final int count = 65536;
		loop: for (int j = 0; j < count; j++) {
			if (j % 256 == 0) {
				System.out.println(j + "/" + count);
			}

			ZipFile zf = new ZipFile("experiment/dup_win.zip");
			byte[] pass = createPassword(j);
			zf.setPassword(pass);
			zf.setCheckCrc(false);

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
				for (;;) {
					int c;
					try {
						c = is.read();
					} catch (ZipCrcException e) {
						System.out.printf("<R1>%04x(%s):(%s) %s%n", j,
								toString(pass), e.getClass(), e.getMessage());
						zf.close();
						fail();
						continue loop;
					} catch (IOException e) {
						System.out.printf("<R1>%04x(%s):(%s) %s%n", j,
								toString(pass), e.getClass(), e.getMessage());
						zf.close();
						continue loop;
					}
					if (c == -1) {
						break;
					}
					bos.write(c);
				}
				is.close();

				// System.out.println("size: " + bos.size());
				// System.out.println(Arrays.toString(bos.toByteArray()));
			}
			zf.close();
		}
	}

	@Test
	public void testCrcException() throws IOException {

		boolean ex = false;

		loop: for (int j = 0; j < 65536; j++) {
			// System.out.println(j);

			ZipFile zf = new ZipFile("experiment/dup_win.zip");
			byte[] pass = createPassword(j);
			zf.setPassword(pass);
			zf.setCheckCrc(true);

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
				for (;;) {
					int c;
					try {
						c = is.read();
					} catch (ZipCrcException e) {
						System.out.printf("<R1>%04x(%s):(%s) %s%n", j,
								toString(pass), e.getClass(), e.getMessage());
						zf.close();
						ex = true;
						continue loop;
					} catch (IOException e) {
						System.out.printf("<R1>%04x(%s):(%s) %s%n", j,
								toString(pass), e.getClass(), e.getMessage());
						zf.close();
						continue loop;
					}
					if (c == -1) {
						break;
					}
					bos.write(c);
				}
				is.close();

				// System.out.println("size: " + bos.size());
				// System.out.println(Arrays.toString(bos.toByteArray()));
			}
			zf.close();

			if (!ex)
				fail("CRC—áŠO‚ªˆê“x‚à”­¶‚µ‚È‚©‚Á‚½");
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
