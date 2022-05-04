package jp.hishidama.html.lexer.rule;

import java.io.IOException;

import jp.hishidama.html.lexer.reader.Char;
import jp.hishidama.html.lexer.reader.CharType;
import jp.hishidama.html.lexer.token.*;

/**
 * HtHtmlLexerルール（CDATA）.
 *<p>
 * CDATAセクション（{@literal <![CDATA[～]]>}）の解釈を行う。
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/htlexer.html"
 *         >ひしだま</a>
 * @since 2009.01.25
 */
public class CDataRule extends MarkupRule {

	protected CDataRule(HtLexer data) {
		super(data);
	}

	protected TextRule textRule;

	public void setTextRule(TextRule rule) {
		textRule = rule;
		textRule.setSepRule(CDATA_SEP_RULE);
	}

	protected class CDataSepRule implements SeparateRule {

		@Override
		public boolean isEnd(Char ch) throws IOException {
			if (ch.getType() == CharType.BRAC) {
				Char n = data.readChar();
				try {
					if (n.getType() == CharType.BRAC) {
						Char n2 = data.readChar();
						data.unreadChar(n2);
						if (n2.getType() == CharType.TAGC) {
							return true;
						}
					}
				} finally {
					data.unreadChar(n);
				}
			}
			return false;
		}
	}

	protected final SeparateRule CDATA_SEP_RULE = createCDataSepRule();

	protected SeparateRule createCDataSepRule() {
		return new CDataSepRule();
	}

	protected static final CharType[] START_TYPE = { CharType.TAGO,
			CharType.EXCL, CharType.BRAO, CharType.STRING, CharType.BRAO };

	/**
	 * CDATAセクション開始判断.
	 *
	 * @param cs
	 *            文字列
	 * @return CDATAセクションが始まる文字列のとき、true
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
	 * @deprecated 解釈対象がCDATAセクション以外の場合に例外が発生する。
	 * @see MarkupRule
	 */
	@Override
	@Deprecated
	public CData parse() throws IOException {
		return (CData) super.parse();
	}

	protected CData parse(Chars cs) throws IOException {
		StringBuilder tago = cs.clear(3);

		CData tag = createCDataToken();
		tag.setTag1(tago);

		parseName(tag);
		parseBraO(tag);
		parseText(tag);
		parseTagC(tag);

		return tag;
	}

	protected CData createCDataToken() {
		return new CData();
	}

	@Override
	protected void parseName(NamedMarkup tag) throws IOException {
		StringBuilder sb = new StringBuilder();
		loop: for (Char ch = data.readChar(); ch != Char.EOF; ch = data
				.readChar()) {
			switch (ch.getType()) {
			case BRAO:
			case SPACE:
			case EOL:
				data.unreadChar(ch);
				break loop;
			}
			sb.append(ch.getSB());
		}

		NameAtom name = createNameAtom();
		name.setName(sb);
		tag.setName(name);
	}

	protected void parseBraO(CData tag) throws IOException {
		StringBuilder sb = new StringBuilder();
		for (Char ch = data.readChar(); ch != Char.EOF; ch = data.readChar()) {
			sb.append(ch.getSB());
			if (ch.getType() == CharType.BRAO) {
				break;
			}
		}

		WordAtom word = createWordAtom();
		word.setString(sb);
		tag.setBraO(word);
	}

	protected WordAtom createWordAtom() {
		return new WordAtom();
	}

	protected void parseText(CData tag) throws IOException {
		TextToken text = textRule.parse();
		tag.setData(text);
	}
}
