package jp.hishidama.debuglogrm;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Copy;
import org.apache.tools.ant.types.FilterChain;
import org.apache.tools.ant.types.FilterSetCollection;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.types.Reference;
import org.apache.tools.ant.util.FileUtils;

/**
 * デバッグログ出力削除タスク.
 * <p>
 * Javassistを利用して、デバッグログ出力メソッドを削除したclassファイルを作成するantタスク。<br>→<a
 * target="hishidama"
 * href="http://www.ne.jp/asahi/hishidama/home/soft/java/dbglogrm.html">使用例</a>
 * </p>
 * 
 * @see DebugRemoveEditor
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/soft/index.html">ひしだま</a>
 * @since 2007.11.18
 */
public class DebugRemoveTask extends Copy {

	protected Path classpath;

	/**
	 * コンストラクター.
	 */
	public DebugRemoveTask() {
		initFileUtils();
	}

	protected void initFileUtils() {
		super.fileUtils = new DebugRemoveFileUtils();
	}

	/**
	 * @deprecated 当タスクでは無視されます。
	 */
	@Override
	public FilterChain createFilterChain() {
		return super.createFilterChain();
	}

	/**
	 * @deprecated 当タスクでは無視されます。
	 */
	@Override
	public void setFiltering(boolean filtering) {
		super.setFiltering(filtering);
	}

	/**
	 * @deprecated 当タスクでは無視されます。
	 */
	@Override
	public void setEncoding(String encoding) {
		super.setEncoding(encoding);
	}

	/**
	 * @deprecated 当タスクでは無視されます。
	 */
	@Override
	public void setOutputEncoding(String encoding) {
		super.setOutputEncoding(encoding);
	}

	/**
	 * クラスパス属性.
	 * <p>
	 * デバッグログクラス（{@link DebugLogWriteMethod}アノテーション付きのメソッドを定義しているクラス）のクラスパスを指定する。
	 * </p>
	 * 
	 * @param path
	 *            CLASSPATH
	 */
	public void setClasspath(Path path) {
		if (classpath == null)
			classpath = path;
		else
			classpath.append(path);
	}

	/**
	 * クラスパス取得.
	 * 
	 * @return CLASSPATH
	 */
	public Path getClasspath() {
		return classpath;
	}

	/**
	 * クラスパス（ネスト）タスク作成.
	 * 
	 * @return CLASSPATH
	 */
	public Path createClasspath() {
		if (classpath == null)
			classpath = new Path(getProject());
		return classpath.createPath();
	}

	/**
	 * クラスパス参照属性.
	 * 
	 * @param r
	 *            CLASSPATH
	 */
	public void setClasspathRef(Reference r) {
		createClasspath().setRefid(r);
	}

	/**
	 * デバッグ出力レベル属性.
	 * 
	 * @param level
	 *            出力レベル
	 * @see DebugRemoveEditor#setLevel(String)
	 */
	public void setLevel(String level) {
		((DebugRemoveFileUtils) fileUtils).setLevel(level);
	}

	@Override
	protected void validateAttributes() throws BuildException {
		super.validateAttributes();

		((DebugRemoveFileUtils) fileUtils).addClasspath(classpath);
	}

	protected class DebugRemoveFileUtils extends FileUtils {

		protected ClassPool classPool = ClassPool.getDefault();

		protected DebugRemoveEditor editor = new DebugRemoveEditor();

		public void setLevel(String level) {
			editor.setLevel(level);
		}

		public void addClasspath(Path path) {
			if (path == null)
				return;
			String[] sa = path.list();
			for (String s : sa) {
				try {
					classPool.appendClassPath(s);
					log("add classpath: " + s, Project.MSG_VERBOSE);
				} catch (NotFoundException e) {
					throw new BuildException(e);
				}
			}
		}

		@Override
		public void copyFile(File sourceFile, File destFile,
				FilterSetCollection filters, Vector filterChains,
				boolean overwrite, boolean preserveLastModified,
				String inputEncoding, String outputEncoding, Project project)
				throws IOException {
			if (overwrite || !destFile.exists()
					|| destFile.lastModified() < sourceFile.lastModified()) {
				if (destFile.exists() && destFile.isFile())
					destFile.delete();
				File parent = destFile.getParentFile();
				if (parent != null && !parent.exists())
					parent.mkdirs();

				FileInputStream in = null;
				DataOutputStream out = null;
				try {
					in = new FileInputStream(sourceFile);
					out = new DataOutputStream(new FileOutputStream(destFile));
					CtClass cc = classPool.makeClass(in);
					cc.instrument(editor);
					cc.toBytecode(out);
				} catch (CannotCompileException e) {
					throw new BuildException(e);
				} finally {
					close(out);
					close(in);
				}

				if (preserveLastModified)
					setFileLastModified(destFile, sourceFile.lastModified());
			}
		}
	}
}
