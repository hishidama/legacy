/*
 * 作成日: 2004/03/14
 */
package jp.hishidama.bitmap;

/**
 * メモリー上で扱う16bit色ビットマップ
 * @author <a target="hishidama" href="http://www.ne.jp/asahi/hishidama/home/soft/java/bitmap.html">ひしだま</a>
 */
public class MemoryBitmap16 extends Bitmap16 {

	private short data[];

	/**
	 * コンストラクタ
	 * @param nx	ビットマップの大きさ
	 * @param ny	ビットマップの大きさ
	 */
	public MemoryBitmap16(int nx,int ny){
		super(nx,ny);
		init();
	}
	/**
	 * @param bi
	 */
	public MemoryBitmap16(BitmapInfoHeader bi) {
		super(bi);
		init();
	}
	/* (非 Javadoc)
	 * @see jp.hishidama.bitmap.Bitmap#init0()
	 */
	protected void init() {
		data=new short[bi.getWidth()*bi.getHeight()];
	}

	/* (非 Javadoc)
	 * @see jp.hishidama.bitmap.Bitmap#set0(int, int, int)
	 */
	public boolean set0(int x, int y, int c) {
		data[x+y*bi.getWidth()]=(short) c;
		return true;
	}

	/* (非 Javadoc)
	 * @see jp.hishidama.bitmap.Bitmap#get0(int, int)
	 */
	public int get0(int x, int y) {
		return data[x+y*bi.getWidth()];
	}

}
