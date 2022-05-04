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
 * デバッグログ出力メソッド削除実験クラス.
 * <p>
 * Javassistを利用して、デバッグログ出力メソッドを削除したclassファイルを作成する。<br>→<a target="hishidama"
 * href="http://www.ne.jp/asahi/hishidama/home/soft/java/dbglogrm.html">使用例</a>
 * </p>
 * 
 * @see DebugRemoveEditor
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/index.html">ひしだま</a>
 * @since 2007.11.17
 */
public class DebugRemoveExperiment {

	/**
	 * メイン. <table border=1>
	 * <tr>
	 * <th>引数</th>
	 * <th>内容</th>
	 * </tr>
	 * <tr>
	 * <td>-level=FEWIVDT</td>
	 * <td>出力するレベルを文字の組み合わせで指定する。</td>
	 * </tr>
	 * <tr>
	 * <td>その他</td>
	 * <td>変換するclassファイルを指定する。</td>
	 * </tr>
	 * </table>
	 * 
	 * @param args
	 *            実行時引数
	 */
	public static void main(String[] args) {
		DebugRemoveExperiment me = new DebugRemoveExperiment();
		if (!me.init(args)) {
			System.out.println("デバッグログ出力メソッド削除実験 by ひしだま 2007.11.17");
			System.out
					.println("指定されたJavaのclassファイルに対し、デバッグログ出力メソッドを削除したclassファイルを出力します。");
			System.out
					.println("usage: java -cp ～/dbglogrm.jar;～/javassist.jar DebugRemoveExperiment [オプション…] classファイル");
			System.out.println("-level=FEWIVDT\t出力するレベルを文字の組み合わせで指定する");
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
		System.out.println("出力レベル：" + editor.getLevelString());

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
