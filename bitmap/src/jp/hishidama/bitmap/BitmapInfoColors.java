/*
 * �쐬��: 2004/03/14
 */
package jp.hishidama.bitmap;

/**
 * �r�b�g�}�b�v�p�p���b�g
 * @author <a target="hishidama" href="http://www.ne.jp/asahi/hishidama/home/soft/java/bitmap.html">�Ђ�����</a>
 */
public class BitmapInfoColors {

	/**
	 * �p���b�g(RGB)�̔z��
	 */
	protected int[] pals;

	/**
	 * �R���X�g���N�^
	 * @param cnt �p���b�g��
	 */
	public BitmapInfoColors(int cnt) {
		pals=new int[cnt];
	}
	/**
	 * �p���b�g�̌���Ԃ�
	 * @return �p���b�g��
	 */
	public int getCount(){
		return pals.length;
	}

	/**
	 * �F�R�[�h�ɑ΂���RGB��Ԃ�
	 * @param c �F�R�[�h
	 * @return RGB
	 */
	public int getRGB(int c) {
		return pals[c];
	}

	/**
	 * RGB�ɑ΂���F�R�[�h��Ԃ�<br>
	 * �i�p���b�g�̒�����߂����̂�T���ĕԂ��j
	 * @param rgb RGB
	 * @return �F�R�[�h
	 */
	public int getColor(int rgb) {
		for(int i=0;i<pals.length;i++){
			if(pals[i]==rgb) return i;
		}

		long cr=getRgbR(rgb);
		long cg=getRgbG(rgb);
		long cb=getRgbB(rgb);
		int n=-1;
		long near=256*256*3;
		for(int i=0;i<pals.length;i++){
			int d=pals[i];
			long lr=getRgbR(d)-cr;
			long lg=getRgbG(d)-cg;
			long lb=getRgbB(d)-cb;
			long l=lr*lr+lg*lg+lb*lb;
			if(l<near){
				n=i;
				l=near;
			}
		}
		return n;
	}

	/**
	 * �p���b�g��ݒ肷��
	 * @param c �F�R�[�h
	 * @param r �ԗv�f�i0�`255�j
	 * @param g �Ηv�f�i0�`255�j
	 * @param b �v�f�i0�`255�j
	 */
	public void setPalet(int c, int r, int g, int b) {
//		System.out.println(c+":"+r+","+g+","+b);
		pals[c]=getRGB(r,g,b);
	}

	/**
	 * �R���F����RGB��Ԃ�
	 * @param r �ԗv�f�i0�`255�j
	 * @param g �Ηv�f�i0�`255�j
	 * @param b �v�f�i0�`255�j
	 * @return RGB
	 */
	public static int getRGB(int r,int g,int b){
		return (r<<16)|(g<<8)|b;
	}
	/**
	 * RGB�̐ԗv�f��Ԃ�
	 * @param rgb RGB
	 * @return �ԗv�f�i0�`255�j
	 */
	public static int getRgbR(int rgb){
		return (rgb>>16)&255;
	}
	/**
	 * RGB�̗Ηv�f��Ԃ�
	 * @param rgb RGB
	 * @return �Ηv�f�i0�`255�j
	 */
	public static int getRgbG(int rgb){
		return (rgb>>8)&255;
	}
	/**
	 * RGB�̐v�f��Ԃ�
	 * @param rgb RGB
	 * @return �v�f�i0�`255�j
	 */
	public static int getRgbB(int rgb){
		return rgb&255;
	}

	/**
	 * �p���b�g�̃t�@�C���ւ̕ۑ��p�̃o�C�g���Ԃ�
	 * @return �o�C�g��
	 */
	public byte[] getPalBytes(){
		byte[] data=new byte[getBytesLength()];
		for(int i=0;i<pals.length;i++){
			int c=pals[i];
//			System.out.println(i+":"+c);
			data[i*4+0]=(byte)getRgbB(c);
			data[i*4+1]=(byte)getRgbG(c);
			data[i*4+2]=(byte)getRgbR(c);
			data[i*4+3]=0;
		}
		return data;
	}
	/**
	 * �p���b�g�̃t�@�C���ւ̕ۑ��p�̃o�C�g����Ԃ�
	 * @return �o�C�g��̒����i�o�C�g�P�ʁj
	 */
	public int getBytesLength(){
		return pals.length*4;
	}

	/**
	 * �p���b�g���t�@�C���̃o�C�g�񂩂珉��������
	 * @param data �o�C�g��
	 */
	public void init(byte[] data) {
		pals=new int[data.length/4];
		for(int i=0;i<pals.length;i++){
			int b=data[i*4+0]&255;
			int g=data[i*4+1]&255;
			int r=data[i*4+2]&255;
			pals[i]=getRGB(r,g,b);
		}
	}

}
