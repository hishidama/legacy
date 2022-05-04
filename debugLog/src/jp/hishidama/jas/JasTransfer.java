package jp.hishidama.jas;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import javassist.ClassPool;
import javassist.CtClass;

/**
 * Javassist使用クラスファイル変換クラス.
 * <p>
 * {@link JasTransferPremain#premain(String, java.lang.instrument.Instrumentation)}から呼び出され、Javassistを使ってクラスファイルを変換する抽象クラス。
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/dbglogrm.html">ひしだま</a>
 * @since 2007.11.17
 */
public abstract class JasTransfer implements ClassFileTransformer {

	/**
	 * 初期化.
	 * <p>
	 * 変換クラスのインスタンス作成後に呼ばれる初期化メソッド。
	 * </p>
	 * 
	 * @param agentArgs
	 *            {@link JasTransferPremain#premain(String, java.lang.instrument.Instrumentation)}に渡された引数
	 */
	public abstract void init(String agentArgs);

	public byte[] transform(ClassLoader loader, String className,
			Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
			byte[] classfileBuffer) throws IllegalClassFormatException {
		try {
			// ここから先はJavassist
			ClassPool classPool = ClassPool.getDefault();
			InputStream is = new ByteArrayInputStream(classfileBuffer);
			CtClass ctClass = classPool.makeClass(is);

			transform(ctClass); // これがJavassitを利用した自作の変換クラス

			return ctClass.toBytecode();
		} catch (Exception e) {
			return classfileBuffer;
		}
	}

	/**
	 * 変換実行.
	 * 
	 * @param ctClass
	 *            クラス
	 */
	protected abstract void transform(CtClass ctClass);

}
