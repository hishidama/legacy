/*
 * �쐬��: 2004/02/29
 */
package jp.hishidama.bitmap;

/**
 * �r�b�g�}�b�v���w�b�_�[
 * @author <a target="hishidama" href="http://www.ne.jp/asahi/hishidama/home/soft/java/bitmap.html">�Ђ�����</a>
 */
public class BitmapInfoHeader extends Bytes {

	/**
	 * ���k�`���iRGB�j
	 */
	public static final int COMPRESSION_RGB=0;
	/**
	 * ���k�`���iRLE8�j
	 */
	public static final int COMPRESSION_RLE8=1;
	/**
	 * ���k�`���iRLE4�j
	 */
	public static final int COMPRESSION_RLE4=2;
	/**
	 * ���k�`���iBITFIELDS�j
	 */
	public static final int COMPRESSION_BITFIELDS=3;

	/**
	 * ���N���X�̕ۑ��p�o�C�g��̃T�C�Y
	 */
	private int	size;
	/**
	 * �r�b�g�}�b�v�̃T�C�Y(X)
	 */
	private int	width;
	/**
	 * �r�b�g�}�b�v�̃T�C�Y(Y)
	 */
	private int	height;
	/**
	 * �v���[���i���1�j
	 */
	private short	planes;
	/**
	 * �F���i�r�b�g�P�ʁj
	 */
	private short	bitCount;
	/**
	 * ���k�`��
	 */
	private int	compression;
	/**
	 * �摜�f�[�^�̃T�C�Y
	 */
	private int	sizeImage;
	/**
	 * �����𑜓x�i0�ł����j
	 */
	private int	xPelsPerMeter;
	/**
	 * �����𑜓x�i0�ł����j
	 */
	private int	yPelsPerMeter;
	/**
	 * �p���b�g�̐��i�g��Ȃ��ꍇ�A0�j
	 */
	private int	colorUsed;
	/**
	 * �d�v�ȐF�̐��i0�F�S�Ă��d�v�j
	 */
	private int	colorImportant;

	/**
	 * �t�@�C���֕ۑ�����ׂ̃o�C�g���Ԃ�
	 */
	public byte[] getBytes(){
		byte[] data=null;
		for(int i=0;i<2;i++){
			int pos=0;
			pos=set(data,pos,size);
			pos=set(data,pos,width);
			pos=set(data,pos,height);
			pos=set(data,pos,planes);
			pos=set(data,pos,bitCount);
			pos=set(data,pos,compression);
			pos=set(data,pos,sizeImage);
			pos=set(data,pos,xPelsPerMeter);
			pos=set(data,pos,yPelsPerMeter);
			pos=set(data,pos,colorUsed);
			pos=set(data,pos,colorImportant);
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
		size=get(data,rpos,size);
		width=get(data,rpos,width);
		height=get(data,rpos,height);
		planes=get(data,rpos,planes);
		bitCount=get(data,rpos,bitCount);
		compression=get(data,rpos,compression);
		sizeImage=get(data,rpos,sizeImage);
		xPelsPerMeter=get(data,rpos,xPelsPerMeter);
		yPelsPerMeter=get(data,rpos,yPelsPerMeter);
		colorUsed=get(data,rpos,colorUsed);
		colorImportant=get(data,rpos,colorImportant);
	}

	/**
	 * ���N���X�̕ۑ��p�z��̃T�C�Y
	 */
	private static int bytesLength=-1;
	static{
		BitmapInfoHeader dummy=new BitmapInfoHeader();
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
	public BitmapInfoHeader(){
		size=getBytesLength();
		planes=1;
		compression=COMPRESSION_RGB;
		xPelsPerMeter=0;
		yPelsPerMeter=0;
	}


	/**
	 * �r�b�g�}�b�v�̐F����Ԃ�
	 * @return �F���i�r�b�g�P�ʁj
	 */
	public short getBitCount() {
		return bitCount;
	}

	/**
	 * �p���b�g�̂����A�d�v�ȐF�̐���Ԃ�
	 * @return �d�v�ȐF�̐�
	 */
	public int getColorImportant() {
		return colorImportant;
	}

	/**
	 * �p���b�g�̌���Ԃ�
	 * @return �p���b�g��
	 */
	public int getColorUsed() {
		return colorUsed;
	}

	/**
	 * ���k�`����Ԃ�
	 * @return ���k�`��
	 * @see jp.hishidama.bitmap.BitmapInfoHeader#COMPRESSION_RGB
	 */
	public int getCompression() {
		return compression;
	}

	/**
	 * �r�b�g�}�b�v�̍�����Ԃ�
	 * @return �r�b�g�}�b�v�T�C�Y(Y)
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * �v���[������Ԃ�
	 * @return �v���[����
	 */
	public short getPlanes() {
		return planes;
	}

	/**
	 * ���N���X�̕ۑ��p�o�C�g��̃T�C�Y��Ԃ�
	 * @return �o�C�g��̃T�C�Y�i�o�C�g�P�ʁj
	 */
	public int getSize() {
		return size;
	}

	/**
	 * �摜�f�[�^�̃T�C�Y��Ԃ�
	 * @return �摜�f�[�^�̃T�C�Y�i�o�C�g�P�ʁj
	 */
	public int getSizeImage() {
		return sizeImage;
	}

	/**
	 * �r�b�g�}�b�v�̕���Ԃ�
	 * @return �r�b�g�}�b�v�T�C�Y(X)
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * �����𑜓x��Ԃ�
	 * @return �����𑜓x
	 */
	public int getXPelsPerMeter() {
		return xPelsPerMeter;
	}

	/**
	 * �����𑜓x��Ԃ�
	 * @return �����𑜓x
	 */
	public int getYPelsPerMeter() {
		return yPelsPerMeter;
	}

	/**
	 * �r�b�g�}�b�v�̐F����ݒ肷��
	 * @param s �r�b�g�}�b�v�̐F���i�r�b�g�P�ʁj
	 */
	public void setBitCount(short s) {
		bitCount = s;
	}

	/**
	 * �p���b�g�̏d�v�ȐF�̐���ݒ肷��<br>
	 * �i0�́A�S�Ă��d�v�ȐF�ł��邱�Ƃ�\���j
	 * @param i �d�v�ȐF�̐�
	 */
	public void setColorImportant(int i) {
		colorImportant = i;
	}

	/**
	 * �g�p����p���b�g�̌���ݒ肷��
	 * @param i �p���b�g��
	 */
	public void setColorUsed(int i) {
		colorUsed = i;
	}

	/**
	 * ���k�`����ݒ肷��<br>
	 * �iRGB�����Ή����Ă��Ȃ��j
	 * @param i ���k�`��
	 * @see jp.hishidama.bitmap.BitmapInfoHeader#COMPRESSION_RGB
	 */
	public void setCompression(int i) {
		compression = i;
	}

	/**
	 * �r�b�g�}�b�v�̍�����ݒ肷��
	 * @param i �r�b�g�}�b�v�T�C�Y(Y)
	 */
	public void setHeight(int i) {
		height = i;
	}

	/**
	 * �v���[������ݒ肷��<br>
	 * �i���1��ݒ肷��K�v������j
	 * @param s �v���[����
	 */
	public void setPlanes(short s) {
		planes = s;
	}

	/**
	 * ���N���X�̕ۑ��p�o�C�g��̃T�C�Y��ݒ肷��
	 * @param i �o�C�g��̃T�C�Y�i�o�C�g�P�ʁj
	 */
	public void setSize(int i) {
		size = i;
	}

	/**
	 * �摜�f�[�^�̃T�C�Y��ݒ肷��
	 * @param i �摜�f�[�^�̃T�C�Y�i�o�C�g�P�ʁj
	 */
	public void setSizeImage(int i) {
		sizeImage = i;
	}

	/**
	 * �r�b�g�}�b�v�̕���ݒ肷��
	 * @param i �r�b�g�}�b�v�T�C�Y(X)
	 */
	public void setWidth(int i) {
		width = i;
	}

	/**
	 * �����𑜓x��ݒ肷��<br>
	 * �i��{�I��0�ł悢�j
	 * @param i �����𑜓x
	 */
	public void setXPelsPerMeter(int i) {
		xPelsPerMeter = i;
	}

	/**
	 * �����𑜓x��ݒ肷��<br>
	 * �i��{�I��0�ł悢�j
	 * @param i �����𑜓x
	 */
	public void setYPelsPerMeter(int i) {
		yPelsPerMeter = i;
	}


}
