package jp.hishidama.ant.types.htlex;

import java.util.List;

import jp.hishidama.html.lexer.token.AttributeToken;
import jp.hishidama.html.lexer.token.ListToken;
import jp.hishidama.html.lexer.token.Tag;

import org.apache.tools.ant.BuildException;

/**
 * HtHtmlLexerタグ属性操作タイプ.
 *<p>
 * HTMLファイル内のタグの属性の操作を管理するデータタイプ。
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/ant/htlex.html"
 *         >ひしだま</a>
 * @since 2009.01.23
 */
public class AttrOpeType extends AttrType {

	/**
	 * 精査実行.
	 *
	 * @throws BuildException
	 *             精査エラー時
	 */
	public void validate() throws BuildException {
		// オーバーライドされる
	}

	/**
	 * タグ属性変換実行.
	 *
	 * @param tag
	 *            タグ
	 * @return 内容を変更した場合、true
	 */
	public boolean convert(Tag tag) {
		boolean ret = false;
		List<AttributeToken> list = tag.getAttributeList();
		for (AttributeToken a : list) {
			pushAttributeToken(a);
			try {
				if (matches(a)) {
					ret |= convert(tag, a);
				}
			} finally {
				popAttributeToken();
			}
		}
		return ret;
	}

	protected boolean convert(Tag tag, AttributeToken a) {
		// オーバーライドする
		return false;
	}

	protected boolean delete(Tag tag, AttributeToken a) {
		ListToken r = tag.cutWithPreSkip(a);
		return r != null;
	}
}
