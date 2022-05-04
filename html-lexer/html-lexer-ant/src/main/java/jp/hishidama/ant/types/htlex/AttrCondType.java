package jp.hishidama.ant.types.htlex;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.taskdefs.condition.Condition;

import jp.hishidama.html.lexer.token.AttributeToken;

/**
 * HtHtmlLexerタグ属性タイプ.
 *<p>
 * HTMLファイル内のタグの属性の条件判断を行うデータタイプ。
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/ant/htlex.html"
 *         >ひしだま</a>
 * @since 2009.01.21
 * @version 2010.01.31
 */
public class AttrCondType extends AttrType implements Condition {

	protected TagType tag;

	/**
	 * 精査実行.
	 *
	 * @param tag
	 *            タグタイプ
	 * @throws BuildException
	 *             精査エラー時
	 */
	public void validate(TagType tag) throws BuildException {
		this.tag = tag;

		if (nameMatch == null && valueMatch == null && let == null
				&& quote == null && ifExp == null) {
			throw new BuildException("attribute must be set.", getLocation());
		}
	}

	/**
	 * 条件判断実行.
	 *
	 * @return 指定されていた条件にマッチした場合、true
	 */
	@Override
	public boolean eval() {
		for (AttributeToken a : tag.getTokenList()) {
			pushAttributeToken(a);
			try {
				if (matches(a)) {
					doSetProperty(a);
					return true;
				}
			} finally {
				popAttributeToken();
			}
		}
		return false;
	}
}
