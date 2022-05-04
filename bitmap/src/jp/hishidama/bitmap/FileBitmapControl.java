/*
 * 作成日: 2004/03/14
 */
package jp.hishidama.bitmap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.stream.FileImageOutputStream;

/**
 * ファイル上で扱うビットマップの実体
 * @author <a target="hishidama" href="http://www.ne.jp/asahi/hishidama/home/soft/java/bitmap.html">ひしだま</a>
 */
public class FileBitmapControl {

	protected FileBitmap target;
	protected FileImageOutputStream io;
	protected BitmapFileHeader fh;
	protected int pitch;
	/**
	 * set0(),get0()で使うためのバッファ
	 */
	public byte[] buf;

	public FileBitmapControl(FileBitmap bmp) {
		this.target=bmp;
	}

	public boolean init(File file,BitmapFileHeader fh){
		try {
			this.io=new FileImageOutputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		this.fh=fh;
		this.pitch=target.getPitch();
		BitmapInfoHeader bi=target.getBitmapInfoHeader();
		this.buf=new byte[(bi.getBitCount()+7)/8];
		return true;
	}

	/**
	 * @param pathName
	 * @param fileName
	 */
	public boolean init(String pathName, String fileName) {
		//ファイル初期化
		File file=new File(pathName,fileName);
		FileOutputStream output;
		try {
			output=new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}

		BitmapInfoHeader bi=target.getBitmapInfoHeader();
		BitmapInfoColors pl=target.getPalets();
		BitmapFileHeader fh=createBitmapFileHeader(bi,pl);
		bi.setSizeImage(fh.getSize()-fh.getOffBits());
		if(pl!=null){
			bi.setColorUsed(pl.getCount());
		}

		try {
			output.write(fh.getBytes());
			output.write(bi.getBytes());
			if(pl!=null){
				output.write(pl.getPalBytes());	//パレットデータ
			}
			byte[] line=new byte[target.getPitch()];
			for(int y=bi.getHeight()-1;y>=0;y--){
				output.write(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		try {
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return init(file,fh);
	}

	public static BitmapFileHeader createBitmapFileHeader(BitmapInfoHeader bi,BitmapInfoColors pl){

		BitmapFileHeader fh=new BitmapFileHeader();

		//画像データの開始オフセット
		int off=fh.getBytesLength()+bi.getBytesLength();
		if(pl!=null) off+=pl.getBytesLength();
		fh.setOffBits(off);

		//ファイルサイズ
		int size=fh.getOffBits();
		if(pl!=null) size+=pl.getBytesLength();
		int pitch=AbstractBitmap.calcPitch(bi.getBitCount(),bi.getWidth());
		size+=pitch*bi.getHeight();
		fh.setSize(size);

		return fh;
	}

	public long getFileOffset(int x,int y){
		BitmapInfoHeader bi=target.getBitmapInfoHeader();
		return getFileOffset(fh,bi,x,y,pitch);
	}
	public static long getFileOffset(BitmapFileHeader fh,BitmapInfoHeader bi,int x,int y,int pitch){
		return fh.getOffBits()+getPicOffset(bi,x,y,pitch);
	}
	public static long getPicOffset(BitmapInfoHeader bi,int x,int y,int pitch){
		short bits=bi.getBitCount();
		if(bits<8){
			x/=8/bits;
		}else{
			x*=bits/8;
		}
		y=bi.getHeight()-1-y;
		return y*pitch+x;
	}

	/* (非 Javadoc)
	 * @see jp.hishidama.bitmap.Bitmap#set0(int, int, int)
	 */
	public boolean set0(int x, int y) {
		long pos=getFileOffset(x,y);
		try {
			io.seek(pos);
			io.write(buf,0,buf.length);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/* (非 Javadoc)
	 * @see jp.hishidama.bitmap.Bitmap#get0(int, int)
	 */
	public boolean get0(int x, int y) {
		long pos=getFileOffset(x,y);
		try {
			io.seek(pos);
			io.read(buf,0,buf.length);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	final byte[] palbuf=new byte[4];

	/**
	 * @param c
	 * @param r
	 * @param g
	 * @param b
	 * @return 成功した場合、true
	 */
	public boolean setPalet(int c, int r, int g, int b) {
		long pos=fh.getBytesLength();
		pos+=target.getBitmapInfoHeader().getBytesLength();
		pos+=c*4;
		int rgb=target.getRGB(c);
		palbuf[0]=(byte) BitmapInfoColors.getRgbB(rgb);
		palbuf[1]=(byte) BitmapInfoColors.getRgbG(rgb);
		palbuf[2]=(byte) BitmapInfoColors.getRgbR(rgb);
		try {
			io.seek(pos);
			io.write(palbuf);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * @return 成功した場合、true
	 */
	public boolean flush() {
		try {
			io.flush();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * @return 成功した場合、true
	 */
	public boolean close() {
		try {
			io.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
