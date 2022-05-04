package jp.hishidama.ant.types.htlex;

import java.io.IOException;

import org.apache.tools.ant.BuildException;

import jp.hishidama.html.HtmlEscape;
import jp.hishidama.html.lexer.rule.HtLexer;
import jp.hishidama.html.lexer.token.AttributeToken;
import jp.hishidama.html.lexer.token.Tag;
import jp.hishidama.html.lexer.token.ValueToken;

/**
 * HtHtmlLexer�^�O�����ϊ��^�C�v.
 *<p>
 * HTML�t�@�C�����̃^�O�̑����̕ϊ����s���f�[�^�^�C�v�B
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/ant/htlex.html"
 *         >�Ђ�����</a>
 * @since 2009.01.20
 */
public class AttrNewType extends AttrOpeType {

	protected String newName;

	/**
	 * �ϊ����O�ݒ�.
	 *
	 * @param name
	 *            ������
	 */
	public void setNewName(String name) {
		newName = name;
	}

	protected StringOpeEnum nameOpe = new StringOpeEnum(StringOpeEnum.LET);

	/**
	 * ���O�u�����@�ݒ�.
	 *
	 * @param ope
	 *            �u�����@
	 */
	public void setNewNameOperation(StringOpeEnum ope) {
		nameOpe = ope;
	}

	protected CaseEnum nameCase;

	/**
	 * ���O�P�[�X�ϊ��ݒ�.
	 *
	 * @param c
	 *            �ϊ����@
	 */
	public void setNewNameCase(CaseEnum c) {
		nameCase = c;
	}

	protected String newLet;

	/**
	 * �ϊ�����L���ݒ�.
	 *
	 * @param s
	 *            ����L��
	 */
	public void setNewLet(String s) {
		newLet = s;
	}

	protected String newValue;

	/**
	 * �ϊ������l�ݒ�.
	 *
	 * @param s
	 *            �����l
	 */
	public void setNewValue(String s) {
		newValue = s;
	}

	protected StringOpeEnum valueOpe = new StringOpeEnum(StringOpeEnum.LET);

	/**
	 * �����l�u�����@�ݒ�.
	 *
	 * @param ope
	 *            �u�����@
	 */
	public void setNewValueOperation(StringOpeEnum ope) {
		valueOpe = ope;
	}

	protected CaseEnum valueCase;

	/**
	 * �����l�P�[�X�ϊ��ݒ�.
	 *
	 * @param c
	 *            �ϊ����@
	 */
	public void setNewValueCase(CaseEnum c) {
		valueCase = c;
	}

	protected HtmlEscape htmlEscape;

	/**
	 * �����lHTML�G�X�P�[�v�ݒ�.
	 *
	 * @param esc
	 *            �G�X�P�[�v���@
	 */
	public void setNewValueHtmlEscape(HtmlEscapeEnum esc) {
		htmlEscape = new HtmlEscape(esc.isDq(), esc.isSq());
	}

	protected String newQuote;

	/**
	 * �ϊ��N�H�[�e�[�V�����ݒ�.
	 *
	 * @param s
	 *            �N�H�[�e�[�V����
	 */
	public void setNewQuote(String s) {
		newQuote = s;
	}

	@Override
	public void validate() throws BuildException {
		super.validate();

		if (newValue != null) {
			if (newLet == null) {
				newLet = "=";
			}
			if (newQuote == null) {
				newQuote = "\"";
			}
		}
		if (newName != null) {
			nameOpe.setProject(getProject());
			nameOpe.validate(nameMatch, getLocation());
		}
		if (newValue != null) {
			valueOpe.setProject(getProject());
			valueOpe.validate(valueMatch, getLocation());
		}
	}

	protected boolean update(Tag tag, AttributeToken a) {
		boolean ret = false;
		ret |= updateName(tag, a);
		ret |= updateLet(tag, a);
		ret |= updateValue(tag, a);
		ret |= updateQuote(tag, a);
		if (ret) {
			int line = a.getLine();
			if (line <= 0) {
				line = tag.getLine();
			}
			htmlConverter.logConvert("rep", line, a);
		}
		return ret;
	}

	protected boolean updateName(Tag tag, AttributeToken a) {
		if (newName == null && nameCase == null) {
			return false;
		}

		String o = a.getName();
		String n = o;
		if (newName != null) {
			n = nameOpe.convert(n, newName);
		}
		if (nameCase != null) {
			n = nameCase.convert(n);
		}
		if (!htmlConverter.equals(n, o)) {
			a.setName(n);
			return true;
		}

		return false;
	}

	protected boolean updateLet(Tag tag, AttributeToken a) {
		if (newLet == null) {
			return false;
		}

		String o = a.getLet();
		if (!htmlConverter.equals(newLet, o)) {
			a.setLet(newLet);
			return true;
		}

		return false;
	}

	protected boolean updateValue(Tag tag, AttributeToken a) {
		if (newValue == null && valueCase == null && htmlEscape == null) {
			return false;
		}

		String o = a.getValue();
		String n = o;
		if (newValue != null) {
			try {
				n = valueOpe.convert(n, newValue);
			} catch (BuildException e) {
				if (e.getLocation() != null) {
					e.setLocation(getLocation());
				}
				throw e;
			} catch (Exception e) {
				throw new BuildException(e, getLocation());
			}
		}
		if (valueCase != null) {
			n = valueCase.convert(n);
		}
		if (htmlEscape != null) {
			n = htmlEscape.escape(n);
		}
		if (!htmlConverter.equals(n, o)) {
			String q1 = newQuote;
			String q2 = newQuote;
			ValueToken ot = a.getValueToken();
			if (ot != null) {
				q1 = ot.getQuote1();
				q2 = ot.getQuote2();
			}
			HtLexer lexer = htmlConverter.getLexer(n);
			ValueToken vt;
			try {
				vt = lexer.parseAttrValue(q1, q2);
			} catch (IOException e) {
				throw new BuildException(e, getLocation());
			} finally {
				try {
					lexer.close();
				} catch (IOException e) {
				}
			}
			if (ot != null) {
				vt.calcLine(ot.getLine());
			}
			a.setValue(vt);
			doSetProperty(a);
			return true;
		}

		return false;
	}

	protected boolean updateQuote(Tag tag, AttributeToken a) {
		if (newQuote == null) {
			return false;
		}

		ValueToken vt = a.getValueToken();
		if (vt == null) {
			vt = new ValueToken();
			a.setValue(vt);
		}

		boolean ret = false;

		String o1 = vt.getQuote1();
		if (!htmlConverter.equals(newQuote, o1)) {
			vt.setQuote1(newQuote);
			ret |= true;
		}
		String o2 = vt.getQuote2();
		if (!htmlConverter.equals(newQuote, o2)) {
			vt.setQuote2(newQuote);
			ret |= true;
		}

		return ret;
	}
}
