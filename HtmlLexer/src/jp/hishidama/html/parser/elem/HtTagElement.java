package jp.hishidama.html.parser.elem;

import java.io.IOException;
import java.io.Writer;

import jp.hishidama.html.lexer.token.ListToken;
import jp.hishidama.html.lexer.token.Tag;
import jp.hishidama.html.lexer.token.Token;

/**
 * HTML�^�O�v�f.
 *<p>
 * HtHtmlParser�ɂ�����A�^�O�Ɉ͂܂ꂽ�v�f�B
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/htlexer.html"
 *         >�Ђ�����</a>
 * @since 2009.02.08
 * @version 2009.02.21
 */
public class HtTagElement extends HtListElement {

	protected Tag stag;
	protected Tag etag;

	/**
	 * �J�n�^�O�ݒ�.
	 *
	 * @param tag
	 *            �^�O
	 */
	public void setStartTag(Tag tag) {
		stag = tag;
		if (stag != null) {
			name = stag.getName().toLowerCase();
		}
	}

	/**
	 * �I���^�O�ݒ�.
	 *
	 * @param tag
	 *            �^�O
	 */
	public void setEndTag(Tag tag) {
		etag = tag;
		if (etag != null) {
			name = etag.getName().toLowerCase();
		}
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * ���N���X�ł́A���true�B
	 * </p>
	 */
	@Override
	public boolean isTag() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * ���N���X�ł́A���true�B
	 * </p>
	 */
	@Override
	public boolean isStart() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * ���N���X�ł́A���true�B
	 * </p>
	 */
	@Override
	public boolean isEnd() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * �J�n�^�O���ȗ�����Ă���ꍇ��null���Ԃ�B
	 * </p>
	 */
	@Override
	public Tag getStartTag() {
		return stag;
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * �I���^�O���ȗ�����Ă���ꍇ ���邢�� �J�n�^�O���I���^�O�����˂Ă���ꍇ��null���Ԃ�B
	 * </p>
	 */
	@Override
	public Tag getEndTag() {
		return etag;
	}

	@Override
	public boolean isEmpty() {
		if (stag != null || etag != null) {
			return false;
		}
		return super.isEmpty();
	}

	@Override
	public int getTextLength() {
		int len = 0;
		if (stag != null) {
			len += stag.getTextLength();
		}

		len += super.getTextLength();

		if (etag != null) {
			len += etag.getTextLength();
		}
		return len;
	}

	@Override
	public void writeTo(StringBuilder sb) {
		if (stag != null) {
			stag.writeTo(sb);
		}

		super.writeTo(sb);

		if (etag != null) {
			etag.writeTo(sb);
		}
	}

	@Override
	public void writeTo(Writer w) throws IOException {
		if (stag != null) {
			stag.writeTo(w);
		}

		super.writeTo(w);

		if (etag != null) {
			etag.writeTo(w);
		}
	}

	@Override
	public HtElement searchToken(Token t) {
		if (stag == t || etag == t) {
			return this;
		} else {
			return super.searchToken(t);
		}
	}

	@Override
	public void toToken(ListToken tlist) {
		if (stag != null) {
			tlist.add(stag);
		}

		super.toToken(tlist);

		if (etag != null) {
			tlist.add(etag);
		}
	}
}
