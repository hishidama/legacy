package jp.hishidama.html.lexer.token;

import java.util.ArrayList;
import java.util.List;

/**
 * HtHtmlLexerトークン（XML宣言）.
 *<p>
 * XML宣言（{@literal <?xml～?>}）を保持するトークン。
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/htlexer.html"
 *         >ひしだま</a>
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
	 * コンストラクター.
	 */
	public XmlDeclare() {
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
}
