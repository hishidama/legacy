package jp.hishidama.ant.tool;

import java.io.File;

import org.apache.tools.ant.util.FileUtils;

/**
 * FileUtilsのラッパークラス.
 * 
 * @since 2007.03.10
 */
public class FileUtilsWrapper extends FileUtils {

	public void deleteFile(File f) {
		delete(f);
	}
}
