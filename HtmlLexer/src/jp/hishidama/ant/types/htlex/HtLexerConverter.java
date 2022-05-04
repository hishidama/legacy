package jp.hishidama.ant.types.htlex;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

import org.apache.tools.ant.*;
import org.apache.tools.ant.types.*;
import org.apache.tools.ant.util.FileUtils;

import jp.hishidama.ant.types.htlex.eval.HtLexerEvalLog;
import jp.hishidama.ant.types.htlex.eval.HtLexerExpRuleFactory;
import jp.hishidama.ant.types.htlex.eval.HtLexerPropertyHelper;
import jp.hishidama.eval.Rule;
import jp.hishidama.html.lexer.rule.HtLexer;
import jp.hishidama.html.lexer.token.*;
import jp.hishidama.html.parser.elem.*;
import jp.hishidama.html.parser.rule.HtParser;
import jp.hishidama.html.parser.rule.HtParserManager;

/**
 * HtHtmlLexer�R���o�[�^�[.
 *<p>
 * {@link HtLexer}���g�p�����AHTML�t�@�C�����̌����E�u�����s���f�[�^�^�C�v�B
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/ant/htlex.html"
 *         >�Ђ�����</a>
 * @since 2009.01.18
 * @version 2010.02.06
 */
public class HtLexerConverter extends DataType {

	protected Charset encoding, outputEncoding;

	/**
	 * ���̓t�@�C�������R�[�h�ݒ�.
	 * <p>
	 * �ݒ肳��Ȃ��ꍇ��JavaVM�f�t�H���g�̕����R�[�h�ƂȂ�B
	 * </p>
	 *
	 * @param encoding
	 *            �����R�[�h
	 */
	public void setEncoding(String encoding) {
		this.encoding = Charset.forName(encoding);
	}

	/**
	 * �o�̓t�@�C�������R�[�h�ݒ�.
	 * <p>
	 * �ݒ肳��Ȃ��ꍇ�͓��̓t�@�C���Ɠ��������R�[�h�ƂȂ�B
	 * </p>
	 *
	 * @param encoding
	 *            �����R�[�h
	 */
	public void setOutputEncoding(String encoding) {
		this.outputEncoding = Charset.forName(encoding);
	}

	protected boolean force, nowrite;

	/**
	 * �����o�͎w��.
	 *
	 * @param b
	 *            true�̏ꍇ�A�ύX�������Ă��t�@�C�����o�͂���B
	 */
	public void setForce(boolean b) {
		force = b;
	}

	/**
	 * ��o�͎w��.
	 *
	 * @param b
	 *            true�̏ꍇ�A�ύX�������Ă��t�@�C�����o�͂��Ȃ��B
	 * @since 2009.02.08
	 */
	public void setNoWrite(boolean b) {
		nowrite = b;
	}

	protected int LogReadFileLevel = -1;
	protected int logMatchLevel = -1;
	protected int logUnmatchLevel = -1;
	protected int logWriteFileLevel = -1;
	protected int logConvertLevel = -1;

	/**
	 * ���O�o�͐ݒ�i�Ǎ��t�@�C���j.
	 * <p>
	 * ���̐ݒ������ƁA�ǂݍ��񂾃t�@�C���������O�o�͂���B
	 * </p>
	 *
	 * @param level
	 *            ���O���x��
	 */
	public void setLogReadFile(LogLevel level) {
		LogReadFileLevel = level.getLevel();
	}

	/**
	 * ���O�o�͐ݒ�i�}�b�`���j.
	 * <p>
	 * ���̐ݒ������ƁA�����Ƀ}�b�`�����ꍇ�Ƀ}�b�`���O���e�����O�o�͂���B
	 * </p>
	 *
	 * @param level
	 *            ���O���x��
	 */
	public void setLogMatch(LogLevel level) {
		logMatchLevel = level.getLevel();
	}

	/**
	 * ���O�o�͐ݒ�i�A���}�b�`���j.
	 * <p>
	 * ���̐ݒ������ƁA������1���}�b�`���Ȃ������ꍇ�Ƀt�@�C���������O�o�͂���B
	 * </p>
	 *
	 * @param level
	 *            ���O���x��
	 */
	public void setLogUnmatch(LogLevel level) {
		logUnmatchLevel = level.getLevel();
	}

	/**
	 * ���O�o�͐ݒ�i�u�����j.
	 * <p>
	 * ���̐ݒ������ƁA�u�����s�����ꍇ�ɒu�����e�����O�o�͂���B
	 * </p>
	 *
	 * @param level
	 *            ���O���x��
	 */
	public void setLogConvert(LogLevel level) {
		logConvertLevel = level.getLevel();
	}

	/**
	 * ���O�o�͐ݒ�i�o�̓t�@�C���j.
	 * <p>
	 * ���̐ݒ������ƁA�t�@�C���o�͂����t�@�C���������O�o�͂���B
	 * </p>
	 *
	 * @param level
	 *            ���O���x��
	 */
	public void setLogWriteFile(LogLevel level) {
		logWriteFileLevel = level.getLevel();
	}

	/**
	 * ���O�o�͐ݒ�i���Z���s���j.
	 * <p>
	 * ���̐ݒ������ƁA���Z���ɉ��Z���e�����O�o�͂���B
	 * </p>
	 *
	 * @param level
	 *            ���O���x��
	 * @since 2010.01.31
	 */
	public void setLogEval(LogLevel level) {
		Rule rule = HtLexerExpRuleFactory.getRule(getProject());
		HtLexerEvalLog log = (HtLexerEvalLog) rule.getDefaultEvalLog();
		log.setMsgLevel(level.getLevel());
	}

	protected boolean useParser = false;

	/**
	 * HtHtmlParser�g�p�L���ݒ�.
	 *<p>
	 * HtHtmlParser�ɂ��\����͌��ʂ��g�p����I�v�V������ʓr�w�肵�Ă���ꍇ�́A������true�ƂȂ�B
	 * </p>
	 *
	 * @param b
	 *            HtHtmlParser���g�p����ꍇ�Atrue
	 * @since 2009.02.17
	 */
	public void setUseParser(boolean b) {
		useParser = b;
	}

	protected int dumpParseNotFixLevel = -1;

	/**
	 * �f�o�b�O�_���v�i���m��^�O�j�ݒ�.
	 * <p>
	 * HtHtmlParser�ɂ��\����͂Ŗ��m��̃^�O���������炻����o�͂���B
	 * </p>
	 *
	 * @param level
	 *            ���O���x��
	 * @since 2009.02.15
	 */
	public void setDumpParseNotFix(LogLevel level) {
		dumpParseNotFixLevel = level.getLevel();
		setUseParser(true);
	}

	protected HtLexerPropertyHelper propertyHelper;

	/**
	 * �v���p�e�B�[�w���p�[�ݒ�.
	 *
	 * @param helper
	 *            �v���p�e�B�[�w���p�[
	 * @since 2010.01.23
	 */
	public void initPropertyHelper(HtLexerPropertyHelper helper) {
		propertyHelper = helper;
		propertyHelper.setConverter(this);
	}

	/**
	 * �v���p�e�B�[�w���p�[�擾.
	 *
	 * @return �v���p�e�B�[�w���p�[
	 * @since 2010.01.23
	 */
	public HtLexerPropertyHelper getPropertyHelper() {
		return propertyHelper;
	}

	/**
	 * �^�O�����ǉ�.
	 *
	 * @param tag
	 *            �^�O�^�C�v
	 */
	public void addConfigured(TagType tag) {
		EmptyFileCondType file = getEmptyFileCondType(tag.getLocation());
		file.addConfigured(tag);
	}

	/**
	 * �e�L�X�g�����ǉ�.
	 *
	 * @param text
	 *            �e�L�X�g�^�C�v
	 */
	public void addConfigured(TextType text) {
		EmptyFileCondType file = getEmptyFileCondType(text.getLocation());
		file.addConfigured(text);
	}

	protected final List<FileCondType> fileList = new ArrayList<FileCondType>();

	/**
	 * �t�@�C�������ǉ�.
	 *
	 * @param file
	 *            �t�@�C�������^�C�v
	 * @since 2010.02.06
	 */
	public void addConfigured(FileCondType file) {
		fileList.add(file);
	}

	protected EmptyFileCondType getEmptyFileCondType(Location location) {
		if (fileList.size() > 0) {
			FileCondType file = fileList.get(fileList.size() - 1);
			if (file instanceof EmptyFileCondType) {
				return (EmptyFileCondType) file;
			}
		}
		EmptyFileCondType file = new EmptyFileCondType(getProject(), location);
		fileList.add(file);
		return file;
	}

	/**
	 * �������s.
	 *
	 * @throws BuildException
	 *             �����G���[��
	 */
	public void validate() throws BuildException {
		for (FileCondType i : fileList) {
			i.validate(this);
		}
	}

	protected File rf, wf;

	/**
	 * ���O�o�́i�Ǎ��t�@�C���j.
	 *
	 * @param msg
	 *            ���b�Z�[�W
	 * @param level
	 *            ���O���x��
	 */
	public void logReadFile(String msg, int level) {
		if (level >= 0) {
			log(msg + rf.getAbsolutePath(), level);
		}
	}

	private boolean matched;

	/**
	 * ���O�o�́i�}�b�`���j.
	 *
	 * @param token
	 *            �g�[�N��
	 */
	public void logMatch(Token token) {
		if (logMatchLevel >= 0) {
			if (!matched) {
				logReadFile("match: ", logMatchLevel);
			}
			log(token.getLine() + "\t" + token.getText(), logMatchLevel);
		}
		matched = true;
	}

	/**
	 * ���O�o�́i�A���}�b�`���j.
	 */
	public void logUnmatch() {
		if (logUnmatchLevel >= 0) {
			logReadFile("unmatch: ", logUnmatchLevel);
		}
	}

	private boolean convert;

	/**
	 * ���O�o�́i�u�����j.
	 *
	 * @param msg
	 *            ���b�Z�[�W
	 * @param line
	 *            �s�ԍ�
	 * @param token
	 *            �g�[�N��
	 */
	public void logConvert(String msg, int line, Token token) {
		if (logConvertLevel >= 0) {
			if (!convert) {
				logReadFile("conv: ", logConvertLevel);
			}

			StringBuilder sb = new StringBuilder(32);
			sb.append(msg);
			sb.append(": ");
			sb.append(line);
			sb.append("\t");
			sb.append(token.getText());
			log(sb.toString(), logConvertLevel);
		}
		convert = true;
	}

	private boolean writeFile;

	/**
	 * ���O�o�́i�o�̓t�@�C���j.
	 *
	 * @param f
	 *            �t�@�C����
	 */
	public void logWriteFile(File f) {
		if (logWriteFileLevel >= 0) {
			if (!writeFile) {
				log("write: " + f.getAbsolutePath(), logWriteFileLevel);
			}
		}
		writeFile = true;
	}

	protected HtLexer lexer = createLexer();

	protected HtLexer createLexer() {
		return new HtLexer();
	}

	/**
	 * �w�肳�ꂽ������p��HtLexer��Ԃ��B
	 *<p>
	 * �C���X�^���X�����L���Ă���ׁA�d���g�p�͕s�B
	 * </p>
	 *
	 * @param s
	 *            ���ߑΏ�
	 * @return HtLexer
	 */
	public HtLexer getLexer(String s) {
		lexer.setTarget(s);
		return lexer;
	}

	/**
	 * �����{��.
	 *<p>
	 * HTML�t�@�C����ǂݍ��݁A�����񌟍���u�����s���B
	 * </p>
	 *
	 * @param rf
	 *            �Ǎ��t�@�C����
	 * @param wf
	 *            �o�̓t�@�C����
	 * @param bf
	 *            �o�b�N�A�b�v�p�t�@�C����
	 * @throws BuildException
	 */
	public void execute(File rf, File wf, File bf) throws BuildException {
		this.rf = rf;
		this.wf = wf;
		this.matched = false;
		this.convert = false;
		this.writeFile = false;

		ListToken tlist = null;
		HtListElement elist = null;
		boolean changed = false;
		for (FileCondType file : fileList) {
			if (!file.isConvert()) {
				continue;
			}
			if (tlist == null) {
				Reader r = createReader();

				tlist = createToken(r);
				elist = createElement(tlist);
			}
			if (elist != null) {
				changed |= file.convert(elist);
			} else {
				changed |= file.convert(tlist);
			}
		}
		if (elist != null) {
			dumpParseNotFix(elist);
		}

		if (!matched) {
			logUnmatch();
		}
		if (changed || force) {
			if (bf != null) {
				backup(rf, bf);
			}
			Writer w = createWriter();
			if (w != null) {
				try {
					if (elist != null) {
						elist.writeTo(w);
					} else {
						tlist.writeTo(w);
					}
				} catch (IOException e) {
					throw new BuildException(e, getLocation());
				} finally {
					try {
						w.close();
					} catch (IOException e) {
					}
				}
			}
		}
	}

	protected ListToken createToken(Reader r) {
		ListToken tlist;
		try {
			lexer.setTarget(r);
			tlist = lexer.parse();
		} catch (IOException e) {
			throw new BuildException(e, getLocation());
		} finally {
			try {
				lexer.close();
			} catch (IOException e) {
			}
		}
		tlist.calcLine(1);

		return tlist;
	}

	protected HtListElement createElement(ListToken tlist) {
		HtListElement elist = null;
		if (useParser) {
			HtParser parser = new HtParserManager().getDefaultParser();
			elist = parser.parse(tlist);
		}
		return elist;
	}

	/**
	 * �����Ώۃt�@�C�����擾.
	 *
	 * @return �Ǎ��t�@�C����
	 */
	public File getReadFile() {
		return rf;
	}

	protected FileUtils fu;

	protected void backup(File rf, File bf) {
		if (nowrite) {
			return;
		}
		if (fu == null) {
			fu = FileUtils.getFileUtils();
		}

		File parent = bf.getParentFile();
		if (!parent.exists()) {
			parent.mkdirs();
		}

		try {
			fu.copyFile(rf, bf, null, true, true);
		} catch (IOException e) {
			throw new BuildException(e, getLocation());
		}
	}

	protected Reader createReader() {
		logReadFile("read: ", LogReadFileLevel);
		try {
			if (encoding == null) {
				return new FileReader(rf);
			} else {
				return new InputStreamReader(new FileInputStream(rf), encoding);
			}
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw new BuildException(e, getLocation());
		}
	}

	protected Writer createWriter() {
		File f = (wf != null) ? wf : rf;
		logWriteFile(f);
		if (nowrite) {
			return null;
		}

		File parent = f.getParentFile();
		if (!parent.exists()) {
			parent.mkdirs();
		}

		Charset cs = outputEncoding;
		if (cs == null) {
			cs = encoding;
		}

		try {
			if (cs == null) {
				return new FileWriter(f);
			} else {
				return new OutputStreamWriter(new FileOutputStream(f), cs);
			}
		} catch (IOException e) {
			throw new BuildException(e, getLocation());
		}
	}

	/**
	 * HTML�ύX.
	 * <p>
	 * <strike>{@link jp.hishidama.ant.taskdefs.HtLexerTask}
	 * �ɂ����HTML���ǂݍ��܂ꂽ��ɌĂ΂��B<br>
	 * �iHtHtmlParser�ɂ���č\����͂���Ă��Ȃ��ꍇ�ɓ����\�b�h���Ă΂��j<br>
	 * �����̓��e��ύX����true��Ԃ��ƁA���̓��e���t�@�C���ɏo�͂���B</strike>
	 * </p>
	 * <p>
	 * <strike>�Ǝ��̕ϊ����s�������ꍇ�́A�����\�b�h���I�[�o�[���C�h����HTML�̕ϊ����W�b�N����������B</strike>
	 * </p>
	 *
	 * @param tlist
	 *            �ǂݍ��܂ꂽHTML
	 * @return tlist�̓��e��ύX�����ꍇ�Atrue
	 * @see #setUseParser(boolean)
	 * @deprecated �����\�b�h�̋@�\��{@link FileCondType}�Ɉڊǂ��ꂽ�B
	 */
	@Deprecated
	public boolean convert(ListToken tlist) {
		throw new UnsupportedOperationException("�����\�b�h�̋@�\��"
				+ FileCondType.class.getSimpleName() + "�Ɉڊǂ���܂���");
	}

	/**
	 * HTML�ύX.
	 * <p>
	 * <strike>{@link jp.hishidama.ant.taskdefs.HtLexerTask}
	 * �ɂ����HTML���ǂݍ��܂ꂽ��ɌĂ΂��B<br>
	 * �iHtHtmlParser�ɂ���č\����͂��ꂽ�ꍇ�ɓ����\�b�h���Ă΂��j<br>
	 * �����̓��e��ύX����true��Ԃ��ƁA���̓��e���t�@�C���ɏo�͂���B</strike>
	 * </p>
	 * <p>
	 * <strike>�Ǝ��̕ϊ����s�������ꍇ�́A�����\�b�h���I�[�o�[���C�h����HTML�̕ϊ����W�b�N����������B</strike>
	 * </p>
	 *
	 * @param elist
	 *            �\����͂��ꂽHTML
	 * @return elist�̓��e��ύX�����ꍇ�Atrue
	 * @see #setUseParser(boolean)
	 * @since 2009.02.17
	 * @deprecated �����\�b�h�̋@�\��{@link FileCondType}�Ɉڊǂ��ꂽ�B
	 */
	@Deprecated
	public boolean convert(HtListElement elist) {
		throw new UnsupportedOperationException("�����\�b�h�̋@�\��"
				+ FileCondType.class.getSimpleName() + "�Ɉڊǂ���܂���");
	}

	protected void dumpParseNotFix(HtListElement elist) {
		if (dumpParseNotFixLevel >= 0) {
			StringPrintStream ps = initPrintStream();
			HtElementUtil.dumpNotFix(elist, ps);
			if (!ps.isEmpty()) {
				logReadFile("notFix: ", dumpParseNotFixLevel);
				String s = ps.toString();
				log(s, dumpParseNotFixLevel);
			}
		}
	}

	private StringPrintStream sps = null;

	protected StringPrintStream initPrintStream() {
		if (sps == null) {
			sps = new StringPrintStream();
		}
		sps.clear();
		return sps;
	}

	/**
	 * �������r.
	 *
	 * @param s1
	 *            ������
	 * @param s2
	 *            ������
	 * @return �����񂪓������ꍇ�Atrue�inull�͋󕶎���Ƃ��Ĕ�r����j
	 */
	public boolean equals(String s1, String s2) {
		if (s1 == null || s1.isEmpty()) {
			if (s2 == null || s2.isEmpty()) {
				return true;
			}
			return false;
		} else {
			return s1.equals(s2);
		}
	}
}
