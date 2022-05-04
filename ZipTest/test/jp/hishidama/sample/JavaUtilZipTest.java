package jp.hishidama.sample;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.*;

import static org.junit.Assert.*;
import org.junit.Test;

public class JavaUtilZipTest {

	@Test
	public void testCRC() throws IOException {
		File f = new File("experiment/crc.zip");
		{
			ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(f));

			ZipEntry ze = new ZipEntry("test/java.txt");
			zos.putNextEntry(ze);
			zos.write("java.util".getBytes());
			zos.close();
		}
		{
			ZipFile zf = new ZipFile(f);

			ZipEntry ze = zf.getEntry("test/java.txt");
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

			ZipEntry ze = zf.getEntry("test/java.txt");
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
	public void testCheckedInputStream() throws IOException {
		File f = new File("experiment/crc.zip");
		{
			ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(f));

			ZipEntry ze = new ZipEntry("test/java.txt");
			zos.putNextEntry(ze);
			zos.write("java.util".getBytes());
			zos.close();
		}
		{
			ZipFile zf = new ZipFile(f);

			ZipEntry ze = zf.getEntry("test/java.txt");
			CheckedInputStream is = new CheckedInputStream(zf
					.getInputStream(ze), new CRC32());

			byte[] buf = new byte[256];
			for (;;) {
				int len = is.read(buf);
				if (len < 0)
					break;
			}
			is.close();

			assertEquals(ze.getCrc(), is.getChecksum().getValue());
			zf.close();
		}
	}
}
