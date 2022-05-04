/*
 * 作成日: 2004/03/14
 */
package jp.hishidama.bitmap;

/**
 * ビットマップ用パレット
 * @author <a target="hishidama" href="http://www.ne.jp/asahi/hishidama/home/soft/java/bitmap.html">ひしだま</a>
 */
public class BitmapInfoColors {

	/**
	 * パレット(RGB)の配列
	 */
	protected int[] pals;

	/**
	 * コンストラクタ
	 * @param cnt パレット数
	 */
	public BitmapInfoColors(int cnt) {
		pals=new int[cnt];
	}
	/**
	 * パレットの個数を返す
	 * @return パレット数
	 */
	public int getCount(){
		return pals.length;
	}

	/**
	 * 色コードに対するRGBを返す
	 * @param c 色コード
	 * @return RGB
	 */
	public int getRGB(int c) {
		return pals[c];
	}

	/**
	 * RGBに対する色コードを返す<br>
	 * （パレットの中から近いものを探して返す）
	 * @param rgb RGB
	 * @return 色コード
	 */
	public int getColor(int rgb) {
		for(int i=0;i<pals.length;i++){
			if(pals[i]==rgb) return i;
		}

		long cr=getRgbR(rgb);
		long cg=getRgbG(rgb);
		long cb=getRgbB(rgb);
		int n=-1;
		long near=256*256*3;
		for(int i=0;i<pals.length;i++){
			int d=pals[i];
			long lr=getRgbR(d)-cr;
			long lg=getRgbG(d)-cg;
			long lb=getRgbB(d)-cb;
			long l=lr*lr+lg*lg+lb*lb;
			if(l<near){
				n=i;
				l=near;
			}
		}
		return n;
	}

	/**
	 * パレットを設定する
	 * @param c 色コード
	 * @param r 赤要素（0～255）
	 * @param g 緑要素（0～255）
	 * @param b 青要素（0～255）
	 */
	public void setPalet(int c, int r, int g, int b) {
//		System.out.println(c+":"+r+","+g+","+b);
		pals[c]=getRGB(r,g,b);
	}

	/**
	 * ３原色からRGBを返す
	 * @param r 赤要素（0～255）
	 * @param g 緑要素（0～255）
	 * @param b 青要素（0～255）
	 * @return RGB
	 */
	public static int getRGB(int r,int g,int b){
		return (r<<16)|(g<<8)|b;
	}
	/**
	 * RGBの赤要素を返す
	 * @param rgb RGB
	 * @return 赤要素（0～255）
	 */
	public static int getRgbR(int rgb){
		return (rgb>>16)&255;
	}
	/**
	 * RGBの緑要素を返す
	 * @param rgb RGB
	 * @return 緑要素（0～255）
	 */
	public static int getRgbG(int rgb){
		return (rgb>>8)&255;
	}
	/**
	 * RGBの青要素を返す
	 * @param rgb RGB
	 * @return 青要素（0～255）
	 */
	public static int getRgbB(int rgb){
		return rgb&255;
	}

	/**
	 * パレットのファイルへの保存用のバイト列を返す
	 * @return バイト列
	 */
	public byte[] getPalBytes(){
		byte[] data=new byte[getBytesLength()];
		for(int i=0;i<pals.length;i++){
			int c=pals[i];
//			System.out.println(i+":"+c);
			data[i*4+0]=(byte)getRgbB(c);
			data[i*4+1]=(byte)getRgbG(c);
			data[i*4+2]=(byte)getRgbR(c);
			data[i*4+3]=0;
		}
		return data;
	}
	/**
	 * パレットのファイルへの保存用のバイト数を返す
	 * @return バイト列の長さ（バイト単位）
	 */
	public int getBytesLength(){
		return pals.length*4;
	}

	/**
	 * パレットをファイルのバイト列から初期化する
	 * @param data バイト列
	 */
	public void init(byte[] data) {
		pals=new int[data.length/4];
		for(int i=0;i<pals.length;i++){
			int b=data[i*4+0]&255;
			int g=data[i*4+1]&255;
			int r=data[i*4+2]&255;
			pals[i]=getRGB(r,g,b);
		}
	}

}
