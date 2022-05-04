package jp.hishidama.jas;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import javassist.ClassPool;
import javassist.CtClass;

/**
 * Javassist�g�p�N���X�t�@�C���ϊ��N���X.
 * <p>
 * {@link JasTransferPremain#premain(String, java.lang.instrument.Instrumentation)}����Ăяo����AJavassist���g���ăN���X�t�@�C����ϊ����钊�ۃN���X�B
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/dbglogrm.html">�Ђ�����</a>
 * @since 2007.11.17
 */
public abstract class JasTransfer implements ClassFileTransformer {

	/**
	 * ������.
	 * <p>
	 * �ϊ��N���X�̃C���X�^���X�쐬��ɌĂ΂�鏉�������\�b�h�B
	 * </p>
	 * 
	 * @param agentArgs
	 *            {@link JasTransferPremain#premain(String, java.lang.instrument.Instrumentation)}�ɓn���ꂽ����
	 */
	public abstract void init(String agentArgs);

	public byte[] transform(ClassLoader loader, String className,
			Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
			byte[] classfileBuffer) throws IllegalClassFormatException {
		try {
			// ����������Javassist
			ClassPool classPool = ClassPool.getDefault();
			InputStream is = new ByteArrayInputStream(classfileBuffer);
			CtClass ctClass = classPool.makeClass(is);

			transform(ctClass); // ���ꂪJavassit�𗘗p��������̕ϊ��N���X

			return ctClass.toBytecode();
		} catch (Exception e) {
			return classfileBuffer;
		}
	}

	/**
	 * �ϊ����s.
	 * 
	 * @param ctClass
	 *            �N���X
	 */
	protected abstract void transform(CtClass ctClass);

}
