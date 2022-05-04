/*
 * 作成日: 2004/03/14
 */
package jp.hishidama.bitmap;

/**
 * ファイル上で扱うビットマップのインターフェース
 * @author <a target="hishidama" href="http://www.ne.jp/asahi/hishidama/home/soft/java/bitmap.html">ひしだま</a>
 */
public interface FileBitmap extends Bitmap {
	/**
	 * ファイルをフラッシュする。
	 * @return 成功した場合、true
	 */
	public boolean flush();
	/**
	 * 開いているファイルを閉じる。
	 * @return 成功した場合、true
	 */
	public boolean close();
}
