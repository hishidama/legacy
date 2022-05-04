package jp.hishidama.zip;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import static jp.hishidama.zip.InfoZIP_Tailor.*;

/**
 * win32zip.c fileio.cの移植
 *
 * @since 2007.12.14
 * @version 2008.12.21
 */
class InfoZIP_Native extends InfoZIP_Globals {

	/**
	 * OSコード取得.
	 *
	 * @return {@link #OS_CODE_WIN32}等
	 */
	protected int getOScode() {
		String os_name = System.getProperty("os.name");
		if (os_name != null && os_name.startsWith("Windows")) {
			return OS_CODE_WIN32;
		}

		/* assume Unix */
		return OS_CODE_UNIX;
	}

	protected String encoding;

	/**
	 * エンコーディング設定.
	 *
	 * @param encoding
	 *            エンコーディング
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	/**
	 * エンコーディング取得.
	 *
	 * @return エンコーディング
	 */
	public String getEncoding() {
		return encoding;
	}

	protected String getString(byte[] bytes) {
		if (bytes == null) {
			return null;
		}
		if (encoding == null) {
			return new String(bytes);
		} else {
			try {
				return new String(bytes, encoding);
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
		}
	}

	private static final int A_RONLY = 0x01;

	private static final int A_HIDDEN = 0x02;

	// private static final int A_SYSTEM = 0x04;

	// private static final int A_LABEL = 0x08;

	private static final int A_DIR = 0x10;

	// private static final int A_ARCHIVE = 0x20;

	/**
	 * @param f
	 *            name of file to get info on
	 * @param a
	 *            return value: file attributes
	 * @param n
	 *            return value: file size
	 * @param t
	 *            return value: access, modific. and creation times
	 */
	protected void filetime(File f, int[] a, int[] n, int[] t) {
		int atx = 0;
		if (dosify != 0) {
			if (f.isDirectory()) {
				atx |= A_DIR;
			}
			if (f.isHidden()) {
				atx |= A_HIDDEN;
			}
			if (f.canRead() && !f.canWrite()) {
				atx |= A_RONLY;
			}
		} else {
			if (f.isFile()) {
				atx |= 0100000;
			}
			if (f.isDirectory()) {
				atx |= 0040000;
			}
			if (f.canRead()) {
				atx |= 00400; // user
				// atx |= 00040; //group
				// atx |= 00004; //other
			}
			if (f.canWrite()) {
				atx |= 00200; // user
				// atx |= 00020; // group
				// atx |= 00002; // other
			}
			if (f.canExecute()) {
				atx |= 00100;// user
				atx |= 00010;// group
				atx |= 00001;// other
			}
		}
		a[0] = atx;

		if (n != null || t != null) {
			throw new RuntimeException("未実装");
		}
	}

	private final byte[] b = new byte[CBSZ]; /* malloc'ed buffer for copying */

	/**
	 * Copy n bytes from file *f to file *g, or until EOF if (long)n == -1.
	 * Return an error code in the ZE_ class.
	 *
	 * @param f
	 *            source
	 * @param g
	 *            destination
	 * @param n
	 *            number of bytes to copy or -1 for all
	 * @throws IOException
	 */
	protected void fcopy(RandomAccessFile f, RandomAccessFile g, long n)
			throws IOException {
		int k; /* result of fread() */
		int m; /* bytes copied so far */

		m = 0;
		while (n == -1 || m < n) {
			long len = (n == -1) ? CBSZ : ((n - m < CBSZ) ? (n - m) : CBSZ);
			if ((k = f.read(b, 0, (int) len)) <= 0) {
				break;
			}
			g.write(b, 0, k);
			m += k;
		}
	}

	/**
	 * ファイルクローズ.
	 *
	 * @param obj
	 *            クローズ対象
	 * @since 2008.01.19
	 */
	protected void close(Closeable obj) {
		if (obj == null)
			return;
		try {
			obj.close();
		} catch (IOException e) {
		}
	}
}
