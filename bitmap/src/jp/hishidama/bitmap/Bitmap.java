/*
 * 作成日: 2004/03/14
 */
package jp.hishidama.bitmap;

/**
 * ビットマップインターフェース
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/bitmap.html">ひしだま</a>
 */
public interface Bitmap {
	/**
	 * 不正を表す色コード
	 */
	public static final int INVALID_COLOR = -1;

	/**
	 * 指定された座標が範囲内かどうか判定する
	 * 
	 * @param x
	 * @param y
	 * @return true:範囲内
	 */
	public boolean validPos(int x, int y);

	/**
	 * 指定された色コードが正当かどうか判定する
	 * 
	 * @param c
	 * @return true:正当
	 */
	public boolean validColor(int c);

	/**
	 * 指定された座標に色コードをセットする
	 * 
	 * @param x
	 * @param y
	 * @param c
	 *            色コード
	 * @return 成功した場合、true
	 */
	public boolean set(int x, int y, int c);

	/**
	 * 指定された座標に色コードをセットする<br>
	 * （座標や色コードの有効範囲チェック無し）
	 * 
	 * @param x
	 * @param y
	 * @param c
	 *            色コード
	 * @return 成功した場合、true
	 */
	public boolean set0(int x, int y, int c);

	/**
	 * 指定された座標にRGBをセットする
	 * 
	 * @param x
	 * @param y
	 * @param rgb
	 * @return 成功した場合、true
	 */
	public boolean setRGB(int x, int y, int rgb);

	/**
	 * 指定された座標の色コードを返す
	 * 
	 * @param x
	 * @param y
	 * @return 色コード
	 */
	public int get(int x, int y);

	/**
	 * 指定された座標の色コードを返す<br>
	 * （座標の有効範囲チェック無し）
	 * 
	 * @param x
	 * @param y
	 * @return 色コード
	 */
	public int get0(int x, int y);

	/**
	 * 指定された座標のRGBを返す
	 * 
	 * @param x
	 * @param y
	 * @return RGB
	 */
	public int getRGB(int x, int y);

	/**
	 * 色コードのRGBを返す
	 * 
	 * @param c
	 *            色コード
	 * @return RGB
	 */
	public int getRGB(int c);

	/**
	 * RGBに該当する色コードを返す
	 * 
	 * @param rgb
	 * @return 色コード
	 */
	public int getColor(int rgb);

	/**
	 * (r,g,b)に該当する色コードを返す
	 * 
	 * @param r
	 *            （0～255）
	 * @param g
	 *            （0～255）
	 * @param b
	 *            （0～255）
	 * @return 色コード
	 */
	public int getColor(int r, int g, int b);

	/**
	 * 塗り潰す
	 * 
	 * @param sx
	 *            始点X
	 * @param sy
	 *            始点Y
	 * @param nx
	 *            サイズX
	 * @param ny
	 *            サイズY
	 * @param c
	 *            色コード
	 * @return 成功した場合、true
	 */
	public boolean fill(int sx, int sy, int nx, int ny, int c);

	/**
	 * 線を引く
	 * 
	 * @param sx
	 *            始点X
	 * @param sy
	 *            始点Y
	 * @param nx
	 *            サイズX
	 * @param ny
	 *            サイズY
	 * @param c
	 *            色コード
	 * @return 成功した場合、true
	 */
	public boolean line(int sx, int sy, int nx, int ny, int c);

	/**
	 * 画像をコピーする
	 * 
	 * @param dx
	 *            コピー先座標X
	 * @param dy
	 *            コピー先座標Y
	 * @param nx
	 *            コピーサイズX
	 * @param ny
	 *            コピーサイズY
	 * @param src
	 *            コピー元のビットマップ
	 * @param sx
	 *            コピー元左上座標X
	 * @param sy
	 *            コピー元左上座標Y
	 */
	public void copy(int dx, int dy, int nx, int ny, Bitmap src, int sx, int sy);

	/**
	 * ファイルへ保存
	 * 
	 * @param pathName
	 *            パス名
	 * @param fileName
	 *            ファイル名
	 * @return 成功した場合、true
	 */
	public boolean save(String pathName, String fileName);

	/**
	 * @return ビットマップヘッダー情報
	 */
	BitmapInfoHeader getBitmapInfoHeader();

	/**
	 * @return ビットマップ色情報
	 */
	BitmapInfoColors getPalets();

	/**
	 * @return ピッチ
	 */
	int getPitch();

}
