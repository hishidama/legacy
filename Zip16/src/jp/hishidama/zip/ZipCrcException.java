package jp.hishidama.zip;

import java.util.zip.ZipException;

/**
 * CRC•sˆê’v—áŠO.
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/zip.html"
 *         >‚Ð‚µ‚¾‚Ü</a>
 * @since 2008.12.21
 * @version 2008.12.22
 * @see ZipFile#setCheckCrc(boolean)
 */
public class ZipCrcException extends ZipException {

	private static final long serialVersionUID = 8267257760411378959L;

	public ZipCrcException(long crc, long expected) {
		super(String.format("CRC unmatch. crc=%04x entry=%04x", crc, expected));
	}
}
