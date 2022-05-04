/*
 * 作成日: 2004/03/14
 */
package jp.hishidama.bitmap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * ビットマップ生成クラス
 * @author <a target="hishidama" href="http://www.ne.jp/asahi/hishidama/home/soft/java/bitmap.html">ひしだま</a>
 */
public class BitmapFactory {

	/**
	 * メモリー上で操作するビットマップを生成する
	 * @param nx 画像サイズX
	 * @param ny 画像サイズY
	 * @param bitCount 色数（ビット単位）
	 * @return ビットマップ
	 */
	public static Bitmap createMemeryBitmap(int nx,int ny,int bitCount){
		Bitmap bmp;
		switch(bitCount){
			case  4:	bmp=new MemoryBitmap04(nx,ny);break;
			case  8:	bmp=new MemoryBitmap08(nx,ny);break;
			case 16:	bmp=new MemoryBitmap16(nx,ny);break;
			case 24:	bmp=new MemoryBitmap24(nx,ny);break;
			case 32:	bmp=new MemoryBitmap32(nx,ny);break;
			default:	throw new RuntimeException("未定義ビットマップ　色数："+bitCount);
		}
		return bmp;
	}

	/**
	 * ファイル上で操作するビットマップを生成する<br>
	 * （ファイルも新しく作られる）
	 * @param pathName ビットマップファイルのパス名
	 * @param fileName ビットマップのファイル名
	 * @param nx 画像サイズX
	 * @param ny 画像サイズY
	 * @param bitCount 色数（ビット単位）
	 * @return ビットマップ
	 */
	public static Bitmap createFileBitmap(String pathName,String fileName,int nx,int ny,int bitCount){
		FileBitmap bmp;
		switch(bitCount){
			case  4:	bmp=new FileBitmap04(nx,ny,pathName,fileName);break;
			case  8:	bmp=new FileBitmap08(nx,ny,pathName,fileName);break;
			case 16:	bmp=new FileBitmap16(nx,ny,pathName,fileName);break;
			case 24:	bmp=new FileBitmap24(nx,ny,pathName,fileName);break;
			case 32:	bmp=new FileBitmap32(nx,ny,pathName,fileName);break;
			default:	throw new RuntimeException("未定義ビットマップ　色数："+bitCount);
		}
		return bmp;
	}

	/**
	 * メモリー上で操作するビットマップをロードする
	 * @param pathName ビットマップファイルのパス名
	 * @param fileName ビットマップのファイル名
	 * @return ビットマップ
	 */
	public static Bitmap loadMemoryBitmap(String pathName,String fileName){
		BitmapFileHeader fh=new BitmapFileHeader();
		BitmapInfoHeader bi=new BitmapInfoHeader();
		BitmapInfoColors pl=new BitmapInfoColors(0);

		File file=loadHeader(pathName,fileName,fh,bi,pl);
		if(file==null) return null;

		Bitmap bmp;
		switch(bi.getBitCount()){
			case  4:	bmp=new MemoryBitmap04(bi,pl);break;
			case  8:	bmp=new MemoryBitmap08(bi,pl);break;
			case 16:	bmp=new MemoryBitmap16(bi);break;
			case 24:	bmp=new MemoryBitmap24(bi);break;
			case 32:	bmp=new MemoryBitmap32(bi);break;
			default:	throw new RuntimeException("未定義ビットマップ　色数："+bi.getBitCount());
		}

		FileInputStream input;
		try {
			input=new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		try {
			int pitch=bmp.getPitch();
			long pos=FileBitmapControl.getFileOffset(fh,bi,0,bi.getHeight()-1,pitch);
			input.skip(pos);

			byte[] line=new byte[pitch];
			for(int y=bi.getHeight()-1;y>=0;y--){
				input.read(line);
				((AbstractBitmap)bmp).setDataBytes(y,line);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		try {
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return bmp;
	}

	/**
	 * ファイル上で操作するビットマップをオープンする
	 * @param pathName ビットマップファイルのパス名
	 * @param fileName ビットマップのファイル名
	 * @return ビットマップ
	 */
	public static FileBitmap openFileBitmap(String pathName,String fileName){
		BitmapFileHeader fh=new BitmapFileHeader();
		BitmapInfoHeader bi=new BitmapInfoHeader();
		BitmapInfoColors pl=new BitmapInfoColors(0);

		File file=loadHeader(pathName,fileName,fh,bi,pl);
		if(file==null) return null;

		return openFileBitmap(file,fh,bi,pl);
	}

	/**
	 * ファイル上で扱うビットマップをオープンする。
	 * @param file ファイル
	 * @param fh ビットマップファイルヘッダー
	 * @param bi ビットマップ情報ヘッダー
	 * @param pl ビットマップパレット
	 * @return ファイルビットマップ
	 */
	static FileBitmap openFileBitmap(File file,BitmapFileHeader fh,BitmapInfoHeader bi,BitmapInfoColors pl){
		FileBitmap bmp;
		switch(bi.getBitCount()){
			case  4:	bmp=new FileBitmap04(file,fh,bi,pl);break;
			case  8:	bmp=new FileBitmap08(file,fh,bi,pl);break;
			case 16:	bmp=new FileBitmap16(file,fh,bi);break;
			case 24:	bmp=new FileBitmap24(file,fh,bi);break;
			case 32:	bmp=new FileBitmap32(file,fh,bi);break;
			default:	throw new RuntimeException("未定義ビットマップ　色数："+bi.getBitCount());
		}
		return bmp;
	}

	/**
	 * ビットマップヘッダー類をファイルから読み込む
	 * @param pathName ファイルのパス名
	 * @param fileName ファイル名
	 * @param fh 読み込んだヘッダーを返す為のビットマップファイルヘッダー
	 * @param bi 読み込んだヘッダーを返す為のビットマップ情報ヘッダー
	 * @param pl 読み込んだパレットを返す為のビットマップパレット
	 * @return ファイル
	 */
	static File loadHeader(String pathName,String fileName,BitmapFileHeader fh,BitmapInfoHeader bi,BitmapInfoColors pl){
		File file=new File(pathName,fileName);
		FileInputStream input;
		try {
			input=new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}

		//ビットマップファイルヘッダーの読み込み
		final int fhlen=fh.getBytesLength();
		byte[] fhdata=new byte[fhlen];
		try {
			input.read(fhdata);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		fh.init(fhdata);

		//ビットマップ情報ヘッダーの読み込み
		final int bilen=bi.getBytesLength();
		byte[] bidata=new byte[bilen];
		try {
			input.read(bidata);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		bi.init(bidata);

		//パレットの読み込み
		final int palPos=fhlen+bi.getSize();
		final int palLen=fh.getOffBits()-palPos; 
		if(palLen>0){
			byte[] pldata=new byte[palLen];
			try {
				input.read(pldata);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
			pl.init(pldata);
		}

		try {
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}


}
