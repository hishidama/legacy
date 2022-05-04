/*
 * 作成日: 2004/03/14
 */
package jp.hishidama.bitmap;

import java.io.File;

/**
 * ファイル上で扱う16色ビットマップ
 * @author <a target="hishidama" href="http://www.ne.jp/asahi/hishidama/home/soft/java/bitmap.html">ひしだま</a>
 */
public class FileBitmap04 extends Bitmap04 implements FilePaletteBitmap {

	protected FileBitmapControl fbc;

	public FileBitmap04(int nx, int ny,String pathName,String fileName) {
		super(nx,ny);
		fbc=new FileBitmapControl(this);
		fbc.init(pathName,fileName);
	}
	public FileBitmap04(File file, BitmapFileHeader fh, BitmapInfoHeader bi,BitmapInfoColors pl){
		super(0,0);
		this.bi=bi;
		this.pals=pl;
		fbc=new FileBitmapControl(this);
		fbc.init(file,fh);
	}
	public boolean flush(){
		return fbc.flush();
	}
	public boolean close(){
		return fbc.close();
	}

	/**
	 * パレットを設定する
	 * @param c	色コード
	 * @param r
	 * @param g
	 * @param b
	 */
	public void setPalet(int c,int r,int g,int b){
		super.setPalet(c,r,g,b);
		fbc.setPalet(c,r,g,b);
	}

	/* (非 Javadoc)
	 * @see jp.hishidama.bitmap.Bitmap#set0(int, int, int)
	 */
	public boolean set0(int x, int y, int c) {
		if(!fbc.get0(x,y)){
			fbc.buf[0]=0;
		}
		if((x&1)!=0){
			fbc.buf[0]&=0x0f;
			fbc.buf[0]|=c<<4;
		}else{
			fbc.buf[0]&=0xf0;
			fbc.buf[0]|=c&15;
		}
		return fbc.set0(x,y);
	}

	/* (非 Javadoc)
	 * @see jp.hishidama.bitmap.Bitmap#get0(int, int)
	 */
	public int get0(int x, int y) {
		if(!fbc.get0(x,y)) return INVALID_COLOR;

		int c;
		if((x&1)!=0){
			c=fbc.buf[0]>>4;
		}else{
			c=fbc.buf[0];
		}
		return c&15;
	}

}
