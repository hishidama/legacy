package jp.hishidama.html.lexer.rule;

import java.io.IOException;

import jp.hishidama.html.lexer.reader.Char;
import jp.hishidama.html.lexer.reader.CharType;
import jp.hishidama.html.lexer.token.Markup;
import jp.hishidama.html.lexer.token.NameAtom;
import jp.hishidama.html.lexer.token.NamedMarkup;

/**
 * HtHtmlLexerルール（マークアップ）.
 *<p>
 * マークアップ（タグや宣言・コメント等）の解釈を行う。
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/htlexer.html"
 *         >ひしだま</a>
 * @since 2009.01.10
 */
public class MarkupRule extends HtLexerRule {

	protected MarkupRule(HtLexer data) {
		super(data);
	}

	protected static final CharType[] START_TYPE = { CharType.TAGO };

	protected MarkupRule getMarkupRule(Chars cs) throws IOException {
		if (!cs.isType(START_TYPE)) {
			return null;
		}

		// <!--
		CommentRule cmtRule = data.getCommentRule();
		if (cmtRule.isStart(cs)) {
			return cmtRule;
		}

		// <![CDATA[
		CDataRule cdtRule = data.getCDataRule();
		if (cdtRule.isStart(cs)) {
			return cdtRule;
		}

		// <!
		MarkDeclRule dclRule = data.getDeclareRule();
		if (dclRule.isStart(cs)) {
			return dclRule;
		}

		// <?
		XmlDeclRule xdcRule = data.getXmlDeclRule();
		if (xdcRule.isStart(cs)) {
			return xdcRule;
		}

		TagRule rule = data.getTagRule();
		if (rule.isStart(cs)) {
			return rule;
		}

		return null;
	}

	@Override
	public Markup parse() throws IOException {
		Chars cs = new Chars();
		MarkupRule rule = getMarkupRule(cs);
		if (rule == null) {
			cs.clear(0);
			return null;
		}

		return rule.parse(cs);
	}

	/**
	 * マークアップ系解釈本体.
	 *
	 * @param cs
	 *            先頭文字列
	 * @return トークン
	 * @throws IOException
	 */
	protected Markup parse(Chars cs) throws IOException {
		throw new UnsupportedOperationException("オーバーライド必須");
	}

	protected void parseName(NamedMarkup tag) throws IOException {
		StringBuilder sb = new StringBuilder();
		loop: for (Char ch = data.readChar(); ch != Char.EOF; ch = data
				.readChar()) {
			if (parseEnd(ch)) {
				data.unreadChar(ch);
				break loop;
			}
			switch (ch.getType()) {
			case SPACE:
			case EOL:
			case TAGC:
				data.unreadChar(ch);
				break loop;
			}
			sb.append(ch.getSB());
		}

		NameAtom name = createNameAtom();
		name.setName(sb);
		tag.setName(name);

		tag.addSkip(skipSpace());
	}

	protected NameAtom createNameAtom() {
		return new NameAtom();
	}

	protected void parseTagC(Markup tag) throws IOException {
		Char ch = data.readChar();
		if (ch == Char.EOF) {
			return;
		}

		StringBuilder sb = null;
		for (; ch != Char.EOF; ch = data.readChar()) {
			if (sb == null) {
				sb = ch.getSB();
			} else {
				sb.append(ch.getSB());
			}
			if (ch.getType() == CharType.TAGC) {
				break;
			}
		}

		tag.setTag2(sb);
	}
}
