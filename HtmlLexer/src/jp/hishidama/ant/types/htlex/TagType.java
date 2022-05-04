package jp.hishidama.ant.types.htlex;

import java.lang.reflect.Method;
import java.util.*;

import jp.hishidama.ant.types.htlex.eval.HtLexerExpRuleFactory;
import jp.hishidama.eval.Expression;
import jp.hishidama.html.lexer.token.AttributeToken;
import jp.hishidama.html.lexer.token.Tag;
import jp.hishidama.html.parser.elem.HtElement;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.taskdefs.condition.*;
import org.apache.tools.ant.types.DataType;

/**
 * HtHtmlLexer�^�O�����^�C�v.
 *<p>
 * HTML�t�@�C�����̃^�O�̏������f���s���f�[�^�^�C�v�B
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/ant/htlex.html"
 *         >�Ђ�����</a>
 * @since 2009.01.20
 * @version 2010.01.31
 */
public class TagType extends DataType {

	protected MatchEnum nameMatch;

	/**
	 * ���O�����ݒ�.
	 *
	 * @param name
	 *            �^�O��
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

	protected String tago, tagc;

	/**
	 * �^�O�J�������ݒ�.
	 *
	 * @param s
	 *            �^�O�J��
	 */
	public void setTagO(String s) {
		tago = s;
	}

	/**
	 * �^�O�������ݒ�.
	 *
	 * @param s
	 *            �^�O��
	 */
	public void setTagC(String s) {
		tagc = s;
	}

	protected PairTagEnum pair = new PairTagEnum(PairTagEnum.IGNORE);

	/**
	 * �y�A�����ݒ�.
	 *
	 * @param p
	 *            �y�A����
	 */
	public void setPair(PairTagEnum p) {
		this.pair = p;
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

	protected Condition attrCond = null;

	/**
	 * �����ǉ�.
	 *
	 * @param cond
	 *            ����
	 */
	public void addConfigured(Condition cond) {
		if (attrCond == null) {
			attrCond = cond;
		} else if (attrCond instanceof And) {
			((And) attrCond).add(cond);
		} else {
			And and = new And();
			and.setProject(this.getProject());
			and.add(attrCond);
			and.add(cond);
			attrCond = and;
		}
	}

	/**
	 * And�����ǉ�.
	 *
	 * @param cond
	 *            ����
	 */
	public void addConfiguredAnd(And cond) {
		addConfigured(cond);
	}

	/**
	 * Or�����ǉ�.
	 *
	 * @param cond
	 *            ����
	 */
	public void addConfiguredOr(Or cond) {
		addConfigured(cond);
	}

	/**
	 * Not�����ǉ�.
	 *
	 * @param cond
	 *            ����
	 */
	public void addConfiguredNot(Not cond) {
		addConfigured(cond);
	}

	protected List<TagNewType> tagList = new ArrayList<TagNewType>();

	/**
	 * �^�O�ϊ����ǉ�.
	 *
	 * @param tag
	 *            �^�O�ϊ��^�C�v
	 */
	public void addConfigured(TagNewType tag) {
		tagList.add(tag);
	}

	protected HtLexerConverter converter;

	/**
	 * �������s.
	 *
	 * @param conv
	 *            �R���o�[�^�[
	 * @throws BuildException
	 *             �����G���[��
	 */
	public void validate(HtLexerConverter conv) throws BuildException {
		converter = conv;

		if (pair.getIndex() >= PairTagEnum.PARSER_INDEX) {
			converter.setUseParser(true);
		}

		if (attrCond != null) {
			validateCondition(attrCond, conv);
		}

		for (TagNewType tt : tagList) {
			tt.validate(conv);
		}
	}

	protected void validateCondition(Condition c, HtLexerConverter conv)
			throws BuildException {
		if (c == null) {
			throw new BuildException("condition must be set.", getLocation());
		}
		if (c instanceof AttrCondType) {
			AttrCondType ac = (AttrCondType) c;
			ac.initHtLexerConverter(conv);
			ac.validate(this);
			return;
		}

		// and,or,not�̂݋���
		if (c instanceof And || c instanceof Or || c instanceof Not) {
			Enumeration<Condition> n = getBaseConditions(c);
			while (n.hasMoreElements()) {
				validateCondition(n.nextElement(), conv);
			}
			return;
		} else {
			throw new BuildException(c.getClass().getSimpleName()
					+ " can not be used.", getLocation());
		}
	}

	protected Method GetConditionMethod;

	@SuppressWarnings("unchecked")
	protected Enumeration<Condition> getBaseConditions(Object obj) {
		try {
			if (GetConditionMethod == null) {
				GetConditionMethod = ConditionBase.class.getDeclaredMethod(
						"getConditions", (Class[]) null);
				GetConditionMethod.setAccessible(true);
			}
			return (Enumeration<Condition>) GetConditionMethod.invoke(obj,
					(Object[]) null);
		} catch (Exception e) {
			throw new BuildException(e, getLocation());
		}
	}

	/**
	 * �^�O�ϊ�.
	 *
	 * @param tag
	 *            �^�O
	 * @param he
	 *            �^�O�̑����Ă���v�f�i��͂���Ă��Ȃ��ꍇ�Anull�j
	 * @return ���e��ύX�����ꍇ�Atrue
	 */
	public boolean convert(Tag tag, HtElement he) {
		converter.propertyHelper.pushTagToken(tag);
		try {
			boolean ret = false;
			if (matches(tag, he)) {
				converter.logMatch(tag);
				for (TagNewType tt : tagList) {
					ret |= tt.convert(tag, he);
				}
			}
			return ret;
		} finally {
			converter.propertyHelper.popTagToken();
		}
	}

	protected List<AttributeToken> tokenList;

	/**
	 * �����Ώۑ����ꗗ�擾.
	 *
	 * @return �����ꗗ
	 * @see AttrCondType#eval()
	 */
	public List<AttributeToken> getTokenList() {
		return tokenList;
	}

	/**
	 * �}�b�`���O���f.
	 *
	 * @param tag
	 *            �^�O
	 * @param he
	 *            �^�O�̑����Ă���v�f�i��͂���Ă��Ȃ��ꍇ�Anull�j
	 * @return �w�肳��Ă��������Ƀ}�b�`�����ꍇ�Atrue
	 */
	public boolean matches(Tag tag, HtElement he) {
		String name = tag.getName();
		if (!equalsName(name)) {
			return false;
		}
		if (!equalsTagO(tag.getTag1())) {
			return false;
		}
		if (!equalsTagC(tag.getTag2())) {
			return false;
		}
		if (attrCond != null) {
			tokenList = tag.getAttributeList();
			if (!attrCond.eval()) {
				return false;
			}
		}
		if (!pair.matches(tag, he)) {
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

	protected boolean equalsTagO(String t) {
		if (tago == null) {
			return true;
		}
		return tago.equals(t);
	}

	protected boolean equalsTagC(String t) {
		if (tagc == null) {
			return true;
		}
		return tagc.equals(t);
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
		} catch (RuntimeException e) {
			throw new BuildException(e, getLocation());
		}
		return true;
	}
}
