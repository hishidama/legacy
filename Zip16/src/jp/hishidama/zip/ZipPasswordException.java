package jp.hishidama.zip;

import java.util.zip.ZipException;

/**
 * �p�X���[�h�s��v��O.
 *<p>
 * �p�X���[�h�iCRC�j�`�F�b�N�ň�v���Ȃ������ꍇ�ɓ���O���������܂��B
 * </p>
 *<p>
 * �p�X���[�h���s��v�ł��K����������O����������킯�ł͂���܂���B �u���b�N���s��v�ȂǑ��̗�O���o��\��������܂��B
 * �܂��A�p�X���[�h������Ă���̂ɗ�O�����������ɉ𓀏I�������Ƃ��Ă��A�f�[�^�̓��e�����k�O�ƈقȂ��Ă���\���������ł��B
 * </p>
 *
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/zip.html"
 *         >�Ђ�����</a>
 * @since 2008.12.21
 * @see ZipFile#getInputStream(ZipEntry)
 * @see ZipFile#setCheckCrc(boolean)
 */
public class ZipPasswordException extends ZipException {

	private static final long serialVersionUID = -7225423289925959589L;

	public ZipPasswordException(String s) {
		super(s);
	}
}
