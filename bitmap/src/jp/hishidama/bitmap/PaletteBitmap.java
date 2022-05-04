/*
 * 作成日: 2004/03/14
 */
package jp.hishidama.bitmap;

/**
 * パレットを使用するビットマップのインターフェース
 * @author <a target="hishidama" href="http://www.ne.jp/asahi/hishidama/home/soft/java/bitmap.html">ひしだま</a>
 */
public interface PaletteBitmap extends Bitmap {
	/**
	 * パレットを設定する
	 * @param c	色コード
	 * @param r
	 * @param g
	 * @param b
	 */
	public void setPalet(int c,int r,int g,int b);

}
