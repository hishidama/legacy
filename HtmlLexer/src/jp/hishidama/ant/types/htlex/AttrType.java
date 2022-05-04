package jp.hishidama.ant.types.htlex;

import jp.hishidama.ant.types.TextParameter;
import jp.hishidama.ant.types.htlex.eval.HtLexerExpRuleFactory;
import jp.hishidama.ant.types.htlex.eval.HtLexerPropertyHelper;
import jp.hishidama.eval.Expression;
import jp.hishidama.html.lexer.token.AttributeToken;
import jp.hishidama.html.lexer.token.ValueToken;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.IntrospectionHelper;
import org.apache.tools.ant.types.DataType;

/**
 * HtHtmlLexer�^�O�����^�C�v.
 *<p>
 * HTML�t�@�C�����̃^�O�̑����̃}�b�`������ێ�����f�[�^�^�C�v�B
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/ant/htlex.html"
 *         >�Ђ�����</a>
 * @since 2009.01.20
 * @version 2010.02.01
 */
public class AttrType extends DataType {

	protected MatchEnum nameMatch;

	/**
	 * ���O�����ݒ�.
	 *
	 * @param name
	 *            ������
	 */
	public void setName(String name) {
		if (nameMatch == null) {
			nameMatch = new MatchEnum(MatchEnum.IGNORE_CASE);
		}
		nameMatch.setPattern(name);
	}

	/**
	 * ���O�}�b�`���O���@�ݒ�.
	 *
	 * @param match
	 *            �}�b�`���O���@
	 */
	public void setNameMatch(MatchEnum match) {
		if (nameMatch != null) {
			match.setPattern(nameMatch.getPatternString());
		}
		nameMatch = match;
	}

	protected String let;

	/**
	 * ����L�������ݒ�.
	 *
	 * @param s
	 *            ����L��
	 */
	public void setLet(String s) {
		let = s;
	}

	protected MatchEnum valueMatch;

	/**
	 * �����l�����ݒ�.
	 *
	 * @param value
	 *            �����l
	 */
	public void setValue(String value) {
		if (valueMatch == null) {
			valueMatch = new MatchEnum(MatchEnum.IGNORE_CASE);
		}
		valueMatch.setPattern(value);
	}

	/**
	 * �����l�}�b�`���O���@�ݒ�.
	 *
	 * @param match
	 *            �}�b�`���O���@
	 */
	public void setValueMatch(MatchEnum match) {
		if (valueMatch != null) {
			match.setPattern(valueMatch.getPatternString());
		}
		valueMatch = match;
	}

	protected String quote;

	/**
	 * �N�H�[�e�[�V���������ݒ�.
	 *
	 * @param s
	 *            �N�H�[�e�[�V����
	 */
	public void setQuote(String s) {
		quote = s;
	}

	protected Expression ifExp;

	/**
	 * �������ݒ�.
	 *
	 * @param s
	 *            ������
	 * @since 2010.01.31
	 */
	public void setIf(String s) {
		ifExp = HtLexerExpRuleFactory.getRule(getProject()).parse(s);
	}

	protected String properyName;

	/**
	 * �v���p�e�B�[���ݒ�.
	 *
	 * @param name
	 *            �v���p�e�B�[��
	 * @since 2010.01.23
	 */
	public void setPropertyName(String name) {
		properyName = name;
	}

	/**
	 * �p�����[�^�[�ݒ�.
	 * <p>
	 * �����l�ɏ����Â炢�������Ȃǂ��p�����[�^�[�̃{�f�B�[���ɋL�q�ł���B<br>
	 * {@code <attr><param name="foo">bar</param></attr>}�̏ꍇ�A{@code <attr
	 * foo="bar">}�Ɠ����B
	 * </p>
	 *
	 * @param param
	 *            �p�����[�^�[
	 * @since 2010.02.01
	 */
	public void addConfiguredParam(TextParameter param) {
		String name = param.getName();
		if (name == null || name.isEmpty()) {
			throw new BuildException("name attribute must be set.", param
					.getLocation());
		}
		IntrospectionHelper ih = IntrospectionHelper.getHelper(getProject(),
				this.getClass());
		try {
			ih.setAttribute(getProject(), this, name, param.getValue());
		} catch (Exception e) {
			throw new BuildException(e, param.getLocation());
		}
	}

	protected HtLexerConverter htmlConverter;

	/**
	 * HtLexerConverter�ݒ�.
	 *
	 * @param conv
	 * @since 2010.01.23
	 */
	public void initHtLexerConverter(HtLexerConverter conv) {
		htmlConverter = conv;
	}

	protected void pushAttributeToken(AttributeToken a) {
		HtLexerPropertyHelper helper = htmlConverter.getPropertyHelper();
		if (helper != null) {
			helper.pushAttributeToken(a);
		}
	}

	protected void popAttributeToken() {
		HtLexerPropertyHelper helper = htmlConverter.getPropertyHelper();
		if (helper != null) {
			helper.popAttributeToken();
		}
	}

	/**
	 * �}�b�`���O���f.
	 *
	 * @param a
	 *            ����
	 * @return �w�肳��Ă��������Ƀ}�b�`�����ꍇ�Atrue
	 */
	public boolean matches(AttributeToken a) {
		String name = a.getName();
		if (!equalsName(name)) {
			return false;
		}

		if (!equalsLet(a.getLet())) {
			return false;
		}

		ValueToken vt = a.getValueToken();
		String v = (vt == null) ? null : vt.getValue();
		if (!equalsValue(v)) {
			return false;
		}
		String q1 = null, q2 = null;
		if (vt != null) {
			q1 = vt.getQuote1();
			q2 = vt.getQuote2();
		}
		if (!equalsQuote(q1) || !equalsQuote(q2)) {
			return false;
		}

		if (!equalsIf()) {
			return false;
		}

		return true;
	}

	protected boolean equalsName(String s) {
		if (nameMatch == null) {
			return true;
		}
		return nameMatch.matches(s);
	}

	protected boolean equalsLet(String s) {
		if (let == null) {
			return true;
		}
		if (let.isEmpty() && s == null) {
			return true;
		}
		return let.equals(s);
	}

	protected boolean equalsValue(String s) {
		if (valueMatch == null) {
			return true;
		}
		return valueMatch.matches(s);
	}

	protected boolean equalsQuote(String q) {
		if (quote == null) {
			return true;
		}
		if (quote.isEmpty() && q == null) {
			return true;
		}
		return quote.equals(q);
	}

	protected boolean equalsIf() {
		if (ifExp == null) {
			return true;
		}
		try {
			Object r = ifExp.eval();
			if (r == null) {
				return false;
			}
			if (r instanceof Boolean) {
				return ((Boolean) r).booleanValue();
			}
			if (r instanceof Number) {
				return ((Number) r).intValue() != 0;
			}
			if (r instanceof String) {
				return !((String) r).isEmpty();
			}
		} catch (BuildException e) {
			e.setLocation(getLocation());
			throw e;
		} catch (Exception e) {
			throw new BuildException(e, getLocation());
		}
		return true;
	}

	/**
	 * �v���p�e�B�[�ݒ菈��.
	 *
	 * @param a
	 *            ����
	 * @since 2010.01.23
	 */
	public void doSetProperty(AttributeToken a) {
		if (properyName == null) {
			return;
		}

		ValueToken vt = a.getValueToken();
		String value = (vt == null) ? null : vt.getValue();

		if (value == null) {
			value = ""; // null�̒l�̓Z�b�g�ł��Ȃ��̂ŋ󕶎���ɕϊ�
		}
		getProject().setProperty(properyName, value);
	}
}
