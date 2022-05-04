/*
 * �쐬��: 2004/03/14
 */
package jp.hishidama.bitmap;

/**
 * �������[��ň���256�F�r�b�g�}�b�v
 * @author <a target="hishidama" href="http://www.ne.jp/asahi/hishidama/home/soft/java/bitmap.html">�Ђ�����</a>
 */
public class MemoryBitmap08 extends Bitmap08 {

	byte data[];

	/**
	 * �R���X�g���N�^
	 * @param nx	�r�b�g�}�b�v�̑傫��
	 * @param ny	�r�b�g�}�b�v�̑傫��
	 */
	public MemoryBitmap08(int nx,int ny){
		super(nx,ny);
		init();
	}
	/**
	 * @param bi
	 * @param pl
	 */
	public MemoryBitmap08(BitmapInfoHeader bi, BitmapInfoColors pl) {
		super(bi,pl);
		init();
	}
	protected void init() {
		data=new byte[bi.getWidth()*bi.getHeight()];
	}

	/* (�� Javadoc)
	 * @see jp.hishidama.bitmap.Bitmap#set0(int, int, int)
	 */
	public boolean set0(int x, int y, int c) {
//		System.out.println("("+x+","+y+")"+c);
		data[x+y*bi.getWidth()]=(byte)c;
		return false;
	}

	/* (�� Javadoc)
	 * @see jp.hishidama.bitmap.Bitmap#get0(int, int)
	 */
	public int get0(int x, int y) {
		int c=data[x+y*bi.getWidth()]&255;
//		System.out.println("("+x+","+y+")"+c);
		return c;
	}

}
