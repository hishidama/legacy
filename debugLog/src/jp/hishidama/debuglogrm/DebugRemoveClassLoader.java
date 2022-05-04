package jp.hishidama.debuglogrm;

import javassist.expr.ExprEditor;
import jp.hishidama.jas.JasClassLoader;

/**
 * �f�o�b�O���O�o�͂��폜����N���X���[�_�[.
 * <p>
 * ���N���X���[�_�[���g���ƁA�N���X�t�@�C���̃��[�h����{@link DebugRemoveEditor}���Ăяo���ăf�o�b�O���O�o�͂��폜����B<br>
 * �f�o�b�O���x���́u-Ddebuglog.level=�����v�Ŏw�肷��B<br> ��<a target="hishidama"
 * href="http://www.ne.jp/asahi/hishidama/home/soft/java/dbglogrm.html">�g�p��</a>
 * </p>
 * 
 * @see UseDebugLog
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/index.html">�Ђ�����</a>
 * @since 2007.11.17
 */
public class DebugRemoveClassLoader extends JasClassLoader {

	/**
	 * �R���X�g���N�^�[.
	 * <p>
	 * ���s����VM�����Ɂu-Djava.system.class.loader=jp.hishidama.debuglog.DebugRemoveClassLoader�v���w�肷��B<br>
	 * �������Ď��s����Ɠ��R���X�g���N�^�[���Ă΂��B
	 * </p>
	 * 
	 * @param parent
	 *            �e�N���X���[�_�[
	 */
	public DebugRemoveClassLoader(ClassLoader parent) {
		super(parent);
	}

	@Override
	protected ExprEditor createEditor() {
		// DebugRemoveEditor�ɑ΂��āA�o�͂���f�o�b�O���x����ݒ�
		String args = System.getProperty("debuglog.level");
		DebugRemoveEditor r = new DebugRemoveEditor(args);
		return r;
	}

	protected static final String ANNOTATION_PACKAGE = UseDebugLog.class
			.getPackage().getName();

	@Override
	protected boolean isTarget(String name) {
		if (name.startsWith(ANNOTATION_PACKAGE)) {
			// {@link DebugRemoveEditor}�ł́A�A�m�e�[�V�������g����
			// �f�o�b�O���O�o�͍폜�̑Ώۂ��ǂ����𔻒f���Ă���B
			// if (a instanceof DebugRemoveTarget) { �` }

			// ������{@link DebugRemoveEditor}�̓f�t�H���g�N���X���[�_�[�ɂ���ă��[�h�����̂�
			// ���̒��ŃR�[�f�B���O����Ă���DebugRemoveTarget���f�t�H���g�N���X���[�_�[�����ƂȂ�B

			// �����\�b�h���Ă΂ꂽ�Ƃ��A���̑ΏۃN���X��DebugRemoveTarget���g���Ă����
			// ��������[�h����ׂɓ��N���X���[�_�[���ēx�Ă΂��B
			// ��������𓖃N���X���[�_�[�Ń��[�h���Ă��܂���
			// �������ʂ̃N���X���[�_�[�ɂȂ��Ă��܂��A
			// ��L��instanceof�őΏۊO�Ɣ��f����Ă��܂�

			// ���������āA�p�b�P�[�W�������āADebugRemoveTarget�̃p�b�P�[�W��������
			// �f�t�H���g�N���X���[�_�[�ɔC���邱�Ƃɂ���B
			return false;
		}

		return true;
	}
}
