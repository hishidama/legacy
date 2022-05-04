/*
 * 作成日: 2004/02/29
 */
package jp.hishidama.bitmap;

/**
 * メモリー上で扱う16色ビットマップ
 * @author <a target="hishidama" href="http://www.ne.jp/asahi/hishidama/home/soft/java/bitmap.html">ひしだま</a>
 */
public class MemoryBitmap04 extends Bitmap04 {
	
	byte data[];

	/**
	 * コンストラクタ
	 * @param nx	ビットマップの大きさ
	 * @param ny	ビットマップの大きさ
	 */
	public MemoryBitmap04(int nx,int ny){
		super(nx,ny);
		init();
	}
	public MemoryBitmap04(BitmapInfoHeader bi,BitmapInfoColors pals){
		super(bi,pals);
		init();
	}
	protected void init() {
		data=new byte[bi.getWidth()*bi.getHeight()];
	}

	/* (非 Javadoc)
	 * @see jp.hishidama.bitmap.Bitmap#set0(int, int, int)
	 */
	public boolean set0(int x, int y, int c) {
//		System.out.println("("+x+","+y+")"+c);
		data[x+y*bi.getWidth()]=(byte)c;
		return false;
	}

	/* (非 Javadoc)
	 * @see jp.hishidama.bitmap.Bitmap#get0(int, int)
	 */
	public int get0(int x, int y) {
		int c=data[x+y*bi.getWidth()]&255;
//		System.out.println("("+x+","+y+")"+c);
		return c;
	}

}
