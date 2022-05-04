/*
 * �쐬��: 2004/03/14
 */
package jp.hishidama.bitmap;

import java.io.File;

/**
 * �t�@�C����ň���256�F�r�b�g�}�b�v
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/bitmap.html">�Ђ�����</a>
 */
public class FileBitmap08 extends Bitmap08 implements FilePaletteBitmap {

	protected FileBitmapControl fbc;

	public FileBitmap08(int nx, int ny, String pathName, String fileName) {
		super(nx, ny);
		fbc = new FileBitmapControl(this);
		fbc.init(pathName, fileName);
	}

	public FileBitmap08(File file, BitmapFileHeader fh, BitmapInfoHeader bi,
			BitmapInfoColors pl) {
		super(0, 0);
		this.bi = bi;
		this.pals = pl;
		fbc = new FileBitmapControl(this);
		fbc.init(file, fh);
	}

	public boolean flush() {
		return fbc.flush();
	}

	public boolean close() {
		return fbc.close();
	}

	/**
	 * �p���b�g��ݒ肷��
	 * 
	 * @param c
	 *            �F�R�[�h
	 * @param r
	 * @param g
	 * @param b
	 */
	public void setPalet(int c, int r, int g, int b) {
		super.setPalet(c, r, g, b);
		fbc.setPalet(c, r, g, b);
	}

	/*
	 * (�� Javadoc)
	 * 
	 * @see jp.hishidama.bitmap.Bitmap#set0(int, int, int)
	 */
	public boolean set0(int x, int y, int c) {
		fbc.buf[0] = (byte) c;
		return fbc.set0(x, y);
	}

	/*
	 * (�� Javadoc)
	 * 
	 * @see jp.hishidama.bitmap.Bitmap#get0(int, int)
	 */
	public int get0(int x, int y) {
		if (!fbc.get0(x, y))
			return INVALID_COLOR;

		int c = fbc.buf[0] & 255;
		return c;
	}

}
