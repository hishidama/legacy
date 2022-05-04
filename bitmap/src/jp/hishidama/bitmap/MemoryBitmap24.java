/*
 * 作成日: 2004/03/14
 */
package jp.hishidama.bitmap;

/**
 * メモリー上で扱う24bit色ビットマップ
 * @author <a target="hishidama" href="http://www.ne.jp/asahi/hishidama/home/soft/java/bitmap.html">ひしだま</a>
 */
public class MemoryBitmap24 extends Bitmap24 {

	protected int data[];

	/**
	 * @param nx
	 * @param ny
	 */
	public MemoryBitmap24(int nx, int ny) {
		super(nx,ny);
		init();
	}
	/**
	 * @param bi
	 */
	public MemoryBitmap24(BitmapInfoHeader bi) {
		super(bi);
		init();
	}

	/* (非 Javadoc)
	 * @see jp.hishidama.bitmap.Bitmap#init0()
	 */
	protected void init() {
		data=new int[bi.getWidth()*bi.getHeight()];
	}

	/* (非 Javadoc)
	 * @see jp.hishidama.bitmap.Bitmap#set0(int, int, int)
	 */
	public boolean set0(int x, int y, int c) {
		data[x+y*bi.getWidth()]=c;
		return true;
	}

	/* (非 Javadoc)
	 * @see jp.hishidama.bitmap.Bitmap#get0(int, int)
	 */
	public int get0(int x, int y) {
		return data[x+y*bi.getWidth()];
	}

}
