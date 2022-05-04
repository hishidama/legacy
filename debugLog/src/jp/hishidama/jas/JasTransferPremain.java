package jp.hishidama.jas;

import java.io.IOException;
import java.io.InputStream;
import java.lang.instrument.Instrumentation;
import java.net.URL;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

/**
 * Javassist���O���s�N���X.
 * <p>
 * Javassist���g���ăN���X�t�@�C����ϊ����鎖�O�������s���N���X�B<br> ��<a target="hishidama"
 * href="http://www.ne.jp/asahi/hishidama/home/soft/java/dbglogrm.html">�g�p��</a>
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/java/dbglogrm.html">�Ђ�����</a>
 * @since 2007.11.17
 */
public class JasTransferPremain {

	/**
	 * {@link JasTransfer}�̋�ۃN���X���w�肷��A�V�X�e���v���p�e�B�[�̃L�[
	 */
	public static final String TRANSFER_CLASS_KEY = "jas.transfer";

	/**
	 * {@link JasTransfer}�̋�ۃN���X���w�肷��A�}�j�t�F�X�g���̃L�[
	 */
	public static final String TRANSFER_CLASS_MF_KEY = "Jas-Transfer";

	/**
	 * ���O���s���\�b�h.
	 * <p>
	 * jar�t�@�C���̃}�j�t�F�X�g�ɁuPremain-Class�v�Ƃ��ē��N���X���w�肵��<br>
	 * ���s���Ɂu<code>-javaagent=<em>jar�t�@�C��</em></code>�v���w�肷�邱�Ƃɂ��A<br>
	 * �����\�b�h��main�̎��s�O�ɌĂ΂��B
	 * </p>
	 * <p>
	 * �܂��A�����\�b�h�ł�{@link JasTransfer}�̋�ۃN���X��ϊ��N���X�Ƃ��Ďg�p����B<br>
	 * ����́A�V�X�e���v���p�e�B�[�́u<code>-Djas.transfer=<em>��ۃN���X</em></code>�v���̓}�j�t�F�X�g���́u<code>Jas-Transfer: <em>��ۃN���X</em></code>�v�ɂ���Ďw�肷����̂Ƃ���B
	 * </p>
	 * 
	 * @param agentArgs
	 *            VM������javaagent�Ɏw�肳�ꂽ����
	 * @param inst
	 *            �C���X�g�D�������e�[�V����
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
