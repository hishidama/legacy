package jp.hishidama.ant.taskdefs;

import java.io.*;
import java.util.*;

import jp.hishidama.ant.types.htlex.HtLexerConverter;
import jp.hishidama.ant.types.htlex.eval.HtLexerPropertyHelper;
import jp.hishidama.html.lexer.rule.HtLexer;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.FileSet;

/**
 * HtHtmlLexerタスク.
 *<p>
 * {@link HtLexer}を使用した、HTMLファイル内の検索・置換を行うタスク。
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/ant/htlex.html"
 *         >ひしだま</a>
 * @since 2009.01.18
 * @version 2010.02.14
 */
public class HtLexerTask extends Task {

	protected File destDir;

	/**
	 * 置換先ディレクトリー設定.
	 * <p>
	 * 置換を行う場合の置換先ディレクトリー。指定しない場合は読み込んだファイルに上書きされる。
	 * </p>
	 *
	 * @param dir
	 *            ディレクトリー
	 */
	public void setTodir(File dir) {
		destDir = dir;
	}

	protected File backupDir;

	/**
	 * バックアップディレクトリー設定.
	 * <p>
	 * 置換を行う場合の置換前のファイルをバックアップしておくディレクトリー。
	 * </p>
	 *
	 * @param dir
	 *            ディレクトリー
	 * @since 2009.02.08
	 */
	public void setBackupDir(File dir) {
		backupDir = dir;
	}

	protected String prefix = "htlex";

	/**
	 * プロパティー名の接頭辞設定.
	 *
	 * @param s
	 *            プロパティー名の接頭辞
	 * @since 2010.01.31
	 */
	public void setPrefix(String s) {
		prefix = s;
	}

	protected List<FileSet> fileset = new ArrayList<FileSet>();

	/**
	 * fileset追加.
	 *
	 * @param set
	 *            ファイルセット要素
	 */
	public void addFileset(FileSet set) {
		fileset.add(set);
	}

	protected HtLexerConverter converter;

	/**
	 * コンバーター設定.
	 *
	 * @param converter
	 *            コンバーター要素
	 * @throws BuildException
	 */
	public void addConfigured(HtLexerConverter converter) {
		this.converter = converter;
	}

	/**
	 * タスク実行.
	 */
	public void execute() throws BuildException {

		HtLexerPropertyHelper helper = HtLexerPropertyHelper.getInstance(
				getProject(), prefix);

		if (converter == null) {
			converter = new HtLexerConverter();
		}
		converter.initPropertyHelper(helper);
		converter.validate();

		for (int i = 0; i < fileset.size(); i++) {
			FileSet fs = (FileSet) fileset.get(i);

			DirectoryScanner ds = fs.getDirectoryScanner(getProject());
			String[] names = ds.getIncludedFiles();
			for (int j = 0; j < names.length; j++) {
				File f = new File(ds.getBasedir(), names[j]);
				File wf = null;
				if (destDir != null) {
					wf = new File(destDir, names[j]);
				}
				File bf = null;
				if (backupDir != null) {
					bf = new File(backupDir, names[j]);
				}

				converter.execute(f, wf, bf);
			}
		}
	}
}
