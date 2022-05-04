package jp.hishidama.zip;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * <a target="www.info-zip.org" href="http://www.info-zip.org/">Info-ZIP</a>��zipcloak�iver2.32�j��Java�ɈڐA�������̂ł��B
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/zip.html">�Ђ�����</a>
 * @since 2007.12.14
 * @version 2008.01.19
 */
public class ZipCloak extends InfoZIP_Crypt {

	/**
	 * �R���X�g���N�^�[.
	 * 
	 * @param src
	 *            zip�t�@�C��
	 */
	public ZipCloak(File src) {
		zipfile = src;
	}

	/**
	 * ZIP������.
	 * <p>
	 * ����������zip�t�@�C�����쐬���܂��B<br>
	 * ���̓t�@�C�����Í�������Ă��Ȃ��ꍇ�A�R�s�[����܂��B
	 * </p>
	 * <p>
	 * �p�X���[�h���Ԉ���Ă����ꍇ�A{@link java.util.zip.ZipException}���������܂��B
	 * </p>
	 * <p>
	 * �g�p��F<br>
	 * <code>ZipCloak zip = new ZipCloak(new File("sample.zip"));<br>
	 * zip.decrypt(new File("decode.zip"), "password".getBytes("MS932"));
	 * </code>
	 * </p>
	 * 
	 * @param dst
	 *            ������t�@�C��
	 * @param passwd
	 *            �p�X���[�h
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
	 * ZIP�Í���.
	 * <p>
	 * �Í�������zip�t�@�C�����쐬���܂��B<br>
	 * ���̓t�@�C�����Í�������Ă����ꍇ�A���̃p�X���[�h�̂܂܃R�s�[����܂��i�V�����p�X���[�h�ɂ͂Ȃ�܂���j�B
	 * </p>
	 * <p>
	 * �g�p��F<br>
	 * <code>ZipCloak zip = new ZipCloak(new File("sample.zip"));<br>
	 * zip.encrypt(new File("encode.zip"), "password".getBytes("MS932"));
	 * </code>
	 * </p>
	 * 
	 * @param dst
	 *            �Í���t�@�C��
	 * @param passwd
	 *            �p�X���[�h
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
