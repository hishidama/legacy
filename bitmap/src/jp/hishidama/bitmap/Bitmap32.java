/*
 * 作成日: 2004/02/29
 */
package jp.hishidama.bitmap;

/**
 * 32bit色ビットマップ
 * @author <a target="hishidama" href="http://www.ne.jp/asahi/hishidama/home/soft/java/bitmap.html">ひしだま</a>
 */
public abstract class Bitmap32 extends Bitmap24 {

	/**
	 * コンストラクタ
	 * @param nx	ビットマップの大きさ
	 * @param ny	ビットマップの大きさ
	 */
	public Bitmap32(int nx,int ny){
		super(nx,ny);
	}
	/**
	 * @param bi
	 */
	public Bitmap32(BitmapInfoHeader bi) {
		super(bi);
	}
	/* (非 Javadoc)
	 * @see jp.hishidama.bitmap.Bitmap#getClassBitCount()
	 */
	protected short getClassBitCount() {
		return 32;
	}

	/**
	 * 指定された色コードが正当かどうか判定する
	 * @param c
	 * @return true:正当
	 */
	public boolean validColor(int c){
		return (0<=c && c<=0xffffff);
	}

	/* (非 Javadoc)
	 * @see bitmap.Bitmap#getDataBytes()
	 */
	protected boolean getDataBytes(int y,byte[] line) {
		for(int x=0;x<bi.getWidth();x++){
			int c=get0(x,y);
			line[+x*4+0]=(byte)c;
			line[+x*4+1]=(byte)(c>>8);
			line[+x*4+2]=(byte)(c>>16);
			line[+x*4+3]=0;
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
			c = line[x*4+0]&255;
			c|=(line[x*4+1]&255)<<8;
			c|=(line[x*4+2]&255)<<16;
			set0(x,y,c);
		}
		return true;
	}

}
