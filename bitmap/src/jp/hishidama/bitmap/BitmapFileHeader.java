/*
 * �쐬��: 2004/02/29
 */
package jp.hishidama.bitmap;

/**
 * �r�b�g�}�b�v�ۑ��p�t�@�C���̃w�b�_�[
 * @author <a target="hishidama" href="http://www.ne.jp/asahi/hishidama/home/soft/java/bitmap.html">�Ђ�����</a>
 */
public class BitmapFileHeader extends Bytes {
	
	/**
	 * �t�@�C���̃^�C�v�uBM�v�ŌŒ�
	 */
	private short	type;
	/**
	 * �t�@�C���S�̂̃T�C�Y
	 */
	private int	size;
	/**
	 * �\���P
	 */
	private short	reserved1;
	/**
	 * �\���Q
	 */
	private short	reserved2;
	/**
	 * �摜�f�[�^���t�@�C���̐擪���牽�o�C�g�ڂɂ��邩
	 */
	private int	offBits;

	/**
	 * �t�@�C���֕ۑ�����ׂ̃o�C�g���Ԃ�
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
	 * �t�@�C���̃o�C�g�񂩂珉��������
	 * @param data �o�C�g��
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
	 * ���N���X�̕ۑ��p�z��̃T�C�Y
	 */
	private static int bytesLength=-1;
	static{
		BitmapFileHeader dummy=new BitmapFileHeader();
		bytesLength=dummy.getBytes().length;
	}
	/**
	 * ���N���X�̕ۑ��p�z��̃T�C�Y��Ԃ�
	 */
	public int getBytesLength(){
		return bytesLength;
	}

	/**
	 * �R���X�g���N�^
	 */
	public BitmapFileHeader(){
		setType("BM");
	}


	/**
	 * �t�@�C���̐擪����摜�f�[�^�̈ʒu�܂ł̃I�t�Z�b�g��Ԃ�
	 * @return �I�t�Z�b�g�i�o�C�g�P�ʁj
	 */
	public int getOffBits() {
		return offBits;
	}

	/**
	 * �\��1��Ԃ�
	 * @return �\��1
	 */
	public short getReserved1() {
		return reserved1;
	}

	/**
	 * �\��2��Ԃ�
	 * @return �\��2
	 */
	public short getReserved2() {
		return reserved2;
	}

	/**
	 * �t�@�C���T�C�Y��Ԃ�
	 * @return �t�@�C���T�C�Y�i�o�C�g�P�ʁj
	 */
	public int getSize() {
		return size;
	}

	/**
	 * �t�@�C���̎��ʎq��Ԃ�<br>
	 * ��{�I�ɁuBM�v�Ƃ���������ɂȂ�l
	 * @return ���ʎq�i���l�j
	 */
	public short getType() {
		return type;
	}

	/**
	 * �t�@�C���̐擪����摜�f�[�^�̈ʒu�܂ł̃I�t�Z�b�g���Z�b�g����
	 * @param i �I�t�Z�b�g�i�o�C�g�P�ʁj
	 */
	public void setOffBits(int i) {
		offBits = i;
	}

	/**
	 * �\��1���Z�b�g����
	 * @param s �\��1
	 */
	public void setReserved1(short s) {
		reserved1 = s;
	}

	/**
	 * �\��2���Z�b�g����
	 * @param s �\��2
	 */
	public void setReserved2(short s) {
		reserved2 = s;
	}

	/**
	 * �t�@�C���T�C�Y���Z�b�g����
	 * @param i �t�@�C���T�C�Y�i�o�C�g�P�ʁj
	 */
	public void setSize(int i) {
		size = i;
	}

	/**
	 * �t�@�C���̎��ʎq���Z�b�g����
	 * @param s ���ʎq�i���l�j
	 */
	public void setType(short s) {
		type = s;
	}
	/**
	 * �t�@�C���̎��ʎq���Z�b�g����
	 * @param s ���ʎq�i������j
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
