package jp.hishidama.ant.types.htlex.eval;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.hishidama.ant.types.htlex.HtLexerConverter;
import jp.hishidama.html.lexer.token.AttributeToken;
import jp.hishidama.html.lexer.token.Tag;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.PropertyHelper;

/**
 * HtHtmlLexer�^�O�������Z�̃v���p�e�B�[�w���p�[.
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/ant/htlex.html"
 *         >�Ђ�����</a>
 * @since 2010.01.31
 */
public class HtLexerPropertyHelper extends PropertyHelper {

	/**
	 * �v���p�e�B�[�w���p�[�C���X�^���X�擾.
	 *
	 * @param project
	 *            Ant�v���W�F�N�g
	 * @param prefix
	 *            �v���p�e�B�[�ړ���
	 * @return �v���p�e�B�[�w���p�[
	 */
	public static HtLexerPropertyHelper getInstance(Project project,
			String prefix) {
		PropertyHelper rootHelper = PropertyHelper.getPropertyHelper(project);
		String key = HtLexerPropertyHelper.class.getName() + "." + prefix;
		HtLexerPropertyHelper helper = (HtLexerPropertyHelper) rootHelper
				.getProperty(key);
		if (helper == null) {
			helper = new HtLexerPropertyHelper();
			rootHelper.setProperty(key, helper, false);
			helper.setProject(project);
			helper.setNext(rootHelper.getNext());
			rootHelper.setNext(helper);

			helper.setPrefix(prefix);
		}

		return helper;
	}

	protected String prefix;

	/** �v���p�e�B�[���F�^�O */
	public static final String KEY_TAG = "tag";
	/** �v���p�e�B�[���F���� */
	public static final String KEY_ATTR = "attr";
	/** �v���p�e�B�[���F�������X�g */
	public static final String KEY_ATTRS = "attrs";
	/** �����Ώۃt�@�C���� */
	public static final String KEY_RFILE = "rfile";

	public void setPrefix(String s) {
		if (!s.endsWith(".")) {
			s += ".";
		}
		prefix = s;
	}

	protected HtLexerConverter conv;

	public void setConverter(HtLexerConverter conv) {
		this.conv = conv;
	}

	protected List<Tag> tagTokenList = null;

	/**
	 * �^�O�g�[�N���ێ�.
	 *
	 * @param tag
	 *            �^�O�g�[�N��
	 */
	public void pushTagToken(Tag tag) {
		if (tagTokenList == null) {
			tagTokenList = new ArrayList<Tag>();
		}
		tagTokenList.add(tag);
	}

	/**
	 * �^�O�g�[�N���j��.
	 */
	public void popTagToken() {
		tagTokenList.remove(tagTokenList.size() - 1);
	}

	/**
	 * �^�O�g�[�N���擾.
	 * <p>
	 * �ێ�����Ă���^�O�g�[�N���̓��A�ł��V�����g�[�N����Ԃ��B
	 * </p>
	 *
	 * @return �^�O�g�[�N���i�ێ�����Ă��Ȃ��ꍇ�Anull�j
	 */
	public Tag getTagToken() {
		int i = tagTokenList.size() - 1;
		if (i < 0) {
			return null;
		}
		return tagTokenList.get(i);
	}

	protected List<AttributeToken> attrTokenList = null;

	/**
	 * �����g�[�N���ێ�.
	 *
	 * @param a
	 *            �����g�[�N��
	 */
	public void pushAttributeToken(AttributeToken a) {
		if (attrTokenList == null) {
			attrTokenList = new ArrayList<AttributeToken>();
		}
		attrTokenList.add(a);
	}

	/**
	 * �����g�[�N���j��.
	 */
	public void popAttributeToken() {
		attrTokenList.remove(attrTokenList.size() - 1);
	}

	/**
	 * �����g�[�N���擾.
	 * <p>
	 * �ێ�����Ă���^�O�g�[�N���̓��A�ł��V�����g�[�N����Ԃ��B
	 * </p>
	 *
	 * @return �����g�[�N���i�ێ�����Ă��Ȃ��ꍇ�Anull�j
	 */
	public AttributeToken getAttributeToken() {
		int i = attrTokenList.size() - 1;
		if (i < 0) {
			return null;
		}
		return attrTokenList.get(i);
	}

	@Override
	public Object getPropertyHook(String ns, String name, boolean user) {
		if (name != null && name.startsWith(prefix)) {
			initPropMap();
			String key = name.substring(prefix.length());
			Prop prop = PROP_MAP.get(key);
			if (prop != null) {
				return prop.getValue();
			}
			return null;
		}

		return super.getPropertyHook(ns, name, user);
	}

	protected Map<String, Prop> PROP_MAP;

	protected void initPropMap() {
		if (PROP_MAP != null) {
			return;
		}
		PROP_MAP = new HashMap<String, Prop>();
		PROP_MAP.put(KEY_TAG, new TagProp());
		PROP_MAP.put(KEY_ATTR, new AttrProp());
		PROP_MAP.put(KEY_ATTRS, new AttrsProp());
		PROP_MAP.put(KEY_RFILE, new RFileProp());
	}

	abstract class Prop {
		abstract Object getValue();
	}

	class TagProp extends Prop {
		@Override
		Object getValue() {
			return getTagToken();
		}
	}

	class AttrProp extends Prop {
		@Override
		Object getValue() {
			return getAttributeToken();
		}
	}

	class AttrsProp extends Prop {
		@Override
		Object getValue() {
			Tag tag = getTagToken();
			return tag.getAttributeList();
		}
	}

	class RFileProp extends Prop {
		@Override
		Object getValue() {
			return conv.getReadFile();
		}
	}
}
