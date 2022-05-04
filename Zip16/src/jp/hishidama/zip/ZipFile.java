package jp.hishidama.zip;

import java.io.*;
import java.util.*;
import java.util.zip.*;
import static jp.hishidama.zip.InfoZIP_Zip.*;

/**
 * Zipファイルを読み込むクラス.
 * <p>
 * <a target="www.info-zip.org"
 * href="http://www.info-zip.org/">Info-ZIP</a>のzipcloakと<a
 * target="www.jajakarta.org" href="http://www.jajakarta.org/ant/">Apache
 * Ant</a>のZipFileを参考に作ったクラスです。
 * </p>
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/zip.html"
 *         >ひしだま</a>
 * @since 2007.12.21
 * @version 2009.12.20
 */
public class ZipFile extends InfoZIP_Crypt implements Closeable {

	protected byte[] password;

	protected RandomAccessFile archive;

	private List<ZipEntry> entries = createZipEntryList();

	protected List<ZipEntry> createZipEntryList() {
		return new LinkedList<ZipEntry>();
	}

	private static class EntryMap {

		private static class Entry {
			ZipEntry ze;
			Offset of;
		}

		private Map<String, Entry> map = new HashMap<String, Entry>();

		void put(String name, ZipEntry ze, Offset of) {
			Entry e = new Entry();
			e.ze = ze;
			e.of = of;
			map.put(name, e);
		}

		ZipEntry getZipEntry(String name) {
			return map.get(name).ze;
		}

		Offset getOffset(String name) {
			return map.get(name).of;
		}
	}

	private EntryMap nameMap = new EntryMap();

	private boolean checkCrc = false;

	/**
	 * コンストラクター.
	 *
	 * @param f
	 *            zipファイル名
	 * @throws IOException
	 */
	public ZipFile(File f) throws IOException {
		this(f, null);
	}

	/**
	 * コンストラクター.
	 *
	 * @param f
	 *            zipファイル名
	 * @param encoding
	 *            エンコーディング
	 * @throws IOException
	 */
	public ZipFile(File f, String encoding) throws IOException {
		this.encoding = encoding;
		archive = new RandomAccessFile(f, "r");
		try {
			populateFromCentralDirectory();
			resolveLocalFileHeaderData();
		} catch (IOException e) {
			close(archive);
			throw e;
		}
	}

	/**
	 * コンストラクター.
	 *
	 * @param name
	 *            zipファイル名
	 * @throws IOException
	 */
	public ZipFile(String name) throws IOException {
		this(new File(name), null);
	}

	/**
	 * コンストラクター.
	 *
	 * @param name
	 *            zipファイル名
	 * @param encoding
	 *            エンコーディング
	 * @throws IOException
	 */
	public ZipFile(String name, String encoding) throws IOException {
		this(new File(name), encoding);
	}

	/** @deprecated ZipFileでは当メソッドは無効です */
	@Deprecated
	@Override
	public void setEncoding(String encoding) {
		throw new UnsupportedOperationException();
	}

	/**
	 * パスワード設定.
	 *
	 * @param password
	 *            パスワード
	 */
	public void setPassword(byte[] password) {
		this.password = password;
	}

	/**
	 * CRCチェック有無設定.
	 * <p>
	 * データのCRCチェックを行うかどうかを設定します。<br>
	 * CRCチェックを行う場合、{@link #getInputStream(ZipEntry)}
	 * で取得したInputStreamのread()において、CRC不一致の場合に{@link ZipCrcException}が発生します。
	 * </p>
	 *
	 * @param b
	 *            true：CRCチェックを行う
	 * @since 2008.12.21
	 */
	public void setCheckCrc(boolean b) {
		checkCrc = b;
	}

	/**
	 * CRCチェック有無取得.
	 *
	 * @return true：CRCチェックを行う
	 * @see #setCheckCrc(boolean)
	 * @since 2008.12.21
	 */
	public boolean isCheckCrc() {
		return checkCrc;
	}

	/**
	 * zipファイルクローズ.
	 *
	 * @throws IOException
	 */
	public void close() throws IOException {
		close(archive);
	}

	/**
	 * エントリー列挙.
	 * <p>
	 * 当メソッドは{@link java.util.zip.ZipFile java.util.zip.ZipFile}
	 * との互換性の為に用意されたものです。
	 * </p>
	 *
	 * @return エントリーの列挙
	 * @see #getEntriesIterator()
	 */
	public Enumeration<? extends ZipEntry> entries() {
		return getEntries();
	}

	/**
	 * エントリー列挙.
	 * <p>
	 * 当メソッドはorg.apache.tools.zip.ZipFileとの互換性の為に用意されたものです。
	 * </p>
	 *
	 * @return エントリーの列挙
	 * @see #getEntriesIterator()
	 */
	public Enumeration<ZipEntry> getEntries() {
		return new Enumeration<ZipEntry>() {
			private Iterator<ZipEntry> i = entries.iterator();

			public boolean hasMoreElements() {
				return i.hasNext();
			}

			public ZipEntry nextElement() {
				return i.next();
			}
		};
	}

	/**
	 * エントリー一覧取得.
	 *
	 * @return {@link ZipEntry}のイテレーター
	 */
	public Iterator<ZipEntry> getEntriesIterator() {
		return entries.iterator();
	}

	/**
	 * エントリー取得.
	 *
	 * @param name
	 *            エントリー名
	 * @return {@link ZipEntry}（存在しない場合、null）
	 */
	public ZipEntry getEntry(String name) {
		return nameMap.getZipEntry(name);
	}

	private void populateFromCentralDirectory() throws IOException {
		positionAtCentralDirectory();
		byte cfh[] = new byte[42];
		byte signatureBytes[] = new byte[4];
		archive.readFully(signatureBytes);
		for (int sig = (int) get4(signatureBytes); sig == InfoZIP_ZipFile.CENSIG; sig = (int) get4(signatureBytes)) {
			archive.readFully(cfh);
			int off = 0;
			ZipEntry ze = createZipEntry("_dummy_for_ZipFile_");

			// ver
			int versionMadeBy = get2(cfh, off);
			off += 2;
			ze.setPlatform((versionMadeBy >> 8) & 15);

			off += 2;

			// flag
			ze.setFlag(get2(cfh, off));
			off += 2;

			// how
			ze.setMethod(get2(cfh, off));
			off += 2;

			// tim
			ze.setTime(fromDosTime(get4(cfh, off)));
			off += 4;

			// crc
			ze.setCrc(get4(cfh, off));
			off += 4;

			// csize
			ze.setCompressedSize(get4(cfh, off));
			off += 4;

			// size
			ze.setSize(get4(cfh, off));
			off += 4;

			// name_len
			int fileNameLen = get2(cfh, off);
			off += 2;

			// ext_len
			int extraLen = get2(cfh, off);
			off += 2;

			// com_len
			int commentLen = get2(cfh, off);
			off += 2;
			off += 2;
			ze.setInternalAttributes(get2(cfh, off));
			off += 2;
			ze.setExternalAttributes(get4(cfh, off));
			off += 4;
			long offset = get4(cfh, off);

			entries.add(ze);

			byte fileName[] = new byte[fileNameLen];
			archive.readFully(fileName);
			ze.setName(getString(fileName));
			nameMap.put(ze.getName(), ze, new Offset(offset));

			archive.skipBytes(extraLen);

			byte comment[] = new byte[commentLen];
			archive.readFully(comment);
			ze.setComment(getString(comment));

			archive.readFully(signatureBytes);
		}
	}

	protected ZipEntry createZipEntry(String name) {
		return new ZipEntry(name);
	}

	private void resolveLocalFileHeaderData() throws IOException {
		byte b[] = new byte[2];
		for (Iterator<ZipEntry> i = entries.iterator(); i.hasNext();) {
			ZipEntry ze = i.next();
			Offset o = nameMap.getOffset(ze.getName());
			archive.seek(o.offset + LOCHEAD);
			archive.readFully(b);
			int fileNameLen = get2(b);
			archive.readFully(b);
			int extraFieldLen = get2(b);
			archive.skipBytes(fileNameLen);
			byte localExtraData[] = new byte[extraFieldLen];
			archive.readFully(localExtraData);
			ze.setExtra(localExtraData);

			o.dataOffset = o.offset + LOCHEAD + 2L + 2L + (long) fileNameLen
					+ (long) extraFieldLen;
		}
	}

	private static final int[] EOCD_SIG = { InfoZIP_ZipFile.ENDSIG & 0xff,
			(InfoZIP_ZipFile.ENDSIG >>> 8) & 0xff,
			(InfoZIP_ZipFile.ENDSIG >>> 16) & 0xff,
			(InfoZIP_ZipFile.ENDSIG >>> 24) & 0xff };

	private void positionAtCentralDirectory() throws IOException {

		long off = archive.length() - ENDHEAD - 4;
		archive.seek(off);
		int curr = archive.read();
		for (; curr != -1; curr = archive.read()) {
			if (curr == EOCD_SIG[0]) {
				curr = archive.read();
				if (curr == EOCD_SIG[1]) {
					curr = archive.read();
					if (curr == EOCD_SIG[2]) {
						curr = archive.read();
						if (curr == EOCD_SIG[3]) {
							archive.seek(off + 16L);
							byte cfdOffset[] = new byte[4];
							archive.readFully(cfdOffset);
							archive.seek(get4(cfdOffset));
							return;
						}
					}
				}
			}
			archive.seek(--off);
		}

		throw new ZipException("archive is not a ZIP archive");
	}

	/**
	 * 入力ストリーム取得.
	 *
	 * @param ze
	 *            エントリー
	 * @return 入力ストリーム
	 * @throws IOException
	 * @throws ZipException
	 * @throws ZipPasswordException
	 * @see #setCheckCrc(boolean)
	 */
	public InputStream getInputStream(ZipEntry ze) throws IOException,
			ZipException {
		Offset o = nameMap.getOffset(ze.getName());
		long start = o.dataOffset;
		if (start == 0)
			return null;
		BoundedInputStream bis;
		if (!ze.isEncrypted()) {
			bis = new BoundedInputStream(ze, 0);
		} else {
			bis = new PasswordInputStream(ze);
		}
		switch (ze.getMethod()) {
		case ZipEntry.STORED:
			if (isCheckCrc()) {
				return new CrcInputStream(ze, bis);
			}
			return bis;

		case ZipEntry.DEFLATED:
			bis.addDummy();
			InflaterInputStream iis = new InflaterInputStream(bis,
					new Inflater(true));
			if (isCheckCrc()) {
				return new CrcInputStream(ze, iis);
			}
			return iis;
		}
		throw new ZipException("Found unsupported compression method "
				+ ze.getMethod());
	}

	protected static final int get2(byte[] bytes) {
		return get2(bytes, 0);
	}

	protected static final int get2(byte[] bytes, int off) {
		return (bytes[off] & 0xff) | ((bytes[off + 1] & 0xff) << 8);
	}

	protected static final long get4(byte[] bytes) {
		return get4(bytes, 0);
	}

	protected static final long get4(byte[] bytes, int off) {
		return ((bytes[off] & 0xff) | ((bytes[off + 1] & 0xff) << 8)
				| ((bytes[off + 2] & 0xff) << 16) | ((bytes[off + 3] & 0xff) << 24)) & 0xffffffffl;
	}

	protected static long fromDosTime(long dosTime) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, (int) (dosTime >> 25 & 127L) + 1980);
		cal.set(Calendar.MONTH, (int) (dosTime >> 21 & 15L) - 1);
		cal.set(Calendar.DATE, (int) (dosTime >> 16) & 31);
		cal.set(Calendar.HOUR_OF_DAY, (int) (dosTime >> 11) & 31);
		cal.set(Calendar.MINUTE, (int) (dosTime >> 5) & 63);
		cal.set(Calendar.SECOND, (int) (dosTime << 1) & 62);
		return cal.getTimeInMillis();
	}

	/* org.apache.tools.zip.ZipFile.BoundedInputStream */
	class BoundedInputStream extends InputStream {

		private long remaining;

		private long loc;

		private boolean addDummyByte;

		BoundedInputStream(ZipEntry ze, long delta) {
			Offset o = nameMap.getOffset(ze.getName());
			this.loc = o.dataOffset + delta;
			this.remaining = ze.getCompressedSize() - delta;
		}

		@Override
		public int read() throws IOException {
			if (remaining-- <= 0L)
				if (addDummyByte) {
					addDummyByte = false;
					return 0;
				} else {
					return -1;
				}
			synchronized (archive) {
				archive.seek(loc++);
				return archive.read();
			}
		}

		@Override
		public int read(byte b[], int off, int len) throws IOException {
			if (len <= 0)
				return 0;
			if (remaining <= 0L)
				if (addDummyByte) {
					addDummyByte = false;
					b[off] = 0;
					return 1;
				} else {
					return -1;
				}
			if ((long) len > remaining)
				len = (int) remaining;
			int ret = -1;
			synchronized (archive) {
				archive.seek(loc);
				ret = archive.read(b, off, len);
			}
			if (ret > 0) {
				loc += ret;
				remaining -= ret;
			}
			return ret;
		}

		void addDummy() {
			addDummyByte = true;
		}
	}

	class PasswordInputStream extends BoundedInputStream {

		PasswordInputStream(ZipEntry ze) throws IOException {
			super(ze, RAND_HEAD_LEN);
			int check;
			if ((ze.getFlag() & 8) != 0) {
				check = (ZipOutputStream.toDosTime(ze.getTime()) & 0xffff) >>> 8;
			} else {
				check = (int) (ze.getCrc() >>> 24);
			}
			Offset o = nameMap.getOffset(ze.getName());
			synchronized (archive) {
				archive.seek(o.dataOffset);
				barehead(archive, check, password);
			}
		}

		@Override
		public int read() throws IOException {
			int c = super.read();
			if (c == -1)
				return -1;
			return zdecode(c);
		}

		@Override
		public int read(byte b[], int off, int len) throws IOException {
			int l = super.read(b, off, len);
			for (int i = 0, j = off; i < l; i++, j++) {
				b[j] = (byte) zdecode(b[j]);
			}
			return l;
		}
	}

	class CrcInputStream extends CheckedInputStream {

		private ZipEntry ze;

		CrcInputStream(ZipEntry ze, InputStream is) {
			super(is, new CRC32());
			this.ze = ze;
		}

		@Override
		public int read() throws IOException {
			int c = super.read();
			if (c < 0) {
				checkCrc();
			}
			return c;
		}

		@Override
		public int read(byte[] b, int off, int len) throws IOException {
			int l = super.read(b, off, len);
			if (l < 0) {
				checkCrc();
			}
			return l;
		}

		void checkCrc() throws ZipCrcException {
			long expected = ze.getCrc();
			if (expected != -1) {
				long crc = getChecksum().getValue();
				if (crc != expected) {
					throw new ZipCrcException(crc, expected);
				}
			}
		}
	}
}
