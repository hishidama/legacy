/*
 * 作成日: 2004/03/14
 */
package jp.hishidama.bitmap;

/**
 * 16bit色ビットマップ
 * @author <a target="hishidama" href="http://www.ne.jp/asahi/hishidama/home/soft/java/bitmap.html">ひしだま</a>
 */
public abstract class Bitmap16 extends AbstractBitmap {

	/**
	 * コンストラクタ
	 * @param nx	ビットマップの大きさ
	 * @param ny	ビットマップの大きさ
	 */
	public Bitmap16(int nx,int ny){
		super(nx,ny);
	}

	/* (非 Javadoc)
	 * @see jp.hishidama.bitmap.Bitmap#getClassBitCount()
	 */
	protected short getClassBitCount() {
		return 16;
	}

	/**
	 * @param bi
	 */
	public Bitmap16(BitmapInfoHeader bi) {
		super(bi);
	}

	/* (非 Javadoc)
	 * @see jp.hishidama.bitmap.Bitmap#getDataBytes()
	 */
	protected boolean getDataBytes(int y,byte[] line) {
		for(int x=0;x<bi.getWidth();x++){
			int c=get0(x,y);
			line[x*2+0]=(byte)(c&255);
			line[x*2+1]=(byte)(c>>8);
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
		int c;
		for(int x=0;x<bi.getWidth();x++){
			c = line[x*2+0]&255;
			c|=(line[x*2+1]&255)<<8;
			set0(x,y,c);
		}
		return true;
	}

	/**
	 * 色コードのRGBを返す
	 * @param c 色コード
	 * @return RGB
	 */
	public int getRGB(int c){
		int r=getR(c);
		int g=getG(c);
		int b=getB(c);
		return BitmapInfoColors.getRGB(r,g,b);
	}
	public int getR(int c){
		return ((c>>10)&31)*255/31;
	}
	public int getG(int c){
		return ((c>>5)&31)*255/31;
	}
	public int getB(int c){
		return (c&31)*255/31;
	}
	public int getColor(int rgb) {
		int r=BitmapInfoColors.getRgbR(rgb);
		int g=BitmapInfoColors.getRgbG(rgb);
		int b=BitmapInfoColors.getRgbB(rgb);
		return getColor(r,g,b);
	}
	public int getColor(int r,int g,int b){
		r*=31;r/=255;
		g*=31;g/=255;
		b*=31;b/=255;
		return (r<<10)|(g<<5)|b;
	}

}
