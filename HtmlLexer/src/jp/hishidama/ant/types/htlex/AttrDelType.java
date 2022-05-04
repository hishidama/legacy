package jp.hishidama.ant.types.htlex;

import jp.hishidama.html.lexer.token.AttributeToken;
import jp.hishidama.html.lexer.token.Tag;

/**
 * HtHtmlLexerタグ属性削除タイプ.
 *<p>
 * HTMLファイル内のタグの属性の削除を行うデータタイプ。
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/ant/htlex.html"
 *         >ひしだま</a>
 * @since 2009.01.23
 * @version 2009.02.01
 */
public class AttrDelType extends AttrOpeType {

	@Override
	protected boolean convert(Tag tag, AttributeToken a) {
		boolean ret = delete(tag, a);
		if (ret) {
			htmlConverter.logConvert("del", a.getLine(), a);
		}
		return ret;
	}
}
