/*
 * 作成日: 2004/02/29
 */
package jp.hishidama.bitmap;

/**
 * ビットマップ保存用ファイルのヘッダー
 * @author <a target="hishidama" href="http://www.ne.jp/asahi/hishidama/home/soft/java/bitmap.html">ひしだま</a>
 */
public class BitmapFileHeader extends Bytes {
	
	/**
	 * ファイルのタイプ「BM」で固定
	 */
	private short	type;
	/**
	 * ファイル全体のサイズ
	 */
	private int	size;
	/**
	 * 予備１
	 */
	private short	reserved1;
	/**
	 * 予備２
	 */
	private short	reserved2;
	/**
	 * 画像データがファイルの先頭から何バイト目にあるか
	 */
	private int	offBits;

	/**
	 * ファイルへ保存する為のバイト列を返す
	 */
	public byte[] getBytes(){
		byte[] data=null;
		for(int i=0;i<2;i++){
			int pos=0;
			pos=set(data,pos,type);
			pos=set(data,pos,size);
			pos=set(data,pos,reserved1);
			pos=set(data,pos,reserved2);
			pos=set(data,pos,offBits);
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
		type=get(data,rpos,type);
		size=get(data,rpos,size);
		reserved1=get(data,rpos,reserved1);
		reserved2=get(data,rpos,reserved2);
		offBits=get(data,rpos,offBits);
	}

	/**
	 * 当クラスの保存用配列のサイズ
	 */
	private static int bytesLength=-1;
	static{
		BitmapFileHeader dummy=new BitmapFileHeader();
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
	public BitmapFileHeader(){
		setType("BM");
	}


	/**
	 * ファイルの先頭から画像データの位置までのオフセットを返す
	 * @return オフセット（バイト単位）
	 */
	public int getOffBits() {
		return offBits;
	}

	/**
	 * 予備1を返す
	 * @return 予備1
	 */
	public short getReserved1() {
		return reserved1;
	}

	/**
	 * 予備2を返す
	 * @return 予備2
	 */
	public short getReserved2() {
		return reserved2;
	}

	/**
	 * ファイルサイズを返す
	 * @return ファイルサイズ（バイト単位）
	 */
	public int getSize() {
		return size;
	}

	/**
	 * ファイルの識別子を返す<br>
	 * 基本的に「BM」という文字列になる値
	 * @return 識別子（数値）
	 */
	public short getType() {
		return type;
	}

	/**
	 * ファイルの先頭から画像データの位置までのオフセットをセットする
	 * @param i オフセット（バイト単位）
	 */
	public void setOffBits(int i) {
		offBits = i;
	}

	/**
	 * 予備1をセットする
	 * @param s 予備1
	 */
	public void setReserved1(short s) {
		reserved1 = s;
	}

	/**
	 * 予備2をセットする
	 * @param s 予備2
	 */
	public void setReserved2(short s) {
		reserved2 = s;
	}

	/**
	 * ファイルサイズをセットする
	 * @param i ファイルサイズ（バイト単位）
	 */
	public void setSize(int i) {
		size = i;
	}

	/**
	 * ファイルの識別子をセットする
	 * @param s 識別子（数値）
	 */
	public void setType(short s) {
		type = s;
	}
	/**
	 * ファイルの識別子をセットする
	 * @param s 識別子（文字列）
	 */
	public void setType(String s) {
		byte[] data=s.getBytes();
		type=0;
		if(data.length>=1){
			type=data[0];
		}
		if(data.length>=2){
			type|=data[1]<<8;
		}
	}

}
