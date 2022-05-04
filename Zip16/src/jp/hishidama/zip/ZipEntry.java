package jp.hishidama.zip;

/**
 * Zipファイル内のエントリーを表すクラス.
 * <p>
 * <a target="www.info-zip.org"
 * href="http://www.info-zip.org/">Info-ZIP</a>のzipcloakと<a
 * target="www.jajakarta.org" href="http://www.jajakarta.org/ant/">Apache
 * Ant</a>のZipEntryを参考に作ったクラスです。
 * </p>
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/zip.html"
 *         >ひしだま</a>
 * @since 2007.12.21
 * @version 2009.12.20
 */
public class ZipEntry extends java.util.zip.ZipEntry {
	private static final int PLATFORM_UNIX = 3;

	private String name;

	private String comment;

	private int internalAttributes;

	private int platform;

	private int flag;

	private long externalAttributes;

	private byte[] centralDirectoryExtra;

	/**
	 * コンストラクター.
	 *
	 * @param name
	 *            エントリー名
	 */
	public ZipEntry(String name) {
		super(name);
	}

	/**
	 * コンストラクター.
	 *
	 * @param entry
	 *            java.util.zip.ZipEntry
	 */
	public ZipEntry(java.util.zip.ZipEntry entry) {
		super(entry);
		setComment(entry.getComment());
	}

	protected void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return (name == null) ? super.getName() : name;
	}

	/**
	 * 暗号化されているかどうか.
	 * <p>
	 * 当メソッドが返す値は、当インスタンスを{@link ZipFile}から取得した場合だけ有効。
	 * </p>
	 *
	 * @return パスワード付きの場合、true
	 */
	public boolean isEncrypted() {
		return (getFlag() & 1) != 0;
	}

	public int getInternalAttributes() {
		return internalAttributes;
	}

	public void setInternalAttributes(int value) {
		internalAttributes = value;
	}

	public long getExternalAttributes() {
		return externalAttributes;
	}

	public void setExternalAttributes(long value) {
		externalAttributes = value;
	}

	public void setUnixMode(int mode) {
		setExternalAttributes(mode << 16 | ((mode & 128) != 0 ? 0 : 1)
				| (isDirectory() ? 16 : 0));
		platform = PLATFORM_UNIX;
	}

	public int getUnixMode() {
		return (int) (getExternalAttributes() >> 16 & 65535L);
	}

	public int getPlatform() {
		return platform;
	}

	protected void setPlatform(int platform) {
		this.platform = platform;
	}

	void setFlag(int flag) {
		this.flag = flag;
	}

	int getFlag() {
		return flag;
	}

	@Override
	public void setComment(String comment) {
		// if (comment != null && getBytes(comment).length > 0xffff) {
		// throw new IllegalArgumentException("invalid entry comment length");
		// }
		this.comment = comment;
	}

	@Override
	public String getComment() {
		return comment;
	}

	public byte[] getLocalFileDataExtra() {
		byte extra[] = getExtra();
		return (extra == null) ? new byte[0] : extra;
	}

	public void setCentralDirectoryExtra(byte[] extra) {
		centralDirectoryExtra = extra;
	}

	public byte[] getCentralDirectoryExtra() {
		return (centralDirectoryExtra == null) ? new byte[0]
				: centralDirectoryExtra;
	}

	@Override
	public boolean isDirectory() {
		return getName().endsWith("/");
	}
}

class Offset {
	long offset, dataOffset;

	Offset(long offset) {
		this.offset = offset;
	}
}
