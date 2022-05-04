package jp.hishidama.zip;

import java.io.File;

/**
 * globals.c�̈ڐA
 * 
 * @since 2007.12.14
 */
class InfoZIP_Globals {

	/* Argument processing globals */
	/** 1=make new entries look like MSDOS */
	protected int dosify = 0;

	/** 1=fix the zip file */
	protected int fix = 0;

	/** 1=adjust offsets for sfx'd file (keep preamble) */
	protected int adjust = 0;

	/* Zip file globals */
	/** New or existing zip archive (zip file) */
	protected File zipfile;

	/** Starting offset of zip structures */
	protected long zipbeg;

	/** Starting offset of central directory */
	protected long cenbeg;

	/** Pointer to list of files in zip file */
	protected Zlist zfiles = null;

	/** Number of files in zip file */
	protected int zcount;

	/** Length of zip file comment */
	protected int zcomlen;

	/** Zip file comment (not zero-terminated) */
	protected byte[] zcomment = null;

	/** Count of bytes written to output zip file */
	protected long tempzn;

	/**
	 * Make entries using DOS names (k for Katz)
	 * 
	 * @param dosify
	 *            0:default, 1:-k
	 */
	protected void setDosify(int dosify) {
		this.dosify = dosify;
	}

	/**
	 * fix the zip file
	 * 
	 * @param fix
	 *            0:default, 1:-F, 2:-FF
	 */
	protected void setFix(int fix) {
		this.fix = fix;
	}

	/**
	 * Adjust unzipsfx'd zipfile: adjust offsets only
	 * 
	 * @param adjust
	 *            0:default, 1:-A
	 */
	protected void setAdjust(int adjust) {
		this.adjust = adjust;
	}
}
