package jp.hishidama.ant.types.htlex;

import java.util.ArrayList;
import java.util.List;

import jp.hishidama.ant.types.TextParameter;
import jp.hishidama.ant.types.htlex.eval.HtLexerExpRuleFactory;
import jp.hishidama.eval.Expression;
import jp.hishidama.html.lexer.token.ListToken;
import jp.hishidama.html.lexer.token.Tag;
import jp.hishidama.html.lexer.token.TextToken;
import jp.hishidama.html.lexer.token.Token;
import jp.hishidama.html.parser.elem.HtElement;
import jp.hishidama.html.parser.elem.HtListElement;
import jp.hishidama.html.parser.elem.HtTagElement;
import jp.hishidama.html.parser.elem.HtTokenElement;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.IntrospectionHelper;
import org.apache.tools.ant.Location;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.types.DataType;

/**
 * HtHtmlLexer�t�@�C�������^�C�v.
 *<p>
 * HTML�t�@�C����ǂݍ��ޑO�ɂ��̃t�@�C���������ΏۂƂ��邩�ǂ����̏�����ێ�����f�[�^�^�C�v�B
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/ant/htlex.html"
 *         >�Ђ�����</a>
 * @since 2010.02.06
 */
public class FileCondType extends DataType {

	protected Expression ifExp;

	/**
	 * �������ݒ�.
	 *
	 * @param s
	 *            ������
	 */
	public void setIf(String s) {
		ifExp = HtLexerExpRuleFactory.getRule(getProject()).parse(s);
	}

	protected final List<TagType> tagList = new ArrayList<TagType>();

	/**
	 * �^�O�����ǉ�.
	 *
	 * @param tag
	 *            �^�O�^�C�v
	 */
	public void addConfigured(TagType tag) {
		tagList.add(tag);
	}

	/**
	 * �p�����[�^�[�ݒ�.
	 * <p>
	 * �����l�ɏ����Â炢�������Ȃǂ��p�����[�^�[�̃{�f�B�[���ɋL�q�ł���B<br>
	 * {@code <cfile><param name="foo">bar</param></cfile>}�̏ꍇ�A{@code <cfile
	 * foo="bar">}�Ɠ����B
	 * </p>
	 *
	 * @param param
	 *            �p�����[�^�[
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

	protected final List<TextType> textList = new ArrayList<TextType>();

	/**
	 * �e�L�X�g�����ǉ�.
	 *
	 * @param text
	 *            �e�L�X�g�^�C�v
	 */
	public void addConfigured(TextType text) {
		textList.add(text);
	}

	protected HtLexerConverter converter;

	/**
	 * �������s.
	 *
	 * @param conv
	 *
	 * @throws BuildException
	 *             �����G���[��
	 */
	public void validate(HtLexerConverter conv) throws BuildException {
		converter = conv;
		for (TagType i : tagList) {
			i.validate(conv);
		}
		for (TextType i : textList) {
			i.validate(conv);
		}
	}

	/**
	 * �ϊ��۔��f.
	 *
	 * @return true�̏ꍇ�AHTML�ύX�����s����
	 */
	public boolean isConvert() {
		if (!equalsIf()) {
			return false;
		}

		return true;
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
	 * HTML�ύX.
	 * <p>
	 * {@link jp.hishidama.ant.taskdefs.HtLexerTask}�ɂ����HTML���ǂݍ��܂ꂽ��ɌĂ΂��B<br>
	 * �iHtHtmlParser�ɂ���č\����͂��ꂽ�ꍇ�ɓ����\�b�h���Ă΂��j<br>
	 * �����̓��e��ύX����true��Ԃ��ƁA���̓��e���t�@�C���ɏo�͂���B
	 * </p>
	 * <p>
	 * �Ǝ��̕ϊ����s�������ꍇ�́A�����\�b�h���I�[�o�[���C�h����HTML�̕ϊ����W�b�N����������B
	 * </p>
	 *
	 * @param elist
	 *            �\����͂��ꂽHTML
	 * @return elist�̓��e��ύX�����ꍇ�Atrue
	 * @see HtLexerConverter#setUseParser(boolean)
	 */
	public boolean convert(HtListElement elist) {
		boolean ret = false;

		ret |= convertLoop(elist);
		return ret;
	}

	protected boolean convertLoop(HtElement he) {
		if (he instanceof HtTokenElement) {
			HtTokenElement te = (HtTokenElement) he;
			Token t = te.getToken();
			if (t != null) {
				return convertToken(t, he);
			} else {
				return false;
			}
		}

		boolean ret = false;

		HtTagElement te = null;
		if (he instanceof HtTagElement) {
			te = (HtTagElement) he;
			Tag stag = te.getStartTag();
			if (stag != null) {
				ret |= convertTag(stag, he);
			}
		}

		HtListElement elist = (HtListElement) he;
		for (int i = 0; i < elist.size(); i++) {
			HtElement e = elist.get(i);
			if (e != null) {
				ret |= convertLoop(e);
			}
		}

		if (te != null) {
			Tag etag = te.getEndTag();
			if (etag != null) {
				ret |= convertTag(etag, he);
			}
		}

		return ret;
	}

	/**
	 * HTML�ύX.
	 * <p>
	 * {@link jp.hishidama.ant.taskdefs.HtLexerTask}�ɂ����HTML���ǂݍ��܂ꂽ��ɌĂ΂��B<br>
	 * �iHtHtmlParser�ɂ���č\����͂���Ă��Ȃ��ꍇ�ɓ����\�b�h���Ă΂��j<br>
	 * �����̓��e��ύX����true��Ԃ��ƁA���̓��e���t�@�C���ɏo�͂���B
	 * </p>
	 * <p>
	 * �Ǝ��̕ϊ����s�������ꍇ�́A�����\�b�h���I�[�o�[���C�h����HTML�̕ϊ����W�b�N����������B
	 * </p>
	 *
	 * @param tlist
	 *            �ǂݍ��܂ꂽHTML
	 * @return tlist�̓��e��ύX�����ꍇ�Atrue
	 * @see HtLexerConverter#setUseParser(boolean)
	 */
	public boolean convert(ListToken tlist) {
		boolean ret = false;
		for (Token t : tlist) {
			ret |= convertToken(t, null);
		}
		return ret;
	}

	protected boolean convertToken(Token t, HtElement he) {
		if (t instanceof Tag) {
			Tag tag = (Tag) t;
			return convertTag(tag, he);
		} else if (t instanceof TextToken) {
			TextToken text = (TextToken) t;
			return convertText(text, he);
		}
		return false;
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
	public boolean convertTag(Tag tag, HtElement he) {
		boolean ret = false;

		for (TagType i : tagList) {
			ret |= i.convert(tag, he);
		}

		return ret;
	}

	/**
	 * �e�L�X�g�ϊ�.
	 *
	 * @param text
	 *            �e�L�X�g
	 * @param he
	 *            �e�L�X�g�̑����Ă���v�f�i��͂���Ă��Ȃ��ꍇ�Anull�j
	 * @return ���e��ύX�����ꍇ�Atrue
	 */
	public boolean convertText(TextToken text, HtElement he) {
		boolean ret = false;

		for (TextType i : textList) {
			ret |= i.convert(text, he);
		}

		return ret;
	}
}

class EmptyFileCondType extends FileCondType {

	EmptyFileCondType(Project project, Location location) {
		setProject(project);
		setLocation(location);
	}

	@Override
	public boolean isConvert() {
		return true;
	}
}