package jp.hishidama.jas;

import java.security.ProtectionDomain;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.expr.ExprEditor;

/**
 * Javassistクラスローダー.
 * <p>
 * Javassistを利用してクラスファイルのバイトコード変換を行う為の抽象クラスローダー。
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/dbglogrm.html">ひしだま</a>
 * @since 2007.11.17
 */
public abstract class JasClassLoader extends ClassLoader {

	protected ExprEditor editor;

	/**
	 * コンストラクター.
	 * <p>
	 * 実行時のVM引数に「-Djava.system.class.loader=クラスローダー」を指定する場合、
	 * 「public」で「ClassLoaderを引数にとる」コンストラクターが必要。
	 * </p>
	 * 
	 * @param parent
	 */
	public JasClassLoader(ClassLoader parent) {
		super(parent);
		editor = createEditor();
	}

	/**
	 * Javassistの{@link ExprEditor}を作成する.
	 * 
	 * @return 変換クラス
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
			// Javassitを使った変換を行う
			ClassPool classPool = ClassPool.getDefault();
			CtClass cc = classPool.get(name);
			if (!cc.isFrozen()) {
				cc.instrument(editor);
			}
			ProtectionDomain pd = this.getClass().getProtectionDomain();
			Class c = cc.toClass(this, pd);
			// × Class<?> c = cc.toClass(getParent(), pd);

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
	 * Javassist対象判断.
	 * <p>
	 * 指定されたクラス名のクラスをJavassitの機能で生成するかどうかを判断する。
	 * </p>
	 * 
	 * @param name
	 *            クラス名
	 * @return Javassistから取得するとき、true
	 */
	protected abstract boolean isTarget(String name);
}
