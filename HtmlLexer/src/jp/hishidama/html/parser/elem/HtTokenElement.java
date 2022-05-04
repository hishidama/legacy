package jp.hishidama.html.parser.elem;

import java.io.IOException;
import java.io.Writer;

import jp.hishidama.html.lexer.token.ListToken;
import jp.hishidama.html.lexer.token.Tag;
import jp.hishidama.html.lexer.token.Token;
import jp.hishidama.html.parser.rule.HtParser;

/**
 * �g�[�N���v�f.
 *<p>
 * HtHtmlParser�ɂ�����A{@link HtTagElement �^�O}�ȊO�̗v�f�B
 * </p>
 * <p>
 * {@link Token HtHtmlLexer�̃g�[�N��}�����̂܂ܕێ�����B<br>
 * ���Ȃ킿�A�e�L�X�g��R�����g�EDOCTYPE�ȂǁB<br>
 * �܂��A{@link HtParser#parse(jp.hishidama.html.lexer.token.ListToken) �p�[�T�[}
 * �ɂ���Ċm��ł��Ȃ������^�O�����N���X�ŕێ�����B
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/htlexer.html"
 *         >�Ђ�����</a>
 * @since 2009.02.08
 * @version 2009.02.21
 */
public class HtTokenElement extends HtElement {
	protected Token token;
	protected Tag tag;

	/**
	 * �R���X�g���N�^�[.
	 *
	 * @param t
	 *            �g�[�N��
	 */
	public HtTokenElement(Token t) {
		setToken(t);
	}

	/**
	 * �g�[�N���ݒ�.
	 *
	 * @param t
	 *            �g�[�N��
	 */
	public void setToken(Token t) {
		token = t;
		if (t instanceof Tag) {
			tag = (Tag) t;
			name = tag.getName();
			if (name != null) {
				name = name.toLowerCase();
			}
		} else {
			tag = null;
			name = null;
		}
	}

	/**
	 * �g�[�N���擾.
	 *
	 * @return �g�[�N��
	 */
	public Token getToken() {
		return token;
	}

	@Override
	public boolean isTag() {
		return tag != null;
	}

	@Override
	public boolean isStart() {
		if (tag != null) {
			return tag.isStart();
		}
		return false;
	}

	@Override
	public boolean isEnd() {
		if (tag != null) {
			return tag.isEnd();
		}
		return false;
	}

	@Override
	public Tag getStartTag() {
		if (isStart()) {
			return tag;
		}
		return null;
	}

	@Override
	public Tag getEndTag() {
		if (!isStart()) {
			return tag;
		}
		return null;
	}

	@Override
	public boolean isEmpty() {
		return token == null;
	}

	@Override
	public int getTextLength() {
		if (token != null) {
			return token.getTextLength();
		}
		return 0;
	}

	@Override
	public void writeTo(StringBuilder sb) {
		if (token != null) {
			token.writeTo(sb);
		}
	}

	@Override
	public void writeTo(Writer w) throws IOException {
		if (token != null) {
			token.writeTo(w);
		}
	}

	@Override
	public HtElement searchToken(Token t) {
		if (token == t) {
			return this;
		} else {
			return null;
		}
	}

	@Override
	public void toToken(ListToken tlist) {
		if (token != null) {
			tlist.add(token);
		}
	}
}
