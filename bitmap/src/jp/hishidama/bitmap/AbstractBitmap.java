/*
 * 作成日: 2004/02/29
 */
package jp.hishidama.bitmap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * ビットマップのスーパークラス
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/bitmap.html">ひしだま</a>
 */
public abstract class AbstractBitmap implements Bitmap {
	/**
	 * ビットマップ情報
	 */
	protected BitmapInfoHeader bi;

	/**
	 * パレット
	 */
	protected BitmapInfoColors pals;

	/**
	 * @param nx
	 * @param ny
	 */
	public AbstractBitmap(int nx, int ny) {
		bi = new BitmapInfoHeader();
		bi.setWidth(nx);
		bi.setHeight(ny);
		bi.setBitCount(getClassBitCount());
	}

	/**
	 * @param bi
	 */
	public AbstractBitmap(BitmapInfoHeader bi) {
		this.bi = bi;
		bi.setBitCount(getClassBitCount());
	}

	/**
	 * サブクラスの色数（ビット単位）
	 */
	protected abstract short getClassBitCount();

	/**
	 * 指定された座標が範囲内かどうか判定する
	 * 
	 * @param x
	 * @param y
	 * @return true:範囲内
	 */
	public boolean validPos(int x, int y) {
		if (x < 0 || x >= bi.getWidth())
			return false;
		if (y < 0 || y >= bi.getHeight())
			return false;
		return true;
	}

	/**
	 * 指定された色コードが正当かどうか判定する
	 * 
	 * @param c
	 * @return true:正当
	 */
	public boolean validColor(int c) {
		return (0 <= c && c < (1 << bi.getBitCount()));
	}

	/**
	 * 指定された座標に色コードをセットする
	 * 
	 * @param x
	 * @param y
	 * @param c
	 *            色コード
	 * @return 成功した場合、true
	 */
	public boolean set(int x, int y, int c) {
		if (!validPos(x, y))
			return false;
		if (!validColor(c))
			return false;
		return set0(x, y, c);
	}

	/**
	 * 指定された座標の色コードを返す
	 * 
	 * @param x
	 * @param y
	 * @return 色コード
	 */
	public int get(int x, int y) {
		if (!validPos(x, y))
			return INVALID_COLOR;
		return get0(x, y);
	}

	/**
	 * 指定された座標にRGBをセットする
	 * 
	 * @param x
	 * @param y
	 * @param rgb
	 * @return 成功した場合、true
	 */
	public boolean setRGB(int x, int y, int rgb) {
		int c = getColor(rgb);
		return set(x, y, c);
	}

	/**
	 * 指定された座標のRGBを返す
	 * 
	 * @param x
	 * @param y
	 * @return RGB
	 */
	public int getRGB(int x, int y) {
		return getRGB(get(x, y));
	}

	/**
	 * 色コードのRGBを返す
	 * 
	 * @param c
	 *            色コード
	 * @return RGB
	 */
	public abstract int getRGB(int c);

	/**
	 * 色コードの赤要素を返す
	 * 
	 * @param c
	 *            色コード
	 * @return R（0～255）
	 */
	public int getR(int c) {
		return BitmapInfoColors.getRgbR(getRGB(c));
	}

	/**
	 * 色コードの緑要素を返す
	 * 
	 * @param c
	 *            色コード
	 * @return G（0～255）
	 */
	public int getG(int c) {
		return BitmapInfoColors.getRgbG(getRGB(c));
	}

	/**
	 * 色コードの青要素を返す
	 * 
	 * @param c
	 *            色コード
	 * @return B（0～255）
	 */
	public int getB(int c) {
		return BitmapInfoColors.getRgbB(getRGB(c));
	}

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
	public int getColor(int r, int g, int b) {
		return getColor(BitmapInfoColors.getRGB(r, g, b));
	}

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
	public boolean fill(int sx, int sy, int nx, int ny, int c) {
		if (nx < 0) {
			sx += nx + 1;
			nx = -nx;
		}
		if (ny < 0) {
			sy += ny + 1;
			ny = -ny;
		}
		for (int y = 0; y < ny; ny++) {
			for (int x = 0; x < nx; x++) {
				set(x, y, c);
			}
		}
		return true;
	}

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
	public boolean line(int sx, int sy, int nx, int ny, int c) {
		int ax = nx;
		if (ax < 0)
			ax = -ax;
		int ay = ny;
		if (ay < 0)
			ay = -ay;
		if (ax > ay) {
			return lineNX(sx, sy, nx, ny, c);
		} else {
			return lineNY(sx, sy, nx, ny, c);
		}
	}

	/**
	 * 横長の矩形内の線を引く
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
	private boolean lineNX(int sx, int sy, int nx, int ny, int c) {
		if (nx < 0) {
			sx += nx + 1;
			nx = -nx;
			sy += ny + (ny < 0 ? +1 : -1);
			ny = -ny;
		}
		for (int x = 0; x < nx; x++) {
			int y = ny * x / nx;
			set(x + sx, y + sy, c);
		}
		return true;
	}

	/**
	 * 縦長の矩形内の線を引く
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
	 * @return
	 */
	private boolean lineNY(int sx, int sy, int nx, int ny, int c) {
		if (ny < 0) {
			sx += nx + (nx < 0 ? +1 : -1);
			nx = -nx;
			sy += ny + 1;
			ny = -ny;
		}
		for (int y = 0; y < ny; y++) {
			int x = nx * y / ny;
			set(x + sx, y + sy, c);
		}
		return true;
	}

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
	public void copy(int dx, int dy, int nx, int ny, Bitmap src, int sx, int sy) {
		if (nx < 0) {
			sx += nx + 1;
			nx = -nx;
		}
		if (ny < 0) {
			sy += ny + 1;
			ny = -ny;
		}
		for (int y = 0; y < ny; y++) {
			for (int x = 0; x < nx; x++) {
				int c = src.getRGB(sx + x, sy + y);
				setRGB(dx + x, dy + y, c);
			}
		}
	}

	/**
	 * 当ビットマップのピッチを返す
	 * 
	 * @return ピッチ
	 */
	public int getPitch() {
		return calcPitch(bi.getBitCount(), bi.getWidth());
	}

	/**
	 * ピッチを計算する
	 * 
	 * @param bitCount
	 * @param width
	 * @return ピッチ
	 */
	public static int calcPitch(int bitCount, int width) {
		int pitch;
		if (bitCount < 8) {
			int n = 8 / bitCount;
			pitch = (width + n - 1) / n;
		} else {
			bitCount /= 8;
			pitch = width * bitCount;
		}
		return (pitch + 3) & ~3; // 4バイト境界に合わせる
	}

	/**
	 * ファイルへ保存
	 * 
	 * @param pathName
	 *            パス名
	 * @param fileName
	 *            ファイル名
	 */
	public boolean save(String pathName, String fileName) {
		File file = new File(pathName, fileName);
		FileOutputStream output;
		try {
			output = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}

		boolean ret = save(output);

		try {
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * ファイルへ保存する
	 * 
	 * @param output
	 * @return 成功した場合、true
	 */
	public boolean save(FileOutputStream output) {

		BitmapFileHeader fh = FileBitmapControl
				.createBitmapFileHeader(bi, null);
		bi.setSizeImage(fh.getSize() - fh.getOffBits());

		try {
			output.write(fh.getBytes());
			output.write(bi.getBytes());
			byte[] line = new byte[getPitch()];
			for (int y = bi.getHeight() - 1; y >= 0; y--) {
				getDataBytes(y, line);
				output.write(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 画像の保存用のバイト列を返す
	 * 
	 * @return 成功した場合、true
	 */
	protected abstract boolean getDataBytes(int y, byte[] line);

	/**
	 * バイト列から画像データをセットする
	 * 
	 * @param y
	 * @param line
	 * @return 成功した場合、true
	 */
	abstract boolean setDataBytes(int y, byte[] line);

	/**
	 * @return ビットマップヘッダー情報
	 */
	public BitmapInfoHeader getBitmapInfoHeader() {
		return bi;
	}

	/**
	 * @return パレット
	 */
	public BitmapInfoColors getPalets() {
		return pals;
	}

}
