package jp.hishidama.zip;

/**
 * zip.h�̈ڐA
 * 
 * @since 2007.12.14
 */
interface InfoZIP_Zip {

	/* Lengths of headers after signatures in bytes */
	public static final int LOCHEAD = 26;

	public static final int CENHEAD = 42;

	public static final int ENDHEAD = 18;
}

/**
 * Structures for in-memory file information
 * 
 * @since 2007.12.14
 */
class Zlist {
	/* See central header in zipfile.c for what vem..off are */
	int vem, ver, flg, how;

	int tim, crc, len;

	long siz;

	int nam, ext, cext, com; /* offset of ext must be >= LOCHEAD */

	int dsk, att, lflg; /* offset of lflg must be >= LOCHEAD */

	int atx;

	long off;

	/** File name in zip file */
	byte[] name;

	/** Extra field (set only if ext != 0) */
	byte[] extra;

	/** Extra in central (set only if cext != 0) */
	byte[] cextra;

	/** Comment (set only if com != 0) */
	byte[] comment;

	/** Internal file name after cleanup */
	byte[] iname;

	/** External version of internal name */
	byte[] zname;

	/** Marker for files to operate on */
	int mark;

	/** Marker for files to delete */
	int trash;

	/** Set to force MSDOS file attributes */
	int dosflag;

	/** Pointer to next header in list */
	Zlist nxt;
}
