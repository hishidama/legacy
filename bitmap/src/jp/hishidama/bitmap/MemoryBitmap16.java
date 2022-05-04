/*
 * �쐬��: 2004/03/14
 */
package jp.hishidama.bitmap;

/**
 * �������[��ň���16bit�F�r�b�g�}�b�v
 * @author <a target="hishidama" href="http://www.ne.jp/asahi/hishidama/home/soft/java/bitmap.html">�Ђ�����</a>
 */
public class MemoryBitmap16 extends Bitmap16 {

	private short data[];

	/**
	 * �R���X�g���N�^
	 * @param nx	�r�b�g�}�b�v�̑傫��
	 * @param ny	�r�b�g�}�b�v�̑傫��
	 */
	public MemoryBitmap16(int nx,int ny){
		super(nx,ny);
		init();
	}
	/**
	 * @param bi
	 */
	public MemoryBitmap16(BitmapInfoHeader bi) {
		super(bi);
		init();
	}
	/* (�� Javadoc)
	 * @see jp.hishidama.bitmap.Bitmap#init0()
	 */
	protected void init() {
		data=new short[bi.getWidth()*bi.getHeight()];
	}

	/* (�� Javadoc)
	 * @see jp.hishidama.bitmap.Bitmap#set0(int, int, int)
	 */
	public boolean set0(int x, int y, int c) {
		data[x+y*bi.getWidth()]=(short) c;
		return true;
	}

	/* (�� Javadoc)
	 * @see jp.hishidama.bitmap.Bitmap#get0(int, int)
	 */
	public int get0(int x, int y) {
		return data[x+y*bi.getWidth()];
	}

}
