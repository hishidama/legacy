package jp.hishidama.zip;

import java.io.*;
import java.util.*;
import java.util.zip.CRC32;
import java.util.zip.Deflater;
import java.util.zip.ZipException;

/**
 * Zipファイルを作成するクラス.
 * <p>
 * <a target="www.info-zip.org"
 * href="http://www.info-zip.org/">Info-ZIP</a>のzipcloakと<a
 * target="www.jajakarta.org" href="http://www.jajakarta.org/ant/">Apache
 * Ant</a>のZipOutputStreamを参考に作ったクラスです。
 * </p>
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/zip.html"
 *         >ひしだま</a>
 * @since 2007.12.21
 * @version 2009.12.20
 */
public class ZipOutputStream extends FilterOutputStream {
	public static final int STORED = ZipEntry.STORED;

	public static final int DEFLATED = ZipEntry.DEFLATED;

	private ZipEntry entry;

	private String comment = "";

	private int level = -1;

	private boolean hasCompressionLevelChanged = false;

	private int method = DEFLATED;

	private List<ZipEntry> entries = createZipEntryList();

	protected List<ZipEntry> createZipEntryList() {
		return new LinkedList<ZipEntry>();
	}

	private CRC32 crc = new CRC32();

	private long written = 0;

	private long dataStart = 0;

	private long cdOffset = 0;

	private long cdLength = 0;

	private String encoding = null;

	protected Deflater def = new Deflater(Deflater.DEFAULT_COMPRESSION, true);

	private Map<String, Offset> offsets = new HashMap<String, Offset>();

	private byte buf[] = new byte[512];

	protected byte[] password;

	protected InfoZIP_Crypt crypt;

	/**
	 * コンストラクター.
	 *
	 * @param out
	 *            出力ストリーム
	 */
	public ZipOutputStream(OutputStream out) {
		this(out, null);
	}

	/**
	 * コンストラクター.
	 *
	 * @param out
	 *            出力ストリーム
	 */
	public ZipOutputStream(OutputStream out, String encoding) {
		super(out);
		this.encoding = encoding;
	}

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

	/**
	 * パスワード設定.
	 *
	 * @param password
	 *            パスワード
	 */
	public void setPassword(byte[] password) {
		this.password = password;
		if (password != null && crypt == null) {
			crypt = new InfoZIP_Crypt();
		}
	}

	/**
	 * 出力終了.
	 *
	 * @throws IOException
	 */
	public void finish() throws IOException {
		closeEntry();
		cdOffset = written;
		for (Iterator<ZipEntry> i = entries.iterator(); i.hasNext();) {
			writeCentralFileHeader(i.next());
		}

		cdLength = written - cdOffset;
		writeCentralDirectoryEnd();
		entries.clear();
	}

	/**
	 * エントリークローズ.
	 *
	 * @throws IOException
	 */
	public void closeEntry() throws IOException {
		if (entry == null)
			return;
		long realCrc = crc.getValue();
		crc.reset();
		if (entry.getMethod() == DEFLATED) {
			def.finish();
			for (; !def.finished(); deflate())
				;
			entry.setSize(def.getBytesRead());

			long csize = def.getBytesWritten();
			if (password == null) {
				entry.setCompressedSize(csize);
			} else {
				entry.setCompressedSize(csize + InfoZIP_Crypt.RAND_HEAD_LEN);
			}
			entry.setCrc(realCrc);
			def.reset();

			written += csize;
		} else {
			if (entry.getCrc() != realCrc)
				throw new ZipException("bad CRC checksum for entry "
						+ entry.getName() + ": "
						+ Long.toHexString(entry.getCrc()) + " instead of "
						+ Long.toHexString(realCrc));
			if (entry.getCompressedSize() != written - dataStart)
				throw new ZipException("bad size for entry " + entry.getName()
						+ ": " + entry.getCompressedSize() + " instead of "
						+ (written - dataStart));
		}
		writeDataDescriptor(entry);
		entry = null;
	}

	/**
	 * エントリー設定.
	 *
	 * @param ze
	 *            エントリー
	 * @throws IOException
	 */
	public void putNextEntry(ZipEntry ze) throws IOException {
		closeEntry();
		entry = ze;
		entries.add(entry);
		if (entry.getMethod() == -1)
			entry.setMethod(method);
		if (entry.getTime() == -1L)
			entry.setTime(System.currentTimeMillis());
		if (entry.getMethod() == STORED) {
			long size = entry.getSize();
			if (size == -1L)
				throw new ZipException(
						"uncompressed size is required for STORED method when not writing to a file");
			if (entry.getCrc() == -1L)
				throw new ZipException(
						"crc checksum is required for STORED method when not writing to a file");
			if (password != null) {
				size += InfoZIP_Crypt.RAND_HEAD_LEN;
			}
			entry.setCompressedSize(size);
		}
		if (entry.getMethod() == DEFLATED && hasCompressionLevelChanged) {
			def.setLevel(level);
			hasCompressionLevelChanged = false;
		}

		if (entry.getMethod() == DEFLATED && password != null) {
			int tim = toDosTime(ze.getTime());
			entry.setCrc(((long) tim << 16) & 0xFFFFFFFFL);
		}

		writeLocalFileHeader(entry);

		if (password != null) {
			crypt.crypthead(password, (int) ze.getCrc(), out);
			written += InfoZIP_Crypt.RAND_HEAD_LEN;
			if (entry.getMethod() == DEFLATED) {
				ze.setSize(ze.getSize() + InfoZIP_Crypt.RAND_HEAD_LEN);
			}
		}
	}

	/**
	 * コメント設定.
	 *
	 * @param comment
	 *            コメント
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * 圧縮レベル設定.
	 *
	 * @param level
	 *            圧縮レベル
	 */
	public void setLevel(int level) {
		hasCompressionLevelChanged = this.level != level;
		this.level = level;
	}

	/**
	 * 圧縮メソッド設定.
	 *
	 * @param method
	 *            圧縮メソッド
	 * @see #DEFLATED
	 * @see #STORED
	 */
	public void setMethod(int method) {
		this.method = method;
	}

	@Override
	public void write(byte b[], int offset, int length) throws IOException {
		if (entry.getMethod() == DEFLATED) {
			if (length > 0 && !def.finished()) {
				def.setInput(b, offset, length);
				for (; !def.needsInput(); deflate())
					;
			}
		} else {
			writeCrypt(b, offset, length);
			written += length;
		}
		crc.update(b, offset, length);
	}

	@Override
	public void write(int b) throws IOException {
		byte buf[] = new byte[1];
		buf[0] = (byte) (b & 255);
		write(buf, 0, 1);
	}

	@Override
	public void close() throws IOException {
		finish();
		if (out != null)
			out.close();
	}

	@Override
	public void flush() throws IOException {
		if (out != null)
			out.flush();
	}

	protected final void deflate() throws IOException {
		int len = def.deflate(buf, 0, buf.length);
		if (len > 0)
			writeCrypt(buf, 0, len);
	}

	/**
	 * putlocal
	 *
	 * @param ze
	 * @throws IOException
	 */
	protected void writeLocalFileHeader(ZipEntry ze) throws IOException {
		offsets.put(ze.getName(), new Offset(written));

		// LOCSIG
		write4(InfoZIP_ZipFile.LOCSIG);
		written += 4;

		int flag;
		if (ze.getMethod() == DEFLATED) {
			write2(20); // ver
			flag = 8;
		} else {
			write2(10); // ver
			flag = 0;
		}
		written += 2;

		if (password != null) {
			flag |= 1;
		}
		write2(flag); // flag
		written += 2;

		// how
		write2(ze.getMethod());
		written += 2;

		// time
		write4(toDosTime(ze.getTime()));
		written += 4L;

		// CRC,SIZE,LEN
		if (ze.getMethod() == DEFLATED) {
			write4(0);
			write4(0);
			write4(0);
		} else {
			write4(ze.getCrc());
			write4(ze.getCompressedSize());
			write4(ze.getSize());
		}
		written += 12;

		// name_len
		byte name[] = getBytes(ze.getName());
		if (name.length > 0xFFFF) {
			throw new IllegalArgumentException("entry name too long");
		}
		write2(name.length);
		written += 2;

		// ext_len
		byte extra[] = ze.getLocalFileDataExtra();
		write2(extra.length);
		written += 2;

		writeOut(name);
		written += name.length;
		writeOut(extra);
		written += extra.length;
		dataStart = written;
	}

	protected void writeDataDescriptor(ZipEntry ze) throws IOException {
		if (ze.getMethod() != DEFLATED) {
			return;
		} else {
			write4(InfoZIP_ZipFile.EXTLOCSIG);
			write4(entry.getCrc());
			write4(entry.getCompressedSize());
			write4(entry.getSize());
			written += 16;
			return;
		}
	}

	protected void writeCentralFileHeader(ZipEntry ze) throws IOException {
		write4(InfoZIP_ZipFile.CENSIG);
		written += 4;
		write2(ze.getPlatform() << 8 | 20);
		written += 2;

		int flag;
		if (ze.getMethod() == DEFLATED) {
			write2(20); // ver
			flag = 8;
		} else {
			write2(10); // ver
			flag = 0;
		}
		written += 2;

		// flag
		if (password != null) {
			flag |= 1;
		}
		write2(flag);
		written += 2;

		// how
		write2(ze.getMethod());
		written += 2;
		write4(toDosTime(ze.getTime()));
		written += 4;
		write4(ze.getCrc());
		write4(ze.getCompressedSize());
		write4(ze.getSize());
		written += 12;
		byte name[] = getBytes(ze.getName());
		if (name.length > 0xFFFF) {
			throw new IllegalArgumentException("entry name too long");
		}
		write2(name.length);
		written += 2;
		byte extra[] = ze.getCentralDirectoryExtra();
		write2(extra.length);
		written += 2;
		String comm = ze.getComment();
		if (comm == null) {
			comm = "";
		}
		byte comment[] = getBytes(comm);
		if (comment.length > 0xffff) {
			throw new IllegalArgumentException("invalid entry comment length");
		}
		write2(comment.length);
		written += 2;
		write2(0);
		written += 2;
		write2(ze.getInternalAttributes());
		written += 2;
		write4(ze.getExternalAttributes());
		written += 4;
		Offset o = offsets.get(ze.getName());
		write4(o.offset);
		written += 4;
		writeOut(name);
		written += name.length;
		writeOut(extra);
		written += extra.length;
		writeOut(comment);
		written += comment.length;
	}

	protected void writeCentralDirectoryEnd() throws IOException {
		write4(InfoZIP_ZipFile.ENDSIG);
		write2(0);
		write2(0);
		write2(entries.size());
		write2(entries.size());
		write4(cdLength);
		write4(cdOffset);

		String comm = comment;
		if (comm == null) {
			comm = "";
		}
		byte data[] = getBytes(comm);
		if (data.length > 0xffff) {
			throw new IllegalArgumentException("invalid comment length");
		}
		write2(data.length);
		writeOut(data);
	}

	protected static int toDosTime(long d) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(d);
		int year = cal.get(Calendar.YEAR);
		if (year < 1980) {
			return (1 << 21) | (1 << 16);
		}
		return (year - 1980) << 25 | (cal.get(Calendar.MONTH) + 1) << 21
				| cal.get(Calendar.DATE) << 16
				| cal.get(Calendar.HOUR_OF_DAY) << 11
				| cal.get(Calendar.MINUTE) << 5 | cal.get(Calendar.SECOND) >> 1;
	}

	protected byte[] getBytes(String name) throws ZipException {
		try {
			if (encoding == null) {
				return name.getBytes();
			} else {
				return name.getBytes(encoding);
			}
		} catch (UnsupportedEncodingException uee) {
			throw new ZipException(uee.getMessage());
		}
	}

	protected void write2(int v) throws IOException {
		out.write((v >>> 0) & 0xff);
		out.write((v >>> 8) & 0xff);
	}

	protected void write4(int v) throws IOException {
		out.write((int) ((v >>> 0) & 0xff));
		out.write((int) ((v >>> 8) & 0xff));
		out.write((int) ((v >>> 16) & 0xff));
		out.write((int) ((v >>> 24) & 0xff));
	}

	protected void write4(long v) throws IOException {
		out.write((int) ((v >>> 0) & 0xff));
		out.write((int) ((v >>> 8) & 0xff));
		out.write((int) ((v >>> 16) & 0xff));
		out.write((int) ((v >>> 24) & 0xff));
	}

	protected void writeOut(byte data[]) throws IOException {
		out.write(data, 0, data.length);
	}

	protected void writeOut(byte data[], int offset, int length)
			throws IOException {
		out.write(data, offset, length);
	}

	protected void writeCrypt(byte data[], int offset, int length)
			throws IOException {
		if (password != null) {
			for (int i = 0, j = offset; i < length; i++, j++) {
				out.write(crypt.zencode(data[j]));
			}
		} else {
			out.write(data, offset, length);
		}
	}

}
