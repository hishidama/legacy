package jp.hishidama.html.lexer.token;

import java.util.ArrayList;
import java.util.List;

/**
 * HtHtmlLexer�g�[�N���i�^�O�j.
 * <p>
 * �^�O�i{@literal <tag�`>��</tag>}�j��ێ�����g�[�N���B
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/htlexer.html"
 *         >�Ђ�����</a>
 * @since 2009.01.10
 */
public class Tag extends NamedMarkup {

	/**
	 * @since 2009.02.07
	 */
	@Override
	public Tag clone() throws CloneNotSupportedException {
		return new Tag(this);
	}

	protected Tag(Tag o) {
		super(o);
	}

	protected static enum Pos {
		TAGO, NAME,
		// ATTR,
		// TAGC,
	}

	/**
	 * �R���X�g���N�^�[.
	 */
	public Tag() {
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
	 * �J�n�^�O���f.
	 *
	 * @return �J�n�^�O�iTAGO���u&lt;�v�j�̂Ƃ��Atrue
	 */
	public boolean isStart() {
		return "<".equals(getTag1());
	}

	/**
	 * �I���^�O���f.
	 *
	 * @return �I���^�O�iTAGO���u&lt;/�v�܂��� TAGC���u/&gt;�v�j�̂Ƃ��Atrue
	 */
	public boolean isEnd() {
		return "</".equals(getTag1()) || "/>".equals(getTag2());
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

	/**
	 * �����擾.
	 *
	 * @param name
	 *            �������i�啶���������͖��������j
	 * @return �����i���݂��Ȃ��ꍇ�Anull�j
	 */
	public AttributeToken getAttribute(String name) {
		for (int i = list.size() - 1; i >= 0; i--) {
			Token token = list.get(i);
			if (token instanceof AttributeToken) {
				AttributeToken a = (AttributeToken) token;
				if (name.equalsIgnoreCase(a.getName())) {
					return a;
				}
			}
		}
		return null;
	}

	/**
	 * �����l�擾.
	 *
	 * @param name
	 *            �������i�啶���������͖��������j
	 * @return �����l�i���݂��Ȃ��ꍇ�Anull�j
	 */
	public String getAttributeValue(String name) {
		AttributeToken attr = getAttribute(name);
		if (attr != null) {
			return attr.getValue();
		}
		return null;
	}

	/**
	 * �����l�u��.
	 * <p>
	 * ���������݂��Ȃ��ꍇ�͖����ɒǉ�����B
	 * </p>
	 *
	 * @param name
	 *            �������i�啶���������͖��������j
	 * @param value
	 *            �����l
	 * @return �ύX���������ꍇ�Atrue
	 * @since 2011.12.10
	 */
	public boolean replaceOrAddAttribute(String name, String value) {
		boolean changed = false;

		AttributeToken attr = getAttribute(name);
		if (attr == null) {
			attr = new AttributeToken(name);
			this.addSkip(" ");
			this.addAttribute(attr);
			changed = true;
		}

		ValueToken token = attr.getValueToken();
		if (token == null) {
			token = new ValueToken("\"", value, "\"");
			attr.setLet("=");
			attr.setValue(token);
			changed = true;
		} else {
			String old = token.getValue();
			token.setValue(value);

			if (old != null) {
				changed = !old.equals(value);
			} else {
				changed = value != null;
			}
		}

		return changed;
	}
}
