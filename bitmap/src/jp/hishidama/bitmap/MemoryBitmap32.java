/*
 * �쐬��: 2004/02/29
 */
package jp.hishidama.bitmap;

/**
 * �������[��ň���32bit�F�r�b�g�}�b�v
 * @author <a target="hishidama" href="http://www.ne.jp/asahi/hishidama/home/soft/java/bitmap.html">�Ђ�����</a>
 */
public class MemoryBitmap32 extends Bitmap32 {

	protected int data[];

	/**
	 * �R���X�g���N�^
	 * @param nx	�r�b�g�}�b�v�̑傫��
	 * @param ny	�r�b�g�}�b�v�̑傫��
	 */
	public MemoryBitmap32(int nx,int ny){
		super(nx,ny);
		init();
	}
	/**
	 * @param bi
	 */
	public MemoryBitmap32(BitmapInfoHeader bi) {
		super(bi);
		init();
	}

	/* (�� Javadoc)
	 * @see jp.hishidama.bitmap.Bitmap#init0()
	 */
	protected void init() {
		data=new int[bi.getWidth()*bi.getHeight()];
	}

	/* (�� Javadoc)
	 * @see jp.hishidama.bitmap.Bitmap#set0(int, int, int)
	 */
	public boolean set0(int x, int y, int c) {
//		System.out.println("set("+x+","+"y"+")"+c);
		data[x+y*bi.getWidth()]=c;
		return true;
	}

	/* (�� Javadoc)
	 * @see jp.hishidama.bitmap.Bitmap#get0(int, int)
	 */
	public int get0(int x, int y) {
		return data[x+y*bi.getWidth()];
	}

}
