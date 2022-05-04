package jp.hishidama.html.lexer.token;

import java.util.ArrayList;
import java.util.List;

/**
 * HtHtmlLexer�g�[�N���iXML�錾�j.
 *<p>
 * XML�錾�i{@literal <?xml�`?>}�j��ێ�����g�[�N���B
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/htlexer.html"
 *         >�Ђ�����</a>
 * @since 2009.01.25
 */
public class XmlDeclare extends NamedMarkup {

	/**
	 * @since 2009.02.07
	 */
	@Override
	public XmlDeclare clone() throws CloneNotSupportedException {
		return new XmlDeclare(this);
	}

	protected XmlDeclare(XmlDeclare o) {
		super(o);
	}

	protected static enum Pos {
		TAGO, NAME,
		// VALUE,
		// TAGC,
	}

	/**
	 * �R���X�g���N�^�[.
	 */
	public XmlDeclare() {
	}

	/**
	 * �����ǉ�.
	 *
	 * @param attr
	 *            ����
	 */
	public void addAttribute(AttributeToken attr) {
		addBeforeTagC(attr);
	}

	/**
	 * �����ꗗ�擾.
	 *
	 * @return �����ꗗ�i�K��null�ȊO�j
	 */
	public List<AttributeToken> getAttributeList() {
		List<AttributeToken> ret = new ArrayList<AttributeToken>(list.size());
		for (Token token : list) {
			if (token instanceof AttributeToken) {
				ret.add((AttributeToken) token);
			}
		}
		return ret;
	}
}
