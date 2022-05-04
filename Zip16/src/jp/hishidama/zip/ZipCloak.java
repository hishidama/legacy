package jp.hishidama.zip;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * <a target="www.info-zip.org" href="http://www.info-zip.org/">Info-ZIP</a>のzipcloak（ver2.32）をJavaに移植したものです。
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/zip.html">ひしだま</a>
 * @since 2007.12.14
 * @version 2008.01.19
 */
public class ZipCloak extends InfoZIP_Crypt {

	/**
	 * コンストラクター.
	 * 
	 * @param src
	 *            zipファイル
	 */
	public ZipCloak(File src) {
		zipfile = src;
	}

	/**
	 * ZIP復号化.
	 * <p>
	 * 復号化したzipファイルを作成します。<br>
	 * 入力ファイルが暗号化されていない場合、コピーされます。
	 * </p>
	 * <p>
	 * パスワードが間違っていた場合、{@link java.util.zip.ZipException}が発生します。
	 * </p>
	 * <p>
	 * 使用例：<br>
	 * <code>ZipCloak zip = new ZipCloak(new File("sample.zip"));<br>
	 * zip.decrypt(new File("decode.zip"), "password".getBytes("MS932"));
	 * </code>
	 * </p>
	 * 
	 * @param dst
	 *            復号先ファイル
	 * @param passwd
	 *            パスワード
	 * @throws IOException
	 */
	public void decrypt(File dst, byte[] passwd) throws IOException {
		readzipfile(zipfile);
		RandomAccessFile inzip = null, outzip = null;
		try {
			inzip = new RandomAccessFile(zipfile, "r");
			outzip = new RandomAccessFile(dst, "rw");
			for (Zlist z = zfiles; z != null; z = z.nxt) {
				if ((z.flg & 1) != 0) {
					zipbare(z, inzip, outzip, passwd);
				} else {
					zipcopy(z, inzip, outzip);
				}
			}

			close(inzip);
			inzip = null;

			last(outzip);
		} finally {
			close(inzip);
			close(outzip);
		}
	}

	/**
	 * ZIP暗号化.
	 * <p>
	 * 暗号化したzipファイルを作成します。<br>
	 * 入力ファイルが暗号化されていた場合、元のパスワードのままコピーされます（新しいパスワードにはなりません）。
	 * </p>
	 * <p>
	 * 使用例：<br>
	 * <code>ZipCloak zip = new ZipCloak(new File("sample.zip"));<br>
	 * zip.encrypt(new File("encode.zip"), "password".getBytes("MS932"));
	 * </code>
	 * </p>
	 * 
	 * @param dst
	 *            暗号先ファイル
	 * @param passwd
	 *            パスワード
	 * @throws IOException
	 */
	public void encrypt(File dst, byte[] passwd) throws IOException {
		readzipfile(zipfile);
		RandomAccessFile inzip = null, outzip = null;
		try {
			inzip = new RandomAccessFile(zipfile, "r");
			outzip = new RandomAccessFile(dst, "rw");
			for (Zlist z = zfiles; z != null; z = z.nxt) {
				if ((z.flg & 1) == 0) {
					zipcloak(z, inzip, outzip, passwd);
				} else {
					zipcopy(z, inzip, outzip);
				}
			}

			close(inzip);
			inzip = null;

			last(outzip);
		} finally {
			close(inzip);
			close(outzip);
		}
	}

	private void last(RandomAccessFile outzip) throws IOException {
		/* Write central directory and end of central directory */

		/* get start of central */
		int start_offset = (int) outzip.getFilePointer();

		for (Zlist z = zfiles; z != null; z = z.nxt) {
			putcentral(z, outzip);
		}

		/* get end of central */
		int length = (int) outzip.getFilePointer();

		length -= start_offset; /* compute length of central */
		putend(zcount, length, start_offset, zcomlen, zcomment, outzip);
	}

	@Override
	public void setDosify(int dosify) {
		super.setDosify(dosify);
	}

	@Override
	public void setFix(int fix) {
		super.setFix(fix);
	}

	@Override
	public void setAdjust(int adjust) {
		super.setAdjust(adjust);
	}
}
