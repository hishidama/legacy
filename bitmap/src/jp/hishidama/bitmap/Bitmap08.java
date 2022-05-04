/*
 * 作成日: 2004/03/14
 */
package jp.hishidama.bitmap;

/**
 * 256色ビットマップ
 * @author <a target="hishidama" href="http://www.ne.jp/asahi/hishidama/home/soft/java/bitmap.html">ひしだま</a>
 */
public abstract class Bitmap08 extends AbstractPaletteBitmap {

	/**
	 * コンストラクタ
	 * @param nx	ビットマップの大きさ
	 * @param ny	ビットマップの大きさ
	 */
	public Bitmap08(int nx,int ny){
		super(nx,ny);
	}
	/**
	 * @param bi
	 * @param pl
	 */
	public Bitmap08(BitmapInfoHeader bi, BitmapInfoColors pl) {
		super(bi,pl);
	}
	/* (非 Javadoc)
	 * @see jp.hishidama.bitmap.Bitmap#getClassBitCount()
	 */
	protected short getClassBitCount() {
		return 8;
	}

	/* (非 Javadoc)
	 * @see bitmap.Bitmap#getDataBytes()
	 */
	protected boolean getDataBytes(int y,byte[] line) {
		for(int x=0;x<bi.getWidth();x++){
			line[x]=(byte)get0(x,y);
		}
		return true;
	}
	/**
	 * バイト列から画像データをセットする
	 * @param y
	 * @param line
	 * @return
	 */
	boolean setDataBytes(int y,byte[] line){
		for(int x=0;x<bi.getWidth();x++){
			set0(x,y,line[x]);
		}
		return true;
	}

}
