/*
 * 作成日: 2004/02/29
 */
package jp.hishidama.bitmap;

/**
 * ビットマップ情報ヘッダー
 * @author <a target="hishidama" href="http://www.ne.jp/asahi/hishidama/home/soft/java/bitmap.html">ひしだま</a>
 */
public class BitmapInfoHeader extends Bytes {

	/**
	 * 圧縮形式（RGB）
	 */
	public static final int COMPRESSION_RGB=0;
	/**
	 * 圧縮形式（RLE8）
	 */
	public static final int COMPRESSION_RLE8=1;
	/**
	 * 圧縮形式（RLE4）
	 */
	public static final int COMPRESSION_RLE4=2;
	/**
	 * 圧縮形式（BITFIELDS）
	 */
	public static final int COMPRESSION_BITFIELDS=3;

	/**
	 * 当クラスの保存用バイト列のサイズ
	 */
	private int	size;
	/**
	 * ビットマップのサイズ(X)
	 */
	private int	width;
	/**
	 * ビットマップのサイズ(Y)
	 */
	private int	height;
	/**
	 * プレーン（常に1）
	 */
	private short	planes;
	/**
	 * 色数（ビット単位）
	 */
	private short	bitCount;
	/**
	 * 圧縮形式
	 */
	private int	compression;
	/**
	 * 画像データのサイズ
	 */
	private int	sizeImage;
	/**
	 * 水平解像度（0でいい）
	 */
	private int	xPelsPerMeter;
	/**
	 * 垂直解像度（0でいい）
	 */
	private int	yPelsPerMeter;
	/**
	 * パレットの数（使わない場合、0）
	 */
	private int	colorUsed;
	/**
	 * 重要な色の数（0：全てが重要）
	 */
	private int	colorImportant;

	/**
	 * ファイルへ保存する為のバイト列を返す
	 */
	public byte[] getBytes(){
		byte[] data=null;
		for(int i=0;i<2;i++){
			int pos=0;
			pos=set(data,pos,size);
			pos=set(data,pos,width);
			pos=set(data,pos,height);
			pos=set(data,pos,planes);
			pos=set(data,pos,bitCount);
			pos=set(data,pos,compression);
			pos=set(data,pos,sizeImage);
			pos=set(data,pos,xPelsPerMeter);
			pos=set(data,pos,yPelsPerMeter);
			pos=set(data,pos,colorUsed);
			pos=set(data,pos,colorImportant);
			if(data==null) data=new byte[pos];
		}
		return data;
	}

	/**
	 * ファイルのバイト列から初期化する
	 * @param data バイト列
	 */
	public void init(byte[] data) {
		int pos=0;
		int[] rpos=new int[]{ pos };
		size=get(data,rpos,size);
		width=get(data,rpos,width);
		height=get(data,rpos,height);
		planes=get(data,rpos,planes);
		bitCount=get(data,rpos,bitCount);
		compression=get(data,rpos,compression);
		sizeImage=get(data,rpos,sizeImage);
		xPelsPerMeter=get(data,rpos,xPelsPerMeter);
		yPelsPerMeter=get(data,rpos,yPelsPerMeter);
		colorUsed=get(data,rpos,colorUsed);
		colorImportant=get(data,rpos,colorImportant);
	}

	/**
	 * 当クラスの保存用配列のサイズ
	 */
	private static int bytesLength=-1;
	static{
		BitmapInfoHeader dummy=new BitmapInfoHeader();
		bytesLength=dummy.getBytes().length;
	}
	/**
	 * 当クラスの保存用配列のサイズを返す
	 */
	public int getBytesLength(){
		return bytesLength;
	}

	/**
	 * コンストラクタ
	 */
	public BitmapInfoHeader(){
		size=getBytesLength();
		planes=1;
		compression=COMPRESSION_RGB;
		xPelsPerMeter=0;
		yPelsPerMeter=0;
	}


	/**
	 * ビットマップの色数を返す
	 * @return 色数（ビット単位）
	 */
	public short getBitCount() {
		return bitCount;
	}

	/**
	 * パレットのうち、重要な色の数を返す
	 * @return 重要な色の数
	 */
	public int getColorImportant() {
		return colorImportant;
	}

	/**
	 * パレットの個数を返す
	 * @return パレット数
	 */
	public int getColorUsed() {
		return colorUsed;
	}

	/**
	 * 圧縮形式を返す
	 * @return 圧縮形式
	 * @see jp.hishidama.bitmap.BitmapInfoHeader#COMPRESSION_RGB
	 */
	public int getCompression() {
		return compression;
	}

	/**
	 * ビットマップの高さを返す
	 * @return ビットマップサイズ(Y)
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * プレーン数を返す
	 * @return プレーン数
	 */
	public short getPlanes() {
		return planes;
	}

	/**
	 * 当クラスの保存用バイト列のサイズを返す
	 * @return バイト列のサイズ（バイト単位）
	 */
	public int getSize() {
		return size;
	}

	/**
	 * 画像データのサイズを返す
	 * @return 画像データのサイズ（バイト単位）
	 */
	public int getSizeImage() {
		return sizeImage;
	}

	/**
	 * ビットマップの幅を返す
	 * @return ビットマップサイズ(X)
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * 水平解像度を返す
	 * @return 水平解像度
	 */
	public int getXPelsPerMeter() {
		return xPelsPerMeter;
	}

	/**
	 * 垂直解像度を返す
	 * @return 垂直解像度
	 */
	public int getYPelsPerMeter() {
		return yPelsPerMeter;
	}

	/**
	 * ビットマップの色数を設定する
	 * @param s ビットマップの色数（ビット単位）
	 */
	public void setBitCount(short s) {
		bitCount = s;
	}

	/**
	 * パレットの重要な色の数を設定する<br>
	 * （0は、全てが重要な色であることを表す）
	 * @param i 重要な色の数
	 */
	public void setColorImportant(int i) {
		colorImportant = i;
	}

	/**
	 * 使用するパレットの個数を設定する
	 * @param i パレット数
	 */
	public void setColorUsed(int i) {
		colorUsed = i;
	}

	/**
	 * 圧縮形式を設定する<br>
	 * （RGBしか対応していない）
	 * @param i 圧縮形式
	 * @see jp.hishidama.bitmap.BitmapInfoHeader#COMPRESSION_RGB
	 */
	public void setCompression(int i) {
		compression = i;
	}

	/**
	 * ビットマップの高さを設定する
	 * @param i ビットマップサイズ(Y)
	 */
	public void setHeight(int i) {
		height = i;
	}

	/**
	 * プレーン数を設定する<br>
	 * （常に1を設定する必要がある）
	 * @param s プレーン数
	 */
	public void setPlanes(short s) {
		planes = s;
	}

	/**
	 * 当クラスの保存用バイト列のサイズを設定する
	 * @param i バイト列のサイズ（バイト単位）
	 */
	public void setSize(int i) {
		size = i;
	}

	/**
	 * 画像データのサイズを設定する
	 * @param i 画像データのサイズ（バイト単位）
	 */
	public void setSizeImage(int i) {
		sizeImage = i;
	}

	/**
	 * ビットマップの幅を設定する
	 * @param i ビットマップサイズ(X)
	 */
	public void setWidth(int i) {
		width = i;
	}

	/**
	 * 水平解像度を設定する<br>
	 * （基本的に0でよい）
	 * @param i 水平解像度
	 */
	public void setXPelsPerMeter(int i) {
		xPelsPerMeter = i;
	}

	/**
	 * 垂直解像度を設定する<br>
	 * （基本的に0でよい）
	 * @param i 垂直解像度
	 */
	public void setYPelsPerMeter(int i) {
		yPelsPerMeter = i;
	}


}
