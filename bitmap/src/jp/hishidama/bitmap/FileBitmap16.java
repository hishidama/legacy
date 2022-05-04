/*
 * 作成日: 2004/03/14
 */
package jp.hishidama.bitmap;

import java.io.File;

/**
 * ファイル上で扱う16bit色ビットマップ
 * @author <a target="hishidama" href="http://www.ne.jp/asahi/hishidama/home/soft/java/bitmap.html">ひしだま</a>
 */
public class FileBitmap16 extends Bitmap16 implements FileBitmap {

	protected FileBitmapControl fbc;

	public FileBitmap16(int nx, int ny,String pathName,String fileName) {
		super(nx,ny);
		fbc=new FileBitmapControl(this);
		fbc.init(pathName,fileName);
	}
	public FileBitmap16(File file, BitmapFileHeader fh, BitmapInfoHeader bi){
		super(0,0);
		this.bi=bi;
		fbc=new FileBitmapControl(this);
		fbc.init(file,fh);
	}
	public boolean flush(){
		return fbc.flush();
	}
	public boolean close(){
		return fbc.close();
	}

	/* (非 Javadoc)
	 * @see jp.hishidama.bitmap.Bitmap#set0(int, int, int)
	 */
	public boolean set0(int x, int y, int c) {
		fbc.buf[0]=(byte)c;
		fbc.buf[1]=(byte)(c>>8);
		return fbc.set0(x,y);
	}

	/* (非 Javadoc)
	 * @see jp.hishidama.bitmap.Bitmap#get0(int, int)
	 */
	public int get0(int x, int y) {
		if(!fbc.get0(x,y)) return INVALID_COLOR;

		int c=fbc.buf[0]&255;
		c|=(fbc.buf[1]&255)<<8;
		return c;
	}

}
