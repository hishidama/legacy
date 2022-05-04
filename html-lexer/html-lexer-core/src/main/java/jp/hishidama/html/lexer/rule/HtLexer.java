package jp.hishidama.html.lexer.rule;

import java.io.*;

import jp.hishidama.html.lexer.reader.*;
import jp.hishidama.html.lexer.rule.HtLexerRule.Chars;
import jp.hishidama.html.lexer.token.*;

/**
 * HtHtmlLexer本体.
 *<p>
 * HTMLを読み込み、トークンに分解する。<br>
 * →<a target="hishidama" href=
 * "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/htlexer.html">使用例</a>
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/htlexer.html"
 *         >ひしだま</a>
 * @since 2009.01.01
 * @version 2009.02.06
 */
public class HtLexer implements Closeable {

	/**
	 * HTMLトークン解釈.
	 *<p>
	 * 指定された文字列の解釈を行う。
	 * </p>
	 *
	 * @param s
	 *            文字列
	 * @return トークン
	 * @throws IOException
	 * @see #parse(Reader)
	 */
	public static ListToken parse(String s) {
		try {
			return parse(new StringReader(s));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * HTMLトークン解釈.
	 * <p>
	 * 指定されたReaderから文字列を読み込み、解釈を行う。
	 * </p>
	 *
	 * @param r
	 *            Reader
	 * @return トークン
	 * @throws IOException
	 * @see #setTarget(Reader)
	 * @see #parse()
	 * @see #close()
	 */
	public static ListToken parse(Reader r) throws IOException {
		HtLexer lexer = new HtLexer(r);
		try {
			return lexer.parse();
		} finally {
			try {
				lexer.close();
			} catch (IOException e) {
			}
		}
	}

	private HtLexReader hr;

	/**
	 * 解釈されたトークンのリスト
	 */
	protected ListToken list;

	/**
	 * コンストラクター.
	 */
	public HtLexer() {
	}

	/**
	 * コンストラクター.
	 *
	 * @param r
	 *            解釈対象
	 * @see #setTarget(Reader)
	 */
	public HtLexer(Reader r) {
		setTarget(r);
	}

	/**
	 * コンストラクター.
	 *
	 * @param s
	 *            解釈対象
	 * @see #setTarget(Reader)
	 */
	public HtLexer(String s) {
		setTarget(s);
	}

	/**
	 * 解釈対象設定.
	 *
	 * @param r
	 *            Reader
	 * @see #parse()
	 * @see #close()
	 */
	public void setTarget(Reader r) {
		hr = new HtLexReader(r);
		list = new ListToken();
	}

	/**
	 * 解釈対象設定.
	 *
	 * @param s
	 *            文字列
	 * @see #setTarget(Reader)
	 */
	public void setTarget(String s) {
		if (s == null) {
			s = "";
		}
		setTarget(new StringReader(s));
	}

	/**
	 * トークン追加.
	 * <p>
	 * 解釈が完了したトークンをリストに追加していく。
	 * </p>
	 *
	 * @param token
	 *            トークン
	 */
	protected void add(Token token) {
		list.add(token);
	}

	protected class TagOpenRule implements SeparateRule {

		@Override
		public boolean isEnd(Char ch) throws IOException {
			MarkupRule markupRule = getMarkupRule();
			Chars cs = markupRule.new Chars(ch);
			boolean r = (markupRule.getMarkupRule(cs) != null);
			cs.clear(1);
			return r;
		}
	}

	protected SeparateRule TAG_OPEN_RULE = createTagOpenRule();

	protected SeparateRule createTagOpenRule() {
		return new TagOpenRule();
	}

	/**
	 * HTMLトークン解釈.
	 *<p>
	 * {@link #setTarget(Reader)}によって指定された文字列のHTML解釈を行う。
	 * </p>
	 *
	 * @return トークン
	 * @throws IOException
	 */
	public ListToken parse() throws IOException {
		TextRule textRule = this.getTopTextRule();
		MarkupRule markupRule = this.getMarkupRule();
		for (;;) {
			// 空白をスキップする
			SkipToken skip = textRule.skipSpace();
			if (skip != null) {
				this.add(skip);
			}
			// タグかどうか判定する
			Markup mark = markupRule.parse();
			if (mark != null) {
				this.add(mark);
				if (mark instanceof Tag) {
					Tag tag = (Tag) mark;
					if ("script".equalsIgnoreCase(tag.getName())) {
						if (tag.isStart() && !tag.isEnd()) {
							parseScript();
						}
					}
				}
				continue;
			}
			// 単なる文章かどうかを判定する
			TextToken text = textRule.parse();
			if (text != null) {
				this.add(text);
				continue;
			}
			// 文章でもタグでも無ければ終了
			break;
		}

		return list;
	}

	protected void parseScript() throws IOException {
		MarkupRule markupRule = this.getMarkupRule();
		ScriptRule scriptRule = this.getScriptRule();

		for (;;) {
			// まずスクリプト本体かどうかを判定する
			TextToken text = scriptRule.parse();
			if (text != null) {
				this.add(text);
				continue;
			}
			// 次にタグ（コメントやCDATA等）かどうか試す
			Markup mark = markupRule.parse();
			if (mark != null) {
				this.add(mark);
				if (mark instanceof Tag) {
					Tag tag = (Tag) mark;
					if ("script".equalsIgnoreCase(tag.getName())) {
						if (tag.isEnd()) {
							return;
						}
					}
				}
				continue;
			}
			return;
		}
	}

	// 個別parse

	private ValueRule attrValueRule;

	/**
	 * 属性値解釈.
	 *<p>
	 * {@link #setTarget(Reader)}によって指定された文字列を属性値として解釈する。
	 * </p>
	 *
	 * @param quote1
	 *            先頭のクォーテーション
	 * @param quote2
	 *            末尾のクォーテーション
	 * @return 値トークン
	 * @throws IOException
	 * @see ValueRule
	 */
	public ValueToken parseAttrValue(String quote1, String quote2)
			throws IOException {
		if (attrValueRule == null) {
			attrValueRule = new ValueRule(this);
		}
		return attrValueRule.parseAll(quote1, quote2);
	}

	private TextRule skipRule;

	/**
	 * 空白解釈.
	 *<p>
	 * {@link #setTarget(Reader)}によって指定された文字列を空白として解釈する。
	 * </p>
	 *
	 * @return 区切りトークン
	 * @throws IOException
	 *             Readerでエラーが発生した場合。<br>
	 *             および、空白文字以外が入っていた場合。
	 * @see HtLexerRule#skipSpace()
	 */
	public SkipToken parseSkip() throws IOException {
		if (skipRule == null) {
			skipRule = new TextRule(this);
		}
		SkipToken s = skipRule.skipSpace();

		Char c = readChar();
		if (c == Char.EOF) {
			return s;
		}
		unreadChar(c);
		throw new IOException("not skip char: " + c);
	}

	protected class EndlessRule implements SeparateRule {

		@Override
		public boolean isEnd(Char ch) throws IOException {
			return false;
		}
	}

	private TextRule textRule;

	/**
	 * テキスト解釈.
	 *<p>
	 * {@link #setTarget(Reader)}によって指定された文字列をテキストとして解釈する。
	 * </p>
	 *
	 * @return テキストトークン
	 * @throws IOException
	 */
	public TextToken parseText() throws IOException {
		if (textRule == null) {
			textRule = new TextRule(this);
			textRule.setSepRule(new EndlessRule());
		}
		return textRule.parse();
	}

	// read

	/**
	 * @see HtLexReader#readChar()
	 */
	protected Char readChar() throws IOException {
		return hr.readChar();
	}

	/**
	 * @param ch
	 *            文字
	 * @see HtLexReader#unreadChar(Char)
	 */
	protected void unreadChar(Char ch) {
		hr.unreadChar(ch);
	}

	/**
	 * クローズ.
	 *
	 * @see #setTarget(Reader)
	 */
	@Override
	public void close() throws IOException {
		hr.close();
	}

	// ルールインスタンス取得

	private TextRule topTextRule;

	protected final TextRule getTopTextRule() {
		if (topTextRule == null) {
			topTextRule = createTopTextRule();
		}
		return topTextRule;
	}

	protected TextRule createTopTextRule() {
		TextRule rule = new TextRule(this);
		rule.setSepRule(TAG_OPEN_RULE);
		return rule;
	}

	protected TextRule createTextRule() {
		return new TextRule(this);
	}

	private MarkupRule markupRlue;

	protected final MarkupRule getMarkupRule() {
		if (markupRlue == null) {
			markupRlue = createMarkupRule();
		}
		return markupRlue;
	}

	protected MarkupRule createMarkupRule() {
		return new MarkupRule(this);
	}

	private TagRule tagRule;

	protected final TagRule getTagRule() {
		if (tagRule == null) {
			tagRule = createTagRule();
		}
		return tagRule;
	}

	protected TagRule createTagRule() {
		TagRule rule = new TagRule(this);
		AttrRule attrRule = new AttrRule(this);
		rule.setAttrRule(attrRule);
		attrRule.setNameRule(new NameRule(this));
		attrRule.setValueRule(new ValueRule(this));
		return rule;
	}

	private MarkDeclRule declareRule;

	protected final MarkDeclRule getDeclareRule() {
		if (declareRule == null) {
			declareRule = createDeclareRule();
		}
		return declareRule;

	}

	protected MarkDeclRule createDeclareRule() {
		MarkDeclRule rule = new MarkDeclRule(this);
		rule.setValueRule(new ValueRule(this));
		return rule;
	}

	private CommentRule commentRule;

	protected final CommentRule getCommentRule() {
		if (commentRule == null) {
			commentRule = createCommentRule();
		}
		return commentRule;
	}

	protected CommentRule createCommentRule() {
		CommentRule rule = new CommentRule(this);
		rule.setTextRule(new TextRule(this));
		return rule;
	}

	private XmlDeclRule xmlDeclRule;

	protected final XmlDeclRule getXmlDeclRule() {
		if (xmlDeclRule == null) {
			xmlDeclRule = createXmlDeclRule();
		}
		return xmlDeclRule;
	}

	protected XmlDeclRule createXmlDeclRule() {
		XmlDeclRule rule = new XmlDeclRule(this);
		AttrRule attrRule = new AttrRule(this);
		rule.setAttrRule(attrRule);
		attrRule.setNameRule(new NameRule(this));
		attrRule.setValueRule(new ValueRule(this));
		return rule;
	}

	protected CDataRule cdataRule;

	protected final CDataRule getCDataRule() {
		if (cdataRule == null) {
			cdataRule = createCDataRule();
		}
		return cdataRule;
	}

	protected CDataRule createCDataRule() {
		CDataRule rule = new CDataRule(this);
		rule.setTextRule(new TextRule(this));
		return rule;
	}

	protected ScriptRule scriptRule;

	protected final ScriptRule getScriptRule() {
		if (scriptRule == null) {
			scriptRule = createScriptRule();
		}
		return scriptRule;
	}

	protected ScriptRule createScriptRule() {
		return new ScriptRule(this);
	}
}
