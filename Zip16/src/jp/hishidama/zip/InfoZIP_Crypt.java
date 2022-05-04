package jp.hishidama.zip;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.Random;
import static jp.hishidama.zip.InfoZIP_CRCtab.*;
import static jp.hishidama.zip.InfoZIP_Zip.*;

/**
 * crypt.cÇÃà⁄êA
 *
 * @since 2007.12.14
 */
class InfoZIP_Crypt extends InfoZIP_ZipFile {

	/*
	 * length of encryption random header
	 */
	static final int RAND_HEAD_LEN = 12;

	/*
	 * use PI as default pattern
	 */
	private static final long ZCR_SEED2 = 3141592654L;

	/*
	 * ensure different random header each time
	 */
	private Random calls = new Random(System.currentTimeMillis() ^ ZCR_SEED2);

	/*
	 * keys defining the pseudo-random sequence
	 */
	private int[] keys = new int[3];

	private static final int CRC32(int c, int b) {
		return crc_table[(c ^ b) & 0xff] ^ (c >>> 8);
	}

	/* encode byte c, using temp t. Warning: c must not have side effects. */
	final int zencode(int c) {
		int t = decrypt_byte();
		update_keys(c);
		return t ^ c;
	}

	/* decode byte c in place */
	final int zdecode(int c) {
		update_keys(c ^= decrypt_byte());
		return c;
	}

	private final int decrypt_byte() {
		int temp = (keys[2] & 0xffff) | 2;
		return ((temp * (temp ^ 1)) >>> 8) & 0xff;
	}

	private final int update_keys(int c) {
		keys[0] = CRC32(keys[0], c);
		keys[1] += keys[0] & 0xff;
		keys[1] = keys[1] * 134775813 + 1;
		{
			int keyshift = keys[1] >>> 24;
			keys[2] = CRC32(keys[2], keyshift);
		}
		return c;
	}

	private void init_keys(byte[] passwd) {
		keys[0] = 305419896;
		keys[1] = 591751049;
		keys[2] = 878082192;
		for (int i = 0; i < passwd.length; i++) {
			update_keys(passwd[i]);
		}
	}

	private final byte[] header = new byte[RAND_HEAD_LEN - 2]; /* random header */

	/**
	 * Write encryption header to file zfile using the password passwd and the
	 * cyclic redundancy check crc.
	 *
	 * @param passwd
	 *            password string
	 * @param crc
	 *            crc of file being encrypted
	 * @param zfile
	 *            where to write header
	 * @throws IOException
	 */
	private void crypthead(byte[] passwd, int crc, RandomAccessFile zfile)
			throws IOException {
		int n; /* index in random header */
		int c; /* random byte */
		int ztemp; /* temporary for zencoded value */

		/*
		 * First generate RAND_HEAD_LEN-2 random bytes. We encrypt the output of
		 * rand() to get less predictability, since rand() is often poorly
		 * implemented.
		 */
		init_keys(passwd);
		for (n = 0; n < RAND_HEAD_LEN - 2; n++) {
			c = (calls.nextInt() >> 7) & 0xff;
			header[n] = (byte) zencode(c);
		}
		/* Encrypt random header (last two bytes is high word of crc) */
		init_keys(passwd);
		for (n = 0; n < RAND_HEAD_LEN - 2; n++) {
			ztemp = zencode(header[n]);
			zfile.write(ztemp);
		}
		ztemp = zencode((crc >>> 16) & 0xff);
		zfile.write(ztemp);
		ztemp = zencode((crc >>> 24) & 0xff);
		zfile.write(ztemp);
	}

	protected void crypthead(byte[] passwd, int crc, OutputStream zfile)
			throws IOException {
		int n; /* index in random header */
		int c; /* random byte */
		int ztemp; /* temporary for zencoded value */

		/*
		 * First generate RAND_HEAD_LEN-2 random bytes. We encrypt the output of
		 * rand() to get less predictability, since rand() is often poorly
		 * implemented.
		 */
		init_keys(passwd);
		for (n = 0; n < RAND_HEAD_LEN - 2; n++) {
			c = (calls.nextInt() >> 7) & 0xff;
			header[n] = (byte) zencode(c);
		}
		/* Encrypt random header (last two bytes is high word of crc) */
		init_keys(passwd);
		for (n = 0; n < RAND_HEAD_LEN - 2; n++) {
			ztemp = zencode(header[n]);
			zfile.write(ztemp);
		}
		ztemp = zencode((crc >>> 16) & 0xff);
		zfile.write(ztemp);
		ztemp = zencode((crc >>> 24) & 0xff);
		zfile.write(ztemp);
	}

	/**
	 * à√çÜâª.
	 * <p>
	 * Encrypt the zip entry described by z from file source to file dest using
	 * the password passwd. Return an error code in the ZE_ class.
	 * </p>
	 *
	 * @param z
	 *            zip entry to encrypt
	 * @param source
	 *            source file
	 * @param dest
	 *            destination file
	 * @param passwd
	 *            password string
	 * @throws IOException
	 */
	protected void zipcloak(Zlist z, RandomAccessFile source,
			RandomAccessFile dest, byte[] passwd) throws IOException {
		int c; /* input byte */
		long n; /* holds offset and counts size */
		int flag; /* previous flags */
		int ztemp; /* temporary storage for zencode value */

		/*
		 * Set encrypted bit, clear extended local header bit and write local
		 * header to output file
		 */
		n = dest.getFilePointer();
		z.off = n;
		flag = z.flg;
		z.flg |= 1;
		z.flg &= ~8;
		z.lflg |= 1;
		z.lflg &= ~8;
		z.siz += RAND_HEAD_LEN;
		putlocal(z, dest);

		/* Initialize keys with password and write random header */
		crypthead(passwd, z.crc, dest);

		/* Skip local header in input file */
		source.seek(source.getFilePointer() + (4 + LOCHEAD) + z.nam + z.ext);

		/* Encrypt data */
		for (n = z.siz - RAND_HEAD_LEN; n != 0; n--) {
			if ((c = source.read()) == -1) {
				throw new IOException();
			}
			ztemp = zencode(c);
			dest.write(ztemp);
		}
		/* Skip extended local header in input file if there is one */
		if ((flag & 8) != 0) {
			source.seek(source.getFilePointer() + 16L);
		}
		// if (fflush(dest) == EOF)
		// return ZE_TEMP;

		/* Update number of bytes written to output file */
		tempzn += (4 + LOCHEAD) + z.nam + z.ext + z.siz;
	}

	/**
	 * ïúçÜâª.
	 * <p>
	 * Decrypt the zip entry described by z from file source to file dest using
	 * the password passwd. Return an error code in the ZE_ class.
	 * </p>
	 *
	 * @param z
	 *            zip entry to encrypt
	 * @param source
	 *            source file
	 * @param dest
	 *            destination file
	 * @param passwd
	 *            password string
	 * @throws IOException
	 */
	protected void zipbare(Zlist z, RandomAccessFile source,
			RandomAccessFile dest, byte[] passwd) throws IOException {
		int c1; /* last input byte */
		long offset; /* used for file offsets */
		long size; /* size of input data */
		int flag; /* previous flags */

		/* Save position and skip local header in input file */
		offset = source.getFilePointer();
		source.seek(source.getFilePointer() + (4 + LOCHEAD) + z.nam + z.ext);

		int check = ((z.flg & 8) != 0 ? (z.tim & 0xffff) >>> 8
				: ((z.crc) >>> 24));
		barehead(source, check, passwd);

		/*
		 * Clear encrypted bit and local header bit, and write local header to
		 * output file
		 */
		offset = dest.getFilePointer();
		z.off = offset;
		flag = z.flg;
		z.flg &= ~9;
		z.lflg &= ~9;
		z.siz -= RAND_HEAD_LEN;
		putlocal(z, dest);

		/* Decrypt data */
		for (size = z.siz; size != 0; size--) {
			if ((c1 = source.read()) == -1) {
				throw new IOException();
			}
			c1 = zdecode(c1);
			dest.write(c1);
		}
		/* Skip extended local header in input file if there is one */
		if ((flag & 8) != 0) {
			source.seek(source.getFilePointer() + 16L);
		}

		/* Update number of bytes written to output file */
		tempzn += (4 + LOCHEAD) + z.nam + z.ext + z.siz;
	}

	protected void barehead(RandomAccessFile source, int check, byte[] passwd)
			throws IOException {
		/* Initialize keys with password */
		init_keys(passwd);

		/* Decrypt encryption header, save last two bytes */
		int c1 = 0;
		for (int r = RAND_HEAD_LEN; r != 0; r--) {
			if ((c1 = source.read()) == -1) {
				throw new IOException();
			}
			c1 = zdecode(c1);
		}

		/*
		 * If last two bytes of header don't match crc (or file time in the case
		 * of an extended local header), back up and just copy. For pkzip 2.0,
		 * the check has been reduced to one byte only.
		 */
		if ((c1 & 0xffff) != check) {
			throw new ZipPasswordException("password miss");
		}
	}

	protected void barehead(InputStream source, int check, byte[] passwd)
			throws IOException {
		/* Initialize keys with password */
		init_keys(passwd);

		/* Decrypt encryption header, save last two bytes */
		int c1 = 0;
		for (int r = RAND_HEAD_LEN; r != 0; r--) {
			if ((c1 = source.read()) == -1) {
				throw new IOException();
			}
			c1 = zdecode(c1);
		}

		/*
		 * If last two bytes of header don't match crc (or file time in the case
		 * of an extended local header), back up and just copy. For pkzip 2.0,
		 * the check has been reduced to one byte only.
		 */
		if ((c1 & 0xffff) != check) {
			System.out.println(c1 & 0xffff);
			throw new ZipPasswordException("password miss");
		}
	}
}
