package jp.hishidama.html.lexer.token;

/**
 * HtHtmlLexer�g�[�N���i��؂蕶���j.
 *<p>
 * ��؂蕶���i{@link SpaceAtom ��}��{@link CrLfAtom ���s}�j������ێ�����g�[�N���B
 * </p>
 *
 * @author <a target="hishidama" href=
 *         "http://www.ne.jp/asahi/hishidama/home/tech/soft/java/htlexer.html"
 *         >�Ђ�����</a>
 * @since 2009.01.10
 */
public final class SkipToken extends TextToken {

	/**
	 * @since 2009.02.07
	 */
	@Override
	public SkipToken clone() throws CloneNotSupportedException {
		return new SkipToken(this);
	}

	protected SkipToken(SkipToken o) {
		super(o);
	}

	/**
	 * �R���X�g���N�^�[.
	 */
	public SkipToken() {
	}
}
