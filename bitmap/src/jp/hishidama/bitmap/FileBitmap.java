/*
 * �쐬��: 2004/03/14
 */
package jp.hishidama.bitmap;

/**
 * �t�@�C����ň����r�b�g�}�b�v�̃C���^�[�t�F�[�X
 * @author <a target="hishidama" href="http://www.ne.jp/asahi/hishidama/home/soft/java/bitmap.html">�Ђ�����</a>
 */
public interface FileBitmap extends Bitmap {
	/**
	 * �t�@�C�����t���b�V������B
	 * @return ���������ꍇ�Atrue
	 */
	public boolean flush();
	/**
	 * �J���Ă���t�@�C�������B
	 * @return ���������ꍇ�Atrue
	 */
	public boolean close();
}
