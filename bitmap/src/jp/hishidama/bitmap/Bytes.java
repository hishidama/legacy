/*
 * 作成日: 2004/02/29
 */
package jp.hishidama.bitmap;

/**
 * 保存時にバイト列に変換したい構造体用のスーパークラス
 * @author <a target="hishidama" href="http://www.ne.jp/asahi/hishidama/home/soft/java/bitmap.html">ひしだま</a>
 */
public abstract class Bytes {
	
	/**
	 * バイト列を返す
	 * @return バイト列
	 */
	public abstract byte[] getBytes();
	/**
	 * バイト列の長さを返す
	 * @return 長さ
	 */
	public abstract int getBytesLength();
	/**
	 * バイト列から初期化する
	 * @param data
	 */
	public abstract void init(byte[] data);

	/**
	 * short型をバイト列に変換する
	 * @param data
	 * @param pos
	 * @param var
	 * @return 次の位置
	 */
	public static int set(byte[] data,int pos,short var){
		if(data!=null){
			data[pos+0]=(byte)(var);
			data[pos+1]=(byte)(var>>8);
		}
		return pos+2;
	}

	/**
	 * int型をバイト列に変換する
	 * @param data
	 * @param pos
	 * @param var
	 * @return 次の位置
	 */
	public static int set(byte[] data,int pos,int var){
		if(data!=null){
			data[pos+0]=(byte)(var);
			data[pos+1]=(byte)(var>>8);
			data[pos+2]=(byte)(var>>16);
			data[pos+3]=(byte)(var>>24);
		}
		return pos+4;
	}

	/**
	 * バイト列からshort型を取得する
	 * @param data
	 * @param rpos
	 * @param var
	 * @return 数値
	 */
	public static short get(byte[] data,int[] rpos,short var){
		int pos=rpos[0];
		var =data[pos+0];
		var|=data[pos+1]<<8;
		rpos[0]=pos+2;
		return var;
	}

	/**
	 * バイト列からshort型を取得する
	 * @param data
	 * @param rpos
	 * @param var
	 * @return 数値
	 */
	public static int get(byte[] data,int[] rpos,int var){
		int pos=rpos[0];
		var=data[pos+0];
		var|=data[pos+1]<<8;
		var|=data[pos+2]<<16;
		var|=data[pos+3]<<24;
		rpos[0]=pos+4;
		return var;
	}
}
