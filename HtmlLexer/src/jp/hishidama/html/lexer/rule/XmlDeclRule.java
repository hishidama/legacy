package jp.hishidama.html.lexer.rule;

import java.io.IOException;

import jp.hishidama.html.lexer.reader.Char;
import jp.hishidama.html.lexer.reader.CharType;
import jp.hishidama.html.lexer.token.AttributeToken;
import jp.hishidama.html.lexer.token.XmlDeclare;

/**
 * HtHtmlLexerルール（XML宣言）.
 *<p>
 * XML宣言（{@literal <?xml～?>}）の解釈を行う。
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/htlexer.html"
 *         >ひしだま</a>
 * @since 2009.01.25
 */
public class XmlDeclRule extends MarkupRule {

	protected XmlDeclRule(HtLexer data) {
		super(data);
		setSepRule(DECL_SEP_RULE);
	}

	protected AttrRule attrRule;

	protected void setAttrRule(AttrRule rule) {
		attrRule = rule;
		attrRule.setSepRule(DECL_SEP_RULE);
	}

	protected class DeclSepRule implements SeparateRule {

		@Override
		public boolean isEnd(Char ch) throws IOException {
			switch (ch.getType()) {
			case SPACE:
			case EOL:
				return true;
			case QUES:
				Char n = data.readChar();
				data.unreadChar(n);
				if (n.getType() == CharType.TAGC) {
					return true;
				}
				break;
			}
			return false;
		}
	}

	protected final SeparateRule DECL_SEP_RULE = createDeclSepRule();

	protected SeparateRule createDeclSepRule() {
		return new DeclSepRule();
	}

	protected static final CharType[] START_TYPE = { CharType.TAGO,
			CharType.QUES };

	/**
	 * XML宣言開始判断.
	 *
	 * @param cs
	 *            文字列
	 * @return XML宣言が始まる文字列のとき、true
	 * @throws IOException
	 */
	public boolean isStart(Chars cs) throws IOException {
		return cs.isType(START_TYPE);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @throws IOException
	 * @throws ClassCastException
	 * @deprecated 解釈対象がXML宣言以外の場合に例外が発生する。
	 * @see MarkupRule
	 */
	@Override
	@Deprecated
	public XmlDeclare parse() throws IOException {
		return (XmlDeclare) super.parse();
	}

	protected XmlDeclare parse(Chars cs) throws IOException {
		StringBuilder tago = cs.clear(START_TYPE.length);

		XmlDeclare tag = createXmlDeclToken();
		tag.setTag1(tago);

		parseName(tag);
		parseAttr(tag);
		parseTagC(tag);

		return tag;
	}

	protected XmlDeclare createXmlDeclToken() {
		return new XmlDeclare();
	}

	protected void parseAttr(XmlDeclare tag) throws IOException {
		for (;;) {
			AttributeToken attr = attrRule.parse();
			if (attr == null) {
				break;
			}
			tag.addAttribute(attr);
			tag.addSkip(skipSpace());
		}
	}
}
