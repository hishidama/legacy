/*
 * �쐬��: 2004/03/14
 */
package jp.hishidama.bitmap;

/**
 * �p���b�g���g�p����r�b�g�}�b�v�̃C���^�[�t�F�[�X
 * @author <a target="hishidama" href="http://www.ne.jp/asahi/hishidama/home/soft/java/bitmap.html">�Ђ�����</a>
 */
public interface PaletteBitmap extends Bitmap {
	/**
	 * �p���b�g��ݒ肷��
	 * @param c	�F�R�[�h
	 * @param r
	 * @param g
	 * @param b
	 */
	public void setPalet(int c,int r,int g,int b);

}
