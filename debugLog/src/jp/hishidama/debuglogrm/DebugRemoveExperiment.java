package jp.hishidama.debuglogrm;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.*;

import javassist.ClassPool;
import javassist.CtClass;

/**
 * �f�o�b�O���O�o�̓��\�b�h�폜�����N���X.
 * <p>
 * Javassist�𗘗p���āA�f�o�b�O���O�o�̓��\�b�h���폜����class�t�@�C�����쐬����B<br>��<a target="hishidama"
 * href="http://www.ne.jp/asahi/hishidama/home/soft/java/dbglogrm.html">�g�p��</a>
 * </p>
 * 
 * @see DebugRemoveEditor
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/index.html">�Ђ�����</a>
 * @since 2007.11.17
 */
public class DebugRemoveExperiment {

	/**
	 * ���C��. <table border=1>
	 * <tr>
	 * <th>����</th>
	 * <th>���e</th>
	 * </tr>
	 * <tr>
	 * <td>-level=FEWIVDT</td>
	 * <td>�o�͂��郌�x���𕶎��̑g�ݍ��킹�Ŏw�肷��B</td>
	 * </tr>
	 * <tr>
	 * <td>���̑�</td>
	 * <td>�ϊ�����class�t�@�C�����w�肷��B</td>
	 * </tr>
	 * </table>
	 * 
	 * @param args
	 *            ���s������
	 */
	public static void main(String[] args) {
		DebugRemoveExperiment me = new DebugRemoveExperiment();
		if (!me.init(args)) {
			System.out.println("�f�o�b�O���O�o�̓��\�b�h�폜���� by �Ђ����� 2007.11.17");
			System.out
					.println("�w�肳�ꂽJava��class�t�@�C���ɑ΂��A�f�o�b�O���O�o�̓��\�b�h���폜����class�t�@�C�����o�͂��܂��B");
			System.out
					.println("usage: java -cp �`/dbglogrm.jar;�`/javassist.jar DebugRemoveExperiment [�I�v�V�����c] class�t�@�C��");
			System.out.println("-level=FEWIVDT\t�o�͂��郌�x���𕶎��̑g�ݍ��킹�Ŏw�肷��");
			return;
		}
		me.execute();
	}

	protected String level = null;

	private List<String> list = new ArrayList<String>();

	protected boolean init(String[] args) {
		for (String a : args) {
			String al = a.toLowerCase();
			if (al.startsWith("-l")) {
				int n = a.lastIndexOf('=');
				if (n < 0) {
					level = null;
				} else {
					level = a.substring(n + 1);
				}
				continue;
			}
			if (a.startsWith("-")) {
				continue;
			}
			list.add(a);
		}
		return !list.isEmpty();
	}

	protected DebugRemoveEditor editor = new DebugRemoveEditor();

	protected void execute() {
		editor = new DebugRemoveEditor(level);
		System.out.println("�o�̓��x���F" + editor.getLevelString());

		for (String fname : list) {
			try {
				execute(fname);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	protected void execute(String input) throws Exception {
		File fi = new File(input);
		String name = fi.getName();
		int n = name.lastIndexOf('.');
		String fname = (n >= 0) ? name.substring(0, n) : name;
		File fo = new File(fi.getParent(), fname + ".dbgrm.class");
		System.out.println("in : " + fi);
		System.out.println("out: " + fo);

		ClassPool classPool = ClassPool.getDefault();

		InputStream is = null;
		DataOutputStream os = null;
		try {
			is = new FileInputStream(fi);
			CtClass cc = classPool.makeClass(is);
			cc.instrument(editor);
			os = new DataOutputStream(new FileOutputStream(fo));
			cc.toBytecode(os);
		} finally {
			if (is != null)
				try {
					is.close();
				} catch (Exception e) {
				}
			if (os != null)
				try {
					os.close();
				} catch (Exception e) {
				}
		}
	}
}
