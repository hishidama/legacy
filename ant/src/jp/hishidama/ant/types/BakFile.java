package jp.hishidama.ant.types;

import java.io.File;

import org.apache.tools.ant.types.DataType;

/**
 * バックアップディレクトリ情報データ.
 * 
 * @since 2007.08.11
 */
public class BakFile extends DataType {

	protected File newDir, oldDir, delDir;

	protected File destDir;

	public void setNewdir(File dir) {
		newDir = dir;
	}

	public void setOlddir(File dir) {
		oldDir = dir;
	}

	public void setDeldir(File dir) {
		delDir = dir;
	}

	public void setDestDir(File dir) {
		destDir = dir;
	}

	public File getNewdir() {
		return newDir;
	}

	public File getOlddir() {
		return oldDir;
	}

	public File getDeldir() {
		return delDir;
	}

	public File getDestDir() {
		return destDir;
	}
}
