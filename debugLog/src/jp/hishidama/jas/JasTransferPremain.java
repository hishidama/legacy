package jp.hishidama.jas;

import java.io.IOException;
import java.io.InputStream;
import java.lang.instrument.Instrumentation;
import java.net.URL;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

/**
 * Javassist事前実行クラス.
 * <p>
 * Javassistを使ってクラスファイルを変換する事前準備を行うクラス。<br> →<a target="hishidama"
 * href="http://www.ne.jp/asahi/hishidama/home/soft/java/dbglogrm.html">使用例</a>
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/dbglogrm.html">ひしだま</a>
 * @since 2007.11.17
 */
public class JasTransferPremain {

	/**
	 * {@link JasTransfer}の具象クラスを指定する、システムプロパティーのキー
	 */
	public static final String TRANSFER_CLASS_KEY = "jas.transfer";

	/**
	 * {@link JasTransfer}の具象クラスを指定する、マニフェスト内のキー
	 */
	public static final String TRANSFER_CLASS_MF_KEY = "Jas-Transfer";

	/**
	 * 事前実行メソッド.
	 * <p>
	 * jarファイルのマニフェストに「Premain-Class」として当クラスを指定して<br>
	 * 実行時に「<code>-javaagent=<em>jarファイル</em></code>」を指定することにより、<br>
	 * 当メソッドがmainの実行前に呼ばれる。
	 * </p>
	 * <p>
	 * また、当メソッドでは{@link JasTransfer}の具象クラスを変換クラスとして使用する。<br>
	 * これは、システムプロパティーの「<code>-Djas.transfer=<em>具象クラス</em></code>」又はマニフェスト内の「<code>Jas-Transfer: <em>具象クラス</em></code>」によって指定するものとする。
	 * </p>
	 * 
	 * @param agentArgs
	 *            VM引数のjavaagentに指定された引数
	 * @param inst
	 *            インストゥルメンテーション
	 */
	@SuppressWarnings("unchecked")
	public static void premain(String agentArgs, Instrumentation inst) {

		// System.out.println("premain agentArgs=" + agentArgs);
		try {
			String name = getTransferClassName();
			if (name == null) {
				throw new RuntimeException("not found Transfer class: -D"
						+ TRANSFER_CLASS_KEY);
			}
			Class<JasTransfer> c = (Class<JasTransfer>) Class.forName(name);
			JasTransfer jt = c.newInstance();
			jt.init(agentArgs);
			inst.addTransformer(jt);
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected static String getTransferClassName() {
		String name = System.getProperty(TRANSFER_CLASS_KEY);
		if (name != null) {
			return name;
		}

		InputStream is = null;
		try {
			Class thisClass = JasTransferPremain.class;
			String tu = thisClass.getResource(
					thisClass.getSimpleName() + ".class").toExternalForm();
			String jar = tu.substring(0, tu.lastIndexOf("/jp"));
			URL url = new URL(jar + "/META-INF/MANIFEST.MF");
			is = url.openStream();
			Manifest mf = new Manifest(is);
			Attributes a = mf.getMainAttributes();
			return a.getValue(TRANSFER_CLASS_MF_KEY);
		} catch (IOException e) {
			return null;
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
				}
			}
		}
	}

}
