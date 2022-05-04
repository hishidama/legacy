package jp.hishidama.win32.mshtml;

import jp.hishidama.robot.ie.IHTMLInputValueUtil;

/**
 * IHTMLInputFileElement�N���X.
 * <p>
 * input�^�O�itype="�t�@�C��"�j�������N���X�ł��B
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">�Ђ�����</a>
 * @since 2007.10.22
 */
public class IHTMLInputFileElement extends IHTMLInputElement {

	protected IHTMLInputFileElement(long dll_ptr) {
		super(dll_ptr);
	}

	/**
	 * @deprecated type="�t�@�C��"�ɂ����Ă͓����\�b�h���g���Ă��G���[�ɂȂ�Ȃ����A���ۂɂ͒l�̓Z�b�g����Ȃ��B<br> ��<a
	 *             target="hishidama"
	 *             href="http://www.ne.jp/asahi/hishidama/home/tech/web/html/input.html#file">�t�@�C���^�C�v�̃Z�L�����e�B�[��̐���</a>
	 * @see IHTMLInputValueUtil#sendValue(String)
	 */
	public void setValue(String value) {
		super.setValue(value);
	}

}
