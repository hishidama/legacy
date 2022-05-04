package jp.hishidama.html.lexer.rule;

import java.io.IOException;

import jp.hishidama.html.lexer.reader.Char;

/**
 * HtHtmlLexer区切りルール.
 *<p>
 * 各{@link HtLexerRule ルール}において解釈終了の判断を行うインターフェース。
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/htlexer.html"
 *         >ひしだま</a>
 * @since 2009.01.25
 */
public interface SeparateRule {

	/**
	 * 解釈終了判断.
	 *
	 * @param ch
	 *            先頭1文字
	 * @return 解釈終了の場合、true
	 * @throws IOException
	 */
	public boolean isEnd(Char ch) throws IOException;
}
