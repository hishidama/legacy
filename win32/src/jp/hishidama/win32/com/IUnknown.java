package jp.hishidama.win32.com;

/**
 * IUnknown�N���X.
 * <p>
 * COM��IUnknown�C���^�[�t�F�[�X�A�Ƃ����ʒu�t���̂���̃N���X�ł��B
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/ierobot.html">�Ђ�����</a>
 * @since 2007.11.04
 */
public class IUnknown extends ComPtr {

	protected IUnknown(long dll_ptr) {
		super(dll_ptr);
	}

}
