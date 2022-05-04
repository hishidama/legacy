/*
 * 作成日: 2004/03/14
 */
package jp.hishidama.bitmap;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * パレットを使うビットマップのスーパークラス
 * @author <a target="hishidama" href="http://www.ne.jp/asahi/hishidama/home/soft/java/bitmap.html">ひしだま</a>
 */
public abstract class AbstractPaletteBitmap extends AbstractBitmap implements PaletteBitmap {

	AbstractPaletteBitmap(int nx,int ny){
		super(nx,ny);
		pals=new BitmapInfoColors(1<<getClassBitCount());
	}
	AbstractPaletteBitmap(BitmapInfoHeader bi,BitmapInfoColors pals){
		super(bi);
		this.pals=pals;
	}

	/**
	 * 色コードのRGBを返す
	 * @param c 色コード
	 * @return RGB
	 */
	public int getRGB(int c){
		return pals.getRGB(c);
	}

	/**
	 * RGBに該当する色コードを返す
	 * @param rgb
	 * @return 色コード
	 */
	public int getColor(int rgb){
		return pals.getColor(rgb);
	}

	/**
	 * パレットを設定する
	 * @param c	色コード
	 * @param r
	 * @param g
	 * @param b
	 */
	public void setPalet(int c,int r,int g,int b){
//		System.out.println(c+":"+r+","+g+","+b);
		pals.setPalet(c,r,g,b);
	}

	/**
	 * ファイルへ保存する
	 * @param output
	 * @return 成功した場合、true
	 */
	public boolean save(FileOutputStream output){

		BitmapFileHeader fh=FileBitmapControl.createBitmapFileHeader(bi,pals);
		bi.setSizeImage(fh.getSize()-fh.getOffBits());
		bi.setColorUsed(pals.getCount());

		try {
			output.write(fh.getBytes());
			output.write(bi.getBytes());
			output.write(pals.getPalBytes());	//パレットデータ
			byte[] line=new byte[getPitch()];
			for(int y=bi.getHeight()-1;y>=0;y--){
				getDataBytes(y,line);
				output.write(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
