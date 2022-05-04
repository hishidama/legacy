/*
 * 作成日: 2004/03/14
 */
package jp.hishidama.bitmap;

/**
 * 24bit色ビットマップ
 * @author <a target="hishidama" href="http://www.ne.jp/asahi/hishidama/home/soft/java/bitmap.html">ひしだま</a>
 */
public abstract class Bitmap24 extends AbstractBitmap {

	/**
	 * @param nx
	 * @param ny
	 */
	public Bitmap24(int nx, int ny) {
		super(nx,ny);
	}

	/**
	 * @param bi
	 */
	public Bitmap24(BitmapInfoHeader bi) {
		super(bi);
	}

	/* (非 Javadoc)
	 * @see jp.hishidama.bitmap.Bitmap#getClassBitCount()
	 */
	protected short getClassBitCount() {
		return 24;
	}

	/* (非 Javadoc)
	 * @see jp.hishidama.bitmap.Bitmap#getDataBytes()
	 */
	protected boolean getDataBytes(int y,byte[] line) {
		for(int x=0;x<bi.getWidth();x++){
			int c=get0(x,y);
			line[x*3+0]=(byte)c;
			line[x*3+1]=(byte)(c>>8);
			line[x*3+2]=(byte)(c>>16);
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
			c = line[x*3+0]&255;
			c|=(line[x*3+1]&255)<<8;
			c|=(line[x*3+2]&255)<<16;
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
		return c;
	}

	public int getR(int c){
		return (c>>16)&255;
	}
	public int getG(int c){
		return (c>>8)&255;
	}
	public int getB(int c){
		return c&255;
	}
	public int getColor(int rgb) {
		return rgb;
	}
	public int getColor(int r,int g,int b){
		return (r<<16)|(g<<8)|b;
	}

}
