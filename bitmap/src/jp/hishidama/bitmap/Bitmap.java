/*
 * �쐬��: 2004/03/14
 */
package jp.hishidama.bitmap;

/**
 * �r�b�g�}�b�v�C���^�[�t�F�[�X
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/bitmap.html">�Ђ�����</a>
 */
public interface Bitmap {
	/**
	 * �s����\���F�R�[�h
	 */
	public static final int INVALID_COLOR = -1;

	/**
	 * �w�肳�ꂽ���W���͈͓����ǂ������肷��
	 * 
	 * @param x
	 * @param y
	 * @return true:�͈͓�
	 */
	public boolean validPos(int x, int y);

	/**
	 * �w�肳�ꂽ�F�R�[�h���������ǂ������肷��
	 * 
	 * @param c
	 * @return true:����
	 */
	public boolean validColor(int c);

	/**
	 * �w�肳�ꂽ���W�ɐF�R�[�h���Z�b�g����
	 * 
	 * @param x
	 * @param y
	 * @param c
	 *            �F�R�[�h
	 * @return ���������ꍇ�Atrue
	 */
	public boolean set(int x, int y, int c);

	/**
	 * �w�肳�ꂽ���W�ɐF�R�[�h���Z�b�g����<br>
	 * �i���W��F�R�[�h�̗L���͈̓`�F�b�N�����j
	 * 
	 * @param x
	 * @param y
	 * @param c
	 *            �F�R�[�h
	 * @return ���������ꍇ�Atrue
	 */
	public boolean set0(int x, int y, int c);

	/**
	 * �w�肳�ꂽ���W��RGB���Z�b�g����
	 * 
	 * @param x
	 * @param y
	 * @param rgb
	 * @return ���������ꍇ�Atrue
	 */
	public boolean setRGB(int x, int y, int rgb);

	/**
	 * �w�肳�ꂽ���W�̐F�R�[�h��Ԃ�
	 * 
	 * @param x
	 * @param y
	 * @return �F�R�[�h
	 */
	public int get(int x, int y);

	/**
	 * �w�肳�ꂽ���W�̐F�R�[�h��Ԃ�<br>
	 * �i���W�̗L���͈̓`�F�b�N�����j
	 * 
	 * @param x
	 * @param y
	 * @return �F�R�[�h
	 */
	public int get0(int x, int y);

	/**
	 * �w�肳�ꂽ���W��RGB��Ԃ�
	 * 
	 * @param x
	 * @param y
	 * @return RGB
	 */
	public int getRGB(int x, int y);

	/**
	 * �F�R�[�h��RGB��Ԃ�
	 * 
	 * @param c
	 *            �F�R�[�h
	 * @return RGB
	 */
	public int getRGB(int c);

	/**
	 * RGB�ɊY������F�R�[�h��Ԃ�
	 * 
	 * @param rgb
	 * @return �F�R�[�h
	 */
	public int getColor(int rgb);

	/**
	 * (r,g,b)�ɊY������F�R�[�h��Ԃ�
	 * 
	 * @param r
	 *            �i0�`255�j
	 * @param g
	 *            �i0�`255�j
	 * @param b
	 *            �i0�`255�j
	 * @return �F�R�[�h
	 */
	public int getColor(int r, int g, int b);

	/**
	 * �h��ׂ�
	 * 
	 * @param sx
	 *            �n�_X
	 * @param sy
	 *            �n�_Y
	 * @param nx
	 *            �T�C�YX
	 * @param ny
	 *            �T�C�YY
	 * @param c
	 *            �F�R�[�h
	 * @return ���������ꍇ�Atrue
	 */
	public boolean fill(int sx, int sy, int nx, int ny, int c);

	/**
	 * ��������
	 * 
	 * @param sx
	 *            �n�_X
	 * @param sy
	 *            �n�_Y
	 * @param nx
	 *            �T�C�YX
	 * @param ny
	 *            �T�C�YY
	 * @param c
	 *            �F�R�[�h
	 * @return ���������ꍇ�Atrue
	 */
	public boolean line(int sx, int sy, int nx, int ny, int c);

	/**
	 * �摜���R�s�[����
	 * 
	 * @param dx
	 *            �R�s�[����WX
	 * @param dy
	 *            �R�s�[����WY
	 * @param nx
	 *            �R�s�[�T�C�YX
	 * @param ny
	 *            �R�s�[�T�C�YY
	 * @param src
	 *            �R�s�[���̃r�b�g�}�b�v
	 * @param sx
	 *            �R�s�[��������WX
	 * @param sy
	 *            �R�s�[��������WY
	 */
	public void copy(int dx, int dy, int nx, int ny, Bitmap src, int sx, int sy);

	/**
	 * �t�@�C���֕ۑ�
	 * 
	 * @param pathName
	 *            �p�X��
	 * @param fileName
	 *            �t�@�C����
	 * @return ���������ꍇ�Atrue
	 */
	public boolean save(String pathName, String fileName);

	/**
	 * @return �r�b�g�}�b�v�w�b�_�[���
	 */
	BitmapInfoHeader getBitmapInfoHeader();

	/**
	 * @return �r�b�g�}�b�v�F���
	 */
	BitmapInfoColors getPalets();

	/**
	 * @return �s�b�`
	 */
	int getPitch();

}
