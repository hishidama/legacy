package jp.hishidama.html.lexer.rule;

import java.io.IOException;

import jp.hishidama.html.lexer.reader.Char;
import jp.hishidama.html.lexer.reader.CharType;
import jp.hishidama.html.lexer.token.AttributeToken;
import jp.hishidama.html.lexer.token.Tag;

/**
 * HtHtmlLexer���[���i�^�O�j.
 *<p>
 * �^�O�i{@literal <tag�`>��</tag>}�j�̉��߂��s���B
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/htlexer.html"
 *         >�Ђ�����</a>
 * @since 2009.01.01
 */
public class TagRule extends MarkupRule {

	protected TagRule(HtLexer data) {
		super(data);
		setSepRule(TAG_SEP_RULE);
	}

	protected AttrRule attrRule;

	protected void setAttrRule(AttrRule rule) {
		attrRule = rule;
		attrRule.setSepRule(TAG_SEP_RULE);
	}

	protected class TagSepRule implements SeparateRule {

		@Override
		public boolean isEnd(Char ch) throws IOException {
			switch (ch.getType()) {
			case TAGC:
				return true;
			case SLASH:
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

	protected final SeparateRule TAG_SEP_RULE = createTagSepRule();

	protected SeparateRule createTagSepRule() {
		return new TagSepRule();
	}

	protected static final CharType[] START_TYPE = { CharType.TAGO };

	/**
	 * �^�O�J�n���f.
	 *
	 * @param cs
	 *            ������
	 * @return �^�O���n�܂镶����̂Ƃ��Atrue
	 * @throws IOException
	 */
	public boolean isStart(Chars cs) throws IOException {
		if (!cs.isType(START_TYPE)) {
			return false;
		}
		Char ch = cs.get(1);
		if (ch.getType() == CharType.SLASH) {
			ch = cs.get(2);
			if (ch.getType() == CharType.TAGC) {
				return true;
			}
		}
		if (ch.getType() != CharType.STRING) {
			return false;
		}
		char c = ch.getSB().charAt(0);
		if (('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z')) {
			return true;
		}

		return false;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @throws IOException
	 * @throws ClassCastException
	 * @deprecated ���ߑΏۂ��^�O�ȊO�̏ꍇ�ɗ�O����������B
	 * @see MarkupRule
	 */
	@Override
	@Deprecated
	public Tag parse() throws IOException {
		return (Tag) super.parse();
	}

	protected Tag parse(Chars cs) throws IOException {
		StringBuilder tago = cs.clear(START_TYPE.length);
		Char c = data.readChar();
		if (c.getType() == CharType.SLASH) {
			tago.append(c.getSB());
		} else {
			data.unreadChar(c);
		}

		Tag tag = createTagToken();
		tag.setTag1(tago);

		parseName(tag);
		parseAttr(tag);
		parseTagC(tag);

		return tag;
	}

	protected Tag createTagToken() {
		return new Tag();
	}

	protected void parseAttr(Tag tag) throws IOException {
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
