package jp.hishidama.ant.tool;

import java.io.*;
import java.util.*;

import jp.hishidama.ant.types.BakFile;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.types.FilterSetCollection;

/**
 * ���O�o�̓t�@�C�����[�e�B���e�B�[.
 * <p>
 * �t�@�C����ύX�������Ƀt�@�C�����������o�͂���B
 * </p>
 * 
 * @since 2007.03.10
 * @version 2007.08.11
 */
public class LogFileUtils extends FileUtilsWrapper {

	protected PrintStream log;

	protected Map msg = new HashMap();

	protected boolean execSync;

	protected File newDir, oldDir, delDir;

	protected File destDir;

	/**
	 * ���ۂ̓���������s�����ǂ������w�肷��.
	 * <p>
	 * ���ۂ̃R�s�[��폜���s�����ǂ������w�肷��B
	 * </p>
	 * 
	 * @param b
	 *            ���ۂ̓�����s���Ƃ��Atrue
	 */
	public void setExecSync(boolean b) {
		execSync = b;
	}

	public void setBakFile(BakFile bf) {
		if (bf != null) {
			newDir = bf.getNewdir();
			oldDir = bf.getOlddir();
			delDir = bf.getDeldir();
			destDir = bf.getDestDir();
		}
	}

	public void openLog(File logfile, Map msg) throws IOException {
		if (logfile != null) {
			log = new PrintStream(new FileOutputStream(logfile));
		} else {
			log = System.out;
		}
		this.msg = msg;
	}

	public void log(String key, File sf, File df) throws IOException {
		String fmt = (String) msg.get(key);
		if (fmt == null) {
			key = key.substring(0, 2);
			fmt = (String) msg.get(key);
		}
		if (fmt != null) {
			log.println(formatToString(fmt, sf, df));
		}
	}

	public void closeLog() {
		if (log != null) {
			if (log == System.out) {
				System.out.flush();
			} else {
				close(log);
			}
			log = null;
		}
	}

	protected String formatToString(String format, File sf, File df) {
		String sname = (sf != null) ? sf.getAbsolutePath() : null;
		String dname = (df != null) ? df.getAbsolutePath() : null;
		StringBuffer sb = new StringBuffer(format.length()
				+ (sname != null ? sname.length() : 0)
				+ (dname != null ? dname.length() : 0));

		for (int i = 0; i < format.length(); i++) {
			char c = format.charAt(i);
			if (c != '%') {
				sb.append(c);
				continue;
			}
			if (++i >= format.length())
				break;
			c = format.charAt(i);
			switch (c) {
			case 'f':
			case 's':
				sb.append(sname);
				break;
			case 't':
			case 'd':
				sb.append(dname);
				break;
			case '%':
				sb.append('%');
				break;
			}
		}

		return sb.toString();
	}

	public void copyFile(File sourceFile, File destFile,
			FilterSetCollection filters, Vector filterChains,
			boolean overwrite, boolean preserveLastModified,
			String inputEncoding, String outputEncoding, Project project)
			throws IOException {
		boolean cp_new = false;
		boolean cp_old = false;

		String key;
		if (overwrite || !destFile.exists()
				|| destFile.lastModified() < sourceFile.lastModified()) {
			// �R�s�[����
			File parent = destFile.getParentFile();
			if (parent != null && !parent.exists()) {
				log("md", null, parent);
			}
			key = "cp";
			cp_new = true;
		} else {
			// �R�s�[���Ȃ�
			key = "no";
		}

		key += "_sf";
		if (destFile.exists()) {
			if (destFile.isFile()) {
				key += "_df";
			} else {
				key += "_dd";
			}
			cp_old = true;
		}
		log(key, sourceFile, destFile);

		if (cp_old && oldDir != null) {
			File f = replaceDir(destFile, destDir, oldDir);
			// System.out.println("old:" + destFile + "\n����" + f);
			super.copyFile(destFile, f, null, null, true, true, null, null,
					null);
		}
		if (cp_new && newDir != null) {
			File f = replaceDir(destFile, destDir, newDir);
			// System.out.println("new:" + sourceFile + "\n����" + f);
			super.copyFile(sourceFile, f, null, null, true, true, null, null,
					null);
		}

		if (execSync) {
			super.copyFile(sourceFile, destFile, filters, filterChains,
					overwrite, preserveLastModified, inputEncoding,
					outputEncoding, project);
		}
	}

	/**
	 * �f�B���N�g����������u������.
	 * 
	 * @param file
	 *            �u���Ώۃt�@�C��
	 * @param base
	 *            �u�����f�B���N�g����
	 * @param to
	 *            �u����f�B���N�g����
	 * @return �u�������t�@�C��
	 * @throws IOException
	 */
	protected File replaceDir(File file, File base, File to) throws IOException {
		String f = file.getCanonicalPath();
		String b = base.getCanonicalPath();
		if (f.startsWith(b)) {
			return new File(to, f.substring(b.length()));
		}
		throw new IOException("no match dir-name:" + f + " dir:" + base);
	}

	public void deleteFile(File f) {
		if (f != null) {
			String key = "rm";
			if (f.isFile()) {
				key += "_df";
			} else {
				key += "_dd";
			}
			try {
				log(key, null, f);
			} catch (IOException e) {
				throw new BuildException(e);
			}

			if (delDir != null && f.isFile()) {
				try {
					File t = replaceDir(f, destDir, delDir);
					// System.out.println("del:" + f + "\n����" + t);
					super.copyFile(f, t, null, null, true, true, null, null,
							null);
				} catch (IOException e) {
					throw new BuildException(e);
				}
			}
		}

		if (execSync)
			super.deleteFile(f);
	}

}
