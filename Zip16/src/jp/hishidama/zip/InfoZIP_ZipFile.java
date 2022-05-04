package jp.hishidama.zip;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.zip.ZipException;
import static jp.hishidama.zip.InfoZIP_Zip.*;
import static jp.hishidama.zip.InfoZIP_Revision.*;

/**
 * zipfile.c�̈ڐA
 * 
 * @since 2007.12.14
 */
class InfoZIP_ZipFile extends InfoZIP_Native {
	/* Signatures for zip file information headers */
	protected static final int LOCSIG = 0x04034b50;

	protected static final int CENSIG = 0x02014b50;

	protected static final int ENDSIG = 0x06054b50;

	protected static final int EXTLOCSIG = 0x08074b50;

	/* Offsets of values in headers */
	/** version needed to extract */
	protected static final int LOCVER = 0;

	/** encrypt, deflate flags */
	protected static final int LOCFLG = 2;

	/** compression method */
	protected static final int LOCHOW = 4;

	/** last modified file time, DOS format */
	protected static final int LOCTIM = 6;

	/** last modified file date, DOS format */
	protected static final int LOCDAT = 8;

	/** uncompressed crc-32 for file */
	protected static final int LOCCRC = 10;

	/** compressed size in zip file */
	protected static final int LOCSIZ = 14;

	/** uncompressed size */
	protected static final int LOCLEN = 18;

	/** length of filename */
	protected static final int LOCNAM = 22;

	/** length of extra field */
	protected static final int LOCEXT = 24;

	/** uncompressed crc-32 for file */
	protected static final int EXTCRC = 0;

	/** compressed size in zip file */
	protected static final int EXTSIZ = 4;

	/** uncompressed size */
	protected static final int EXTLEN = 8;

	/** version made by */
	protected static final int CENVEM = 0;

	/** version needed to extract */
	protected static final int CENVER = 2;

	/** encrypt, deflate flags */
	protected static final int CENFLG = 4;

	/** compression method */
	protected static final int CENHOW = 6;

	/** last modified file time, DOS format */
	protected static final int CENTIM = 8;

	/** last modified file date, DOS format */
	protected static final int CENDAT = 10;

	/** uncompressed crc-32 for file */
	protected static final int CENCRC = 12;

	/** compressed size in zip file */
	protected static final int CENSIZ = 16;

	/** uncompressed size */
	protected static final int CENLEN = 20;

	/** length of filename */
	protected static final int CENNAM = 24;

	/** length of extra field */
	protected static final int CENEXT = 26;

	/** file comment length */
	protected static final int CENCOM = 28;

	/** disk number start */
	protected static final int CENDSK = 30;

	/** internal file attributes */
	protected static final int CENATT = 32;

	/** external file attributes */
	protected static final int CENATX = 34;

	/** relative offset of local header */
	protected static final int CENOFF = 38;

	/** number of this disk */
	protected static final int ENDDSK = 0;

	/** number of the starting disk */
	protected static final int ENDBEG = 2;

	/** entries on this disk */
	protected static final int ENDSUB = 4;

	/** total number of entries */
	protected static final int ENDTOT = 6;

	/** size of entire central directory */
	protected static final int ENDSIZ = 8;

	/** offset of central on starting disk */
	protected static final int ENDOFF = 12;

	/** length of zip file comment */
	protected static final int ENDCOM = 16;

	private static final int SH(byte[] a, int n) {
		return (a[n] & 0xff) | ((a[n + 1] & 0xff) << 8);
	}

	private static final int LG(byte[] a, int n) {
		return SH(a, n) | (SH(a, n + 2) << 16);
	}

	private static final void PUTSH(int a, RandomAccessFile f)
			throws IOException {
		f.write(a & 0xff);
		f.write((a >>> 8) & 0xff);
	}

	private static final void PUTLG(int a, RandomAccessFile f)
			throws IOException {
		PUTSH((a & 0xffff), f);
		PUTSH((a >>> 16), f);
	}

	private final byte[] b = new byte[CENHEAD]; /* buffer for central headers */

	/**
	 * scanzipf_fix is called with zip -F or zip -FF read the file from front to
	 * back and pick up the pieces NOTE: there are still checks missing to see
	 * if the header that was found is *VALID*
	 * 
	 * @param f
	 *            zip file
	 * @throws IOException
	 */
	private void scanzipf_fix(RandomAccessFile f) throws IOException
	/*
	 * The name of the zip file is pointed to by the global "zipfile". The
	 * globals zipbeg, cenbeg, zfiles, zcount, zcomlen, zcomment, and zsort are
	 * filled in. Return an error code in the ZE_ class.
	 */
	{
		int a = 0; /* attributes returned by filetime() */
		int flg; /* general purpose bit flag */
		int m; /* mismatch flag */
		int n; /* length of name */
		long p; /* current file offset */
		long s; /* size of data, start of central */
		Zlist x; /* pointer last entry's link */
		Zlist z; /* current zip entry structure */

		/*
		 * Get any file attribute valid for this OS, to set in the central
		 * directory when fixing the archive:
		 */
		int[] ap = new int[1];
		filetime(zipfile, ap, null, null);
		a = ap[0];

		x = null; /* first link */
		p = 0; /* starting file offset */
		/* Find start of zip structures */
		for (;;) {
			try {
				while ((m = f.read()) != -1 && m != 0x50) /* 0x50 == 'P' */
				{
					p++;
				}
			} catch (IOException e2) {
				m = -1;
			}
			b[0] = (byte) m;
			try {
				if (f.read(b, 1, 3) != 3 || (s = LG(b, 0)) == LOCSIG
						|| s == ENDSIG)
					break;
			} catch (IOException e1) {
				break;
			}
			f.seek(f.getFilePointer() - 3);
			p++;
		}
		zipbeg = p;

		/* Read local headers */
		while (LG(b, 0) == LOCSIG) {
			try {
				if (f.read(b, 0, LOCHEAD) != LOCHEAD) {
					break;
				}
			} catch (IOException e4) {
				break;
			}
			z = new Zlist();

			z.ver = SH(b, LOCVER);
			z.vem = (dosify != 0 ? 20 : getOScode() + Z_MAJORVER * 10
					+ Z_MINORVER);
			z.dosflag = dosify;
			flg = z.flg = z.lflg = SH(b, LOCFLG);
			z.how = SH(b, LOCHOW);
			z.tim = LG(b, LOCTIM); /* time and date into one long */
			z.crc = LG(b, LOCCRC);
			z.siz = LG(b, LOCSIZ);
			z.len = LG(b, LOCLEN);
			n = z.nam = SH(b, LOCNAM);
			z.cext = z.ext = SH(b, LOCEXT);

			z.com = 0;
			z.dsk = 0;
			z.att = 0;
			/* Attributes from filetime() */
			z.atx = dosify != 0 ? a & 0xff : a;
			z.mark = 0;
			z.trash = 0;

			/* discard compressed size with -FF */
			s = fix > 1 ? 0 : z.siz;

			/* Initialize all fields pointing to malloced data to null */
			z.zname = z.name = z.iname = z.extra = z.cextra = z.comment = null;

			/* Link into list */
			if (x == null) {
				zfiles = z;
			} else {
				x.nxt = z;
			}
			z.nxt = null;
			x = z;

			/* Read file name and extra field and skip data */
			if (n == 0) {
				throw new ZipException("zero-length name for entry #"
						+ (zcount + 1));
			}
			z.iname = new byte[n];
			z.extra = new byte[z.ext];
			if (f.read(z.iname) != n || f.read(z.extra) != z.ext) {
				throw new IOException();
			}
			if (s != 0)
				f.seek(f.getFilePointer() + s);
			/*
			 * If there is an extended local header, s is either 0 or the
			 * correct compressed size.
			 */
			z.zname = z.iname; /* convert to external name */
			z.name = z.zname;
			z.cextra = z.extra;

			/* Save offset, update for next header */
			z.off = p;
			p += 4 + LOCHEAD + n + z.ext + s;
			zcount++;

			/* Skip extended local header if there is one */
			if ((flg & 8) != 0) {
				/*
				 * Skip the compressed data if compressed size is unknown. For
				 * safety, we should use the central directory.
				 */
				if (s == 0) {
					for (;;) {
						try {
							while ((m = f.read()) != -1 && m != 0x50)
								/* 0x50 == 'P' */
								;
						} catch (IOException e2) {
							m = -1;
						}
						b[0] = (byte) m;
						try {
							if (f.read(b, 1, 15) != 15 || LG(b, 0) == EXTLOCSIG)
								break;
						} catch (IOException e1) {
							break;
						}
						f.seek(f.getFilePointer() - 15);
					}
					s = LG(b, 4 + EXTSIZ);
					p += s;
					if (f.getFilePointer() != p + 16L) {
						throw new ZipException("bad extended local header for "
								+ getString(z.zname));
					}
				} else {
					/* compressed size non-zero, assume that it is valid: */
					if (p != f.getFilePointer()) {
						throw new ZipException(
								"bad compressed size with extended header");
					}

					f.seek(p);
					if (f.read(b, 0, 16) != 16) {
						throw new IOException();
					}
					if (LG(b, 0) != EXTLOCSIG) {
						throw new ZipException(
								"extended local header not found for "
										+ getString(z.zname));
					}
				}
				/* overwrite the unknown values of the local header: */

				/* already in host format */
				z.crc = LG(b, 4 + EXTCRC);
				z.siz = s;
				z.len = LG(b, 4 + EXTLEN);

				p += 16L;
			} else if (fix > 1) {
				/* Don't trust the compressed size */
				for (;;) {
					try {
						while ((m = f.read()) != -1 && m != 0x50)
							/* 0x50 == 'P' */
							p++;
					} catch (IOException e2) {
						m = -1;
					}
					b[0] = (byte) m;
					try {
						if (f.read(b, 1, 3) != 3 || (s = LG(b, 0)) == LOCSIG
								|| s == CENSIG)
							break;
					} catch (IOException e1) {
						break;
					}
					f.seek(f.getFilePointer() - 3);
					p++;
				}
				s = p - (z.off + 4 + LOCHEAD + n + z.ext);
				if (s != z.siz) {
					// mesg.printf(
					// " compressed size %ld, actual size %ld for %s\n",
					// z.siz, s, z.zname);
					z.siz = s;
				}
				/* next LOCSIG already read at this point, don't read it again: */
				continue;
			}

			/* Read next signature */
			try {
				if (f.read(b, 0, 4) != 4)
					break;
			} catch (IOException e) {
				break;
			}
		}

		s = p; /* save start of central */

		cenbeg = s;
	}

	/* temp buffer for reading zipfile */
	private final byte[] buf = new byte[4096 + 4];

	private final byte[] buf2 = new byte[16];

	/**
	 * scanzipf_reg starts searching for the End Signature at the end of the
	 * file The End Signature points to the Central Directory Signature which
	 * points to the Local Directory Signature XXX probably some more
	 * consistency checks are needed
	 * 
	 * @param f
	 *            zip file
	 * @throws IOException
	 */
	private void scanzipf_reg(RandomAccessFile f) throws IOException
	/*
	 * The name of the zip file is pointed to by the global "zipfile". The
	 * globals zipbeg, cenbeg, zfiles, zcount, zcomlen, zcomment, and zsort are
	 * filled in. Return an error code in the ZE_ class.
	 */
	{
		int flg; /* general purpose bit flag */
		int m; /* mismatch flag */
		int n; /* length of name */
		Zlist x; /* pointer last entry's link */
		Zlist z; /* current zip entry structure */
		int t; /* temporary pointer */
		int found;
		long deltaoff;

		found = 0;
		t = 4096;// t = &buf[4096];
		buf[t + 1] = '\0';
		buf[t + 2] = '\0';
		buf[t + 3] = '\0';
		try {
			f.seek(f.length() - 4096);
			zipbeg = f.getFilePointer() + 4096;
			while (found == 0 && zipbeg >= 4096) {
				zipbeg -= 4096;
				buf[4096] = buf[t + 1];
				buf[4097] = buf[t + 2];
				buf[4098] = buf[t + 3];
				/*
				 * XXX error check ??
				 */
				try {
					f.read(buf, 0, 4096);
					f.seek(f.getFilePointer() - 8192);
				} catch (IOException e) {
				}
				t = 4095;// t = &buf[4095];
				/*
				 * XXX far pointer arithmetic in DOS
				 */
				while (t >= 0) {
					/*
					 * Check for ENDSIG the End Of Central Directory Record
					 * signature ("PK\5\6" in ASCII)
					 */
					if (LG(buf, t) == ENDSIG) {
						found = 1;
						/*
						 * XXX error check ?? XXX far pointer arithmetic in DOS
						 */
						zipbeg += t;
						f.seek(zipbeg + 4);
						break;
					}
					--t;
				}
			}
		} catch (IOException e) {
			zipbeg = 4096;
		}
		/*
		 * XXX warn: garbage at the end of the file ignored
		 */
		if (found == 0 && zipbeg > 0) {
			int s;

			try {
				f.seek(0);
			} catch (IOException e) {
			}
			// clearerr(f);
			s = f.read(buf, 0, (int) zipbeg);
			buf[s] = buf[t + 1];
			buf[s + 1] = buf[t + 2];
			buf[s + 2] = buf[t + 3];
			t = s - 1;// t = &buf[s - 1];
			/*
			 * XXX far pointer comparison in DOS
			 */
			while (t >= 0) {
				/* Check for ENDSIG ("PK\5\6" in ASCII) */
				if (LG(buf, t) == ENDSIG) {
					found = 1;
					/*
					 * XXX far pointer arithmetic in DOS
					 */
					zipbeg = t;
					try {
						f.seek(zipbeg + 4);
					} catch (IOException e) {
					}
					break;
				}
				--t;
			}
		}
		if (found == 0) {
			throw new ZipException(
					"missing end signature--probably not a zip file (did you remember to use binary mode when you transferred it?)");
		}

		/*
		 * Read the End Of Central Directory Record
		 */
		/* Read end header */
		if (f.read(b, 0, ENDHEAD) != ENDHEAD) {
			throw new IOException();
		}
		if (SH(b, ENDDSK) != 0 || SH(b, ENDBEG) != 0
				|| SH(b, ENDSUB) != SH(b, ENDTOT)) {
			// zipwarn("multiple disk information ignored", "");
		}
		zcomlen = SH(b, ENDCOM);
		if (zcomlen != 0) {
			zcomment = new byte[zcomlen];
			if (f.read(zcomment) != zcomlen) {
				throw new IOException();
			}
		}
		/*
		 * XXX assumes central header immediately precedes end header
		 */
		cenbeg = zipbeg - LG(b, ENDSIZ);
		deltaoff = adjust != 0 ? cenbeg - LG(b, ENDOFF) : 0;
		f.seek(cenbeg);

		x = null; /* first link */

		if (f.read(b, 0, 4) != 4) {
			throw new IOException();
		}

		while (LG(b, 0) == CENSIG) {
			/*
			 * Read central header. The portion of the central header that
			 * should be in common with local header is read raw, for later
			 * comparison. (this requires that the offset of ext in the zlist
			 * structure be greater than or equal to LOCHEAD)
			 */
			if (f.read(b, 0, CENHEAD) != CENHEAD) {
				throw new IOException();
			}
			z = new Zlist();
			z.vem = SH(b, CENVEM);

			// for (u = (char far *)(&(z.ver)), n = 0; n < (CENNAM-CENVER); n++)
			// u[n] = b[CENVER + n];
			z.ver = SH(b, CENVER);
			z.flg = SH(b, CENFLG);
			z.how = SH(b, CENHOW);
			z.tim = LG(b, CENTIM);
			z.crc = LG(b, CENCRC);
			z.siz = LG(b, CENSIZ);
			z.len = LG(b, CENLEN);

			z.nam = SH(b, CENNAM); /*
									 * used before comparing cen vs. loc
									 */
			z.cext = SH(b, CENEXT);/*
									 * may be different from z.ext
									 */
			z.com = SH(b, CENCOM);
			z.dsk = SH(b, CENDSK);
			z.att = SH(b, CENATT);
			z.atx = LG(b, CENATX);
			z.off = LG(b, CENOFF) + deltaoff;
			z.dosflag = ((z.vem & 0xff00) == 0) ? 1 : 0;

			/* Initialize all fields pointing to malloced data to null */
			z.zname = z.name = z.iname = z.extra = z.cextra = z.comment = null;

			/* Link into list */
			if (x == null) {
				zfiles = z;
			} else {
				x.nxt = z;
			}
			z.nxt = null;
			x = z;

			/* Read file name, extra field and comment field */
			if (z.nam == 0) {
				throw new ZipException("zero-length name for entry #"
						+ (zcount + 1));
			}
			z.iname = new byte[z.nam];
			z.cextra = new byte[z.cext];
			z.comment = new byte[z.com];
			if (f.read(z.iname) != z.nam || (f.read(z.cextra) != z.cext)
					|| (f.read(z.comment) != z.com)) {
				throw new IOException();
			}
			/* Update zipbeg offset, prepare for next header */
			if (z.off < zipbeg)
				zipbeg = z.off;
			zcount++;
			/* Read next signature */
			if (f.read(b, 0, 4) != 4) {
				throw new IOException();
			}
		}

		/* Point to start of header list and read local headers */
		z = zfiles;
		while (z != null) {
			/* Read next signature */
			f.seek(z.off);
			if (f.read(b, 0, 4) != 4) {
				throw new IOException();
			}
			if (LG(b, 0) == LOCSIG) {
				if (f.read(b, 0, LOCHEAD) != LOCHEAD) {
					throw new IOException();
				}
				z.lflg = SH(b, LOCFLG);
				n = SH(b, LOCNAM);
				z.ext = SH(b, LOCEXT);

				/* Compare name and extra fields */
				if (n != z.nam) {
					throw new ZipException(
							"name lengths in local and central differ for "
									+ getString(z.iname));
				}
				byte[] tb = new byte[z.nam];
				if (f.read(tb, 0, z.nam) != z.nam) {
					throw new IOException();
				}
				if (!Arrays.equals(tb, z.iname)) {
					throw new ZipException(
							"names in local and central differ for "
									+ getString(z.iname));
				}
				tb = null;
				if (z.ext != 0) {
					z.extra = new byte[z.ext];
					if (f.read(z.extra) != z.ext) {
						throw new IOException();
					}
					if (z.ext == z.cext && Arrays.equals(z.extra, z.cextra)) {
						z.extra = z.cextra;
					}
				}

				/* Check extended local header if there is one */
				if ((z.lflg & 8) != 0) {
					long s; /* size of compressed data */

					s = LG(b, LOCSIZ);
					if (s == 0) {
						// s = LG((CENSIZ-CENVER) + (char far *)(&(z.ver)));
						s = z.siz;
					}
					f.seek(z.off + (4 + LOCHEAD) + z.nam + z.ext + s);
					if (f.read(buf2, 0, 16) != 16) {
						throw new IOException();
					}
					if (LG(buf2, 0) != EXTLOCSIG) {
						throw new ZipException(
								"extended local header not found for "
										+ getString(z.iname));
					}
					/* overwrite the unknown values of the local header: */
					for (n = 0; n < 12; n++)
						b[LOCCRC + n] = buf2[4 + n];
				}

				/*
				 * Compare local header with that part of central header (except
				 * for the reserved bits in the general purpose flags and except
				 * for the already checked entry name length
				 */
				// u = (char far *)(&(z.ver));
				/* Save central flags word */
				flg = z.flg; // flg = SH((CENFLG-CENVER) + u);

				z.flg &= 0x1fff; // u[CENFLG - CENVER + 1] &= 0x1f; //Mask
				// reserved flag bits

				b[LOCFLG + 1] &= 0x1f;
				// for (m = 0, n = 0; n < LOCNAM; n++)
				// if (b[n] != u[n]) {
				// if (m == 0) {
				// zipwarn("local and central headers differ for ", z.zname);
				// m = 1;
				// }
				// }
				m = 0;
				m += eqSH(z.ver, b, CENVER - CENVER); // 0
				m += eqSH(z.flg, b, CENFLG - CENVER); // 2
				m += eqSH(z.how, b, CENHOW - CENVER); // 4
				m += eqLG(z.tim, b, CENTIM - CENVER); // 6
				m += eqLG(z.crc, b, CENCRC - CENVER); // 10
				m += eqLG((int) z.siz, b, CENSIZ - CENVER); // 14
				m += eqLG(z.len, b, CENLEN - CENVER); // 18
				if (m != 0 && adjust == 0) {
					throw new ZipException(
							"local and central headers differ for "
									+ getString(z.zname));
				}

				/*
				 * Complete the setup of the zlist entry by translating the
				 * remaining central header fields in memory, starting with the
				 * fields with highest offset. This order of the conversion
				 * commands takes into account potential buffer overlaps caused
				 * by structure padding.
				 */
				// z.len = LG(u, (CENLEN - CENVER));
				// z.siz = LG(u, (CENSIZ - CENVER));
				// z.crc = LG(u, (CENCRC - CENVER));
				/* time and date into one long */
				// z.tim = LG(u, (CENTIM - CENVER));
				// z.how = SH(u, (CENHOW - CENVER));
				z.flg = flg; /* may be different from z.lflg */
				// z.ver = SH(u, (CENVER - CENVER));

				/* Clear actions */
				z.mark = 0;
				z.trash = 0;
				z.zname = z.iname; /* convert to external name */
				z.name = z.zname;
			} else {
				throw new ZipException("local header not found for "
						+ getString(z.iname));
			}
			// if (verbose != 0)
			// zipoddities(z);
			z = z.nxt;
		}
	}

	private static final int eqLG(int z, byte[] b, int pos) {
		int v = LG(b, pos);
		// System.out.println(pos + ":" + z + "/" + v);
		return (z != v) ? 1 : 0;
	}

	private static final int eqSH(int z, byte[] b, int pos) {
		int v = SH(b, pos);
		// System.out.println(pos + ":" + z + "/" + v);
		return (z != v) ? 1 : 0;
	}

	/**
	 * The name of the zip file is pointed to by the global "zipfile". The
	 * globals zipbeg, zfiles, zcount, and zcomlen are initialized. Return an
	 * error code in the ZE_ class.
	 * 
	 * @param zipfile
	 * @throws IOException
	 */
	protected void readzipfile(File zipfile) throws IOException {
		/* Initialize zip file info */
		zipbeg = 0;
		zfiles = null; // Points to first header
		zcount = 0; // number of files
		zcomlen = 0; // zip file comment length
		tempzn = 0;

		/* If zip file exists, read headers and check structure */
		RandomAccessFile f = new RandomAccessFile(zipfile, "r");
		try {
			if (fix != 0 && adjust == 0) {
				scanzipf_fix(f);
			} else {
				scanzipf_reg(f);
			}
		} finally {
			/* Done with zip file for now */
			try {
				f.close();
			} catch (IOException e) {
			}
		}
	}

	/**
	 * Write a local header described by *z to file *f. Return an error code in
	 * the ZE_ class.
	 * 
	 * @param z
	 *            zip entry to write local header for
	 * @param f
	 *            file to write to
	 * @throws IOException
	 */
	protected void putlocal(Zlist z, RandomAccessFile f) throws IOException {
		PUTLG(LOCSIG, f);
		PUTSH(z.ver, f);
		PUTSH(z.lflg, f);
		PUTSH(z.how, f);
		PUTLG(z.tim, f);
		PUTLG(z.crc, f);
		PUTLG((int) z.siz, f);
		PUTLG(z.len, f);
		PUTSH(z.nam, f);
		PUTSH(z.ext, f);
		f.write(z.iname, 0, z.nam);
		if (z.ext != 0) {
			f.write(z.extra, 0, z.ext);
		}
	}

	/**
	 * Write a central header described by *z to file *f. Return an error code
	 * in the ZE_ class.
	 * 
	 * @param z
	 *            zip entry to write central header for
	 * @param f
	 *            file to write to
	 * @return
	 * @throws IOException
	 */
	protected void putcentral(Zlist z, RandomAccessFile f) throws IOException {
		PUTLG(CENSIG, f);
		PUTSH(z.vem, f);
		PUTSH(z.ver, f);
		PUTSH(z.flg, f);
		PUTSH(z.how, f);
		PUTLG(z.tim, f);
		PUTLG(z.crc, f);
		PUTLG((int) z.siz, f);
		PUTLG(z.len, f);
		PUTSH(z.nam, f);
		PUTSH(z.cext, f);
		PUTSH(z.com, f);
		PUTSH(z.dsk, f);
		PUTSH(z.att, f);
		PUTLG(z.atx, f);
		PUTLG((int) z.off, f);
		f.write(z.iname);
		if (z.cext != 0) {
			f.write(z.cextra);
		}
		if (z.com != 0) {
			f.write(z.comment);
		}
	}

	/**
	 * Write the end of central directory data to file *f. Return an error code
	 * in the ZE_ class.
	 * 
	 * @param n
	 *            number of entries in central directory
	 * @param s
	 *            size of central directory
	 * @param c
	 *            offset of central directory
	 * @param m
	 *            length of zip file comment (0 if none)
	 * @param z
	 *            zip file comment if m != 0
	 * @param f
	 *            file to write to
	 * @return
	 * @throws IOException
	 */
	protected void putend(int n, int s, int c, int m, byte[] z,
			RandomAccessFile f) throws IOException {
		PUTLG(ENDSIG, f);
		PUTSH(0, f);
		PUTSH(0, f);
		PUTSH(n, f);
		PUTSH(n, f);
		PUTLG(s, f);
		PUTLG(c, f);
		PUTSH(m, f);
		/* Write the comment, if any */
		if (m != 0) {
			f.write(z);
		}
	}

	/**
	 * Copy the zip entry described by *z from file *x to file *y. Return an
	 * error code in the ZE_ class. Also update tempzn by the number of bytes
	 * copied.
	 * 
	 * @param z
	 *            zip entry to copy
	 * @param x
	 *            source
	 * @param y
	 *            destination
	 * @return
	 * @throws IOException
	 */
	protected void zipcopy(Zlist z, RandomAccessFile x, RandomAccessFile y)
			throws IOException {
		long n; /* holds local header offset */

		n = (4 + LOCHEAD) + z.nam + z.ext;

		if (fix > 1) {
			x.seek(z.off + n);/* seek to compressed data */

			if (fix > 2) {
				/*
				 * Update length of entry's name, it may have been changed. This
				 * is needed to support the ZipNote ability to rename archive
				 * entries.
				 */
				z.nam = z.iname.length;
				n = (4 + LOCHEAD) + z.nam + z.ext;
			}

			/* do not trust the old compressed size */
			putlocal(z, y);

			z.off = tempzn;
			tempzn += n;
			n = z.siz;
		} else {
			x.seek(z.off); /* seek to local header */

			z.off = tempzn;
			n += z.siz;
		}
		/*
		 * copy the compressed data and the extended local header if there is
		 * one
		 */
		if ((z.lflg & 8) != 0)
			n += 16;
		tempzn += n;
		fcopy(x, y, n);
	}

}
