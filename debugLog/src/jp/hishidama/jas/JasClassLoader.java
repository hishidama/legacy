package jp.hishidama.jas;

import java.security.ProtectionDomain;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.expr.ExprEditor;

/**
 * Javassist�N���X���[�_�[.
 * <p>
 * Javassist�𗘗p���ăN���X�t�@�C���̃o�C�g�R�[�h�ϊ����s���ׂ̒��ۃN���X���[�_�[�B
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/dbglogrm.html">�Ђ�����</a>
 * @since 2007.11.17
 */
public abstract class JasClassLoader extends ClassLoader {

	protected ExprEditor editor;

	/**
	 * �R���X�g���N�^�[.
	 * <p>
	 * ���s����VM�����Ɂu-Djava.system.class.loader=�N���X���[�_�[�v���w�肷��ꍇ�A
	 * �upublic�v�ŁuClassLoader�������ɂƂ�v�R���X�g���N�^�[���K�v�B
	 * </p>
	 * 
	 * @param parent
	 */
	public JasClassLoader(ClassLoader parent) {
		super(parent);
		editor = createEditor();
	}

	/**
	 * Javassist��{@link ExprEditor}���쐬����.
	 * 
	 * @return �ϊ��N���X
	 */
	protected abstract ExprEditor createEditor();

	@Override
	protected synchronized Class<?> loadClass(String name, boolean resolve)
			throws ClassNotFoundException {
		if (name.startsWith("java") || name.startsWith("sun.")) {
			return super.loadClass(name, resolve);
		}
		if (!isTarget(name)) {
			return super.loadClass(name, resolve);
		}

		try {
			// Javassit���g�����ϊ����s��
			ClassPool classPool = ClassPool.getDefault();
			CtClass cc = classPool.get(name);
			if (!cc.isFrozen()) {
				cc.instrument(editor);
			}
			ProtectionDomain pd = this.getClass().getProtectionDomain();
			Class c = cc.toClass(this, pd);
			// �~ Class<?> c = cc.toClass(getParent(), pd);

			if (resolve) {
				resolveClass(c);
			}
			return c;

		} catch (Exception e) {
			// e.printStackTrace();
			return super.loadClass(name, resolve);
		}
	}

	/**
	 * Javassist�Ώ۔��f.
	 * <p>
	 * �w�肳�ꂽ�N���X���̃N���X��Javassit�̋@�\�Ő������邩�ǂ����𔻒f����B
	 * </p>
	 * 
	 * @param name
	 *            �N���X��
	 * @return Javassist����擾����Ƃ��Atrue
	 */
	protected abstract boolean isTarget(String name);
}
