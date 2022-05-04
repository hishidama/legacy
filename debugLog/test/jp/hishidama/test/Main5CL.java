package jp.hishidama.test;

import jp.hishidama.debuglogrm.DebugRemoveClassLoader;
import jp.hishidama.test.debuglog.TestClass;

/**
 * ���s����VM�����ɓƎ��N���X���[�_�[���w�肵�A���̃N���X���[�_�[�̒���Javassist���g���ĕϊ���������B
 * <p>
 * <code>-Djava.system.class.loader=jp.hishidama.debuglog.DebugRemoveClassLoader</code>
 * </p>
 * 
 * @see DebugRemoveClassLoader
 */
public class Main5CL {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		System.out.println("��Main5CL�F" + Main5CL.class.getClassLoader());
		System.out.println("��Thread�F"
				+ Thread.currentThread().getContextClassLoader());

		TestClass tt = new TestClass();
		System.out.println("��TestClass�F" + tt.getClass().getClassLoader());

		tt.exec();
		tt.dump();

		// �폜�ΏۃN���X�̎w��ŃA�m�e�[�V��������肭���f�ł��Ȃ��̂�
		// �폜�ΏۂƂ��ĔF������Ȃ��B

		// ��DebugRemoveEditor#doit
		// for (Object a : anns) {
		// if (a instanceof DebugRemoveTarget) { ������
		// t = true;
		// break;
		// }
		// }

		// DebugRemoveEditor�́y�f�t�H���g���[�_�[�z�Ń��[�h�����B
		// �i������DebugRemoveTarget���y�f�t�H���g���[�_�[�z�Ń��[�h�����j
		// ���f���Ɏ擾�����a�́y���샍�[�_�[�z�ɂ��A�m�e�[�V�����Ȃ̂�
		// instanceof�ł͕ʃN���X�����ƂȂ�A�ΏۊO�ƂȂ�B

		// �d�����Ȃ��̂ŁA�y���샍�[�_�[�z����
		// ���̃A�m�e�[�V�����Ɋւ��Ă̓��[�h�ΏۊO�Ƃ��Ă���

	}
}
