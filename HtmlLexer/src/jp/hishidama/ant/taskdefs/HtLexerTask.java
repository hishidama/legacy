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
 * HtHtmlLexer�^�X�N.
 *<p>
 * {@link HtLexer}���g�p�����AHTML�t�@�C�����̌����E�u�����s���^�X�N�B
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/ant/htlex.html"
 *         >�Ђ�����</a>
 * @since 2009.01.18
 * @version 2010.02.14
 */
public class HtLexerTask extends Task {

	protected File destDir;

	/**
	 * �u����f�B���N�g���[�ݒ�.
	 * <p>
	 * �u�����s���ꍇ�̒u����f�B���N�g���[�B�w�肵�Ȃ��ꍇ�͓ǂݍ��񂾃t�@�C���ɏ㏑�������B
	 * </p>
	 *
	 * @param dir
	 *            �f�B���N�g���[
	 */
	public void setTodir(File dir) {
		destDir = dir;
	}

	protected File backupDir;

	/**
	 * �o�b�N�A�b�v�f�B���N�g���[�ݒ�.
	 * <p>
	 * �u�����s���ꍇ�̒u���O�̃t�@�C�����o�b�N�A�b�v���Ă����f�B���N�g���[�B
	 * </p>
	 *
	 * @param dir
	 *            �f�B���N�g���[
	 * @since 2009.02.08
	 */
	public void setBackupDir(File dir) {
		backupDir = dir;
	}

	protected String prefix = "htlex";

	/**
	 * �v���p�e�B�[���̐ړ����ݒ�.
	 *
	 * @param s
	 *            �v���p�e�B�[���̐ړ���
	 * @since 2010.01.31
	 */
	public void setPrefix(String s) {
		prefix = s;
	}

	protected List<FileSet> fileset = new ArrayList<FileSet>();

	/**
	 * fileset�ǉ�.
	 *
	 * @param set
	 *            �t�@�C���Z�b�g�v�f
	 */
	public void addFileset(FileSet set) {
		fileset.add(set);
	}

	protected HtLexerConverter converter;

	/**
	 * �R���o�[�^�[�ݒ�.
	 *
	 * @param converter
	 *            �R���o�[�^�[�v�f
	 * @throws BuildException
	 */
	public void addConfigured(HtLexerConverter converter) {
		this.converter = converter;
	}

	/**
	 * �^�X�N���s.
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
