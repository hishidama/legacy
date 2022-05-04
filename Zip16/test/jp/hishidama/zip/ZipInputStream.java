package jp.hishidama.zip;

import java.io.EOFException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.io.UnsupportedEncodingException;
import java.util.zip.CRC32;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import java.util.zip.ZipException;

public class ZipInputStream extends FilterInputStream {
	public static final int STORED = ZipEntry.STORED;

	public static final int DEFLATED = ZipEntry.DEFLATED;

	/*
	 * Header signatures
	 */
	static final long LOCSIG = 0x04034b50L; // "PK\003\004"

	static final long EXTSIG = 0x08074b50L; // "PK\007\008"

	static final long CENSIG = 0x02014b50L; // "PK\001\002"

	static final long ENDSIG = 0x06054b50L; // "PK\005\006"

	/*
	 * Header sizes in bytes (including signatures)
	 */
	static final int LOCHDR = 30; // LOC header size

	static final int EXTHDR = 16; // EXT header size

	static final int CENHDR = 46; // CEN header size

	static final int ENDHDR = 22; // END header size

	/*
	 * Local file (LOC) header field offsets
	 */
	static final int LOCVER = 4; // version needed to extract

	static final int LOCFLG = 6; // general purpose bit flag

	static final int LOCHOW = 8; // compression method

	static final int LOCTIM = 10; // modification time

	static final int LOCCRC = 14; // uncompressed file crc-32 value

	static final int LOCSIZ = 18; // compressed size

	static final int LOCLEN = 22; // uncompressed size

	static final int LOCNAM = 26; // filename length

	static final int LOCEXT = 28; // extra field length

	/*
	 * Extra local (EXT) header field offsets
	 */
	static final int EXTCRC = 4; // uncompressed file crc-32 value

	static final int EXTSIZ = 8; // compressed size

	static final int EXTLEN = 12; // uncompressed size

	protected String encoding;

	private ZipEntry entry;

	private CRC32 crc = new CRC32();

	private long remaining;

	private boolean closed = false;

	private boolean entryEOF = false;

	protected byte[] password;

	protected InfoZIP_Crypt crypt;

	private Inflater inf = new Inflater(true);

	private byte[] buf = new byte[512];

	private byte[] tmpbuf = new byte[512];

	/**
	 * Length of input buffer.
	 */
	protected int len;

	public ZipInputStream(InputStream in) {
		this(in, null);
	}

	public ZipInputStream(InputStream in, String encoding) {
		super(new PushbackInputStream(in, 512));
		this.encoding = encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setPassword(byte[] password) {
		this.password = password;
		if (password != null && crypt == null) {
			crypt = new InfoZIP_Crypt();
		}
	}

	public ZipEntry getNextEntry() throws IOException {
		if (entry != null) {
			closeEntry();
		}
		crc.reset();
		inf.reset();
		if ((entry = readLOC()) == null) {
			return null;
		}
		if (entry.getMethod() == STORED) {
			remaining = entry.getSize();
		}
		entryEOF = false;
		return entry;
	}

	public void closeEntry() throws IOException {
		while (read(tmpbuf, 0, tmpbuf.length) != -1)
			;
		entryEOF = true;
	}

	private byte[] b = new byte[256];

	/*
	 * Reads local file (LOC) header for next entry.
	 */
	private ZipEntry readLOC() throws IOException {
		try {
			readFully(tmpbuf, 0, LOCHDR);
		} catch (EOFException e) {
			return null;
		}
		if (get32(tmpbuf, 0) != LOCSIG) {
			return null;
		}
		// get the entry name and create the ZipEntry first
		int len = get16(tmpbuf, LOCNAM);
		int blen = b.length;
		if (len > blen) {
			do
				blen = blen * 2;
			while (len > blen);
			b = new byte[blen];
		}
		readFully(b, 0, len);
		ZipEntry e = new ZipEntry(getString(b, 0, len));
		// now get the remaining fields for the entry
		int flag = get16(tmpbuf, LOCFLG);
		e.setFlag(flag);

		e.setMethod(get16(tmpbuf, LOCHOW));
		long tim = get32(tmpbuf, LOCTIM);
		e.setTime(tim);
		if ((flag & 8) == 8) {
			/* "Data Descriptor" present */
			if (e.getMethod() != DEFLATED) {
				throw new ZipException(
						"only DEFLATED entries can have EXT descriptor");
			}
		} else {
			e.setCrc(get32(tmpbuf, LOCCRC));
			e.setCompressedSize(get32(tmpbuf, LOCSIZ));
			e.setSize(get32(tmpbuf, LOCLEN));
		}
		len = get16(tmpbuf, LOCEXT);
		if (len > 0) {
			byte[] bb = new byte[len];
			readFully(bb, 0, len);
			e.setExtra(bb);
		}

		if (e.isEncrypted()) {
			int check;
			if ((e.getFlag() & 8) != 0) {
				check = (int) ((tim & 0xffff) >>> 8);
			} else {
				check = (int) (e.getCrc() >>> 24);
			}
			crypt.barehead(in, check, password);
			// System.out.println(e.getName());
		}

		return e;
	}

	protected String getString(byte[] bytes, int offset, int length) {
		if (bytes == null) {
			return null;
		}
		if (encoding == null) {
			return new String(bytes, offset, length);
		} else {
			try {
				return new String(bytes, offset, length, encoding);
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public int available() throws IOException {
		if (entryEOF) {
			return 0;
		} else {
			return 1;
		}
	}

	private byte[] singleByteBuf = new byte[1];

	public int read() throws IOException {
		return read(singleByteBuf, 0, 1) == -1 ? -1 : singleByteBuf[0] & 0xff;
	}

	public int read(byte[] b, int off, int len) throws IOException {
		if (off < 0 || len < 0 || off > b.length - len) {
			throw new IndexOutOfBoundsException();
		} else if (len == 0) {
			return 0;
		}

		if (entry == null) {
			return -1;
		}
		switch (entry.getMethod()) {
		case DEFLATED:
			len = inflate(b, off, len);
			if (len == -1) {
				readEnd(entry);
				entryEOF = true;
				entry = null;
			} else {
				crc.update(b, off, len);
			}
			return len;
		case STORED:
			if (remaining <= 0) {
				entryEOF = true;
				entry = null;
				return -1;
			}
			if (len > remaining) {
				len = (int) remaining;
			}
			len = readCrypt(b, off, len);
			if (len == -1) {
				throw new ZipException("unexpected EOF");
			}
			crc.update(b, off, len);
			remaining -= len;
			return len;
		default:
			throw new ZipException("invalid compression method");
		}
	}

	/**
	 * Reads uncompressed data into an array of bytes. If <code>len</code> is
	 * not zero, the method will block until some input can be decompressed;
	 * otherwise, no bytes are read and <code>0</code> is returned.
	 *
	 * @param b
	 *            the buffer into which the data is read
	 * @param off
	 *            the start offset in the destination array <code>b</code>
	 * @param len
	 *            the maximum number of bytes read
	 * @return the actual number of bytes read, or -1 if the end of the
	 *         compressed input is reached or a preset dictionary is needed
	 * @exception NullPointerException
	 *                If <code>b</code> is <code>null</code>.
	 * @exception IndexOutOfBoundsException
	 *                If <code>off</code> is negative, <code>len</code> is
	 *                negative, or <code>len</code> is greater than
	 *                <code>b.length - off</code>
	 * @exception ZipException
	 *                if a ZIP format error has occurred
	 * @exception IOException
	 *                if an I/O error has occurred
	 */
	protected int inflate(byte[] b, int off, int len) throws IOException {
		try {
			int n;
			while ((n = inf.inflate(b, off, len)) == 0) {
				if (inf.finished() || inf.needsDictionary()) {
					return -1;
				}
				if (inf.needsInput()) {
					fill();
				}
			}
			return n;
		} catch (DataFormatException e) {
			String s = e.getMessage();
			throw new ZipException(s != null ? s : "Invalid ZLIB data format");
		}
	}

	/**
	 * Fills input buffer with more data to decompress.
	 *
	 * @exception IOException
	 *                if an I/O error has occurred
	 */
	protected void fill() throws IOException {
		len = readCrypt(buf, 0, buf.length);
		if (len == -1) {
			throw new EOFException("Unexpected end of ZLIB input stream");
		}
		inf.setInput(buf, 0, len);
	}

	protected int readCrypt(byte data[], int offset, int length)
			throws IOException {
		// TODO 圧縮サイズまでしか読んではいけないのだが、そのサイズを知る方法が無い
		int len = in.read(data, offset, length);
		if (password != null) {
			for (int i = 0, j = offset; i < len; i++, j++) {
				// System.out.println("r:" + (data[j] & 0xff));
				data[j] = (byte) crypt.zdecode(data[j]);
			}
		}
		return len;
	}

	public long skip(long n) throws IOException {
		if (n < 0) {
			throw new IllegalArgumentException("negative skip length");
		}
		int max = (int) Math.min(n, Integer.MAX_VALUE);
		int total = 0;
		while (total < max) {
			int len = max - total;
			if (len > tmpbuf.length) {
				len = tmpbuf.length;
			}
			len = read(tmpbuf, 0, len);
			if (len == -1) {
				entryEOF = true;
				break;
			}
			total += len;
		}
		return total;
	}

	public void close() throws IOException {
		if (!closed) {
			inf.end();
			super.close();
			closed = true;
		}
	}

	/*
	 * Reads end of deflated entry as well as EXT descriptor if present.
	 */
	private void readEnd(ZipEntry e) throws IOException {
		int n = inf.getRemaining();
		if (n > 0) {
			((PushbackInputStream) in).unread(buf, len - n, n);
		}
		if ((e.getFlag() & 8) == 8) {
			/* "Data Descriptor" present */
			readFully(tmpbuf, 0, EXTHDR);
			long sig = get32(tmpbuf, 0);
			if (sig != EXTSIG) { // no EXTSIG present
				e.setCrc(sig);
				e.setCompressedSize(get32(tmpbuf, EXTSIZ - EXTCRC));
				e.setSize(get32(tmpbuf, EXTLEN - EXTCRC));
				((PushbackInputStream) in).unread(tmpbuf, EXTHDR - EXTCRC - 1,
						EXTCRC);
			} else {
				e.setCrc(get32(tmpbuf, EXTCRC));
				e.setCompressedSize(get32(tmpbuf, EXTSIZ));
				e.setSize(get32(tmpbuf, EXTLEN));
			}
		}
		if (e.getSize() != inf.getBytesWritten()) {
			throw new ZipException("invalid entry size (expected "
					+ e.getSize() + " but got " + inf.getBytesWritten()
					+ " bytes)");
		}
		if (e.getCompressedSize() != inf.getBytesRead()) {
			throw new ZipException("invalid entry compressed size (expected "
					+ e.getCompressedSize() + " but got " + inf.getBytesRead()
					+ " bytes)");
		}
		if (e.getCrc() != crc.getValue()) {
			throw new ZipException("invalid entry CRC (expected 0x"
					+ Long.toHexString(e.getCrc()) + " but got 0x"
					+ Long.toHexString(crc.getValue()) + ")");
		}
	}

	/*
	 * Reads bytes, blocking until all bytes are read.
	 */
	private void readFully(byte[] b, int off, int len) throws IOException {
		while (len > 0) {
			int n = in.read(b, off, len);
			if (n == -1) {
				throw new EOFException();
			}
			off += n;
			len -= n;
		}
	}

	/*
	 * Fetches unsigned 16-bit value from byte array at specified offset. The
	 * bytes are assumed to be in Intel (little-endian) byte order.
	 */
	private static final int get16(byte b[], int off) {
		return (b[off] & 0xff) | ((b[off + 1] & 0xff) << 8);
	}

	/*
	 * Fetches unsigned 32-bit value from byte array at specified offset. The
	 * bytes are assumed to be in Intel (little-endian) byte order.
	 */
	private static final long get32(byte b[], int off) {
		return get16(b, off) | ((long) get16(b, off + 2) << 16);
	}

}
