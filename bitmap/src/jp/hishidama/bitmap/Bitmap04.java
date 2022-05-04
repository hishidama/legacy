/*
 * 作成日: 2004/02/29
 */
package jp.hishidama.bitmap;

/**
 * 16色ビットマップ
 * @author <a target="hishidama" href="http://www.ne.jp/asahi/hishidama/home/soft/java/bitmap.html">ひしだま</a>
 */
public abstract class Bitmap04 extends AbstractPaletteBitmap {

	/**
	 * コンストラクタ
	 * @param nx	ビットマップの大きさ
	 * @param ny	ビットマップの大きさ
	 */
	public Bitmap04(int nx,int ny){
		super(nx,ny);
	}
	/**
	 * @param bi
	 * @param pals
	 */
	public Bitmap04(BitmapInfoHeader bi, BitmapInfoColors pals) {
		super(bi,pals);
	}
	/* (非 Javadoc)
	 * @see jp.hishidama.bitmap.Bitmap#getClassBitCount()
	 */
	protected short getClassBitCount() {
		return 4;
	}

	/* (非 Javadoc)
	 * @see bitmap.Bitmap#getDataBytes()
	 */
	protected boolean getDataBytes(int y,byte[] line) {
		for(int x=0;x<bi.getWidth();x++){
			if((x%2)==0){
				line[x/2]=(byte)(get0(x,y)<<4);
			}else{
				line[x/2]|=get0(x,y);
			}
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
			int c;
			if((x%2)==0){
				c=(line[x/2]>>4)&15;
				set0(x,y,c);
			}else{
				c=line[x/2]&15;
				set0(x,y,c);
			}
		}
		return true;
	}

}
