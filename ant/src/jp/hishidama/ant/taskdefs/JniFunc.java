package jp.hishidama.ant.taskdefs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.FileSet;

/**
 * JNIヘッダーファイル関数抽出.
 * <p>
 * javahによって生成されたヘッダーファイル内の関数を列挙します。
 * </p>
 * <p>
 * このファイルをC++のソースで配列変数の初期値として#includeしてコンパイル・リンクすることにより、未実装の関数がエラーになるので分かるという寸法です。<br>→<a
 * target="hishidama"
 * href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/ant/jnif.html">使用例</a>
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/ant.html">ひしだま</a>
 * @since 2007.10.07
 */
public class JniFunc extends Task {

	/** 出力ファイル名接頭辞 */
	public String PREFIX = "jni_func.";

	/** 出力ファイル名接尾辞 */
	public String SUFFIX = ".txt";

	protected File destDir = null;

	protected boolean force = false;

	/**
	 * build.xmlで指定されたFileSetの一覧
	 */
	protected List fss = new ArrayList();

	public void addFileset(FileSet set) {
		fss.add(set);
	}

	/**
	 * 接頭辞指定.
	 * <p>
	 * 出力するファイル名の接頭辞を指定する。
	 * </p>
	 * 
	 * @param pre
	 *            接頭辞
	 */
	public void setPrefix(String pre) {
		this.PREFIX = pre;
	}

	/**
	 * 接尾辞指定.
	 * <p>
	 * 出力するファイル名の接尾辞を指定する。
	 * </p>
	 * 
	 * @param suf
	 *            接尾辞
	 */
	public void setSuffix(String suf) {
		this.SUFFIX = suf;
	}

	/**
	 * 出力先ディレクトリ指定.
	 * 
	 * @param dir
	 *            ディレクトリ
	 */
	public void setDestdir(File dir) {
		this.destDir = dir;
	}

	/**
	 * 強制出力指定.
	 * <p>
	 * false（デフォルト）だと、元となるjavahによるヘッダーファイルと当タスクによって生成されるファイルのタイムスタンプを比較し、新しいときだけ作成する。<br>
	 * trueだと無関係に作成する。
	 * </p>
	 * 
	 * @param f
	 *            true：強制上書きする
	 */
	public void setForce(boolean f) {
		this.force = f;
	}

	public void execute() throws BuildException {
		for (int i = 0; i < fss.size(); i++) {
			FileSet fs = (FileSet) fss.get(i);
			DirectoryScanner ds = fs.getDirectoryScanner(getProject());
			String[] names = ds.getIncludedFiles();
			for (int j = 0; j < names.length; j++) {
				File f = new File(ds.getBasedir(), names[j]);
				execute(f);
			}
		}
	}

	/**
	 * ファイル処理
	 * 
	 * @param f
	 *            ファイル
	 * @throws BuildException
	 */
	protected void execute(File f) throws BuildException {
		File parent = (destDir != null) ? destDir : f.getParentFile();
		File wf = new File(parent, PREFIX + f.getName() + SUFFIX);

		if (!force && f.lastModified() < wf.lastModified()) {
			log("skip: " + f.getAbsolutePath(), Project.MSG_DEBUG);
			return;
		}
		log(f.getAbsolutePath(), Project.MSG_INFO);
		log(wf.getAbsolutePath(), Project.MSG_DEBUG);

		BufferedReader br = null;
		PrintStream ps = null;
		try {
			br = new BufferedReader(new FileReader(f));
			ps = new PrintStream(new FileOutputStream(wf));

			// ps.print("#include \"");
			// ps.print(f.getName());
			// ps.println("\"");

			// ps.print("void* ");
			// ps.print(f.getName().replace('.', '_'));
			// ps.println("[] = {");

			execute(br, ps);

			// ps.println("};");
		} catch (Exception e) {
			throw new BuildException(e);
		} finally {
			if (br != null)
				try {
					br.close();
				} catch (IOException e) {
				}
			if (ps != null)
				ps.close();
		}
	}

	protected final String KEYWORD = " JNICALL ";

	protected void execute(BufferedReader br, PrintStream ps) throws Exception {

		for (;;) {
			String line = br.readLine();
			if (line == null)
				break;
			int i = line.indexOf(KEYWORD);
			if (i > 0) {
				i += KEYWORD.length();
				ps.print("\t");
				ps.print(line.substring(i));
				ps.println(",");
			}
		}
	}
}
