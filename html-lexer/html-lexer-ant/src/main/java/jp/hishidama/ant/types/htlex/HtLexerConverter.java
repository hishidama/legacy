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
 * HtHtmlLexerコンバーター.
 *<p>
 * {@link HtLexer}を使用した、HTMLファイル内の検索・置換を行うデータタイプ。
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/ant/htlex.html"
 *         >ひしだま</a>
 * @since 2009.01.18
 * @version 2010.02.06
 */
public class HtLexerConverter extends DataType {

	protected Charset encoding, outputEncoding;

	/**
	 * 入力ファイル文字コード設定.
	 * <p>
	 * 設定されない場合はJavaVMデフォルトの文字コードとなる。
	 * </p>
	 *
	 * @param encoding
	 *            文字コード
	 */
	public void setEncoding(String encoding) {
		this.encoding = Charset.forName(encoding);
	}

	/**
	 * 出力ファイル文字コード設定.
	 * <p>
	 * 設定されない場合は入力ファイルと同じ文字コードとなる。
	 * </p>
	 *
	 * @param encoding
	 *            文字コード
	 */
	public void setOutputEncoding(String encoding) {
		this.outputEncoding = Charset.forName(encoding);
	}

	protected boolean force, nowrite;

	/**
	 * 強制出力指定.
	 *
	 * @param b
	 *            trueの場合、変更が無くてもファイルを出力する。
	 */
	public void setForce(boolean b) {
		force = b;
	}

	/**
	 * 非出力指定.
	 *
	 * @param b
	 *            trueの場合、変更があってもファイルを出力しない。
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
	 * ログ出力設定（読込ファイル）.
	 * <p>
	 * この設定をすると、読み込んだファイル名をログ出力する。
	 * </p>
	 *
	 * @param level
	 *            ログレベル
	 */
	public void setLogReadFile(LogLevel level) {
		LogReadFileLevel = level.getLevel();
	}

	/**
	 * ログ出力設定（マッチ時）.
	 * <p>
	 * この設定をすると、条件にマッチした場合にマッチング内容をログ出力する。
	 * </p>
	 *
	 * @param level
	 *            ログレベル
	 */
	public void setLogMatch(LogLevel level) {
		logMatchLevel = level.getLevel();
	}

	/**
	 * ログ出力設定（アンマッチ時）.
	 * <p>
	 * この設定をすると、条件に1つもマッチしなかった場合にファイル名をログ出力する。
	 * </p>
	 *
	 * @param level
	 *            ログレベル
	 */
	public void setLogUnmatch(LogLevel level) {
		logUnmatchLevel = level.getLevel();
	}

	/**
	 * ログ出力設定（置換時）.
	 * <p>
	 * この設定をすると、置換を行った場合に置換内容をログ出力する。
	 * </p>
	 *
	 * @param level
	 *            ログレベル
	 */
	public void setLogConvert(LogLevel level) {
		logConvertLevel = level.getLevel();
	}

	/**
	 * ログ出力設定（出力ファイル）.
	 * <p>
	 * この設定をすると、ファイル出力したファイル名をログ出力する。
	 * </p>
	 *
	 * @param level
	 *            ログレベル
	 */
	public void setLogWriteFile(LogLevel level) {
		logWriteFileLevel = level.getLevel();
	}

	/**
	 * ログ出力設定（演算実行時）.
	 * <p>
	 * この設定をすると、演算時に演算内容をログ出力する。
	 * </p>
	 *
	 * @param level
	 *            ログレベル
	 * @since 2010.01.31
	 */
	public void setLogEval(LogLevel level) {
		Rule rule = HtLexerExpRuleFactory.getRule(getProject());
		HtLexerEvalLog log = (HtLexerEvalLog) rule.getDefaultEvalLog();
		log.setMsgLevel(level.getLevel());
	}

	protected boolean useParser = false;

	/**
	 * HtHtmlParser使用有無設定.
	 *<p>
	 * HtHtmlParserによる構文解析結果を使用するオプションを別途指定している場合は、自動でtrueとなる。
	 * </p>
	 *
	 * @param b
	 *            HtHtmlParserを使用する場合、true
	 * @since 2009.02.17
	 */
	public void setUseParser(boolean b) {
		useParser = b;
	}

	protected int dumpParseNotFixLevel = -1;

	/**
	 * デバッグダンプ（未確定タグ）設定.
	 * <p>
	 * HtHtmlParserによる構文解析で未確定のタグがあったらそれを出力する。
	 * </p>
	 *
	 * @param level
	 *            ログレベル
	 * @since 2009.02.15
	 */
	public void setDumpParseNotFix(LogLevel level) {
		dumpParseNotFixLevel = level.getLevel();
		setUseParser(true);
	}

	protected HtLexerPropertyHelper propertyHelper;

	/**
	 * プロパティーヘルパー設定.
	 *
	 * @param helper
	 *            プロパティーヘルパー
	 * @since 2010.01.23
	 */
	public void initPropertyHelper(HtLexerPropertyHelper helper) {
		propertyHelper = helper;
		propertyHelper.setConverter(this);
	}

	/**
	 * プロパティーヘルパー取得.
	 *
	 * @return プロパティーヘルパー
	 * @since 2010.01.23
	 */
	public HtLexerPropertyHelper getPropertyHelper() {
		return propertyHelper;
	}

	/**
	 * タグ条件追加.
	 *
	 * @param tag
	 *            タグタイプ
	 */
	public void addConfigured(TagType tag) {
		EmptyFileCondType file = getEmptyFileCondType(tag.getLocation());
		file.addConfigured(tag);
	}

	/**
	 * テキスト条件追加.
	 *
	 * @param text
	 *            テキストタイプ
	 */
	public void addConfigured(TextType text) {
		EmptyFileCondType file = getEmptyFileCondType(text.getLocation());
		file.addConfigured(text);
	}

	protected final List<FileCondType> fileList = new ArrayList<FileCondType>();

	/**
	 * ファイル条件追加.
	 *
	 * @param file
	 *            ファイル条件タイプ
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
	 * 精査実行.
	 *
	 * @throws BuildException
	 *             精査エラー時
	 */
	public void validate() throws BuildException {
		for (FileCondType i : fileList) {
			i.validate(this);
		}
	}

	protected File rf, wf;

	/**
	 * ログ出力（読込ファイル）.
	 *
	 * @param msg
	 *            メッセージ
	 * @param level
	 *            ログレベル
	 */
	public void logReadFile(String msg, int level) {
		if (level >= 0) {
			log(msg + rf.getAbsolutePath(), level);
		}
	}

	private boolean matched;

	/**
	 * ログ出力（マッチ時）.
	 *
	 * @param token
	 *            トークン
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
	 * ログ出力（アンマッチ時）.
	 */
	public void logUnmatch() {
		if (logUnmatchLevel >= 0) {
			logReadFile("unmatch: ", logUnmatchLevel);
		}
	}

	private boolean convert;

	/**
	 * ログ出力（置換時）.
	 *
	 * @param msg
	 *            メッセージ
	 * @param line
	 *            行番号
	 * @param token
	 *            トークン
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
	 * ログ出力（出力ファイル）.
	 *
	 * @param f
	 *            ファイル名
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
	 * 指定された文字列用のHtLexerを返す。
	 *<p>
	 * インスタンスを共有している為、重複使用は不可。
	 * </p>
	 *
	 * @param s
	 *            解釈対象
	 * @return HtLexer
	 */
	public HtLexer getLexer(String s) {
		lexer.setTarget(s);
		return lexer;
	}

	/**
	 * 処理本体.
	 *<p>
	 * HTMLファイルを読み込み、文字列検索や置換を行う。
	 * </p>
	 *
	 * @param rf
	 *            読込ファイル名
	 * @param wf
	 *            出力ファイル名
	 * @param bf
	 *            バックアップ用ファイル名
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
	 * 処理対象ファイル名取得.
	 *
	 * @return 読込ファイル名
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
	 * HTML変更.
	 * <p>
	 * <strike>{@link jp.hishidama.ant.taskdefs.HtLexerTask}
	 * によってHTMLが読み込まれた後に呼ばれる。<br>
	 * （HtHtmlParserによって構文解析されていない場合に当メソッドが呼ばれる）<br>
	 * 引数の内容を変更してtrueを返すと、その内容をファイルに出力する。</strike>
	 * </p>
	 * <p>
	 * <strike>独自の変換を行いたい場合は、当メソッドをオーバーライドしてHTMLの変換ロジックを実装する。</strike>
	 * </p>
	 *
	 * @param tlist
	 *            読み込まれたHTML
	 * @return tlistの内容を変更した場合、true
	 * @see #setUseParser(boolean)
	 * @deprecated 当メソッドの機能は{@link FileCondType}に移管された。
	 */
	@Deprecated
	public boolean convert(ListToken tlist) {
		throw new UnsupportedOperationException("当メソッドの機能は"
				+ FileCondType.class.getSimpleName() + "に移管されました");
	}

	/**
	 * HTML変更.
	 * <p>
	 * <strike>{@link jp.hishidama.ant.taskdefs.HtLexerTask}
	 * によってHTMLが読み込まれた後に呼ばれる。<br>
	 * （HtHtmlParserによって構文解析された場合に当メソッドが呼ばれる）<br>
	 * 引数の内容を変更してtrueを返すと、その内容をファイルに出力する。</strike>
	 * </p>
	 * <p>
	 * <strike>独自の変換を行いたい場合は、当メソッドをオーバーライドしてHTMLの変換ロジックを実装する。</strike>
	 * </p>
	 *
	 * @param elist
	 *            構文解析されたHTML
	 * @return elistの内容を変更した場合、true
	 * @see #setUseParser(boolean)
	 * @since 2009.02.17
	 * @deprecated 当メソッドの機能は{@link FileCondType}に移管された。
	 */
	@Deprecated
	public boolean convert(HtListElement elist) {
		throw new UnsupportedOperationException("当メソッドの機能は"
				+ FileCondType.class.getSimpleName() + "に移管されました");
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
	 * 文字列比較.
	 *
	 * @param s1
	 *            文字列
	 * @param s2
	 *            文字列
	 * @return 文字列が等しい場合、true（nullは空文字列として比較する）
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
