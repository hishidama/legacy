package jp.hishidama.ant.taskdefs.condition;

import java.io.*;
import java.util.*;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.types.DataType;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.util.FileUtils;

/**
 * テキストファイル内容比較クラス.
 * <p>
 * FilesMatchに似せて作ってある。当初はFilesMatchを継承していたが、DataTypeを継承したくなったので やめた。
 * </p>
 * 
 * @author <a target="hishidama"
 *         href="http://www.ne.jp/asahi/hishidama/home/tech/soft/java/ant/compsync.html">ひしだま</a>
 */
public class TextFilesMatch extends DataType {

	protected File file1;

	protected File file2;

	protected String encoding1;

	protected String encoding2;

	protected Vector filesets;

	protected Set compTarget;

	public void setFile1(File file1) {
		this.file1 = file1;
	}

	public void setFile2(File file2) {
		this.file2 = file2;
	}

	public void setEncoding1(String enc1) {
		this.encoding1 = enc1;
	}

	public void setEncoding2(String enc2) {
		this.encoding2 = enc2;
	}

	public void addFileset(FileSet set) {
		if (filesets == null)
			filesets = new Vector();
		filesets.addElement(set);
	}

	public void initCompareTarget() {
		compTarget = null;
		if (filesets == null)
			return;
		for (int i = 0; i < filesets.size(); i++) {
			FileSet fs = (FileSet) filesets.elementAt(i);
			DirectoryScanner ds = null;
			try {
				ds = fs.getDirectoryScanner(getProject());
			} catch (BuildException e) {
				if (/* failonerror || */!e.getMessage()
						.endsWith(" not found."))
					throw e;
				log("Warning: " + e.getMessage());
				continue;
			}
			File fromDir = fs.getDir(getProject());
			String srcFiles[] = ds.getIncludedFiles();
			if (compTarget == null)
				compTarget = new HashSet(srcFiles.length);
			for (int j = 0; j < srcFiles.length; j++) {
				File f = new File(fromDir, srcFiles[j]);
				compTarget.add(f.getAbsolutePath());
			}
		}
	}

	public boolean eval() throws BuildException {
		if (compTarget != null) {
			if (!compTarget.contains(file1.getAbsolutePath()))
				return true;
		}
		if (!file1.exists() || !file2.exists())
			return file1.exists() == file2.exists();

		if (encoding1 == null)
			encoding1 = encoding2;
		if (encoding2 == null)
			encoding2 = encoding1;
		BufferedReader sr = null;
		BufferedReader dr = null;
		try {
			Reader sf = (encoding1 != null) ? new InputStreamReader(
					new FileInputStream(file1), encoding1) : new FileReader(
					file1);
			Reader df = (encoding2 != null) ? new InputStreamReader(
					new FileInputStream(file2), encoding2) : new FileReader(
					file2);
			sr = new BufferedReader(sf);
			dr = new BufferedReader(df);

			return contentEquals(sr, dr);
		} catch (IOException ioe) {
			throw new BuildException("when comparing files: "
					+ ioe.getMessage(), ioe);
		} finally {
			FileUtils.close(sr);
			FileUtils.close(dr);
		}
	}

	protected boolean contentEquals(BufferedReader sr, BufferedReader dr)
			throws IOException {
		for (;;) {
			String s = sr.readLine();
			String d = dr.readLine();
			if (s == null) {
				return d == null;
			}
			if (d == null)
				return false;
			if (lineEquals(s, d))
				continue;
			else
				return false;
		}
	}

	/**
	 * 行を比較する.
	 * 
	 * @param s
	 *            文字列（null以外）
	 * @param d
	 *            文字列（null以外）
	 * @return 等しいとき、true
	 */
	protected boolean lineEquals(String s, String d) {
		return s.equals(d);
	}
}
