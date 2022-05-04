/*
 * �쐬��: 2004/02/29
 */
package jp.hishidama.bitmap;

/**
 * 32bit�F�r�b�g�}�b�v
 * @author <a target="hishidama" href="http://www.ne.jp/asahi/hishidama/home/soft/java/bitmap.html">�Ђ�����</a>
 */
public abstract class Bitmap32 extends Bitmap24 {

	/**
	 * �R���X�g���N�^
	 * @param nx	�r�b�g�}�b�v�̑傫��
	 * @param ny	�r�b�g�}�b�v�̑傫��
	 */
	public Bitmap32(int nx,int ny){
		super(nx,ny);
	}
	/**
	 * @param bi
	 */
	public Bitmap32(BitmapInfoHeader bi) {
		super(bi);
	}
	/* (�� Javadoc)
	 * @see jp.hishidama.bitmap.Bitmap#getClassBitCount()
	 */
	protected short getClassBitCount() {
		return 32;
	}

	/**
	 * �w�肳�ꂽ�F�R�[�h���������ǂ������肷��
	 * @param c
	 * @return true:����
	 */
	public boolean validColor(int c){
		return (0<=c && c<=0xffffff);
	}

	/* (�� Javadoc)
	 * @see bitmap.Bitmap#getDataBytes()
	 */
	protected boolean getDataBytes(int y,byte[] line) {
		for(int x=0;x<bi.getWidth();x++){
			int c=get0(x,y);
			line[+x*4+0]=(byte)c;
			line[+x*4+1]=(byte)(c>>8);
			line[+x*4+2]=(byte)(c>>16);
			line[+x*4+3]=0;
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
		int c;
		for(int x=0;x<bi.getWidth();x++){
			c = line[x*4+0]&255;
			c|=(line[x*4+1]&255)<<8;
			c|=(line[x*4+2]&255)<<16;
			set0(x,y,c);
		}
		return true;
	}

}
