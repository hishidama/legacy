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
 * HtHtmlLexerタグ属性演算のプロパティーヘルパー.
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/ant/htlex.html"
 *         >ひしだま</a>
 * @since 2010.01.31
 */
public class HtLexerPropertyHelper extends PropertyHelper {

	/**
	 * プロパティーヘルパーインスタンス取得.
	 *
	 * @param project
	 *            Antプロジェクト
	 * @param prefix
	 *            プロパティー接頭辞
	 * @return プロパティーヘルパー
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

	/** プロパティー名：タグ */
	public static final String KEY_TAG = "tag";
	/** プロパティー名：属性 */
	public static final String KEY_ATTR = "attr";
	/** プロパティー名：属性リスト */
	public static final String KEY_ATTRS = "attrs";
	/** 処理対象ファイル名 */
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
	 * タグトークン保持.
	 *
	 * @param tag
	 *            タグトークン
	 */
	public void pushTagToken(Tag tag) {
		if (tagTokenList == null) {
			tagTokenList = new ArrayList<Tag>();
		}
		tagTokenList.add(tag);
	}

	/**
	 * タグトークン破棄.
	 */
	public void popTagToken() {
		tagTokenList.remove(tagTokenList.size() - 1);
	}

	/**
	 * タグトークン取得.
	 * <p>
	 * 保持されているタグトークンの内、最も新しいトークンを返す。
	 * </p>
	 *
	 * @return タグトークン（保持されていない場合、null）
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
	 * 属性トークン保持.
	 *
	 * @param a
	 *            属性トークン
	 */
	public void pushAttributeToken(AttributeToken a) {
		if (attrTokenList == null) {
			attrTokenList = new ArrayList<AttributeToken>();
		}
		attrTokenList.add(a);
	}

	/**
	 * 属性トークン破棄.
	 */
	public void popAttributeToken() {
		attrTokenList.remove(attrTokenList.size() - 1);
	}

	/**
	 * 属性トークン取得.
	 * <p>
	 * 保持されているタグトークンの内、最も新しいトークンを返す。
	 * </p>
	 *
	 * @return 属性トークン（保持されていない場合、null）
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
