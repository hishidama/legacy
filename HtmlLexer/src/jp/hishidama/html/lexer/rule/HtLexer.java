package jp.hishidama.html.lexer.rule;

import java.io.*;

import jp.hishidama.html.lexer.reader.*;
import jp.hishidama.html.lexer.rule.HtLexerRule.Chars;
import jp.hishidama.html.lexer.token.*;

/**
 * HtHtmlLexer�{��.
 *<p>
 * HTML��ǂݍ��݁A�g�[�N���ɕ�������B<br>
 * ��<a target="hishidama" href=
 * "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/htlexer.html">�g�p��</a>
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/htlexer.html"
 *         >�Ђ�����</a>
 * @since 2009.01.01
 * @version 2009.02.06
 */
public class HtLexer implements Closeable {

	/**
	 * HTML�g�[�N������.
	 *<p>
	 * �w�肳�ꂽ������̉��߂��s���B
	 * </p>
	 *
	 * @param s
	 *            ������
	 * @return �g�[�N��
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
	 * HTML�g�[�N������.
	 * <p>
	 * �w�肳�ꂽReader���當�����ǂݍ��݁A���߂��s���B
	 * </p>
	 *
	 * @param r
	 *            Reader
	 * @return �g�[�N��
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
	 * ���߂��ꂽ�g�[�N���̃��X�g
	 */
	protected ListToken list;

	/**
	 * �R���X�g���N�^�[.
	 */
	public HtLexer() {
	}

	/**
	 * �R���X�g���N�^�[.
	 *
	 * @param r
	 *            ���ߑΏ�
	 * @see #setTarget(Reader)
	 */
	public HtLexer(Reader r) {
		setTarget(r);
	}

	/**
	 * �R���X�g���N�^�[.
	 *
	 * @param s
	 *            ���ߑΏ�
	 * @see #setTarget(Reader)
	 */
	public HtLexer(String s) {
		setTarget(s);
	}

	/**
	 * ���ߑΏېݒ�.
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
	 * ���ߑΏېݒ�.
	 *
	 * @param s
	 *            ������
	 * @see #setTarget(Reader)
	 */
	public void setTarget(String s) {
		if (s == null) {
			s = "";
		}
		setTarget(new StringReader(s));
	}

	/**
	 * �g�[�N���ǉ�.
	 * <p>
	 * ���߂����������g�[�N�������X�g�ɒǉ����Ă����B
	 * </p>
	 *
	 * @param token
	 *            �g�[�N��
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
	 * HTML�g�[�N������.
	 *<p>
	 * {@link #setTarget(Reader)}�ɂ���Ďw�肳�ꂽ�������HTML���߂��s���B
	 * </p>
	 *
	 * @return �g�[�N��
	 * @throws IOException
	 */
	public ListToken parse() throws IOException {
		TextRule textRule = this.getTopTextRule();
		MarkupRule markupRule = this.getMarkupRule();
		for (;;) {
			// �󔒂��X�L�b�v����
			SkipToken skip = textRule.skipSpace();
			if (skip != null) {
				this.add(skip);
			}
			// �^�O���ǂ������肷��
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
			// �P�Ȃ镶�͂��ǂ����𔻒肷��
			TextToken text = textRule.parse();
			if (text != null) {
				this.add(text);
				continue;
			}
			// ���͂ł��^�O�ł�������ΏI��
			break;
		}

		return list;
	}

	protected void parseScript() throws IOException {
		MarkupRule markupRule = this.getMarkupRule();
		ScriptRule scriptRule = this.getScriptRule();

		for (;;) {
			// �܂��X�N���v�g�{�̂��ǂ����𔻒肷��
			TextToken text = scriptRule.parse();
			if (text != null) {
				this.add(text);
				continue;
			}
			// ���Ƀ^�O�i�R�����g��CDATA���j���ǂ�������
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

	// ��parse

	private ValueRule attrValueRule;

	/**
	 * �����l����.
	 *<p>
	 * {@link #setTarget(Reader)}�ɂ���Ďw�肳�ꂽ������𑮐��l�Ƃ��ĉ��߂���B
	 * </p>
	 *
	 * @param quote1
	 *            �擪�̃N�H�[�e�[�V����
	 * @param quote2
	 *            �����̃N�H�[�e�[�V����
	 * @return �l�g�[�N��
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
	 * �󔒉���.
	 *<p>
	 * {@link #setTarget(Reader)}�ɂ���Ďw�肳�ꂽ��������󔒂Ƃ��ĉ��߂���B
	 * </p>
	 *
	 * @return ��؂�g�[�N��
	 * @throws IOException
	 *             Reader�ŃG���[�����������ꍇ�B<br>
	 *             ����сA�󔒕����ȊO�������Ă����ꍇ�B
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
	 * �e�L�X�g����.
	 *<p>
	 * {@link #setTarget(Reader)}�ɂ���Ďw�肳�ꂽ��������e�L�X�g�Ƃ��ĉ��߂���B
	 * </p>
	 *
	 * @return �e�L�X�g�g�[�N��
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
	 *            ����
	 * @see HtLexReader#unreadChar(Char)
	 */
	protected void unreadChar(Char ch) {
		hr.unreadChar(ch);
	}

	/**
	 * �N���[�Y.
	 *
	 * @see #setTarget(Reader)
	 */
	@Override
	public void close() throws IOException {
		hr.close();
	}

	// ���[���C���X�^���X�擾

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
