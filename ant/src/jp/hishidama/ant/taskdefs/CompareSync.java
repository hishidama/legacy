package jp.hishidama.ant.taskdefs;

import java.io.File;
import java.io.IOException;
import java.util.*;

import jp.hishidama.ant.taskdefs.condition.TextFilesMatch;
import jp.hishidama.ant.tool.FileUtilsWrapper;
import jp.hishidama.ant.tool.LogFileUtils;
import jp.hishidama.ant.types.BakFile;
import jp.hishidama.ant.types.LogFile;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.taskdefs.Copy;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.util.FileNameMapper;
import org.apache.tools.ant.util.IdentityMapper;

/**
 * ファイル内容比較Sync.
 * <p>
 * （タイムスタンプでなく）ファイルの内容が不一致の場合のみコピーするsync。<br>
 * ファイルの内容を比較するので動作は遅くなるが、比較方法に正規表現を指定すると 特定パターンはマッチしたものと見なされてコピーされなくなる。
 * </p>
 * <p>
 * 本来ならばSyncを継承して内部クラスのMyCopyを拡張し、doFileOperations()をオーバーライドするだけでほとんど事足りた。
 * しかしMyCopyのインスタンスである_copyがprivateフィールドだった為、ソースを全てコピーしなければならなかった…。
 * </p>
 *
 * @see org.apache.tools.ant.taskdefs.Sync
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/ant/compsync.html"
 *         >ひしだま</a>
 * @version 2008.11.03
 */
public class CompareSync extends Task {

	protected TextFilesMatch _fm;

	protected LogFile _lf;

	protected FileUtilsWrapper fu;

	protected BakFile bf;

	protected boolean execSync = true;

	public class MyCopy extends Copy {

		protected Hashtable _dest2src;

		protected boolean hasFileset = false;

		public MyCopy() {
			_dest2src = new Hashtable();
		}

		public void addFileset(FileSet set) {
			super.addFileset(set);
			hasFileset = true;
		}

		protected void buildMap(File fromDir, File toDir, String names[],
				FileNameMapper mapper, Hashtable map) {
			assertTrue("No mapper", mapper instanceof IdentityMapper);
			super.buildMap(fromDir, toDir, names, mapper, map);
			for (int i = 0; i < names.length; i++) {
				String name = names[i];
				File dest = new File(toDir, name);
				_dest2src.put(dest, fromDir);
			}
		}

		protected void doFileOperations() { // add(override)
			if (_fm == null)
				_fm = new TextFilesMatch();
			_fm.initCompareTarget();

			LogFileUtils lu = null;
			if (fu instanceof LogFileUtils) {
				lu = (LogFileUtils) fu;
			}

			List rmList = new ArrayList(fileCopyMap.size());

			for (Enumeration e = fileCopyMap.keys(); e.hasMoreElements();) {
				String fromFile = (String) e.nextElement();
				String toFiles[] = (String[]) fileCopyMap.get(fromFile);
				Vector v = new Vector(toFiles.length);
				for (int i = 0; i < toFiles.length; i++) {
					String toFile = toFiles[i];

					File f = new File(fromFile);
					File t = new File(toFile);
					_fm.setFile1(f);
					_fm.setFile2(t);
					if (_fm.eval()) {
						log("No copy " + f + " to " + t.getName(), verbosity);
						if (lu != null) {
							try {
								lu.log("eq_sf_df", f, t);
							} catch (IOException e1) {
								throw new BuildException(e1);
							}
						}
					} else {
						v.add(toFile);
					}
				}
				if (v.size() <= 0) {
					rmList.add(fromFile);
				} else if (v.size() != toFiles.length) {
					toFiles = new String[v.size()];
					v.copyInto(toFiles);
					fileCopyMap.put(fromFile, toFiles);
				}
			}

			for (int i = 0; i < rmList.size(); i++) {
				Object fromFile = rmList.get(i);
				fileCopyMap.remove(fromFile);
			}

			super.doFileOperations();
		}

		protected void doLogOperations() {

		}

		public File getToDir() {
			return destDir;
		}

		public boolean getIncludeEmptyDirs() {
			return includeEmpty;
		}

		public void setFileUtils(FileUtilsWrapper fu) { // add
			fileUtils = fu;
		}
	}

	protected MyCopy _copy;

	public void init() throws BuildException {
		_copy = new MyCopy();
		configureTask(_copy);
		_copy.setFiltering(false);
		_copy.setIncludeEmptyDirs(false);
		_copy.setPreserveLastModified(true);
		_copy.setOverwrite(true); // add
	}

	protected void configureTask(Task helper) {
		helper.setProject(getProject());
		helper.setTaskName(getTaskName());
		helper.setOwningTarget(getOwningTarget());
		helper.init();
	}

	public void execute() throws BuildException { // replace
		validateAttributes();

		LogFileUtils log;
		fu = log = new LogFileUtils();
		if (_lf != null) {
			try {
				log.openLog(_lf.getFile(), _lf.getMessageMap());
			} catch (IOException e) {
				throw new BuildException(e);
			}
		}
		_copy.setFileUtils(fu);

		if (bf != null) {
			bf.setDestDir(_copy.getToDir());
		}
		log.setBakFile(bf);
		log.setExecSync(execSync);

		try {
			execute_sync();
		} finally {
			log.closeLog();
		}
	}

	protected void execute_sync() throws BuildException {
		File toDir = _copy.getToDir();
		Hashtable allFiles = _copy._dest2src;
		boolean noRemovalNecessary = !toDir.exists() || toDir.list().length < 1;
		log("PASS#1: Copying files to " + toDir, 4);
		_copy.execute();
		if (noRemovalNecessary) {
			log("NO removing necessary in " + toDir, 4);
			return;
		}
		log("PASS#2: Removing orphan files from " + toDir, 4);
		int removedFileCount[] = removeOrphanFiles(allFiles, toDir);
		logRemovedCount(removedFileCount[0], "dangling director", "y", "ies");
		logRemovedCount(removedFileCount[1], "dangling file", "", "s");
		if (!_copy.getIncludeEmptyDirs()) {
			log("PASS#3: Removing empty directories from " + toDir, 4);
			int removedDirCount = removeEmptyDirectories(toDir, false);
			logRemovedCount(removedDirCount, "empty director", "y", "ies");
		}
	}

	private void logRemovedCount(int count, String prefix,
			String singularSuffix, String pluralSuffix) {
		File toDir = _copy.getToDir();
		String what = prefix != null ? prefix : "";
		what = what + (count >= 2 ? pluralSuffix : singularSuffix);
		if (count > 0)
			log("Removed " + count + " " + what + " from " + toDir, 2);
		else
			log("NO " + what + " to remove from " + toDir, 3);
	}

	private int[] removeOrphanFiles(Hashtable nonOrphans, File file) {
		int removedCount[] = { 0, 0, 0 };
		if (file.isDirectory()) {
			File children[] = file.listFiles();
			for (int i = 0; i < children.length; i++) {
				int temp[] = removeOrphanFiles(nonOrphans, children[i]);
				removedCount[0] += temp[0];
				removedCount[1] += temp[1];
				removedCount[2] += temp[2];
			}

			if (nonOrphans.get(file) == null && removedCount[2] == 0) {
				log("Removing orphan directory: " + file, 4);
				fu.deleteFile(file); // replace file.delete();
				removedCount[0]++;
			} else {
				removedCount[2] = 1;
			}
		} else if (nonOrphans.get(file) == null) {
			log("Removing orphan file: " + file, 4);
			fu.deleteFile(file); // replace file.delete();
			removedCount[1]++;
		} else {
			removedCount[2] = 1;
		}
		return removedCount;
	}

	private int removeEmptyDirectories(File dir, boolean removeIfEmpty) {
		int removedCount = 0;
		if (dir.isDirectory()) {
			File children[] = dir.listFiles();
			for (int i = 0; i < children.length; i++) {
				File file = children[i];
				if (file.isDirectory())
					removedCount += removeEmptyDirectories(file, true);
			}

			if (children.length > 0)
				children = dir.listFiles();
			if (children.length < 1 && removeIfEmpty) {
				log("Removing empty directory: " + dir, 4);
				fu.deleteFile(dir); // replace dir.delete();
				removedCount++;
			}
		}
		return removedCount;
	}

	public void setTodir(File destDir) {
		_copy.setTodir(destDir);
	}

	public void setVerbose(boolean verbose) {
		_copy.setVerbose(verbose);
	}

	public void setOverwrite(boolean overwrite) {
		_copy.setOverwrite(overwrite);
	}

	public void setIncludeEmptyDirs(boolean includeEmpty) {
		_copy.setIncludeEmptyDirs(includeEmpty);
	}

	public void setFailOnError(boolean failonerror) {
		_copy.setFailOnError(failonerror);
	}

	public void addFileset(FileSet set) {
		_copy.addFileset(set);
	}

	public void setGranularity(long granularity) {
		_copy.setGranularity(granularity);
	}

	private static void assertTrue(String message, boolean condition) {
		if (!condition)
			throw new BuildException("Assertion Error: " + message);
		else
			return;
	}

	/*
	 * add start
	 */

	public void setEncoding(String encoding) {
		_copy.setEncoding(encoding);
	}

	public void setOutputEncoding(String encoding) {
		_copy.setOutputEncoding(encoding);
	}

	protected void validateAttributes() throws BuildException {
		if (_copy.getToDir() == null) {
			throw new BuildException("todir must be set.");
		}
		if (!_copy.hasFileset) {
			throw new BuildException("Specify a fileset.");
		}
	}

	public void setExecsync(boolean t) {
		// System.out.println("setExecsync:" + t);
		execSync = t;
	}

	/**
	 * TextFilesMatchのサブクラスのタスクをセットする.
	 *
	 * @param comp
	 *            ファイル比較タスク
	 */
	public void addConfigured(TextFilesMatch comp) {
		_fm = comp;
	}

	public void addLog(LogFile logfile) {
		_lf = logfile;
	}

	public void addBackup(BakFile bakfile) {
		bf = bakfile;
	}

}
