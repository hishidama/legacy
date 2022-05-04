package jp.hishidama.html.lexer.rule;

import java.io.IOException;

import jp.hishidama.html.lexer.reader.Char;
import jp.hishidama.html.lexer.reader.CharType;
import jp.hishidama.html.lexer.token.AttributeToken;
import jp.hishidama.html.lexer.token.NameAtom;
import jp.hishidama.html.lexer.token.ValueToken;
import jp.hishidama.html.lexer.token.WordAtom;

/**
 * HtHtmlLexerルール（属性）.
 * <p>
 * 属性（属性名=属性値）の解釈を行う。
 * </p>
 *
 * @author <a target="hishidama" href= "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/htlexer.html" >ひしだま</a>
 * @since 2009.01.10
 */
public class AttrRule extends HtLexerRule {

	protected AttrRule(HtLexer data) {
		super(data);
	}

	protected NameRule nameRule;
	protected ValueRule valueRule;

	/**
	 * 属性名解釈ルール設定.
	 *
	 * @param rule
	 *            ルール
	 */
	protected void setNameRule(NameRule rule) {
		nameRule = rule;
		nameRule.setSepRule(createNameSepRule());
	}

	/**
	 * 属性値解釈ルール設定.
	 *
	 * @param rule
	 *            ルール
	 */
	protected void setValueRule(ValueRule rule) {
		valueRule = rule;
		valueRule.setSepRule(createValueSepRule());
	}

	protected class NameSepRule extends ValueSepRule {

		protected NameSepRule(SeparateRule sep) {
			super(sep);
		}

		@Override
		public boolean isEnd(Char ch) throws IOException {
			if (super.isEnd(ch)) {
				return true;
			}
			if (ch.getType() == CharType.EQ) {
				return true;
			}
			return false;
		}
	}

	protected SeparateRule createNameSepRule() {
		return new NameSepRule(sepRule);
	}

	protected class ValueSepRule implements SeparateRule {

		protected SeparateRule sepRule;

		protected ValueSepRule(SeparateRule sep) {
			sepRule = sep;
		}

		@Override
		public boolean isEnd(Char ch) throws IOException {
			if (sepRule.isEnd(ch)) {
				return true;
			}
			switch (ch.getType()) {
			case SPACE:
			case EOL:
				return true;
			default:
				break;
			}
			return false;
		}
	}

	protected SeparateRule createValueSepRule() {
		return new ValueSepRule(sepRule);
	}

	@Override
	public AttributeToken parse() throws IOException {
		NameAtom name = nameRule.parse();
		if (name == null) {
			return null;
		}

		AttributeToken attr = createAttrToken();
		attr.setName(name);
		attr.setSkip1(skipSpace());

		Char ch = data.readChar();
		if (ch.getType() == CharType.EQ) {
			WordAtom eq = new WordAtom();
			eq.setString(ch.getSB());
			attr.setLet(eq);
			attr.setSkip2(skipSpace());

			ValueToken value = valueRule.parse();
			attr.setValue(value);
		} else {
			data.unreadChar(ch);
		}

		return attr;
	}

	protected AttributeToken createAttrToken() {
		return new AttributeToken();
	}
}
