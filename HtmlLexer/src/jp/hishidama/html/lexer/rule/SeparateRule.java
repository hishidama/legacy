package jp.hishidama.html.lexer.rule;

import java.io.IOException;

import jp.hishidama.html.lexer.reader.Char;

/**
 * HtHtmlLexer��؂胋�[��.
 *<p>
 * �e{@link HtLexerRule ���[��}�ɂ����ĉ��ߏI���̔��f���s���C���^�[�t�F�[�X�B
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/htlexer.html"
 *         >�Ђ�����</a>
 * @since 2009.01.25
 */
public interface SeparateRule {

	/**
	 * ���ߏI�����f.
	 *
	 * @param ch
	 *            �擪1����
	 * @return ���ߏI���̏ꍇ�Atrue
	 * @throws IOException
	 */
	public boolean isEnd(Char ch) throws IOException;
}
