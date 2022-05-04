/*
 * �쐬��: 2004/02/29
 */
package jp.hishidama.bitmap;

/**
 * 16�F�r�b�g�}�b�v
 * @author <a target="hishidama" href="http://www.ne.jp/asahi/hishidama/home/soft/java/bitmap.html">�Ђ�����</a>
 */
public abstract class Bitmap04 extends AbstractPaletteBitmap {

	/**
	 * �R���X�g���N�^
	 * @param nx	�r�b�g�}�b�v�̑傫��
	 * @param ny	�r�b�g�}�b�v�̑傫��
	 */
	public Bitmap04(int nx,int ny){
		super(nx,ny);
	}
	/**
	 * @param bi
	 * @param pals
	 */
	public Bitmap04(BitmapInfoHeader bi, BitmapInfoColors pals) {
		super(bi,pals);
	}
	/* (�� Javadoc)
	 * @see jp.hishidama.bitmap.Bitmap#getClassBitCount()
	 */
	protected short getClassBitCount() {
		return 4;
	}

	/* (�� Javadoc)
	 * @see bitmap.Bitmap#getDataBytes()
	 */
	protected boolean getDataBytes(int y,byte[] line) {
		for(int x=0;x<bi.getWidth();x++){
			if((x%2)==0){
				line[x/2]=(byte)(get0(x,y)<<4);
			}else{
				line[x/2]|=get0(x,y);
			}
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
			int c;
			if((x%2)==0){
				c=(line[x/2]>>4)&15;
				set0(x,y,c);
			}else{
				c=line[x/2]&15;
				set0(x,y,c);
			}
		}
		return true;
	}

}
