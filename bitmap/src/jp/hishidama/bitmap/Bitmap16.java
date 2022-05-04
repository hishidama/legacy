/*
 * �쐬��: 2004/03/14
 */
package jp.hishidama.bitmap;

/**
 * 16bit�F�r�b�g�}�b�v
 * @author <a target="hishidama" href="http://www.ne.jp/asahi/hishidama/home/soft/java/bitmap.html">�Ђ�����</a>
 */
public abstract class Bitmap16 extends AbstractBitmap {

	/**
	 * �R���X�g���N�^
	 * @param nx	�r�b�g�}�b�v�̑傫��
	 * @param ny	�r�b�g�}�b�v�̑傫��
	 */
	public Bitmap16(int nx,int ny){
		super(nx,ny);
	}

	/* (�� Javadoc)
	 * @see jp.hishidama.bitmap.Bitmap#getClassBitCount()
	 */
	protected short getClassBitCount() {
		return 16;
	}

	/**
	 * @param bi
	 */
	public Bitmap16(BitmapInfoHeader bi) {
		super(bi);
	}

	/* (�� Javadoc)
	 * @see jp.hishidama.bitmap.Bitmap#getDataBytes()
	 */
	protected boolean getDataBytes(int y,byte[] line) {
		for(int x=0;x<bi.getWidth();x++){
			int c=get0(x,y);
			line[x*2+0]=(byte)(c&255);
			line[x*2+1]=(byte)(c>>8);
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
			c = line[x*2+0]&255;
			c|=(line[x*2+1]&255)<<8;
			set0(x,y,c);
		}
		return true;
	}

	/**
	 * �F�R�[�h��RGB��Ԃ�
	 * @param c �F�R�[�h
	 * @return RGB
	 */
	public int getRGB(int c){
		int r=getR(c);
		int g=getG(c);
		int b=getB(c);
		return BitmapInfoColors.getRGB(r,g,b);
	}
	public int getR(int c){
		return ((c>>10)&31)*255/31;
	}
	public int getG(int c){
		return ((c>>5)&31)*255/31;
	}
	public int getB(int c){
		return (c&31)*255/31;
	}
	public int getColor(int rgb) {
		int r=BitmapInfoColors.getRgbR(rgb);
		int g=BitmapInfoColors.getRgbG(rgb);
		int b=BitmapInfoColors.getRgbB(rgb);
		return getColor(r,g,b);
	}
	public int getColor(int r,int g,int b){
		r*=31;r/=255;
		g*=31;g/=255;
		b*=31;b/=255;
		return (r<<10)|(g<<5)|b;
	}

}
