package jp.hishidama.html.lexer.token;

import java.util.ArrayList;
import java.util.List;

/**
 * HtHtmlLexerトークン（タグ）.
 * <p>
 * タグ（{@literal <tag～>や</tag>}）を保持するトークン。
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/htlexer.html"
 *         >ひしだま</a>
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
	 * コンストラクター.
	 */
	public Tag() {
	}

	/**
	 * 属性追加.
	 *
	 * @param attr
	 *            属性
	 */
	public void addAttribute(AttributeToken attr) {
		addBeforeTagC(attr);
	}

	/**
	 * 開始タグ判断.
	 *
	 * @return 開始タグ（TAGOが「&lt;」）のとき、true
	 */
	public boolean isStart() {
		return "<".equals(getTag1());
	}

	/**
	 * 終了タグ判断.
	 *
	 * @return 終了タグ（TAGOが「&lt;/」または TAGCが「/&gt;」）のとき、true
	 */
	public boolean isEnd() {
		return "</".equals(getTag1()) || "/>".equals(getTag2());
	}

	/**
	 * 属性一覧取得.
	 *
	 * @return 属性一覧（必ずnull以外）
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
	 * 属性取得.
	 *
	 * @param name
	 *            属性名（大文字小文字は無視される）
	 * @return 属性（存在しない場合、null）
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
	 * 属性値取得.
	 *
	 * @param name
	 *            属性名（大文字小文字は無視される）
	 * @return 属性値（存在しない場合、null）
	 */
	public String getAttributeValue(String name) {
		AttributeToken attr = getAttribute(name);
		if (attr != null) {
			return attr.getValue();
		}
		return null;
	}

	/**
	 * 属性値置換.
	 * <p>
	 * 属性が存在しない場合は末尾に追加する。
	 * </p>
	 *
	 * @param name
	 *            属性名（大文字小文字は無視される）
	 * @param value
	 *            属性値
	 * @return 変更があった場合、true
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
