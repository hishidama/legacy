/*
 * �쐬��: 2004/03/14
 */
package jp.hishidama.bitmap;

/**
 * 256�F�r�b�g�}�b�v
 * @author <a target="hishidama" href="http://www.ne.jp/asahi/hishidama/home/soft/java/bitmap.html">�Ђ�����</a>
 */
public abstract class Bitmap08 extends AbstractPaletteBitmap {

	/**
	 * �R���X�g���N�^
	 * @param nx	�r�b�g�}�b�v�̑傫��
	 * @param ny	�r�b�g�}�b�v�̑傫��
	 */
	public Bitmap08(int nx,int ny){
		super(nx,ny);
	}
	/**
	 * @param bi
	 * @param pl
	 */
	public Bitmap08(BitmapInfoHeader bi, BitmapInfoColors pl) {
		super(bi,pl);
	}
	/* (�� Javadoc)
	 * @see jp.hishidama.bitmap.Bitmap#getClassBitCount()
	 */
	protected short getClassBitCount() {
		return 8;
	}

	/* (�� Javadoc)
	 * @see bitmap.Bitmap#getDataBytes()
	 */
	protected boolean getDataBytes(int y,byte[] line) {
		for(int x=0;x<bi.getWidth();x++){
			line[x]=(byte)get0(x,y);
		}
		return true;
	}
	/**
	 * �o�C�g�񂩂�摜�f�[�^���Z�b�g����
	 * @param y
	 * @param line
	 * @return
	 */
	boolean setDataBytes(int y,byte[] line){
		for(int x=0;x<bi.getWidth();x++){
			set0(x,y,line[x]);
		}
		return true;
	}

}
